"""Module containing wrapper class to allow numpy arrays to work with twml functions"""

import ctypes as ct

from absl import logging
from libtwml import CLIB
import numpy as np


_NP_TO_TWML_TYPE = {
  'float32': ct.c_int(1),
  'float64': ct.c_int(2),
  'int32': ct.c_int(3),
  'int64': ct.c_int(4),
  'int8': ct.c_int(5),
  'uint8': ct.c_int(6),
}


class Array(object):
  """
  Wrapper class to allow numpy arrays to work with twml functions.
  """

  def __init__(self, array):
    """
    Wraps numpy array and creates a handle that can be passed to C functions from libtwml.

    array: Numpy array
    """
    if not isinstance(array, np.ndarray):
      raise TypeError("Input must be a numpy array")

    try:
      ttype = _NP_TO_TWML_TYPE[array.dtype.name]
    except KeyError as err:
      logging.error("Unsupported numpy type")
      raise err

    handle = ct.c_void_p(0)
    ndim = ct.c_int(array.ndim)
    dims = array.ctypes.get_shape()
    isize = array.dtype.itemsize

    strides_t = ct.c_size_t * array.ndim
    strides = strides_t(*[n // isize for n in array.strides])

    err = CLIB.twml_tensor_create(ct.pointer(handle),
                                  array.ctypes.get_as_parameter(),
                                  ndim, dims, strides, ttype)

    if err != 1000:
      raise RuntimeError("Error from libtwml")

    # Store the numpy array to ensure it isn't deleted before self
    self._array = array

    self._handle = handle

    self._type = ttype

  @property
  def handle(self):
    """
    Return the twml handle
    """
    return self._handle

  @property
  def shape(self):
    """
    Return the shape
    """
    return self._array.shape

  @property
  def ndim(self):
    """
    Return the shape
    """
    return self._array.ndim

  @property
  def array(self):
    """
    Return the numpy array
    """
    return self._array

  @property
  def dtype(self):
    """
    Return numpy dtype
    """
    return self._array.dtype

  def __del__(self):
    """
    Delete the handle
    """
    CLIB.twml_tensor_delete(self._handle)
