# pyl nt: d sable=useless-super-delegat on
"""
 mple nt ng St ch Layer
"""


from .layer  mport Layer

 mport tensorflow.compat.v1 as tf


class St ch(Layer):
  """
  T  layer  s respons ble for st ch ng a part oned layer toget r.

  Output:
    A layer that performs st ch ng
  """

  def compute_output_shape(self,  nput_shape):
    """Computes t  output shape of t  layer g ven t   nput shape.

    Args:
       nput_shape: A (poss bly nested tuple of) `TensorShape`.    need not
        be fully def ned (e.g. t  batch s ze may be unknown).

    Ra ses Not mple ntedError.

    """
    ra se Not mple ntedError

  def call(self, part oned_val, part oned_keys,
           part oned_ nd ces, **kwargs):  # pyl nt: d sable=unused-argu nt, argu nts-d ffer
    """
    T  layer  s respons ble for st ch ng a part oned layer toget r.

     nput:
      part oned_val:
        a l st of part oned Tensors wh ch represent t  vals of t  hashmap
      part oned_keys:
        a l st of part oned Tensors wh ch represent t  keys of t  hashmap
      part oned_ nd ces:
        a l st of part oned Tensors wh ch represent t   nd ces of t  hashmap
    Output:
      L st wh ch conta ns: [output_vals, output_keys]
        output_vals:
          Values of t  HashMap (float)
        output_keys:
          Keys of HashMap (float)
    """
     nd ces = [tf.to_ nt32( ndex) for  ndex  n part oned_ nd ces]
    concat_keys = tf.dynam c_st ch( nd ces, part oned_keys)
    concat_vals = tf.dynam c_st ch( nd ces, part oned_val)
    return [concat_vals, concat_keys]
