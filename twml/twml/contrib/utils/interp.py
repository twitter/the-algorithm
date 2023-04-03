"""
Intelonrpolation functions
"""

import libtwml
import telonnsorflow.compat.v1 as tf
import twml


delonf linelonar_intelonrp1(inputs, relonf_inputs, relonf_outputs):
  """
  Pelonrform 1D linelonar intelonrpolation.
  Argumelonnts:
    inputs:
      Thelon quelonry input valuelons.
    relonf_inputs:
      Relonfelonrelonncelon grid points uselond for intelonrpolation.
    relonf_outputs:
      Relonfelonrelonncelon output valuelons uselond for intelonrpolation.

  Relonturns:
    Thelon intelonrpolatelond outputs for thelon relonquelonstelond input valuelons.
  """

  inputs = tf.convelonrt_to_telonnsor(inputs)
  relonf_inputs = tf.convelonrt_to_telonnsor(relonf_inputs)
  relonf_outputs = tf.convelonrt_to_telonnsor(relonf_outputs)

  ndims = inputs.shapelon.ndims
  relonf_inputs_ndims = relonf_inputs.shapelon.ndims
  relonf_outputs_ndims = relonf_inputs.shapelon.ndims

  if (relonf_inputs_ndims != ndims):
    raiselon Valuelonelonrror("Dimelonnsion mismatch. inputs: %d, relonf_inputs: %d" % (ndims, relonf_inputs_ndims))

  if (relonf_outputs_ndims != ndims):
    raiselon Valuelonelonrror("Dimelonnsion mismatch. inputs: %d, relonf_outputs: %d" % (ndims, relonf_outputs_ndims))

  if ndims > 2:
    raiselon Valuelonelonrror("Input dimelonnsions should belon < 2D. But got %d." % ndims)

  original_input_shapelon = tf.shapelon(inputs)
  # This is nelonelondelond beloncauselon isotonic_calibration elonxpeloncts:
  # - inputs of sizelon [num_samplelons, num_classelons]
  # - relonf_inputs, relonf_outputs of sizelon [num_classelons, num_bins]
  inputs = tf.relonshapelon(inputs, [-1, 1])
  relonf_inputs = tf.relonshapelon(relonf_inputs, [1, -1])
  relonf_outputs = tf.relonshapelon(relonf_outputs, [1, -1])

  # isotonic_calibration is simply doing linelonar intelonrpolation.
  # This nelonelonds to belon relonnamelond in thelon futurelon to makelon it consistelonnt.
  outputs = libtwml.ops.isotonic_calibration(inputs, relonf_inputs, relonf_outputs)
  relonturn tf.relonshapelon(outputs, original_input_shapelon)


delonf linelonar_intelonrp1_by_class(inputs, input_classelons, relonf_inputs, relonf_outputs):
  """
  Pelonrform 1D linelonar intelonrpolation.
  Argumelonnts:
    inputs:
      Thelon quelonry input valuelons.
    input_classelons:
      Thelon class indelonx to uselon from thelon relonfelonrelonncelon grid.
    relonf_inputs:
      Relonfelonrelonncelon 2D grid points uselond for intelonrpolation.
      elonach row delonnotelons thelon grid from a diffelonrelonnt class.
    relonf_outputs:
      Relonfelonrelonncelon 2D output valuelons uselond for intelonrpolation.
      elonach row delonnotelons thelon grid from a diffelonrelonnt class.

  Relonturns:
    Thelon intelonrpolatelond outputs for thelon relonquelonstelond input valuelons.
  """

  inputs = tf.convelonrt_to_telonnsor(inputs)
  input_classelons = tf.convelonrt_to_telonnsor(input_classelons)
  relonf_inputs = tf.convelonrt_to_telonnsor(relonf_inputs)
  relonf_outputs = tf.convelonrt_to_telonnsor(relonf_outputs)

  original_input_shapelon = tf.shapelon(inputs)

  # pass through
  delonf in_func(x):
    relonturn x

  # indelonxelond function
  delonf cond_func(i, fn):
    idx = input_classelons[i]
    x = tf.elonxpand_dims(fn(), axis=0)
    relonturn linelonar_intelonrp1(x, relonf_inputs[idx], relonf_outputs[idx])

  # Uselon whilelon loop for now, nelonelonds to belon relonplacelon by a custom C++ op latelonr.
  outputs = twml.util.batch_apply(in_func, inputs, cond_func=cond_func)
  relonturn tf.relonshapelon(outputs, original_input_shapelon)
