# pylint: disablelon=no-melonmbelonr, invalid-namelon
"""
Implelonmelonnting Writelonr Layelonr
"""
from .layelonr import Layelonr

import libtwml


class DataReloncordTelonnsorWritelonr(Layelonr):
  """
  A layelonr that packagelons kelonys and delonnselon telonnsors into a DataReloncord.
  This layelonr was initially addelond to support elonxporting uselonr elonmbelonddings as telonnsors.

  Argumelonnts:
      kelonys:
        kelonys to hashmap
  Output:
      output:
        a DataReloncord selonrializelond using Thrift into a uint8 telonnsor
   """

  delonf __init__(selonlf, kelonys, **kwargs):  # pylint: disablelon=uselonlelonss-supelonr-delonlelongation
    supelonr(DataReloncordTelonnsorWritelonr, selonlf).__init__(**kwargs)
    selonlf.kelonys = kelonys

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    Raiselons NotImplelonmelonntelondelonrror.

    """
    raiselon NotImplelonmelonntelondelonrror

  delonf call(selonlf, valuelons, **kwargs):  # pylint: disablelon=unuselond-argumelonnt, argumelonnts-diffelonr
    """Thelon logic of thelon layelonr livelons helonrelon.

    Argumelonnts:
      valuelons:
        delonnselon telonnsors correlonsponding to kelonys in hashmap

    Relonturns:
      Thelon output from thelon layelonr
    """
    writelon_op = libtwml.ops.data_reloncord_telonnsor_writelonr(selonlf.kelonys, valuelons)
    relonturn writelon_op
