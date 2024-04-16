package com.tw ter.un f ed_user_act ons.serv ce.module

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent.MtlsThr ftMuxCl entSyntax
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.thr ft.R chCl entParam
 mport com.tw ter.graphql.thr ftscala.GraphqlExecut onServ ce
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

object GraphqlCl entProv derModule extends Tw terModule {
  pr vate def bu ldCl ent(serv ce dent f er: Serv ce dent f er, cl ent d: Cl ent d) =
    Thr ftMux.cl ent
      .w hRequestT  out(Durat on.fromSeconds(5))
      .w hMutualTls(serv ce dent f er)
      .w hOpportun st cTls(Opportun st cTls.Requ red)
      .w hCl ent d(cl ent d)
      .newServ ce("/s/graphql-serv ce/graphql-ap :thr ft")

  def bu ldGraphQlCl ent(
    serv ce dent fer: Serv ce dent f er,
    cl ent d: Cl ent d
  ): GraphqlExecut onServ ce.F nagledCl ent = {
    val cl ent = bu ldCl ent(serv ce dent fer, cl ent d)
    new GraphqlExecut onServ ce.F nagledCl ent(cl ent, R chCl entParam())
  }

  @Prov des
  @S ngleton
  def prov desGraphQlCl ent(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d
  ): GraphqlExecut onServ ce.F nagledCl ent =
    bu ldGraphQlCl ent(
      serv ce dent f er,
      cl ent d
    )
}
