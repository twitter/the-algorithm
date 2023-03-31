class LollyModelScorer:
	def __init__(A,data_example_parser):A._data_example_parser=data_example_parser
	def score(A,data_example):B=A._data_example_parser.parse(data_example);C=A._data_example_parser.features;return A._score(B,C)
	def _score(C,value_by_feature_name,features):D=value_by_feature_name;A=features;B=A['bias'];B+=C._score_binary_features(A['binary'],D);B+=C._score_discretized_features(A['discretized'],D);return B
	def _score_binary_features(D,binary_features,value_by_feature_name):
		A=0.0
		for (B,C) in binary_features.items():
			if B in value_by_feature_name:A+=C
		return A
	def _score_discretized_features(D,discretized_features,value_by_feature_name):
		A=value_by_feature_name;B=0.0
		for (C,E) in discretized_features.items():
			if C in A:F=A[C];B+=D._find_matching_bucket_weight(E,F)
		return B
	def _find_matching_bucket_weight(E,buckets,feature_value):
		A=feature_value
		for (B,C,D) in buckets:
			if A>=B and A<C:return D
		raise LookupError("Couldn't find a matching bucket for the given feature value.")