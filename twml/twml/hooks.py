""" T  f le conta ns tf.tra n.Sess onRunHooks def ned by TWML """
from datet    mport datet  
 mport json
 mport operator
 mport os

from absl  mport logg ng
 mport numpy as np
 mport tensorflow.compat.v1 as tf
from tensorflow.python.tra n ng.bas c_sess on_run_hooks  mport NeverTr ggerT  r, SecondOrStepT  r
 mport twml


class StepProgressHook(tf.tra n.Sess onRunHook):
  """Hook that d splays a progress bar to mon or global step progress """

  def __ n __(self, max_step):
    """
     n  al zes a `StepProgressHook`.
    T  hook d splays a progress bar for max_steps.

    Note that t  hook only works for tra n ng and cal brat on.

    Args:
      max_steps:
        max mum steps to mon or  n progress bar.
        W n t  many steps  s reac d, t  progress bar w ll be full.
    """
    self._max_step = max_step
    self._start_step = 0
    self._global_step_tensor = None
    self._progress_bar = None

  def beg n(self):
    """ sets t  global_step_tensor """
    self._global_step_tensor = tf.tra n.get_or_create_global_step()
     f self._global_step_tensor  s None:
      ra se Runt  Error("Global step should be created to use StepProgressHook.")

  def after_create_sess on(self, sess on, coord):
    """ creates t  progress bar and keeps track of t  f rst global step upon sess on creat on """
    global_step = sess on.run(self._global_step_tensor)
    self._start_step = global_step
    self._progress_bar = tf.keras.ut ls.Progbar(self._max_step)

  def before_run(self, run_context):  # pyl nt: d sable=unused-argu nt
    """  nvoked before call ng sess on.run """
    return tf.tra n.Sess onRunArgs(self._global_step_tensor)

  def after_run(self, run_context, run_values):
    """  nvoked after run  s called. Updates t  progress bar. """
    step = run_context.sess on.run(self._global_step_tensor)
    self._progress_bar.update(step - self._start_step)


class Get tr csHook(tf.tra n.Sess onRunHook):
  """
  Hook used to obta n evaluat on  tr cs.
  Typ cally used for early-stopp ng by obta n ng t  value of a
   tr c at t  end of an epoch.
  Note that t   tr c tensor and  s com nsurate update Op
  are respons ble for aggregat ng t   tr c dur ng t  sess on
  (one sess on per epoch). Used for evaluat on.
  """

  def __ n __(self, get_ tr cs_fn):
    """Get tr csHook constructor.

    Args:
      get_ tr cs_fn:
        Funct on that returns a d ct mapp ng  tr c keys to
        tensors as a tf.Tensor.
        See Tra ner.learn for an example use-case.
    """

    self._get_ tr cs_fn = get_ tr cs_fn
    self._ tr c_tensors = None
    self. tr c_values = None

  def beg n(self):
    """ sets t  global_step_tensor and  tr c tensor"""
    self._ tr c_tensors = self._get_ tr cs_fn()
    assert  s nstance(self._ tr c_tensors, d ct)

  def end(self, sess on):
    self. tr c_values = sess on.run(self._ tr c_tensors)


