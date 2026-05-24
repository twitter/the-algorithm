"""
This module contains utility functions for twml.
"""

import argparse
from datetime import datetime
import itertools
import json
import logging as _logging
import os
import re

from twitter.ml.common.resources import AuroraPath
from twitter.deepbird.hparam import HParams
from twitter.deepbird.io.util import (
  _get_feature_id,  # noqa: F401
  feature_id,  # noqa: F401
  preprocess_feature_regex,  # noqa: F401
  preprocess_path,  # noqa: F401
  sanitize_hdfs_path,  # noqa: F401
  is_string,  # noqa: F401
  list_files,  # noqa: F401
  match_files,  # noqa: F401
)
from twitter.deepbird.io.legacy.util import (
  batch_apply,  # noqa: F401
  boolean_mask,  # noqa: F401
  fixed_length_tensor,  # noqa: F401
)
from twitter.deepbird.sparse.util import (
  convert_to_sparse,  # noqa: F401
  limit_bits,  # noqa: F401
)

from dateutil import rrule
from joblib import delayed, Parallel
from six import string_types

from absl import logging
from libtwml import CLIB, OPLIB  # noqa: F401
import tensorflow.compat.v1 as tf
from tensorflow.python.platform import tf_logging
import twml
from twml.feature_config import FeatureConfigBuilder


# big_prime is less than 2**32
# This just needs to be co-prime with powers of 2
# any large prime is sufficient, but it's not necessary.
HASHING_PRIME = 2479700537


def multiplicative_hash(input, hash_constant=HASHING_PRIME):
  return input * hash_constant


def _return_tensors_from_checkpoint_folder(init_dir, model_name=None):
  """Returns tensors list from a checkpoint folder

  Args:
    init_dir: Name of the checkpoint directory.
    model_name: the model which we will use to obtain the checkpoint
      (e.g. model.ckpt-50000) if set to None it will default to the
      latest model saved in the checkpont file.

  """
  if model_name is None:
    # gets the most recently generated model.cpkt file
    model_path = tf.train.latest_checkpoint(init_dir)
    if model_path is None:
      raise ValueError("Could not find a valid model checkpoint inside the directory")
  else:
    model_path = os.path.join(init_dir, model_name)
  reader = tf.train.NewCheckpointReader(model_path)
  try:
    return (reader.debug_string().decode("utf-8"))
  except OSError:
    logging.error('Could not decode the string')


def get_scope_dict(init_dir, incoming_scope_name, current_scope_name, model_name=None):
  """Returns tensors map from a checkpoint file.

  Args:
    file_name:
      Name of the checkpoint directory.
    incoming_scope_name:
      scope name of the previous phase
    current_scope_name:
      scope name of current phase
    model_name:
      the model which we will use to obtain the checkpoint
      (e.g. model.ckpt-50000) if set to None it will default
      to the latest model saved in the checkpoint file.
  Returns:
    init_map:
      init_map which will be inputted to the checkpoint
  """
  init_map = {}
  reader_dump = _return_tensors_from_checkpoint_folder(init_dir=init_dir,
                                                       model_name=model_name).splitlines()
  for member in reader_dump:
    # remove global_step since it is not necessary
    if 'global_step' not in member:
      saved_variables = str(member.split(" ")[0])
      saved_scope = saved_variables.rsplit('/', 1)[0] + "/"
      new_scope = saved_scope.replace(incoming_scope_name, current_scope_name, 1)
      # create key in init_map
      if saved_scope not in init_map.keys():  # pylint: disable=dict-keys-not-iterating
        init_map[saved_scope] = new_scope
  return init_map


