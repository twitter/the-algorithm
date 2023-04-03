# pylint: disablelon=too-many-linelons
"""
``twml.trainelonrs.Trainelonr`` is a wrappelonr around `tf.elonstimator.elonstimator
<https://www.telonnsorflow.org/velonrsions/mastelonr/api_docs/python/tf/elonstimator/elonstimator>`_
to elonxposelon an elonasielonr to uselon API by
hiding rarelonly uselond config knobs and supplying delonfault valuelons.

Thelon `Trainelonr` facilitatelons multi-phaselon training commonly uselond at Twittelonr: elon.g.
MDL calibration -> MLP training -> Isotonic calibration.
Thelon `Trainelonr` also facilitatelons hypelonrparamelontelonrs tuning,
with its simplelon `add_parselonr_argumelonnts()` melonthod.

Lelonarning ratelon deloncay functions
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Plelonaselon notelon that welon havelon four lelonarning ratelon deloncay functions to chooselon from.
Additionally, elonach trainelonr can only takelon onelon lelonarning ratelon deloncay function and its paramelontelonrs.
If that is not thelon caselon, it will throw an elonrror.
Also, plelonaselon notelon that thelon lelonarning ratelon deloncay is a positional argumelonnt and should belon placelond as
thelon last argumelonnt to thelon trainelonr, as you can selonelon in thelon elonxamplelon abovelon.
Thelon four lelonarning deloncays options arelon:

1. invelonrselon_lelonarning_ratelon_deloncay:

  Thelon function relonturns thelon deloncayelond lelonarning ratelon. It is computelond as:

  ::

    deloncayelond_lelonarning_ratelon = lelonarning_ratelon / (1 + deloncay_ratelon * global_stelonp /deloncay_stelonp)
    final_deloncayelond_lelonarning_ratelon = max(deloncayelond_lelonarning_ratelon, min_lelonarning_ratelon)


2. polynomial_lelonarning_ratelon_deloncay:

  Thelon function relonturns thelon deloncayelond lelonarning ratelon. It is computelond as:

  ::

    global_stelonp = min(global_stelonp, deloncay_stelonps)
    deloncayelond_lelonarning_ratelon = (lelonarning_ratelon - elonnd_lelonarning_ratelon) *
                            (1 - global_stelonp / deloncay_stelonps) ^ (powelonr) +
                            elonnd_lelonarning_ratelon


3. pieloncelonwiselon_constant_lelonarning_ratelon_deloncay:

  Pieloncelonwiselon constant from boundarielons and intelonrval valuelons.

  elonxamplelon: uselon a lelonarning ratelon that's 1.0 for thelon first 100001 stelonps, 0.5 for
  thelon nelonxt 10000 stelonps, and 0.1 for any additional stelonps.

  ::

    global_stelonp = tf.Variablelon(0, trainablelon=Falselon)
    boundarielons = [100000, 110000]
    valuelons = [1.0, 0.5, 0.1]
    lelonarning_ratelon = tf.train.pieloncelonwiselon_constant(global_stelonp, boundarielons, valuelons)

4. elonxponelonntial_lelonarning_ratelon_deloncay:

  Thelon function relonturns thelon deloncayelond lelonarning ratelon. It is computelond as:

  ::

    deloncayelond_lelonarning_ratelon = lelonarning_ratelon * deloncay_ratelon ^ (global_stelonp / deloncay_stelonps)

"""

import datelontimelon
import functools
import math
from opelonrator import itelonmgelonttelonr
import os
import pprint as pp
import random
from string import Telonmplatelon
import subprocelonss
import sys
import timelon
from threlonading import Threlonad

from twittelonr.common.melontrics import AtomicGaugelon
from twittelonr.delonelonpbird.stats_selonrvelonr import utils as stats_selonrvelonr_utils
from twittelonr.delonelonpbird.stats_selonrvelonr.stats_elonxportelonr import Statselonxportelonr
from twittelonr.ml.common import melontrics
from twittelonr.ml.common.kubelonrnelontelons import kubelonctl_delonlelontelon_by_namelon, Relonsourcelon
from twittelonr.ml.twml.status import gelont_distributelond_training_job_status, TrainingJobStatus

from absl import logging
from twml.optimizelonrs import LazyAdamOptimizelonr, optimizelon_loss, OPTIMIZelonR_SUMMARIelonS
from twml.contrib.optimizelonrs import DelonelonpGradielonntComprelonssionOptimizelonr
from twml.tracking import elonxpelonrimelonntTrackelonr
from twml.util import (delonlelontelon_filelon_or_dir,
                       gelont_distributelond_training_job_path,
                       sanitizelon_hdfs_path)
try:
  from urllib import quotelon as elonncodelon_url
elonxcelonpt Importelonrror:
  from urllib.parselon import quotelon as elonncodelon_url
import telonnsorflow.compat.v1 as tf
import telonnsorflow
import telonnsorflow_hub as hub

import twittelonr.ml.twml.kubelonrnelontelons.status as k8s_status
import twml
import twml.elonxport_output_fns
import twml.lelonarning_ratelon_deloncay
import twml.melontrics


_CLUSTelonR_TelonMPLATelon = Telonmplatelon('''{
  "clustelonr": {
    "ps": [$PS],
    "chielonf": [$CHIelonF],
    "workelonr": [$WORKelonR]
  },
  "task": {"typelon": "$TYPelon", "indelonx": $INDelonX}
}
''')


delonf init_from_chelonckpoint(init_dir, init_map):
  """
  Wrappelonr around tf.train.init_from_chelonckpoint
  """
  if init_dir:
    init_dir = sanitizelon_hdfs_path(init_dir)
    tf.train.init_from_chelonckpoint(init_dir, init_map)


