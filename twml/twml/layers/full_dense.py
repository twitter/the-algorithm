# pyl nt: d sable=no- mber,argu nts-d ffer, attr bute-def ned-outs de- n 
"""
 mple nt ng Full Dense Layer
"""
from tensorflow.python.layers  mport core as core_layers
from tensorflow.python.ops  mport  n _ops
from tensorflow.python.fra work  mport tensor_shape
from tensorflow.python.keras.eng ne.base_layer  mport  nputSpec
 mport tensorflow.compat.v1 as tf


class FullDense(core_layers.Dense):
  """
  Densely-connected layer class.
  T   s wrapp ng tensorflow.python.layers.core.Dense
  T  layer  mple nts t  operat on:

  .. code-block:: python

    outputs = act vat on( nputs.  ght + b as)

  W re ``act vat on``  s t  act vat on funct on passed as t  ``act vat on``
  argu nt ( f not ``None``), ``  ght``  s a   ghts matr x created by t  layer,
  and ``b as``  s a b as vector created by t  layer.

  Argu nts:
    output_s ze:
       nteger or Long, d  ns onal y of t  output space.
    act vat on:
      Act vat on funct on (callable). Set   to None to ma nta n a l near act vat on.
      ght_ n  al zer:
       n  al zer funct on for t    ght matr x.
    b as_ n  al zer:
       n  al zer funct on for t  b as.
      ght_regular zer:
      Regular zer funct on for t    ght matr x.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
    b as_regular zer:
      Regular zer funct on for t  b as.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
    act v y_regular zer:
      Regular zer funct on for t  output.
      ght_constra nt:
      An opt onal project on funct on to be appl ed to t 
        ght after be ng updated by an `Opt m zer` (e.g. used to  mple nt
      norm constra nts or value constra nts for layer   ghts). T  funct on
      must take as  nput t  unprojected var able and must return t 
      projected var able (wh ch must have t  sa  shape). Constra nts are
      not safe to use w n do ng asynchronous d str buted tra n ng.
    b as_constra nt:
      An opt onal project on funct on to be appl ed to t 
      b as after be ng updated by an `Opt m zer`.
    tra nable:
      Boolean,  f `True` also add var ables to t  graph collect on
      ``GraphKeys.TRA NABLE_VAR ABLES`` (see `tf.Var able
      <https://www.tensorflow.org/vers ons/master/ap _docs/python/tf/Var able>`_).
    na :
      Str ng, t  na  of t  layer. Layers w h t  sa  na  w ll
      share   ghts, but to avo d m stakes   requ re ``reuse=True``  n such cases.

  Propert es:
    output_s ze:
      Python  nteger, d  ns onal y of t  output space.
    act vat on:
      Act vat on funct on (callable).
      ght_ n  al zer:
       n  al zer  nstance (or na ) for t    ght matr x.
    b as_ n  al zer:
       n  al zer  nstance (or na ) for t  b as.
      ght:
        ght matr x (TensorFlow var able or tensor). (  ght)
    b as:
      B as vector,  f appl cable (TensorFlow var able or tensor).
      ght_regular zer:
      Regular zer  nstance for t    ght matr x (callable)
    b as_regular zer:
      Regular zer  nstance for t  b as (callable).
    act v y_regular zer:
      Regular zer  nstance for t  output (callable)
      ght_constra nt:
      Constra nt funct on for t    ght matr x.
    b as_constra nt:
      Constra nt funct on for t  b as.

  """

  def __ n __(self, output_s ze,
                 ght_ n  al zer=None,
                 ght_regular zer=None,
                 ght_constra nt=None,
               b as_constra nt=None,
               num_part  ons=None,
               **kwargs):
    super(FullDense, self).__ n __(un s=output_s ze,
                                    kernel_ n  al zer=  ght_ n  al zer,
                                    kernel_regular zer=  ght_regular zer,
                                    kernel_constra nt=  ght_constra nt,
                                    **kwargs)
    self._num_part  ons = num_part  ons

  def bu ld(self,  nput_shape):
    '''
    code adapted from TF 1.12 Keras Dense layer:
    https://g hub.com/tensorflow/tensorflow/blob/r1.12/tensorflow/python/keras/layers/core.py#L930-L956
    '''
     nput_shape = tensor_shape.TensorShape( nput_shape)
     f  nput_shape[-1]  s None:
      ra se ValueError('T  last d  ns on of t   nputs to `Dense` '
                       'should be def ned. Found `None`.')
    self. nput_spec =  nputSpec(m n_nd m=2,
                                axes={-1:  nput_shape[-1]})

    part  oner = None
     f self._num_part  ons:
      part  oner = tf.f xed_s ze_part  oner(self._num_part  ons)

    self.kernel = self.add_  ght(
        'kernel',
        shape=[ nput_shape[-1], self.un s],
         n  al zer=self.kernel_ n  al zer,
        regular zer=self.kernel_regular zer,
        constra nt=self.kernel_constra nt,
        dtype=self.dtype,
        part  oner=part  oner,
        tra nable=True)

     f self.use_b as:
      self.b as = self.add_  ght(
          'b as',
          shape=[self.un s, ],
           n  al zer=self.b as_ n  al zer,
          regular zer=self.b as_regular zer,
          constra nt=self.b as_constra nt,
          dtype=self.dtype,
          tra nable=True)
    else:
      self.b as = None
    self.bu lt = True

  @property
  def output_s ze(self):
    """
    Returns output_s ze
    """
    return self.un s

  @property
  def   ght(self):
    """
    Returns   ght
    """
    return self.kernel

  @property
  def   ght_regular zer(self):
    """
    Returns   ght_regular zer
    """
    return self.kernel_regular zer

  @property
  def   ght_ n  al zer(self):
    """
    Returns   ght_ n  al zer
    """
    return self.kernel_ n  al zer

  @property
  def   ght_constra nt(self):
    """
    Returns   ght_constra nt
    """
    return self.kernel_constra nt


