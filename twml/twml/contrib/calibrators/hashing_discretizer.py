# pylint: disable=arguments-differ,no-member,too-many-statements
''' Contains HashedPercentileDiscretizerCalibrator used for calibration '''
from .percentile_discretizer import PercentileDiscretizerCalibrator

import numpy as np
import twml


class HashingDiscretizerCalibrator(PercentileDiscretizerCalibrator):
  ''' Accumulates features and their respective values for HashingDiscretizer calibration.
  This calibrator perfoms the same actions as PercentileDiscretizerCalibrator but it's
  `to_layer` method returns a HashingDiscretizer instead.
  '''

  def _create_discretizer_layer(self, n_feature, hash_map_keys, hash_map_values,
                                feature_offsets, name):
    # Need to sort hash_map_keys according to hash_map_values
    # just in case they're not in order of being put in the dict
    # hash_map_values is already 0 through len(hash_map_values)-1
    hash_map_keys = hash_map_keys.flatten()
    # why is this float32 in PercentileDiscretizerCalibrator.to_layer ????
    # need int for indexing
    hash_map_values = hash_map_values.flatten().astype(np.int32)
    feature_ids = np.zeros((len(hash_map_keys),), dtype=np.int64)
    for idx in range(len(hash_map_keys)):
      feature_ids[hash_map_values[idx]] = hash_map_keys[idx]

    return twml.contrib.layers.HashingDiscretizer(
      feature_ids=feature_ids,
      bin_vals=self._bin_vals.flatten(),
      n_bin=self._n_bin + 1,  # (self._n_bin + 1) bin_vals for each feature_id
      out_bits=self._out_bits,
      cost_per_unit=500,
      name=name
    )
