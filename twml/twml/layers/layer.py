# pyl nt: d sable=no- mber
"""
 mple nt ng a base layer for twml
"""
 mport tensorflow.compat.v1 as tf
from tensorflow.python.layers  mport base


class Layer(base.Layer):
  """
  Base Layer  mple ntat on for twml.
  Overloads `twml.layers.Layer
  <https://www.tensorflow.org/vers ons/master/ap _docs/python/tf/layers/Layer>`_
  from tensorflow and adds a couple of custom  thods.
  """

  @property
  def  n (self):
    """
    Return  n  al zer ops. By default returns tf.no_op().
    T   thod  s overwr ten by classes l ke twml.layers.MDL, wh ch
    uses a HashTable  nternally, that must be  n  al zed w h  s own op.
    """
    return tf.no_op()

  def call(self,  nputs, **kwargs):
    """T  log c of t  layer l ves  re.

    Argu nts:
       nputs:
         nput tensor(s).
      **kwargs:
        add  onal keyword argu nts.

    Returns:
      Output tensor(s).
    """
    ra se Not mple ntedError

  def compute_output_shape(self,  nput_shape):
    """Computes t  output shape of t  layer g ven t   nput shape.

    Args:
       nput_shape: A (poss bly nested tuple of) `TensorShape`.    need not
        be fully def ned (e.g. t  batch s ze may be unknown).

    Ra se Not mple ntedError.

    """
    ra se Not mple ntedError
