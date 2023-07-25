from collections import OrderedDict
import json
import os
from os.path import join

from twitter.magicpony.common import file_access
import twml

from .model_utils import read_config

import numpy as np
from scipy import stats
import tensorflow.compat.v1 as tf


# checkstyle: noqa


def get_model_type_to_tensors_to_change_axis():
  model_type_to_tensors_to_change_axis = {
    "magic_recs/model/batch_normalization/beta": ([0], "continuous"),
    "magic_recs/model/batch_normalization/gamma": ([0], "continuous"),
    "magic_recs/model/batch_normalization/moving_mean": ([0], "continuous"),
    "magic_recs/model/batch_normalization/moving_stddev": ([0], "continuous"),
    "magic_recs/model/batch_normalization/moving_variance": ([0], "continuous"),
    "magic_recs/model/batch_normalization/renorm_mean": ([0], "continuous"),
    "magic_recs/model/batch_normalization/renorm_stddev": ([0], "continuous"),
    "magic_recs/model/logits/EngagementGivenOONC_logits/clem_net_1/block2_4/channel_wise_dense_4/kernel": (
      [1],
      "all",
    ),
    "magic_recs/model/logits/OONC_logits/clem_net/block2/channel_wise_dense/kernel": ([1], "all"),
  }

  return model_type_to_tensors_to_change_axis


def mkdirp(dirname):
  if not tf.io.gfile.exists(dirname):
    tf.io.gfile.makedirs(dirname)


def rename_dir(dirname, dst):
  file_access.hdfs.mv(dirname, dst)


def rmdir(dirname):
  if tf.io.gfile.exists(dirname):
    if tf.io.gfile.isdir(dirname):
      tf.io.gfile.rmtree(dirname)
    else:
      tf.io.gfile.remove(dirname)


def get_var_dict(checkpoint_path):
  checkpoint = tf.train.get_checkpoint_state(checkpoint_path)
  var_dict = OrderedDict()
  with tf.Session() as sess:
    all_var_list = tf.train.list_variables(checkpoint_path)
    for var_name, _ in all_var_list:
      # Load the variable
      var = tf.train.load_variable(checkpoint_path, var_name)
      var_dict[var_name] = var
  return var_dict


def get_continunous_mapping_from_feat_list(old_feature_list, new_feature_list):
  """
  get var_ind for old_feature and corresponding var_ind for new_feature
  """
  new_var_ind, old_var_ind = [], []
  for this_new_id, this_new_name in enumerate(new_feature_list):
    if this_new_name in old_feature_list:
      this_old_id = old_feature_list.index(this_new_name)
      new_var_ind.append(this_new_id)
      old_var_ind.append(this_old_id)
  return np.asarray(old_var_ind), np.asarray(new_var_ind)


def get_continuous_mapping_from_feat_dict(old_feature_dict, new_feature_dict):
  """
  get var_ind for old_feature and corresponding var_ind for new_feature
  """
  old_cont = old_feature_dict["continuous"]
  old_bin = old_feature_dict["binary"]

  new_cont = new_feature_dict["continuous"]
  new_bin = new_feature_dict["binary"]

  _dummy_sparse_feat = [f"sparse_feature_{_idx}" for _idx in range(100)]

  cont_old_var_ind, cont_new_var_ind = get_continunous_mapping_from_feat_list(old_cont, new_cont)

  all_old_var_ind, all_new_var_ind = get_continunous_mapping_from_feat_list(
    old_cont + old_bin + _dummy_sparse_feat, new_cont + new_bin + _dummy_sparse_feat
  )

  _res = {
    "continuous": (cont_old_var_ind, cont_new_var_ind),
    "all": (all_old_var_ind, all_new_var_ind),
  }

  return _res


def warm_start_from_var_dict(
  old_ckpt_path,
  var_ind_dict,
  output_dir,
  new_len_var,
  var_to_change_dict_fn=get_model_type_to_tensors_to_change_axis,
):
  """
  Parameters:
      old_ckpt_path (str): path to the old checkpoint path
      new_var_ind (array of int): index to overlapping features in new var between old and new feature list.
      old_var_ind (array of int): index to overlapping features in old var between old and new feature list.

      output_dir (str): dir that used to write modified checkpoint
      new_len_var ({str:int}): number of feature in the new feature list.
      var_to_change_dict_fn (dict): A function to get the dictionary of format {var_name: dim_to_change}
  """
  old_var_dict = get_var_dict(old_ckpt_path)

  ckpt_file_name = os.path.basename(old_ckpt_path)
  mkdirp(output_dir)
  output_path = join(output_dir, ckpt_file_name)

  tensors_to_change = var_to_change_dict_fn()
  tf.compat.v1.reset_default_graph()

  with tf.Session() as sess:
    var_name_shape_list = tf.train.list_variables(old_ckpt_path)
    count = 0

    for var_name, var_shape in var_name_shape_list:
      old_var = old_var_dict[var_name]
      if var_name in tensors_to_change.keys():
        _info_tuple = tensors_to_change[var_name]
        dims_to_remove_from, var_type = _info_tuple

        new_var_ind, old_var_ind = var_ind_dict[var_type]

        this_shape = list(old_var.shape)
        for this_dim in dims_to_remove_from:
          this_shape[this_dim] = new_len_var[var_type]

        stddev = np.std(old_var)
        truncated_norm_generator = stats.truncnorm(-0.5, 0.5, loc=0, scale=stddev)
        size = np.prod(this_shape)
        new_var = truncated_norm_generator.rvs(size).reshape(this_shape)
        new_var = new_var.astype(old_var.dtype)

        new_var = copy_feat_based_on_mapping(
          new_var, old_var, dims_to_remove_from, new_var_ind, old_var_ind
        )
        count = count + 1
      else:
        new_var = old_var
      var = tf.Variable(new_var, name=var_name)
    assert count == len(tensors_to_change.keys()), "not all variables are exchanged.\n"
    saver = tf.train.Saver()
    sess.run(tf.global_variables_initializer())
    saver.save(sess, output_path)
  return output_path


