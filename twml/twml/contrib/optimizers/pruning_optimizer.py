"""
Provides a general optimizer for pruning features of a neural network.

The optimizer estimates the computational cost of features, combines this information with pruning
signals indicating their usefulness, and disables features via binary masks at regular intervals.

To make a layer prunable, use `twml.contrib.pruning.apply_mask`:

  dense1 = tf.layers.dense(inputs=inputs, units=50, activation=tf.nn.relu)
  dense1 = apply_mask(dense1)

To prune the network, apply PruningOptimizer to any cross-entropy loss:

  loss = tf.losses.sparse_softmax_cross_entropy(labels=labels, logits=logits)

  optimizer = PruningOptimizer(learning_rate=0.001, momentum=0.5)
  minimize = optimizer.minimize(
      loss=loss,
      prune_every=10,
      burn_in=100,
      global_step=tf.train.get_global_step())
"""

import tensorflow.compat.v1 as tf

from twml.contrib.pruning import computational_cost, prune, update_pruning_signals
from twml.contrib.pruning import MASK_COLLECTION


class PruningOptimizer(tf.train.MomentumOptimizer):
  """
  Updates parameters with SGD and pruning masks using Fisher pruning.

  Arguments:
    learning_rate: float
      Learning rate of SGD

    momentum: float
      Momentum used by SGD

    use_locking: bool
      If `True`, use locks for update operations

    name: str
      Optional name prefix for the operations created when applying gradients

    use_nesterov: bool
      If `True`, use Nesterov momentum
  """

  def __init__(
      self,
      learning_rate,
      momentum=0.9,
      use_locking=False,
      name="PruningOptimizer",
      use_nesterov=False):
    super(PruningOptimizer, self).__init__(
        learning_rate=learning_rate,
        momentum=momentum,
        use_locking=use_locking,
        name=name,
        use_nesterov=use_nesterov)

  def minimize(
    self,
    loss,
    prune_every=100,
    burn_in=0,
    decay=.96,
    flops_weight='AUTO',
    flops_target=0,
    update_params=None,
    method='Fisher',
    *args,
    **kwargs):
    """
    Create operations to minimize loss and to prune features.

    A pruning signal measures the importance of feature maps. This is weighed against the
    computational cost of computing a feature map. Features are then iteratively pruned
    based on a weighted average of feature importance S and computational cost C (in FLOPs):

    $$S + w * C$$

    Setting `flops_weight` to 'AUTO' is the most convenient and recommended option, but not
    necessarily optimal.

    Arguments:
      loss: tf.Tensor
        The value to minimize

      prune_every: int
        One entry of a mask is set to zero only every few update steps

      burn_in: int
        Pruning starts only after this many parameter updates

      decay: float
        Controls exponential moving average of pruning signals

      flops_weight: float or str
        Controls the targeted trade-off between computational complexity and performance

      flops_target: float
        Stop pruning when computational complexity is less or this many floating point ops

      update_params: tf.Operation
        Optional training operation used instead of MomentumOptimizer to update parameters

      method: str
        Method used to compute pruning signal (currently only supports 'Fisher')

    Returns:
      A `tf.Operation` updating parameters and pruning masks

    References:
    * Theis et al., Faster gaze prediction with dense networks and Fisher pruning, 2018
    """

    # gradient-based updates of parameters
    if update_params is None:
      update_params = super(PruningOptimizer, self).minimize(loss, *args, **kwargs)

    masks = tf.get_collection(MASK_COLLECTION)

    with tf.variable_scope('pruning_opt', reuse=True):
      # estimate computational cost per data point
      batch_size = tf.cast(tf.shape(masks[0].tensor), loss.dtype)[0]
      cost = tf.divide(computational_cost(loss), batch_size, name='computational_cost')

      tf.summary.scalar('computational_cost', cost)

      if masks:
        signals = update_pruning_signals(loss, masks=masks, decay=decay, method=method)

        # estimate computational cost per feature map
        costs = tf.gradients(cost, masks)

        # trade off computational complexity and performance
        if flops_weight.upper() == 'AUTO':
          signals = [s / (c + 1e-6) for s, c in zip(signals, costs)]
        elif not isinstance(flops_weight, float) or flops_weight != 0.:
          signals = [s - flops_weight * c for s, c in zip(signals, costs)]

        counter = tf.Variable(0, name='pruning_counter')
        counter = tf.assign_add(counter, 1, use_locking=True)

        # only prune every so often after a burn-in phase
        pruning_cond = tf.logical_and(counter > burn_in, tf.equal(counter % prune_every, 0))

        # stop pruning after reaching threshold
        if flops_target > 0:
          pruning_cond = tf.logical_and(pruning_cond, tf.greater(cost, flops_target))

        update_masks = tf.cond(
          pruning_cond,
          lambda: prune(signals, masks=masks),
          lambda: tf.group(masks))

        return tf.group([update_params, update_masks])

    # no masks found
    return update_params
