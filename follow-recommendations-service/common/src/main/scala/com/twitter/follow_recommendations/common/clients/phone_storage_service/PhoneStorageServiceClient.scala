package com.tw ter.follow_recom ndat ons.common.cl ents.phone_storage_serv ce

 mport com.tw ter.cds.contact_consent_state.thr ftscala.PurposeOfProcess ng
 mport com.tw ter.phonestorage.ap .thr ftscala.GetUserPhonesByUsersRequest
 mport com.tw ter.phonestorage.ap .thr ftscala.PhoneStorageServ ce
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PhoneStorageServ ceCl ent @ nject() (
  val phoneStorageServ ce: PhoneStorageServ ce. thodPerEndpo nt) {

  /**
   * PSS can potent ally return mult ple phone records.
   * T  current  mple ntat on of getUserPhonesByUsers returns only a s ngle phone for a s ngle user_ d but
   *   can tr v ally support handl ng mult ple  n case that changes  n t  future.
   */
  def getPhoneNumbers(
    user d: Long,
    purposeOfProcess ng: PurposeOfProcess ng,
    forceCarr erLookup: Opt on[Boolean] = None
  ): St ch[Seq[Str ng]] = {
    val req = GetUserPhonesByUsersRequest(
      user ds = Seq(user d),
      forceCarr erLookup = forceCarr erLookup,
      purposesOfProcess ng = So (Seq(purposeOfProcess ng))
    )

    St ch.callFuture(phoneStorageServ ce.getUserPhonesByUsers(req)) map {
      _.userPhones.map(_.phoneNumber)
    }
  }
}
