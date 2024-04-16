# pyl nt: d sable=unused-argu nt, m ss ng-docstr ng
'''
Common bu ld graphs that can be reused
'''
 mport tensorflow.compat.v1 as tf


def get_saved_modules_graph( nput_graph_fn):
  """
  Get common graph for st ch ng d fferent saved modules for export.
  T  graph  s used to save c ckpo nts; and t n export t  modules
  as a un y.
  Args:
        features:
          model features
        params:
          model params
         nput_graph_fn:
          ma n log c for t  st ch ng
  Returns:
    bu ld_graph
  """
  def bu ld_graph(features, label, mode, params, conf g=None):
    output =  nput_graph_fn(features, params)
    #  f mode  s tra n,   just need to ass gn a dum  loss
    # and update t  tra n op. T   s done to save t  graph to save_d r.
     f mode == 'tra n':
      loss = tf.constant(1)
      tra n_op = tf.ass gn_add(tf.tra n.get_global_step(), 1)
      return {'tra n_op': tra n_op, 'loss': loss}
    return output
  return bu ld_graph
