"""Utility functions to create FeatureConfig objects from feature_spec.yaml files"""
import os
import re

import tensorflow.compat.v1 as tf
import yaml
from twml.feature_config import FeatureConfigBuilder
from twml.contrib.feature_config import FeatureConfigBuilder as FeatureConfigBuilderV2


def _get_config_version(config_dict):
  doc = config_dict
  supported_classes = {
    "twml.FeatureConfig": "v1",
    "twml.contrib.FeatureConfig": "v2"
  }
  if "class" not in doc:
    raise ValueError("'class' key not found")
  if doc["class"] not in supported_classes.keys():
    raise ValueError("Class %s not supported. Supported clases are %s"
                     % (doc["class"], supported_classes.keys()))
  return supported_classes[doc["class"]]


def _validate_config_dict_v1(config_dict):
  """
  Validate spec exported by twml.FeatureConfig
  """
  doc = config_dict

  def malformed_error(msg):
    raise ValueError("twml.FeatureConfig: Malformed feature_spec. %s" % msg)

  if doc["class"] != "twml.FeatureConfig":
    malformed_error("'class' is not twml.FeatureConfig")
  if "format" not in doc:
    malformed_error("'format' key not found")

  # validate spec exported by twml.FeatureConfig
  if doc["format"] == "exported":
    dict_keys = ["features", "labels", "weight", "tensors", "sparse_tensors"]
    for key in dict_keys:
      if key not in doc:
        malformed_error("'%s' key not found" % key)
      if type(doc[key]) != dict:
        malformed_error("'%s' is not a dict" % key)
    if "filters" not in doc:
      malformed_error("'filters' key not found")
    elif type(doc["filters"]) != list:
      malformed_error("'filters' is not a list")

  # validate spec provided by modeler
  elif doc["format"] == "manual":
    raise NotImplementedError("Manual config support not yet implemented")
  else:
    malformed_error("'format' must be 'exported' or 'manual'")


def _validate_config_dict_v2(config_dict):
  """
  Validate spec exported by twml.contrib.FeatureConfig
  """
  doc = config_dict

  def malformed_error(msg):
    raise ValueError("twml.contrib.FeatureConfig: Malformed feature_spec. %s" % msg)

  if doc["class"] != "twml.contrib.FeatureConfig":
    malformed_error("'class' is not twml.contrib.FeatureConfig")
  if "format" not in doc:
    malformed_error("'format key not found'")

  # validate spec exported by twml.contrib.FeatureConfig (basic validation only)
  if doc["format"] == "exported":
    dict_keys = ["features", "labels", "weight", "tensors", "sparseTensors", "discretizeConfig"]
    for key in dict_keys:
      if key not in doc:
        malformed_error("'%s' key not found" % key)
      if type(doc[key]) != dict:
        malformed_error("'%s' is not a dict" % key)
    list_keys = ["sparseFeatureGroups", "denseFeatureGroups", "denseFeatures", "images", "filters"]
    for key in list_keys:
      if key not in doc:
        malformed_error("'%s' key not found" % key)
      if type(doc[key]) != list:
        malformed_error("'%s' is not a list" % key)

  # validate spec provided by modeler
  elif doc["format"] == "manual":
    raise NotImplementedError("Manual config support not yet implemented")
  else:
    malformed_error("'format' must be 'exported' or 'manual'")


def _create_feature_config_v1(config_dict, data_spec_path):
  fc_builder = FeatureConfigBuilder(data_spec_path)

  if config_dict["format"] == "exported":
    # add features
    for feature_info in config_dict["features"].values():
      feature_name = re.escape(feature_info["featureName"])
      feature_group = feature_info["featureGroup"]
      fc_builder.add_feature(feature_name, feature_group)
    # add labels
    labels = []
    for label_info in config_dict["labels"].values():
      labels.append(label_info["featureName"])
    fc_builder.add_labels(labels)
    # feature filters
    for feature_name in config_dict["filters"]:
      fc_builder.add_filter(feature_name)
    # weight
    if config_dict["weight"]:
      weight_feature = list(config_dict["weight"].values())[0]["featureName"]
      fc_builder.define_weight(weight_feature)
  else:
    raise ValueError("Format '%s' not implemented" % config_dict["format"])

  return fc_builder.build()


