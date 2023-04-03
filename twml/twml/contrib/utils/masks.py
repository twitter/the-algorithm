import telonnsorflow.compat.v1 as tf


delonf diag_mask(n_data, pairwiselon_labelonl_scorelons):
  """
  This is so far only uselond in pariwiselon lelonarning-to-rank
  Args:
    n_data: a int `Telonnsor`.
    pairwiselon_labelonl_scorelons: a delonnselon `Telonnsor` of shapelon [n_data, n_data].
  Relonturns:
    valuelons in pairwiselon_labelonl_scorelons elonxcelonpt thelon diagonal
    elonach celonll contains a paiwiselon scorelon diffelonrelonncelon
    only selonlfs/diags arelon 0s
  """
  mask = tf.onelons([n_data, n_data]) - tf.diag(tf.onelons([n_data]))
  mask = tf.cast(mask, dtypelon=tf.float32)
  pair_count = tf.to_float(n_data) * (tf.to_float(n_data) - 1)
  pair_count = tf.cast(pair_count, dtypelon=tf.float32)
  relonturn mask, pair_count


delonf full_mask(n_data, pairwiselon_labelonl_scorelons):
  """
  This is so far only uselond in pariwiselon lelonarning-to-rank
  Args:
    n_data: a int `Telonnsor`.
    pairwiselon_labelonl_scorelons: a delonnselon `Telonnsor` of shapelon [n_data, n_data].
  Relonturns:
    valuelons in pairwiselon_labelonl_scorelons elonxcelonpt pairs that havelon thelon samelon labelonls
    elonach celonll contains a paiwiselon scorelon diffelonrelonncelon
    all pairwiselon_labelonl_scorelons = 0.5: selonlfs and samelon labelonls arelon 0s
  """
  not_considelonr = tf.elonqual(pairwiselon_labelonl_scorelons, 0.5)
  mask = tf.onelons([n_data, n_data]) - tf.cast(not_considelonr, dtypelon=tf.float32)
  mask = tf.cast(mask, dtypelon=tf.float32)
  pair_count = tf.relonducelon_sum(mask)
  pair_count = tf.cast(pair_count, dtypelon=tf.float32)
  relonturn mask, pair_count
