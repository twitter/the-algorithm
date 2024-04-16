package com.tw ter.ho _m xer.product.l st_recom nded_users.cand date_s ce

 mport com.tw ter. rm .cand date.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.generated.cl ent.recom ndat ons.s m lar y.S m larUsersByS msOnUserCl entColumn

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class S m lar yBasedUsersCand dateS ce @ nject() (
  s m larUsersByS msOnUserCl entColumn: S m larUsersByS msOnUserCl entColumn)
    extends Cand dateS ce[Seq[Long], t.Cand date] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("S m lar yBasedUsers")

  pr vate val fetc r: Fetc r[Long, Un , t.Cand dates] =
    s m larUsersByS msOnUserCl entColumn.fetc r

  pr vate val MaxCand datesToKeep = 4000

  overr de def apply(request: Seq[Long]): St ch[Seq[t.Cand date]] = {
    St ch
      .collect {
        request.map { user d =>
          fetc r
            .fetch(user d, Un ).map { result =>
              result.v.map(_.cand dates).getOrElse(Seq.empty)
            }.map { cand dates =>
              val sortedCand dates = cand dates.sortBy(-_.score)
              sortedCand dates.take(MaxCand datesToKeep)
            }
        }
      }.map(_.flatten)
  }
}
