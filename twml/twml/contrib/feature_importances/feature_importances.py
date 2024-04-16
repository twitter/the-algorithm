# c ckstyle: noqa

 mport t  
from collect ons  mport defaultd ct

from com.tw ter.ml tastore.modelrepo.cl ent  mport ModelRepoCl ent
from com.tw ter.ml tastore.modelrepo.core  mport Feature mportance, FeatureNa s
from tw ter.deepb rd. o.ut l  mport match_feature_regex_l st

from twml.contr b.feature_ mportances. lpers  mport (
  _get_feature_na _from_conf g,
  _get_feature_types_from_records,
  _get_ tr cs_hook,
  _expand_pref x,
  longest_common_pref x,
  wr e_l st_to_hdfs_gf le)
from twml.contr b.feature_ mportances.feature_permutat on  mport Permuted nputFnFactory
from twml.track ng  mport Exper  ntTracker

from tensorflow.compat.v1  mport logg ng
from requests.except ons  mport HTTPError, RetryError
from queue  mport Queue


SER AL = "ser al"
TREE = "tree"
 ND V DUAL = " nd v dual"
GROUP = "Group"
ROC_AUC = "roc_auc"
RCE = "rce"
LOSS = "loss"


def _repart  on(feature_l st_queue, fna s_ftypes, spl _feature_group_on_per od):
  """
   erate through letters to part  on each feature by pref x, and t n put each tuple
    (pref x, feature_part  on)  nto t  feature_l st_queue
  Args:
    pref x (str): T  pref x shared by each feature  n l st_of_feature_types
    feature_l st_queue (Queue<(str, l st<(str, str)>)>): T  queue of feature groups
    fna s_ftypes (l st<(str, str)>): L st of (fna , ftype) pa rs. Each fna  beg ns w h pref x
    spl _feature_group_on_per od (str):  f true, requ re that feature groups end  n a per od
  Returns:
    Updated queue w h each group  n fna s_ftypes
  """
  assert len(fna s_ftypes) > 1

  spl _character = "."  f spl _feature_group_on_per od else None
  # Compute t  longest pref x of t  words
  pref x = longest_common_pref x(
    str ngs=[fna  for fna , _  n fna s_ftypes], spl _character=spl _character)

  # Separate t  features by pref x
  pref x_to_features = defaultd ct(l st)
  for fna , ftype  n fna s_ftypes:
    assert fna .startsw h(pref x)
    new_pref x = _expand_pref x(fna =fna , pref x=pref x, spl _character=spl _character)
    pref x_to_features[new_pref x].append((fna , ftype))

  # Add all of t  new part  ons to t  queue
  for new_pref x, fna _ftype_l st  n pref x_to_features. ems():
    extended_new_pref x = longest_common_pref x(
      str ngs=[fna  for fna , _  n fna _ftype_l st], spl _character=spl _character)
    assert extended_new_pref x.startsw h(new_pref x)
    feature_l st_queue.put((extended_new_pref x, fna _ftype_l st))
  return feature_l st_queue


def _ nfer_ f_ s_ tr c_larger_t _better(stopp ng_ tr c):
  #  nfers w t r a  tr c should be  nterpreted such that larger numbers are better (e.g. ROC_AUC), as opposed to
  #   larger numbers be ng worse (e.g. LOSS)
   f stopp ng_ tr c  s None:
    ra se ValueError("Error: Stopp ng  tr c cannot be None")
  el f stopp ng_ tr c.startsw h(LOSS):
    logg ng. nfo(" nterpret ng {} to be a  tr c w re larger numbers are worse".format(stopp ng_ tr c))
     s_ tr c_larger_t _better = False
  else:
    logg ng. nfo(" nterpret ng {} to be a  tr c w re larger numbers are better".format(stopp ng_ tr c))
     s_ tr c_larger_t _better = True
  return  s_ tr c_larger_t _better


def _c ck_w t r_tree_should_expand(basel ne_performance, computed_performance, sens  v y, stopp ng_ tr c,  s_ tr c_larger_t _better):
  """
  Returns True  f
    - t   tr c  s pos  ve (e.g. ROC_AUC) and computed_performance  s nontr v ally smaller than t  basel ne_performance
    - t   tr c  s negat ve (e.g. LOSS) and computed_performance  s nontr v ally larger than t  basel ne_performance
  """
  d fference = ((basel ne_performance[stopp ng_ tr c] - computed_performance[stopp ng_ tr c]) /
                 basel ne_performance[stopp ng_ tr c])

   f not  s_ tr c_larger_t _better:
      d fference = -d fference

  logg ng. nfo(
    "Found a {} d fference of {}. Sens  v y  s {}.".format("pos  ve"  f  s_ tr c_larger_t _better else "negat ve", d fference, sens  v y))
  return d fference > sens  v y


