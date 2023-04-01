"""
This module implements tools for pruning neural networks.

In particular, it provides tools for dealing with masks:

  features = apply_mask(features)

The function `apply_mask` applies a binary mask to the channels of a given tensor. Consider the
following loss:

  logits = tf.matmul(features, weights)
  loss = tf.losses.sparse_softmax_cross_entropy(labels, logits)

Each mask has a corresponding pruning signal. The function `update_pruning_signals` will update and
return these signals:

  signals = update_pruning_signals(loss)

The pruning operation will zero out the mask entry with the smallest corresponding pruning signal:

  prune(signals)

The following function allows us to estimate the computational cost of a graph (number of FLOPs):

  cost = computational_cost(loss)

To compute the cost of each feature per data point, we can do:

  costs = tf.gradients(cost / batch_size, masks)

The current implementation of `computational_cost` is designed to work with standard feed-forward
and convolutional network architectures only, but may fail with more complicated architectures.
"""


import numpy as np
import tensorflow.compat.v1 as tf

MASK_COLLECTION = 'pruning/masks'
MASK_EXTENDED_COLLECTION = 'pruning/masks_extended'
OP_COLLECTION = 'pruning/ops'


def apply_mask(tensor, name='pruning'):
  """
  Point-wise multiplies a tensor with a binary mask.

  During training, pruning is simulated by setting entries of the mask to zero.

  Arguments:
    tensor: tf.Tensor
      A tensor where the last dimension represents channels which will be masked

  Returns:
    `tf.Tensor` with same shape as `tensor`
  """

  tensor_shape = tensor.shape

  with tf.variable_scope(name, reuse=True):
    # allocate masks and corresponding pruning signals
    mask = tf.Variable(tf.ones(tensor.shape.as_list()[-1]), trainable=False, name='mask')
    pruning_signal = tf.Variable(tf.zeros_like(mask), trainable=False, name='signal')

    # extending masks is a trick to get a separate gradient for each data point
    mask_extended = extend_mask(mask, tensor)

  # store extended mask, pruning signal, and other vars for easy access later
  mask.extended = mask_extended
  mask.pruning_signal = pruning_signal
  mask.tensor = tensor

  # mask tensor
  tensor = tf.multiply(tensor, mask_extended)
  tensor.set_shape(tensor_shape)
  tensor._mask = mask

  tf.add_to_collection(MASK_COLLECTION, mask)
  tf.add_to_collection(MASK_EXTENDED_COLLECTION, mask.extended)
  tf.add_to_collection(OP_COLLECTION, tensor.op)

  return tensor


def extend_mask(mask, tensor):
  """
  Repeats the mask for each data point stored in a tensor.

  If `tensor` is AxBxC dimensional and `mask` is C dimensional, returns an Ax1xC dimensional
  tensor with A copies or `mask`.

  Arguments:
    mask: tf.Tensor
      The mask which will be extended

    tensor: tf.Tensor
      The tensor to which the extended mask will be applied

  Returns:
    The extended mask
  """

  batch_size = tf.shape(tensor)[:1]
  ones = tf.ones([tf.rank(tensor) - 1], dtype=batch_size.dtype)
  multiples = tf.concat([batch_size, ones], 0)
  mask_shape = tf.concat([ones, [-1]], 0)
  return tf.tile(tf.reshape(mask, mask_shape), multiples)


def find_input_mask(tensor):
  """
  Find ancestral mask affecting the number of pruned channels of a tensor.

  Arguments:
    tensor: tf.Tensor
      Tensor for which to identify relevant mask

  Returns:
    A `tf.Tensor` or `None`
  """

  if hasattr(tensor, '_mask'):
    return tensor._mask
  if tensor.op.type in ['MatMul', 'Conv1D', 'Conv2D', 'Conv3D', 'Transpose']:
    # op produces a new number of channels, preceding mask therefore irrelevant
    return None
  if not tensor.op.inputs:
    return None
  for input in tensor.op.inputs:
    mask = find_input_mask(input)
    if mask is not None:
      return mask


def find_output_mask(tensor):
  """
  Find mask applied to the tensor or one of its descendants if it affects the tensor's pruned shape.

  Arguments:
    tensor: tf.Tensor or tf.Variable
      Tensor for which to identify relevant mask

  Returns:
    A `tf.Tensor` or `None`
  """

  if isinstance(tensor, tf.Variable):
    return find_output_mask(tensor.op.outputs[0])
  if hasattr(tensor, '_mask'):
    return tensor._mask
  for op in tensor.consumers():
    if len(op.outputs) != 1:
      continue
    if op.type in ['MatMul', 'Conv1D', 'Conv2D', 'Conv3D']:
      # masks of descendants are only relevant if tensor is right-multiplied
      if tensor == op.inputs[1]:
        return find_output_mask(op.outputs[0])
      return None
    mask = find_output_mask(op.outputs[0])
    if mask is not None:
      return mask


def find_mask(tensor):
  """
  Returns masks indicating channels of the tensor that are effectively removed from the graph.

  Arguments:
    tensor: tf.Tensor
      Tensor for which to compute a mask

  Returns:
    A `tf.Tensor` with binary entries indicating disabled channels
  """

  input_mask = find_input_mask(tensor)
  output_mask = find_output_mask(tensor)
  if input_mask is None:
    return output_mask
  if output_mask is None:
    return input_mask
  if input_mask is output_mask:
    return input_mask
  return input_mask * output_mask


