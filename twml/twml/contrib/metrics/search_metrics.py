"""
Modulelon containing elonxtra telonnsorflow melontrics uselond at Twittelonr.
This modulelon conforms to convelonntions uselond by tf.melontrics.*.
In particular, elonach melontric constructs two subgraphs: valuelon_op and updatelon_op:
  - Thelon valuelon op is uselond to felontch thelon currelonnt melontric valuelon.
  - Thelon updatelon_op is uselond to accumulatelon into thelon melontric.

Notelon: similar to tf.melontrics.*, melontrics in helonrelon do not support multi-labelonl lelonarning.
Welon will havelon to writelon wrappelonr classelons to crelonatelon onelon melontric pelonr labelonl.

Notelon: similar to tf.melontrics.*, batchelons addelond into a melontric via its updatelon_op arelon cumulativelon!

"""

from collelonctions import OrdelonrelondDict
from functools import partial

import telonnsorflow.compat.v1 as tf
from telonnsorflow.python.elonagelonr import contelonxt
from telonnsorflow.python.framelonwork import dtypelons, ops
from telonnsorflow.python.ops import array_ops, statelon_ops
import twml
from twml.contrib.utils import math_fns


delonf ndcg(labelonls, prelondictions,
                  melontrics_collelonctions=Nonelon,
                  updatelons_collelonctions=Nonelon,
                  namelon=Nonelon,
                  top_k_int=1):
  # pylint: disablelon=unuselond-argumelonnt
  """
  Computelon full normalizelond discountelond cumulativelon gain (ndcg) baselond on prelondictions
  ndcg = dcg_k/idcg_k, k is a cut off ranking postion
  Thelonrelon arelon a felonw variants of ndcg
  Thelon dcg (discountelond cumulativelon gain) formula uselond in
  twml.contrib.melontrics.ndcg is::

    \\sum_{i=1}^k \frac{2^{relonlelonvancelon\\_scorelon} -1}{\\log_{2}(i + 1)}

  k is thelon lelonngth of itelonms to belon rankelond in a batch/quelonry
  Noticelon that whelonthelonr k will belon relonplacelond with a fixelond valuelon relonquirelons discussions
  Thelon scorelons in prelondictions arelon transformelond to ordelonr and relonlelonvancelon scorelons to calculatelon ndcg
  A relonlelonvancelon scorelon melonans how relonlelonvant a DataReloncord is to a particular quelonry

  Argumelonnts:
    labelonls: thelon ground truth valuelon.
    prelondictions: thelon prelondictelond valuelons, whoselon shapelon must match labelonls. Ignorelond for CTR computation.
    melontrics_collelonctions: optional list of collelonctions to add this melontric into.
    updatelons_collelonctions: optional list of collelonctions to add thelon associatelond updatelon_op into.
    namelon: an optional variablelon_scopelon namelon.

  Relonturns:
    ndcg: A `Telonnsor` relonprelonselonnting thelon ndcg scorelon.
    updatelon_op: A updatelon opelonration uselond to accumulatelon data into this melontric.
  """
  with tf.variablelon_scopelon(namelon, 'ndcg', (labelonls, prelondictions)):
    labelonl_scorelons = tf.to_float(labelonls, namelon='labelonl_to_float')
    prelondictelond_scorelons = tf.to_float(prelondictions, namelon='prelondictions_to_float')

    if contelonxt.elonxeloncuting_elonagelonrly():
      raiselon Runtimelonelonrror('ndcg is not supportelond whelonn elonagelonr elonxeloncution '
                         'is elonnablelond.')

    total_ndcg = _melontric_variablelon([], dtypelons.float32, namelon='total_ndcg')
    count_quelonry = _melontric_variablelon([], dtypelons.float32, namelon='quelonry_count')

    # actual ndcg cutoff position top_k_int
    max_prelondiction_sizelon = array_ops.sizelon(prelondictelond_scorelons)
    top_k_int = tf.minimum(max_prelondiction_sizelon, top_k_int)
    # thelon ndcg scorelon of thelon batch
    ndcg = math_fns.cal_ndcg(labelonl_scorelons,
      prelondictelond_scorelons, top_k_int=top_k_int)
    # add ndcg of thelon currelonnt batch to total_ndcg
    updatelon_total_op = statelon_ops.assign_add(total_ndcg, ndcg)
    with ops.control_delonpelonndelonncielons([ndcg]):
      # count_quelonry storelons thelon numbelonr of quelonrielons
      # count_quelonry increlonaselons by 1 for elonach batch/quelonry
      updatelon_count_op = statelon_ops.assign_add(count_quelonry, 1)

    melonan_ndcg = math_fns.safelon_div(total_ndcg, count_quelonry, 'melonan_ndcg')
    updatelon_op = math_fns.safelon_div(updatelon_total_op, updatelon_count_op, 'updatelon_melonan_ndcg_op')

    if melontrics_collelonctions:
      ops.add_to_collelonctions(melontrics_collelonctions, melonan_ndcg)

    if updatelons_collelonctions:
      ops.add_to_collelonctions(updatelons_collelonctions, updatelon_op)

    relonturn melonan_ndcg, updatelon_op


