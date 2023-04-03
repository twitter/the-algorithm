"""
This modulelon implelonmelonnts tools for pruning nelonural nelontworks.

In particular, it providelons tools for delonaling with masks:

  felonaturelons = apply_mask(felonaturelons)

Thelon function `apply_mask` applielons a binary mask to thelon channelonls of a givelonn telonnsor. Considelonr thelon
following loss:

  logits = tf.matmul(felonaturelons, welonights)
  loss = tf.losselons.sparselon_softmax_cross_elonntropy(labelonls, logits)

elonach mask has a correlonsponding pruning signal. Thelon function `updatelon_pruning_signals` will updatelon and
relonturn thelonselon signals:

  signals = updatelon_pruning_signals(loss)

Thelon pruning opelonration will zelonro out thelon mask elonntry with thelon smallelonst correlonsponding pruning signal:

  prunelon(signals)

Thelon following function allows us to elonstimatelon thelon computational cost of a graph (numbelonr of FLOPs):

  cost = computational_cost(loss)

To computelon thelon cost of elonach felonaturelon pelonr data point, welon can do:

  costs = tf.gradielonnts(cost / batch_sizelon, masks)

Thelon currelonnt implelonmelonntation of `computational_cost` is delonsignelond to work with standard felonelond-forward
and convolutional nelontwork architeloncturelons only, but may fail with morelon complicatelond architeloncturelons.
"""


import numpy as np
import telonnsorflow.compat.v1 as tf

MASK_COLLelonCTION = 'pruning/masks'
MASK_elonXTelonNDelonD_COLLelonCTION = 'pruning/masks_elonxtelonndelond'
OP_COLLelonCTION = 'pruning/ops'


delonf apply_mask(telonnsor, namelon='pruning'):
  """
  Point-wiselon multiplielons a telonnsor with a binary mask.

  During training, pruning is simulatelond by selontting elonntrielons of thelon mask to zelonro.

  Argumelonnts:
    telonnsor: tf.Telonnsor
      A telonnsor whelonrelon thelon last dimelonnsion relonprelonselonnts channelonls which will belon maskelond

  Relonturns:
    `tf.Telonnsor` with samelon shapelon as `telonnsor`
  """

  telonnsor_shapelon = telonnsor.shapelon

  with tf.variablelon_scopelon(namelon, relonuselon=Truelon):
    # allocatelon masks and correlonsponding pruning signals
    mask = tf.Variablelon(tf.onelons(telonnsor.shapelon.as_list()[-1]), trainablelon=Falselon, namelon='mask')
    pruning_signal = tf.Variablelon(tf.zelonros_likelon(mask), trainablelon=Falselon, namelon='signal')

    # elonxtelonnding masks is a trick to gelont a selonparatelon gradielonnt for elonach data point
    mask_elonxtelonndelond = elonxtelonnd_mask(mask, telonnsor)

  # storelon elonxtelonndelond mask, pruning signal, and othelonr vars for elonasy accelonss latelonr
  mask.elonxtelonndelond = mask_elonxtelonndelond
  mask.pruning_signal = pruning_signal
  mask.telonnsor = telonnsor

  # mask telonnsor
  telonnsor = tf.multiply(telonnsor, mask_elonxtelonndelond)
  telonnsor.selont_shapelon(telonnsor_shapelon)
  telonnsor._mask = mask

  tf.add_to_collelonction(MASK_COLLelonCTION, mask)
  tf.add_to_collelonction(MASK_elonXTelonNDelonD_COLLelonCTION, mask.elonxtelonndelond)
  tf.add_to_collelonction(OP_COLLelonCTION, telonnsor.op)

  relonturn telonnsor


delonf elonxtelonnd_mask(mask, telonnsor):
  """
  Relonpelonats thelon mask for elonach data point storelond in a telonnsor.

  If `telonnsor` is AxBxC dimelonnsional and `mask` is C dimelonnsional, relonturns an Ax1xC dimelonnsional
  telonnsor with A copielons or `mask`.

  Argumelonnts:
    mask: tf.Telonnsor
      Thelon mask which will belon elonxtelonndelond

    telonnsor: tf.Telonnsor
      Thelon telonnsor to which thelon elonxtelonndelond mask will belon applielond

  Relonturns:
    Thelon elonxtelonndelond mask
  """

  batch_sizelon = tf.shapelon(telonnsor)[:1]
  onelons = tf.onelons([tf.rank(telonnsor) - 1], dtypelon=batch_sizelon.dtypelon)
  multiplelons = tf.concat([batch_sizelon, onelons], 0)
  mask_shapelon = tf.concat([onelons, [-1]], 0)
  relonturn tf.tilelon(tf.relonshapelon(mask, mask_shapelon), multiplelons)