class EarlyStopHook(Get tr csHook):
  """
  A Get tr csHook aug nted w h early-stopp ng log c for use
  w h n t  Tra ner.learn  thod.
  """

  def __ n __(self,
                tr c,
               pat ence,
               m n m ze,
               get_est mator_spec_fn,
               c ckpo nt_d r,
               f le_path=None,
               ex _on_end=True,
               start_epoch=0,
               tolerance=0):
    """
    Prepare early-stopp ng hook and var ables.

    Args:
       tr c:
        Str ng spec fy ng t   tr c to early-stop on. Requ red w h pos  ve
        ``early_stop_pat ence``. For example, 'accuracy', 'accuracy_0', 'loss', etc.
        T  str ng  s used to extract t  relevant tensor Op from t  d ct returned by
        t  get_eval_ tr c_ops  thod. For `` tr cs`` pass to t  constructor,
        t  str ng  s one of those. For mult -class (that  s, mult - tr c)
         tr cs, t  str ng may be appended w h a ``_0``, ``_1``, etc. or one
        of t  ``mult _ tr c_na s`` (one per class).
      pat ence:
        Max mum number of epochs to wa  for an  mprove nt  n t  early_stop_ tr c
        before break ng off tra n ng. For example, a pat ence of 10  ans that
        tra n ng w ll have 10 epochs to  mprove t   tr c before    s k lled.
        W never t   tr c  s  mproved before runn ng out of pat ence,
        pat ence  s reset to ``early_stop_pat ence``.
      m n m ze:
        Set t  to True for  tr cs that need to be m n m zed
        (l ke ``loss``).  tr cs l ke ``accuracy`` that need to be max m zed
        should set t  to False.
      tolerance:
        A non-negat ve tolerance for compar ng early_stop_ tr c.
        e.g. w n max m z ng t  cond  on  s current_ tr c > best_ tr c + tolerance."
        Defaults to 0.
      get_est mator_spec_fn:
        funct on that returns t  current Est matorSpec.
        T  Est matorSpec  s used to obta n t  current eval_ tr c_ops.
      c ckpo nt_d r:
        path to d rectory conta n ng t  Est mator c ckpo nts.
      f le_path:
        path to f le that  s used by t  hook to commun cate early-stopp ng
        to Stop fEx stsHook. T  hook would be used for evaluat on, wh le
        t  Stop fEx stsHooks (t  l steners) would be used for tra n ng.
        W n t  f le  s created, t  Stop fEx stsHooks detect and term nate tra n ng.
        T  argu nt  s used by ``Tra ner.tra n_and_evaluate``.
      ex _on_end:
        w n t  end()  thod  s called to  nd cate that t  sess on  s term nat ng,
        and ex _on_end  s True, twml.errors.EarlyStopError()  s tr ggered to stop t  evaluat on job.
        T   s set to False by t  tra ner for non d str buted jobs.
      start_epoch:
        Spec f es t  start ng epoch number. T   s used for logg ng purposes only.
    """
     f not  s nstance( tr c, str):
      ra se ValueError("Expect ng str ng for  tr c arg")
     f not  s nstance(pat ence,  nt):
      ra se ValueError("Expect ng pos  ve number for  tr c arg")

    self.should_stop = False
    self._ tr c =  tr c
    self._pat ence = pat ence
    self._current_pat ence = pat ence
    self._c ckpo nt_d r = c ckpo nt_d r
    self._ex _on_end = ex _on_end
    self._latest_c ckpo nt_path = None
    # used for d str buted tra n ng (tf.est mator.tra n_and_evaluate)
    self._f le_path = f le_path
    self._epoch = start_epoch
     f self._f le_path  s not None:
      # TODO try to read epoch from a f le that   create
       f tf. o.gf le.ex sts(self._f le_path):
        # delete t  f le  f   ex sts (not sure t  makes sense)
        logg ng. nfo("EarlyStopHook: Remov ng ex st ng f le: %s.", self._f le_path)
        tf. o.gf le.remove(self._f le_path)

    # best_c ckpo nt d r w ll conta n t  best c ckpo nt
    self._best_c ckpo nt_path = os.path.jo n(c ckpo nt_d r, 'best_c ckpo nt')
    self._eval_c ckpo nt_path = os.path.jo n(c ckpo nt_d r, 'eval_c ckpo nt')
    self._best_ tr c_path = os.path.jo n(self._best_c ckpo nt_path, self._ tr c)

     f tf. o.gf le.ex sts(self._best_ tr c_path):
      w h tf. o.gf le.GF le(self._best_ tr c_path, mode="r") as f:
        best_ tr c_from_f le = float(f.read())
    else:
      best_ tr c_from_f le = None

     f m n m ze:
      # current < best :  s better
      self._ s_better_than = operator.lt
      # worse  tr c poss ble
       f best_ tr c_from_f le  s None:
        self._best_ tr c = np. nf
      else:
        self._best_ tr c = best_ tr c_from_f le - tolerance
      # used for pr nt ng
      self._early_stop_na  = "m n mum"
    else:
      # current > best :  s better
      self._ s_better_than = operator.gt
      # worse  tr c poss ble
       f best_ tr c_from_f le  s None:
        self._best_ tr c = -np. nf
      else:
        self._best_ tr c = best_ tr c_from_f le + tolerance
      # used for pr nt ng
      self._early_stop_na  = "max mum"

    def get_ tr cs_fn():
      """ funct on to get  tr c tensors to early-stopp ng """
      est mator_spec = get_est mator_spec_fn()
      eval_ tr c_ops = est mator_spec.eval_ tr c_ops
       f  tr c not  n eval_ tr c_ops:
        ra se ValueError(
          "Expect ng early_stop_ tr c '%s' key  n eval_ tr c_ops d ct"
          % ( tr c))
      # get t  value_op from t  (value_op, update_op) value
      return {k: v[0] for k, v  n eval_ tr c_ops. ems()}

    #  n  al ze Get tr csHook to get current value of  tr c from sess on
    super(EarlyStopHook, self).__ n __(get_ tr cs_fn=get_ tr cs_fn)

  def early_stop(self, epoch):
    """
    Looks at t  current value of t  early stopp ng  tr c.
    Decre nts current pat ence.  f  tr c  mproves, pat ence  s reset
    and latest c ckpo nt  s moved to c ckpo nt_d r/best_c ckpo nt.
     f current pat ence reac s zero, returns True.

    Args:
      epoch:
        T  current epoch number.

    Returns:
      True w n early-stopped. False ot rw se.
    """
    # decre nt pat ence
    self._current_pat ence -= 1

    # get t  current  tr c value
    current_ tr c = self. tr c_values[self._ tr c]

     f self._ s_better_than(current_ tr c, self._best_ tr c):
      # save best vers on of model
      self._best_ tr c = current_ tr c
      logg ng. nfo(
        "Found new %s %s=%f @ epoch %d",
        self._early_stop_na , self._ tr c, self._best_ tr c, epoch)
      # backup t  f le to c ckpo nt_d r/best_c ckpo nt
      assert self._latest_c ckpo nt_path, "expect ng latest c ckpo nt"
      logg ng. nfo("Back ng up " + self._latest_c ckpo nt_path)

      try:
        eval_c ckpo nt = tf.tra n.latest_c ckpo nt(self._eval_c ckpo nt_path)
        twml.ut l.backup_c ckpo nt(
          c ckpo nt_path_pref x=eval_c ckpo nt,
          backup_path=self._best_c ckpo nt_path)
      except twml.errors.C ckpo ntNotFoundError as ex:
        msg = "Cons der  ncreas ng 'keep_c ckpo nt_max' or 'save_c ckpo nt_secs'"
        ra se twml.errors.C ckpo ntNotFoundError(str(ex) + "\n" + msg)

      tf. o.gf le.maked rs(os.path.d rna (self._best_ tr c_path))
      w h tf. o.gf le.GF le(self._best_ tr c_path, mode="w") as f:
        # Wr e w h enough prec s on
        f.wr e("%.8f" % self._best_ tr c)

      # reset pat ence
      self._current_pat ence = self._pat ence

    el f self._current_pat ence > 0:
      logg ng. nfo("No new %s found after %d epochs",
                   self._early_stop_na , self._pat ence - self._current_pat ence)
    el f self._current_pat ence == 0:
      logg ng. nfo(
        "No new %s found after %d epochs. Early-stopp ng exper  nt.",
        self._early_stop_na , self._pat ence)
      return True

    return False

  def cleanup_c ckpo nts(self):
    """
    makes   so that t  best c ckpo nt  s t  only c ckpo nt
     n c ckpo nt_d r.
    """
    ra se Not mple ntedError("cleanup_c ckpo nts  s no longer supported")

  def end(self, sess on):
    """
    T   thod  s called at t  end of an evaluat on/epoch.
    W n f le_path constructor argu nt  s prov ded, t 
    w ll call ``early_stop()``.
    W n ``early_stop()`` returns True,   creates t  f le_path,
    wh ch w ll be detected by Stop fEx stsHooks
    and stop tra n ng for all workers and t  ch ef.   w ll
    also call ``cleanup_c ckpo nts()``.
    """
    super(EarlyStopHook, self).end(sess on)

    # C cks for early stopp ng cr er a and makes a backup
    self.should_stop = self.early_stop(self._epoch)

     f self._f le_path  s not None:
       f self.should_stop:
        # create a f le to  nform workers
        w h tf. o.gf le.GF le(self._f le_path, "wb") as gf le:
          gf le.wr e("early-stop\n")
        # makes t  best c ckpo nt t  only c ckpo nt  n save_d r.
        msg = "early-stopp ng evaluat on at epoch %d" % self._epoch
        logg ng. nfo(msg)
         f self._ex _on_end:
          ra se twml.errors.EarlyStopError(msg)
      else:
        self._latest_c ckpo nt_path = None

    self._epoch += 1

  def beg n(self):
    """
    Saves t  latest_c ckpo nt  n case   gets superseded by anot r c ckpo nt.
    Re mber that w n used w h tra n_and_evaluate, t  ch ef saves c ckpo nts
    cont nuouly. T  ch ef could save a c ckpo nt after evaluat on started.
    So sav ng t  c ckpo nt at t  beg nn ng of evaluat on ensures that  
    later save t  correct best c ckpo nt.
    """
    super(EarlyStopHook, self).beg n()
    self._latest_c ckpo nt_path = tf.tra n.latest_c ckpo nt(self._c ckpo nt_d r)

    assert self._latest_c ckpo nt_path, "expect ng latest c ckpo nt"
    # Backup to temporary d rectory
    try:
      twml.ut l.backup_c ckpo nt(
        c ckpo nt_path_pref x=self._latest_c ckpo nt_path,
        backup_path=self._eval_c ckpo nt_path)
    except twml.errors.C ckpo ntNotFoundError as ex:
      msg = "Cons der  ncreas ng 'keep_c ckpo nt_max' or 'save_c ckpo nt_secs'"
      ra se twml.errors.C ckpo ntNotFoundError(str(ex) + "\n" + msg)


