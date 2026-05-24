# pylint: disable=arguments-differ, invalid-name
"""
This module contains the ``DataRecordTrainer``.
Unlike the parent ``Trainer`` class, the ``DataRecordTrainer``
is used specifically for processing data records.
It abstracts away a lot of the intricacies of working with DataRecords.
`DataRecord <http://go/datarecord>`_ is the main piping format for data samples.
The `DataRecordTrainer` assumes training data and production responses and requests
to be organized as the `Thrift prediction service API

A ``DataRecord`` is a Thrift struct that defines how to encode the data:

::

  struct DataRecord {
    1: optional set<i64> binaryFeatures;                     // stores BINARY features
    2: optional map<i64, double> continuousFeatures;         // stores CONTINUOUS features
    3: optional map<i64, i64> discreteFeatures;              // stores DISCRETE features
    4: optional map<i64, string> stringFeatures;             // stores STRING features
    5: optional map<i64, set<string>> sparseBinaryFeatures;  // stores sparse BINARY features
    6: optional map<i64, map<string, double>> sparseContinuousFeatures; // sparse CONTINUOUS feature
    7: optional map<i64, binary> blobFeatures; // stores features as BLOBs (binary large objects)
    8: optional map<i64, tensor.GeneralTensor> tensors; // stores TENSOR features
    9: optional map<i64, tensor.SparseTensor> sparseTensors; // stores SPARSE_TENSOR features
  }


A significant portion of Twitter data is hydrated
and then temporarily stored on HDFS as DataRecords.
The files are compressed (.gz or .lzo) partitions of data records.
These form supervised datasets. Each sample captures the relationship
between input and output (cause and effect).
To create your own dataset, please see https://github.com/twitter/elephant-bird.

The default ``DataRecordTrainer.[train,evaluate,learn]()`` reads these datarecords.
The data is a read from multiple ``part-*.[compression]`` files.
The default behavior of ``DataRecordTrainer`` is to read sparse features from ``DataRecords``.
This is a legacy default piping format at Twitter.
The ``DataRecordTrainer`` is flexible enough for research and yet simple enough
for a new beginner ML practioner.

By means of the feature string to key hashing function,
the ``[train,eval]_feature_config`` constructor arguments
control which features can be used as sample labels, sample weights,
or sample features.
Samples ids, and feature keys, feature values and feature weights
can be skipped, included, excluded or used as labels, weights, or features.
This allows you to easily define and control sparse distributions of
named features.

Yet sparse data is difficult to work with. We are currently working to
optimize the sparse operations due to inefficiencies in the gradient descent
and parameter update processes. There are efforts underway
to minimize the footprint of sparse data as it is inefficient to process.
CPUs and GPUs much prefer dense tensor data.
"""

import datetime

import tensorflow.compat.v1 as tf
from twitter.deepbird.io.dal import dal_to_hdfs_path, is_dal_path
import twml
from twml.trainers import Trainer
from twml.contrib.feature_importances.feature_importances import (
  compute_feature_importances,
  TREE,
  write_feature_importances_to_hdfs,
  write_feature_importances_to_ml_dash)
from absl import logging


