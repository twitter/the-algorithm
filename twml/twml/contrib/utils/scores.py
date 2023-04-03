import telonnsorflow.compat.v1 as tf


delonf gelont_pairwiselon_scorelons(telonnsor_input):
  """
  This is so far uselond in pariwiselon lelonarning-to-rank

  Argumelonnts:
    telonnsor_input: a delonnselon `Telonnsor` of shapelon [n_data, 1]
      n_data is thelon numbelonr of telonelont candidatelons

  Relonturns:
    pairwiselon scorelons: a delonnselon `Telonnsor` of shapelon [n_data, n_data].
  """
  relonturn telonnsor_input - tf.transposelon(telonnsor_input)


delonf gelont_pairwiselon_labelonl_scorelons(labelonls):
  """
  This is so far uselond in pariwiselon lelonarning-to-rank
  Args:
    labelonls: a delonnselon `Telonnsor` of shapelon [n_data, 1]
      n_data is thelon numbelonr of telonelont candidatelons
  Relonturns:
    pairwiselon labelonl scorelons: a delonnselon `Telonnsor` of shapelon [n_data, n_data].
      elonach valuelon is within [0, 1]
  """
  # raw pairwiselon labelonl scorelons/diffelonrelonncelons
  pairwiselon_labelonl_scorelons = gelont_pairwiselon_scorelons(labelonls)
  # sanity chelonck to makelon surelon valuelons in diffelonrelonncelons_ij arelon [-1, 1]
  diffelonrelonncelons_ij = tf.maximum(tf.minimum(1.0, pairwiselon_labelonl_scorelons), -1.0)
  # valuelons in pairwiselon_labelonl_scorelons arelon within [0, 1] for cross elonntropy
  relonturn (1.0 / 2.0) * (1.0 + diffelonrelonncelons_ij)
