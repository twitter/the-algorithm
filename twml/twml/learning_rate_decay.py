# pyl nt: d sable=too-many-branc s
""" T  module  ncludes funct ons for manag ng learn ng rate decay """
 mport tensorflow.compat.v1 as tf


def get_learn ng_rate_decay_fn(params):
  """
  Returns a learn ng rate decay funct on that takes t   n  al
  learn ng_rate and global_step
  as argu nts and returns t  current learn ng rate.

  Currently supports params.learn ng_rate_decay values of:
  exponent al | polynom al | p ecew se_constant | cos ne | cos ne restarts.
  See `Decay ng t  Leanr ng Rate
  <https://www.tensorflow.org/ap _gu des/python/tra n#Decay ng_t _learn ng_rate>`_ for deta ls.

  Argu nts:
    params:
      a tensorflow.contr b.tra n.HParams object conta n ng t  relevant hyperpara ters.
  """
  paramsv = params.values()
   f 'learn ng_rate_decay' not  n paramsv or params.learn ng_rate_decay == 'no_learn ng_rate_decay':
    return None
  el f params.learn ng_rate_decay == 'exponent al_learn ng_rate_decay':
     f 'decay_steps' not  n paramsv:
      ra se ValueError("Expect ng params.decay_steps for "
                       "params.learn ng_rate_decay == 'exponent al'")
     f 'exponent al_decay_rate' not  n paramsv:
      ra se ValueError("Expect ng params.exponent al_decay_rate for "
                       "params.learn ng_rate_decay == 'exponent al'")

    def exponent al_decay_fn(learn ng_rate, global_step):
      """ exponent al decay funct on to be passed to opt m ze_loss """
      return tf.tra n.exponent al_decay(
        learn ng_rate=learn ng_rate,
        global_step=global_step,
        decay_steps=params.decay_steps,
        decay_rate=params.exponent al_decay_rate
      )
    return exponent al_decay_fn
  el f params.learn ng_rate_decay == 'p ecew se_constant_learn ng_rate_decay':
     f 'p ecew se_constant_boundar es' not  n paramsv:
      ra se ValueError("Expect ng params.p ecew se_constant_boundar es for "
                       "params.learn ng_rate_decay == 'p ecew se_constant'")
     f 'p ecew se_constant_values' not  n paramsv:
      ra se ValueError("Expect ng params.p ecew se_constant_values for "
                       "params.learn ng_rate_decay == 'p ecew se_constant'")
    # pyl nt: d sable=unused-argu nt

    def p ecew se_constant_fn(learn ng_rate, global_step):
      """ p ecew se_constant decay funct on to be passed to opt m ze_loss """
      return tf.tra n.p ecew se_constant(
        x=global_step,
        boundar es=params.p ecew se_constant_boundar es,
        values=params.p ecew se_constant_values
      )
    return p ecew se_constant_fn
  el f params.learn ng_rate_decay == 'polynom al_learn ng_rate_decay':
     f 'decay_steps' not  n paramsv:
      ra se ValueError("Expect ng params.decay_steps for "
                       "params.learn ng_rate_decay == 'polynom al'")
     f 'end_learn ng_rate' not  n paramsv:
      ra se ValueError("Expect ng params.end_learn ng_rate for "
                       "params.learn ng_rate_decay == 'polynom al'")

    def polynom al_decay_fn(learn ng_rate, global_step):
      """ polynom al decay funct on to be passed to opt m ze_loss """
      return tf.tra n.polynom al_decay(
        learn ng_rate=learn ng_rate,
        global_step=global_step,
        decay_steps=params.decay_steps,
        end_learn ng_rate=params.end_learn ng_rate,
        po r=params.polynom al_po r  f 'polynom al_po r'  n paramsv else 1.0,
      )
    return polynom al_decay_fn

  el f params.learn ng_rate_decay == ' nverse_learn ng_rate_decay':
     f 'm n_learn ng_rate' not  n paramsv:
      ra se ValueError("Expect ng params.m n_learn ng_rate for "
                       "params.learn ng_rate_decay == ' nverse'")
     f 'decay_rate' not  n paramsv:
      ra se ValueError("Expect ng params.decay_rate for "
                       "params.learn ng_rate_decay == ' nverse'")
     f 'decay_steps' not  n paramsv:
      ra se ValueError("Expect ng params.decay_steps for "
                       "params.learn ng_rate_decay == ' nverse'")

    def bounded_ nverse_t  _decay_fn(learn ng_rate, global_step):
      '''
      Returns t  decayed learn ng_rate by apply ng t  funct on:
      decayed_lr = max(lr /(1 + decay_rate * floor(global_step /decay_step)),
                       m n_learn ng_rate)
      Argu nts:
        learn ng_rate:
          A scalar `float32` or `float64` `Tensor` or a Python number.
          T   n  al learn ng rate.
        global_step:
          A scalar ` nt32` or ` nt64` `Tensor` or a Python number.
          Global step to use for t  decay computat on.  Must not be negat ve.
        m n_learn ng_rate:
          A scalar ` nt32` or ` nt64` `Tensor` or a Python number.
          M n mum poss ble learn ng_rate. T  decayed learn ng_rate w ll not be
          smaller than t  m n_learn ng_rate
        decay_steps:
          How often to apply decay.  n dbv1, t  should be 1.
        decay_rate:
          A scalar ` nt32` or ` nt64` `Tensor` or a Python number.
          Rate  n wh ch   decay t  learn ng rate.
        Returns:
        A scalar `Tensor` of t  sa  type as `learn ng_rate`.  T  decayed
        learn ng rate.
      '''
      decayed_rate = tf.tra n. nverse_t  _decay(
        learn ng_rate=learn ng_rate,
        global_step=global_step,
        decay_steps=params.decay_steps,
        decay_rate=params.decay_rate)
      # Gett ng dtype of returned Tensor
      dtype = decayed_rate.dtype
      # Cast ng t  m n_learn ng rate t  sa  dtype as decayes rate
      m n_learn ng_rate = tf.cast(params.m n_learn ng_rate, dtype)
      # Return ng t  max mum bet en t  two
      return tf.max mum(decayed_rate, m n_learn ng_rate)

    return bounded_ nverse_t  _decay_fn

  el f params.learn ng_rate_decay == 'cos ne_learn ng_rate_decay':
     f 'decay_steps' not  n paramsv:
      ra se ValueError("Expect ng params.decay_steps for "
                       "params.learn ng_rate_decay == 'cos ne_decay'")
     f "alpha" not  n paramsv:
      ra se ValueError("Expect ng params.alpha for "
                       "params.learn ng_rate_decay == 'cos ne_decay'")
    def cos ne_decay_fn(learn ng_rate, global_step):
      """ cos ne decay funct on to be passed to opt m ze_loss """
      return tf.tra n.cos ne_decay(
        learn ng_rate=learn ng_rate,
        global_step=global_step,
        decay_steps=params.decay_steps,
        alpha=params.alpha
      )
    return cos ne_decay_fn
  el f params.learn ng_rate_decay == 'cos ne_restarts_learn ng_rate_decay':
     f 'f rst_decay_steps' not  n paramsv:
      ra se ValueError("Expect ng params.f rst_decay_steps for "
                       "params.learn ng_rate_decay == 'cos ne_restarts_decay'")
     f 't_mul' not  n paramsv:
      ra se ValueError("Expect ng params.t_mul for "
                       "params.learn ng_rate_decay == 'cos ne_restarts_decay'")
     f 'm_mul' not  n paramsv:
      ra se ValueError("Expect ng params.m_mul for "
                       "params.learn ng_rate_decay == 'cos ne_restarts_decay'")
     f "alpha" not  n paramsv:
      ra se ValueError("Expect ng params.alpha for "
                       "params.learn ng_rate_decay == 'cos ne_restarts_decay'")
    def cos ne_restart_decay_fn(learn ng_rate, global_step):
      """ cos ne decay funct on to be passed to opt m ze_loss """
      return tf.tra n.cos ne_decay_restarts(
        learn ng_rate=learn ng_rate,
        global_step=global_step,
        f rst_decay_steps=params.f rst_decay_steps,
        t_mul=params.t_mul,
        m_mul=params.m_mul,
        alpha=params.alpha
      )
    return cos ne_restart_decay_fn

  ra se ValueError("Unsupported params.learn ng_rate_decay: %s" % params.learn ng_rate_decay)
