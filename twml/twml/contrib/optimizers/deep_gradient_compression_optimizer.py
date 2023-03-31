'\nA custom optimizer to implement Deep Gradient Compression. The general idea of\ngradient compression is to compress the gradients exchanged across machines,\nin order to reduce the communication overhead of distributing computing efforts.\nMore details in https://arxiv.org/abs/1712.01887\n'
_C='g_buffer'
_B=None
_A=False
import tensorflow.compat.v1 as tf
def compute_threshold(grad,density):'\n  A utility function to compute the threshold for gradient sparsification, given the gradient\n  tensor and the density.\n  Args:\n    grad(tf.Tensor):\n      Gradient tensor for some variable.\n    density(float):\n      Density degree when sparsifying gradients.\n  Returns(float):\n    Threshold for gradient sparsification.\n  ';B=tf.reshape(grad,[-1]);A=tf.abs(B);C=tf.shape(A)[0];D=tf.maximum(tf.constant(1),tf.cast(tf.scalar_mul(density,tf.cast(C,tf.float32)),tf.int32));E,F=tf.nn.top_k(A,D,_A);return E[-1]
def get_top_row_indices(values,density):
	'\n  A utility function to get indices of most significant rows, given the density degree.\n  Args:\n    values(tf.Tensor):\n      Gradient or locally accumulated gradient for some variable.\n    density(float):\n      Density degree when filtering out rows.\n  Returns(list(int)):\n    Indices of most significant rows.\n  ';A=values;B=tf.abs(A)
	try:C=tf.shape(B)[0];D=tf.maximum(tf.constant(1),tf.cast(tf.scalar_mul(density,tf.cast(C,tf.float32)),tf.int32));E=tf.squeeze(tf.reduce_sum(A,axis=1,keepdims=True));G,F=tf.nn.top_k(E,k=D,sorted=_A);return F
	except ValueError:return _B
class DeepGradientCompressionOptimizer(tf.train.GradientDescentOptimizer):
	'\n  A custom optimizer to implement Deep Gradient Compression (https://arxiv.org/abs/1712.01887).\n  '
	def __init__(A,learning_rate,use_locking=_A,name='Sparse',density=1.0,density_decay=_A,density_decay_steps=10000,density_decay_rate=0.5,min_density=0.1,accumulation=_A):super(DeepGradientCompressionOptimizer,A).__init__(learning_rate,use_locking,name);A._initial_density_t=tf.convert_to_tensor(density);A._density_decay=density_decay;B=A._initial_density_t.dtype;A._density_decay_steps_t=tf.convert_to_tensor(density_decay_steps,B);A._density_decay_rate_t=tf.convert_to_tensor(density_decay_rate,B);A._min_density_t=tf.convert_to_tensor(min_density,B);A._accumulation=accumulation
	def _prepare(A):
		super(DeepGradientCompressionOptimizer,A)._prepare()
		if not A._density_decay:A._density_t=A._initial_density_t
		else:B=A._initial_density_t.dtype;C=tf.cast(tf.train.get_global_step(),B);D=tf.floor(tf.divide(C,A._density_decay_steps_t));E=tf.multiply(A._initial_density_t,tf.pow(A._density_decay_rate_t,D));A._density_t=tf.maximum(A._min_density_t,E)
	def _create_slots(A,var_list):
		'\n    Create a slot variable to accumulate gradients locally for each variable in `var_list`.\n    Args:\n      var_list(list(tf.Variable)):\n        List of variables to accumulate gradients locally for.\n    '
		for B in var_list:A._zeros_slot(B,_C,A._name)
	def _apply_dense(A,grad,var):
		C=var;D=grad
		if not A._accumulation:
			B=get_top_row_indices(D,A._density_t)
			if B is _B:return super(DeepGradientCompressionOptimizer,A)._apply_dense(D,C)
			F=tf.gather(D,B);G=B;H=tf.IndexedSlices(F,G);return super(DeepGradientCompressionOptimizer,A)._apply_sparse_duplicate_indices(H,C)
		else:
			E=A.get_slot(C,_C);E=tf.assign_add(E,D);B=get_top_row_indices(E,A._density_t)
			if B is _B:return super(DeepGradientCompressionOptimizer,A)._apply_dense(D,C)
			F=tf.gather(E,B);G=B;H=tf.IndexedSlices(F,G);I=super(DeepGradientCompressionOptimizer,A)._apply_sparse_duplicate_indices(H,C);J=tf.scatter_update(E,G,tf.zeros_like(F));return tf.group(*[I,J])
	def _apply_sparse_duplicate_indices(A,grad,var):
		D=var;B=grad
		if not A._accumulation:
			C=get_top_row_indices(B.values,A._density_t)
			if C is _B:return super(DeepGradientCompressionOptimizer,A)._apply_sparse_duplicate_indices(B,D)
			F=tf.gather(B.values,C);G=tf.gather(B.indices,C);H=tf.IndexedSlices(F,G);return super(DeepGradientCompressionOptimizer,A)._apply_sparse_duplicate_indices(H,D)
		else:
			E=A.get_slot(D,_C);E=tf.scatter_update(E,B.indices,B.values);C=get_top_row_indices(E,A._density_t)
			if C is _B:return super(DeepGradientCompressionOptimizer,A)._apply_sparse_duplicate_indices(B,D)
			F=tf.gather(E,C);G=C;H=tf.IndexedSlices(F,G);I=super(DeepGradientCompressionOptimizer,A)._apply_sparse_duplicate_indices(H,D);J=tf.scatter_update(E,G,tf.zeros_like(F));return tf.group(*[I,J])