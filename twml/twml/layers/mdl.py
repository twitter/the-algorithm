# pyl nt: d sable=no- mber, attr bute-def ned-outs de- n , too-many- nstance-attr butes
"""
 mple nt ng MDL Layer
"""


from .layer  mport Layer
from .part  on  mport Part  on
from .st ch  mport St ch

 mport l btwml
 mport numpy as np
 mport tensorflow.compat.v1 as tf
 mport twml


class MDL(Layer):  # noqa: T000
  """
  MDL layer  s constructed by MDLCal brator after accumulat ng data
  and perform ng m n mum descr pt on length (MDL) cal brat on.

  MDL takes sparse cont nuous features and converts t n to sparse
  b nary features. Each b nary output feature  s assoc ated to an MDL b n.
  Each MDL  nput feature  s converted to n_b n b ns.
  Each MDL cal brat on tr es to f nd b n del m ers such that t  number of features values
  per b n  s roughly equal (for each g ven MDL feature).
  Note that  f an  nput feature  s rarely used, so w ll  s assoc ated output b n/features.
  """

  def __ n __(
          self,
          n_feature, n_b n, out_b s,
          b n_values=None, hash_keys=None, hash_values=None,
          b n_ ds=None, feature_offsets=None, **kwargs):
    """
    Creates a non- n  al zed `MDL` object.
    Before us ng t  table   w ll have to  n  al ze  . After  n  al zat on
    t  table w ll be  mmutable.

    Parent class args:
      see [tf.layers.Layer](https://www.tensorflow.org/ap _docs/python/tf/layers/Layer)
      for docu ntat on of parent class argu nts.

    Requ red args:
      n_feature:
        number of un que features accumulated dur ng MDL cal brat on.
        T   s t  number of features  n t  hash map.
        Used to  n  al ze b n_values, hash_keys, hash_values,
        b n_ ds, b n_values and feature_offsets.
      n_b n:
        number of MDL b ns used for MDL cal brat on.
        Used to  n  al ze b n_values, hash_keys, hash_values,
        b n_ ds, b n_values and feature_offsets.
      out_b s:
        Determ nes t  max mum value for output feature  Ds.
        T  dense_shape of t  SparseTensor returned by lookup(x)
        w ll be [x.shape[0], 1 << output_b s].

    Opt onal args:
      hash_keys:
        conta ns t  features  D that MDL d scret zes and knows about.
        T  hash map (hash_keys->hash_values)  s used for two reasons:
          1. d v de  nputs  nto two feature spaces: MDL vs non-MDL
          2. transate t  MDL features  nto a hash_feature  D that MDL understands.
        T  hash_map  s expected to conta n n_feature  ems.
      hash_values:
        translates t  feature  Ds  nto hash_feature  Ds for MDL.
      b n_ ds:
        a 1D Tensor of s ze n_feature * n_b n + 1 wh ch conta ns
        un que  Ds to wh ch t  MDL features w ll be translated to.
        For example, tf.Tensor(np.arange(n_feature * n_b n)) would produce
        t  most eff c ent output space.
      b n_values:
        a 1D Tensor al gned w h b n_ ds.
        For a g ven hash_feature  D j,  's value b n's are  ndexed bet en
        `j*n_b n` and `j*n_b n + n_b n-1`.
        As such, b n_ ds[j*n_b n+ ]  s translated from a hash_feature  D of j
        and a  nputs value bet en
        `b n_values[j*n_b n +  ]` and `b n_values[j*n_b n+ +1]`.
      feature_offsets:
        a 1D Tensor spec fy ng t  start ng locat on of b ns for a g ven feature  d.
        For example, tf.Tensor(np.arange(0, b n_values.s ze, n_b n, dtype=' nt64')).
    """
    super(MDL, self).__ n __(**kwargs)
    tf.logg ng.warn ng("MDL w ll be deprecated. Please use Percent leD scret zer  nstead")

    max_mdl_feature = n_feature * (n_b n + 1)
    self._n_feature = n_feature
    self._n_b n = n_b n

    self._hash_keys_ n  al zer = tf.constant_ n  al zer(
      hash_keys  f hash_keys  s not None
      else np.empty(n_feature, dtype=np. nt64),
      dtype=np. nt64
    )
    self._hash_values_ n  al zer = tf.constant_ n  al zer(
      hash_values  f hash_values  s not None
      else np.empty(n_feature, dtype=np. nt64),
      dtype=np. nt64
    )
    self._b n_ ds_ n  al zer = tf.constant_ n  al zer(
      b n_ ds  f b n_ ds  s not None
      else np.empty(max_mdl_feature, dtype=np. nt64),
      dtype=np. nt64
    )
    self._b n_values_ n  al zer = tf.constant_ n  al zer(
      b n_values  f b n_values  s not None
      else np.empty(max_mdl_feature, dtype=np.float32),
      dtype=np.float32
    )
    self._feature_offsets_ n  al zer = tf.constant_ n  al zer(
      feature_offsets  f feature_offsets  s not None
      else np.empty(n_feature, dtype=np. nt64),
      dtype=np. nt64
    )

    # note that call ng bu ld  re  s an except on as typ cally __call__ would call bu ld().
    #   call    re because   need to  n  al ze hash_map.
    # Also note that t  var able_scope  s set by add_var able  n bu ld()
     f not self.bu lt:
      self.bu ld( nput_shape=None)

    self.output_s ze = tf.convert_to_tensor(1 << out_b s, tf. nt64)

  def bu ld(self,  nput_shape):  # pyl nt: d sable=unused-argu nt
    """
    Creates t  var ables of t  layer:
    hash_keys, hash_values, b n_ ds, b n_values, feature_offsets and self.output_s ze.
    """

    # bu ld layers
    self.part  on = Part  on()
    self.st ch = St ch()

    # bu ld var ables

    hash_keys = self.add_var able(
      'hash_keys',
       n  al zer=self._hash_keys_ n  al zer,
      shape=[self._n_feature],
      dtype=tf. nt64,
      tra nable=False)

    hash_values = self.add_var able(
      'hash_values',
       n  al zer=self._hash_values_ n  al zer,
      shape=[self._n_feature],
      dtype=tf. nt64,
      tra nable=False)

    # hashmap converts known features  nto range [0, n_feature)
     n  al zer = tf.lookup.KeyValueTensor n  al zer(hash_keys, hash_values)
    self.hash_map = tf.lookup.Stat cHashTable( n  al zer, -1)

    self.b n_ ds = self.add_var able(
      'b n_ ds',
       n  al zer=self._b n_ ds_ n  al zer,
      shape=[self._n_feature * (self._n_b n + 1)],
      dtype=tf. nt64,
      tra nable=False)

    self.b n_values = self.add_var able(
      'b n_values',
       n  al zer=self._b n_values_ n  al zer,
      shape=[self._n_feature * (self._n_b n + 1)],
      dtype=tf.float32,
      tra nable=False)

    self.feature_offsets = self.add_var able(
      'feature_offsets',
       n  al zer=self._feature_offsets_ n  al zer,
      shape=[self._n_feature],
      dtype=tf. nt64,
      tra nable=False)

    # make sure t   s last
    self.bu lt = True

  def call(self,  nputs, **kwargs):
    """Looks up `keys`  n a table, outputs t  correspond ng values.

     mple nts MDL  nference w re  nputs are  ntersected w h a hash_map.
    Part of t   nputs are d scret zed us ng twml.mdl to produce a mdl_output SparseTensor.
    T  SparseTensor  s t n jo ned w h t  or g nal  nputs SparseTensor,
    but only for t   nputs keys that d d not get d scret zed.

    Args:
       nputs: A 2D SparseTensor that  s  nput to MDL for d scret zat on.
          has a dense_shape of [batch_s ze,  nput_s ze]
      na : A na  for t  operat on (opt onal).
    Returns:
      A `SparseTensor` of t  sa  type as ` nputs`.
       s dense_shape  s [shape_ nput.dense_shape[0], 1 << output_b s].
    """
     f  s nstance( nputs, tf.SparseTensor):
       nputs = twml.SparseTensor.from_tf( nputs)

    assert( s nstance( nputs, twml.SparseTensor))

    # sparse column  nd ces
     ds =  nputs. ds
    # sparse row  nd ces
    keys =  nputs. nd ces
    # sparse values
    vals =  nputs.values

    # get  ntersect(keys, hash_map)
    has d_keys = self.hash_map.lookup(keys)

    found = tf.not_equal(has d_keys, tf.constant(-1, tf. nt64))
    part  on_ ds = tf.cast(found, tf. nt32)

    vals, key,  nd ces = self.part  on(part  on_ ds, vals, tf.w re(found, has d_keys, keys))
    non_mdl_keys, mdl_ n_keys = key
    non_mdl_vals, mdl_ n_vals = vals

    self.non_mdl_keys = non_mdl_keys

    # run MDL on t  keys/values   knows about
    mdl_keys, mdl_vals = l btwml.ops.mdl(mdl_ n_keys, mdl_ n_vals, self.b n_ ds, self.b n_values,
                                         self.feature_offsets)

    # handle output  D confl cts
    mdl_s ze = tf.s ze(self.b n_ ds, out_type=tf. nt64)
    non_mdl_s ze = tf.subtract(self.output_s ze, mdl_s ze)
    non_mdl_keys = tf.add(tf.floormod(non_mdl_keys, non_mdl_s ze), mdl_s ze)

    # St ch t  keys and values from mdl and non mdl  nd ces back, w h  lp
    # of t  St ch Layer

    # out for  nference c ck ng
    self.mdl_out_keys = mdl_keys

    concat_data = self.st ch([non_mdl_vals, mdl_vals],
                              [non_mdl_keys, mdl_keys],
                               nd ces)

    concat_vals, concat_keys = concat_data

    # Generate output shape us ng _compute_output_shape

    batch_s ze = tf.to_ nt64( nputs.dense_shape[0])
    output_shape = [batch_s ze, self.output_s ze]
    return twml.SparseTensor( ds, concat_keys, concat_vals, output_shape).to_tf()

  def compute_output_shape(self,  nput_shape):
    """Computes t  output shape of t  layer g ven t   nput shape.

    Args:
       nput_shape: A (poss bly nested tuple of) `TensorShape`.    need not
        be fully def ned (e.g. t  batch s ze may be unknown).

    Ra ses Not mple ntedError.

    """
    ra se Not mple ntedError
