# pyl nt: d sable=no- mber, attr bute-def ned-outs de- n , too-many- nstance-attr butes
"""
 mple nt ng Has dPercent leD scret zer Layer
"""


from tw ter.deepb rd.ut l.hash ng  mport (
   nteger_mult pl cat ve_hash ng_un form,
   nteger_mult pl cat ve_hash ng,
)  # noqa: F401

from l btwml  mport percent le_d scret zer_b n_ nd ces
 mport numpy as np
 mport tensorflow.compat.v1 as tf
 mport twml
from twml.layers.layer  mport Layer
from twml.layers.part  on  mport Part  on
from twml.layers.st ch  mport St ch


class Has dPercent leD scret zer(Layer):
  """
  Has dPercent leD scret zer layer  s constructed by Percent leD scret zerCal brator
  after accumulat ng data
  and perform ng m n mum descr pt on length (Percent leD scret zer) cal brat on.

  Has dPercent leD scret zer takes sparse cont nuous features and converts t n to sparse
  b nary features. Each b nary output feature  s assoc ated to an Has dPercent leD scret zer
  b n.
  Each Has dPercent leD scret zer  nput feature  s converted to n_b n b ns.
  Each Has dPercent leD scret zer cal brat on tr es to f nd b n del m ers such
  that t  number of features values
  per b n  s roughly equal (for each g ven Has dPercent leD scret zer feature).
  Note that  f an  nput feature  s rarely used, so w ll  s assoc ated output b n/features.
  T  d fference bet en t  layer and Percent leD scret zer  s that t 
  Determ n st cPercent leD scret ze always ass gns t  sa  output  d  n t  SparseTensor to t 
  sa   nput feature  d + b n. T   s useful  f   want to user transfer learn ng on pre-tra ned
  sparse to dense embedd ng layers, but re-cal brate y  d scret zer on ne r data.
  """

  def __ n __(self, n_feature, n_b n, out_b s,
               b n_values=None, hash_keys=None, hash_values=None,
               b n_ ds=None, feature_offsets=None,
               hash_fn= nteger_mult pl cat ve_hash ng_un form, **kwargs):
    """
    Creates a non- n  al zed `Has dPercent leD scret zer` object.
    Before us ng t  table   w ll have to  n  al ze  . After  n  al zat on
    t  table w ll be  mmutable.

    Parent class args:
      see [tf.layers.Layer](https://www.tensorflow.org/ap _docs/python/tf/layers/Layer)
      for docu ntat on of parent class argu nts.

    Requ red args:
      n_feature:
        number of un que features accumulated dur ng Has dPercent leD scret zer cal brat on.
        T   s t  number of features  n t  hash map.
        Used to  n  al ze b n_values, hash_keys, hash_values,
        b n_ ds, b n_values and feature_offsets.
      n_b n:
        number of Has dPercent leD scret zer b ns used for
        Has dPercent leD scret zer cal brat on. Used to  n  al ze b n_values, hash_keys,
        hash_values, b n_ ds, b n_values and feature_offsets.
      out_b s:
        Determ nes t  max mum value for output feature  Ds.
        T  dense_shape of t  SparseTensor returned by lookup(x)
        w ll be [x.shape[0], 1 << output_b s].

    Opt onal args:
      hash_keys:
        conta ns t  features  D that Has dPercent leD scret zer d scret zes and knows
        about. T  hash map (hash_keys->hash_values)  s used for two reasons:
          1. d v de  nputs  nto two feature spaces:
          Has dPercent leD scret zer vs non-Has dPercent leD scret zer
          2. transate t  Has dPercent leD scret zer features  nto a hash_feature  D that
          Has dPercent leD scret zer understands.
        T  hash_map  s expected to conta n n_feature  ems.
      hash_values:
        translates t  feature  Ds  nto hash_feature  Ds for Has dPercent leD scret zer.
      b n_ ds:
        a 1D Tensor of s ze n_feature * n_b n + 1 wh ch conta ns
        un que  Ds to wh ch t  Has dPercent leD scret zer features w ll be translated to.
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
      hash_fn:
        a funct on that takes  n `feature_ ds`, `bucket_ nd ces` and `output_s ze` and
        has s t  bucketed features  nto t  `output_s ze` buckets. T  default uses knuth's
        mult pl cat ve hash ng
    """
    super(Has dPercent leD scret zer, self).__ n __(**kwargs)

    max_d scret zer_feature = n_feature * (n_b n + 1)
    self._n_feature = n_feature
    self._n_b n = n_b n

     f not self.bu lt:
      self.bu ld( nput_shape=None)

    # bu ld var ables
    self.output_s ze = tf.convert_to_tensor(1 << out_b s, tf. nt64)
    self._out_b s = out_b s

    hash_keys = hash_keys
     f hash_keys  s None:
      hash_keys = np.empty(n_feature, dtype=np. nt64)

    hash_values = hash_values
     f hash_values  s None:
      hash_values = np.empty(n_feature, dtype=np. nt64)

     n  al zer = tf.lookup.KeyValueTensor n  al zer(hash_keys, hash_values)
    self.hash_map = tf.lookup.Stat cHashTable( n  al zer, -1)
    self.b n_ ds = b n_ ds
     f b n_ ds  s None:
      b n_ ds = np.empty(max_d scret zer_feature, dtype=np. nt64)

    self.b n_values = b n_values
     f b n_values  s None:
      b n_values = np.empty(max_d scret zer_feature, dtype=np.float32)

    self.feature_offsets = feature_offsets
     f feature_offsets  s None:
      feature_offsets = np.empty(n_feature, dtype=np. nt64)

    self.hash_fn = hash_fn

  def bu ld(self,  nput_shape):  # pyl nt: d sable=unused-argu nt
    """
    Creates t  var ables of t  layer:
    hash_keys, hash_values, b n_ ds, b n_values, feature_offsets and self.output_s ze.
    """
    # bu ld layers
    self.part  on = Part  on()
    self.st ch = St ch()
    # make sure t   s last
    self.bu lt = True

  def call(self,  nputs, **kwargs):
    """Looks up `keys`  n a table, outputs t  correspond ng values.

     mple nts Has dPercent leD scret zer  nference w re  nputs are  ntersected w h a
    hash_map.
    Part of t   nputs are d scret zed us ng twml.d scret zer
    to produce a d scret zer_output SparseTensor.
    T  SparseTensor  s t n jo ned w h t  or g nal  nputs SparseTensor,
    but only for t   nputs keys that d d not get d scret zed.

    Args:
       nputs: A 2D SparseTensor that  s  nput to Has dPercent leD scret zer for
        d scret zat on.   has a dense_shape of [batch_s ze,  nput_s ze]
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

    has d_keys = self.hash_map.lookup(keys)
    has d_keys = tf.cast(has d_keys, tf. nt64)

    found = tf.not_equal(has d_keys, tf.constant(-1, tf. nt64))
    part  on_ ds = tf.cast(found, tf. nt32)

    found = tf.reshape(found, [-1])
    cont nuous_feature_ ds = tf.boolean_mask(keys, found)

    vals, key,  nd ces = self.part  on(part  on_ ds, vals, tf.w re(found, has d_keys, keys))
    non_d scret zer_keys, d scret zer_ n_keys = key
    non_d scret zer_vals, d scret zer_ n_vals = vals

    non_d scret zer_keys = twml.ut l.l m _b s(non_d scret zer_keys, self._out_b s)
    self.non_d scret zer_keys = non_d scret zer_keys

    # run Has dPercent leD scret zer on t  keys/values   knows about
    output = percent le_d scret zer_b n_ nd ces(d scret zer_ n_keys,
                                                d scret zer_ n_vals,
                                                self.b n_ ds,
                                                self.b n_values,
                                                self.feature_offsets)
    d scret zer_bucket_ dxs, d scret zer_vals = output
    new_d scret zer_keys = self.hash_fn(cont nuous_feature_ ds, d scret zer_bucket_ dxs,
                                        self.output_s ze)
    # St ch t  keys and values from d scret zer and non d scret zer  nd ces back, w h  lp
    # of t  St ch Layer
    self.d scret zer_out_keys = new_d scret zer_keys

    concat_data = self.st ch([non_d scret zer_vals, d scret zer_vals],
                              [non_d scret zer_keys, new_d scret zer_keys],
                               nd ces)

    concat_vals, concat_keys = concat_data

    # Generate output shape us ng _compute_output_shape

    batch_s ze = tf.to_ nt64( nputs.dense_shape[0])
    output_shape = [batch_s ze, self.output_s ze]
    return twml.SparseTensor( ds, concat_keys, concat_vals, output_shape).to_tf()