class  tr csUpdateHook(Get tr csHook):
  """
  A Get tr csHook aug nted w h log c to map Sess onRun events to  tr cs updates.
     s ma nly used by `TrackRun` to pers st model  tr cs v a Model Repo.
  """

  def __ n __(self,
               get_est mator_spec_fn,
               add_ tr cs_fn,
               every_n_ er=None,
               every_n_secs=None
               ):
    """
    Args:
      get_est mator_spec_fn:
        funct on that returns t  current Est matorSpec.
        T  Est matorSpec  s used to obta n t  current eval_ tr c_ops.
      add_ tr cs_fn: `funct on` callback used to report  tr cs, called automat cally
        at t  end of every epoch.
      every_n_ er: ` nt`, log t   tr cs once every N local
        steps taken  n t  current epoch.
      every_n_secs: ` nt` or `float`, log t   tr cs once every N
        seconds passed  n t  current epoch. Exactly one of `every_n_ er` and `every_n_secs`
        should be prov ded.
    Ra ses:
      ValueError:  f `every_n_ er`  s non-pos  ve or  f not exactly one of `every_n_ er` and
        `every_n_secs`  s set w n `add_progress_ tr cs_fn`  s prov ded.
    """
    only_log_at_end = (every_n_ er  s None) and (every_n_secs  s None)

     f (not only_log_at_end and every_n_ er and every_n_secs):
      ra se ValueError(
        'exactly one of every_n_ er and every_n_secs must be prov ded'
      )

    # TODO: should have a m n mum to avo d too many calls to ModelRepo?
     f every_n_ er  s not None and every_n_ er <= 0:
      ra se ValueError(" nval d every_n_ er=%s." % every_n_ er)

    self._t  r = (
      NeverTr ggerT  r()  f only_log_at_end else
      SecondOrStepT  r(every_secs=every_n_secs, every_steps=every_n_ er)
    )

    self._should_tr gger = False
    self._ er_count = 0

    self._add_ tr cs_fn = add_ tr cs_fn

    def get_ tr cs_fn():
      """
      Funct on that returns t  current Est matorSpec.
        T  Est matorSpec  s used to obta n t  current eval_ tr c_ops.
      """
      est mator_spec = get_est mator_spec_fn()
      eval_ tr c_ops = est mator_spec.eval_ tr c_ops
      # get t  value_op from t  (value_op, update_op) value
      return {k: v[0] for k, v  n eval_ tr c_ops. ems()}
    super( tr csUpdateHook, self).__ n __(get_ tr cs_fn=get_ tr cs_fn)

  def report_ tr cs(self):
    """
    Tr ggers a  tr cs report.
    """
    self._t  r.update_last_tr ggered_step(self._ er_count)
     f self. tr c_values  s not None:
      self._add_ tr cs_fn(self. tr c_values)

  def beg n(self):
    """
    Tr ggered before each epoch.
    """
    self._t  r.reset()
    self._ er_count = 0
    return super( tr csUpdateHook, self).beg n()

  def before_run(self, run_context):
    """
    Tr ggered before each step.
    """
    self._should_tr gger = self._t  r.should_tr gger_for_step(self._ er_count)
    return super( tr csUpdateHook, self).before_run(run_context)

  def after_run(self, run_context, run_values):
    """
    Tr ggered after each step.
    """
     f self._should_tr gger:
      self.report_ tr cs()
    self._ er_count += 1
    return super( tr csUpdateHook, self).after_run(run_context, run_values)

  def end(self, sess on):
    """
    Tr ggered after each epoch.
    """
    self.report_ tr cs()
    return super( tr csUpdateHook, self).end(sess on)


