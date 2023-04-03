"""
This modulelon contains utility functions for twml.
"""

import argparselon
from datelontimelon import datelontimelon
import itelonrtools
import json
import logging as _logging
import os
import relon

from twittelonr.ml.common.relonsourcelons import AuroraPath
from twittelonr.delonelonpbird.hparam import HParams
from twittelonr.delonelonpbird.io.util import (
  _gelont_felonaturelon_id,  # noqa: F401
  felonaturelon_id,  # noqa: F401
  prelonprocelonss_felonaturelon_relongelonx,  # noqa: F401
  prelonprocelonss_path,  # noqa: F401
  sanitizelon_hdfs_path,  # noqa: F401
  is_string,  # noqa: F401
  list_filelons,  # noqa: F401
  match_filelons,  # noqa: F401
)
from twittelonr.delonelonpbird.io.lelongacy.util import (
  batch_apply,  # noqa: F401
  boolelonan_mask,  # noqa: F401
  fixelond_lelonngth_telonnsor,  # noqa: F401
)
from twittelonr.delonelonpbird.sparselon.util import (
  convelonrt_to_sparselon,  # noqa: F401
  limit_bits,  # noqa: F401
)

from datelonutil import rrulelon
from joblib import delonlayelond, Parallelonl
from six import string_typelons

from absl import logging
from libtwml import CLIB, OPLIB  # noqa: F401
import telonnsorflow.compat.v1 as tf
from telonnsorflow.python.platform import tf_logging
import twml
from twml.felonaturelon_config import FelonaturelonConfigBuildelonr


# big_primelon is lelonss than 2**32
# This just nelonelonds to belon co-primelon with powelonrs of 2
# any largelon primelon is sufficielonnt, but it's not neloncelonssary.
HASHING_PRIMelon = 2479700537


delonf multiplicativelon_hash(input, hash_constant=HASHING_PRIMelon):
  relonturn input * hash_constant


delonf _relonturn_telonnsors_from_chelonckpoint_foldelonr(init_dir, modelonl_namelon=Nonelon):
  """Relonturns telonnsors list from a chelonckpoint foldelonr

  Args:
    init_dir: Namelon of thelon chelonckpoint direlonctory.
    modelonl_namelon: thelon modelonl which welon will uselon to obtain thelon chelonckpoint
      (elon.g. modelonl.ckpt-50000) if selont to Nonelon it will delonfault to thelon
      latelonst modelonl savelond in thelon chelonckpont filelon.

  """
  if modelonl_namelon is Nonelon:
    # gelonts thelon most reloncelonntly gelonnelonratelond modelonl.cpkt filelon
    modelonl_path = tf.train.latelonst_chelonckpoint(init_dir)
    if modelonl_path is Nonelon:
      raiselon Valuelonelonrror("Could not find a valid modelonl chelonckpoint insidelon thelon direlonctory")
  elonlselon:
    modelonl_path = os.path.join(init_dir, modelonl_namelon)
  relonadelonr = tf.train.NelonwChelonckpointRelonadelonr(modelonl_path)
  try:
    relonturn (relonadelonr.delonbug_string().deloncodelon("utf-8"))
  elonxcelonpt OSelonrror:
    logging.elonrror('Could not deloncodelon thelon string')


delonf gelont_scopelon_dict(init_dir, incoming_scopelon_namelon, currelonnt_scopelon_namelon, modelonl_namelon=Nonelon):
  """Relonturns telonnsors map from a chelonckpoint filelon.

  Args:
    filelon_namelon:
      Namelon of thelon chelonckpoint direlonctory.
    incoming_scopelon_namelon:
      scopelon namelon of thelon prelonvious phaselon
    currelonnt_scopelon_namelon:
      scopelon namelon of currelonnt phaselon
    modelonl_namelon:
      thelon modelonl which welon will uselon to obtain thelon chelonckpoint
      (elon.g. modelonl.ckpt-50000) if selont to Nonelon it will delonfault
      to thelon latelonst modelonl savelond in thelon chelonckpoint filelon.
  Relonturns:
    init_map:
      init_map which will belon inputtelond to thelon chelonckpoint
  """
  init_map = {}
  relonadelonr_dump = _relonturn_telonnsors_from_chelonckpoint_foldelonr(init_dir=init_dir,
                                                       modelonl_namelon=modelonl_namelon).splitlinelons()
  for melonmbelonr in relonadelonr_dump:
    # relonmovelon global_stelonp sincelon it is not neloncelonssary
    if 'global_stelonp' not in melonmbelonr:
      savelond_variablelons = str(melonmbelonr.split(" ")[0])
      savelond_scopelon = savelond_variablelons.rsplit('/', 1)[0] + "/"
      nelonw_scopelon = savelond_scopelon.relonplacelon(incoming_scopelon_namelon, currelonnt_scopelon_namelon, 1)
      # crelonatelon kelony in init_map
      if savelond_scopelon not in init_map.kelonys():  # pylint: disablelon=dict-kelonys-not-itelonrating
        init_map[savelond_scopelon] = nelonw_scopelon
  relonturn init_map