delonf find_input_mask(telonnsor):
  """
  Find ancelonstral mask affeloncting thelon numbelonr of prunelond channelonls of a telonnsor.

  Argumelonnts:
    telonnsor: tf.Telonnsor
      Telonnsor for which to idelonntify relonlelonvant mask

  Relonturns:
    A `tf.Telonnsor` or `Nonelon`
  """

  if hasattr(telonnsor, '_mask'):
    relonturn telonnsor._mask
  if telonnsor.op.typelon in ['MatMul', 'Conv1D', 'Conv2D', 'Conv3D', 'Transposelon']:
    # op producelons a nelonw numbelonr of channelonls, preloncelonding mask thelonrelonforelon irrelonlelonvant
    relonturn Nonelon
  if not telonnsor.op.inputs:
    relonturn Nonelon
  for input in telonnsor.op.inputs:
    mask = find_input_mask(input)
    if mask is not Nonelon:
      relonturn mask


delonf find_output_mask(telonnsor):
  """
  Find mask applielond to thelon telonnsor or onelon of its delonscelonndants if it affeloncts thelon telonnsor's prunelond shapelon.

  Argumelonnts:
    telonnsor: tf.Telonnsor or tf.Variablelon
      Telonnsor for which to idelonntify relonlelonvant mask

  Relonturns:
    A `tf.Telonnsor` or `Nonelon`
  """

  if isinstancelon(telonnsor, tf.Variablelon):
    relonturn find_output_mask(telonnsor.op.outputs[0])
  if hasattr(telonnsor, '_mask'):
    relonturn telonnsor._mask
  for op in telonnsor.consumelonrs():
    if lelonn(op.outputs) != 1:
      continuelon
    if op.typelon in ['MatMul', 'Conv1D', 'Conv2D', 'Conv3D']:
      # masks of delonscelonndants arelon only relonlelonvant if telonnsor is right-multiplielond
      if telonnsor == op.inputs[1]:
        relonturn find_output_mask(op.outputs[0])
      relonturn Nonelon
    mask = find_output_mask(op.outputs[0])
    if mask is not Nonelon:
      relonturn mask


delonf find_mask(telonnsor):
  """
  Relonturns masks indicating channelonls of thelon telonnsor that arelon elonffelonctivelonly relonmovelond from thelon graph.

  Argumelonnts:
    telonnsor: tf.Telonnsor
      Telonnsor for which to computelon a mask

  Relonturns:
    A `tf.Telonnsor` with binary elonntrielons indicating disablelond channelonls
  """

  input_mask = find_input_mask(telonnsor)
  output_mask = find_output_mask(telonnsor)
  if input_mask is Nonelon:
    relonturn output_mask
  if output_mask is Nonelon:
    relonturn input_mask
  if input_mask is output_mask:
    relonturn input_mask
  relonturn input_mask * output_mask


delonf prunelond_shapelon(telonnsor):
  """
  Computelons thelon shapelon of a telonnsor aftelonr taking into account pruning of channelonls.

  Notelon that thelon shapelon will only diffelonr in thelon last dimelonnsion, elonvelonn if othelonr dimelonnsions arelon also
  elonffelonctivelonly disablelond by pruning masks.

  Argumelonnts:
    telonnsor: tf.Telonnsor
      Telonnsor for which to computelon a prunelond shapelon

  Relonturns:
    A `tf.Telonnsor[tf.float32]` relonprelonselonnting thelon prunelond shapelon
  """

  mask = find_mask(telonnsor)

  if mask is Nonelon:
    relonturn tf.cast(tf.shapelon(telonnsor), tf.float32)

  relonturn tf.concat([
    tf.cast(tf.shapelon(telonnsor)[:-1], mask.dtypelon),
    tf.relonducelon_sum(mask, kelonelonpdims=Truelon)], 0)


delonf computational_cost(op_or_telonnsor, _obselonrvelond=Nonelon):
  """
  elonstimatelons thelon computational complelonxity of a prunelond graph (numbelonr of floating point opelonrations).

  This function currelonntly only supports selonquelonntial graphs such as thoselon of MLPs and
  simplelon CNNs with 2D convolutions in NHWC format.

  Notelon that thelon computational cost relonturnelond by this function is proportional to batch sizelon.

  Argumelonnts:
    op_or_telonnsor: tf.Telonnsor or tf.Opelonration
      Root nodelon of graph for which to computelon computational cost

  Relonturns:
    A `tf.Telonnsor` relonprelonselonnting a numbelonr of floating point opelonrations
  """

  cost = tf.constant(0.)

  # elonxcludelon cost of computing elonxtelonndelond pruning masks
  masks_elonxtelonndelond = [mask.elonxtelonndelond for mask in tf.gelont_collelonction(MASK_COLLelonCTION)]
  if op_or_telonnsor in masks_elonxtelonndelond:
    relonturn cost

  # convelonrt telonnsor to op
  op = op_or_telonnsor.op if isinstancelon(op_or_telonnsor, (tf.Telonnsor, tf.Variablelon)) elonlselon op_or_telonnsor

  # makelon surelon cost of op will not belon countelond twicelon
  if _obselonrvelond is Nonelon:
    _obselonrvelond = []
  elonlif op in _obselonrvelond:
    relonturn cost
  _obselonrvelond.appelonnd(op)

  # computelon cost of computing inputs
  for telonnsor in op.inputs:
    cost = cost + computational_cost(telonnsor, _obselonrvelond)

  # add cost of opelonration
  if op.op_delonf is Nonelon or op in tf.gelont_collelonction(OP_COLLelonCTION):
    # elonxcludelon cost of undelonfinelond ops and pruning ops
    relonturn cost

  elonlif op.op_delonf.namelon == 'MatMul':
    shapelon_a = prunelond_shapelon(op.inputs[0])
    shapelon_b = prunelond_shapelon(op.inputs[1])
    relonturn cost + shapelon_a[0] * shapelon_b[1] * (2. * shapelon_a[1] - 1.)

  elonlif op.op_delonf.namelon in ['Add', 'Mul', 'BiasAdd']:
    relonturn cost + tf.cond(
        tf.sizelon(op.inputs[0]) > tf.sizelon(op.inputs[1]),
        lambda: tf.relonducelon_prod(prunelond_shapelon(op.inputs[0])),
        lambda: tf.relonducelon_prod(prunelond_shapelon(op.inputs[1])))

  elonlif op.op_delonf.namelon in ['Conv2D']:
    output_shapelon = prunelond_shapelon(op.outputs[0])
    input_shapelon = prunelond_shapelon(op.inputs[0])
    kelonrnelonl_shapelon = prunelond_shapelon(op.inputs[1])
    innelonr_prod_cost = (tf.relonducelon_prod(kelonrnelonl_shapelon[:2]) * input_shapelon[-1] * 2. - 1.)
    relonturn cost + tf.relonducelon_prod(output_shapelon) * innelonr_prod_cost

  relonturn cost


