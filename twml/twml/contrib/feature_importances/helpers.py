import uuid

from tensorflow.compat.v1 import logging
import twml
import tensorflow.compat.v1 as tf


def write_list_to_hdfs_gfile(list_to_write, output_path):
  """Use tensorflow gfile to write a list to a location on hdfs"""
  locname = "/tmp/{}".format(str(uuid.uuid4()))
  with open(locname, "w") as f:
    for row in list_to_write:
      f.write("%s\n" % row)
  tf.io.gfile.copy(locname, output_path, overwrite=False)


def decode_str_or_unicode(str_or_unicode):
  return str_or_unicode.decode() if hasattr(str_or_unicode, 'decode') else str_or_unicode


def longest_common_prefix(strings, split_character):
  """
  Args:
    string (list<str>): The list of strings to find the longest common prefix of
    split_character (str): If not None, require that the return string end in this character or
      be the length of the entire string
  Returns:
    The string corresponding to the longest common prefix
  """
  sorted_strings = sorted(strings)
  s1, s2 = sorted_strings[0], sorted_strings[-1]
  if s1 == s2:
    # If the strings are the same, just return the full string
    out = s1
  else:
    # If the strings are not the same, return the longest common prefix optionally ending in split_character
    ix = 0
    for i in range(min(len(s1), len(s2))):
      if s1[i] != s2[i]:
        break
      if split_character is None or s1[i] == split_character:
        ix = i + 1
    out = s1[:ix]
  return out


def _expand_prefix(fname, prefix, split_character):
  if len(fname) == len(prefix):
    # If the prefix is already the full feature, just take the feature name
    out = fname
  elif split_character is None:
    # Advance the prefix by one character
    out = fname[:len(prefix) + 1]
  else:
    # Advance the prefix to the next instance of split_character or the end of the string
    for ix in range(len(prefix), len(fname)):
      if fname[ix] == split_character:
        break
    out = fname[:ix + 1]
  return out


def _get_feature_types_from_records(records, fnames):
  # This method gets the types of the features in fnames by looking at the datarecords themselves.
  #   The reason why we do this rather than extract the feature types from the feature_config is
  #   that the feature naming conventions in the feature_config are different from those in the
  #   datarecords.
  fids = [twml.feature_id(fname)[0] for fname in fnames]
  feature_to_type = {}
  for record in records:
    for feature_type, values in record.__dict__.items():
      if values is not None:
        included_ids = set(values)
        for fname, fid in zip(fnames, fids):
          if fid in included_ids:
            feature_to_type[fname] = feature_type
  return feature_to_type


def _get_metrics_hook(trainer):
  def get_metrics_fn(trainer=trainer):
    return {k: v[0]for k, v in trainer.current_estimator_spec.eval_metric_ops.items()}
  return twml.hooks.GetMetricsHook(get_metrics_fn=get_metrics_fn)


def _get_feature_name_from_config(feature_config):
  """Extract the names of the features on a feature config object
  """
  decoded_feature_names = []
  for f in feature_config.get_feature_spec()['features'].values():
    try:
      fname = decode_str_or_unicode(f['featureName'])
    except UnicodeEncodeError as e:
      logging.error("Encountered decoding exception when decoding %s: %s" % (f, e))
    decoded_feature_names.append(fname)
  return decoded_feature_names
