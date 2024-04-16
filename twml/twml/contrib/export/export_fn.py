"""
Funct ons for export ng models for d fferent modes.
"""
from collect ons  mport OrderedD ct
 mport os

 mport tensorflow.compat.v1 as tf
from tensorflow.python.est mator.export  mport export
 mport twml
 mport yaml


def get_sparse_batch_superv sed_ nput_rece ver_fn(feature_conf g, keep_f elds=None):
  """Gets superv sed_ nput_rece ver_fn that decodes a BatchPred ct onRequest as sparse tensors
  w h labels and   ghts as def ned  n feature_conf g.
  T   nput_rece ver_fn  s requ red for export ng models w h 'tra n' mode to be tra ned w h
  Java AP 

  Args:
    feature_conf g (FeatureConf g): deepb rd v2 feature conf g object
    keep_f elds (l st): l st of f elds to keep

  Returns:
    superv sed_ nput_rece ver_fn:  nput_rece ver_fn used for tra n mode
  """
  def superv sed_ nput_rece ver_fn():
    ser al zed_request = tf.placeholder(dtype=tf.u nt8, na ='request')
    rece ver_tensors = {'request': ser al zed_request}

    bpr = twml.contr b.readers.Has dBatchPred ct onRequest(ser al zed_request, feature_conf g)
    features = bpr.get_sparse_features()  f keep_f elds  s None else bpr.get_features(keep_f elds)
    features['  ghts'] = bpr.  ghts
    labels = bpr.labels
    features, labels = bpr.apply_f lter(features, labels)

    return export.Superv sed nputRece ver(features, labels, rece ver_tensors)

  return superv sed_ nput_rece ver_fn


def update_bu ld_graph_fn_for_tra n(bu ld_graph_fn):
  """Updates a bu ld_graph_fn by  nsert ng  n graph output a ser al zed BatchPred ct onResponse
  s m lar to t  export_output_fns for serv ng.
  T  key d fference  re  s that
  1.    nsert ser al zed BatchPred ct onResponse  n graph output w h key 'pred ct on'  nstead of
     creat ng an export_output object. T   s because of t  way est mators export model  n 'tra n'
     mode doesn't take custom export_output
  2.   only do   w n `mode == 'tra n'` to avo d alter ng t  graph w n export ng
     for ' nfer' mode

  Args:
    bu ld_graph_fn (Callable): deepb rd v2 bu ld graph funct on

  Returns:
    new_bu ld_graph_fn: An updated bu ld_graph_fn that  nserts ser al zed BatchPred ctResponse
                        to graph output w n  n 'tra n' mode
  """
  def new_bu ld_graph_fn(features, label, mode, params, conf g=None):
    output = bu ld_graph_fn(features, label, mode, params, conf g)
     f mode == tf.est mator.ModeKeys.TRA N:
      output.update(
        twml.export_output_fns.batch_pred ct on_cont nuous_output_fn(output)[
          tf.saved_model.s gnature_constants.DEFAULT_SERV NG_S GNATURE_DEF_KEY].outputs
      )
    return output
  return new_bu ld_graph_fn


