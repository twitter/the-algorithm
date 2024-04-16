package com.tw ter.product_m xer.component_l brary.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.user_sess on_store.ReadOnlyUserSess onStore
 mport com.tw ter.user_sess on_store.ReadWr eUserSess onStore
 mport com.tw ter.user_sess on_store.UserSess onDataset
 mport com.tw ter.user_sess on_store.UserSess onDataset.UserSess onDataset
 mport com.tw ter.user_sess on_store.conf g.manhattan.UserSess onStoreManhattanConf g
 mport com.tw ter.user_sess on_store. mpl.manhattan.readonly.ReadOnlyManhattanUserSess onStoreBu lder
 mport com.tw ter.user_sess on_store. mpl.manhattan.readwr e.ReadWr eManhattanUserSess onStoreBu lder

 mport javax. nject.S ngleton

object UserSess onStoreModule extends Tw terModule {
  pr vate val ReadWr eApp d = "t  l neserv ce_user_sess on_store"
  pr vate val ReadWr eStag ngDataset = "tls_user_sess on_store_nonprod"
  pr vate val ReadWr eProdDataset = "tls_user_sess on_store"
  pr vate val ReadOnlyApp d = "user_sess on_store"
  pr vate val ReadOnlyDataset = "user_sess on_f elds"

  @Prov des
  @S ngleton
  def prov desReadWr eUserSess onStore(
     njectedServ ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): ReadWr eUserSess onStore = {
    val scopedStatsRece ver = statsRece ver.scope( njectedServ ce dent f er.serv ce)

    val dataset =  njectedServ ce dent f er.env ron nt.toLo rCase match {
      case "prod" => ReadWr eProdDataset
      case _ => ReadWr eStag ngDataset
    }

    val cl entReadWr eConf g = new UserSess onStoreManhattanConf g.Prod.ReadWr e.O ga {
      overr de val app d = ReadWr eApp d
      overr de val defaultMaxT  out = 400.m ll seconds
      overr de val maxRetryCount = 1
      overr de val serv ce dent f er =  njectedServ ce dent f er
      overr de val datasetNa sBy d = Map[UserSess onDataset, Str ng](
        UserSess onDataset.Act veDays nfo -> dataset,
        UserSess onDataset.NonPoll ngT  s -> dataset
      )
    }

    ReadWr eManhattanUserSess onStoreBu lder
      .bu ldReadWr eUserSess onStore(cl entReadWr eConf g, statsRece ver, scopedStatsRece ver)
  }

  @Prov des
  @S ngleton
  def prov desReadOnlyUserSess onStore(
     njectedServ ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): ReadOnlyUserSess onStore = {
    val scopedStatsRece ver = statsRece ver.scope( njectedServ ce dent f er.serv ce)

    val cl entReadOnlyConf g = new UserSess onStoreManhattanConf g.Prod.ReadOnly.At na {
      overr de val app d = ReadOnlyApp d
      overr de val defaultMaxT  out = 400.m ll seconds
      overr de val maxRetryCount = 1
      overr de val serv ce dent f er =  njectedServ ce dent f er
      overr de val datasetNa sBy d = Map[UserSess onDataset, Str ng](
        UserSess onDataset.User alth -> ReadOnlyDataset
      )
    }

    ReadOnlyManhattanUserSess onStoreBu lder
      .bu ldReadOnlyUserSess onStore(cl entReadOnlyConf g, statsRece ver, scopedStatsRece ver)
  }
}
