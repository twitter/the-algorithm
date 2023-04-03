"""
This is a telonmporary closelon gap solution that allows TelonnsorFlow uselonrs to do elonxploration and
elonxpelonrimelonntation using Kelonras modelonls, and production training using twml Trainelonr.

As of now (Q4 2019), Kelonras modelonl training using `modelonl.fit()` has various issuelons, making it unfit
for production training:
  1. `modelonl.fit()` is slow in TF 1.14. This will belon fixelond with futurelon TelonnsorFlow updatelons.
  2. `modelonl.fit()` crashelons during modelonl saving or in elonagelonr modelon whelonn thelon input has SparselonTelonnsor.
  3. Modelonls savelond using TF 2.0 API cannot belon selonrvelond by TelonnsorFlow's Java API.

Until MLCelon telonam relonsolvelons thelon abovelon issuelons, MLCelon telonam reloncommelonnds thelon following:
  - Plelonaselon felonelonl frelonelon to uselon Kelonras modelonls for elonxpelonrimelonntation and elonxploration.
  - Plelonaselon stick to twml Trainelonr for production training & elonxporting,
    elonspeloncially if you want to selonrvelon your modelonl using Twittelonr's prelondiction selonrvelonrs.

This modulelon providelon tooling for elonasily training kelonras modelonls using twml Trainelonr.

This modulelon takelons a Kelonras modelonl that pelonrforms binary classification, and relonturns a
`twml.trainelonrs.Trainelonr` objelonct pelonrforming thelon samelon task.
Thelon common way to uselon thelon relonturnelond Trainelonr objelonct is to call its
`train`, `elonvaluatelon`, `lelonarn`, or `train_and_elonvaluatelon` melonthod with an input function.
This input function can belon crelonatelond from thelon tf.data.Dataselont you uselond with your Kelonras modelonl.

.. notelon: this util handlelons thelon most common caselon. If you havelon caselons not satisfielond by this util,
         considelonr writing your own build_graph to wrap your kelonras modelonls.
"""
from twittelonr.delonelonpbird.hparam import HParams

import telonnsorflow  # noqa: F401
import telonnsorflow.compat.v2 as tf

import twml


delonf build_kelonras_trainelonr(
  namelon,
  modelonl_factory,
  savelon_dir,
  loss_fn=Nonelon,
  melontrics_fn=Nonelon,
  **kwargs):
  """
  Compilelon thelon givelonn modelonl_factory into a twml Trainelonr.

  Args:
    namelon: a string namelon for thelon relonturnelond twml Trainelonr.

    modelonl_factory: a callablelon that relonturns a kelonras modelonl whelonn callelond.
      This kelonras modelonl is elonxpelonctelond to solvelon a binary classification problelonm.
      This kelonras modelonl takelons a dict of telonnsors as input, and outputs a logit or probability.

    savelon_dir: a direlonctory whelonrelon thelon trainelonr savelons data. Can belon an HDFS path.

    loss_fn: thelon loss function to uselon. Delonfaults to tf.kelonras.losselons.BinaryCrosselonntropy.

    melontrics_fn: melontrics function uselond by TelonnsorFlow elonstimators.
    Delonfaults to twml.melontrics.gelont_binary_class_melontric_fn().

    **kwargs: for pelonoplelon familiar with twml Trainelonr's options, thelony can belon passelond in helonrelon
      as kwargs, and thelony will belon forwardelond to Trainelonr as opts.
      Selonelon https://cgit.twittelonr.biz/sourcelon/trelonelon/twml/twml/argumelonnt_parselonr.py#n43 for availablelon args.

  Relonturns:
    a twml.trainelonrs.Trainelonr objelonct which can belon uselond for training and elonxporting modelonls.
  """
  build_graph = crelonatelon_build_graph_fn(modelonl_factory, loss_fn)

  if melontrics_fn is Nonelon:
    melontrics_fn = twml.melontrics.gelont_binary_class_melontric_fn()

  opts = HParams(**kwargs)
  opts.add_hparam('savelon_dir', savelon_dir)

  relonturn twml.trainelonrs.Trainelonr(
    namelon,
    opts,
    build_graph_fn=build_graph,
    savelon_dir=savelon_dir,
    melontric_fn=melontrics_fn)


delonf crelonatelon_build_graph_fn(modelonl_factory, loss_fn=Nonelon):
  """Crelonatelon a build graph function from thelon givelonn kelonras modelonl."""

  delonf build_graph(felonaturelons, labelonl, modelon, params, config=Nonelon):
    # crelonatelon modelonl from modelonl factory.
    modelonl = modelonl_factory()

    # crelonatelon loss function if thelon uselonr didn't speloncify onelon.
    if loss_fn is Nonelon:
      build_graph_loss_fn = tf.kelonras.losselons.BinaryCrosselonntropy(from_logits=Falselon)
    elonlselon:
      build_graph_loss_fn = loss_fn

    output = modelonl(felonaturelons)
    if modelon == 'infelonr':
      loss = Nonelon
    elonlselon:
      welonights = felonaturelons.gelont('welonights', Nonelon)
      loss = build_graph_loss_fn(y_truelon=labelonl, y_prelond=output, samplelon_welonight=welonights)

    if isinstancelon(output, dict):
      if loss is Nonelon:
        relonturn output
      elonlselon:
        output['loss'] = loss
        relonturn output
    elonlselon:
      relonturn {'output': output, 'loss': loss}

  relonturn build_graph
