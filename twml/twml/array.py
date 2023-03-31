'Module containing wrapper class to allow numpy arrays to work with twml functions'
import ctypes as ct
from absl import logging
from libtwml import CLIB
import numpy as np
_NP_TO_TWML_TYPE={'float32':ct.c_int(1),'float64':ct.c_int(2),'int32':ct.c_int(3),'int64':ct.c_int(4),'int8':ct.c_int(5),'uint8':ct.c_int(6)}
class Array:
	'\n  Wrapper class to allow numpy arrays to work with twml functions.\n  '
	def __init__(B,array):
		'\n    Wraps numpy array and creates a handle that can be passed to C functions from libtwml.\n\n    array: Numpy array\n    ';A=array
		if not isinstance(A,np.ndarray):raise TypeError('Input must be a numpy array')
		try:D=_NP_TO_TWML_TYPE[A.dtype.name]
		except KeyError as C:logging.error('Unsupported numpy type');raise C
		E=ct.c_void_p(0);F=ct.c_int(A.ndim);G=A.ctypes.get_shape();H=A.dtype.itemsize;I=ct.c_size_t*A.ndim;J=I(*([A//H for A in A.strides]));C=CLIB.twml_tensor_create(ct.pointer(E),A.ctypes.get_as_parameter(),F,G,J,D)
		if C!=1000:raise RuntimeError('Error from libtwml')
		B._array=A;B._handle=E;B._type=D
	@property
	def handle(self):'\n    Return the twml handle\n    ';return self._handle
	@property
	def shape(self):'\n    Return the shape\n    ';return self._array.shape
	@property
	def ndim(self):'\n    Return the shape\n    ';return self._array.ndim
	@property
	def array(self):'\n    Return the numpy array\n    ';return self._array
	@property
	def dtype(self):'\n    Return numpy dtype\n    ';return self._array.dtype
	def __del__(A):'\n    Delete the handle\n    ';CLIB.twml_tensor_delete(A._handle)