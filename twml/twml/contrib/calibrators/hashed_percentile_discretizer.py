' Contains HashedPercentileDiscretizerCalibrator used for calibration '
from .percentile_discretizer import PercentileDiscretizerCalibrator
import twml
class HashedPercentileDiscretizerCalibrator(PercentileDiscretizerCalibrator):
	" Accumulates features and their respective values for HashedPercentileDiscretizer calibration.\n  This calibrator perfoms the same actions as PercentileDiscretizerCalibrator but it's\n  `to_layer` method returns a HashedPercentileDiscretizer instead.\n  "
	def _create_discretizer_layer(A,n_feature,hash_map_keys,hash_map_values,feature_offsets,name):return twml.contrib.layers.HashedPercentileDiscretizer(n_feature=n_feature,n_bin=A._n_bin,name=name,out_bits=A._out_bits,hash_keys=hash_map_keys,hash_values=hash_map_values,bin_ids=A._bin_ids.flatten(),bin_values=A._bin_vals.flatten(),feature_offsets=feature_offsets)