def get_init_map(
        init_from_dir,
        exclude_var_names=None,
        exclude_name_scopes=None,
        name_scope_to_remove=None,
        name_scope_to_prepend=None):
  """
  Builds a map for initializing from a checkpoint (see tf.train.init_from_checkpoint).

  It assumes that the latter part of the variable names are consistent between the checkpoint and
  the new model, but their name_scopes may be different. If the checkpoint model has variable names
  of the form old/scope/var/foo, and the corresponding variable names for the new model should be
  my/new/scope/var/foo, then you should set name_scope_to_remove = 'old/' and
  name_scope_to_prepend = 'my/new/'.

  This function can be used to

  1. Generate an ``init_map`` map that can be passed to the ``Trainer`` init or
  2. Used to generate an ``init_map`` directly inside ``build_graph_fn``, in
     which case it should be passed directly to ``tf.train.init_from_checkpoint`` inside
     ``build_graph_fn``, in which case you do not also need to specify the ``init_map`` argument to
     the trainer.

  Parameters
  ----------
  init_from_dir: Directory containing checkpoint
  exclude_var_names: list[str]
    List of variables in the checkpoint that should be excluded from the map.
  exclude_name_scopes: list[str]
    List of name_scopes in the checkpoint model that should be excluded from the map.
  name_scope_to_remove: str
    portion of name_scope for checkpoint variables that should not be included in variable names
    for new model.
  name_scope_to_prepend: str
    name_scope to prepend to variable names in checkpoint to give variable names for new model.

  Returns
  -------
  dict
    keys are variable names in the checkpoint and values are variable names in the new model,
    into which the checkpoint parameters should be loaded.
  """
  vars_to_restore = get_checkpoint_variable_names(
    init_from_dir,
    exclude_var_names=exclude_var_names,
    exclude_scopes=exclude_name_scopes,
  )

  if name_scope_to_prepend is not None:
    if not name_scope_to_prepend.endswith('/'):
      name_scope_to_prepend += '/'

  if name_scope_to_remove is not None:
    if not name_scope_to_remove.endswith('/'):
      name_scope_to_remove += '/'

  init_map = {}

  for var_name in vars_to_restore:
    var_name_checkpoint = var_name

    if name_scope_to_remove is not None:
      var_name = var_name.replace(name_scope_to_remove, '')

    var_name_new_model = var_name

    if name_scope_to_prepend is not None:
      var_name_new_model = name_scope_to_prepend + var_name_new_model

    init_map[var_name_checkpoint] = var_name_new_model

  return init_map


def get_checkpoint_variable_names(model_dir, exclude_var_names=None, exclude_scopes=None):
  """
  Gets a list of variable names from the latest checkpoint in model_dir.
  Removes variables with scope defined by exclude_scopes, and/or with names defined by
  exclude_var_names.

  Args:
    model_dir (str): Directory containing checkpoint file for the pre-trained model
    exclude_var_names (list): Optional variable names to exclude (can include full/partial scope)
    exclude_scopes (list): Optional scopes to exclude

  Returns:
    list: variable names
  """
  checkpoint_path = tf.train.latest_checkpoint(model_dir)
  variables_and_shapes = tf.train.list_variables(checkpoint_path)

  def _keep(name):
    if exclude_scopes and any(name.startswith(exc_scope) for exc_scope in exclude_scopes):
      return False
    if exclude_var_names and any(name.endswith(exc_var) for exc_var in exclude_var_names):
      return False
    return True

  names = [x[0] for x in variables_and_shapes if _keep(x[0])]

  return names


def to_snake_case(name):
  """
  Changes name to snake case
  """
  intermediate = re.sub('(.)([A-Z][a-z0-9]+)', r'\1_\2', name)
  insecure = re.sub('([a-z])([A-Z])', r'\1_\2', intermediate).lower()
  # If the class is private the name starts with "_" which is not secure
  # for creating scopes. We prefix the name with "private" in this case.
  if insecure[0] != '_':
    return insecure
  return 'private' + insecure


