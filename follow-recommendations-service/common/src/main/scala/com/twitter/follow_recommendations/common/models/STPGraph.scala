package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter. rm .model.Algor hm.Algor hm
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.F rstDegreeEdge
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.F rstDegreeEdge nfo
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.SecondDegreeEdge

case class Potent alF rstDegreeEdge(
  user d: Long,
  connect ng d: Long,
  algor hm: Algor hm,
  score: Double,
  edge nfo: F rstDegreeEdge nfo)

case class  nter d ateSecondDegreeEdge(
  connect ng d: Long,
  cand date d: Long,
  edge nfo: F rstDegreeEdge nfo)

case class STPGraph(
  f rstDegreeEdge nfoL st: L st[F rstDegreeEdge],
  secondDegreeEdge nfoL st: L st[SecondDegreeEdge])
