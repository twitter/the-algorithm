'\nImplementing Writer Layer\n'
from .layer import Layer
import libtwml
class BatchPredictionTensorWriter(Layer):
	'\n  A layer that packages keys and dense tensors into a BatchPredictionResponse.\n  Typically used at the out of an exported model for use in a the PredictionEngine\n  (that is, in production) when model predictions are dense tensors.\n\n  Arguments:\n      keys:\n        keys to hashmap\n  Output:\n      output:\n        a BatchPredictionResponse serialized using Thrift into a uint8 tensor.\n   '
	def __init__(A,keys,**B):super(BatchPredictionTensorWriter,A).__init__(**B);A.keys=keys
	def compute_output_shape(A,input_shape):'Computes the output shape of the layer given the input shape.\n\n    Args:\n      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not\n        be fully defined (e.g. the batch size may be unknown).\n\n    Raise NotImplementedError.\n\n    ';raise NotImplementedError
	def call(A,values,**C):'The logic of the layer lives here.\n\n    Arguments:\n      values:\n        dense tensors corresponding to keys in hashmap\n\n    Returns:\n      The output from the layer\n    ';B=libtwml.ops.batch_prediction_tensor_response_writer(A.keys,values);return B