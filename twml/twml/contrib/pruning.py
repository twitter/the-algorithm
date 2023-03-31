'\nThis module implements tools for pruning neural networks.\n\nIn particular, it provides tools for dealing with masks:\n\n  features = apply_mask(features)\n\nThe function `apply_mask` applies a binary mask to the channels of a given tensor. Consider the\nfollowing loss:\n\n  logits = tf.matmul(features, weights)\n  loss = tf.losses.sparse_softmax_cross_entropy(labels, logits)\n\nEach mask has a corresponding pruning signal. The function `update_pruning_signals` will update and\nreturn these signals:\n\n  signals = update_pruning_signals(loss)\n\nThe pruning operation will zero out the mask entry with the smallest corresponding pruning signal:\n\n  prune(signals)\n\nThe following function allows us to estimate the computational cost of a graph (number of FLOPs):\n\n  cost = computational_cost(loss)\n\nTo compute the cost of each feature per data point, we can do:\n\n  costs = tf.gradients(cost / batch_size, masks)\n\nThe current implementation of `computational_cost` is designed to work with standard feed-forward\nand convolutional network architectures only, but may fail with more complicated architectures.\n'
_H='pruning_opt'
_G='Fisher'
_F='Conv3D'
_E='Conv1D'
_D='Conv2D'
_C='MatMul'
_B=True
_A=None
import numpy as np,tensorflow.compat.v1 as tf
MASK_COLLECTION='pruning/masks'
MASK_EXTENDED_COLLECTION='pruning/masks_extended'
OP_COLLECTION='pruning/ops'
def apply_mask(tensor,name='pruning'):
	'\n  Point-wise multiplies a tensor with a binary mask.\n\n  During training, pruning is simulated by setting entries of the mask to zero.\n\n  Arguments:\n    tensor: tf.Tensor\n      A tensor where the last dimension represents channels which will be masked\n\n  Returns:\n    `tf.Tensor` with same shape as `tensor`\n  ';C=False;A=tensor;E=A.shape
	with tf.variable_scope(name,reuse=_B):B=tf.Variable(tf.ones(A.shape.as_list()[-1]),trainable=C,name='mask');F=tf.Variable(tf.zeros_like(B),trainable=C,name='signal');D=extend_mask(B,A)
	B.extended=D;B.pruning_signal=F;B.tensor=A;A=tf.multiply(A,D);A.set_shape(E);A._mask=B;tf.add_to_collection(MASK_COLLECTION,B);tf.add_to_collection(MASK_EXTENDED_COLLECTION,B.extended);tf.add_to_collection(OP_COLLECTION,A.op);return A
def extend_mask(mask,tensor):'\n  Repeats the mask for each data point stored in a tensor.\n\n  If `tensor` is AxBxC dimensional and `mask` is C dimensional, returns an Ax1xC dimensional\n  tensor with A copies or `mask`.\n\n  Arguments:\n    mask: tf.Tensor\n      The mask which will be extended\n\n    tensor: tf.Tensor\n      The tensor to which the extended mask will be applied\n\n  Returns:\n    The extended mask\n  ';A=tensor;B=tf.shape(A)[:1];C=tf.ones([tf.rank(A)-1],dtype=B.dtype);D=tf.concat([B,C],0);E=tf.concat([C,[-1]],0);return tf.tile(tf.reshape(mask,E),D)
def find_input_mask(tensor):
	'\n  Find ancestral mask affecting the number of pruned channels of a tensor.\n\n  Arguments:\n    tensor: tf.Tensor\n      Tensor for which to identify relevant mask\n\n  Returns:\n    A `tf.Tensor` or `None`\n  ';A=tensor
	if hasattr(A,'_mask'):return A._mask
	if A.op.type in[_C,_E,_D,_F,'Transpose']:return _A
	if not A.op.inputs:return _A
	for input in A.op.inputs:
		B=find_input_mask(input)
		if B is not _A:return B
def find_output_mask(tensor):
	"\n  Find mask applied to the tensor or one of its descendants if it affects the tensor's pruned shape.\n\n  Arguments:\n    tensor: tf.Tensor or tf.Variable\n      Tensor for which to identify relevant mask\n\n  Returns:\n    A `tf.Tensor` or `None`\n  ";A=tensor
	if isinstance(A,tf.Variable):return find_output_mask(A.op.outputs[0])
	if hasattr(A,'_mask'):return A._mask
	for B in A.consumers():
		if len(B.outputs)!=1:continue
		if B.type in[_C,_E,_D,_F]:
			if A==B.inputs[1]:return find_output_mask(B.outputs[0])
			return _A
		C=find_output_mask(B.outputs[0])
		if C is not _A:return C
def find_mask(tensor):
	'\n  Returns masks indicating channels of the tensor that are effectively removed from the graph.\n\n  Arguments:\n    tensor: tf.Tensor\n      Tensor for which to compute a mask\n\n  Returns:\n    A `tf.Tensor` with binary entries indicating disabled channels\n  ';C=tensor;A=find_input_mask(C);B=find_output_mask(C)
	if A is _A:return B
	if B is _A:return A
	if A is B:return A
	return A*B
