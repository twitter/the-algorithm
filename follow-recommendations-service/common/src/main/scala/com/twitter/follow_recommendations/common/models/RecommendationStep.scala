package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}

case class Recom ndat onStep(
  recom ndat ons: Seq[FlowRecom ndat on],
  follo dUser ds: Set[Long]) {

  def toThr ft: t.Recom ndat onStep = t.Recom ndat onStep(
    recom ndat ons = recom ndat ons.map(_.toThr ft),
    follo dUser ds = follo dUser ds
  )

  def toOffl neThr ft: offl ne.Offl neRecom ndat onStep =
    offl ne.Offl neRecom ndat onStep(
      recom ndat ons = recom ndat ons.map(_.toOffl neThr ft),
      follo dUser ds = follo dUser ds)

}

object Recom ndat onStep {

  def fromThr ft(recom ndat onStep: t.Recom ndat onStep): Recom ndat onStep = {
    Recom ndat onStep(
      recom ndat ons = recom ndat onStep.recom ndat ons.map(FlowRecom ndat on.fromThr ft),
      follo dUser ds = recom ndat onStep.follo dUser ds.toSet)
  }

}
