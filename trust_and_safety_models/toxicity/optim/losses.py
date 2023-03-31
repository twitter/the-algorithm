import tensorflow as tf
from keras.utils import tf_utils
from keras.utils import losses_utils
from keras import backend

def inv_kl_divergence(y_true, y_pred):
  y_pred = tf.convert_to_tensor(y_pred)
  y_true = tf.cast(y_true, y_pred.dtype)
  y_true = backend.clip(y_true, backend.epsilon(), 1)
  y_pred = backend.clip(y_pred, backend.epsilon(), 1)
  return tf.reduce_sum(y_pred * tf.math.log(y_pred / y_true), axis=-1)

def masked_bce(y_true, y_pred):
  y_true = tf.cast(y_true, dtype=tf.float32)
  mask = y_true != -1
  
  return tf.keras.metrics.binary_crossentropy(tf.boolean_mask(y_true, mask), 
                                              tf.boolean_mask(y_pred, mask))


class LossFunctionWrapper(tf.keras.losses.Loss):
  def __init__(self,
    fn,
    reduction=losses_utils.ReductionV2.AUTO,
    name=None,
    **kwargs):
    super().__init__(reduction=reduction, name=name)
    self.fn = fn
    self._fn_kwargs = kwargs

  def call(self, y_true, y_pred):
    if tf.is_tensor(y_pred) and tf.is_tensor(y_true):
      y_pred, y_true = losses_utils.squeeze_or_expand_dimensions(y_pred, y_true)

    ag_fn = tf.__internal__.autograph.tf_convert(self.fn, tf.__internal__.autograph.control_status_ctx())
    return ag_fn(y_true, y_pred, **self._fn_kwargs)

  def get_config(self):
    config = {}
    for k, v in self._fn_kwargs.items():
      config[k] = backend.eval(v) if tf_utils.is_tensor_or_variable(v) else v
    base_config = super().get_config()
    return dict(list(base_config.items()) + list(config.items()))

class InvKLD(LossFunctionWrapper):
  def __init__(self,
    reduction=losses_utils.ReductionV2.AUTO,
    name='inv_kl_divergence'):
    super().__init__(inv_kl_divergence, name=name, reduction=reduction)


class MaskedBCE(LossFunctionWrapper):
  def __init__(self,
    reduction=losses_utils.ReductionV2.AUTO,
    name='masked_bce'):
    super().__init__(masked_bce, name=name, reduction=reduction)
