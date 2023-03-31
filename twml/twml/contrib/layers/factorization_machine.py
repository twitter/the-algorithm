'\nImplementing factorization Layer\n'
_B=True
_A=None
from twitter.deepbird.sparse.sparse_ops import _pad_empty_outputs
import tensorflow.compat.v1 as tf,twml
from twml.layers.layer import Layer
class FactorizationMachine(Layer):
	'factorization machine layer class.\n  This layer implements the factorization machine operation.\n  The paper is "Factorization Machines" by Steffen Rendle.\n  TDD: go/tf-fm-tdd\n\n  Arguments:\n    num_latent_variables:\n      num of latent variables\n      The number of parameter in this layer is num_latent_variables x n where n is number of\n      input features.\n    weight_initializer:\n      Initializer function for the weight matrix.\n      This argument defaults to zeros_initializer().\n      This is valid when the FullSparse is the first layer of\n      parameters but should be changed otherwise.\n    weight_regularizer:\n      Regularizer function for the weight matrix.\n      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.\n    activation:\n      Activation function (callable). Set it to None to maintain a linear activation.\n    trainable:\n      Boolean, if `True` also add variables to the graph collection\n      ``GraphKeys.TRAINABLE_VARIABLES`` (see `tf.Variable\n      <https://www.tensorflow.org/versions/master/api_docs/python/tf/Variable>`_).\n    name:\n      String, the name of the layer. Layers with the same name will\n      share weights, but to avoid mistakes we require ``reuse=True`` in such cases.\n    use_sparse_grads:\n      Boolean, if `True` do sparse mat mul with `embedding_lookup_sparse`, which will\n      make gradients to weight matrix also sparse in backward pass. This can lead to non-trivial\n      speed up at training time when input_size is large and optimizer handles sparse gradients\n      correctly (eg. with SGD or LazyAdamOptimizer). If weight matrix is small, it\'s recommended\n      to set this flag to `False`; for most use cases of FullSparse, however, weight matrix will\n      be large, so it\'s better to set it to `True`\n    use_binary_values:\n      Assume all non zero values are 1. Defaults to False.\n      This can improve training if used in conjunction with MDL.\n      This parameter can also be a list of binary values if `inputs` passed to `call` a list.\n  '
	def __init__(A,num_latent_variables=10,weight_initializer=_A,activation=_A,trainable=_B,name=_A,use_sparse_grads=_B,use_binary_values=False,weight_regularizer=_A,substract_self_cross=_B,**C):
		B=weight_initializer;super(FactorizationMachine,A).__init__(trainable=trainable,name=name,**C)
		if B is _A:B=tf.zeros_initializer()
		A.weight_initializer=B;A.num_latent_variables=num_latent_variables;A.activation=activation;A.use_sparse_grads=use_sparse_grads;A.use_binary_values=use_binary_values;A.weight_regularizer=weight_regularizer;A.substract_self_cross=substract_self_cross
	def build(A,input_shape):
		'\n    creates``weight`` Variable of shape``[input_size, num_latent_variables]``.\n\n    ';B=input_shape;C=[B[1],A.num_latent_variables];D=tf.as_dtype(A.dtype);E=B[1]*A.num_latent_variables*D.size
		if E>=2**31:raise ValueError('Weight tensor can not be larger than 2GB. '%'Requested Dimensions(%d, %d) of type %s (%d bytes total)'(B[1],A.num_latent_variables,D.name))
		if not callable(A.weight_initializer):C=_A
		A.weight=A.add_variable('weight',initializer=A.weight_initializer,regularizer=A.weight_regularizer,shape=C,dtype=A.dtype,trainable=_B);A.built=_B
	def compute_output_shape(A,input_shape):'Computes the output shape of the layer given the input shape.\n\n    Args:\n      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not\n        be fully defined (e.g. the batch size may be unknown).\n\n    Raises NotImplementedError.\n\n    ';raise NotImplementedError
	def call(A,inputs,**P):
		'The logic of the layer lives here.\n\n    Arguments:\n      inputs:\n        A SparseTensor\n    Returns:\n      - If `inputs` is `SparseTensor`, then returns a number with cross info\n    ';D=inputs;C=D
		if isinstance(D,twml.SparseTensor):C=D.to_tf()
		elif not isinstance(C,tf.SparseTensor):raise TypeError('The sp_x must be of type tf.SparseTensor or twml.SparseTensor')
		F=C.indices[:,1];E=C.indices[:,0];G=tf.reshape(C.values,[-1,1],name=A.name)
		if A.use_sparse_grads:
			L=tf.nn.embedding_lookup(A.weight,F);H=L*G;I=tf.segment_sum(H,E,name=A.name);J=tf.reduce_sum(I*I,1)
			if A.substract_self_cross:M=H**2;N=tf.reduce_sum(tf.segment_sum(M,E,name=A.name),1);B=J-N
			else:B=J
		else:
			K=tf.reduce_sum(tf.sparse_tensor_dense_matmul(C,A.weight)**2,1)
			if A.substract_self_cross:O=tf.reduce_sum(tf.segment_sum((tf.gather(A.weight,F)*G)**2,E),1);B=K-O
			else:B=K
		if A.activation is not _A:B=A.activation(B)
		B=tf.reshape(B,[-1,1],name=A.name);B=_pad_empty_outputs(B,tf.cast(C.dense_shape[0],tf.int32));B.set_shape([_A,1]);return B