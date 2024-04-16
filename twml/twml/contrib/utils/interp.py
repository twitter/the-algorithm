"""
 nterpolat on funct ons
"""

 mport l btwml
 mport tensorflow.compat.v1 as tf
 mport twml


def l near_ nterp1( nputs, ref_ nputs, ref_outputs):
  """
  Perform 1D l near  nterpolat on.
  Argu nts:
     nputs:
      T  query  nput values.
    ref_ nputs:
      Reference gr d po nts used for  nterpolat on.
    ref_outputs:
      Reference output values used for  nterpolat on.

  Returns:
    T   nterpolated outputs for t  requested  nput values.
  """

   nputs = tf.convert_to_tensor( nputs)
  ref_ nputs = tf.convert_to_tensor(ref_ nputs)
  ref_outputs = tf.convert_to_tensor(ref_outputs)

  nd ms =  nputs.shape.nd ms
  ref_ nputs_nd ms = ref_ nputs.shape.nd ms
  ref_outputs_nd ms = ref_ nputs.shape.nd ms

   f (ref_ nputs_nd ms != nd ms):
    ra se ValueError("D  ns on m smatch.  nputs: %d, ref_ nputs: %d" % (nd ms, ref_ nputs_nd ms))

   f (ref_outputs_nd ms != nd ms):
    ra se ValueError("D  ns on m smatch.  nputs: %d, ref_outputs: %d" % (nd ms, ref_outputs_nd ms))

   f nd ms > 2:
    ra se ValueError(" nput d  ns ons should be < 2D. But got %d." % nd ms)

  or g nal_ nput_shape = tf.shape( nputs)
  # T   s needed because  soton c_cal brat on expects:
  # -  nputs of s ze [num_samples, num_classes]
  # - ref_ nputs, ref_outputs of s ze [num_classes, num_b ns]
   nputs = tf.reshape( nputs, [-1, 1])
  ref_ nputs = tf.reshape(ref_ nputs, [1, -1])
  ref_outputs = tf.reshape(ref_outputs, [1, -1])

  #  soton c_cal brat on  s s mply do ng l near  nterpolat on.
  # T  needs to be rena d  n t  future to make   cons stent.
  outputs = l btwml.ops. soton c_cal brat on( nputs, ref_ nputs, ref_outputs)
  return tf.reshape(outputs, or g nal_ nput_shape)


def l near_ nterp1_by_class( nputs,  nput_classes, ref_ nputs, ref_outputs):
  """
  Perform 1D l near  nterpolat on.
  Argu nts:
     nputs:
      T  query  nput values.
     nput_classes:
      T  class  ndex to use from t  reference gr d.
    ref_ nputs:
      Reference 2D gr d po nts used for  nterpolat on.
      Each row denotes t  gr d from a d fferent class.
    ref_outputs:
      Reference 2D output values used for  nterpolat on.
      Each row denotes t  gr d from a d fferent class.

  Returns:
    T   nterpolated outputs for t  requested  nput values.
  """

   nputs = tf.convert_to_tensor( nputs)
   nput_classes = tf.convert_to_tensor( nput_classes)
  ref_ nputs = tf.convert_to_tensor(ref_ nputs)
  ref_outputs = tf.convert_to_tensor(ref_outputs)

  or g nal_ nput_shape = tf.shape( nputs)

  # pass through
  def  n_func(x):
    return x

  #  ndexed funct on
  def cond_func( , fn):
     dx =  nput_classes[ ]
    x = tf.expand_d ms(fn(), ax s=0)
    return l near_ nterp1(x, ref_ nputs[ dx], ref_outputs[ dx])

  # Use wh le loop for now, needs to be replace by a custom C++ op later.
  outputs = twml.ut l.batch_apply( n_func,  nputs, cond_func=cond_func)
  return tf.reshape(outputs, or g nal_ nput_shape)
