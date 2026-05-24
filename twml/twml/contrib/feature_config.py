"""
Feature configuration for DeepBird jobs returns dictionary of sparse and dense Features
"""
from twitter.deepbird.io.legacy.contrib import feature_config
import twml


class FeatureConfig(feature_config.FeatureConfig):
  def get_feature_spec(self):
    """
    Generates a serialization-friendly dict representing this FeatureConfig.
    """
    doc = super(FeatureConfig, self).get_feature_spec()

    # Override the class in the spec.
    doc["class"] = "twml.contrib.FeatureConfig"

    return doc


class FeatureConfigBuilder(feature_config.FeatureConfigBuilder):
  # Overwrite self.build() to return twml.FeatureConfig instead
  def build(self):
    """
    Returns an instance of FeatureConfig with the features passed to the FeatureConfigBuilder.
    """

    (
      keep_tensors,
      keep_sparse_tensors,
      feature_map,
      features_add,
      feature_name_to_feature_parser,
      feature_in_bq_name,
    ) = self._build()

    discretize_dict = {}
    for config in self._sparse_extraction_configs:
      if config.discretize_num_bins and config.discretize_output_size_bits:
        if config.discretize_type == "percentile":
          calibrator = twml.contrib.calibrators.PercentileDiscretizerCalibrator
        elif config.discretize_type == "hashed_percentile":
          calibrator = twml.contrib.calibrators.HashedPercentileDiscretizerCalibrator
        elif config.discretize_type == "hashing":
          calibrator = twml.contrib.calibrators.HashingDiscretizerCalibrator
        else:
          raise ValueError("Unsupported discretizer type: " + config.discretize_type)
        discretize_dict[config.output_name] = calibrator(
          config.discretize_num_bins,
          config.discretize_output_size_bits,
          allow_empty_calibration=config.allow_empty_calibration,
        )
      elif config.discretize_num_bins or config.discretize_output_size_bits:
        raise ValueError(
          "Discretize_num_bins AND discretize_output_size_bits need to be in the FeatureConfig"
        )

    return FeatureConfig(
      features={},
      labels=self._labels,
      weight=self._weight,
      filters=self._filter_features,
      tensor_types=keep_tensors,
      sparse_tensor_types=keep_sparse_tensors,
      feature_types=feature_map,
      sparse_extraction_configs=self._sparse_extraction_configs,
      feature_extraction_configs=self._feature_extraction_configs,
      feature_group_extraction_configs=self._feature_group_extraction_configs,
      image_configs=self._image_configs,
      discretize_config=discretize_dict,
      feature_ids=features_add,
      decode_mode=self._decode_mode,
      legacy_sparse=self._legacy_sparse,
      feature_name_to_feature_parser=feature_name_to_feature_parser,
      feature_in_bq_name=feature_in_bq_name,
    )


TensorExtractionConfig = feature_config.TensorExtractionConfig

FeatureGroupExtractionConfig = feature_config.FeatureGroupExtractionConfig

ImageExtractionConfig = feature_config.ImageExtractionConfig

_set_tensor_namedtuple = feature_config._set_tensor_namedtuple
