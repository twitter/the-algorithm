# pylint: disable=arguments-differ, unused-argument
''' Contains Isotonic Calibration'''

from .calibrator import CalibrationFeature, Calibrator

from absl import logging
import numpy as np
from sklearn.isotonic import isotonic_regression
import tensorflow.compat.v1 as tf
import tensorflow_hub as hub
import twml
import twml.layers


DEFAULT_SAMPLE_WEIGHT = 1


def sort_values(inputs, target, weight, ascending=True):
  '''
  Sorts arrays based on the first array.

  Arguments:
    inputs:
      1D array which will dictate the order which the remainder 2 arrays will be sorted
    target:
      1D array
    weight:
      1D array
    ascending:
      Boolean. If set to True (the default), sorts values in ascending order.

  Returns:
    sorted inputs:
      1D array sorted by the order of `ascending`
    sorted targets:
      1D array
    sorted weight:
      1D array
  '''
  # assert that the length of inputs and target are the same
  if len(inputs) != len(target):
    raise ValueError('Expecting inputs and target sizes to match')
   # assert that the length of inputs and weight are the same
  if len(inputs) != len(weight):
    raise ValueError('Expecting inputs and weight sizes to match')
  inds = inputs.argsort()
  if not ascending:
    inds = inds[::-1]
  return inputs[inds], target[inds], weight[inds]


class IsotonicFeature(CalibrationFeature):
  '''
  IsotonicFeature adds values, weights and targets to each feature and then runs
  isotonic regression by calling `sklearn.isotonic.isotonic_regression
  <http://scikit-learn.org/stable/auto_examples/plot_isotonic_regression.html>`_
  '''

  def _get_bin_boundaries(self, n_samples, bins, similar_bins):
    """
    Calculates the sample indices that define bin boundaries

    Arguments:
      n_samples:
        (int) number of samples
      bins:
        (int) number of bins. Needs to be smaller or equal than n_samples.
      similar_bins:
        (bool) If True, samples will be distributed in bins of equal size (up to one sample).
        If False bins will be filled with step = N_samples//bins, and last bin will contain all remaining samples.
        Note that equal_bins=False can create a last bins with a very large number of samples.

    Returns:
      (list[int]) List of sample indices defining bin boundaries
    """

    if bins > n_samples:
      raise ValueError(
        "The number of bins needs to be less than or equal to the number of samples. "
        "Currently bins={0} and n_samples={1}.".format(bins, n_samples)
      )

    step = n_samples // bins

    if similar_bins:
      # dtype=int will floor the linspace
      bin_boundaries = np.linspace(0, n_samples - step, num=bins, dtype=int)
    else:
      bin_boundaries = range(0, step * bins, step)

    bin_boundaries = np.append(bin_boundaries, n_samples)

    return bin_boundaries

  def calibrate(self, bins, similar_bins=False, debug=False):
    '''Calibrates the IsotonicFeature into calibrated weights and bias.

    1. Sorts the values of the feature class, based on the order of values
    2. Performs isotonic regression using sklearn.isotonic.isotonic_regression
    3. Performs the binning of the samples, in order to obtain the final weight and bias
      which will be used for inference

    Note that this method can only be called once.

    Arguments:
      bins:
        number of bins.
      similar_bins:
        If True, samples will be distributed in bins of equal size (up to one sample).
        If False bins will be filled with step = N_samples//bins, and last bin will contain all remaining samples.
        Note that equal_bins=False can create a last bins with a very large number of samples.
      debug:
        Defaults to False. If debug is set to true, output other parameters useful for debugging.

    Returns:
      [calibrated weight, calibrated bias]
    '''
    if self._calibrated:
      raise RuntimeError("Can only calibrate once")
    # parse through the dict to obtain the targets, weights and values
    self._concat_arrays()
    feature_targets = self._features_dict['targets']
    feature_values = self._features_dict['values']
    feature_weights = self._features_dict['weights']
    srtd_feature_values, srtd_feature_targets, srtd_feature_weights = sort_values(
      inputs=feature_values,
      target=feature_targets,
      weight=feature_weights
    )
    calibrated_feature_values = isotonic_regression(
      srtd_feature_targets, sample_weight=srtd_feature_weights)
    # create the final outputs for the prediction of each class
    bpreds = []
    btargets = []
    bweights = []
    rpreds = []

    # Create bin boundaries
    bin_boundaries = self._get_bin_boundaries(
      len(calibrated_feature_values), bins, similar_bins=similar_bins)

    for sidx, eidx in zip(bin_boundaries, bin_boundaries[1:]):
      # separate each one of the arrays based on their respective bins
      lpreds = srtd_feature_values[int(sidx):int(eidx)]
      lrpreds = calibrated_feature_values[int(sidx):int(eidx)]
      ltargets = srtd_feature_targets[int(sidx):int(eidx)]
      lweights = srtd_feature_weights[int(sidx):int(eidx)]

      # calculate the outputs (including the bpreds and rpreds)
      bpreds.append(np.sum(lpreds * lweights) / (np.squeeze(np.sum(lweights))))
      rpreds.append(np.sum(lrpreds * lweights) / (np.squeeze(np.sum(lweights))))
      btargets.append(np.sum(ltargets * lweights) / (np.squeeze(np.sum(lweights))))
      bweights.append(np.squeeze(np.sum(lweights)))
    # transposing the bpreds and rpreds which will be used as input to the inference step
    bpreds = np.asarray(bpreds).T
    rpreds = np.asarray(rpreds).T
    btargets = np.asarray(btargets).T
    bweights = np.asarray(bweights).T
    # setting _calibrated to be True which is necessary in order to prevent it to re-calibrate
    self._calibrated = True
    if debug:
      return bpreds, rpreds, btargets, bweights
    return bpreds, rpreds