def copy_phase_inputs(init_dir, dest_dir):
  """Automatically copies the .json.tf from the init_dir to save_dir
  so we can load multiple parameters at the same time.

  Args:
    init_dir:
      Name of the checkpoint directory.
    dest_dir:
      Name of the output directory.
  """
  if init_dir is not None:
    # we are using tf.io.gfile so we can use it with both local and hdfs paths
    for files in tf.io.gfile.listdir(init_dir):
      if files.endswith(".json.tf"):
        src_file = os.path.join(init_dir, files)
        dest_file = os.path.join(dest_dir, files)
        if not tf.io.gfile.exists(dest_dir):
          # creates the folder
          try:
            tf.io.gfile.makedirs(dest_dir)
          # to prevent racing condition
          except OSError:
            if not tf.io.gfile.isdir(dest_dir):
              raise
        # dest_file may be old if it exists and
        # dest_file gets copied several times in distributed training
        tf.io.gfile.copy(src_file, dest_file, overwrite=True)


def rehash_sparse_features_nbits(sp_a, nbits, hash_fn=multiplicative_hash):
  """
  Rehash the feature ids of the sparse tensor,
  and limit the output to n bits.

  This is useful for making the distribution of
  feature_ids more uniform, which may improve performance
  in some situations.

  This would typically be used on the output of
  PercentileDiscretizer, since it assigns many
  bins to low-valued output feature ids.

  Input feature IDs should take values less than 2**32,
  and nbits should be less than 32

  Args:
    sp_a:
      a tf.SparseTensor object
    nbits:
      integer number of bits to mask output feature_ids
    hash_fn:
      Function that takes integer values and returns hashes of these values.
      The output does not need to be masked to the desired number of bits,
      as this masking will be taken care of. Default value = multiplicative_hash.

  Returns:
    a new tf.SparseTensor
  """

  feature_ids = sp_a.indices[:, 1]
  feature_ids = hash_fn(feature_ids)

  sample_ids = sp_a.indices[:, 0]
  values = sp_a.values
  dense_shape = sp_a.dense_shape

  indices = tf.stack([sample_ids, feature_ids], axis=1)

  sp_a = tf.SparseTensor(indices, values, dense_shape)

  # note - we need 2**nbits >= batch size
  # otherwise, sample_ids will be squashed by the mask.
  return limit_sparse_tensor_size(sp_a, nbits)


def convert_to_hparams(opt):
  """
  Converts argparse.Namespace object to twitter.deepbird.hparam.hparam.HParams.
  Note that tensorflow.contrib.training.HParams is gone in TF 2.x, and we forward ported
  tensorflow.contrib.training.HParams to twitter.deepbird.hparam.hapram.HParams.

  NOTE: If you are using estimators, please don't call this method and directly pass python dict
  to TensorFlow estimator. Starting TensorFlow 2.0, Estimator will only accept dicts.
  """

  # Convert to dict so we can iterate through it cleanly.
  if isinstance(opt, argparse.Namespace):
    params_dict = vars(opt)
  elif isinstance(opt, dict):
    params_dict = opt
  elif isinstance(opt, HParams):
    logging.warning('If you are using Estimator, please pass python dict directly to Estimator.')
    params_dict = opt.values()
  else:
    raise ValueError("Input can not be of type %s. "
                     "It can be one of { argparse.Namespace, dict, "
                     "twitter.deepbird.hparam.HParams}."
                     % type(opt))

  params = HParams()
  # Hack to convert all parameters from hdfs:/// format to hdfs://default/
  # Note: .items() makes a copy in python 2.7, but that is fine since the performance isn't critical.
  for key, val in params_dict.items():
    val = params_dict[key]
    # Fix the path if the value is a string
    if isinstance(val, str):
      params.add_hparam(key, sanitize_hdfs_path(val))
    else:
      params.add_hparam(key, val)

  return params


