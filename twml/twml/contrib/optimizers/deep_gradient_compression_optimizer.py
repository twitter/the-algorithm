"""
A custom opt m zer to  mple nt Deep Grad ent Compress on. T  general  dea of
grad ent compress on  s to compress t  grad ents exchanged across mach nes,
 n order to reduce t  commun cat on over ad of d str but ng comput ng efforts.
More deta ls  n https://arx v.org/abs/1712.01887
"""

# TODO: Test how much commun cat on over ad t  DeepGrad entCompress onOpt m zer can reduce under
# mult -GPU and d str buted sett ng.

 mport tensorflow.compat.v1 as tf


def compute_threshold(grad, dens y):
  """
  A ut l y funct on to compute t  threshold for grad ent spars f cat on, g ven t  grad ent
  tensor and t  dens y.
  Args:
    grad(tf.Tensor):
      Grad ent tensor for so  var able.
    dens y(float):
      Dens y degree w n spars fy ng grad ents.
  Returns(float):
    Threshold for grad ent spars f cat on.
  """
  flat_grad = tf.reshape(grad, [-1])
  abs_flat_grad = tf.abs(flat_grad)
  s ze = tf.shape(abs_flat_grad)[0]
  k = tf.max mum(tf.constant(1),
                 tf.cast(tf.scalar_mul(dens y, tf.cast(s ze, tf.float32)), tf. nt32))
  topk, _ = tf.nn.top_k(abs_flat_grad, k, False)
  return topk[-1]


def get_top_row_ nd ces(values, dens y):
  """
  A ut l y funct on to get  nd ces of most s gn f cant rows, g ven t  dens y degree.
  Args:
    values(tf.Tensor):
      Grad ent or locally accumulated grad ent for so  var able.
    dens y(float):
      Dens y degree w n f lter ng out rows.
  Returns(l st( nt)):
     nd ces of most s gn f cant rows.
  """
  abs_values = tf.abs(values)

  try:
    row_num = tf.shape(abs_values)[0]
    k = tf.max mum(tf.constant(1),
                   tf.cast(tf.scalar_mul(dens y, tf.cast(row_num, tf.float32)), tf. nt32))
    row_sums = tf.squeeze(tf.reduce_sum(values, ax s=1, keepd ms=True))
    _, top_row_ nd ces = tf.nn.top_k(row_sums, k=k, sorted=False)
    # pr nt "abs_values", abs_values, "row_sums", row_sums
    return top_row_ nd ces
    # return tf.range(row_num)

  except ValueError:  #  f t  tensor  s 0-D or 1-D
    return None