def export_model_for_tra n_and_ nfer(
    tra ner, feature_conf g, keep_f elds, export_d r, as_text=False):
  """Funct on for export ng model w h both 'tra n' and ' nfer' mode.

  T   ans t  exported saved_model.pb w ll conta n two  ta graphs, one w h tag 'tra n'
  and t  ot r w h tag 'serve', and   can be loaded  n Java AP  w h e  r tag depend ng on
  t  use case

  Args:
    tra ner (DataRecordTra ner): deepb rd v2 DataRecordTra ner
    feature_conf g (FeatureConf g): deepb rd v2 feature conf g
    keep_f elds (l st of str ng): l st of f eld keys, e.g.
                                  (' ds', 'keys', 'values', 'batch_s ze', 'total_s ze', 'codes')
    export_d r (str): a d rectory (local or hdfs) to export model to
    as_text (bool):  f True, wr e 'saved_model.pb' as b nary f le, else wr e
                    'saved_model.pbtxt' as human readable text f le. Default False
  """
  tra n_ nput_rece ver_fn = get_sparse_batch_superv sed_ nput_rece ver_fn(
    feature_conf g, keep_f elds)
  pred ct_ nput_rece ver_fn = twml.parsers.get_sparse_serv ng_ nput_rece ver_fn(
    feature_conf g, keep_f elds)
  tra ner._export_output_fn = twml.export_output_fns.batch_pred ct on_cont nuous_output_fn
  tra ner._bu ld_graph_fn = update_bu ld_graph_fn_for_tra n(tra ner._bu ld_graph_fn)
  tra ner._est mator._export_all_saved_models(
    export_d r_base=export_d r,
     nput_rece ver_fn_map={
      tf.est mator.ModeKeys.TRA N: tra n_ nput_rece ver_fn,
      tf.est mator.ModeKeys.PRED CT: pred ct_ nput_rece ver_fn
    },
    as_text=as_text,
  )

  tra ner.export_model_effects(export_d r)


def export_all_models_w h_rece vers(est mator, export_d r,
                                     tra n_ nput_rece ver_fn,
                                     eval_ nput_rece ver_fn,
                                     pred ct_ nput_rece ver_fn,
                                     export_output_fn,
                                     export_modes=('tra n', 'eval', 'pred ct'),
                                     reg ster_model_fn=None,
                                     feature_spec=None,
                                     c ckpo nt_path=None,
                                     log_features=True):
  """
  Funct on for export ng a model w h tra n, eval, and  nfer modes.

  Args:
    est mator:
      Should be of type tf.est mator.Est mator.
        can get t  from tra ner us ng tra ner.est mator
    export_d r:
      D rectory to export t  model.
    tra n_ nput_rece ver_fn:
       nput rece ver for tra n  nterface.
    eval_ nput_rece ver_fn:
       nput rece ver for eval  nterface.
    pred ct_ nput_rece ver_fn:
       nput rece ver for pred ct  nterface.
    export_output_fn:
      export_output_fn to be used for serv ng.
    export_modes:
      A l st to Spec fy what modes to export. Can be "tra n", "eval", "pred ct".
      Defaults to ["tra n", "eval", "pred ct"]
    reg ster_model_fn:
      An opt onal funct on wh ch  s called w h export_d r after models are exported.
      Defaults to None.
  Returns:
     T  t  stamped d rectory t  models are exported to.
  """
  # TODO: F x for hogw ld / d str buted tra n ng.

   f export_d r  s None:
    ra se ValueError("export_d r can not be None")
  export_d r = twml.ut l.san  ze_hdfs_path(export_d r)
   nput_rece ver_fn_map = {}

   f "tra n"  n export_modes:
     nput_rece ver_fn_map[tf.est mator.ModeKeys.TRA N] = tra n_ nput_rece ver_fn

   f "eval"  n export_modes:
     nput_rece ver_fn_map[tf.est mator.ModeKeys.EVAL] = eval_ nput_rece ver_fn

   f "pred ct"  n export_modes:
     nput_rece ver_fn_map[tf.est mator.ModeKeys.PRED CT] = pred ct_ nput_rece ver_fn

  export_d r = est mator._export_all_saved_models(
    export_d r_base=export_d r,
     nput_rece ver_fn_map= nput_rece ver_fn_map,
    c ckpo nt_path=c ckpo nt_path,
  )

   f reg ster_model_fn  s not None:
    reg ster_model_fn(export_d r, feature_spec, log_features)

  return export_d r