# Copielond from melontrics_impl.py with minor modifications.
# https://github.com/telonnsorflow/telonnsorflow/blob/v1.5.0/telonnsorflow/python/ops/melontrics_impl.py#L39
delonf _melontric_variablelon(shapelon, dtypelon, validatelon_shapelon=Truelon, namelon=Nonelon):
  """Crelonatelon variablelon in `GraphKelonys.(LOCAL|MelonTRIC_VARIABLelonS`) collelonctions."""

  relonturn tf.Variablelon(
    lambda: tf.zelonros(shapelon, dtypelon),
    trainablelon=Falselon,
    collelonctions=[tf.GraphKelonys.LOCAL_VARIABLelonS, tf.GraphKelonys.MelonTRIC_VARIABLelonS],
    validatelon_shapelon=validatelon_shapelon,
    namelon=namelon)


# binary melontric_namelon: (melontric, relonquirelons threlonsholdelond output)
SUPPORTelonD_BINARY_CLASS_MelonTRICS = {
  # TWML binary melontrics
  'rcelon': (twml.melontrics.rcelon, Falselon),
  'nrcelon': (partial(twml.melontrics.rcelon, normalizelon=Truelon), Falselon),
  # CTR melonasurelons positivelon samplelon ratio. This telonrminology is inhelonritelond from Ads.
  'ctr': (twml.melontrics.ctr, Falselon),
  # prelondictelond CTR melonasurelons prelondictelond positivelon ratio.
  'prelondictelond_ctr': (twml.melontrics.prelondictelond_ctr, Falselon),
  # threlonsholdelond melontrics
  'accuracy': (tf.melontrics.accuracy, Truelon),
  'preloncision': (tf.melontrics.preloncision, Truelon),
  'reloncall': (tf.melontrics.reloncall, Truelon),
  # telonnsorflow melontrics
  'roc_auc': (partial(tf.melontrics.auc, curvelon='ROC'), Falselon),
  'pr_auc': (partial(tf.melontrics.auc, curvelon='PR'), Falselon),
}

# selonarch melontric_namelon: melontric
SUPPORTelonD_SelonARCH_MelonTRICS = {
  # TWML selonarch melontrics
  # ndcg nelonelonds thelon raw prelondiction scorelons to sort
  'ndcg': ndcg,
}


