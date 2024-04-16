"""Module conta n ng wrapper class to allow numpy arrays to work w h twml funct ons"""

 mport ctypes as ct

from absl  mport logg ng
from l btwml  mport CL B
 mport numpy as np


_NP_TO_TWML_TYPE = {
  'float32': ct.c_ nt(1),
  'float64': ct.c_ nt(2),
  ' nt32': ct.c_ nt(3),
  ' nt64': ct.c_ nt(4),
  ' nt8': ct.c_ nt(5),
  'u nt8': ct.c_ nt(6),
}


class Array(object):
  """
  Wrapper class to allow numpy arrays to work w h twml funct ons.
  """

  def __ n __(self, array):
    """
    Wraps numpy array and creates a handle that can be passed to C funct ons from l btwml.

    array: Numpy array
    """
     f not  s nstance(array, np.ndarray):
      ra se TypeError(" nput must be a numpy array")

    try:
      ttype = _NP_TO_TWML_TYPE[array.dtype.na ]
    except KeyError as err:
      logg ng.error("Unsupported numpy type")
      ra se err

    handle = ct.c_vo d_p(0)
    nd m = ct.c_ nt(array.nd m)
    d ms = array.ctypes.get_shape()
     s ze = array.dtype. ems ze

    str des_t = ct.c_s ze_t * array.nd m
    str des = str des_t(*[n //  s ze for n  n array.str des])

    err = CL B.twml_tensor_create(ct.po nter(handle),
                                  array.ctypes.get_as_para ter(),
                                  nd m, d ms, str des, ttype)

     f err != 1000:
      ra se Runt  Error("Error from l btwml")

    # Store t  numpy array to ensure    sn't deleted before self
    self._array = array

    self._handle = handle

    self._type = ttype

  @property
  def handle(self):
    """
    Return t  twml handle
    """
    return self._handle

  @property
  def shape(self):
    """
    Return t  shape
    """
    return self._array.shape

  @property
  def nd m(self):
    """
    Return t  shape
    """
    return self._array.nd m

  @property
  def array(self):
    """
    Return t  numpy array
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
    Delete t  handle
    """
    CL B.twml_tensor_delete(self._handle)
