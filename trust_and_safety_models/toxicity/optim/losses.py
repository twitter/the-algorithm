 mport tensorflow as tf
from keras.ut ls  mport tf_ut ls
from keras.ut ls  mport losses_ut ls
from keras  mport backend

def  nv_kl_d vergence(y_true, y_pred):
  y_pred = tf.convert_to_tensor(y_pred)
  y_true = tf.cast(y_true, y_pred.dtype)
  y_true = backend.cl p(y_true, backend.eps lon(), 1)
  y_pred = backend.cl p(y_pred, backend.eps lon(), 1)
  return tf.reduce_sum(y_pred * tf.math.log(y_pred / y_true), ax s=-1)

def masked_bce(y_true, y_pred):
  y_true = tf.cast(y_true, dtype=tf.float32)
  mask = y_true != -1
  
  return tf.keras. tr cs.b nary_crossentropy(tf.boolean_mask(y_true, mask), 
                                              tf.boolean_mask(y_pred, mask))


class LossFunct onWrapper(tf.keras.losses.Loss):
  def __ n __(self,
    fn,
    reduct on=losses_ut ls.Reduct onV2.AUTO,
    na =None,
    **kwargs):
    super().__ n __(reduct on=reduct on, na =na )
    self.fn = fn
    self._fn_kwargs = kwargs

  def call(self, y_true, y_pred):
     f tf. s_tensor(y_pred) and tf. s_tensor(y_true):
      y_pred, y_true = losses_ut ls.squeeze_or_expand_d  ns ons(y_pred, y_true)

    ag_fn = tf.__ nternal__.autograph.tf_convert(self.fn, tf.__ nternal__.autograph.control_status_ctx())
    return ag_fn(y_true, y_pred, **self._fn_kwargs)

  def get_conf g(self):
    conf g = {}
    for k, v  n self._fn_kwargs. ems():
      conf g[k] = backend.eval(v)  f tf_ut ls. s_tensor_or_var able(v) else v
    base_conf g = super().get_conf g()
    return d ct(l st(base_conf g. ems()) + l st(conf g. ems()))

class  nvKLD(LossFunct onWrapper):
  def __ n __(self,
    reduct on=losses_ut ls.Reduct onV2.AUTO,
    na =' nv_kl_d vergence'):
    super().__ n __( nv_kl_d vergence, na =na , reduct on=reduct on)


class MaskedBCE(LossFunct onWrapper):
  def __ n __(self,
    reduct on=losses_ut ls.Reduct onV2.AUTO,
    na ='masked_bce'):
    super().__ n __(masked_bce, na =na , reduct on=reduct on)
