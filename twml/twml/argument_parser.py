# pyl nt: d sable=protected-access, argu nts-d ffer
"""
Command-l ne argu nt pars ng for t  Tra ner.
"""
 mport argparse
from argparse  mport Argu ntError
from operator  mport attrgetter
 mport tempf le

 mport twml
 mport tensorflow.compat.v1 as tf


SER AL = "ser al"
TREE = "tree"
LOG_LEVELS = {
  "debug": tf.logg ng.DEBUG,
  " nfo": tf.logg ng. NFO,
  "warn": tf.logg ng.WARN,
  "error": tf.logg ng.ERROR}


class Sort ng lpFormatter(argparse. lpFormatter):
  """
  Used to sort args alphabet cally  n t   lp  ssage.
  """

  def add_argu nts(self, act ons):
    act ons = sorted(act ons, key=attrgetter('opt on_str ngs'))
    super(Sort ng lpFormatter, self).add_argu nts(act ons)


def _set_log_level(level=None):
  """Sets t  tensorflow log level to t   nput level."""
   f level  s None:
    return None
  level = level.lo r()
   f level not  n LOG_LEVELS.keys():
    ra se ValueError(f"Unexpected log level {level} was g ven but expected one of {LOG_LEVELS.keys()}.")
  tf.logg ng.set_verbos y(LOG_LEVELS[level])
  tf.logg ng. nfo(f"Sett ng tensorflow logg ng level to {level} or {LOG_LEVELS[level]}")
  return level


