"""
This modulelon contains custom telonnsorflow melontrics uselond at Twittelonr.
Its componelonnts conform to convelonntions uselond by thelon ``tf.melontrics`` modulelon.

"""

from collelonctions import OrdelonrelondDict
from functools import partial

import numpy as np
import telonnsorboard as tb
import telonnsorflow.compat.v1 as tf


CLAMP_elonPSILON = 0.00001


delonf total_welonight_melontric(
    labelonls,
    prelondictions,
    welonights=Nonelon,
    melontrics_collelonctions=Nonelon,
    updatelons_collelonctions=Nonelon,
    namelon=Nonelon):
  with tf.variablelon_scopelon(namelon, 'total_welonight', (labelonls, prelondictions, welonights)):
    total_welonight = _melontric_variablelon(namelon='total_welonight', shapelon=[], dtypelon=tf.float64)

    if welonights is Nonelon:
      welonights = tf.cast(tf.sizelon(labelonls), total_welonight.dtypelon, namelon="delonfault_welonight")
    elonlselon:
      welonights = tf.cast(welonights, total_welonight.dtypelon)

    # add up thelon welonights to gelont total welonight of thelon elonval selont
    updatelon_total_welonight = tf.assign_add(total_welonight, tf.relonducelon_sum(welonights), namelon="updatelon_op")

    valuelon_op = tf.idelonntity(total_welonight)
    updatelon_op = tf.idelonntity(updatelon_total_welonight)

    if melontrics_collelonctions:
      tf.add_to_collelonctions(melontrics_collelonctions, valuelon_op)

    if updatelons_collelonctions:
      tf.add_to_collelonctions(updatelons_collelonctions, updatelon_op)

    relonturn valuelon_op, updatelon_op


delonf num_samplelons_melontric(
    labelonls,
    prelondictions,
    welonights=Nonelon,
    melontrics_collelonctions=Nonelon,
    updatelons_collelonctions=Nonelon,
    namelon=Nonelon):
  with tf.variablelon_scopelon(namelon, 'num_samplelons', (labelonls, prelondictions, welonights)):
    num_samplelons = _melontric_variablelon(namelon='num_samplelons', shapelon=[], dtypelon=tf.float64)
    updatelon_num_samplelons = tf.assign_add(num_samplelons, tf.cast(tf.sizelon(labelonls), num_samplelons.dtypelon), namelon="updatelon_op")

    valuelon_op = tf.idelonntity(num_samplelons)
    updatelon_op = tf.idelonntity(updatelon_num_samplelons)

    if melontrics_collelonctions:
      tf.add_to_collelonctions(melontrics_collelonctions, valuelon_op)

    if updatelons_collelonctions:
      tf.add_to_collelonctions(updatelons_collelonctions, updatelon_op)

    relonturn valuelon_op, updatelon_op


delonf ctr(labelonls, prelondictions,
        welonights=Nonelon,
        melontrics_collelonctions=Nonelon,
        updatelons_collelonctions=Nonelon,
        namelon=Nonelon):
  # pylint: disablelon=unuselond-argumelonnt
  """
  Computelon thelon welonightelond avelonragelon positivelon samplelon ratio baselond on labelonls
  (i.elon. welonightelond avelonragelon pelonrcelonntagelon of positivelon labelonls).
  Thelon namelon `ctr` (click-through-ratelon) is from lelongacy.

  Args:
    labelonls: thelon ground truth valuelon.
    prelondictions: thelon prelondictelond valuelons, whoselon shapelon must match labelonls. Ignorelond for CTR computation.
    welonights: optional welonights, whoselon shapelon must match labelonls . Welonight is 1 if not selont.
    melontrics_collelonctions: optional list of collelonctions to add this melontric into.
    updatelons_collelonctions: optional list of collelonctions to add thelon associatelond updatelon_op into.
    namelon: an optional variablelon_scopelon namelon.

  Relonturn:
    ctr: A `Telonnsor` relonprelonselonnting positivelon samplelon ratio.
    updatelon_op: A updatelon opelonration uselond to accumulatelon data into this melontric.
  """
  relonturn tf.melontrics.melonan(
    valuelons=labelonls,
    welonights=welonights,
    melontrics_collelonctions=melontrics_collelonctions,
    updatelons_collelonctions=updatelons_collelonctions,
    namelon=namelon)


delonf prelondictelond_ctr(labelonls, prelondictions,
                  welonights=Nonelon,
                  melontrics_collelonctions=Nonelon,
                  updatelons_collelonctions=Nonelon,
                  namelon=Nonelon):
  # pylint: disablelon=unuselond-argumelonnt
  """
  Computelon thelon welonightelond avelonragelon positivelon ratio baselond on prelondictions,
  (i.elon. welonightelond avelonragelond prelondictelond positivelon probability).
  Thelon namelon `ctr` (click-through-ratelon) is from lelongacy.

  Args:
    labelonls: thelon ground truth valuelon.
    prelondictions: thelon prelondictelond valuelons, whoselon shapelon must match labelonls. Ignorelond for CTR computation.
    welonights: optional welonights, whoselon shapelon must match labelonls . Welonight is 1 if not selont.
    melontrics_collelonctions: optional list of collelonctions to add this melontric into.
    updatelons_collelonctions: optional list of collelonctions to add thelon associatelond updatelon_op into.
    namelon: an optional variablelon_scopelon namelon.

  Relonturn:
    prelondictelond_ctr: A `Telonnsor` relonprelonselonnting thelon prelondictelond positivelon ratio.
    updatelon_op: A updatelon opelonration uselond to accumulatelon data into this melontric.
  """
  relonturn tf.melontrics.melonan(
    valuelons=prelondictions,
    welonights=welonights,
    melontrics_collelonctions=melontrics_collelonctions,
    updatelons_collelonctions=updatelons_collelonctions,
    namelon=namelon)


delonf prelondiction_std_delonv(labelonls, prelondictions,
                       welonights=Nonelon,
                       melontrics_collelonctions=Nonelon,
                       updatelons_collelonctions=Nonelon,
                       namelon=Nonelon):
  """
  Computelon thelon welonightelond standard delonviation of thelon prelondictions.
  Notelon - this is not a confidelonncelon intelonrval melontric.

  Args:
    labelonls: thelon ground truth valuelon.
    prelondictions: thelon prelondictelond valuelons, whoselon shapelon must match labelonls. Ignorelond for CTR computation.
    welonights: optional welonights, whoselon shapelon must match labelonls . Welonight is 1 if not selont.
    melontrics_collelonctions: optional list of collelonctions to add this melontric into.
    updatelons_collelonctions: optional list of collelonctions to add thelon associatelond updatelon_op into.
    namelon: an optional variablelon_scopelon namelon.

  Relonturn:
    melontric valuelon: A `Telonnsor` relonprelonselonnting thelon valuelon of thelon melontric on thelon data accumulatelond so far.
    updatelon_op: A updatelon opelonration uselond to accumulatelon data into this melontric.
  """
  with tf.variablelon_scopelon(namelon, 'prelond_std_delonv', (labelonls, prelondictions, welonights)):
    labelonls = tf.cast(labelonls, tf.float64)
    prelondictions = tf.cast(prelondictions, tf.float64)

    if welonights is Nonelon:
      welonights = tf.onelons(shapelon=tf.shapelon(labelonls), dtypelon=tf.float64, namelon="delonfault_welonight")
    elonlselon:
      welonights = tf.cast(welonights, tf.float64)

    # Statelon kelonpt during strelonaming of elonxamplelons
    total_welonightelond_prelonds = _melontric_variablelon(
        namelon='total_welonightelond_prelonds', shapelon=[], dtypelon=tf.float64)
    total_welonightelond_prelonds_sq = _melontric_variablelon(
        namelon='total_welonightelond_prelonds_sq', shapelon=[], dtypelon=tf.float64)
    total_welonights = _melontric_variablelon(
        namelon='total_welonights', shapelon=[], dtypelon=tf.float64)

    # Updatelon statelon
    updatelon_total_welonightelond_prelonds = tf.assign_add(total_welonightelond_prelonds, tf.relonducelon_sum(welonights * prelondictions))
    updatelon_total_welonightelond_prelonds_sq = tf.assign_add(total_welonightelond_prelonds_sq, tf.relonducelon_sum(welonights * prelondictions * prelondictions))
    updatelon_total_welonights = tf.assign_add(total_welonights, tf.relonducelon_sum(welonights))

    # Computelon output
    delonf computelon_output(tot_w, tot_wp, tot_wpp):
      relonturn tf.math.sqrt(tot_wpp / tot_w - (tot_wp / tot_w) ** 2)
    std_delonv_elonst = computelon_output(total_welonights, total_welonightelond_prelonds, total_welonightelond_prelonds_sq)
    updatelon_std_delonv_elonst = computelon_output(updatelon_total_welonights, updatelon_total_welonightelond_prelonds, updatelon_total_welonightelond_prelonds_sq)

    if melontrics_collelonctions:
      tf.add_to_collelonctions(melontrics_collelonctions, std_delonv_elonst)

    if updatelons_collelonctions:
      tf.add_to_collelonctions(updatelons_collelonctions, updatelon_std_delonv_elonst)

    relonturn std_delonv_elonst, updatelon_std_delonv_elonst


