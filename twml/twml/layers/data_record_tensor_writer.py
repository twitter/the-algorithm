# pylint: disable=no-member, invalid-name
"""
Implementing Writer Layer
"""
from typing import Tuple

import libtwml
import tensorflow.compat.v1 as tf

from .layer import Layer


class DataRecordTensorWriter(Layer):
    """
    A layer that packages keys and dense tensors into a DataRecord.
    This layer was initially added to support exporting user embeddings as tensors.

    Args:
        keys:
            keys to hashmap
    Output:
        output:
            a DataRecord serialized using Thrift into a uint8 tensor
    """

    def __init__(self, keys, **kwargs):  # pylint: disable=useless-super-delegation
        super(DataRecordTensorWriter, self).__init__(**kwargs)
        self.keys = keys

    def compute_output_shape(self, input_shape: Tuple[tf.TensorShape]):
        """Computes the output shape of the layer given the input shape.

        Args:
            input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
                be fully defined (e.g. the batch size may be unknown).

        Raises NotImplementedError.
        """
        raise NotImplementedError

    def call(
        self, values: Tuple[tf.Tensor], **kwargs
    ) -> tf.Tensor:  # pylint: disable=unused-argument, arguments-differ
        """The logic of the layer lives here.

        Args:
            values:
                dense tensors corresponding to keys in hashmap

        Returns:
            The output from the layer
        """
        write_op = libtwml.ops.data_record_tensor_writer(self.keys, values)
        return write_op
