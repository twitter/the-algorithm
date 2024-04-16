package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.product_m xer.shared_l brary.thr ft_cl ent.F nagleThr ftCl entBu lder
 mport com.tw ter.product_m xer.shared_l brary.thr ft_cl ent.Non dempotent
 mport com.tw ter.search.blender.thr ftscala.BlenderServ ce
 mport javax. nject.S ngleton

object BlenderCl entModule extends Tw terModule {

  @S ngleton
  @Prov des
  def prov desBlenderCl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): BlenderServ ce. thodPerEndpo nt = {
    val cl ent d = serv ce dent f er.env ron nt.toLo rCase match {
      case "prod" => Cl ent d("")
      case _ => Cl ent d("")
    }

    F nagleThr ftCl entBu lder.bu ldF nagle thodPerEndpo nt[
      BlenderServ ce.Serv cePerEndpo nt,
      BlenderServ ce. thodPerEndpo nt
    ](
      serv ce dent f er = serv ce dent f er,
      cl ent d = cl ent d,
      dest = "/s/blender-un versal/blender",
      label = "blender",
      statsRece ver = statsRece ver,
       dempotency = Non dempotent,
      t  outPerRequest = 1000.m ll seconds,
      t  outTotal = 1000.m ll seconds,
    )
  }
}
