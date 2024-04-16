 mport tensorflow.compat.v1 as tf


def cos ne_s m lar y(x1, x2, ax s):
  """
  cos ne s m lar y of two tensors.

  Argu nts:
    x1:
      A tf.Tensor
    x2:
      A tf.Tensor
    ax s: D  ns on along wh ch to normal ze.
  """
  normal ze_x1 = tf.nn.l2_normal ze(x1, ax s=ax s)
  normal ze_x2 = tf.nn.l2_normal ze(x2, ax s=ax s)
  return tf.reduce_sum(tf.mult ply(normal ze_x1, normal ze_x2), ax s=ax s)
