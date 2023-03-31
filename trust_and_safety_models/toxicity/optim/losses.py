import tensorflow as tf
from keras.utils import tf_utils,losses_utils
from keras import backend
def inv_kl_divergence(y_true,y_pred):B=y_true;A=y_pred;A=tf.convert_to_tensor(A);B=tf.cast(B,A.dtype);B=backend.clip(B,backend.epsilon(),1);A=backend.clip(A,backend.epsilon(),1);return tf.reduce_sum(A*tf.math.log(A/B),axis=-1)
def masked_bce(y_true,y_pred):A=y_true;A=tf.cast(A,dtype=tf.float32);B=A!=-1;return tf.keras.metrics.binary_crossentropy(tf.boolean_mask(A,B),tf.boolean_mask(y_pred,B))
class LossFunctionWrapper(tf.keras.losses.Loss):
	def __init__(A,fn,reduction=losses_utils.ReductionV2.AUTO,name=None,**B):super().__init__(reduction=reduction,name=name);A.fn=fn;A._fn_kwargs=B
	def call(C,y_true,y_pred):
		A=y_pred;B=y_true
		if tf.is_tensor(A)and tf.is_tensor(B):A,B=losses_utils.squeeze_or_expand_dimensions(A,B)
		D=tf.__internal__.autograph.tf_convert(C.fn,tf.__internal__.autograph.control_status_ctx());return D(B,A,**C._fn_kwargs)
	def get_config(C):
		B={}
		for (D,A) in C._fn_kwargs.items():B[D]=backend.eval(A)if tf_utils.is_tensor_or_variable(A)else A
		E=super().get_config();return dict(list(E.items())+list(B.items()))
class InvKLD(LossFunctionWrapper):
	def __init__(A,reduction=losses_utils.ReductionV2.AUTO,name='inv_kl_divergence'):super().__init__(inv_kl_divergence,name=name,reduction=reduction)
class MaskedBCE(LossFunctionWrapper):
	def __init__(A,reduction=losses_utils.ReductionV2.AUTO,name='masked_bce'):super().__init__(masked_bce,name=name,reduction=reduction)