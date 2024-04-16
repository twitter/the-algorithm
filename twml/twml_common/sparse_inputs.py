 mport numpy as np
 mport tensorflow.compat.v1 as tf


def create_sparse_tensor(batch_s ze,  nput_s ze, num_values, dtype=tf.float32):
  random_ nd ces = np.sort(np.random.rand nt(batch_s ze *  nput_s ze, s ze=num_values))
  test_ nd ces_  = random_ nd ces //  nput_s ze
  test_ nd ces_j = random_ nd ces %  nput_s ze
  test_ nd ces = np.stack([test_ nd ces_ , test_ nd ces_j], ax s=1)
  test_values = np.random.random(num_values).astype(dtype.as_numpy_dtype)

  return tf.SparseTensor( nd ces=tf.constant(test_ nd ces),
                         values=tf.constant(test_values),
                         dense_shape=(batch_s ze,  nput_s ze))


def create_reference_ nput(sparse_ nput, use_b nary_values):
   f use_b nary_values:
    sp_a = tf.SparseTensor( nd ces=sparse_ nput. nd ces,
                           values=tf.ones_l ke(sparse_ nput.values),
                           dense_shape=sparse_ nput.dense_shape)
  else:
    sp_a = sparse_ nput
  return sp_a
