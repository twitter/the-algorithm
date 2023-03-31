from twml.contrib.pruning import apply_mask
from twml.layers import Layer
class MaskLayer(Layer):
	'\n  This layer corresponds to `twml.contrib.pruning.apply_mask`.\n\n  It applies a binary mask to mask out channels of a given tensor. The masks can be\n  optimized using `twml.contrib.trainers.PruningDataRecordTrainer`.\n  '
	def call(A,inputs,**B):'\n    Applies a binary mask to the channels of the input.\n\n    Arguments:\n      inputs:\n        input tensor\n      **kwargs:\n        additional keyword arguments\n\n    Returns:\n      Masked tensor\n    ';return apply_mask(inputs)
	def compute_output_shape(A,input_shape):return input_shape