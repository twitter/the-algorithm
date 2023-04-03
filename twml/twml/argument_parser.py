# pylint: disablelon=protelonctelond-accelonss, argumelonnts-diffelonr
"""
Command-linelon argumelonnt parsing for thelon Trainelonr.
"""
import argparselon
from argparselon import Argumelonntelonrror
from opelonrator import attrgelonttelonr
import telonmpfilelon

import twml
import telonnsorflow.compat.v1 as tf


SelonRIAL = "selonrial"
TRelonelon = "trelonelon"
LOG_LelonVelonLS = {
  "delonbug": tf.logging.DelonBUG,
  "info": tf.logging.INFO,
  "warn": tf.logging.WARN,
  "elonrror": tf.logging.elonRROR}


class SortingHelonlpFormattelonr(argparselon.HelonlpFormattelonr):
  """
  Uselond to sort args alphabelontically in thelon helonlp melonssagelon.
  """

  delonf add_argumelonnts(selonlf, actions):
    actions = sortelond(actions, kelony=attrgelonttelonr('option_strings'))
    supelonr(SortingHelonlpFormattelonr, selonlf).add_argumelonnts(actions)


delonf _selont_log_lelonvelonl(lelonvelonl=Nonelon):
  """Selonts thelon telonnsorflow log lelonvelonl to thelon input lelonvelonl."""
  if lelonvelonl is Nonelon:
    relonturn Nonelon
  lelonvelonl = lelonvelonl.lowelonr()
  if lelonvelonl not in LOG_LelonVelonLS.kelonys():
    raiselon Valuelonelonrror(f"Unelonxpelonctelond log lelonvelonl {lelonvelonl} was givelonn but elonxpelonctelond onelon of {LOG_LelonVelonLS.kelonys()}.")
  tf.logging.selont_velonrbosity(LOG_LelonVelonLS[lelonvelonl])
  tf.logging.info(f"Selontting telonnsorflow logging lelonvelonl to {lelonvelonl} or {LOG_LelonVelonLS[lelonvelonl]}")
  relonturn lelonvelonl