def _create_feature_config_v2(config_dict, data_spec_path):
  fc_builder = FeatureConfigBuilderV2(data_spec_path)

  if config_dict["format"] == "exported":
    # add sparse group extraction configs
    for sparse_group in config_dict["sparseFeatureGroups"]:
      fids = sparse_group["features"].keys()
      fnames = [sparse_group["features"][fid]["featureName"] for fid in fids]
      fc_builder.extract_features_as_hashed_sparse(
        feature_regexes=[re.escape(fname) for fname in fnames],
        output_tensor_name=sparse_group["outputName"],
        hash_space_size_bits=sparse_group["hashSpaceBits"],
        discretize_num_bins=sparse_group["discretize"]["numBins"],
        discretize_output_size_bits=sparse_group["discretize"]["outputSizeBits"],
        discretize_type=sparse_group["discretize"]["type"],
        type_filter=sparse_group["filterType"])

    # add dense group extraction configs
    for dense_group in config_dict["denseFeatureGroups"]:
      fids = dense_group["features"].keys()
      fnames = [dense_group["features"][fid]["featureName"] for fid in fids]
      fc_builder.extract_feature_group(
        feature_regexes=[re.escape(fname) for fname in fnames],
        group_name=dense_group["outputName"],
        type_filter=dense_group["filterType"],
        default_value=dense_group["defaultValue"])

    # add dense feature configs
    for dense_features in config_dict["denseFeatures"]:
      fids = dense_features["features"].keys()
      fnames = [dense_features["features"][fid]["featureName"] for fid in fids]
      default_value = dense_features["defaultValue"]
      if len(fnames) == 1 and type(default_value) != dict:
        fc_builder.extract_feature(
          feature_name=re.escape(fnames[0]),
          expected_shape=dense_features["expectedShape"],
          default_value=dense_features["defaultValue"])
      else:
        fc_builder.extract_features(
          feature_regexes=[re.escape(fname) for fname in fnames],
          default_value_map=dense_features["defaultValue"])

    # add image feature configs
    for image in config_dict["images"]:
      fc_builder.extract_image(
        feature_name=image["featureName"],
        preprocess=image["preprocess"],
        out_type=tf.as_dtype(image["outType"].lower()),
        channels=image["channels"],
        default_image=image["defaultImage"],
      )

    # add other tensor features (non-image)
    tensor_fnames = []
    image_fnames = [img["featureName"] for img in config_dict["images"]]
    for tensor_fname in config_dict["tensors"]:
      if tensor_fname not in image_fnames:
        tensor_fnames.append(tensor_fname)
    for sparse_tensor_fname in config_dict["sparseTensors"]:
      tensor_fnames.append(sparse_tensor_fname)
    fc_builder.extract_tensors(tensor_fnames)

    # add labels
    labels = []
    for label_info in config_dict["labels"].values():
      labels.append(label_info["featureName"])
    fc_builder.add_labels(labels)

  else:
    raise ValueError("Format '%s' not implemented" % config_dict["format"])

  return fc_builder.build()


def create_feature_config_from_dict(config_dict, data_spec_path):
  """
  Create a FeatureConfig object from a feature spec dict.
  """
  config_version = _get_config_version(config_dict)
  if config_version == "v1":
    _validate_config_dict_v1(config_dict)
    feature_config = _create_feature_config_v1(config_dict, data_spec_path)
  elif config_version == "v2":
    _validate_config_dict_v2(config_dict)
    feature_config = _create_feature_config_v2(config_dict, data_spec_path)
  else:
    raise ValueError("version not supported")

  return feature_config


def create_feature_config(config_path, data_spec_path):
  """
  Create a FeatureConfig object from a feature_spec.yaml file.
  """
  _, ext = os.path.splitext(config_path)
  if ext not in ['.yaml', '.yml']:
    raise ValueError("create_feature_config_from_yaml: Only .yaml/.yml supported")

  with tf.io.gfile.GFile(config_path, mode='r') as fs:
    config_dict = yaml.safe_load(fs)

  return create_feature_config_from_dict(config_dict, data_spec_path)
