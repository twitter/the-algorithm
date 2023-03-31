import numpy as np,tensorflow.compat.v1 as tf
def create_sparse_tensor(batch_size,input_size,num_values,dtype=tf.float32):B=num_values;C=batch_size;A=input_size;D=np.sort(np.random.randint(C*A,size=B));E=D//A;F=D%A;G=np.stack([E,F],axis=1);H=np.random.random(B).astype(dtype.as_numpy_dtype);return tf.SparseTensor(indices=tf.constant(G),values=tf.constant(H),dense_shape=(C,A))
def create_reference_input(sparse_input,use_binary_values):
	A=sparse_input
	if use_binary_values:B=tf.SparseTensor(indices=A.indices,values=tf.ones_like(A.values),dense_shape=A.dense_shape)
	else:B=A
	return B