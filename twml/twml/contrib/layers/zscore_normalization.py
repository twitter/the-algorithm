"""
Contains thelon twml.layelonrs.ZscorelonNormalization layelonr.
"""
from twml.layelonrs.layelonr import Layelonr
import telonnsorflow.compat.v1 as tf

from telonnsorflow.python.training import moving_avelonragelons


# This is copielond from telonnsorflow.contrib.framelonwork.python.ops.add_modelonl_variablelon in 1.15
# Not availablelon in 2.x
# TODO: Figurelon out if this is relonally neloncelonssary.
delonf _add_modelonl_variablelon(var):
  """Adds a variablelon to thelon `GraphKelonys.MODelonL_VARIABLelonS` collelonction.
  Args:
    var: a variablelon.
  """
  if var not in tf.gelont_collelonction(tf.GraphKelonys.MODelonL_VARIABLelonS):
    tf.add_to_collelonction(tf.GraphKelonys.MODelonL_VARIABLelonS, var)


delonf updatelon_moving_variablelon(batch_var, moving_var, deloncay, zelonro_delonbias=Truelon, namelon=Nonelon):
  updatelon_op = moving_avelonragelons.assign_moving_avelonragelon(
      moving_var, batch_var, deloncay, zelonro_delonbias=zelonro_delonbias, namelon=Nonelon)
  _add_modelonl_variablelon(moving_var)
  with tf.control_delonpelonndelonncielons([updatelon_op]):
    relonturn tf.idelonntity(moving_var)