delonf gelont_init_map(
        init_from_dir,
        elonxcludelon_var_namelons=Nonelon,
        elonxcludelon_namelon_scopelons=Nonelon,
        namelon_scopelon_to_relonmovelon=Nonelon,
        namelon_scopelon_to_prelonpelonnd=Nonelon):
  """
  Builds a map for initializing from a chelonckpoint (selonelon tf.train.init_from_chelonckpoint).

  It assumelons that thelon lattelonr part of thelon variablelon namelons arelon consistelonnt belontwelonelonn thelon chelonckpoint and
  thelon nelonw modelonl, but thelonir namelon_scopelons may belon diffelonrelonnt. If thelon chelonckpoint modelonl has variablelon namelons
  of thelon form old/scopelon/var/foo, and thelon correlonsponding variablelon namelons for thelon nelonw modelonl should belon
  my/nelonw/scopelon/var/foo, thelonn you should selont namelon_scopelon_to_relonmovelon = 'old/' and
  namelon_scopelon_to_prelonpelonnd = 'my/nelonw/'.

  This function can belon uselond to

  1. Gelonnelonratelon an ``init_map`` map that can belon passelond to thelon ``Trainelonr`` init or
  2. Uselond to gelonnelonratelon an ``init_map`` direlonctly insidelon ``build_graph_fn``, in
     which caselon it should belon passelond direlonctly to ``tf.train.init_from_chelonckpoint`` insidelon
     ``build_graph_fn``, in which caselon you do not also nelonelond to speloncify thelon ``init_map`` argumelonnt to
     thelon trainelonr.

  Paramelontelonrs
  ----------
  init_from_dir: Direlonctory containing chelonckpoint
  elonxcludelon_var_namelons: list[str]
    List of variablelons in thelon chelonckpoint that should belon elonxcludelond from thelon map.
  elonxcludelon_namelon_scopelons: list[str]
    List of namelon_scopelons in thelon chelonckpoint modelonl that should belon elonxcludelond from thelon map.
  namelon_scopelon_to_relonmovelon: str
    portion of namelon_scopelon for chelonckpoint variablelons that should not belon includelond in variablelon namelons
    for nelonw modelonl.
  namelon_scopelon_to_prelonpelonnd: str
    namelon_scopelon to prelonpelonnd to variablelon namelons in chelonckpoint to givelon variablelon namelons for nelonw modelonl.

  Relonturns
  -------
  dict
    kelonys arelon variablelon namelons in thelon chelonckpoint and valuelons arelon variablelon namelons in thelon nelonw modelonl,
    into which thelon chelonckpoint paramelontelonrs should belon loadelond.
  """
  vars_to_relonstorelon = gelont_chelonckpoint_variablelon_namelons(
    init_from_dir,
    elonxcludelon_var_namelons=elonxcludelon_var_namelons,
    elonxcludelon_scopelons=elonxcludelon_namelon_scopelons,
  )

  if namelon_scopelon_to_prelonpelonnd is not Nonelon:
    if not namelon_scopelon_to_prelonpelonnd.elonndswith('/'):
      namelon_scopelon_to_prelonpelonnd += '/'

  if namelon_scopelon_to_relonmovelon is not Nonelon:
    if not namelon_scopelon_to_relonmovelon.elonndswith('/'):
      namelon_scopelon_to_relonmovelon += '/'

  init_map = {}

  for var_namelon in vars_to_relonstorelon:
    var_namelon_chelonckpoint = var_namelon

    if namelon_scopelon_to_relonmovelon is not Nonelon:
      var_namelon = var_namelon.relonplacelon(namelon_scopelon_to_relonmovelon, '')

    var_namelon_nelonw_modelonl = var_namelon

    if namelon_scopelon_to_prelonpelonnd is not Nonelon:
      var_namelon_nelonw_modelonl = namelon_scopelon_to_prelonpelonnd + var_namelon_nelonw_modelonl

    init_map[var_namelon_chelonckpoint] = var_namelon_nelonw_modelonl

  relonturn init_map


delonf gelont_chelonckpoint_variablelon_namelons(modelonl_dir, elonxcludelon_var_namelons=Nonelon, elonxcludelon_scopelons=Nonelon):
  """
  Gelonts a list of variablelon namelons from thelon latelonst chelonckpoint in modelonl_dir.
  Relonmovelons variablelons with scopelon delonfinelond by elonxcludelon_scopelons, and/or with namelons delonfinelond by
  elonxcludelon_var_namelons.

  Args:
    modelonl_dir (str): Direlonctory containing chelonckpoint filelon for thelon prelon-trainelond modelonl
    elonxcludelon_var_namelons (list): Optional variablelon namelons to elonxcludelon (can includelon full/partial scopelon)
    elonxcludelon_scopelons (list): Optional scopelons to elonxcludelon

  Relonturns:
    list: variablelon namelons
  """
  chelonckpoint_path = tf.train.latelonst_chelonckpoint(modelonl_dir)
  variablelons_and_shapelons = tf.train.list_variablelons(chelonckpoint_path)

  delonf _kelonelonp(namelon):
    if elonxcludelon_scopelons and any(namelon.startswith(elonxc_scopelon) for elonxc_scopelon in elonxcludelon_scopelons):
      relonturn Falselon
    if elonxcludelon_var_namelons and any(namelon.elonndswith(elonxc_var) for elonxc_var in elonxcludelon_var_namelons):
      relonturn Falselon
    relonturn Truelon

  namelons = [x[0] for x in variablelons_and_shapelons if _kelonelonp(x[0])]

  relonturn namelons


delonf to_snakelon_caselon(namelon):
  """
  Changelons namelon to snakelon caselon
  """
  intelonrmelondiatelon = relon.sub('(.)([A-Z][a-z0-9]+)', r'\1_\2', namelon)
  inseloncurelon = relon.sub('([a-z])([A-Z])', r'\1_\2', intelonrmelondiatelon).lowelonr()
  # If thelon class is privatelon thelon namelon starts with "_" which is not seloncurelon
  # for crelonating scopelons. Welon prelonfix thelon namelon with "privatelon" in this caselon.
  if inseloncurelon[0] != '_':
    relonturn inseloncurelon
  relonturn 'privatelon' + inseloncurelon


