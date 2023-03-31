'\nImplementing a base layer for twml\n'
import tensorflow.compat.v1 as tf
from tensorflow.python.layers import base
class Layer(base.Layer):
	'\n  Base Layer implementation for twml.\n  Overloads `twml.layers.Layer\n  <https://www.tensorflow.org/versions/master/api_docs/python/tf/layers/Layer>`_\n  from tensorflow and adds a couple of custom methods.\n  '
	@property
	def init(self):'\n    Return initializer ops. By default returns tf.no_op().\n    This method is overwritten by classes like twml.layers.MDL, which\n    uses a HashTable internally, that must be initialized with its own op.\n    ';return tf.no_op()
	def call(A,inputs,**B):'The logic of the layer lives here.\n\n    Arguments:\n      inputs:\n        input tensor(s).\n      **kwargs:\n        additional keyword arguments.\n\n    Returns:\n      Output tensor(s).\n    ';raise NotImplementedError
	def compute_output_shape(A,input_shape):'Computes the output shape of the layer given the input shape.\n\n    Args:\n      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not\n        be fully defined (e.g. the batch size may be unknown).\n\n    Raise NotImplementedError.\n\n    ';raise NotImplementedError