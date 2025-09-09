# pylint: disable=no-member, invalid-name, attribute-defined-outside-init
"""
Contains the Isotonic Layer
"""

from typing import Optional

import libtwml
import numpy as np
import tensorflow.compat.v1 as tf

from .layer import Layer


class Isotonic(Layer):
    """
    This layer is created by the IsotonicCalibrator.
    Typically it is used instead of sigmoid activation on the output unit.

    Args:
        n_unit:
            number of input units to the layer (same as number of output units).
        n_bin:
            number of bins used for isotonic calibration.
            More bins means a more precise isotonic function.
            Less bins means a more regularized isotonic function.
        xs_input:
            A tensor containing the boundaries of the bins.
        ys_input:
            A tensor containing calibrated values for the corresponding bins.

    Output:
        output:
            A layer containing calibrated probabilities with same shape and size as input.
    Expected Sizes:
        xs_input, ys_input:
            [n_unit, n_bin].
    Expected Types:
        xs_input, ys_input:
            same as input.
    """

    def __init__(
        self,
        n_unit: int,
        n_bin: int,
        xs_input: Optional[np.ndarray] = None,
        ys_input: Optional[np.ndarray] = None,
        **kwargs,
    ):
        super(Isotonic, self).__init__(**kwargs)

        self._n_unit = n_unit
        self._n_bin = n_bin

        self.xs_input = (
            np.empty([n_unit, n_bin], dtype=np.float32)
            if xs_input is None
            else xs_input
        )
        self.ys_input = (
            np.empty([n_unit, n_bin], dtype=np.float32)
            if ys_input is None
            else ys_input
        )

    def compute_output_shape(self, input_shape: tf.TensorShape):
        """Computes the output shape of the layer given the input shape.

        Args:
            input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
                be fully defined (e.g. the batch size may be unknown).

        Raises NotImplementedError.

        """
        raise NotImplementedError

    def build(self, input_shape: tf.TensorShape):  # pylint: disable=unused-argument
        """Creates the variables of the layer."""
        self.built = True

    def call(self, inputs: tf.Tensor, **kwargs):  # pylint: disable=unused-argument
        """The logic of the layer lives here.

        Args:
            inputs: input tensor(s).

        Returns:
            The output from the layer
        """
        calibrate_op = libtwml.ops.isotonic_calibration(
            inputs, self.xs_input, self.ys_input
        )
        return calibrate_op
