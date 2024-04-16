# pyl nt: d sable=no- mber, argu nts-d ffer, attr bute-def ned-outs de- n , unused-argu nt
"""
 mple nt ng Full Sparse Layer, allow spec fy use_b nary_value  n call() to
over de default act on.
"""

from twml.layers  mport FullSparse as defaultFullSparse
from twml.layers.full_sparse  mport sparse_dense_matmul

 mport tensorflow.compat.v1 as tf


class FullSparse(defaultFullSparse):
  def call(self,  nputs, use_b nary_values=None, **kwargs):  # pyl nt: d sable=unused-argu nt
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

     f use_b nary_values  s not None:
      default_use_b nary_values = use_b nary_values
    else:
      default_use_b nary_values = self.use_b nary_values

     f  s nstance(default_use_b nary_values, (l st, tuple)):
      ra se ValueError(
        "use_b nary_values can not be %s w n  nputs  s %s"
        % (type(default_use_b nary_values), type( nputs))
      )

    outputs = sparse_dense_matmul(
       nputs,
      self.  ght,
      self.use_sparse_grads,
      default_use_b nary_values,
      na ="sparse_mm",
      part  on_ax s=self.part  on_ax s,
      num_part  ons=self.num_part  ons,
      compress_ ds=self._use_compress on,
      cast_ nd ces_dtype=self._cast_ nd ces_dtype,
    )

     f self.b as  s not None:
      outputs = tf.nn.b as_add(outputs, self.b as)

     f self.act vat on  s not None:
      return self.act vat on(outputs)  # pyl nt: d sable=not-callable
    return outputs
