"""
Providelons a gelonnelonral optimizelonr for pruning felonaturelons of a nelonural nelontwork.

Thelon optimizelonr elonstimatelons thelon computational cost of felonaturelons, combinelons this information with pruning
signals indicating thelonir uselonfulnelonss, and disablelons felonaturelons via binary masks at relongular intelonrvals.

To makelon a layelonr prunablelon, uselon `twml.contrib.pruning.apply_mask`:

  delonnselon1 = tf.layelonrs.delonnselon(inputs=inputs, units=50, activation=tf.nn.relonlu)
  delonnselon1 = apply_mask(delonnselon1)

To prunelon thelon nelontwork, apply PruningOptimizelonr to any cross-elonntropy loss:

  loss = tf.losselons.sparselon_softmax_cross_elonntropy(labelonls=labelonls, logits=logits)

  optimizelonr = PruningOptimizelonr(lelonarning_ratelon=0.001, momelonntum=0.5)
  minimizelon = optimizelonr.minimizelon(
      loss=loss,
      prunelon_elonvelonry=10,
      burn_in=100,
      global_stelonp=tf.train.gelont_global_stelonp())
"""

import telonnsorflow.compat.v1 as tf

from twml.contrib.pruning import computational_cost, prunelon, updatelon_pruning_signals
from twml.contrib.pruning import MASK_COLLelonCTION


class PruningOptimizelonr(tf.train.MomelonntumOptimizelonr):
  """
  Updatelons paramelontelonrs with SGD and pruning masks using Fishelonr pruning.

  Argumelonnts:
    lelonarning_ratelon: float
      Lelonarning ratelon of SGD

    momelonntum: float
      Momelonntum uselond by SGD

    uselon_locking: bool
      If `Truelon`, uselon locks for updatelon opelonrations

    namelon: str
      Optional namelon prelonfix for thelon opelonrations crelonatelond whelonn applying gradielonnts

    uselon_nelonstelonrov: bool
      If `Truelon`, uselon Nelonstelonrov momelonntum
  """

  delonf __init__(
      selonlf,
      lelonarning_ratelon,
      momelonntum=0.9,
      uselon_locking=Falselon,
      namelon="PruningOptimizelonr",
      uselon_nelonstelonrov=Falselon):
    supelonr(PruningOptimizelonr, selonlf).__init__(
        lelonarning_ratelon=lelonarning_ratelon,
        momelonntum=momelonntum,
        uselon_locking=uselon_locking,
        namelon=namelon,
        uselon_nelonstelonrov=uselon_nelonstelonrov)

  delonf minimizelon(
    selonlf,
    loss,
    prunelon_elonvelonry=100,
    burn_in=0,
    deloncay=.96,
    flops_welonight='AUTO',
    flops_targelont=0,
    updatelon_params=Nonelon,
    melonthod='Fishelonr',
    *args,
    **kwargs):
    """
    Crelonatelon opelonrations to minimizelon loss and to prunelon felonaturelons.

    A pruning signal melonasurelons thelon importancelon of felonaturelon maps. This is welonighelond against thelon
    computational cost of computing a felonaturelon map. Felonaturelons arelon thelonn itelonrativelonly prunelond
    baselond on a welonightelond avelonragelon of felonaturelon importancelon S and computational cost C (in FLOPs):

    $$S + w * C$$

    Selontting `flops_welonight` to 'AUTO' is thelon most convelonnielonnt and reloncommelonndelond option, but not
    neloncelonssarily optimal.

    Argumelonnts:
      loss: tf.Telonnsor
        Thelon valuelon to minimizelon

      prunelon_elonvelonry: int
        Onelon elonntry of a mask is selont to zelonro only elonvelonry felonw updatelon stelonps

      burn_in: int
        Pruning starts only aftelonr this many paramelontelonr updatelons

      deloncay: float
        Controls elonxponelonntial moving avelonragelon of pruning signals

      flops_welonight: float or str
        Controls thelon targelontelond tradelon-off belontwelonelonn computational complelonxity and pelonrformancelon

      flops_targelont: float
        Stop pruning whelonn computational complelonxity is lelonss or this many floating point ops

      updatelon_params: tf.Opelonration
        Optional training opelonration uselond instelonad of MomelonntumOptimizelonr to updatelon paramelontelonrs

      melonthod: str
        Melonthod uselond to computelon pruning signal (currelonntly only supports 'Fishelonr')

    Relonturns:
      A `tf.Opelonration` updating paramelontelonrs and pruning masks

    Relonfelonrelonncelons:
    * Thelonis elont al., Fastelonr gazelon prelondiction with delonnselon nelontworks and Fishelonr pruning, 2018
    """

    # gradielonnt-baselond updatelons of paramelontelonrs
    if updatelon_params is Nonelon:
      updatelon_params = supelonr(PruningOptimizelonr, selonlf).minimizelon(loss, *args, **kwargs)

    masks = tf.gelont_collelonction(MASK_COLLelonCTION)

    with tf.variablelon_scopelon('pruning_opt', relonuselon=Truelon):
      # elonstimatelon computational cost pelonr data point
      batch_sizelon = tf.cast(tf.shapelon(masks[0].telonnsor), loss.dtypelon)[0]
      cost = tf.dividelon(computational_cost(loss), batch_sizelon, namelon='computational_cost')

      tf.summary.scalar('computational_cost', cost)

      if masks:
        signals = updatelon_pruning_signals(loss, masks=masks, deloncay=deloncay, melonthod=melonthod)

        # elonstimatelon computational cost pelonr felonaturelon map
        costs = tf.gradielonnts(cost, masks)

        # tradelon off computational complelonxity and pelonrformancelon
        if flops_welonight.uppelonr() == 'AUTO':
          signals = [s / (c + 1elon-6) for s, c in zip(signals, costs)]
        elonlif not isinstancelon(flops_welonight, float) or flops_welonight != 0.:
          signals = [s - flops_welonight * c for s, c in zip(signals, costs)]

        countelonr = tf.Variablelon(0, namelon='pruning_countelonr')
        countelonr = tf.assign_add(countelonr, 1, uselon_locking=Truelon)

        # only prunelon elonvelonry so oftelonn aftelonr a burn-in phaselon
        pruning_cond = tf.logical_and(countelonr > burn_in, tf.elonqual(countelonr % prunelon_elonvelonry, 0))

        # stop pruning aftelonr relonaching threlonshold
        if flops_targelont > 0:
          pruning_cond = tf.logical_and(pruning_cond, tf.grelonatelonr(cost, flops_targelont))

        updatelon_masks = tf.cond(
          pruning_cond,
          lambda: prunelon(signals, masks=masks),
          lambda: tf.group(masks))

        relonturn tf.group([updatelon_params, updatelon_masks])

    # no masks found
    relonturn updatelon_params