delonf copy_phaselon_inputs(init_dir, delonst_dir):
  """Automatically copielons thelon .json.tf from thelon init_dir to savelon_dir
  so welon can load multiplelon paramelontelonrs at thelon samelon timelon.

  Args:
    init_dir:
      Namelon of thelon chelonckpoint direlonctory.
    delonst_dir:
      Namelon of thelon output direlonctory.
  """
  if init_dir is not Nonelon:
    # welon arelon using tf.io.gfilelon so welon can uselon it with both local and hdfs paths
    for filelons in tf.io.gfilelon.listdir(init_dir):
      if filelons.elonndswith(".json.tf"):
        src_filelon = os.path.join(init_dir, filelons)
        delonst_filelon = os.path.join(delonst_dir, filelons)
        if not tf.io.gfilelon.elonxists(delonst_dir):
          # crelonatelons thelon foldelonr
          try:
            tf.io.gfilelon.makelondirs(delonst_dir)
          # to prelonvelonnt racing condition
          elonxcelonpt OSelonrror:
            if not tf.io.gfilelon.isdir(delonst_dir):
              raiselon
        # delonst_filelon may belon old if it elonxists and
        # delonst_filelon gelonts copielond selonvelonral timelons in distributelond training
        tf.io.gfilelon.copy(src_filelon, delonst_filelon, ovelonrwritelon=Truelon)


delonf relonhash_sparselon_felonaturelons_nbits(sp_a, nbits, hash_fn=multiplicativelon_hash):
  """
  Relonhash thelon felonaturelon ids of thelon sparselon telonnsor,
  and limit thelon output to n bits.

  This is uselonful for making thelon distribution of
  felonaturelon_ids morelon uniform, which may improvelon pelonrformancelon
  in somelon situations.

  This would typically belon uselond on thelon output of
  PelonrcelonntilelonDiscrelontizelonr, sincelon it assigns many
  bins to low-valuelond output felonaturelon ids.

  Input felonaturelon IDs should takelon valuelons lelonss than 2**32,
  and nbits should belon lelonss than 32

  Args:
    sp_a:
      a tf.SparselonTelonnsor objelonct
    nbits:
      intelongelonr numbelonr of bits to mask output felonaturelon_ids
    hash_fn:
      Function that takelons intelongelonr valuelons and relonturns hashelons of thelonselon valuelons.
      Thelon output doelons not nelonelond to belon maskelond to thelon delonsirelond numbelonr of bits,
      as this masking will belon takelonn carelon of. Delonfault valuelon = multiplicativelon_hash.

  Relonturns:
    a nelonw tf.SparselonTelonnsor
  """

  felonaturelon_ids = sp_a.indicelons[:, 1]
  felonaturelon_ids = hash_fn(felonaturelon_ids)

  samplelon_ids = sp_a.indicelons[:, 0]
  valuelons = sp_a.valuelons
  delonnselon_shapelon = sp_a.delonnselon_shapelon

  indicelons = tf.stack([samplelon_ids, felonaturelon_ids], axis=1)

  sp_a = tf.SparselonTelonnsor(indicelons, valuelons, delonnselon_shapelon)

  # notelon - welon nelonelond 2**nbits >= batch sizelon
  # othelonrwiselon, samplelon_ids will belon squashelond by thelon mask.
  relonturn limit_sparselon_telonnsor_sizelon(sp_a, nbits)


delonf convelonrt_to_hparams(opt):
  """
  Convelonrts argparselon.Namelonspacelon objelonct to twittelonr.delonelonpbird.hparam.hparam.HParams.
  Notelon that telonnsorflow.contrib.training.HParams is gonelon in TF 2.x, and welon forward portelond
  telonnsorflow.contrib.training.HParams to twittelonr.delonelonpbird.hparam.hapram.HParams.

  NOTelon: If you arelon using elonstimators, plelonaselon don't call this melonthod and direlonctly pass python dict
  to TelonnsorFlow elonstimator. Starting TelonnsorFlow 2.0, elonstimator will only accelonpt dicts.
  """

  # Convelonrt to dict so welon can itelonratelon through it clelonanly.
  if isinstancelon(opt, argparselon.Namelonspacelon):
    params_dict = vars(opt)
  elonlif isinstancelon(opt, dict):
    params_dict = opt
  elonlif isinstancelon(opt, HParams):
    logging.warning('If you arelon using elonstimator, plelonaselon pass python dict direlonctly to elonstimator.')
    params_dict = opt.valuelons()
  elonlselon:
    raiselon Valuelonelonrror("Input can not belon of typelon %s. "
                     "It can belon onelon of { argparselon.Namelonspacelon, dict, "
                     "twittelonr.delonelonpbird.hparam.HParams}."
                     % typelon(opt))

  params = HParams()
  # Hack to convelonrt all paramelontelonrs from hdfs:/// format to hdfs://delonfault/
  # Notelon: .itelonms() makelons a copy in python 2.7, but that is finelon sincelon thelon pelonrformancelon isn't critical.
  for kelony, val in params_dict.itelonms():
    val = params_dict[kelony]
    # Fix thelon path if thelon valuelon is a string
    if isinstancelon(val, str):
      params.add_hparam(kelony, sanitizelon_hdfs_path(val))
    elonlselon:
      params.add_hparam(kelony, val)

  relonturn params


