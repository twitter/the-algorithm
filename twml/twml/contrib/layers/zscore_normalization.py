"""
Conta ns t  twml.layers.ZscoreNormal zat on layer.
"""
from twml.layers.layer  mport Layer
 mport tensorflow.compat.v1 as tf

from tensorflow.python.tra n ng  mport mov ng_averages


# T   s cop ed from tensorflow.contr b.fra work.python.ops.add_model_var able  n 1.15
# Not ava lable  n 2.x
# TODO: F gure out  f t   s really necessary.
def _add_model_var able(var):
  """Adds a var able to t  `GraphKeys.MODEL_VAR ABLES` collect on.
  Args:
    var: a var able.
  """
   f var not  n tf.get_collect on(tf.GraphKeys.MODEL_VAR ABLES):
    tf.add_to_collect on(tf.GraphKeys.MODEL_VAR ABLES, var)


def update_mov ng_var able(batch_var, mov ng_var, decay, zero_deb as=True, na =None):
  update_op = mov ng_averages.ass gn_mov ng_average(
      mov ng_var, batch_var, decay, zero_deb as=zero_deb as, na =None)
  _add_model_var able(mov ng_var)
  w h tf.control_dependenc es([update_op]):
    return tf. dent y(mov ng_var)


class ZscoreNormal zat on(Layer):
  """
  Perform z-score normal zat on us ng mov ng  an and std.
  M ss ng values are not  ncluded dur ng  an/std calculat on
  T  layer should only be used r ght after  nput layer.

  Args:
    decay:
      us ng large decay to  nclude longer mov ng  ans.
    data_type:
      use float64 to prevent overflow dur ng var ance calculat on.
    na :
      Layer na 
  Returns:
    A layer represent ng t  output of t  ZscoreNormal zat on transformat on.
   """

  def __ n __(
    self,
    decay=0.9999,
    data_type=tf.float64,
    na =None,
    **kwargs):
    super(ZscoreNormal zat on, self).__ n __(na =na , **kwargs)
    self.eps lon = tf.constant(1., data_type)
    self.decay = decay
    self.data_type = data_type

  def bu ld(self,  nput_shape):  # pyl nt: d sable=unused-argu nt
    """Creates t  mov ng_ an and mov ng_var tf.Var ables of t  layer."""
     nput_d m =  nput_shape[1]
    self.mov ng_ an = self.add_var able(
      '{}_ an/EMA'.format(self.na ),
       n  al zer=tf.constant_ n  al zer(),
      shape=[ nput_d m],
      dtype=self.data_type,
      tra nable=False
    )
    self.mov ng_var = self.add_var able(
      '{}_var ance/EMA'.format(self.na ),
       n  al zer=tf.constant_ n  al zer(),
      shape=[ nput_d m],
      dtype=self.data_type,
      tra nable=False
    )
    self.bu lt = True

  def compute_output_shape(self,  nput_shape):
    """Computes t  output shape of t  layer g ven t   nput shape.

    Args:
       nput_shape: A (poss bly nested tuple of) `TensorShape`.    need not
        be fully def ned (e.g. t  batch s ze may be unknown).

    """

    return  nput_shape

  def _tra n ng_pass(self,  nput, dense_mask,  nput_dtype, handle_s ngle, zero_deb as):
    eps lon = self.eps lon
    mov ng_ an, mov ng_var = self.mov ng_ an, self.mov ng_var
    # calculate t  number of ex s  ng value for each feature
    tensor_batch_num = tf.reduce_sum(tf.cast(dense_mask, self.data_type), ax s=0)
    mask_ones = tf.cast(tensor_batch_num, tf.bool)
    eps_vector = tf.f ll(tf.shape(tensor_batch_num), eps lon)
    # t  follow ng f lled 0 w h ep s on
    tensor_batch_num_eps = tf.w re(mask_ones,
                                    tensor_batch_num,
                                    eps_vector
                                  )
    tensor_batch_num_eps_broacast = tf.expand_d ms(tensor_batch_num_eps, 0)
    tensor_batch_d v ded =  nput / tensor_batch_num_eps_broacast
    tensor_batch_ an = tf.reduce_sum(tensor_batch_d v ded, ax s=0)

    # update mov ng  an  re, and use   to calculate t  std.
    tensor_mov ng_ an = update_mov ng_var able(tensor_batch_ an, mov ng_ an, self.decay,
                                                zero_deb as, na =" an_ema_op")

    tensor_batch_sub_ an =  nput - tf.expand_d ms(tensor_mov ng_ an, 0)
    tensor_batch_sub_ an = tf.w re(dense_mask,
                                    tensor_batch_sub_ an,
                                    tf.zeros_l ke(tensor_batch_sub_ an))
    # d v ded by sqrt(n) before square, and t n do summat on for nu r c stab l y.
    broad_sqrt_num_eps = tf.expand_d ms(tf.sqrt(tensor_batch_num_eps), 0)
    tensor_batch_sub_ an_d v = tensor_batch_sub_ an / broad_sqrt_num_eps
    tensor_batch_sub_ an_d v_square = tf.square(tensor_batch_sub_ an_d v)
    tensor_batch_var = tf.reduce_sum(tensor_batch_sub_ an_d v_square, ax s=0)

    # update mov ng var  re, dont replace 0 w h eps before updat ng.
    tensor_mov ng_var = update_mov ng_var able(tensor_batch_var, mov ng_var, self.decay,
                                               zero_deb as, na ="var_ema_op")

    #  f std  s 0, replace   w h eps lon
    tensor_mov ng_std = tf.sqrt(tensor_mov ng_var)
    tensor_mov ng_std_eps = tf.w re(tf.equal(tensor_mov ng_std, 0),
                                    eps_vector,
                                    tensor_mov ng_std)

    m ss ng_ nput_norm = tensor_batch_sub_ an / tf.expand_d ms(tensor_mov ng_std_eps, 0)

     f handle_s ngle:
      #  f std==0 and value not m ss ng, reset   to 1.
      mov ng_var_mask_zero = tf.math.equal(tensor_mov ng_var, 0)
      mov ng_var_mask_zero = tf.expand_d ms(mov ng_var_mask_zero, 0)
      m ss ng_ nput_norm = tf.w re(
        tf.math.log cal_and(dense_mask, mov ng_var_mask_zero),
        tf.ones_l ke(m ss ng_ nput_norm),
        m ss ng_ nput_norm
      )
     f  nput_dtype != self.data_type:
      m ss ng_ nput_norm = tf.cast(m ss ng_ nput_norm,  nput_dtype)
    return m ss ng_ nput_norm

  def _ nfer_pass(self,  nput, dense_mask,  nput_dtype, handle_s ngle):
    eps lon = tf.cast(self.eps lon,  nput_dtype)
    test ng_mov ng_ an = tf.cast(self.mov ng_ an,  nput_dtype)
    tensor_mov ng_std = tf.cast(tf.sqrt(self.mov ng_var),  nput_dtype)

    broad_ an = tf.expand_d ms(test ng_mov ng_ an, 0)
    tensor_batch_sub_ an =  nput - broad_ an

    tensor_batch_sub_ an = tf.w re(dense_mask,
                                    tensor_batch_sub_ an,
                                    tf.zeros_l ke(tensor_batch_sub_ an)
                            )
    tensor_mov ng_std_eps = tf.w re(tf.equal(tensor_mov ng_std, 0),
                                      tf.f ll(tf.shape(tensor_mov ng_std), eps lon),
                                      tensor_mov ng_std)
    m ss ng_ nput_norm = tensor_batch_sub_ an / tf.expand_d ms(tensor_mov ng_std_eps, 0)
     f handle_s ngle:
      #  f std==0 and value not m ss ng, reset   to 1.
      mov ng_var_broad = tf.expand_d ms(tensor_mov ng_std, 0)
      mov ng_var_mask_zero = tf.math.log cal_not(tf.cast(mov ng_var_broad, tf.bool))

      m ss ng_ nput_norm = tf.w re(tf.math.log cal_and(dense_mask, mov ng_var_mask_zero),
                          tf.ones_l ke(m ss ng_ nput_norm),
                          m ss ng_ nput_norm
                          )
    return m ss ng_ nput_norm

  def call(
    self,
     nput,
     s_tra n ng,
    dense_mask=None,
    zero_deb as=True,
    handle_s ngle=False):
    """
    Args:
    -----------
     nput:  B x D : float32/float64
      m ss ng value must be set to 0.
     s_tra n ng: bool
      tra n ng phase or test ng phase
    dense_mask: B x D : bool
      m ss ng value should be marked as 0, non-m ss ng as 1. sa  shape as  nput
    zero_deb as: bool
      b as correct on of t  mov ng average. (b ased towards 0  n t  beg nn ng.
      see adam paper. https://arx v.org/abs/1412.6980)
    handle_s ngle: bool
       f std==0, and feature  s not m ss ng value, set t  value to 1,  nstead of 0.
      T   s super rare  f  nput only cons sts of cont nous feature.
      But  f one-hot feature  s  ncluded,
      t y w ll all have sa  values 1,  n that case, make sure to set handle_s ngle to true.
    """

     f dense_mask  s None:
      dense_mask = tf.math.log cal_not(tf.equal( nput, 0))
     nput_dtype =  nput.dtype

     f  s_tra n ng:
       f  nput_dtype != self.data_type:
         nput = tf.cast( nput, self.data_type)
      return self._tra n ng_pass( nput, dense_mask,  nput_dtype, handle_s ngle, zero_deb as)
    else:
      return self._ nfer_pass( nput, dense_mask,  nput_dtype, handle_s ngle)


