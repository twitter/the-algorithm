package com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.AddressBookParams.ReadFromABV2Only
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.AddressbookCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models.EdgeType
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models.Record dent f er
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.ut ls.RescueW hStatsUt ls.rescueW hStats
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.ForwardPhoneContactsCl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ForwardPhoneBookS ce @ nject() (
  forwardPhoneContactsCl entColumn: ForwardPhoneContactsCl entColumn,
  addressBookCl ent: AddressbookCl ent,
  statsRece ver: StatsRece ver = NullStatsRece ver)
    extends Cand dateS ce[HasParams w h HasCl entContext, Cand dateUser] {

  overr de val  dent f er: Cand dateS ce dent f er =
    ForwardPhoneBookS ce. dent f er
  pr vate val stats: StatsRece ver = statsRece ver.scope(t .getClass.getS mpleNa )

  /**
   * Generate a l st of cand dates for t  target
   */
  overr de def apply(target: HasParams w h HasCl entContext): St ch[Seq[Cand dateUser]] = {
    val cand dateUsers: St ch[Seq[Long]] = target.getOpt onalUser d
      .map { user d =>
        rescueW hStats(
          addressBookCl ent.getUsers(
            user d,
             dent f ers =
              Seq(Record dent f er(user d = So (user d), ema l = None, phoneNumber = None)),
            batchS ze = AddressbookCl ent.AddressBook2BatchS ze,
            edgeType = ForwardPhoneBookS ce.DefaultEdgeType,
            fetc rOpt on =
               f (target.params.apply(ReadFromABV2Only)) None
              else So (forwardPhoneContactsCl entColumn.fetc r),
            queryOpt on = AddressbookCl ent
              .createQueryOpt on(
                edgeType = ForwardPhoneBookS ce.DefaultEdgeType,
                 sPhone = ForwardPhoneBookS ce. sPhone)
          ),
          stats,
          "AddressBookCl ent"
        )
      }.getOrElse(St ch.N l)

    cand dateUsers
      .map(
        _.take(ForwardPhoneBookS ce.NumPhoneBookEntr es)
          .map(Cand dateUser(_, score = So (Cand dateUser.DefaultCand dateScore))
            .w hCand dateS ce( dent f er)))
  }
}

object ForwardPhoneBookS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.ForwardPhoneBook.toStr ng)
  val NumPhoneBookEntr es:  nt = 1000
  val  sPhone = true
  val DefaultEdgeType: EdgeType = EdgeType.Forward
}
