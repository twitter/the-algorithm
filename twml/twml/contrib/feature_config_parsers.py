'Utility functions to create FeatureConfig objects from feature_spec.yaml files'
_V="Format '%s' not implemented"
_U='denseFeatures'
_T='denseFeatureGroups'
_S='sparseFeatureGroups'
_R='sparseTensors'
_Q="'format' must be 'exported' or 'manual'"
_P='Manual config support not yet implemented'
_O='manual'
_N="'%s' is not a dict"
_M='twml.contrib.FeatureConfig'
_L='twml.FeatureConfig'
_K='images'
_J="'%s' key not found"
_I='tensors'
_H='filters'
_G='weight'
_F='labels'
_E='exported'
_D='class'
_C='featureName'
_B='features'
_A='format'
import os,re,tensorflow.compat.v1 as tf,yaml
from twml.feature_config import FeatureConfigBuilder
from twml.contrib.feature_config import FeatureConfigBuilder as FeatureConfigBuilderV2
def _get_config_version(config_dict):
	A=config_dict;B={_L:'v1',_M:'v2'}
	if _D not in A:raise ValueError("'class' key not found")
	if A[_D]not in B.keys():raise ValueError('Class %s not supported. Supported clases are %s'%(A[_D],B.keys()))
	return B[A[_D]]
def _validate_config_dict_v1(config_dict):
	'\n  Validate spec exported by twml.FeatureConfig\n  ';A=config_dict
	def B(msg):raise ValueError('twml.FeatureConfig: Malformed feature_spec. %s'%msg)
	if A[_D]!=_L:B("'class' is not twml.FeatureConfig")
	if _A not in A:B("'format' key not found")
	if A[_A]==_E:
		D=[_B,_F,_G,_I,'sparse_tensors']
		for C in D:
			if C not in A:B(_J%C)
			if type(A[C])!=dict:B(_N%C)
		if _H not in A:B("'filters' key not found")
		elif type(A[_H])!=list:B("'filters' is not a list")
	elif A[_A]==_O:raise NotImplementedError(_P)
	else:B(_Q)
def _validate_config_dict_v2(config_dict):
	'\n  Validate spec exported by twml.contrib.FeatureConfig\n  ';B=config_dict
	def C(msg):raise ValueError('twml.contrib.FeatureConfig: Malformed feature_spec. %s'%msg)
	if B[_D]!=_M:C("'class' is not twml.contrib.FeatureConfig")
	if _A not in B:C("'format key not found'")
	if B[_A]==_E:
		D=[_B,_F,_G,_I,_R,'discretizeConfig']
		for A in D:
			if A not in B:C(_J%A)
			if type(B[A])!=dict:C(_N%A)
		E=[_S,_T,_U,_K,_H]
		for A in E:
			if A not in B:C(_J%A)
			if type(B[A])!=list:C("'%s' is not a list"%A)
	elif B[_A]==_O:raise NotImplementedError(_P)
	else:C(_Q)
def _create_feature_config_v1(config_dict,data_spec_path):
	A=config_dict;B=FeatureConfigBuilder(data_spec_path)
	if A[_A]==_E:
		for D in A[_B].values():C=re.escape(D[_C]);F=D['featureGroup'];B.add_feature(C,F)
		E=[]
		for G in A[_F].values():E.append(G[_C])
		B.add_labels(E)
		for C in A[_H]:B.add_filter(C)
		if A[_G]:H=list(A[_G].values())[0][_C];B.define_weight(H)
	else:raise ValueError(_V%A[_A])
	return B.build()
def _create_feature_config_v2(config_dict,data_spec_path):
	L='filterType';M='outputName';J='discretize';I='defaultValue';A=config_dict;B=FeatureConfigBuilderV2(data_spec_path)
	if A[_A]==_E:
		for C in A[_S]:F=C[_B].keys();D=[C[_B][A][_C]for A in F];B.extract_features_as_hashed_sparse(feature_regexes=[re.escape(A)for A in D],output_tensor_name=C[M],hash_space_size_bits=C['hashSpaceBits'],discretize_num_bins=C[J]['numBins'],discretize_output_size_bits=C[J]['outputSizeBits'],discretize_type=C[J]['type'],type_filter=C[L])
		for G in A[_T]:F=G[_B].keys();D=[G[_B][A][_C]for A in F];B.extract_feature_group(feature_regexes=[re.escape(A)for A in D],group_name=G[M],type_filter=G[L],default_value=G[I])
		for E in A[_U]:
			F=E[_B].keys();D=[E[_B][A][_C]for A in F];P=E[I]
			if len(D)==1 and type(P)!=dict:B.extract_feature(feature_name=re.escape(D[0]),expected_shape=E['expectedShape'],default_value=E[I])
			else:B.extract_features(feature_regexes=[re.escape(A)for A in D],default_value_map=E[I])
		for H in A[_K]:B.extract_image(feature_name=H[_C],preprocess=H['preprocess'],out_type=tf.as_dtype(H['outType'].lower()),channels=H['channels'],default_image=H['defaultImage'])
		K=[];Q=[A[_C]for A in A[_K]]
		for N in A[_I]:
			if N not in Q:K.append(N)
		for R in A[_R]:K.append(R)
		B.extract_tensors(K);O=[]
		for S in A[_F].values():O.append(S[_C])
		B.add_labels(O)
	else:raise ValueError(_V%A[_A])
	return B.build()
def create_feature_config_from_dict(config_dict,data_spec_path):
	'\n  Create a FeatureConfig object from a feature spec dict.\n  ';B=data_spec_path;A=config_dict;C=_get_config_version(A)
	if C=='v1':_validate_config_dict_v1(A);D=_create_feature_config_v1(A,B)
	elif C=='v2':_validate_config_dict_v2(A);D=_create_feature_config_v2(A,B)
	else:raise ValueError('version not supported')
	return D
def create_feature_config(config_path,data_spec_path):
	'\n  Create a FeatureConfig object from a feature_spec.yaml file.\n  ';A=config_path;E,B=os.path.splitext(A)
	if B not in['.yaml','.yml']:raise ValueError('create_feature_config_from_yaml: Only .yaml/.yml supported')
	with tf.io.gfile.GFile(A,mode='r')as C:D=yaml.safe_load(C)
	return create_feature_config_from_dict(D,data_spec_path)