def dynamic_partition(features, partitions, num_partitions=2, name=None):
  """
  Partitions each of the tensor in features using the provided mask.

  Args:
    features:
      A single tensor or an iterable of tensors (list, tuple, dict)
    partitions:
      A bool or integer tensor representing the partitions.

  Returns partitioned outputs as a list. Each element of the list is the same type as features.

  This uses tf.dynamic_partition but adds the following niceties:
    - features can be a list or dict of different tensor types.
    - only a partition tensor is used to partition all the feature tensors recursively.
    - the partition tensor is automatically converted into an integer tensor.
    - defaults to num_partitions == 2
  """

  if not isinstance(features, (dict, list, tuple, tf.Tensor)):
    raise AssertionError("features container must be a dict, list, or tuple, tf.Tensor")

  if isinstance(partitions, tf.Tensor):
    partitions = tf.cast(partitions, tf.int32)

  if isinstance(features, tf.Tensor):
    return tf.dynamic_partition(features, partitions, num_partitions, name)

  outputs = []
  for _ in range(num_partitions):
    if isinstance(features, (tuple, list)):
      # Create an empty list of lists first, will be converted to right type afterwards.
      outputs.append([None for _ in range(len(features))])
    else:
      outputs.append(dict())

  iterable = features.items() if isinstance(features, dict) else enumerate(features)

  # Handling partitions of nested classes handled here:
  # Recursively call dynamic_partition for containers
  for key, feature in iterable:
    name_key = None if name is None else name + "_" + str(key)
    if isinstance(partitions, tf.Tensor):
      results = tf.dynamic_partition(feature, partitions, num_partitions, name_key)
    else:
      results = tf.dynamic_partition(feature, partitions[key], num_partitions[key], name_key)
      # Append the result to the proper output container
    for idx, result in enumerate(results):
      outputs[idx][key] = result

  # if input is tuple, convert list of lists back to list of tuples
  if isinstance(features, tuple):
    outputs = [type(features)(output) for output in outputs]

  return outputs


def write_file(filename, contents, encode=False):
  '''
  Optionally encodes contents and writes contents to a file.

  Arguments:
    filename:
      path to file where the contents will be saved.
      Accepts HDFS and local paths.
    contents:
      contents to save to the file.
      Must be a string when encode is False.
    encode:
      False | 'json'. When encode='json', contents is encoded
      with json.dumps.
  '''
  if encode == 'json':
    contents = json.dumps(contents)
  elif not is_string(contents):
    raise ValueError("Expecting string for encode=False")

  graph = tf.Graph()
  with graph.as_default():
    write = tf.write_file(filename, contents)

  with tf.Session(graph=graph) as sess:
    sess.run(write)


def read_file(filename, decode=False):
  '''
  Reads contents from a file and optionally decodes it.

  Arguments:
    filename:
      path to file where the contents will be loaded from.
      Accepts HDFS and local paths.
    decode:
      False | 'json'. When decode='json', contents is decoded
      with json.loads. When False, contents is returned as is.

  Returns:
    contents
  '''
  graph = tf.Graph()
  with graph.as_default():
    read = tf.read_file(filename)

  with tf.Session(graph=graph) as sess:
    contents = (sess.run(read))
    # particular version of TF and/or Python may or may not perform decoding step from utf-8 to str
    if not isinstance(contents, str):
      contents = contents.decode()

  if decode == 'json':
    contents = json.loads(contents)

  return contents

def setup_tf_logging_formatter():
  formatter = _logging.Formatter(
      '%(asctime)s [%(levelname)s] %(name)s: %(message)s',
      None)
  # Setting up absl logging verbosity
  logging.set_verbosity('info')
  logging.set_stderrthreshold('info')
  logging.get_absl_handler().setFormatter(formatter)
  tf.logging.set_verbosity(tf.logging.INFO)
  # Set tensorflow logging handler format
  if len(tf_logging.get_logger().handlers) > 0:
    tf_logging.get_logger().handlers[0].setFormatter(formatter)


def set_tensorflow_log_level(log_level):
  """
  Sets tensorflow's default logging level.

  0. all logs are shown.
  1. filter out INFO logs.
  2. filter out WARNINGs and INFOs.
  3. filter out ERRORs, WARNINGs, and INFOs.

  Note that tf.Print output are INFO logs, so setting log_level above 0 would hide
  output from tf.Print.
  """
  assert isinstance(log_level, int) and log_level >= 0 and log_level <= 3
  os.environ['TF_CPP_MIN_LOG_LEVEL'] = str(log_level)


