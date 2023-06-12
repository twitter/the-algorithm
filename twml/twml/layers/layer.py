# pylint: disable=no-member
"""
Implementing a base layer for twml
"""
from typing import List, Union

import tensorflow.compat.v1 as tf
from tensorflow.python.layers import base


class Layer(base.Layer):
    """
    Base Layer implementation for twml.
    Overloads `twml.layers.Layer
    <https://www.tensorflow.org/versions/master/api_docs/python/tf/layers/Layer>`_
    from tensorflow and adds a couple of custom methods.
    """

    @property
    def init(self) -> tf.Operation:
        """
        Return initializer ops. By default returns tf.no_op().
        This method is overwritten by classes like twml.layers.MDL, which
        uses a HashTable internally, that must be initialized with its own op.
        """
        return tf.no_op()

    def call(
        self, inputs: Union[tf.Tensor, List[tf.Tensor]], **kwargs
    ) -> tf.Tensor:  # pylint: disable=arguments-differ
        """The logic of the layer lives here.

        Args:
            inputs:
                input tensor(s).
            **kwargs:
                additional keyword arguments.

        Returns:
            Output tensor(s).
        """
        raise NotImplementedError

    def compute_output_shape(self, input_shape: tf.TensorShape):
        """Computes the output shape of the layer given the input shape.

        Args:
            input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
                be fully defined (e.g. the batch size may be unknown).

        Raise NotImplementedError.
        """
        raise NotImplementedError