def _compute_mult ple_permuted_performances_from_tra ner(
    factory, fna _ftypes, tra ner, parse_fn, record_count):
  """Compute performances w h fna  and fype permuted
  """
   tr cs_hook = _get_ tr cs_hook(tra ner)
  tra ner._est mator.evaluate(
     nput_fn=factory.get_permuted_ nput_fn(
      batch_s ze=tra ner._params.eval_batch_s ze, parse_fn=parse_fn, fna _ftypes=fna _ftypes),
    steps=(record_count + tra ner._params.eval_batch_s ze) // tra ner._params.eval_batch_s ze,
    hooks=[ tr cs_hook],
    c ckpo nt_path=tra ner.best_or_latest_c ckpo nt)
  return  tr cs_hook. tr c_values


def _get_extra_feature_group_performances(factory, tra ner, parse_fn, extra_groups, feature_to_type, record_count):
  """Compute performance d fferences for t  extra feature groups
  """
  extra_group_feature_performance_results = {}
  for group_na , raw_feature_regex_l st  n extra_groups. ems():
    start = t  .t  ()
    fna s = match_feature_regex_l st(
      features=feature_to_type.keys(),
      feature_regex_l st=[regex for regex  n raw_feature_regex_l st],
      preprocess=False,
      as_d ct=False)

    fna s_ftypes = [(fna , feature_to_type[fna ]) for fna   n fna s]

    logg ng. nfo("Extracted extra group {} w h features {}".format(group_na , fna s_ftypes))
    extra_group_feature_performance_results[group_na ] = _compute_mult ple_permuted_performances_from_tra ner(
      factory=factory, fna _ftypes=fna s_ftypes,
      tra ner=tra ner, parse_fn=parse_fn, record_count=record_count)
    logg ng. nfo("\n\n mportances computed for {}  n {} seconds \n\n".format(
      group_na ,  nt(t  .t  () - start)))
  return extra_group_feature_performance_results


