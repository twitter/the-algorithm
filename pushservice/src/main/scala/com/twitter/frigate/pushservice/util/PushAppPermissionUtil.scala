package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.fr gate.common.store.dev ce nfo.Dev ce nfo
 mport com.tw ter.onboard ng.task.serv ce.models.external.Perm ss onState
 mport com.tw ter.perm ss ons_storage.thr ftscala.AppPerm ss on
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

object PushAppPerm ss onUt l {

  f nal val AddressBookPerm ss onKey = "addressBook"
  f nal val SyncStateKey = "syncState"
  f nal val SyncStateOnValue = "on"

  /**
   * Obta ns t  spec f ed target's App Perm ss ons, based on t  r pr mary dev ce.
   * @param target d            Target's  dent f er
   * @param perm ss onNa       T  perm ss on type   are query ng for (address book, geolocat on, etc.)
   * @param dev ce nfoFut       Dev ce  nfo of t  Target, presented as a Future
   * @param appPerm ss onStore  Readable Store wh ch allows us to query t  App Perm ss on Strato Column
   * @return                    Returns t  AppPerm ss on of t  Target, presented as a Future
   */
  def getAppPerm ss on(
    target d: Long,
    perm ss onNa : Str ng,
    dev ce nfoFut: Future[Opt on[Dev ce nfo]],
    appPerm ss onStore: ReadableStore[(Long, (Str ng, Str ng)), AppPerm ss on]
  ): Future[Opt on[AppPerm ss on]] = {
    dev ce nfoFut.flatMap { dev ce nfoOpt =>
      val pr maryDev ce dOpt = dev ce nfoOpt.flatMap(_.pr maryDev ce d)
      pr maryDev ce dOpt match {
        case So (pr maryDev ce d) =>
          val queryKey = (target d, (pr maryDev ce d, perm ss onNa ))
          appPerm ss onStore.get(queryKey)
        case _ => Future.None
      }
    }
  }

  def hasTargetUploadedAddressBook(
    appPerm ss onOpt: Opt on[AppPerm ss on]
  ): Boolean = {
    appPerm ss onOpt.ex sts { appPerm ss on =>
      val syncState = appPerm ss on. tadata.get(SyncStateKey)
      appPerm ss on.systemPerm ss onState == Perm ss onState.On && syncState
        .ex sts(_.equals gnoreCase(SyncStateOnValue))
    }
  }
}
