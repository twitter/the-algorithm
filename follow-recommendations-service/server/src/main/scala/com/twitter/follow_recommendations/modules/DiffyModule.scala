package com.tw ter.follow_recom ndat ons.modules

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent.MtlsThr ftMuxCl entSyntax
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f natra.annotat ons.DarkTraff cServ ce
 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derKey
 mport com.tw ter.follow_recom ndat ons.thr ftscala.FollowRecom ndat onsThr ftServ ce
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.thr ft.f lters.DarkTraff cF lter
 mport com.tw ter.servo.dec der.Dec derGateBu lder

object D ffyModule extends Tw terModule {
  // d ffy.dest  s def ned  n t  Follow Recom ndat ons Serv ce aurora f le
  // and po nts to t  Dark Traff c Proxy server
  pr vate val destFlag =
    flag[Str ng]("d ffy.dest", "/$/n l", "Resolvable na  of d ffy-serv ce or proxy")

  @Prov des
  @S ngleton
  @DarkTraff cServ ce
  def prov deDarkTraff cServ ce(
    serv ce dent f er: Serv ce dent f er
  ): FollowRecom ndat onsThr ftServ ce.ReqRepServ cePerEndpo nt = {
    Thr ftMux.cl ent
      .w hCl ent d(Cl ent d("follow_recos_serv ce_darktraff c_proxy_cl ent"))
      .w hMutualTls(serv ce dent f er)
      .serv cePerEndpo nt[FollowRecom ndat onsThr ftServ ce.ReqRepServ cePerEndpo nt](
        dest = destFlag(),
        label = "darktraff cproxy"
      )
  }

  @Prov des
  @S ngleton
  def prov deDarkTraff cF lter(
    @DarkTraff cServ ce darkServ ce: FollowRecom ndat onsThr ftServ ce.ReqRepServ cePerEndpo nt,
    dec derGateBu lder: Dec derGateBu lder,
    statsRece ver: StatsRece ver,
    @Flag("env ron nt") env: Str ng
  ): DarkTraff cF lter[FollowRecom ndat onsThr ftServ ce.ReqRepServ cePerEndpo nt] = {
    // sampleFunct on  s used to determ ne wh ch requests should get repl cated
    // to t  dark traff c proxy server
    val sampleFunct on: Any => Boolean = { _ =>
      // c ck w t r t  current FRS  nstance  s deployed  n product on
      env match {
        case "prod" =>
          statsRece ver.scope("prov deDarkTraff cF lter").counter("prod"). ncr()
          destFlag. sDef ned && dec derGateBu lder
            .keyToFeature(Dec derKey.EnableTraff cDarkRead ng). sAva lable(RandomRec p ent)
        case _ =>
          statsRece ver.scope("prov deDarkTraff cF lter").counter("devel"). ncr()
          // repl cate zero requests  f  n non-product on env ron nt
          false
      }
    }
    new DarkTraff cF lter[FollowRecom ndat onsThr ftServ ce.ReqRepServ cePerEndpo nt](
      darkServ ce,
      sampleFunct on,
      forwardAfterServ ce = true,
      statsRece ver.scope("DarkTraff cF lter"),
      lookupBy thod = true
    )
  }
}
