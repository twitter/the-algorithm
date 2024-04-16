# pyl nt: d sable=no- mber,  nval d-na , attr bute-def ned-outs de- n 
"""
Conta ns t   soton c Layer
"""

from .layer  mport Layer

 mport l btwml
 mport numpy as np


class  soton c(Layer):
  """
  T  layer  s created by t   soton cCal brator.
  Typ cally    s used  ntead of s gmo d act vat on on t  output un .

  Argu nts:
    n_un :
      number of  nput un s to t  layer (sa  as number of output un s).
    n_b n:
      number of b ns used for  soton c cal brat on.
      More b ns  ans a more prec se  soton c funct on.
      Less b ns  ans a more regular zed  soton c funct on.
    xs_ nput:
      A tensor conta n ng t  boundar es of t  b ns.
    ys_ nput:
      A tensor conta n ng cal brated values for t  correspond ng b ns.

  Output:
      output:
        A layer conta n ng cal brated probab l  es w h sa  shape and s ze as  nput.
  Expected S zes:
      xs_ nput, ys_ nput:
        [n_un , n_b n].
  Expected Types:
      xs_ nput, ys_ nput:
        sa  as  nput.
  """

  def __ n __(self, n_un , n_b n, xs_ nput=None, ys_ nput=None, **kwargs):
    super( soton c, self).__ n __(**kwargs)

    self._n_un  = n_un 
    self._n_b n = n_b n

    self.xs_ nput = np.empty([n_un , n_b n], dtype=np.float32)  f xs_ nput  s None else xs_ nput
    self.ys_ nput = np.empty([n_un , n_b n], dtype=np.float32)  f ys_ nput  s None else ys_ nput

  def compute_output_shape(self,  nput_shape):
    """Computes t  output shape of t  layer g ven t   nput shape.

    Args:
       nput_shape: A (poss bly nested tuple of) `TensorShape`.    need not
        be fully def ned (e.g. t  batch s ze may be unknown).

    Ra ses Not mple ntedError.

    """
    ra se Not mple ntedError

  def bu ld(self,  nput_shape):  # pyl nt: d sable=unused-argu nt
    """Creates t  var ables of t  layer."""

    self.bu lt = True

  def call(self,  nputs, **kwargs):  # pyl nt: d sable=unused-argu nt
    """T  log c of t  layer l ves  re.

    Argu nts:
       nputs:  nput tensor(s).

    Returns:
      T  output from t  layer
    """
    cal brate_op = l btwml.ops. soton c_cal brat on( nputs, self.xs_ nput, self.ys_ nput)
    return cal brate_op
