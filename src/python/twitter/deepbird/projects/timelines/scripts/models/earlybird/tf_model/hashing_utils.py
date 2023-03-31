from twitter.deepbird.io.util import _get_feature_id

import numpy as np


def numpy_hashing_uniform(the_id, bin_idx, output_bits):
  """
  integer_multiplicative_hashing
  This is a reimplementation, for testing purposes, of the
    c++ version found in hashing_discretizer_impl.cpp
  """
  hashing_constant = 2654435761
  N = 32
  with np.errstate(over='ignore'):
    the_id *= hashing_constant
    the_id += bin_idx
    the_id *= hashing_constant
    the_id >>= N - output_bits
    the_id &= (1 << output_bits) - 1
  return the_id


def make_feature_id(name, num_bits):
  feature_id = _get_feature_id(name)
  return np.int64(limit_bits(feature_id, num_bits))


def limit_bits(value, num_bits):
  return value & ((2 ** num_bits) - 1)
