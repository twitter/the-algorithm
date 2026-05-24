"""
Feature configuration for DeepBird jobs:
- Which features to keep
- Which features to blacklist
- Which features are labels
- Which feature is the weight
"""

from twitter.deepbird.io.legacy import feature_config


class FeatureConfig(feature_config.FeatureConfig):
  def get_feature_spec(self):
    """
    Generates a serialization-friendly dict representing this FeatureConfig.
    """
    doc = super(FeatureConfig, self).get_feature_spec()
    # Override the class in the spec.
    doc["class"] = "twml.FeatureConfig"
    return doc


class FeatureConfigBuilder(feature_config.FeatureConfigBuilder):
  def build(self):
    # Overwrite self.build() to return twml.FeatureConfig instead
    """
    Builds and returns FeatureConfig object.
    """

    (
      features,
      tensor_types,
      sparse_tensor_types,
      feature_map,
      feature_name_to_feature_parser,
      feature_in_bq_name,
    ) = self._build()

    return FeatureConfig(
      features=features,
      labels=self._labels,
      weight=self._weight,
      filters=self._filter_features,
      tensor_types=tensor_types,
      sparse_tensor_types=sparse_tensor_types,
      feature_types=feature_map,
      decode_mode=self._decode_mode,
      legacy_sparse=self._legacy_sparse,
      feature_name_to_feature_parser=self._feature_name_to_feature_parser,
      feature_in_bq_name=self._feature_in_bq_name,
    )


_name_to_id = feature_config._name_to_id