def _feature_ mportances_tree_algor hm(
    data_d r, tra ner, parse_fn, fna s, stopp ng_ tr c, f le_l st=None, datarecord_f lter_fn=None, spl _feature_group_on_per od=True,
    record_count=99999,  s_ tr c_larger_t _better=None, sens  v y=0.025, extra_groups=None, dont_bu ld_tree=False):
  """Tree algor hm for feature and feature group  mportances. T  algor hm bu ld a pref x tree of
  t  feature na s and t n traverses t  tree w h a BFS. At each node (aka group of features w h
  a shared pref x) t  algor hm computes t  performance of t  model w n   permute all features
   n t  group. T  algor hm only zooms- n on groups that  mpact t  performance by more than
  sens  v y. As a result, features that affect t  model performance by less than sens  v y w ll
  not have an exact  mportance.
  Args:
    data_d r: (str): T  locat on of t  tra n ng or test ng data to compute  mportances over.
       f None, t  tra ner._eval_f les are used
    tra ner: (DataRecordTra ner): A DataRecordTra ner object
    parse_fn: (funct on): T  parse_fn used by eval_ nput_fn
    fna s (l st<str ng>): T  l st of feature na s
    stopp ng_ tr c (str): T   tr c to use to determ ne w n to stop expand ng trees
    f le_l st (l st<str>): T  l st of f lena s. Exactly one of f le_l st and data_d r should be
      prov ded
    datarecord_f lter_fn (funct on): a funct on takes a s ngle data sample  n com.tw ter.ml.ap .ttypes.DataRecord format
        and return a boolean value, to  nd cate  f t  data record should be kept  n feature  mportance module or not.
    spl _feature_group_on_per od (boolean):  f true, spl  feature groups by per od rat r than on
      opt mal pref x
    record_count ( nt): T  number of records to compute  mportances over
     s_ tr c_larger_t _better (boolean):  f true, assu  that stopp ng_ tr c  s a  tr c w re larger
      values are better (e.g. ROC-AUC)
    sens  v y (float): T  smallest change  n performance to cont nue to expand t  tree
    extra_groups (d ct<str, l st<str>>): A d ct onary mapp ng t  na  of extra feature groups to t  l st of
      t  na s of t  features  n t  group.   should only supply a value for t  argu nt  f   have a set
      of features that   want to evaluate as a group but don't share a pref x
    dont_bu ld_tree (boolean):  f True, don't bu ld t  tree and only compute t  extra_groups  mportances
  Returns:
    A d ct onary that conta ns t   nd v dual and group feature  mportances
  """
  factory = Permuted nputFnFactory(
    data_d r=data_d r, record_count=record_count, f le_l st=f le_l st, datarecord_f lter_fn=datarecord_f lter_fn)
  basel ne_performance = _compute_mult ple_permuted_performances_from_tra ner(
    factory=factory, fna _ftypes=[],
    tra ner=tra ner, parse_fn=parse_fn, record_count=record_count)
  out = {"None": basel ne_performance}

   f stopp ng_ tr c not  n basel ne_performance:
    ra se ValueError("T  stopp ng  tr c '{}' not found  n basel ne_performance.  tr cs are {}".format(
      stopp ng_ tr c, l st(basel ne_performance.keys())))

   s_ tr c_larger_t _better = (
     s_ tr c_larger_t _better  f  s_ tr c_larger_t _better  s not None else _ nfer_ f_ s_ tr c_larger_t _better(stopp ng_ tr c))
  logg ng. nfo("Us ng {} as t  stopp ng  tr c for t  tree algor hm".format(stopp ng_ tr c))

  feature_to_type = _get_feature_types_from_records(records=factory.records, fna s=fna s)
  all_feature_types = l st(feature_to_type. ems())

   nd v dual_feature_performances = {}
  feature_group_performances = {}
   f dont_bu ld_tree:
    logg ng. nfo("Not bu ld ng feature  mportance tr e. W ll only compute  mportances for t  extra_groups")
  else:
    logg ng. nfo("Bu ld ng feature  mportance tr e")
    # Each ele nt  n t  Queue w ll be a tuple of (pref x, l st_of_feature_type_pa rs) w re
    #   each feature  n l st_of_feature_type_pa rs w ll have have t  pref x "pref x"
    feature_l st_queue = _repart  on(
      feature_l st_queue=Queue(), fna s_ftypes=all_feature_types, spl _feature_group_on_per od=spl _feature_group_on_per od)

    wh le not feature_l st_queue.empty():
      # Pop t  queue.   should never have an empty l st  n t  queue
      pref x, fna s_ftypes = feature_l st_queue.get()
      assert len(fna s_ftypes) > 0

      # Compute performance from permut ng all features  n fna _ftypes
      logg ng. nfo(
        "\n\nComput ng  mportances for {} ({}...). {} ele nts left  n t  queue \n\n".format(
          pref x, fna s_ftypes[:5], feature_l st_queue.qs ze()))
      start = t  .t  ()
      computed_performance = _compute_mult ple_permuted_performances_from_tra ner(
        factory=factory, fna _ftypes=fna s_ftypes,
        tra ner=tra ner, parse_fn=parse_fn, record_count=record_count)
      logg ng. nfo("\n\n mportances computed for {}  n {} seconds \n\n".format(
        pref x,  nt(t  .t  () - start)))
       f len(fna s_ftypes) == 1:
         nd v dual_feature_performances[fna s_ftypes[0][0]] = computed_performance
      else:
        feature_group_performances[pref x] = computed_performance
      # D g deeper  nto t  features  n fna _ftypes only  f t re  s more than one feature  n t 
      #    l st and t  performance drop  s nontr v al
      logg ng. nfo("C ck ng performance for {} ({}...)".format(pref x, fna s_ftypes[:5]))
      c ck = _c ck_w t r_tree_should_expand(
        basel ne_performance=basel ne_performance, computed_performance=computed_performance,
        sens  v y=sens  v y, stopp ng_ tr c=stopp ng_ tr c,  s_ tr c_larger_t _better= s_ tr c_larger_t _better)
       f len(fna s_ftypes) > 1 and c ck:
        logg ng. nfo("Expand ng {} ({}...)".format(pref x, fna s_ftypes[:5]))
        feature_l st_queue = _repart  on(
          feature_l st_queue=feature_l st_queue, fna s_ftypes=fna s_ftypes, spl _feature_group_on_per od=spl _feature_group_on_per od)
      else:
        logg ng. nfo("Not expand ng {} ({}...)".format(pref x, fna s_ftypes[:5]))

  # Basel ne performance  s grouped  n w h  nd v dual_feature_ mportance_results
   nd v dual_feature_performance_results = d ct(
    out, **{k: v for k, v  n  nd v dual_feature_performances. ems()})
  group_feature_performance_results = {k: v for k, v  n feature_group_performances. ems()}

   f extra_groups  s not None:
    logg ng. nfo("Comput ng performances for extra groups {}".format(extra_groups.keys()))
    for group_na , performances  n _get_extra_feature_group_performances(
        factory=factory,
        tra ner=tra ner,
        parse_fn=parse_fn,
        extra_groups=extra_groups,
        feature_to_type=feature_to_type,
        record_count=record_count). ems():
      group_feature_performance_results[group_na ] = performances
  else:
    logg ng. nfo("Not comput ng performances for extra groups")

  return { ND V DUAL:  nd v dual_feature_performance_results,
          GROUP: group_feature_performance_results}


