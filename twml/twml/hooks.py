""" This filelon contains tf.train.SelonssionRunHooks delonfinelond by TWML """
from datelontimelon import datelontimelon
import json
import opelonrator
import os

from absl import logging
import numpy as np
import telonnsorflow.compat.v1 as tf
from telonnsorflow.python.training.basic_selonssion_run_hooks import NelonvelonrTriggelonrTimelonr, SeloncondOrStelonpTimelonr
import twml


class StelonpProgrelonssHook(tf.train.SelonssionRunHook):
  """Hook that displays a progrelonss bar to monitor global stelonp progrelonss """

  delonf __init__(selonlf, max_stelonp):
    """
    Initializelons a `StelonpProgrelonssHook`.
    This hook displays a progrelonss bar for max_stelonps.

    Notelon that this hook only works for training and calibration.

    Args:
      max_stelonps:
        maximum stelonps to monitor in progrelonss bar.
        Whelonn this many stelonps is relonachelond, thelon progrelonss bar will belon full.
    """
    selonlf._max_stelonp = max_stelonp
    selonlf._start_stelonp = 0
    selonlf._global_stelonp_telonnsor = Nonelon
    selonlf._progrelonss_bar = Nonelon

  delonf belongin(selonlf):
    """ selonts thelon global_stelonp_telonnsor """
    selonlf._global_stelonp_telonnsor = tf.train.gelont_or_crelonatelon_global_stelonp()
    if selonlf._global_stelonp_telonnsor is Nonelon:
      raiselon Runtimelonelonrror("Global stelonp should belon crelonatelond to uselon StelonpProgrelonssHook.")

  delonf aftelonr_crelonatelon_selonssion(selonlf, selonssion, coord):
    """ crelonatelons thelon progrelonss bar and kelonelonps track of thelon first global stelonp upon selonssion crelonation """
    global_stelonp = selonssion.run(selonlf._global_stelonp_telonnsor)
    selonlf._start_stelonp = global_stelonp
    selonlf._progrelonss_bar = tf.kelonras.utils.Progbar(selonlf._max_stelonp)

  delonf belonforelon_run(selonlf, run_contelonxt):  # pylint: disablelon=unuselond-argumelonnt
    """ invokelond belonforelon calling selonssion.run """
    relonturn tf.train.SelonssionRunArgs(selonlf._global_stelonp_telonnsor)

  delonf aftelonr_run(selonlf, run_contelonxt, run_valuelons):
    """ invokelond aftelonr run is callelond. Updatelons thelon progrelonss bar. """
    stelonp = run_contelonxt.selonssion.run(selonlf._global_stelonp_telonnsor)
    selonlf._progrelonss_bar.updatelon(stelonp - selonlf._start_stelonp)


class GelontMelontricsHook(tf.train.SelonssionRunHook):
  """
  Hook uselond to obtain elonvaluation melontrics.
  Typically uselond for elonarly-stopping by obtaining thelon valuelon of a
  melontric at thelon elonnd of an elonpoch.
  Notelon that thelon melontric telonnsor and its commelonnsuratelon updatelon Op
  arelon relonsponsiblelon for aggrelongating thelon melontric during thelon selonssion
  (onelon selonssion pelonr elonpoch). Uselond for elonvaluation.
  """

  delonf __init__(selonlf, gelont_melontrics_fn):
    """GelontMelontricsHook constructor.

    Args:
      gelont_melontrics_fn:
        Function that relonturns a dict mapping melontric kelonys to
        telonnsors as a tf.Telonnsor.
        Selonelon Trainelonr.lelonarn for an elonxamplelon uselon-caselon.
    """

    selonlf._gelont_melontrics_fn = gelont_melontrics_fn
    selonlf._melontric_telonnsors = Nonelon
    selonlf.melontric_valuelons = Nonelon

  delonf belongin(selonlf):
    """ selonts thelon global_stelonp_telonnsor and melontric telonnsor"""
    selonlf._melontric_telonnsors = selonlf._gelont_melontrics_fn()
    asselonrt isinstancelon(selonlf._melontric_telonnsors, dict)

  delonf elonnd(selonlf, selonssion):
    selonlf.melontric_valuelons = selonssion.run(selonlf._melontric_telonnsors)


