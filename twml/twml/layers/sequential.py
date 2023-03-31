'\nImplementing Sequential Layer container\n'
from .layer import Layer
from tensorflow import keras
from tensorflow.python.layers import base
class Sequential(Layer):
	'\n  A sequential stack of layers.\n\n  Arguments:\n      layers: list of layers to add to the model.\n\n  Output:\n      the output of the sequential layers\n   '
	def __init__(A,layers=None,**C):
		B=layers;A._layers=[];A._layer_names=[];A._layer_outputs=[]
		if B:
			for D in B:A.add(D)
		super(Sequential,A).__init__(**C)
	def add(B,layer):
		'Adds a layer instance on top of the layer stack.\n\n    Arguments:\n      layer:\n        layer instance.\n\n    Raises:\n      TypeError:\n        if the layer argument is not instance of base.Layer\n    ';A=layer
		if not isinstance(A,base.Layer)and not isinstance(A,keras.layers.Layer):raise TypeError('The added layer must be an instance of class Layer')
		if A.name in B._layer_names:raise ValueError('Layer with name %s already exists in sequential layer'%A.name)
		B._layers.append(A);B._layer_names.append(A.name)
	def pop(A):
		'Removes the last layer in the model.\n\n    Raises:\n      TypeError:\n        if there are no layers in the model.\n    '
		if not A._layers or not A._layer_names:raise TypeError('There are no layers in the model.')
		A._layers.pop();A._layer_names.pop()
	def call(B,inputs,**D):
		'The logic of the layer lives here.\n\n    Arguments:\n      inputs:\n        input tensor(s).\n\n    Returns:\n      The output of the sequential layers\n    ';A=inputs;B._layer_outputs=[]
		for C in B._layers:A=C(A);B._layer_outputs.append(A)
		return A
	@property
	def layers(self):' Return the layers in the sequential layer ';return self._layers
	@property
	def layer_names(self):' Return the layer names in the sequential layer ';return self._layer_names
	@property
	def layer_outputs(self):' Return the layer outputs in the sequential layer ';return self._layer_outputs
	def get(A,key):'Retrieves the n-th layer.\n\n    Arguments:\n      key:\n        index of the layer\n\n    Output:\n      The n-th layer where n is equal to the key.\n    ';return A._layers[key]
	def get_output(A,key):'Retrieves the n-th layer output.\n\n    Arguments:\n      key:\n        index of the layer\n\n    Output:\n      The intermediary output equivalent to the nth layer, where n is equal to the key.\n    ';return A._layer_outputs[key]
	def get_layer_by_name(A,name):'Retrieves the layer corresponding to the name.\n\n    Arguments:\n      name:\n        name of the layer\n\n    Output:\n      list of layers that have the name desired\n    ';return A._layers[A._layer_names.index(name)]
	def get_layer_output_by_name(A,name):'Retrieves the layer output corresponding to the name.\n\n    Arguments:\n      name:\n        name of the layer\n\n    Output:\n      list of the output of the layers that have the desired name\n    ';return A._layer_outputs[A._layer_names.index(name)]
	@property
	def init(self):' returns a list of initialization ops (one per layer) ';return[A.init for A in self._layers]
	def compute_output_shape(A,input_shape):'Computes the output shape of the layer given the input shape.\n\n    Args:\n      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not\n        be fully defined (e.g. the batch size may be unknown).\n\n    Raise NotImplementedError.\n\n    ';raise NotImplementedError