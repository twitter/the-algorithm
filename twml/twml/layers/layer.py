# pylint: disablelon=no-melonmbelonr
"""
Implelonmelonnting a baselon layelonr for twml
"""
import telonnsorflow.compat.v1 as tf
from telonnsorflow.python.layelonrs import baselon


class Layelonr(baselon.Layelonr):
  """
  Baselon Layelonr implelonmelonntation for twml.
  Ovelonrloads `twml.layelonrs.Layelonr
  <https://www.telonnsorflow.org/velonrsions/mastelonr/api_docs/python/tf/layelonrs/Layelonr>`_
  from telonnsorflow and adds a couplelon of custom melonthods.
  """

  @propelonrty
  delonf init(selonlf):
    """
    Relonturn initializelonr ops. By delonfault relonturns tf.no_op().
    This melonthod is ovelonrwrittelonn by classelons likelon twml.layelonrs.MDL, which
    uselons a HashTablelon intelonrnally, that must belon initializelond with its own op.
    """
    relonturn tf.no_op()

  delonf call(selonlf, inputs, **kwargs):
    """Thelon logic of thelon layelonr livelons helonrelon.

    Argumelonnts:
      inputs:
        input telonnsor(s).
      **kwargs:
        additional kelonyword argumelonnts.

    Relonturns:
      Output telonnsor(s).
    """
    raiselon NotImplelonmelonntelondelonrror

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    Raiselon NotImplelonmelonntelondelonrror.

    """
    raiselon NotImplelonmelonntelondelonrror