def get_tra ner_parser():
  """
  Add common commandl ne args to parse for t  Tra ner class.
  Typ cally, t  user calls t  funct on and t n parses cmd-l ne argu nts
   nto an argparse.Na space object wh ch  s t n passed to t  Tra ner constructor
  v a t  params argu nt.

  See t  `code <_modules/twml/argu nt_parser.html#get_tra ner_parser>`_
  for a l st and descr pt on of all cmd-l ne argu nts.

  Args:
    learn ng_rate_decay:
      Defaults to False. W n True, parses learn ng rate decay argu nts.

  Returns:
    argparse.Argu ntParser  nstance w h so  useful args already added.
  """
  parser = twml.DefaultSubcommandArgParse(formatter_class=Sort ng lpFormatter)

  parser.add_argu nt(
    "--save_d r", type=str, default=tempf le.mkdtemp(),
     lp="Path to t  tra n ng result d rectory."
         "supports local f lesystem path and hdfs://default/<path> wh ch requ res "
         "sett ng HDFS conf gurat on v a env var able HADOOP_CONF_D R ")
  parser.add_argu nt(
    "--export_d r", type=str, default=None,
     lp="Path to t  d rectory to export a SavedModel for pred ct on servers.")
  parser.add_argu nt(
    "--log_aggregat on_app_ d", type=str, default=None,
     lp="spec fy app_ d for log aggregat on. d sabled by default.")
  parser.add_argu nt(
    "--tra n.batch_s ze", "--tra n_batch_s ze", type= nt, default=32,
    dest='tra n_batch_s ze',
     lp="number of samples per tra n ng batch")
  parser.add_argu nt(
    "--eval.batch_s ze", "--eval_batch_s ze", type= nt, default=32,
    dest='eval_batch_s ze',
     lp="number of samples per cross-val dat on batch. Defaults to tra n_batch_s ze")
  parser.add_argu nt(
    "--tra n.learn ng_rate", "--learn ng_rate", type=float, default=0.002,
    dest='learn ng_rate',
     lp="learn ng rate. Scales t  grad ent update.")
  parser.add_argu nt(
    "--tra n.steps", "--tra n_steps", type= nt, default=-1,
    dest='tra n_steps',
     lp="number of tra n ng batc s before runn ng evaluat on."
         "Defaults to -1 (runs through ent re dataset). "
         "Only used for Tra ner.[tra n,learn]. "
         "For Tra ner.tra n_and_evaluate, use tra n.max_steps  nstead. ")
  parser.add_argu nt(
    "--eval.steps", "--eval_steps", type= nt, default=-1,
    dest="eval_steps",
     lp="number of steps per evaluat on. Each batch  s a step."
         "Defaults to -1 (runs through ent re dataset). ")
  parser.add_argu nt(
    "--eval.per od", "--eval_per od", type= nt, default=600,
    dest="eval_per od",
     lp="Tra ner.tra n_and_evaluate wa s for t  long after each evaluat on. "
         "Defaults to 600 seconds (evaluate every ten m nutes). "
         "Note that anyth ng lo r than 10*60seconds  s probably a bad  dea because TF saves "
         "c ckpo nts every 10m ns by default. eval.delay  s t   to wa  before do ng f rst eval. "
         "eval.per od  s t   bet en success ve evals.")
  parser.add_argu nt(
    "--eval.delay", "--eval_delay", type= nt, default=120,
    dest="eval_delay",
     lp="Tra ner.tra n_and_evaluate wa s for t  long before perform ng t  f rst evaluat on"
         "Defaults to 120 seconds (evaluate after f rst 2 m nutes of tra n ng). "
         "eval.delay  s t   to wa  before do ng f rst eval. "
         "eval.per od  s t   bet en success ve evals.")
  parser.add_argu nt(
    "--tra n.max_steps", "--tra n_max_steps", type= nt, default=None,
    dest="tra n_max_steps",
     lp="Stop tra n ng after t  many global steps. Each tra n ng batch  s  s own step."
         " f set to None, step after one tra n()/evaluate() call. Useful w n tra n.steps=-1."
         " f set to a non-pos  ve value, loop forever. Usually useful w h early stopp ng.")
  parser.add_argu nt(
    "--tra n.log_ tr cs", dest="tra n_log_ tr cs", act on="store_true", default=False,
     lp="Set t  to true to see  tr cs dur ng tra n ng. "
         "WARN NG:  tr cs dur ng tra n ng does not represent model performance. "
         "WARN NG: use for debugg ng only as t  slows down tra n ng.")
  parser.add_argu nt(
    "--tra n.early_stop_pat ence", "--early_stop_pat ence", type= nt, default=-1,
    dest="early_stop_pat ence",
     lp="max number of evaluat ons (epochs) to wa  for an  mprove nt  n t  early_stop_ tr c."
         "Defaults to -1 (no early-stopp ng)."
         "NOTE: T  can not be enabled w n --d str buted  s also set.")
  parser.add_argu nt(
    "--tra n.early_stop_tolerance", "--early_stop_tolerance", type=float, default=0,
    dest="early_stop_tolerance",
     lp="a non-negat ve tolerance for compar ng early_stop_ tr c."
         "e.g. w n max m z ng t  cond  on  s current_ tr c > best_ tr c + tolerance."
         "Defaults to 0.")
  parser.add_argu nt(
    "--tra n.dataset_shards", "--tra n_dataset_shards",
    dest="tra n_dataset_shards",
    type= nt, default=None,
     lp="An  nt value that  nd cates t  number of part  ons (shards) for t  dataset. T   s"
    " useful for cod st llat on and ot r techn ques that requ re each worker to tra n on d sjo nt"
    " part  ons of t  dataset.")
  parser.add_argu nt(
    "--tra n.dataset_shard_ ndex", "--tra n_dataset_shard_ ndex",
    dest="tra n_dataset_shard_ ndex",
    type= nt, default=None,
     lp="An  nt value (start ng at zero) that  nd cates wh ch part  on (shard) of t  dataset"
    " to use  f --tra n.dataset_shards  s set.")
  parser.add_argu nt(
    "--cont nue_from_c ckpo nt", dest="cont nue_from_c ckpo nt", act on="store_true",
     lp="DEPRECATED. T  opt on  s currently a no-op."
    " Cont nu ng from t  prov ded c ckpo nt  s now t  default."
    " Use --overwr e_save_d r  f   would l ke to overr de    nstead"
    " and restart tra n ng from scratch.")
  parser.add_argu nt(
    "--overwr e_save_d r", dest="overwr e_save_d r", act on="store_true",
     lp="Delete t  contents of t  current save_d r  f   ex sts")
  parser.add_argu nt(
    "--data_threads", "--num_threads", type= nt, default=2,
    dest="num_threads",
     lp="Number of threads to use for load ng t  dataset. "
         "num_threads  s deprecated and to be removed  n future vers ons. Use data_threads.")
  parser.add_argu nt(
    "--max_durat on", "--max_durat on", type=float, default=None,
    dest="max_durat on",
     lp="Max mum durat on ( n secs) that tra n ng/val dat on w ll be allo d to run for before be ng automat cally term nated.")
  parser.add_argu nt(
    "--num_workers", type= nt, default=None,
     lp="Number of workers to use w n tra n ng  n hogw ld manner on a s ngle node.")
  parser.add_argu nt(
    "--d str buted", dest="d str buted", act on="store_true",
     lp="Pass t  flag to use tra n_and_evaluate to tra n  n a d str buted fash on"
         "NOTE:   can not use early stopp ng w n --d str buted  s enabled"
  )
  parser.add_argu nt(
    "--d str buted_tra n ng_cleanup",
    dest="d str buted_tra n ng_cleanup",
    act on="store_true",
     lp="Set  f us ng d str buted tra n ng on GKE to stop Tw terSetDeploy nt"
         "from cont nu ng tra n ng upon restarts (w ll be deprecated once   m grate off"
         "Tw terSetDeploy nt for d str buted tra n ng on GKE)."
  )
  parser.add_argu nt(
    "--d sable_auto_ps_shutdown", default=False, act on="store_true",
     lp="D sable t  funct onal y of automat cally shutt ng down para ter server after "
         "d str buted tra n ng complete (e  r succeed or fa led)."
  )
  parser.add_argu nt(
    "--d sable_tensorboard", default=False, act on="store_true",
     lp="Do not start t  TensorBoard server."
  )
  parser.add_argu nt(
    "--tensorboard_port", type= nt, default=None,
     lp="Port for tensorboard to run on.  gnored  f --d sable_tensorboard  s set.")
  parser.add_argu nt(
    "-- alth_port", type= nt, default=None,
     lp="Port to l sten on for  alth-related endpo nts (e.g. graceful shutdown)."
         "Not user-fac ng as    s set automat cally by t  twml_cl ."
  )
  parser.add_argu nt(
    "--stats_port", type= nt, default=None,
     lp="Port to l sten on for stats endpo nts"
  )
  parser.add_argu nt(
    "--exper  nt_track ng_path",
    dest="exper  nt_track ng_path",
    type=str, default=None,
     lp="T  track ng path of t  exper  nt. Format: \
        user_na :project_na :exper  nt_na :run_na . T  path  s used to track and d splay \
        a record of t  exper  nt on ML Dashboard. Note: t  embedded exper  nt track ng  s \
        d sabled w n t  deprecated Model Repo TrackRun  s used  n y  model conf g. ")
  parser.add_argu nt(
    "--d sable_exper  nt_track ng",
    dest="d sable_exper  nt_track ng",
    act on="store_true",
     lp="W t r exper  nt track ng should be d sabled.")
  parser.add_argu nt(
    "--conf g.save_c ckpo nts_secs", "--save_c ckpo nts_secs", type= nt, default=600,
    dest='save_c ckpo nts_secs',
     lp="Conf gures t  tf.est mator.RunConf g.save_c ckpo nts_secs attr bute. "
    "Spec f es how often c ckpo nts are saved  n seconds. Defaults to 10*60 seconds.")
  parser.add_argu nt(
    "--conf g.keep_c ckpo nt_max", "--keep_c ckpo nt_max", type= nt, default=20,
    dest='keep_c ckpo nt_max',
     lp="Conf gures t  tf.est mator.RunConf g.keep_c ckpo nt_max attr bute. "
    "Spec f es how many c ckpo nts to keep. Defaults to 20.")
  parser.add_argu nt(
    "--conf g.tf_random_seed", "--tf_random_seed", type= nt, default=None,
    dest='tf_random_seed',
     lp="Conf gures t  tf.est mator.RunConf g.tf_random_seed attr bute. "
         "Spec f es t  seed to use. Defaults to None.")
  parser.add_argu nt(
    "--opt m zer", type=str, default='SGD',
     lp="Opt m zer to use: SGD (Default), Adagrad, Adam, Ftrl, Mo ntum, RMSProp, LazyAdam, DGC.")
  parser.add_argu nt(
    "--grad ent_no se_scale", type=float, default=None,
     lp="adds 0- an normal no se scaled by t  value. Defaults to None.")
  parser.add_argu nt(
    "--cl p_grad ents", type=float, default=None,
     lp=" f spec f ed, a global cl pp ng  s appl ed to prevent "
         "t  norm of t  grad ent to exceed t  value. Defaults to None.")
  parser.add_argu nt(
    "--dgc.dens y", "--dgc_dens y", type=float, default=0.1,
    dest="dgc_dens y",
     lp="Spec f es grad ent dens y level w n us ng deep grad ent compress on opt m zer."
         "E.g., default value be ng 0.1  ans that only top 10%% most s gn f cant rows "
         "(based on absolute value sums) are kept."
  )
  parser.add_argu nt(
    "--dgc.dens y_decay", "--dgc_dens y_decay", type=bool, default=True,
    dest="dgc_dens y_decay",
     lp="Spec f es w t r to (exponent ally) decay t  grad ent dens y level w n"
         " do ng grad ent compress on.  f set 'False', t  'dens y_decay_steps', "
         "'dens y_decay_rate' and 'm n_dens y' argu nts w ll be  gnored."
  )
  parser.add_argu nt(
    "--dgc.dens y_decay_steps", "--dgc_dens y_decay_steps", type= nt, default=10000,
    dest="dgc_dens y_decay_steps",
     lp="Spec f es t  step  nterval to perform dens y decay."
  )
  parser.add_argu nt(
    "--dgc.dens y_decay_rate", "--dgc_dens y_decay_rate", type=float, default=0.5,
    dest="dgc_dens y_decay_rate",
     lp="Spec f es t  decay rate w n perfom ng dens y decay."
  )
  parser.add_argu nt(
    "--dgc.m n_dens y", "--dgc_m n_dens y", type=float, default=0.1,
    dest="dgc_m n_dens y",
     lp="Spec f es t  m n mum dens y level w n perfom ng dens y decay."
  )
  parser.add_argu nt(
    "--dgc.accumulat on", "--dgc_accumulat on", type=bool, default=False,
    dest="dgc_accumulat on",
     lp="Spec f es w t r to accumulate small grad ents w n us ng deep grad ent compress on "
         "opt m zer."
  )
  parser.add_argu nt(
    "--show_opt m zer_summar es", dest="show_opt m zer_summar es", act on="store_true",
     lp="W n spec f ed, d splays grad ents and learn ng rate  n tensorboard."
    "Turn ng   on has 10-20%% performance h . Enable for debugg ng only")

  parser.add_argu nt(
    "--num_mkl_threads", dest="num_mkl_threads", default=1, type= nt,
     lp="Spec f es how many threads to use for MKL"
    " nter_op_ parallel sm_threds  s set to TWML_NUM_CPUS / num_mkl_threads."
    " ntra_op_parallel sm_threads  s set to num_mkl_threads.")

  parser.add_argu nt("--verbos y", type=_set_log_level, cho ces=LOG_LEVELS.keys(), default=None,
     lp="Sets log level to a g ven verbos y.")

  parser.add_argu nt(
    "--feature_ mportance.algor hm", dest="feature_ mportance_algor hm",
    type=str, default=TREE, cho ces=[SER AL, TREE],
     lp="""
    T re are two algor hms that t  module supports, `ser al` and `tree`.
      T  `ser al` algor hm computes feature  mportances for each feature, and
      t  `tree` algor hm groups features by feature na  pref x, computes feature
       mportances for groups of features, and t n only 'zooms- n' on a group w n t 
       mportance  s greater than t  `--feature_ mportance.sens  v y` value. T  `tree` algor hm
      w ll usually run faster, but for relat vely un mportant features   w ll only compute an
      upper bound rat r than an exact  mportance value.   suggest that users generally st ck
      to t  `tree` algor hm, unless  f t y have a very small number of features or
      near-random model performance.
      """)

  parser.add_argu nt(
    "--feature_ mportance.sens  v y", dest="feature_ mportance_sens  v y", type=float, default=0.03,
     lp="""
    T  max mum amount that permut ng a feature group can cause t  model performance (determ ned
      by `feature_ mportance. tr c`) to drop before t  algor hm dec des to not expand t  feature
      group. T   s only used for t  `tree` algor hm.
    """)

  parser.add_argu nt(
    "--feature_ mportance.dont_bu ld_tree", dest="dont_bu ld_tree", act on="store_true", default=False,
     lp="""
     f True, don't bu ld t  feature tr e for t  tree algor hm and only use t  extra_groups
    """)

  parser.add_argu nt(
    "--feature_ mportance.spl _feature_group_on_per od", dest="spl _feature_group_on_per od", act on="store_true", default=False,
     lp=" f true, spl  feature groups by t  per od rat r than t  opt mal pref x. Only used for t  TREE algor hm")

  parser.add_argu nt(
    "--feature_ mportance.example_count", dest="feature_ mportance_example_count", type= nt, default=10000,
     lp="""
    T  number of examples used to compute feature  mportance.
    Larger values y eld more rel able results, but also take longer to compute.
    T se records are loaded  nto  mory. T  number  s agnost c to batch s ze.
    """)

  parser.add_argu nt(
    "--feature_ mportance.data_d r", dest="feature_ mportance_data_d r", type=str, default=None,
     lp="Path to t  dataset used to compute feature  mportance."
         "supports local f lesystem path and hdfs://default/<path> wh ch requ res "
         "sett ng HDFS conf gurat on v a env var able HADOOP_CONF_D R "
         "Defaults to eval_data_d r")

  parser.add_argu nt(
    "--feature_ mportance. tr c", dest="feature_ mportance_ tr c", type=str, default="roc_auc",
     lp="T   tr c used to determ ne w n to stop expand ng t  feature  mportance tree. T   s only used for t  `tree` algor hm.")

  parser.add_argu nt(
    "--feature_ mportance. s_ tr c_larger_t _better", dest="feature_ mportance_ s_ tr c_larger_t _better", act on="store_true", default=False,
     lp=" f true,  nterpret `--feature_ mportance. tr c` to be a  tr c w re larger values are better (e.g. ROC_AUC)")

  parser.add_argu nt(
    "--feature_ mportance. s_ tr c_smaller_t _better", dest="feature_ mportance_ s_ tr c_smaller_t _better", act on="store_true", default=False,
     lp=" f true,  nterpret `--feature_ mportance. tr c` to be a  tr c w re smaller values are better (e.g. LOSS)")

  subparsers = parser.add_subparsers( lp='Learn ng Rate Decay Funct ons. Can only pass 1.'
                                          'Should be spec f ed after all t  opt onal argu nts'
                                          'and follo d by  s spec f c args'
                                          'e.g. --learn ng_rate 0.01  nverse_learn ng_rate_decay_fn'
                                          ' --decay_rate 0.0004 --m n_learn ng_rate 0.001',
                                     dest='learn ng_rate_decay')

  # Create t  parser for t  "exponent al_learn ng_rate_decay_fn"
  parser_exponent al = subparsers.add_parser('exponent al_learn ng_rate_decay',
                                              lp='Exponent al learn ng rate decay. '
                                             'Exponent al decay  mple nts:'
                                             'decayed_learn ng_rate = learn ng_rate * '
                                             'exponent al_decay_rate ^ '
                                             '(global_step / decay_steps')
  parser_exponent al.add_argu nt(
    "--decay_steps", type=float, default=None,
     lp="Requ red for 'exponent al' learn ng_rate_decay.")
  parser_exponent al.add_argu nt(
    "--exponent al_decay_rate", type=float, default=None,
     lp="Requ red for 'exponent al' learn ng_rate_decay. Must be pos  ve. ")

  # Create t  parser for t  "polynom al_learn ng_rate_decay_fn"
  parser_polynom al = subparsers.add_parser('polynom al_learn ng_rate_decay',
                                             lp='Polynom al learn ng rate decay. '
                                            'Polynom al decay  mple nts: '
                                            'global_step = m n(global_step, decay_steps)'
                                            'decayed_learn ng_rate = '
                                            '(learn ng_rate - end_learn ng_rate) * '
                                            '(1 - global_step / decay_steps) ^ '
                                            '(polynom al_po r) + end_learn ng_rate'
                                            'So for l near decay   can use a '
                                            'polynom al_po r=1 (t  default)')
  parser_polynom al.add_argu nt(
    "--end_learn ng_rate", type=float, default=0.0001,
     lp="Requ red for 'polynom al' learn ng_rate_decay ( gnored ot rw se).")
  parser_polynom al.add_argu nt(
    "--polynom al_po r", type=float, default=0.0001,
     lp="Requ red for 'polynom al' learn ng_rate_decay."
         "T  po r of t  polynom al. Defaults to l near, 1.0.")
  parser_polynom al.add_argu nt(
    "--decay_steps", type=float, default=None,
     lp="Requ red for 'polynom al' learn ng_rate_decay. ")

  # Create t  parser for t  "p ecew se_constant_learn ng_rate_decay_fn"
  parser_p ecew se_constant = subparsers.add_parser('p ecew se_constant_learn ng_rate_decay',
                                                     lp='P ecew se Constant '
                                                    'learn ng rate decay. '
                                                    'For p ecew se_constant, '
                                                    'cons der t  example: '
                                                    '  want to use a learn ng rate '
                                                    'that  s 1.0 for'
                                                    't  f rst 100000 steps,'
                                                    '0.5 for steps 100001 to 110000, '
                                                    'and 0.1 for any add  onal steps. '
                                                    'To do so, spec fy '
                                                    '--p ecew se_constant_boundar es=100000,110000'
                                                    '--p ecew se_constant_values=1.0,0.5,0.1')
  parser_p ecew se_constant.add_argu nt(
    "--p ecew se_constant_values",
    act on=parse_comma_separated_l st(ele nt_type=float),
    default=None,
     lp="Requ red for 'p ecew se_constant_values' learn ng_rate_decay. "
         "A l st of comma seperated floats or  nts that spec f es t  values "
         "for t   ntervals def ned by boundar es.   should have one more "
         "ele nt than boundar es.")
  parser_p ecew se_constant.add_argu nt(
    "--p ecew se_constant_boundar es",
    act on=parse_comma_separated_l st(ele nt_type= nt),
    default=None,
     lp="Requ red for 'p ecew se_constant_values' learn ng_rate_decay. "
         "A l st of comma seperated  ntegers, w h str ctly  ncreas ng entr es.")

  # Create t  parser for t  " nverse_learn ng_rate_decay_fn"
  parser_ nverse = subparsers.add_parser(' nverse_learn ng_rate_decay',
                                          lp=' nverse Lean ng rate decay. '
                                         ' nverse  mple nts:'
                                         'decayed_lr = max(lr /(1 + decay_rate * '
                                         'floor(global_step /decay_step)),'
                                         ' m n_learn ng_rate)'
                                         'W n decay_step=1 t  m m cs t  behav  '
                                         'of t  default learn ng rate decay'
                                         'of DeepB rd v1.')

  parser_ nverse.add_argu nt(
    "--decay_rate", type=float, default=None,
     lp="Requ red for ' nverse' learn ng_rate_decay. Rate  n wh ch   decay t  learn ng rate.")
  parser_ nverse.add_argu nt(
    "--m n_learn ng_rate", type=float, default=None,
     lp="Requ red for ' nverse' learn ng_rate_decay.M n mum poss ble learn ng_rate.")
  parser_ nverse.add_argu nt(
    "--decay_steps", type=float, default=1,
     lp="Requ red for ' nverse' learn ng_rate_decay.")

  # Create t  parser for t  "cos ne_learn ng_rate_decay_fn"
  parser_cos ne = subparsers.add_parser('cos ne_learn ng_rate_decay',
                                         lp='Cos ne Lean ng rate decay. '
                                        'Cos ne  mple nts:'
                                        'decayed_lr = 0.5 * (1 + cos(p  *\
                                         global_step / decay_steps)) * lr'
                                       )

  parser_cos ne.add_argu nt(
    "--alpha", type=float, default=0,
     lp="A scalar float32 or float64 Tensor or a Python number.\
    M n mum learn ng rate value as a fract on of learn ng_rate.")
  parser_cos ne.add_argu nt(
    "--decay_steps", type=float,
     lp="Requ red for ' nverse' learn ng_rate_decay.")

  # Create t  parser for t  "cos ne_restart_learn ng_rate_decay_fn"
  parser_cos ne_restart = subparsers.add_parser('cos ne_restarts_learn ng_rate_decay',
                                                 lp='Appl es cos ne decay w h restarts \
                                                  to t  learn ng rate'
                                                'See [Loshch lov & Hutter,  CLR2016],\
                                                   SGDR: Stochast c'
                                                'Grad ent Descent w h Warm Restarts.'
                                                'https://arx v.org/abs/1608.03983'
                                               )
  parser_cos ne_restart.add_argu nt(
    "--f rst_decay_steps", type=float,
     lp="Requ red for 'cos ne_restart' learn ng_rate_decay.")
  parser_cos ne_restart.add_argu nt(
    "--alpha", type=float, default=0,
     lp="A scalar float32 or float64 Tensor or a Python number. \
           M n mum learn ng rate value as a fract on of learn ng_rate.")
  parser_cos ne_restart.add_argu nt(
    "--t_mul", type=float, default=2,
     lp="A scalar float32 or float64 Tensor or a Python number. \
           Used to der ve t  number of  erat ons  n t   -th per od")
  parser_cos ne_restart.add_argu nt(
    "--m_mul", type=float, default=1,
     lp="A scalar float32 or float64 Tensor or a Python number. \
      Used to der ve t   n  al learn ng rate of t   -th per od.")

  # Create dum  parser for None, wh ch  s t  default.
  parser_default = subparsers.add_parser(
    'no_learn ng_rate_decay',
     lp='No learn ng rate decay')  # noqa: F841

  parser.set_default_subparser('no_learn ng_rate_decay')

  return parser


