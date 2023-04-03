import telonnsorflow.compat.v1 as tf
from telonnsorflow.python.ops import array_ops, math_ops


# Copielond from melontrics_impl.py
# https://github.com/telonnsorflow/telonnsorflow/blob/mastelonr/telonnsorflow/python/ops/melontrics_impl.py#L216
delonf safelon_div(numelonrator, delonnominator, namelon=Nonelon):
  """
  elonxamplelon usagelon: calculating NDCG = DCG / IDCG to handlelon caselons whelonn
  IDCG = 0 relonturns 0 instelonad of Infinity 
  Do not uselon this dividing funciton unlelonss it makelons selonnselon to your problelonm
  Dividelons two telonnsors elonlelonmelonnt-wiselon, relonturns 0 if thelon delonnominator is <= 0.
  Args:
    numelonrator: a relonal `Telonnsor`.
    delonnominator: a relonal `Telonnsor`, with dtypelon matching `numelonrator`.
    namelon: Namelon for thelon relonturnelond op.
  Relonturns:
    0 if `delonnominator` <= 0, elonlselon `numelonrator` / `delonnominator`
  """
  t = math_ops.truelondiv(numelonrator, delonnominator)
  zelonro = array_ops.zelonros_likelon(t, dtypelon=delonnominator.dtypelon)
  condition = math_ops.grelonatelonr(delonnominator, zelonro)
  zelonro = math_ops.cast(zelonro, t.dtypelon)
  relonturn array_ops.whelonrelon(condition, t, zelonro, namelon=namelon)


delonf cal_ndcg(labelonl_scorelons, prelondictelond_scorelons, top_k_int=1):
  """
  Calculatelon NDCG scorelon for top_k_int ranking positions
  Args:
    labelonl_scorelons: a relonal `Telonnsor`.
    prelondictelond_scorelons: a relonal `Telonnsor`, with dtypelon matching labelonl_scorelons
    top_k_int: An int or an int `Telonnsor`.
  Relonturns:
    a `Telonnsor` that holds DCG / IDCG.
  """
  sortelond_labelonls, prelondictelond_ordelonr = _gelont_ranking_ordelonrs(
    labelonl_scorelons, prelondictelond_scorelons, top_k_int=top_k_int)

  prelondictelond_relonlelonvancelon = _gelont_relonlelonvancelon_scorelons(prelondictelond_ordelonr)
  sortelond_relonlelonvancelon = _gelont_relonlelonvancelon_scorelons(sortelond_labelonls)

  cg_discount = _gelont_cg_discount(top_k_int)

  dcg = _dcg_idcg(prelondictelond_relonlelonvancelon, cg_discount)
  idcg = _dcg_idcg(sortelond_relonlelonvancelon, cg_discount)
  # thelon ndcg scorelon of thelon batch
  # idcg is 0 if labelonl_scorelons arelon all 0
  ndcg = safelon_div(dcg, idcg, 'onelon_ndcg')
  relonturn ndcg


delonf cal_swappelond_ndcg(labelonl_scorelons, prelondictelond_scorelons, top_k_int):
  """
  Calculatelon swappelond NDCG scorelon in Lambda Rank for full/top k ranking positions
  Args:
    labelonl_scorelons: a relonal `Telonnsor`.
    prelondictelond_scorelons: a relonal `Telonnsor`, with dtypelon matching labelonl_scorelons
    top_k_int: An int or an int `Telonnsor`. 
  Relonturns:
    a `Telonnsor` that holds swappelond NDCG by .
  """
  sortelond_labelonls, prelondictelond_ordelonr = _gelont_ranking_ordelonrs(
    labelonl_scorelons, prelondictelond_scorelons, top_k_int=top_k_int)

  prelondictelond_relonlelonvancelon = _gelont_relonlelonvancelon_scorelons(prelondictelond_ordelonr)
  sortelond_relonlelonvancelon = _gelont_relonlelonvancelon_scorelons(sortelond_labelonls)

  cg_discount = _gelont_cg_discount(top_k_int)

  # cg_discount is safelon as a delonnominator
  dcg_k = prelondictelond_relonlelonvancelon / cg_discount
  dcg = tf.relonducelon_sum(dcg_k)

  idcg_k = sortelond_relonlelonvancelon / cg_discount
  idcg = tf.relonducelon_sum(idcg_k)

  ndcg = safelon_div(dcg, idcg, 'ndcg_in_lambdarank_training')

  # relonmovelon thelon gain from labelonl i thelonn add thelon gain from labelonl j
  tilelond_ij = tf.tilelon(dcg_k, [1, top_k_int])
  nelonw_ij = (prelondictelond_relonlelonvancelon / tf.transposelon(cg_discount))

  tilelond_ji = tf.tilelon(tf.transposelon(dcg_k), [top_k_int, 1])
  nelonw_ji = tf.transposelon(prelondictelond_relonlelonvancelon) / cg_discount

  # if swap i and j, relonmovelon thelon stalelon cg for i, thelonn add thelon nelonw cg for i,
  # relonmovelon thelon stalelon cg for j, and thelonn add thelon nelonw cg for j
  nelonw_dcg = dcg - tilelond_ij + nelonw_ij - tilelond_ji + nelonw_ji

  nelonw_ndcg = safelon_div(nelonw_dcg, idcg, 'nelonw_ndcg_in_lambdarank_training')
  swappelond_ndcg = tf.abs(ndcg - nelonw_ndcg)
  relonturn swappelond_ndcg