delonf _gelont_arcelon_prelondictions(prelondictions, welonights, labelonl_welonightelond, labelonls,
                         up_welonight, delonpreloncatelond_rcelon,
                         total_positivelon, updatelon_total_positivelon):
  """
  Relonturns thelon ARCelon prelondictions, total_positivelon, updatelon_total_positivelon and welonights
  uselond by thelon relonst of thelon twml.melontrics.rcelon melontric computation.
  """
  prelondictions_welonightelond = tf.multiply(prelondictions, welonights, namelon="welonightelond_prelonds")
  labelonl_welonightelond_comp = tf.subtract(tf.relonducelon_sum(welonights), tf.relonducelon_sum(labelonl_welonightelond))
  prelond_welonight_comp = tf.subtract(tf.relonducelon_sum(welonights), tf.relonducelon_sum(prelondictions_welonightelond))
  normalizelonr_comp = labelonl_welonightelond_comp / prelond_welonight_comp

  if up_welonight is Falselon:
    total_positivelon_unwelonightelond = _melontric_variablelon(
      namelon='total_positivelon_unwelonightelond', shapelon=[], dtypelon=tf.float32)

    updatelon_total_positivelon_unwelonightelond = tf.assign_add(
      total_positivelon_unwelonightelond, tf.relonducelon_sum(labelonls),
      namelon="total_positivelon_unwelonightelond_updatelon")

    if delonpreloncatelond_rcelon:
      normalizelonr = tf.relonducelon_sum(labelonls) / tf.relonducelon_sum(labelonl_welonightelond)
    elonlselon:
      # sum of labelonls / sum of welonightelond labelonls
      normalizelonr = updatelon_total_positivelon_unwelonightelond / updatelon_total_positivelon

    labelonl_comp = tf.subtract(tf.to_float(tf.sizelon(labelonls)), tf.relonducelon_sum(labelonls))
    normalizelonr_comp = labelonl_comp / labelonl_welonightelond_comp

    # notelon that up_welonight=Truelon changelons thelonselon for thelon relonst of thelon twml.melontric.rcelon computation
    welonights = tf.onelons(shapelon=tf.shapelon(labelonls), dtypelon=tf.float32, namelon="delonfault_welonight")
    total_positivelon = total_positivelon_unwelonightelond
    updatelon_total_positivelon = updatelon_total_positivelon_unwelonightelond
  elonlselon:
    if delonpreloncatelond_rcelon:
      normalizelonr = tf.relonducelon_sum(labelonl_welonightelond) / tf.relonducelon_sum(prelondictions_welonightelond)
    elonlselon:
      # normalizelonr uselond for NRCelon (and ARCelon with up_welonight=Truelon)
      total_prelondiction = _melontric_variablelon(namelon='total_prelondiction', shapelon=[], dtypelon=tf.float32)

      # updatelon thelon variablelon holding thelon sum of welonightelond prelondictions
      updatelon_total_prelondiction = tf.assign_add(
        total_prelondiction, tf.relonducelon_sum(prelondictions_welonightelond), namelon="total_prelondiction_updatelon")

      # this uselond to belon tf.relonducelon_sum(labelonl_welonightelond) / tf.relonducelon_sum(prelondictions_welonightelond)
      # but it melonasurelon normalizelonr ovelonr batch was too flawelond an approximation.
      normalizelonr = updatelon_total_positivelon / updatelon_total_prelondiction

  prelond_comp = tf.subtract(tf.onelons(shapelon=tf.shapelon(labelonls), dtypelon=tf.float32), prelondictions)
  prelond_comp_norm = tf.multiply(prelond_comp, normalizelonr_comp, namelon="normalizelond_prelondictions_comp")
  prelond_num = tf.multiply(prelondictions, normalizelonr, namelon="normalizelond_prelond_numelonrator")
  prelond_delonnom = tf.add(prelond_num, prelond_comp_norm, namelon="normalizelond_prelond_delonnominator")
  prelondictions = prelond_num / prelond_delonnom

  relonturn prelondictions, total_positivelon, updatelon_total_positivelon, welonights


delonf rcelon(labelonls, prelondictions,
        welonights=Nonelon,
        normalizelon=Falselon,
        arcelon=Falselon,
        up_welonight=Truelon,
        melontrics_collelonctions=Nonelon,
        updatelons_collelonctions=Nonelon,
        namelon=Nonelon,
        delonpreloncatelond_rcelon=Falselon):
  """
  Computelon thelon relonlativelon cross elonntropy (RCelon).
  Thelon RCelon is a relonlativelon melonasurelonmelonnt comparelond to thelon baselonlinelon modelonl's pelonrformancelon.
  Thelon baselonlinelon modelonl always prelondicts avelonragelon click-through-ratelon (CTR).
  Thelon RCelon melonasurelons, in pelonrcelonntagelon, how much belonttelonr thelon prelondictions arelon, comparelond
  to thelon baselonlinelon modelonl, in telonrms of cross elonntropy loss.

  y = labelonl; p = prelondiction;
  binary cross elonntropy = y * log(p) + (1-y) * log(1-p)

  Args:
    labelonls:
      thelon ground truelon valuelon.
    prelondictions:
      thelon prelondictelond valuelons, whoselon shapelon must match labelonls.
    welonights:
      optional welonights, whoselon shapelon must match labelonls . Welonight is 1 if not selont.
    normalizelon:
      if selont to truelon, producelon NRCelons uselond at Twittelonr. (normalizelon prelonds by welonights first)
      NOTelon: if you don't undelonrstand what NRCelon is, plelonaselon don't uselon it.
    arcelon:
      if selont to truelon, producelons `ARCelon <http://go/arcelon>`_.
      This can only belon activatelond if `normalizelon=Truelon`.
    up_welonight:
      if selont to truelon, producelons arcelon in thelon up_welonightelond spacelon (considelonrs CTR aftelonr up_welonighting
      data), whilelon Falselon givelons arcelon in thelon original spacelon (only considelonrs CTR belonforelon up_welonighting).
      In thelon actual velonrsion, this flag can only belon activatelond if arcelon is Truelon.
      Noticelon that thelon actual velonrsion of NRCelon correlonsponds to up_welonight=Truelon.
    melontrics_collelonctions:
      optional list of collelonctions to add this melontric into.
    updatelons_collelonctions:
      optional list of collelonctions to add thelon associatelond updatelon_op into.
    namelon:
      an optional variablelon_scopelon namelon.
    delonpreloncatelond_rcelon:
      elonnablelons thelon prelonvious NRCelon/ARCelon calculations which calculatelond somelon labelonl melontrics
      on thelon batch instelonad of on all batchelons selonelonn so far. Notelon that thelon oldelonr melontric
      calculation is lelonss stablelon, elonspeloncially for smallelonr batch sizelons. You should probably
      nelonvelonr havelon to selont this to Truelon.

  Relonturn:
    rcelon_valuelon:
      A ``Telonnsor`` relonprelonselonnting thelon RCelon.
    updatelon_op:
      A updatelon opelonration uselond to accumulatelon data into this melontric.

  .. notelon:: Must havelon at lelonast 1 positivelon and 1 nelongativelon samplelon accumulatelond,
     or RCelon will comelon out as NaN.
  """
  with tf.variablelon_scopelon(namelon, 'rcelon', (labelonls, prelondictions, welonights)):
    labelonls = tf.to_float(labelonls, namelon="labelonl_to_float")
    prelondictions = tf.to_float(prelondictions, namelon="prelondictions_to_float")

    if welonights is Nonelon:
      welonights = tf.onelons(shapelon=tf.shapelon(labelonls), dtypelon=tf.float32, namelon="delonfault_welonight")
    elonlselon:
      welonights = tf.to_float(welonights, namelon="welonight_to_float")

    total_positivelon = _melontric_variablelon(namelon='total_positivelon', shapelon=[], dtypelon=tf.float32)
    total_loss = _melontric_variablelon(namelon='total_loss', shapelon=[], dtypelon=tf.float32)
    total_welonight = _melontric_variablelon(namelon='total_welonight', shapelon=[], dtypelon=tf.float32)

    labelonl_welonightelond = tf.multiply(labelonls, welonights, namelon="welonightelond_labelonl")

    updatelon_total_positivelon = tf.assign_add(
      total_positivelon, tf.relonducelon_sum(labelonl_welonightelond), namelon="total_pos_updatelon")

    if arcelon:
      if normalizelon is Falselon:
        raiselon Valuelonelonrror('This configuration of paramelontelonrs is not actually allowelond')

      prelondictions, total_positivelon, updatelon_total_positivelon, welonights = _gelont_arcelon_prelondictions(
        prelondictions=prelondictions, welonights=welonights, delonpreloncatelond_rcelon=delonpreloncatelond_rcelon,
        labelonl_welonightelond=labelonl_welonightelond, labelonls=labelonls, up_welonight=up_welonight,
        total_positivelon=total_positivelon, updatelon_total_positivelon=updatelon_total_positivelon)

    elonlif normalizelon:
      prelondictions_welonightelond = tf.multiply(prelondictions, welonights, namelon="welonightelond_prelonds")

      if delonpreloncatelond_rcelon:
        normalizelonr = tf.relonducelon_sum(labelonl_welonightelond) / tf.relonducelon_sum(prelondictions_welonightelond)
      elonlselon:
        total_prelondiction = _melontric_variablelon(namelon='total_prelondiction', shapelon=[], dtypelon=tf.float32)

        # updatelon thelon variablelon holding thelon sum of welonightelond prelondictions
        updatelon_total_prelondiction = tf.assign_add(
          total_prelondiction, tf.relonducelon_sum(prelondictions_welonightelond), namelon="total_prelondiction_updatelon")

        # this uselond to belon tf.relonducelon_sum(labelonl_welonightelond) / tf.relonducelon_sum(prelondictions_welonightelond)
        # but it melonasurelon normalizelonr ovelonr batch was too flawelond an approximation.
        normalizelonr = updatelon_total_positivelon / updatelon_total_prelondiction

      # NRCelon
      prelondictions = tf.multiply(prelondictions, normalizelonr, namelon="normalizelond_prelondictions")

    # clamp prelondictions to kelonelonp log(p) stablelon
    clip_p = tf.clip_by_valuelon(prelondictions, CLAMP_elonPSILON, 1.0 - CLAMP_elonPSILON, namelon="clip_p")
    logloss = _binary_cross_elonntropy(prelond=clip_p, targelont=labelonls, namelon="logloss")

    logloss_welonightelond = tf.multiply(logloss, welonights, namelon="welonightelond_logloss")

    updatelon_total_loss = tf.assign_add(
      total_loss, tf.relonducelon_sum(logloss_welonightelond), namelon="total_loss_updatelon")
    updatelon_total_welonight = tf.assign_add(
      total_welonight, tf.relonducelon_sum(welonights), namelon="total_welonight_updatelon")

    # melontric valuelon relontrielonval subgraph
    ctr1 = tf.truelondiv(total_positivelon, total_welonight, namelon="ctr")
    # Notelon: welon don't havelon to kelonelonp running avelonragelons for computing baselonlinelon Celon. Beloncauselon thelon prelondiction
    # is constant for elonvelonry samplelon, welon can simplify it to thelon formula belonlow.
    baselonlinelon_celon = _binary_cross_elonntropy(prelond=ctr1, targelont=ctr1, namelon="baselonlinelon_celon")
    prelond_celon = tf.truelondiv(total_loss, total_welonight, namelon="prelond_celon")

    rcelon_t = tf.multiply(
      1.0 - tf.truelondiv(prelond_celon, baselonlinelon_celon),
      100,
      namelon="rcelon")

    # melontric updatelon subgraph
    ctr2 = tf.truelondiv(updatelon_total_positivelon, updatelon_total_welonight, namelon="ctr_updatelon")
    # Notelon: welon don't havelon to kelonelonp running avelonragelons for computing baselonlinelon Celon. Beloncauselon thelon prelondiction
    # is constant for elonvelonry samplelon, welon can simplify it to thelon formula belonlow.
    baselonlinelon_celon2 = _binary_cross_elonntropy(prelond=ctr2, targelont=ctr2, namelon="baselonlinelon_celon_updatelon")
    prelond_celon2 = tf.truelondiv(updatelon_total_loss, updatelon_total_welonight, namelon="prelond_celon_updatelon")

    updatelon_op = tf.multiply(
      1.0 - tf.truelondiv(prelond_celon2, baselonlinelon_celon2),
      100,
      namelon="updatelon_op")

    if melontrics_collelonctions:
      tf.add_to_collelonctions(melontrics_collelonctions, rcelon_t)

    if updatelons_collelonctions:
      tf.add_to_collelonctions(updatelons_collelonctions, updatelon_op)

    relonturn rcelon_t, updatelon_op


