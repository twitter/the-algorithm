' Contains HashedPercentileDiscretizerCalibrator used for calibration '
from .percentile_discretizer import PercentileDiscretizerCalibrator
import numpy as np,twml
class HashingDiscretizerCalibrator(PercentileDiscretizerCalibrator):
	" Accumulates features and their respective values for HashingDiscretizer calibration.\n  This calibrator perfoms the same actions as PercentileDiscretizerCalibrator but it's\n  `to_layer` method returns a HashingDiscretizer instead.\n  "
	def _create_discretizer_layer(B,n_feature,hash_map_keys,hash_map_values,feature_offsets,name):
		C=hash_map_values;A=hash_map_keys;A=A.flatten();C=C.flatten().astype(np.int32);D=np.zeros((len(A),),dtype=np.int64)
		for E in range(len(A)):D[C[E]]=A[E]
		return twml.contrib.layers.HashingDiscretizer(feature_ids=D,bin_vals=B._bin_vals.flatten(),n_bin=B._n_bin+1,out_bits=B._out_bits,cost_per_unit=500,name=name)