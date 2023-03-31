'\nFeature configuration for DeepBird jobs returns dictionary of sparse and dense Features\n'
from twitter.deepbird.io.legacy.contrib import feature_config
import twml
class FeatureConfig(feature_config.FeatureConfig):
	def get_feature_spec(B):'\n    Generates a serialization-friendly dict representing this FeatureConfig.\n    ';A=super(FeatureConfig,B).get_feature_spec();A['class']='twml.contrib.FeatureConfig';return A
class FeatureConfigBuilder(feature_config.FeatureConfigBuilder):
	def build(B):
		'\n    Returns an instance of FeatureConfig with the features passed to the FeatureConfigBuilder.\n    ';E,F,G,H,I,J=B._build();D={}
		for A in B._sparse_extraction_configs:
			if A.discretize_num_bins and A.discretize_output_size_bits:
				if A.discretize_type=='percentile':C=twml.contrib.calibrators.PercentileDiscretizerCalibrator
				elif A.discretize_type=='hashed_percentile':C=twml.contrib.calibrators.HashedPercentileDiscretizerCalibrator
				elif A.discretize_type=='hashing':C=twml.contrib.calibrators.HashingDiscretizerCalibrator
				else:raise ValueError('Unsupported discretizer type: '+A.discretize_type)
				D[A.output_name]=C(A.discretize_num_bins,A.discretize_output_size_bits,allow_empty_calibration=A.allow_empty_calibration)
			elif A.discretize_num_bins or A.discretize_output_size_bits:raise ValueError('Discretize_num_bins AND discretize_output_size_bits need to be in the FeatureConfig')
		return FeatureConfig(features={},labels=B._labels,weight=B._weight,filters=B._filter_features,tensor_types=E,sparse_tensor_types=F,feature_types=G,sparse_extraction_configs=B._sparse_extraction_configs,feature_extraction_configs=B._feature_extraction_configs,feature_group_extraction_configs=B._feature_group_extraction_configs,image_configs=B._image_configs,discretize_config=D,feature_ids=H,decode_mode=B._decode_mode,legacy_sparse=B._legacy_sparse,feature_name_to_feature_parser=I,feature_in_bq_name=J)
TensorExtractionConfig=feature_config.TensorExtractionConfig
FeatureGroupExtractionConfig=feature_config.FeatureGroupExtractionConfig
ImageExtractionConfig=feature_config.ImageExtractionConfig
_set_tensor_namedtuple=feature_config._set_tensor_namedtuple