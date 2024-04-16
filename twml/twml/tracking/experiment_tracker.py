"""
T  module conta ns t  exper  nt tracker for track ng tra n ng  n ML  tastore
"""
from contextl b  mport contextmanager
from datet    mport datet  
 mport getpass
 mport hashl b
 mport os
 mport re
 mport sys
 mport t  

from absl  mport logg ng
 mport tensorflow.compat.v1 as tf
from twml.hooks  mport  tr csUpdateHook


try:
  from urll b  mport quote as encode_url
except  mportError:
  from urll b.parse  mport quote as encode_url


try:
  # ML  tastore packages m ght not be ava lable on GCP.
  #  f t y are not found, track ng  s d sabled
   mport requests
  from com.tw ter.ml tastore.modelrepo.cl ent  mport ModelRepoCl ent
  from com.tw ter.ml tastore.modelrepo.core.path  mport (
    c ck_val d_ d, get_components_from_ d, generate_ d)
  from com.tw ter.ml tastore.modelrepo.core  mport (
    Deepb rdRun, Exper  nt, FeatureConf g, FeatureConf gFeature, Model, ProgressReport, Project, StatusUpdate)
except  mportError:
  ModelRepoCl ent = None


class Exper  ntTracker(object):
  """
  A tracker that records twml runs  n ML  tastore.
  """

  def __ n __(self, params, run_conf g, save_d r):
    """

    Args:
      params (python d ct):
        T  tra ner params. Exper  ntTracker uses `params.exper  nt_track ng_path` (Str ng) and
        `params.d sable_exper  nt_track ng`.
         f `exper  nt_track ng_path`  s set to None, t  tracker tr es to guess a path w h
        save_d r.
         f `d sable_exper  nt_track ng`  s True, t  tracker  s d sabled.
      run_conf g (tf.est mator.RunConf g):
        T  run conf g used by t  est mator.
      save_d r (str):
        save_d r of t  tra ner
    """
     f  s nstance(params, d ct):
      self._params = params
    else:
      # preserv ng backward compat b l y for people st ll us ng HParams
      logg ng.warn ng("Please stop us ng HParams and use python d cts. HParams are removed  n TF 2")
      self._params = d ct((k, v) for k, v  n params.values(). ems()  f v != 'null')
    self._run_conf g = run_conf g
    self._graceful_shutdown_port = self._params.get(' alth_port')

    self.track ng_path = self._params.get('exper  nt_track ng_path')
     s_track ng_path_too_long = self.track ng_path  s not None and len(self.track ng_path) > 256

     f  s_track ng_path_too_long:
      ra se ValueError("Exper  nt Track ng Path longer than 256 characters")

    self.d sabled = (
      self._params.get('d sable_exper  nt_track ng', False) or
      not self._ s_env_el g ble_for_track ng() or
      ModelRepoCl ent  s None
    )

    self._ s_hogw ld = bool(os.env ron.get('TWML_HOGW LD_PORTS'))

    self._ s_d str buted = bool(os.env ron.get('TF_CONF G'))

    self._cl ent = None  f self.d sabled else ModelRepoCl ent()

    run_na _from_env ron = self.run_na _from_env ron()
    run_na _can_be_ nferred = (
      self.track ng_path  s not None or run_na _from_env ron  s not None)

    # Turn t  flags off as needed  n hogw ld / d str buted
     f self._ s_hogw ld or self._ s_d str buted:
      self._env_el g ble_for_record ng_exper  nt = (
        self._run_conf g.task_type == "evaluator")
       f run_na _can_be_ nferred:
        self._env_el g ble_for_record ng_export_ tadata = (
          self._run_conf g.task_type == "ch ef")
      else:
        logg ng. nfo(
          'exper  nt_track ng_path  s not set and can not be  nferred. '
          'Record ng export  tadata  s d sabled because t  ch ef node and eval node '
          'are sett ng d fferent exper  nt track ng paths.')
        self._env_el g ble_for_record ng_export_ tadata = False
    else:
      # Defaults to True
      self._env_el g ble_for_record ng_exper  nt = True
      self._env_el g ble_for_record ng_export_ tadata = True

     f not self.d sabled:
      # San  ze passed  n exper  nt track ng paths. e.g. own:proJ:exp:Run.Na 
      # -> own:proj:exp:Run_Na 
       f self.track ng_path:
        try:
          c ck_val d_ d(self.track ng_path)
        except ValueError as err:
          logg ng.error(f' nval d exper  nt track ng path prov ded. San  z ng: {self.track ng_path}\nError: {err}')
          self.track ng_path = generate_ d(
            owner=self.path['owner'],
            project_na =self.path['project_na '],
            exper  nt_na =self.path['exper  nt_na '],
            run_na =self.path['run_na ']
          )
          logg ng.error(f'Generated san  zed exper  nt track ng path: {self.track ng_path}')
      else:
        logg ng. nfo(
          'No exper  nt_track ng_path set. Exper  nt Tracker w ll try to guess a path')
        self.track ng_path = self.guess_path(save_d r, run_na _from_env ron)
        logg ng. nfo('Guessed path: %s', self.track ng_path)

      # add  onal c ck to see  f generated path  s val d
      try:
        c ck_val d_ d(self.track ng_path)
      except ValueError as err:
        logg ng.error(
          'Could not generate val d exper  nt track ng path. D sabl ng track ng. ' +
          'Error:\n{}'.format(err)
        )
        self.d sabled = True

    self.project_ d = None  f self.d sabled else '{}:{}'.format(
      self.path['owner'], self.path['project_na '])
    self.base_run_ d = None  f self.d sabled else self.track ng_path
    self._current_run_na _suff x = None

    self._current_tracker_hook = None

     f self.d sabled:
      logg ng. nfo('Exper  nt Tracker  s d sabled')
    else:
      logg ng. nfo('Exper  nt Tracker  n  al zed w h base run  d: %s', self.base_run_ d)

  @contextmanager
  def track_exper  nt(self, eval_hooks, get_est mator_spec_fn, na =None):
    """
    A context manager for track ng exper  nt.   should wrap t  tra n ng loop.
    An exper  nt tracker eval hook  s appended to eval_hooks to collect  tr cs.

    Args:
      eval_hooks (l st):
        T  l st of eval_hooks to be used. W n  's not None, and does not conta n any ,
         tr csUpdateHook an exper  nt tracker eval hook  s appended to  . W n   conta ns
        any  tr csUpdateHook, t  tracker  s d sabled to avo d confl ct w h legacy Model Repo
        tracker (`TrackRun`).
      get_est mator_spec_fn (func):
        A funct on to get t  current Est matorSpec of t  tra ner, used by t  eval hook.
      na  (str);
        Na  of t  tra n ng or evaluat on. Used as a suff x of t  run_ d.

    Returns:
      T  tracker's eval hook wh ch  s appended to eval_hooks.
    """

    # d sable t  tracker  f legacy TrackRun hook  s present
    # TODO: remove t  once   completely deprecate t  old TrackRun  nterface
     f eval_hooks  s not None:
      self.d sabled = self.d sabled or any( s nstance(x,  tr csUpdateHook) for x  n eval_hooks)

    logg ng. nfo(' s env ron nt el g ble for record ng exper  nt: %s',
                 self._env_el g ble_for_record ng_exper  nt)

     f self._env_el g ble_for_record ng_exper  nt and self._graceful_shutdown_port:
      requests.post('http://localhost:{}/track_tra n ng_start'.format(
        self._graceful_shutdown_port
      ))

     f self.d sabled or eval_hooks  s None:
      y eld None
    else:
      assert self._current_tracker_hook  s None, 'exper  nt track ng has been started already'

       f na   s not None:
        self._current_run_na _suff x = '_' + na 

      logg ng. nfo('Start ng exper  nt track ng. Path: %s', self._current_run_ d)
      logg ng. nfo(' s env ron nt el g ble for record ng export  tadata: %s',
                   self._env_el g ble_for_record ng_export_ tadata)
      logg ng. nfo('T  run w ll be ava lable at: http://go/mldash/exper  nts/%s',
                   encode_url(self.exper  nt_ d))

      try:
        self._record_run()
        self._add_run_status(StatusUpdate(self._current_run_ d, status='RUNN NG'))
        self._reg ster_for_graceful_shutdown()

        self._current_tracker_hook = self.create_eval_hook(get_est mator_spec_fn)
      except Except on as err:
        logg ng.error(
          'Fa led to record run. T  exper  nt w ll not be tracked. Error: %s', str(err))
        self._current_tracker_hook = None

       f self._current_tracker_hook  s None:
        y eld None
      else:
        try:
          eval_hooks.append(self._current_tracker_hook)
          y eld self._current_tracker_hook
        except Except on as err:
          self._add_run_status(
            StatusUpdate(self._current_run_ d, status='FA LED', descr pt on=str(err)))
          self._dereg ster_for_graceful_shutdown()
          self._current_tracker_hook = None
          self._current_run_na _suff x = None
          logg ng.error('Exper  nt track ng done. Exper  nt fa led.')
          ra se

        try:
           f self._current_tracker_hook. tr c_values:
            self._record_update(self._current_tracker_hook. tr c_values)
          self._add_run_status(StatusUpdate(self._current_run_ d, status='SUCCESS'))
          logg ng. nfo('Exper  nt track ng done. Exper  nt succeeded.')
        except Except on as err:
          logg ng.error(
            'Fa led to update mark run as successful. Error: %s', str(err))
        f nally:
          self._dereg ster_for_graceful_shutdown()
          self._current_tracker_hook = None
          self._current_run_na _suff x = None

  def create_eval_hook(self, get_est mator_spec_fn):
    """
    Create an eval_hook to track eval  tr cs

    Args:
      get_est mator_spec_fn (func):
        A funct on that returns t  current Est matorSpec of t  tra ner.
    """
    return  tr csUpdateHook(
      get_est mator_spec_fn=get_est mator_spec_fn,
      add_ tr cs_fn=self._record_update)

  def reg ster_model(self, export_path):
    """
    Record t  exported model.

    Args:
      export_path (str):
        T  path to t  exported model.
    """
     f self.d sabled:
      return None

    try:
      logg ng. nfo('Model  s exported to %s. Comput ng hash of t  model.', export_path)
      model_hash = self.compute_model_hash(export_path)
      logg ng. nfo('Model hash: %s. Reg ster ng    n ML  tastore.', model_hash)
      self._cl ent.reg ster_model(Model(model_hash, self.path['owner'], self.base_run_ d))
    except Except on as err:
      logg ng.error('Fa led to reg ster model. Error: %s', str(err))

  def export_feature_spec(self, feature_spec_d ct):
    """
    Export feature spec to ML  tastore (go/ml- tastore).

    Please note that t  feature l st  n FeatureConf g only keeps t  l st of feature hash  ds due
    to t  1mb upper l m  for values  n manhattan, and more spec f c  nformat on (feature type,
    feature na ) for each feature conf g feature  s stored separately  n FeatureConf gFeature dataset.

    Args:
       feature_spec_d ct (d ct): A d ct onary obta ned from FeatureConf g.get_feature_spec()
    """
     f self.d sabled or not self._env_el g ble_for_record ng_export_ tadata:
      return None

    try:
      logg ng. nfo('Export ng feature spec to ML  tastore.')
      feature_l st = feature_spec_d ct['features']
      label_l st = feature_spec_d ct['labels']
        ght_l st = feature_spec_d ct['  ght']
      self._cl ent.add_feature_conf g(FeatureConf g(self._current_run_ d, l st(feature_l st.keys()),
                                                    l st(label_l st.keys()), l st(  ght_l st.keys())))

      feature_conf g_features = [
        FeatureConf gFeature(
          hash_ d=_feature_hash_ d,
          feature_na =_feature['featureNa '],
          feature_type=_feature['featureType']
        )
        for _feature_hash_ d, _feature  n z p(feature_l st.keys(), feature_l st.values())
      ]
      self._cl ent.add_feature_conf g_features(l st(feature_l st.keys()), feature_conf g_features)

      feature_conf g_labels = [
        FeatureConf gFeature(
          hash_ d=_label_hash_ d,
          feature_na =_label['featureNa ']
        )
        for _label_hash_ d, _label  n z p(label_l st.keys(), label_l st.values())
      ]
      self._cl ent.add_feature_conf g_features(l st(label_l st.keys()), feature_conf g_labels)

      feature_conf g_  ghts = [
        FeatureConf gFeature(
          hash_ d=_  ght_hash_ d,
          feature_na =_  ght['featureNa '],
          feature_type=_  ght['featureType']
        )
        for _  ght_hash_ d, _  ght  n z p(  ght_l st.keys(),   ght_l st.values())
      ]
      self._cl ent.add_feature_conf g_features(l st(  ght_l st.keys()), feature_conf g_  ghts)

    except Except on as err:
      logg ng.error('Fa led to export feature spec. Error: %s', str(err))

  @property
  def path(self):
     f self.d sabled:
      return None
    return get_components_from_ d(self.track ng_path, ensure_val d_ d=False)

  @property
  def exper  nt_ d(self):
     f self.d sabled:
      return None
    return '%s:%s:%s' % (self.path['owner'], self.path['project_na '],
                         self.path['exper  nt_na '])

  @property
  def _current_run_na (self):
    """
    Return t  current run na .
    """
     f self._current_run_na _suff x  s not None:
      return self.path['run_na '] + self._current_run_na _suff x
    else:
      return self.path['run_na ']

  @property
  def _current_run_ d(self):
    """
    Return t  current run  d.
    """
     f self._current_run_na _suff x  s not None:
      return self.base_run_ d + self._current_run_na _suff x
    else:
      return self.base_run_ d

  def get_run_status(self) -> str:
     f not self.d sabled:
      return self._cl ent.get_latest_dbv2_status(self._current_run_ d)

  def _add_run_status(self, status):
    """
    Add run status w h underly ng cl ent.

    Args:
      status (StatusUpdate):
        T  status update to add.
    """
     f not self.d sabled and self._env_el g ble_for_record ng_exper  nt:
      self._cl ent.add_run_status(status)

  def _record_run(self):
    """
    Record t  run  n ML  tastore.
    """
     f self.d sabled or not self._env_el g ble_for_record ng_exper  nt:
      return None

     f not self._cl ent.project_ex sts(self.project_ d):
      self._cl ent.add_project(Project(self.path['project_na '], self.path['owner']))
      t  .sleep(1)

     f not self._cl ent.exper  nt_ex sts(self.exper  nt_ d):
      self._cl ent.add_exper  nt(Exper  nt(
        self.path['exper  nt_na '], self.path['owner'], self.project_ d, ''))
      t  .sleep(1)

    run = Deepb rdRun(self.exper  nt_ d, self._current_run_na , '',
                      {'raw_command': ' '.jo n(sys.argv)}, self._params)
    self._cl ent.add_deepb rd_run(run, force=True)
    t  .sleep(1)

  def _record_update(self,  tr cs):
    """
    Record  tr cs update  n ML  tastore.

    Args:
       tr cs (d ct):
        T  d ct of t   tr cs and t  r values.
    """

     f self.d sabled or not self._env_el g ble_for_record ng_exper  nt:
      return None

    reported_ tr cs = {}
    for k, v  n  tr cs. ems():

       f hasattr(v, ' em'):
        reported_ tr cs[k] = v. em()  f v.s ze == 1 else str(v.tol st())
      else:
        logg ng.warn ng(" gnor ng %s because t  value (%s)  s not val d" % (k, str(v)))

    report = ProgressReport(self._current_run_ d, reported_ tr cs)

    try:
      self._cl ent.add_progress_report(report)
    except Except on as err:
      logg ng.error('Fa led to record  tr cs  n ML  tastore. Error: {}'.format(err))
      logg ng.error('Run  D: {}'.format(self._current_run_ d))
      logg ng.error('Progress Report: {}'.format(report.to_json_str ng()))

  def _reg ster_for_graceful_shutdown(self):
    """
    Reg ster t  tracker w h t   alth server, enabl ng graceful shutdown.

    Returns:
      (Response)  alth server response
    """
     f self._graceful_shutdown_port and not self.d sabled and self._env_el g ble_for_record ng_exper  nt:
      return requests.post('http://localhost:{}/reg ster_ d/{}'.format(
        self._graceful_shutdown_port,
        self._current_run_ d
      ))

  def _dereg ster_for_graceful_shutdown(self):
    """
    Dereg ster t  tracker w h t   alth server, d sabl ng graceful shutdown.

    Returns:
      (Response)  alth server response
    """
     f self._graceful_shutdown_port and not self.d sabled and self._env_el g ble_for_record ng_exper  nt:
      return requests.post('http://localhost:{}/dereg ster_ d/{}'.format(
        self._graceful_shutdown_port,
        self._current_run_ d
      ))

  def _ s_env_el g ble_for_track ng(self):
    """
    Determ ne  f exper  nt track ng should run  n t  env.
    """
     s_un _test = (
      os.env ron.get('PYTEST_CURRENT_TEST')  s not None and
      os.env ron.get('TEST_EXP_TRACKER')  s None
    )

     s_runn ng_on_c  = (
      getpass.getuser() == 'scoot-serv ce' and
      os.env ron.get('TEST_EXP_TRACKER')  s None
    )

    return (
      not  s_un _test and
      not  s_runn ng_on_c 
    )

  @class thod
  def run_na _from_env ron(cls):
    """
    Create run  d from env ron nt  f poss ble.
    """
    job_na  = os.env ron.get("TWML_JOB_NAME")
    job_launch_t   = os.env ron.get("TWML_JOB_LAUNCH_T ME")

     f not job_na  or not job_launch_t  :
      return None

    try:
      # job_launch_t   should be  n  soformat
      # python2 doesnt support datet  .from soformat, so use hardcoded format str ng.
      job_launch_t  _formatted = datet  .strpt  (job_launch_t  ,
                                                    "%Y-%m-%dT%H:%M:%S.%f")
    except ValueError:
      # Fallback  n case aurora conf g  s generat ng datet    n a d fferent format.
      job_launch_t  _formatted = (job_launch_t  
                                   .replace("-", "_").replace("T", "_")
                                   .replace(":", "_").replace(".", "_"))

    return '{}_{}'.format(
      job_na , job_launch_t  _formatted.strft  ('%m_%d_%Y_% _%M_%p'))

  @class thod
  def guess_path(cls, save_d r, run_na =None):
    """
    Guess an exper  nt track ng path based on save_d r.

    Returns:
      (str) guessed path
    """
     f not run_na :
      run_na  = 'Unna d_{}'.format(datet  .now().strft  ('%m_%d_%Y_% _%M_%p'))

     f save_d r.startsw h('hdfs://'):
      path_match = re.search(r'/user/([a-z0-9\-_]+)/([a-z0-9\-_]+)', save_d r)

       f path_match:
        groups = path_match.groups()
        user = groups[0]
        project_na  = groups[1]

        return generate_ d(user, 'default', project_na , run_na )

    user = getpass.getuser()
    project_na  = re.sub(r'^[a-z0-9\-_]', os.path.basena (save_d r), '')
     f not project_na :
      project_na  = 'unna d'

    return generate_ d(user, 'default', project_na , run_na )

  @class thod
  def compute_model_hash(cls, export_path):
    """
    Computes t  hash of an exported model. T   s a gf le vers on of
    tw ter.ml tastore.common.vers on ng.compute_hash. T  two funct ons should generate
    t  sa  hash w n g ven t  sa  model.

    Args:
      export_path (str):
        T  path to t  exported model.

    Returns:
      (str) hash of t  exported model
    """
    paths = []
    for path, subd rs, f les  n tf. o.gf le.walk(export_path):
      for na   n sorted(f les):
        paths.append(os.path.jo n(path, na ))

    paths.sort()
    hash_object = hashl b.new('sha1')

    for path  n paths:
      w h tf. o.gf le.GF le(path, "rb") as f le:
        hash_object.update(f le.read())

    return hash_object. xd gest()