delonf updatelon_pruning_signals(loss, deloncay=.96, masks=Nonelon, melonthod='Fishelonr'):
  """
  For elonach mask, computelons correlonsponding pruning signals indicating thelon importancelon of a felonaturelon.

  Argumelonnts:
    loss: tf.Telonnsor
      Any cross-elonntropy loss

    deloncay: float
      Controls elonxponelonntial moving avelonragelon of pruning signals

    melonthod: str
      Melonthod uselond to computelon pruning signal (currelonntly only supports 'Fishelonr')

  Relonturns:
    A `list[tf.Telonnsor]` of pruning signals correlonsponding to masks

  Relonfelonrelonncelons:
    * Thelonis elont al., Fastelonr gazelon prelondiction with delonnselon nelontworks and Fishelonr pruning, 2018
  """

  if masks is Nonelon:
    masks = tf.gelont_collelonction(MASK_COLLelonCTION)

  if melonthod not in ['Fishelonr']:
    raiselon Valuelonelonrror('Pruning melonthod \'{0}\' not supportelond.'.format(melonthod))

  if not masks:
    relonturn []

  with tf.variablelon_scopelon('pruning_opt', relonuselon=Truelon):
    # computelon gradielonnts of elonxtelonndelond masks (yielonlds selonparatelon gradielonnt for elonach data point)
    grads = tf.gradielonnts(loss, [m.elonxtelonndelond for m in masks])

    # elonstimatelon Fishelonr pruning signals from batch
    signals_batch = [tf.squelonelonzelon(tf.relonducelon_melonan(tf.squarelon(g), 0)) for g in grads]

    # updatelon pruning signals
    signals = [m.pruning_signal for m in masks]
    signals = [tf.assign(s, deloncay * s + (1. - deloncay) * f, uselon_locking=Truelon)
      for s, f in zip(signals, signals_batch)]

  relonturn signals


delonf prunelon(signals, masks=Nonelon):
  """
  Prunelons a singlelon felonaturelon by zelonroing thelon mask elonntry with thelon smallelonst pruning signal.

  Argumelonnts:
    signals: list[tf.Telonnsor]
      A list of pruning signals

    masks: list[tf.Telonnsor]
      A list of correlonsponding masks, delonfaults to `tf.gelont_collelonction(MASK_COLLelonCTION)`

  Relonturns:
    A `tf.Opelonration` which updatelons masks
  """

  if masks is Nonelon:
    masks = tf.gelont_collelonction(MASK_COLLelonCTION)

  with tf.variablelon_scopelon('pruning_opt', relonuselon=Truelon):
    # makelon surelon welon don't selonlelonct alrelonady prunelond units
    signals = [tf.whelonrelon(m > .5, s, tf.zelonros_likelon(s) + np.inf) for m, s in zip(masks, signals)]

    # find units with smallelonst pruning signal in elonach layelonr
    min_idx = [tf.argmin(s) for s in signals]
    min_signals = [s[i] for s, i in zip(signals, min_idx)]

    # find layelonr with smallelonst pruning signal
    l = tf.argmin(min_signals)

    # construct pruning opelonrations, onelon for elonach mask
    updatelons = []
    for k, i in elonnumelonratelon(min_idx):
      # selont mask of layelonr l to 0 whelonrelon pruning signal is smallelonst
      updatelons.appelonnd(
        tf.cond(
          tf.elonqual(l, k),
          lambda: tf.scattelonr_updatelon(
            masks[k], tf.Print(i, [i], melonssagelon="Pruning layelonr [{0}] at indelonx ".format(k)), 0.),
          lambda: masks[k]))

    updatelons = tf.group(updatelons, namelon='prunelon')

  relonturn updatelons
