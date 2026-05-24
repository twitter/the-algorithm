# pylint: disable=arguments-differ,no-member,too-many-statements
''' Contains MDLFeature and MDLCalibrator used for MDL calibration '''


import os

from .percentile_discretizer import PercentileDiscretizerCalibrator, PercentileDiscretizerFeature

from absl import logging
import numpy as np
import tensorflow.compat.v1 as tf
import twml
import twml.layers


DEFAULT_SAMPLE_WEIGHT = 1


class MDLFeature(PercentileDiscretizerFeature):
  ''' Accumulates and calibrates a single sparse MDL feature. '''


class MDLCalibrator(PercentileDiscretizerCalibrator):
  ''' Accumulates features and their respective values for MDL calibration.
  Internally, each feature's values is accumulated via its own ``MDLFeature`` object.
  The steps for calibration are typically as follows:

   1. accumulate feature values from batches by calling ``accumulate()``;
   2. calibrate all feature into MDL bin_vals by calling ``calibrate()``; and
   3. convert to a twml.layers.MDL layer by calling ``to_layer()``.

  '''

  def to_layer(self, name=None):
    """
    Returns a twml.layers.PercentileDiscretizer Layer
    that can be used for feature discretization.

    Arguments:
      name:
        name-scope of the PercentileDiscretizer layer
    """
    n_feature = len(self._discretizer_feature_dict)
    max_discretizer_feature = n_feature * (self._n_bin + 1)

    if not self._calibrated:
      raise RuntimeError("Expecting prior call to calibrate()")

    if self._bin_ids.shape[0] != n_feature:
      raise RuntimeError("Expecting self._bin_ids.shape[0] \
        != len(self._discretizer_feature_dict)")
    if self._bin_vals.shape[0] != n_feature:
      raise RuntimeError("Expecting self._bin_vals.shape[0] \
        != len(self._discretizer_feature_dict)")

    # can add at most #features * (n_bin+1) new feature ids
    if 2**self._out_bits <= max_discretizer_feature:
      raise ValueError("""Maximum number of features created by discretizer is
        %d but requested that the output be limited to %d values (%d bits),
        which is smaller than that. Please ensure the output has enough bits
        to represent at least the new features"""
                       % (max_discretizer_feature, 2**self._out_bits, self._out_bits))

    # build feature_offsets, hash_map_keys, hash_map_values
    feature_offsets = np.arange(0, max_discretizer_feature,
                                self._n_bin + 1, dtype='int64')
    hash_map_keys = np.array(list(self._hash_map.keys()), dtype=np.int64)
    hash_map_values = np.array(list(self._hash_map.values()), dtype=np.float32)

    discretizer = twml.layers.MDL(
      n_feature=n_feature, n_bin=self._n_bin,
      name=name, out_bits=self._out_bits,
      hash_keys=hash_map_keys, hash_values=hash_map_values,
      bin_ids=self._bin_ids.flatten(), bin_values=self._bin_vals.flatten(),
      feature_offsets=feature_offsets,
      **self._kwargs
    )

    return discretizer

  def save(self, save_dir, name='calibrator', verbose=False):
    '''Save the calibrator into the given save_directory.
    Arguments:
      save_dir:
        name of the saving directory
      name:
        name for the graph scope. Passed to to_layer(name=name) to set
        scope of layer.
    '''
    if not self._calibrated:
      raise RuntimeError("Expecting prior call to calibrate().Cannot save() prior to calibrate()")

    layer_args = self.get_layer_args()

    calibrator_filename = os.path.join(save_dir, name + '.json.tf')
    calibrator_dict = {
      'layer_args': layer_args,
      'saved_layer_scope': name + '/',
    }
    twml.write_file(calibrator_filename, calibrator_dict, encode='json')

    if verbose:
      logging.info("The layer graph and other information necessary ")
      logging.info("for multi-phase training is saved in directory:")
      logging.info(save_dir)
      logging.info("This directory can be specified as --init_from_dir argument.")
      logging.info("")
      logging.info("Other information is available in: %s.json.tf", name)
      logging.info("This file can be loaded with twml.read_file(decode='json) to obtain ")
      logging.info("layer_args, saved_layer_scope and variable_names")

    graph = tf.Graph()
    # save graph for tensorboard as well
    writer = tf.summary.FileWriter(logdir=save_dir, graph=graph)

    with tf.Session(graph=graph) as sess:
      self.write_summary(writer, sess)
    writer.flush()
