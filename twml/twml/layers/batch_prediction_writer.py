# pyl nt: d sable=no- mber,  nval d-na 
"""
 mple nt ng Wr er Layer
"""
from .layer  mport Layer

 mport l btwml


class BatchPred ct onWr er(Layer):
  """
  A layer that packages keys and values  nto a BatchPred ct onResponse.
  Typ cally used at t  out of an exported model for use  n a t  Pred ct onEng ne
  (that  s,  n product on).

  Argu nts:
      keys:
        keys to hashmap
  Output:
      output:
        a BatchPred ct onResponse ser al zed us ng Thr ft  nto a u nt8 tensor.
   """

  def __ n __(self, keys, **kwargs):  # pyl nt: d sable=useless-super-delegat on
    super(BatchPred ct onWr er, self).__ n __(**kwargs)
    self.keys = keys

  def compute_output_shape(self,  nput_shape):
    """Computes t  output shape of t  layer g ven t   nput shape.

    Args:
       nput_shape: A (poss bly nested tuple of) `TensorShape`.    need not
        be fully def ned (e.g. t  batch s ze may be unknown).

    Ra se Not mple ntedError.

    """
    ra se Not mple ntedError

  def call(self, values, **kwargs):  # pyl nt: d sable=unused-argu nt, argu nts-d ffer
    """T  log c of t  layer l ves  re.

    Argu nts:
      values:
        values correspond ng to keys  n hashmap

    Returns:
      T  output from t  layer
    """
    wr e_op = l btwml.ops.batch_pred ct on_response_wr er(self.keys, values)
    return wr e_op
