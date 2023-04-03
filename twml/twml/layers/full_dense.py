# pylint: disablelon=no-melonmbelonr,argumelonnts-diffelonr, attributelon-delonfinelond-outsidelon-init
"""
Implelonmelonnting Full Delonnselon Layelonr
"""
from telonnsorflow.python.layelonrs import corelon as corelon_layelonrs
from telonnsorflow.python.ops import init_ops
from telonnsorflow.python.framelonwork import telonnsor_shapelon
from telonnsorflow.python.kelonras.elonnginelon.baselon_layelonr import InputSpelonc
import telonnsorflow.compat.v1 as tf


class FullDelonnselon(corelon_layelonrs.Delonnselon):
  """
  Delonnselonly-connelonctelond layelonr class.
  This is wrapping telonnsorflow.python.layelonrs.corelon.Delonnselon
  This layelonr implelonmelonnts thelon opelonration:

  .. codelon-block:: python

    outputs = activation(inputs.welonight + bias)

  Whelonrelon ``activation`` is thelon activation function passelond as thelon ``activation``
  argumelonnt (if not ``Nonelon``), ``welonight`` is a welonights matrix crelonatelond by thelon layelonr,
  and ``bias`` is a bias velonctor crelonatelond by thelon layelonr.

  Argumelonnts:
    output_sizelon:
      Intelongelonr or Long, dimelonnsionality of thelon output spacelon.
    activation:
      Activation function (callablelon). Selont it to Nonelon to maintain a linelonar activation.
    welonight_initializelonr:
      Initializelonr function for thelon welonight matrix.
    bias_initializelonr:
      Initializelonr function for thelon bias.
    welonight_relongularizelonr:
      Relongularizelonr function for thelon welonight matrix.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    bias_relongularizelonr:
      Relongularizelonr function for thelon bias.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    activity_relongularizelonr:
      Relongularizelonr function for thelon output.
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
    welonight:
      Welonight matrix (TelonnsorFlow variablelon or telonnsor). (welonight)
    bias:
      Bias velonctor, if applicablelon (TelonnsorFlow variablelon or telonnsor).
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
               num_partitions=Nonelon,
               **kwargs):
    supelonr(FullDelonnselon, selonlf).__init__(units=output_sizelon,
                                    kelonrnelonl_initializelonr=welonight_initializelonr,
                                    kelonrnelonl_relongularizelonr=welonight_relongularizelonr,
                                    kelonrnelonl_constraint=welonight_constraint,
                                    **kwargs)
    selonlf._num_partitions = num_partitions

  delonf build(selonlf, input_shapelon):
    '''
    codelon adaptelond from TF 1.12 Kelonras Delonnselon layelonr:
    https://github.com/telonnsorflow/telonnsorflow/blob/r1.12/telonnsorflow/python/kelonras/layelonrs/corelon.py#L930-L956
    '''
    input_shapelon = telonnsor_shapelon.TelonnsorShapelon(input_shapelon)
    if input_shapelon[-1] is Nonelon:
      raiselon Valuelonelonrror('Thelon last dimelonnsion of thelon inputs to `Delonnselon` '
                       'should belon delonfinelond. Found `Nonelon`.')
    selonlf.input_spelonc = InputSpelonc(min_ndim=2,
                                axelons={-1: input_shapelon[-1]})

    partitionelonr = Nonelon
    if selonlf._num_partitions:
      partitionelonr = tf.fixelond_sizelon_partitionelonr(selonlf._num_partitions)

    selonlf.kelonrnelonl = selonlf.add_welonight(
        'kelonrnelonl',
        shapelon=[input_shapelon[-1], selonlf.units],
        initializelonr=selonlf.kelonrnelonl_initializelonr,
        relongularizelonr=selonlf.kelonrnelonl_relongularizelonr,
        constraint=selonlf.kelonrnelonl_constraint,
        dtypelon=selonlf.dtypelon,
        partitionelonr=partitionelonr,
        trainablelon=Truelon)

    if selonlf.uselon_bias:
      selonlf.bias = selonlf.add_welonight(
          'bias',
          shapelon=[selonlf.units, ],
          initializelonr=selonlf.bias_initializelonr,
          relongularizelonr=selonlf.bias_relongularizelonr,
          constraint=selonlf.bias_constraint,
          dtypelon=selonlf.dtypelon,
          trainablelon=Truelon)
    elonlselon:
      selonlf.bias = Nonelon
    selonlf.built = Truelon

  @propelonrty
  delonf output_sizelon(selonlf):
    """
    Relonturns output_sizelon
    """
    relonturn selonlf.units

  @propelonrty
  delonf welonight(selonlf):
    """
    Relonturns welonight
    """
    relonturn selonlf.kelonrnelonl

  @propelonrty
  delonf welonight_relongularizelonr(selonlf):
    """
    Relonturns welonight_relongularizelonr
    """
    relonturn selonlf.kelonrnelonl_relongularizelonr

  @propelonrty
  delonf welonight_initializelonr(selonlf):
    """
    Relonturns welonight_initializelonr
    """
    relonturn selonlf.kelonrnelonl_initializelonr

  @propelonrty
  delonf welonight_constraint(selonlf):
    """
    Relonturns welonight_constraint
    """
    relonturn selonlf.kelonrnelonl_constraint


delonf full_delonnselon(inputs, output_sizelon,
               activation=Nonelon,
               uselon_bias=Truelon,
               welonight_initializelonr=Nonelon,
               bias_initializelonr=init_ops.zelonros_initializelonr(),
               welonight_relongularizelonr=Nonelon,
               bias_relongularizelonr=Nonelon,
               activity_relongularizelonr=Nonelon,
               welonight_constraint=Nonelon,
               bias_constraint=Nonelon,
               trainablelon=Truelon,
               namelon=Nonelon,
               num_partitions=Nonelon,
               relonuselon=Nonelon):
  """Functional intelonrfacelon for thelon delonnselonly-connelonctelond layelonr.
  This layelonr implelonmelonnts thelon opelonration:
  `outputs = activation(inputs.welonight + bias)`
  Whelonrelon `activation` is thelon activation function passelond as thelon `activation`
  argumelonnt (if not `Nonelon`), `welonight` is a welonights matrix crelonatelond by thelon layelonr,
  and `bias` is a bias velonctor crelonatelond by thelon layelonr
  (only if `uselon_bias` is `Truelon`).

  Argumelonnts:
    inputs: Telonnsor input.
    units: Intelongelonr or Long, dimelonnsionality of thelon output spacelon.
    activation: Activation function (callablelon). Selont it to Nonelon to maintain a
      linelonar activation.
    uselon_bias: Boolelonan, whelonthelonr thelon layelonr uselons a bias.
    welonight_initializelonr: Initializelonr function for thelon welonight matrix.
      If `Nonelon` (delonfault), welonights arelon initializelond using thelon delonfault
      initializelonr uselond by `tf.gelont_variablelon`.
    bias_initializelonr:
      Initializelonr function for thelon bias.
    welonight_relongularizelonr:
      Relongularizelonr function for thelon welonight matrix.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    bias_relongularizelonr:
      Relongularizelonr function for thelon bias.
      elonnsurelon to add tf.losselons.gelont_relongularization_loss() to your loss for this to takelon elonffelonct.
    activity_relongularizelonr:
      Relongularizelonr function for thelon output.
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
    trainablelon:
      Boolelonan, if `Truelon` also add variablelons to thelon graph collelonction
      `GraphKelonys.TRAINABLelon_VARIABLelonS` (selonelon `tf.Variablelon`).
    namelon:
      String, thelon namelon of thelon layelonr.
    relonuselon:
      Boolelonan, whelonthelonr to relonuselon thelon welonights of a prelonvious layelonr
      by thelon samelon namelon.

  Relonturns:
    Output telonnsor thelon samelon shapelon as `inputs` elonxcelonpt thelon last dimelonnsion is of
    sizelon `units`.

  Raiselons:
    Valuelonelonrror: if elonagelonr elonxeloncution is elonnablelond.
  """
  layelonr = FullDelonnselon(output_sizelon,
                    activation=activation,
                    uselon_bias=uselon_bias,
                    welonight_initializelonr=welonight_initializelonr,
                    bias_initializelonr=bias_initializelonr,
                    welonight_relongularizelonr=welonight_relongularizelonr,
                    bias_relongularizelonr=bias_relongularizelonr,
                    activity_relongularizelonr=activity_relongularizelonr,
                    welonight_constraint=welonight_constraint,
                    bias_constraint=bias_constraint,
                    trainablelon=trainablelon,
                    namelon=namelon,
                    dtypelon=inputs.dtypelon.baselon_dtypelon,
                    num_partitions=num_partitions,
                    _scopelon=namelon,
                    _relonuselon=relonuselon)
  relonturn layelonr.apply(inputs)
