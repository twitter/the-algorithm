# pyl nt: d sable=no- mber, attr bute-def ned-outs de- n , dupl cate-code
"""
Conta ns t  twml.layers.SparseMaxNorm layer.
"""
from .layer  mport Layer

from l btwml  mport OPL B
 mport tensorflow.compat.v1 as tf
 mport twml


class SparseMaxNorm(Layer):
  """
  Computes a max-normal zat on and adds b as to t  sparse_ nput,
  forwards that through a sparse aff ne transform follo d
  by an non-l near act vat on on t  result ng dense representat on.

  T  layer has two para ters, one of wh ch learns through grad ent descent:
    b as_x (opt onal):
      vector of shape [ nput_s ze]. Learned through grad ent descent.
    max_x:
      vector of shape [ nput_s ze]. Holds t  max mas of  nput ``x`` for normal zat on.
      E  r cal brated through SparseMaxNorm cal brator, or cal brated onl ne, or both.

  T  pseudo-code for t  layer looks l ke:

  .. code-block:: python

    abs_x = abs(x)
    nor d_x = cl p_by_value(x / max_x, -1, 1)
    b ased_x = nor d_x + b as_x
    return b ased


  Args:
    max_x_ n  al zer:
       n  al zer vector of shape [ nput_s ze] used by var able `max_x`
    b as_x_ n  al zer:
       n  al zer vector of shape [ nput_s ze] used by para ter `b as_x`
     s_tra n ng:
      Are   tra n ng t  layer to learn t  normal zat on max mas.
       f set to True, max_x w ll be able to learn. T   s  ndependent of b as_x
    eps lon:
      T  m n mum value used for max_x. Defaults to 1E-5.
    use_b as:
      Default True. Set to False to not use a b as term.

  Returns:
    A layer represent ng t  output of t  sparse_max_norm transformat on.
   """

  def __ n __(
          self,
           nput_s ze=None,
          max_x_ n  al zer=None,
          b as_x_ n  al zer=None,
           s_tra n ng=True,
          eps lon=1E-5,
          use_b as=True,
          **kwargs):

    super(SparseMaxNorm, self).__ n __(**kwargs)
     f  nput_s ze:
      ra se ValueError(' nput_s ze  s deprecated -    s now automat cally \
                        nferred from y   nput.')
     f max_x_ n  al zer  s None:
      max_x_ n  al zer = tf.zeros_ n  al zer()
    self.max_x_ n  al zer = max_x_ n  al zer

    self._use_b as = use_b as
     f use_b as:
       f b as_x_ n  al zer  s None:
        b as_x_ n  al zer = tf.zeros_ n  al zer()
      self.b as_x_ n  al zer = b as_x_ n  al zer

    self.eps lon = eps lon
    self. s_tra n ng =  s_tra n ng

  def bu ld(self,  nput_shape):  # pyl nt: d sable=unused-argu nt
    """Creates t  max_x and b as_x tf.Var ables of t  layer."""

    self.max_x = self.add_var able(
      'max_x',
       n  al zer=self.max_x_ n  al zer,
      shape=[ nput_shape[1]],
      dtype=tf.float32,
      tra nable=False)

     f self._use_b as:
      self.b as_x = self.add_var able(
        'b as_x',
         n  al zer=self.b as_x_ n  al zer,
        shape=[ nput_shape[1]],
        dtype=tf.float32,
        tra nable=True)

    self.bu lt = True

  def compute_output_shape(self,  nput_shape):
    """Computes t  output shape of t  layer g ven t   nput shape.

    Args:
       nput_shape: A (poss bly nested tuple of) `TensorShape`.    need not
        be fully def ned (e.g. t  batch s ze may be unknown).

    Ra ses Not mple ntedError.

    """
    ra se Not mple ntedError

  def _call(self,  nputs, **kwargs):  # pyl nt: d sable=unused-argu nt
    """
    T  forward propagat on log c of t  layer l ves  re.

    Argu nts:
      sparse_ nput:
        A 2D ``tf.SparseTensor`` of dense_shape ``[batch_s ze,  nput_s ze]``
    Returns:
       A ``tf.SparseTensor`` represent ng t  output of t  max_norm transformat on, t  can
       be fed  nto twml.layers.FullSparse  n order to be transfor d  nto a ``tf.Tensor``.
    """

     f  s nstance( nputs, twml.SparseTensor):
       nputs =  nputs.to_tf()
    el f not  s nstance( nputs, tf.SparseTensor):
      ra se TypeError("T   nputs must be of type tf.SparseTensor or twml.SparseTensor")

     nd ces_x =  nputs. nd ces[:, 1]
    values_x =  nputs.values

     f self. s_tra n ng  s False:
      normal zed_x = OPL B.sparse_max_norm_ nference(self.max_x,
                                                      nd ces_x,
                                                     values_x,
                                                     self.eps lon)

      update_op = tf.no_op()
    else:
      max_x, normal zed_x = OPL B.sparse_max_norm_tra n ng(self.max_x,
                                                            nd ces_x,
                                                           values_x,
                                                           self.eps lon)

      update_op = tf.ass gn(self.max_x, max_x)

    w h tf.control_dependenc es([update_op]):
      normal zed_x = tf.stop_grad ent(normal zed_x)

    # add  nput b as
     f self._use_b as:
      normal zed_x = normal zed_x + tf.gat r(self.b as_x,  nd ces_x)

    # convert back to sparse tensor
    return tf.SparseTensor( nputs. nd ces, normal zed_x,  nputs.dense_shape)

  def call(self,  nputs, **kwargs):  # pyl nt: d sable=unused-argu nt
    """
    T  forward propagat on log c of t  layer l ves  re.

    Argu nts:
      sparse_ nput:
        A 2D ``tf.SparseTensor`` of dense_shape ``[batch_s ze,  nput_s ze]``
    Returns:
       A ``tf.SparseTensor`` represent ng t  output of t  max_norm transformat on, t  can
       be fed  nto twml.layers.FullSparse  n order to be transfor d  nto a ``tf.Tensor``.
    """
    w h tf.dev ce(self.max_x.dev ce):
      return self._call( nputs, **kwargs)

