package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.t  l nem xer.cl ents.feedback.Feedback toryManhattanCl ent
 mport com.tw ter.t  l nem xer.cl ents.feedback.Feedback toryManhattanCl entConf g
 mport com.tw ter.t  l nes.cl ents.manhattan.mhv3.ManhattanCl entBu lder
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

object Feedback toryCl entModule extends Tw terModule {
  pr vate val ProdDataset = "feedback_ tory"
  pr vate val Stag ngDataset = "feedback_ tory_nonprod"
  pr vate f nal val T  out = "mh_feedback_ tory.t  out"

  flag[Durat on](T  out, 150.m ll s, "T  out per request")

  @Prov des
  @S ngleton
  def prov desFeedback toryCl ent(
    @Flag(T  out) t  out: Durat on,
    serv ce d: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ) = {
    val manhattanDataset = serv ce d.env ron nt.toLo rCase match {
      case "prod" => ProdDataset
      case _ => Stag ngDataset
    }

    val conf g = new Feedback toryManhattanCl entConf g {
      val dataset = manhattanDataset
      val  sReadOnly = true
      val serv ce dent f er = serv ce d
      overr de val defaultMaxT  out = t  out
    }

    new Feedback toryManhattanCl ent(
      ManhattanCl entBu lder.bu ldManhattanEndpo nt(conf g, statsRece ver),
      manhattanDataset,
      statsRece ver
    )
  }
}
