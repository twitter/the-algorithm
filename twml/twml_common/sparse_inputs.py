import numpy as np
import tensorflow.compat.v1 as tf


def create_sparse_tensor(batch_size, input_size, num_values, dtype=tf.float32):
  random_indices = np.sort(np.random.randint(batch_size * input_size, size=num_values))
  test_indices_i = random_indices // input_size
  test_indices_j = random_indices % input_size
  test_indices = np.stack([test_indices_i, test_indices_j], axis=1)
  test_values = np.random.random(num_values).astype(dtype.as_numpy_dtype)

  return tf.SparseTensor(indices=tf.constant(test_indices),
                         values=tf.constant(test_values),
                         dense_shape=(batch_size, input_size))


def create_reference_input(sparse_input, use_binary_values):
  if use_binary_values:
    sp_a = tf.SparseTensor(indices=sparse_input.indices,
                           values=tf.ones_like(sparse_input.values),
                           dense_shape=sparse_input.dense_shape)
  else:
    sp_a = sparse_input
  return sp_a