def pruned_shape(tensor):
	'\n  Computes the shape of a tensor after taking into account pruning of channels.\n\n  Note that the shape will only differ in the last dimension, even if other dimensions are also\n  effectively disabled by pruning masks.\n\n  Arguments:\n    tensor: tf.Tensor\n      Tensor for which to compute a pruned shape\n\n  Returns:\n    A `tf.Tensor[tf.float32]` representing the pruned shape\n  ';A=tensor;B=find_mask(A)
	if B is _A:return tf.cast(tf.shape(A),tf.float32)
	return tf.concat([tf.cast(tf.shape(A)[:-1],B.dtype),tf.reduce_sum(B,keepdims=_B)],0)
def computational_cost(op_or_tensor,_observed=_A):
	'\n  Estimates the computational complexity of a pruned graph (number of floating point operations).\n\n  This function currently only supports sequential graphs such as those of MLPs and\n  simple CNNs with 2D convolutions in NHWC format.\n\n  Note that the computational cost returned by this function is proportional to batch size.\n\n  Arguments:\n    op_or_tensor: tf.Tensor or tf.Operation\n      Root node of graph for which to compute computational cost\n\n  Returns:\n    A `tf.Tensor` representing a number of floating point operations\n  ';D=op_or_tensor;C=_observed;B=tf.constant(0.0);F=[A.extended for A in tf.get_collection(MASK_COLLECTION)]
	if D in F:return B
	A=D.op if isinstance(D,(tf.Tensor,tf.Variable))else D
	if C is _A:C=[]
	elif A in C:return B
	C.append(A)
	for G in A.inputs:B=B+computational_cost(G,C)
	if A.op_def is _A or A in tf.get_collection(OP_COLLECTION):return B
	elif A.op_def.name==_C:E=pruned_shape(A.inputs[0]);H=pruned_shape(A.inputs[1]);return B+E[0]*H[1]*(2.0*E[1]-1.0)
	elif A.op_def.name in['Add','Mul','BiasAdd']:return B+tf.cond(tf.size(A.inputs[0])>tf.size(A.inputs[1]),lambda:tf.reduce_prod(pruned_shape(A.inputs[0])),lambda:tf.reduce_prod(pruned_shape(A.inputs[1])))
	elif A.op_def.name in[_D]:I=pruned_shape(A.outputs[0]);J=pruned_shape(A.inputs[0]);K=pruned_shape(A.inputs[1]);L=tf.reduce_prod(K[:2])*J[-1]*2.0-1.0;return B+tf.reduce_prod(I)*L
	return B
def update_pruning_signals(loss,decay=0.96,masks=_A,method=_G):
	"\n  For each mask, computes corresponding pruning signals indicating the importance of a feature.\n\n  Arguments:\n    loss: tf.Tensor\n      Any cross-entropy loss\n\n    decay: float\n      Controls exponential moving average of pruning signals\n\n    method: str\n      Method used to compute pruning signal (currently only supports 'Fisher')\n\n  Returns:\n    A `list[tf.Tensor]` of pruning signals corresponding to masks\n\n  References:\n    * Theis et al., Faster gaze prediction with dense networks and Fisher pruning, 2018\n  ";C=method;D=decay;A=masks
	if A is _A:A=tf.get_collection(MASK_COLLECTION)
	if C not in[_G]:raise ValueError("Pruning method '{0}' not supported.".format(C))
	if not A:return[]
	with tf.variable_scope(_H,reuse=_B):E=tf.gradients(loss,[A.extended for A in A]);F=[tf.squeeze(tf.reduce_mean(tf.square(A),0))for A in E];B=[A.pruning_signal for A in A];B=[tf.assign(A,D*A+(1.0-D)*B,use_locking=_B)for(A,B)in zip(B,F)]
	return B
def prune(signals,masks=_A):
	'\n  Prunes a single feature by zeroing the mask entry with the smallest pruning signal.\n\n  Arguments:\n    signals: list[tf.Tensor]\n      A list of pruning signals\n\n    masks: list[tf.Tensor]\n      A list of corresponding masks, defaults to `tf.get_collection(MASK_COLLECTION)`\n\n  Returns:\n    A `tf.Operation` which updates masks\n  ';B=signals;A=masks
	if A is _A:A=tf.get_collection(MASK_COLLECTION)
	with tf.variable_scope(_H,reuse=_B):
		B=[tf.where(B>0.5,A,tf.zeros_like(A)+np.inf)for(B,A)in zip(A,B)];E=[tf.argmin(A)for A in B];G=[A[B]for(A,B)in zip(B,E)];H=tf.argmin(G);C=[]
		for (D,F) in enumerate(E):C.append(tf.cond(tf.equal(H,D),lambda:tf.scatter_update(A[D],tf.Print(F,[F],message='Pruning layer [{0}] at index '.format(D)),0.0),lambda:A[D]))
		C=tf.group(C,name='prune')
	return C