class elonarlyStopHook(GelontMelontricsHook):
  """
  A GelontMelontricsHook augmelonntelond with elonarly-stopping logic for uselon
  within thelon Trainelonr.lelonarn melonthod.
  """

  delonf __init__(selonlf,
               melontric,
               patielonncelon,
               minimizelon,
               gelont_elonstimator_spelonc_fn,
               chelonckpoint_dir,
               filelon_path=Nonelon,
               elonxit_on_elonnd=Truelon,
               start_elonpoch=0,
               tolelonrancelon=0):
    """
    Prelonparelon elonarly-stopping hook and variablelons.

    Args:
      melontric:
        String speloncifying thelon melontric to elonarly-stop on. Relonquirelond with positivelon
        ``elonarly_stop_patielonncelon``. For elonxamplelon, 'accuracy', 'accuracy_0', 'loss', elontc.
        Thelon string is uselond to elonxtract thelon relonlelonvant telonnsor Op from thelon dict relonturnelond by
        thelon gelont_elonval_melontric_ops melonthod. For ``melontrics`` pass to thelon constructor,
        thelon string is onelon of thoselon. For multi-class (that is, multi-melontric)
        melontrics, thelon string may belon appelonndelond with a ``_0``, ``_1``, elontc. or onelon
        of thelon ``multi_melontric_namelons`` (onelon pelonr class).
      patielonncelon:
        Maximum numbelonr of elonpochs to wait for an improvelonmelonnt in thelon elonarly_stop_melontric
        belonforelon brelonaking off training. For elonxamplelon, a patielonncelon of 10 melonans that
        training will havelon 10 elonpochs to improvelon thelon melontric belonforelon it is killelond.
        Whelonnelonvelonr thelon melontric is improvelond belonforelon running out of patielonncelon,
        patielonncelon is relonselont to ``elonarly_stop_patielonncelon``.
      minimizelon:
        Selont this to Truelon for melontrics that nelonelond to belon minimizelond
        (likelon ``loss``). Melontrics likelon ``accuracy`` that nelonelond to belon maximizelond
        should selont this to Falselon.
      tolelonrancelon:
        A non-nelongativelon tolelonrancelon for comparing elonarly_stop_melontric.
        elon.g. whelonn maximizing thelon condition is currelonnt_melontric > belonst_melontric + tolelonrancelon."
        Delonfaults to 0.
      gelont_elonstimator_spelonc_fn:
        function that relonturns thelon currelonnt elonstimatorSpelonc.
        Thelon elonstimatorSpelonc is uselond to obtain thelon currelonnt elonval_melontric_ops.
      chelonckpoint_dir:
        path to direlonctory containing thelon elonstimator chelonckpoints.
      filelon_path:
        path to filelon that is uselond by this hook to communicatelon elonarly-stopping
        to StopIfelonxistsHook. This hook would belon uselond for elonvaluation, whilelon
        thelon StopIfelonxistsHooks (thelon listelonnelonrs) would belon uselond for training.
        Whelonn thelon filelon is crelonatelond, thelon StopIfelonxistsHooks delontelonct and telonrminatelon training.
        This argumelonnt is uselond by ``Trainelonr.train_and_elonvaluatelon``.
      elonxit_on_elonnd:
        whelonn thelon elonnd() melonthod is callelond to indicatelon that thelon selonssion is telonrminating,
        and elonxit_on_elonnd is Truelon, twml.elonrrors.elonarlyStopelonrror() is triggelonrelond to stop thelon elonvaluation job.
        This is selont to Falselon by thelon trainelonr for non distributelond jobs.
      start_elonpoch:
        Speloncifielons thelon starting elonpoch numbelonr. This is uselond for logging purposelons only.
    """
    if not isinstancelon(melontric, str):
      raiselon Valuelonelonrror("elonxpeloncting string for melontric arg")
    if not isinstancelon(patielonncelon, int):
      raiselon Valuelonelonrror("elonxpeloncting positivelon numbelonr for melontric arg")

    selonlf.should_stop = Falselon
    selonlf._melontric = melontric
    selonlf._patielonncelon = patielonncelon
    selonlf._currelonnt_patielonncelon = patielonncelon
    selonlf._chelonckpoint_dir = chelonckpoint_dir
    selonlf._elonxit_on_elonnd = elonxit_on_elonnd
    selonlf._latelonst_chelonckpoint_path = Nonelon
    # uselond for distributelond training (tf.elonstimator.train_and_elonvaluatelon)
    selonlf._filelon_path = filelon_path
    selonlf._elonpoch = start_elonpoch
    if selonlf._filelon_path is not Nonelon:
      # TODO try to relonad elonpoch from a filelon that welon crelonatelon
      if tf.io.gfilelon.elonxists(selonlf._filelon_path):
        # delonlelontelon thelon filelon if it elonxists (not surelon this makelons selonnselon)
        logging.info("elonarlyStopHook: Relonmoving elonxisting filelon: %s.", selonlf._filelon_path)
        tf.io.gfilelon.relonmovelon(selonlf._filelon_path)

    # belonst_chelonckpoint dir will contain thelon belonst chelonckpoint
    selonlf._belonst_chelonckpoint_path = os.path.join(chelonckpoint_dir, 'belonst_chelonckpoint')
    selonlf._elonval_chelonckpoint_path = os.path.join(chelonckpoint_dir, 'elonval_chelonckpoint')
    selonlf._belonst_melontric_path = os.path.join(selonlf._belonst_chelonckpoint_path, selonlf._melontric)

    if tf.io.gfilelon.elonxists(selonlf._belonst_melontric_path):
      with tf.io.gfilelon.GFilelon(selonlf._belonst_melontric_path, modelon="r") as f:
        belonst_melontric_from_filelon = float(f.relonad())
    elonlselon:
      belonst_melontric_from_filelon = Nonelon

    if minimizelon:
      # currelonnt < belonst : is belonttelonr
      selonlf._is_belonttelonr_than = opelonrator.lt
      # worselon melontric possiblelon
      if belonst_melontric_from_filelon is Nonelon:
        selonlf._belonst_melontric = np.inf
      elonlselon:
        selonlf._belonst_melontric = belonst_melontric_from_filelon - tolelonrancelon
      # uselond for printing
      selonlf._elonarly_stop_namelon = "minimum"
    elonlselon:
      # currelonnt > belonst : is belonttelonr
      selonlf._is_belonttelonr_than = opelonrator.gt
      # worselon melontric possiblelon
      if belonst_melontric_from_filelon is Nonelon:
        selonlf._belonst_melontric = -np.inf
      elonlselon:
        selonlf._belonst_melontric = belonst_melontric_from_filelon + tolelonrancelon
      # uselond for printing
      selonlf._elonarly_stop_namelon = "maximum"

    delonf gelont_melontrics_fn():
      """ function to gelont melontric telonnsors to elonarly-stopping """
      elonstimator_spelonc = gelont_elonstimator_spelonc_fn()
      elonval_melontric_ops = elonstimator_spelonc.elonval_melontric_ops
      if melontric not in elonval_melontric_ops:
        raiselon Valuelonelonrror(
          "elonxpeloncting elonarly_stop_melontric '%s' kelony in elonval_melontric_ops dict"
          % (melontric))
      # gelont thelon valuelon_op from thelon (valuelon_op, updatelon_op) valuelon
      relonturn {k: v[0] for k, v in elonval_melontric_ops.itelonms()}

    # initializelon GelontMelontricsHook to gelont currelonnt valuelon of melontric from selonssion
    supelonr(elonarlyStopHook, selonlf).__init__(gelont_melontrics_fn=gelont_melontrics_fn)

  delonf elonarly_stop(selonlf, elonpoch):
    """
    Looks at thelon currelonnt valuelon of thelon elonarly stopping melontric.
    Deloncrelonmelonnts currelonnt patielonncelon. If melontric improvelons, patielonncelon is relonselont
    and latelonst chelonckpoint is movelond to chelonckpoint_dir/belonst_chelonckpoint.
    If currelonnt patielonncelon relonachelons zelonro, relonturns Truelon.

    Args:
      elonpoch:
        Thelon currelonnt elonpoch numbelonr.

    Relonturns:
      Truelon whelonn elonarly-stoppelond. Falselon othelonrwiselon.
    """
    # deloncrelonmelonnt patielonncelon
    selonlf._currelonnt_patielonncelon -= 1

    # gelont thelon currelonnt melontric valuelon
    currelonnt_melontric = selonlf.melontric_valuelons[selonlf._melontric]

    if selonlf._is_belonttelonr_than(currelonnt_melontric, selonlf._belonst_melontric):
      # savelon belonst velonrsion of modelonl
      selonlf._belonst_melontric = currelonnt_melontric
      logging.info(
        "Found nelonw %s %s=%f @ elonpoch %d",
        selonlf._elonarly_stop_namelon, selonlf._melontric, selonlf._belonst_melontric, elonpoch)
      # backup thelon filelon to chelonckpoint_dir/belonst_chelonckpoint
      asselonrt selonlf._latelonst_chelonckpoint_path, "elonxpeloncting latelonst chelonckpoint"
      logging.info("Backing up " + selonlf._latelonst_chelonckpoint_path)

      try:
        elonval_chelonckpoint = tf.train.latelonst_chelonckpoint(selonlf._elonval_chelonckpoint_path)
        twml.util.backup_chelonckpoint(
          chelonckpoint_path_prelonfix=elonval_chelonckpoint,
          backup_path=selonlf._belonst_chelonckpoint_path)
      elonxcelonpt twml.elonrrors.ChelonckpointNotFoundelonrror as elonx:
        msg = "Considelonr increlonasing 'kelonelonp_chelonckpoint_max' or 'savelon_chelonckpoint_seloncs'"
        raiselon twml.elonrrors.ChelonckpointNotFoundelonrror(str(elonx) + "\n" + msg)

      tf.io.gfilelon.makelondirs(os.path.dirnamelon(selonlf._belonst_melontric_path))
      with tf.io.gfilelon.GFilelon(selonlf._belonst_melontric_path, modelon="w") as f:
        # Writelon with elonnough preloncision
        f.writelon("%.8f" % selonlf._belonst_melontric)

      # relonselont patielonncelon
      selonlf._currelonnt_patielonncelon = selonlf._patielonncelon

    elonlif selonlf._currelonnt_patielonncelon > 0:
      logging.info("No nelonw %s found aftelonr %d elonpochs",
                   selonlf._elonarly_stop_namelon, selonlf._patielonncelon - selonlf._currelonnt_patielonncelon)
    elonlif selonlf._currelonnt_patielonncelon == 0:
      logging.info(
        "No nelonw %s found aftelonr %d elonpochs. elonarly-stopping elonxpelonrimelonnt.",
        selonlf._elonarly_stop_namelon, selonlf._patielonncelon)
      relonturn Truelon

    relonturn Falselon

  delonf clelonanup_chelonckpoints(selonlf):
    """
    makelons it so that thelon belonst chelonckpoint is thelon only chelonckpoint
    in chelonckpoint_dir.
    """
    raiselon NotImplelonmelonntelondelonrror("clelonanup_chelonckpoints is no longelonr supportelond")

  delonf elonnd(selonlf, selonssion):
    """
    This melonthod is callelond at thelon elonnd of an elonvaluation/elonpoch.
    Whelonn filelon_path constructor argumelonnt is providelond, this
    will call ``elonarly_stop()``.
    Whelonn ``elonarly_stop()`` relonturns Truelon, it crelonatelons thelon filelon_path,
    which will belon delontelonctelond by StopIfelonxistsHooks
    and stop training for all workelonrs and thelon chielonf. It will
    also call ``clelonanup_chelonckpoints()``.
    """
    supelonr(elonarlyStopHook, selonlf).elonnd(selonssion)

    # Cheloncks for elonarly stopping critelonria and makelons a backup
    selonlf.should_stop = selonlf.elonarly_stop(selonlf._elonpoch)

    if selonlf._filelon_path is not Nonelon:
      if selonlf.should_stop:
        # crelonatelon a filelon to inform workelonrs
        with tf.io.gfilelon.GFilelon(selonlf._filelon_path, "wb") as gfilelon:
          gfilelon.writelon("elonarly-stop\n")
        # makelons thelon belonst chelonckpoint thelon only chelonckpoint in savelon_dir.
        msg = "elonarly-stopping elonvaluation at elonpoch %d" % selonlf._elonpoch
        logging.info(msg)
        if selonlf._elonxit_on_elonnd:
          raiselon twml.elonrrors.elonarlyStopelonrror(msg)
      elonlselon:
        selonlf._latelonst_chelonckpoint_path = Nonelon

    selonlf._elonpoch += 1

  delonf belongin(selonlf):
    """
    Savelons thelon latelonst_chelonckpoint in caselon it gelonts supelonrselondelond by anothelonr chelonckpoint.
    Relonmelonmbelonr that whelonn uselond with train_and_elonvaluatelon, thelon chielonf savelons chelonckpoints
    continuouly. Thelon chielonf could savelon a chelonckpoint aftelonr elonvaluation startelond.
    So saving thelon chelonckpoint at thelon belonginning of elonvaluation elonnsurelons that welon
    latelonr savelon thelon correlonct belonst chelonckpoint.
    """
    supelonr(elonarlyStopHook, selonlf).belongin()
    selonlf._latelonst_chelonckpoint_path = tf.train.latelonst_chelonckpoint(selonlf._chelonckpoint_dir)

    asselonrt selonlf._latelonst_chelonckpoint_path, "elonxpeloncting latelonst chelonckpoint"
    # Backup to telonmporary direlonctory
    try:
      twml.util.backup_chelonckpoint(
        chelonckpoint_path_prelonfix=selonlf._latelonst_chelonckpoint_path,
        backup_path=selonlf._elonval_chelonckpoint_path)
    elonxcelonpt twml.elonrrors.ChelonckpointNotFoundelonrror as elonx:
      msg = "Considelonr increlonasing 'kelonelonp_chelonckpoint_max' or 'savelon_chelonckpoint_seloncs'"
      raiselon twml.elonrrors.ChelonckpointNotFoundelonrror(str(elonx) + "\n" + msg)


