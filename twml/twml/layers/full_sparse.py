# pyl nt: d sable=no- mber, argu nts-d ffer, attr bute-def ned-outs de- n , unused-argu nt
"""
 mple nt ng Full Sparse Layer
"""

 mport math

from tw ter.deepb rd.sparse  mport sparse_dense_matmul

from .layer  mport Layer

 mport tensorflow.compat.v1 as tf
 mport twml


class FullSparse(Layer):
  """Fully-sparse layer class.
  T  layer  mple nts t  operat on:

  .. code-block:: python

    outputs = act vat on( nputs.  ght + b as)

  Argu nts:
    output_s ze:
      Long or  nteger, d  ns onal y of t  output space.
     nput_s ze:
      T  number of  nput un s. (Deprecated)
      ght_ n  al zer:
       n  al zer funct on for t    ght matr x.
      T  argu nt defaults to zeros_ n  al zer().
      T   s val d w n t  FullSparse  s t  f rst layer of
      para ters but should be changed ot rw se.
      ght_regular zer:
      Regular zer funct on for t    ght matr x.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
    b as_regular zer:
      Regular zer funct on for t  b as.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect
    act vat on:
      Act vat on funct on (callable). Set   to None to ma nta n a l near act vat on.
    b as_ n  al zer:
       n  al zer funct on for t  b as.
      T  argu nt defaults to tf.constant_ n  al zer(1/output_s ze)
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
    num_part  ons:
      Number of part  ons to use for t    ght var able. Defaults to 1.
    part  on_ax s:
       f num_part  ons  s spec f ed, t  part  on ax s for t    ght var able
      Defaults to 0 (part  on by row).
      Must be 0 (row) or 1 (column)
    use_b nary_values:
      Assu  all non zero values are 1. Defaults to False.
      T  can  mprove tra n ng  f used  n conjunct on w h MDL.
      T  para ter can also be a l st of b nary values  f ` nputs` passed to `call` a l st.
    use_compress on:
      Default False. Set True to enable data compress on techn ques for
      opt m zat on of network traff c for d str buted tra n ng.
    use_b nary_sparse_dense_matmul:
       f b nary sparse dense matmul op  s to be used.   w ll only be enabled  f
      `use_b nary_values`  s set true.   only should be used for  nference, best pract ce  s
      to set `use_b nary_sparse_dense_matmul = not  s_tra n ng`.
  """

  def __ n __(self,
               output_s ze,
                nput_s ze=None,
                 ght_ n  al zer=None,
               act vat on=None,
               b as_ n  al zer=None,
               tra nable=True,
               na =None,
               use_sparse_grads=True,
               num_part  ons=None,
               part  on_ax s=0,
               use_b nary_values=False,
               b as_regular zer=None,
                 ght_regular zer=None,
               use_compress on=False,
               use_b nary_sparse_dense_matmul=False,
               **kwargs):
    super(FullSparse, self).__ n __(tra nable=tra nable, na =na , **kwargs)
    # TODO - remove  nput_s ze warn ng.
     f  nput_s ze:
      ra se ValueError(' nput_s ze  s deprecated -    s now automat cally \
                        nferred from y   nput.')

    # T  b as  n  al zat on and   ghts  n  al zat on  s set to match v1's  mple ntat on.
     f b as_ n  al zer  s None:
      b as_ n  al zer = tf.constant_ n  al zer(1 / output_s ze)
    #   ghts  n  al zat on  s set to 0s. T   s safe for full sparse layers because
    #   are supposed to learn y  embedd ng from t  label.
     f   ght_ n  al zer  s None:
        ght_ n  al zer = tf.zeros_ n  al zer()
    self.  ght_ n  al zer =   ght_ n  al zer
    self.b as_ n  al zer = b as_ n  al zer
    self.output_s ze = output_s ze
    self.act vat on = act vat on
    self.use_sparse_grads = use_sparse_grads
    self.num_part  ons = num_part  ons
     f part  on_ax s != 0 and part  on_ax s != 1:
      ra se ValueError('part  on_ax s must be 0 or 1')
    self.part  on_ax s = part  on_ax s
    self.use_b nary_values = use_b nary_values
    self.  ght_regular zer =   ght_regular zer
    self.b as_regular zer = b as_regular zer
    self._use_compress on = use_compress on
    self._cast_ nd ces_dtype = tf. nt32  f self._use_compress on else None
    self.use_b nary_sparse_dense_matmul = use_b nary_sparse_dense_matmul

  def _make_  ght_var(self, shape, part  oner):
    self.  ght = self.add_var able(
      '  ght',
       n  al zer=self.  ght_ n  al zer,
      regular zer=self.  ght_regular zer,
      shape=shape,
      dtype=self.dtype,
      tra nable=True,
      part  oner=part  oner,
    )

  def bu ld(self,  nput_shapes):
    """
    creates t  ``b as`` and ``  ght`` Var ables
    of shape ``[output_s ze]`` and ``[ nput_s ze, output_s ze]`` respect vely.
    """

     f  s nstance( nput_shapes, (l st, tuple)):
       nput_shape =  nput_shapes[0]
       s_compat ble = True
      for ot r_shape  n  nput_shapes[1:]:
         s_compat ble &=  nput_shape. s_compat ble_w h(ot r_shape)
       f not  s_compat ble:
        ra se ValueError(" nput shapes %s are not compat ble." %  nput_shapes)
    else:
       nput_shape =  nput_shapes

    self.b as = self.add_var able(
      'b as',
       n  al zer=self.b as_ n  al zer,
      regular zer=self.b as_regular zer,
      shape=[self.output_s ze, ],
      dtype=self.dtype,
      tra nable=True
    )

    part  oner = None
    shape = [ nput_shape[1], self.output_s ze]

    # T re  s a 2gb l m at on for each tensor because of protobuf.
    # 2**30  s 1GB. 2 * (2**30)  s 2GB.
    dtype = tf.as_dtype(self.dtype)
    num_part  ons = 1  f self.num_part  ons  s None else self.num_part  ons
     n_shape =  nput_shape[1]
    out_shape = self.output_s ze

    # w n v2 behav or  s d sabled,  n_shape  s tf.D  ns on. ot rw se    s  nt.
     f  s nstance( n_shape, tf.D  ns on):
       n_shape =  n_shape.value

     f  n_shape  s None:
      ra se ValueError(" nput tensor should have shape."
                       "   can set   us ng twml.ut l.l m _sparse_tensor_s ze")

    (spl _d m, ot r_d m) = ( n_shape, out_shape)  f self.part  on_ax s == 0 else (out_shape,  n_shape)
    requested_s ze = math.ce l(float(spl _d m) / num_part  ons) * ot r_d m * dtype.s ze
     f (requested_s ze >= 2**31):
      ra se ValueError("  ght tensor part  ons cannot be larger than 2GB.\n"
                       "Requested D  ns ons(%d, %d) of type %s (%d bytes total) over %d part  ons.\n"
                       "Poss ble solut ons:\n"
                       "- reduce t  params.output_s ze_b s\n"
                       "- reduce t  output_s ze of t  sparse_layer\n"
                       "- spec fy a larger num_part  ons argu nt\n"
                       "- reduce  nput_s ze_b s" %
                       ( n_shape, self.output_s ze, dtype.na , requested_s ze, num_part  ons))

     f self.num_part  ons:
      part  on_ax s =  nt(self.part  on_ax s)
      part  oner = tf.f xed_s ze_part  oner(self.num_part  ons, ax s=part  on_ax s)
    else:
      # Regular var ables do not l ke   w n   pass both constant tensors and shape
       f not callable(self.  ght_ n  al zer):
        shape = None

    self._make_  ght_var(shape, part  oner)

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
        A SparseTensor or a l st of SparseTensors.
         f ` nputs`  s a l st, all tensors must have sa  `dense_shape`.

    Returns:
      -  f ` nputs`  s `SparseTensor`, t n returns `b as +  nputs * dense_b`.
      -  f ` nputs`  s a `l st[SparseTensor`, t n returns
        `b as + add_n([sp_a * dense_b for sp_a  n  nputs])`.

    """
     f  s nstance( nputs, (l st, tuple)):

       f  s nstance(self.use_b nary_values, (l st, tuple)):
        use_b nary_values = self.use_b nary_values
      else:
        use_b nary_values = [self.use_b nary_values] * len( nputs)

      num_ nputs = len( nputs)
       f num_ nputs != len(use_b nary_values):
        ra se ValueError("# nputs  s %d wh le #use_b nary_values  s %d"
                         % (num_ nputs, len(use_b nary_values)))

      outputs = []
      for n  n range(num_ nputs):
        outputs.append(sparse_dense_matmul( nputs[n], self.  ght,
                                           self.use_sparse_grads,
                                           use_b nary_values[n],
                                           na ='sparse_mm_' + str(n),
                                           part  on_ax s=self.part  on_ax s,
                                           num_part  ons=self.num_part  ons,
                                           compress_ ds=self._use_compress on,
                                           cast_ nd ces_dtype=self._cast_ nd ces_dtype,
                                           use_b nary_sparse_dense_matmul=self.use_b nary_sparse_dense_matmul))
      outputs = tf.accumulate_n(outputs)
    else:

       f  s nstance(self.use_b nary_values, (l st, tuple)):
        ra se ValueError("use_b nary_values can not be %s w n  nputs  s %s" %
                         (type(self.use_b nary_values), type( nputs)))

      outputs = sparse_dense_matmul( nputs, self.  ght,
                                    self.use_sparse_grads,
                                    self.use_b nary_values,
                                    na ='sparse_mm',
                                    part  on_ax s=self.part  on_ax s,
                                    num_part  ons=self.num_part  ons,
                                    compress_ ds=self._use_compress on,
                                    cast_ nd ces_dtype=self._cast_ nd ces_dtype,
                                    use_b nary_sparse_dense_matmul=self.use_b nary_sparse_dense_matmul)

     f self.b as  s not None:
      outputs = tf.nn.b as_add(outputs, self.b as)

     f self.act vat on  s not None:
      return self.act vat on(outputs)  # pyl nt: d sable=not-callable
    return outputs


def full_sparse(
         nputs, output_s ze,
         nput_s ze=None,
        act vat on=None,
        b as_regular zer=None,
          ght_regular zer=None,
        b as_ n  al zer=None,
          ght_ n  al zer=None,
        tra nable=True,
        na =None,
        reuse=None,
        use_sparse_grads=True,
        num_part  ons=None,
        part  on_ax s=0,
        use_b nary_values=False,
        use_compress on=False):
  """Funct onal  nterface for t  sparsely-connected layer.

  Argu nts:
     nputs:
      A sparse tensor (can be twml.SparseTensor or tf.SparseTensor)
    output_s ze:
      Long or  nteger, d  ns onal y of t  output space.
      ght_ n  al zer:
       n  al zer funct on for t    ght matr x.
    act vat on:
      Act vat on funct on (callable). Set   to None to ma nta n a l near act vat on.
    b as_ n  al zer:
       n  al zer funct on for t  b as.
      ght_regular zer:
      Regular zer funct on for t    ght matr x.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
    b as_regular zer:
      Regular zer funct on for t  b as.
      Ensure to add tf.losses.get_regular zat on_loss() to y  loss for t  to take effect.
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
    num_part  ons:
      Number of part  ons to use for t    ght var able. Defaults to 1.
    part  on_ax s:
       f num_part  ons  s spec f ed, t  part  on ax s for t    ght var able
      Defaults to 0 (part  on by row).
      Must be 0 (row) or 1 (column)
    use_b nary_values:
      Assu  all non zero values are 1. Defaults to False.
      T  can  mprove tra n ng  f used  n conjunct on w h MDL.
    use_compress on:
      Default False. Set True to enable data compress on techn ques for
      opt m zat on of network traff c for d str buted tra n ng.
  Returns:
    Outputs a ``tf.Tensor`` of s ze ``[batch_s ze x output_s ze]``.
  """
  # TODO - remove  nput_s ze warn ng.
   f  nput_s ze:
    ra se ValueError(' nput_s ze  s deprecated -    s now \
                      automat cally  nferred from y   nput.')

  dtype = None
   f  s nstance( nputs, twml.SparseTensor):
     nputs =  nputs.to_tf()
    dtype =  nputs.dtype.base_dtype

   f  s nstance( nputs, (l st, tuple)):
     nputs = [ np.to_tf()  f  s nstance( np, twml.SparseTensor) else  np for  np  n  nputs]
    dtype =  nputs[0].dtype.base_dtype

  layer = FullSparse(output_s ze=output_s ze,
                     act vat on=act vat on,
                     tra nable=tra nable,
                     na =na ,
                       ght_ n  al zer=  ght_ n  al zer,
                     b as_ n  al zer=b as_ n  al zer,
                       ght_regular zer=  ght_regular zer,
                     b as_regular zer=b as_regular zer,
                     dtype=dtype,
                     _scope=na ,
                     _reuse=reuse,
                     use_sparse_grads=use_sparse_grads,
                     num_part  ons=num_part  ons,
                     part  on_ax s=part  on_ax s,
                     use_compress on=use_compress on,
                     use_b nary_values=use_b nary_values)
  return layer( nputs)
