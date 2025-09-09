import tensorflow.compat.v1 as tf

from twml.contrib.pruning import apply_mask
from twml.layers import Layer


class MaskLayer(Layer):
    """
    This layer corresponds to `twml.contrib.pruning.apply_mask`.

    It applies a binary mask to mask out channels of a given tensor. The masks can be
    optimized using `twml.contrib.trainers.PruningDataRecordTrainer`.
    """

    def call(self, inputs: tf.Tensor, **kwargs):
        """
        Applies a binary mask to the channels of the input.

        Args:
            inputs:
                input tensor
            **kwargs:
                additional keyword arguments

        Returns:
            Masked tensor
        """
        return apply_mask(inputs)

    def compute_output_shape(self, input_shape: tf.TensorShape) -> tf.TensorShape:
        return input_shape
