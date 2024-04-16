package com.tw ter.representat on_manager

 mport com.google. nject.Module
 mport com.tw ter. nject.thr ft.modules.Thr ftCl ent dModule
 mport com.tw ter.representat on_manager.columns.top c.LocaleEnt y dS mClustersEmbedd ngCol
 mport com.tw ter.representat on_manager.columns.top c.Top c dS mClustersEmbedd ngCol
 mport com.tw ter.representat on_manager.columns.t et.T etS mClustersEmbedd ngCol
 mport com.tw ter.representat on_manager.columns.user.UserS mClustersEmbedd ngCol
 mport com.tw ter.representat on_manager.modules.Cac Module
 mport com.tw ter.representat on_manager.modules. nterestsThr ftCl entModule
 mport com.tw ter.representat on_manager.modules.LegacyRMSConf gModule
 mport com.tw ter.representat on_manager.modules.StoreModule
 mport com.tw ter.representat on_manager.modules.T  rModule
 mport com.tw ter.representat on_manager.modules.UttCl entModule
 mport com.tw ter.strato.fed._
 mport com.tw ter.strato.fed.server._

object Representat onManagerFedServerMa n extends Representat onManagerFedServer

tra  Representat onManagerFedServer extends StratoFedServer {
  overr de def dest: Str ng = "/s/representat on-manager/representat on-manager"
  overr de val modules: Seq[Module] =
    Seq(
      Cac Module,
       nterestsThr ftCl entModule,
      LegacyRMSConf gModule,
      StoreModule,
      Thr ftCl ent dModule,
      T  rModule,
      UttCl entModule
    )

  overr de def columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(
      classOf[T etS mClustersEmbedd ngCol],
      classOf[UserS mClustersEmbedd ngCol],
      classOf[Top c dS mClustersEmbedd ngCol],
      classOf[LocaleEnt y dS mClustersEmbedd ngCol]
    )
}
