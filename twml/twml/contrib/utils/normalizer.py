import tensorflow.compat.v1 as tf
from twml.contrib.utils import math_fns


def mean_max_normalizaiton(dense_tensor):
  """
  In-batch normalization
  Args:
    dense_tensor: A dense `Tensor`.
  Returns:
    (dense_tensor - mean) / abs(max value)
  Note:
    when dense_tensor is of size [1, ?] it will give 0
    If this is not what you want handle it outside the function
  """
  dense_mean = tf.reduce_mean(dense_tensor, reduction_indices=[0])
  dense_abs_max = tf.abs(tf.reduce_max(dense_tensor, reduction_indices=[0]))
  dense_tensor = math_fns.safe_div(dense_tensor - dense_mean, dense_abs_max,
    'mean_max_normalization_in_batch')
  return dense_tensor


def standard_normalizaiton(dense_tensor):
  """
  In-batch normalization
  z-normalization or standard_normalization in batch
  Args:
    dense_tensor: A dense `Tensor`.
  Returns:
    (dense_tensor - mean) / variance
  Note:
    when dense_tensor is of size [1, ?] it will give 0
    If this is not what you want handle it outside the function
  """
  epsilon = 1E-7
  dense_mean, dense_variance = tf.nn.moments(dense_tensor, 0)
  # using epsilon is safer than math_fns.safe_div in here
  dense_tensor = (dense_tensor - dense_mean) / (dense_variance + epsilon)
  return dense_tensor
