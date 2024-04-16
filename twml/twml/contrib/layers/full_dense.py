# pyl nt: d sable=no- mber,argu nts-d ffer, attr bute-def ned-outs de- n 
"""
 mple nt ng Full Dense Layer
"""
from twml.layers  mport Layer

 mport tensorflow.compat.v1 as tf
from tensorflow.python.layers  mport core


class FullDense(Layer):
  """
  Full-connected, Dense  nput layer class.
  T  layer  mple nts t  operat on:

  .. code-block:: python

    outputs = act vat on( nputs.  ght + b as)

  W re ``act vat on``  s t  act vat on funct on passed as t  ``act vat on``
  argu nt ( f not ``None``), ``  ght``  s a   ghts matr x created by t  layer,
  and ``b as``  s a b as vector created by t  layer.

  Ho ver, t  layer breaks up ``  ght``  nto ``num_part  ons`` parts,
  for t  purpose of even d sr but on of   ghts across para ter servers
  for d str buted tra n ng.

  Note - T  layer  s created to allow d str buted tra n ng opt m zat ons,
  but can also be used for s ngle node tra n ng (e.g. hogw ld) w hout
  code mod f cat on

  Argu nts:
    output_s ze:
       nteger or Long, d  ns onal y of t  output space.
      ght_ n  al zer:
       n  al zer funct on for t    ght matr x.
      ght_regular zer:
      Regular zer funct on for t    ght matr x.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
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
    num_part  ons:
      Number of p eces to part  on t    ghts  nto. T  layer does
      column part  on ng of t    ghts, wh ch  s equ valent to
      process ng t   nput tensor w h mult ple fully connected layers
      of smaller output s ze, and t n concatenat ng t se outputs
    act vat on:
      Act vat on funct on (callable). Set   to None to ma nta n a l near act vat on.
    use_b as:
      Boolean w t r to  nclude a b as para ter  n t  layer
    b as_ n  al zer:
       n  al zer funct on for t  b as.
    b as_regular zer:
      Regular zer funct on for t  b as.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
    act v y_regular zer:
      Regular zer funct on for t  output.
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
      ghts:
      l st of underly ng   ght and b as matr x components. no guarantee on order of ele nts
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
               num_part  ons=3,
               act vat on=None,
               use_b as=True,
               b as_ n  al zer=tf.zeros_ n  al zer(),
               b as_regular zer=None,
               act v y_regular zer=None,
               tra nable=True,
               na =None,
               **kwargs):
    super(FullDense, self).__ n __(tra nable=tra nable, na =na , **kwargs)
    self._output_s zes = self._get_output_part  on_s zes(output_s ze, num_part  ons)
    self._un s = output_s ze
    self._act vat on = act vat on
    self._  ght_ n  al zer =   ght_ n  al zer
    self._b as_ n  al zer = b as_ n  al zer
    self._  ght_regular zer =   ght_regular zer
    self._b as_regular zer = b as_regular zer
    self._  ght_constra nt =   ght_constra nt
    self._b as_constra nt = b as_constra nt
    self._use_b as = use_b as
    # NOTE - many  n  al zers depend on fan_ n and fan_out
    #      - as such,  n  al zat on  re may be d fferent than
    #      - for a non-part  oned FullDense
    self._parts = [core.Dense(un s=out_s ze,
                              act vat on=act vat on,
                              use_b as=use_b as,
                              kernel_ n  al zer=  ght_ n  al zer,
                              b as_ n  al zer=b as_ n  al zer,
                              kernel_regular zer=  ght_regular zer,
                              b as_regular zer=b as_regular zer,
                              act v y_regular zer=act v y_regular zer,
                              kernel_constra nt=  ght_constra nt,
                              b as_constra nt=b as_constra nt,
                              tra nable=tra nable,
                              na =na ,
                              **kwargs) for out_s ze  n self._output_s zes]

  @stat c thod
  def _get_output_part  on_s zes(out_s ze, num_parts):
    """ Returns t  appropr ate output s zes of t  part  ons """
    boundar es = [out_s ze * n // num_parts for n  n range(num_parts + 1)]
    return [k - j for j, k  n z p(boundar es[:], boundar es[1:])]

  def bu ld(self,  nput_shapes):
    """ Create t  appropr ately s zed   ghts and b ases  n each layer part  on """
     f  s nstance( nput_shapes, (l st, tuple)):
       nput_shape =  nput_shapes[0]
       s_compat ble = True
      for ot r_shape  n  nput_shapes[1:]:
         s_compat ble &=  nput_shape. s_compat ble_w h(ot r_shape)
       f not  s_compat ble:
        ra se ValueError(" nput shapes %s are not compat ble." %  nput_shapes)
    else:
       nput_shape =  nput_shapes

    for part  n self._parts:
      part.bu ld( nput_shape)

    self.bu lt = True

  @property
  def un s(self):
    """ Returns t  number of output un s of t  layer """
    return self._un s

  @property
  def output_s ze(self):
    """ Returns t  number of output un s of t  layer """
    return self._un s

  @property
  def act vat on(self):
    """ Returns t  act vat on funct on """
    return self._act vat on

  @property
  def   ght_ n  al zer(self):
    """ Returns t    ght_ n  al zer """
    return self._  ght_ n  al zer

  @property
  def   ght_regular zer(self):
    """ Returns t    ght_regular zer """
    return self._  ght_regular zer

  @property
  def   ght_constra nt(self):
    """ Returns t    ght_constra nt """
    return self._  ght_constra nt

  @property
  def b as_ n  al zer(self):
    """ Returns t  b as_ n  al zer """
    return self._b as_ n  al zer

  @property
  def b as_regular zer(self):
    """ Returns t  b as_regular zer """
    return self._b as_regular zer

  @property
  def b as_constra nt(self):
    """ Returns t  b as_constra nt """
    return self._b as_constra nt

  @property
  def use_b as(self):
    """ Returns w t r a b as  s used  n t  layer """
    return self._use_b as

  @property
  def tra nable_var ables(self):
    """ Returns t  tra nable var ables of t  layer """
    tra nable_vars = []
    for pt  n self._parts:
      tra nable_vars += pt.tra nable_var ables
    return tra nable_vars

  @property
  def tra nable_  ghts(self):
    """ Returns t  tra nable var ables of t  layer """
    return self.tra nable_var ables

  @property
  def non_tra nable_var ables(self):
    """ Returns t  non-tra nable var ables of t  layer """
    non_tra nable_vars = []
    for pt  n self._parts:
      non_tra nable_vars += pt.non_tra nable_var ables
    return non_tra nable_vars

  @property
  def non_tra nable_  ghts(self):
    """ Returns t  non-tra nable var ables of t  layer """
    return self.non_tra nable_var ables

  @property
  def var ables(self):
    """ Returns a l st of all   ghts and b ases  n t  layer """
    layer_vars = []
    for pt  n self._parts:
      layer_vars += pt.  ghts
    return layer_vars

  @property
  def   ghts(self):
    """ Returns a l st of all   ghts and b ases  n t  layer """
    return self.var ables

  @property
  def dtype(self):
    """ Returns t  dtype of t  layers   ghts """
    return self._parts[0].dtype

  def call(self,  nputs, **kwargs):  # pyl nt: d sable=unused-argu nt
    """T  log c of t  layer l ves  re.

    Argu nts:
       nputs:
        A dense Tensor or a l st of such.
         f ` nputs`  s a l st, all tensors must have sa  `dense_shape`.

    Returns:
      -  f ` nputs`  s `SparseTensor`, t n returns `b as +  nputs * dense_b`.
      -  f ` nputs`  s a `l st[SparseTensor`, t n returns
       `b as + accumulate_n([sp_a * dense_b for sp_a  n  nputs])`.
    """
     f not  s nstance( nputs, (l st, tuple)):
       nputs = [ nputs]

    outputs = []
    for  np  n  nputs:
      part_outputs = [part( np) for part  n self._parts]
      outputs.append(tf.concat(part_outputs, ax s=-1))

    return tf.accumulate_n(outputs)


def full_dense( nputs, output_s ze,
                 ght_ n  al zer=None,
                 ght_regular zer=None,
                 ght_constra nt=None,
               b as_constra nt=None,
               num_part  ons=3,
               act vat on=None,
               use_b as=True,
               b as_ n  al zer=tf.zeros_ n  al zer(),
               b as_regular zer=None,
               act v y_regular zer=None,
               tra nable=True,
               na =None,
               reuse=None,
               **kwargs):
  """Funct onal  nterface for t  fully-connected dense- nput layer.
  T  layer  mple nts t  operat on:
  `outputs = act vat on( nputs.  ght + b as)`
  W re `act vat on`  s t  act vat on funct on passed as t  `act vat on`
  argu nt ( f not `None`), `  ght`  s a   ghts matr x created by t  layer,
  and `b as`  s a b as vector created by t  layer
  (only  f `use_b as`  s `True`).

  Ho ver, t  layer breaks up ``  ght``  nto ``num_part  ons`` parts,
  for t  purpose of even d sr but on of   ghts across para ter servers
  for d str buted tra n ng.

  Note - T  layer  s created to allow d str buted tra n ng opt m zat ons,
  but can also be used for s ngle node tra n ng (e.g. hogw ld) w hout
  code mod f cat on

  Argu nts:
     nputs: Tensor  nput.
    output_s ze:  nteger or Long, d  ns onal y of t  output space.
      ght_ n  al zer:  n  al zer funct on for t    ght matr x.
       f `None` (default),   ghts are  n  al zed us ng t  default
       n  al zer used by `tf.get_var able`.
      ght_regular zer:
      Regular zer funct on for t    ght matr x.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
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
    num_part  ons:
      Number of p eces to part  on t    ghts  nto. T  layer does
      column part  on ng of t    ghts, wh ch  s equ valent to
      process ng t   nput tensor w h mult ple fully connected layers
      of smaller output s ze, and t n concatenat ng t se outputs
    act vat on: Act vat on funct on (callable). Set   to None to ma nta n a
      l near act vat on.
    use_b as: Boolean, w t r t  layer uses a b as.
    b as_ n  al zer:
       n  al zer funct on for t  b as.
    b as_regular zer:
      Regular zer funct on for t  b as.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
    act v y_regular zer:
      Regular zer funct on for t  output.
    tra nable:
      Boolean,  f `True` also add var ables to t  graph collect on
      `GraphKeys.TRA NABLE_VAR ABLES` (see `tf.Var able`).
    na :
      Str ng, t  na  of t  layer.
    reuse:
      Boolean, w t r to reuse t    ghts of a prev ous layer
      by t  sa  na .

  Returns:
    Output tensor w h shape ` nputs.shape[:-1] + [output_s ze]`.
  """
   f not  s nstance( nputs, (l st, tuple)):
     nputs = [ nputs]

  dtype =  nputs[0].dtype.base_dtype

  layer = FullDense(output_s ze=output_s ze,
                      ght_ n  al zer=  ght_ n  al zer,
                      ght_regular zer=  ght_regular zer,
                      ght_constra nt=  ght_constra nt,
                    b as_constra nt=b as_constra nt,
                    num_part  ons=num_part  ons,
                    act vat on=act vat on,
                    use_b as=use_b as,
                    b as_ n  al zer=b as_ n  al zer,
                    b as_regular zer=b as_regular zer,
                    act v y_regular zer=act v y_regular zer,
                    tra nable=tra nable,
                    na =na ,
                    dtype=dtype,
                    _scope=na ,
                    _reuse=reuse,
                    **kwargs)

  return layer( nputs)
