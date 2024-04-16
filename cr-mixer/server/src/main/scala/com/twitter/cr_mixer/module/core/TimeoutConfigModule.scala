package com.tw ter.cr_m xer.module.core

 mport com.tw ter. nject.Tw terModule
 mport com.google. nject.Prov des
 mport javax. nject.S ngleton
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.app.Flag
 mport com.tw ter.cr_m xer.conf g.T  outConf g

/**
 * All t  out sett ngs  n CrM xer.
 * T  out numbers are def ned  n s ce/cr-m xer/server/conf g/deploy.aurora
 */
object T  outConf gModule extends Tw terModule {

  /**
   * Flag na s for cl ent t  out
   * T se are used  n modules extend ng Thr ft thodBu lderCl entModule
   * wh ch cannot accept  nject on of T  outConf g
   */
  val Earlyb rdCl entT  outFlagNa  = "earlyb rd.cl ent.t  out"
  val FrsCl entT  outFlagNa  = "frsS gnalFetch.cl ent.t  out"
  val Q gRankerCl entT  outFlagNa  = "q gRanker.cl ent.t  out"
  val T etyp eCl entT  outFlagNa  = "t etyp e.cl ent.t  out"
  val UserT etGraphCl entT  outFlagNa  = "userT etGraph.cl ent.t  out"
  val UserT etGraphPlusCl entT  outFlagNa  = "userT etGraphPlus.cl ent.t  out"
  val UserAdGraphCl entT  outFlagNa  = "userAdGraph.cl ent.t  out"
  val UserV deoGraphCl entT  outFlagNa  = "userV deoGraph.cl ent.t  out"
  val UtegCl entT  outFlagNa  = "uteg.cl ent.t  out"
  val Nav RequestT  outFlagNa  = "nav .cl ent.request.t  out"

  /**
   * Flags for t  outs
   * T se are def ned and  n  al zed only  n t  f le
   */
  // t  out for t  serv ce
  pr vate val serv ceT  out: Flag[Durat on] =
    flag("serv ce.t  out", "serv ce total t  out")

  // t  out for s gnal fetch
  pr vate val s gnalFetchT  out: Flag[Durat on] =
    flag[Durat on]("s gnalFetch.t  out", "s gnal fetch t  out")

  // t  out for s m lar y eng ne
  pr vate val s m lar yEng neT  out: Flag[Durat on] =
    flag[Durat on]("s m lar yEng ne.t  out", "s m lar y eng ne t  out")
  pr vate val annServ ceCl entT  out: Flag[Durat on] =
    flag[Durat on]("annServ ce.cl ent.t  out", "annQueryServ ce cl ent t  out")

  // t  out for user aff n  es fetc r
  pr vate val userStateUnderly ngStoreT  out: Flag[Durat on] =
    flag[Durat on]("userStateUnderly ngStore.t  out", "user state underly ng store t  out")

  pr vate val userStateStoreT  out: Flag[Durat on] =
    flag[Durat on]("userStateStore.t  out", "user state store t  out")

  pr vate val utegS m lar yEng neT  out: Flag[Durat on] =
    flag[Durat on]("uteg.s m lar yEng ne.t  out", "uteg s m lar y eng ne t  out")

  pr vate val earlyb rdServerT  out: Flag[Durat on] =
    flag[Durat on]("earlyb rd.server.t  out", "earlyb rd server t  out")

  pr vate val earlyb rdS m lar yEng neT  out: Flag[Durat on] =
    flag[Durat on]("earlyb rd.s m lar yEng ne.t  out", "Earlyb rd s m lar y eng ne t  out")

  pr vate val frsBasedT etEndpo ntT  out: Flag[Durat on] =
    flag[Durat on](
      "frsBasedT et.endpo nt.t  out",
      "frsBasedT et endpo nt t  out"
    )

  pr vate val top cT etEndpo ntT  out: Flag[Durat on] =
    flag[Durat on](
      "top cT et.endpo nt.t  out",
      "top cT et endpo nt t  out"
    )

  // t  out for Nav  cl ent
  pr vate val nav RequestT  out: Flag[Durat on] =
    flag[Durat on](
      Nav RequestT  outFlagNa ,
      Durat on.fromM ll seconds(2000),
      "Request t  out for a s ngle RPC Call",
    )

  @Prov des
  @S ngleton
  def prov deT  outBudget(): T  outConf g =
    T  outConf g(
      serv ceT  out = serv ceT  out(),
      s gnalFetchT  out = s gnalFetchT  out(),
      s m lar yEng neT  out = s m lar yEng neT  out(),
      annServ ceCl entT  out = annServ ceCl entT  out(),
      utegS m lar yEng neT  out = utegS m lar yEng neT  out(),
      userStateUnderly ngStoreT  out = userStateUnderly ngStoreT  out(),
      userStateStoreT  out = userStateStoreT  out(),
      earlyb rdServerT  out = earlyb rdServerT  out(),
      earlyb rdS m lar yEng neT  out = earlyb rdS m lar yEng neT  out(),
      frsBasedT etEndpo ntT  out = frsBasedT etEndpo ntT  out(),
      top cT etEndpo ntT  out = top cT etEndpo ntT  out(),
      nav RequestT  out = nav RequestT  out()
    )

}
