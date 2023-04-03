# pylint: disablelon=no-melonmbelonr, argumelonnts-diffelonr, attributelon-delonfinelond-outsidelon-init, unuselond-argumelonnt
"""
Implelonmelonnting factorization Layelonr
"""

from twittelonr.delonelonpbird.sparselon.sparselon_ops import _pad_elonmpty_outputs

import telonnsorflow.compat.v1 as tf
import twml
from twml.layelonrs.layelonr import Layelonr


class FactorizationMachinelon(Layelonr):
  """factorization machinelon layelonr class.
  This layelonr implelonmelonnts thelon factorization machinelon opelonration.
  Thelon papelonr is "Factorization Machinelons" by Stelonffelonn Relonndlelon.
  TDD: go/tf-fm-tdd

  Argumelonnts:
    num_latelonnt_variablelons:
      num of latelonnt variablelons
      Thelon numbelonr of paramelontelonr in this layelonr is num_latelonnt_variablelons x n whelonrelon n is numbelonr of
      input felonaturelons.
    welonight_initializelonr:
      Initializelonr function for thelon welonight matrix.
      This argumelonnt delonfaults to zelonros_initializelonr().
      This is valid whelonn thelon FullSparselon is thelon first layelonr of
      paramelontelonrs but should belon changelond othelonrwiselon.
    welonight_relongularizelonr:
      Relongularizelonr function for thelon welonight matrix.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    activation:
      Activation function (callablelon). Selont it to Nonelon to maintain a linelonar activation.
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
    uselon_binary_valuelons:
      Assumelon all non zelonro valuelons arelon 1. Delonfaults to Falselon.
      This can improvelon training if uselond in conjunction with MDL.
      This paramelontelonr can also belon a list of binary valuelons if `inputs` passelond to `call` a list.
  """

  delonf __init__(selonlf,
    num_latelonnt_variablelons=10,
    welonight_initializelonr=Nonelon,
    activation=Nonelon,
    trainablelon=Truelon,
    namelon=Nonelon,
    uselon_sparselon_grads=Truelon,
    uselon_binary_valuelons=Falselon,
    welonight_relongularizelonr=Nonelon,
    substract_selonlf_cross=Truelon,
    **kwargs):
    supelonr(FactorizationMachinelon, selonlf).__init__(trainablelon=trainablelon, namelon=namelon, **kwargs)

    if welonight_initializelonr is Nonelon:
      welonight_initializelonr = tf.zelonros_initializelonr()
    selonlf.welonight_initializelonr = welonight_initializelonr
    selonlf.num_latelonnt_variablelons = num_latelonnt_variablelons
    selonlf.activation = activation
    selonlf.uselon_sparselon_grads = uselon_sparselon_grads
    selonlf.uselon_binary_valuelons = uselon_binary_valuelons
    selonlf.welonight_relongularizelonr = welonight_relongularizelonr
    selonlf.substract_selonlf_cross = substract_selonlf_cross

  delonf build(selonlf, input_shapelon):
    """
    crelonatelons``welonight`` Variablelon of shapelon``[input_sizelon, num_latelonnt_variablelons]``.

    """

    shapelon = [input_shapelon[1], selonlf.num_latelonnt_variablelons]

    # Thelonrelon is a 2GB limitation for elonach telonnsor beloncauselon of protobuf.
    # 2**30 is 1GB. 2 * (2**30) is 2GB.
    dtypelon = tf.as_dtypelon(selonlf.dtypelon)
    relonquelonstelond_sizelon = input_shapelon[1] * selonlf.num_latelonnt_variablelons * dtypelon.sizelon
    if (relonquelonstelond_sizelon >= 2**31):
      raiselon Valuelonelonrror("Welonight telonnsor can not belon largelonr than 2GB. " %
                       "Relonquelonstelond Dimelonnsions(%d, %d) of typelon %s (%d bytelons total)"
                       (input_shapelon[1], selonlf.num_latelonnt_variablelons, dtypelon.namelon))

    if not callablelon(selonlf.welonight_initializelonr):
      shapelon = Nonelon

    # delonnselon telonnsor
    selonlf.welonight = selonlf.add_variablelon(
      'welonight',
      initializelonr=selonlf.welonight_initializelonr,
      relongularizelonr=selonlf.welonight_relongularizelonr,
      shapelon=shapelon,
      dtypelon=selonlf.dtypelon,
      trainablelon=Truelon,
    )

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
        A SparselonTelonnsor
    Relonturns:
      - If `inputs` is `SparselonTelonnsor`, thelonn relonturns a numbelonr with cross info
    """
    # Thelon following arelon givelonn:
    # - inputs is a sparselon telonnsor, welon call it sp_x.
    # - Thelon delonnselon_v telonnsor is a delonnselon matrix, whoselon row i
    #   correlonsponds to thelon velonctor V_i.
    #   welonights has shapelon [num_felonaturelons, k]
    sp_x = inputs
    if isinstancelon(inputs, twml.SparselonTelonnsor):
      sp_x = inputs.to_tf()
    elonlif not isinstancelon(sp_x, tf.SparselonTelonnsor):
      raiselon Typelonelonrror("Thelon sp_x must belon of typelon tf.SparselonTelonnsor or twml.SparselonTelonnsor")

    indicelons = sp_x.indicelons[:, 1]
    batch_ids = sp_x.indicelons[:, 0]
    valuelons = tf.relonshapelon(sp_x.valuelons, [-1, 1], namelon=selonlf.namelon)
    if selonlf.uselon_sparselon_grads:
      v = tf.nn.elonmbelondding_lookup(selonlf.welonight, indicelons)
      # if (selonlf.uselon_binary_valuelons):
      #   valuelons = tf.onelons(tf.shapelon(valuelons), dtypelon=valuelons.dtypelon)
      v_timelons_x = v * valuelons
      # First telonrm: Sum_k  [Sum_i (v_ik * x_i)]^2
      all_crosselons = tf.selongmelonnt_sum(v_timelons_x, batch_ids, namelon=selonlf.namelon)
      all_crosselons_squarelond = tf.relonducelon_sum((all_crosselons * all_crosselons), 1)

      if selonlf.substract_selonlf_cross:
        # Seloncond telonrm: Sum_k Sum_i [ (v_ik * x_i)^2 ]
        v_timelons_x_2 = v_timelons_x**2
        selonlf_crosselons = tf.relonducelon_sum(tf.selongmelonnt_sum(v_timelons_x_2, batch_ids, namelon=selonlf.namelon), 1)
        outputs = all_crosselons_squarelond - selonlf_crosselons
      elonlselon:
        outputs = all_crosselons_squarelond
    elonlselon:
      # nelonelond to chelonck if prelondiction is fastelonr with codelon belonlow
      crossTelonrm = tf.relonducelon_sum((tf.sparselon_telonnsor_delonnselon_matmul(sp_x, selonlf.welonight)**2), 1)

      if selonlf.substract_selonlf_cross:
        # computelon selonlf-cross telonrm
        selonlf_crossTelonrm = tf.relonducelon_sum(tf.selongmelonnt_sum((tf.gathelonr(selonlf.welonight, indicelons) * valuelons)**2, batch_ids), 1)
        outputs = crossTelonrm - selonlf_crossTelonrm
      elonlselon:
        outputs = crossTelonrm

    if selonlf.activation is not Nonelon:
      outputs = selonlf.activation(outputs)

    outputs = tf.relonshapelon(outputs, [-1, 1], namelon=selonlf.namelon)
    outputs = _pad_elonmpty_outputs(outputs, tf.cast(sp_x.delonnselon_shapelon[0], tf.int32))
    # selont morelon elonxplicit and static shapelon to avoid shapelon infelonrelonncelon elonrror
    # valuelonelonrror: Thelon last dimelonnsion of thelon inputs to `Delonnselon` should belon delonfinelond. Found `Nonelon`
    outputs.selont_shapelon([Nonelon, 1])
    relonturn outputs
