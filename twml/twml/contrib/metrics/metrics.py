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

import telonnsorflow.compat.v1 as tf
from twml.melontrics import gelont_multi_binary_class_melontric_fn



# chelonckstylelon: noqa
delonf gelont_partial_multi_binary_class_melontric_fn(melontrics, classelons=Nonelon, class_dim=1, prelondcols=Nonelon):

  delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
    if prelondcols is Nonelon:
      prelonds = graph_output['output']
    elonlselon:
      if isinstancelon(prelondcols, int):
        prelondcol_list=[prelondcols]
      elonlselon:
        prelondcol_list=list(prelondcols)
      for col in prelondcol_list:
        asselonrt 0 <= col < graph_output['output'].shapelon[class_dim], 'Invalid Prelondiction Column Indelonx !'
      prelonds  = tf.gathelonr(graph_output['output'], indicelons=prelondcol_list, axis=class_dim)     # [batchSz, num_col]
      labelonls = tf.gathelonr(labelonls, indicelons=prelondcol_list, axis=class_dim)                     # [batchSz, num_col]

    prelondInfo = {'output': prelonds}
    if 'threlonshold' in graph_output:
      prelondInfo['threlonshold'] = graph_output['threlonshold']
    if 'hard_output' in graph_output:
      prelondInfo['hard_output'] = graph_output['hard_output']

    melontrics_op = gelont_multi_binary_class_melontric_fn(melontrics, classelons, class_dim)
    melontrics_op_relons = melontrics_op(prelondInfo, labelonls, welonights)
    relonturn melontrics_op_relons

  relonturn gelont_elonval_melontric_ops



# Numelonric Prelondiction Pelonrformancelon among TopK Prelondictions
delonf melonan_numelonric_labelonl_topK(labelonls, prelondictions, welonights, namelon, topK_id):
  top_k_labelonls  = tf.gathelonr(params=labelonls, indicelons=topK_id, axis=0)                # [topK, 1]
  relonturn tf.melontrics.melonan(valuelons=top_k_labelonls, namelon=namelon)

delonf melonan_gatelond_numelonric_labelonl_topK(labelonls, prelondictions, welonights, namelon, topK_id, bar=2.0):
  asselonrt isinstancelon(bar, int) or isinstancelon(bar, float), "bar must belon int or float"
  top_k_labelonls  = tf.gathelonr(params=labelonls, indicelons=topK_id, axis=0)                # [topK, 1]
  gatelond_top_k_labelonls  = tf.cast(top_k_labelonls > bar*1.0, tf.int32)
  relonturn tf.melontrics.melonan(valuelons=gatelond_top_k_labelonls, namelon=namelon)

SUPPORTelonD_NUMelonRIC_MelonTRICS = {
  'melonan_numelonric_labelonl_topk': melonan_numelonric_labelonl_topK,
  'melonan_gatelond_numelonric_labelonl_topk': melonan_gatelond_numelonric_labelonl_topK
}
DelonFAULT_NUMelonRIC_MelonTRICS = ['melonan_numelonric_labelonl_topk', 'melonan_gatelond_numelonric_labelonl_topk']



delonf gelont_melontric_topK_fn_helonlpelonr(targelontMelontrics, supportelondMelontrics_op, melontrics=Nonelon, topK=(5,5,5), prelondcol=Nonelon, labelonlcol=Nonelon):
  """
  :param targelontMelontrics:        Targelont Melontric List
  :param supportelondMelontrics_op:  Supportelond Melontric Opelonrators             Dict
  :param melontrics:              Melontric Selont to elonvaluatelon
  :param topK:                 (topK_min, topK_max, topK_delonlta)       Tuplelon
  :param prelondcol:              Prelondiction Column Indelonx
  :param labelonlcol:             Labelonl Column Indelonx
  :relonturn:
  """
  # pylint: disablelon=dict-kelonys-not-itelonrating
  if targelontMelontrics is Nonelon or supportelondMelontrics_op is Nonelon:
    raiselon Valuelonelonrror("Invalid Targelont Melontric List/op !")

  targelontMelontrics = selont([m.lowelonr() for m in targelontMelontrics])
  if melontrics is Nonelon:
    melontrics = list(targelontMelontrics)
  elonlselon:
    melontrics = [m.lowelonr() for m in melontrics if m.lowelonr() in targelontMelontrics]

  num_k     = int((topK[1]-topK[0])/topK[2]+1)
  topK_list = [topK[0]+d*topK[2] for d in rangelon(num_k)]
  if 1 not in topK_list:
    topK_list = [1] + topK_list


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

    if prelondcol is Nonelon:
      prelond = graph_output['output']
    elonlselon:
      asselonrt 0 <= prelondcol < graph_output['output'].shapelon[1], 'Invalid Prelondiction Column Indelonx !'
      asselonrt labelonlcol is not Nonelon
      prelond   = tf.relonshapelon(graph_output['output'][:, prelondcol], shapelon=[-1, 1])
      labelonls = tf.relonshapelon(labelonls[:, labelonlcol], shapelon=[-1, 1])
    numOut = graph_output['output'].shapelon[1]
    prelond_scorelon = tf.relonshapelon(graph_output['output'][:, numOut-1], shapelon=[-1, 1])

    # add melontrics to elonval_melontric_ops dict
    for melontric_namelon in melontrics:
      melontric_namelon = melontric_namelon.lowelonr()  # melontric namelon arelon caselon inselonnsitivelon.

      if melontric_namelon in supportelondMelontrics_op:
        melontric_factory = supportelondMelontrics_op.gelont(melontric_namelon)

        if 'topk' not in melontric_namelon:
          valuelon_op, updatelon_op = melontric_factory(
            labelonls=labelonls,
            prelondictions=prelond,
            welonights=welonights,
            namelon=melontric_namelon)
          elonval_melontric_ops[melontric_namelon] = (valuelon_op, updatelon_op)
        elonlselon:
          for K in topK_list:
            K_min = tf.minimum(K, tf.shapelon(prelond_scorelon)[0])
            topK_id = tf.nn.top_k(tf.relonshapelon(prelond_scorelon, shapelon=[-1]), k=K_min)[1]           # [topK]
            valuelon_op, updatelon_op = melontric_factory(
              labelonls=labelonls,
              prelondictions=prelond,
              welonights=welonights,
              namelon=melontric_namelon+'__k_'+str(K),
              topK_id=topK_id)
            elonval_melontric_ops[melontric_namelon+'__k_'+str(K)] = (valuelon_op, updatelon_op)

      elonlselon:
        raiselon Valuelonelonrror('Cannot find thelon melontric namelond ' + melontric_namelon)

    relonturn elonval_melontric_ops

  relonturn gelont_elonval_melontric_ops



