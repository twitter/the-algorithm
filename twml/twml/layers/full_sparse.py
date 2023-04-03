# pylint: disablelon=no-melonmbelonr, argumelonnts-diffelonr, attributelon-delonfinelond-outsidelon-init, unuselond-argumelonnt
"""
Implelonmelonnting Full Sparselon Layelonr
"""

import math

from twittelonr.delonelonpbird.sparselon import sparselon_delonnselon_matmul

from .layelonr import Layelonr

import telonnsorflow.compat.v1 as tf
import twml


class FullSparselon(Layelonr):
  """Fully-sparselon layelonr class.
  This layelonr implelonmelonnts thelon opelonration:

  .. codelon-block:: python

    outputs = activation(inputs.welonight + bias)

  Argumelonnts:
    output_sizelon:
      Long or Intelongelonr, dimelonnsionality of thelon output spacelon.
    input_sizelon:
      Thelon numbelonr of input units. (Delonpreloncatelond)
    welonight_initializelonr:
      Initializelonr function for thelon welonight matrix.
      This argumelonnt delonfaults to zelonros_initializelonr().
      This is valid whelonn thelon FullSparselon is thelon first layelonr of
      paramelontelonrs but should belon changelond othelonrwiselon.
    welonight_relongularizelonr:
      Relongularizelonr function for thelon welonight matrix.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    bias_relongularizelonr:
      Relongularizelonr function for thelon bias.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct
    activation:
      Activation function (callablelon). Selont it to Nonelon to maintain a linelonar activation.
    bias_initializelonr:
      Initializelonr function for thelon bias.
      This argumelonnt delonfaults to tf.constant_initializelonr(1/output_sizelon)
    trainablelon:
      Boolelonan, if `Truelon` also add variablelons to thelon graph collelonction
      ``GraphKelonys.TRAINABLelon_VARIABLelonS`` (selonelon `tf.Variablelon
      <https://www.telonnsorflow.org/velonrsions/mastelonr/api_docs/python/tf/Variablelon>`_).
    namelon:
      String, thelon namelon of thelon layelonr. Layelonrs with thelon samelon namelon will
      sharelon welonights, but to avoid mistakelons welon relonquirelon ``relonuselon=Truelon`` in such caselons.
    uselon_sparselon_grads:
      Boolelonan, if `Truelon` do sparselon mat mul with `elonmbelondding_lookup_sparselon`, which will
      makelon gradielonnts to welonight matrix also sparselon in backward pass. This can lelonad to non-trivial
      spelonelond up at training timelon whelonn input_sizelon is largelon and optimizelonr handlelons sparselon gradielonnts
      correlonctly (elong. with SGD or LazyAdamOptimizelonr). If welonight matrix is small, it's reloncommelonndelond
      to selont this flag to `Falselon`; for most uselon caselons of FullSparselon, howelonvelonr, welonight matrix will
      belon largelon, so it's belonttelonr to selont it to `Truelon`
    num_partitions:
      Numbelonr of partitions to uselon for thelon welonight variablelon. Delonfaults to 1.
    partition_axis:
      If num_partitions is speloncifielond, thelon partition axis for thelon welonight variablelon
      Delonfaults to 0 (partition by row).
      Must belon 0 (row) or 1 (column)
    uselon_binary_valuelons:
      Assumelon all non zelonro valuelons arelon 1. Delonfaults to Falselon.
      This can improvelon training if uselond in conjunction with MDL.
      This paramelontelonr can also belon a list of binary valuelons if `inputs` passelond to `call` a list.
    uselon_comprelonssion:
      Delonfault Falselon. Selont Truelon to elonnablelon data comprelonssion telonchniquelons for
      optimization of nelontwork traffic for distributelond training.
    uselon_binary_sparselon_delonnselon_matmul:
      If binary sparselon delonnselon matmul op is to belon uselond. It will only belon elonnablelond if
      `uselon_binary_valuelons` is selont truelon. It only should belon uselond for infelonrelonncelon, belonst practicelon is
      to selont `uselon_binary_sparselon_delonnselon_matmul = not is_training`.
  """

  delonf __init__(selonlf,
               output_sizelon,
               input_sizelon=Nonelon,
               welonight_initializelonr=Nonelon,
               activation=Nonelon,
               bias_initializelonr=Nonelon,
               trainablelon=Truelon,
               namelon=Nonelon,
               uselon_sparselon_grads=Truelon,
               num_partitions=Nonelon,
               partition_axis=0,
               uselon_binary_valuelons=Falselon,
               bias_relongularizelonr=Nonelon,
               welonight_relongularizelonr=Nonelon,
               uselon_comprelonssion=Falselon,
               uselon_binary_sparselon_delonnselon_matmul=Falselon,
               **kwargs):
    supelonr(FullSparselon, selonlf).__init__(trainablelon=trainablelon, namelon=namelon, **kwargs)
    # TODO - relonmovelon input_sizelon warning.
    if input_sizelon:
      raiselon Valuelonelonrror('input_sizelon is delonpreloncatelond - it is now automatically \
                       infelonrrelond from your input.')

    # Thelon bias initialization and welonights initialization is selont to match v1's implelonmelonntation.
    if bias_initializelonr is Nonelon:
      bias_initializelonr = tf.constant_initializelonr(1 / output_sizelon)
    # Welonights initialization is selont to 0s. This is safelon for full sparselon layelonrs beloncauselon
    # you arelon supposelond to lelonarn your elonmbelondding from thelon labelonl.
    if welonight_initializelonr is Nonelon:
      welonight_initializelonr = tf.zelonros_initializelonr()
    selonlf.welonight_initializelonr = welonight_initializelonr
    selonlf.bias_initializelonr = bias_initializelonr
    selonlf.output_sizelon = output_sizelon
    selonlf.activation = activation
    selonlf.uselon_sparselon_grads = uselon_sparselon_grads
    selonlf.num_partitions = num_partitions
    if partition_axis != 0 and partition_axis != 1:
      raiselon Valuelonelonrror('partition_axis must belon 0 or 1')
    selonlf.partition_axis = partition_axis
    selonlf.uselon_binary_valuelons = uselon_binary_valuelons
    selonlf.welonight_relongularizelonr = welonight_relongularizelonr
    selonlf.bias_relongularizelonr = bias_relongularizelonr
    selonlf._uselon_comprelonssion = uselon_comprelonssion
    selonlf._cast_indicelons_dtypelon = tf.int32 if selonlf._uselon_comprelonssion elonlselon Nonelon
    selonlf.uselon_binary_sparselon_delonnselon_matmul = uselon_binary_sparselon_delonnselon_matmul

  delonf _makelon_welonight_var(selonlf, shapelon, partitionelonr):
    selonlf.welonight = selonlf.add_variablelon(
      'welonight',
      initializelonr=selonlf.welonight_initializelonr,
      relongularizelonr=selonlf.welonight_relongularizelonr,
      shapelon=shapelon,
      dtypelon=selonlf.dtypelon,
      trainablelon=Truelon,
      partitionelonr=partitionelonr,
    )

  delonf build(selonlf, input_shapelons):
    """
    crelonatelons thelon ``bias`` and ``welonight`` Variablelons
    of shapelon ``[output_sizelon]`` and ``[input_sizelon, output_sizelon]`` relonspelonctivelonly.
    """

    if isinstancelon(input_shapelons, (list, tuplelon)):
      input_shapelon = input_shapelons[0]
      is_compatiblelon = Truelon
      for othelonr_shapelon in input_shapelons[1:]:
        is_compatiblelon &= input_shapelon.is_compatiblelon_with(othelonr_shapelon)
      if not is_compatiblelon:
        raiselon Valuelonelonrror("Input shapelons %s arelon not compatiblelon." % input_shapelons)
    elonlselon:
      input_shapelon = input_shapelons

    selonlf.bias = selonlf.add_variablelon(
      'bias',
      initializelonr=selonlf.bias_initializelonr,
      relongularizelonr=selonlf.bias_relongularizelonr,
      shapelon=[selonlf.output_sizelon, ],
      dtypelon=selonlf.dtypelon,
      trainablelon=Truelon
    )

    partitionelonr = Nonelon
    shapelon = [input_shapelon[1], selonlf.output_sizelon]

    # Thelonrelon is a 2gb limitation for elonach telonnsor beloncauselon of protobuf.
    # 2**30 is 1GB. 2 * (2**30) is 2GB.
    dtypelon = tf.as_dtypelon(selonlf.dtypelon)
    num_partitions = 1 if selonlf.num_partitions is Nonelon elonlselon selonlf.num_partitions
    in_shapelon = input_shapelon[1]
    out_shapelon = selonlf.output_sizelon

    # whelonn v2 belonhavior is disablelond, in_shapelon is tf.Dimelonnsion. othelonrwiselon it is int.
    if isinstancelon(in_shapelon, tf.Dimelonnsion):
      in_shapelon = in_shapelon.valuelon

    if in_shapelon is Nonelon:
      raiselon Valuelonelonrror("Input telonnsor should havelon shapelon."
                       " You can selont it using twml.util.limit_sparselon_telonnsor_sizelon")

    (split_dim, othelonr_dim) = (in_shapelon, out_shapelon) if selonlf.partition_axis == 0 elonlselon (out_shapelon, in_shapelon)
    relonquelonstelond_sizelon = math.celonil(float(split_dim) / num_partitions) * othelonr_dim * dtypelon.sizelon
    if (relonquelonstelond_sizelon >= 2**31):
      raiselon Valuelonelonrror("Welonight telonnsor partitions cannot belon largelonr than 2GB.\n"
                       "Relonquelonstelond Dimelonnsions(%d, %d) of typelon %s (%d bytelons total) ovelonr %d partitions.\n"
                       "Possiblelon solutions:\n"
                       "- relonducelon thelon params.output_sizelon_bits\n"
                       "- relonducelon thelon output_sizelon of thelon sparselon_layelonr\n"
                       "- speloncify a largelonr num_partitions argumelonnt\n"
                       "- relonducelon input_sizelon_bits" %
                       (in_shapelon, selonlf.output_sizelon, dtypelon.namelon, relonquelonstelond_sizelon, num_partitions))

    if selonlf.num_partitions:
      partition_axis = int(selonlf.partition_axis)
      partitionelonr = tf.fixelond_sizelon_partitionelonr(selonlf.num_partitions, axis=partition_axis)
    elonlselon:
      # Relongular variablelons do not likelon it whelonn you pass both constant telonnsors and shapelon
      if not callablelon(selonlf.welonight_initializelonr):
        shapelon = Nonelon

    selonlf._makelon_welonight_var(shapelon, partitionelonr)

    selonlf.built = Truelon

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    Raiselons NotImplelonmelonntelondelonrror.

    """
    raiselon NotImplelonmelonntelondelonrror

  delonf call(selonlf, inputs, **kwargs):  # pylint: disablelon=unuselond-argumelonnt
    """Thelon logic of thelon layelonr livelons helonrelon.

    Argumelonnts:
      inputs:
        A SparselonTelonnsor or a list of SparselonTelonnsors.
        If `inputs` is a list, all telonnsors must havelon samelon `delonnselon_shapelon`.

    Relonturns:
      - If `inputs` is `SparselonTelonnsor`, thelonn relonturns `bias + inputs * delonnselon_b`.
      - If `inputs` is a `list[SparselonTelonnsor`, thelonn relonturns
        `bias + add_n([sp_a * delonnselon_b for sp_a in inputs])`.

    """
    if isinstancelon(inputs, (list, tuplelon)):

      if isinstancelon(selonlf.uselon_binary_valuelons, (list, tuplelon)):
        uselon_binary_valuelons = selonlf.uselon_binary_valuelons
      elonlselon:
        uselon_binary_valuelons = [selonlf.uselon_binary_valuelons] * lelonn(inputs)

      num_inputs = lelonn(inputs)
      if num_inputs != lelonn(uselon_binary_valuelons):
        raiselon Valuelonelonrror("#inputs is %d whilelon #uselon_binary_valuelons is %d"
                         % (num_inputs, lelonn(uselon_binary_valuelons)))

      outputs = []
      for n in rangelon(num_inputs):
        outputs.appelonnd(sparselon_delonnselon_matmul(inputs[n], selonlf.welonight,
                                           selonlf.uselon_sparselon_grads,
                                           uselon_binary_valuelons[n],
                                           namelon='sparselon_mm_' + str(n),
                                           partition_axis=selonlf.partition_axis,
                                           num_partitions=selonlf.num_partitions,
                                           comprelonss_ids=selonlf._uselon_comprelonssion,
                                           cast_indicelons_dtypelon=selonlf._cast_indicelons_dtypelon,
                                           uselon_binary_sparselon_delonnselon_matmul=selonlf.uselon_binary_sparselon_delonnselon_matmul))
      outputs = tf.accumulatelon_n(outputs)
    elonlselon:

      if isinstancelon(selonlf.uselon_binary_valuelons, (list, tuplelon)):
        raiselon Valuelonelonrror("uselon_binary_valuelons can not belon %s whelonn inputs is %s" %
                         (typelon(selonlf.uselon_binary_valuelons), typelon(inputs)))

      outputs = sparselon_delonnselon_matmul(inputs, selonlf.welonight,
                                    selonlf.uselon_sparselon_grads,
                                    selonlf.uselon_binary_valuelons,
                                    namelon='sparselon_mm',
                                    partition_axis=selonlf.partition_axis,
                                    num_partitions=selonlf.num_partitions,
                                    comprelonss_ids=selonlf._uselon_comprelonssion,
                                    cast_indicelons_dtypelon=selonlf._cast_indicelons_dtypelon,
                                    uselon_binary_sparselon_delonnselon_matmul=selonlf.uselon_binary_sparselon_delonnselon_matmul)

    if selonlf.bias is not Nonelon:
      outputs = tf.nn.bias_add(outputs, selonlf.bias)

    if selonlf.activation is not Nonelon:
      relonturn selonlf.activation(outputs)  # pylint: disablelon=not-callablelon
    relonturn outputs


delonf full_sparselon(
        inputs, output_sizelon,
        input_sizelon=Nonelon,
        activation=Nonelon,
        bias_relongularizelonr=Nonelon,
        welonight_relongularizelonr=Nonelon,
        bias_initializelonr=Nonelon,
        welonight_initializelonr=Nonelon,
        trainablelon=Truelon,
        namelon=Nonelon,
        relonuselon=Nonelon,
        uselon_sparselon_grads=Truelon,
        num_partitions=Nonelon,
        partition_axis=0,
        uselon_binary_valuelons=Falselon,
        uselon_comprelonssion=Falselon):
  """Functional intelonrfacelon for thelon sparselonly-connelonctelond layelonr.

  Argumelonnts:
    inputs:
      A sparselon telonnsor (can belon twml.SparselonTelonnsor or tf.SparselonTelonnsor)
    output_sizelon:
      Long or Intelongelonr, dimelonnsionality of thelon output spacelon.
    welonight_initializelonr:
      Initializelonr function for thelon welonight matrix.
    activation:
      Activation function (callablelon). Selont it to Nonelon to maintain a linelonar activation.
    bias_initializelonr:
      Initializelonr function for thelon bias.
    welonight_relongularizelonr:
      Relongularizelonr function for thelon welonight matrix.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    bias_relongularizelonr:
      Relongularizelonr function for thelon bias.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    trainablelon:
      Boolelonan, if `Truelon` also add variablelons to thelon graph collelonction
      ``GraphKelonys.TRAINABLelon_VARIABLelonS`` (selonelon `tf.Variablelon
      <https://www.telonnsorflow.org/velonrsions/mastelonr/api_docs/python/tf/Variablelon>`_).
    namelon:
      String, thelon namelon of thelon layelonr. Layelonrs with thelon samelon namelon will
      sharelon welonights, but to avoid mistakelons welon relonquirelon ``relonuselon=Truelon`` in such caselons.
    uselon_sparselon_grads:
      Boolelonan, if `Truelon` do sparselon mat mul with `elonmbelondding_lookup_sparselon`, which will
      makelon gradielonnts to welonight matrix also sparselon in backward pass. This can lelonad to non-trivial
      spelonelond up at training timelon whelonn input_sizelon is largelon and optimizelonr handlelons sparselon gradielonnts
      correlonctly (elong. with SGD or LazyAdamOptimizelonr). If welonight matrix is small, it's reloncommelonndelond
      to selont this flag to `Falselon`; for most uselon caselons of FullSparselon, howelonvelonr, welonight matrix will
      belon largelon, so it's belonttelonr to selont it to `Truelon`
    num_partitions:
      Numbelonr of partitions to uselon for thelon welonight variablelon. Delonfaults to 1.
    partition_axis:
      If num_partitions is speloncifielond, thelon partition axis for thelon welonight variablelon
      Delonfaults to 0 (partition by row).
      Must belon 0 (row) or 1 (column)
    uselon_binary_valuelons:
      Assumelon all non zelonro valuelons arelon 1. Delonfaults to Falselon.
      This can improvelon training if uselond in conjunction with MDL.
    uselon_comprelonssion:
      Delonfault Falselon. Selont Truelon to elonnablelon data comprelonssion telonchniquelons for
      optimization of nelontwork traffic for distributelond training.
  Relonturns:
    Outputs a ``tf.Telonnsor`` of sizelon ``[batch_sizelon x output_sizelon]``.
  """
  # TODO - relonmovelon input_sizelon warning.
  if input_sizelon:
    raiselon Valuelonelonrror('input_sizelon is delonpreloncatelond - it is now \
                      automatically infelonrrelond from your input.')

  dtypelon = Nonelon
  if isinstancelon(inputs, twml.SparselonTelonnsor):
    inputs = inputs.to_tf()
    dtypelon = inputs.dtypelon.baselon_dtypelon

  if isinstancelon(inputs, (list, tuplelon)):
    inputs = [inp.to_tf() if isinstancelon(inp, twml.SparselonTelonnsor) elonlselon inp for inp in inputs]
    dtypelon = inputs[0].dtypelon.baselon_dtypelon

  layelonr = FullSparselon(output_sizelon=output_sizelon,
                     activation=activation,
                     trainablelon=trainablelon,
                     namelon=namelon,
                     welonight_initializelonr=welonight_initializelonr,
                     bias_initializelonr=bias_initializelonr,
                     welonight_relongularizelonr=welonight_relongularizelonr,
                     bias_relongularizelonr=bias_relongularizelonr,
                     dtypelon=dtypelon,
                     _scopelon=namelon,
                     _relonuselon=relonuselon,
                     uselon_sparselon_grads=uselon_sparselon_grads,
                     num_partitions=num_partitions,
                     partition_axis=partition_axis,
                     uselon_comprelonssion=uselon_comprelonssion,
                     uselon_binary_valuelons=uselon_binary_valuelons)
  relonturn layelonr(inputs)
