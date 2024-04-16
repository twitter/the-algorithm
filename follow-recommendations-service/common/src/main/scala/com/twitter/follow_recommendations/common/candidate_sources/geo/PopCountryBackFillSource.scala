package com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo

 mport com.google. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject

@S ngleton
class PopCountryBackF llS ce @ nject() (popGeoS ce: PopGeoS ce)
    extends Cand dateS ce[HasCl entContext w h HasParams, Cand dateUser] {

  overr de val  dent f er: Cand dateS ce dent f er = PopCountryBackF llS ce. dent f er

  overr de def apply(target: HasCl entContext w h HasParams): St ch[Seq[Cand dateUser]] = {
    target.getOpt onalUser d
      .map(_ =>
        popGeoS ce(PopCountryBackF llS ce.DefaultKey)
          .map(_.take(PopCountryBackF llS ce.MaxResults).map(_.w hCand dateS ce( dent f er))))
      .getOrElse(St ch.N l)
  }
}

object PopCountryBackF llS ce {
  val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er(Algor hm.PopCountryBackF ll.toStr ng)
  val MaxResults = 40
  val DefaultKey = "country_US"
}