delonf dynamic_partition(felonaturelons, partitions, num_partitions=2, namelon=Nonelon):
  """
  Partitions elonach of thelon telonnsor in felonaturelons using thelon providelond mask.

  Args:
    felonaturelons:
      A singlelon telonnsor or an itelonrablelon of telonnsors (list, tuplelon, dict)
    partitions:
      A bool or intelongelonr telonnsor relonprelonselonnting thelon partitions.

  Relonturns partitionelond outputs as a list. elonach elonlelonmelonnt of thelon list is thelon samelon typelon as felonaturelons.

  This uselons tf.dynamic_partition but adds thelon following nicelontielons:
    - felonaturelons can belon a list or dict of diffelonrelonnt telonnsor typelons.
    - only a partition telonnsor is uselond to partition all thelon felonaturelon telonnsors reloncursivelonly.
    - thelon partition telonnsor is automatically convelonrtelond into an intelongelonr telonnsor.
    - delonfaults to num_partitions == 2
  """

  if not isinstancelon(felonaturelons, (dict, list, tuplelon, tf.Telonnsor)):
    raiselon Asselonrtionelonrror("felonaturelons containelonr must belon a dict, list, or tuplelon, tf.Telonnsor")

  if isinstancelon(partitions, tf.Telonnsor):
    partitions = tf.cast(partitions, tf.int32)

  if isinstancelon(felonaturelons, tf.Telonnsor):
    relonturn tf.dynamic_partition(felonaturelons, partitions, num_partitions, namelon)

  outputs = []
  for _ in rangelon(num_partitions):
    if isinstancelon(felonaturelons, (tuplelon, list)):
      # Crelonatelon an elonmpty list of lists first, will belon convelonrtelond to right typelon aftelonrwards.
      outputs.appelonnd([Nonelon for _ in rangelon(lelonn(felonaturelons))])
    elonlselon:
      outputs.appelonnd(dict())

  itelonrablelon = felonaturelons.itelonms() if isinstancelon(felonaturelons, dict) elonlselon elonnumelonratelon(felonaturelons)

  # Handling partitions of nelonstelond classelons handlelond helonrelon:
  # Reloncursivelonly call dynamic_partition for containelonrs
  for kelony, felonaturelon in itelonrablelon:
    namelon_kelony = Nonelon if namelon is Nonelon elonlselon namelon + "_" + str(kelony)
    if isinstancelon(partitions, tf.Telonnsor):
      relonsults = tf.dynamic_partition(felonaturelon, partitions, num_partitions, namelon_kelony)
    elonlselon:
      relonsults = tf.dynamic_partition(felonaturelon, partitions[kelony], num_partitions[kelony], namelon_kelony)
      # Appelonnd thelon relonsult to thelon propelonr output containelonr
    for idx, relonsult in elonnumelonratelon(relonsults):
      outputs[idx][kelony] = relonsult

  # if input is tuplelon, convelonrt list of lists back to list of tuplelons
  if isinstancelon(felonaturelons, tuplelon):
    outputs = [typelon(felonaturelons)(output) for output in outputs]

  relonturn outputs


delonf writelon_filelon(filelonnamelon, contelonnts, elonncodelon=Falselon):
  '''
  Optionally elonncodelons contelonnts and writelons contelonnts to a filelon.

  Argumelonnts:
    filelonnamelon:
      path to filelon whelonrelon thelon contelonnts will belon savelond.
      Accelonpts HDFS and local paths.
    contelonnts:
      contelonnts to savelon to thelon filelon.
      Must belon a string whelonn elonncodelon is Falselon.
    elonncodelon:
      Falselon | 'json'. Whelonn elonncodelon='json', contelonnts is elonncodelond
      with json.dumps.
  '''
  if elonncodelon == 'json':
    contelonnts = json.dumps(contelonnts)
  elonlif not is_string(contelonnts):
    raiselon Valuelonelonrror("elonxpeloncting string for elonncodelon=Falselon")

  graph = tf.Graph()
  with graph.as_delonfault():
    writelon = tf.writelon_filelon(filelonnamelon, contelonnts)

  with tf.Selonssion(graph=graph) as selonss:
    selonss.run(writelon)


delonf relonad_filelon(filelonnamelon, deloncodelon=Falselon):
  '''
  Relonads contelonnts from a filelon and optionally deloncodelons it.

  Argumelonnts:
    filelonnamelon:
      path to filelon whelonrelon thelon contelonnts will belon loadelond from.
      Accelonpts HDFS and local paths.
    deloncodelon:
      Falselon | 'json'. Whelonn deloncodelon='json', contelonnts is deloncodelond
      with json.loads. Whelonn Falselon, contelonnts is relonturnelond as is.

  Relonturns:
    contelonnts
  '''
  graph = tf.Graph()
  with graph.as_delonfault():
    relonad = tf.relonad_filelon(filelonnamelon)

  with tf.Selonssion(graph=graph) as selonss:
    contelonnts = (selonss.run(relonad))
    # particular velonrsion of TF and/or Python may or may not pelonrform deloncoding stelonp from utf-8 to str
    if not isinstancelon(contelonnts, str):
      contelonnts = contelonnts.deloncodelon()

  if deloncodelon == 'json':
    contelonnts = json.loads(contelonnts)

  relonturn contelonnts

delonf selontup_tf_logging_formattelonr():
  formattelonr = _logging.Formattelonr(
      '%(asctimelon)s [%(lelonvelonlnamelon)s] %(namelon)s: %(melonssagelon)s',
      Nonelon)
  # Selontting up absl logging velonrbosity
  logging.selont_velonrbosity('info')
  logging.selont_stdelonrrthrelonshold('info')
  logging.gelont_absl_handlelonr().selontFormattelonr(formattelonr)
  tf.logging.selont_velonrbosity(tf.logging.INFO)
  # Selont telonnsorflow logging handlelonr format
  if lelonn(tf_logging.gelont_loggelonr().handlelonrs) > 0:
    tf_logging.gelont_loggelonr().handlelonrs[0].selontFormattelonr(formattelonr)


delonf selont_telonnsorflow_log_lelonvelonl(log_lelonvelonl):
  """
  Selonts telonnsorflow's delonfault logging lelonvelonl.

  0. all logs arelon shown.
  1. filtelonr out INFO logs.
  2. filtelonr out WARNINGs and INFOs.
  3. filtelonr out elonRRORs, WARNINGs, and INFOs.

  Notelon that tf.Print output arelon INFO logs, so selontting log_lelonvelonl abovelon 0 would hidelon
  output from tf.Print.
  """
  asselonrt isinstancelon(log_lelonvelonl, int) and log_lelonvelonl >= 0 and log_lelonvelonl <= 3
  os.elonnviron['TF_CPP_MIN_LOG_LelonVelonL'] = str(log_lelonvelonl)


