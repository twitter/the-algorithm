# pyl nt: d sable=too-many-l nes
"""
``twml.tra ners.Tra ner``  s a wrapper around `tf.est mator.Est mator
<https://www.tensorflow.org/vers ons/master/ap _docs/python/tf/est mator/Est mator>`_
to expose an eas er to use AP  by
h d ng rarely used conf g knobs and supply ng default values.

T  `Tra ner` fac l ates mult -phase tra n ng commonly used at Tw ter: e.g.
MDL cal brat on -> MLP tra n ng ->  soton c cal brat on.
T  `Tra ner` also fac l ates hyperpara ters tun ng,
w h  s s mple `add_parser_argu nts()`  thod.

Learn ng rate decay funct ons
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Please note that   have f  learn ng rate decay funct ons to choose from.
Add  onally, each tra ner can only take one learn ng rate decay funct on and  s para ters.
 f that  s not t  case,   w ll throw an error.
Also, please note that t  learn ng rate decay  s a pos  onal argu nt and should be placed as
t  last argu nt to t  tra ner, as   can see  n t  example above.
T  f  learn ng decays opt ons are:

1.  nverse_learn ng_rate_decay:

  T  funct on returns t  decayed learn ng rate.    s computed as:

  ::

    decayed_learn ng_rate = learn ng_rate / (1 + decay_rate * global_step /decay_step)
    f nal_decayed_learn ng_rate = max(decayed_learn ng_rate, m n_learn ng_rate)


2. polynom al_learn ng_rate_decay:

  T  funct on returns t  decayed learn ng rate.    s computed as:

  ::

    global_step = m n(global_step, decay_steps)
    decayed_learn ng_rate = (learn ng_rate - end_learn ng_rate) *
                            (1 - global_step / decay_steps) ^ (po r) +
                            end_learn ng_rate


3. p ecew se_constant_learn ng_rate_decay:

  P ecew se constant from boundar es and  nterval values.

  Example: use a learn ng rate that's 1.0 for t  f rst 100001 steps, 0.5 for
  t  next 10000 steps, and 0.1 for any add  onal steps.

  ::

    global_step = tf.Var able(0, tra nable=False)
    boundar es = [100000, 110000]
    values = [1.0, 0.5, 0.1]
    learn ng_rate = tf.tra n.p ecew se_constant(global_step, boundar es, values)

4. exponent al_learn ng_rate_decay:

  T  funct on returns t  decayed learn ng rate.    s computed as:

  ::

    decayed_learn ng_rate = learn ng_rate * decay_rate ^ (global_step / decay_steps)

"""

 mport datet  
 mport functools
 mport math
from operator  mport  emgetter
 mport os
 mport ppr nt as pp
 mport random
from str ng  mport Template
 mport subprocess
 mport sys
 mport t  
from thread ng  mport Thread

from tw ter.common. tr cs  mport Atom cGauge
from tw ter.deepb rd.stats_server  mport ut ls as stats_server_ut ls
from tw ter.deepb rd.stats_server.stats_exporter  mport StatsExporter
from tw ter.ml.common  mport  tr cs
from tw ter.ml.common.kubernetes  mport kubectl_delete_by_na , Res ce
from tw ter.ml.twml.status  mport get_d str buted_tra n ng_job_status, Tra n ngJobStatus

from absl  mport logg ng
from twml.opt m zers  mport LazyAdamOpt m zer, opt m ze_loss, OPT M ZER_SUMMAR ES
from twml.contr b.opt m zers  mport DeepGrad entCompress onOpt m zer
from twml.track ng  mport Exper  ntTracker
from twml.ut l  mport (delete_f le_or_d r,
                       get_d str buted_tra n ng_job_path,
                       san  ze_hdfs_path)
try:
  from urll b  mport quote as encode_url
except  mportError:
  from urll b.parse  mport quote as encode_url
 mport tensorflow.compat.v1 as tf
 mport tensorflow
 mport tensorflow_hub as hub

 mport tw ter.ml.twml.kubernetes.status as k8s_status
 mport twml
 mport twml.export_output_fns
 mport twml.learn ng_rate_decay
 mport twml. tr cs


_CLUSTER_TEMPLATE = Template('''{
  "cluster": {
    "ps": [$PS],
    "ch ef": [$CH EF],
    "worker": [$WORKER]
  },
  "task": {"type": "$TYPE", " ndex": $ NDEX}
}
''')


def  n _from_c ckpo nt( n _d r,  n _map):
  """
  Wrapper around tf.tra n. n _from_c ckpo nt
  """
   f  n _d r:
     n _d r = san  ze_hdfs_path( n _d r)
    tf.tra n. n _from_c ckpo nt( n _d r,  n _map)


