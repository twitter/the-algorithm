'\nContains the twml.layers.SparseMaxNorm layer.\n'
_B=True
_A=None
from .layer import Layer
from libtwml import OPLIB
import tensorflow.compat.v1 as tf,twml
class SparseMaxNorm(Layer):
	'\n  Computes a max-normalization and adds bias to the sparse_input,\n  forwards that through a sparse affine transform followed\n  by an non-linear activation on the resulting dense representation.\n\n  This layer has two parameters, one of which learns through gradient descent:\n    bias_x (optional):\n      vector of shape [input_size]. Learned through gradient descent.\n    max_x:\n      vector of shape [input_size]. Holds the maximas of input ``x`` for normalization.\n      Either calibrated through SparseMaxNorm calibrator, or calibrated online, or both.\n\n  The pseudo-code for this layer looks like:\n\n  .. code-block:: python\n\n    abs_x = abs(x)\n    normed_x = clip_by_value(x / max_x, -1, 1)\n    biased_x = normed_x + bias_x\n    return biased\n\n\n  Args:\n    max_x_initializer:\n      initializer vector of shape [input_size] used by variable `max_x`\n    bias_x_initializer:\n      initializer vector of shape [input_size] used by parameter `bias_x`\n    is_training:\n      Are we training the layer to learn the normalization maximas.\n      If set to True, max_x will be able to learn. This is independent of bias_x\n    epsilon:\n      The minimum value used for max_x. Defaults to 1E-5.\n    use_bias:\n      Default True. Set to False to not use a bias term.\n\n  Returns:\n    A layer representing the output of the sparse_max_norm transformation.\n   '
	def __init__(A,input_size=_A,max_x_initializer=_A,bias_x_initializer=_A,is_training=_B,epsilon=1e-05,use_bias=_B,**E):
		D=use_bias;B=bias_x_initializer;C=max_x_initializer;super(SparseMaxNorm,A).__init__(**E)
		if input_size:raise ValueError('input_size is deprecated - it is now automatically                        inferred from your input.')
		if C is _A:C=tf.zeros_initializer()
		A.max_x_initializer=C;A._use_bias=D
		if D:
			if B is _A:B=tf.zeros_initializer()
			A.bias_x_initializer=B
		A.epsilon=epsilon;A.is_training=is_training
	def build(A,input_shape):
		'Creates the max_x and bias_x tf.Variables of the layer.';B=input_shape;A.max_x=A.add_variable('max_x',initializer=A.max_x_initializer,shape=[B[1]],dtype=tf.float32,trainable=False)
		if A._use_bias:A.bias_x=A.add_variable('bias_x',initializer=A.bias_x_initializer,shape=[B[1]],dtype=tf.float32,trainable=_B)
		A.built=_B
	def compute_output_shape(A,input_shape):'Computes the output shape of the layer given the input shape.\n\n    Args:\n      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not\n        be fully defined (e.g. the batch size may be unknown).\n\n    Raises NotImplementedError.\n\n    ';raise NotImplementedError
	def _call(A,inputs,**H):
		'\n    The forward propagation logic of the layer lives here.\n\n    Arguments:\n      sparse_input:\n        A 2D ``tf.SparseTensor`` of dense_shape ``[batch_size, input_size]``\n    Returns:\n       A ``tf.SparseTensor`` representing the output of the max_norm transformation, this can\n       be fed into twml.layers.FullSparse in order to be transformed into a ``tf.Tensor``.\n    ';B=inputs
		if isinstance(B,twml.SparseTensor):B=B.to_tf()
		elif not isinstance(B,tf.SparseTensor):raise TypeError('The inputs must be of type tf.SparseTensor or twml.SparseTensor')
		D=B.indices[:,1];E=B.values
		if A.is_training is False:C=OPLIB.sparse_max_norm_inference(A.max_x,D,E,A.epsilon);F=tf.no_op()
		else:G,C=OPLIB.sparse_max_norm_training(A.max_x,D,E,A.epsilon);F=tf.assign(A.max_x,G)
		with tf.control_dependencies([F]):C=tf.stop_gradient(C)
		if A._use_bias:C=C+tf.gather(A.bias_x,D)
		return tf.SparseTensor(B.indices,C,B.dense_shape)
	def call(A,inputs,**B):
		'\n    The forward propagation logic of the layer lives here.\n\n    Arguments:\n      sparse_input:\n        A 2D ``tf.SparseTensor`` of dense_shape ``[batch_size, input_size]``\n    Returns:\n       A ``tf.SparseTensor`` representing the output of the max_norm transformation, this can\n       be fed into twml.layers.FullSparse in order to be transformed into a ``tf.Tensor``.\n    '
		with tf.device(A.max_x.device):return A._call(inputs,**B)
MaxNorm=SparseMaxNorm
def sparse_max_norm(inputs,input_size=_A,max_x_initializer=_A,bias_x_initializer=_A,is_training=_B,epsilon=1e-05,use_bias=_B,name=_A,reuse=_A):
	'\n  Functional inteface to SparseMaxNorm.\n\n  Args:\n    inputs:\n      A sparse tensor (can be twml.SparseTensor or tf.SparseTensor)\n    input_size:\n      number of input units\n    max_x_initializer:\n      initializer vector of shape [input_size] used by variable `max_x`\n    bias_x_initializer:\n      initializer vector of shape [input_size] used by parameter `bias_x`\n    is_training:\n      Are we training the layer to learn the normalization maximas.\n      If set to True, max_x will be able to learn. This is independent of bias_x\n    epsilon:\n      The minimum value used for max_x. Defaults to 1E-5.\n    use_bias:\n      Default True. Set to False to not use a bias term.\n\n  Returns:\n    Output after normalizing with the max value.\n   ';A=inputs
	if input_size:raise ValueError('input_size is deprecated - it is now automatically                      inferred from your input.')
	if isinstance(A,twml.SparseTensor):A=A.to_tf()
	B=SparseMaxNorm(max_x_initializer=max_x_initializer,bias_x_initializer=bias_x_initializer,is_training=is_training,epsilon=epsilon,use_bias=use_bias,name=name,_scope=name,_reuse=reuse);return B(A)