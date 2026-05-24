# pylint: disable=missing-docstring, unused-argument
''' Contains the base classes for CalibrationFeature and Calibrator '''


from collections import defaultdict

import numpy as np
import tensorflow.compat.v1 as tf
import tensorflow_hub as hub
import twml
import twml.util


class CalibrationFeature(object):
  '''
  Accumulates values and weights for individual features.
  Typically, each unique feature defined in the accumulated SparseTensor or Tensor
  would have its own CalibrationFeature instance.
  '''

  def __init__(self, feature_id):
    ''' Constructs a CalibrationFeature

    Arguments:
      feature_id:
        number identifying the feature.
    '''
    self.feature_id = feature_id
    self._calibrated = False
    self._features_dict = defaultdict(list)

  def add_values(self, new_features):
    '''
    Extends lists to contain the values in this batch
    '''
    for key in new_features:
      self._features_dict[key].append(new_features[key])

  def _concat_arrays(self):
    '''
    This class calls this function after you have added all the values.
    It creates a dictionary with the concatanated arrays
    '''
    self._features_dict.update((k, np.concatenate(v)) for k, v in self._features_dict.items())

  def calibrate(self, *args, **kwargs):
    raise NotImplementedError


class Calibrator(object):
  '''
  Accumulates features and their respective values for Calibration
  The steps for calibration are typically as follows:

   1. accumulate feature values from batches by calling ``accumulate()`` and;
   2. calibrate by calling ``calibrate()``;
   3. convert to a twml.layers layer by calling ``to_layer()``.

  Note you can only use one calibrator per Trainer.
  '''

  def __init__(self, calibrator_name=None, **kwargs):
    '''
    Arguments:
      calibrator_name.
        Default: if set to None it will be the same as the class name.
        Please be reminded that if in the model there are many calibrators
        of the same type the calibrator_name should be changed to avoid confusion.
    '''
    self._calibrated = False
    if calibrator_name is None:
      calibrator_name = twml.util.to_snake_case(self.__class__.__name__)
    self._calibrator_name = calibrator_name
    self._kwargs = kwargs

  @property
  def is_calibrated(self):
    return self._calibrated

  @property
  def name(self):
    return self._calibrator_name

  def accumulate(self, *args, **kwargs):
    '''Accumulates features and their respective values for Calibration.'''
    raise NotImplementedError

  def calibrate(self):
    '''Calibrates after the accumulation has ended.'''
    self._calibrated = True

  def to_layer(self, name=None):
    '''
    Returns a twml.layers.Layer instance with the result of calibrator.

    Arguments:
      name:
        name-scope of the layer
    '''
    raise NotImplementedError

  def get_layer_args(self):
    '''
    Returns layer arguments required to implement multi-phase training.

    Returns:
      dictionary of Layer constructor arguments to initialize the
      layer Variables. Typically, this should contain enough information
      to initialize empty layer Variables of the correct size, which will then
      be filled with the right data using init_map.
    '''
    raise NotImplementedError

  def save(self, save_dir, name="default", verbose=False):
    '''Save the calibrator into the given save_directory.
    Arguments:
      save_dir:
        name of the saving directory. Default (string): "default".
      name:
        name for the calibrator.
    '''
    if not self._calibrated:
      raise RuntimeError("Expecting prior call to calibrate().Cannot save() prior to calibrate()")

    # This module allows for the calibrator to save be saved as part of
    # Tensorflow Hub (this will allow it to be used in further steps)
    def calibrator_module():
      # Note that this is usually expecting a sparse_placeholder
      inputs = tf.sparse_placeholder(tf.float32)
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

  def write_summary(self, writer, sess=None):
    """
    This method is called by save() to write tensorboard summaries to disk.
    See MDLCalibrator.write_summary for an example.
    By default, the method does nothing. It can be overloaded by child-classes.

    Arguments:
      writer:
        `tf.summary.FilteWriter
        <https://www.tensorflow.org/versions/master/api_docs/python/tf/summary/FileWriter>`_
        instance.
        The ``writer`` is used to add summaries to event files for inclusion in tensorboard.
      sess (optional):
        `tf.Session <https://www.tensorflow.org/versions/master/api_docs/python/tf/Session>`_
        instance. The ``sess`` is used to produces summaries for the writer.
    """
