from .hashing_utils import make_feature_id

from twml.contrib.layers.hashing_discretizer import HashingDiscretizer
import numpy as np


class TFModelDiscretizerBuilder(object):
  def __init__(self, num_bits):
    self.num_bits = num_bits

  def build(self, tf_model_initializer):
    '''
    :param tf_model_initializer: dictionary of the following format:
      {
        "features": {
          "bias": 0.0,
          "binary": {
            # (feature name : feature weight) pairs
            "feature_name_1": 0.0,
            ...
            "feature_nameN": 0.0
          },
          "discretized": {
            # (feature name : index aligned lists of bin_boundaries and weights
            "feature_name_1": {
              "bin_boundaries": [1, ..., inf],
              "weights": [0.0, ..., 0.0]
            }
            ...
            "feature_name_K": {
              "bin_boundaries": [1, ..., inf],
              "weights": [0.0, ..., 0.0]
            }
          }
        }
      }
    :return: a HashingDiscretizer instance.
    '''
    discretized_features = tf_model_initializer["features"]["discretized"]

    max_bins = 0

    feature_ids = []
    bin_vals = []
    for feature_name in discretized_features:
      bin_boundaries = discretized_features[feature_name]["bin_boundaries"]
      feature_id = make_feature_id(feature_name, self.num_bits)
      feature_ids.append(feature_id)
      np_bin_boundaries = [np.float(bin_boundary) for bin_boundary in bin_boundaries]
      bin_vals.append(np_bin_boundaries)

      max_bins = max(max_bins, len(np_bin_boundaries))

    feature_ids_np = np.array(feature_ids)
    bin_vals_np = np.array(bin_vals).flatten()

    return HashingDiscretizer(
      feature_ids=feature_ids_np,
      bin_vals=bin_vals_np,
      n_bin=max_bins,
      out_bits=self.num_bits
    )