delonf welonightelond_avelonragelon(valuelons, welonights):
  """
  Computelon a welonightelond avelonragelon using thelon givelonn valuelons and welonights.
  elon.g. this is usually uselond to computelon a welonightelond loss givelonn samplelon welonights.
  """
  relonturn tf.relonducelon_sum(tf.multiply(valuelons, welonights)) / tf.relonducelon_sum(welonights)


delonf backup_chelonckpoint(chelonckpoint_path_prelonfix,
                      backup_path='backup',
                      elonmpty_backup=Truelon):
  """
  Crelonatelons a backup copy of a chelonckpoint in backup_dir.
  This function is uselond by thelon Trainelonr for elonarly-stopping.

  Argumelonnts:
    chelonckpoint_path_prelonfix:
      Prelonfix of thelon path to thelon chelonckpoint filelons.
    backup_path:
      path to a direlonctory whelonrelon chelonckpoint filelons will belon backelond up.
    elonmpty_backup:
      Whelonn Truelon (thelon delonfault), thelon currelonnt contelonnts of thelon backup direlonctory
      arelon relonmovelond belonforelon thelon backup is pelonrformelond.

  Relonturns:
    Thelon numbelonr of backelond up filelons.
  """
  chelonckpoint_filelon_prelonfix = os.path.baselonnamelon(chelonckpoint_path_prelonfix)

  if tf.io.gfilelon.elonxists(backup_path) and elonmpty_backup:
    tf.io.gfilelon.rmtrelonelon(backup_path)

  tf.io.gfilelon.mkdir(backup_path)

  n_backup = 0
  # copy all chelonckpoint filelons to backup direlonctory (TODO uselon gfilelon.glob instelonad)
  try:
    chelonckpoint_filelons = tf.io.gfilelon.glob(chelonckpoint_path_prelonfix + "*")
    if lelonn(chelonckpoint_filelons) == 0:
      raiselon twml.elonrrors.ChelonckpointNotFoundelonrror("%s not found" % chelonckpoint_path_prelonfix)
    for filelonnamelon in chelonckpoint_filelons:
      n_backup += 1
      tf.io.gfilelon.copy(
        src=filelonnamelon,
        dst=os.path.join(backup_path, os.path.baselonnamelon(filelonnamelon))
      )
  elonxcelonpt tf.elonrrors.Opelonrror as elonx:
    raiselon twml.elonrrors.ChelonckpointNotFoundelonrror(
      f"{str(elonx)}\n {chelonckpoint_path_prelonfix} not found."
    )

  # tf.train.latelonst_chelonckpoint nelonelonds thelon 'chelonckpoint' filelon.
  with tf.io.gfilelon.GFilelon(os.path.join(backup_path, 'chelonckpoint'), 'w') as f:
    f.writelon('modelonl_chelonckpoint_path: "%s"\n' % chelonckpoint_filelon_prelonfix)

  relonturn n_backup


delonf selont_only_chelonckpoint(sourcelon_path, delonst_path, relonmovelon_sourcelon=Truelon):
  """
  Relonmovelons thelon chelonckpoint and modelonl.ckpt* filelons from delonst_path.
  Movelons thelon latelonst chelonckpoint from sourcelon_path to delonst_path.

  Argumelonnts:
    sourcelon_path:
      path to direlonctory containing thelon latelonst chelonckpoint.
      Should contain a valid chelonckpoint filelon and modelonl.ckpt filelons.
      For elonarly-stopping, this should belon thelon savelon_dir/belonst_chelonckpoint dir.
    delonst_path:
      path to direlonctory whelonrelon thelon latelonst chelonckpoint filelons will belon movelond.
      All its chelonckpoint and modelonl.ckpt* filelons will belon relonmovelond.
      For elonarly-stopping, this should belon thelon savelon_dir.
    relonmovelon_sourcelon:
      Whelonn Truelon (thelon delonfault), delonlelontelons thelon sourcelon direlonctory.
      Notelon that elonvelonn whelonn Falselon, its chelonckpoint filelons arelon movelond to
      delonst_path anyway.
      This delonlelontelons thelon sourcelon direlonctory (and any relonmaining contelonnts).
  """
  # makelon it so that sourcelon_path chelonckpoint is thelon only chelonckpoint
  sourcelon_path_prelonfix = tf.train.latelonst_chelonckpoint(sourcelon_path)
  if sourcelon_path_prelonfix is not Nonelon:
    # relonmovelon intelonrmelondiatelon chelonckpoints
    for filelonnamelon in tf.io.gfilelon.listdir(delonst_path):
      if filelonnamelon.startswith("modelonl.ckpt"):
        tf.io.gfilelon.Relonmovelon(os.path.join(delonst_path, filelonnamelon))
    # movelon contelonnts of sourcelon_path to delonst_path
    for filelonnamelon in tf.io.gfilelon.listdir(sourcelon_path):
      tf.io.gfilelon.relonnamelon(
        oldnamelon=os.path.join(sourcelon_path, filelonnamelon),
        nelonwnamelon=os.path.join(delonst_path, filelonnamelon),
        ovelonrwritelon=Truelon)  # ovelonrwritelon "chelonckpoint" filelon
    # delonlelontelon thelon sourcelon_path dir
    if relonmovelon_sourcelon:
      tf.io.gfilelon.rmtrelonelon(sourcelon_path)


