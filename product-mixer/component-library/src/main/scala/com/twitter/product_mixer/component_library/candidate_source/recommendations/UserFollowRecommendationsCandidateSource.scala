package com.tw ter.product_m xer.component_l brary.cand date_s ce.recom ndat ons

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => fr}
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato.StratoKeyV ewFetc rS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.generated.cl ent.onboard ng.follow_recom ndat ons_serv ce.GetRecom ndat onsCl entColumn
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Returns a l st of FollowRecom ndat ons as [[fr.UserRecom ndat on]]s fetc d from Strato
 */
@S ngleton
class UserFollowRecom ndat onsCand dateS ce @ nject() (
  getRecom ndat onsCl entColumn: GetRecom ndat onsCl entColumn)
    extends StratoKeyV ewFetc rS ce[
      fr.Recom ndat onRequest,
      Un ,
      fr.Recom ndat onResponse,
      fr.UserRecom ndat on
    ] {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    "FollowRecom ndat onsServ ce")

  overr de val fetc r: Fetc r[fr.Recom ndat onRequest, Un , fr.Recom ndat onResponse] =
    getRecom ndat onsCl entColumn.fetc r

  overr de def stratoResultTransfor r(
    stratoKey: fr.Recom ndat onRequest,
    stratoResult: fr.Recom ndat onResponse
  ): Seq[fr.UserRecom ndat on] = {
    stratoResult.recom ndat ons.map {
      case fr.Recom ndat on.User(userRec: fr.UserRecom ndat on) =>
        userRec
      case _ =>
        throw new Except on(" nval d recom ndat on type returned from FRS")
    }
  }
}