class EarlyStopDurat on(tf.tra n.Sess onRunHook):
  """
  Hook that can be used to term nate a job (tra n ng or val dat on) after a certa n durat on.
  T  hook  s fault tolerant,  .e.,  f a job  s allotted 1 h  to run and fa ls after 45 m nutes,
  t n   w ll only run for 15 m nutes once restarted.

  Args:
    max_durat on: 
      A float. W n t  argu nt  s def ned, t  job w ll automat cally term nate after
      `max_durat on` seconds  f   has not already compeleted. 
    
    overwr e:
      A boolean.  f set to True, t  hook w ll overwr e t  f le conta n ng t  elapsed t  
      s nce t  beg nn ng of t  job.  n a d str buted sett ng, t  w ll be used so only one 
      job wr es to t  f le wh le all ot rs w ll have read access.  n a d str buted sett ng,
       f all executors have t  para ter set to False, t n   just  ans that t  hook w ll 
      not be fault tolerant. W n restarted, t  job w ll restart t  clock from 0.
      
    save_d r:
      Str ng. A d rectory (located on a f le system that  s Tensorflow compat ble) w re 
        can store t  f le wh ch conta ns t  record of t  elapsed t  . T  f le  s what makes 
      t  hook faul tolerant.

    ex _on_end:
      w n ex _on_end  s True, twml.errors.EarlyStopError()  s tr ggered to stop t  job.
      T   s usually set to True to k ll a val dat on job  n a d str buted sett ng.
   """

  def __ n __(self, max_durat on: float, ex _on_end: bool, save_d r: str, overwr e: bool):
    self._overwr e = overwr e
    self._save_d r = save_d r
    self._ex _on_end = ex _on_end
    self._max_durat on = max_durat on
    self._last_t  _c ck = datet  .now()

    #  n  al ze elapse t   f le
     f overwr e:
      self.elapsed_t  ()

  @property
  def elapsed_f le_path(self):
    return os.path.jo n(self._save_d r, "early_stop_durat on.txt")

  def early_stop(self) -> bool:
    return self.elapsed_t  () > self._max_durat on

  def elapsed_t  (self) -> float:
    # Recorded elapsed t    s 0 unless  's been recorded  n a f le already
    recorded_elapsed_t   = 0
     f tf. o.gf le.ex sts(self.elapsed_f le_path):
      w h tf. o.gf le.GF le(self.elapsed_f le_path, mode="r") as f le:
        recorded_elapsed_t   = json.loads(f le.read())["elapsed_t  "]

    elapsed_t   = recorded_elapsed_t   + (datet  .now() - self._last_t  _c ck).total_seconds()
    self._last_t  _c ck = datet  .now()

     f self._overwr e:
      # Record t  actual zed new elapsed t   to t  f le
      tf. o.gf le.maked rs(os.path.d rna (self.elapsed_f le_path))
      w h tf. o.gf le.GF le(self.elapsed_f le_path, mode="w") as f le:
        record = {
          "elapsed_t  ": elapsed_t  ,
          "max_durat on": self._max_durat on
        }
        f le.wr e(json.dumps(record,  ndent=2))

    return elapsed_t  

  def before_run(self, run_context: tf.est mator.Sess onRunContext) -> None:
     f self.early_stop():
       ssage = f"""
        Stopp ng job wh ch now exceeded t  max mum durat on of {self._max_durat on} seconds. 
      """
      logg ng. nfo( ssage)
      run_context.request_stop()

       f self._ex _on_end:
        ra se twml.errors.EarlyStopError( ssage)