def _feature_ mportances_ser al_algor hm(
    data_d r, tra ner, parse_fn, fna s, f le_l st=None, datarecord_f lter_fn=None, factory=None, record_count=99999):
  """Ser al algor hm for feature  mportances. T  algor hm computes t 
   mportance of each feature.
  """
  factory = Permuted nputFnFactory(
    data_d r=data_d r, record_count=record_count, f le_l st=f le_l st, datarecord_f lter_fn=datarecord_f lter_fn)
  feature_to_type = _get_feature_types_from_records(records=factory.records, fna s=fna s)

  out = {}
  for fna , ftype  n l st(feature_to_type. ems()) + [(None, None)]:
    logg ng. nfo("\n\nComput ng  mportances for {}\n\n".format(fna ))
    start = t  .t  ()
    fna _ftypes = [(fna , ftype)]  f fna   s not None else []
    out[str(fna )] = _compute_mult ple_permuted_performances_from_tra ner(
      factory=factory, fna _ftypes=fna _ftypes,
      tra ner=tra ner, parse_fn=parse_fn, record_count=record_count)
    logg ng. nfo("\n\n mportances computed for {}  n {} seconds \n\n".format(
      fna ,  nt(t  .t  () - start)))
  # T  ser al algor hm does not compute group feature results.
  return { ND V DUAL: out, GROUP: {}}


def _process_feature_na _for_mldash(feature_na ):
  # Us ng a forward slash  n t  na  causes feature  mportance wr  ng to fa l because strato  nterprets   as
  #   part of a url
  return feature_na .replace("/", "__")


def compute_feature_ mportances(
    tra ner, data_d r=None, feature_conf g=None, algor hm=TREE, parse_fn=None, datarecord_f lter_fn=None, **kwargs):
  """Perform a feature  mportance analys s on a tra ned model
  Args:
    tra ner: (DataRecordTra ner): A DataRecordTra ner object
    data_d r: (str): T  locat on of t  tra n ng or test ng data to compute  mportances over.
       f None, t  tra ner._eval_f les are used
    feature_conf g (contr b.FeatureConf g): T  feature conf g object.  f t   s not prov ded,  
       s taken from t  tra ner
    algor hm (str): T  algor hm to use
    parse_fn: (funct on): T  parse_fn used by eval_ nput_fn. By default t   s
      feature_conf g.get_parse_fn()
    datarecord_f lter_fn (funct on): a funct on takes a s ngle data sample  n com.tw ter.ml.ap .ttypes.DataRecord format
        and return a boolean value, to  nd cate  f t  data record should be kept  n feature  mportance module or not.
  """

  #   only use t  tra ner's eval f les  f an overr de data_d r  s not prov ded
   f data_d r  s None:
    logg ng. nfo("Us ng tra ner._eval_f les (found {} as f les)".format(tra ner._eval_f les))
    f le_l st = tra ner._eval_f les
  else:
    logg ng. nfo("data_d r prov ded. Look ng at {} for data.".format(data_d r))
    f le_l st = None

  feature_conf g = feature_conf g or tra ner._feature_conf g
  out = {}
   f not feature_conf g:
    logg ng.warn("WARN: Not comput ng feature  mportance because tra ner._feature_conf g  s None")
    out = None
  else:
    parse_fn = parse_fn  f parse_fn  s not None else feature_conf g.get_parse_fn()
    fna s = _get_feature_na _from_conf g(feature_conf g)
    logg ng. nfo("Comput ng  mportances for {}".format(fna s))
    logg ng. nfo("Us ng t  {} feature  mportance computat on algor hm".format(algor hm))
    algor hm = {
      SER AL: _feature_ mportances_ser al_algor hm,
      TREE: _feature_ mportances_tree_algor hm}[algor hm]
    out = algor hm(data_d r=data_d r, tra ner=tra ner, parse_fn=parse_fn, fna s=fna s, f le_l st=f le_l st, datarecord_f lter_fn=datarecord_f lter_fn, **kwargs)
  return out


