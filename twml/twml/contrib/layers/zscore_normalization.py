'\nContains the twml.layers.ZscoreNormalization layer.\n'
_C=False
_B=True
_A=None
from twml.layers.layer import Layer
import tensorflow.compat.v1 as tf
from tensorflow.python.training import moving_averages
def _add_model_variable(var):
	'Adds a variable to the `GraphKeys.MODEL_VARIABLES` collection.\n  Args:\n    var: a variable.\n  '
	if var not in tf.get_collection(tf.GraphKeys.MODEL_VARIABLES):tf.add_to_collection(tf.GraphKeys.MODEL_VARIABLES,var)
def update_moving_variable(batch_var,moving_var,decay,zero_debias=_B,name=_A):
	A=moving_var;B=moving_averages.assign_moving_average(A,batch_var,decay,zero_debias=zero_debias,name=_A);_add_model_variable(A)
	with tf.control_dependencies([B]):return tf.identity(A)
class ZscoreNormalization(Layer):
	'\n  Perform z-score normalization using moving mean and std.\n  Missing values are not included during mean/std calculation\n  This layer should only be used right after input layer.\n\n  Args:\n    decay:\n      using large decay to include longer moving means.\n    data_type:\n      use float64 to prevent overflow during variance calculation.\n    name:\n      Layer name\n  Returns:\n    A layer representing the output of the ZscoreNormalization transformation.\n   '
	def __init__(A,decay=0.9999,data_type=tf.float64,name=_A,**C):B=data_type;super(ZscoreNormalization,A).__init__(name=name,**C);A.epsilon=tf.constant(1.0,B);A.decay=decay;A.data_type=B
	def build(A,input_shape):'Creates the moving_mean and moving_var tf.Variables of the layer.';B=input_shape[1];A.moving_mean=A.add_variable('{}_mean/EMA'.format(A.name),initializer=tf.constant_initializer(),shape=[B],dtype=A.data_type,trainable=_C);A.moving_var=A.add_variable('{}_variance/EMA'.format(A.name),initializer=tf.constant_initializer(),shape=[B],dtype=A.data_type,trainable=_C);A.built=_B
	def compute_output_shape(A,input_shape):'Computes the output shape of the layer given the input shape.\n\n    Args:\n      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not\n        be fully defined (e.g. the batch size may be unknown).\n\n    ';return input_shape
	def _training_pass(A,input,dense_mask,input_dtype,handle_single,zero_debias):
		G=zero_debias;H=input_dtype;D=dense_mask;M=A.epsilon;N,O=A.moving_mean,A.moving_var;E=tf.reduce_sum(tf.cast(D,A.data_type),axis=0);P=tf.cast(E,tf.bool);I=tf.fill(tf.shape(E),M);J=tf.where(P,E,I);Q=tf.expand_dims(J,0);R=input/Q;S=tf.reduce_sum(R,axis=0);T=update_moving_variable(S,N,A.decay,G,name='mean_ema_op');C=input-tf.expand_dims(T,0);C=tf.where(D,C,tf.zeros_like(C));U=tf.expand_dims(tf.sqrt(J),0);V=C/U;W=tf.square(V);X=tf.reduce_sum(W,axis=0);K=update_moving_variable(X,O,A.decay,G,name='var_ema_op');L=tf.sqrt(K);Y=tf.where(tf.equal(L,0),I,L);B=C/tf.expand_dims(Y,0)
		if handle_single:F=tf.math.equal(K,0);F=tf.expand_dims(F,0);B=tf.where(tf.math.logical_and(D,F),tf.ones_like(B),B)
		if H!=A.data_type:B=tf.cast(B,H)
		return B
	def _infer_pass(D,input,dense_mask,input_dtype,handle_single):
		F=dense_mask;E=input_dtype;G=tf.cast(D.epsilon,E);H=tf.cast(D.moving_mean,E);A=tf.cast(tf.sqrt(D.moving_var),E);I=tf.expand_dims(H,0);B=input-I;B=tf.where(F,B,tf.zeros_like(B));J=tf.where(tf.equal(A,0),tf.fill(tf.shape(A),G),A);C=B/tf.expand_dims(J,0)
		if handle_single:K=tf.expand_dims(A,0);L=tf.math.logical_not(tf.cast(K,tf.bool));C=tf.where(tf.math.logical_and(F,L),tf.ones_like(C),C)
		return C
	def call(A,input,is_training,dense_mask=_A,zero_debias=_B,handle_single=_C):
		'\n    Args:\n    -----------\n    input:  B x D : float32/float64\n      missing value must be set to 0.\n    is_training: bool\n      training phase or testing phase\n    dense_mask: B x D : bool\n      missing value should be marked as 0, non-missing as 1. same shape as input\n    zero_debias: bool\n      bias correction of the moving average. (biased towards 0 in the beginning.\n      see adam paper. https://arxiv.org/abs/1412.6980)\n    handle_single: bool\n      if std==0, and feature is not missing value, set the value to 1, instead of 0.\n      This is super rare if input only consists of continous feature.\n      But if one-hot feature is included,\n      they will all have same values 1, in that case, make sure to set handle_single to true.\n    ';D=handle_single;B=dense_mask
		if B is _A:B=tf.math.logical_not(tf.equal(input,0))
		C=input.dtype
		if is_training:
			if C!=A.data_type:input=tf.cast(input,A.data_type)
			return A._training_pass(input,B,C,D,zero_debias)
		else:return A._infer_pass(input,B,C,D)
def zscore_normalization(input,is_training,decay=0.9999,data_type=tf.float64,name=_A,dense_mask=_A,zero_debias=_B,handle_single=_C,**A):'\n  Args:\n  ------------\n  input:  B x D : float32/float64\n    missing value must be set to 0.\n  is_training: bool\n    training phase or testing phase\n  decay:\n    using large decay to include longer moving means.\n  data_type:\n    use float64 to zprevent overflow during variance calculation.\n  name:\n    Layer name\n  dense_mask: B x D : bool\n    missing value should be marked as 0, non-missing as 1. same shape as input\n  zero_debias: bool\n    bias correction of the moving average. (biased towards 0 in the beginning.\n    see adam paper. https://arxiv.org/abs/1412.6980)\n  handle_single: bool\n    if std==0, and feature is not missing value, set the value to 1, instead of 0.\n    This is super rare if input only consists of continous feature.\n    But if one-hot feature is included,\n    they will all have same values 1, in that case, make sure to set handle_single to true.\n  ';B=ZscoreNormalization(decay=decay,data_type=data_type,name=name,**A);return B(input,is_training,dense_mask=dense_mask,zero_debias=zero_debias,handle_single=handle_single)