class DeepGrad entCompress onOpt m zer(tf.tra n.Grad entDescentOpt m zer):
  """
  A custom opt m zer to  mple nt Deep Grad ent Compress on (https://arx v.org/abs/1712.01887).
  """

  def __ n __(self, learn ng_rate, use_lock ng=False, na ="Sparse",
               dens y=1.0,
               dens y_decay=False,
               dens y_decay_steps=10000,
               dens y_decay_rate=0.5,
               m n_dens y=0.1,
               accumulat on=False):
    super(DeepGrad entCompress onOpt m zer, self).__ n __(learn ng_rate, use_lock ng, na )
    self._ n  al_dens y_t = tf.convert_to_tensor(dens y)
    self._dens y_decay = dens y_decay
    dtype = self._ n  al_dens y_t.dtype
    self._dens y_decay_steps_t = tf.convert_to_tensor(dens y_decay_steps, dtype)
    self._dens y_decay_rate_t = tf.convert_to_tensor(dens y_decay_rate, dtype)
    self._m n_dens y_t = tf.convert_to_tensor(m n_dens y, dtype)
    self._accumulat on = accumulat on

  def _prepare(self):
    super(DeepGrad entCompress onOpt m zer, self)._prepare()
     f not self._dens y_decay:
      self._dens y_t = self._ n  al_dens y_t
    else:
      dtype = self._ n  al_dens y_t.dtype
      global_step = tf.cast(tf.tra n.get_global_step(), dtype)
      p = tf.floor(tf.d v de(global_step, self._dens y_decay_steps_t))
      decayed_dens y = tf.mult ply(self._ n  al_dens y_t,
                                    tf.pow(self._dens y_decay_rate_t, p))
      self._dens y_t = tf.max mum(self._m n_dens y_t, decayed_dens y)

  def _create_slots(self, var_l st):
    """
    Create a slot var able to accumulate grad ents locally for each var able  n `var_l st`.
    Args:
      var_l st(l st(tf.Var able)):
        L st of var ables to accumulate grad ents locally for.
    """
    for var  n var_l st:
      self._zeros_slot(var, "g_buffer", self._na )

  def _apply_dense(self, grad, var):
     f not self._accumulat on:
      top_row_ nd ces = get_top_row_ nd ces(grad, self._dens y_t)

       f top_row_ nd ces  s None:
        return super(DeepGrad entCompress onOpt m zer, self)._apply_dense(grad, var)

      spars f ed_values = tf.gat r(grad, top_row_ nd ces)
      spars f ed_ nd ces = top_row_ nd ces

      spars f ed_grad = tf. ndexedSl ces(spars f ed_values, spars f ed_ nd ces)

      return super(DeepGrad entCompress onOpt m zer, self)._apply_sparse_dupl cate_ nd ces(
        spars f ed_grad, var)

    else:
      g_buffer = self.get_slot(var, "g_buffer")

      g_buffer = tf.ass gn_add(g_buffer, grad)

      top_row_ nd ces = get_top_row_ nd ces(g_buffer, self._dens y_t)

       f top_row_ nd ces  s None:
        return super(DeepGrad entCompress onOpt m zer, self)._apply_dense(grad, var)

      spars f ed_values = tf.gat r(g_buffer, top_row_ nd ces)
      spars f ed_ nd ces = top_row_ nd ces

      spars f ed_grad = tf. ndexedSl ces(spars f ed_values, spars f ed_ nd ces)

      update_var = super(DeepGrad entCompress onOpt m zer, self)._apply_sparse_dupl cate_ nd ces(
        spars f ed_grad, var)

      update_g_buffer = tf.scatter_update(g_buffer, spars f ed_ nd ces, tf.zeros_l ke(
        spars f ed_values))

      return tf.group(*[update_var, update_g_buffer])

  def _apply_sparse_dupl cate_ nd ces(self, grad, var):
     f not self._accumulat on:
      top_row_ nd ces = get_top_row_ nd ces(grad.values, self._dens y_t)

       f top_row_ nd ces  s None:
        return super(DeepGrad entCompress onOpt m zer, self)._apply_sparse_dupl cate_ nd ces(grad, var)  # noqa: E501

      spars f ed_values = tf.gat r(grad.values, top_row_ nd ces)
      spars f ed_ nd ces = tf.gat r(grad. nd ces, top_row_ nd ces)

      spars f ed_grad = tf. ndexedSl ces(spars f ed_values, spars f ed_ nd ces)

      return super(DeepGrad entCompress onOpt m zer, self)._apply_sparse_dupl cate_ nd ces(
        spars f ed_grad, var)

    else:
      g_buffer = self.get_slot(var, "g_buffer")

      g_buffer = tf.scatter_update(g_buffer, grad. nd ces, grad.values)

      top_row_ nd ces = get_top_row_ nd ces(g_buffer, self._dens y_t)

       f top_row_ nd ces  s None:
        return super(DeepGrad entCompress onOpt m zer,
                     self)._apply_sparse_dupl cate_ nd ces(grad, var)

      spars f ed_values = tf.gat r(g_buffer, top_row_ nd ces)
      spars f ed_ nd ces = top_row_ nd ces

      spars f ed_grad = tf. ndexedSl ces(spars f ed_values, spars f ed_ nd ces)

      update_var = super(DeepGrad entCompress onOpt m zer, self)._apply_sparse_dupl cate_ nd ces(
        spars f ed_grad, var)

      update_g_buffer = tf.scatter_update(g_buffer, spars f ed_ nd ces, tf.zeros_l ke(
        spars f ed_values))

      return tf.group(*[update_var, update_g_buffer])
