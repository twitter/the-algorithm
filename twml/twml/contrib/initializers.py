_A=None
import numpy as np,tensorflow.compat.v1 as tf
TWML_INIT_FEED_KEY='TWML_INIT_FEED_COLLECTION'
class PartitionConstant(tf.keras.initializers.Constant):
	'A constant initializer that supports partitions'
	def __call__(A,shape,dtype=_A,partition_info=_A):
		B=partition_info
		if B is not _A:
			if not isinstance(A.value,np.ndarray):raise ValueError('Currently, PartitionConstant only supports partitioning on np.ndarrays. Got {}'.format(type(A.value).__name__))
			C=B.var_offset;D=tuple([slice(A,A+B)for(A,B)in zip(C,shape)]);E=A.value[D];return E
		else:return A.value
partition_constant_initializer=PartitionConstant
class PlaceholderInitializer(tf.keras.initializers.Initializer):
	'A placeholder initializer that supports partitions'
	def __init__(A,shape,dtype):B=dtype;A.dtype=B;A.value=tf.placeholder(dtype=B,shape=shape)
	def __call__(A,shape,dtype=_A,partition_info=_A):
		B=partition_info
		if B is not _A:
			if A.dtype!=dtype:raise ValueError('dtype does not match placeholder dtype')
			C=B.var_offset;D=tuple([slice(A,A+B)for(A,B)in zip(C,shape)]);E=A.value[D];return E
		else:return A.value
def get_init_feed_dict():
	'Get the init feed dictionary to be used when running the init op.';B=tf.get_collection(TWML_INIT_FEED_KEY);A={}
	for C in B:A.update(C)
	return A
def clear_init_feed_collection():
	'Clear the init feed collection.';A=tf.get_collection_ref(TWML_INIT_FEED_KEY)
	while A:A.pop()