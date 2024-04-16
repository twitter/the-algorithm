package com.tw ter.users gnalserv ce

 mport com.google. nject.Module
 mport com.tw ter. nject.thr ft.modules.Thr ftCl ent dModule
 mport com.tw ter.users gnalserv ce.columns.UserS gnalServ ceColumn
 mport com.tw ter.strato.fed._
 mport com.tw ter.strato.fed.server._
 mport com.tw ter.users gnalserv ce.module.Cac Module
 mport com.tw ter.users gnalserv ce.module.MHMtlsParamsModule
 mport com.tw ter.users gnalserv ce.module.Soc alGraphServ ceCl entModule
 mport com.tw ter.users gnalserv ce.module.T  rModule

object UserS gnalServ ceStratoFedServerMa n extends UserS gnalServ ceStratoFedServer

tra  UserS gnalServ ceStratoFedServer extends StratoFedServer {
  overr de def dest: Str ng = "/s/user-s gnal-serv ce/user-s gnal-serv ce"

  overr de def columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(
      classOf[UserS gnalServ ceColumn]
    )

  overr de def modules: Seq[Module] =
    Seq(
      Cac Module,
      MHMtlsParamsModule,
      Soc alGraphServ ceCl entModule,
      Thr ftCl ent dModule,
      T  rModule,
    )

}
