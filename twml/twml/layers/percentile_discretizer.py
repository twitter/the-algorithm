# pyl nt: d sable=no- mber, attr bute-def ned-outs de- n , too-many- nstance-attr butes
"""
 mple nt ng Percent leD scret zer Layer
"""


 mport l btwml
 mport numpy as np
 mport tensorflow.compat.v1 as tf
 mport twml
from twml.layers  mport Layer


class Percent leD scret zer(Layer):
  """
  Percent leD scret zer layer  s constructed by Percent leD scret zerCal brator after
  accumulat ng data and perform ng percent le bucket cal brat on.

  Percent leD scret zer takes sparse cont nuous features and converts t n to sparse
  b nary features. Each b nary output feature  s assoc ated to an Percent leD scret zer b n.
  Each Percent leD scret zer  nput feature  s converted to n_b n b ns.
  Each Percent leD scret zer cal brat on tr es to f nd b n del m ers such
  that t  number of features values per b n  s roughly equal (for
  each g ven Percent leD scret zer feature).  n ot r words, b ns are cal brated to be approx.
  equ probable, accord ng to t  g ven cal brat on data.
  Note that  f an  nput feature  s rarely used, so w ll  s assoc ated output b n/features.
  """

  def __ n __(
      self,
      n_feature, n_b n, out_b s,
      b n_values=None, hash_keys=None, hash_values=None,
      b n_ ds=None, feature_offsets=None, num_parts=1, cost_per_un =100, **kwargs):
    """
    Creates a non- n  al zed `Percent leD scret zer` object.
    Before us ng t  table   w ll have to  n  al ze  . After  n  al zat on
    t  table w ll be  mmutable.

     f t re are no cal brated features, t n t  d scret zer w ll only apply
    twml.ut l.l m _b s to t  t  feature keys (aka "feature_ ds"). Essent ally,
    t  d scret zer w ll be a "no-operat on", ot r than obey ng `out_b s`

    Parent class args:
      see [tf.layers.Layer](https://www.tensorflow.org/ap _docs/python/tf/layers/Layer)
      for docu ntat on of parent class argu nts.

    Requ red args:
      n_feature:
        number of un que features accumulated dur ng Percent leD scret zer cal brat on.
        T   s t  number of features  n t  hash map.
        Used to  n  al ze b n_values, hash_keys, hash_values,
        b n_ ds, b n_values and feature_offsets.
      n_b n:
        number of Percent leD scret zer b ns used for Percent leD scret zer cal brat on.
        Used to  n  al ze b n_values, hash_keys, hash_values,
        b n_ ds, b n_values and feature_offsets.
      out_b s:
        Determ nes t  max mum value for output feature  Ds.
        T  dense_shape of t  SparseTensor returned by lookup(x)
        w ll be [x.shape[0], 1 << output_b s].

    Opt onal args:
      hash_keys:
        conta ns t  features  D that Percent leD scret zer d scret zes and knows about.
        T  hash map (hash_keys->hash_values)  s used for two reasons:
          1. d v de  nputs  nto two feature spaces:
          Percent leD scret zer vs non-Percent leD scret zer
          2. transate t  Percent leD scret zer features  nto a hash_feature  D that
          Percent leD scret zer understands.
        T  hash_map  s expected to conta n n_feature  ems.
      hash_values:
        translates t  feature  Ds  nto hash_feature  Ds for Percent leD scret zer.
      b n_ ds:
        a 1D Tensor of s ze n_feature * n_b n + 1 wh ch conta ns
        un que  Ds to wh ch t  Percent leD scret zer features w ll be translated to.
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

    super(Percent leD scret zer, self).__ n __(**kwargs)

     f not self.bu lt:
      self.bu ld( nput_shape=None)

    max_d scret zer_feature = n_feature * (n_b n + 1)
    self._n_feature = n_feature
    self._n_b n = n_b n

    # bu ld var ables
    self._out_b s = out_b s
    self._output_s ze = tf.convert_to_tensor(1 << out_b s, tf. nt64)
    self._hash_keys = (hash_keys  f hash_keys  s not None else
     np.empty(n_feature, dtype=np. nt64))
    self._hash_values = (hash_values  f hash_values  s not None else
     np.empty(n_feature, dtype=np. nt64))
    self._b n_ ds = (b n_ ds  f b n_ ds  s not None else
     np.empty(max_d scret zer_feature, dtype=np. nt64))
    self._b n_values = (b n_values  f b n_values  s not None else
     np.empty(max_d scret zer_feature, dtype=np.float32))
    self._feature_offsets = (feature_offsets  f feature_offsets  s not None else
     np.empty(n_feature, dtype=np. nt64))
    self.num_parts = num_parts
    self.cost_per_un  = cost_per_un 

  def bu ld(self,  nput_shape):  # pyl nt: d sable=unused-argu nt
    """
    Creates t  var ables of t  layer
    """
    self.bu lt = True

  def call(self,  nputs, keep_ nputs=False, **kwargs):
    """Looks up `keys`  n a table, outputs t  correspond ng values.

     mple nts Percent leD scret zer  nference w re  nputs are  ntersected w h a hash_map.
     nput features that  re not cal brated have t  r feature  Ds truncated, so as
    to be less than 1<<output_b s, but t  r values rema n untouc d (not d scret zed)

     f t re are no cal brated features, t n t  d scret zer w ll only apply
    twml.ut l.l m _b s to t  t  feature keys (aka "feature_ ds"). Essent ally,
    t  d scret zer w ll be a "no-operat on", ot r than obey ng `out_b s`

    Args:
       nputs: A 2D SparseTensor that  s  nput to Percent leD scret zer for d scret zat on.
          has a dense_shape of [batch_s ze,  nput_s ze]
      keep_ nputs:
         nclude t  or g nal  nputs  n t  output.
        Note -  f True, und scret zed features w ll be passed through, but w ll have
        t  r values doubled (unless t re are no cal brated features to d scret ze).
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

     f self._n_feature > 0:
      d scret zer_keys, d scret zer_vals = l btwml.ops.percent le_d scret zer_v2(
         nput_ ds=keys,  #  nc key ass gned to feature_ d, or -1
         nput_vals=vals,  # t  observed feature values
        b n_ ds=self._b n_ ds,  # n_feat X (n_b n+1) 2D arange
        b n_vals=self._b n_values,  # b n boundar es
        feature_offsets=self._feature_offsets,  # 0 : nb n_1 : max_feat
        output_b s=self._out_b s,
        feature_ ds=tf.make_tensor_proto(self._hash_keys),  # feature  ds to bu ld  nternal hash map
        feature_ nd ces=tf.make_tensor_proto(self._hash_values),  # keys assoc ated w/ feat.  nd ces
        start_compute=tf.constant(0, shape=[], dtype=tf. nt64),
        end_compute=tf.constant(-1, shape=[], dtype=tf. nt64),
        cost_per_un =self.cost_per_un 
      )
    else:
      d scret zer_keys = twml.ut l.l m _b s(keys, self._out_b s)
      d scret zer_vals = vals
      # don't 2x t   nput.
      keep_ nputs = False

    batch_s ze = tf.to_ nt64( nputs.dense_shape[0])
    output_shape = [batch_s ze, self._output_s ze]

    output = twml.SparseTensor( ds, d scret zer_keys, d scret zer_vals, output_shape).to_tf()

     f keep_ nputs:
      # Note t  non-d scret zed features w ll end up doubled,
      #   s nce t se are already  n `output`
      # handle output  D confl cts
      mdl_s ze = self._n_feature * (self._n_b n + 1)
      non_mdl_s ze = tf.subtract(self._output_s ze, mdl_s ze)
       nput_keys = tf.add(tf.floormod(keys, non_mdl_s ze), mdl_s ze)

      new_ nput = twml.SparseTensor(
         ds= ds,  nd ces= nput_keys, values=vals, dense_shape=output_shape).to_tf()

      # concatenate d scret zer output w h or g nal  nput
      sparse_add = tf.sparse_add(new_ nput, output)
      output = tf.SparseTensor(sparse_add. nd ces, sparse_add.values, output_shape)

    return output

  def compute_output_shape(self,  nput_shape):
    """Computes t  output shape of t  layer g ven t   nput shape.

    Args:
       nput_shape: A (poss bly nested tuple of) `TensorShape`.    need not
        be fully def ned (e.g. t  batch s ze may be unknown).

    Ra ses Not mple ntedError.

    """
    ra se Not mple ntedError