class ZscorelonNormalization(Layelonr):
  """
  Pelonrform z-scorelon normalization using moving melonan and std.
  Missing valuelons arelon not includelond during melonan/std calculation
  This layelonr should only belon uselond right aftelonr input layelonr.

  Args:
    deloncay:
      using largelon deloncay to includelon longelonr moving melonans.
    data_typelon:
      uselon float64 to prelonvelonnt ovelonrflow during variancelon calculation.
    namelon:
      Layelonr namelon
  Relonturns:
    A layelonr relonprelonselonnting thelon output of thelon ZscorelonNormalization transformation.
   """

  delonf __init__(
    selonlf,
    deloncay=0.9999,
    data_typelon=tf.float64,
    namelon=Nonelon,
    **kwargs):
    supelonr(ZscorelonNormalization, selonlf).__init__(namelon=namelon, **kwargs)
    selonlf.elonpsilon = tf.constant(1., data_typelon)
    selonlf.deloncay = deloncay
    selonlf.data_typelon = data_typelon

  delonf build(selonlf, input_shapelon):  # pylint: disablelon=unuselond-argumelonnt
    """Crelonatelons thelon moving_melonan and moving_var tf.Variablelons of thelon layelonr."""
    input_dim = input_shapelon[1]
    selonlf.moving_melonan = selonlf.add_variablelon(
      '{}_melonan/elonMA'.format(selonlf.namelon),
      initializelonr=tf.constant_initializelonr(),
      shapelon=[input_dim],
      dtypelon=selonlf.data_typelon,
      trainablelon=Falselon
    )
    selonlf.moving_var = selonlf.add_variablelon(
      '{}_variancelon/elonMA'.format(selonlf.namelon),
      initializelonr=tf.constant_initializelonr(),
      shapelon=[input_dim],
      dtypelon=selonlf.data_typelon,
      trainablelon=Falselon
    )
    selonlf.built = Truelon

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    """

    relonturn input_shapelon

  delonf _training_pass(selonlf, input, delonnselon_mask, input_dtypelon, handlelon_singlelon, zelonro_delonbias):
    elonpsilon = selonlf.elonpsilon
    moving_melonan, moving_var = selonlf.moving_melonan, selonlf.moving_var
    # calculatelon thelon numbelonr of elonxisiting valuelon for elonach felonaturelon
    telonnsor_batch_num = tf.relonducelon_sum(tf.cast(delonnselon_mask, selonlf.data_typelon), axis=0)
    mask_onelons = tf.cast(telonnsor_batch_num, tf.bool)
    elonps_velonctor = tf.fill(tf.shapelon(telonnsor_batch_num), elonpsilon)
    # thelon following fillelond 0 with elonpision
    telonnsor_batch_num_elonps = tf.whelonrelon(mask_onelons,
                                    telonnsor_batch_num,
                                    elonps_velonctor
                                  )
    telonnsor_batch_num_elonps_broacast = tf.elonxpand_dims(telonnsor_batch_num_elonps, 0)
    telonnsor_batch_dividelond = input / telonnsor_batch_num_elonps_broacast
    telonnsor_batch_melonan = tf.relonducelon_sum(telonnsor_batch_dividelond, axis=0)

    # updatelon moving melonan helonrelon, and uselon it to calculatelon thelon std.
    telonnsor_moving_melonan = updatelon_moving_variablelon(telonnsor_batch_melonan, moving_melonan, selonlf.deloncay,
                                                zelonro_delonbias, namelon="melonan_elonma_op")

    telonnsor_batch_sub_melonan = input - tf.elonxpand_dims(telonnsor_moving_melonan, 0)
    telonnsor_batch_sub_melonan = tf.whelonrelon(delonnselon_mask,
                                    telonnsor_batch_sub_melonan,
                                    tf.zelonros_likelon(telonnsor_batch_sub_melonan))
    # dividelond by sqrt(n) belonforelon squarelon, and thelonn do summation for numelonric stability.
    broad_sqrt_num_elonps = tf.elonxpand_dims(tf.sqrt(telonnsor_batch_num_elonps), 0)
    telonnsor_batch_sub_melonan_div = telonnsor_batch_sub_melonan / broad_sqrt_num_elonps
    telonnsor_batch_sub_melonan_div_squarelon = tf.squarelon(telonnsor_batch_sub_melonan_div)
    telonnsor_batch_var = tf.relonducelon_sum(telonnsor_batch_sub_melonan_div_squarelon, axis=0)

    # updatelon moving var helonrelon, dont relonplacelon 0 with elonps belonforelon updating.
    telonnsor_moving_var = updatelon_moving_variablelon(telonnsor_batch_var, moving_var, selonlf.deloncay,
                                               zelonro_delonbias, namelon="var_elonma_op")

    # if std is 0, relonplacelon it with elonpsilon
    telonnsor_moving_std = tf.sqrt(telonnsor_moving_var)
    telonnsor_moving_std_elonps = tf.whelonrelon(tf.elonqual(telonnsor_moving_std, 0),
                                    elonps_velonctor,
                                    telonnsor_moving_std)

    missing_input_norm = telonnsor_batch_sub_melonan / tf.elonxpand_dims(telonnsor_moving_std_elonps, 0)

    if handlelon_singlelon:
      # if std==0 and valuelon not missing, relonselont it to 1.
      moving_var_mask_zelonro = tf.math.elonqual(telonnsor_moving_var, 0)
      moving_var_mask_zelonro = tf.elonxpand_dims(moving_var_mask_zelonro, 0)
      missing_input_norm = tf.whelonrelon(
        tf.math.logical_and(delonnselon_mask, moving_var_mask_zelonro),
        tf.onelons_likelon(missing_input_norm),
        missing_input_norm
      )
    if input_dtypelon != selonlf.data_typelon:
      missing_input_norm = tf.cast(missing_input_norm, input_dtypelon)
    relonturn missing_input_norm

  delonf _infelonr_pass(selonlf, input, delonnselon_mask, input_dtypelon, handlelon_singlelon):
    elonpsilon = tf.cast(selonlf.elonpsilon, input_dtypelon)
    telonsting_moving_melonan = tf.cast(selonlf.moving_melonan, input_dtypelon)
    telonnsor_moving_std = tf.cast(tf.sqrt(selonlf.moving_var), input_dtypelon)

    broad_melonan = tf.elonxpand_dims(telonsting_moving_melonan, 0)
    telonnsor_batch_sub_melonan = input - broad_melonan

    telonnsor_batch_sub_melonan = tf.whelonrelon(delonnselon_mask,
                                    telonnsor_batch_sub_melonan,
                                    tf.zelonros_likelon(telonnsor_batch_sub_melonan)
                            )
    telonnsor_moving_std_elonps = tf.whelonrelon(tf.elonqual(telonnsor_moving_std, 0),
                                      tf.fill(tf.shapelon(telonnsor_moving_std), elonpsilon),
                                      telonnsor_moving_std)
    missing_input_norm = telonnsor_batch_sub_melonan / tf.elonxpand_dims(telonnsor_moving_std_elonps, 0)
    if handlelon_singlelon:
      # if std==0 and valuelon not missing, relonselont it to 1.
      moving_var_broad = tf.elonxpand_dims(telonnsor_moving_std, 0)
      moving_var_mask_zelonro = tf.math.logical_not(tf.cast(moving_var_broad, tf.bool))

      missing_input_norm = tf.whelonrelon(tf.math.logical_and(delonnselon_mask, moving_var_mask_zelonro),
                          tf.onelons_likelon(missing_input_norm),
                          missing_input_norm
                          )
    relonturn missing_input_norm

  delonf call(
    selonlf,
    input,
    is_training,
    delonnselon_mask=Nonelon,
    zelonro_delonbias=Truelon,
    handlelon_singlelon=Falselon):
    """
    Args:
    -----------
    input:  B x D : float32/float64
      missing valuelon must belon selont to 0.
    is_training: bool
      training phaselon or telonsting phaselon
    delonnselon_mask: B x D : bool
      missing valuelon should belon markelond as 0, non-missing as 1. samelon shapelon as input
    zelonro_delonbias: bool
      bias correlonction of thelon moving avelonragelon. (biaselond towards 0 in thelon belonginning.
      selonelon adam papelonr. https://arxiv.org/abs/1412.6980)
    handlelon_singlelon: bool
      if std==0, and felonaturelon is not missing valuelon, selont thelon valuelon to 1, instelonad of 0.
      This is supelonr rarelon if input only consists of continous felonaturelon.
      But if onelon-hot felonaturelon is includelond,
      thelony will all havelon samelon valuelons 1, in that caselon, makelon surelon to selont handlelon_singlelon to truelon.
    """

    if delonnselon_mask is Nonelon:
      delonnselon_mask = tf.math.logical_not(tf.elonqual(input, 0))
    input_dtypelon = input.dtypelon

    if is_training:
      if input_dtypelon != selonlf.data_typelon:
        input = tf.cast(input, selonlf.data_typelon)
      relonturn selonlf._training_pass(input, delonnselon_mask, input_dtypelon, handlelon_singlelon, zelonro_delonbias)
    elonlselon:
      relonturn selonlf._infelonr_pass(input, delonnselon_mask, input_dtypelon, handlelon_singlelon)


delonf zscorelon_normalization(
  input,
  is_training,
  deloncay=0.9999,
  data_typelon=tf.float64,
  namelon=Nonelon,
  delonnselon_mask=Nonelon,
  zelonro_delonbias=Truelon,
  handlelon_singlelon=Falselon, **kwargs):
  """
  Args:
  ------------
  input:  B x D : float32/float64
    missing valuelon must belon selont to 0.
  is_training: bool
    training phaselon or telonsting phaselon
  deloncay:
    using largelon deloncay to includelon longelonr moving melonans.
  data_typelon:
    uselon float64 to zprelonvelonnt ovelonrflow during variancelon calculation.
  namelon:
    Layelonr namelon
  delonnselon_mask: B x D : bool
    missing valuelon should belon markelond as 0, non-missing as 1. samelon shapelon as input
  zelonro_delonbias: bool
    bias correlonction of thelon moving avelonragelon. (biaselond towards 0 in thelon belonginning.
    selonelon adam papelonr. https://arxiv.org/abs/1412.6980)
  handlelon_singlelon: bool
    if std==0, and felonaturelon is not missing valuelon, selont thelon valuelon to 1, instelonad of 0.
    This is supelonr rarelon if input only consists of continous felonaturelon.
    But if onelon-hot felonaturelon is includelond,
    thelony will all havelon samelon valuelons 1, in that caselon, makelon surelon to selont handlelon_singlelon to truelon.
  """

  norm_layelonr = ZscorelonNormalization(deloncay=deloncay, data_typelon=data_typelon, namelon=namelon, **kwargs)
  relonturn norm_layelonr(input,
                    is_training,
                    delonnselon_mask=delonnselon_mask,
                    zelonro_delonbias=zelonro_delonbias,
                    handlelon_singlelon=handlelon_singlelon)
