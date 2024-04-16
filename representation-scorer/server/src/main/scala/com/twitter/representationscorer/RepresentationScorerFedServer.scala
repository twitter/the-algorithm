package com.tw ter.representat onscorer

 mport com.google. nject.Module
 mport com.tw ter. nject.thr ft.modules.Thr ftCl ent dModule
 mport com.tw ter.representat onscorer.columns.L stScoreColumn
 mport com.tw ter.representat onscorer.columns.ScoreColumn
 mport com.tw ter.representat onscorer.columns.S mClustersRecentEngage ntS m lar yColumn
 mport com.tw ter.representat onscorer.columns.S mClustersRecentEngage ntS m lar yUserT etEdgeColumn
 mport com.tw ter.representat onscorer.modules.Cac Module
 mport com.tw ter.representat onscorer.modules.Embedd ngStoreModule
 mport com.tw ter.representat onscorer.modules.RMSConf gModule
 mport com.tw ter.representat onscorer.modules.T  rModule
 mport com.tw ter.representat onscorer.tw stlyfeatures.UserS gnalServ ceRecentEngage ntsCl entModule
 mport com.tw ter.strato.fed._
 mport com.tw ter.strato.fed.server._

object Representat onScorerFedServerMa n extends Representat onScorerFedServer

tra  Representat onScorerFedServer extends StratoFedServer {
  overr de def dest: Str ng = "/s/representat on-scorer/representat on-scorer"
  overr de val modules: Seq[Module] =
    Seq(
      Cac Module,
      Thr ftCl ent dModule,
      UserS gnalServ ceRecentEngage ntsCl entModule,
      T  rModule,
      RMSConf gModule,
      Embedd ngStoreModule
    )

  overr de def columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(
      classOf[L stScoreColumn],
      classOf[ScoreColumn],
      classOf[S mClustersRecentEngage ntS m lar yUserT etEdgeColumn],
      classOf[S mClustersRecentEngage ntS m lar yColumn]
    )
}