def weighted_average(values, weights):
  """
  Compute a weighted average using the given values and weights.
  E.g. this is usually used to compute a weighted loss given sample weights.
  """
  return tf.reduce_sum(tf.multiply(values, weights)) / tf.reduce_sum(weights)


def backup_checkpoint(checkpoint_path_prefix,
                      backup_path='backup',
                      empty_backup=True):
  """
  Creates a backup copy of a checkpoint in backup_dir.
  This function is used by the Trainer for early-stopping.

  Arguments:
    checkpoint_path_prefix:
      Prefix of the path to the checkpoint files.
    backup_path:
      path to a directory where checkpoint files will be backed up.
    empty_backup:
      When True (the default), the current contents of the backup directory
      are removed before the backup is performed.

  Returns:
    The number of backed up files.
  """
  checkpoint_file_prefix = os.path.basename(checkpoint_path_prefix)

  if tf.io.gfile.exists(backup_path) and empty_backup:
    tf.io.gfile.rmtree(backup_path)

  tf.io.gfile.mkdir(backup_path)

  n_backup = 0
  # copy all checkpoint files to backup directory (TODO use gfile.glob instead)
  try:
    checkpoint_files = tf.io.gfile.glob(checkpoint_path_prefix + "*")
    if len(checkpoint_files) == 0:
      raise twml.errors.CheckpointNotFoundError("%s not found" % checkpoint_path_prefix)
    for filename in checkpoint_files:
      n_backup += 1
      tf.io.gfile.copy(
        src=filename,
        dst=os.path.join(backup_path, os.path.basename(filename))
      )
  except tf.errors.OpError as ex:
    raise twml.errors.CheckpointNotFoundError(
      f"{str(ex)}\n {checkpoint_path_prefix} not found."
    )

  # tf.train.latest_checkpoint needs the 'checkpoint' file.
  with tf.io.gfile.GFile(os.path.join(backup_path, 'checkpoint'), 'w') as f:
    f.write('model_checkpoint_path: "%s"\n' % checkpoint_file_prefix)

  return n_backup


def set_only_checkpoint(source_path, dest_path, remove_source=True):
  """
  Removes the checkpoint and model.ckpt* files from dest_path.
  Moves the latest checkpoint from source_path to dest_path.

  Arguments:
    source_path:
      path to directory containing the latest checkpoint.
      Should contain a valid checkpoint file and model.ckpt files.
      For early-stopping, this should be the save_dir/best_checkpoint dir.
    dest_path:
      path to directory where the latest checkpoint files will be moved.
      All its checkpoint and model.ckpt* files will be removed.
      For early-stopping, this should be the save_dir.
    remove_source:
      When True (the default), deletes the source directory.
      Note that even when False, its checkpoint files are moved to
      dest_path anyway.
      This deletes the source directory (and any remaining contents).
  """
  # make it so that source_path checkpoint is the only checkpoint
  source_path_prefix = tf.train.latest_checkpoint(source_path)
  if source_path_prefix is not None:
    # remove intermediate checkpoints
    for filename in tf.io.gfile.listdir(dest_path):
      if filename.startswith("model.ckpt"):
        tf.io.gfile.Remove(os.path.join(dest_path, filename))
    # move contents of source_path to dest_path
    for filename in tf.io.gfile.listdir(source_path):
      tf.io.gfile.rename(
        oldname=os.path.join(source_path, filename),
        newname=os.path.join(dest_path, filename),
        overwrite=True)  # overwrite "checkpoint" file
    # delete the source_path dir
    if remove_source:
      tf.io.gfile.rmtree(source_path)


