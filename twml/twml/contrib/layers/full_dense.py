'\nImplementing Full Dense Layer\n'
_B=True
_A=None
from twml.layers import Layer
import tensorflow.compat.v1 as tf
from tensorflow.python.layers import core
class FullDense(Layer):
	'\n  Full-connected, Dense input layer class.\n  This layer implements the operation:\n\n  .. code-block:: python\n\n    outputs = activation(inputs.weight + bias)\n\n  Where ``activation`` is the activation function passed as the ``activation``\n  argument (if not ``None``), ``weight`` is a weights matrix created by the layer,\n  and ``bias`` is a bias vector created by the layer.\n\n  However, this layer breaks up ``weight`` into ``num_partitions`` parts,\n  for the purpose of even disribution of weights across parameter servers\n  for distributed training.\n\n  Note - This layer is created to allow distributed training optimizations,\n  but can also be used for single node training (e.g. hogwild) without\n  code modification\n\n  Arguments:\n    output_size:\n      Integer or Long, dimensionality of the output space.\n    weight_initializer:\n      Initializer function for the weight matrix.\n    weight_regularizer:\n      Regularizer function for the weight matrix.\n      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.\n    weight_constraint:\n      An optional projection function to be applied to the\n      weight after being updated by an `Optimizer` (e.g. used to implement\n      norm constraints or value constraints for layer weights). The function\n      must take as input the unprojected variable and must return the\n      projected variable (which must have the same shape). Constraints are\n      not safe to use when doing asynchronous distributed training.\n    bias_constraint:\n      An optional projection function to be applied to the\n      bias after being updated by an `Optimizer`.\n    num_partitions:\n      Number of pieces to partition the weights into. This layer does\n      column partitioning of the weights, which is equivalent to\n      processing the input tensor with multiple fully connected layers\n      of smaller output size, and then concatenating these outputs\n    activation:\n      Activation function (callable). Set it to None to maintain a linear activation.\n    use_bias:\n      Boolean whether to include a bias parameter in the layer\n    bias_initializer:\n      Initializer function for the bias.\n    bias_regularizer:\n      Regularizer function for the bias.\n      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.\n    activity_regularizer:\n      Regularizer function for the output.\n    trainable:\n      Boolean, if `True` also add variables to the graph collection\n      ``GraphKeys.TRAINABLE_VARIABLES`` (see `tf.Variable\n      <https://www.tensorflow.org/versions/master/api_docs/python/tf/Variable>`_).\n    name:\n      String, the name of the layer. Layers with the same name will\n      share weights, but to avoid mistakes we require ``reuse=True`` in such cases.\n\n  Properties:\n    output_size:\n      Python integer, dimensionality of the output space.\n    activation:\n      Activation function (callable).\n    weight_initializer:\n      Initializer instance (or name) for the weight matrix.\n    bias_initializer:\n      Initializer instance (or name) for the bias.\n    weights:\n      list of underlying weight and bias matrix components. no guarantee on order of elements\n    weight_regularizer:\n      Regularizer instance for the weight matrix (callable)\n    bias_regularizer:\n      Regularizer instance for the bias (callable).\n    activity_regularizer:\n      Regularizer instance for the output (callable)\n    weight_constraint:\n      Constraint function for the weight matrix.\n    bias_constraint:\n      Constraint function for the bias.\n  '
	def __init__(A,output_size,weight_initializer=_A,weight_regularizer=_A,weight_constraint=_A,bias_constraint=_A,num_partitions=3,activation=_A,use_bias=_B,bias_initializer=tf.zeros_initializer(),bias_regularizer=_A,activity_regularizer=_A,trainable=_B,name=_A,**B):C=trainable;D=bias_regularizer;E=bias_initializer;F=use_bias;G=activation;H=bias_constraint;I=weight_constraint;J=weight_regularizer;K=weight_initializer;L=output_size;super(FullDense,A).__init__(trainable=C,name=name,**B);A._output_sizes=A._get_output_partition_sizes(L,num_partitions);A._units=L;A._activation=G;A._weight_initializer=K;A._bias_initializer=E;A._weight_regularizer=J;A._bias_regularizer=D;A._weight_constraint=I;A._bias_constraint=H;A._use_bias=F;A._parts=[core.Dense(units=A,activation=G,use_bias=F,kernel_initializer=K,bias_initializer=E,kernel_regularizer=J,bias_regularizer=D,activity_regularizer=activity_regularizer,kernel_constraint=I,bias_constraint=H,trainable=C,name=name,**B)for A in A._output_sizes]
	@staticmethod
	def _get_output_partition_sizes(out_size,num_parts):' Returns the appropriate output sizes of the partitions ';A=num_parts;B=[out_size*B//A for B in range(A+1)];return[B-A for(A,B)in zip(B[:],B[1:])]
	def build(C,input_shapes):
		' Create the appropriately sized weights and biases in each layer partition ';A=input_shapes
		if isinstance(A,(list,tuple)):
			B=A[0];D=_B
			for E in A[1:]:D&=B.is_compatible_with(E)
			if not D:raise ValueError('Input shapes %s are not compatible.'%A)
		else:B=A
		for F in C._parts:F.build(B)
		C.built=_B
	@property
	def units(self):' Returns the number of output units of the layer ';return self._units
	@property
	def output_size(self):' Returns the number of output units of the layer ';return self._units
	@property
	def activation(self):' Returns the activation function ';return self._activation
	@property
	def weight_initializer(self):' Returns the weight_initializer ';return self._weight_initializer
	@property
	def weight_regularizer(self):' Returns the weight_regularizer ';return self._weight_regularizer
	@property
	def weight_constraint(self):' Returns the weight_constraint ';return self._weight_constraint
	@property
	def bias_initializer(self):' Returns the bias_initializer ';return self._bias_initializer
	@property
	def bias_regularizer(self):' Returns the bias_regularizer ';return self._bias_regularizer
	@property
	def bias_constraint(self):' Returns the bias_constraint ';return self._bias_constraint
	@property
	def use_bias(self):' Returns whether a bias is used in the layer ';return self._use_bias
	@property
	def trainable_variables(self):
		' Returns the trainable variables of the layer ';A=[]
		for B in self._parts:A+=B.trainable_variables
		return A
	@property
	def trainable_weights(self):' Returns the trainable variables of the layer ';return self.trainable_variables
	@property
	def non_trainable_variables(self):
		' Returns the non-trainable variables of the layer ';A=[]
		for B in self._parts:A+=B.non_trainable_variables
		return A
	@property
	def non_trainable_weights(self):' Returns the non-trainable variables of the layer ';return self.non_trainable_variables
	@property
	def variables(self):
		' Returns a list of all weights and biases in this layer ';A=[]
		for B in self._parts:A+=B.weights
		return A
	@property
	def weights(self):' Returns a list of all weights and biases in this layer ';return self.variables
	@property
	def dtype(self):' Returns the dtype of the layers weights ';return self._parts[0].dtype
	def call(C,inputs,**F):
		'The logic of the layer lives here.\n\n    Arguments:\n      inputs:\n        A dense Tensor or a list of such.\n        If `inputs` is a list, all tensors must have same `dense_shape`.\n\n    Returns:\n      - If `inputs` is `SparseTensor`, then returns `bias + inputs * dense_b`.\n      - If `inputs` is a `list[SparseTensor`, then returns\n       `bias + accumulate_n([sp_a * dense_b for sp_a in inputs])`.\n    ';A=inputs
		if not isinstance(A,(list,tuple)):A=[A]
		B=[]
		for D in A:E=[A(D)for A in C._parts];B.append(tf.concat(E,axis=-1))
		return tf.accumulate_n(B)
def full_dense(inputs,output_size,weight_initializer=_A,weight_regularizer=_A,weight_constraint=_A,bias_constraint=_A,num_partitions=3,activation=_A,use_bias=_B,bias_initializer=tf.zeros_initializer(),bias_regularizer=_A,activity_regularizer=_A,trainable=_B,name=_A,reuse=_A,**B):
	'Functional interface for the fully-connected dense-input layer.\n  This layer implements the operation:\n  `outputs = activation(inputs.weight + bias)`\n  Where `activation` is the activation function passed as the `activation`\n  argument (if not `None`), `weight` is a weights matrix created by the layer,\n  and `bias` is a bias vector created by the layer\n  (only if `use_bias` is `True`).\n\n  However, this layer breaks up ``weight`` into ``num_partitions`` parts,\n  for the purpose of even disribution of weights across parameter servers\n  for distributed training.\n\n  Note - This layer is created to allow distributed training optimizations,\n  but can also be used for single node training (e.g. hogwild) without\n  code modification\n\n  Arguments:\n    inputs: Tensor input.\n    output_size: Integer or Long, dimensionality of the output space.\n    weight_initializer: Initializer function for the weight matrix.\n      If `None` (default), weights are initialized using the default\n      initializer used by `tf.get_variable`.\n    weight_regularizer:\n      Regularizer function for the weight matrix.\n      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.\n    weight_constraint:\n      An optional projection function to be applied to the\n      weight after being updated by an `Optimizer` (e.g. used to implement\n      norm constraints or value constraints for layer weights). The function\n      must take as input the unprojected variable and must return the\n      projected variable (which must have the same shape). Constraints are\n      not safe to use when doing asynchronous distributed training.\n    bias_constraint:\n      An optional projection function to be applied to the\n      bias after being updated by an `Optimizer`.\n    num_partitions:\n      Number of pieces to partition the weights into. This layer does\n      column partitioning of the weights, which is equivalent to\n      processing the input tensor with multiple fully connected layers\n      of smaller output size, and then concatenating these outputs\n    activation: Activation function (callable). Set it to None to maintain a\n      linear activation.\n    use_bias: Boolean, whether the layer uses a bias.\n    bias_initializer:\n      Initializer function for the bias.\n    bias_regularizer:\n      Regularizer function for the bias.\n      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.\n    activity_regularizer:\n      Regularizer function for the output.\n    trainable:\n      Boolean, if `True` also add variables to the graph collection\n      `GraphKeys.TRAINABLE_VARIABLES` (see `tf.Variable`).\n    name:\n      String, the name of the layer.\n    reuse:\n      Boolean, whether to reuse the weights of a previous layer\n      by the same name.\n\n  Returns:\n    Output tensor with shape `inputs.shape[:-1] + [output_size]`.\n  ';A=inputs
	if not isinstance(A,(list,tuple)):A=[A]
	C=A[0].dtype.base_dtype;D=FullDense(output_size=output_size,weight_initializer=weight_initializer,weight_regularizer=weight_regularizer,weight_constraint=weight_constraint,bias_constraint=bias_constraint,num_partitions=num_partitions,activation=activation,use_bias=use_bias,bias_initializer=bias_initializer,bias_regularizer=bias_regularizer,activity_regularizer=activity_regularizer,trainable=trainable,name=name,dtype=C,_scope=name,_reuse=reuse,**B);return D(A)