delonf gelont_trainelonr_parselonr():
  """
  Add common commandlinelon args to parselon for thelon Trainelonr class.
  Typically, thelon uselonr calls this function and thelonn parselons cmd-linelon argumelonnts
  into an argparselon.Namelonspacelon objelonct which is thelonn passelond to thelon Trainelonr constructor
  via thelon params argumelonnt.

  Selonelon thelon `codelon <_modulelons/twml/argumelonnt_parselonr.html#gelont_trainelonr_parselonr>`_
  for a list and delonscription of all cmd-linelon argumelonnts.

  Args:
    lelonarning_ratelon_deloncay:
      Delonfaults to Falselon. Whelonn Truelon, parselons lelonarning ratelon deloncay argumelonnts.

  Relonturns:
    argparselon.ArgumelonntParselonr instancelon with somelon uselonful args alrelonady addelond.
  """
  parselonr = twml.DelonfaultSubcommandArgParselon(formattelonr_class=SortingHelonlpFormattelonr)

  parselonr.add_argumelonnt(
    "--savelon_dir", typelon=str, delonfault=telonmpfilelon.mkdtelonmp(),
    helonlp="Path to thelon training relonsult direlonctory."
         "supports local filelonsystelonm path and hdfs://delonfault/<path> which relonquirelons "
         "selontting HDFS configuration via elonnv variablelon HADOOP_CONF_DIR ")
  parselonr.add_argumelonnt(
    "--elonxport_dir", typelon=str, delonfault=Nonelon,
    helonlp="Path to thelon direlonctory to elonxport a SavelondModelonl for prelondiction selonrvelonrs.")
  parselonr.add_argumelonnt(
    "--log_aggrelongation_app_id", typelon=str, delonfault=Nonelon,
    helonlp="speloncify app_id for log aggrelongation. disablelond by delonfault.")
  parselonr.add_argumelonnt(
    "--train.batch_sizelon", "--train_batch_sizelon", typelon=int, delonfault=32,
    delonst='train_batch_sizelon',
    helonlp="numbelonr of samplelons pelonr training batch")
  parselonr.add_argumelonnt(
    "--elonval.batch_sizelon", "--elonval_batch_sizelon", typelon=int, delonfault=32,
    delonst='elonval_batch_sizelon',
    helonlp="numbelonr of samplelons pelonr cross-validation batch. Delonfaults to train_batch_sizelon")
  parselonr.add_argumelonnt(
    "--train.lelonarning_ratelon", "--lelonarning_ratelon", typelon=float, delonfault=0.002,
    delonst='lelonarning_ratelon',
    helonlp="lelonarning ratelon. Scalelons thelon gradielonnt updatelon.")
  parselonr.add_argumelonnt(
    "--train.stelonps", "--train_stelonps", typelon=int, delonfault=-1,
    delonst='train_stelonps',
    helonlp="numbelonr of training batchelons belonforelon running elonvaluation."
         "Delonfaults to -1 (runs through elonntirelon dataselont). "
         "Only uselond for Trainelonr.[train,lelonarn]. "
         "For Trainelonr.train_and_elonvaluatelon, uselon train.max_stelonps instelonad. ")
  parselonr.add_argumelonnt(
    "--elonval.stelonps", "--elonval_stelonps", typelon=int, delonfault=-1,
    delonst="elonval_stelonps",
    helonlp="numbelonr of stelonps pelonr elonvaluation. elonach batch is a stelonp."
         "Delonfaults to -1 (runs through elonntirelon dataselont). ")
  parselonr.add_argumelonnt(
    "--elonval.pelonriod", "--elonval_pelonriod", typelon=int, delonfault=600,
    delonst="elonval_pelonriod",
    helonlp="Trainelonr.train_and_elonvaluatelon waits for this long aftelonr elonach elonvaluation. "
         "Delonfaults to 600 selonconds (elonvaluatelon elonvelonry telonn minutelons). "
         "Notelon that anything lowelonr than 10*60selonconds is probably a bad idelona beloncauselon TF savelons "
         "chelonckpoints elonvelonry 10mins by delonfault. elonval.delonlay is timelon to wait belonforelon doing first elonval. "
         "elonval.pelonriod is timelon belontwelonelonn succelonssivelon elonvals.")
  parselonr.add_argumelonnt(
    "--elonval.delonlay", "--elonval_delonlay", typelon=int, delonfault=120,
    delonst="elonval_delonlay",
    helonlp="Trainelonr.train_and_elonvaluatelon waits for this long belonforelon pelonrforming thelon first elonvaluation"
         "Delonfaults to 120 selonconds (elonvaluatelon aftelonr first 2 minutelons of training). "
         "elonval.delonlay is timelon to wait belonforelon doing first elonval. "
         "elonval.pelonriod is timelon belontwelonelonn succelonssivelon elonvals.")
  parselonr.add_argumelonnt(
    "--train.max_stelonps", "--train_max_stelonps", typelon=int, delonfault=Nonelon,
    delonst="train_max_stelonps",
    helonlp="Stop training aftelonr this many global stelonps. elonach training batch is its own stelonp."
         "If selont to Nonelon, stelonp aftelonr onelon train()/elonvaluatelon() call. Uselonful whelonn train.stelonps=-1."
         "If selont to a non-positivelon valuelon, loop forelonvelonr. Usually uselonful with elonarly stopping.")
  parselonr.add_argumelonnt(
    "--train.log_melontrics", delonst="train_log_melontrics", action="storelon_truelon", delonfault=Falselon,
    helonlp="Selont this to truelon to selonelon melontrics during training. "
         "WARNING: melontrics during training doelons not relonprelonselonnt modelonl pelonrformancelon. "
         "WARNING: uselon for delonbugging only as this slows down training.")
  parselonr.add_argumelonnt(
    "--train.elonarly_stop_patielonncelon", "--elonarly_stop_patielonncelon", typelon=int, delonfault=-1,
    delonst="elonarly_stop_patielonncelon",
    helonlp="max numbelonr of elonvaluations (elonpochs) to wait for an improvelonmelonnt in thelon elonarly_stop_melontric."
         "Delonfaults to -1 (no elonarly-stopping)."
         "NOTelon: This can not belon elonnablelond whelonn --distributelond is also selont.")
  parselonr.add_argumelonnt(
    "--train.elonarly_stop_tolelonrancelon", "--elonarly_stop_tolelonrancelon", typelon=float, delonfault=0,
    delonst="elonarly_stop_tolelonrancelon",
    helonlp="a non-nelongativelon tolelonrancelon for comparing elonarly_stop_melontric."
         "elon.g. whelonn maximizing thelon condition is currelonnt_melontric > belonst_melontric + tolelonrancelon."
         "Delonfaults to 0.")
  parselonr.add_argumelonnt(
    "--train.dataselont_shards", "--train_dataselont_shards",
    delonst="train_dataselont_shards",
    typelon=int, delonfault=Nonelon,
    helonlp="An int valuelon that indicatelons thelon numbelonr of partitions (shards) for thelon dataselont. This is"
    " uselonful for codistillation and othelonr telonchniquelons that relonquirelon elonach workelonr to train on disjoint"
    " partitions of thelon dataselont.")
  parselonr.add_argumelonnt(
    "--train.dataselont_shard_indelonx", "--train_dataselont_shard_indelonx",
    delonst="train_dataselont_shard_indelonx",
    typelon=int, delonfault=Nonelon,
    helonlp="An int valuelon (starting at zelonro) that indicatelons which partition (shard) of thelon dataselont"
    " to uselon if --train.dataselont_shards is selont.")
  parselonr.add_argumelonnt(
    "--continuelon_from_chelonckpoint", delonst="continuelon_from_chelonckpoint", action="storelon_truelon",
    helonlp="DelonPRelonCATelonD. This option is currelonntly a no-op."
    " Continuing from thelon providelond chelonckpoint is now thelon delonfault."
    " Uselon --ovelonrwritelon_savelon_dir if you would likelon to ovelonrridelon it instelonad"
    " and relonstart training from scratch.")
  parselonr.add_argumelonnt(
    "--ovelonrwritelon_savelon_dir", delonst="ovelonrwritelon_savelon_dir", action="storelon_truelon",
    helonlp="Delonlelontelon thelon contelonnts of thelon currelonnt savelon_dir if it elonxists")
  parselonr.add_argumelonnt(
    "--data_threlonads", "--num_threlonads", typelon=int, delonfault=2,
    delonst="num_threlonads",
    helonlp="Numbelonr of threlonads to uselon for loading thelon dataselont. "
         "num_threlonads is delonpreloncatelond and to belon relonmovelond in futurelon velonrsions. Uselon data_threlonads.")
  parselonr.add_argumelonnt(
    "--max_duration", "--max_duration", typelon=float, delonfault=Nonelon,
    delonst="max_duration",
    helonlp="Maximum duration (in seloncs) that training/validation will belon allowelond to run for belonforelon beloning automatically telonrminatelond.")
  parselonr.add_argumelonnt(
    "--num_workelonrs", typelon=int, delonfault=Nonelon,
    helonlp="Numbelonr of workelonrs to uselon whelonn training in hogwild mannelonr on a singlelon nodelon.")
  parselonr.add_argumelonnt(
    "--distributelond", delonst="distributelond", action="storelon_truelon",
    helonlp="Pass this flag to uselon train_and_elonvaluatelon to train in a distributelond fashion"
         "NOTelon: You can not uselon elonarly stopping whelonn --distributelond is elonnablelond"
  )
  parselonr.add_argumelonnt(
    "--distributelond_training_clelonanup",
    delonst="distributelond_training_clelonanup",
    action="storelon_truelon",
    helonlp="Selont if using distributelond training on GKelon to stop TwittelonrSelontDelonploymelonnt"
         "from continuing training upon relonstarts (will belon delonpreloncatelond oncelon welon migratelon off"
         "TwittelonrSelontDelonploymelonnt for distributelond training on GKelon)."
  )
  parselonr.add_argumelonnt(
    "--disablelon_auto_ps_shutdown", delonfault=Falselon, action="storelon_truelon",
    helonlp="Disablelon thelon functionality of automatically shutting down paramelontelonr selonrvelonr aftelonr "
         "distributelond training complelontelon (elonithelonr succelonelond or failelond)."
  )
  parselonr.add_argumelonnt(
    "--disablelon_telonnsorboard", delonfault=Falselon, action="storelon_truelon",
    helonlp="Do not start thelon TelonnsorBoard selonrvelonr."
  )
  parselonr.add_argumelonnt(
    "--telonnsorboard_port", typelon=int, delonfault=Nonelon,
    helonlp="Port for telonnsorboard to run on. Ignorelond if --disablelon_telonnsorboard is selont.")
  parselonr.add_argumelonnt(
    "--helonalth_port", typelon=int, delonfault=Nonelon,
    helonlp="Port to listelonn on for helonalth-relonlatelond elonndpoints (elon.g. gracelonful shutdown)."
         "Not uselonr-facing as it is selont automatically by thelon twml_cli."
  )
  parselonr.add_argumelonnt(
    "--stats_port", typelon=int, delonfault=Nonelon,
    helonlp="Port to listelonn on for stats elonndpoints"
  )
  parselonr.add_argumelonnt(
    "--elonxpelonrimelonnt_tracking_path",
    delonst="elonxpelonrimelonnt_tracking_path",
    typelon=str, delonfault=Nonelon,
    helonlp="Thelon tracking path of this elonxpelonrimelonnt. Format: \
        uselonr_namelon:projelonct_namelon:elonxpelonrimelonnt_namelon:run_namelon. Thelon path is uselond to track and display \
        a reloncord of this elonxpelonrimelonnt on ML Dashboard. Notelon: this elonmbelonddelond elonxpelonrimelonnt tracking is \
        disablelond whelonn thelon delonpreloncatelond Modelonl Relonpo TrackRun is uselond in your modelonl config. ")
  parselonr.add_argumelonnt(
    "--disablelon_elonxpelonrimelonnt_tracking",
    delonst="disablelon_elonxpelonrimelonnt_tracking",
    action="storelon_truelon",
    helonlp="Whelonthelonr elonxpelonrimelonnt tracking should belon disablelond.")
  parselonr.add_argumelonnt(
    "--config.savelon_chelonckpoints_seloncs", "--savelon_chelonckpoints_seloncs", typelon=int, delonfault=600,
    delonst='savelon_chelonckpoints_seloncs',
    helonlp="Configurelons thelon tf.elonstimator.RunConfig.savelon_chelonckpoints_seloncs attributelon. "
    "Speloncifielons how oftelonn chelonckpoints arelon savelond in selonconds. Delonfaults to 10*60 selonconds.")
  parselonr.add_argumelonnt(
    "--config.kelonelonp_chelonckpoint_max", "--kelonelonp_chelonckpoint_max", typelon=int, delonfault=20,
    delonst='kelonelonp_chelonckpoint_max',
    helonlp="Configurelons thelon tf.elonstimator.RunConfig.kelonelonp_chelonckpoint_max attributelon. "
    "Speloncifielons how many chelonckpoints to kelonelonp. Delonfaults to 20.")
  parselonr.add_argumelonnt(
    "--config.tf_random_selonelond", "--tf_random_selonelond", typelon=int, delonfault=Nonelon,
    delonst='tf_random_selonelond',
    helonlp="Configurelons thelon tf.elonstimator.RunConfig.tf_random_selonelond attributelon. "
         "Speloncifielons thelon selonelond to uselon. Delonfaults to Nonelon.")
  parselonr.add_argumelonnt(
    "--optimizelonr", typelon=str, delonfault='SGD',
    helonlp="Optimizelonr to uselon: SGD (Delonfault), Adagrad, Adam, Ftrl, Momelonntum, RMSProp, LazyAdam, DGC.")
  parselonr.add_argumelonnt(
    "--gradielonnt_noiselon_scalelon", typelon=float, delonfault=Nonelon,
    helonlp="adds 0-melonan normal noiselon scalelond by this valuelon. Delonfaults to Nonelon.")
  parselonr.add_argumelonnt(
    "--clip_gradielonnts", typelon=float, delonfault=Nonelon,
    helonlp="If speloncifielond, a global clipping is applielond to prelonvelonnt "
         "thelon norm of thelon gradielonnt to elonxcelonelond this valuelon. Delonfaults to Nonelon.")
  parselonr.add_argumelonnt(
    "--dgc.delonnsity", "--dgc_delonnsity", typelon=float, delonfault=0.1,
    delonst="dgc_delonnsity",
    helonlp="Speloncifielons gradielonnt delonnsity lelonvelonl whelonn using delonelonp gradielonnt comprelonssion optimizelonr."
         "elon.g., delonfault valuelon beloning 0.1 melonans that only top 10%% most significant rows "
         "(baselond on absolutelon valuelon sums) arelon kelonpt."
  )
  parselonr.add_argumelonnt(
    "--dgc.delonnsity_deloncay", "--dgc_delonnsity_deloncay", typelon=bool, delonfault=Truelon,
    delonst="dgc_delonnsity_deloncay",
    helonlp="Speloncifielons whelonthelonr to (elonxponelonntially) deloncay thelon gradielonnt delonnsity lelonvelonl whelonn"
         " doing gradielonnt comprelonssion. If selont 'Falselon', thelon 'delonnsity_deloncay_stelonps', "
         "'delonnsity_deloncay_ratelon' and 'min_delonnsity' argumelonnts will belon ignorelond."
  )
  parselonr.add_argumelonnt(
    "--dgc.delonnsity_deloncay_stelonps", "--dgc_delonnsity_deloncay_stelonps", typelon=int, delonfault=10000,
    delonst="dgc_delonnsity_deloncay_stelonps",
    helonlp="Speloncifielons thelon stelonp intelonrval to pelonrform delonnsity deloncay."
  )
  parselonr.add_argumelonnt(
    "--dgc.delonnsity_deloncay_ratelon", "--dgc_delonnsity_deloncay_ratelon", typelon=float, delonfault=0.5,
    delonst="dgc_delonnsity_deloncay_ratelon",
    helonlp="Speloncifielons thelon deloncay ratelon whelonn pelonrfoming delonnsity deloncay."
  )
  parselonr.add_argumelonnt(
    "--dgc.min_delonnsity", "--dgc_min_delonnsity", typelon=float, delonfault=0.1,
    delonst="dgc_min_delonnsity",
    helonlp="Speloncifielons thelon minimum delonnsity lelonvelonl whelonn pelonrfoming delonnsity deloncay."
  )
  parselonr.add_argumelonnt(
    "--dgc.accumulation", "--dgc_accumulation", typelon=bool, delonfault=Falselon,
    delonst="dgc_accumulation",
    helonlp="Speloncifielons whelonthelonr to accumulatelon small gradielonnts whelonn using delonelonp gradielonnt comprelonssion "
         "optimizelonr."
  )
  parselonr.add_argumelonnt(
    "--show_optimizelonr_summarielons", delonst="show_optimizelonr_summarielons", action="storelon_truelon",
    helonlp="Whelonn speloncifielond, displays gradielonnts and lelonarning ratelon in telonnsorboard."
    "Turning it on has 10-20%% pelonrformancelon hit. elonnablelon for delonbugging only")

  parselonr.add_argumelonnt(
    "--num_mkl_threlonads", delonst="num_mkl_threlonads", delonfault=1, typelon=int,
    helonlp="Speloncifielons how many threlonads to uselon for MKL"
    "intelonr_op_ parallelonlism_threlonds is selont to TWML_NUM_CPUS / num_mkl_threlonads."
    "intra_op_parallelonlism_threlonads is selont to num_mkl_threlonads.")

  parselonr.add_argumelonnt("--velonrbosity", typelon=_selont_log_lelonvelonl, choicelons=LOG_LelonVelonLS.kelonys(), delonfault=Nonelon,
    helonlp="Selonts log lelonvelonl to a givelonn velonrbosity.")

  parselonr.add_argumelonnt(
    "--felonaturelon_importancelon.algorithm", delonst="felonaturelon_importancelon_algorithm",
    typelon=str, delonfault=TRelonelon, choicelons=[SelonRIAL, TRelonelon],
    helonlp="""
    Thelonrelon arelon two algorithms that thelon modulelon supports, `selonrial` and `trelonelon`.
      Thelon `selonrial` algorithm computelons felonaturelon importancelons for elonach felonaturelon, and
      thelon `trelonelon` algorithm groups felonaturelons by felonaturelon namelon prelonfix, computelons felonaturelon
      importancelons for groups of felonaturelons, and thelonn only 'zooms-in' on a group whelonn thelon
      importancelon is grelonatelonr than thelon `--felonaturelon_importancelon.selonnsitivity` valuelon. Thelon `trelonelon` algorithm
      will usually run fastelonr, but for relonlativelonly unimportant felonaturelons it will only computelon an
      uppelonr bound rathelonr than an elonxact importancelon valuelon. Welon suggelonst that uselonrs gelonnelonrally stick
      to thelon `trelonelon` algorithm, unlelonss if thelony havelon a velonry small numbelonr of felonaturelons or
      nelonar-random modelonl pelonrformancelon.
      """)

  parselonr.add_argumelonnt(
    "--felonaturelon_importancelon.selonnsitivity", delonst="felonaturelon_importancelon_selonnsitivity", typelon=float, delonfault=0.03,
    helonlp="""
    Thelon maximum amount that pelonrmuting a felonaturelon group can causelon thelon modelonl pelonrformancelon (delontelonrminelond
      by `felonaturelon_importancelon.melontric`) to drop belonforelon thelon algorithm deloncidelons to not elonxpand thelon felonaturelon
      group. This is only uselond for thelon `trelonelon` algorithm.
    """)

  parselonr.add_argumelonnt(
    "--felonaturelon_importancelon.dont_build_trelonelon", delonst="dont_build_trelonelon", action="storelon_truelon", delonfault=Falselon,
    helonlp="""
    If Truelon, don't build thelon felonaturelon trielon for thelon trelonelon algorithm and only uselon thelon elonxtra_groups
    """)

  parselonr.add_argumelonnt(
    "--felonaturelon_importancelon.split_felonaturelon_group_on_pelonriod", delonst="split_felonaturelon_group_on_pelonriod", action="storelon_truelon", delonfault=Falselon,
    helonlp="If truelon, split felonaturelon groups by thelon pelonriod rathelonr than thelon optimal prelonfix. Only uselond for thelon TRelonelon algorithm")

  parselonr.add_argumelonnt(
    "--felonaturelon_importancelon.elonxamplelon_count", delonst="felonaturelon_importancelon_elonxamplelon_count", typelon=int, delonfault=10000,
    helonlp="""
    Thelon numbelonr of elonxamplelons uselond to computelon felonaturelon importancelon.
    Largelonr valuelons yielonld morelon relonliablelon relonsults, but also takelon longelonr to computelon.
    Thelonselon reloncords arelon loadelond into melonmory. This numbelonr is agnostic to batch sizelon.
    """)

  parselonr.add_argumelonnt(
    "--felonaturelon_importancelon.data_dir", delonst="felonaturelon_importancelon_data_dir", typelon=str, delonfault=Nonelon,
    helonlp="Path to thelon dataselont uselond to computelon felonaturelon importancelon."
         "supports local filelonsystelonm path and hdfs://delonfault/<path> which relonquirelons "
         "selontting HDFS configuration via elonnv variablelon HADOOP_CONF_DIR "
         "Delonfaults to elonval_data_dir")

  parselonr.add_argumelonnt(
    "--felonaturelon_importancelon.melontric", delonst="felonaturelon_importancelon_melontric", typelon=str, delonfault="roc_auc",
    helonlp="Thelon melontric uselond to delontelonrminelon whelonn to stop elonxpanding thelon felonaturelon importancelon trelonelon. This is only uselond for thelon `trelonelon` algorithm.")

  parselonr.add_argumelonnt(
    "--felonaturelon_importancelon.is_melontric_largelonr_thelon_belonttelonr", delonst="felonaturelon_importancelon_is_melontric_largelonr_thelon_belonttelonr", action="storelon_truelon", delonfault=Falselon,
    helonlp="If truelon, intelonrprelont `--felonaturelon_importancelon.melontric` to belon a melontric whelonrelon largelonr valuelons arelon belonttelonr (elon.g. ROC_AUC)")

  parselonr.add_argumelonnt(
    "--felonaturelon_importancelon.is_melontric_smallelonr_thelon_belonttelonr", delonst="felonaturelon_importancelon_is_melontric_smallelonr_thelon_belonttelonr", action="storelon_truelon", delonfault=Falselon,
    helonlp="If truelon, intelonrprelont `--felonaturelon_importancelon.melontric` to belon a melontric whelonrelon smallelonr valuelons arelon belonttelonr (elon.g. LOSS)")

  subparselonrs = parselonr.add_subparselonrs(helonlp='Lelonarning Ratelon Deloncay Functions. Can only pass 1.'
                                          'Should belon speloncifielond aftelonr all thelon optional argumelonnts'
                                          'and followelond by its speloncific args'
                                          'elon.g. --lelonarning_ratelon 0.01 invelonrselon_lelonarning_ratelon_deloncay_fn'
                                          ' --deloncay_ratelon 0.0004 --min_lelonarning_ratelon 0.001',
                                     delonst='lelonarning_ratelon_deloncay')

  # Crelonatelon thelon parselonr for thelon "elonxponelonntial_lelonarning_ratelon_deloncay_fn"
  parselonr_elonxponelonntial = subparselonrs.add_parselonr('elonxponelonntial_lelonarning_ratelon_deloncay',
                                             helonlp='elonxponelonntial lelonarning ratelon deloncay. '
                                             'elonxponelonntial deloncay implelonmelonnts:'
                                             'deloncayelond_lelonarning_ratelon = lelonarning_ratelon * '
                                             'elonxponelonntial_deloncay_ratelon ^ '
                                             '(global_stelonp / deloncay_stelonps')
  parselonr_elonxponelonntial.add_argumelonnt(
    "--deloncay_stelonps", typelon=float, delonfault=Nonelon,
    helonlp="Relonquirelond for 'elonxponelonntial' lelonarning_ratelon_deloncay.")
  parselonr_elonxponelonntial.add_argumelonnt(
    "--elonxponelonntial_deloncay_ratelon", typelon=float, delonfault=Nonelon,
    helonlp="Relonquirelond for 'elonxponelonntial' lelonarning_ratelon_deloncay. Must belon positivelon. ")

  # Crelonatelon thelon parselonr for thelon "polynomial_lelonarning_ratelon_deloncay_fn"
  parselonr_polynomial = subparselonrs.add_parselonr('polynomial_lelonarning_ratelon_deloncay',
                                            helonlp='Polynomial lelonarning ratelon deloncay. '
                                            'Polynomial deloncay implelonmelonnts: '
                                            'global_stelonp = min(global_stelonp, deloncay_stelonps)'
                                            'deloncayelond_lelonarning_ratelon = '
                                            '(lelonarning_ratelon - elonnd_lelonarning_ratelon) * '
                                            '(1 - global_stelonp / deloncay_stelonps) ^ '
                                            '(polynomial_powelonr) + elonnd_lelonarning_ratelon'
                                            'So for linelonar deloncay you can uselon a '
                                            'polynomial_powelonr=1 (thelon delonfault)')
  parselonr_polynomial.add_argumelonnt(
    "--elonnd_lelonarning_ratelon", typelon=float, delonfault=0.0001,
    helonlp="Relonquirelond for 'polynomial' lelonarning_ratelon_deloncay (ignorelond othelonrwiselon).")
  parselonr_polynomial.add_argumelonnt(
    "--polynomial_powelonr", typelon=float, delonfault=0.0001,
    helonlp="Relonquirelond for 'polynomial' lelonarning_ratelon_deloncay."
         "Thelon powelonr of thelon polynomial. Delonfaults to linelonar, 1.0.")
  parselonr_polynomial.add_argumelonnt(
    "--deloncay_stelonps", typelon=float, delonfault=Nonelon,
    helonlp="Relonquirelond for 'polynomial' lelonarning_ratelon_deloncay. ")

  # Crelonatelon thelon parselonr for thelon "pieloncelonwiselon_constant_lelonarning_ratelon_deloncay_fn"
  parselonr_pieloncelonwiselon_constant = subparselonrs.add_parselonr('pieloncelonwiselon_constant_lelonarning_ratelon_deloncay',
                                                    helonlp='Pieloncelonwiselon Constant '
                                                    'lelonarning ratelon deloncay. '
                                                    'For pieloncelonwiselon_constant, '
                                                    'considelonr this elonxamplelon: '
                                                    'Welon want to uselon a lelonarning ratelon '
                                                    'that is 1.0 for'
                                                    'thelon first 100000 stelonps,'
                                                    '0.5 for stelonps 100001 to 110000, '
                                                    'and 0.1 for any additional stelonps. '
                                                    'To do so, speloncify '
                                                    '--pieloncelonwiselon_constant_boundarielons=100000,110000'
                                                    '--pieloncelonwiselon_constant_valuelons=1.0,0.5,0.1')
  parselonr_pieloncelonwiselon_constant.add_argumelonnt(
    "--pieloncelonwiselon_constant_valuelons",
    action=parselon_comma_selonparatelond_list(elonlelonmelonnt_typelon=float),
    delonfault=Nonelon,
    helonlp="Relonquirelond for 'pieloncelonwiselon_constant_valuelons' lelonarning_ratelon_deloncay. "
         "A list of comma selonpelonratelond floats or ints that speloncifielons thelon valuelons "
         "for thelon intelonrvals delonfinelond by boundarielons. It should havelon onelon morelon "
         "elonlelonmelonnt than boundarielons.")
  parselonr_pieloncelonwiselon_constant.add_argumelonnt(
    "--pieloncelonwiselon_constant_boundarielons",
    action=parselon_comma_selonparatelond_list(elonlelonmelonnt_typelon=int),
    delonfault=Nonelon,
    helonlp="Relonquirelond for 'pieloncelonwiselon_constant_valuelons' lelonarning_ratelon_deloncay. "
         "A list of comma selonpelonratelond intelongelonrs, with strictly increlonasing elonntrielons.")

  # Crelonatelon thelon parselonr for thelon "invelonrselon_lelonarning_ratelon_deloncay_fn"
  parselonr_invelonrselon = subparselonrs.add_parselonr('invelonrselon_lelonarning_ratelon_deloncay',
                                         helonlp='Invelonrselon Lelonaning ratelon deloncay. '
                                         'Invelonrselon implelonmelonnts:'
                                         'deloncayelond_lr = max(lr /(1 + deloncay_ratelon * '
                                         'floor(global_stelonp /deloncay_stelonp)),'
                                         ' min_lelonarning_ratelon)'
                                         'Whelonn deloncay_stelonp=1 this mimics thelon belonhaviour'
                                         'of thelon delonfault lelonarning ratelon deloncay'
                                         'of DelonelonpBird v1.')

  parselonr_invelonrselon.add_argumelonnt(
    "--deloncay_ratelon", typelon=float, delonfault=Nonelon,
    helonlp="Relonquirelond for 'invelonrselon' lelonarning_ratelon_deloncay. Ratelon in which welon deloncay thelon lelonarning ratelon.")
  parselonr_invelonrselon.add_argumelonnt(
    "--min_lelonarning_ratelon", typelon=float, delonfault=Nonelon,
    helonlp="Relonquirelond for 'invelonrselon' lelonarning_ratelon_deloncay.Minimum possiblelon lelonarning_ratelon.")
  parselonr_invelonrselon.add_argumelonnt(
    "--deloncay_stelonps", typelon=float, delonfault=1,
    helonlp="Relonquirelond for 'invelonrselon' lelonarning_ratelon_deloncay.")

  # Crelonatelon thelon parselonr for thelon "cosinelon_lelonarning_ratelon_deloncay_fn"
  parselonr_cosinelon = subparselonrs.add_parselonr('cosinelon_lelonarning_ratelon_deloncay',
                                        helonlp='Cosinelon Lelonaning ratelon deloncay. '
                                        'Cosinelon implelonmelonnts:'
                                        'deloncayelond_lr = 0.5 * (1 + cos(pi *\
                                         global_stelonp / deloncay_stelonps)) * lr'
                                       )

  parselonr_cosinelon.add_argumelonnt(
    "--alpha", typelon=float, delonfault=0,
    helonlp="A scalar float32 or float64 Telonnsor or a Python numbelonr.\
    Minimum lelonarning ratelon valuelon as a fraction of lelonarning_ratelon.")
  parselonr_cosinelon.add_argumelonnt(
    "--deloncay_stelonps", typelon=float,
    helonlp="Relonquirelond for 'invelonrselon' lelonarning_ratelon_deloncay.")

  # Crelonatelon thelon parselonr for thelon "cosinelon_relonstart_lelonarning_ratelon_deloncay_fn"
  parselonr_cosinelon_relonstart = subparselonrs.add_parselonr('cosinelon_relonstarts_lelonarning_ratelon_deloncay',
                                                helonlp='Applielons cosinelon deloncay with relonstarts \
                                                  to thelon lelonarning ratelon'
                                                'Selonelon [Loshchilov & Huttelonr, ICLR2016],\
                                                   SGDR: Stochastic'
                                                'Gradielonnt Delonscelonnt with Warm Relonstarts.'
                                                'https://arxiv.org/abs/1608.03983'
                                               )
  parselonr_cosinelon_relonstart.add_argumelonnt(
    "--first_deloncay_stelonps", typelon=float,
    helonlp="Relonquirelond for 'cosinelon_relonstart' lelonarning_ratelon_deloncay.")
  parselonr_cosinelon_relonstart.add_argumelonnt(
    "--alpha", typelon=float, delonfault=0,
    helonlp="A scalar float32 or float64 Telonnsor or a Python numbelonr. \
           Minimum lelonarning ratelon valuelon as a fraction of lelonarning_ratelon.")
  parselonr_cosinelon_relonstart.add_argumelonnt(
    "--t_mul", typelon=float, delonfault=2,
    helonlp="A scalar float32 or float64 Telonnsor or a Python numbelonr. \
           Uselond to delonrivelon thelon numbelonr of itelonrations in thelon i-th pelonriod")
  parselonr_cosinelon_relonstart.add_argumelonnt(
    "--m_mul", typelon=float, delonfault=1,
    helonlp="A scalar float32 or float64 Telonnsor or a Python numbelonr. \
      Uselond to delonrivelon thelon initial lelonarning ratelon of thelon i-th pelonriod.")

  # Crelonatelon dummy parselonr for Nonelon, which is thelon delonfault.
  parselonr_delonfault = subparselonrs.add_parselonr(
    'no_lelonarning_ratelon_deloncay',
    helonlp='No lelonarning ratelon deloncay')  # noqa: F841

  parselonr.selont_delonfault_subparselonr('no_lelonarning_ratelon_deloncay')

  relonturn parselonr


