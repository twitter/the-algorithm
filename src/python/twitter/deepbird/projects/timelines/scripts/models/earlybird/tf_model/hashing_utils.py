from twitter.deepbird.io.util import _get_feature_id
import numpy as np
def numpy_hashing_uniform(the_id,bin_idx,output_bits):
	'\n  integer_multiplicative_hashing\n  This is a reimplementation, for testing purposes, of the\n    c++ version found in hashing_discretizer_impl.cpp\n  ';B=output_bits;A=the_id;C=2654435761;D=32
	with np.errstate(over='ignore'):A*=C;A+=bin_idx;A*=C;A>>=D-B;A&=(1<<B)-1
	return A
def make_feature_id(name,num_bits):A=_get_feature_id(name);return np.int64(limit_bits(A,num_bits))
def limit_bits(value,num_bits):return value&2**num_bits-1