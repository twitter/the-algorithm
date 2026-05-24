"""
Interpolation functions
"""

import libtwml
import tensorflow.compat.v1 as tf
import twml


def linear_interp1(inputs, ref_inputs, ref_outputs):
  """
  Perform 1D linear interpolation.
  Arguments:
    inputs:
      The query input values.
    ref_inputs:
      Reference grid points used for interpolation.
    ref_outputs:
      Reference output values used for interpolation.

  Returns:
    The interpolated outputs for the requested input values.
  """

  inputs = tf.convert_to_tensor(inputs)
  ref_inputs = tf.convert_to_tensor(ref_inputs)
  ref_outputs = tf.convert_to_tensor(ref_outputs)

  ndims = inputs.shape.ndims
  ref_inputs_ndims = ref_inputs.shape.ndims
  ref_outputs_ndims = ref_inputs.shape.ndims

  if (ref_inputs_ndims != ndims):
    raise ValueError("Dimension mismatch. inputs: %d, ref_inputs: %d" % (ndims, ref_inputs_ndims))

  if (ref_outputs_ndims != ndims):
    raise ValueError("Dimension mismatch. inputs: %d, ref_outputs: %d" % (ndims, ref_outputs_ndims))

  if ndims > 2:
    raise ValueError("Input dimensions should be < 2D. But got %d." % ndims)

  original_input_shape = tf.shape(inputs)
  # This is needed because isotonic_calibration expects:
  # - inputs of size [num_samples, num_classes]
  # - ref_inputs, ref_outputs of size [num_classes, num_bins]
  inputs = tf.reshape(inputs, [-1, 1])
  ref_inputs = tf.reshape(ref_inputs, [1, -1])
  ref_outputs = tf.reshape(ref_outputs, [1, -1])

  # isotonic_calibration is simply doing linear interpolation.
  # This needs to be renamed in the future to make it consistent.
  outputs = libtwml.ops.isotonic_calibration(inputs, ref_inputs, ref_outputs)
  return tf.reshape(outputs, original_input_shape)


def linear_interp1_by_class(inputs, input_classes, ref_inputs, ref_outputs):
  """
  Perform 1D linear interpolation.
  Arguments:
    inputs:
      The query input values.
    input_classes:
      The class index to use from the reference grid.
    ref_inputs:
      Reference 2D grid points used for interpolation.
      Each row denotes the grid from a different class.
    ref_outputs:
      Reference 2D output values used for interpolation.
      Each row denotes the grid from a different class.

  Returns:
    The interpolated outputs for the requested input values.
  """

  inputs = tf.convert_to_tensor(inputs)
  input_classes = tf.convert_to_tensor(input_classes)
  ref_inputs = tf.convert_to_tensor(ref_inputs)
  ref_outputs = tf.convert_to_tensor(ref_outputs)

  original_input_shape = tf.shape(inputs)

  # pass through
  def in_func(x):
    return x

  # indexed function
  def cond_func(i, fn):
    idx = input_classes[i]
    x = tf.expand_dims(fn(), axis=0)
    return linear_interp1(x, ref_inputs[idx], ref_outputs[idx])

  # Use while loop for now, needs to be replace by a custom C++ op later.
  outputs = twml.util.batch_apply(in_func, inputs, cond_func=cond_func)
  return tf.reshape(outputs, original_input_shape)
