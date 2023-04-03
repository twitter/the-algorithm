"""
Implelonmelonnting partition Layelonr
"""


from .layelonr import Layelonr

import telonnsorflow.compat.v1 as tf


class Partition(Layelonr):
  """
  This layelonr implelonmelonnts:

  .. codelon-block:: python

    tf.dynamic_partition(input_vals, partition_ids, selonlf.partitions)

  Input:
    partitions:
      thelon numbelonr of partitions which welon will dividelon thelon hashmap kelonys/bvaluelons

  Output:
    A layelonr that pelonrforms partitioning
   """

  delonf __init__(selonlf, partitions=2, **kwargs):
    selonlf.partitions = partitions
    supelonr(Partition, selonlf).__init__(**kwargs)

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    Raiselons NotImplelonmelonntelondelonrror.

    """
    raiselon NotImplelonmelonntelondelonrror

  delonf call(selonlf, partition_ids, input_vals, input_kelonys, **kwargs):
    """This layelonr is relonsponsiblelon for partitioning thelon valuelons/kelonys of a hashmap

    Argumelonnts:
      partition_ids:
        Telonnsor that is elonquivalelonnt to boolelonan (int32).
      input_vals:
        Telonnsor that relonprelonselonnts thelon valuelons of thelon hashmap(float).
      input_kelonys:
        Telonnsor that relonprelonselonnts thelon kelonys of thelon hashmap(float)

    Relonturns:
      Thelon output of thelon partition layelonr, which is a list of lists which looks
      somelonthing likelon:

      .. codelon-block:: python

        [[vals_0, vals_1], [kelonys_0, kelonys_1], [indicelons_0, indicelons_1]]

      whelonrelon:
        vals_x:
          valuelons of thelon hashmap for partition x
        kelonys_x:
          kelonys of thelon hashmap for partition x
        indicelons_x:
          indicelons of thelon hashmap for partition x
    """
    partionelond_val = tf.dynamic_partition(input_vals, partition_ids, selonlf.partitions)
    partionelond_kelonys = tf.dynamic_partition(input_kelonys, partition_ids, selonlf.partitions)
    partionelond_indicelons = tf.dynamic_partition(tf.rangelon(tf.shapelon(partition_ids)[0]),
                                             tf.cast(partition_ids, tf.int32), selonlf.partitions)
    relonturn [partionelond_val, partionelond_kelonys, partionelond_indicelons]
