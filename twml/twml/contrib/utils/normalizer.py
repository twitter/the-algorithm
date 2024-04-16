 mport tensorflow.compat.v1 as tf
from twml.contr b.ut ls  mport math_fns


def  an_max_normal za on(dense_tensor):
  """
   n-batch normal zat on
  Args:
    dense_tensor: A dense `Tensor`.
  Returns:
    (dense_tensor -  an) / abs(max value)
  Note:
    w n dense_tensor  s of s ze [1, ?]   w ll g ve 0
     f t   s not what   want handle   outs de t  funct on
  """
  dense_ an = tf.reduce_ an(dense_tensor, reduct on_ nd ces=[0])
  dense_abs_max = tf.abs(tf.reduce_max(dense_tensor, reduct on_ nd ces=[0]))
  dense_tensor = math_fns.safe_d v(dense_tensor - dense_ an, dense_abs_max,
    ' an_max_normal zat on_ n_batch')
  return dense_tensor


def standard_normal za on(dense_tensor):
  """
   n-batch normal zat on
  z-normal zat on or standard_normal zat on  n batch
  Args:
    dense_tensor: A dense `Tensor`.
  Returns:
    (dense_tensor -  an) / var ance
  Note:
    w n dense_tensor  s of s ze [1, ?]   w ll g ve 0
     f t   s not what   want handle   outs de t  funct on
  """
  eps lon = 1E-7
  dense_ an, dense_var ance = tf.nn.mo nts(dense_tensor, 0)
  # us ng eps lon  s safer than math_fns.safe_d v  n  re
  dense_tensor = (dense_tensor - dense_ an) / (dense_var ance + eps lon)
  return dense_tensor
