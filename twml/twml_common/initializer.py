import tensorflow.compat.v1 as tf
class PartitionInitializer(tf.keras.initializers.Initializer):
	'Required to initialize partitioned weight with numpy array for tests'
	def __init__(A,np_array):A.np_array=np_array
	def __call__(C,shape,dtype=None,partition_info=None):B=shape;A=partition_info.var_offset;D,E=A[0],A[0]+B[0];F,G=A[1],A[1]+B[1];return C.np_array[D:E,F:G]