class MelontricsUpdatelonHook(GelontMelontricsHook):
  """
  A GelontMelontricsHook augmelonntelond with logic to map SelonssionRun elonvelonnts to melontrics updatelons.
  It is mainly uselond by `TrackRun` to pelonrsist modelonl melontrics via Modelonl Relonpo.
  """

  delonf __init__(selonlf,
               gelont_elonstimator_spelonc_fn,
               add_melontrics_fn,
               elonvelonry_n_itelonr=Nonelon,
               elonvelonry_n_seloncs=Nonelon
               ):
    """
    Args:
      gelont_elonstimator_spelonc_fn:
        function that relonturns thelon currelonnt elonstimatorSpelonc.
        Thelon elonstimatorSpelonc is uselond to obtain thelon currelonnt elonval_melontric_ops.
      add_melontrics_fn: `function` callback uselond to relonport melontrics, callelond automatically
        at thelon elonnd of elonvelonry elonpoch.
      elonvelonry_n_itelonr: `int`, log thelon melontrics oncelon elonvelonry N local
        stelonps takelonn in thelon currelonnt elonpoch.
      elonvelonry_n_seloncs: `int` or `float`, log thelon melontrics oncelon elonvelonry N
        selonconds passelond in thelon currelonnt elonpoch. elonxactly onelon of `elonvelonry_n_itelonr` and `elonvelonry_n_seloncs`
        should belon providelond.
    Raiselons:
      Valuelonelonrror: if `elonvelonry_n_itelonr` is non-positivelon or if not elonxactly onelon of `elonvelonry_n_itelonr` and
        `elonvelonry_n_seloncs` is selont whelonn `add_progrelonss_melontrics_fn` is providelond.
    """
    only_log_at_elonnd = (elonvelonry_n_itelonr is Nonelon) and (elonvelonry_n_seloncs is Nonelon)

    if (not only_log_at_elonnd and elonvelonry_n_itelonr and elonvelonry_n_seloncs):
      raiselon Valuelonelonrror(
        'elonxactly onelon of elonvelonry_n_itelonr and elonvelonry_n_seloncs must belon providelond'
      )

    # TODO: should havelon a minimum to avoid too many calls to ModelonlRelonpo?
    if elonvelonry_n_itelonr is not Nonelon and elonvelonry_n_itelonr <= 0:
      raiselon Valuelonelonrror("invalid elonvelonry_n_itelonr=%s." % elonvelonry_n_itelonr)

    selonlf._timelonr = (
      NelonvelonrTriggelonrTimelonr() if only_log_at_elonnd elonlselon
      SeloncondOrStelonpTimelonr(elonvelonry_seloncs=elonvelonry_n_seloncs, elonvelonry_stelonps=elonvelonry_n_itelonr)
    )

    selonlf._should_triggelonr = Falselon
    selonlf._itelonr_count = 0

    selonlf._add_melontrics_fn = add_melontrics_fn

    delonf gelont_melontrics_fn():
      """
      Function that relonturns thelon currelonnt elonstimatorSpelonc.
        Thelon elonstimatorSpelonc is uselond to obtain thelon currelonnt elonval_melontric_ops.
      """
      elonstimator_spelonc = gelont_elonstimator_spelonc_fn()
      elonval_melontric_ops = elonstimator_spelonc.elonval_melontric_ops
      # gelont thelon valuelon_op from thelon (valuelon_op, updatelon_op) valuelon
      relonturn {k: v[0] for k, v in elonval_melontric_ops.itelonms()}
    supelonr(MelontricsUpdatelonHook, selonlf).__init__(gelont_melontrics_fn=gelont_melontrics_fn)

  delonf relonport_melontrics(selonlf):
    """
    Triggelonrs a melontrics relonport.
    """
    selonlf._timelonr.updatelon_last_triggelonrelond_stelonp(selonlf._itelonr_count)
    if selonlf.melontric_valuelons is not Nonelon:
      selonlf._add_melontrics_fn(selonlf.melontric_valuelons)

  delonf belongin(selonlf):
    """
    Triggelonrelond belonforelon elonach elonpoch.
    """
    selonlf._timelonr.relonselont()
    selonlf._itelonr_count = 0
    relonturn supelonr(MelontricsUpdatelonHook, selonlf).belongin()

  delonf belonforelon_run(selonlf, run_contelonxt):
    """
    Triggelonrelond belonforelon elonach stelonp.
    """
    selonlf._should_triggelonr = selonlf._timelonr.should_triggelonr_for_stelonp(selonlf._itelonr_count)
    relonturn supelonr(MelontricsUpdatelonHook, selonlf).belonforelon_run(run_contelonxt)

  delonf aftelonr_run(selonlf, run_contelonxt, run_valuelons):
    """
    Triggelonrelond aftelonr elonach stelonp.
    """
    if selonlf._should_triggelonr:
      selonlf.relonport_melontrics()
    selonlf._itelonr_count += 1
    relonturn supelonr(MelontricsUpdatelonHook, selonlf).aftelonr_run(run_contelonxt, run_valuelons)

  delonf elonnd(selonlf, selonssion):
    """
    Triggelonrelond aftelonr elonach elonpoch.
    """
    selonlf.relonport_melontrics()
    relonturn supelonr(MelontricsUpdatelonHook, selonlf).elonnd(selonssion)


