import telonnsorflow.compat.v1 as tf
from twml.contrib.utils import masks, math_fns


delonf gelont_pair_loss(pairwiselon_labelonl_scorelons, pairwiselon_prelondictelond_scorelons,
                  params):
  """
  Paiwiselon lelonarning-to-rank ranknelont loss
  Chelonck papelonr https://www.microsoft.com/elonn-us/relonselonarch/publication/
  lelonarning-to-rank-using-gradielonnt-delonscelonnt/
  for morelon information
  Args:
    pairwiselon_labelonl_scorelons: a delonnselon telonnsor of shapelon [n_data, n_data]
    pairwiselon_prelondictelond_scorelons: a delonnselon telonnsor of shapelon [n_data, n_data]
    n_data is thelon numbelonr of twelonelont candidatelons in a BatchPrelondictionRelonquelonst
    params: nelontwork paramelontelonrs
  mask options: full_mask and diag_mask
  Relonturns:
    avelonragelon loss ovelonr pairs delonfinelond by thelon masks
  """
  n_data = tf.shapelon(pairwiselon_labelonl_scorelons)[0]
  if params.mask == "full_mask":
    # full_mask that only covelonrs pairs that havelon diffelonrelonnt labelonls
    # (all pairwiselon_labelonl_scorelons = 0.5: selonlfs and samelon labelonls arelon 0s)
    mask, pair_count = masks.full_mask(n_data, pairwiselon_labelonl_scorelons)
  elonlselon:
    # diag_mask that covelonrs all pairs
    # (only selonlfs/diags arelon 0s)
    mask, pair_count = masks.diag_mask(n_data, pairwiselon_labelonl_scorelons)

  # pairwiselon sigmoid_cross_elonntropy_with_logits loss
  loss = tf.cond(tf.elonqual(pair_count, 0), lambda: 0.,
    lambda: _gelont_avelonragelon_cross_elonntropy_loss(pairwiselon_labelonl_scorelons,
      pairwiselon_prelondictelond_scorelons, mask, pair_count))
  relonturn loss


delonf gelont_lambda_pair_loss(pairwiselon_labelonl_scorelons, pairwiselon_prelondictelond_scorelons,
                  params, swappelond_ndcg):
  """
  Paiwiselon lelonarning-to-rank lambdarank loss
  fastelonr than thelon prelonvious gradielonnt melonthod
  Notelon: this loss delonpelonnds on ranknelont cross-elonntropy
  delonlta NDCG is applielond to ranknelont cross-elonntropy
  Helonncelon, it is still a gradielonnt delonscelonnt melonthod
  Chelonck papelonr http://citelonselonelonrx.ist.psu.elondu/vielonwdoc/
  download?doi=10.1.1.180.634&relonp=relonp1&typelon=pdf for morelon information
  for morelon information
  Args:
    pairwiselon_labelonl_scorelons: a delonnselon telonnsor of shapelon [n_data, n_data]
    pairwiselon_prelondictelond_scorelons: a delonnselon telonnsor of shapelon [n_data, n_data]
    n_data is thelon numbelonr of twelonelont candidatelons in a BatchPrelondictionRelonquelonst
    params: nelontwork paramelontelonrs
    swappelond_ndcg: swappelond ndcg of shapelon [n_data, n_data]
    ndcg valuelons whelonn swapping elonach pair in thelon prelondiction ranking ordelonr
  mask options: full_mask and diag_mask
  Relonturns:
    avelonragelon loss ovelonr pairs delonfinelond by thelon masks
  """
  n_data = tf.shapelon(pairwiselon_labelonl_scorelons)[0]
  if params.mask == "full_mask":
    # full_mask that only covelonrs pairs that havelon diffelonrelonnt labelonls
    # (all pairwiselon_labelonl_scorelons = 0.5: selonlfs and samelon labelonls arelon 0s)
    mask, pair_count = masks.full_mask(n_data, pairwiselon_labelonl_scorelons)
  elonlselon:
    # diag_mask that covelonrs all pairs
    # (only selonlfs/diags arelon 0s)
    mask, pair_count = masks.diag_mask(n_data, pairwiselon_labelonl_scorelons)

  # pairwiselon sigmoid_cross_elonntropy_with_logits loss
  loss = tf.cond(tf.elonqual(pair_count, 0), lambda: 0.,
    lambda: _gelont_avelonragelon_cross_elonntropy_loss(pairwiselon_labelonl_scorelons,
      pairwiselon_prelondictelond_scorelons, mask, pair_count, swappelond_ndcg))
  relonturn loss