def full_dense( nputs, output_s ze,
               act vat on=None,
               use_b as=True,
                 ght_ n  al zer=None,
               b as_ n  al zer= n _ops.zeros_ n  al zer(),
                 ght_regular zer=None,
               b as_regular zer=None,
               act v y_regular zer=None,
                 ght_constra nt=None,
               b as_constra nt=None,
               tra nable=True,
               na =None,
               num_part  ons=None,
               reuse=None):
  """Funct onal  nterface for t  densely-connected layer.
  T  layer  mple nts t  operat on:
  `outputs = act vat on( nputs.  ght + b as)`
  W re `act vat on`  s t  act vat on funct on passed as t  `act vat on`
  argu nt ( f not `None`), `  ght`  s a   ghts matr x created by t  layer,
  and `b as`  s a b as vector created by t  layer
  (only  f `use_b as`  s `True`).

  Argu nts:
     nputs: Tensor  nput.
    un s:  nteger or Long, d  ns onal y of t  output space.
    act vat on: Act vat on funct on (callable). Set   to None to ma nta n a
      l near act vat on.
    use_b as: Boolean, w t r t  layer uses a b as.
      ght_ n  al zer:  n  al zer funct on for t    ght matr x.
       f `None` (default),   ghts are  n  al zed us ng t  default
       n  al zer used by `tf.get_var able`.
    b as_ n  al zer:
       n  al zer funct on for t  b as.
      ght_regular zer:
      Regular zer funct on for t    ght matr x.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
    b as_regular zer:
      Regular zer funct on for t  b as.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
    act v y_regular zer:
      Regular zer funct on for t  output.
      ght_constra nt:
      An opt onal project on funct on to be appl ed to t 
        ght after be ng updated by an `Opt m zer` (e.g. used to  mple nt
      norm constra nts or value constra nts for layer   ghts). T  funct on
      must take as  nput t  unprojected var able and must return t 
      projected var able (wh ch must have t  sa  shape). Constra nts are
      not safe to use w n do ng asynchronous d str buted tra n ng.
    b as_constra nt:
      An opt onal project on funct on to be appl ed to t 
      b as after be ng updated by an `Opt m zer`.
    tra nable:
      Boolean,  f `True` also add var ables to t  graph collect on
      `GraphKeys.TRA NABLE_VAR ABLES` (see `tf.Var able`).
    na :
      Str ng, t  na  of t  layer.
    reuse:
      Boolean, w t r to reuse t    ghts of a prev ous layer
      by t  sa  na .

  Returns:
    Output tensor t  sa  shape as ` nputs` except t  last d  ns on  s of
    s ze `un s`.

  Ra ses:
    ValueError:  f eager execut on  s enabled.
  """
  layer = FullDense(output_s ze,
                    act vat on=act vat on,
                    use_b as=use_b as,
                      ght_ n  al zer=  ght_ n  al zer,
                    b as_ n  al zer=b as_ n  al zer,
                      ght_regular zer=  ght_regular zer,
                    b as_regular zer=b as_regular zer,
                    act v y_regular zer=act v y_regular zer,
                      ght_constra nt=  ght_constra nt,
                    b as_constra nt=b as_constra nt,
                    tra nable=tra nable,
                    na =na ,
                    dtype= nputs.dtype.base_dtype,
                    num_part  ons=num_part  ons,
                    _scope=na ,
                    _reuse=reuse)
  return layer.apply( nputs)