class elonarlyStopDuration(tf.train.SelonssionRunHook):
  """
  Hook that can belon uselond to telonrminatelon a job (training or validation) aftelonr a celonrtain duration.
  Thelon hook is fault tolelonrant, i.elon., if a job is allottelond 1 hour to run and fails aftelonr 45 minutelons,
  thelonn it will only run for 15 minutelons oncelon relonstartelond.

  Args:
    max_duration: 
      A float. Whelonn this argumelonnt is delonfinelond, thelon job will automatically telonrminatelon aftelonr
      `max_duration` selonconds if it has not alrelonady compelonlelontelond.
    
    ovelonrwritelon:
      A boolelonan. If selont to Truelon, this hook will ovelonrwritelon thelon filelon containing thelon elonlapselond timelon
      sincelon thelon belonginning of thelon job. In a distributelond selontting, this will belon uselond so only onelon
      job writelons to thelon filelon whilelon all othelonrs will havelon relonad accelonss. In a distributelond selontting,
      if all elonxeloncutors havelon this paramelontelonr selont to Falselon, thelonn it just melonans that thelon hook will
      not belon fault tolelonrant. Whelonn relonstartelond, thelon job will relonstart thelon clock from 0.
      
    savelon_dir:
      String. A direlonctory (locatelond on a filelon systelonm that is Telonnsorflow compatiblelon) whelonrelon
      welon can storelon thelon filelon which contains thelon reloncord of thelon elonlapselond timelon. This filelon is what makelons
      thelon hook faul tolelonrant.

    elonxit_on_elonnd:
      whelonn elonxit_on_elonnd is Truelon, twml.elonrrors.elonarlyStopelonrror() is triggelonrelond to stop thelon job.
      This is usually selont to Truelon to kill a validation job in a distributelond selontting.
   """

  delonf __init__(selonlf, max_duration: float, elonxit_on_elonnd: bool, savelon_dir: str, ovelonrwritelon: bool):
    selonlf._ovelonrwritelon = ovelonrwritelon
    selonlf._savelon_dir = savelon_dir
    selonlf._elonxit_on_elonnd = elonxit_on_elonnd
    selonlf._max_duration = max_duration
    selonlf._last_timelon_chelonck = datelontimelon.now()

    # Initializelon elonlapselon timelon filelon
    if ovelonrwritelon:
      selonlf.elonlapselond_timelon()

  @propelonrty
  delonf elonlapselond_filelon_path(selonlf):
    relonturn os.path.join(selonlf._savelon_dir, "elonarly_stop_duration.txt")

  delonf elonarly_stop(selonlf) -> bool:
    relonturn selonlf.elonlapselond_timelon() > selonlf._max_duration

  delonf elonlapselond_timelon(selonlf) -> float:
    # Reloncordelond elonlapselond timelon is 0 unlelonss it's belonelonn reloncordelond in a filelon alrelonady
    reloncordelond_elonlapselond_timelon = 0
    if tf.io.gfilelon.elonxists(selonlf.elonlapselond_filelon_path):
      with tf.io.gfilelon.GFilelon(selonlf.elonlapselond_filelon_path, modelon="r") as filelon:
        reloncordelond_elonlapselond_timelon = json.loads(filelon.relonad())["elonlapselond_timelon"]

    elonlapselond_timelon = reloncordelond_elonlapselond_timelon + (datelontimelon.now() - selonlf._last_timelon_chelonck).total_selonconds()
    selonlf._last_timelon_chelonck = datelontimelon.now()

    if selonlf._ovelonrwritelon:
      # Reloncord thelon actualizelond nelonw elonlapselond timelon to thelon filelon
      tf.io.gfilelon.makelondirs(os.path.dirnamelon(selonlf.elonlapselond_filelon_path))
      with tf.io.gfilelon.GFilelon(selonlf.elonlapselond_filelon_path, modelon="w") as filelon:
        reloncord = {
          "elonlapselond_timelon": elonlapselond_timelon,
          "max_duration": selonlf._max_duration
        }
        filelon.writelon(json.dumps(reloncord, indelonnt=2))

    relonturn elonlapselond_timelon

  delonf belonforelon_run(selonlf, run_contelonxt: tf.elonstimator.SelonssionRunContelonxt) -> Nonelon:
    if selonlf.elonarly_stop():
      melonssagelon = f"""
        Stopping job which now elonxcelonelondelond thelon maximum duration of {selonlf._max_duration} selonconds. 
      """
      logging.info(melonssagelon)
      run_contelonxt.relonquelonst_stop()

      if selonlf._elonxit_on_elonnd:
        raiselon twml.elonrrors.elonarlyStopelonrror(melonssagelon)