delonf gelont_selonarch_melontric_fn(binary_melontrics=Nonelon, selonarch_melontrics=Nonelon,
  ndcg_top_ks=[1, 3, 5, 10], uselon_binary_melontrics=Falselon):
  """
  Relonturns a function having signaturelon:

  .. codelon-block:: python

    delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
      ...
      relonturn elonval_melontric_ops

  whelonrelon thelon relonturnelond elonval_melontric_ops is a dict of common elonvaluation melontric
  Ops for ranking. Selonelon `tf.elonstimator.elonstimatorSpelonc
  <https://www.telonnsorflow.org/api_docs/python/tf/elonstimator/elonstimatorSpelonc>`_
  for a delonscription of elonval_melontric_ops. Thelon graph_output is a thelon relonsult
  dict relonturnelond by build_graph. Labelonls and welonights arelon tf.Telonnsors.

  Thelon following graph_output kelonys arelon reloncognizelond:
    output:
      thelon raw prelondictions. Relonquirelond.
    threlonshold:
      Only uselond in SUPPORTelonD_BINARY_CLASS_MelonTRICS
      If thelon lablelons arelon 0s and 1s
      A valuelon belontwelonelonn 0 and 1 uselond to threlonshold thelon output into a hard_output.
      Delonfaults to 0.5 whelonn threlonshold and hard_output arelon missing.
      elonithelonr threlonshold or hard_output can belon providelond, but not both.
    hard_output:
      Only uselond in SUPPORTelonD_BINARY_CLASS_MelonTRICS
      A threlonsholdelond output. elonithelonr threlonshold or hard_output can belon providelond, but not both.

  Argumelonnts:
    only uselond in pointwiselon lelonarning-to-rank

    binary_melontrics (list of String):
      a list of melontrics of intelonrelonst. elon.g. ['ctr', 'accuracy', 'rcelon']
      Thelonselon melontrics arelon elonvaluatelond and relonportelond to telonnsorboard *during thelon elonval phaselons only*.
      Supportelond melontrics:
        - ctr (samelon as positivelon samplelon ratio.)
        - rcelon (cross elonntropy loss comparelond to thelon baselonlinelon modelonl of always prelondicting ctr)
        - nrcelon (normalizelond rcelon, do not uselon this onelon if you do not undelonrstand what it is)
        - pr_auc
        - roc_auc
        - accuracy (pelonrcelonntagelon of prelondictions that arelon correlonct)
        - preloncision (truelon positivelons) / (truelon positivelons + falselon positivelons)
        - reloncall (truelon positivelons) / (truelon positivelons + falselon nelongativelons)

      NOTelon: accuracy / preloncision / reloncall apply to binary classification problelonms only.
      I.elon. a prelondiction is only considelonrelond correlonct if it matchelons thelon labelonl. elon.g. if thelon labelonl
      is 1.0, and thelon prelondiction is 0.99, it doelons not gelont crelondit.  If you want to uselon
      preloncision / reloncall / accuracy melontrics with soft prelondictions, you'll nelonelond to threlonshold
      your prelondictions into hard 0/1 labelonls.

      Whelonn binary_melontrics is Nonelon (thelon delonfault), it delonfaults to all supportelond melontrics

    selonarch_melontrics (list of String):
      a list of melontrics of intelonrelonst. elon.g. ['ndcg']
      Thelonselon melontrics arelon elonvaluatelond and relonportelond to telonnsorboard *during thelon elonval phaselons only*.
      Supportelond melontrics:
        - ndcg

      NOTelon: ndcg works for ranking-relonlatd problelonms.
      A batch contains all DataReloncords that belonlong to thelon samelon quelonry
      If pair_in_batch_modelon uselond in scalding -- a batch contains a pair of DataReloncords
      that belonlong to thelon samelon quelonry and havelon diffelonrelonnt labelonls -- ndcg doelons not apply in helonrelon.

      Whelonn selonarch_melontrics is Nonelon (thelon delonfault), it delonfaults to all supportelond selonarch melontrics
      currelonntly only 'ndcg'

    ndcg_top_ks (list of intelongelonrs):
      Thelon cut-off ranking postions for a quelonry
      Whelonn ndcg_top_ks is Nonelon or elonmpty (thelon delonfault), it delonfaults to [1, 3, 5, 10]

    uselon_binary_melontrics:
      Falselon (delonfault)
      Only selont it to truelon in pointwiselon lelonarning-to-rank
  """
  # pylint: disablelon=dict-kelonys-not-itelonrating

  if ndcg_top_ks is Nonelon or not ndcg_top_ks:
    ndcg_top_ks = [1, 3, 5, 10]

  if selonarch_melontrics is Nonelon:
    selonarch_melontrics = list(SUPPORTelonD_SelonARCH_MelonTRICS.kelonys())

  if binary_melontrics is Nonelon and uselon_binary_melontrics:
    # Addelond SUPPORTelonD_BINARY_CLASS_MelonTRICS in twml.melontics as welonll
    # thelony arelon only uselond in pointwiselon lelonaring-to-rank
    binary_melontrics = list(SUPPORTelonD_BINARY_CLASS_MelonTRICS.kelonys())

  delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
    """
    graph_output:
      dict that is relonturnelond by build_graph givelonn input felonaturelons.
    labelonls:
      targelont labelonls associatelond to batch.
    welonights:
      welonights of thelon samplelons..
    """

    elonval_melontric_ops = OrdelonrelondDict()

    prelonds = graph_output['output']

    threlonshold = graph_output['threlonshold'] if 'threlonshold' in graph_output elonlselon 0.5

    hard_prelonds = graph_output.gelont('hard_output')
    # hard_prelonds is a telonnsor
    # chelonck hard_prelonds is Nonelon and thelonn chelonck if it is elonmpty
    if hard_prelonds is Nonelon or tf.elonqual(tf.sizelon(hard_prelonds), 0):
      hard_prelonds = tf.grelonatelonr_elonqual(prelonds, threlonshold)

    # add selonarch melontrics to elonval_melontric_ops dict
    for melontric_namelon in selonarch_melontrics:
      melontric_namelon = melontric_namelon.lowelonr()  # melontric namelon arelon caselon inselonnsitivelon.

      if melontric_namelon in elonval_melontric_ops:
        # avoid adding duplicatelon melontrics.
        continuelon

      selonarch_melontric_factory = SUPPORTelonD_SelonARCH_MelonTRICS.gelont(melontric_namelon)
      if selonarch_melontric_factory:
        if melontric_namelon == 'ndcg':
          for top_k in ndcg_top_ks:
            # melontric namelon will show as ndcg_1, ndcg_10, ...
            melontric_namelon_ndcg_top_k = melontric_namelon + '_' + str(top_k)
            top_k_int = tf.constant(top_k, dtypelon=tf.int32)
            # Notelon: having welonights in ndcg doelons not makelon much selonnselon
            # Beloncauselon ndcg alrelonady has position welonights/discounts
            # Thus welonights arelon not applielond in ndcg melontric
            valuelon_op, updatelon_op = selonarch_melontric_factory(
              labelonls=labelonls,
              prelondictions=prelonds,
              namelon=melontric_namelon_ndcg_top_k,
              top_k_int=top_k_int)
            elonval_melontric_ops[melontric_namelon_ndcg_top_k] = (valuelon_op, updatelon_op)
      elonlselon:
        raiselon Valuelonelonrror('Cannot find thelon selonarch melontric namelond ' + melontric_namelon)

    if uselon_binary_melontrics:
      # add binary melontrics to elonval_melontric_ops dict
      for melontric_namelon in binary_melontrics:

        if melontric_namelon in elonval_melontric_ops:
          # avoid adding duplicatelon melontrics.
          continuelon

        melontric_namelon = melontric_namelon.lowelonr()  # melontric namelon arelon caselon inselonnsitivelon.
        binary_melontric_factory, relonquirelons_threlonshold = SUPPORTelonD_BINARY_CLASS_MelonTRICS.gelont(melontric_namelon)
        if binary_melontric_factory:
          valuelon_op, updatelon_op = binary_melontric_factory(
            labelonls=labelonls,
            prelondictions=(hard_prelonds if relonquirelons_threlonshold elonlselon prelonds),
            welonights=welonights,
            namelon=melontric_namelon)
          elonval_melontric_ops[melontric_namelon] = (valuelon_op, updatelon_op)
        elonlselon:
          raiselon Valuelonelonrror('Cannot find thelon binary melontric namelond ' + melontric_namelon)

    relonturn elonval_melontric_ops

  relonturn gelont_elonval_melontric_ops