class Tra ner(object):
  """
  T  class wraps ``tf.est mator.Est mator`` to make construct on, sav ng, and load ng eas er.
  Supports mult -phase tra n ng (for example, use a Tra ner for MDL cal brat on, t n
  anot r for tra n ng t  rest of t  model, t n anot r for  soton c cal brat on).
  T  Tra ner also  mple nts a tra n ng and evaluat on loop v a t  ``learn()``  thod.
  Each Tra ner  s assoc ated to a f xed set of hyper para ters (params), and a s ngle model
  spec f ed by ``bu ld_graph``. G ven t se constra nts, a s ngle Tra ner can be called
  mult ple t  s for tra n ng and evaluat on over mult ple epochs.

  Ho ver,  f    ntend to try d fferent sets of hyper-para ters,   recom nd    nstant ate
  a d fferent Tra ner for each such exper  nt. That way, each exper  nt can be tracked
   n a d fferent ``save_d r``.  ndeed, after call ng ``learn``, a Tra ner's save_d r w ll conta n
  c ckpo nts of t  model ( s graph, and var ables), and t   tory of  tr cs (for example,
  evaluat on accuracy at each epoch), and ot r store observat ons l ke t  average t   per step.
  T  latter  tr cs can be v e d by po nt ng
  TensorBoard to t  save_d r and access ng TensorBoard v a y  browser.
  """

  def __ n __(self, na , params, bu ld_graph_fn,
                tr c_fn=None,
               opt m ze_loss_fn=None,
               run_conf g=None,
               save_d r=None,
                n _from_d r=None,
                n _map=None,
               warm_start_from=None,
               prof ler_steps=None,
               **kwargs):
    """

    Args:
      na  (Str ng):
        str ng na  of t  est mator; used as scope na s for var ables and tensors.
      params (HParams, Na space, or D ct):
        hyper-para ters to be passed to Est mator constructor.
        Must  nclude params.tra n_batch_s ze and params.eval_batch_s ze.
        Note that params  s passed to twml.ut l.convert_to_hparams() to produce an HParams.
      bu ld_graph_fn:
        A funct on for bu ld ng tensorflow graphs.
        T  matc s TensorFlow Est mator's model_fn s gnature.
        For example,

        .. code-block:: python

          def bu ld_graph(features, label, mode, params, conf g=None):
            #  mple nts a s mple b nary log st c regress on model
            sparse_tf = twml.ut l.convert_to_sparse(features, params. nput_s ze_b s)

            log s = twml.layers.full_sparse(sparse_tf, 1 << params. nput_s ze_b s, 1)

             f mode == ' nfer':
              loss = None
            else:
              loss = tf.nn.s gmo d_cross_entropy_w h_log s(labels=label, log s=log s)
              loss = twml.ut l.  ghted_average(loss, features['  ghts'])

            output = tf.nn.s gmo d(log s)

            return {'output': output, 'loss': loss}

        Args:
          features (d ct of Tensor keyed by a str ng na ):
             nput tensors.
          mode (tf.est mator.ModeKeys / Str ng):
            one of 'tra n', 'eval', ' nfer'.
          label (Tensor):
             f  n ``mode == 'tra n'`` mode, t se conta n t  correspond ng labels for  nput.
          params (HParams):
            hyper para ters that control how to bu ld a graph.
          conf g:
            t  RunConf g object passed to Est mator constructor.

        T  funct on  s expected to return a d ct onary conta n ng t  follow ng keys:

        * 'output': a node represent ng model output; requ red.
        * 'loss': (requ red) a loss node used for opt m zat on; requ red for tra n ng and
          evaluat on.
        * 'tra n_op': (opt onal) an operat on that m n m zes t  loss (as output by
          `tf.tra n.Opt m zer.m n m ze`).  f tra n_op  s spec f ed, tra n_op  s used
          for opt m zat on as opposed to loss. Loss  s always logged to tensorboard.

        Notes:

        * any tf.summary wr ten  ns de bu ld graph are logged to tensorboard dur ng tra n ng.
        * t  ``bu ld_graph_fn``  s called once or tw ce per epoch (once per tra n ng,
          once per evaluat on). All data load ng (and preprocess ng) log c not requ red
          for serv ng should be  n t  `` nput_fn`` passed to ``learn``, ``tra n``,
          ``evalulate``, etc.

      opt m ze_loss_fn:
        Defaults to Tra ner.get_tra n_op. A funct on that takes params and loss as argu nts
        and returns a tra n ng op. T  tra n ng op  s used to update para ters (that  s, to learn).
       tr c_fn:
        A funct on that returns t  eval_ tr c_ops d ct g ven graph_output, labels and   ghts.
        Defaults to None.
        Use ``twml. tr cs.get_b nary_class_ tr c_fn()`` to return a `` tr c_fn``
        wh ch  mple nts many b nary class f cat on  tr cs.
      run_conf g (RunConf g):
        opt onal conf gurat on to be passed to Est mator constructor. Defaults to None.
      save_d r (Str ng):
        opt onal d rectory w re to save model c ckpo nts,
        tensorboard event f les and tra ned para ters.
        Overwr es and defaults to run_conf g.model_d r.
       n _from_d r (Str ng):
        opt onal d rectory to load   ghts from.
         f set to None (t  default), do not  n  from any d rectory.
       n _map (map from Str ng to Str ng):
        Must be spec f ed  f  n _from_d r  s spec f ed.
        Def nes wh ch scopes and var ables to load.
        Keys are t  var ables and scopes to load from t  d rectory.
        Values are t  dest nat ons ( n t  current graph) to load  nto.
        See tf. n _from_c ckpo nt for more  nformat on.
        Note that t  t  tra ner prepends na _scope of t  form `na `/model/ to t  na _scope
        of any var able def ned  ns de `bu ld_graph_fn` and t  should be taken  nto account w n
        def n ng t  values.
      warm_start_from:
        Opt onal str ng f lepath to a c ckpo nt to warm-start from,
        or a tf.est mator.WarmStartSett ngs object to fully conf gure warm-start ng.
         f t  str ng f lepath  s prov ded  nstead of a WarmStartSett ngs,
        t n all var ables are warm-started, and    s assu d that
        vocabular es and Tensor na s are unchanged.
      prof ler_steps ( nteger):
        Defaults to None.  f set def nes t  number of steps  n t 
        `tf.tra n.Prof leHook <https://www.tensorflow.org/ap _docs/python/tf/tra n/Prof lerHook>`_.
        Captures CPU/GPU prof l ng  nformat on every ``prof ler_steps`` steps or seconds.
        W n execut ng ``learn``, ``tra n`` or ``pred ct``  thods,
        w h ``prof ler_steps`` set to a number,
        a ``t  l ne_X.json`` f le  s created  n t  save_d r. T  f le conta ns prof l ng data
        stored n Chro  trace format. To v ew stored data, use t  Chro  browser to follow
        t se steps:

        1) Go to t  page chro ://trac ng.
        2)  n t  upper left corner,   w ll f nd Load button.
        3) Press   and load   JSON f le, wh ch can be found  n t  ``save_d r``

        *Warn ng*: T  could create too many t se json f les wh ch can be a potent al problem,
        e.g. for  HDFS t re  s normally quota forf le count, so use w h caut on.

        Note: t  argu nt  s  gnored w n a non-None ``hooks`` argu nt  s pasesd to
        ``tra n``, ``learn``, or ``pred ct``  thods. T  hook can be added manually by pass ng
        ``tra ner.tra n(..., hooks= hooks.extend(tra ner.get_tra n_hooks()))``, for example.
    """

     f tensorflow.__vers on__ >= "2.0":
      Runt  Error("Tra ner not yet supported for Tensorflow >= 2.0")

    self._na  = na 
    self._bu ld_graph_fn = bu ld_graph_fn
    self._ tr c_fn =  tr c_fn
    self._tensorboard_handle = None
    self._current_est mator_spec = None  # holds t  current est mator spec
    self._prof ler_steps = prof ler_steps
    self._export_output_fn = None
    self._ s_early_stopp ng = False

    # NOTE: San  ze all HDFS paths f rst.
    save_d r = san  ze_hdfs_path(save_d r)
     n _from_d r = san  ze_hdfs_path( n _from_d r)

    # warm_start_from can be of type tf.est mator.WarmStartSett ngs.
     f  s nstance(warm_start_from, str):
      warm_start_from = san  ze_hdfs_path(warm_start_from)

    # convert to tw ter.deepb rd.hparam.hparam.HParams object
    params = twml.ut l.convert_to_hparams(params)

    # keep a copy of t  params because call ng self._est mator.params creates a deepcopy
    self._params = params
    self.c ck_params()

    self._us ng_hogw ld = True  f os.env ron.get('TWML_HOGW LD_PORTS') else False
    # conf gure Hogw ld (needs to be called before RunConf g  s created)
    self._hogw ld_setup()

     f not run_conf g:
      sess on_conf g = tf.Conf gProto()
      # By default each process tr es to allocate (almost) all of t   mory.
      # T  opt on ensures t  gpu  mory grows dynam cally  nstead.
      sess on_conf g.gpu_opt ons.allow_growth = True  # pyl nt: d sable=no- mber

       f 'TWML_NUM_CPUS'  n os.env ron:
        num_ava lable_cpus =  nt(os.env ron.get("TWML_MESOS_CPU", "8"))
         f params.num_mkl_threads > 1:
          os.env ron["OMP_NUM_THREADS"] = str(params.num_mkl_threads)
          os.env ron["MKL_NUM_THREADS"] = str(params.num_mkl_threads)
          sess on_conf g. nter_op_parallel sm_threads = num_ava lable_cpus // params.num_mkl_threads
          sess on_conf g. ntra_op_parallel sm_threads = params.num_mkl_threads

      run_conf g = tf.est mator.RunConf g(
        sess on_conf g=sess on_conf g,
        keep_c ckpo nt_max=self._params.get('keep_c ckpo nt_max', 20),
        log_step_count_steps=10000,
        save_c ckpo nts_secs=self._params.get('save_c ckpo nts_secs', 600),
        tf_random_seed=self._tf_random_seed())
    el f not  s nstance(run_conf g, tf.est mator.RunConf g):
      ra se ValueError("Expect ng run_conf g argu nt of type None or tf.est mator.RunConf g"
        "Got %s  nstead." % type(run_conf g).__na __)
    el f os.env ron.get('TWML_HOGW LD_PORTS'):
      ra se ValueError("Custom RunConf g not supported w h Hogw ld")

     f run_conf g.model_d r  s None and save_d r  s None:
      ra se ValueError(
          "Expect ng e  r save_d r or run_conf g.model_d r to be spec f ed. Got None for each.")
    el f run_conf g.model_d r  s None:
      run_conf g = run_conf g.replace(model_d r=save_d r)
    el f save_d r  s None:
      save_d r = run_conf g.model_d r

    self._save_d r = save_d r
    self.exper  nt_tracker = Exper  ntTracker(self._params, run_conf g, self._save_d r)

    # C ck  f should delete t  tsd runn ng t  tra n ng job.  n certa n use case w n 
    # t re are ot r tf operat ons follow ng tra ner.tra n_and_evaluate (or tra ner.learn),
    # add  onal state f les need to be spec f ed to ensure those steps are executed after job restart.
    kwargs['gke_state_f les'] = kwargs.get('gke_state_f les', ['_SUCCESS'])
    self._maybe_del_tsd_ex (kwargs['gke_state_f les'])
    logg ng. nfo("C ckpo nt and event f les w ll be saved at save_d r=%s", save_d r)
    self._opt m ze_loss_fn = self.get_tra n_op  f opt m ze_loss_fn  s None else opt m ze_loss_fn

    # overwr e t  current save_d r
     f self._params.get('overwr e_save_d r') and tf. o.gf le.ex sts(self._save_d r):
      logg ng. nfo("Tra ner overwr  ng ex st ng save d rectory: %s (params.overwr e_save_d r)"
                   % self._save_d r)
      #  f d str buted or hogw ld:
       f self._params.get('d str buted', False):
        # sleep for 30 seconds to allow each worker to get to t  po nt.
        t  .sleep(30)
         f run_conf g. s_ch ef:
          logg ng. nfo("Ch ef delet ng t  save_d r now")
          delete_f le_or_d r(self._save_d r)
        # sleep for 30 seconds to allow each worker to get to t  po nt.
        t  .sleep(30)
      else:
        delete_f le_or_d r(self._save_d r)

    # Expos ng stats to a /vars.json endpo nt that w ll be collected
    # by t  absorber
     f self._params.get('stats_port'):
      try:
        stats_server_ut ls.start_stats_server(self._params.get('stats_port'), self._save_d r)
      except Except on as err:
        logg ng.error('Fa led to start t  stats server. Error: %s', str(err))

    c ckpo nt = os.path.jo n(self._save_d r, 'c ckpo nt')
     f tf. o.gf le.ex sts(c ckpo nt):
      logg ng. nfo("T  prov ded save_d r d rectory %s already ex sts."
                   " Tra n ng w ll be resu d."
                   % c ckpo nt)

    self._maybe_restore_c ckpo nt = lambda:  n _from_c ckpo nt( n _from_d r,  n _map)

     f  n _from_d r  s not None and  n _map  s None:
      ra se ValueError("Need to prov de  n _map w n  n _from_d r  s prov ded.")

     f not tf. o.gf le.ex sts(self._save_d r):
      # so tensorboard can po nt to a d rectory that ex sts
      tf. o.gf le.mkd r(self._save_d r)

    self._est mator = tf.est mator.Est mator(
      model_fn=self._model_fn,
      params=self._params,  # HParams
      conf g=run_conf g,  # RunConf g
      warm_start_from=warm_start_from,
      model_d r=self._save_d r,  # By t  po nt    s sa  as run_conf g.model_d r
    )

    # Log para ters that are used to construct tra ner. T  allows people to see default values.
    logg ng. nfo("Tra ner constructed us ng t  follow ng para ters: ")
    pp_params = pp.pformat(self._params.values())
    logg ng. nfo(pp_params)

    # Start TensorBoard
     f self._params.get('d sable_tensorboard', False):
      logg ng. nfo("Sk pp ng launch ng TensorBoard [--d sable_tensorboard  s set]")
    el f "tensorboard_port"  n self._params.values() and self._params.tensorboard_port  s not None:
      self.start_tensorboard(self._params.tensorboard_port)

    # Export gauge that w ll track w t r a model was exported
    self.stats_exporter = StatsExporter("twml.tra ner")
    self.export_gauge = Atom cGauge('export_model')
    self.stats_exporter.reg ster_ tr cs(self.export_gauge)

  def _hogw ld_setup(self):
    """
    Setup t  para ters requ red for hogw ld.
    """
    self._num_workers = self._params.get('num_workers') or 1
    logg ng. nfo("NUM_WORKERS: %d", self._num_workers)
     f self._num_workers <= 1:
      self._ports = None
      return

    # a hogw ld job  s cons dered d str buted
     f 'd str buted'  n self._params:
      self._params.set_hparam('d str buted', True)
    else:
      self._params.add_hparam('d str buted', True)

    ports = os.env ron.get('TWML_HOGW LD_PORTS')
     f ports:
      self._ports = [ nt(port) for port  n ports.str p().spl (",")]
       f (self._num_workers + 1!= len(self._ports)):
        ra se ValueError("Number of (workers + PS) and ports need to match")
    else:
       f self._num_workers > 1:
        ra se ValueError("TWML_HOGW LD_PORTS needs to be set to use hogw ld tra n ng")

    # Spl  t  number of data threads across mult ple workers
    num_threads = self._params.get('num_threads')
    num_threads_per_worker =  nt(math.ce l(float(num_threads) / self._num_workers))
    self._params.set_hparam('num_threads', num_threads_per_worker)

    hogw ld_task_type = os.env ron.get('TWML_HOGW LD_TASK_TYPE')
    hogw ld_task_ d =  nt(os.env ron.get('TWML_HOGW LD_TASK_ D'))
    os.env ron['TF_CONF G'] = self._get_cluster_conf g(hogw ld_task_type, hogw ld_task_ d)

  def _tf_random_seed(self):
    """ Returns user set seed and deal w h Hogw ld mult ple seeds """
    tf_random_seed = self._params.get('tf_random_seed', None)
     f tf_random_seed  s None:
      return None
    el f self.us ng_hogw ld and os.env ron.get('TWML_HOGW LD_TASK_TYPE') == 'worker':
      # ch ef (tf_random_seed), worker_0 (tf_random_seed + 1), worker_1 (tf_random_seed + 2)...
      return tf_random_seed + 1 +  nt(os.env ron.get('TWML_HOGW LD_TASK_ D'))
    else:
      return tf_random_seed

  def c ck_params(self):
    """ Ver fy that params has t  correct key,values """
    param_values = self._params.values()

     f 'tra n_batch_s ze'  n param_values:
       f not  s nstance(self._params.tra n_batch_s ze,  nt):
        ra se ValueError("Expect ng params.tra n_batch_s ze to be an  nteger.")
       f self._params.tra n_batch_s ze <= 0:
        ra se ValueError("tra n_batch_s ze needs to be pos  ve")
    else:
      ra se ValueError("tra n_batch_s ze needs to be present  n params")

     f 'eval_batch_s ze'  n param_values:
       f not  s nstance(self._params.eval_batch_s ze,  nt):
        ra se ValueError("Expect ng params.eval_batch_s ze to be an  nteger.")
       f self._params.eval_batch_s ze <= 0:
        ra se ValueError("eval_batch_s ze needs to be pos  ve.")
    else:
      self._params.add_hparam('eval_batch_s ze', self._params.tra n_batch_s ze)

     f (self._params.get('d str buted_tra n ng_cleanup') and
      not self._params.get('d str buted')):
      #   only need to support tra n ng d scont nuat on for d str buted tra n ng
      # bc   are st ll us ng TSDs on GKE for d str buted tra n ng
      ra se ValueError(
        "Expect ng params.d str buted to be set  f "
        "params.d str buted_tra n ng_cleanup  s set."
      )

  def _get_cluster_conf g(self, na ,  ndex):
    """Create a tensorflow cluster conf g from ports, na  and  ndex"""
    host = '"localhost:%d"'
    ps = host % self._ports[0]
    ch ef = host % self._ports[1]
    workers = ", ".jo n([host % port for port  n self._ports[2:]])
    conf g = _CLUSTER_TEMPLATE.subst ute(
      PS=ps,
      CH EF=ch ef,
      WORKER=workers,
      TYPE=na ,
       NDEX= ndex,
    )
    return conf g

  @property
  def current_est mator_spec(self):
    """
    returns t  current est mator (warn ng: often reset)
    """
    return self._current_est mator_spec

  @property
  def est mator(self):
    """ returns est mator encapsulated by Tra ner """
    return self._est mator

  @property
  def num_workers(self):
    """ returns number of workers """
    return self._est mator.conf g.num_worker_repl cas

  @property
  def worker_ ndex(self):
    """
    returns  ndex of worker  n t  cluster
    ch ef has  ndex 0
    non-ch ef workers have  nd ces 1 through (num_workers - 1)
    """
    return self._est mator.conf g.global_ d_ n_cluster

  @property
  def us ng_hogw ld(self):
    """ returns a bool  nd cat ng w t r hogw ld  s be ng used """
    return self._us ng_hogw ld

  def set_est mator(self, est mator):
    """ sets t  est mator used  nternally by Tra ner """
     f not  s nstance(est mator, tf.est mator.Est mator):
      ra se ValueError("Expect ng tf.est mator.Est mator")
    self._est mator = est mator
    self._params = self.est mator.params

  @property
  def params(self):
    """
    returns t  hyper-para ters passed to t  constructor.
    """
    return self._params

  @stat c thod
  def add_parser_argu nts():
    """
    Add common commandl ne args to parse for t  Tra ner class.
    Typ cally, t  user calls t  funct on and t n parses cmd-l ne argu nts
     nto an argparse.Na space object wh ch  s t n passed to t  Tra ner constructor
    v a t  params argu nt.

    See t  `code <_modules/twml/argu nt_parser.html#get_tra ner_parser>`_
    for a l st and descr pt on of all cmd-l ne argu nts.

    Returns:
      argparse.Argu ntParser  nstance w h so  useful args already added.
    """
    return twml.argu nt_parser.get_tra ner_parser()

  @stat c thod
  def get_tra n_op(params, loss):
    """
    Return a tra n ng Op, that  s, a `twml.opt m zers.opt m ze_loss
    <https://www.tensorflow.org/ap _docs/python/tf/contr b/layers/opt m ze_loss>`_
     nstance g ven params and loss.
    T   thod can be overwr ten by pass ng t  opt m ze_loss_fn to t  Tra ner
    constructor.

    Args:
      params:
        tensorflow.contr b.tra n ng.HParams  nstance. Recogn zes t  opt m zer, opt m zer_summar es,
        grad ent_no se_scale, cl p_grad ents and learn ng_rate_decay ( nclud ng
        ot r learn ng rate decay argu nts).
      loss:
        scalar Op returned by t  bu ld_graph that spec f es t  tra n ng loss to
        be m n m zed.
    """
    opt m zer = params.get('opt m zer')

     f not opt m zer:
      opt m zer = 'SGD'

     f opt m zer == 'LazyAdam':
      opt m zer = LazyAdamOpt m zer

     f opt m zer == 'DGC':
      opt m zer = DeepGrad entCompress onOpt m zer(
          learn ng_rate=params.learn ng_rate,
          use_lock ng=False,
          na ="Sparse",
          dens y=params.get('dgc_dens y'),
          dens y_decay=params.get('dgc_dens y_decay'),
          dens y_decay_steps=params.get('dgc_dens y_decay_steps'),
          dens y_decay_rate=params.get('dgc_dens y_decay_rate'),
          m n_dens y=params.get('dgc_m n_dens y'),
          accumulat on=params.get('dgc_accumulat on')
      )

    summar es = ['loss']
     f params.get('show_opt m zer_summar es'):
      summar es = OPT M ZER_SUMMAR ES

    tra n_op = opt m ze_loss(
      loss=loss,
      global_step=tf.tra n.get_global_step(),
      opt m zer=opt m zer,
      learn ng_rate=params.learn ng_rate,
      summar es=summar es,
      colocate_grad ents_w h_ops=True,
      grad ent_no se_scale=params.get('grad ent_no se_scale'),
      cl p_grad ents=params.get('cl p_grad ents'),
      learn ng_rate_decay_fn=twml.learn ng_rate_decay.get_learn ng_rate_decay_fn(params)
    )
    return tra n_op

  def export_model_effects(self, export_path, feature_spec=None, log_features=True):

    # DO NOT CHANGE THE ORDER.
    # T  needs to be done before reg ster ng t  model.
     f feature_spec:
       f log_features:
        features = feature_spec['features']
        feature_na s = ['.'.jo n(features[f d]['featureNa '].spl ('.')[1:]) for f d  n features.keys()]
        features_to_log = ','.jo n(feature_na s)
        try:
          model_hash = self.exper  nt_tracker.compute_model_hash(export_path)
           tr cs.log_usage('dbv2', 'export_model_effects', 'v1', custom_attrs=[model_hash, "feature conf g present", features_to_log])
        except:  # noqa: T803
          logg ng. nfo("Fa led to log Feature Conf g features")

      twml.contr b.export.export_fn.export_feature_spec(export_path, feature_spec)
      export_start_t   = t  .t  ()
      self.exper  nt_tracker.export_feature_spec(feature_spec)
      logg ng. nfo("Exported feature spec to ML  tastore  n %s seconds.", t  .t  () - export_start_t  )

    self.exper  nt_tracker.reg ster_model(str(export_path))
    self.export_gauge. ncre nt()

  @property
  def best_or_latest_c ckpo nt(self):
     f self._ s_early_stopp ng:
      best_c ckpo nt_path = os.path.jo n(self._save_d r, "best_c ckpo nt")
      c ckpo nt_path = tf.tra n.latest_c ckpo nt(best_c ckpo nt_path)
      # Return best c ckpo nt  f necessary
       f c ckpo nt_path:
        return c ckpo nt_path
      else:
        ra se ValueError("Best c ckpo nt not found at %s." % best_c ckpo nt_path)
    else:  # Fallback to latest c ckpo nt from save d rectory
      return self.latest_c ckpo nt

  @property
  def latest_c ckpo nt(self):
    return self.est mator.latest_c ckpo nt()

  def export_model(self, serv ng_ nput_rece ver_fn,
                   export_output_fn=None,
                   export_d r=None, c ckpo nt_path=None,
                   feature_spec=None,
                   log_features=True):
    """
    Export t  model for pred ct on. Typ cally, t  exported model
    w ll later be run  n product on servers. T   thod  s called
    by t  user to export t  PRED CTgraph to d sk.

     nternally, t   thod calls `tf.est mator.Est mator.export_savedmodel
    <https://www.tensorflow.org/ap _docs/python/tf/est mator/Est mator#export_savedmodel>`_.

    Note that a val d self._export_output_fn  s requ red.
     f export_ouput_fn  s prov ded,    s used to set t  self._export_output_fn.

    Args:
      serv ng_ nput_rece ver_fn:
        funct on prepar ng t  model for  nference requests.
        T  funt on returns t  ``features`` d ct passed to ``bu ld_graph``.
      export_d r:
        d rectory to export a SavedModel for pred ct on servers.
        Defaults to ``[save_d r]/exported_models``.
      c ckpo nt_path:
        t  c ckpo nt path to export.  f None (t  default), t  most recent c ckpo nt
        found w h n t  model d rectory  s chosen.
      export_output_fn:
        Funct on to export t  graph_output (output of bu ld_graph) for
        pred ct on. Takes a graph_output d ct as sole argu nt and returns
        t  export_output_fns d ct.
        Defaults to `twml.export_output_fns.default_output_fn`.

    Return:
      returns a str ng path to exported d rectory.

    # set t  export output funct on
    """
     f not self. s_ch ef():
      logg ng. nfo("Tra ner.export_model  gnored due to t  process not be ng ch ef.")
      return

    self._export_output_fn = export_output_fn or twml.export_output_fns.default_output_fn

     f not callable(self._export_output_fn):
      ra se Runt  Error(
        "Expect ng export_output_fn funct on. Got %s."
        % type(self._export_output_fn).__na __)

     f export_d r:
      export_d r = san  ze_hdfs_path(export_d r)

     f c ckpo nt_path:
      c ckpo nt_path = san  ze_hdfs_path(c ckpo nt_path)
    else:
      c ckpo nt_path = self.best_or_latest_c ckpo nt

    # actually export t  model us ng t  Est mator AP 
    export_path = self._est mator.export_savedmodel(
      export_d r_base=export_d r or os.path.jo n(self._save_d r, 'exported_models'),
      serv ng_ nput_rece ver_fn=serv ng_ nput_rece ver_fn,
      c ckpo nt_path=c ckpo nt_path)

    # export_path  s bytes, need to convert to str ng for python3 to work.
    logg ng. nfo("T  exported model path  s: " + str(export_path))

    self.export_model_effects(export_path, feature_spec, log_features)

    return export_path

  def _model_fn(self, features, labels, mode, params, conf g=None):
    """
    returns tf.est mator.Est matorSpec that can be used w h tf.est mator.Est mators.
      would probably never need to mod fy t   thod.
     nstead,   should overr de bu ld_graph, wh ch t   thod calls.

    Args:
      features:
        D ct of  nput tensors.
      labels:
        Tensor of target labels.
      mode:
        an  nstance of tf.est mator.ModeKeys.
        Typ cally used to toggle TRA N ng or EVALuat on.
      params:
        HParams object conta n ng hyper-para ters.
    """
    # pyl nt: d sable=too-many-branc s
     f  s nstance(features, d ct):
        ghts = features.get('  ghts', None)
    else:
        ghts = None

    w h tf.var able_scope(self._na  + '/model'):
      graph_output = self._bu ld_graph_fn(features, labels, mode, params, conf g)
      loss = graph_output['loss']  f 'loss'  n graph_output else None

    self._maybe_restore_c ckpo nt()

    w h tf.var able_scope(self._na  + '/opt m'):
      tra n_op = None
       f mode == tf.est mator.ModeKeys.TRA N:
         f 'tra n_op'  n graph_output:
          tra n_op = graph_output['tra n_op']
          graph_output['tra n_op'] = None  # remove from preds to prevent error
        el f loss  s not None:
          tra n_op = self._opt m ze_loss_fn(params, loss)

         f params.get('tra n_log_ tr cs') and self._ tr c_fn:
           tr c_ops = self._ tr c_fn(graph_output=graph_output, labels=labels,   ghts=  ghts)
          for  tr c_na   n  tr c_ops:
            tf.summary.scalar(
              na ="tra n ng_ tr c_" +  tr c_na ,
              tensor= tr c_ops[ tr c_na ][1])  #  ndex 0 conta ns value_op, 1 conta ns update_op

     f mode == tf.est mator.ModeKeys.PRED CT and self._export_output_fn  s not None:
      # note that t   s  gnored by t  pred ct  thod.
      # Est mator only uses export_output_fn for export_model.
      export_outputs = self._export_output_fn(graph_output)
    else:
      export_outputs = None

     f mode == tf.est mator.ModeKeys.EVAL and self._ tr c_fn:
      eval_ tr c_ops = self._ tr c_fn(graph_output=graph_output, labels=labels,   ghts=  ghts)
    else:
      eval_ tr c_ops = None

    # None and loss (scalar, not sl ceable by TFMA) should be removed from t  graph_output
    preds = {key: graph_output[key] for key  n graph_output  f (graph_output[key]  s not None) and (key  s not 'loss')}

     n _feed_d ct = twml.contr b. n  al zers.get_ n _feed_d ct()
    scaffold = tf.tra n.Scaffold( n _feed_d ct= n _feed_d ct)

    # Clear t   n  feed collect on to avo d ser al z ng t   n  al zers.
    twml.contr b. n  al zers.clear_ n _feed_collect on()

    # save est mator for use by later  thods and hooks (warn ng: often reset)
    self._current_est mator_spec = tf.est mator.Est matorSpec(
      mode=mode,
      pred ct ons=preds,
      export_outputs=export_outputs,
      loss=loss,
      tra n_op=tra n_op,
      eval_ tr c_ops=eval_ tr c_ops,
      scaffold=scaffold,
    )

    return self._current_est mator_spec

  def get_tra n_hooks(self):
    """Return Sess onRunHooks used dur ng tra n ng.

    By default tra n ng uses one hooks `tf.tra n.StepCounterHook` for mon or ng step speed.

     f self._prof ler_steps  s set t n   also use t  Prof lerHook `tf.tra n.Prof lerHook`
    for mon or ng t  prof le.

    """
    #  nstead of hav ng every_n_steps be a constant number,
    # change   dynam cally based on batch s ze.
    #  deally   should be us ng every_n_secs, but that seems buggy as of 1.7.
    # T  every_n_steps = 20K / batch_s ze
    every_n_steps = ((2048 * 100) // self._params.tra n_batch_s ze)
    step_counter = tf.tra n.StepCounterHook(
      every_n_steps=every_n_steps, output_d r=self._save_d r
    )
    tra n_hooks = [step_counter]

     f self._prof ler_steps  s not None:
       f not self._params.get('d str buted') or self._est mator.conf g. s_ch ef:
        prof ler = tf.tra n.Prof lerHook(
          save_steps=self._prof ler_steps,
          output_d r=self._save_d r
        )
        tra n_hooks.append(prof ler)

    return tra n_hooks

  def  s_task_type(self, na ):
    """
     lper funct on to spec fy  f t  current process  s of t  g ven worker type.
    Note: T  an only be called *after* self._hogw ld_setup()  s called  n __ n __()
    """
     f os.env ron.get('TF_CONF G'):
       f self._est mator.conf g.task_type == na :
        return True
      else:
        return False
    return True

  def  s_evaluator(self):
    """
     lper funct on to let   know  f t  worker  s evaluator.
    Note: T  an only be called *after* self._hogw ld_setup()  s called  n __ n __()
    """
    return self. s_task_type("evaluator")

  def  s_ch ef(self):
    """
     lper funct on to let   know  f t  worker  s ch ef.
    Note: T  an only be called *after* self._hogw ld_setup()  s called  n __ n __()
    """
    return self. s_task_type("ch ef") or self. s_task_type("master")

  def  s_ps(self):
    """
     lper funct on to let   know  f t  task  s para ter server.
    """
     f os.env ron.get('TF_CONF G') and self._est mator.conf g.task_type == 'ps':
      return True
    return False

  def _ex _ps_after_tra n ng_complete(self):
    """
     lper funct on to shutdown para ter server after tra n ng job complete (e  r succeed or fa led).
    """
     f not self. s_ps():
      return

    # No need to ex  ps  f on t  sa  mach ne
     f os.env ron.get('TWML_HOGW LD_PORTS'):
      return

     f self._params.get('d sable_auto_ps_shutdown', False):
      logg ng. nfo("Sk p shutt ng down para ter server after tra n ng complete [--d sable_auto_ps_shutdown  s set]")
      return

    # c ck ng job status  s d fferent on gke vs aurora
     f self._ s_on_gke():
      get_job_status = functools.part al(
        k8s_status.get_tra n ng_job_status,
        cluster=None,
        na space=os.env ron['TWML_JOB_ROLE'],
        env ron nt=os.env ron['TWML_JOB_ENV'],
        job_na =os.env ron['TWML_JOB_NAME'],
        us ng_tsd=True)
    else:
      get_job_status = functools.part al(
        get_d str buted_tra n ng_job_path,
        base_job_path=get_d str buted_tra n ng_job_path()
      )

    def wa _complete_t n_ex ():
      retry_max = 60
      retry = 0
      wh le True:
        try:
          tra n ng_status = get_job_status()
           f tra n ng_status == Tra n ngJobStatus.F N SHED:
            logg ng. nfo("D str buted tra n ng job succeed, shutt ng down para ter server.")
            os._ex (0)
          el f tra n ng_status == Tra n ngJobStatus.FA LED:
            logg ng. nfo("D str buted tra n ng job fa led, shutt ng down para ter server.")
            os._ex (0)
          el f tra n ng_status == Tra n ngJobStatus.NOT_FOUND:
            ra se Except on("D str buted tra n ng job status not found.")
          else:
            poke_ nterval = random.randrange(60, 90)  # prevent sp ke QPS to aurora endpo nt
            t  .sleep(poke_ nterval)
            retry = 0
        except Except on as e:
           f retry >= retry_max:
            ra se e  # only except on  n t  thread, won't fa l para ter server thread
          retry += 1
          poke_ nterval = random.randrange(60, 90) + retry * 10
          logg ng.warn("Error gett ng d str buted tra n ng job status, w ll retry after %s seconds." % poke_ nterval)
          t  .sleep(poke_ nterval)
    Thread(target=wa _complete_t n_ex ).start()

  def get_eval_hooks(self):  # pyl nt: d sable=no-self-use
    """ Return Sess onRunHooks used dur ng evaluat on."""
    return None

  def get_pred ct_hooks(self):
    """ Return hooks used dur ng pred ct on.
     f prof ler_steps  s set  n t  constructor to t  Tra ner,
      pass a tf.Tra n.Prof lerHook to t  est mator's pred ct funct on.
    """
    hooks = []
     f self._prof ler_steps  s not None:
      prof ler = tf.tra n.Prof lerHook(
        save_steps=self._prof ler_steps,
        output_d r=self._save_d r
      )
      hooks.append(prof ler)
    return hooks

  def learn(self, tra n_ nput_fn=None, eval_ nput_fn=None,
            tra n_max_steps=None,
            tra n_steps=None, eval_steps=None,
            tra n_hooks=None, eval_hooks=None,
            early_stop_ tr c=None, early_stop_pat ence=-1,
            early_stop_m n m ze=True, early_stop_tolerance=0, start_epoch=0,
            exporters=None, export_output_fn=None, max_durat on=None):
    """
    Tra n and evaluate t  est mator for ``tra n_max_steps`` steps.
    Each epoch  nvolves ``tra n_steps`` tra n ng steps follo d
    by ``eval_steps`` evaluat on steps. Note that each step
     s a ``sess on.run()``, that  s, each batch  s a step.

    Args:
      tra n_max_steps:
        max mum number of global steps of tra n ng to run.
        Defaults to params.tra n_max_steps.
        None-values cause learn() to term nate after *one* call to tra n() and evaluate(),
        wh ch  s usually useful w n us ng tra n_steps=-1
        Non-pos  ve values tra ns  ndef n ely  n a loop (use w h caut on),
        wh ch  s usually useful w n used w h early stopp ng.
      tra n_steps:
        number of tra n ng steps per epoch. For example, 100  ans each
        tra n ng epoch w ll end after process ng 100 batc s.
        Defaults to params.tra n_steps.
        Non-pos  ve values and None-values go through t  ent re tra n ng set each epoch.
      eval_steps:
        number of evaluat on steps per epoch.
        Defaults to params.eval_steps.
        Non-pos  ve values and None-values go through t  ent re evaluat on set each epoch.
      tra n_ nput_fn:
        Funct on to  erate through tra n ng set.    s passed to est mator.tra n.
      eval_ nput_fn:
        Funct on to  erate through evaluat on set.    s passed to est mator.evaluate.
      tra n_hooks:
        L st of Sess onRunHooks uses for tra n ng. Defaults to self.get_tra n_hooks().
      eval_hooks:
        L st of Sess onRunHooks uses for evaluat on. Defaults to self.get_eval_hooks()
      start_epoch:
        T  epoch from wh ch to start learn.  f   want to do tra n ng and evaluat on
        for N epochs,   can call ``learn()``  n a loop as follows:
      exporters:
        L st of exporters called at t  end of each evaluat on run.
        Defaults to none.
      export_output_fn:
        T  output format to use for exported models.
        Only used  f exporters  s not None.

        .. code-block:: python

          for epoch  n range(1,max_epoch):
            tra ner.learn(start_epoch=epoch)

    Early-stopp ng argu nts:
      early_stop_ tr c:
        Str ng spec fy ng t   tr c to early-stop on. Requ red w h pos  ve
        ``early_stop_pat ence``. For example, 'accuracy', 'accuracy_0', 'loss', etc.
        T  str ng  s used to extract t  relevant tensor Op from t  d ct returned by
        t  get_eval_ tr c_ops  thod. For `` tr cs`` pass to t  constructor,
        t  str ng  s one of those. For mult -class (that  s, mult - tr c)
         tr cs, t  str ng may be appended w h a ``_0``, ``_1``, etc. or one
        of t  ``mult _ tr c_na s`` (one per class).
      early_stop_pat ence:
        Max mum number of epochs to wa  for an  mprove nt  n t  early_stop_ tr c
        before break ng off tra n ng. For example, a pat ence of 10  ans that
        tra n ng w ll have 10 epochs to  mprove t   tr c before    s k lled.
        W never t   tr c  s  mproved before runn ng out of pat ence,
        pat ence  s reset to ``early_stop_pat ence``.
        Defaults to -1 (that  s, no early-stopp ng).
      early_stop_m n m ze:
        Set t  to True (t  default) for  tr cs that need to be m n m zed
        (l ke ``loss``).  tr cs l ke ``accuracy`` that need to be max m zed
        should set t  to False.
      early_stop_tolerance:
        A non-negat ve tolerance for compar ng early_stop_ tr c.
        E.g. w n max m z ng t  cond  on  s current_ tr c > best_ tr c + tolerance.
        Defaults to 0.
      max_durat on:
        A float. W n t  argu nt  s def ned, t  job w ll automat cally term nate after
        `max_durat on` seconds  f   has not already compeleted. 

    Returns:
      T  d rectory w re t  c ckpo nts  re saved.
      That  s, save_d r.
        can po nt TensorBoard to t  d rectory to get  tr cs,
      or pass   to anot r Tra ner v a `` n _from_d r`` w n do ng
      mult -phase tra n ng.
    """
    # pyl nt: d sable=too-many-branc s

     f not callable(tra n_ nput_fn):
      ra se ValueError("Expect ng callable tra n_ nput_fn funct on")
     f not callable(eval_ nput_fn):
      ra se ValueError("Expect ng callable eval_ nput_fn funct on")

     f os.env ron.get('TF_CONF G'):
      ra se ValueError("tra ner.learn() can not be used w h d str buted / hogw ld setups")

     f exporters and export_output_fn:
      self._export_output_fn = export_output_fn

    tra n_hooks = self.get_tra n_hooks()  f tra n_hooks  s None else tra n_hooks
    eval_hooks = self.get_eval_hooks()  f eval_hooks  s None else eval_hooks
    eval_hooks = []  f eval_hooks  s None else eval_hooks

     f tra n_max_steps  s None:
      tra n_max_steps = self.params.get('tra n_max_steps')

     f tra n_steps  s None:
      tra n_steps = self.params.tra n_steps
     f tra n_steps <= 0:
      tra n_steps = None

     f eval_steps  s None:
      eval_steps = self.params.eval_steps
     f eval_steps <= 0:
      eval_steps = None

     f early_stop_pat ence > 0:
      assert tra n_max_steps  s not None, "Early stopp ng and max_steps=None are not compat ble."
      # prepare early stopp ng hook (wh ch also handles log c  re)
      self._ s_early_stopp ng = True
      early_stop_hook = twml.hooks.EarlyStopHook(
         tr c=early_stop_ tr c,
        c ckpo nt_d r=self._save_d r,
        pat ence=early_stop_pat ence,
        m n m ze=early_stop_m n m ze,
        tolerance=early_stop_tolerance,
        get_est mator_spec_fn=lambda: self.current_est mator_spec,
        start_epoch=start_epoch)
      # add early stop hook to eval hooks
      eval_hooks.append(early_stop_hook)

     f max_durat on  s not None:
      tra n_early_stop_durat on_hook = twml.hooks.EarlyStopDurat on(
        max_durat on=max_durat on,
        ex _on_end=False,
        save_d r=self._save_d r,
        overwr e=True,
      )
      tra n_hooks.append(tra n_early_stop_durat on_hook)

      eval_early_stop_durat on_hook = twml.hooks.EarlyStopDurat on(
        max_durat on=max_durat on,
        ex _on_end=False,
        save_d r=self._save_d r,
        overwr e=True,
      )
      eval_hooks.append(eval_early_stop_durat on_hook)

     f not self._ s_early_stopp ng:
       f (tra n_max_steps  s not None) and (tra n_max_steps <= 0):
         f ((max_durat on  s not None) and (max_durat on < 0)) or (max_durat on  s None):
          logg ng.warn("tra n.max_steps  s non-pos  ve, and no early or durat on stopp ng  s conf gured. "
                      "Tra n ng job w ll loop forever.")

     f tra n_max_steps  s not None and tra n_max_steps > 0:
      #   can't pass max_steps AND steps to est mator.tra n.
      # so   pass steps to est mator.tra n and max_steps to t  hook  nstead...
      stop_at_step_hook = twml.hooks.StopAtStepHook(last_step=tra n_max_steps)
      tra n_hooks.append(stop_at_step_hook)

    w h self.exper  nt_tracker.track_exper  nt(eval_hooks,
                                                  lambda: self.current_est mator_spec):
      # alternate tra n ng and evaluat on epochs
      epoch = start_epoch
      wh le True:
        logg ng. nfo("Tra n ng epoch %d", epoch)
        self._est mator.tra n(tra n_ nput_fn, steps=tra n_steps, hooks=tra n_hooks)

        logg ng. nfo("Evaluat ng epoch %d", epoch)
        eval_result = self._est mator.evaluate(
          eval_ nput_fn, steps=eval_steps, hooks=eval_hooks)

         f exporters:
          c ckpo nt_path = self.est mator.latest_c ckpo nt()
          for exporter  n exporters:
            export_path = os.path.jo n(self._save_d r, "export", exporter.na )
            exporter.export(
              est mator=self.est mator, export_path=export_path,
              c ckpo nt_path=c ckpo nt_path, eval_result=eval_result,
               s_t _f nal_export=False)

        #  f tra n_max_step  s none. Term nate after one loop.
         f tra n_max_steps  s None:
          break

        #  f stop_at_step_hook requested a stop, break
         f tra n_max_steps > 0 and stop_at_step_hook.stop_requested:
          break

        # early-stopp ng log c  s handled  nternally by t  hook
         f early_stop_pat ence > 0 and early_stop_hook.should_stop:
          # but   st ll need to break  re
          break
        epoch += 1

      self.wr e_state_to_d sk(save_d r=self._save_d r, f lena ='_SUCCESS')

    return self._save_d r

  def get_tra n_spec(self,  nput_fn, max_steps=None, hooks=None):
    """Get t  Tra nSpec used by ``tf.tra n.tra n_and_evaluate``."""
     f not callable( nput_fn):
      ra se ValueError("Expect ng callable tra n_ nput_fn")

     f max_steps  s None:
      max_steps = self.params.tra n_max_steps

     f max_steps  s not None and max_steps <= 0:
      max_steps = None

    hooks = self.get_tra n_hooks()  f hooks  s None else hooks

    return tf.est mator.Tra nSpec( nput_fn= nput_fn,
                                  max_steps=max_steps,
                                  hooks=hooks)

  def get_eval_spec(self,  nput_fn, steps=None, delay=None, per od=None,
                    hooks=None, exporters=None):
    """Get t  EvalSpec used by ``tf.tra n.tra n_and_evaluate``."""
     f not callable( nput_fn):
      ra se ValueError("Expect ng callable eval_ nput_fn")

     f steps  s None:
      steps = self.params.eval_steps

     f steps <= 0:
      steps = None

     f delay  s None:
      delay = self.params.eval_delay

     f per od  s None:
      per od = self.params.eval_per od

    hooks = self.get_eval_hooks()  f hooks  s None else hooks

    eval_na  = self.params.get("eval_na ", None)

    return tf.est mator.EvalSpec( nput_fn= nput_fn,
                                 steps=steps,
                                 na =eval_na ,
                                 start_delay_secs=delay,
                                 throttle_secs=per od,
                                 hooks=hooks,
                                 exporters=exporters)

  def tra n_and_evaluate(self, tra n_ nput_fn=None, eval_ nput_fn=None,
                         tra n_max_steps=None, eval_steps=None,
                         eval_delay=None, eval_per od=None,
                         tra n_hooks=None, eval_hooks=None,
                         early_stop_ tr c=None, early_stop_pat ence=-1,
                         early_stop_m n m ze=True, early_stop_tolerance=0, exporters=None,
                         export_output_fn=None, max_durat on=None):
    """
    Tra n and evaluate t  est mator for ``tra n_max_steps``
    us ng ``tf.est mator.tra n_and_evaluate``.
    W h a cluster conf gurat on prov ded  n t  ``TF_CONF G`` env ron nt var able, t   thod
    can be used for d str buted tra n ng (mult -node or mult -process).
    Unl ke t  ``learn``  thod, tra n ng  s cont nuous w h ``tra n_max_steps``.
    For d str buted use case, evaluat on happens per od cally.
    That  s, after ``eval_delay`` seconds, an evaluat on epoch of ``eval_step`` steps
    occurs every ``eval_per od`` seconds. Evaluat on happens on t  most recent c ckpo nt.
    TF defaults to sav ng c ckpo nts every 10 m ns.
    For local use case, tra n ng occurs for tra n_max_steps epochs follo d by a
    s ngle evaluat on. For local use case   t refore recom nd us ng learn()  nstead
    as   prov des early-stopp ng and mult ple evaluat ons.

    ``tra n_and_evaluate`` w ll evaluate for ``eval_steps`` every ``eval_per od`` seconds.
      w ll stop after ``tra n_steps``  s reac d.

      must ensure that all workers/servers are ass gned t  sa  `save_d r`.

    .. Note::

       f t  TF_CONF G env ron nt var able  s set, t  funct on assu s  s runn ng a d str bute job.

    Args:
      tra n_ nput_fn:
        Funct on to  erate through tra n ng set.    s passed to est mator.tra n_and_evalute
      eval_ nput_fn:
        Funct on to  erate through evaluat on set.    s passed to est mator.tra n_and_evalute.
      tra n_max_steps:
        max mum number of global steps of tra n ng to run.
        Defaults to params.tra n_max_steps.
        Non-pos  ve values and None-values tra n  ndef n ely (use w h caut on).
      eval_steps:
        number of steps per evaluat on.
        Defaults to params.eval_steps.
        Non-pos  ve values and None-values go through
        t  ent re evaluat on set for each evaluat on.
        Note that t  number of eval_steps should be h gh enough to m n m ze no se.
        T   s espec ally true for early-stopp ng.
      eval_delay:
        Start t  f rst evaluat on after eval_delay. Defaults to params.eval_delay or 2*60s.
      eval_per od:
        Run an evaluat on every eval_per od seconds. Defaults to params.eval_per od or 10*60s.
      exporters:
        L st of exporters called at t  end of each evaluat on run.
        Defaults to none.
      export_output_fn:
        T  output format to use for exported models.
        Only used  f exporters  s not None.

    Early-stopp ng argu nts:
      early_stop_ tr c:
        Str ng spec fy ng t   tr c to early-stop on. Requ red w h pos  ve
        ``early_stop_pat ence``. For example, 'accuracy', 'accuracy_0', 'loss', etc.
        T  str ng  s used to extract t  relevant tensor Op from t  d ct returned by
        t  get_eval_ tr c_ops  thod. For `` tr cs`` pass to t  constructor,
        t  str ng  s one of those. For mult -class (that  s, mult - tr c)
         tr cs, t  str ng may be appended w h a ``_0``, ``_1``, etc. or one
        of t  ``mult _ tr c_na s`` (one per class).
      early_stop_pat ence:
        Max mum number of epochs to wa  for an  mprove nt  n t  early_stop_ tr c
        before break ng off tra n ng. For example, a pat ence of 10  ans that
        tra n ng w ll have 10 epochs to  mprove t   tr c before    s k lled.
        W never t   tr c  s  mproved before runn ng out of pat ence,
        pat ence  s reset to ``early_stop_pat ence``.
        Defaults to -1 (that  s, no early-stopp ng).
      early_stop_m n m ze:
        Set t  to True (t  default) for  tr cs that need to be m n m zed
        (l ke ``loss``).  tr cs l ke ``accuracy`` that need to be max m zed
        should set t  to False.
      early_stop_tolerance:
        A non-negat ve tolerance for compar ng early_stop_ tr c.
        E.g. w n max m z ng t  cond  on  s current_ tr c > best_ tr c + tolerance.
        Defaults to 0.
      max_durat on:
        A float. W n t  argu nt  s def ned, t  job w ll automat cally term nate after
        `max_durat on` seconds  f   has not already compeleted. 

    Returns:
      T  d rectory w re t  c ckpo nts  re saved.
    """

    logg ng. nfo("WARN NG: Tra ner.tra n_and_evaluate  s an EXPER MENTAL AP .")
    logg ng. nfo("Tra ner.tra n_and_evaluate may change or be removed  n future vers ons.")

     f not callable(tra n_ nput_fn):
      ra se ValueError("Expect ng callable tra n_ nput_fn funct on")
     f not callable(eval_ nput_fn):
      ra se ValueError("Expect ng callable eval_ nput_fn funct on")

    self._ex _ps_after_tra n ng_complete()

    # Maybe export  n eval processes.
     f self. s_evaluator():
       f self.params.get("eval_na ")  s not None:
        # Do not export  f runn ng spec al eval.
        exporters = None
        export_output_fn = None
      el f exporters and export_output_fn:
        self._export_output_fn = export_output_fn
      else:
        # Default opt on.
        self._export_output_fn = None

    tra n_hooks = self.get_tra n_hooks()  f tra n_hooks  s None else tra n_hooks
    tra n_hooks = []  f tra n_hooks  s None else tra n_hooks

    eval_hooks = self.get_eval_hooks()  f eval_hooks  s None else eval_hooks
    eval_hooks = []  f eval_hooks  s None else eval_hooks

     f tra n_max_steps  s None:
      tra n_max_steps = self.params.get('tra n_max_steps')

     f eval_steps  s None:
      eval_steps = self.params.eval_steps
     f eval_steps <= 0:
      eval_steps = None

     f eval_delay  s None:
      eval_delay = self.params.eval_delay
     f eval_per od  s None:
      eval_per od = self.params.eval_per od

     f early_stop_pat ence > 0:
      # w n tra n ng hooks detect t  f le, t y request a stop to tra n ng
      early_stop_path = os.path.jo n(self._save_d r, 'earlystop_now.txt')
      # prepare early stopp ng hook (wh ch also handles log c  re)

      self._ s_early_stopp ng = True

      eval_early_stop_hook = twml.hooks.EarlyStopHook(
         tr c=early_stop_ tr c,
        c ckpo nt_d r=self._save_d r,
        pat ence=early_stop_pat ence,
        m n m ze=early_stop_m n m ze,
        tolerance=early_stop_tolerance,
        get_est mator_spec_fn=lambda: self.current_est mator_spec,
        f le_path=early_stop_path,
        ex _on_end=os.env ron.get('TF_CONF G')  s not None)  # only ex  for d str buted jobs
      # add early stop hook to eval hooks
      eval_hooks.append(eval_early_stop_hook)

      # prepare t  com nsurate tra n ng hook
      tra n_early_stop_hook = twml.hooks.Stop fEx stsHook(early_stop_path)
      tra n_hooks.append(tra n_early_stop_hook)

     f max_durat on  s not None:
      tra n_early_stop_durat on_hook = twml.hooks.EarlyStopDurat on(
        max_durat on=max_durat on,
        ex _on_end=False,
        save_d r=self._save_d r,
        overwr e=self. s_ch ef()
      )
      eval_early_stop_durat on_hook = twml.hooks.EarlyStopDurat on(
        max_durat on=max_durat on,
        ex _on_end=os.env ron.get('TF_CONF G')  s not None,
        save_d r=self._save_d r,
        overwr e=False
      )  # only ex  for d str buted jobs

      tra n_hooks.append(tra n_early_stop_durat on_hook)
      eval_hooks.append(eval_early_stop_durat on_hook)

    w h self.exper  nt_tracker.track_exper  nt(eval_hooks, lambda: self.current_est mator_spec):
      tra n_spec = self.get_tra n_spec(tra n_ nput_fn, tra n_max_steps, tra n_hooks)
      eval_spec = self.get_eval_spec(eval_ nput_fn, eval_steps,
                                     eval_delay, eval_per od,
                                     eval_hooks, exporters)
      self._tra n_and_evaluate(tra n_spec, eval_spec)

     f self. s_ch ef():
      self.wr e_state_to_d sk(save_d r=self._save_d r, f lena ='_SUCCESS')

    return self._save_d r

  def _tra n_and_evaluate(self, tra n_spec, eval_spec):
    """
    Pr vate  thod that calls
    ``tf.est mator.tra n_and_evaluate(self._est mator, tra n_spec, eval_spec)``.
    """
    try:
      tf.est mator.tra n_and_evaluate(self._est mator, tra n_spec, eval_spec)
    except twml.errors.EarlyStopError:
      #  gnore t  except on  f on evaluator.
       f self. s_evaluator():
        pass
      else:
        ra se

  def tra n(self,  nput_fn=None, steps=None, hooks=None):
    """
    Tra n t  est mator for `steps` tra n ng steps.

    Args:
      steps:
        number of steps for wh ch to perform tra n ng. For example, 100  ans each
        evaluat on w ll end after process ng 100 batc s.
        Defaults to None.  .e. tra ns on t  ent re dataset a s ngle t  .
        Non-pos  ve values and None-values go through t  ent re tra n ng set each epoch.
       nput_fn:
        Funct on to  erate through tra n ng set.    s passed to est mator.tra n.
      hooks:
        L st of Sess onRunHooks uses for tra n ng. Defaults to self.get_tra n_hooks().
    """
     f os.env ron.get('TF_CONF G') and " s_cal brat ng" not  n self.params:
      ra se ValueError("tra ner.tra n() can not be used w h d str buted / hogw ld setups")

     f not callable( nput_fn):
      ra se ValueError("Expect ng callable  nput_fn funct on")

     f self._ s_early_stopp ng:
      ra se ValueError("Can not call tra n() after learn() w n us ng early stopp ng.")

    hooks = self.get_tra n_hooks()  f hooks  s None else hooks
    self._est mator.tra n( nput_fn, steps=steps, hooks=hooks)
    return self

  def evaluate(self,  nput_fn=None, steps=None, hooks=None, na =None):
    """
    Evaluate t  est mator for `steps` evaluat on steps.

    Args:
      steps:
        number of steps for wh ch to perform evaluat on. For example, 100  ans each
        evaluat on w ll end after process ng 100 batc s.
        Defaults to None.  .e. evaluates on t  ent re dataset a s ngle t  .
        Negat ve values and None-values go through t  ent re tra n ng set each epoch.
       nput_fn:
        Funct on to  erate through evaluat on set.    s passed to est mator.evaluate.
      hooks:
        L st of Sess onRunHooks used for evaluat on. Defaults to None.
        Note that, unl ke learn(), hooks defaults to None  nstead of self.get_eval_hooks()
        as t  latter may  mple nt early-stopp ng, wh ch  sn't necessar lty t  des red
        behav or w n call ng evaluate() on  s own.
      na :
        Na  of t  evaluat on  f user needs to run mult ple evaluat ons on d fferent data sets.
         tr cs for d fferent evaluat ons are saved  n separate folders,
        and appear separately  n tensorboard.

    Returns:
       f ` s_evaluator()`, returns a d ct conta n ng t  evaluat on  tr cs spec f ed
       n ` tr c_fn` keyed by na , as  ll as an entry `global_step` that conta ns
      t  value of t  global step for wh ch t  evaluat on was perfor d.
      Ot rw se ( .e. ` s_evaluator() == False`), returns None.
    """
     f not self. s_evaluator():
      return None

     f not callable( nput_fn):
      ra se ValueError("Expect ng callable  nput_fn funct on")

    hooks = self.get_eval_hooks()  f hooks  s None else hooks
    hooks = []  f hooks  s None else hooks

    # for cons stency w h tra n/learn
    eval_steps = None  f steps  s not None and steps < 0 else steps

    w h self.exper  nt_tracker.track_exper  nt(hooks, lambda: self.current_est mator_spec, na =na ):
      c ckpo nt = self.best_or_latest_c ckpo nt
      computed_ tr cs = self._est mator.evaluate(
         nput_fn,
        steps=eval_steps,
        hooks=hooks,
        c ckpo nt_path=c ckpo nt,
        na =na 
      )

    return computed_ tr cs

  def start_tensorboard(self, port=None):
    """
    Start tensorboard process to v sual ze logs  n save_d r.
    """
    logg ng. nfo("Start ng tensorboard.")
     f self._tensorboard_handle:
      logg ng.warn("Tensorboard already runn ng. Noth ng done.")
      return

     f port  s None:
       f 'tensorboard_port' not  n self.params.values():
        ra se ValueError('  must spec fy a port for tensorboard to run on.')
      el f self.params.tensorboard_port  s None:
        return
      else:
        port = self.params.tensorboard_port

    mldash_path = 'exper  nts'
     f self.exper  nt_tracker.path:
      mldash_path += '/%s' % encode_url(self.exper  nt_tracker.exper  nt_ d)
    tensorboard_args = ['--logd r=%s' % self._save_d r, '--port=%d' % port]

    try:
      args = ['ema l_and_launch_tensorboard', mldash_path, '--'] + tensorboard_args
      self._tensorboard_handle = subprocess.Popen(args)
    except OSError:
      try:
        self._tensorboard_handle = subprocess.Popen(['tensorboard'] + tensorboard_args)
      except OSError:
        try:
          # t  w ll work w h Tw ter  nternal pants bu ld w n run locally
          args = ['./pants', 'run', 'twml:tensorboard', '--'] + tensorboard_args
          self._tensorboard_handle = subprocess.Popen(args)
        except OSError:
          logg ng.error("No tensorboard  nstalled, won't able to v sual ze tra n ng  n tensorboard.")

  def stop_tensorboard(self):
    """
    Shutdown t  Tra ner's assoc ated Tensorboard.
    """
     f self._tensorboard_handle:
      logg ng. nfo("Shutt ng down tensorboard.")
      self._tensorboard_handle.k ll()
    else:
      logg ng.warn("No known tensorboard process. Noth ng done.")

  def cal brate(self,
                cal brator,
                steps=None,
                 nput_fn=None,
                save_cal brator=True,
                hooks=None):
    """
    Cal brate t  cal brator for `steps` cal brat on steps us ng t  est mator.tra n  thod.
    T  bu ld_graph passed to t  Tra ner constructor should
    call cal brator.accumulate us ng so th ng l ke tf.py_func.
    That way, w n t   thod calls est mator.tra n t  cal brator w ll
    accumulate one epoch of samples. After wh ch, t   thod calls cal brator.cal brate().
       s up to t  user to t n call cal brator.save() to save t  cal brated Layer
    and ot r  nformat on to d sk for mult -phase tra n ng.

    Args:
      cal brator:
        a twml.Cal brator  nstance or a d ct of t  form {na (str): twml.Cal brator}.
      steps:
        Max mum steps to accumulate examples for cal brat on. Opt onal.
         f not spec f ed, examples w ll be accumulated unt l all downsampled parts are processed.
       nput_fn:
        Funct on to  erate through tra n ng set.    s passed to est mator.tra n.
      hooks:
        L st of Sess onRunHooks uses for tra n ng. Defaults to self.get_tra n_hooks().
      save_cal brator:
        Boolean (default: True).  f set to True   w ll save t  cal brator layer.
    """

     f not callable( nput_fn):
      ra se ValueError("Expect ng callable  nput_fn funct on")

    # mak ng everyth ng a d ct to avo d mult ple  fs
     f  s nstance(cal brator, twml.contr b.cal brators.Cal brator):
      cal brator = {"default": cal brator}

    # T   s a dum  call to tra n, s nce   cannot pred ct w hout tra n ng
    # from t  Est mator AP 
    self._est mator.tra n( nput_fn, steps=1)
    max_steps = steps  f steps  s not None else -1
    for na , clbrt  n sorted(cal brator. ems(), key= emgetter(0)):
      count = 0
      for out  n self._est mator.pred ct( nput_fn, hooks=hooks, y eld_s ngle_examples=False):
         f max_steps > 0 and count > max_steps:
          break
        clbrt.accumulate_feature(out)
        count += 1
      clbrt.cal brate()

    # t  step  s done to allow us to keep t  current phases event f le for
    # v sual zat on on Tensorboard.   removes all f les that
    # are not event f les. T  p ece of code should be deprecated w n
    #   deprecate t  MDL cal brator (CX-12329)
    for fna   n tf. o.gf le.l std r(self._save_d r):
       f not fna .startsw h("events"):
        tf. o.gf le.remove(os.path.jo n(self._save_d r, fna ))

     f save_cal brator:
      #  f   only have one cal brator, t  cal brator s gnature
      # w ll be set to default
       f len(cal brator) == 1:
        cal brator = cal brator['default']
        cal brator.save(
          self.params.save_d r,
          na =cal brator.na ,
          verbose=True
        )
      else:
        for na , clbrt  n cal brator. ems():
          clbrt.save(
            self.params.save_d r,
            na =clbrt.na  + str(na ),
            verbose=True
          )

  def pred ct(self, *args, **kwargs):
    """
    Wrapper over t  tensorflow `Est mator.pred ct
    <https://www.tensorflow.org/ap _docs/python/tf/est mator/Est mator#pred ct>`_.
     thod. See that docu ntat on for descr pt on of argu nts accepted.

     f hooks  s passed as an argu nt, t  spec f ed hooks are used.
    Else w n prof ler_steps  s spec f ed  n t  constructor of t  Tra ner, a
    tf.tra n.Prof lerHook  s passed to t  pred ct  nterface.
    Ot rw se, hooks  s set to an empty l st.
    """
     f 'hooks' not  n kwargs and len(args) < 3:
      #  f hooks  s not spec f ed as a keyword argu nt, nor as a pos  onal argu nt
      # add hooks as a keyword argu nt.
      kwargs['hooks'] = self.get_pred ct_hooks()

    return self.est mator.pred ct(*args, **kwargs)

  def hub_export(self,
                 na ,
                 serv ng_ nput_rece ver_fn,
                 export_d r=None,
                 c ckpo nt_path=None,
                 export_task_type_overr der=None):
    """
    Exports reg stered modules  nto a save d rectory.

    T   thod creates a d rectory under export_path w h t  save TF Hub.
    One sub-d rectory (na d export_na ) per module reg stered v a reg ster_module_for_export.

    Argu nts:
      na :
        un que na  of t  module to export.
      serv ng_ nput_rece ver_fn:
        A funct on w h no argu nts that returns a Serv ng nputRece ver.
        T   s used w h t  est mator passed to export() to bu ld t  graph ( n PRED CT mode)
        that reg sters t  modules for export. T  model  n that graph  s never run,
        so t  actual data prov ded by t   nput fn does not matter.
      export_d r:
        A str ng conta n ng a d rectory w re to wr e t  export d rector es.
        Defaults to t  save_d r.
      c ckpo nt_path:
        T  c ckpo nt path to export. Defaults to t  latest.
      export_task_type_overr der:
        Spec f es t  task type that w ll overr de t  default task type used for export
        (hogw ld tra n ng defaults to evaluator, ot rw se, defaults to ch ef)
    """
     f export_task_type_overr der:
       f not self. s_task_type(export_task_type_overr der):
        logg ng. nfo(
          f"Tra ner.hub_export  gnored due to process not be ng {export_task_type_overr der}")
        return
    else:
       f self._us ng_hogw ld:
         f not self. s_evaluator():
          logg ng. nfo("Tra ner.hub_export  gnored due to t  process not be ng evaluator.")
          return
      else:
         f not self. s_ch ef():
          logg ng. nfo("Tra ner.hub_export  gnored due to t  process not be ng ch ef.")
          return

     f export_d r:
      export_d r = san  ze_hdfs_path(export_d r)

     f c ckpo nt_path:
      c ckpo nt_path = san  ze_hdfs_path(c ckpo nt_path)
    else:
      c ckpo nt_path = self.best_or_latest_c ckpo nt

    export_d r = export_d r  f export_d r  s not None else self._save_d r
    exporter = hub.LatestModuleExporter(na , serv ng_ nput_rece ver_fn)
    # T  path_exporter by default conta ns a t  stamp d rectory  n  s path.
    path_exporter = exporter.export(est mator=self.est mator,
                                    export_path=export_d r,
                                    c ckpo nt_path=c ckpo nt_path)

    # LatestModuleExporter.export() returns a b nary str ng on Cloud ML Eng ne
    # but tf. o.gf le.l std r() does not; t   s an  ssue w n jo n ng paths
     f  s nstance(path_exporter, bytes):
      path_exporter = path_exporter.decode()

    # Copy ng t  saved hub module to export_d r so   don't need to spec fy
    # t  t  stamp w n load ng t  module.
    # T   s a workaround due to t  current  mple ntat on of hub.LatestModuleExporter.
    # T  works for mult ple hub modules.
    hub_exported_modules = tf. o.gf le.l std r(path_exporter)

    backup_d r = os.path.jo n(export_d r, "backups",
                              datet  .datet  .now().strft  ('%Y-%m-%d_%H-%M-%S'))

    for folder  n hub_exported_modules:
      hub_module_oldpath = os.path.jo n(path_exporter, folder)
      hub_module_newpath = os.path.jo n(export_d r, folder)

      #  f t  dest nat on already ex sts, move to backup
       f tf. o.gf le.ex sts(hub_module_newpath):
        # Ensure backup_d r ex sts
        tf. o.gf le.maked rs(backup_d r)
        hub_module_backup = os.path.jo n(backup_d r, folder)
        tf. o.gf le.rena (hub_module_newpath, hub_module_backup)

      tf. o.gf le.rena (hub_module_oldpath, hub_module_newpath)

    # S nce t  t  stamped folder ex sts but  s empty,   can delete  .
    tf. o.gf le.rmtree(path_exporter)

  def _ s_on_gke(self) -> bool:
    """Returns True  f runn ng on gke."""
    cluster = os.env ron.get('TWML_JOB_CLUSTER')
     f not cluster or cluster  n {'smf1', 'atla'}:
      return False
    return True

  def _maybe_del_tsd_ex (self, state_f les) -> None:
    """Handle potent al early ex  and Tw terSetDeploy nt delet on.

       f:
        - d str buted tra n ng
        - runn ng GKE
        - tra n ng  s f n s d (all state_f les ex sts)
        w ll ex  early and not restart work

       f --d str buted_tra n ng_cleanup = True t n   w ll also handle
      clean ng up t  Tw terSetDeploy nts.

      Args:
        state_f les: A python l st  nd cate state f les to determ ne t  f n sh 
        state of t  job.
    """
    # job type that  s respons ble for exper  nt track ng w ll rema n al ve
    # unt l   marks t  exper  nt as f n s d.
     f self.exper  nt_tracker._env_el g ble_for_record ng_exper  nt:
      exp_status = self.exper  nt_tracker.get_run_status()
       f exp_status and exp_status not  n {'Success', 'Fa led'}:
        logg ng. nfo(
          f"Not ex  ng early because exper  nt  s st ll {exp_status}."
        )
        return

    # do not bot r  f   are on prem
     f not self._ s_on_gke():
      logg ng. nfo("No need to ex  early because runn ng on prem.")
      return

    states = [
      twml.ut l.f le_ex st_ n_d r(self._save_d r, state_f le) for state_f le  n state_f les]
    do_not_restart = (self._params.get('d str buted') and all(states))
     f not do_not_restart:
      return

    logg ng. nfo(
      f"Ex  ng early because a _SUCCESS f le already ex sts  n {self._save_d r}")
     f self._params.get('d str buted_tra n ng_cleanup'):
      res ce_na  = '-'.jo n([
        os.env ron['TWML_JOB_NAME'],
        os.env ron['TWML_D STR BUTED_JOB_TYPE'],
        os.env ron['TWML_JOB_ENV'],
      ])
      logg ng. nfo(f"Delet ng Tw terSetDeploy nt {res ce_na }")
      # each job type w ll manage  s own delet on so that delet on happens
      #  n t  tra ner  n  call for every job type
      # ot rw se   may k ll anot r job type dur ng an  mportant
      # process l ke exper  nt track ng manage nt (handled by t  evaluator
      kubectl_delete_by_na (
        zone=None,
        na space=os.env ron['TWML_JOB_ROLE'],
        res ce_type=Res ce.TW TTERSETDEPLOYMENTS.value,
        res ce_na =res ce_na ,
        wa =False,
      )
    sys.ex (0)

  def wr e_state_to_d sk(self, save_d r, f lena ='_SUCCESS') -> None:
    """Wr e state f le to d sk to  nd cate t  state of tra n ng process. T   s usually used 
      to mark t  state of tra n ng progress and determ ne t  start w n job restarts/resu s.
    Args:
      save_d r: A str of local/gcs/hdfs d r to wr e t  state f le.
      f le_na : A str  nd cate t  state f le. Default to `_SUCCESS`.
    """
    f le_path = os.path.jo n(save_d r, f lena )
     f tf. o.gf le.ex sts(f le_path):
      tf.logg ng.warn(f'{f le_path} already ex st.')
      return

    w h tf. o.gf le.GF le(f le_path, 'w') as f:
      f.wr e('')