class DefaultSubcommandArgParse(argparse.Argu ntParser):
  """
  Subclass of argparse.Argu ntParser that sets default parser
  """
  _DEFAULT_SUBPARSER = None

  def set_default_subparser(self, na ):
    """
    sets t  default subparser
    """
    self._DEFAULT_SUBPARSER = na 

  def _parse_known_args(self, arg_str ngs, *args, **kwargs):
    """
    Overwr es _parse_known_args
    """
     n_args = set(arg_str ngs)
    d_sp = self._DEFAULT_SUBPARSER
     f d_sp  s not None and not {'-h', '-- lp'}. ntersect on( n_args):
      for x_val  n self._subparsers._act ons:
        subparser_found = (
           s nstance(x_val, argparse._SubParsersAct on) and
           n_args. ntersect on(x_val._na _parser_map.keys())
        )
         f subparser_found:
          break
      else:
        #  nsert default  n f rst pos  on, t   mpl es no
        # global opt ons w hout a sub_parsers spec f ed
        arg_str ngs = arg_str ngs + [d_sp]
    return super(DefaultSubcommandArgParse, self)._parse_known_args(
      arg_str ngs, *args, **kwargs
    )

  def _c ck_value(self, act on, value):
    try:
      super(DefaultSubcommandArgParse, self)._c ck_value(
        act on, value
      )
    except Argu ntError as error:
      error. ssage += ("\nERROR: Deepb rd  s try ng to  nterpret \"{}\" as a value of {}.  f t   s not what   expected, "
        "t n most l kely one of t  follow ng two th ngs are happen ng: E  r one of y  cl  argu nts are not recogn zed, "
        "probably {} or wh c ver argu nt   are pass ng {} as a value to OR   are pass ng  n an argu nt after "
        "t  `learn ng_rate_decay` argu nt.\n").format(value, act on.dest, value, value)
      ra se error


def parse_comma_separated_l st(ele nt_type=str):
  """
  Generates an argparse.Act on that converts a str ng represent ng a comma separated l st to a
  l st and converts each ele nt to a spec f ed type.
  """

  # pyl nt: d sable-msg=too-few-publ c- thods
  class _ParseCommaSeparatedL st(argparse.Act on):
    """
    Converts a str ng represent ng a comma separated l st to a l st and converts each ele nt to a
    spec f ed type.
    """

    def __call__(self, parser, na space, values, opt on_str ng=None):
       f values  s not None:
        values = [ele nt_type(v) for v  n values.spl (',')]
      setattr(na space, self.dest, values)

  return _ParseCommaSeparatedL st
