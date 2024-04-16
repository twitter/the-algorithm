package com.tw ter.follow_recom ndat ons.common.cl ents.ema l_storage_serv ce

 mport com.tw ter.cds.contact_consent_state.thr ftscala.PurposeOfProcess ng
 mport com.tw ter.ema lstorage.ap .thr ftscala.Ema lStorageServ ce
 mport com.tw ter.ema lstorage.ap .thr ftscala.GetUsersEma lsRequest
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ema lStorageServ ceCl ent @ nject() (
  val ema lStorageServ ce: Ema lStorageServ ce. thodPerEndpo nt) {

  def getVer f edEma l(
    user d: Long,
    purposeOfProcess ng: PurposeOfProcess ng
  ): St ch[Opt on[Str ng]] = {
    val req = GetUsersEma lsRequest(
      user ds = Seq(user d),
      cl ent dent f er = So ("follow-recom ndat ons-serv ce"),
      purposesOfProcess ng = So (Seq(purposeOfProcess ng))
    )

    St ch.callFuture(ema lStorageServ ce.getUsersEma ls(req)) map {
      _.usersEma ls.map(_.conf r dEma l.map(_.ema l)). ad
    }
  }
}
