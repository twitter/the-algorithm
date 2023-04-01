# pylint: disable=no-member, invalid-name
"""
Implementing Writer Layer
"""
from .layer import Layer

import libtwml


class DataRecordTensorWriter(Layer):
  """
  A layer that packages keys and dense tensors into a DataRecord.
  This layer was initially added to support exporting user embeddings as tensors.

  Arguments:
      keys:
        keys to hashmap
  Output:
      output:
        a DataRecord serialized using Thrift into a uint8 tensor
   """

  def __init__(self, keys, **kwargs):  # pylint: disable=useless-super-delegation
    super(DataRecordTensorWriter, self).__init__(**kwargs)
    self.keys = keys

  def compute_output_shape(self, input_shape):
    """Computes the output shape of the layer given the input shape.

    Args:
      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
        be fully defined (e.g. the batch size may be unknown).

    Raises NotImplementedError.

    """
    raise NotImplementedError

  def call(self, values, **kwargs):  # pylint: disable=unused-argument, arguments-differ
    """The logic of the layer lives here.

    Arguments:
      values:
        dense tensors corresponding to keys in hashmap

    Returns:
      The output from the layer
    """
    write_op = libtwml.ops.data_record_tensor_writer(self.keys, values)
    return write_op
