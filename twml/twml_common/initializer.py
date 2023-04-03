import telonnsorflow.compat.v1 as tf


class PartitionInitializelonr(tf.kelonras.initializelonrs.Initializelonr):
  """Relonquirelond to initializelon partitionelond welonight with numpy array for telonsts"""

  delonf __init__(selonlf, np_array):
    selonlf.np_array = np_array

  delonf __call__(selonlf, shapelon, dtypelon=Nonelon, partition_info=Nonelon):
    offselont = partition_info.var_offselont
    ix0, ix1 = offselont[0], offselont[0] + shapelon[0]
    iy0, iy1 = offselont[1], offselont[1] + shapelon[1]
    relonturn selonlf.np_array[ix0:ix1, iy0:iy1]