def zscore_normal zat on(
   nput,
   s_tra n ng,
  decay=0.9999,
  data_type=tf.float64,
  na =None,
  dense_mask=None,
  zero_deb as=True,
  handle_s ngle=False, **kwargs):
  """
  Args:
  ------------
   nput:  B x D : float32/float64
    m ss ng value must be set to 0.
   s_tra n ng: bool
    tra n ng phase or test ng phase
  decay:
    us ng large decay to  nclude longer mov ng  ans.
  data_type:
    use float64 to zprevent overflow dur ng var ance calculat on.
  na :
    Layer na 
  dense_mask: B x D : bool
    m ss ng value should be marked as 0, non-m ss ng as 1. sa  shape as  nput
  zero_deb as: bool
    b as correct on of t  mov ng average. (b ased towards 0  n t  beg nn ng.
    see adam paper. https://arx v.org/abs/1412.6980)
  handle_s ngle: bool
     f std==0, and feature  s not m ss ng value, set t  value to 1,  nstead of 0.
    T   s super rare  f  nput only cons sts of cont nous feature.
    But  f one-hot feature  s  ncluded,
    t y w ll all have sa  values 1,  n that case, make sure to set handle_s ngle to true.
  """

  norm_layer = ZscoreNormal zat on(decay=decay, data_type=data_type, na =na , **kwargs)
  return norm_layer( nput,
                     s_tra n ng,
                    dense_mask=dense_mask,
                    zero_deb as=zero_deb as,
                    handle_s ngle=handle_s ngle)