class StopAtStepHook(tf.tra n.StopAtStepHook):
  """
  Overr des ``tf.tra n.StopAtStepHook`` so that
  a ``stop_requested`` property can be accessed to determ ne
   f t  hook requested a stop.
  """

  def __ n __(self, *args, **kwargs):
    super(StopAtStepHook, self).__ n __(*args, **kwargs)
    self._stop_requested = False

  @property
  def stop_requested(self):
    """ true  f t  hook requested a stop """
    return self._stop_requested

  def after_run(self, run_context, run_values):
    """ sets self.stop_requested to true w n request ng a stop """
    super(StopAtStepHook, self).after_run(run_context, run_values)
    self._stop_requested = run_context.stop_requested


class Stop fEx stsHook(tf.tra n.Sess onRunHook):
  """
  Hook that requests stop  f a f le ex sts.
  T  hook  s used w h t  EarlyStopHook to  mple nt
  early-stopp ng for d str buted tra n ng (tf.est mator.tra n_and_evaluate).
  """

  def __ n __(self, f le_path):
    """
    Argu nts:
      f le_path:
        path to f le. W n t  hook detects that t  f le ex sts,
          requests a stop, wh ch effect vely k lls t  worker.
    """
    self._f le_path = f le_path
    self._stop_requested = False

  def after_run(self, run_context, run_values):
     f tf. o.gf le.ex sts(self._f le_path):
      logg ng. nfo("Early-stopp ng f le detected; request ng stop")
      run_context.request_stop()
      self._stop_requested = True

  @property
  def stop_requested(self):
    """ true  f t  hook requested a stop """
    return self._stop_requested
