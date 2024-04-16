package com.tw ter.follow_recom ndat ons.common.cl ents.deepb rdv2

 mport com.google. nject.Prov des
 mport com.google. nject.na .Na d
 mport com.tw ter.b ject on.scrooge.TB naryProtocol
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cortex.deepb rd.thr ftjava.Deepb rdPred ct onServ ce
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.bu lder.Cl entBu lder
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.thr ft.R chCl entParam
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter. nject.Tw terModule

/**
 * Module that prov des mult ple deepb rdv2 pred ct on serv ce cl ents
 *   use t  java ap  s nce data records are nat ve java objects and   want to reduce over ad
 * wh le ser al z ng/deser al z ng data.
 */
object DeepB rdV2Pred ct onServ ceCl entModule extends Tw terModule {

  val RequestT  out = 300.m ll s

  pr vate def getDeepb rdPred ct onServ ceCl ent(
    cl ent d: Cl ent d,
    label: Str ng,
    dest: Str ng,
    statsRece ver: StatsRece ver,
    serv ce dent f er: Serv ce dent f er
  ): Deepb rdPred ct onServ ce.Serv ceToCl ent = {
    val cl entStatsRece ver = statsRece ver.scope("clnt")
    val mTlsCl ent = Thr ftMux.cl ent.w hCl ent d(cl ent d).w hMutualTls(serv ce dent f er)
    new Deepb rdPred ct onServ ce.Serv ceToCl ent(
      Cl entBu lder()
        .na (label)
        .stack(mTlsCl ent)
        .dest(dest)
        .requestT  out(RequestT  out)
        .reportHostStats(NullStatsRece ver)
        .bu ld(),
      R chCl entParam(
        new TB naryProtocol.Factory(),
        cl entStats = cl entStatsRece ver
      )
    )
  }

  @Prov des
  @Na d(Gu ceNa dConstants.WTF_PROD_DEEPB RDV2_CL ENT)
  def prov desWtfProdDeepb rdV2Pred ct onServ ce(
    cl ent d: Cl ent d,
    statsRece ver: StatsRece ver,
    serv ce dent f er: Serv ce dent f er
  ): Deepb rdPred ct onServ ce.Serv ceToCl ent = {
    getDeepb rdPred ct onServ ceCl ent(
      cl ent d = cl ent d,
      label = "WtfProdDeepb rdV2Pred ct onServ ce",
      dest = "/s/cassowary/deepb rdv2- rm -wtf",
      statsRece ver = statsRece ver,
      serv ce dent f er = serv ce dent f er
    )
  }
}