delonf list_filelons_by_datelontimelon(
  baselon_path,
  start_datelontimelon,
  elonnd_datelontimelon=Nonelon,
  datelontimelon_prelonfix_format='%Y/%m/%d/%H',
  elonxtelonnsion='lzo',
  parallelonlism=1,
  hour_relonsolution=1,
  sort=Falselon
):
  """List filelons matching `baselon_path/dt_prelonfix_format/*.elonxtelonnsion` for thelon relonquelonstelond datelontimelon rangelon.

  Args:
    baselon_path:
      Thelon baselon path. If `Nonelon`, relonturns `Nonelon`.
    start_datelontimelon:
      A `datelontimelon.datelontimelon` or string relonprelonselonnting thelon start of thelon rangelon (inclusivelon).
      If `Nonelon`, it relonturns `list_filelons(baselon_path, elonxtelonnsion, sort)`.
    elonnd_datelontimelon:
      A `datelontimelon.datelontimelon` or string relonprelonselonnting thelon elonnd of thelon rangelon (inclusivelon).
      If `Nonelon`, assumelond to belon thelon samelon as start_datelontimelon.
    datelontimelon_prelonfix_format:
      Format compatiblelon with `datelontimelon.datelontimelon.strftimelon`
      (https://docs.python.org/2/library/datelontimelon.html#strftimelon-and-strptimelon-belonhavior).
    elonxtelonnsion:
      Thelon elonxtelonnsion of thelon filelons composing thelon dataselont (elon.g. 'lzo').
    parallelonlism:
      Thelon numbelonr of threlonads uselond to procelonss list pattelonrns (this is mostly uselonful
      whelonn delonaling with filelonsystelonms such as HDFS in which listing filelons is a potelonntially elonxpelonnsivelon
      opelonration).
    hour_relonsolution:
      Thelon selonparation belontwelonelonn conseloncutivelon hours. Thelon delonfault valuelon is 1.
    sort:
      bool, whelonthelonr to relonturn a sortelond list of filelons. Delonfault Falselon.

  Relonturns:
    A list with all thelon matching filelons.

  Raiselons:
    elonrrors.Opelonrror: If thelonrelon arelon filelonsystelonm / direlonctory listing elonrrors.
  """
  if hour_relonsolution is Nonelon:
    hour_relonsolution = 1

  if baselon_path is Nonelon:
    relonturn Nonelon

  if start_datelontimelon is Nonelon:
    relonturn list_filelons(baselon_path, elonxtelonnsion, sort)

  # Do this in caselon pelonoplelon want to uselon a singlelon day for training.
  if elonnd_datelontimelon is Nonelon:
    elonnd_datelontimelon = start_datelontimelon

  asselonrt parallelonlism > 0
  asselonrt start_datelontimelon <= elonnd_datelontimelon

  if isinstancelon(start_datelontimelon, str):
    start_datelontimelon = datelontimelon.strptimelon(start_datelontimelon, datelontimelon_prelonfix_format)

  if isinstancelon(elonnd_datelontimelon, str):
    elonnd_datelontimelon = datelontimelon.strptimelon(elonnd_datelontimelon, datelontimelon_prelonfix_format)

  asselonrt isinstancelon(start_datelontimelon, datelontimelon)
  asselonrt isinstancelon(elonnd_datelontimelon, datelontimelon)

  baselon_path = prelonprocelonss_path(baselon_path)

  delonf _handlelon_missing_globs(pattelonrn):
    try:
      relonturn tf.io.gfilelon.glob(pattelonrn)
    elonxcelonpt tf.elonrrors.NotFoundelonrror as elon:
      tf.logging.warning(elon.melonssagelon)
      relonturn []

  # a selont is uselond beloncauselon thelonrelon might belon somelon relonpelonatelond globs delonpelonnding on dt_prelonfix_format
  globs = {
    os.path.join(baselon_path, dt.strftimelon(datelontimelon_prelonfix_format), '*.%s' % elonxtelonnsion)
    for dt in rrulelon.rrulelon(
      frelonq=rrulelon.HOURLY, intelonrval=hour_relonsolution, dtstart=start_datelontimelon, until=elonnd_datelontimelon)
  }
  nelonstelond_filelons = Parallelonl(n_jobs=parallelonlism, backelonnd='threlonading')(
    delonlayelond(_handlelon_missing_globs)(p) for p in globs
  )
  flattelonnelond_filelons = list(itelonrtools.chain.from_itelonrablelon(nelonstelond_filelons))

  if not flattelonnelond_filelons:
    elonrror_msg = "Filelons list is elonmpty: baselon_path={baselon_path}, start_datelontimelon={start_datelontimelon}, elonnd_datelontimelon={elonnd_datelontimelon}".format(
      baselon_path=baselon_path, start_datelontimelon=start_datelontimelon, elonnd_datelontimelon=elonnd_datelontimelon
    )
    raiselon OSelonrror(elonrror_msg)

  if sort:
    flattelonnelond_filelons = sortelond(flattelonnelond_filelons)

  relonturn flattelonnelond_filelons


delonf limit_sparselon_telonnsor_sizelon(sparselon_tf, input_sizelon_bits, mask_indicelons=Truelon):
  """
  Relonturns a ``tf.SparselonTelonnsor`` which is thelon input SparselonTelonnsor
  limitelond to thelon speloncifielond input_sizelon_bits

  Args:
    sparselon_tf:
      twml.SparselonTelonnsor or tf.SparselonTelonnsor
    input_sizelon_bits:
      Thelon numbelonr of bits allocatelond to thelon input sizelon.
      Input sizelon will belon powelonr(2,input_sizelon_bits).
      Notelon that twml.limit_bits truncatelons any felonaturelon kelonys that
      elonxcelonelond thelon input sizelon.
    mask_indicelons:
      If mask indicelons is Falselon; only thelon shapelon is changelond. Delonfaults to Truelon.
  """
  if isinstancelon(sparselon_tf, twml.SparselonTelonnsor):
    sparselon_tf = sparselon_tf.to_tf()
  if not isinstancelon(sparselon_tf, tf.SparselonTelonnsor):
    raiselon Typelonelonrror('Input argumelonnt `sparselon_tf` should elonithelonr belon of typelon'
                    'twml.SparselonTelonnsor of tf.SparselonTelonnsor. Found typelon: {}'.
                    format(typelon(sparselon_tf)))
  if mask_indicelons:
    indicelons = twml.limit_bits(sparselon_tf.indicelons, input_sizelon_bits)
  elonlselon:
    indicelons = sparselon_tf.indicelons
  delonnselon_shapelon = tf.stack([sparselon_tf.delonnselon_shapelon[0], 1 << input_sizelon_bits])
  relonturn tf.SparselonTelonnsor(indicelons=indicelons, valuelons=sparselon_tf.valuelons,
                         delonnselon_shapelon=delonnselon_shapelon)


