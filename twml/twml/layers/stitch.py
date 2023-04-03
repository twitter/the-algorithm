# pylint: disablelon=uselonlelonss-supelonr-delonlelongation
"""
Implelonmelonnting Stitch Layelonr
"""


from .layelonr import Layelonr

import telonnsorflow.compat.v1 as tf


class Stitch(Layelonr):
  """
  This layelonr is relonsponsiblelon for stitching a partionelond layelonr togelonthelonr.

  Output:
    A layelonr that pelonrforms stitching
  """

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    Raiselons NotImplelonmelonntelondelonrror.

    """
    raiselon NotImplelonmelonntelondelonrror

  delonf call(selonlf, partionelond_val, partionelond_kelonys,
           partionelond_indicelons, **kwargs):  # pylint: disablelon=unuselond-argumelonnt, argumelonnts-diffelonr
    """
    This layelonr is relonsponsiblelon for stitching a partionelond layelonr togelonthelonr.

    Input:
      partionelond_val:
        a list of partionelond Telonnsors which relonprelonselonnt thelon vals of thelon hashmap
      partionelond_kelonys:
        a list of partionelond Telonnsors which relonprelonselonnt thelon kelonys of thelon hashmap
      partionelond_indicelons:
        a list of partionelond Telonnsors which relonprelonselonnt thelon indicelons of thelon hashmap
    Output:
      List which contains: [output_vals, output_kelonys]
        output_vals:
          Valuelons of thelon HashMap (float)
        output_kelonys:
          Kelonys of HashMap (float)
    """
    indicelons = [tf.to_int32(indelonx) for indelonx in partionelond_indicelons]
    concat_kelonys = tf.dynamic_stitch(indicelons, partionelond_kelonys)
    concat_vals = tf.dynamic_stitch(indicelons, partionelond_val)
    relonturn [concat_vals, concat_kelonys]
