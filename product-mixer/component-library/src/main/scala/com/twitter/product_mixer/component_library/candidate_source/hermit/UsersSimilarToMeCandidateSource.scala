package com.tw ter.product_m xer.component_l brary.cand date_s ce. rm 

 mport com.tw ter. rm .thr ftscala.Recom ndat onRequest
 mport com.tw ter. rm .thr ftscala.Recom ndat onResponse
 mport com.tw ter. rm .thr ftscala.RelatedUser
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato.StratoKeyV ewFetc rS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.generated.cl ent.onboard ng. rm Recom ndUsersCl entColumn
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UsersS m larTo Cand dateS ce @ nject() (
  column:  rm Recom ndUsersCl entColumn)
    extends StratoKeyV ewFetc rS ce[
      Long,
      Recom ndat onRequest,
      Recom ndat onResponse,
      RelatedUser
    ] {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("UsersS m larTo ")

  overr de val fetc r: Fetc r[Long, Recom ndat onRequest, Recom ndat onResponse] =
    column.fetc r

  overr de def stratoResultTransfor r(
    stratoKey: Long,
    result: Recom ndat onResponse
  ): Seq[RelatedUser] = result.suggest ons.getOrElse(Seq.empty).f lter(_. d. sDef ned)
}