def list_files_by_datetime(
  base_path,
  start_datetime,
  end_datetime=None,
  datetime_prefix_format='%Y/%m/%d/%H',
  extension='lzo',
  parallelism=1,
  hour_resolution=1,
  sort=False
):
  """List files matching `base_path/dt_prefix_format/*.extension` for the requested datetime range.

  Args:
    base_path:
      The base path. If `None`, returns `None`.
    start_datetime:
      A `datetime.datetime` or string representing the start of the range (inclusive).
      If `None`, it returns `list_files(base_path, extension, sort)`.
    end_datetime:
      A `datetime.datetime` or string representing the end of the range (inclusive).
      If `None`, assumed to be the same as start_datetime.
    datetime_prefix_format:
      Format compatible with `datetime.datetime.strftime`
      (https://docs.python.org/2/library/datetime.html#strftime-and-strptime-behavior).
    extension:
      The extension of the files composing the dataset (e.g. 'lzo').
    parallelism:
      The number of threads used to process list patterns (this is mostly useful
      when dealing with filesystems such as HDFS in which listing files is a potentially expensive
      operation).
    hour_resolution:
      The separation between consecutive hours. The default value is 1.
    sort:
      bool, whether to return a sorted list of files. Default False.

  Returns:
    A list with all the matching files.

  Raises:
    errors.OpError: If there are filesystem / directory listing errors.
  """
  if hour_resolution is None:
    hour_resolution = 1

  if base_path is None:
    return None

  if start_datetime is None:
    return list_files(base_path, extension, sort)

  # Do this in case people want to use a single day for training.
  if end_datetime is None:
    end_datetime = start_datetime

  assert parallelism > 0
  assert start_datetime <= end_datetime

  if isinstance(start_datetime, str):
    start_datetime = datetime.strptime(start_datetime, datetime_prefix_format)

  if isinstance(end_datetime, str):
    end_datetime = datetime.strptime(end_datetime, datetime_prefix_format)

  assert isinstance(start_datetime, datetime)
  assert isinstance(end_datetime, datetime)

  base_path = preprocess_path(base_path)

  def _handle_missing_globs(pattern):
    try:
      return tf.io.gfile.glob(pattern)
    except tf.errors.NotFoundError as e:
      tf.logging.warning(e.message)
      return []

  # a set is used because there might be some repeated globs depending on dt_prefix_format
  globs = {
    os.path.join(base_path, dt.strftime(datetime_prefix_format), '*.%s' % extension)
    for dt in rrule.rrule(
      freq=rrule.HOURLY, interval=hour_resolution, dtstart=start_datetime, until=end_datetime)
  }
  nested_files = Parallel(n_jobs=parallelism, backend='threading')(
    delayed(_handle_missing_globs)(p) for p in globs
  )
  flattened_files = list(itertools.chain.from_iterable(nested_files))

  if not flattened_files:
    error_msg = "Files list is empty: base_path={base_path}, start_datetime={start_datetime}, end_datetime={end_datetime}".format(
      base_path=base_path, start_datetime=start_datetime, end_datetime=end_datetime
    )
    raise OSError(error_msg)

  if sort:
    flattened_files = sorted(flattened_files)

  return flattened_files


def limit_sparse_tensor_size(sparse_tf, input_size_bits, mask_indices=True):
  """
  Returns a ``tf.SparseTensor`` which is the input SparseTensor
  limited to the specified input_size_bits

  Args:
    sparse_tf:
      twml.SparseTensor or tf.SparseTensor
    input_size_bits:
      The number of bits allocated to the input size.
      Input size will be power(2,input_size_bits).
      Note that twml.limit_bits truncates any feature keys that
      exceed the input size.
    mask_indices:
      If mask indices is False; only the shape is changed. Defaults to True.
  """
  if isinstance(sparse_tf, twml.SparseTensor):
    sparse_tf = sparse_tf.to_tf()
  if not isinstance(sparse_tf, tf.SparseTensor):
    raise TypeError('Input argument `sparse_tf` should either be of type'
                    'twml.SparseTensor of tf.SparseTensor. Found type: {}'.
                    format(type(sparse_tf)))
  if mask_indices:
    indices = twml.limit_bits(sparse_tf.indices, input_size_bits)
  else:
    indices = sparse_tf.indices
  dense_shape = tf.stack([sparse_tf.dense_shape[0], 1 << input_size_bits])
  return tf.SparseTensor(indices=indices, values=sparse_tf.values,
                         dense_shape=dense_shape)