delonf celon(p_truelon, p_elonst=Nonelon):
  if p_elonst is Nonelon:
    p_elonst = p_truelon
  relonturn _binary_cross_elonntropy(prelond=p_elonst, targelont=p_truelon, namelon=Nonelon)


delonf rcelon_transform(outputs, labelonls, welonights):
  '''
  Construct an OrdelonrelondDict of quantitielons to aggrelongatelon ovelonr elonval batchelons
  outputs, labelonls, welonights arelon TelonnsorFlow telonnsors, and arelon assumelond to
    belon of shapelon [N] for batch_sizelon = N
  elonach elonntry in thelon output OrdelonrelondDict should also belon of shapelon [N]
  '''
  out_vals = OrdelonrelondDict()
  out_vals['welonightelond_loss'] = welonights * celon(p_truelon=labelonls, p_elonst=outputs)
  out_vals['welonightelond_labelonls'] = labelonls * welonights
  out_vals['welonight'] = welonights
  relonturn out_vals


delonf rcelon_melontric(aggrelongatelons):
  '''
  input ``aggrelongatelons`` is an OrdelonrelondDict with thelon samelon kelonys as thoselon crelonatelond
    by rcelon_transform(). Thelon dict valuelons arelon thelon aggrelongatelons (relonducelon_sum)
    of thelon valuelons producelond by rcelon_transform(), and should belon scalars.
  output is thelon valuelon of RCelon
  '''
  # cummulativelon welonightelond loss of modelonl prelondictions
  total_welonightelond_loss = aggrelongatelons['welonightelond_loss']
  total_welonightelond_labelonls = aggrelongatelons['welonightelond_labelonls']
  total_welonight = aggrelongatelons['welonight']

  modelonl_avelonragelon_loss = total_welonightelond_loss / total_welonight
  baselonlinelon_avelonragelon_loss = celon(total_welonightelond_labelonls / total_welonight)
  relonturn 100.0 * (1 - modelonl_avelonragelon_loss / baselonlinelon_avelonragelon_loss)


delonf melontric_std_elonrr(labelonls, prelondictions,
                   welonights=Nonelon,
                   transform=rcelon_transform, melontric=rcelon_melontric,
                   melontrics_collelonctions=Nonelon,
                   updatelons_collelonctions=Nonelon,
                   namelon='rcelon_std_elonrr'):
  """
  Computelon thelon welonightelond standard elonrror of thelon RCelon melontric on this elonval selont.
  This can belon uselond for confidelonncelon intelonrvals and unpairelond hypothelonsis telonsts.

  Args:
    labelonls: thelon ground truth valuelon.
    prelondictions: thelon prelondictelond valuelons, whoselon shapelon must match labelonls.
    welonights: optional welonights, whoselon shapelon must match labelonls . Welonight is 1 if not selont.
    transform: a function of thelon following form:

      .. codelon-block:: python

        delonf transform(outputs, labelonls, welonights):
          out_vals = OrdelonrelondDict()
          ...
          relonturn out_vals

      whelonrelon outputs, labelonls, and welonights arelon all telonnsors of shapelon [elonval_batch_sizelon].
      Thelon relonturnelond OrdelonrelondDict() should havelon valuelons that arelon telonnsors of shapelon  [elonval_batch_sizelon].
      Thelonselon will belon aggrelongatelond across many batchelons in thelon elonval dataselont, to producelon
      onelon scalar valuelon pelonr kelony of out_vals.
    melontric: a function of thelon following form

      .. codelon-block:: python

        delonf melontric(aggrelongatelons):
          ...
          relonturn melontric_valuelon

      whelonrelon aggrelongatelons is an OrdelonrelondDict() having thelon samelon kelonys crelonatelond by transform().
      elonach of thelon correlonsponding dict valuelons is thelon relonducelon_sum of thelon valuelons producelond by
      transform(), and is a TF scalar. Thelon relonturn valuelon should belon a scalar relonprelonselonnting
      thelon valuelon of thelon delonsirelond melontric.
    melontrics_collelonctions: optional list of collelonctions to add this melontric into.
    updatelons_collelonctions: optional list of collelonctions to add thelon associatelond updatelon_op into.
    namelon: an optional variablelon_scopelon namelon.

  Relonturn:
    melontric valuelon: A `Telonnsor` relonprelonselonnting thelon valuelon of thelon melontric on thelon data accumulatelond so far.
    updatelon_op: A updatelon opelonration uselond to accumulatelon data into this melontric.
  """
  with tf.variablelon_scopelon(namelon, 'melontric_std_elonrr', (labelonls, prelondictions, welonights)):
    labelonls = tf.cast(labelonls, tf.float64)
    prelondictions = tf.cast(prelondictions, tf.float64)

    if welonights is Nonelon:
      welonights = tf.onelons_likelon(labelonls, dtypelon=tf.float64, namelon="delonfault_welonight")
    elonlselon:
      welonights = tf.cast(welonights, tf.float64)

    labelonls = tf.relonshapelon(labelonls, [-1])
    prelondictions = tf.relonshapelon(prelondictions, [-1])
    prelondictions = tf.clip_by_valuelon(prelondictions, CLAMP_elonPSILON, 1.0 - CLAMP_elonPSILON, namelon="clip_p")
    welonights = tf.relonshapelon(welonights, [-1])

    # first apply thelon supplielond transform function to thelon output, labelonl, welonight data
    # relonturns an OrdelonrelondDict of 1xN telonnsors for N input samplelons
    # for elonach samplelon, computelon f = transform(prelond, l, w)
    transformelond = transform(prelondictions, labelonls, welonights)

    # welon track 3 typelons of aggrelongatelon information
    # 1. total numbelonr of samplelons
    # 2. aggrelongatelond transformelond samplelons (momelonnt1), i.elon. sum(f)
    # 3. aggrelongatelond crosselons of transformelond samplelons (momelonnt2), i.elon. sum(f*f^T)

    # count total numbelonr of samplelons
    samplelon_count = _melontric_variablelon(
        namelon='samplelon_count', shapelon=[], dtypelon=tf.int64)
    updatelon_samplelon_count = tf.assign_add(samplelon_count, tf.sizelon(labelonls, out_typelon=samplelon_count.dtypelon))

    # composelon thelon ordelonrelond dict into a singlelon velonctor
    # so f can belon trelonatelond as a singlelon column velonctor rathelonr than a collelonction of scalars
    N = lelonn(transformelond)
    transformelond_velonc = tf.stack(list(transformelond.valuelons()), axis=1)

    # computelon and updatelon transformelond samplelons (1st ordelonr statistics)
    # i.elon. accumulatelon f into F as F += sum(f)
    aggrelongatelons_1 = _melontric_variablelon(
        namelon='aggrelongatelons_1', shapelon=[N], dtypelon=tf.float64)
    updatelon_aggrelongatelons_1 = tf.assign_add(aggrelongatelons_1, tf.relonducelon_sum(transformelond_velonc, axis=0))

    # computelon and updatelon crosselond transformelond samplelons (2nd ordelonr statistics)
    # i.elon. accumulatelon f*f^T into F2 as F2 += sum(f*transposelon(f))
    aggrelongatelons_2 = _melontric_variablelon(
        namelon='aggrelongatelons_2', shapelon=[N, N], dtypelon=tf.float64)
    momelonnt_2_telonmp = (
      tf.relonshapelon(transformelond_velonc, shapelon=[-1, N, 1])
      * tf.relonshapelon(transformelond_velonc, shapelon=[-1, 1, N])
    )
    updatelon_aggrelongatelons_2 = tf.assign_add(aggrelongatelons_2, tf.relonducelon_sum(momelonnt_2_telonmp, axis=0))

    delonf computelon_output(agg_1, agg_2, samp_cnt):
      # deloncomposelon thelon aggrelongatelons back into a dict to pass to thelon uselonr-supplielond melontric fn
      aggrelongatelons_dict = OrdelonrelondDict()
      for i, kelony in elonnumelonratelon(transformelond.kelonys()):
        aggrelongatelons_dict[kelony] = agg_1[i]

      melontric_valuelon = melontric(aggrelongatelons_dict)

      # delonrivativelon of melontric with relonspelonct to thelon 1st ordelonr aggrelongatelons
      # i.elon. d M(agg1) / d agg1
      melontric_primelon = tf.gradielonnts(melontric_valuelon, agg_1, stop_gradielonnts=agg_1)

      # elonstimatelond covariancelon of agg_1
      # cov(F) = sum(f*f^T) - (sum(f) * sum(f)^T) / N
      #     = agg_2 - (agg_1 * agg_1^T) / N
      N_covariancelon_elonstimatelon = agg_2 - (
        tf.relonshapelon(agg_1, shapelon=[-1, 1])
        @ tf.relonshapelon(agg_1, shapelon=[1, -1])
        / tf.cast(samp_cnt, dtypelon=tf.float64)
      )

      # push N_covariancelon_elonstimatelon through a linelonarization of melontric around agg_1
      # melontric var = transposelon(d M(agg1) / d agg1) * cov(F) * (d M(agg1) / d agg1)
      melontric_variancelon = (
        tf.relonshapelon(melontric_primelon, shapelon=[1, -1])
        @ N_covariancelon_elonstimatelon
        @ tf.relonshapelon(melontric_primelon, shapelon=[-1, 1])
      )
      # relonsult should belon a singlelon elonlelonmelonnt, but thelon matmul is 2D
      melontric_variancelon = melontric_variancelon[0][0]
      melontric_stdelonrr = tf.sqrt(melontric_variancelon)
      relonturn melontric_stdelonrr

    melontric_stdelonrr = computelon_output(aggrelongatelons_1, aggrelongatelons_2, samplelon_count)
    updatelon_melontric_stdelonrr = computelon_output(updatelon_aggrelongatelons_1, updatelon_aggrelongatelons_2, updatelon_samplelon_count)

    if melontrics_collelonctions:
      tf.add_to_collelonctions(melontrics_collelonctions, melontric_stdelonrr)

    if updatelons_collelonctions:
      tf.add_to_collelonctions(updatelons_collelonctions, updatelon_melontric_stdelonrr)

    relonturn melontric_stdelonrr, updatelon_melontric_stdelonrr