def export_all_models(tra ner,
                      export_d r,
                      parse_fn,
                      serv ng_ nput_rece ver_fn,
                      export_output_fn=None,
                      export_modes=('tra n', 'eval', 'pred ct'),
                      feature_spec=None,
                      c ckpo nt=None,
                      log_features=True):
  """
  Funct on for export ng a model w h tra n, eval, and  nfer modes.

  Args:
    tra ner:
      An object of type twml.tra ners.Tra ner.
    export_d r:
      D rectory to export t  model.
    parse_fn:
      T  parse funct on used parse t   nputs for tra n and eval.
    serv ng_ nput_rece ver_fn:
      T   nput rece ver funct on used dur ng serv ng.
    export_output_fn:
      export_output_fn to be used for serv ng.
    export_modes:
      A l st to Spec fy what modes to export. Can be "tra n", "eval", "pred ct".
      Defaults to ["tra n", "eval", "pred ct"]
    feature_spec:
      A d ct onary obta ned from FeatureConf g.get_feature_spec() to ser al ze
      as feature_spec.yaml  n export_d r.
      Defaults to None
  Returns:
     T  t  stamped d rectory t  models are exported to.
  """
  # Only export from ch ef  n hogw ld or d str buted modes.
   f tra ner.params.get('d str buted', False) and not tra ner.est mator.conf g. s_ch ef:
    tf.logg ng. nfo("Tra ner.export_model  gnored due to  nstance not be ng ch ef.")
    return

   f feature_spec  s None:
     f getattr(tra ner, '_feature_conf g')  s None:
      ra se ValueError("feature_spec  s set to None."
                       "Please pass feature_spec=feature_conf g.get_feature_spec() to t  export_all_model funct on")
    else:
      feature_spec = tra ner._feature_conf g.get_feature_spec()

  export_d r = twml.ut l.san  ze_hdfs_path(export_d r)
  old_export_output_fn = tra ner._export_output_fn
  tra ner._export_output_fn = export_output_fn
  superv sed_ nput_rece ver_fn = twml.parsers.convert_to_superv sed_ nput_rece ver_fn(parse_fn)
   f not c ckpo nt:
    c ckpo nt = tra ner.best_or_latest_c ckpo nt

  export_d r = export_all_models_w h_rece vers(est mator=tra ner.est mator,
                                                export_d r=export_d r,
                                                tra n_ nput_rece ver_fn=superv sed_ nput_rece ver_fn,
                                                eval_ nput_rece ver_fn=superv sed_ nput_rece ver_fn,
                                                pred ct_ nput_rece ver_fn=serv ng_ nput_rece ver_fn,
                                                export_output_fn=export_output_fn,
                                                export_modes=export_modes,
                                                reg ster_model_fn=tra ner.export_model_effects,
                                                feature_spec=feature_spec,
                                                c ckpo nt_path=c ckpo nt,
                                                log_features=log_features)
  tra ner._export_output_fn = old_export_output_fn
  return export_d r


def export_feature_spec(d r_path, feature_spec_d ct):
  """
  Exports a FeatureConf g.get_feature_spec() d ct to <d r_path>/feature_spec.yaml.
  """
  def ordered_d ct_representer(dumper, data):
    return dumper.represent_mapp ng('tag:yaml.org,2002:map', data. ems())

  try:
    # needed for Python 2
    yaml.add_representer(str, yaml.representer.SafeRepresenter.represent_str)
    yaml.add_representer(un code, yaml.representer.SafeRepresenter.represent_un code)
  except Na Error:
    # 'un code' type doesn't ex st on Python 3
    # PyYAML handles un code correctly  n Python 3
    pass

  yaml.add_representer(OrderedD ct, ordered_d ct_representer)

  fbase = "feature_spec.yaml"
  fna  = fbase.encode('utf-8')  f type(d r_path) != str else fbase
  f le_path = os.path.jo n(d r_path, fna )
  w h tf. o.gf le.GF le(f le_path, mode='w') as f:
    yaml.dump(feature_spec_d ct, f, default_flow_style=False, allow_un code=True)
  tf.logg ng. nfo("Exported feature spec to %s" % f le_path)

  return f le_path


# Keep t  al as for compat b l y.
get_superv sed_ nput_rece ver_fn = twml.parsers.convert_to_superv sed_ nput_rece ver_fn