def create_module_spec(mlp_fn, mode, params, drop_collections=None):
  """
  Creates a standard tags_and_args which should be passed to the create_module_spec
  spec = hub.create_module_spec(mlp_fn, tags_and_args=tags_and_args).

  Args:
    module_fn:
      a function to build a graph for the Module.
    mode:
      mode in which the Estimator is run
    params:
      parameters passed to the Estimator
  """
  import tensorflow_hub as hub # noqa: F402
  tags_and_args = [(set(), {"params": params, "mode": mode}),  # serving graph
                   ({"train"}, {"params": params, "mode": mode})  # training graph
                   ]
  spec = hub.create_module_spec(mlp_fn, tags_and_args=tags_and_args, drop_collections=drop_collections)
  return spec


def change_name_scope_from_dir(init_scope_name, final_scope_name, save_dir):
  """
  Changes the name of the saved scope to the desired name and saves it
  to the same save_dir.

  Args:
    init_scope_name:
      initial scope name
    final_scope_name:
      desired (final) scope name
    save_dir:
      directory which the scopes are saved

  In the follwing section we:
    - Read all the variables from the latest checkpoint.
    - Make a copy of the variables with new name scope.
    - Store both sets of variables into the latest checkpoint.
  This essentially doubles up the size of the checkpoint.
  But when a job is restarted after this part is done, the checkpoint size doubles again.
  To avoid doing this, we create a copy in backup if a backup isn't found.
  This allows us always read (from backup) and write same sized checkpoint files.
  """

  # Create a backup_checkpoints dir
  backup_dir = os.path.join(save_dir, "change_name_scope_backups")
  tf.io.gfile.makedirs(backup_dir)

  latest_checkpoint = tf.train.latest_checkpoint(save_dir)

  if latest_checkpoint is None:
    raise OSError("No checkpoints found in save_dir: %s" % save_dir)

  latest_backup_checkpoint = tf.train.latest_checkpoint(backup_dir)

  if (latest_backup_checkpoint is None or
      (os.path.basename(latest_checkpoint) !=
       os.path.basename(latest_backup_checkpoint))):
    backup_checkpoint(latest_checkpoint, backup_dir, empty_backup=False)

  variables = tf.train.list_variables(backup_dir)
  with tf.Graph().as_default(), tf.Session().as_default() as sess:
    new_variables = []
    for name, _ in variables:
      var = tf.train.load_variable(backup_dir, name)
      # Append both the rename and the original variable
      new_variables.append(
        tf.Variable(var, name=name.replace(init_scope_name, final_scope_name)))
      new_variables.append(tf.Variable(var, name=name))
    # Save this to the checkpoint in the save_dir
    saver = tf.train.Saver(new_variables)
    sess.run(tf.global_variables_initializer())
    saver.save(sess, latest_checkpoint)  # pylint: disable=no-member


def hub_import(input, module, module_name, trainable=False):
  """
  Loads exported hub module.

  Args:
    input:
      input to hub module
    module:
      module path
    module_name:
      signature of the exported hub module
  """
  import tensorflow_hub as hub # noqa: F402
  hub_module = hub.Module(module, trainable=trainable)
  output = hub_module(input, signature=module_name)
  return output


def _extract_hash_space_bits(feature_config):
  """
  Extract Sparse Shapes for contrib.FeatureConfig.
  Arguments:
    feature_config:
      Feature Configuration of the type contrib.FeatureConfig
  Returns:
    Dictionary of tensor names and hash space bits.
  """
  if not isinstance(feature_config, twml.contrib.feature_config.FeatureConfig):
    fc_type = type(feature_config)
    raise TypeError(f"Feature config must be of type contrib.FeatureConfig: {fc_type}")
  sparse_shapes_dict = {}
  for config in feature_config.sparse_extraction_configs:
    sparse_shapes_dict[config.output_name] = config.hash_space_bits
  return sparse_shapes_dict


