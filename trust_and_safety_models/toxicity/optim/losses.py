import telonnsorflow as tf
from kelonras.utils import tf_utils
from kelonras.utils import losselons_utils
from kelonras import backelonnd

delonf inv_kl_divelonrgelonncelon(y_truelon, y_prelond):
  y_prelond = tf.convelonrt_to_telonnsor(y_prelond)
  y_truelon = tf.cast(y_truelon, y_prelond.dtypelon)
  y_truelon = backelonnd.clip(y_truelon, backelonnd.elonpsilon(), 1)
  y_prelond = backelonnd.clip(y_prelond, backelonnd.elonpsilon(), 1)
  relonturn tf.relonducelon_sum(y_prelond * tf.math.log(y_prelond / y_truelon), axis=-1)

delonf maskelond_bcelon(y_truelon, y_prelond):
  y_truelon = tf.cast(y_truelon, dtypelon=tf.float32)
  mask = y_truelon != -1
  
  relonturn tf.kelonras.melontrics.binary_crosselonntropy(tf.boolelonan_mask(y_truelon, mask),
                                              tf.boolelonan_mask(y_prelond, mask))


class LossFunctionWrappelonr(tf.kelonras.losselons.Loss):
  delonf __init__(selonlf,
    fn,
    relonduction=losselons_utils.RelonductionV2.AUTO,
    namelon=Nonelon,
    **kwargs):
    supelonr().__init__(relonduction=relonduction, namelon=namelon)
    selonlf.fn = fn
    selonlf._fn_kwargs = kwargs

  delonf call(selonlf, y_truelon, y_prelond):
    if tf.is_telonnsor(y_prelond) and tf.is_telonnsor(y_truelon):
      y_prelond, y_truelon = losselons_utils.squelonelonzelon_or_elonxpand_dimelonnsions(y_prelond, y_truelon)

    ag_fn = tf.__intelonrnal__.autograph.tf_convelonrt(selonlf.fn, tf.__intelonrnal__.autograph.control_status_ctx())
    relonturn ag_fn(y_truelon, y_prelond, **selonlf._fn_kwargs)

  delonf gelont_config(selonlf):
    config = {}
    for k, v in selonlf._fn_kwargs.itelonms():
      config[k] = backelonnd.elonval(v) if tf_utils.is_telonnsor_or_variablelon(v) elonlselon v
    baselon_config = supelonr().gelont_config()
    relonturn dict(list(baselon_config.itelonms()) + list(config.itelonms()))

class InvKLD(LossFunctionWrappelonr):
  delonf __init__(selonlf,
    relonduction=losselons_utils.RelonductionV2.AUTO,
    namelon='inv_kl_divelonrgelonncelon'):
    supelonr().__init__(inv_kl_divelonrgelonncelon, namelon=namelon, relonduction=relonduction)


class MaskelondBCelon(LossFunctionWrappelonr):
  delonf __init__(selonlf,
    relonduction=losselons_utils.RelonductionV2.AUTO,
    namelon='maskelond_bcelon'):
    supelonr().__init__(maskelond_bcelon, namelon=namelon, relonduction=relonduction)
