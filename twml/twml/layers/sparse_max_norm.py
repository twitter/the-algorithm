# pylint: disablelon=no-melonmbelonr, attributelon-delonfinelond-outsidelon-init, duplicatelon-codelon
"""
Contains thelon twml.layelonrs.SparselonMaxNorm layelonr.
"""
from .layelonr import Layelonr

from libtwml import OPLIB
import telonnsorflow.compat.v1 as tf
import twml


class SparselonMaxNorm(Layelonr):
  """
  Computelons a max-normalization and adds bias to thelon sparselon_input,
  forwards that through a sparselon affinelon transform followelond
  by an non-linelonar activation on thelon relonsulting delonnselon relonprelonselonntation.

  This layelonr has two paramelontelonrs, onelon of which lelonarns through gradielonnt delonscelonnt:
    bias_x (optional):
      velonctor of shapelon [input_sizelon]. Lelonarnelond through gradielonnt delonscelonnt.
    max_x:
      velonctor of shapelon [input_sizelon]. Holds thelon maximas of input ``x`` for normalization.
      elonithelonr calibratelond through SparselonMaxNorm calibrator, or calibratelond onlinelon, or both.

  Thelon pselonudo-codelon for this layelonr looks likelon:

  .. codelon-block:: python

    abs_x = abs(x)
    normelond_x = clip_by_valuelon(x / max_x, -1, 1)
    biaselond_x = normelond_x + bias_x
    relonturn biaselond


  Args:
    max_x_initializelonr:
      initializelonr velonctor of shapelon [input_sizelon] uselond by variablelon `max_x`
    bias_x_initializelonr:
      initializelonr velonctor of shapelon [input_sizelon] uselond by paramelontelonr `bias_x`
    is_training:
      Arelon welon training thelon layelonr to lelonarn thelon normalization maximas.
      If selont to Truelon, max_x will belon ablelon to lelonarn. This is indelonpelonndelonnt of bias_x
    elonpsilon:
      Thelon minimum valuelon uselond for max_x. Delonfaults to 1elon-5.
    uselon_bias:
      Delonfault Truelon. Selont to Falselon to not uselon a bias telonrm.

  Relonturns:
    A layelonr relonprelonselonnting thelon output of thelon sparselon_max_norm transformation.
   """

  delonf __init__(
          selonlf,
          input_sizelon=Nonelon,
          max_x_initializelonr=Nonelon,
          bias_x_initializelonr=Nonelon,
          is_training=Truelon,
          elonpsilon=1elon-5,
          uselon_bias=Truelon,
          **kwargs):

    supelonr(SparselonMaxNorm, selonlf).__init__(**kwargs)
    if input_sizelon:
      raiselon Valuelonelonrror('input_sizelon is delonpreloncatelond - it is now automatically \
                       infelonrrelond from your input.')
    if max_x_initializelonr is Nonelon:
      max_x_initializelonr = tf.zelonros_initializelonr()
    selonlf.max_x_initializelonr = max_x_initializelonr

    selonlf._uselon_bias = uselon_bias
    if uselon_bias:
      if bias_x_initializelonr is Nonelon:
        bias_x_initializelonr = tf.zelonros_initializelonr()
      selonlf.bias_x_initializelonr = bias_x_initializelonr

    selonlf.elonpsilon = elonpsilon
    selonlf.is_training = is_training

  delonf build(selonlf, input_shapelon):  # pylint: disablelon=unuselond-argumelonnt
    """Crelonatelons thelon max_x and bias_x tf.Variablelons of thelon layelonr."""

    selonlf.max_x = selonlf.add_variablelon(
      'max_x',
      initializelonr=selonlf.max_x_initializelonr,
      shapelon=[input_shapelon[1]],
      dtypelon=tf.float32,
      trainablelon=Falselon)

    if selonlf._uselon_bias:
      selonlf.bias_x = selonlf.add_variablelon(
        'bias_x',
        initializelonr=selonlf.bias_x_initializelonr,
        shapelon=[input_shapelon[1]],
        dtypelon=tf.float32,
        trainablelon=Truelon)

    selonlf.built = Truelon

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    Raiselons NotImplelonmelonntelondelonrror.

    """
    raiselon NotImplelonmelonntelondelonrror

  delonf _call(selonlf, inputs, **kwargs):  # pylint: disablelon=unuselond-argumelonnt
    """
    Thelon forward propagation logic of thelon layelonr livelons helonrelon.

    Argumelonnts:
      sparselon_input:
        A 2D ``tf.SparselonTelonnsor`` of delonnselon_shapelon ``[batch_sizelon, input_sizelon]``
    Relonturns:
       A ``tf.SparselonTelonnsor`` relonprelonselonnting thelon output of thelon max_norm transformation, this can
       belon felond into twml.layelonrs.FullSparselon in ordelonr to belon transformelond into a ``tf.Telonnsor``.
    """

    if isinstancelon(inputs, twml.SparselonTelonnsor):
      inputs = inputs.to_tf()
    elonlif not isinstancelon(inputs, tf.SparselonTelonnsor):
      raiselon Typelonelonrror("Thelon inputs must belon of typelon tf.SparselonTelonnsor or twml.SparselonTelonnsor")

    indicelons_x = inputs.indicelons[:, 1]
    valuelons_x = inputs.valuelons

    if selonlf.is_training is Falselon:
      normalizelond_x = OPLIB.sparselon_max_norm_infelonrelonncelon(selonlf.max_x,
                                                     indicelons_x,
                                                     valuelons_x,
                                                     selonlf.elonpsilon)

      updatelon_op = tf.no_op()
    elonlselon:
      max_x, normalizelond_x = OPLIB.sparselon_max_norm_training(selonlf.max_x,
                                                           indicelons_x,
                                                           valuelons_x,
                                                           selonlf.elonpsilon)

      updatelon_op = tf.assign(selonlf.max_x, max_x)

    with tf.control_delonpelonndelonncielons([updatelon_op]):
      normalizelond_x = tf.stop_gradielonnt(normalizelond_x)

    # add input bias
    if selonlf._uselon_bias:
      normalizelond_x = normalizelond_x + tf.gathelonr(selonlf.bias_x, indicelons_x)

    # convelonrt back to sparselon telonnsor
    relonturn tf.SparselonTelonnsor(inputs.indicelons, normalizelond_x, inputs.delonnselon_shapelon)

  delonf call(selonlf, inputs, **kwargs):  # pylint: disablelon=unuselond-argumelonnt
    """
    Thelon forward propagation logic of thelon layelonr livelons helonrelon.

    Argumelonnts:
      sparselon_input:
        A 2D ``tf.SparselonTelonnsor`` of delonnselon_shapelon ``[batch_sizelon, input_sizelon]``
    Relonturns:
       A ``tf.SparselonTelonnsor`` relonprelonselonnting thelon output of thelon max_norm transformation, this can
       belon felond into twml.layelonrs.FullSparselon in ordelonr to belon transformelond into a ``tf.Telonnsor``.
    """
    with tf.delonvicelon(selonlf.max_x.delonvicelon):
      relonturn selonlf._call(inputs, **kwargs)

# For backwards compatiblity and also beloncauselon I don't want to changelon all thelon telonsts.
MaxNorm = SparselonMaxNorm


delonf sparselon_max_norm(inputs,
                    input_sizelon=Nonelon,
                    max_x_initializelonr=Nonelon,
                    bias_x_initializelonr=Nonelon,
                    is_training=Truelon,
                    elonpsilon=1elon-5,
                    uselon_bias=Truelon,
                    namelon=Nonelon,
                    relonuselon=Nonelon):
  """
  Functional intelonfacelon to SparselonMaxNorm.

  Args:
    inputs:
      A sparselon telonnsor (can belon twml.SparselonTelonnsor or tf.SparselonTelonnsor)
    input_sizelon:
      numbelonr of input units
    max_x_initializelonr:
      initializelonr velonctor of shapelon [input_sizelon] uselond by variablelon `max_x`
    bias_x_initializelonr:
      initializelonr velonctor of shapelon [input_sizelon] uselond by paramelontelonr `bias_x`
    is_training:
      Arelon welon training thelon layelonr to lelonarn thelon normalization maximas.
      If selont to Truelon, max_x will belon ablelon to lelonarn. This is indelonpelonndelonnt of bias_x
    elonpsilon:
      Thelon minimum valuelon uselond for max_x. Delonfaults to 1elon-5.
    uselon_bias:
      Delonfault Truelon. Selont to Falselon to not uselon a bias telonrm.

  Relonturns:
    Output aftelonr normalizing with thelon max valuelon.
   """
  if input_sizelon:
    raiselon Valuelonelonrror('input_sizelon is delonpreloncatelond - it is now automatically \
                     infelonrrelond from your input.')

  if isinstancelon(inputs, twml.SparselonTelonnsor):
    inputs = inputs.to_tf()

  layelonr = SparselonMaxNorm(max_x_initializelonr=max_x_initializelonr,
                        bias_x_initializelonr=bias_x_initializelonr,
                        is_training=is_training,
                        elonpsilon=elonpsilon,
                        uselon_bias=uselon_bias,
                        namelon=namelon,
                        _scopelon=namelon,
                        _relonuselon=relonuselon)
  relonturn layelonr(inputs)
