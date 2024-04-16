package com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook

 mport com.tw ter.cds.contact_consent_state.thr ftscala.PurposeOfProcess ng
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.AddressbookCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models.EdgeType
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models.Record dent f er
 mport com.tw ter.follow_recom ndat ons.common.cl ents.phone_storage_serv ce.PhoneStorageServ ceCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.ut ls.RescueW hStatsUt ls.rescueW hStats
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.ReversePhoneContactsCl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ReversePhoneBookS ce @ nject() (
  reversePhoneContactsCl entColumn: ReversePhoneContactsCl entColumn,
  pssCl ent: PhoneStorageServ ceCl ent,
  addressBookCl ent: AddressbookCl ent,
  statsRece ver: StatsRece ver = NullStatsRece ver)
    extends Cand dateS ce[HasParams w h HasCl entContext, Cand dateUser] {

  overr de val  dent f er: Cand dateS ce dent f er = ReversePhoneBookS ce. dent f er
  pr vate val stats: StatsRece ver = statsRece ver.scope(t .getClass.getS mpleNa )

  /**
   * Generate a l st of cand dates for t  target
   */
  overr de def apply(target: HasParams w h HasCl entContext): St ch[Seq[Cand dateUser]] = {
    val reverseCand datesFromPhones: St ch[Seq[Long]] = target.getOpt onalUser d
      .map { user d =>
        pssCl ent
          .getPhoneNumbers(user d, PurposeOfProcess ng.ContentRecom ndat ons)
          .flatMap { phoneNumbers =>
            rescueW hStats(
              addressBookCl ent.getUsers(
                user d = user d,
                 dent f ers = phoneNumbers.map(phoneNumber =>
                  Record dent f er(user d = None, ema l = None, phoneNumber = So (phoneNumber))),
                batchS ze = ReversePhoneBookS ce.NumPhoneBookEntr es,
                edgeType = ReversePhoneBookS ce.DefaultEdgeType,
                fetc rOpt on =
                   f (target.params(AddressBookParams.ReadFromABV2Only)) None
                  else So (reversePhoneContactsCl entColumn.fetc r),
                queryOpt on = AddressbookCl ent.createQueryOpt on(
                  edgeType = ReversePhoneBookS ce.DefaultEdgeType,
                   sPhone = ReversePhoneBookS ce. sPhone)
              ),
              stats,
              "AddressBookCl ent"
            )
          }
      }.getOrElse(St ch.N l)

    reverseCand datesFromPhones.map(
      _.take(ReversePhoneBookS ce.NumPhoneBookEntr es)
        .map(
          Cand dateUser(_, score = So (Cand dateUser.DefaultCand dateScore))
            .w hCand dateS ce( dent f er))
    )
  }
}

object ReversePhoneBookS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.ReversePhoneBook.toStr ng)
  val NumPhoneBookEntr es:  nt = 500
  val  sPhone = true
  val DefaultEdgeType: EdgeType = EdgeType.Reverse
}