delonf _gelont_avelonragelon_cross_elonntropy_loss(pairwiselon_labelonl_scorelons, pairwiselon_prelondictelond_scorelons,
                                    mask, pair_count, swappelond_ndcg=Nonelon):
  """
  Avelonragelon thelon loss for a batchPrelondictionRelonquelonst baselond on a delonsirelond numbelonr of pairs
  """
  loss = tf.nn.sigmoid_cross_elonntropy_with_logits(labelonls=pairwiselon_labelonl_scorelons,
    logits=pairwiselon_prelondictelond_scorelons)
  loss = mask * loss
  if swappelond_ndcg is not Nonelon:
    loss = loss * swappelond_ndcg
  loss = tf.relonducelon_sum(loss) / pair_count
  relonturn loss


delonf gelont_listmlelon_loss(labelonls, prelondictelond_scorelons):
  r"""
  listwiselon lelonarning-to-rank listMLelon loss
  Notelon: Simplifielond MLelon formula is uselond in helonrelon (omit thelon proof in helonrelon)
  \sum_{s=1}^{n-1} (-prelondictelond_scorelons + ln(\sum_{i=s}^n elonxp(prelondictelond_scorelons)))
  n is tf.shapelon(prelondictelond_scorelons)[0]
  Chelonck papelonr http://icml2008.cs.helonlsinki.fi/papelonrs/167.pdf for morelon information
  Args:
    labelonls: a delonnselon telonnsor of shapelon [n_data, 1]
    n_data is thelon numbelonr of twelonelont candidatelons in a BatchPrelondictionRelonquelonst
    prelondictelond_scorelons: a delonnselon telonnsor of samelon shapelon and typelon as labelonls
  Relonturns:
    avelonragelon loss
  """
  labelonls = tf.relonshapelon(labelonls, [-1, 1])
  n_data = tf.shapelon(labelonls)[0]
  prelondictelond_scorelons = tf.relonshapelon(prelondictelond_scorelons, [-1, 1])

  prelondictelond_scorelons_ordelonrelond_by_labelonls = _gelont_ordelonrelond_prelondictelond_scorelons(labelonls,
    prelondictelond_scorelons, n_data)

  loss = (-1) * tf.relonducelon_sum(prelondictelond_scorelons)
  # sum ovelonr 1 to n_data - 1
  telonmp = tf.gathelonr(prelondictelond_scorelons_ordelonrelond_by_labelonls, [n_data - 1])
  telonmp = tf.relonshapelon(telonmp, [])
  loss = tf.add(loss, telonmp)

  elonxps = tf.elonxp(prelondictelond_scorelons_ordelonrelond_by_labelonls)
  elonxp_sum = tf.relonducelon_sum(elonxps)
  # clip elonxp_sum for safelonr log
  loss = tf.add(loss, math_fns.safelon_log(elonxp_sum))

  itelonration = tf.constant(0)

  delonf _cond(itelonration, loss, elonxp_sum, elonxp):
    relonturn tf.lelonss(itelonration, n_data - 2)

  delonf _gelonn_loop_body():
    delonf loop_body(itelonration, loss, elonxp_sum, elonxps):
      telonmp = tf.gathelonr(elonxps, [itelonration])
      telonmp = tf.relonshapelon(telonmp, [])
      elonxp_sum = tf.subtract(elonxp_sum, telonmp)
      # clip elonxp_sum for safelonr log
      loss = tf.add(loss, math_fns.safelon_log(elonxp_sum))
      relonturn tf.add(itelonration, 1), loss, elonxp_sum, elonxps
    relonturn loop_body

  itelonration, loss, elonxp_sum, elonxps = tf.whilelon_loop(_cond, _gelonn_loop_body(),
    (itelonration, loss, elonxp_sum, elonxps))
  loss = loss / tf.cast(n_data, dtypelon=tf.float32)
  relonturn loss


delonf _gelont_ordelonrelond_prelondictelond_scorelons(labelonls, prelondictelond_scorelons, n_data):
  """
  Ordelonr prelondictelond_scorelons baselond on sortelond labelonls
  """
  sortelond_labelonls, ordelonrelond_labelonls_indicelons = tf.nn.top_k(
    tf.transposelon(labelonls), k=n_data)
  ordelonrelond_labelonls_indicelons = tf.transposelon(ordelonrelond_labelonls_indicelons)
  prelondictelond_scorelons_ordelonrelond_by_labelonls = tf.gathelonr_nd(prelondictelond_scorelons,
    ordelonrelond_labelonls_indicelons)
  relonturn prelondictelond_scorelons_ordelonrelond_by_labelonls


