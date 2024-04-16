# pyl nt: d sable=no- mber, argu nts-d ffer, attr bute-def ned-outs de- n , unused-argu nt
"""
 mple nt ng factor zat on Layer
"""

from tw ter.deepb rd.sparse.sparse_ops  mport _pad_empty_outputs

 mport tensorflow.compat.v1 as tf
 mport twml
from twml.layers.layer  mport Layer


class Factor zat onMach ne(Layer):
  """factor zat on mach ne layer class.
  T  layer  mple nts t  factor zat on mach ne operat on.
  T  paper  s "Factor zat on Mach nes" by Steffen Rendle.
  TDD: go/tf-fm-tdd

  Argu nts:
    num_latent_var ables:
      num of latent var ables
      T  number of para ter  n t  layer  s num_latent_var ables x n w re n  s number of
       nput features.
      ght_ n  al zer:
       n  al zer funct on for t    ght matr x.
      T  argu nt defaults to zeros_ n  al zer().
      T   s val d w n t  FullSparse  s t  f rst layer of
      para ters but should be changed ot rw se.
      ght_regular zer:
      Regular zer funct on for t    ght matr x.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
    act vat on:
      Act vat on funct on (callable). Set   to None to ma nta n a l near act vat on.
    tra nable:
      Boolean,  f `True` also add var ables to t  graph collect on
      ``GraphKeys.TRA NABLE_VAR ABLES`` (see `tf.Var able
      <https://www.tensorflow.org/vers ons/master/ap _docs/python/tf/Var able>`_).
    na :
      Str ng, t  na  of t  layer. Layers w h t  sa  na  w ll
      share   ghts, but to avo d m stakes   requ re ``reuse=True``  n such cases.
    use_sparse_grads:
      Boolean,  f `True` do sparse mat mul w h `embedd ng_lookup_sparse`, wh ch w ll
      make grad ents to   ght matr x also sparse  n backward pass. T  can lead to non-tr v al
      speed up at tra n ng t   w n  nput_s ze  s large and opt m zer handles sparse grad ents
      correctly (eg. w h SGD or LazyAdamOpt m zer).  f   ght matr x  s small,  's recom nded
      to set t  flag to `False`; for most use cases of FullSparse, ho ver,   ght matr x w ll
      be large, so  's better to set   to `True`
    use_b nary_values:
      Assu  all non zero values are 1. Defaults to False.
      T  can  mprove tra n ng  f used  n conjunct on w h MDL.
      T  para ter can also be a l st of b nary values  f ` nputs` passed to `call` a l st.
  """

  def __ n __(self,
    num_latent_var ables=10,
      ght_ n  al zer=None,
    act vat on=None,
    tra nable=True,
    na =None,
    use_sparse_grads=True,
    use_b nary_values=False,
      ght_regular zer=None,
    substract_self_cross=True,
    **kwargs):
    super(Factor zat onMach ne, self).__ n __(tra nable=tra nable, na =na , **kwargs)

     f   ght_ n  al zer  s None:
        ght_ n  al zer = tf.zeros_ n  al zer()
    self.  ght_ n  al zer =   ght_ n  al zer
    self.num_latent_var ables = num_latent_var ables
    self.act vat on = act vat on
    self.use_sparse_grads = use_sparse_grads
    self.use_b nary_values = use_b nary_values
    self.  ght_regular zer =   ght_regular zer
    self.substract_self_cross = substract_self_cross

  def bu ld(self,  nput_shape):
    """
    creates``  ght`` Var able of shape``[ nput_s ze, num_latent_var ables]``.

    """

    shape = [ nput_shape[1], self.num_latent_var ables]

    # T re  s a 2GB l m at on for each tensor because of protobuf.
    # 2**30  s 1GB. 2 * (2**30)  s 2GB.
    dtype = tf.as_dtype(self.dtype)
    requested_s ze =  nput_shape[1] * self.num_latent_var ables * dtype.s ze
     f (requested_s ze >= 2**31):
      ra se ValueError("  ght tensor can not be larger than 2GB. " %
                       "Requested D  ns ons(%d, %d) of type %s (%d bytes total)"
                       ( nput_shape[1], self.num_latent_var ables, dtype.na ))

     f not callable(self.  ght_ n  al zer):
      shape = None

    # dense tensor
    self.  ght = self.add_var able(
      '  ght',
       n  al zer=self.  ght_ n  al zer,
      regular zer=self.  ght_regular zer,
      shape=shape,
      dtype=self.dtype,
      tra nable=True,
    )

    self.bu lt = True

  def compute_output_shape(self,  nput_shape):
    """Computes t  output shape of t  layer g ven t   nput shape.

    Args:
       nput_shape: A (poss bly nested tuple of) `TensorShape`.    need not
        be fully def ned (e.g. t  batch s ze may be unknown).

    Ra ses Not mple ntedError.

    """
    ra se Not mple ntedError

  def call(self,  nputs, **kwargs):  # pyl nt: d sable=unused-argu nt
    """T  log c of t  layer l ves  re.

    Argu nts:
       nputs:
        A SparseTensor
    Returns:
      -  f ` nputs`  s `SparseTensor`, t n returns a number w h cross  nfo
    """
    # T  follow ng are g ven:
    # -  nputs  s a sparse tensor,   call   sp_x.
    # - T  dense_v tensor  s a dense matr x, whose row  
    #   corresponds to t  vector V_ .
    #     ghts has shape [num_features, k]
    sp_x =  nputs
     f  s nstance( nputs, twml.SparseTensor):
      sp_x =  nputs.to_tf()
    el f not  s nstance(sp_x, tf.SparseTensor):
      ra se TypeError("T  sp_x must be of type tf.SparseTensor or twml.SparseTensor")

     nd ces = sp_x. nd ces[:, 1]
    batch_ ds = sp_x. nd ces[:, 0]
    values = tf.reshape(sp_x.values, [-1, 1], na =self.na )
     f self.use_sparse_grads:
      v = tf.nn.embedd ng_lookup(self.  ght,  nd ces)
      #  f (self.use_b nary_values):
      #   values = tf.ones(tf.shape(values), dtype=values.dtype)
      v_t  s_x = v * values
      # F rst term: Sum_k  [Sum_  (v_ k * x_ )]^2
      all_crosses = tf.seg nt_sum(v_t  s_x, batch_ ds, na =self.na )
      all_crosses_squared = tf.reduce_sum((all_crosses * all_crosses), 1)

       f self.substract_self_cross:
        # Second term: Sum_k Sum_  [ (v_ k * x_ )^2 ]
        v_t  s_x_2 = v_t  s_x**2
        self_crosses = tf.reduce_sum(tf.seg nt_sum(v_t  s_x_2, batch_ ds, na =self.na ), 1)
        outputs = all_crosses_squared - self_crosses
      else:
        outputs = all_crosses_squared
    else:
      # need to c ck  f pred ct on  s faster w h code below
      crossTerm = tf.reduce_sum((tf.sparse_tensor_dense_matmul(sp_x, self.  ght)**2), 1)

       f self.substract_self_cross:
        # compute self-cross term
        self_crossTerm = tf.reduce_sum(tf.seg nt_sum((tf.gat r(self.  ght,  nd ces) * values)**2, batch_ ds), 1)
        outputs = crossTerm - self_crossTerm
      else:
        outputs = crossTerm

     f self.act vat on  s not None:
      outputs = self.act vat on(outputs)

    outputs = tf.reshape(outputs, [-1, 1], na =self.na )
    outputs = _pad_empty_outputs(outputs, tf.cast(sp_x.dense_shape[0], tf. nt32))
    # set more expl c  and stat c shape to avo d shape  nference error
    # valueError: T  last d  ns on of t   nputs to `Dense` should be def ned. Found `None`
    outputs.set_shape([None, 1])
    return outputs
