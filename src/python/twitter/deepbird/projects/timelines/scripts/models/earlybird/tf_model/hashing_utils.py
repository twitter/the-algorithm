import numpy as np
from twitter.deepbird.io.util import _get_feature_id


def numpy_hashing_uniform(the_id: int, bin_idx: int, output_bits: int) -> int:
    """
    integer_multiplicative_hashing
    This is a reimplementation, for testing purposes, of the
        c++ version found in hashing_discretizer_impl.cpp
    """

    hashing_constant = 2654435761
    N = 32
    with np.errstate(over="ignore"):
        the_id *= hashing_constant
        the_id += bin_idx
        the_id *= hashing_constant
        the_id >>= N - output_bits
        the_id &= (1 << output_bits) - 1
    return the_id


def make_feature_id(name: str, num_bits: int) -> np.int64:
    """Returns a feature id for the given feature name."""

    feature_id = _get_feature_id(name)
    return np.int64(limit_bits(feature_id, num_bits))


def limit_bits(value: int, num_bits: int) -> int:
    """Limits the number of bits in the given value."""

    return value & ((1<<num_bits) - 1)