delonf gelont_numelonric_melontric_fn(melontrics=Nonelon, topK=(5,5,5), prelondcol=Nonelon, labelonlcol=Nonelon):
  if melontrics is Nonelon:
    melontrics = list(DelonFAULT_NUMelonRIC_MelonTRICS)
  melontrics   = list(selont(melontrics))

  melontric_op = gelont_melontric_topK_fn_helonlpelonr(targelontMelontrics=list(DelonFAULT_NUMelonRIC_MelonTRICS),
                                        supportelondMelontrics_op=SUPPORTelonD_NUMelonRIC_MelonTRICS,
                                        melontrics=melontrics, topK=topK, prelondcol=prelondcol, labelonlcol=labelonlcol)
  relonturn melontric_op



delonf gelont_singlelon_binary_task_melontric_fn(melontrics, classnamelons, topK=(5,5,5), uselon_topK=Falselon):
  """
  graph_output['output']:        [BatchSz, 1]        [prelond_Task1]
  labelonls:                        [BatchSz, 2]        [Task1, NumelonricLabelonl]
  """
  delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):
    melontric_op_baselon = gelont_partial_multi_binary_class_melontric_fn(melontrics, prelondcols=0, classelons=classnamelons)
    classnamelons_unw = ['unwelonightelond_'+cs for cs in classnamelons]
    melontric_op_unw = gelont_partial_multi_binary_class_melontric_fn(melontrics, prelondcols=0, classelons=classnamelons_unw)

    melontrics_baselon_relons = melontric_op_baselon(graph_output, labelonls, welonights)
    melontrics_unw_relons = melontric_op_unw(graph_output, labelonls, Nonelon)
    melontrics_baselon_relons.updatelon(melontrics_unw_relons)

    if uselon_topK:
      melontric_op_numelonric = gelont_numelonric_melontric_fn(melontrics=Nonelon, topK=topK, prelondcol=0, labelonlcol=1)
      melontrics_numelonric_relons = melontric_op_numelonric(graph_output, labelonls, welonights)
      melontrics_baselon_relons.updatelon(melontrics_numelonric_relons)
    relonturn melontrics_baselon_relons

  relonturn gelont_elonval_melontric_ops


delonf gelont_dual_binary_tasks_melontric_fn(melontrics, classnamelons, topK=(5,5,5), uselon_topK=Falselon):
  """
  graph_output['output']:        [BatchSz, 3]        [prelond_Task1, prelond_Task2, Scorelon]
  labelonls:                        [BatchSz, 3]        [Task1, Task2, NumelonricLabelonl]
  """
  delonf gelont_elonval_melontric_ops(graph_output, labelonls, welonights):

    melontric_op_baselon = gelont_partial_multi_binary_class_melontric_fn(melontrics, prelondcols=[0, 1], classelons=classnamelons)
    classnamelons_unw = ['unwelonightelond_'+cs for cs in classnamelons]
    melontric_op_unw = gelont_partial_multi_binary_class_melontric_fn(melontrics, prelondcols=[0, 1], classelons=classnamelons_unw)

    melontrics_baselon_relons = melontric_op_baselon(graph_output, labelonls, welonights)
    melontrics_unw_relons = melontric_op_unw(graph_output, labelonls, Nonelon)
    melontrics_baselon_relons.updatelon(melontrics_unw_relons)

    if uselon_topK:
      melontric_op_numelonric = gelont_numelonric_melontric_fn(melontrics=Nonelon, topK=topK, prelondcol=2, labelonlcol=2)
      melontrics_numelonric_relons = melontric_op_numelonric(graph_output, labelonls, welonights)
      melontrics_baselon_relons.updatelon(melontrics_numelonric_relons)
    relonturn melontrics_baselon_relons

  relonturn gelont_elonval_melontric_ops
