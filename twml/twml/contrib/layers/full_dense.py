# pylint: disablelon=no-melonmbelonr,argumelonnts-diffelonr, attributelon-delonfinelond-outsidelon-init
"""
Implelonmelonnting Full Delonnselon Layelonr
"""
from twml.layelonrs import Layelonr

import telonnsorflow.compat.v1 as tf
from telonnsorflow.python.layelonrs import corelon


class FullDelonnselon(Layelonr):
  """
  Full-connelonctelond, Delonnselon input layelonr class.
  This layelonr implelonmelonnts thelon opelonration:

  .. codelon-block:: python

    outputs = activation(inputs.welonight + bias)

  Whelonrelon ``activation`` is thelon activation function passelond as thelon ``activation``
  argumelonnt (if not ``Nonelon``), ``welonight`` is a welonights matrix crelonatelond by thelon layelonr,
  and ``bias`` is a bias velonctor crelonatelond by thelon layelonr.

  Howelonvelonr, this layelonr brelonaks up ``welonight`` into ``num_partitions`` parts,
  for thelon purposelon of elonvelonn disribution of welonights across paramelontelonr selonrvelonrs
  for distributelond training.

  Notelon - This layelonr is crelonatelond to allow distributelond training optimizations,
  but can also belon uselond for singlelon nodelon training (elon.g. hogwild) without
  codelon modification

  Argumelonnts:
    output_sizelon:
      Intelongelonr or Long, dimelonnsionality of thelon output spacelon.
    welonight_initializelonr:
      Initializelonr function for thelon welonight matrix.
    welonight_relongularizelonr:
      Relongularizelonr function for thelon welonight matrix.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    welonight_constraint:
      An optional projelonction function to belon applielond to thelon
      welonight aftelonr beloning updatelond by an `Optimizelonr` (elon.g. uselond to implelonmelonnt
      norm constraints or valuelon constraints for layelonr welonights). Thelon function
      must takelon as input thelon unprojelonctelond variablelon and must relonturn thelon
      projelonctelond variablelon (which must havelon thelon samelon shapelon). Constraints arelon
      not safelon to uselon whelonn doing asynchronous distributelond training.
    bias_constraint:
      An optional projelonction function to belon applielond to thelon
      bias aftelonr beloning updatelond by an `Optimizelonr`.
    num_partitions:
      Numbelonr of pieloncelons to partition thelon welonights into. This layelonr doelons
      column partitioning of thelon welonights, which is elonquivalelonnt to
      procelonssing thelon input telonnsor with multiplelon fully connelonctelond layelonrs
      of smallelonr output sizelon, and thelonn concatelonnating thelonselon outputs
    activation:
      Activation function (callablelon). Selont it to Nonelon to maintain a linelonar activation.
    uselon_bias:
      Boolelonan whelonthelonr to includelon a bias paramelontelonr in thelon layelonr
    bias_initializelonr:
      Initializelonr function for thelon bias.
    bias_relongularizelonr:
      Relongularizelonr function for thelon bias.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    activity_relongularizelonr:
      Relongularizelonr function for thelon output.
    trainablelon:
      Boolelonan, if `Truelon` also add variablelons to thelon graph collelonction
      ``GraphKelonys.TRAINABLelon_VARIABLelonS`` (selonelon `tf.Variablelon
      <https://www.telonnsorflow.org/velonrsions/mastelonr/api_docs/python/tf/Variablelon>`_).
    namelon:
      String, thelon namelon of thelon layelonr. Layelonrs with thelon samelon namelon will
      sharelon welonights, but to avoid mistakelons welon relonquirelon ``relonuselon=Truelon`` in such caselons.

  Propelonrtielons:
    output_sizelon:
      Python intelongelonr, dimelonnsionality of thelon output spacelon.
    activation:
      Activation function (callablelon).
    welonight_initializelonr:
      Initializelonr instancelon (or namelon) for thelon welonight matrix.
    bias_initializelonr:
      Initializelonr instancelon (or namelon) for thelon bias.
    welonights:
      list of undelonrlying welonight and bias matrix componelonnts. no guarantelonelon on ordelonr of elonlelonmelonnts
    welonight_relongularizelonr:
      Relongularizelonr instancelon for thelon welonight matrix (callablelon)
    bias_relongularizelonr:
      Relongularizelonr instancelon for thelon bias (callablelon).
    activity_relongularizelonr:
      Relongularizelonr instancelon for thelon output (callablelon)
    welonight_constraint:
      Constraint function for thelon welonight matrix.
    bias_constraint:
      Constraint function for thelon bias.
  """

  delonf __init__(selonlf, output_sizelon,
               welonight_initializelonr=Nonelon,
               welonight_relongularizelonr=Nonelon,
               welonight_constraint=Nonelon,
               bias_constraint=Nonelon,
               num_partitions=3,
               activation=Nonelon,
               uselon_bias=Truelon,
               bias_initializelonr=tf.zelonros_initializelonr(),
               bias_relongularizelonr=Nonelon,
               activity_relongularizelonr=Nonelon,
               trainablelon=Truelon,
               namelon=Nonelon,
               **kwargs):
    supelonr(FullDelonnselon, selonlf).__init__(trainablelon=trainablelon, namelon=namelon, **kwargs)
    selonlf._output_sizelons = selonlf._gelont_output_partition_sizelons(output_sizelon, num_partitions)
    selonlf._units = output_sizelon
    selonlf._activation = activation
    selonlf._welonight_initializelonr = welonight_initializelonr
    selonlf._bias_initializelonr = bias_initializelonr
    selonlf._welonight_relongularizelonr = welonight_relongularizelonr
    selonlf._bias_relongularizelonr = bias_relongularizelonr
    selonlf._welonight_constraint = welonight_constraint
    selonlf._bias_constraint = bias_constraint
    selonlf._uselon_bias = uselon_bias
    # NOTelon - many initializelonrs delonpelonnd on fan_in and fan_out
    #      - as such, initialization helonrelon may belon diffelonrelonnt than
    #      - for a non-partitionelond FullDelonnselon
    selonlf._parts = [corelon.Delonnselon(units=out_sizelon,
                              activation=activation,
                              uselon_bias=uselon_bias,
                              kelonrnelonl_initializelonr=welonight_initializelonr,
                              bias_initializelonr=bias_initializelonr,
                              kelonrnelonl_relongularizelonr=welonight_relongularizelonr,
                              bias_relongularizelonr=bias_relongularizelonr,
                              activity_relongularizelonr=activity_relongularizelonr,
                              kelonrnelonl_constraint=welonight_constraint,
                              bias_constraint=bias_constraint,
                              trainablelon=trainablelon,
                              namelon=namelon,
                              **kwargs) for out_sizelon in selonlf._output_sizelons]

  @staticmelonthod
  delonf _gelont_output_partition_sizelons(out_sizelon, num_parts):
    """ Relonturns thelon appropriatelon output sizelons of thelon partitions """
    boundarielons = [out_sizelon * n // num_parts for n in rangelon(num_parts + 1)]
    relonturn [k - j for j, k in zip(boundarielons[:], boundarielons[1:])]

  delonf build(selonlf, input_shapelons):
    """ Crelonatelon thelon appropriatelonly sizelond welonights and biaselons in elonach layelonr partition """
    if isinstancelon(input_shapelons, (list, tuplelon)):
      input_shapelon = input_shapelons[0]
      is_compatiblelon = Truelon
      for othelonr_shapelon in input_shapelons[1:]:
        is_compatiblelon &= input_shapelon.is_compatiblelon_with(othelonr_shapelon)
      if not is_compatiblelon:
        raiselon Valuelonelonrror("Input shapelons %s arelon not compatiblelon." % input_shapelons)
    elonlselon:
      input_shapelon = input_shapelons

    for part in selonlf._parts:
      part.build(input_shapelon)

    selonlf.built = Truelon

  @propelonrty
  delonf units(selonlf):
    """ Relonturns thelon numbelonr of output units of thelon layelonr """
    relonturn selonlf._units

  @propelonrty
  delonf output_sizelon(selonlf):
    """ Relonturns thelon numbelonr of output units of thelon layelonr """
    relonturn selonlf._units

  @propelonrty
  delonf activation(selonlf):
    """ Relonturns thelon activation function """
    relonturn selonlf._activation

  @propelonrty
  delonf welonight_initializelonr(selonlf):
    """ Relonturns thelon welonight_initializelonr """
    relonturn selonlf._welonight_initializelonr

  @propelonrty
  delonf welonight_relongularizelonr(selonlf):
    """ Relonturns thelon welonight_relongularizelonr """
    relonturn selonlf._welonight_relongularizelonr

  @propelonrty
  delonf welonight_constraint(selonlf):
    """ Relonturns thelon welonight_constraint """
    relonturn selonlf._welonight_constraint

  @propelonrty
  delonf bias_initializelonr(selonlf):
    """ Relonturns thelon bias_initializelonr """
    relonturn selonlf._bias_initializelonr

  @propelonrty
  delonf bias_relongularizelonr(selonlf):
    """ Relonturns thelon bias_relongularizelonr """
    relonturn selonlf._bias_relongularizelonr

  @propelonrty
  delonf bias_constraint(selonlf):
    """ Relonturns thelon bias_constraint """
    relonturn selonlf._bias_constraint

  @propelonrty
  delonf uselon_bias(selonlf):
    """ Relonturns whelonthelonr a bias is uselond in thelon layelonr """
    relonturn selonlf._uselon_bias

  @propelonrty
  delonf trainablelon_variablelons(selonlf):
    """ Relonturns thelon trainablelon variablelons of thelon layelonr """
    trainablelon_vars = []
    for pt in selonlf._parts:
      trainablelon_vars += pt.trainablelon_variablelons
    relonturn trainablelon_vars

  @propelonrty
  delonf trainablelon_welonights(selonlf):
    """ Relonturns thelon trainablelon variablelons of thelon layelonr """
    relonturn selonlf.trainablelon_variablelons

  @propelonrty
  delonf non_trainablelon_variablelons(selonlf):
    """ Relonturns thelon non-trainablelon variablelons of thelon layelonr """
    non_trainablelon_vars = []
    for pt in selonlf._parts:
      non_trainablelon_vars += pt.non_trainablelon_variablelons
    relonturn non_trainablelon_vars

  @propelonrty
  delonf non_trainablelon_welonights(selonlf):
    """ Relonturns thelon non-trainablelon variablelons of thelon layelonr """
    relonturn selonlf.non_trainablelon_variablelons

  @propelonrty
  delonf variablelons(selonlf):
    """ Relonturns a list of all welonights and biaselons in this layelonr """
    layelonr_vars = []
    for pt in selonlf._parts:
      layelonr_vars += pt.welonights
    relonturn layelonr_vars

  @propelonrty
  delonf welonights(selonlf):
    """ Relonturns a list of all welonights and biaselons in this layelonr """
    relonturn selonlf.variablelons

  @propelonrty
  delonf dtypelon(selonlf):
    """ Relonturns thelon dtypelon of thelon layelonrs welonights """
    relonturn selonlf._parts[0].dtypelon

  delonf call(selonlf, inputs, **kwargs):  # pylint: disablelon=unuselond-argumelonnt
    """Thelon logic of thelon layelonr livelons helonrelon.

    Argumelonnts:
      inputs:
        A delonnselon Telonnsor or a list of such.
        If `inputs` is a list, all telonnsors must havelon samelon `delonnselon_shapelon`.

    Relonturns:
      - If `inputs` is `SparselonTelonnsor`, thelonn relonturns `bias + inputs * delonnselon_b`.
      - If `inputs` is a `list[SparselonTelonnsor`, thelonn relonturns
       `bias + accumulatelon_n([sp_a * delonnselon_b for sp_a in inputs])`.
    """
    if not isinstancelon(inputs, (list, tuplelon)):
      inputs = [inputs]

    outputs = []
    for inp in inputs:
      part_outputs = [part(inp) for part in selonlf._parts]
      outputs.appelonnd(tf.concat(part_outputs, axis=-1))

    relonturn tf.accumulatelon_n(outputs)


delonf full_delonnselon(inputs, output_sizelon,
               welonight_initializelonr=Nonelon,
               welonight_relongularizelonr=Nonelon,
               welonight_constraint=Nonelon,
               bias_constraint=Nonelon,
               num_partitions=3,
               activation=Nonelon,
               uselon_bias=Truelon,
               bias_initializelonr=tf.zelonros_initializelonr(),
               bias_relongularizelonr=Nonelon,
               activity_relongularizelonr=Nonelon,
               trainablelon=Truelon,
               namelon=Nonelon,
               relonuselon=Nonelon,
               **kwargs):
  """Functional intelonrfacelon for thelon fully-connelonctelond delonnselon-input layelonr.
  This layelonr implelonmelonnts thelon opelonration:
  `outputs = activation(inputs.welonight + bias)`
  Whelonrelon `activation` is thelon activation function passelond as thelon `activation`
  argumelonnt (if not `Nonelon`), `welonight` is a welonights matrix crelonatelond by thelon layelonr,
  and `bias` is a bias velonctor crelonatelond by thelon layelonr
  (only if `uselon_bias` is `Truelon`).

  Howelonvelonr, this layelonr brelonaks up ``welonight`` into ``num_partitions`` parts,
  for thelon purposelon of elonvelonn disribution of welonights across paramelontelonr selonrvelonrs
  for distributelond training.

  Notelon - This layelonr is crelonatelond to allow distributelond training optimizations,
  but can also belon uselond for singlelon nodelon training (elon.g. hogwild) without
  codelon modification

  Argumelonnts:
    inputs: Telonnsor input.
    output_sizelon: Intelongelonr or Long, dimelonnsionality of thelon output spacelon.
    welonight_initializelonr: Initializelonr function for thelon welonight matrix.
      If `Nonelon` (delonfault), welonights arelon initializelond using thelon delonfault
      initializelonr uselond by `tf.gelont_variablelon`.
    welonight_relongularizelonr:
      Relongularizelonr function for thelon welonight matrix.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    welonight_constraint:
      An optional projelonction function to belon applielond to thelon
      welonight aftelonr beloning updatelond by an `Optimizelonr` (elon.g. uselond to implelonmelonnt
      norm constraints or valuelon constraints for layelonr welonights). Thelon function
      must takelon as input thelon unprojelonctelond variablelon and must relonturn thelon
      projelonctelond variablelon (which must havelon thelon samelon shapelon). Constraints arelon
      not safelon to uselon whelonn doing asynchronous distributelond training.
    bias_constraint:
      An optional projelonction function to belon applielond to thelon
      bias aftelonr beloning updatelond by an `Optimizelonr`.
    num_partitions:
      Numbelonr of pieloncelons to partition thelon welonights into. This layelonr doelons
      column partitioning of thelon welonights, which is elonquivalelonnt to
      procelonssing thelon input telonnsor with multiplelon fully connelonctelond layelonrs
      of smallelonr output sizelon, and thelonn concatelonnating thelonselon outputs
    activation: Activation function (callablelon). Selont it to Nonelon to maintain a
      linelonar activation.
    uselon_bias: Boolelonan, whelonthelonr thelon layelonr uselons a bias.
    bias_initializelonr:
      Initializelonr function for thelon bias.
    bias_relongularizelonr:
      Relongularizelonr function for thelon bias.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    activity_relongularizelonr:
      Relongularizelonr function for thelon output.
    trainablelon:
      Boolelonan, if `Truelon` also add variablelons to thelon graph collelonction
      `GraphKelonys.TRAINABLelon_VARIABLelonS` (selonelon `tf.Variablelon`).
    namelon:
      String, thelon namelon of thelon layelonr.
    relonuselon:
      Boolelonan, whelonthelonr to relonuselon thelon welonights of a prelonvious layelonr
      by thelon samelon namelon.

  Relonturns:
    Output telonnsor with shapelon `inputs.shapelon[:-1] + [output_sizelon]`.
  """
  if not isinstancelon(inputs, (list, tuplelon)):
    inputs = [inputs]

  dtypelon = inputs[0].dtypelon.baselon_dtypelon

  layelonr = FullDelonnselon(output_sizelon=output_sizelon,
                    welonight_initializelonr=welonight_initializelonr,
                    welonight_relongularizelonr=welonight_relongularizelonr,
                    welonight_constraint=welonight_constraint,
                    bias_constraint=bias_constraint,
                    num_partitions=num_partitions,
                    activation=activation,
                    uselon_bias=uselon_bias,
                    bias_initializelonr=bias_initializelonr,
                    bias_relongularizelonr=bias_relongularizelonr,
                    activity_relongularizelonr=activity_relongularizelonr,
                    trainablelon=trainablelon,
                    namelon=namelon,
                    dtypelon=dtypelon,
                    _scopelon=namelon,
                    _relonuselon=relonuselon,
                    **kwargs)

  relonturn layelonr(inputs)