class IsotonicCalibrator(Calibrator):
  ''' Accumulates features and their respective values for isotonic calibration.
  Internally, each feature's values is accumulated via its own isotonicFeature object.
  The steps for calibration are typically as follows:

   1. accumulate feature values from batches by calling ``accumulate()``;
   2. calibrate all feature into Isotonic ``bpreds``, ``rpreds`` by calling ``calibrate()``; and
   3. convert to a ``twml.layers.Isotonic`` layer by calling ``to_layer()``.

  '''

  def __init__(self, n_bin, similar_bins=False, **kwargs):
    ''' Constructs an isotonicCalibrator instance.

    Arguments:
      n_bin:
        the number of bins per feature to use for isotonic.
        Note that each feature actually maps to ``n_bin+1`` output IDs.
    '''
    super(IsotonicCalibrator, self).__init__(**kwargs)
    self._n_bin = n_bin
    self._similar_bins = similar_bins
    self._ys_input = []
    self._xs_input = []
    self._isotonic_feature_dict = {}

  def accumulate_feature(self, output):
    '''
    Wrapper around accumulate for trainer API.
    Arguments:
      output: output of prediction of build_graph for calibrator
    '''
    weights = output['weights'] if 'weights' in output else None
    return self.accumulate(output['predictions'], output['targets'], weights)

  def accumulate(self, predictions, targets, weights=None):
    '''
    Accumulate a single batch of class predictions, class targets and class weights.
    These are accumulated until calibrate() is called.

    Arguments:
      predictions:
        float matrix of class values. Each dimension corresponds to a different class.
        Shape is ``[n, d]``, where d is the number of classes.
      targets:
        float matrix of class targets. Each dimension corresponds to a different class.
        Shape ``[n, d]``, where d is the number of classes.
      weights:
        Defaults to weights of 1.
        1D array containing the weights of each prediction.
    '''
    if predictions.shape != targets.shape:
      raise ValueError(
        'Expecting predictions.shape == targets.shape, got %s and %s instead' %
        (str(predictions.shape), str(targets.shape)))
    if weights is not None:
      if weights.ndim != 1:
        raise ValueError('Expecting 1D weight, got %dD instead' % weights.ndim)
      elif weights.size != predictions.shape[0]:
        raise ValueError(
          'Expecting predictions.shape[0] == weights.size, got %d != %d instead' %
          (predictions.shape[0], weights.size))
    # iterate through the rows of predictions and sets one class to each row
    if weights is None:
      weights = np.full(predictions.shape[0], fill_value=DEFAULT_SAMPLE_WEIGHT)
    for class_key in range(predictions.shape[1]):
      # gets the predictions and targets for that class
      class_predictions = predictions[:, class_key]
      class_targets = targets[:, class_key]
      if class_key not in self._isotonic_feature_dict:
        isotonic_feature = IsotonicFeature(class_key)
        self._isotonic_feature_dict[class_key] = isotonic_feature
      else:
        isotonic_feature = self._isotonic_feature_dict[class_key]
      isotonic_feature.add_values({'values': class_predictions, 'weights': weights,
                                   'targets': class_targets})

  def calibrate(self, debug=False):
    '''
    Calibrates each IsotonicFeature after accumulation is complete.
    Results are stored in ``self._ys_input`` and ``self._xs_input``

    Arguments:
      debug:
        Defaults to False. If set to true, returns the ``xs_input`` and ``ys_input``.
    '''
    super(IsotonicCalibrator, self).calibrate()
    bias_temp = []
    weight_temp = []
    logging.info("Beginning isotonic calibration.")
    isotonic_features_dict = self._isotonic_feature_dict
    for class_id in isotonic_features_dict:
      bpreds, rpreds = isotonic_features_dict[class_id].calibrate(bins=self._n_bin, similar_bins=self._similar_bins)
      weight_temp.append(bpreds)
      bias_temp.append(rpreds)
    # save isotonic results onto a matrix
    self._xs_input = np.array(weight_temp, dtype=np.float32)
    self._ys_input = np.array(bias_temp, dtype=np.float32)
    logging.info("Isotonic calibration finished.")
    if debug:
      return np.array(weight_temp), np.array(bias_temp)
    return None

  def save(self, save_dir, name="default", verbose=False):
    '''Save the calibrator into the given save_directory.
    Arguments:
      save_dir:
        name of the saving directory. Default (string): "default".
    '''
    if not self._calibrated:
      raise RuntimeError("Expecting prior call to calibrate().Cannot save() prior to calibrate()")

    # This module allows for the calibrator to save be saved as part of
    # Tensorflow Hub (this will allow it to be used in further steps)
    logging.info("You probably do not need to save the isotonic layer. \
                  So feel free to set save to False in the Trainer. \
                  Additionally this only saves the layer not the whole graph.")

    def calibrator_module():
      '''
      Way to save Isotonic layer
      '''
      # The input to isotonic is a dense layer
      inputs = tf.placeholder(tf.float32)
      calibrator_layer = self.to_layer()
      output = calibrator_layer(inputs)
      # creates the signature to the calibrator module
      hub.add_signature(inputs=inputs, outputs=output, name=name)

    # exports the module to the save_dir
    spec = hub.create_module_spec(calibrator_module)
    with tf.Graph().as_default():
      module = hub.Module(spec)
      with tf.Session() as session:
        module.export(save_dir, session)

  def to_layer(self):
    """ Returns a twml.layers.Isotonic Layer that can be used for feature discretization.
    """
    if not self._calibrated:
      raise RuntimeError("Expecting prior call to calibrate()")

    isotonic_layer = twml.layers.Isotonic(
      n_unit=self._xs_input.shape[0], n_bin=self._xs_input.shape[1],
      xs_input=self._xs_input, ys_input=self._ys_input,
      **self._kwargs)

    return isotonic_layer

  def get_layer_args(self, name=None):
    """ Returns layer args. See ``Calibrator.get_layer_args`` for more detailed documentation """
    return {'n_unit': self._xs_input.shape[0], 'n_bin': self._xs_input.shape[1]}
