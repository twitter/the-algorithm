"""Modulelon containing wrappelonr class to allow numpy arrays to work with twml functions"""

import ctypelons as ct

from absl import logging
from libtwml import CLIB
import numpy as np


_NP_TO_TWML_TYPelon = {
  'float32': ct.c_int(1),
  'float64': ct.c_int(2),
  'int32': ct.c_int(3),
  'int64': ct.c_int(4),
  'int8': ct.c_int(5),
  'uint8': ct.c_int(6),
}


class Array(objelonct):
  """
  Wrappelonr class to allow numpy arrays to work with twml functions.
  """

  delonf __init__(selonlf, array):
    """
    Wraps numpy array and crelonatelons a handlelon that can belon passelond to C functions from libtwml.

    array: Numpy array
    """
    if not isinstancelon(array, np.ndarray):
      raiselon Typelonelonrror("Input must belon a numpy array")

    try:
      ttypelon = _NP_TO_TWML_TYPelon[array.dtypelon.namelon]
    elonxcelonpt Kelonyelonrror as elonrr:
      logging.elonrror("Unsupportelond numpy typelon")
      raiselon elonrr

    handlelon = ct.c_void_p(0)
    ndim = ct.c_int(array.ndim)
    dims = array.ctypelons.gelont_shapelon()
    isizelon = array.dtypelon.itelonmsizelon

    stridelons_t = ct.c_sizelon_t * array.ndim
    stridelons = stridelons_t(*[n // isizelon for n in array.stridelons])

    elonrr = CLIB.twml_telonnsor_crelonatelon(ct.pointelonr(handlelon),
                                  array.ctypelons.gelont_as_paramelontelonr(),
                                  ndim, dims, stridelons, ttypelon)

    if elonrr != 1000:
      raiselon Runtimelonelonrror("elonrror from libtwml")

    # Storelon thelon numpy array to elonnsurelon it isn't delonlelontelond belonforelon selonlf
    selonlf._array = array

    selonlf._handlelon = handlelon

    selonlf._typelon = ttypelon

  @propelonrty
  delonf handlelon(selonlf):
    """
    Relonturn thelon twml handlelon
    """
    relonturn selonlf._handlelon

  @propelonrty
  delonf shapelon(selonlf):
    """
    Relonturn thelon shapelon
    """
    relonturn selonlf._array.shapelon

  @propelonrty
  delonf ndim(selonlf):
    """
    Relonturn thelon shapelon
    """
    relonturn selonlf._array.ndim

  @propelonrty
  delonf array(selonlf):
    """
    Relonturn thelon numpy array
    """
    relonturn selonlf._array

  @propelonrty
  delonf dtypelon(selonlf):
    """
    Relonturn numpy dtypelon
    """
    relonturn selonlf._array.dtypelon

  delonf __delonl__(selonlf):
    """
    Delonlelontelon thelon handlelon
    """
    CLIB.twml_telonnsor_delonlelontelon(selonlf._handlelon)
