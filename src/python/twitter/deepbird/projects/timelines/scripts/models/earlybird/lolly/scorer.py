class LollyModelScorer(object):
  def __init__(self, data_example_parser):
    self._data_example_parser = data_example_parser

  def score(self, data_example):
    value_by_feature_name = self._data_example_parser.parse(data_example)
    features = self._data_example_parser.features
    return self._score(value_by_feature_name, features)

  def _score(self, value_by_feature_name, features):
    score = features["bias"]
    score += self._score_binary_features(features["binary"], value_by_feature_name)
    score += self._score_discretized_features(features["discretized"], value_by_feature_name)
    return score

  def _score_binary_features(self, binary_features, value_by_feature_name):
    score = 0.0
    for binary_feature_name, binary_feature_weight in binary_features.items():
      if binary_feature_name in value_by_feature_name:
        score += binary_feature_weight
    return score

  def _score_discretized_features(self, discretized_features, value_by_feature_name):
    score = 0.0
    for discretized_feature_name, buckets in discretized_features.items():
      if discretized_feature_name in value_by_feature_name:
        feature_value = value_by_feature_name[discretized_feature_name]
        score += self._find_matching_bucket_weight(buckets, feature_value)
    return score

  def _find_matching_bucket_weight(self, buckets, feature_value):
    for left_side, right_side, weight in buckets:
      # The Earlybird Lolly prediction engine discretizer bin membership interval is [a, b)
      if feature_value >= left_side and feature_value < right_side:
        return weight

    raise LookupError("Couldn't find a matching bucket for the given feature value.")
