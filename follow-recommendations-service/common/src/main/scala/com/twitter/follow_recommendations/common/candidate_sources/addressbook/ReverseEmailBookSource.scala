package com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook

 mport com.tw ter.cds.contact_consent_state.thr ftscala.PurposeOfProcess ng
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.AddressbookCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models.EdgeType
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models.Record dent f er
 mport com.tw ter.follow_recom ndat ons.common.cl ents.ema l_storage_serv ce.Ema lStorageServ ceCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.ut ls.RescueW hStatsUt ls.rescueOpt onalW hStats
 mport com.tw ter.follow_recom ndat ons.common.ut ls.RescueW hStatsUt ls.rescueW hStats
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.ReverseEma lContactsCl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ReverseEma lBookS ce @ nject() (
  reverseEma lContactsCl entColumn: ReverseEma lContactsCl entColumn,
  essCl ent: Ema lStorageServ ceCl ent,
  addressBookCl ent: AddressbookCl ent,
  statsRece ver: StatsRece ver = NullStatsRece ver)
    extends Cand dateS ce[HasParams w h HasCl entContext, Cand dateUser] {
  overr de val  dent f er: Cand dateS ce dent f er = ReverseEma lBookS ce. dent f er
  pr vate val rescueStats = statsRece ver.scope("ReverseEma lBookS ce")

  /**
   * Generate a l st of cand dates for t  target
   */
  overr de def apply(target: HasParams w h HasCl entContext): St ch[Seq[Cand dateUser]] = {
    val reverseCand datesFromEma l = target.getOpt onalUser d
      .map { user d =>
        val ver f edEma lSt chOpt =
          rescueOpt onalW hStats(
            essCl ent.getVer f edEma l(user d, PurposeOfProcess ng.ContentRecom ndat ons),
            rescueStats,
            "getVer f edEma l")
        ver f edEma lSt chOpt.flatMap { ema lOpt =>
          rescueW hStats(
            addressBookCl ent.getUsers(
              user d = user d,
               dent f ers = ema lOpt
                .map(ema l =>
                  Record dent f er(user d = None, ema l = So (ema l), phoneNumber = None)).toSeq,
              batchS ze = ReverseEma lBookS ce.NumEma lBookEntr es,
              edgeType = ReverseEma lBookS ce.DefaultEdgeType,
              fetc rOpt on =
                 f (target.params(AddressBookParams.ReadFromABV2Only)) None
                else So (reverseEma lContactsCl entColumn.fetc r)
            ),
            rescueStats,
            "AddressBookCl ent"
          )
        }
      }.getOrElse(St ch.N l)

    reverseCand datesFromEma l.map(
      _.take(ReverseEma lBookS ce.NumEma lBookEntr es)
        .map(
          Cand dateUser(_, score = So (Cand dateUser.DefaultCand dateScore))
            .w hCand dateS ce( dent f er))
    )
  }
}

object ReverseEma lBookS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.ReverseEma lBook b s.toStr ng)
  val NumEma lBookEntr es:  nt = 500
  val  sPhone = false
  val DefaultEdgeType: EdgeType = EdgeType.Reverse
}