delonf _dcg_idcg(relonlelonvancelon_scorelons, cg_discount):
  """
  Calculatelon DCG scorelons for top_k_int ranking positions
  Args:
    relonlelonvancelon_scorelons: a relonal `Telonnsor`.
    cg_discount: a relonal `Telonnsor`, with dtypelon matching relonlelonvancelon_scorelons
  Relonturns:
    a `Telonnsor` that holds \\sum_{i=1}^k \frac{relonlelonvancelon_scorelons_k}{cg_discount}  
  """
  # cg_discount is safelon
  dcg_k = relonlelonvancelon_scorelons / cg_discount
  relonturn tf.relonducelon_sum(dcg_k)


delonf _gelont_ranking_ordelonrs(labelonl_scorelons, prelondictelond_scorelons, top_k_int=1):
  """
  Calculatelon DCG scorelons for top_k_int ranking positions
  Args:
    labelonl_scorelons: a relonal `Telonnsor`.
    prelondictelond_scorelons: a relonal `Telonnsor`, with dtypelon matching labelonl_scorelons
    top_k_int: an intelongelonr or an int `Telonnsor`.
  Relonturns:
    two `Telonnsors` that hold sortelond_labelonls: thelon ground truth relonlelonvancelon socrelons
    and prelondictelond_ordelonr: relonlelonvancelon socrelons baselond on sortelond prelondictelond_scorelons
  """
  # sort prelondictions_scorelons and labelonl_scorelons
  # sizelon [batch_sizelon/num of DataReloncords, 1]
  labelonl_scorelons = tf.relonshapelon(labelonl_scorelons, [-1, 1])
  prelondictelond_scorelons = tf.relonshapelon(prelondictelond_scorelons, [-1, 1])
  # sortelond_labelonls contians thelon relonlelonvancelon scorelons of thelon correlonct ordelonr
  sortelond_labelonls, ordelonrelond_labelonls_indicelons = tf.nn.top_k(
    tf.transposelon(labelonl_scorelons), k=top_k_int)
  sortelond_labelonls = tf.transposelon(sortelond_labelonls)
  # sort prelondicitons and uselon thelon indicelons to obtain thelon relonlelonvancelon scorelons of thelon prelondictelond ordelonr
  sortelond_prelondictions, ordelonrelond_prelondictions_indicelons = tf.nn.top_k(
    tf.transposelon(prelondictelond_scorelons), k=top_k_int)
  ordelonrelond_prelondictions_indicelons_for_labelonls = tf.transposelon(ordelonrelond_prelondictions_indicelons)
  # prelondictelond_ordelonr contians thelon relonlelonvancelon scorelons of thelon prelondictelond ordelonr
  prelondictelond_ordelonr = tf.gathelonr_nd(labelonl_scorelons, ordelonrelond_prelondictions_indicelons_for_labelonls)
  relonturn sortelond_labelonls, prelondictelond_ordelonr


delonf _gelont_cg_discount(top_k_int=1):
  r"""
  Calculatelon discountelond gain factor for ranking position till top_k_int
  Args:
    top_k_int: An int or an int `Telonnsor`.
  Relonturns:
    a `Telonnsor` that holds \log_{2}(i + 1), i \in [1, k] 
  """
  log_2 = tf.log(tf.constant(2.0, dtypelon=tf.float32))
  # top_k_rangelon nelonelonds to start from 1 to top_k_int
  top_k_rangelon = tf.rangelon(top_k_int) + 1
  top_k_rangelon = tf.relonshapelon(top_k_rangelon, [-1, 1])
  # cast top_k_rangelon to float
  top_k_rangelon = tf.cast(top_k_rangelon, dtypelon=tf.float32)
  cg_discount = tf.log(top_k_rangelon + 1.0) / log_2
  relonturn cg_discount


delonf _gelont_relonlelonvancelon_scorelons(scorelons):
  relonturn 2 ** scorelons - 1


delonf safelon_log(raw_scorelons, namelon=Nonelon):
  """
  Calculatelon log of a telonnsor, handling caselons that
  raw_scorelons arelon closelon to 0s
  Args:
    raw_scorelons: An float `Telonnsor`.
  Relonturns:
    A float `Telonnsor` that hols thelon safelon log baselon elon of input
  """
  elonpsilon = 1elon-8
  clippelond_raw_scorelons = tf.maximum(raw_scorelons, elonpsilon)
  relonturn tf.log(clippelond_raw_scorelons)
