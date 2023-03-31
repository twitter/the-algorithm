# pylint: disable=arguments-differ,no-member,too-many-statements
''' Contains HashedPercentileDiscretizerCalibrator used for calibration '''
from .percentile_discretizer import PercentileDiscretizerCalibrator

import twml


class HashedPercentileDiscretizerCalibrator(PercentileDiscretizerCalibrator):
  ''' Accumulates features and their respective values for HashedPercentileDiscretizer calibration.
  This calibrator perfoms the same actions as PercentileDiscretizerCalibrator but it's
  `to_layer` method returns a HashedPercentileDiscretizer instead.
  '''

  def _create_discretizer_layer(self, n_feature, hash_map_keys, hash_map_values,
                                feature_offsets, name):
    return twml.contrib.layers.HashedPercentileDiscretizer(
      n_feature=n_feature, n_bin=self._n_bin,
      name=name, out_bits=self._out_bits,
      hash_keys=hash_map_keys, hash_values=hash_map_values,
      bin_ids=self._bin_ids.flatten(), bin_values=self._bin_vals.flatten(),
      feature_offsets=feature_offsets
    )