class DataRecordTrainer(Trainer):  # pylint: disable=abstract-method
  """
  The ``DataRecordTrainer`` implementation is intended to satisfy the most common use cases
  at Twitter where only the build_graph methods needs to be overridden.
  For this reason, ``Trainer.[train,eval]_input_fn`` methods
  assume a DataRecord dataset partitioned into part files stored in compressed (e.g. gzip) format.

  For use-cases that differ from this common Twitter use-case,
  further Trainer methods can be overridden.
  If that still doesn't provide enough flexibility, the user can always
  use the tf.estimator.Esimator or tf.session.run directly.
  """

  def __init__(
          self, name, params,
          build_graph_fn,
          feature_config=None,
          **kwargs):
    """
    The DataRecordTrainer constructor builds a
    ``tf.estimator.Estimator`` and stores it in self.estimator.
    For this reason, DataRecordTrainer accepts the same Estimator constructor arguments.
    It also accepts additional arguments to facilitate metric evaluation and multi-phase training
    (init_from_dir, init_map).

    Args:
      parent arguments:
        See the `Trainer constructor <#twml.trainers.Trainer.__init__>`_ documentation
        for a full list of arguments accepted by the parent class.
      name, params, build_graph_fn (and other parent class args):
        see documentation for twml.Trainer doc.
      feature_config:
        An object of type FeatureConfig describing what features to decode.
        Defaults to None. But it is needed in the following cases:
          - `get_train_input_fn()` / `get_eval_input_fn()` is called without a `parse_fn`
          - `learn()`, `train()`, `eval()`, `calibrate()` are called without providing `*input_fn`.

      **kwargs:
        further kwargs can be specified and passed to the Estimator constructor.
    """

    # NOTE: DO NOT MODIFY `params` BEFORE THIS CALL.
    super(DataRecordTrainer, self).__init__(
      name=name, params=params, build_graph_fn=build_graph_fn, **kwargs)

    self._feature_config = feature_config

    # date range parameters common to both training and evaluation data:
    hour_resolution = self.params.get("hour_resolution", 1)
    data_threads = self.params.get("data_threads", 4)
    datetime_format = self.params.get("datetime_format", "%Y/%m/%d")

    # retrieve the desired training dataset files
    self._train_files = self.build_files_list(
      files_list_path=self.params.get("train_files_list", None),
      data_dir=self.params.get("train_data_dir", None),
      start_datetime=self.params.get("train_start_datetime", None),
      end_datetime=self.params.get("train_end_datetime", None),
      datetime_format=datetime_format, data_threads=data_threads,
      hour_resolution=hour_resolution, maybe_save=self.is_chief(),
      overwrite=self.params.get("train_overwrite_files_list", False),
    )

    # retrieve the desired evaluation dataset files
    eval_name = self.params.get("eval_name", None)

    if eval_name == "train":
      self._eval_files = self._train_files
    else:
      self._eval_files = self.build_files_list(
        files_list_path=self.params.get("eval_files_list", None),
        data_dir=self.params.get("eval_data_dir", None),
        start_datetime=self.params.get("eval_start_datetime", None),
        end_datetime=self.params.get("eval_end_datetime", None),
        datetime_format=datetime_format, data_threads=data_threads,
        hour_resolution=hour_resolution, maybe_save=self.is_chief(),
        overwrite=self.params.get("eval_overwrite_files_list", False),
      )

      if not self.params.get("allow_train_eval_overlap"):
        # if there is overlap between train and eval, error out!
        if self._train_files and self._eval_files:
          overlap_files = set(self._train_files) & set(self._eval_files)
        else:
          overlap_files = set()
        if overlap_files:
          raise ValueError("There is an overlap between train and eval files:\n %s" %
                           (overlap_files))

  @staticmethod
  def build_hdfs_files_list(
      files_list_path, data_dir,
      start_datetime, end_datetime, datetime_format,
      data_threads, hour_resolution, maybe_save, overwrite):
    if files_list_path:
      files_list_path = twml.util.preprocess_path(files_list_path)

    if isinstance(start_datetime, datetime.datetime):
      start_datetime = start_datetime.strftime(datetime_format)
    if isinstance(end_datetime, datetime.datetime):
      end_datetime = end_datetime.strftime(datetime_format)

    list_files_by_datetime_args = {
      "base_path": data_dir,
      "start_datetime": start_datetime,
      "end_datetime": end_datetime,
      "datetime_prefix_format": datetime_format,
      "extension": "lzo",
      "parallelism": data_threads,
      "hour_resolution": hour_resolution,
      "sort": True,
    }

    # no cache of data file paths, just get the list by scraping the directory
    if not files_list_path or not tf.io.gfile.exists(files_list_path):
      # twml.util.list_files_by_datetime returns None if data_dir is None.
      # twml.util.list_files_by_datetime passes through data_dir if data_dir is a list
      files_list = twml.util.list_files_by_datetime(**list_files_by_datetime_args)
    else:
      # the cached data file paths file exists.
      files_info = twml.util.read_file(files_list_path, decode="json")
      # use the cached list if data params match current params,
      #  or if current params are None
      # Not including None checks for datetime_format and hour_resolution,
      #  since those are shared between eval and training.
      if (all(param is None for param in [data_dir, start_datetime, end_datetime]) or
          (files_info["data_dir"] == data_dir and
           files_info["start_datetime"] == start_datetime and
           files_info["end_datetime"] == end_datetime and
           files_info["datetime_format"] == datetime_format and
           files_info["hour_resolution"] == hour_resolution)):
        files_list = files_info["files"]
      elif overwrite:
        # current params are not none and don't match saved params
        # `overwrite` indicates we should thus update the list
        files_list = twml.util.list_files_by_datetime(**list_files_by_datetime_args)
      else:
        # dont update the cached list
        raise ValueError("Information in files_list is inconsistent with provided args.\n"
                         "Did you intend to overwrite files_list using "
                         "--train.overwrite_files_list or --eval.overwrite_files_list?\n"
                         "If you instead want to use the paths in files_list, ensure that "
                         "data_dir, start_datetime, and end_datetime are None.")

    if maybe_save and files_list_path and (overwrite or not tf.io.gfile.exists(files_list_path)):
      save_dict = {}
      save_dict["files"] = files_list
      save_dict["data_dir"] = data_dir
      save_dict["start_datetime"] = start_datetime
      save_dict["end_datetime"] = end_datetime
      save_dict["datetime_format"] = datetime_format
      save_dict["hour_resolution"] = hour_resolution
      twml.util.write_file(files_list_path, save_dict, encode="json")

    return files_list

  @staticmethod
  def build_files_list(files_list_path, data_dir,
                        start_datetime, end_datetime, datetime_format,
                        data_threads, hour_resolution, maybe_save, overwrite):
    '''
    When specifying DAL datasets, only data_dir, start_dateime, and end_datetime
    should be given with the format:

    dal://{cluster}/{role}/{dataset_name}/{env}

    '''
    if not data_dir or not is_dal_path(data_dir):
      logging.warn(f"Please consider specifying a dal:// dataset rather than passing a physical hdfs path.")
      return DataRecordTrainer.build_hdfs_files_list(
        files_list_path, data_dir,
        start_datetime, end_datetime, datetime_format,
        data_threads, hour_resolution, maybe_save, overwrite)

    del datetime_format
    del data_threads
    del hour_resolution
    del maybe_save
    del overwrite

    return dal_to_hdfs_path(
      path=data_dir,
      start_datetime=start_datetime,
      end_datetime=end_datetime,
    )

  @property
  def train_files(self):
    return self._train_files

  @property
  def eval_files(self):
    return self._eval_files

  @staticmethod
  def add_parser_arguments():
    """
    Add common commandline args to parse for the Trainer class.
    Typically, the user calls this function and then parses cmd-line arguments
    into an argparse.Namespace object which is then passed to the Trainer constructor
    via the params argument.

    See the `Trainer code <_modules/twml/trainers/trainer.html#Trainer.add_parser_arguments>`_
    and `DataRecordTrainer code
    <_modules/twml/trainers/trainer.html#DataRecordTrainer.add_parser_arguments>`_
    for a list and description of all cmd-line arguments.

    Args:
      learning_rate_decay:
        Defaults to False. When True, parses learning rate decay arguments.

    Returns:
      argparse.ArgumentParser instance with some useful args already added.
    """
    parser = super(DataRecordTrainer, DataRecordTrainer).add_parser_arguments()
    parser.add_argument(
      "--train.files_list", "--train_files_list", type=str, default=None,
      dest="train_files_list",
      help="Path for a json file storing information on training data.\n"
           "Specifically, the file at files_list should contain the dataset parameters "
           "for constructing the list of data files, and the list of data file paths.\n"
           "If the json file does not exist, other args are used to construct the "
           "training files list, and that list will be saved to the indicated json file.\n"
           "If the json file does exist, and current args are consistent with "
           "saved args, or are all None, then the saved files list will be used.\n"
           "If current args are not consistent with the saved args, then error out "
           "if train_overwrite_files_list==False, else overwrite files_list with "
           "a newly constructed list.")
    parser.add_argument(
      "--train.overwrite_files_list", "--train_overwrite_files_list", action="store_true", default=False,
      dest="train_overwrite_files_list",
      help="When the --train.files_list param is used, indicates whether to "
           "overwrite the existing --train.files_list when there are differences "
           "between the current and saved dataset args. Default (False) is to "
           "error out if files_list exists and differs from current params.")
    parser.add_argument(
      "--train.data_dir", "--train_data_dir", type=str, default=None,
      dest="train_data_dir",
      help="Path to the training data directory."
           "Supports local, dal://{cluster}-{region}/{role}/{dataset_name}/{environment}, "
           "and HDFS (hdfs://default/<path> ) paths.")
    parser.add_argument(
      "--train.start_date", "--train_start_datetime",
      type=str, default=None,
      dest="train_start_datetime",
      help="Starting date for training inside the train data dir."
           "The start datetime is inclusive."
           "e.g. 2019/01/15")
    parser.add_argument(
      "--train.end_date", "--train_end_datetime", type=str, default=None,
      dest="train_end_datetime",
      help="Ending date for training inside the train data dir."
           "The end datetime is inclusive."
           "e.g. 2019/01/15")
    parser.add_argument(
      "--eval.files_list", "--eval_files_list", type=str, default=None,
      dest="eval_files_list",
      help="Path for a json file storing information on evaluation data.\n"
           "Specifically, the file at files_list should contain the dataset parameters "
           "for constructing the list of data files, and the list of data file paths.\n"
           "If the json file does not exist, other args are used to construct the "
           "evaluation files list, and that list will be saved to the indicated json file.\n"
           "If the json file does exist, and current args are consistent with "
           "saved args, or are all None, then the saved files list will be used.\n"
           "If current args are not consistent with the saved args, then error out "
           "if eval_overwrite_files_list==False, else overwrite files_list with "
           "a newly constructed list.")
    parser.add_argument(
      "--eval.overwrite_files_list", "--eval_overwrite_files_list", action="store_true", default=False,
      dest="eval_overwrite_files_list",
      help="When the --eval.files_list param is used, indicates whether to "
           "overwrite the existing --eval.files_list when there are differences "
           "between the current and saved dataset args. Default (False) is to "
           "error out if files_list exists and differs from current params.")
    parser.add_argument(
      "--eval.data_dir", "--eval_data_dir", type=str, default=None,
      dest="eval_data_dir",
      help="Path to the cross-validation data directory."
           "Supports local, dal://{cluster}-{region}/{role}/{dataset_name}/{environment}, "
           "and HDFS (hdfs://default/<path> ) paths.")
    parser.add_argument(
      "--eval.start_date", "--eval_start_datetime",
      type=str, default=None,
      dest="eval_start_datetime",
      help="Starting date for evaluating inside the eval data dir."
           "The start datetime is inclusive."
           "e.g. 2019/01/15")
    parser.add_argument(
      "--eval.end_date", "--eval_end_datetime", type=str, default=None,
      dest="eval_end_datetime",
      help="Ending date for evaluating inside the eval data dir."
           "The end datetime is inclusive."
           "e.g. 2019/01/15")
    parser.add_argument(
      "--datetime_format", type=str, default="%Y/%m/%d",
      help="Date format for training and evaluation datasets."
           "Has to be a format that is understood by python datetime."
           "e.g. %%Y/%%m/%%d for 2019/01/15."
           "Used only if {train/eval}.{start/end}_date are provided.")
    parser.add_argument(
      "--hour_resolution", type=int, default=None,
      help="Specify the hourly resolution of the stored data.")
    parser.add_argument(
      "--data_spec", type=str, required=True,
      help="Path to data specification JSON file. This file is used to decode DataRecords")
    parser.add_argument(
      "--train.keep_rate", "--train_keep_rate", type=float, default=None,
      dest="train_keep_rate",
      help="A float value in (0.0, 1.0] that indicates to drop records according to the Bernoulli \
      distribution with p = 1 - keep_rate.")
    parser.add_argument(
      "--eval.keep_rate", "--eval_keep_rate", type=float, default=None,
      dest="eval_keep_rate",
      help="A float value in (0.0, 1.0] that indicates to drop records according to the Bernoulli \
      distribution with p = 1 - keep_rate.")
    parser.add_argument(
      "--train.parts_downsampling_rate", "--train_parts_downsampling_rate",
      dest="train_parts_downsampling_rate",
      type=float, default=None,
      help="A float value in (0.0, 1.0] that indicates the factor by which to downsample part \
      files. For example, a value of 0.2 means only 20 percent of part files become part of the \
      dataset.")
    parser.add_argument(
      "--eval.parts_downsampling_rate", "--eval_parts_downsampling_rate",
      dest="eval_parts_downsampling_rate",
      type=float, default=None,
      help="A float value in (0.0, 1.0] that indicates the factor by which to downsample part \
      files. For example, a value of 0.2 means only 20 percent of part files become part of the \
      dataset.")
    parser.add_argument(
      "--allow_train_eval_overlap",
      dest="allow_train_eval_overlap",
      action="store_true",
      help="Allow overlap between train and eval datasets."
    )
    parser.add_argument(
      "--eval_name", type=str, default=None,
      help="String denoting what we want to name the eval. If this is `train`, then we eval on \
      the training dataset."
    )
    return parser

  def contrib_run_feature_importances(self, feature_importances_parse_fn=None, write_to_hdfs=True, extra_groups=None, datarecord_filter_fn=None, datarecord_filter_run_name=None):
    """Compute feature importances on a trained model (this is a contrib feature)
    Args:
      feature_importances_parse_fn (fn): The same parse_fn that we use for training/evaluation.
        Defaults to feature_config.get_parse_fn()
      write_to_hdfs (bool): Setting this to True writes the feature importance metrics to HDFS
    extra_groups (dict<str, list<str>>): A dictionary mapping the name of extra feature groups to the list of
      the names of the features in the group
    datarecord_filter_fn (function): a function takes a single data sample in com.twitter.ml.api.ttypes.DataRecord format
        and return a boolean value, to indicate if this data record should be kept in feature importance module or not.
    """
    logging.info("Computing feature importance")
    algorithm = self._params.feature_importance_algorithm

    kwargs = {}
    if algorithm == TREE:
      kwargs["split_feature_group_on_period"] = self._params.split_feature_group_on_period
      kwargs["stopping_metric"] = self._params.feature_importance_metric
      kwargs["sensitivity"] = self._params.feature_importance_sensitivity
      kwargs["dont_build_tree"] = self._params.dont_build_tree
      kwargs["extra_groups"] = extra_groups
      if self._params.feature_importance_is_metric_larger_the_better:
        # The user has specified that the stopping metric is one where larger values are better (e.g. ROC_AUC)
        kwargs["is_metric_larger_the_better"] = True
      elif self._params.feature_importance_is_metric_smaller_the_better:
        # The user has specified that the stopping metric is one where smaller values are better (e.g. LOSS)
        kwargs["is_metric_larger_the_better"] = False
      else:
        # The user has not specified which direction is better for the stopping metric
        kwargs["is_metric_larger_the_better"] = None
      logging.info("Using the tree algorithm with kwargs {}".format(kwargs))

    feature_importances = compute_feature_importances(
      trainer=self,
      data_dir=self._params.get('feature_importance_data_dir'),
      feature_config=self._feature_config,
      algorithm=algorithm,
      record_count=self._params.feature_importance_example_count,
      parse_fn=feature_importances_parse_fn,
      datarecord_filter_fn=datarecord_filter_fn,
      **kwargs)

    if not feature_importances:
      logging.info("Feature importances returned None")
    else:
      if write_to_hdfs:
        logging.info("Writing feature importance to HDFS")
        write_feature_importances_to_hdfs(
          trainer=self,
          feature_importances=feature_importances,
          output_path=datarecord_filter_run_name,
          metric=self._params.get('feature_importance_metric'))
      else:
        logging.info("Not writing feature importance to HDFS")

      logging.info("Writing feature importance to ML Metastore")
      write_feature_importances_to_ml_dash(
        trainer=self, feature_importances=feature_importances)
    return feature_importances

  def export_model(self, serving_input_receiver_fn=None,
                   export_output_fn=None,
                   export_dir=None, checkpoint_path=None,
                   feature_spec=None):
    """
    Export the model for prediction. Typically, the exported model
    will later be run in production servers. This method is called
    by the user to export the PREDICT graph to disk.

    Internally, this method calls `tf.estimator.Estimator.export_savedmodel
    <https://www.tensorflow.org/api_docs/python/tf/estimator/Estimator#export_savedmodel>`_.

    Args:
      serving_input_receiver_fn (Function):
        function preparing the model for inference requests.
        If not set; defaults to the the serving input receiver fn set by the FeatureConfig.
      export_output_fn (Function):
        Function to export the graph_output (output of build_graph) for
        prediction. Takes a graph_output dict as sole argument and returns
        the export_output_fns dict.
        Defaults to ``twml.export_output_fns.batch_prediction_continuous_output_fn``.
      export_dir:
        directory to export a SavedModel for prediction servers.
        Defaults to ``[save_dir]/exported_models``.
      checkpoint_path:
        the checkpoint path to export. If None (the default), the most recent checkpoint
        found within the model directory ``save_dir`` is chosen.

    Returns:
      The export directory where the PREDICT graph is saved.
    """
    if serving_input_receiver_fn is None:
      if self._feature_config is None:
        raise ValueError("`feature_config` was not passed to `DataRecordTrainer`")
      serving_input_receiver_fn = self._feature_config.get_serving_input_receiver_fn()

    if feature_spec is None:
      if self._feature_config is None:
        raise ValueError("feature_spec can not be inferred."
                         "Please pass feature_spec=feature_config.get_feature_spec() to the trainer.export_model method")
      else:
        feature_spec = self._feature_config.get_feature_spec()

    if isinstance(serving_input_receiver_fn, twml.feature_config.FeatureConfig):
      raise ValueError("Cannot pass FeatureConfig as a parameter to serving_input_receiver_fn")
    elif not callable(serving_input_receiver_fn):
      raise ValueError("Expecting Function for serving_input_receiver_fn")

    if export_output_fn is None:
      export_output_fn = twml.export_output_fns.batch_prediction_continuous_output_fn

    return super(DataRecordTrainer, self).export_model(
      export_dir=export_dir,
      serving_input_receiver_fn=serving_input_receiver_fn,
      checkpoint_path=checkpoint_path,
      export_output_fn=export_output_fn,
      feature_spec=feature_spec,
    )

  def get_train_input_fn(
      self, parse_fn=None, repeat=None, shuffle=True, interleave=True, shuffle_files=None,
      initializable=False, log_tf_data_summaries=False, **kwargs):
    """
    This method is used to create input function used by estimator.train().

    Args:
      parse_fn:
        Function to parse a data record into a set of features.
        Defaults to the parser returned by the FeatureConfig selected
      repeat (optional):
        Specifies if the dataset is to be repeated. Defaults to `params.train_steps > 0`.
        This ensures the training is run for atleast `params.train_steps`.
        Toggling this to `False` results in training finishing when one of the following happens:
          - The entire dataset has been trained upon once.
          - `params.train_steps` has been reached.
      shuffle (optional):
        Specifies if the files and records in the files need to be shuffled.
        When `True`,  files are shuffled, and records of each files are shuffled.
        When `False`, files are read in alpha-numerical order. Also when `False`
        the dataset is sharded among workers for Hogwild and distributed training
        if no sharding configuration is provided in `params.train_dataset_shards`.
        Defaults to `True`.
      interleave (optional):
        Specifies if records from multiple files need to be interleaved in parallel.
        Defaults to `True`.
      shuffle_files (optional):
        Shuffle the list of files. Defaults to 'Shuffle' if not provided.
      initializable (optional):
        A boolean indicator. When the parsing function depends on some resource, e.g. a HashTable or
        a Tensor, i.e. it's an initializable iterator, set it to True. Otherwise, default value
        (false) is used for most plain iterators.
      log_tf_data_summaries (optional):
        A boolean indicator denoting whether to add a `tf.data.experimental.StatsAggregator` to the
        tf.data pipeline. This adds summaries of pipeline utilization and buffer sizes to the output
        events files. This requires that `initializable` is `True` above.

    Returns:
      An input_fn that can be consumed by `estimator.train()`.
    """
    if parse_fn is None:
      if self._feature_config is None:
        raise ValueError("`feature_config` was not passed to `DataRecordTrainer`")
      parse_fn = self._feature_config.get_parse_fn()

    if not callable(parse_fn):
      raise ValueError("Expecting parse_fn to be a function.")

    if log_tf_data_summaries and not initializable:
      raise ValueError("Require `initializable` if `log_tf_data_summaries`.")

    if repeat is None:
      repeat = self.params.train_steps > 0 or self.params.get('distributed', False)

    if not shuffle and self.num_workers > 1 and self.params.train_dataset_shards is None:
      num_shards = self.num_workers
      shard_index = self.worker_index
    else:
      num_shards = self.params.train_dataset_shards
      shard_index = self.params.train_dataset_shard_index

    return lambda: twml.input_fns.default_input_fn(
      files=self._train_files,
      batch_size=self.params.train_batch_size,
      parse_fn=parse_fn,
      num_threads=self.params.num_threads,
      repeat=repeat,
      keep_rate=self.params.train_keep_rate,
      parts_downsampling_rate=self.params.train_parts_downsampling_rate,
      shards=num_shards,
      shard_index=shard_index,
      shuffle=shuffle,
      shuffle_files=(shuffle if shuffle_files is None else shuffle_files),
      interleave=interleave,
      initializable=initializable,
      log_tf_data_summaries=log_tf_data_summaries,
      **kwargs)

  def get_eval_input_fn(
      self, parse_fn=None, repeat=None,
      shuffle=True, interleave=True,
      shuffle_files=None, initializable=False, log_tf_data_summaries=False, **kwargs):
    """
    This method is used to create input function used by estimator.eval().

    Args:
      parse_fn:
        Function to parse a data record into a set of features.
        Defaults to twml.parsers.get_sparse_parse_fn(feature_config).
      repeat (optional):
        Specifies if the dataset is to be repeated. Defaults to `params.eval_steps > 0`.
        This ensures the evaluation is run for atleast `params.eval_steps`.
        Toggling this to `False` results in evaluation finishing when one of the following happens:
          - The entire dataset has been evaled upon once.
          - `params.eval_steps` has been reached.
      shuffle (optional):
        Specifies if the files and records in the files need to be shuffled.
        When `False`, files are read in alpha-numerical order.
        When `True`,  files are shuffled, and records of each files are shuffled.
        Defaults to `True`.
      interleave (optional):
        Specifies if records from multiple files need to be interleaved in parallel.
        Defaults to `True`.
      shuffle_files (optional):
        Shuffles the list of files. Defaults to 'Shuffle' if not provided.
      initializable (optional):
        A boolean indicator. When the parsing function depends on some resource, e.g. a HashTable or
        a Tensor, i.e. it's an initializable iterator, set it to True. Otherwise, default value
        (false) is used for most plain iterators.
      log_tf_data_summaries (optional):
        A boolean indicator denoting whether to add a `tf.data.experimental.StatsAggregator` to the
        tf.data pipeline. This adds summaries of pipeline utilization and buffer sizes to the output
        events files. This requires that `initializable` is `True` above.

    Returns:
      An input_fn that can be consumed by `estimator.eval()`.
    """
    if parse_fn is None:
      if self._feature_config is None:
        raise ValueError("`feature_config` was not passed to `DataRecordTrainer`")
      parse_fn = self._feature_config.get_parse_fn()

    if not self._eval_files:
      raise ValueError("`eval_files` was not present in `params` passed to `DataRecordTrainer`")

    if not callable(parse_fn):
      raise ValueError("Expecting parse_fn to be a function.")

    if log_tf_data_summaries and not initializable:
      raise ValueError("Require `initializable` if `log_tf_data_summaries`.")

    if repeat is None:
      repeat = self.params.eval_steps > 0

    return lambda: twml.input_fns.default_input_fn(
      files=self._eval_files,
      batch_size=self.params.eval_batch_size,
      parse_fn=parse_fn,
      num_threads=self.params.num_threads,
      repeat=repeat,
      keep_rate=self.params.eval_keep_rate,
      parts_downsampling_rate=self.params.eval_parts_downsampling_rate,
      shuffle=shuffle,
      shuffle_files=(shuffle if shuffle_files is None else shuffle_files),
      interleave=interleave,
      initializable=initializable,
      log_tf_data_summaries=log_tf_data_summaries,
      **kwargs
    )

  def _assert_train_files(self):
    if not self._train_files:
      raise ValueError("train.data_dir was not set in params passed to DataRecordTrainer.")

  def _assert_eval_files(self):
    if not self._eval_files:
      raise ValueError("eval.data_dir was not set in params passed to DataRecordTrainer.")

  def train(self, input_fn=None, steps=None, hooks=None):
    """
    Makes input functions optional. input_fn defaults to self.get_train_input_fn().
    See Trainer for more detailed documentation documentation.
    """
    if input_fn is None:
      self._assert_train_files()
    input_fn = input_fn if input_fn else self.get_train_input_fn()
    super(DataRecordTrainer, self).train(input_fn=input_fn, steps=steps, hooks=hooks)

  def evaluate(self, input_fn=None, steps=None, hooks=None, name=None):
    """
    Makes input functions optional. input_fn defaults to self.get_eval_input_fn().
    See Trainer for more detailed documentation.
    """
    if input_fn is None:
      self._assert_eval_files()
    input_fn = input_fn if input_fn else self.get_eval_input_fn(repeat=False)
    return super(DataRecordTrainer, self).evaluate(
      input_fn=input_fn,
      steps=steps,
      hooks=hooks,
      name=name
    )

  def learn(self, train_input_fn=None, eval_input_fn=None, **kwargs):
    """
    Overrides ``Trainer.learn`` to make ``input_fn`` functions optional.
    Respectively, ``train_input_fn`` and ``eval_input_fn`` default to
    ``self.train_input_fn`` and ``self.eval_input_fn``.
    See ``Trainer.learn`` for more detailed documentation.
    """
    if train_input_fn is None:
      self._assert_train_files()
    if eval_input_fn is None:
      self._assert_eval_files()
    train_input_fn = train_input_fn if train_input_fn else self.get_train_input_fn()
    eval_input_fn = eval_input_fn if eval_input_fn else self.get_eval_input_fn()

    super(DataRecordTrainer, self).learn(
      train_input_fn=train_input_fn,
      eval_input_fn=eval_input_fn,
      **kwargs
    )

  def train_and_evaluate(self,
                         train_input_fn=None, eval_input_fn=None,
                          **kwargs):
    """
    Overrides ``Trainer.train_and_evaluate`` to make ``input_fn`` functions optional.
    Respectively, ``train_input_fn`` and ``eval_input_fn`` default to
    ``self.train_input_fn`` and ``self.eval_input_fn``.
    See ``Trainer.train_and_evaluate`` for detailed documentation.
    """
    if train_input_fn is None:
      self._assert_train_files()
    if eval_input_fn is None:
      self._assert_eval_files()
    train_input_fn = train_input_fn if train_input_fn else self.get_train_input_fn()
    eval_input_fn = eval_input_fn if eval_input_fn else self.get_eval_input_fn()

    super(DataRecordTrainer, self).train_and_evaluate(
      train_input_fn=train_input_fn,
      eval_input_fn=eval_input_fn,
      **kwargs
    )

  def _model_fn(self, features, labels, mode, params, config=None):
    """
    Overrides the _model_fn to correct for the features shape of the sparse features
    extracted with the contrib.FeatureConfig
    """
    if isinstance(self._feature_config, twml.contrib.feature_config.FeatureConfig):
      # Fix the shape of the features. The features dictionary will be modified to
      # contain the shape changes.
      twml.util.fix_shape_sparse(features, self._feature_config)
    return super(DataRecordTrainer, self)._model_fn(
      features=features,
      labels=labels,
      mode=mode,
      params=params,
      config=config
    )

  def calibrate(self,
                calibrator,
                input_fn=None,
                steps=None,
                save_calibrator=True,
                hooks=None):
    """
    Makes input functions optional. input_fn defaults to self.train_input_fn.
    See Trainer for more detailed documentation.
    """
    if input_fn is None:
      self._assert_train_files()
    input_fn = input_fn if input_fn else self.get_train_input_fn()
    super(DataRecordTrainer, self).calibrate(calibrator=calibrator,
                                             input_fn=input_fn,
                                             steps=steps,
                                             save_calibrator=save_calibrator,
                                             hooks=hooks)

  def save_checkpoints_and_export_model(self,
                                        serving_input_receiver_fn,
                                        export_output_fn=None,
                                        export_dir=None,
                                        checkpoint_path=None,
                                        input_fn=None):
    """
    Exports saved module after saving checkpoint to save_dir.
    Please note that to use this method, you need to assign a loss to the output
    of the build_graph (for the train mode).
    See export_model for more detailed information.
    """
    self.train(input_fn=input_fn, steps=1)
    self.export_model(serving_input_receiver_fn, export_output_fn, export_dir, checkpoint_path)

  def save_checkpoints_and_evaluate(self,
                                    input_fn=None,
                                    steps=None,
                                    hooks=None,
                                    name=None):
    """
    Evaluates model after saving checkpoint to save_dir.
    Please note that to use this method, you need to assign a loss to the output
    of the build_graph (for the train mode).
    See evaluate for more detailed information.
    """
    self.train(input_fn=input_fn, steps=1)
    self.evaluate(input_fn, steps, hooks, name)
