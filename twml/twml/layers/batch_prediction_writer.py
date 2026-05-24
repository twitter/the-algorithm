# pylint: disable=no-member, invalid-name
"""
Implementing Writer Layer
"""
from .layer import Layer

import libtwml


class BatchPredictionWriter(Layer):
  """
  A layer that packages keys and values into a BatchPredictionResponse.
  Typically used at the out of an exported model for use in a the PredictionEngine
  (that is, in production).

  Arguments:
      keys:
        keys to hashmap
  Output:
      output:
        a BatchPredictionResponse serialized using Thrift into a uint8 tensor.
   """

  def __init__(self, keys, **kwargs):  # pylint: disable=useless-super-delegation
    super(BatchPredictionWriter, self).__init__(**kwargs)
    self.keys = keys

  def compute_output_shape(self, input_shape):
    """Computes the output shape of the layer given the input shape.

    Args:
      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
        be fully defined (e.g. the batch size may be unknown).

    Raise NotImplementedError.

    """
    raise NotImplementedError

  def call(self, values, **kwargs):  # pylint: disable=unused-argument, arguments-differ
    """The logic of the layer lives here.

    Arguments:
      values:
        values corresponding to keys in hashmap

    Returns:
      The output from the layer
    """
    write_op = libtwml.ops.batch_prediction_response_writer(self.keys, values)
    return write_op