# For backwards compat bl y and also because   don't want to change all t  tests.
MaxNorm = SparseMaxNorm


def sparse_max_norm( nputs,
                     nput_s ze=None,
                    max_x_ n  al zer=None,
                    b as_x_ n  al zer=None,
                     s_tra n ng=True,
                    eps lon=1E-5,
                    use_b as=True,
                    na =None,
                    reuse=None):
  """
  Funct onal  nteface to SparseMaxNorm.

  Args:
     nputs:
      A sparse tensor (can be twml.SparseTensor or tf.SparseTensor)
     nput_s ze:
      number of  nput un s
    max_x_ n  al zer:
       n  al zer vector of shape [ nput_s ze] used by var able `max_x`
    b as_x_ n  al zer:
       n  al zer vector of shape [ nput_s ze] used by para ter `b as_x`
     s_tra n ng:
      Are   tra n ng t  layer to learn t  normal zat on max mas.
       f set to True, max_x w ll be able to learn. T   s  ndependent of b as_x
    eps lon:
      T  m n mum value used for max_x. Defaults to 1E-5.
    use_b as:
      Default True. Set to False to not use a b as term.

  Returns:
    Output after normal z ng w h t  max value.
   """
   f  nput_s ze:
    ra se ValueError(' nput_s ze  s deprecated -    s now automat cally \
                      nferred from y   nput.')

   f  s nstance( nputs, twml.SparseTensor):
     nputs =  nputs.to_tf()

  layer = SparseMaxNorm(max_x_ n  al zer=max_x_ n  al zer,
                        b as_x_ n  al zer=b as_x_ n  al zer,
                         s_tra n ng= s_tra n ng,
                        eps lon=eps lon,
                        use_b as=use_b as,
                        na =na ,
                        _scope=na ,
                        _reuse=reuse)
  return layer( nputs)