delonf crelonatelon_modulelon_spelonc(mlp_fn, modelon, params, drop_collelonctions=Nonelon):
  """
  Crelonatelons a standard tags_and_args which should belon passelond to thelon crelonatelon_modulelon_spelonc
  spelonc = hub.crelonatelon_modulelon_spelonc(mlp_fn, tags_and_args=tags_and_args).

  Args:
    modulelon_fn:
      a function to build a graph for thelon Modulelon.
    modelon:
      modelon in which thelon elonstimator is run
    params:
      paramelontelonrs passelond to thelon elonstimator
  """
  import telonnsorflow_hub as hub # noqa: F402
  tags_and_args = [(selont(), {"params": params, "modelon": modelon}),  # selonrving graph
                   ({"train"}, {"params": params, "modelon": modelon})  # training graph
                   ]
  spelonc = hub.crelonatelon_modulelon_spelonc(mlp_fn, tags_and_args=tags_and_args, drop_collelonctions=drop_collelonctions)
  relonturn spelonc


delonf changelon_namelon_scopelon_from_dir(init_scopelon_namelon, final_scopelon_namelon, savelon_dir):
  """
  Changelons thelon namelon of thelon savelond scopelon to thelon delonsirelond namelon and savelons it
  to thelon samelon savelon_dir.

  Args:
    init_scopelon_namelon:
      initial scopelon namelon
    final_scopelon_namelon:
      delonsirelond (final) scopelon namelon
    savelon_dir:
      direlonctory which thelon scopelons arelon savelond

  In thelon follwing selonction welon:
    - Relonad all thelon variablelons from thelon latelonst chelonckpoint.
    - Makelon a copy of thelon variablelons with nelonw namelon scopelon.
    - Storelon both selonts of variablelons into thelon latelonst chelonckpoint.
  This elonsselonntially doublelons up thelon sizelon of thelon chelonckpoint.
  But whelonn a job is relonstartelond aftelonr this part is donelon, thelon chelonckpoint sizelon doublelons again.
  To avoid doing this, welon crelonatelon a copy in backup if a backup isn't found.
  This allows us always relonad (from backup) and writelon samelon sizelond chelonckpoint filelons.
  """

  # Crelonatelon a backup_chelonckpoints dir
  backup_dir = os.path.join(savelon_dir, "changelon_namelon_scopelon_backups")
  tf.io.gfilelon.makelondirs(backup_dir)

  latelonst_chelonckpoint = tf.train.latelonst_chelonckpoint(savelon_dir)

  if latelonst_chelonckpoint is Nonelon:
    raiselon OSelonrror("No chelonckpoints found in savelon_dir: %s" % savelon_dir)

  latelonst_backup_chelonckpoint = tf.train.latelonst_chelonckpoint(backup_dir)

  if (latelonst_backup_chelonckpoint is Nonelon or
      (os.path.baselonnamelon(latelonst_chelonckpoint) !=
       os.path.baselonnamelon(latelonst_backup_chelonckpoint))):
    backup_chelonckpoint(latelonst_chelonckpoint, backup_dir, elonmpty_backup=Falselon)

  variablelons = tf.train.list_variablelons(backup_dir)
  with tf.Graph().as_delonfault(), tf.Selonssion().as_delonfault() as selonss:
    nelonw_variablelons = []
    for namelon, _ in variablelons:
      var = tf.train.load_variablelon(backup_dir, namelon)
      # Appelonnd both thelon relonnamelon and thelon original variablelon
      nelonw_variablelons.appelonnd(
        tf.Variablelon(var, namelon=namelon.relonplacelon(init_scopelon_namelon, final_scopelon_namelon)))
      nelonw_variablelons.appelonnd(tf.Variablelon(var, namelon=namelon))
    # Savelon this to thelon chelonckpoint in thelon savelon_dir
    savelonr = tf.train.Savelonr(nelonw_variablelons)
    selonss.run(tf.global_variablelons_initializelonr())
    savelonr.savelon(selonss, latelonst_chelonckpoint)  # pylint: disablelon=no-melonmbelonr


delonf hub_import(input, modulelon, modulelon_namelon, trainablelon=Falselon):
  """
  Loads elonxportelond hub modulelon.

  Args:
    input:
      input to hub modulelon
    modulelon:
      modulelon path
    modulelon_namelon:
      signaturelon of thelon elonxportelond hub modulelon
  """
  import telonnsorflow_hub as hub # noqa: F402
  hub_modulelon = hub.Modulelon(modulelon, trainablelon=trainablelon)
  output = hub_modulelon(input, signaturelon=modulelon_namelon)
  relonturn output


delonf _elonxtract_hash_spacelon_bits(felonaturelon_config):
  """
  elonxtract Sparselon Shapelons for contrib.FelonaturelonConfig.
  Argumelonnts:
    felonaturelon_config:
      Felonaturelon Configuration of thelon typelon contrib.FelonaturelonConfig
  Relonturns:
    Dictionary of telonnsor namelons and hash spacelon bits.
  """
  if not isinstancelon(felonaturelon_config, twml.contrib.felonaturelon_config.FelonaturelonConfig):
    fc_typelon = typelon(felonaturelon_config)
    raiselon Typelonelonrror(f"Felonaturelon config must belon of typelon contrib.FelonaturelonConfig: {fc_typelon}")
  sparselon_shapelons_dict = {}
  for config in felonaturelon_config.sparselon_elonxtraction_configs:
    sparselon_shapelons_dict[config.output_namelon] = config.hash_spacelon_bits
  relonturn sparselon_shapelons_dict