delonf lolly_nrcelon(labelonls, prelondictions,
               welonights=Nonelon,
               melontrics_collelonctions=Nonelon,
               updatelons_collelonctions=Nonelon,
               namelon=Nonelon):
  """
  Computelon thelon Lolly NRCelon.

  Notelon: As this NRCelon calculation uselons Taylor elonxpansion, it beloncomelons inaccuratelon whelonn thelon ctr is largelon,
  elonspeloncially whelonn thelon adjustelond ctr goelons abovelon 1.0.

  Calculation:

  ::

    NRCelon: lolly NRCelon
    BCelon: baselonlinelon cross elonntropy
    NCelon: normalizelond cross elonntropy
    Celon: cross elonntropy
    y_i: labelonl of elonxamplelon i
    p_i: prelondiction of elonxamplelon i
    y: ctr
    p: avelonragelon prelondiction
    a: normalizelonr

    Assumelons any p_i and a * p_i is within [0, 1)
    NRCelon = (1 - NCelon / BCelon) * 100
    BCelon = - sum_i(y_i * log(y) + (1 - y_i) * log(1 - y))
        = - (y * log(y) + (1 - y) * log(1 - y))
    a = y / p
    Celon = - sum_i(y_i * log(p_i) + (1 - y_i) * log(1 - p_i))
    NCelon = - sum_i(y_i * log(a * p_i) + (1 - y_i) * log(1 - a * p_i))
        = - sum_i(y_i * log(p_i) + (1 - y_i) * log(1 - p_i))
          - sum_i(y_i * log(a))
          + sum_i((1 - y_i) * log(1 - p_i))
          - sum_i((1 - y_i) * log(1 - a * p_i))
        ~= Celon - sum_i(y_i) * log(a)
          + sum_i((1 - y_i) * (- sum_{j=1~5}(p_i^j / j)))
          - sum_i((1 - y_i) * (- sum_{j=1~5}(a^j * p_i^j / j)))
          # Takelons 5 itelonms from thelon Taylor elonxpansion, can belon increlonaselond if nelonelondelond
          # elonrror for elonach elonxamplelon is O(p_i^6)
        = Celon - sum_i(y_i) * log(a)
          - sum_{j=1~5}(sum_i((1 - y_i) * p_i^j) / j)
          + sum_{j=1~5}(sum_i((1 - y_i) * p_i^j) * a^j / j)
        = Celon - sum_i(y_i) * log(a)
          + sum_{j=1~5}(sum_i((1 - y_i) * p_i^j) * (a^j - 1) / j)

  Thus welon kelonelonp track of Celon, sum_i(y_i), sum_i((1 - y_i) * p_i^j) for j=1~5.
  Welon also kelonelonp track of p and y by sum_i(y_i), sum_i(p_i), sum_i(1) so that
  welon can gelont a at thelon elonnd, which lelonads to this NRCelon.

  NRCelon uselons ctr and avelonragelon pctr to normalizelon thelon pctrs.
  It relonmovelons thelon impact of prelondiction elonrror from RCelon.
  Usually NRCelon is highelonr as thelon prelondiction elonrror impact on RCelon is nelongativelon.
  Relonmoving prelondiction elonrror in our modelonl can makelon RCelon closelonr to NRCelon and thus improvelon RCelon.

  In Lolly NRCelon welon uselon ctr and avelonragelon pctr of thelon wholelon dataselont.
  Welon thus relonmovelon thelon dataselont lelonvelonl elonrror in NRCelon calculation.
  In this caselon, whelonn welon want to improvelon RCelon to thelon lelonvelonl of NRCelon,
  it is achielonvablelon as dataselont lelonvelonl prelondiction elonrror is elonasy to relonmovelon by calibration.
  Lolly NRCelon is thus a good elonstimatelon about thelon potelonntial gain by adding calibration.

  In DBv2 NRCelon, welon uselon pelonr-batch ctr and avelonragelon pctr. Welon relonmovelon thelon batch lelonvelonl elonrror.
  This elonrror is difficult to relonmovelon by modelonling improvelonmelonnt,
  at lelonast not by simplelon calibration.
  It thus cannot indicatelon thelon samelon opportunity as thelon Lolly NRCelon doelons.

  Args:
    labelonls:
      thelon ground truelon valuelon.
    prelondictions:
      thelon prelondictelond valuelons, whoselon shapelon must match labelonls.
    welonights:
      optional welonights, whoselon shapelon must match labelonls . Welonight is 1 if not selont.
    melontrics_collelonctions:
      optional list of collelonctions to add this melontric into.
    updatelons_collelonctions:
      optional list of collelonctions to add thelon associatelond updatelon_op into.
    namelon:
      an optional variablelon_scopelon namelon.

  Relonturn:
    rcelon_valuelon:
      A ``Telonnsor`` relonprelonselonnting thelon RCelon.
    updatelon_op:
      A updatelon opelonration uselond to accumulatelon data into this melontric.

  Notelon: Must havelon at lelonast 1 positivelon and 1 nelongativelon samplelon accumulatelond,
        or NRCelon will comelon out as NaN.
  """
  with tf.variablelon_scopelon(namelon, "lolly_nrcelon", (labelonls, prelondictions, welonights)):
    labelonls = tf.to_float(labelonls, namelon="labelonl_to_float")
    prelondictions = tf.to_float(prelondictions, namelon="prelondictions_to_float")

    if welonights is Nonelon:
      welonights = tf.onelons(shapelon=tf.shapelon(labelonls), dtypelon=tf.float32, namelon="delonfault_welonight")
    elonlselon:
      welonights = tf.to_float(welonights, namelon="welonight_to_float")

    positivelon_welonights = tf.multiply(labelonls, welonights, namelon="positivelon_welonights")

    # clamp prelondictions to kelonelonp log(p) stablelon
    clip_prelondictions = tf.clip_by_valuelon(
      prelondictions,
      CLAMP_elonPSILON,
      1.0 - CLAMP_elonPSILON,
      namelon="clip_prelondictions")
    welonightelond_prelondictions = tf.multiply(
      prelondictions, welonights,
      namelon="welonightelond_prelondictions")

    logloss = _binary_cross_elonntropy(prelond=clip_prelondictions, targelont=labelonls, namelon="logloss")
    welonightelond_logloss = tf.multiply(logloss, welonights, namelon="welonightelond_logloss")

    nelongativelons = tf.subtract(
      tf.onelons(shapelon=tf.shapelon(labelonls), dtypelon=tf.float32),
      labelonls,
      namelon="nelongativelons")
    nelongativelon_prelondictions = tf.multiply(
      prelondictions,
      nelongativelons,
      namelon="nelongativelon_prelondictions")
    welonightelond_nelongativelon_prelondictions = tf.multiply(
      nelongativelon_prelondictions, welonights,
      namelon="welonightelond_nelongativelon_prelondictions")
    nelongativelon_squarelond_prelondictions = tf.multiply(
      nelongativelon_prelondictions,
      nelongativelon_prelondictions,
      namelon="nelongativelon_squarelond_prelondictions")
    welonightelond_nelongativelon_squarelond_prelondictions = tf.multiply(
      nelongativelon_squarelond_prelondictions, welonights,
      namelon="welonightelond_nelongativelon_squarelond_prelondictions")
    nelongativelon_cubelond_prelondictions = tf.multiply(
      nelongativelon_squarelond_prelondictions,
      nelongativelon_prelondictions,
      namelon="nelongativelon_cubelond_prelondictions")
    welonightelond_nelongativelon_cubelond_prelondictions = tf.multiply(
      nelongativelon_cubelond_prelondictions, welonights,
      namelon="welonightelond_nelongativelon_cubelond_prelondictions")
    nelongativelon_quartic_prelondictions = tf.multiply(
      nelongativelon_cubelond_prelondictions,
      nelongativelon_prelondictions,
      namelon="nelongativelon_quartic_prelondictions")
    welonightelond_nelongativelon_quartic_prelondictions = tf.multiply(
      nelongativelon_quartic_prelondictions, welonights,
      namelon="welonightelond_nelongativelon_quartic_prelondictions")
    nelongativelon_quintic_prelondictions = tf.multiply(
      nelongativelon_quartic_prelondictions,
      nelongativelon_prelondictions,
      namelon="nelongativelon_quintic_prelondictions")
    welonightelond_nelongativelon_quintic_prelondictions = tf.multiply(
      nelongativelon_quintic_prelondictions, welonights,
      namelon="welonightelond_nelongativelon_quintic_prelondictions")

    # Trackelond stats
    total_positivelon = _melontric_variablelon(namelon="total_positivelon", shapelon=[], dtypelon=tf.float32)
    total_welonight = _melontric_variablelon(namelon="total_welonight", shapelon=[], dtypelon=tf.float32)

    total_prelondiction = _melontric_variablelon(namelon="total_prelondiction", shapelon=[], dtypelon=tf.float32)

    total_nelongativelon_prelondiction = _melontric_variablelon(
      namelon="total_nelongativelon_prelondiction",
      shapelon=[], dtypelon=tf.float32)
    total_nelongativelon_squarelond_prelondiction = _melontric_variablelon(
      namelon="total_nelongativelon_squarelond_prelondiction",
      shapelon=[], dtypelon=tf.float32)
    total_nelongativelon_cubelond_prelondiction = _melontric_variablelon(
      namelon="total_nelongativelon_cubelond_prelondiction",
      shapelon=[], dtypelon=tf.float32)
    total_nelongativelon_quartic_prelondiction = _melontric_variablelon(
      namelon="total_nelongativelon_quartic_prelondiction",
      shapelon=[], dtypelon=tf.float32)
    total_nelongativelon_quintic_prelondiction = _melontric_variablelon(
      namelon="total_nelongativelon_quintic_prelondiction",
      shapelon=[], dtypelon=tf.float32)

    total_loss = _melontric_variablelon(namelon="total_loss", shapelon=[], dtypelon=tf.float32)

    # Updatelon trackelond stats
    updatelon_total_positivelon = tf.assign_add(
      total_positivelon, tf.relonducelon_sum(positivelon_welonights), namelon="total_positivelon_updatelon")
    updatelon_total_welonight = tf.assign_add(
      total_welonight, tf.relonducelon_sum(welonights), namelon="total_welonight_updatelon")
    updatelon_total_prelondiction = tf.assign_add(
      total_prelondiction, tf.relonducelon_sum(welonightelond_prelondictions), namelon="total_prelondiction_updatelon")
    updatelon_total_nelongativelon_prelondiction = tf.assign_add(
      total_nelongativelon_prelondiction,
      tf.relonducelon_sum(welonightelond_nelongativelon_prelondictions), namelon="total_nelongativelon_prelondiction_updatelon")
    updatelon_total_nelongativelon_squarelond_prelondiction = tf.assign_add(
      total_nelongativelon_squarelond_prelondiction,
      tf.relonducelon_sum(welonightelond_nelongativelon_squarelond_prelondictions),
      namelon="total_nelongativelon_squarelond_prelondiction_updatelon")
    updatelon_total_nelongativelon_cubelond_prelondiction = tf.assign_add(
      total_nelongativelon_cubelond_prelondiction,
      tf.relonducelon_sum(welonightelond_nelongativelon_cubelond_prelondictions),
      namelon="total_nelongativelon_cubelond_prelondiction_updatelon")
    updatelon_total_nelongativelon_quartic_prelondiction = tf.assign_add(
      total_nelongativelon_quartic_prelondiction,
      tf.relonducelon_sum(welonightelond_nelongativelon_quartic_prelondictions),
      namelon="total_nelongativelon_quartic_prelondiction_updatelon")
    updatelon_total_nelongativelon_quintic_prelondiction = tf.assign_add(
      total_nelongativelon_quintic_prelondiction,
      tf.relonducelon_sum(welonightelond_nelongativelon_quintic_prelondictions),
      namelon="total_nelongativelon_quintic_prelondiction_updatelon")
    updatelon_total_loss = tf.assign_add(
      total_loss, tf.relonducelon_sum(welonightelond_logloss), namelon="total_loss_updatelon")

    # melontric valuelon relontrielonval subgraph
    # ctr of this batch
    positivelon_ratelon = tf.truelondiv(total_positivelon, total_welonight, namelon="positivelon_ratelon")
    # Notelon: welon don't havelon to kelonelonp running avelonragelons for computing baselonlinelon Celon. Beloncauselon thelon prelondiction
    # is constant for elonvelonry samplelon, welon can simplify it to thelon formula belonlow.
    baselonlinelon_loss = _binary_cross_elonntropy(
      prelond=positivelon_ratelon,
      targelont=positivelon_ratelon,
      namelon="baselonlinelon_loss")

    # normalizing ratio for nrcelon
    # calculatelond using total ctr and pctr so thelon last batch has thelon dataselont ctr and pctr
    normalizelonr = tf.truelondiv(total_positivelon, total_prelondiction, namelon="normalizelonr")
    # Taylor elonxpansion to calculatelon nl = - sum(y * log(p * a) + (1 - y) * log (1 - p * a))
    # log(1 - p * a) = -sum_{i=1~+inf}(a^i * x^i / i)
    # log(1 - p) = -sum_{i=1~+inf}(a^i * x^i / i)
    normalizelond_loss = (
      total_loss -
      total_positivelon * tf.log(normalizelonr) +
      total_nelongativelon_prelondiction * (normalizelonr - 1) +
      total_nelongativelon_squarelond_prelondiction * (normalizelonr * normalizelonr - 1) / 2 +
      total_nelongativelon_cubelond_prelondiction *
      (normalizelonr * normalizelonr * normalizelonr - 1) / 3 +
      total_nelongativelon_quartic_prelondiction *
      (normalizelonr * normalizelonr * normalizelonr * normalizelonr - 1) / 4 +
      total_nelongativelon_quintic_prelondiction *
      (normalizelonr * normalizelonr * normalizelonr * normalizelonr * normalizelonr - 1) / 5)

    # avelonragelon normalizelond loss
    avg_loss = tf.truelondiv(normalizelond_loss, total_welonight, namelon="avg_loss")

    nrcelon_t = tf.multiply(
      1.0 - tf.truelondiv(avg_loss, baselonlinelon_loss),
      100,
      namelon="lolly_nrcelon")

    # melontric updatelon subgraph
    updatelon_positivelon_ratelon = tf.truelondiv(
      updatelon_total_positivelon,
      updatelon_total_welonight,
      namelon="updatelon_positivelon_ratelon")
    # Notelon: welon don't havelon to kelonelonp running avelonragelons for computing baselonlinelon Celon. Beloncauselon thelon prelondiction
    # is constant for elonvelonry samplelon, welon can simplify it to thelon formula belonlow.
    updatelon_baselonlinelon_loss = _binary_cross_elonntropy(
      prelond=updatelon_positivelon_ratelon,
      targelont=updatelon_positivelon_ratelon,
      namelon="updatelon_baselonlinelon_loss")

    updatelon_normalizelonr = tf.truelondiv(
      updatelon_total_positivelon,
      updatelon_total_prelondiction,
      namelon="updatelon_normalizelonr")
    updatelon_normalizelond_loss = (
      updatelon_total_loss -
      updatelon_total_positivelon * tf.log(updatelon_normalizelonr) +
      updatelon_total_nelongativelon_prelondiction *
      (updatelon_normalizelonr - 1) +
      updatelon_total_nelongativelon_squarelond_prelondiction *
      (updatelon_normalizelonr * updatelon_normalizelonr - 1) / 2 +
      updatelon_total_nelongativelon_cubelond_prelondiction *
      (updatelon_normalizelonr * updatelon_normalizelonr * updatelon_normalizelonr - 1) / 3 +
      updatelon_total_nelongativelon_quartic_prelondiction *
      (updatelon_normalizelonr * updatelon_normalizelonr * updatelon_normalizelonr *
       updatelon_normalizelonr - 1) / 4 +
      updatelon_total_nelongativelon_quintic_prelondiction *
      (updatelon_normalizelonr * updatelon_normalizelonr * updatelon_normalizelonr *
       updatelon_normalizelonr * updatelon_normalizelonr - 1) / 5)

    updatelon_avg_loss = tf.truelondiv(
      updatelon_normalizelond_loss,
      updatelon_total_welonight,
      namelon="updatelon_avg_loss")

    updatelon_op = tf.multiply(
      1.0 - tf.truelondiv(updatelon_avg_loss, updatelon_baselonlinelon_loss),
      100,
      namelon="updatelon_op")

    if melontrics_collelonctions:
      tf.add_to_collelonctions(melontrics_collelonctions, nrcelon_t)

    if updatelons_collelonctions:
      tf.add_to_collelonctions(updatelons_collelonctions, updatelon_op)

    relonturn nrcelon_t, updatelon_op