def fix_shape_sparse(features, feature_config):
  """
  Modifies the shape of features which are extracted using the hashing trick.
  Features itself is changed by this function.
  Arguments:
    features:
      Feature dictionary extracted by the feature config
    feature_config:
      Feature Configuration of the type contrib.FeatureConfig
  """
  if not isinstance(feature_config, twml.contrib.feature_config.FeatureConfig):
    raise TypeError(f"Feature config must be of type contrib.FeatureConfig, currently of {type(feature_config)}")
  sparse_shape = _extract_hash_space_bits(feature_config)
  if not isinstance(features, dict):
    raise TypeError(f"features must be of dictionary type, it is of {type(features)} type")
  for key in set(features) & set(sparse_shape):
    features[key] = limit_sparse_tensor_size(features[key], sparse_shape[key], mask_indices=False)


def touch_file_in_dir(directory, filename):
  """
  Creates a file named filename in directory.

  Arguments:
    filename: (str)
    directory: (str)
  """
  file_path = os.path.join(directory, filename)
  with tf.io.gfile.GFile(file_path, "w") as f:
    f.write("")


def file_exist_in_dir(directory: str, filename: str) -> bool:
  file_path = os.path.join(directory, filename)
  return tf.io.gfile.exists(file_path)


def copy_to_local(remote, local, filename, overwrite=False):
  """Function to file from remote directory to local directory."""
  assert "hdfs://" not in local
  tf.io.gfile.makedirs(local)
  return tf.io.gfile.copy(
    os.path.join(remote, filename),
    os.path.join(local, filename),
    overwrite=overwrite,
  )


def copy_recursive(src, dst, overwrite=False):
  """
  Function to copy a directory recursively.

  Arguments:
    src: Source directory.
    dst: Destination directory.
    overwrite: Specifies if files are to be overwritten if they exist.
  """

  src = src.rstrip("/")
  dst = dst.rstrip("/")

  for dirname, subdirs, files in tf.io.gfile.walk(src):
    dst_dirname = dirname.replace(src, dst)
    tf.io.gfile.makedirs(dst_dirname)

    for f in files:
      src_f = os.path.join(dirname, f)
      dst_f = os.path.join(dst_dirname, f)

      tf.logging.info(f"Copying {src_f} to {dst_f}")
      tf.io.gfile.copy(src_f, dst_f, overwrite=overwrite)


def delete_file_or_dir(path):
  """
  Delete the file or directory given by `path`
  Arguments:
    path:
      string indicating path of file or directory to remove
  """
  if tf.io.gfile.isdir(path):
    tf.io.gfile.rmtree(path)
  else:
    tf.io.gfile.remove(path)


def get_distributed_training_job_path():
  """
  Function to get distributed training job path.
  Note: distributed training has three jobs, one parameter server job,
  one worker job and one evaluator job. All of these three jobs' name
  share a common base job name.
  """
  job_path = AuroraPath(dc=os.environ.get("TWML_JOB_CLUSTER"),
    role=os.environ.get("TWML_JOB_ROLE"),
    env=os.environ.get("TWML_JOB_ENV"),
    job_name=os.environ.get("TWML_DISTRIBUTED_BASE_JOBNAME"))
  return job_path

def do_every_n_steps(action, num_steps):
  """
  Execute a sequence of TensorFlow operations only once in a while.
  Specifically, `action` is performed if `global_step` is a
    multiple of `num_steps`

  Args:
    action: callable to be performed at regular intervals. This callable
      must return a TF op with no output tensors.
    num_steps: period of performing the action, as measured
      in number of training steps

  Returns:
    A TensorFlow op with no output tensors, like a tf.print() or tf.no_op().
    You must use tf.control_dependencies() to execute the op.

  """
  global_step = tf.train.get_or_create_global_step()
  condition = tf.math.equal(tf.math.floormod(global_step, num_steps), 0)
  return tf.cond(condition, action, lambda: tf.no_op())