def pruned_shape(tensor):
  """
  Computes the shape of a tensor after taking into account pruning of channels.

  Note that the shape will only differ in the last dimension, even if other dimensions are also
  effectively disabled by pruning masks.

  Arguments:
    tensor: tf.Tensor
      Tensor for which to compute a pruned shape

  Returns:
    A `tf.Tensor[tf.float32]` representing the pruned shape
  """

  mask = find_mask(tensor)

  if mask is None:
    return tf.cast(tf.shape(tensor), tf.float32)

  return tf.concat([
    tf.cast(tf.shape(tensor)[:-1], mask.dtype),
    tf.reduce_sum(mask, keepdims=True)], 0)


def computational_cost(op_or_tensor, _observed=None):
  """
  Estimates the computational complexity of a pruned graph (number of floating point operations).

  This function currently only supports sequential graphs such as those of MLPs and
  simple CNNs with 2D convolutions in NHWC format.

  Note that the computational cost returned by this function is proportional to batch size.

  Arguments:
    op_or_tensor: tf.Tensor or tf.Operation
      Root node of graph for which to compute computational cost

  Returns:
    A `tf.Tensor` representing a number of floating point operations
  """

  cost = tf.constant(0.)

  # exclude cost of computing extended pruning masks
  masks_extended = [mask.extended for mask in tf.get_collection(MASK_COLLECTION)]
  if op_or_tensor in masks_extended:
    return cost

  # convert tensor to op
  op = op_or_tensor.op if isinstance(op_or_tensor, (tf.Tensor, tf.Variable)) else op_or_tensor

  # make sure cost of op will not be counted twice
  if _observed is None:
    _observed = []
  elif op in _observed:
    return cost
  _observed.append(op)

  # compute cost of computing inputs
  for tensor in op.inputs:
    cost = cost + computational_cost(tensor, _observed)

  # add cost of operation
  if op.op_def is None or op in tf.get_collection(OP_COLLECTION):
    # exclude cost of undefined ops and pruning ops
    return cost

  elif op.op_def.name == 'MatMul':
    shape_a = pruned_shape(op.inputs[0])
    shape_b = pruned_shape(op.inputs[1])
    return cost + shape_a[0] * shape_b[1] * (2. * shape_a[1] - 1.)

  elif op.op_def.name in ['Add', 'Mul', 'BiasAdd']:
    return cost + tf.cond(
        tf.size(op.inputs[0]) > tf.size(op.inputs[1]),
        lambda: tf.reduce_prod(pruned_shape(op.inputs[0])),
        lambda: tf.reduce_prod(pruned_shape(op.inputs[1])))

  elif op.op_def.name in ['Conv2D']:
    output_shape = pruned_shape(op.outputs[0])
    input_shape = pruned_shape(op.inputs[0])
    kernel_shape = pruned_shape(op.inputs[1])
    inner_prod_cost = (tf.reduce_prod(kernel_shape[:2]) * input_shape[-1] * 2. - 1.)
    return cost + tf.reduce_prod(output_shape) * inner_prod_cost

  return cost


def update_pruning_signals(loss, decay=.96, masks=None, method='Fisher'):
  """
  For each mask, computes corresponding pruning signals indicating the importance of a feature.

  Arguments:
    loss: tf.Tensor
      Any cross-entropy loss

    decay: float
      Controls exponential moving average of pruning signals

    method: str
      Method used to compute pruning signal (currently only supports 'Fisher')

  Returns:
    A `list[tf.Tensor]` of pruning signals corresponding to masks

  References:
    * Theis et al., Faster gaze prediction with dense networks and Fisher pruning, 2018
  """

  if masks is None:
    masks = tf.get_collection(MASK_COLLECTION)

  if method not in ['Fisher']:
    raise ValueError('Pruning method \'{0}\' not supported.'.format(method))

  if not masks:
    return []

  with tf.variable_scope('pruning_opt', reuse=True):
    # compute gradients of extended masks (yields separate gradient for each data point)
    grads = tf.gradients(loss, [m.extended for m in masks])

    # estimate Fisher pruning signals from batch
    signals_batch = [tf.squeeze(tf.reduce_mean(tf.square(g), 0)) for g in grads]

    # update pruning signals
    signals = [m.pruning_signal for m in masks]
    signals = [tf.assign(s, decay * s + (1. - decay) * f, use_locking=True)
      for s, f in zip(signals, signals_batch)]

  return signals


def prune(signals, masks=None):
  """
  Prunes a single feature by zeroing the mask entry with the smallest pruning signal.

  Arguments:
    signals: list[tf.Tensor]
      A list of pruning signals

    masks: list[tf.Tensor]
      A list of corresponding masks, defaults to `tf.get_collection(MASK_COLLECTION)`

  Returns:
    A `tf.Operation` which updates masks
  """

  if masks is None:
    masks = tf.get_collection(MASK_COLLECTION)

  with tf.variable_scope('pruning_opt', reuse=True):
    # make sure we don't select already pruned units
    signals = [tf.where(m > .5, s, tf.zeros_like(s) + np.inf) for m, s in zip(masks, signals)]

    # find units with smallest pruning signal in each layer
    min_idx = [tf.argmin(s) for s in signals]
    min_signals = [s[i] for s, i in zip(signals, min_idx)]

    # find layer with smallest pruning signal
    l = tf.argmin(min_signals)

    # construct pruning operations, one for each mask
    updates = []
    for k, i in enumerate(min_idx):
      # set mask of layer l to 0 where pruning signal is smallest
      updates.append(
        tf.cond(
          tf.equal(l, k),
          lambda: tf.scatter_update(
            masks[k], tf.Print(i, [i], message="Pruning layer [{0}] at index ".format(k)), 0.),
          lambda: masks[k]))

    updates = tf.group(updates, name='prune')

  return updates