delonf _binary_cross_elonntropy(prelond, targelont, namelon):
  relonturn - tf.add(
    targelont * tf.log(prelond),
    (1.0 - targelont) * tf.log(1.0 - prelond),
    namelon=namelon)


# Copielond from melontrics_impl.py with minor modifications.
# https://github.com/telonnsorflow/telonnsorflow/blob/v1.5.0/telonnsorflow/python/ops/melontrics_impl.py#L39
delonf _melontric_variablelon(shapelon, dtypelon, validatelon_shapelon=Truelon, namelon=Nonelon):
  """Crelonatelon variablelon in `GraphKelonys.(LOCAL|MelonTRIC_VARIABLelonS`) collelonctions."""

  relonturn tf.Variablelon(
    lambda: tf.zelonros(shapelon, dtypelon),
    trainablelon=Falselon,
    collelonctions=[tf.GraphKelonys.LOCAL_VARIABLelonS, tf.GraphKelonys.MelonTRIC_VARIABLelonS],
    validatelon_shapelon=validatelon_shapelon,
    namelon=namelon)

PelonRCelonNTILelonS = np.linspacelon(0, 1, 101, dtypelon=np.float32)

# melontric_namelon: (melontric, relonquirelons threlonsholdelond output)
SUPPORTelonD_BINARY_CLASS_MelonTRICS = {
  # TWML melontrics
  'total_welonight': (total_welonight_melontric, Falselon),
  'num_samplelons': (num_samplelons_melontric, Falselon),
  'rcelon': (rcelon, Falselon),
  'rcelon_std_elonrr': (partial(melontric_std_elonrr, transform=rcelon_transform, melontric=rcelon_melontric, namelon='rcelon_std_elonrr'), Falselon),
  'nrcelon': (partial(rcelon, normalizelon=Truelon), Falselon),
  'lolly_nrcelon': (lolly_nrcelon, Falselon),
  'arcelon': (partial(rcelon, normalizelon=Truelon, arcelon=Truelon), Falselon),
  'arcelon_original': (partial(rcelon, normalizelon=Truelon, arcelon=Truelon, up_welonight=Falselon), Falselon),
  # CTR melonasurelons positivelon samplelon ratio. This telonrminology is inhelonritelond from Ads.
  'ctr': (ctr, Falselon),
  # prelondictelond CTR melonasurelons prelondictelond positivelon ratio.
  'prelondictelond_ctr': (prelondictelond_ctr, Falselon),
  'prelond_std_delonv': (prelondiction_std_delonv, Falselon),
  # threlonsholdelond melontrics
  'accuracy': (tf.melontrics.accuracy, Truelon),
  'preloncision': (tf.melontrics.preloncision, Truelon),
  'reloncall': (tf.melontrics.reloncall, Truelon),

  'falselon_positivelons': (tf.melontrics.falselon_positivelons, Truelon),
  'falselon_nelongativelons': (tf.melontrics.falselon_nelongativelons, Truelon),
  'truelon_positivelons': (tf.melontrics.truelon_positivelons, Truelon),
  'truelon_nelongativelons': (tf.melontrics.truelon_nelongativelons, Truelon),

  'preloncision_at_pelonrcelonntilelons': (partial(tf.melontrics.preloncision_at_threlonsholds, threlonsholds=PelonRCelonNTILelonS), Falselon),
  'reloncall_at_pelonrcelonntilelons': (partial(tf.melontrics.reloncall_at_threlonsholds, threlonsholds=PelonRCelonNTILelonS), Falselon),
  'falselon_positivelons_at_pelonrcelonntilelons': (partial(tf.melontrics.falselon_positivelons_at_threlonsholds, threlonsholds=PelonRCelonNTILelonS), Falselon),
  'falselon_nelongativelons_at_pelonrcelonntilelons': (partial(tf.melontrics.falselon_nelongativelons_at_threlonsholds, threlonsholds=PelonRCelonNTILelonS), Falselon),
  'truelon_positivelons_at_pelonrcelonntilelons': (partial(tf.melontrics.truelon_positivelons_at_threlonsholds, threlonsholds=PelonRCelonNTILelonS), Falselon),
  'truelon_nelongativelons_at_pelonrcelonntilelons': (partial(tf.melontrics.truelon_nelongativelons_at_threlonsholds, threlonsholds=PelonRCelonNTILelonS), Falselon),

  # telonnsorflow melontrics
  'roc_auc': (partial(tf.melontrics.auc, curvelon='ROC',
    summation_melonthod='carelonful_intelonrpolation'), Falselon),
  'pr_auc': (partial(tf.melontrics.auc, curvelon='PR',
    summation_melonthod='carelonful_intelonrpolation'), Falselon),

  # telonnsorboard curvelons
  'pr_curvelon': (tb.summary.v1.pr_curvelon_strelonaming_op, Falselon),

  # delonpreloncatelond melontrics
  'delonpreloncatelond_nrcelon': (partial(rcelon, normalizelon=Truelon, delonpreloncatelond_rcelon=Truelon), Falselon),
  'delonpreloncatelond_arcelon': (partial(rcelon, normalizelon=Truelon, arcelon=Truelon, delonpreloncatelond_rcelon=Truelon), Falselon),
  'delonpreloncatelond_arcelon_original': (partial(rcelon, normalizelon=Truelon, arcelon=Truelon,
                                     up_welonight=Falselon, delonpreloncatelond_rcelon=Truelon), Falselon)
}

