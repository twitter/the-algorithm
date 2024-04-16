package com.tw ter.t  l neranker.conf g

 mport com.tw ter.t  l neranker.dec der.Dec derKey._
 mport com.tw ter.t  l nes.author zat on.TrustedPerm ss on
 mport com.tw ter.t  l nes.author zat on.RateL m  ngTrustedPerm ss on
 mport com.tw ter.t  l nes.author zat on.RateL m  ngUntrustedPerm ss on
 mport com.tw ter.t  l nes.author zat on.Cl entDeta ls

object Cl entAccessPerm ss ons {
  //   want t  l neranker locked down for requests outs de of what's def ned  re.
  val DefaultRateL m  = 0d

  def unknown(na : Str ng): Cl entDeta ls = {
    Cl entDeta ls(na , RateL m  ngUntrustedPerm ss on(RateL m Overr deUnknown, DefaultRateL m ))
  }

  val All: Seq[Cl entDeta ls] = Seq(
    /**
     * Product on cl ents for t  l nem xer.
     */
    new Cl entDeta ls(
      "t  l nem xer.recap.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerRecapProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.recycled.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerRecycledProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.hydrate.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerHydrateProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.hydrate_recos.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerHydrateRecosProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.seed_author_ ds.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerSeedAuthorsProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.s mcluster.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerS mclusterProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.ent y_t ets.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerEnt yT etsProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    /**
     * T  cl ent  s wh el sted for t  l nem xer only as   used by
     * L st  nject on serv ce wh ch w ll not be m grated to t  l nescorer.
     */
    new Cl entDeta ls(
      "t  l nem xer.l st.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerL stProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.l st_t et.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerL stT etProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.commun y.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerCommun yProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.commun y_t et.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerCommun yT etProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.uteg_l ked_by_t ets.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerUtegL kedByT etsProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    /**
     * Product on cl ents for t  l nescorer. Most of t se cl ents have t  r
     * equ valents under t  t  l nem xer scope (w h except on of l st  nject on
     * cl ent).
     */
    new Cl entDeta ls(
      "t  l nescorer.recap.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerRecapProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.recycled.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerRecycledProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.hydrate.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerHydrateProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.hydrate_recos.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerHydrateRecosProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.seed_author_ ds.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerSeedAuthorsProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.s mcluster.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerS mclusterProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.ent y_t ets.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerEnt yT etsProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.l st_t et.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerL stT etProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.uteg_l ked_by_t ets.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerUtegL kedByT etsProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l neserv ce.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neServ ceProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.hydrate_t et_scor ng.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neScorerHydrateT etScor ngProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.commun y_t et.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerCommun yT etProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.recom nded_trend_t et.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neScorerRecom ndedTrendT etProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.rec_top c_t ets.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neScorerRecTop cT etsProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.popular_top c_t ets.prod",
      RateL m  ngTrustedPerm ss on(AllowT  l neScorerPopularTop cT etsProd),
      protectedWr eAccess = TrustedPerm ss on
    ),
    /**
     * T  l neRanker ut l  es. Traff c proxy, warmups, and console.
     */
    new Cl entDeta ls(
      "t  l neranker.proxy",
      RateL m  ngTrustedPerm ss on(AllowT  l neRankerProxy),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      T  l neRankerConstants.WarmupCl entNa ,
      RateL m  ngTrustedPerm ss on(AllowT  l neRankerWarmup),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      T  l neRankerConstants.ForwardedCl entNa ,
      RateL m  ngTrustedPerm ss on(AllowT  l neRankerWarmup),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l neranker.console",
      RateL m  ngUntrustedPerm ss on(RateL m Overr deUnknown, 1d),
      protectedWr eAccess = TrustedPerm ss on
    ),
    /**
     * Stag ng cl ents.
     */
    new Cl entDeta ls(
      "t  l nem xer.recap.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.recycled.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.hydrate.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.hydrate_recos.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.seed_author_ ds.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.s mcluster.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.ent y_t ets.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.l st.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.l st_t et.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.commun y.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.commun y_t et.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.commun y_t et.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.recom nded_trend_t et.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.uteg_l ked_by_t ets.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nem xer.ent y_t ets.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.hydrate_t et_scor ng.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.rec_top c_t ets.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l nescorer.popular_top c_t ets.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neM xerStag ng),
      protectedWr eAccess = TrustedPerm ss on
    ),
    new Cl entDeta ls(
      "t  l neserv ce.stag ng",
      RateL m  ngTrustedPerm ss on(AllowT  l neServ ceStag ng),
      protectedWr eAccess = TrustedPerm ss on
    )
  )
}