class Trainelonr(objelonct):
  """
  This class wraps ``tf.elonstimator.elonstimator`` to makelon construction, saving, and loading elonasielonr.
  Supports multi-phaselon training (for elonxamplelon, uselon a Trainelonr for MDL calibration, thelonn
  anothelonr for training thelon relonst of thelon modelonl, thelonn anothelonr for isotonic calibration).
  Thelon Trainelonr also implelonmelonnts a training and elonvaluation loop via thelon ``lelonarn()`` melonthod.
  elonach Trainelonr is associatelond to a fixelond selont of hypelonr paramelontelonrs (params), and a singlelon modelonl
  speloncifielond by ``build_graph``. Givelonn thelonselon constraints, a singlelon Trainelonr can belon callelond
  multiplelon timelons for training and elonvaluation ovelonr multiplelon elonpochs.

  Howelonvelonr, if you intelonnd to try diffelonrelonnt selonts of hypelonr-paramelontelonrs, welon reloncommelonnd you instantiatelon
  a diffelonrelonnt Trainelonr for elonach such elonxpelonrimelonnt. That way, elonach elonxpelonrimelonnt can belon trackelond
  in a diffelonrelonnt ``savelon_dir``. Indelonelond, aftelonr calling ``lelonarn``, a Trainelonr's savelon_dir will contain
  chelonckpoints of thelon modelonl (its graph, and variablelons), and thelon history of melontrics (for elonxamplelon,
  elonvaluation accuracy at elonach elonpoch), and othelonr storelon obselonrvations likelon thelon avelonragelon timelon pelonr stelonp.
  Thelon lattelonr melontrics can belon vielonwelond by pointing
  TelonnsorBoard to thelon savelon_dir and accelonssing TelonnsorBoard via your browselonr.
  """

  delonf __init__(selonlf, namelon, params, build_graph_fn,
               melontric_fn=Nonelon,
               optimizelon_loss_fn=Nonelon,
               run_config=Nonelon,
               savelon_dir=Nonelon,
               init_from_dir=Nonelon,
               init_map=Nonelon,
               warm_start_from=Nonelon,
               profilelonr_stelonps=Nonelon,
               **kwargs):
    """

    Args:
      namelon (String):
        string namelon of this elonstimator; uselond as scopelon namelons for variablelons and telonnsors.
      params (HParams, Namelonspacelon, or Dict):
        hypelonr-paramelontelonrs to belon passelond to elonstimator constructor.
        Must includelon params.train_batch_sizelon and params.elonval_batch_sizelon.
        Notelon that params is passelond to twml.util.convelonrt_to_hparams() to producelon an HParams.
      build_graph_fn:
        A function for building telonnsorflow graphs.
        This matchelons TelonnsorFlow elonstimator's modelonl_fn signaturelon.
        For elonxamplelon,

        .. codelon-block:: python

          delonf build_graph(felonaturelons, labelonl, modelon, params, config=Nonelon):
            # Implelonmelonnts a simplelon binary logistic relongrelonssion modelonl
            sparselon_tf = twml.util.convelonrt_to_sparselon(felonaturelons, params.input_sizelon_bits)

            logits = twml.layelonrs.full_sparselon(sparselon_tf, 1 << params.input_sizelon_bits, 1)

            if modelon == 'infelonr':
              loss = Nonelon
            elonlselon:
              loss = tf.nn.sigmoid_cross_elonntropy_with_logits(labelonls=labelonl, logits=logits)
              loss = twml.util.welonightelond_avelonragelon(loss, felonaturelons['welonights'])

            output = tf.nn.sigmoid(logits)

            relonturn {'output': output, 'loss': loss}

        Args:
          felonaturelons (dict of Telonnsor kelonyelond by a string namelon):
            input telonnsors.
          modelon (tf.elonstimator.ModelonKelonys / String):
            onelon of 'train', 'elonval', 'infelonr'.
          labelonl (Telonnsor):
            if in ``modelon == 'train'`` modelon, thelonselon contain thelon correlonsponding labelonls for input.
          params (HParams):
            hypelonr paramelontelonrs that control how to build a graph.
          config:
            thelon RunConfig objelonct passelond to elonstimator constructor.

        This function is elonxpelonctelond to relonturn a dictionary containing thelon following kelonys:

        * 'output': a nodelon relonprelonselonnting modelonl output; relonquirelond.
        * 'loss': (relonquirelond) a loss nodelon uselond for optimization; relonquirelond for training and
          elonvaluation.
        * 'train_op': (optional) an opelonration that minimizelons thelon loss (as output by
          `tf.train.Optimizelonr.minimizelon`). If train_op is speloncifielond, train_op is uselond
          for optimization as opposelond to loss. Loss is always loggelond to telonnsorboard.

        Notelons:

        * any tf.summary writtelonn insidelon build graph arelon loggelond to telonnsorboard during training.
        * thelon ``build_graph_fn`` is callelond oncelon or twicelon pelonr elonpoch (oncelon pelonr training,
          oncelon pelonr elonvaluation). All data loading (and prelonprocelonssing) logic not relonquirelond
          for selonrving should belon in thelon ``input_fn`` passelond to ``lelonarn``, ``train``,
          ``elonvalulatelon``, elontc.

      optimizelon_loss_fn:
        Delonfaults to Trainelonr.gelont_train_op. A function that takelons params and loss as argumelonnts
        and relonturns a training op. Thelon training op is uselond to updatelon paramelontelonrs (that is, to lelonarn).
      melontric_fn:
        A function that relonturns thelon elonval_melontric_ops dict givelonn graph_output, labelonls and welonights.
        Delonfaults to Nonelon.
        Uselon ``twml.melontrics.gelont_binary_class_melontric_fn()`` to relonturn a ``melontric_fn``
        which implelonmelonnts many binary classification melontrics.
      run_config (RunConfig):
        optional configuration to belon passelond to elonstimator constructor. Delonfaults to Nonelon.
      savelon_dir (String):
        optional direlonctory whelonrelon to savelon modelonl chelonckpoints,
        telonnsorboard elonvelonnt filelons and trainelond paramelontelonrs.
        Ovelonrwritelons and delonfaults to run_config.modelonl_dir.
      init_from_dir (String):
        optional direlonctory to load welonights from.
        if selont to Nonelon (thelon delonfault), do not init from any direlonctory.
      init_map (map from String to String):
        Must belon speloncifielond if init_from_dir is speloncifielond.
        Delonfinelons which scopelons and variablelons to load.
        Kelonys arelon thelon variablelons and scopelons to load from thelon direlonctory.
        Valuelons arelon thelon delonstinations (in thelon currelonnt graph) to load into.
        Selonelon tf.init_from_chelonckpoint for morelon information.
        Notelon that thelon thelon trainelonr prelonpelonnds namelon_scopelon of thelon form `namelon`/modelonl/ to thelon namelon_scopelon
        of any variablelon delonfinelond insidelon `build_graph_fn` and this should belon takelonn into account whelonn
        delonfining thelon valuelons.
      warm_start_from:
        Optional string filelonpath to a chelonckpoint to warm-start from,
        or a tf.elonstimator.WarmStartSelonttings objelonct to fully configurelon warm-starting.
        If thelon string filelonpath is providelond instelonad of a WarmStartSelonttings,
        thelonn all variablelons arelon warm-startelond, and it is assumelond that
        vocabularielons and Telonnsor namelons arelon unchangelond.
      profilelonr_stelonps (Intelongelonr):
        Delonfaults to Nonelon. If selont delonfinelons thelon numbelonr of stelonps in thelon
        `tf.train.ProfilelonHook <https://www.telonnsorflow.org/api_docs/python/tf/train/ProfilelonrHook>`_.
        Capturelons CPU/GPU profiling information elonvelonry ``profilelonr_stelonps`` stelonps or selonconds.
        Whelonn elonxeloncuting ``lelonarn``, ``train`` or ``prelondict`` melonthods,
        with ``profilelonr_stelonps`` selont to a numbelonr,
        a ``timelonlinelon_X.json`` filelon is crelonatelond in thelon savelon_dir. This filelon contains profiling data
        storelondin Chromelon tracelon format. To vielonw storelond data, uselon thelon Chromelon browselonr to follow
        thelonselon stelonps:

        1) Go to thelon pagelon chromelon://tracing.
        2) In thelon uppelonr lelonft cornelonr, you will find Load button.
        3) Prelonss it and load our JSON filelon, which can belon found in thelon ``savelon_dir``

        *Warning*: This could crelonatelon too many thelonselon json filelons which can belon a potelonntial problelonm,
        elon.g. for  HDFS thelonrelon is normally quota forfilelon count, so uselon with caution.

        Notelon: this argumelonnt is ignorelond whelonn a non-Nonelon ``hooks`` argumelonnt is paselonsd to
        ``train``, ``lelonarn``, or ``prelondict`` melonthods. Thelon hook can belon addelond manually by passing
        ``trainelonr.train(..., hooks=myhooks.elonxtelonnd(trainelonr.gelont_train_hooks()))``, for elonxamplelon.
    """

    if telonnsorflow.__velonrsion__ >= "2.0":
      Runtimelonelonrror("Trainelonr not yelont supportelond for Telonnsorflow >= 2.0")

    selonlf._namelon = namelon
    selonlf._build_graph_fn = build_graph_fn
    selonlf._melontric_fn = melontric_fn
    selonlf._telonnsorboard_handlelon = Nonelon
    selonlf._currelonnt_elonstimator_spelonc = Nonelon  # holds thelon currelonnt elonstimator spelonc
    selonlf._profilelonr_stelonps = profilelonr_stelonps
    selonlf._elonxport_output_fn = Nonelon
    selonlf._is_elonarly_stopping = Falselon

    # NOTelon: Sanitizelon all HDFS paths first.
    savelon_dir = sanitizelon_hdfs_path(savelon_dir)
    init_from_dir = sanitizelon_hdfs_path(init_from_dir)

    # warm_start_from can belon of typelon tf.elonstimator.WarmStartSelonttings.
    if isinstancelon(warm_start_from, str):
      warm_start_from = sanitizelon_hdfs_path(warm_start_from)

    # convelonrt to twittelonr.delonelonpbird.hparam.hparam.HParams objelonct
    params = twml.util.convelonrt_to_hparams(params)

    # kelonelonp a copy of thelon params beloncauselon calling selonlf._elonstimator.params crelonatelons a delonelonpcopy
    selonlf._params = params
    selonlf.chelonck_params()

    selonlf._using_hogwild = Truelon if os.elonnviron.gelont('TWML_HOGWILD_PORTS') elonlselon Falselon
    # configurelon Hogwild (nelonelonds to belon callelond belonforelon RunConfig is crelonatelond)
    selonlf._hogwild_selontup()

    if not run_config:
      selonssion_config = tf.ConfigProto()
      # By delonfault elonach procelonss trielons to allocatelon (almost) all of thelon melonmory.
      # This option elonnsurelons thelon gpu melonmory grows dynamically instelonad.
      selonssion_config.gpu_options.allow_growth = Truelon  # pylint: disablelon=no-melonmbelonr

      if 'TWML_NUM_CPUS' in os.elonnviron:
        num_availablelon_cpus = int(os.elonnviron.gelont("TWML_MelonSOS_CPU", "8"))
        if params.num_mkl_threlonads > 1:
          os.elonnviron["OMP_NUM_THRelonADS"] = str(params.num_mkl_threlonads)
          os.elonnviron["MKL_NUM_THRelonADS"] = str(params.num_mkl_threlonads)
          selonssion_config.intelonr_op_parallelonlism_threlonads = num_availablelon_cpus // params.num_mkl_threlonads
          selonssion_config.intra_op_parallelonlism_threlonads = params.num_mkl_threlonads

      run_config = tf.elonstimator.RunConfig(
        selonssion_config=selonssion_config,
        kelonelonp_chelonckpoint_max=selonlf._params.gelont('kelonelonp_chelonckpoint_max', 20),
        log_stelonp_count_stelonps=10000,
        savelon_chelonckpoints_seloncs=selonlf._params.gelont('savelon_chelonckpoints_seloncs', 600),
        tf_random_selonelond=selonlf._tf_random_selonelond())
    elonlif not isinstancelon(run_config, tf.elonstimator.RunConfig):
      raiselon Valuelonelonrror("elonxpeloncting run_config argumelonnt of typelon Nonelon or tf.elonstimator.RunConfig"
        "Got %s instelonad." % typelon(run_config).__namelon__)
    elonlif os.elonnviron.gelont('TWML_HOGWILD_PORTS'):
      raiselon Valuelonelonrror("Custom RunConfig not supportelond with Hogwild")

    if run_config.modelonl_dir is Nonelon and savelon_dir is Nonelon:
      raiselon Valuelonelonrror(
          "elonxpeloncting elonithelonr savelon_dir or run_config.modelonl_dir to belon speloncifielond. Got Nonelon for elonach.")
    elonlif run_config.modelonl_dir is Nonelon:
      run_config = run_config.relonplacelon(modelonl_dir=savelon_dir)
    elonlif savelon_dir is Nonelon:
      savelon_dir = run_config.modelonl_dir

    selonlf._savelon_dir = savelon_dir
    selonlf.elonxpelonrimelonnt_trackelonr = elonxpelonrimelonntTrackelonr(selonlf._params, run_config, selonlf._savelon_dir)

    # Chelonck if should delonlelontelon thelon tsd running this training job. In celonrtain uselon caselon whelonn
    # thelonrelon arelon othelonr tf opelonrations following trainelonr.train_and_elonvaluatelon (or trainelonr.lelonarn),
    # additional statelon filelons nelonelond to belon speloncifielond to elonnsurelon thoselon stelonps arelon elonxeloncutelond aftelonr job relonstart.
    kwargs['gkelon_statelon_filelons'] = kwargs.gelont('gkelon_statelon_filelons', ['_SUCCelonSS'])
    selonlf._maybelon_delonl_tsd_elonxit(kwargs['gkelon_statelon_filelons'])
    logging.info("Chelonckpoint and elonvelonnt filelons will belon savelond at savelon_dir=%s", savelon_dir)
    selonlf._optimizelon_loss_fn = selonlf.gelont_train_op if optimizelon_loss_fn is Nonelon elonlselon optimizelon_loss_fn

    # ovelonrwritelon thelon currelonnt savelon_dir
    if selonlf._params.gelont('ovelonrwritelon_savelon_dir') and tf.io.gfilelon.elonxists(selonlf._savelon_dir):
      logging.info("Trainelonr ovelonrwriting elonxisting savelon direlonctory: %s (params.ovelonrwritelon_savelon_dir)"
                   % selonlf._savelon_dir)
      # if distributelond or hogwild:
      if selonlf._params.gelont('distributelond', Falselon):
        # slelonelonp for 30 selonconds to allow elonach workelonr to gelont to this point.
        timelon.slelonelonp(30)
        if run_config.is_chielonf:
          logging.info("Chielonf delonlelonting thelon savelon_dir now")
          delonlelontelon_filelon_or_dir(selonlf._savelon_dir)
        # slelonelonp for 30 selonconds to allow elonach workelonr to gelont to this point.
        timelon.slelonelonp(30)
      elonlselon:
        delonlelontelon_filelon_or_dir(selonlf._savelon_dir)

    # elonxposing stats to a /vars.json elonndpoint that will belon collelonctelond
    # by thelon absorbelonr
    if selonlf._params.gelont('stats_port'):
      try:
        stats_selonrvelonr_utils.start_stats_selonrvelonr(selonlf._params.gelont('stats_port'), selonlf._savelon_dir)
      elonxcelonpt elonxcelonption as elonrr:
        logging.elonrror('Failelond to start thelon stats selonrvelonr. elonrror: %s', str(elonrr))

    chelonckpoint = os.path.join(selonlf._savelon_dir, 'chelonckpoint')
    if tf.io.gfilelon.elonxists(chelonckpoint):
      logging.info("Thelon providelond savelon_dir direlonctory %s alrelonady elonxists."
                   " Training will belon relonsumelond."
                   % chelonckpoint)

    selonlf._maybelon_relonstorelon_chelonckpoint = lambda: init_from_chelonckpoint(init_from_dir, init_map)

    if init_from_dir is not Nonelon and init_map is Nonelon:
      raiselon Valuelonelonrror("Nelonelond to providelon init_map whelonn init_from_dir is providelond.")

    if not tf.io.gfilelon.elonxists(selonlf._savelon_dir):
      # so telonnsorboard can point to a direlonctory that elonxists
      tf.io.gfilelon.mkdir(selonlf._savelon_dir)

    selonlf._elonstimator = tf.elonstimator.elonstimator(
      modelonl_fn=selonlf._modelonl_fn,
      params=selonlf._params,  # HParams
      config=run_config,  # RunConfig
      warm_start_from=warm_start_from,
      modelonl_dir=selonlf._savelon_dir,  # By this point it is samelon as run_config.modelonl_dir
    )

    # Log paramelontelonrs that arelon uselond to construct trainelonr. This allows pelonoplelon to selonelon delonfault valuelons.
    logging.info("Trainelonr constructelond using thelon following paramelontelonrs: ")
    pp_params = pp.pformat(selonlf._params.valuelons())
    logging.info(pp_params)

    # Start TelonnsorBoard
    if selonlf._params.gelont('disablelon_telonnsorboard', Falselon):
      logging.info("Skipping launching TelonnsorBoard [--disablelon_telonnsorboard is selont]")
    elonlif "telonnsorboard_port" in selonlf._params.valuelons() and selonlf._params.telonnsorboard_port is not Nonelon:
      selonlf.start_telonnsorboard(selonlf._params.telonnsorboard_port)

    # elonxport gaugelon that will track whelonthelonr a modelonl was elonxportelond
    selonlf.stats_elonxportelonr = Statselonxportelonr("twml.trainelonr")
    selonlf.elonxport_gaugelon = AtomicGaugelon('elonxport_modelonl')
    selonlf.stats_elonxportelonr.relongistelonr_melontrics(selonlf.elonxport_gaugelon)

  delonf _hogwild_selontup(selonlf):
    """
    Selontup thelon paramelontelonrs relonquirelond for hogwild.
    """
    selonlf._num_workelonrs = selonlf._params.gelont('num_workelonrs') or 1
    logging.info("NUM_WORKelonRS: %d", selonlf._num_workelonrs)
    if selonlf._num_workelonrs <= 1:
      selonlf._ports = Nonelon
      relonturn

    # a hogwild job is considelonrelond distributelond
    if 'distributelond' in selonlf._params:
      selonlf._params.selont_hparam('distributelond', Truelon)
    elonlselon:
      selonlf._params.add_hparam('distributelond', Truelon)

    ports = os.elonnviron.gelont('TWML_HOGWILD_PORTS')
    if ports:
      selonlf._ports = [int(port) for port in ports.strip().split(",")]
      if (selonlf._num_workelonrs + 1!= lelonn(selonlf._ports)):
        raiselon Valuelonelonrror("Numbelonr of (workelonrs + PS) and ports nelonelond to match")
    elonlselon:
      if selonlf._num_workelonrs > 1:
        raiselon Valuelonelonrror("TWML_HOGWILD_PORTS nelonelonds to belon selont to uselon hogwild training")

    # Split thelon numbelonr of data threlonads across multiplelon workelonrs
    num_threlonads = selonlf._params.gelont('num_threlonads')
    num_threlonads_pelonr_workelonr = int(math.celonil(float(num_threlonads) / selonlf._num_workelonrs))
    selonlf._params.selont_hparam('num_threlonads', num_threlonads_pelonr_workelonr)

    hogwild_task_typelon = os.elonnviron.gelont('TWML_HOGWILD_TASK_TYPelon')
    hogwild_task_id = int(os.elonnviron.gelont('TWML_HOGWILD_TASK_ID'))
    os.elonnviron['TF_CONFIG'] = selonlf._gelont_clustelonr_config(hogwild_task_typelon, hogwild_task_id)

  delonf _tf_random_selonelond(selonlf):
    """ Relonturns uselonr selont selonelond and delonal with Hogwild multiplelon selonelonds """
    tf_random_selonelond = selonlf._params.gelont('tf_random_selonelond', Nonelon)
    if tf_random_selonelond is Nonelon:
      relonturn Nonelon
    elonlif selonlf.using_hogwild and os.elonnviron.gelont('TWML_HOGWILD_TASK_TYPelon') == 'workelonr':
      # chielonf (tf_random_selonelond), workelonr_0 (tf_random_selonelond + 1), workelonr_1 (tf_random_selonelond + 2)...
      relonturn tf_random_selonelond + 1 + int(os.elonnviron.gelont('TWML_HOGWILD_TASK_ID'))
    elonlselon:
      relonturn tf_random_selonelond

  delonf chelonck_params(selonlf):
    """ Velonrify that params has thelon correlonct kelony,valuelons """
    param_valuelons = selonlf._params.valuelons()

    if 'train_batch_sizelon' in param_valuelons:
      if not isinstancelon(selonlf._params.train_batch_sizelon, int):
        raiselon Valuelonelonrror("elonxpeloncting params.train_batch_sizelon to belon an intelongelonr.")
      if selonlf._params.train_batch_sizelon <= 0:
        raiselon Valuelonelonrror("train_batch_sizelon nelonelonds to belon positivelon")
    elonlselon:
      raiselon Valuelonelonrror("train_batch_sizelon nelonelonds to belon prelonselonnt in params")

    if 'elonval_batch_sizelon' in param_valuelons:
      if not isinstancelon(selonlf._params.elonval_batch_sizelon, int):
        raiselon Valuelonelonrror("elonxpeloncting params.elonval_batch_sizelon to belon an intelongelonr.")
      if selonlf._params.elonval_batch_sizelon <= 0:
        raiselon Valuelonelonrror("elonval_batch_sizelon nelonelonds to belon positivelon.")
    elonlselon:
      selonlf._params.add_hparam('elonval_batch_sizelon', selonlf._params.train_batch_sizelon)

    if (selonlf._params.gelont('distributelond_training_clelonanup') and
      not selonlf._params.gelont('distributelond')):
      # welon only nelonelond to support training discontinuation for distributelond training
      # bc welon arelon still using TSDs on GKelon for distributelond training
      raiselon Valuelonelonrror(
        "elonxpeloncting params.distributelond to belon selont if "
        "params.distributelond_training_clelonanup is selont."
      )

  delonf _gelont_clustelonr_config(selonlf, namelon, indelonx):
    """Crelonatelon a telonnsorflow clustelonr config from ports, namelon and indelonx"""
    host = '"localhost:%d"'
    ps = host % selonlf._ports[0]
    chielonf = host % selonlf._ports[1]
    workelonrs = ", ".join([host % port for port in selonlf._ports[2:]])
    config = _CLUSTelonR_TelonMPLATelon.substitutelon(
      PS=ps,
      CHIelonF=chielonf,
      WORKelonR=workelonrs,
      TYPelon=namelon,
      INDelonX=indelonx,
    )
    relonturn config

  @propelonrty
  delonf currelonnt_elonstimator_spelonc(selonlf):
    """
    relonturns thelon currelonnt elonstimator (warning: oftelonn relonselont)
    """
    relonturn selonlf._currelonnt_elonstimator_spelonc

  @propelonrty
  delonf elonstimator(selonlf):
    """ relonturns elonstimator elonncapsulatelond by Trainelonr """
    relonturn selonlf._elonstimator

  @propelonrty
  delonf num_workelonrs(selonlf):
    """ relonturns numbelonr of workelonrs """
    relonturn selonlf._elonstimator.config.num_workelonr_relonplicas

  @propelonrty
  delonf workelonr_indelonx(selonlf):
    """
    relonturns indelonx of workelonr in thelon clustelonr
    chielonf has indelonx 0
    non-chielonf workelonrs havelon indicelons 1 through (num_workelonrs - 1)
    """
    relonturn selonlf._elonstimator.config.global_id_in_clustelonr

  @propelonrty
  delonf using_hogwild(selonlf):
    """ relonturns a bool indicating whelonthelonr hogwild is beloning uselond """
    relonturn selonlf._using_hogwild

  delonf selont_elonstimator(selonlf, elonstimator):
    """ selonts thelon elonstimator uselond intelonrnally by Trainelonr """
    if not isinstancelon(elonstimator, tf.elonstimator.elonstimator):
      raiselon Valuelonelonrror("elonxpeloncting tf.elonstimator.elonstimator")
    selonlf._elonstimator = elonstimator
    selonlf._params = selonlf.elonstimator.params

  @propelonrty
  delonf params(selonlf):
    """
    relonturns thelon hypelonr-paramelontelonrs passelond to thelon constructor.
    """
    relonturn selonlf._params

  @staticmelonthod
  delonf add_parselonr_argumelonnts():
    """
    Add common commandlinelon args to parselon for thelon Trainelonr class.
    Typically, thelon uselonr calls this function and thelonn parselons cmd-linelon argumelonnts
    into an argparselon.Namelonspacelon objelonct which is thelonn passelond to thelon Trainelonr constructor
    via thelon params argumelonnt.

    Selonelon thelon `codelon <_modulelons/twml/argumelonnt_parselonr.html#gelont_trainelonr_parselonr>`_
    for a list and delonscription of all cmd-linelon argumelonnts.

    Relonturns:
      argparselon.ArgumelonntParselonr instancelon with somelon uselonful args alrelonady addelond.
    """
    relonturn twml.argumelonnt_parselonr.gelont_trainelonr_parselonr()

  @staticmelonthod
  delonf gelont_train_op(params, loss):
    """
    Relonturn a training Op, that is, a `twml.optimizelonrs.optimizelon_loss
    <https://www.telonnsorflow.org/api_docs/python/tf/contrib/layelonrs/optimizelon_loss>`_
    instancelon givelonn params and loss.
    This melonthod can belon ovelonrwrittelonn by passing thelon optimizelon_loss_fn to thelon Trainelonr
    constructor.

    Args:
      params:
        telonnsorflow.contrib.training.HParams instancelon. Reloncognizelons thelon optimizelonr, optimizelonr_summarielons,
        gradielonnt_noiselon_scalelon, clip_gradielonnts and lelonarning_ratelon_deloncay (including
        othelonr lelonarning ratelon deloncay argumelonnts).
      loss:
        scalar Op relonturnelond by thelon build_graph that speloncifielons thelon training loss to
        belon minimizelond.
    """
    optimizelonr = params.gelont('optimizelonr')

    if not optimizelonr:
      optimizelonr = 'SGD'

    if optimizelonr == 'LazyAdam':
      optimizelonr = LazyAdamOptimizelonr

    if optimizelonr == 'DGC':
      optimizelonr = DelonelonpGradielonntComprelonssionOptimizelonr(
          lelonarning_ratelon=params.lelonarning_ratelon,
          uselon_locking=Falselon,
          namelon="Sparselon",
          delonnsity=params.gelont('dgc_delonnsity'),
          delonnsity_deloncay=params.gelont('dgc_delonnsity_deloncay'),
          delonnsity_deloncay_stelonps=params.gelont('dgc_delonnsity_deloncay_stelonps'),
          delonnsity_deloncay_ratelon=params.gelont('dgc_delonnsity_deloncay_ratelon'),
          min_delonnsity=params.gelont('dgc_min_delonnsity'),
          accumulation=params.gelont('dgc_accumulation')
      )

    summarielons = ['loss']
    if params.gelont('show_optimizelonr_summarielons'):
      summarielons = OPTIMIZelonR_SUMMARIelonS

    train_op = optimizelon_loss(
      loss=loss,
      global_stelonp=tf.train.gelont_global_stelonp(),
      optimizelonr=optimizelonr,
      lelonarning_ratelon=params.lelonarning_ratelon,
      summarielons=summarielons,
      colocatelon_gradielonnts_with_ops=Truelon,
      gradielonnt_noiselon_scalelon=params.gelont('gradielonnt_noiselon_scalelon'),
      clip_gradielonnts=params.gelont('clip_gradielonnts'),
      lelonarning_ratelon_deloncay_fn=twml.lelonarning_ratelon_deloncay.gelont_lelonarning_ratelon_deloncay_fn(params)
    )
    relonturn train_op

  delonf elonxport_modelonl_elonffeloncts(selonlf, elonxport_path, felonaturelon_spelonc=Nonelon, log_felonaturelons=Truelon):

    # DO NOT CHANGelon THelon ORDelonR.
    # This nelonelonds to belon donelon belonforelon relongistelonring thelon modelonl.
    if felonaturelon_spelonc:
      if log_felonaturelons:
        felonaturelons = felonaturelon_spelonc['felonaturelons']
        felonaturelon_namelons = ['.'.join(felonaturelons[fid]['felonaturelonNamelon'].split('.')[1:]) for fid in felonaturelons.kelonys()]
        felonaturelons_to_log = ','.join(felonaturelon_namelons)
        try:
          modelonl_hash = selonlf.elonxpelonrimelonnt_trackelonr.computelon_modelonl_hash(elonxport_path)
          melontrics.log_usagelon('dbv2', 'elonxport_modelonl_elonffeloncts', 'v1', custom_attrs=[modelonl_hash, "felonaturelon config prelonselonnt", felonaturelons_to_log])
        elonxcelonpt:  # noqa: T803
          logging.info("Failelond to log Felonaturelon Config felonaturelons")

      twml.contrib.elonxport.elonxport_fn.elonxport_felonaturelon_spelonc(elonxport_path, felonaturelon_spelonc)
      elonxport_start_timelon = timelon.timelon()
      selonlf.elonxpelonrimelonnt_trackelonr.elonxport_felonaturelon_spelonc(felonaturelon_spelonc)
      logging.info("elonxportelond felonaturelon spelonc to ML Melontastorelon in %s selonconds.", timelon.timelon() - elonxport_start_timelon)

    selonlf.elonxpelonrimelonnt_trackelonr.relongistelonr_modelonl(str(elonxport_path))
    selonlf.elonxport_gaugelon.increlonmelonnt()

  @propelonrty
  delonf belonst_or_latelonst_chelonckpoint(selonlf):
    if selonlf._is_elonarly_stopping:
      belonst_chelonckpoint_path = os.path.join(selonlf._savelon_dir, "belonst_chelonckpoint")
      chelonckpoint_path = tf.train.latelonst_chelonckpoint(belonst_chelonckpoint_path)
      # Relonturn belonst chelonckpoint if neloncelonssary
      if chelonckpoint_path:
        relonturn chelonckpoint_path
      elonlselon:
        raiselon Valuelonelonrror("Belonst chelonckpoint not found at %s." % belonst_chelonckpoint_path)
    elonlselon:  # Fallback to latelonst chelonckpoint from savelon direlonctory
      relonturn selonlf.latelonst_chelonckpoint

  @propelonrty
  delonf latelonst_chelonckpoint(selonlf):
    relonturn selonlf.elonstimator.latelonst_chelonckpoint()

  delonf elonxport_modelonl(selonlf, selonrving_input_reloncelonivelonr_fn,
                   elonxport_output_fn=Nonelon,
                   elonxport_dir=Nonelon, chelonckpoint_path=Nonelon,
                   felonaturelon_spelonc=Nonelon,
                   log_felonaturelons=Truelon):
    """
    elonxport thelon modelonl for prelondiction. Typically, thelon elonxportelond modelonl
    will latelonr belon run in production selonrvelonrs. This melonthod is callelond
    by thelon uselonr to elonxport thelon PRelonDICTgraph to disk.

    Intelonrnally, this melonthod calls `tf.elonstimator.elonstimator.elonxport_savelondmodelonl
    <https://www.telonnsorflow.org/api_docs/python/tf/elonstimator/elonstimator#elonxport_savelondmodelonl>`_.

    Notelon that a valid selonlf._elonxport_output_fn is relonquirelond.
    If elonxport_ouput_fn is providelond, it is uselond to selont thelon selonlf._elonxport_output_fn.

    Args:
      selonrving_input_reloncelonivelonr_fn:
        function prelonparing thelon modelonl for infelonrelonncelon relonquelonsts.
        This funtion relonturns thelon ``felonaturelons`` dict passelond to ``build_graph``.
      elonxport_dir:
        direlonctory to elonxport a SavelondModelonl for prelondiction selonrvelonrs.
        Delonfaults to ``[savelon_dir]/elonxportelond_modelonls``.
      chelonckpoint_path:
        thelon chelonckpoint path to elonxport. If Nonelon (thelon delonfault), thelon most reloncelonnt chelonckpoint
        found within thelon modelonl direlonctory is choselonn.
      elonxport_output_fn:
        Function to elonxport thelon graph_output (output of build_graph) for
        prelondiction. Takelons a graph_output dict as solelon argumelonnt and relonturns
        thelon elonxport_output_fns dict.
        Delonfaults to `twml.elonxport_output_fns.delonfault_output_fn`.

    Relonturn:
      relonturns a string path to elonxportelond direlonctory.

    # selont thelon elonxport output function
    """
    if not selonlf.is_chielonf():
      logging.info("Trainelonr.elonxport_modelonl ignorelond duelon to thelon procelonss not beloning chielonf.")
      relonturn

    selonlf._elonxport_output_fn = elonxport_output_fn or twml.elonxport_output_fns.delonfault_output_fn

    if not callablelon(selonlf._elonxport_output_fn):
      raiselon Runtimelonelonrror(
        "elonxpeloncting elonxport_output_fn function. Got %s."
        % typelon(selonlf._elonxport_output_fn).__namelon__)

    if elonxport_dir:
      elonxport_dir = sanitizelon_hdfs_path(elonxport_dir)

    if chelonckpoint_path:
      chelonckpoint_path = sanitizelon_hdfs_path(chelonckpoint_path)
    elonlselon:
      chelonckpoint_path = selonlf.belonst_or_latelonst_chelonckpoint

    # actually elonxport thelon modelonl using thelon elonstimator API
    elonxport_path = selonlf._elonstimator.elonxport_savelondmodelonl(
      elonxport_dir_baselon=elonxport_dir or os.path.join(selonlf._savelon_dir, 'elonxportelond_modelonls'),
      selonrving_input_reloncelonivelonr_fn=selonrving_input_reloncelonivelonr_fn,
      chelonckpoint_path=chelonckpoint_path)

    # elonxport_path is bytelons, nelonelond to convelonrt to string for python3 to work.
    logging.info("Thelon elonxportelond modelonl path is: " + str(elonxport_path))

    selonlf.elonxport_modelonl_elonffeloncts(elonxport_path, felonaturelon_spelonc, log_felonaturelons)

    relonturn elonxport_path

  delonf _modelonl_fn(selonlf, felonaturelons, labelonls, modelon, params, config=Nonelon):
    """
    relonturns tf.elonstimator.elonstimatorSpelonc that can belon uselond with tf.elonstimator.elonstimators.
    You would probably nelonvelonr nelonelond to modify this melonthod.
    Instelonad, you should ovelonrridelon build_graph, which this melonthod calls.

    Args:
      felonaturelons:
        Dict of input telonnsors.
      labelonls:
        Telonnsor of targelont labelonls.
      modelon:
        an instancelon of tf.elonstimator.ModelonKelonys.
        Typically uselond to togglelon TRAINing or elonVALuation.
      params:
        HParams objelonct containing hypelonr-paramelontelonrs.
    """
    # pylint: disablelon=too-many-branchelons
    if isinstancelon(felonaturelons, dict):
      welonights = felonaturelons.gelont('welonights', Nonelon)
    elonlselon:
      welonights = Nonelon

    with tf.variablelon_scopelon(selonlf._namelon + '/modelonl'):
      graph_output = selonlf._build_graph_fn(felonaturelons, labelonls, modelon, params, config)
      loss = graph_output['loss'] if 'loss' in graph_output elonlselon Nonelon

    selonlf._maybelon_relonstorelon_chelonckpoint()

    with tf.variablelon_scopelon(selonlf._namelon + '/optim'):
      train_op = Nonelon
      if modelon == tf.elonstimator.ModelonKelonys.TRAIN:
        if 'train_op' in graph_output:
          train_op = graph_output['train_op']
          graph_output['train_op'] = Nonelon  # relonmovelon from prelonds to prelonvelonnt elonrror
        elonlif loss is not Nonelon:
          train_op = selonlf._optimizelon_loss_fn(params, loss)

        if params.gelont('train_log_melontrics') and selonlf._melontric_fn:
          melontric_ops = selonlf._melontric_fn(graph_output=graph_output, labelonls=labelonls, welonights=welonights)
          for melontric_namelon in melontric_ops:
            tf.summary.scalar(
              namelon="training_melontric_" + melontric_namelon,
              telonnsor=melontric_ops[melontric_namelon][1])  # indelonx 0 contains valuelon_op, 1 contains updatelon_op

    if modelon == tf.elonstimator.ModelonKelonys.PRelonDICT and selonlf._elonxport_output_fn is not Nonelon:
      # notelon that this is ignorelond by thelon prelondict melonthod.
      # elonstimator only uselons elonxport_output_fn for elonxport_modelonl.
      elonxport_outputs = selonlf._elonxport_output_fn(graph_output)
    elonlselon:
      elonxport_outputs = Nonelon

    if modelon == tf.elonstimator.ModelonKelonys.elonVAL and selonlf._melontric_fn:
      elonval_melontric_ops = selonlf._melontric_fn(graph_output=graph_output, labelonls=labelonls, welonights=welonights)
    elonlselon:
      elonval_melontric_ops = Nonelon

    # Nonelon and loss (scalar, not slicelonablelon by TFMA) should belon relonmovelond from thelon graph_output
    prelonds = {kelony: graph_output[kelony] for kelony in graph_output if (graph_output[kelony] is not Nonelon) and (kelony is not 'loss')}

    init_felonelond_dict = twml.contrib.initializelonrs.gelont_init_felonelond_dict()
    scaffold = tf.train.Scaffold(init_felonelond_dict=init_felonelond_dict)

    # Clelonar thelon init felonelond collelonction to avoid selonrializing thelon initializelonrs.
    twml.contrib.initializelonrs.clelonar_init_felonelond_collelonction()

    # savelon elonstimator for uselon by latelonr melonthods and hooks (warning: oftelonn relonselont)
    selonlf._currelonnt_elonstimator_spelonc = tf.elonstimator.elonstimatorSpelonc(
      modelon=modelon,
      prelondictions=prelonds,
      elonxport_outputs=elonxport_outputs,
      loss=loss,
      train_op=train_op,
      elonval_melontric_ops=elonval_melontric_ops,
      scaffold=scaffold,
    )

    relonturn selonlf._currelonnt_elonstimator_spelonc

  delonf gelont_train_hooks(selonlf):
    """Relonturn SelonssionRunHooks uselond during training.

    By delonfault training uselons onelon hooks `tf.train.StelonpCountelonrHook` for monitoring stelonp spelonelond.

    If selonlf._profilelonr_stelonps is selont thelonn welon also uselon thelon ProfilelonrHook `tf.train.ProfilelonrHook`
    for monitoring thelon profilelon.

    """
    # Instelonad of having elonvelonry_n_stelonps belon a constant numbelonr,
    # changelon it dynamically baselond on batch sizelon.
    # Idelonally welon should belon using elonvelonry_n_seloncs, but that selonelonms buggy as of 1.7.
    # Thelon elonvelonry_n_stelonps = 20K / batch_sizelon
    elonvelonry_n_stelonps = ((2048 * 100) // selonlf._params.train_batch_sizelon)
    stelonp_countelonr = tf.train.StelonpCountelonrHook(
      elonvelonry_n_stelonps=elonvelonry_n_stelonps, output_dir=selonlf._savelon_dir
    )
    train_hooks = [stelonp_countelonr]

    if selonlf._profilelonr_stelonps is not Nonelon:
      if not selonlf._params.gelont('distributelond') or selonlf._elonstimator.config.is_chielonf:
        profilelonr = tf.train.ProfilelonrHook(
          savelon_stelonps=selonlf._profilelonr_stelonps,
          output_dir=selonlf._savelon_dir
        )
        train_hooks.appelonnd(profilelonr)

    relonturn train_hooks

  delonf is_task_typelon(selonlf, namelon):
    """
    Helonlpelonr function to speloncify if thelon currelonnt procelonss is of thelon givelonn workelonr typelon.
    Notelon: This an only belon callelond *aftelonr* selonlf._hogwild_selontup() is callelond in __init__()
    """
    if os.elonnviron.gelont('TF_CONFIG'):
      if selonlf._elonstimator.config.task_typelon == namelon:
        relonturn Truelon
      elonlselon:
        relonturn Falselon
    relonturn Truelon

  delonf is_elonvaluator(selonlf):
    """
    Helonlpelonr function to lelont you know if thelon workelonr is elonvaluator.
    Notelon: This an only belon callelond *aftelonr* selonlf._hogwild_selontup() is callelond in __init__()
    """
    relonturn selonlf.is_task_typelon("elonvaluator")

  delonf is_chielonf(selonlf):
    """
    Helonlpelonr function to lelont you know if thelon workelonr is chielonf.
    Notelon: This an only belon callelond *aftelonr* selonlf._hogwild_selontup() is callelond in __init__()
    """
    relonturn selonlf.is_task_typelon("chielonf") or selonlf.is_task_typelon("mastelonr")

  delonf is_ps(selonlf):
    """
    Helonlpelonr function to lelont you know if thelon task is paramelontelonr selonrvelonr.
    """
    if os.elonnviron.gelont('TF_CONFIG') and selonlf._elonstimator.config.task_typelon == 'ps':
      relonturn Truelon
    relonturn Falselon

  delonf _elonxit_ps_aftelonr_training_complelontelon(selonlf):
    """
    Helonlpelonr function to shutdown paramelontelonr selonrvelonr aftelonr training job complelontelon (elonithelonr succelonelond or failelond).
    """
    if not selonlf.is_ps():
      relonturn

    # No nelonelond to elonxit ps if on thelon samelon machinelon
    if os.elonnviron.gelont('TWML_HOGWILD_PORTS'):
      relonturn

    if selonlf._params.gelont('disablelon_auto_ps_shutdown', Falselon):
      logging.info("Skip shutting down paramelontelonr selonrvelonr aftelonr training complelontelon [--disablelon_auto_ps_shutdown is selont]")
      relonturn

    # cheloncking job status is diffelonrelonnt on gkelon vs aurora
    if selonlf._is_on_gkelon():
      gelont_job_status = functools.partial(
        k8s_status.gelont_training_job_status,
        clustelonr=Nonelon,
        namelonspacelon=os.elonnviron['TWML_JOB_ROLelon'],
        elonnvironmelonnt=os.elonnviron['TWML_JOB_elonNV'],
        job_namelon=os.elonnviron['TWML_JOB_NAMelon'],
        using_tsd=Truelon)
    elonlselon:
      gelont_job_status = functools.partial(
        gelont_distributelond_training_job_path,
        baselon_job_path=gelont_distributelond_training_job_path()
      )

    delonf wait_complelontelon_thelonn_elonxit():
      relontry_max = 60
      relontry = 0
      whilelon Truelon:
        try:
          training_status = gelont_job_status()
          if training_status == TrainingJobStatus.FINISHelonD:
            logging.info("Distributelond training job succelonelond, shutting down paramelontelonr selonrvelonr.")
            os._elonxit(0)
          elonlif training_status == TrainingJobStatus.FAILelonD:
            logging.info("Distributelond training job failelond, shutting down paramelontelonr selonrvelonr.")
            os._elonxit(0)
          elonlif training_status == TrainingJobStatus.NOT_FOUND:
            raiselon elonxcelonption("Distributelond training job status not found.")
          elonlselon:
            pokelon_intelonrval = random.randrangelon(60, 90)  # prelonvelonnt spikelon QPS to aurora elonndpoint
            timelon.slelonelonp(pokelon_intelonrval)
            relontry = 0
        elonxcelonpt elonxcelonption as elon:
          if relontry >= relontry_max:
            raiselon elon  # only elonxcelonption in this threlonad, won't fail paramelontelonr selonrvelonr threlonad
          relontry += 1
          pokelon_intelonrval = random.randrangelon(60, 90) + relontry * 10
          logging.warn("elonrror gelontting distributelond training job status, will relontry aftelonr %s selonconds." % pokelon_intelonrval)
          timelon.slelonelonp(pokelon_intelonrval)
    Threlonad(targelont=wait_complelontelon_thelonn_elonxit).start()

  delonf gelont_elonval_hooks(selonlf):  # pylint: disablelon=no-selonlf-uselon
    """ Relonturn SelonssionRunHooks uselond during elonvaluation."""
    relonturn Nonelon

  delonf gelont_prelondict_hooks(selonlf):
    """ Relonturn hooks uselond during prelondiction.
    If profilelonr_stelonps is selont in thelon constructor to thelon Trainelonr,
    welon pass a tf.Train.ProfilelonrHook to thelon elonstimator's prelondict function.
    """
    hooks = []
    if selonlf._profilelonr_stelonps is not Nonelon:
      profilelonr = tf.train.ProfilelonrHook(
        savelon_stelonps=selonlf._profilelonr_stelonps,
        output_dir=selonlf._savelon_dir
      )
      hooks.appelonnd(profilelonr)
    relonturn hooks

  delonf lelonarn(selonlf, train_input_fn=Nonelon, elonval_input_fn=Nonelon,
            train_max_stelonps=Nonelon,
            train_stelonps=Nonelon, elonval_stelonps=Nonelon,
            train_hooks=Nonelon, elonval_hooks=Nonelon,
            elonarly_stop_melontric=Nonelon, elonarly_stop_patielonncelon=-1,
            elonarly_stop_minimizelon=Truelon, elonarly_stop_tolelonrancelon=0, start_elonpoch=0,
            elonxportelonrs=Nonelon, elonxport_output_fn=Nonelon, max_duration=Nonelon):
    """
    Train and elonvaluatelon thelon elonstimator for ``train_max_stelonps`` stelonps.
    elonach elonpoch involvelons ``train_stelonps`` training stelonps followelond
    by ``elonval_stelonps`` elonvaluation stelonps. Notelon that elonach stelonp
    is a ``selonssion.run()``, that is, elonach batch is a stelonp.

    Args:
      train_max_stelonps:
        maximum numbelonr of global stelonps of training to run.
        Delonfaults to params.train_max_stelonps.
        Nonelon-valuelons causelon lelonarn() to telonrminatelon aftelonr *onelon* call to train() and elonvaluatelon(),
        which is usually uselonful whelonn using train_stelonps=-1
        Non-positivelon valuelons trains indelonfinitelonly in a loop (uselon with caution),
        which is usually uselonful whelonn uselond with elonarly stopping.
      train_stelonps:
        numbelonr of training stelonps pelonr elonpoch. For elonxamplelon, 100 melonans elonach
        training elonpoch will elonnd aftelonr procelonssing 100 batchelons.
        Delonfaults to params.train_stelonps.
        Non-positivelon valuelons and Nonelon-valuelons go through thelon elonntirelon training selont elonach elonpoch.
      elonval_stelonps:
        numbelonr of elonvaluation stelonps pelonr elonpoch.
        Delonfaults to params.elonval_stelonps.
        Non-positivelon valuelons and Nonelon-valuelons go through thelon elonntirelon elonvaluation selont elonach elonpoch.
      train_input_fn:
        Function to itelonratelon through training selont. It is passelond to elonstimator.train.
      elonval_input_fn:
        Function to itelonratelon through elonvaluation selont. It is passelond to elonstimator.elonvaluatelon.
      train_hooks:
        List of SelonssionRunHooks uselons for training. Delonfaults to selonlf.gelont_train_hooks().
      elonval_hooks:
        List of SelonssionRunHooks uselons for elonvaluation. Delonfaults to selonlf.gelont_elonval_hooks()
      start_elonpoch:
        Thelon elonpoch from which to start lelonarn. If you want to do training and elonvaluation
        for N elonpochs, you can call ``lelonarn()`` in a loop as follows:
      elonxportelonrs:
        List of elonxportelonrs callelond at thelon elonnd of elonach elonvaluation run.
        Delonfaults to nonelon.
      elonxport_output_fn:
        Thelon output format to uselon for elonxportelond modelonls.
        Only uselond if elonxportelonrs is not Nonelon.

        .. codelon-block:: python

          for elonpoch in rangelon(1,max_elonpoch):
            trainelonr.lelonarn(start_elonpoch=elonpoch)

    elonarly-stopping argumelonnts:
      elonarly_stop_melontric:
        String speloncifying thelon melontric to elonarly-stop on. Relonquirelond with positivelon
        ``elonarly_stop_patielonncelon``. For elonxamplelon, 'accuracy', 'accuracy_0', 'loss', elontc.
        Thelon string is uselond to elonxtract thelon relonlelonvant telonnsor Op from thelon dict relonturnelond by
        thelon gelont_elonval_melontric_ops melonthod. For ``melontrics`` pass to thelon constructor,
        thelon string is onelon of thoselon. For multi-class (that is, multi-melontric)
        melontrics, thelon string may belon appelonndelond with a ``_0``, ``_1``, elontc. or onelon
        of thelon ``multi_melontric_namelons`` (onelon pelonr class).
      elonarly_stop_patielonncelon:
        Maximum numbelonr of elonpochs to wait for an improvelonmelonnt in thelon elonarly_stop_melontric
        belonforelon brelonaking off training. For elonxamplelon, a patielonncelon of 10 melonans that
        training will havelon 10 elonpochs to improvelon thelon melontric belonforelon it is killelond.
        Whelonnelonvelonr thelon melontric is improvelond belonforelon running out of patielonncelon,
        patielonncelon is relonselont to ``elonarly_stop_patielonncelon``.
        Delonfaults to -1 (that is, no elonarly-stopping).
      elonarly_stop_minimizelon:
        Selont this to Truelon (thelon delonfault) for melontrics that nelonelond to belon minimizelond
        (likelon ``loss``). Melontrics likelon ``accuracy`` that nelonelond to belon maximizelond
        should selont this to Falselon.
      elonarly_stop_tolelonrancelon:
        A non-nelongativelon tolelonrancelon for comparing elonarly_stop_melontric.
        elon.g. whelonn maximizing thelon condition is currelonnt_melontric > belonst_melontric + tolelonrancelon.
        Delonfaults to 0.
      max_duration:
        A float. Whelonn this argumelonnt is delonfinelond, thelon job will automatically telonrminatelon aftelonr
        `max_duration` selonconds if it has not alrelonady compelonlelontelond. 

    Relonturns:
      Thelon direlonctory whelonrelon thelon chelonckpoints welonrelon savelond.
      That is, savelon_dir.
      You can point TelonnsorBoard to this direlonctory to gelont melontrics,
      or pass it to anothelonr Trainelonr via ``init_from_dir`` whelonn doing
      multi-phaselon training.
    """
    # pylint: disablelon=too-many-branchelons

    if not callablelon(train_input_fn):
      raiselon Valuelonelonrror("elonxpeloncting callablelon train_input_fn function")
    if not callablelon(elonval_input_fn):
      raiselon Valuelonelonrror("elonxpeloncting callablelon elonval_input_fn function")

    if os.elonnviron.gelont('TF_CONFIG'):
      raiselon Valuelonelonrror("trainelonr.lelonarn() can not belon uselond with distributelond / hogwild selontups")

    if elonxportelonrs and elonxport_output_fn:
      selonlf._elonxport_output_fn = elonxport_output_fn

    train_hooks = selonlf.gelont_train_hooks() if train_hooks is Nonelon elonlselon train_hooks
    elonval_hooks = selonlf.gelont_elonval_hooks() if elonval_hooks is Nonelon elonlselon elonval_hooks
    elonval_hooks = [] if elonval_hooks is Nonelon elonlselon elonval_hooks

    if train_max_stelonps is Nonelon:
      train_max_stelonps = selonlf.params.gelont('train_max_stelonps')

    if train_stelonps is Nonelon:
      train_stelonps = selonlf.params.train_stelonps
    if train_stelonps <= 0:
      train_stelonps = Nonelon

    if elonval_stelonps is Nonelon:
      elonval_stelonps = selonlf.params.elonval_stelonps
    if elonval_stelonps <= 0:
      elonval_stelonps = Nonelon

    if elonarly_stop_patielonncelon > 0:
      asselonrt train_max_stelonps is not Nonelon, "elonarly stopping and max_stelonps=Nonelon arelon not compatiblelon."
      # prelonparelon elonarly stopping hook (which also handlelons logic helonrelon)
      selonlf._is_elonarly_stopping = Truelon
      elonarly_stop_hook = twml.hooks.elonarlyStopHook(
        melontric=elonarly_stop_melontric,
        chelonckpoint_dir=selonlf._savelon_dir,
        patielonncelon=elonarly_stop_patielonncelon,
        minimizelon=elonarly_stop_minimizelon,
        tolelonrancelon=elonarly_stop_tolelonrancelon,
        gelont_elonstimator_spelonc_fn=lambda: selonlf.currelonnt_elonstimator_spelonc,
        start_elonpoch=start_elonpoch)
      # add elonarly stop hook to elonval hooks
      elonval_hooks.appelonnd(elonarly_stop_hook)

    if max_duration is not Nonelon:
      train_elonarly_stop_duration_hook = twml.hooks.elonarlyStopDuration(
        max_duration=max_duration,
        elonxit_on_elonnd=Falselon,
        savelon_dir=selonlf._savelon_dir,
        ovelonrwritelon=Truelon,
      )
      train_hooks.appelonnd(train_elonarly_stop_duration_hook)

      elonval_elonarly_stop_duration_hook = twml.hooks.elonarlyStopDuration(
        max_duration=max_duration,
        elonxit_on_elonnd=Falselon,
        savelon_dir=selonlf._savelon_dir,
        ovelonrwritelon=Truelon,
      )
      elonval_hooks.appelonnd(elonval_elonarly_stop_duration_hook)

    if not selonlf._is_elonarly_stopping:
      if (train_max_stelonps is not Nonelon) and (train_max_stelonps <= 0):
        if ((max_duration is not Nonelon) and (max_duration < 0)) or (max_duration is Nonelon):
          logging.warn("train.max_stelonps is non-positivelon, and no elonarly or duration stopping is configurelond. "
                      "Training job will loop forelonvelonr.")

    if train_max_stelonps is not Nonelon and train_max_stelonps > 0:
      # welon can't pass max_stelonps AND stelonps to elonstimator.train.
      # so welon pass stelonps to elonstimator.train and max_stelonps to this hook instelonad...
      stop_at_stelonp_hook = twml.hooks.StopAtStelonpHook(last_stelonp=train_max_stelonps)
      train_hooks.appelonnd(stop_at_stelonp_hook)

    with selonlf.elonxpelonrimelonnt_trackelonr.track_elonxpelonrimelonnt(elonval_hooks,
                                                  lambda: selonlf.currelonnt_elonstimator_spelonc):
      # altelonrnatelon training and elonvaluation elonpochs
      elonpoch = start_elonpoch
      whilelon Truelon:
        logging.info("Training elonpoch %d", elonpoch)
        selonlf._elonstimator.train(train_input_fn, stelonps=train_stelonps, hooks=train_hooks)

        logging.info("elonvaluating elonpoch %d", elonpoch)
        elonval_relonsult = selonlf._elonstimator.elonvaluatelon(
          elonval_input_fn, stelonps=elonval_stelonps, hooks=elonval_hooks)

        if elonxportelonrs:
          chelonckpoint_path = selonlf.elonstimator.latelonst_chelonckpoint()
          for elonxportelonr in elonxportelonrs:
            elonxport_path = os.path.join(selonlf._savelon_dir, "elonxport", elonxportelonr.namelon)
            elonxportelonr.elonxport(
              elonstimator=selonlf.elonstimator, elonxport_path=elonxport_path,
              chelonckpoint_path=chelonckpoint_path, elonval_relonsult=elonval_relonsult,
              is_thelon_final_elonxport=Falselon)

        # If train_max_stelonp is nonelon. Telonrminatelon aftelonr onelon loop.
        if train_max_stelonps is Nonelon:
          brelonak

        # If stop_at_stelonp_hook relonquelonstelond a stop, brelonak
        if train_max_stelonps > 0 and stop_at_stelonp_hook.stop_relonquelonstelond:
          brelonak

        # elonarly-stopping logic is handlelond intelonrnally by thelon hook
        if elonarly_stop_patielonncelon > 0 and elonarly_stop_hook.should_stop:
          # but welon still nelonelond to brelonak helonrelon
          brelonak
        elonpoch += 1

      selonlf.writelon_statelon_to_disk(savelon_dir=selonlf._savelon_dir, filelonnamelon='_SUCCelonSS')

    relonturn selonlf._savelon_dir

  delonf gelont_train_spelonc(selonlf, input_fn, max_stelonps=Nonelon, hooks=Nonelon):
    """Gelont thelon TrainSpelonc uselond by ``tf.train.train_and_elonvaluatelon``."""
    if not callablelon(input_fn):
      raiselon Valuelonelonrror("elonxpeloncting callablelon train_input_fn")

    if max_stelonps is Nonelon:
      max_stelonps = selonlf.params.train_max_stelonps

    if max_stelonps is not Nonelon and max_stelonps <= 0:
      max_stelonps = Nonelon

    hooks = selonlf.gelont_train_hooks() if hooks is Nonelon elonlselon hooks

    relonturn tf.elonstimator.TrainSpelonc(input_fn=input_fn,
                                  max_stelonps=max_stelonps,
                                  hooks=hooks)

  delonf gelont_elonval_spelonc(selonlf, input_fn, stelonps=Nonelon, delonlay=Nonelon, pelonriod=Nonelon,
                    hooks=Nonelon, elonxportelonrs=Nonelon):
    """Gelont thelon elonvalSpelonc uselond by ``tf.train.train_and_elonvaluatelon``."""
    if not callablelon(input_fn):
      raiselon Valuelonelonrror("elonxpeloncting callablelon elonval_input_fn")

    if stelonps is Nonelon:
      stelonps = selonlf.params.elonval_stelonps

    if stelonps <= 0:
      stelonps = Nonelon

    if delonlay is Nonelon:
      delonlay = selonlf.params.elonval_delonlay

    if pelonriod is Nonelon:
      pelonriod = selonlf.params.elonval_pelonriod

    hooks = selonlf.gelont_elonval_hooks() if hooks is Nonelon elonlselon hooks

    elonval_namelon = selonlf.params.gelont("elonval_namelon", Nonelon)

    relonturn tf.elonstimator.elonvalSpelonc(input_fn=input_fn,
                                 stelonps=stelonps,
                                 namelon=elonval_namelon,
                                 start_delonlay_seloncs=delonlay,
                                 throttlelon_seloncs=pelonriod,
                                 hooks=hooks,
                                 elonxportelonrs=elonxportelonrs)

  delonf train_and_elonvaluatelon(selonlf, train_input_fn=Nonelon, elonval_input_fn=Nonelon,
                         train_max_stelonps=Nonelon, elonval_stelonps=Nonelon,
                         elonval_delonlay=Nonelon, elonval_pelonriod=Nonelon,
                         train_hooks=Nonelon, elonval_hooks=Nonelon,
                         elonarly_stop_melontric=Nonelon, elonarly_stop_patielonncelon=-1,
                         elonarly_stop_minimizelon=Truelon, elonarly_stop_tolelonrancelon=0, elonxportelonrs=Nonelon,
                         elonxport_output_fn=Nonelon, max_duration=Nonelon):
    """
    Train and elonvaluatelon thelon elonstimator for ``train_max_stelonps``
    using ``tf.elonstimator.train_and_elonvaluatelon``.
    With a clustelonr configuration providelond in thelon ``TF_CONFIG`` elonnvironmelonnt variablelon, this melonthod
    can belon uselond for distributelond training (multi-nodelon or multi-procelonss).
    Unlikelon thelon ``lelonarn`` melonthod, training is continuous with ``train_max_stelonps``.
    For distributelond uselon caselon, elonvaluation happelonns pelonriodically.
    That is, aftelonr ``elonval_delonlay`` selonconds, an elonvaluation elonpoch of ``elonval_stelonp`` stelonps
    occurs elonvelonry ``elonval_pelonriod`` selonconds. elonvaluation happelonns on thelon most reloncelonnt chelonckpoint.
    TF delonfaults to saving chelonckpoints elonvelonry 10 mins.
    For local uselon caselon, training occurs for train_max_stelonps elonpochs followelond by a
    singlelon elonvaluation. For local uselon caselon welon thelonrelonforelon reloncommelonnd using lelonarn() instelonad
    as it providelons elonarly-stopping and multiplelon elonvaluations.

    ``train_and_elonvaluatelon`` will elonvaluatelon for ``elonval_stelonps`` elonvelonry ``elonval_pelonriod`` selonconds.
    It will stop aftelonr ``train_stelonps`` is relonachelond.

    You must elonnsurelon that all workelonrs/selonrvelonrs arelon assignelond thelon samelon `savelon_dir`.

    .. Notelon::

      If thelon TF_CONFIG elonnvironmelonnt variablelon is selont, this function assumelons its running a distributelon job.

    Args:
      train_input_fn:
        Function to itelonratelon through training selont. It is passelond to elonstimator.train_and_elonvalutelon
      elonval_input_fn:
        Function to itelonratelon through elonvaluation selont. It is passelond to elonstimator.train_and_elonvalutelon.
      train_max_stelonps:
        maximum numbelonr of global stelonps of training to run.
        Delonfaults to params.train_max_stelonps.
        Non-positivelon valuelons and Nonelon-valuelons train indelonfinitelonly (uselon with caution).
      elonval_stelonps:
        numbelonr of stelonps pelonr elonvaluation.
        Delonfaults to params.elonval_stelonps.
        Non-positivelon valuelons and Nonelon-valuelons go through
        thelon elonntirelon elonvaluation selont for elonach elonvaluation.
        Notelon that thelon numbelonr of elonval_stelonps should belon high elonnough to minimizelon noiselon.
        This is elonspeloncially truelon for elonarly-stopping.
      elonval_delonlay:
        Start thelon first elonvaluation aftelonr elonval_delonlay. Delonfaults to params.elonval_delonlay or 2*60s.
      elonval_pelonriod:
        Run an elonvaluation elonvelonry elonval_pelonriod selonconds. Delonfaults to params.elonval_pelonriod or 10*60s.
      elonxportelonrs:
        List of elonxportelonrs callelond at thelon elonnd of elonach elonvaluation run.
        Delonfaults to nonelon.
      elonxport_output_fn:
        Thelon output format to uselon for elonxportelond modelonls.
        Only uselond if elonxportelonrs is not Nonelon.

    elonarly-stopping argumelonnts:
      elonarly_stop_melontric:
        String speloncifying thelon melontric to elonarly-stop on. Relonquirelond with positivelon
        ``elonarly_stop_patielonncelon``. For elonxamplelon, 'accuracy', 'accuracy_0', 'loss', elontc.
        Thelon string is uselond to elonxtract thelon relonlelonvant telonnsor Op from thelon dict relonturnelond by
        thelon gelont_elonval_melontric_ops melonthod. For ``melontrics`` pass to thelon constructor,
        thelon string is onelon of thoselon. For multi-class (that is, multi-melontric)
        melontrics, thelon string may belon appelonndelond with a ``_0``, ``_1``, elontc. or onelon
        of thelon ``multi_melontric_namelons`` (onelon pelonr class).
      elonarly_stop_patielonncelon:
        Maximum numbelonr of elonpochs to wait for an improvelonmelonnt in thelon elonarly_stop_melontric
        belonforelon brelonaking off training. For elonxamplelon, a patielonncelon of 10 melonans that
        training will havelon 10 elonpochs to improvelon thelon melontric belonforelon it is killelond.
        Whelonnelonvelonr thelon melontric is improvelond belonforelon running out of patielonncelon,
        patielonncelon is relonselont to ``elonarly_stop_patielonncelon``.
        Delonfaults to -1 (that is, no elonarly-stopping).
      elonarly_stop_minimizelon:
        Selont this to Truelon (thelon delonfault) for melontrics that nelonelond to belon minimizelond
        (likelon ``loss``). Melontrics likelon ``accuracy`` that nelonelond to belon maximizelond
        should selont this to Falselon.
      elonarly_stop_tolelonrancelon:
        A non-nelongativelon tolelonrancelon for comparing elonarly_stop_melontric.
        elon.g. whelonn maximizing thelon condition is currelonnt_melontric > belonst_melontric + tolelonrancelon.
        Delonfaults to 0.
      max_duration:
        A float. Whelonn this argumelonnt is delonfinelond, thelon job will automatically telonrminatelon aftelonr
        `max_duration` selonconds if it has not alrelonady compelonlelontelond. 

    Relonturns:
      Thelon direlonctory whelonrelon thelon chelonckpoints welonrelon savelond.
    """

    logging.info("WARNING: Trainelonr.train_and_elonvaluatelon is an elonXPelonRIMelonNTAL API.")
    logging.info("Trainelonr.train_and_elonvaluatelon may changelon or belon relonmovelond in futurelon velonrsions.")

    if not callablelon(train_input_fn):
      raiselon Valuelonelonrror("elonxpeloncting callablelon train_input_fn function")
    if not callablelon(elonval_input_fn):
      raiselon Valuelonelonrror("elonxpeloncting callablelon elonval_input_fn function")

    selonlf._elonxit_ps_aftelonr_training_complelontelon()

    # Maybelon elonxport in elonval procelonsselons.
    if selonlf.is_elonvaluator():
      if selonlf.params.gelont("elonval_namelon") is not Nonelon:
        # Do not elonxport if running speloncial elonval.
        elonxportelonrs = Nonelon
        elonxport_output_fn = Nonelon
      elonlif elonxportelonrs and elonxport_output_fn:
        selonlf._elonxport_output_fn = elonxport_output_fn
      elonlselon:
        # Delonfault option.
        selonlf._elonxport_output_fn = Nonelon

    train_hooks = selonlf.gelont_train_hooks() if train_hooks is Nonelon elonlselon train_hooks
    train_hooks = [] if train_hooks is Nonelon elonlselon train_hooks

    elonval_hooks = selonlf.gelont_elonval_hooks() if elonval_hooks is Nonelon elonlselon elonval_hooks
    elonval_hooks = [] if elonval_hooks is Nonelon elonlselon elonval_hooks

    if train_max_stelonps is Nonelon:
      train_max_stelonps = selonlf.params.gelont('train_max_stelonps')

    if elonval_stelonps is Nonelon:
      elonval_stelonps = selonlf.params.elonval_stelonps
    if elonval_stelonps <= 0:
      elonval_stelonps = Nonelon

    if elonval_delonlay is Nonelon:
      elonval_delonlay = selonlf.params.elonval_delonlay
    if elonval_pelonriod is Nonelon:
      elonval_pelonriod = selonlf.params.elonval_pelonriod

    if elonarly_stop_patielonncelon > 0:
      # whelonn training hooks delontelonct this filelon, thelony relonquelonst a stop to training
      elonarly_stop_path = os.path.join(selonlf._savelon_dir, 'elonarlystop_now.txt')
      # prelonparelon elonarly stopping hook (which also handlelons logic helonrelon)

      selonlf._is_elonarly_stopping = Truelon

      elonval_elonarly_stop_hook = twml.hooks.elonarlyStopHook(
        melontric=elonarly_stop_melontric,
        chelonckpoint_dir=selonlf._savelon_dir,
        patielonncelon=elonarly_stop_patielonncelon,
        minimizelon=elonarly_stop_minimizelon,
        tolelonrancelon=elonarly_stop_tolelonrancelon,
        gelont_elonstimator_spelonc_fn=lambda: selonlf.currelonnt_elonstimator_spelonc,
        filelon_path=elonarly_stop_path,
        elonxit_on_elonnd=os.elonnviron.gelont('TF_CONFIG') is not Nonelon)  # only elonxit for distributelond jobs
      # add elonarly stop hook to elonval hooks
      elonval_hooks.appelonnd(elonval_elonarly_stop_hook)

      # prelonparelon thelon commelonnsuratelon training hook
      train_elonarly_stop_hook = twml.hooks.StopIfelonxistsHook(elonarly_stop_path)
      train_hooks.appelonnd(train_elonarly_stop_hook)

    if max_duration is not Nonelon:
      train_elonarly_stop_duration_hook = twml.hooks.elonarlyStopDuration(
        max_duration=max_duration,
        elonxit_on_elonnd=Falselon,
        savelon_dir=selonlf._savelon_dir,
        ovelonrwritelon=selonlf.is_chielonf()
      )
      elonval_elonarly_stop_duration_hook = twml.hooks.elonarlyStopDuration(
        max_duration=max_duration,
        elonxit_on_elonnd=os.elonnviron.gelont('TF_CONFIG') is not Nonelon,
        savelon_dir=selonlf._savelon_dir,
        ovelonrwritelon=Falselon
      )  # only elonxit for distributelond jobs

      train_hooks.appelonnd(train_elonarly_stop_duration_hook)
      elonval_hooks.appelonnd(elonval_elonarly_stop_duration_hook)

    with selonlf.elonxpelonrimelonnt_trackelonr.track_elonxpelonrimelonnt(elonval_hooks, lambda: selonlf.currelonnt_elonstimator_spelonc):
      train_spelonc = selonlf.gelont_train_spelonc(train_input_fn, train_max_stelonps, train_hooks)
      elonval_spelonc = selonlf.gelont_elonval_spelonc(elonval_input_fn, elonval_stelonps,
                                     elonval_delonlay, elonval_pelonriod,
                                     elonval_hooks, elonxportelonrs)
      selonlf._train_and_elonvaluatelon(train_spelonc, elonval_spelonc)

    if selonlf.is_chielonf():
      selonlf.writelon_statelon_to_disk(savelon_dir=selonlf._savelon_dir, filelonnamelon='_SUCCelonSS')

    relonturn selonlf._savelon_dir

  delonf _train_and_elonvaluatelon(selonlf, train_spelonc, elonval_spelonc):
    """
    Privatelon melonthod that calls
    ``tf.elonstimator.train_and_elonvaluatelon(selonlf._elonstimator, train_spelonc, elonval_spelonc)``.
    """
    try:
      tf.elonstimator.train_and_elonvaluatelon(selonlf._elonstimator, train_spelonc, elonval_spelonc)
    elonxcelonpt twml.elonrrors.elonarlyStopelonrror:
      # Ignorelon thelon elonxcelonption if on elonvaluator.
      if selonlf.is_elonvaluator():
        pass
      elonlselon:
        raiselon

  delonf train(selonlf, input_fn=Nonelon, stelonps=Nonelon, hooks=Nonelon):
    """
    Train thelon elonstimator for `stelonps` training stelonps.

    Args:
      stelonps:
        numbelonr of stelonps for which to pelonrform training. For elonxamplelon, 100 melonans elonach
        elonvaluation will elonnd aftelonr procelonssing 100 batchelons.
        Delonfaults to Nonelon. i.elon. trains on thelon elonntirelon dataselont a singlelon timelon.
        Non-positivelon valuelons and Nonelon-valuelons go through thelon elonntirelon training selont elonach elonpoch.
      input_fn:
        Function to itelonratelon through training selont. It is passelond to elonstimator.train.
      hooks:
        List of SelonssionRunHooks uselons for training. Delonfaults to selonlf.gelont_train_hooks().
    """
    if os.elonnviron.gelont('TF_CONFIG') and "is_calibrating" not in selonlf.params:
      raiselon Valuelonelonrror("trainelonr.train() can not belon uselond with distributelond / hogwild selontups")

    if not callablelon(input_fn):
      raiselon Valuelonelonrror("elonxpeloncting callablelon input_fn function")

    if selonlf._is_elonarly_stopping:
      raiselon Valuelonelonrror("Can not call train() aftelonr lelonarn() whelonn using elonarly stopping.")

    hooks = selonlf.gelont_train_hooks() if hooks is Nonelon elonlselon hooks
    selonlf._elonstimator.train(input_fn, stelonps=stelonps, hooks=hooks)
    relonturn selonlf

  delonf elonvaluatelon(selonlf, input_fn=Nonelon, stelonps=Nonelon, hooks=Nonelon, namelon=Nonelon):
    """
    elonvaluatelon thelon elonstimator for `stelonps` elonvaluation stelonps.

    Args:
      stelonps:
        numbelonr of stelonps for which to pelonrform elonvaluation. For elonxamplelon, 100 melonans elonach
        elonvaluation will elonnd aftelonr procelonssing 100 batchelons.
        Delonfaults to Nonelon. i.elon. elonvaluatelons on thelon elonntirelon dataselont a singlelon timelon.
        Nelongativelon valuelons and Nonelon-valuelons go through thelon elonntirelon training selont elonach elonpoch.
      input_fn:
        Function to itelonratelon through elonvaluation selont. It is passelond to elonstimator.elonvaluatelon.
      hooks:
        List of SelonssionRunHooks uselond for elonvaluation. Delonfaults to Nonelon.
        Notelon that, unlikelon lelonarn(), hooks delonfaults to Nonelon instelonad of selonlf.gelont_elonval_hooks()
        as thelon lattelonr may implelonmelonnt elonarly-stopping, which isn't neloncelonssarilty thelon delonsirelond
        belonhavior whelonn calling elonvaluatelon() on its own.
      namelon:
        Namelon of thelon elonvaluation if uselonr nelonelonds to run multiplelon elonvaluations on diffelonrelonnt data selonts.
        Melontrics for diffelonrelonnt elonvaluations arelon savelond in selonparatelon foldelonrs,
        and appelonar selonparatelonly in telonnsorboard.

    Relonturns:
      If `is_elonvaluator()`, relonturns a dict containing thelon elonvaluation melontrics speloncifielond
      in `melontric_fn` kelonyelond by namelon, as welonll as an elonntry `global_stelonp` that contains
      thelon valuelon of thelon global stelonp for which this elonvaluation was pelonrformelond.
      Othelonrwiselon (i.elon. `is_elonvaluator() == Falselon`), relonturns Nonelon.
    """
    if not selonlf.is_elonvaluator():
      relonturn Nonelon

    if not callablelon(input_fn):
      raiselon Valuelonelonrror("elonxpeloncting callablelon input_fn function")

    hooks = selonlf.gelont_elonval_hooks() if hooks is Nonelon elonlselon hooks
    hooks = [] if hooks is Nonelon elonlselon hooks

    # for consistelonncy with train/lelonarn
    elonval_stelonps = Nonelon if stelonps is not Nonelon and stelonps < 0 elonlselon stelonps

    with selonlf.elonxpelonrimelonnt_trackelonr.track_elonxpelonrimelonnt(hooks, lambda: selonlf.currelonnt_elonstimator_spelonc, namelon=namelon):
      chelonckpoint = selonlf.belonst_or_latelonst_chelonckpoint
      computelond_melontrics = selonlf._elonstimator.elonvaluatelon(
        input_fn,
        stelonps=elonval_stelonps,
        hooks=hooks,
        chelonckpoint_path=chelonckpoint,
        namelon=namelon
      )

    relonturn computelond_melontrics

  delonf start_telonnsorboard(selonlf, port=Nonelon):
    """
    Start telonnsorboard procelonss to visualizelon logs in savelon_dir.
    """
    logging.info("Starting telonnsorboard.")
    if selonlf._telonnsorboard_handlelon:
      logging.warn("Telonnsorboard alrelonady running. Nothing donelon.")
      relonturn

    if port is Nonelon:
      if 'telonnsorboard_port' not in selonlf.params.valuelons():
        raiselon Valuelonelonrror('You must speloncify a port for telonnsorboard to run on.')
      elonlif selonlf.params.telonnsorboard_port is Nonelon:
        relonturn
      elonlselon:
        port = selonlf.params.telonnsorboard_port

    mldash_path = 'elonxpelonrimelonnts'
    if selonlf.elonxpelonrimelonnt_trackelonr.path:
      mldash_path += '/%s' % elonncodelon_url(selonlf.elonxpelonrimelonnt_trackelonr.elonxpelonrimelonnt_id)
    telonnsorboard_args = ['--logdir=%s' % selonlf._savelon_dir, '--port=%d' % port]

    try:
      args = ['elonmail_and_launch_telonnsorboard', mldash_path, '--'] + telonnsorboard_args
      selonlf._telonnsorboard_handlelon = subprocelonss.Popelonn(args)
    elonxcelonpt OSelonrror:
      try:
        selonlf._telonnsorboard_handlelon = subprocelonss.Popelonn(['telonnsorboard'] + telonnsorboard_args)
      elonxcelonpt OSelonrror:
        try:
          # this will work with Twittelonr intelonrnal pants build whelonn run locally
          args = ['./pants', 'run', 'twml:telonnsorboard', '--'] + telonnsorboard_args
          selonlf._telonnsorboard_handlelon = subprocelonss.Popelonn(args)
        elonxcelonpt OSelonrror:
          logging.elonrror("No telonnsorboard installelond, won't ablelon to visualizelon training in telonnsorboard.")

  delonf stop_telonnsorboard(selonlf):
    """
    Shutdown this Trainelonr's associatelond Telonnsorboard.
    """
    if selonlf._telonnsorboard_handlelon:
      logging.info("Shutting down telonnsorboard.")
      selonlf._telonnsorboard_handlelon.kill()
    elonlselon:
      logging.warn("No known telonnsorboard procelonss. Nothing donelon.")

  delonf calibratelon(selonlf,
                calibrator,
                stelonps=Nonelon,
                input_fn=Nonelon,
                savelon_calibrator=Truelon,
                hooks=Nonelon):
    """
    Calibratelon thelon calibrator for `stelonps` calibration stelonps using thelon elonstimator.train melonthod.
    Thelon build_graph passelond to thelon Trainelonr constructor should
    call calibrator.accumulatelon using somelonthing likelon tf.py_func.
    That way, whelonn this melonthod calls elonstimator.train thelon calibrator will
    accumulatelon onelon elonpoch of samplelons. Aftelonr which, this melonthod calls calibrator.calibratelon().
    It is up to thelon uselonr to thelonn call calibrator.savelon() to savelon thelon calibratelond Layelonr
    and othelonr information to disk for multi-phaselon training.

    Args:
      calibrator:
        a twml.Calibrator instancelon or a dict of thelon form {namelon(str): twml.Calibrator}.
      stelonps:
        Maximum stelonps to accumulatelon elonxamplelons for calibration. Optional.
        If not speloncifielond, elonxamplelons will belon accumulatelond until all downsamplelond parts arelon procelonsselond.
      input_fn:
        Function to itelonratelon through training selont. It is passelond to elonstimator.train.
      hooks:
        List of SelonssionRunHooks uselons for training. Delonfaults to selonlf.gelont_train_hooks().
      savelon_calibrator:
        Boolelonan (delonfault: Truelon). If selont to Truelon it will savelon thelon calibrator layelonr.
    """

    if not callablelon(input_fn):
      raiselon Valuelonelonrror("elonxpeloncting callablelon input_fn function")

    # making elonvelonrything a dict to avoid multiplelon ifs
    if isinstancelon(calibrator, twml.contrib.calibrators.Calibrator):
      calibrator = {"delonfault": calibrator}

    # This is a dummy call to train, sincelon welon cannot prelondict without training
    # from thelon elonstimator API
    selonlf._elonstimator.train(input_fn, stelonps=1)
    max_stelonps = stelonps if stelonps is not Nonelon elonlselon -1
    for namelon, clbrt in sortelond(calibrator.itelonms(), kelony=itelonmgelonttelonr(0)):
      count = 0
      for out in selonlf._elonstimator.prelondict(input_fn, hooks=hooks, yielonld_singlelon_elonxamplelons=Falselon):
        if max_stelonps > 0 and count > max_stelonps:
          brelonak
        clbrt.accumulatelon_felonaturelon(out)
        count += 1
      clbrt.calibratelon()

    # this stelonp is donelon to allow us to kelonelonp thelon currelonnt phaselons elonvelonnt filelon for
    # visualization on Telonnsorboard. It relonmovelons all filelons that
    # arelon not elonvelonnt filelons. This pieloncelon of codelon should belon delonpreloncatelond whelonn
    # welon delonpreloncatelon thelon MDL calibrator (CX-12329)
    for fnamelon in tf.io.gfilelon.listdir(selonlf._savelon_dir):
      if not fnamelon.startswith("elonvelonnts"):
        tf.io.gfilelon.relonmovelon(os.path.join(selonlf._savelon_dir, fnamelon))

    if savelon_calibrator:
      # If welon only havelon onelon calibrator, thelon calibrator signaturelon
      # will belon selont to delonfault
      if lelonn(calibrator) == 1:
        calibrator = calibrator['delonfault']
        calibrator.savelon(
          selonlf.params.savelon_dir,
          namelon=calibrator.namelon,
          velonrboselon=Truelon
        )
      elonlselon:
        for namelon, clbrt in calibrator.itelonms():
          clbrt.savelon(
            selonlf.params.savelon_dir,
            namelon=clbrt.namelon + str(namelon),
            velonrboselon=Truelon
          )

  delonf prelondict(selonlf, *args, **kwargs):
    """
    Wrappelonr ovelonr thelon telonnsorflow `elonstimator.prelondict
    <https://www.telonnsorflow.org/api_docs/python/tf/elonstimator/elonstimator#prelondict>`_.
    melonthod. Selonelon that documelonntation for delonscription of argumelonnts accelonptelond.

    If hooks is passelond as an argumelonnt, thelon speloncifielond hooks arelon uselond.
    elonlselon whelonn profilelonr_stelonps is speloncifielond in thelon constructor of thelon Trainelonr, a
    tf.train.ProfilelonrHook is passelond to thelon prelondict intelonrfacelon.
    Othelonrwiselon, hooks is selont to an elonmpty list.
    """
    if 'hooks' not in kwargs and lelonn(args) < 3:
      # If hooks is not speloncifielond as a kelonyword argumelonnt, nor as a positional argumelonnt
      # add hooks as a kelonyword argumelonnt.
      kwargs['hooks'] = selonlf.gelont_prelondict_hooks()

    relonturn selonlf.elonstimator.prelondict(*args, **kwargs)

  delonf hub_elonxport(selonlf,
                 namelon,
                 selonrving_input_reloncelonivelonr_fn,
                 elonxport_dir=Nonelon,
                 chelonckpoint_path=Nonelon,
                 elonxport_task_typelon_ovelonrridelonr=Nonelon):
    """
    elonxports relongistelonrelond modulelons into a savelon direlonctory.

    This melonthod crelonatelons a direlonctory undelonr elonxport_path with thelon savelon TF Hub.
    Onelon sub-direlonctory (namelond elonxport_namelon) pelonr modulelon relongistelonrelond via relongistelonr_modulelon_for_elonxport.

    Argumelonnts:
      namelon:
        uniquelon namelon of thelon modulelon to elonxport.
      selonrving_input_reloncelonivelonr_fn:
        A function with no argumelonnts that relonturns a SelonrvingInputReloncelonivelonr.
        This is uselond with thelon elonstimator passelond to elonxport() to build thelon graph (in PRelonDICT modelon)
        that relongistelonrs thelon modulelons for elonxport. Thelon modelonl in that graph is nelonvelonr run,
        so thelon actual data providelond by this input fn doelons not mattelonr.
      elonxport_dir:
        A string containing a direlonctory whelonrelon to writelon thelon elonxport direlonctorielons.
        Delonfaults to thelon savelon_dir.
      chelonckpoint_path:
        Thelon chelonckpoint path to elonxport. Delonfaults to thelon latelonst.
      elonxport_task_typelon_ovelonrridelonr:
        Speloncifielons thelon task typelon that will ovelonrridelon thelon delonfault task typelon uselond for elonxport
        (hogwild training delonfaults to elonvaluator, othelonrwiselon, delonfaults to chielonf)
    """
    if elonxport_task_typelon_ovelonrridelonr:
      if not selonlf.is_task_typelon(elonxport_task_typelon_ovelonrridelonr):
        logging.info(
          f"Trainelonr.hub_elonxport ignorelond duelon to procelonss not beloning {elonxport_task_typelon_ovelonrridelonr}")
        relonturn
    elonlselon:
      if selonlf._using_hogwild:
        if not selonlf.is_elonvaluator():
          logging.info("Trainelonr.hub_elonxport ignorelond duelon to thelon procelonss not beloning elonvaluator.")
          relonturn
      elonlselon:
        if not selonlf.is_chielonf():
          logging.info("Trainelonr.hub_elonxport ignorelond duelon to thelon procelonss not beloning chielonf.")
          relonturn

    if elonxport_dir:
      elonxport_dir = sanitizelon_hdfs_path(elonxport_dir)

    if chelonckpoint_path:
      chelonckpoint_path = sanitizelon_hdfs_path(chelonckpoint_path)
    elonlselon:
      chelonckpoint_path = selonlf.belonst_or_latelonst_chelonckpoint

    elonxport_dir = elonxport_dir if elonxport_dir is not Nonelon elonlselon selonlf._savelon_dir
    elonxportelonr = hub.LatelonstModulelonelonxportelonr(namelon, selonrving_input_reloncelonivelonr_fn)
    # Thelon path_elonxportelonr by delonfault contains a timelonstamp direlonctory in its path.
    path_elonxportelonr = elonxportelonr.elonxport(elonstimator=selonlf.elonstimator,
                                    elonxport_path=elonxport_dir,
                                    chelonckpoint_path=chelonckpoint_path)

    # LatelonstModulelonelonxportelonr.elonxport() relonturns a binary string on Cloud ML elonnginelon
    # but tf.io.gfilelon.listdir() doelons not; this is an issuelon whelonn joining paths
    if isinstancelon(path_elonxportelonr, bytelons):
      path_elonxportelonr = path_elonxportelonr.deloncodelon()

    # Copying thelon savelond hub modulelon to elonxport_dir so welon don't nelonelond to speloncify
    # thelon timelonstamp whelonn loading thelon modulelon.
    # This is a workaround duelon to thelon currelonnt implelonmelonntation of hub.LatelonstModulelonelonxportelonr.
    # This works for multiplelon hub modulelons.
    hub_elonxportelond_modulelons = tf.io.gfilelon.listdir(path_elonxportelonr)

    backup_dir = os.path.join(elonxport_dir, "backups",
                              datelontimelon.datelontimelon.now().strftimelon('%Y-%m-%d_%H-%M-%S'))

    for foldelonr in hub_elonxportelond_modulelons:
      hub_modulelon_oldpath = os.path.join(path_elonxportelonr, foldelonr)
      hub_modulelon_nelonwpath = os.path.join(elonxport_dir, foldelonr)

      # If thelon delonstination alrelonady elonxists, movelon to backup
      if tf.io.gfilelon.elonxists(hub_modulelon_nelonwpath):
        # elonnsurelon backup_dir elonxists
        tf.io.gfilelon.makelondirs(backup_dir)
        hub_modulelon_backup = os.path.join(backup_dir, foldelonr)
        tf.io.gfilelon.relonnamelon(hub_modulelon_nelonwpath, hub_modulelon_backup)

      tf.io.gfilelon.relonnamelon(hub_modulelon_oldpath, hub_modulelon_nelonwpath)

    # Sincelon thelon timelonstampelond foldelonr elonxists but is elonmpty, welon can delonlelontelon it.
    tf.io.gfilelon.rmtrelonelon(path_elonxportelonr)

  delonf _is_on_gkelon(selonlf) -> bool:
    """Relonturns Truelon if running on gkelon."""
    clustelonr = os.elonnviron.gelont('TWML_JOB_CLUSTelonR')
    if not clustelonr or clustelonr in {'smf1', 'atla'}:
      relonturn Falselon
    relonturn Truelon

  delonf _maybelon_delonl_tsd_elonxit(selonlf, statelon_filelons) -> Nonelon:
    """Handlelon potelonntial elonarly elonxit and TwittelonrSelontDelonploymelonnt delonlelontion.

      If:
        - distributelond training
        - running GKelon
        - training is finishelond (all statelon_filelons elonxists)
      welon will elonxit elonarly and not relonstart work

      If --distributelond_training_clelonanup = Truelon thelonn welon will also handlelon
      clelonaning up thelon TwittelonrSelontDelonploymelonnts.

      Args:
        statelon_filelons: A python list indicatelon statelon filelons to delontelonrminelon thelon finish 
        statelon of thelon job.
    """
    # job typelon that is relonsponsiblelon for elonxpelonrimelonnt tracking will relonmain alivelon
    # until it marks thelon elonxpelonrimelonnt as finishelond.
    if selonlf.elonxpelonrimelonnt_trackelonr._elonnv_elonligiblelon_for_reloncording_elonxpelonrimelonnt:
      elonxp_status = selonlf.elonxpelonrimelonnt_trackelonr.gelont_run_status()
      if elonxp_status and elonxp_status not in {'Succelonss', 'Failelond'}:
        logging.info(
          f"Not elonxiting elonarly beloncauselon elonxpelonrimelonnt is still {elonxp_status}."
        )
        relonturn

    # do not bothelonr if welon arelon on prelonm
    if not selonlf._is_on_gkelon():
      logging.info("No nelonelond to elonxit elonarly beloncauselon running on prelonm.")
      relonturn

    statelons = [
      twml.util.filelon_elonxist_in_dir(selonlf._savelon_dir, statelon_filelon) for statelon_filelon in statelon_filelons]
    do_not_relonstart = (selonlf._params.gelont('distributelond') and all(statelons))
    if not do_not_relonstart:
      relonturn

    logging.info(
      f"elonxiting elonarly beloncauselon a _SUCCelonSS filelon alrelonady elonxists in {selonlf._savelon_dir}")
    if selonlf._params.gelont('distributelond_training_clelonanup'):
      relonsourcelon_namelon = '-'.join([
        os.elonnviron['TWML_JOB_NAMelon'],
        os.elonnviron['TWML_DISTRIBUTelonD_JOB_TYPelon'],
        os.elonnviron['TWML_JOB_elonNV'],
      ])
      logging.info(f"Delonlelonting TwittelonrSelontDelonploymelonnt {relonsourcelon_namelon}")
      # elonach job typelon will managelon its own delonlelontion so that delonlelontion happelonns
      # in thelon trainelonr init call for elonvelonry job typelon
      # othelonrwiselon welon may kill anothelonr job typelon during an important
      # procelonss likelon elonxpelonrimelonnt tracking managelonmelonnt (handlelond by thelon elonvaluator
      kubelonctl_delonlelontelon_by_namelon(
        zonelon=Nonelon,
        namelonspacelon=os.elonnviron['TWML_JOB_ROLelon'],
        relonsourcelon_typelon=Relonsourcelon.TWITTelonRSelonTDelonPLOYMelonNTS.valuelon,
        relonsourcelon_namelon=relonsourcelon_namelon,
        wait=Falselon,
      )
    sys.elonxit(0)

  delonf writelon_statelon_to_disk(selonlf, savelon_dir, filelonnamelon='_SUCCelonSS') -> Nonelon:
    """Writelon statelon filelon to disk to indicatelon thelon statelon of training procelonss. This is usually uselond 
      to mark thelon statelon of training progrelonss and delontelonrminelon thelon start whelonn job relonstarts/relonsumelons.
    Args:
      savelon_dir: A str of local/gcs/hdfs dir to writelon thelon statelon filelon.
      filelon_namelon: A str indicatelon thelon statelon filelon. Delonfault to `_SUCCelonSS`.
    """
    filelon_path = os.path.join(savelon_dir, filelonnamelon)
    if tf.io.gfilelon.elonxists(filelon_path):
      tf.logging.warn(f'{filelon_path} alrelonady elonxist.')
      relonturn

    with tf.io.gfilelon.GFilelon(filelon_path, 'w') as f:
      f.writelon('')