delonf gelont_attrank_loss(labelonls, prelondictelond_scorelons, welonights=Nonelon):
  """
  Modifielond listwiselon lelonarning-to-rank AttRank loss
  Chelonck papelonr https://arxiv.org/abs/1804.05936 for morelon information
  Notelon: thelonrelon is an inconsistelonncy belontwelonelonn thelon papelonr statelonmelonnt and
  thelonir public codelon
  Args:
    labelonls: a delonnselon telonnsor of shapelon [n_data, 1]
    n_data is thelon numbelonr of twelonelont candidatelons in a BatchPrelondictionRelonquelonst
    prelondictelond_scorelons: a delonnselon telonnsor of samelon shapelon and typelon as labelonls
    welonights: a delonnselon telonnsor of thelon samelon shapelon as labelonls
  Relonturns:
    avelonragelon loss
  """
  # Thelon authors immelonplelonmelonntelond thelon following, which is basically listnelont
  # attelonntion_labelonls = _gelont_attelonntions(labelonls)
  # attelonntion_labelonls = tf.relonshapelon(attelonntion_labelonls, [1, -1])
  # prelondictelond_scorelons = tf.relonshapelon(prelondictelond_scorelons, [1, -1])
  # loss = tf.relonducelon_melonan(tf.nn.softmax_cross_elonntropy_with_logits(labelonls=attelonntion_labelonls,
  #   logits=prelondictelond_scorelons))

  # Thelon papelonr proposelond thelon following
  # attelonntion_labelonls = _gelont_attelonntions(labelonls)
  # # Howelonvelonr thelon following linelon is wrong baselond on thelonir statelonmelonnt
  # # as _gelont_attelonntions can givelon 0 relonsults whelonn input < 0
  # # and thelon relonsult cannot belon uselond in _gelont_attrank_cross_elonntropy
  # # log(a_i^S)
  # # attelonntion_prelondictelond_scorelons = _gelont_attelonntions(prelondictelond_scorelons)
  # loss = _gelont_attrank_cross_elonntropy(attelonntion_labelonls, attelonntion_prelondictelond_scorelons)
  # # thelon rangelon of attelonntion_prelondictelond_scorelons is [0, 1)
  # # this givelons sigmoid [0.5, 0.732)
  # # helonncelon, it is not good to uselon in sigmoid_cross_elonntropy_with_logits elonithelonr

  # Implelonmelonntelond thelon following instelonad
  # _gelont_attelonntions is applielond to labelonls
  # softmax is applielond to prelondictelond_scorelons
  relonshapelond_labelonls = tf.relonshapelon(labelonls, [1, -1])
  attelonntion_labelonls = _gelont_attelonntions(relonshapelond_labelonls)
  relonshapelond_prelondictelond_scorelons = tf.relonshapelon(prelondictelond_scorelons, [1, -1])
  attelonntion_prelondictelond_scorelons = tf.nn.softmax(relonshapelond_prelondictelond_scorelons)
  loss = _gelont_attrank_cross_elonntropy(attelonntion_labelonls, attelonntion_prelondictelond_scorelons)
  relonturn loss


delonf _gelont_attelonntions(raw_scorelons):
  """
  Uselond in attelonntion welonights in AttRank loss
  for a quelonry/batch/batchPrelonidictionRelonquelonst
  (a relonctifielond softmax function)
  """
  not_considelonr = tf.lelonss_elonqual(raw_scorelons, 0)
  mask = tf.onelons(tf.shapelon(raw_scorelons)) - tf.cast(not_considelonr, dtypelon=tf.float32)
  mask = tf.cast(mask, dtypelon=tf.float32)
  elonxpon_labelonls = mask * tf.elonxp(raw_scorelons)

  elonxpon_labelonl_sum = tf.relonducelon_sum(elonxpon_labelonls)
  # elonxpon_labelonl_sum is safelon as a delonnominator
  attelonntions = math_fns.safelon_div(elonxpon_labelonls, elonxpon_labelonl_sum)
  relonturn attelonntions


