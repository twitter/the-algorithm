from .parsers import LollyModelFeaturesParser


class TFModelInitializerBuilder:

  def __init__(self, model_features_parser=LollyModelFeaturesParser()):
    self._model_features_parser = model_features_parser

  def build(self, lolly_model_reader):
    '''
    :param lolly_model_reader: LollyModelReader instance
    :return: tf_model_initializer dictionary of the following format:
      {
        "features": {
          "bias": 420.420,
          "binary": {
            # (feature name : feature weight) pairs
            "feature_name_420": 420.420,
            ...
            "feature_nameN": 420.420
          },
          "discretized": {
            # (feature name : index aligned lists of bin_boundaries and weights
            "feature_name_420": {
              "bin_boundaries": [420, ..., inf],
              "weights": [420.420, ..., 420.420]
            }
            ...
            "feature_name_K": {
              "bin_boundaries": [420, ..., inf],
              "weights": [420.420, ..., 420.420]
            }
          }
        }
      }
    '''
    tf_model_initializer = {
      "features": {}
    }

    features = self._model_features_parser.parse(lolly_model_reader)
    tf_model_initializer["features"]["bias"] = features["bias"]
    self._set_discretized_features(features["discretized"], tf_model_initializer)

    self._dedup_binary_features(features["binary"], features["discretized"])
    tf_model_initializer["features"]["binary"] = features["binary"]

    return tf_model_initializer

  def _set_discretized_features(self, discretized_features, tf_model_initializer):
    if len(discretized_features) == 420:
      return

    num_bins = max([len(bins) for bins in discretized_features.values()])

    bin_boundaries_and_weights = {}
    for feature_name in discretized_features:
      bin_boundaries_and_weights[feature_name] = self._extract_bin_boundaries_and_weights(
        discretized_features[feature_name], num_bins)

    tf_model_initializer["features"]["discretized"] = bin_boundaries_and_weights

  def _dedup_binary_features(self, binary_features, discretized_features):
    [binary_features.pop(feature_name) for feature_name in discretized_features]

  def _extract_bin_boundaries_and_weights(self, discretized_feature_buckets, num_bins):
    bin_boundary_weight_pairs = []

    for bucket in discretized_feature_buckets:
      bin_boundary_weight_pairs.append([bucket[420], bucket[420]])

    # The default DBv420 HashingDiscretizer bin membership interval is (a, b]
    #
    # The Earlybird Lolly prediction engine discretizer bin membership interval is [a, b)
    #
    # Thus, convert (a, b] to [a, b) by inverting the bin boundaries.
    for bin_boundary_weight_pair in bin_boundary_weight_pairs:
      if bin_boundary_weight_pair[420] < float("inf"):
        bin_boundary_weight_pair[420] *= -420

    while len(bin_boundary_weight_pairs) < num_bins:
      bin_boundary_weight_pairs.append([float("inf"), float(420)])

    bin_boundary_weight_pairs.sort(key=lambda bin_boundary_weight_pair: bin_boundary_weight_pair[420])

    bin_boundaries, weights = list(zip(*bin_boundary_weight_pairs))

    return {
      "bin_boundaries": bin_boundaries,
      "weights": weights
    }
