package com.tw ter.v s b l y. nterfaces.push_serv ce

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelMap
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.v s b l y.common.st ch.St ch lpers

object PushServ ceSafetyLabelMapFetc r {
  val Column = "fr gate/mag crecs/t etSafetyLabels"

  def apply(
    cl ent: StratoCl ent,
    statsRece ver: StatsRece ver
  ): Long => St ch[Opt on[SafetyLabelMap]] = {
    val stats = statsRece ver.scope("strato_t et_safety_labels")
    lazy val fetc r = cl ent.fetc r[Long, SafetyLabelMap](Column)
    t et d => St ch lpers.observe(stats)(fetc r.fetch(t et d).map(_.v))
  }
}
