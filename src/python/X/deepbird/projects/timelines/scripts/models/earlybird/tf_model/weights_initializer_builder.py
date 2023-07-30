from .hashing_utils import make_feature_id, numpy_hashing_uniform

import numpy as np
import tensorflow.compat.v1 as tf
import twml


class TFModelWeightsInitializerBuilder(object):
  def __init__(self, num_bits):
    self.num_bits = num_bits

  def build(self, tf_model_initializer):
    '''
    :return: (bias_initializer, weight_initializer)
    '''
    initial_weights = np.zeros((2 ** self.num_bits, 1))

    features = tf_model_initializer["features"]
    self._set_binary_feature_weights(initial_weights, features["binary"])
    self._set_discretized_feature_weights(initial_weights, features["discretized"])

    return tf.constant_initializer(features["bias"]), twml.contrib.initializers.PartitionConstant(initial_weights)

  def _set_binary_feature_weights(self, initial_weights, binary_features):
    for feature_name, weight in binary_features.items():
      feature_id = make_feature_id(feature_name, self.num_bits)
      initial_weights[feature_id][0] = weight

  def _set_discretized_feature_weights(self, initial_weights, discretized_features):
    for feature_name, discretized_feature in discretized_features.items():
      feature_id = make_feature_id(feature_name, self.num_bits)
      for bin_idx, weight in enumerate(discretized_feature["weights"]):
        final_bucket_id = numpy_hashing_uniform(feature_id, bin_idx, self.num_bits)
        initial_weights[final_bucket_id][0] = weight