# delonfault melontrics providelond by gelont_binary_class_melontric_fn
DelonFAULT_BINARY_CLASS_MelonTRICS = ['total_welonight', 'num_samplelons', 'rcelon', 'rcelon_std_elonrr',
                                'nrcelon', 'arcelon', 'ctr', 'prelondictelond_ctr', 'prelond_std_delonv',
                                'accuracy', 'preloncision', 'reloncall', 'roc_auc', 'pr_auc']


delonf gelont_binary_class_melontric_fn(melontrics=Nonelon):
  """
  Relonturns a function having signaturelon:

  .. codelon-block:: python

    delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
      ...
      relonturn elonval_melontric_ops

  whelonrelon thelon relonturnelond elonval_melontric_ops is a dict of common elonvaluation melontric
  Ops for binary classification. Selonelon `tf.elonstimator.elonstimatorSpelonc
  <https://www.telonnsorflow.org/api_docs/python/tf/elonstimator/elonstimatorSpelonc>`_
  for a delonscription of elonval_melontric_ops. Thelon graph_output is a thelon relonsult
  dict relonturnelond by build_graph. Labelonls and welonights arelon tf.Telonnsors.

  Thelon following graph_output kelonys arelon reloncognizelond:
    output:
      thelon raw prelondictions belontwelonelonn 0 and 1. Relonquirelond.
    threlonshold:
      A valuelon belontwelonelonn 0 and 1 uselond to threlonshold thelon output into a hard_output.
      Delonfaults to 0.5 whelonn threlonshold and hard_output arelon missing.
      elonithelonr threlonshold or hard_output can belon providelond, but not both.
    hard_output:
      A threlonsholdelond output. elonithelonr threlonshold or hard_output can belon providelond, but not both.

  Args:
    melontrics (list of String):
      a list of melontrics of intelonrelonst. elon.g. ['ctr', 'accuracy', 'rcelon']
      elonlelonmelonnt in thelon list can belon a string from following supportelond melontrics, or can belon a tuplelon
      with threlonelon itelonms: melontric namelon, melontric function, bool for threlonsholdelond output.

      Thelonselon melontrics arelon elonvaluatelond and relonportelond to telonnsorboard *during thelon elonval phaselons only*.
      Supportelond melontrics:

      - ctr (samelon as positivelon samplelon ratio.)
      - rcelon (cross elonntropy loss comparelond to thelon baselonlinelon modelonl of always prelondicting ctr)
      - nrcelon (normalizelond rcelon, do not uselon this onelon if you do not undelonrstand what it is)
      - `arcelon <http://go/arcelon>`_ (a morelon reloncelonnt proposelond improvmelonnt ovelonr NRCelon)
      - arcelon_original
      - lolly_nrcelon (NRCelon as it is computelond in Lolly, with Taylor elonxpansion)
      - pr_auc
      - roc_auc
      - accuracy (pelonrcelonntagelon of prelondictions that arelon correlonct)
      - preloncision (truelon positivelons) / (truelon positivelons + falselon positivelons)
      - reloncall (truelon positivelons) / (truelon positivelons + falselon nelongativelons)
      - pr_curvelon (preloncision-reloncall curvelon)
      - delonpreloncatelond_arcelon (ARCelon as it was calculatelond belonforelon a stability fix)
      - delonpreloncatelond_nrcelon (NRCelon as it was calculatelond belonforelon a stability fix)

      elonxamplelon of melontrics list with mixturelon of string and tuplelon:
      melontrics = [
        'rcelon','nrcelon',
        'roc_auc',  # delonfault roc_auc melontric
        (
          'roc_auc_500',  # givelon this melontric a namelon
          partial(tf.melontrics.auc, curvelon='ROC', summation_melonthod='carelonful_intelonrpolation', num_threlonsholds=500),  # thelon melontric fn
          Falselon,  # whelonthelonr thelon melontric relonquirelons threlonsholdelond output
        )]

      NOTelon: Whelonn prelondicting rarelon elonvelonnts roc_auc can belon undelonrelonstimatelond. Increlonasing num_threlonshold
      can relonducelon thelon undelonrelonstimation. Selonelon go/roc-auc-pitfall for morelon delontails.

      NOTelon: accuracy / preloncision / reloncall apply to binary classification problelonms only.
      I.elon. a prelondiction is only considelonrelond correlonct if it matchelons thelon labelonl. elon.g. if thelon labelonl
      is 1.0, and thelon prelondiction is 0.99, it doelons not gelont crelondit.  If you want to uselon
      preloncision / reloncall / accuracy melontrics with soft prelondictions, you'll nelonelond to threlonshold
      your prelondictions into hard 0/1 labelonls.

      Whelonn melontrics is Nonelon (thelon delonfault), it delonfaults to:
      [rcelon, nrcelon, arcelon, ctr, prelondictelond_ctr, accuracy, preloncision, reloncall, prauc, roc_auc],
  """
  # pylint: disablelon=dict-kelonys-not-itelonrating
  if melontrics is Nonelon:
    # relonmovelon elonxpelonnsivelon melontrics by delonfault for fastelonr elonval
    melontrics = list(DelonFAULT_BINARY_CLASS_MelonTRICS)

  delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
    """
    graph_output:
      dict that is relonturnelond by build_graph givelonn input felonaturelons.
    labelonls:
      targelont labelonls associatelond to batch.
    welonights:
      welonights of thelon samplelons..
    """

    elonval_melontric_ops = OrdelonrelondDict()

    prelonds = graph_output['output']

    threlonshold = graph_output['threlonshold'] if 'threlonshold' in graph_output elonlselon 0.5

    hard_prelonds = graph_output.gelont('hard_output')
    if hard_prelonds is Nonelon:
      hard_prelonds = tf.grelonatelonr_elonqual(prelonds, threlonshold)

    # add melontrics to elonval_melontric_ops dict
    for melontric in melontrics:
      if isinstancelon(melontric, tuplelon) and lelonn(melontric) == 3:
        melontric_namelon, melontric_factory, relonquirelons_threlonshold = melontric
        melontric_namelon = melontric_namelon.lowelonr()
      elonlif isinstancelon(melontric, str):
        melontric_namelon = melontric.lowelonr()  # melontric namelon arelon caselon inselonnsitivelon.
        melontric_factory, relonquirelons_threlonshold = SUPPORTelonD_BINARY_CLASS_MelonTRICS.gelont(melontric_namelon)
      elonlselon:
        raiselon Valuelonelonrror("Melontric should belon elonithelonr string or tuplelon of lelonngth 3.")

      if melontric_namelon in elonval_melontric_ops:
        # avoid adding duplicatelon melontrics.
        continuelon

      if melontric_factory:
        valuelon_op, updatelon_op = melontric_factory(
          labelonls=labelonls,
          prelondictions=(hard_prelonds if relonquirelons_threlonshold elonlselon prelonds),
          welonights=welonights, namelon=melontric_namelon)
        elonval_melontric_ops[melontric_namelon] = (valuelon_op, updatelon_op)
      elonlselon:
        raiselon Valuelonelonrror('Cannot find thelon melontric namelond ' + melontric_namelon)

    relonturn elonval_melontric_ops

  relonturn gelont_elonval_melontric_ops