def wr e_feature_ mportances_to_hdfs(
    tra ner, feature_ mportances, output_path=None,  tr c="roc_auc"):
  """Publ sh a feature  mportance analys s to hdfs as a tsv
  Args:
    (see compute_feature_ mportances for ot r args)
    tra ner (Tra ner)
    feature_ mportances (d ct): D ct onary of feature  mportances
    output_path (str): T  remote or local f le to wr e t  feature  mportances to.  f not
      prov ded, t   s  nferred to be t  tra ner save d r
     tr c (str): T   tr c to wr e to tsv
  """
  # Str ng formatt ng appends ( nd v dual) or (Group) to feature na  depend ng on type
  perfs = {"{} ({})".format(k,  mportance_key)  f k != "None" else k: v[ tr c]
    for  mportance_key,  mportance_value  n feature_ mportances. ems()
    for k, v  n  mportance_value. ems()}

  output_path = ("{}/feature_ mportances-{}".format(
    tra ner._save_d r[:-1]  f tra ner._save_d r.endsw h('/') else tra ner._save_d r,
    output_path  f output_path  s not None else str(t  .t  ())))

   f len(perfs) > 0:
    logg ng. nfo("Wr  ng feature_ mportances for {} to hdfs".format(perfs.keys()))
    entr es = [
      {
        "na ": na ,
        "drop": perfs["None"] - perfs[na ],
        "pdrop": 100 * (perfs["None"] - perfs[na ]) / (perfs["None"] + 1e-8),
        "perf": perfs[na ]
      } for na   n perfs.keys()]
    out = ["Na \tPerformance Drop\tPercent Performance Drop\tPerformance"]
    for entry  n sorted(entr es, key=lambda d: d["drop"]):
      out.append("{na }\t{drop}\t{pdrop}%\t{perf}".format(**entry))
    logg ng. nfo("\n".jo n(out))
    wr e_l st_to_hdfs_gf le(out, output_path)
    logg ng. nfo("Wrote feature feature_ mportances to {}".format(output_path))
  else:
    logg ng. nfo("Not wr  ng feature_ mportances to hdfs")
  return output_path


def wr e_feature_ mportances_to_ml_dash(tra ner, feature_ mportances, feature_conf g=None):
  # type: (DataRecordTra ner, FeatureConf g, d ct) -> None
  """Publ sh feature  mportances + all feature na s to ML  tastore
  Args:
    tra ner: (DataRecordTra ner): A DataRecordTra ner object
    feature_conf g (contr b.FeatureConf g): T  feature conf g object.  f t   s not prov ded,  
       s taken from t  tra ner
    feature_ mportances (d ct, default=None): D ct onary of precomputed feature  mportances
    feature_ mportance_ tr c (str, default=None): T   tr c to wr e to ML Dashboard
  """
  exper  nt_track ng_path = tra ner.exper  nt_tracker.track ng_path\
     f tra ner.exper  nt_tracker.track ng_path\
    else Exper  ntTracker.guess_path(tra ner._save_d r)

  logg ng. nfo('Comput ng feature  mportances for run: {}'.format(exper  nt_track ng_path))

  feature_ mportance_l st = []
  for key  n feature_ mportances:
    for feature,  mps  n feature_ mportances[key]. ems():
      logg ng. nfo('FEATURE NAME: {}'.format(feature))
      feature_na  = feature.spl (' (').pop(0)
      for  tr c_na , value  n  mps. ems():
        try:
           mps[ tr c_na ] = float(value)
          logg ng. nfo('Wrote feature  mportance value {} for  tr c: {}'.format(str(value),  tr c_na ))
        except Except on as ex:
          logg ng.error("Sk pp ng wr  ng  tr c:{} to ML  tastore due to  nval d  tr c value: {} or value type: {}. Except on: {}".format( tr c_na , str(value), type(value), str(ex)))
          pass

      feature_ mportance_l st.append(Feature mportance(
        run_ d=exper  nt_track ng_path,
        feature_na =_process_feature_na _for_mldash(feature_na ),
        feature_ mportance_ tr cs= mps,
         s_group=key == GROUP
      ))

# sett ng feature conf g to match t  one used  n compute_feature_ mportances
  feature_conf g = feature_conf g or tra ner._feature_conf g
  feature_na s = FeatureNa s(
    run_ d=exper  nt_track ng_path,
    na s=l st(feature_conf g.features.keys())
  )

  try:
    cl ent = ModelRepoCl ent()
    logg ng. nfo('Wr  ng feature  mportances to ML  tastore')
    cl ent.add_feature_ mportances(feature_ mportance_l st)
    logg ng. nfo('Wr  ng feature na s to ML  tastore')
    cl ent.add_feature_na s(feature_na s)
  except (HTTPError, RetryError) as err:
    logg ng.error('Feature  mportance  s not be ng wr ten due to: '
                  'HTTPError w n attempt ng to wr e to ML  tastore: \n{}.'.format(err))
