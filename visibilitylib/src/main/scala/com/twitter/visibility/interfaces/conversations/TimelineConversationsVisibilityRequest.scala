package com.tw ter.v s b l y. nterfaces.conversat ons

 mport com.tw ter.g zmoduck.thr ftscala.Label
 mport com.tw ter.g zmoduck.thr ftscala.LabelValue
 mport com.tw ter.servo.repos ory.KeyValueResult
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabel
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType
 mport com.tw ter.v s b l y.models.V e rContext

case class T  l neConversat onsV s b l yRequest(
  conversat on d: Long,
  t et ds: Seq[Long],
  v e rContext: V e rContext,
  m n malSect on ngOnly: Boolean = false,
  prefetc dSafetyLabels: Opt on[KeyValueResult[Long, Map[SafetyLabelType, SafetyLabel]]] = None,
  prefetc dT etAuthorUserLabels: Opt on[KeyValueResult[Long, Map[LabelValue, Label]]] = None,
   nnerC rcleOfFr endsRelat onsh ps: Opt on[KeyValueResult[Long, Boolean]] = None,
  t etParent dMap: Opt on[Map[Long, Opt on[Long]]] = None,
  rootAuthor sVer f ed: Boolean = false,
  t etAuthors: Opt on[KeyValueResult[Long, Long]] = None)