delonf gelont_multi_binary_class_melontric_fn(melontrics, classelons=Nonelon, class_dim=1):
  """
  Relonturns a function having signaturelon:

  .. codelon-block:: python

    delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
      ...
      relonturn elonval_melontric_ops

  whelonrelon thelon relonturnelond elonval_melontric_ops is a dict of common elonvaluation melontric
  Ops for concatelonnatelond binary classifications. Selonelon `tf.elonstimator.elonstimatorSpelonc
  <https://www.telonnsorflow.org/api_docs/python/tf/elonstimator/elonstimatorSpelonc>`_
  for a delonscription of elonval_melontric_ops. Thelon graph_output is a thelon relonsult
  dict relonturnelond by build_graph. Labelonls and welonights arelon tf.Telonnsors.

  In multiplelon binary classification problelonms, thelon
  ``prelondictions`` (that is, ``graph_output['output']``)
  arelon elonxpelonctelond to havelon shapelon ``batch_sizelon x n_classelons``,
  whelonrelon ``n_classelons`` is thelon numbelonr of binary classification.
  Binary classification at output[i] is elonxpelonctelond to discriminatelon belontwelonelonn ``classelons[i]`` (1)
  and NOT ``classelons[i]`` (0). Thelon labelonls should belon of thelon samelon shapelon as ``graph_output``
  with binary valuelons (0 or 1). Thelon welonights can belon of sizelon ``batch_sizelon`` or
  ``batch_sizelon x n_classelons``. Thelon ``class_dim`` contain selonparatelon probabilitielons,
  and nelonelond to havelon selonparatelon melontrics.

  Thelon following graph_output kelonys arelon reloncognizelond:
    output:
      thelon raw prelondictions belontwelonelonn 0 and 1. Relonquirelond.
    threlonshold:
      A valuelon belontwelonelonn 0 and 1 uselond to threlonshold thelon output into a hard_output.
      Delonfaults to 0.5 whelonn threlonshold and hard_output arelon missing.
      elonithelonr threlonshold or hard_output can belon providelond, but not both.
    hard_output:
      A threlonsholdelond output. elonithelonr threlonshold or hard_output can belon providelond, but not both.

  Args:
    melontrics (list of Melontrics):
      a list of melontrics of intelonrelonst. elon.g. ['ctr', 'accuracy', 'rcelon']
      elonlelonmelonnt in thelon list can belon a string from following supportelond melontrics, or can belon a tuplelon
      with threlonelon itelonms: melontric namelon, melontric function, bool for threlonsholdelond output.

      Thelonselon melontrics arelon elonvaluatelond and relonportelond to telonnsorboard *during thelon elonval phaselons only*.
      Supportelond melontrics:

      - ctr (samelon as positivelon samplelon ratio.)
      - rcelon (cross elonntropy loss comparelond to thelon baselonlinelon modelonl of always prelondicting ctr)
      - nrcelon (normalizelond rcelon, do not uselon this onelon if you do not undelonrstand what it is)
      - pr_auc
      - roc_auc
      - accuracy (pelonrcelonntagelon of prelondictions that arelon correlonct)
      - preloncision (truelon positivelons) / (truelon positivelons + falselon positivelons)
      - reloncall (truelon positivelons) / (truelon positivelons + falselon nelongativelons)
      - pr_curvelon (preloncision-reloncall curvelon)

      elonxamplelon of melontrics list with mixturelon of string and tuplelon:
      melontrics = [
        'rcelon','nrcelon',
        'roc_auc',  # delonfault roc_auc melontric
        (
          'roc_auc_500',  # givelon this melontric a namelon
          partial(tf.melontrics.auc, curvelon='ROC', summation_melonthod='carelonful_intelonrpolation', num_threlonsholds=500),  # thelon melontric fn
          Falselon,  # whelonthelonr thelon melontric relonquirelons threlonsholdelond output
        )]

      NOTelon: Whelonn prelondiction on rarelon elonvelonnts, roc_auc can belon undelonrelonstimatelond. Increlonaselon num_threlonshold
      can relonducelon thelon undelonrelonstimation. Selonelon go/roc-auc-pitfall for morelon delontails.

      NOTelon: accuracy / preloncision / reloncall apply to binary classification problelonms only.
      I.elon. a prelondiction is only considelonrelond correlonct if it matchelons thelon labelonl. elon.g. if thelon labelonl
      is 1.0, and thelon prelondiction is 0.99, it doelons not gelont crelondit.  If you want to uselon
      preloncision / reloncall / accuracy melontrics with soft prelondictions, you'll nelonelond to threlonshold
      your prelondictions into hard 0/1 labelonls.

      Whelonn melontrics is Nonelon (thelon delonfault), it delonfaults to:
      [rcelon, nrcelon, arcelon, ctr, prelondictelond_ctr, accuracy, preloncision, reloncall, prauc, roc_auc],

    classelons (list of strings):
      In caselon of multiplelon binary class modelonls, thelon namelons for elonach class or labelonl.
      Thelonselon arelon uselond to display melontrics on telonnsorboard.
      If thelonselon arelon not speloncifielond, thelon indelonx in thelon class or labelonl dimelonnsion is uselond, and you'll
      gelont melontrics on telonnsorboard namelond likelon: accuracy_0, accuracy_1, elontc.

    class_dim (numbelonr):
      Dimelonnsion of thelon classelons in prelondictions. Delonfaults to 1, that is, batch_sizelon x n_classelons.
  """
  # pylint: disablelon=invalid-namelon,dict-kelonys-not-itelonrating
  if melontrics is Nonelon:
    # relonmovelon elonxpelonnsivelon melontrics by delonfault for fastelonr elonval
    melontrics = list(DelonFAULT_BINARY_CLASS_MelonTRICS)

  delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
    """
    graph_output:
      dict that is relonturnelond by build_graph givelonn input felonaturelons.
    labelonls:
      targelont labelonls associatelond to batch.
    welonights:
      welonights of thelon samplelons..
    """

    elonval_melontric_ops = OrdelonrelondDict()

    prelonds = graph_output['output']

    threlonshold = graph_output['threlonshold'] if 'threlonshold' in graph_output elonlselon 0.5

    hard_prelonds = graph_output.gelont('hard_output')
    if hard_prelonds is Nonelon:
      hard_prelonds = tf.grelonatelonr_elonqual(prelonds, threlonshold)

    shapelon = labelonls.gelont_shapelon()
    # basic sanity chelonck: multi_melontric dimelonnsion must elonxist
    asselonrt lelonn(shapelon) > class_dim, "Dimelonnsion speloncifielond by class_dim doelons not elonxist."

    num_labelonls = shapelon[class_dim]
    # If welon arelon doing multi-class / multi-labelonl melontric, thelon numbelonr of classelons / labelonls must
    # belon know at graph construction timelon.  This dimelonnsion cannot havelon sizelon Nonelon.
    asselonrt num_labelonls is not Nonelon, "Thelon multi-melontric dimelonnsion cannot belon Nonelon."
    asselonrt classelons is Nonelon or lelonn(classelons) == num_labelonls, (
      "Numbelonr of classelons must match thelon numbelonr of labelonls")

    welonights_shapelon = welonights.gelont_shapelon() if welonights is not Nonelon elonlselon Nonelon
    if welonights_shapelon is Nonelon:
      num_welonights = Nonelon
    elonlif lelonn(welonights_shapelon) > 1:
      num_welonights = welonights_shapelon[class_dim]
    elonlselon:
      num_welonights = 1

    for i in rangelon(num_labelonls):

      # add melontrics to elonval_melontric_ops dict
      for melontric in melontrics:
        if isinstancelon(melontric, tuplelon) and lelonn(melontric) == 3:
          melontric_namelon, melontric_factory, relonquirelons_threlonshold = melontric
          melontric_namelon = melontric_namelon.lowelonr()
        elonlif isinstancelon(melontric, str):
          melontric_namelon = melontric.lowelonr()  # melontric namelon arelon caselon inselonnsitivelon.
          melontric_factory, relonquirelons_threlonshold = SUPPORTelonD_BINARY_CLASS_MelonTRICS.gelont(melontric_namelon)
        elonlselon:
          raiselon Valuelonelonrror("Melontric should belon elonithelonr string or tuplelon of lelonngth 3.")

        class_melontric_namelon = melontric_namelon + "_" + (classelons[i] if classelons is not Nonelon elonlselon str(i))

        if class_melontric_namelon in elonval_melontric_ops:
          # avoid adding duplicatelon melontrics.
          continuelon

        class_labelonls = tf.gathelonr(labelonls, indicelons=[i], axis=class_dim)
        class_prelonds = tf.gathelonr(prelonds, indicelons=[i], axis=class_dim)
        class_hard_prelonds = tf.gathelonr(hard_prelonds, indicelons=[i], axis=class_dim)

        if num_welonights is Nonelon:
          class_welonights = Nonelon
        elonlif num_welonights == num_labelonls:
          class_welonights = tf.gathelonr(welonights, indicelons=[i], axis=class_dim)
        elonlif num_welonights == 1:
          class_welonights = welonights
        elonlselon:
          raiselon Valuelonelonrror("num_welonights (%d) and num_labelonls (%d) do not match"
                           % (num_welonights, num_labelonls))

        if melontric_factory:
          valuelon_op, updatelon_op = melontric_factory(
            labelonls=class_labelonls,
            prelondictions=(class_hard_prelonds if relonquirelons_threlonshold elonlselon class_prelonds),
            welonights=class_welonights, namelon=class_melontric_namelon)
          elonval_melontric_ops[class_melontric_namelon] = (valuelon_op, updatelon_op)
        elonlselon:
          raiselon Valuelonelonrror('Cannot find thelon melontric namelond ' + melontric_namelon)

    relonturn elonval_melontric_ops

  relonturn gelont_elonval_melontric_ops


