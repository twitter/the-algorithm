"""
A custom optimizer to implement Deep Gradient Compression. The general idea of
gradient compression is to compress the gradients exchanged across machines,
in order to reduce the communication overhead of distributing computing efforts.
More details in https://arxiv.org/abs/1712.01887
"""

# TODO: Test how much communication overhead this DeepGradientCompressionOptimizer can reduce under
# multi-GPU and distributed setting.

import tensorflow.compat.v1 as tf


def compute_threshold(grad, density):
  """
  A utility function to compute the threshold for gradient sparsification, given the gradient
  tensor and the density.
  Args:
    grad(tf.Tensor):
      Gradient tensor for some variable.
    density(float):
      Density degree when sparsifying gradients.
  Returns(float):
    Threshold for gradient sparsification.
  """
  flat_grad = tf.reshape(grad, [-1])
  abs_flat_grad = tf.abs(flat_grad)
  size = tf.shape(abs_flat_grad)[0]
  k = tf.maximum(tf.constant(1),
                 tf.cast(tf.scalar_mul(density, tf.cast(size, tf.float32)), tf.int32))
  topk, _ = tf.nn.top_k(abs_flat_grad, k, False)
  return topk[-1]


def get_top_row_indices(values, density):
  """
  A utility function to get indices of most significant rows, given the density degree.
  Args:
    values(tf.Tensor):
      Gradient or locally accumulated gradient for some variable.
    density(float):
      Density degree when filtering out rows.
  Returns(list(int)):
    Indices of most significant rows.
  """
  abs_values = tf.abs(values)

  try:
    row_num = tf.shape(abs_values)[0]
    k = tf.maximum(tf.constant(1),
                   tf.cast(tf.scalar_mul(density, tf.cast(row_num, tf.float32)), tf.int32))
    row_sums = tf.squeeze(tf.reduce_sum(values, axis=1, keepdims=True))
    _, top_row_indices = tf.nn.top_k(row_sums, k=k, sorted=False)
    # print "abs_values", abs_values, "row_sums", row_sums
    return top_row_indices
    # return tf.range(row_num)

  except ValueError:  # if the tensor is 0-D or 1-D
    return None


class DeepGradientCompressionOptimizer(tf.train.GradientDescentOptimizer):
  """
  A custom optimizer to implement Deep Gradient Compression (https://arxiv.org/abs/1712.01887).
  """

  def __init__(self, learning_rate, use_locking=False, name="Sparse",
               density=1.0,
               density_decay=False,
               density_decay_steps=10000,
               density_decay_rate=0.5,
               min_density=0.1,
               accumulation=False):
    super(DeepGradientCompressionOptimizer, self).__init__(learning_rate, use_locking, name)
    self._initial_density_t = tf.convert_to_tensor(density)
    self._density_decay = density_decay
    dtype = self._initial_density_t.dtype
    self._density_decay_steps_t = tf.convert_to_tensor(density_decay_steps, dtype)
    self._density_decay_rate_t = tf.convert_to_tensor(density_decay_rate, dtype)
    self._min_density_t = tf.convert_to_tensor(min_density, dtype)
    self._accumulation = accumulation

  def _prepare(self):
    super(DeepGradientCompressionOptimizer, self)._prepare()
    if not self._density_decay:
      self._density_t = self._initial_density_t
    else:
      dtype = self._initial_density_t.dtype
      global_step = tf.cast(tf.train.get_global_step(), dtype)
      p = tf.floor(tf.divide(global_step, self._density_decay_steps_t))
      decayed_density = tf.multiply(self._initial_density_t,
                                    tf.pow(self._density_decay_rate_t, p))
      self._density_t = tf.maximum(self._min_density_t, decayed_density)

  def _create_slots(self, var_list):
    """
    Create a slot variable to accumulate gradients locally for each variable in `var_list`.
    Args:
      var_list(list(tf.Variable)):
        List of variables to accumulate gradients locally for.
    """
    for var in var_list:
      self._zeros_slot(var, "g_buffer", self._name)

  def _apply_dense(self, grad, var):
    if not self._accumulation:
      top_row_indices = get_top_row_indices(grad, self._density_t)

      if top_row_indices is None:
        return super(DeepGradientCompressionOptimizer, self)._apply_dense(grad, var)

      sparsified_values = tf.gather(grad, top_row_indices)
      sparsified_indices = top_row_indices

      sparsified_grad = tf.IndexedSlices(sparsified_values, sparsified_indices)

      return super(DeepGradientCompressionOptimizer, self)._apply_sparse_duplicate_indices(
        sparsified_grad, var)

    else:
      g_buffer = self.get_slot(var, "g_buffer")

      g_buffer = tf.assign_add(g_buffer, grad)

      top_row_indices = get_top_row_indices(g_buffer, self._density_t)

      if top_row_indices is None:
        return super(DeepGradientCompressionOptimizer, self)._apply_dense(grad, var)

      sparsified_values = tf.gather(g_buffer, top_row_indices)
      sparsified_indices = top_row_indices

      sparsified_grad = tf.IndexedSlices(sparsified_values, sparsified_indices)

      update_var = super(DeepGradientCompressionOptimizer, self)._apply_sparse_duplicate_indices(
        sparsified_grad, var)

      update_g_buffer = tf.scatter_update(g_buffer, sparsified_indices, tf.zeros_like(
        sparsified_values))

      return tf.group(*[update_var, update_g_buffer])

  def _apply_sparse_duplicate_indices(self, grad, var):
    if not self._accumulation:
      top_row_indices = get_top_row_indices(grad.values, self._density_t)

      if top_row_indices is None:
        return super(DeepGradientCompressionOptimizer, self)._apply_sparse_duplicate_indices(grad, var)  # noqa: E501

      sparsified_values = tf.gather(grad.values, top_row_indices)
      sparsified_indices = tf.gather(grad.indices, top_row_indices)

      sparsified_grad = tf.IndexedSlices(sparsified_values, sparsified_indices)

      return super(DeepGradientCompressionOptimizer, self)._apply_sparse_duplicate_indices(
        sparsified_grad, var)

    else:
      g_buffer = self.get_slot(var, "g_buffer")

      g_buffer = tf.scatter_update(g_buffer, grad.indices, grad.values)

      top_row_indices = get_top_row_indices(g_buffer, self._density_t)

      if top_row_indices is None:
        return super(DeepGradientCompressionOptimizer,
                     self)._apply_sparse_duplicate_indices(grad, var)

      sparsified_values = tf.gather(g_buffer, top_row_indices)
      sparsified_indices = top_row_indices

      sparsified_grad = tf.IndexedSlices(sparsified_values, sparsified_indices)

      update_var = super(DeepGradientCompressionOptimizer, self)._apply_sparse_duplicate_indices(
        sparsified_grad, var)

      update_g_buffer = tf.scatter_update(g_buffer, sparsified_indices, tf.zeros_like(
        sparsified_values))

      return tf.group(*[update_var, update_g_buffer])