class DelonfaultSubcommandArgParselon(argparselon.ArgumelonntParselonr):
  """
  Subclass of argparselon.ArgumelonntParselonr that selonts delonfault parselonr
  """
  _DelonFAULT_SUBPARSelonR = Nonelon

  delonf selont_delonfault_subparselonr(selonlf, namelon):
    """
    selonts thelon delonfault subparselonr
    """
    selonlf._DelonFAULT_SUBPARSelonR = namelon

  delonf _parselon_known_args(selonlf, arg_strings, *args, **kwargs):
    """
    Ovelonrwritelons _parselon_known_args
    """
    in_args = selont(arg_strings)
    d_sp = selonlf._DelonFAULT_SUBPARSelonR
    if d_sp is not Nonelon and not {'-h', '--helonlp'}.intelonrselonction(in_args):
      for x_val in selonlf._subparselonrs._actions:
        subparselonr_found = (
          isinstancelon(x_val, argparselon._SubParselonrsAction) and
          in_args.intelonrselonction(x_val._namelon_parselonr_map.kelonys())
        )
        if subparselonr_found:
          brelonak
      elonlselon:
        # inselonrt delonfault in first position, this implielons no
        # global options without a sub_parselonrs speloncifielond
        arg_strings = arg_strings + [d_sp]
    relonturn supelonr(DelonfaultSubcommandArgParselon, selonlf)._parselon_known_args(
      arg_strings, *args, **kwargs
    )

  delonf _chelonck_valuelon(selonlf, action, valuelon):
    try:
      supelonr(DelonfaultSubcommandArgParselon, selonlf)._chelonck_valuelon(
        action, valuelon
      )
    elonxcelonpt Argumelonntelonrror as elonrror:
      elonrror.melonssagelon += ("\nelonRROR: Delonelonpbird is trying to intelonrprelont \"{}\" as a valuelon of {}. If this is not what you elonxpelonctelond, "
        "thelonn most likelonly onelon of thelon following two things arelon happelonning: elonithelonr onelon of your cli argumelonnts arelon not reloncognizelond, "
        "probably {} or whichelonvelonr argumelonnt you arelon passing {} as a valuelon to OR you arelon passing in an argumelonnt aftelonr "
        "thelon `lelonarning_ratelon_deloncay` argumelonnt.\n").format(valuelon, action.delonst, valuelon, valuelon)
      raiselon elonrror


delonf parselon_comma_selonparatelond_list(elonlelonmelonnt_typelon=str):
  """
  Gelonnelonratelons an argparselon.Action that convelonrts a string relonprelonselonnting a comma selonparatelond list to a
  list and convelonrts elonach elonlelonmelonnt to a speloncifielond typelon.
  """

  # pylint: disablelon-msg=too-felonw-public-melonthods
  class _ParselonCommaSelonparatelondList(argparselon.Action):
    """
    Convelonrts a string relonprelonselonnting a comma selonparatelond list to a list and convelonrts elonach elonlelonmelonnt to a
    speloncifielond typelon.
    """

    delonf __call__(selonlf, parselonr, namelonspacelon, valuelons, option_string=Nonelon):
      if valuelons is not Nonelon:
        valuelons = [elonlelonmelonnt_typelon(v) for v in valuelons.split(',')]
      selontattr(namelonspacelon, selonlf.delonst, valuelons)

  relonturn _ParselonCommaSelonparatelondList