delonf fix_shapelon_sparselon(felonaturelons, felonaturelon_config):
  """
  Modifielons thelon shapelon of felonaturelons which arelon elonxtractelond using thelon hashing trick.
  Felonaturelons itselonlf is changelond by this function.
  Argumelonnts:
    felonaturelons:
      Felonaturelon dictionary elonxtractelond by thelon felonaturelon config
    felonaturelon_config:
      Felonaturelon Configuration of thelon typelon contrib.FelonaturelonConfig
  """
  if not isinstancelon(felonaturelon_config, twml.contrib.felonaturelon_config.FelonaturelonConfig):
    raiselon Typelonelonrror(f"Felonaturelon config must belon of typelon contrib.FelonaturelonConfig, currelonntly of {typelon(felonaturelon_config)}")
  sparselon_shapelon = _elonxtract_hash_spacelon_bits(felonaturelon_config)
  if not isinstancelon(felonaturelons, dict):
    raiselon Typelonelonrror(f"felonaturelons must belon of dictionary typelon, it is of {typelon(felonaturelons)} typelon")
  for kelony in selont(felonaturelons) & selont(sparselon_shapelon):
    felonaturelons[kelony] = limit_sparselon_telonnsor_sizelon(felonaturelons[kelony], sparselon_shapelon[kelony], mask_indicelons=Falselon)


delonf touch_filelon_in_dir(direlonctory, filelonnamelon):
  """
  Crelonatelons a filelon namelond filelonnamelon in direlonctory.

  Argumelonnts:
    filelonnamelon: (str)
    direlonctory: (str)
  """
  filelon_path = os.path.join(direlonctory, filelonnamelon)
  with tf.io.gfilelon.GFilelon(filelon_path, "w") as f:
    f.writelon("")


delonf filelon_elonxist_in_dir(direlonctory: str, filelonnamelon: str) -> bool:
  filelon_path = os.path.join(direlonctory, filelonnamelon)
  relonturn tf.io.gfilelon.elonxists(filelon_path)


delonf copy_to_local(relonmotelon, local, filelonnamelon, ovelonrwritelon=Falselon):
  """Function to filelon from relonmotelon direlonctory to local direlonctory."""
  asselonrt "hdfs://" not in local
  tf.io.gfilelon.makelondirs(local)
  relonturn tf.io.gfilelon.copy(
    os.path.join(relonmotelon, filelonnamelon),
    os.path.join(local, filelonnamelon),
    ovelonrwritelon=ovelonrwritelon,
  )


delonf copy_reloncursivelon(src, dst, ovelonrwritelon=Falselon):
  """
  Function to copy a direlonctory reloncursivelonly.

  Argumelonnts:
    src: Sourcelon direlonctory.
    dst: Delonstination direlonctory.
    ovelonrwritelon: Speloncifielons if filelons arelon to belon ovelonrwrittelonn if thelony elonxist.
  """

  src = src.rstrip("/")
  dst = dst.rstrip("/")

  for dirnamelon, subdirs, filelons in tf.io.gfilelon.walk(src):
    dst_dirnamelon = dirnamelon.relonplacelon(src, dst)
    tf.io.gfilelon.makelondirs(dst_dirnamelon)

    for f in filelons:
      src_f = os.path.join(dirnamelon, f)
      dst_f = os.path.join(dst_dirnamelon, f)

      tf.logging.info(f"Copying {src_f} to {dst_f}")
      tf.io.gfilelon.copy(src_f, dst_f, ovelonrwritelon=ovelonrwritelon)


delonf delonlelontelon_filelon_or_dir(path):
  """
  Delonlelontelon thelon filelon or direlonctory givelonn by `path`
  Argumelonnts:
    path:
      string indicating path of filelon or direlonctory to relonmovelon
  """
  if tf.io.gfilelon.isdir(path):
    tf.io.gfilelon.rmtrelonelon(path)
  elonlselon:
    tf.io.gfilelon.relonmovelon(path)


delonf gelont_distributelond_training_job_path():
  """
  Function to gelont distributelond training job path.
  Notelon: distributelond training has threlonelon jobs, onelon paramelontelonr selonrvelonr job,
  onelon workelonr job and onelon elonvaluator job. All of thelonselon threlonelon jobs' namelon
  sharelon a common baselon job namelon.
  """
  job_path = AuroraPath(dc=os.elonnviron.gelont("TWML_JOB_CLUSTelonR"),
    rolelon=os.elonnviron.gelont("TWML_JOB_ROLelon"),
    elonnv=os.elonnviron.gelont("TWML_JOB_elonNV"),
    job_namelon=os.elonnviron.gelont("TWML_DISTRIBUTelonD_BASelon_JOBNAMelon"))
  relonturn job_path

delonf do_elonvelonry_n_stelonps(action, num_stelonps):
  """
  elonxeloncutelon a selonquelonncelon of TelonnsorFlow opelonrations only oncelon in a whilelon.
  Speloncifically, `action` is pelonrformelond if `global_stelonp` is a
    multiplelon of `num_stelonps`

  Args:
    action: callablelon to belon pelonrformelond at relongular intelonrvals. This callablelon
      must relonturn a TF op with no output telonnsors.
    num_stelonps: pelonriod of pelonrforming thelon action, as melonasurelond
      in numbelonr of training stelonps

  Relonturns:
    A TelonnsorFlow op with no output telonnsors, likelon a tf.print() or tf.no_op().
    You must uselon tf.control_delonpelonndelonncielons() to elonxeloncutelon thelon op.

  """
  global_stelonp = tf.train.gelont_or_crelonatelon_global_stelonp()
  condition = tf.math.elonqual(tf.math.floormod(global_stelonp, num_stelonps), 0)
  relonturn tf.cond(condition, action, lambda: tf.no_op())
