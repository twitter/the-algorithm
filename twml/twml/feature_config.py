'\nFeature configuration for DeepBird jobs:\n- Which features to keep\n- Which features to blacklist\n- Which features are labels\n- Which feature is the weight\n'
from twitter.deepbird.io.legacy import feature_config
class FeatureConfig(feature_config.FeatureConfig):
	def get_feature_spec(B):'\n    Generates a serialization-friendly dict representing this FeatureConfig.\n    ';A=super(FeatureConfig,B).get_feature_spec();A['class']='twml.FeatureConfig';return A
class FeatureConfigBuilder(feature_config.FeatureConfigBuilder):
	def build(A):'\n    Builds and returns FeatureConfig object.\n    ';B,C,D,E,F,G=A._build();return FeatureConfig(features=B,labels=A._labels,weight=A._weight,filters=A._filter_features,tensor_types=C,sparse_tensor_types=D,feature_types=E,decode_mode=A._decode_mode,legacy_sparse=A._legacy_sparse,feature_name_to_feature_parser=A._feature_name_to_feature_parser,feature_in_bq_name=A._feature_in_bq_name)
_name_to_id=feature_config._name_to_id