def copy_feat_based_on_mapping(new_array, old_array, dims_to_remove_from, new_var_ind, old_var_ind):
  if dims_to_remove_from == [0, 1]:
    for this_new_ind, this_old_ind in zip(new_var_ind, old_var_ind):
      new_array[this_new_ind, new_var_ind] = old_array[this_old_ind, old_var_ind]
  elif dims_to_remove_from == [0]:
    new_array[new_var_ind] = old_array[old_var_ind]
  elif dims_to_remove_from == [1]:
    new_array[:, new_var_ind] = old_array[:, old_var_ind]
  else:
    raise RuntimeError(f"undefined dims_to_remove_from pattern: ({dims_to_remove_from})")
  return new_array


def read_file(filename, decode=False):
  """
  Reads contents from a file and optionally decodes it.

  Arguments:
    filename:
      path to file where the contents will be loaded from.
      Accepts HDFS and local paths.
    decode:
      False or 'json'. When decode='json', contents is decoded
      with json.loads. When False, contents is returned as is.
  """
  graph = tf.Graph()
  with graph.as_default():
    read = tf.read_file(filename)

  with tf.Session(graph=graph) as sess:
    contents = sess.run(read)
    if not isinstance(contents, str):
      contents = contents.decode()

  if decode == "json":
    contents = json.loads(contents)

  return contents


def read_feat_list_from_disk(file_path):
  return read_file(file_path, decode="json")


def get_feature_list_for_light_ranking(feature_list_path, data_spec_path):
  feature_list = read_config(feature_list_path).items()
  string_feat_list = [f[0] for f in feature_list if f[1] != "S"]

  feature_config_builder = twml.contrib.feature_config.FeatureConfigBuilder(
    data_spec_path=data_spec_path
  )
  feature_config_builder = feature_config_builder.extract_feature_group(
    feature_regexes=string_feat_list,
    group_name="continuous",
    default_value=-1,
    type_filter=["CONTINUOUS"],
  )
  feature_config = feature_config_builder.build()
  feature_list = feature_config_builder._feature_group_extraction_configs[0].feature_map[
    "CONTINUOUS"
  ]
  return feature_list


def get_feature_list_for_heavy_ranking(feature_list_path, data_spec_path):
  feature_list = read_config(feature_list_path).items()
  string_feat_list = [f[0] for f in feature_list if f[1] != "S"]

  feature_config_builder = twml.contrib.feature_config.FeatureConfigBuilder(
    data_spec_path=data_spec_path
  )
  feature_config_builder = feature_config_builder.extract_feature_group(
    feature_regexes=string_feat_list,
    group_name="continuous",
    default_value=-1,
    type_filter=["CONTINUOUS"],
  )

  feature_config_builder = feature_config_builder.extract_feature_group(
    feature_regexes=string_feat_list,
    group_name="binary",
    default_value=False,
    type_filter=["BINARY"],
  )

  feature_config_builder = feature_config_builder.build()

  continuous_feature_list = feature_config_builder._feature_group_extraction_configs[0].feature_map[
    "CONTINUOUS"
  ]

  binary_feature_list = feature_config_builder._feature_group_extraction_configs[1].feature_map[
    "BINARY"
  ]
  return {"continuous": continuous_feature_list, "binary": binary_feature_list}


def warm_start_checkpoint(
  old_best_ckpt_folder,
  old_feature_list_path,
  feature_allow_list_path,
  data_spec_path,
  output_ckpt_folder,
  *args,
):
  """
  Reads old checkpoint and the old feature list, and create a new ckpt warm started from old ckpt using new features .

  Arguments:
    old_best_ckpt_folder:
      path to the best_checkpoint_folder for old model
    old_feature_list_path:
      path to the json file that stores the list of continuous features used in old models.
    feature_allow_list_path:
      yaml file that contain the feature allow list.
    data_spec_path:
      path to the data_spec file
    output_ckpt_folder:
      folder that contains the modified ckpt.

  Returns:
    path to the modified ckpt."""
  old_ckpt_path = tf.train.latest_checkpoint(old_best_ckpt_folder, latest_filename=None)

  new_feature_dict = get_feature_list(feature_allow_list_path, data_spec_path)
  old_feature_dict = read_feat_list_from_disk(old_feature_list_path)

  var_ind_dict = get_continuous_mapping_from_feat_dict(new_feature_dict, old_feature_dict)

  new_len_var = {
    "continuous": len(new_feature_dict["continuous"]),
    "all": len(new_feature_dict["continuous"] + new_feature_dict["binary"]) + 100,
  }

  warm_started_ckpt_path = warm_start_from_var_dict(
    old_ckpt_path,
    var_ind_dict,
    output_dir=output_ckpt_folder,
    new_len_var=new_len_var,
  )

  return warm_started_ckpt_path