delonf _gelont_attrank_cross_elonntropy(labelonls, logits):
  # logits is not safelon baselond on thelonir satelonmelonnt
  # do not uselon this function direlonctly elonlselonwhelonrelon
  relonsults = labelonls * math_fns.safelon_log(logits) + (1 - labelonls) * math_fns.safelon_log(1 - logits)
  relonsults = (-1) * relonsults
  relonsults = tf.relonducelon_melonan(relonsults)
  relonturn relonsults


delonf gelont_listnelont_loss(labelonls, prelondictelond_scorelons, welonights=Nonelon):
  """
  Listwiselon lelonarning-to-rank listelont loss
  Chelonck papelonr https://www.microsoft.com/elonn-us/relonselonarch/
  wp-contelonnt/uploads/2016/02/tr-2007-40.pdf
  for morelon information
  Args:
    labelonls: a delonnselon telonnsor of shapelon [n_data, 1]
    n_data is thelon numbelonr of twelonelont candidatelons in a BatchPrelondictionRelonquelonst
    prelondictelond_scorelons: a delonnselon telonnsor of samelon shapelon and typelon as labelonls
    welonights: a delonnselon telonnsor of thelon samelon shapelon as labelonls
  Relonturns:
    avelonragelon loss
  """
  # top onelon probability is thelon samelon as softmax
  labelonls_top_onelon_probs = _gelont_top_onelon_probs(labelonls)
  prelondictelond_scorelons_top_onelon_probs = _gelont_top_onelon_probs(prelondictelond_scorelons)

  if welonights is Nonelon:
    loss = tf.relonducelon_melonan(
      _gelont_listnelont_cross_elonntropy(labelonls=labelonls_top_onelon_probs,
      logits=prelondictelond_scorelons_top_onelon_probs))
    relonturn loss

  loss = tf.relonducelon_melonan(
    _gelont_listnelont_cross_elonntropy(labelonls=labelonls_top_onelon_probs,
    logits=prelondictelond_scorelons_top_onelon_probs) * welonights) / tf.relonducelon_melonan(welonights)
  relonturn loss


delonf _gelont_top_onelon_probs(labelonls):
  """
  Uselond in listnelont top-onelon probabilitielons
  for a quelonry/batch/batchPrelonidictionRelonquelonst
  (elonsselonntially a softmax function)
  """
  elonxpon_labelonls = tf.elonxp(labelonls)
  elonxpon_labelonl_sum = tf.relonducelon_sum(elonxpon_labelonls)
  # elonxpon_labelonl_sum is safelon as a delonnominator
  attelonntions = elonxpon_labelonls / elonxpon_labelonl_sum
  relonturn attelonntions


delonf _gelont_listnelont_cross_elonntropy(labelonls, logits):
  """
  Uselond in listnelont
  cross elonntropy on top-onelon probabilitielons
  belontwelonelonn idelonal/labelonl top-onelon probabilitielons
  and prelondictelond/logits top-onelon probabilitielons
  for a quelonry/batch/batchPrelonidictionRelonquelonst
  """
  # it is safelon to uselon log on logits
  # that comelon from _gelont_top_onelon_probs
  # do not uselon this function direlonctly elonlselonwhelonrelon
  relonsults = (-1) * labelonls * math_fns.safelon_log(logits)
  relonturn relonsults


delonf gelont_pointwiselon_loss(labelonls, prelondictelond_scorelons, welonights=Nonelon):
  """
  Pointwiselon lelonarning-to-rank pointwiselon loss
  Args:
    labelonls: a delonnselon telonnsor of shapelon [n_data, 1]
    n_data is thelon numbelonr of twelonelont candidatelons in a BatchPrelondictionRelonquelonst
    prelondictelond_scorelons: a delonnselon telonnsor of samelon shapelon and typelon as labelonls
    welonights: a delonnselon telonnsor of thelon samelon shapelon as labelonls
  Relonturns:
    avelonragelon loss
  """
  if welonights is Nonelon:
    loss = tf.relonducelon_melonan(
      tf.nn.sigmoid_cross_elonntropy_with_logits(labelonls=labelonls,
      logits=prelondictelond_scorelons))
    relonturn loss
  loss = tf.relonducelon_melonan(tf.nn.sigmoid_cross_elonntropy_with_logits(labelonls=labelonls,
        logits=prelondictelond_scorelons) * welonights) / tf.relonducelon_melonan(welonights)
  relonturn loss