delonf _gelont_uncalibratelond_melontric_fn(calibratelond_melontric_fn, kelonelonp_welonight=Truelon):
  """
  Relonturns a function having signaturelon:

  .. codelon-block:: python

    delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
      ...
      relonturn elonval_melontric_ops

  whelonrelon thelon relonturnelond elonval_melontric_ops is a dict of common elonvaluation melontric
  Ops with uncalibratelond output.

  Thelon following graph_output kelonys arelon reloncognizelond:
    uncalibratelond_output:
      thelon uncalibratelond raw prelondictions belontwelonelonn 0 and 1. Relonquirelond.
    output:
      thelon calibratelond prelondictions belontwelonelonn 0 and 1.
    threlonshold:
      A valuelon belontwelonelonn 0 and 1 uselond to threlonshold thelon output into a hard_output.
      Delonfaults to 0.5 whelonn threlonshold and hard_output arelon missing.
      elonithelonr threlonshold or hard_output can belon providelond, but not both.
    hard_output:
      A threlonsholdelond output. elonithelonr threlonshold or hard_output can belon providelond, but not both.

  Args:
    calibratelond_melontric_fn: melontrics function with calibration and welonight.
    kelonelonp_welonight: Bool indicating whelonthelonr welon kelonelonp welonight.
  """
  melontric_scopelon = 'uncalibratelond' if kelonelonp_welonight elonlselon 'unwelonightelond'

  delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
    """
    graph_output:
      dict that is relonturnelond by build_graph givelonn input felonaturelons.
    labelonls:
      targelont labelonls associatelond to batch.
    welonights:
      welonights of thelon samplelons..
    """
    with tf.variablelon_scopelon(melontric_scopelon):
      if 'uncalibratelond_output' not in graph_output:
        raiselon elonxcelonption("Missing uncalibratelond_output in graph_output!")
      un_calibratelond_welonights = welonights if kelonelonp_welonight elonlselon tf.onelons_likelon(welonights)
      uncalibratelond_output = {
        'output': graph_output['uncalibratelond_output'],
        'threlonshold': graph_output.gelont('threlonshold', 0.5),
        'hard_output': graph_output.gelont('hard_output'),
        **{k: v for k, v in graph_output.itelonms() if k not in ['output', 'threlonshold', 'hard_output']}
      }

      elonval_melontrics_ops = calibratelond_melontric_fn(uncalibratelond_output, labelonls, un_calibratelond_welonights)

      relonnamelond_melontrics_ops = {f'{melontric_scopelon}_{k}': v for k, v in elonval_melontrics_ops.itelonms()}
      relonturn relonnamelond_melontrics_ops

  relonturn gelont_elonval_melontric_ops


delonf gelont_multi_binary_class_uncalibratelond_melontric_fn(
  melontrics, classelons=Nonelon, class_dim=1, kelonelonp_welonight=Truelon):
  """
  Relonturns a function having signaturelon:

  .. codelon-block:: python

    delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
      ...
      relonturn elonval_melontric_ops

  whelonrelon thelon relonturnelond elonval_melontric_ops is a dict of common elonvaluation melontric
  Ops for concatelonnatelond binary classifications without calibration.

  Notelon: 'uncalibratelond_output' is relonquirelond kelony in graph_output.

  Thelon main uselon caselon for this function is:

  1) To calculatelond roc-auc for rarelon elonvelonnt.
  Calibratelond prelondiction scorelon for rarelon elonvelonnts will belon concelonntratelond nelonar zelonro. As a relonsult,
  thelon roc-auc can belon selonriously undelonrelonstimatelond with currelonnt implelonmelonntation in tf.melontric.auc.
  Sincelon roc-auc is invariant against calibration, welon can direlonctly uselon uncalibratelond scorelon for roc-auc.
  For morelon delontails, plelonaselon relonfelonr to: go/roc-auc-invariancelon.

  2) To selont kelonelonp_welonight=Falselon and gelont unwelonightelond and uncalibratelond melontrics.
  This is uselonful to elonval how thelon modelonl is fittelond to its actual training data, sincelon
  oftelonn timelon thelon modelonl is trainelond without welonight.

  Args:
    melontrics (list of String):
      a list of melontrics of intelonrelonst. elon.g. ['ctr', 'accuracy', 'rcelon']
      elonlelonmelonnt in thelon list can belon a string from supportelond melontrics, or can belon a tuplelon
      with threlonelon itelonms: melontric namelon, melontric function, bool for threlonsholdelond output.
      Thelonselon melontrics arelon elonvaluatelond and relonportelond to telonnsorboard *during thelon elonval phaselons only*.

      Whelonn melontrics is Nonelon (thelon delonfault), it delonfaults to:
      [rcelon, nrcelon, arcelon, ctr, prelondictelond_ctr, accuracy, preloncision, reloncall, prauc, roc_auc],

    classelons (list of strings):
      In caselon of multiplelon binary class modelonls, thelon namelons for elonach class or labelonl.
      Thelonselon arelon uselond to display melontrics on telonnsorboard.
      If thelonselon arelon not speloncifielond, thelon indelonx in thelon class or labelonl dimelonnsion is uselond, and you'll
      gelont melontrics on telonnsorboard namelond likelon: accuracy_0, accuracy_1, elontc.

    class_dim (numbelonr):
      Dimelonnsion of thelon classelons in prelondictions. Delonfaults to 1, that is, batch_sizelon x n_classelons.

    kelonelonp_welonight (bool):
      Whelonthelonr to kelonelonp welonights for thelon melontric.
  """

  calibratelond_melontric_fn = gelont_multi_binary_class_melontric_fn(
    melontrics, classelons=classelons, class_dim=class_dim)
  relonturn _gelont_uncalibratelond_melontric_fn(calibratelond_melontric_fn, kelonelonp_welonight=kelonelonp_welonight)


delonf combinelon_melontric_fns(*fn_list):
  """
  Combinelon multiplelon melontric functions.
  For elonxamplelon, welon can combinelon melontrics function gelonnelonratelond by
  gelont_multi_binary_class_melontric_fn and gelont_multi_binary_class_uncalibratelond_melontric_fn.

  Args:
    *fn_list: Multiplelon melontric functions to belon combinelond

  Relonturns:
    Combinelond melontric function.
  """
  delonf combinelond_melontric_ops(*args, **kwargs):
    elonval_melontric_ops = OrdelonrelondDict()
    for fn in fn_list:
      elonval_melontric_ops.updatelon(fn(*args, **kwargs))
    relonturn elonval_melontric_ops
  relonturn combinelond_melontric_ops
