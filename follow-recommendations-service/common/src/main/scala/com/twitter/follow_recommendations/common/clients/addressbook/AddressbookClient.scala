package com.tw ter.follow_recom ndat ons.common.cl ents.addressbook

 mport com.tw ter.addressbook.datatypes.thr ftscala.QueryType
 mport com.tw ter.addressbook.thr ftscala.AddressBookGetRequest
 mport com.tw ter.addressbook.thr ftscala.AddressBookGetResponse
 mport com.tw ter.addressbook.thr ftscala.Addressbook2
 mport com.tw ter.addressbook.thr ftscala.Cl ent nfo
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.wtf.scald ng.jobs.addressbook.thr ftscala.STPResultFeature
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models.Contact
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models.EdgeType
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models.QueryOpt on
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models.Record dent f er
 mport com.tw ter.wtf.scald ng.jobs.address_book.ABUt l.hashContact
 mport com.tw ter.wtf.scald ng.jobs.address_book.ABUt l.normal zeEma l
 mport com.tw ter.wtf.scald ng.jobs.address_book.ABUt l.normal zePhoneNumber
 mport com.tw ter. rm .usercontacts.thr ftscala.{UserContacts => tUserContacts}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class AddressbookCl ent @ nject() (
  addressbookServ ce: Addressbook2. thodPerEndpo nt,
  statsRece ver: StatsRece ver = NullStatsRece ver) {

  pr vate val stats: StatsRece ver = statsRece ver.scope(t .getClass.getS mpleNa )

  pr vate[t ] def getResponseFromServ ce(
     dent f ers: Seq[Record dent f er],
    batchS ze:  nt,
    edgeType: EdgeType,
    maxFetc s:  nt,
    queryOpt on: Opt on[QueryOpt on]
  ): St ch[Seq[AddressBookGetResponse]] = {
    St ch
      .collect(
         dent f ers.map {  dent f er =>
          St ch.callFuture(
            addressbookServ ce.get(AddressBookGetRequest(
              cl ent nfo = Cl ent nfo(None),
               dent f er =  dent f er.toThr ft,
              edgeType = edgeType.toThr ft,
              queryType = QueryType.User d,
              queryOpt on = queryOpt on.map(_.toThr ft),
              maxFetc s = maxFetc s,
              resultBatchS ze = batchS ze
            )))
        }
      )
  }

  pr vate[t ] def getContactsResponseFromServ ce(
     dent f ers: Seq[Record dent f er],
    batchS ze:  nt,
    edgeType: EdgeType,
    maxFetc s:  nt,
    queryOpt on: Opt on[QueryOpt on]
  ): St ch[Seq[AddressBookGetResponse]] = {
    St ch
      .collect(
         dent f ers.map {  dent f er =>
          St ch.callFuture(
            addressbookServ ce.get(AddressBookGetRequest(
              cl ent nfo = Cl ent nfo(None),
               dent f er =  dent f er.toThr ft,
              edgeType = edgeType.toThr ft,
              queryType = QueryType.Contact,
              queryOpt on = queryOpt on.map(_.toThr ft),
              maxFetc s = maxFetc s,
              resultBatchS ze = batchS ze
            )))
        }
      )
  }

  /** Mode of addressbook resolv ng log c
   * ManhattanT nABV2: fetch ng manhattan cac d result and backf ll w h addressbook v2
   * ABV2Only: call ng addressbook v2 d rectly w hout fetch ng manhattan cac d result
   * T  can be controlled by pass ng a fetc r or not. Pass ng a fetc r w ll attempt to use  ,
   *  f not t n   won't.
   */
  def getUsers(
    user d: Long,
     dent f ers: Seq[Record dent f er],
    batchS ze:  nt,
    edgeType: EdgeType,
    fetc rOpt on: Opt on[Fetc r[Long, Un , tUserContacts]] = None,
    maxFetc s:  nt = 1,
    queryOpt on: Opt on[QueryOpt on] = None,
  ): St ch[Seq[Long]] = {
    fetc rOpt on match {
      case So (fetc r) =>
        getUsersFromManhattan(user d, fetc r).flatMap { userContacts =>
           f (userContacts. sEmpty) {
            stats.counter("mhEmptyT nFromAbServ ce"). ncr()
            getResponseFromServ ce( dent f ers, batchS ze, edgeType, maxFetc s, queryOpt on)
              .map(_.flatMap(_.users).flatten.d st nct)
          } else {
            stats.counter("fromManhattan"). ncr()
            St ch.value(userContacts)
          }
        }
      case None =>
        stats.counter("fromAbServ ce"). ncr()
        getResponseFromServ ce( dent f ers, batchS ze, edgeType, maxFetc s, queryOpt on)
          .map(_.flatMap(_.users).flatten.d st nct)
    }
  }

  def getHas dContacts(
    normal zeFn: Str ng => Str ng,
    extractF eld: Str ng,
  )(
    user d: Long,
     dent f ers: Seq[Record dent f er],
    batchS ze:  nt,
    edgeType: EdgeType,
    fetc rOpt on: Opt on[Fetc r[Str ng, Un , STPResultFeature]] = None,
    maxFetc s:  nt = 1,
    queryOpt on: Opt on[QueryOpt on] = None,
  ): St ch[Seq[Str ng]] = {

    fetc rOpt on match {
      case So (fetc r) =>
        getContactsFromManhattan(user d, fetc r).flatMap { userContacts =>
           f (userContacts. sEmpty) {
            getContactsResponseFromServ ce(
               dent f ers,
              batchS ze,
              edgeType,
              maxFetc s,
              queryOpt on)
              .map { response =>
                for {
                  resp <- response
                  contacts <- resp.contacts
                  contactsThr ft = contacts.map(Contact.fromThr ft)
                  contactsSet = extractF eld match {
                    case "ema ls" => contactsThr ft.flatMap(_.ema ls.toSeq.flatten)
                    case "phoneNumbers" => contactsThr ft.flatMap(_.phoneNumbers.toSeq.flatten)
                  }
                  has dAndNormal zedContacts = contactsSet.map(c => hashContact(normal zeFn(c)))
                } y eld has dAndNormal zedContacts
              }.map(_.flatten)
          } else {
            St ch.N l
          }
        }
      case None => {
        getContactsResponseFromServ ce( dent f ers, batchS ze, edgeType, maxFetc s, queryOpt on)
          .map { response =>
            for {
              resp <- response
              contacts <- resp.contacts
              contactsThr ft = contacts.map(Contact.fromThr ft)
              contactsSet = extractF eld match {
                case "ema ls" => contactsThr ft.flatMap(_.ema ls.toSeq.flatten)
                case "phoneNumbers" => contactsThr ft.flatMap(_.phoneNumbers.toSeq.flatten)
              }
              has dAndNormal zedContacts = contactsSet.map(c => hashContact(normal zeFn(c)))
            } y eld has dAndNormal zedContacts
          }.map(_.flatten)
      }
    }
  }

  def getEma lContacts = getHas dContacts(normal zeEma l, "ema ls") _
  def getPhoneContacts = getHas dContacts(normal zePhoneNumber, "phoneNumbers") _

  pr vate def getUsersFromManhattan(
    user d: Long,
    fetc r: Fetc r[Long, Un , tUserContacts],
  ): St ch[Seq[Long]] = fetc r
    .fetch(user d)
    .map(_.v.map(_.dest nat on ds).toSeq.flatten.d st nct)

  pr vate def getContactsFromManhattan(
    user d: Long,
    fetc r: Fetc r[Str ng, Un , STPResultFeature],
  ): St ch[Seq[Str ng]] = fetc r
    .fetch(user d.toStr ng)
    .map(_.v.map(_.strongT eUserFeature.map(_.dest d)).toSeq.flatten.d st nct)
}

object AddressbookCl ent {
  val AddressBook2BatchS ze = 500

  def createQueryOpt on(edgeType: EdgeType,  sPhone: Boolean): Opt on[QueryOpt on] =
    (edgeType,  sPhone) match {
      case (EdgeType.Reverse, _) =>
        None
      case (EdgeType.Forward, true) =>
        So (
          QueryOpt on(
            onlyD scoverable nExpans on = false,
            onlyConf r d nExpans on = false,
            onlyD scoverable nResult = false,
            onlyConf r d nResult = false,
            fetchGlobalAp Na space = false,
             sDebugRequest = false,
            resolveEma ls = false,
            resolvePhoneNumbers = true
          ))
      case (EdgeType.Forward, false) =>
        So (
          QueryOpt on(
            onlyD scoverable nExpans on = false,
            onlyConf r d nExpans on = false,
            onlyD scoverable nResult = false,
            onlyConf r d nResult = false,
            fetchGlobalAp Na space = false,
             sDebugRequest = false,
            resolveEma ls = true,
            resolvePhoneNumbers = false
          ))
    }

}
