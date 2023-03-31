from copy import deepcopy
import random
import types

from twitter.deepbird.util.thrift.simple_converters import (
  bytes_to_thrift_object, thrift_object_to_bytes)

from tensorflow.compat.v1 import logging
from com.twitter.ml.api.ttypes import DataRecord  # pylint: disable=import-error
import tensorflow.compat.v1 as tf
import twml


class PermutedInputFnFactory(object):

  def __init__(self, data_dir, record_count, file_list=None, datarecord_filter_fn=None):
    """
    Args:
      data_dir (str): The location of the records on hdfs
      record_count (int): The number of records to process
      file_list (list<str>, default=None): The list of data files on HDFS. If provided, use this instead
        of data_dir
      datarecord_filter_fn (function): a function takes a single data sample in com.twitter.ml.api.ttypes.DataRecord format
        and return a boolean value, to indicate if this data record should be kept in feature importance module or not.
    """
    if not (data_dir is None) ^ (file_list is None):
      raise ValueError("Exactly one of data_dir and file_list can be provided. Got {} for data_dir and {} for file_list".format(
        data_dir, file_list))

    file_list = file_list if file_list is not None else twml.util.list_files(twml.util.preprocess_path(data_dir))
    _next_batch = twml.input_fns.default_input_fn(file_list, 1, lambda x: x,
      num_threads=2, shuffle=True, shuffle_files=True)
    self.records = []
    # Validate datarecord_filter_fn
    if datarecord_filter_fn is not None and not isinstance(datarecord_filter_fn, types.FunctionType):
      raise TypeError("datarecord_filter_fn is not function type")
    with tf.Session() as sess:
      for i in range(record_count):
        try:
          record = bytes_to_thrift_object(sess.run(_next_batch)[0], DataRecord)
          if datarecord_filter_fn is None or datarecord_filter_fn(record):
            self.records.append(record)
        except tf.errors.OutOfRangeError:
          logging.info("Stopping after reading {} records out of {}".format(i, record_count))
          break
      if datarecord_filter_fn:
        logging.info("datarecord_filter_fn has been applied; keeping {} records out of {}".format(len(self.records), record_count))

  def _get_record_generator(self):
    return (thrift_object_to_bytes(r) for r in self.records)

  def get_permuted_input_fn(self, batch_size, parse_fn, fname_ftypes):
    """Get an input function that passes in a preset number of records that have been feature permuted
    Args:
      parse_fn (function): The function to parse inputs
      fname_ftypes: (list<(str, str)>): The names and types of the features to permute
    """
    def permuted_parse_pyfn(bytes_array):
      out = []
      for b in bytes_array:
        rec = bytes_to_thrift_object(b, DataRecord)
        if fname_ftypes:
          rec = _permutate_features(rec, fname_ftypes=fname_ftypes, records=self.records)
        out.append(thrift_object_to_bytes(rec))
      return [out]

    def permuted_parse_fn(bytes_tensor):
      parsed_bytes_tensor = parse_fn(tf.py_func(permuted_parse_pyfn, [bytes_tensor], tf.string))
      return parsed_bytes_tensor

    def input_fn(batch_size=batch_size, parse_fn=parse_fn, factory=self):
      return (tf.data.Dataset
          .from_generator(self._get_record_generator, tf.string)
          .batch(batch_size)
          .map(permuted_parse_fn, 4)
          .make_one_shot_iterator()
          .get_next())
    return input_fn


def _permutate_features(rec, fname_ftypes, records):
  """Replace a feature value with a value from random selected record
  Args:
    rec: (datarecord): A datarecord returned from DataRecordGenerator
    fname_ftypes: (list<(str, str)>): The names and types of the features to permute
    records: (list<datarecord>): The records to sample from
  Returns:
    The record with the feature permuted
  """
  rec_new = deepcopy(rec)
  rec_replace = random.choice(records)

  # If the replacement datarecord does not have the feature type entirely, add it in
  #   to make the logic a bit simpler
  for fname, feature_type in fname_ftypes:
    fid = twml.feature_id(fname)[0]
    if rec_replace.__dict__.get(feature_type, None) is None:
      rec_replace.__dict__[feature_type] = (
        dict() if feature_type != 'binaryFeatures' else set())
    if rec_new.__dict__.get(feature_type, None) is None:
      rec_new.__dict__[feature_type] = (
        dict() if feature_type != 'binaryFeatures' else set())

    if feature_type != 'binaryFeatures':
      if fid not in rec_replace.__dict__[feature_type] and fid in rec_new.__dict__.get(feature_type, dict()):
        # If the replacement datarecord does not contain the feature but the original does
        del rec_new.__dict__[feature_type][fid]
      elif fid in rec_replace.__dict__[feature_type]:
        # If the replacement datarecord does contain the feature
        if rec_new.__dict__[feature_type] is None:
          rec_new.__dict__[feature_type] = dict()
        rec_new.__dict__[feature_type][fid] = rec_replace.__dict__[feature_type][fid]
      else:
        # If neither datarecord contains this feature
        pass
    else:
      if fid not in rec_replace.__dict__[feature_type] and fid in rec_new.__dict__.get(feature_type, set()):
        # If the replacement datarecord does not contain the feature but the original does
        rec_new.__dict__[feature_type].remove(fid)
      elif fid in rec_replace.__dict__[feature_type]:
        # If the replacement datarecord does contain the feature
        if rec_new.__dict__[feature_type] is None:
          rec_new.__dict__[feature_type] = set()
        rec_new.__dict__[feature_type].add(fid)
        # If neither datarecord contains this feature
      else:
        # If neither datarecord contains this feature
        pass
  return rec_new
