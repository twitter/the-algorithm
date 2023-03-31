# pylint: disable=arguments-differ,no-member,too-many-statements
''' Contains PercentileDiscretizerFeature and PercentileDiscretizerCalibrator used \
    for PercentileDiscretizer calibration '''



from .calibrator import CalibrationFeature, Calibrator

import os
import numpy as np
import tensorflow.compat.v1 as tf
import tensorflow_hub as hub
import twml
import twml.layers


DEFAULT_SAMPLE_WEIGHT = 1


class PercentileDiscretizerFeature(CalibrationFeature):
  ''' Accumulates and calibrates a single sparse PercentileDiscretizer feature. '''

  @staticmethod
  def _gather_debug_info(values, indices, bin_vals, bin_counts_buffer):
    '''
    Determine how many training values fell into a given bin during calibration.
    This is calculated by finding the index of the first appearance of each bin
    boundary in values (values may repeat, so that isn't trivially in indices.)
    Subtracting each bin boundary index from the next tells you how many values fall in
    that bin.
    To get this to calculate the last bin correctly, len(values) is appended to the
    list of bound indices.

    This assumes that ``bin_vals`` excludes np.inf bin boundaries when
    PercentileDiscretizer was calibrated
    with fewer values than bins.

    Arguments:
      values:
        1D ndarray of the PercentileDiscretizerFeature's accumulated values, sorted ascending
      indices:
        1D int32 ndarray of the indices (in values) of the bin boundaries
      bin_vals:
        1D ndarray containing the bin boundaries
      bin_counts_buffer:
        ndarray buffer for returning the PercentileDiscretizer histogram
    '''
    # np.flatnonzero(np.diff(x)) gives you the indices i in x s.t. x[i] != x[i+1]
    # append index of the last bin since that cannot be empty with how
    # PercentileDiscretizer is implemented
    nonempty_bins = np.append(np.flatnonzero(np.diff(bin_vals)), len(bin_vals) - 1)
    bin_start_indices = indices.take(nonempty_bins)

    # if multiples of a bin's lower bound value exist, find the first one
    for (i, idx) in enumerate(bin_start_indices):
      cur_idx = idx
      while cur_idx > 0 and values[cur_idx] == values[cur_idx - 1]:
        bin_start_indices[i] = cur_idx = cur_idx - 1

    # the end of each bin is the start of the next bin,
    # until the last, which is the end of the array
    # broadcast the counts to the nonempty bins, 0 otherwise
    bin_counts_buffer[:] = 0
    bin_counts_buffer[nonempty_bins] = np.diff(np.append(bin_start_indices, values.size))

  def calibrate(
          self,
          bin_vals, percentiles, percentile_indices,
          bin_counts_buffer=None):
    '''Calibrates the PercentileDiscretizerFeature into bin values for
    use in PercentileDiscretizerCalibrator.
    Note that this method can only be called once.

    Arguments:
      bin_vals:
        Row in the PercentileDiscretizerCalibrator.bin_vals matrix corresponding to this feature.
        Will be updated with the results of the calibration.
        A 1D ndarray.
      percentiles:
        1D array of size n_bin with values ranging from 0 to 1.
        For example, ``percentiles = np.linspace(0, 1, num=self._n_bin+1, dtype=np.float32)``
      percentile_indices:
        Empty 1D array of size n_bin used to store intermediate results when
        calling twml.twml_optim_nearest_interpolation().
        For example, np.empty(self._n_bin + 1, dtype=np.float32).
      bin_counts_buffer:
        optional ndarray buffer used for retaining count of values per PercentileDiscretizer
        bucket (for debug and feature exploration purposes)

    Returns:
      calibrated bin_vals for use by ``PercentileDiscretizerCalibrator``
    '''
    if self._calibrated:
      raise RuntimeError("Can only calibrate once")
    if bin_vals.ndim != 1:
      raise RuntimeError("Expecting bin_vals row")

    # # concatenate values and weights buffers
    self._concat_arrays()
    feature_values = self._features_dict['values']
    feature_weights = self._features_dict['weights']

    # get features ready for the bins, order array indices by feature values.
    indices = np.argsort(feature_values)

    # get ordered values and weights using array indices
    values = feature_values.take(indices)
    weights = feature_weights.take(indices)

    # Normalizes the sum of weights to be between 0 and 1
    weights = np.cumsum(weights, out=feature_weights)
    weights -= weights[0]
    if weights[-1] > 0:  # prevent zero-division
      weights /= weights[-1]

    # Check if we have less values than bin_vals
    if values.size < bin_vals.size:
      # Fills all the bins with a value that won't ever be reached
      bin_vals.fill(np.inf)
      # Forces the first to be -inf
      bin_vals[0] = -np.inf
      # Copies the values as boundaries
      bin_vals[1:values.size + 1] = values

      if bin_counts_buffer is not None:
        # slice out bins with +/-np.inf boundary -- their count will be zero anyway
        # we can't just assume all other bins will have 1 value since there can be dups
        short_indices = np.arange(values.size, dtype=np.int32)
        bin_counts_buffer.fill(0)
        self._gather_debug_info(
          values, short_indices, bin_vals[1:values.size + 1],
          bin_counts_buffer[1:values.size + 1])

    else:
      # Gets the indices for the values that define the boundary for the bins
      indices_float = np.arange(0, weights.size, dtype=np.float32)

      # Gets things in the correct shape for the linear interpolation
      weights = weights.reshape(1, weights.size)
      indices_float = indices_float.reshape(1, weights.size)

      # wrap ndarrays into twml.Array
      percentiles_tarray = twml.Array(percentiles.reshape(percentiles.size, 1))
      weights_tarray = twml.Array(weights)
      indices_float_tarray = twml.Array(indices_float)
      percentile_indices_tarray = twml.Array(percentile_indices.reshape(percentiles.size, 1))

      # Performs the binary search to find the indices corresponding to the percentiles
      err = twml.CLIB.twml_optim_nearest_interpolation(
        percentile_indices_tarray.handle, percentiles_tarray.handle,  # output, input
        weights_tarray.handle, indices_float_tarray.handle  # xs, ys
      )
      if err != 1000:
        raise ValueError("""twml.CLIB.twml_optim_nearest_interpolation
          caught an error (see previous stdout). Error code: """ % err)

      indices = indices[:bin_vals.size]
      indices[:] = percentile_indices
      indices[0] = 0
      indices[-1] = weights.size - 1

      # Gets the values at those indices and copies them into bin_vals
      values.take(indices, out=bin_vals)

      # get # of values per bucket
      if bin_counts_buffer is not None:
        self._gather_debug_info(values, indices, bin_vals, bin_counts_buffer)

    self._calibrated = True