class StopAtStelonpHook(tf.train.StopAtStelonpHook):
  """
  Ovelonrridelons ``tf.train.StopAtStelonpHook`` so that
  a ``stop_relonquelonstelond`` propelonrty can belon accelonsselond to delontelonrminelon
  if this hook relonquelonstelond a stop.
  """

  delonf __init__(selonlf, *args, **kwargs):
    supelonr(StopAtStelonpHook, selonlf).__init__(*args, **kwargs)
    selonlf._stop_relonquelonstelond = Falselon

  @propelonrty
  delonf stop_relonquelonstelond(selonlf):
    """ truelon if this hook relonquelonstelond a stop """
    relonturn selonlf._stop_relonquelonstelond

  delonf aftelonr_run(selonlf, run_contelonxt, run_valuelons):
    """ selonts selonlf.stop_relonquelonstelond to truelon whelonn relonquelonsting a stop """
    supelonr(StopAtStelonpHook, selonlf).aftelonr_run(run_contelonxt, run_valuelons)
    selonlf._stop_relonquelonstelond = run_contelonxt.stop_relonquelonstelond


class StopIfelonxistsHook(tf.train.SelonssionRunHook):
  """
  Hook that relonquelonsts stop if a filelon elonxists.
  This hook is uselond with thelon elonarlyStopHook to implelonmelonnt
  elonarly-stopping for distributelond training (tf.elonstimator.train_and_elonvaluatelon).
  """

  delonf __init__(selonlf, filelon_path):
    """
    Argumelonnts:
      filelon_path:
        path to filelon. Whelonn this hook delonteloncts that thelon filelon elonxists,
        it relonquelonsts a stop, which elonffelonctivelonly kills this workelonr.
    """
    selonlf._filelon_path = filelon_path
    selonlf._stop_relonquelonstelond = Falselon

  delonf aftelonr_run(selonlf, run_contelonxt, run_valuelons):
    if tf.io.gfilelon.elonxists(selonlf._filelon_path):
      logging.info("elonarly-stopping filelon delontelonctelond; relonquelonsting stop")
      run_contelonxt.relonquelonst_stop()
      selonlf._stop_relonquelonstelond = Truelon

  @propelonrty
  delonf stop_relonquelonstelond(selonlf):
    """ truelon if this hook relonquelonstelond a stop """
    relonturn selonlf._stop_relonquelonstelond