class PercentileDiscretizerCalibrator(Calibrator):
  ''' Accumulates features and their respective values for PercentileDiscretizer calibration.
  Internally, each feature's values is accumulated via its own
  ``PercentileDiscretizerFeature`` object.
  The steps for calibration are typically as follows:

   1. accumulate feature values from batches by calling ``accumulate()``;
   2. calibrate all feature into PercentileDiscretizer bin_vals by calling ``calibrate()``; and
   3. convert to a twml.layers.PercentileDiscretizer layer by calling ``to_layer()``.

  '''

  def __init__(self, n_bin, out_bits, bin_histogram=True,
               allow_empty_calibration=False, **kwargs):
    ''' Constructs an PercentileDiscretizerCalibrator instance.

    Arguments:
      n_bin:
        the number of bins per feature to use for PercentileDiscretizer.
        Note that each feature actually maps to n_bin+1 output IDs.
      out_bits:
        The maximum number of bits to use for the output IDs.
        2**out_bits must be greater than bin_ids.size or an error is raised.
      bin_histogram:
        When True (the default), gathers information during calibration
        to build a bin_histogram.
      allow_empty_calibration:
        allows operation where we might not calibrate any features.
        Default False to error out if no features were calibrated.
        Typically, values of uncalibrated features pass through discretizers
        untouched (though the feature ids will be truncated to obey out_bits).
    '''
    super(PercentileDiscretizerCalibrator, self).__init__(**kwargs)
    self._n_bin = n_bin
    self._out_bits = out_bits

    self._bin_ids = None
    self._bin_vals = np.empty(0, dtype=np.float32)  # Note changed from 64 (v1) to 32 (v2)

    self._bin_histogram = bin_histogram
    self._bin_histogram_dict = None

    self._hash_map_counter = 0
    self._hash_map = {}

    self._discretizer_feature_dict = {}
    self._allow_empty_calibration = allow_empty_calibration

  @property
  def bin_ids(self):
    '''
    Gets bin_ids
    '''
    return self._bin_ids

  @property
  def bin_vals(self):
    '''
    Gets bin_vals
    '''
    return self._bin_vals

  @property
  def hash_map(self):
    '''
    Gets hash_map
    '''
    return self._hash_map

  @property
  def discretizer_feature_dict(self):
    '''
    Gets feature_dict
    '''
    return self._discretizer_feature_dict

  def accumulate_features(self, inputs, name):
    '''
    Wrapper around accumulate for PercentileDiscretizer.
    Arguments:
      inputs:
        batch that will be accumulated
      name:
        name of the tensor that will be accumulated

    '''
    sparse_tf = inputs[name]
    indices = sparse_tf.indices[:, 1]
    ids = sparse_tf.indices[:, 0]
    weights = np.take(inputs["weights"], ids)
    return self.accumulate(indices, sparse_tf.values, weights)

  def accumulate_feature(self, output):
    '''
    Wrapper around accumulate for trainer API.
    Arguments:
      output:
        output of prediction of build_graph for calibrator
    '''
    return self.accumulate(output['feature_ids'], output['feature_values'], output['weights'])

  def accumulate(self, feature_keys, feature_vals, weights=None):
    '''Accumulate a single batch of feature keys, values and weights.

    These are accumulate until ``calibrate()`` is called.

    Arguments:
      feature_keys:
        1D int64 array of feature keys.
      feature_vals:
        1D float array of feature values. Each element of this array
        maps to the commensurate element in ``feature_keys``.
      weights:
        Defaults to weights of 1.
        1D array containing the weights of each feature key, value pair.
        Typically, this is the weight of each sample (but you still need
        to provide one weight per key,value pair).
        Each element of this array maps to the commensurate element in feature_keys.
    '''
    if feature_keys.ndim != 1:
      raise ValueError('Expecting 1D feature_keys, got %dD' % feature_keys.ndim)
    if feature_vals.ndim != 1:
      raise ValueError('Expecting 1D feature_values, got %dD' % feature_vals.ndim)
    if feature_vals.size != feature_keys.size:
      raise ValueError(
        'Expecting feature_keys.size == feature_values.size, got %d != %d' %
        (feature_keys.size, feature_vals.size))
    if weights is not None:
      weights = np.squeeze(weights)
      if weights.ndim != 1:
        raise ValueError('Expecting 1D weights, got %dD' % weights.ndim)
      elif weights.size != feature_keys.size:
        raise ValueError(
          'Expecting feature_keys.size == weights.size, got %d != %d' %
          (feature_keys.size, weights.size))
    if weights is None:
      weights = np.full(feature_vals.size, fill_value=DEFAULT_SAMPLE_WEIGHT)
    unique_keys = np.unique(feature_keys)
    for feature_id in unique_keys:
      idx = np.where(feature_keys == feature_id)
      if feature_id not in self._discretizer_feature_dict:
        self._hash_map[feature_id] = self._hash_map_counter
        # unlike v1, the hash_map_counter is incremented AFTER assignment.
        # This makes the hash_map features zero-indexed: 0, 1, 2 instead of 1, 2, 3
        self._hash_map_counter += 1
        # creates a new cache if we never saw the feature before
        discretizer_feature = PercentileDiscretizerFeature(feature_id)
        self._discretizer_feature_dict[feature_id] = discretizer_feature
      else:
        discretizer_feature = self._discretizer_feature_dict[feature_id]
      discretizer_feature.add_values({'values': feature_vals[idx], 'weights': weights[idx]})

  def calibrate(self, debug=False):
    '''
    Calibrates each PercentileDiscretizer feature after accumulation is complete.

    Arguments:
      debug:
        Boolean to request debug info be returned by the method.
        (see Returns section below)

    The calibration results are stored in two matrices:
      bin_ids:
        2D array of size number of accumulate ``features x n_bin+1``.
        Contains the new IDs generated by PercentileDiscretizer. Each row maps to a feature.
        Each row maps to different value bins. The IDs
        are in the range ``1 -> bin_ids.size+1``
      bin_vals:
        2D array of the same size as bin_ids.
        Each row maps to a feature. Each row contains the bin boundaries.
        These boundaries represent feature values.

    Returns:
      if debug is True, the method returns

        - 1D int64 array of feature_ids
        - 2D float32 array copy of bin_vals (the bin boundaries) for each feature
        - 2D int64 array of bin counts corresponding to the bin boundaries

    '''
    n_feature = len(self._discretizer_feature_dict)
    if n_feature == 0 and not self._allow_empty_calibration:
      raise RuntimeError("Need to accumulate some features for calibration\n"
                         "Likely, the calibration data is empty. This can\n"
                         "happen if the dataset is small, or if the following\n"
                         "cli args are set too low:\n"
                         "  --discretizer_keep_rate (default=0.0008)\n"
                         "  --discretizer_parts_downsampling_rate (default=0.2)\n"
                         "Consider increasing the values of these args.\n"
                         "To allow empty calibration data (and degenerate discretizer),\n"
                         "use the allow_empty_calibration input of the constructor.")

    self._bin_ids = np.arange(1, n_feature * (self._n_bin + 1) + 1)
    self._bin_ids = self._bin_ids.reshape(n_feature, self._n_bin + 1)

    self._bin_vals.resize(n_feature, self._n_bin + 1)

    # buffers shared by PercentileDiscretizerFeature.calibrate()
    percentile_indices = np.empty(self._n_bin + 1, dtype=np.float32)

    # Tensor from 0 to 1 in the number of steps provided
    percentiles = np.linspace(0, 1, num=self._n_bin + 1, dtype=np.float32)

    if debug or self._bin_histogram:
      debug_feature_ids = np.empty(n_feature, dtype=np.int64)
      bin_counts = np.empty((n_feature, self._n_bin + 1), dtype=np.int64)

    # progress bar for calibration phase
    progress_bar = tf.keras.utils.Progbar(n_feature)

    discretizer_features_dict = self._discretizer_feature_dict
    for i, feature_id in enumerate(discretizer_features_dict):
      if debug or self._bin_histogram:
        debug_feature_ids[self._hash_map[feature_id]] = feature_id
        bin_counts_buffer = bin_counts[self._hash_map[feature_id]]
      else:
        bin_counts_buffer = None

      # calibrate each PercentileDiscretizer feature (puts results in bin_vals)
      discretizer_features_dict[feature_id].calibrate(
        self._bin_vals[self._hash_map[feature_id]],  # Gets feature-values
        percentiles, percentile_indices,
        bin_counts_buffer=bin_counts_buffer
      )

      # update progress bar 20 times
      if (i % max(1.0, round(n_feature / 20)) == 0) or (i == n_feature - 1):
        progress_bar.update(i + 1)

    super(PercentileDiscretizerCalibrator, self).calibrate()

    if self._bin_histogram:
      # save bin histogram data for later
      self._bin_histogram_dict = {
        'feature_ids': debug_feature_ids,
        'bin_counts': bin_counts,
        'bin_vals': self._bin_vals,
        'out_bits': self._out_bits,
      }

    if debug:
      return debug_feature_ids, self._bin_vals.copy(), bin_counts

    return None

  def _create_discretizer_layer(self, n_feature, hash_map_keys, hash_map_values,
                                feature_offsets, name):
    return twml.layers.PercentileDiscretizer(
      n_feature=n_feature,
      n_bin=self._n_bin,
      out_bits=self._out_bits,
      bin_values=self._bin_vals.flatten(),
      hash_keys=hash_map_keys,
      hash_values=hash_map_values.astype(np.int64),
      bin_ids=self._bin_ids.flatten().astype(np.int64),
      feature_offsets=feature_offsets,
      name=name,
      **self._kwargs
    )

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

    discretizer = self._create_discretizer_layer(n_feature, hash_map_keys,
                                                 hash_map_values, feature_offsets, name)

    return discretizer

  def get_layer_args(self):
    '''
    Returns layer arguments required to implement multi-phase training.
    See twml.calibrator.Calibrator.get_layer_args for more detailed documentation.
    '''
    layer_args = {
      'n_feature': len(self._discretizer_feature_dict),
      'n_bin': self._n_bin,
      'out_bits': self._out_bits,
    }

    return layer_args

  def add_hub_signatures(self, name):
    """
    Add Hub Signatures for each calibrator

    Arguments:
      name:
        Calibrator name
    """
    sparse_tf = tf.sparse_placeholder(tf.float32)
    calibrator_layer = self.to_layer()
    hub.add_signature(
      inputs=sparse_tf,
      outputs=calibrator_layer(sparse_tf, keep_inputs=False),
      name=name)

  def write_summary(self, writer, sess=None):
    """
    This method is called by save() to write a histogram of
    PercentileDiscretizer feature bins to disk. A histogram is included for each
    feature.

    Arguments:
      writer:
        tf.summary.FilteWriter instance.
        used to add summaries to event files for inclusion in tensorboard.
      sess:
        tf.Session instance. Used to produces summaries for the writer.
    """
    bin_counts_ph = tf.placeholder(tf.int64)
    bin_counts = self._bin_histogram_dict['bin_counts']

    # Record that distribution into a histogram summary
    histo = tf.summary.histogram("discretizer_feature_bin_counts", bin_counts_ph)
    for i in range(bin_counts.shape[0]):
      bin_counts_summary = sess.run(histo, feed_dict={bin_counts_ph: bin_counts[i]})
      writer.add_summary(bin_counts_summary, global_step=i)

  def write_summary_json(self, save_dir, name="default"):
    """
    Export bin information to HDFS.
    
    Arguments:
      save_dir:
        name of the saving directory.
      name:
        prefix of the saved hub signature. Default (string): "default".
    """
    # Since the size is small: (# of bins) * (# of features), we always dump the file.
    discretizer_export_bin_filename = os.path.join(save_dir, name + '_bin.json')
    discretizer_export_bin_dict = {
      'feature_ids': self._bin_histogram_dict['feature_ids'].tolist(),
      'bin_boundaries': self._bin_histogram_dict['bin_vals'].tolist(),
      'output_bits': self._bin_histogram_dict['out_bits']
    }
    twml.write_file(discretizer_export_bin_filename, discretizer_export_bin_dict, encode='json')

  def save(self, save_dir, name="default", verbose=False):
    '''Save the calibrator into the given save_directory using TF Hub.
    Arguments:
      save_dir:
        name of the saving directory.
      name:
        prefix of the saved hub signature. Default (string): "default".
    '''
    if not self._calibrated:
      raise RuntimeError("Expecting prior call to calibrate().Cannot save() prior to calibrate()")

    # This module allows for the calibrator to save be saved as part of
    # Tensorflow Hub (this will allow it to be used in further steps)
    def calibrator_module():
      # Note that this is usually expecting a sparse_placeholder
      inputs = tf.sparse_placeholder(tf.float32)
      calibrator_layer = self.to_layer()
      # creates the signature to the calibrator module
      hub.add_signature(
        inputs=inputs,
        outputs=calibrator_layer(inputs, keep_inputs=False),
        name=name)
      # and another signature for keep_inputs mode
      hub.add_signature(
        inputs=inputs,
        outputs=calibrator_layer(inputs, keep_inputs=True),
        name=name + '_keep_inputs')

    # exports the module to the save_dir
    spec = hub.create_module_spec(calibrator_module)
    with tf.Graph().as_default():
      module = hub.Module(spec)
      with tf.Session() as session:
        module.export(save_dir, session)

    self.write_summary_json(save_dir, name)
