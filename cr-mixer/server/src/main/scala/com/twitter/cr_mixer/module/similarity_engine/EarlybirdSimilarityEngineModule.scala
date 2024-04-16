package com.tw ter.cr_m xer.module.s m lar y_eng ne

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.cr_m xer.param.dec der.Dec derConstants
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Earlyb rdModelBasedS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Earlyb rdRecencyBasedS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Earlyb rdS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Earlyb rdTensorflowBasedS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.Dec derConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.Gat ngConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.S m lar yEng neConf g
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.S ngleton

object Earlyb rdS m lar yEng neModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desRecencyBasedEarlyb rdS m lar yEng ne(
    earlyb rdRecencyBasedS m lar yEng ne: Earlyb rdRecencyBasedS m lar yEng ne,
    t  outConf g: T  outConf g,
    dec der: CrM xerDec der,
    statsRece ver: StatsRece ver
  ): Earlyb rdS m lar yEng ne[
    Earlyb rdRecencyBasedS m lar yEng ne.Earlyb rdRecencyBasedSearchQuery,
    Earlyb rdRecencyBasedS m lar yEng ne
  ] = {
    new Earlyb rdS m lar yEng ne[
      Earlyb rdRecencyBasedS m lar yEng ne.Earlyb rdRecencyBasedSearchQuery,
      Earlyb rdRecencyBasedS m lar yEng ne
    ](
       mple nt ngStore = earlyb rdRecencyBasedS m lar yEng ne,
       dent f er = S m lar yEng neType.Earlyb rdRecencyBasedS m lar yEng ne,
      globalStats =
        statsRece ver.scope(S m lar yEng neType.Earlyb rdRecencyBasedS m lar yEng ne.na ),
      eng neConf g = S m lar yEng neConf g(
        t  out = t  outConf g.earlyb rdS m lar yEng neT  out,
        gat ngConf g = Gat ngConf g(
          dec derConf g = So (
            Dec derConf g(
              dec der = dec der,
              dec derStr ng = Dec derConstants.enableEarlyb rdTraff cDec derKey
            )),
          enableFeatureSw ch = None
        )
      )
    )
  }

  @Prov des
  @S ngleton
  def prov desModelBasedEarlyb rdS m lar yEng ne(
    earlyb rdModelBasedS m lar yEng ne: Earlyb rdModelBasedS m lar yEng ne,
    t  outConf g: T  outConf g,
    dec der: CrM xerDec der,
    statsRece ver: StatsRece ver
  ): Earlyb rdS m lar yEng ne[
    Earlyb rdModelBasedS m lar yEng ne.Earlyb rdModelBasedSearchQuery,
    Earlyb rdModelBasedS m lar yEng ne
  ] = {
    new Earlyb rdS m lar yEng ne[
      Earlyb rdModelBasedS m lar yEng ne.Earlyb rdModelBasedSearchQuery,
      Earlyb rdModelBasedS m lar yEng ne
    ](
       mple nt ngStore = earlyb rdModelBasedS m lar yEng ne,
       dent f er = S m lar yEng neType.Earlyb rdModelBasedS m lar yEng ne,
      globalStats =
        statsRece ver.scope(S m lar yEng neType.Earlyb rdModelBasedS m lar yEng ne.na ),
      eng neConf g = S m lar yEng neConf g(
        t  out = t  outConf g.earlyb rdS m lar yEng neT  out,
        gat ngConf g = Gat ngConf g(
          dec derConf g = So (
            Dec derConf g(
              dec der = dec der,
              dec derStr ng = Dec derConstants.enableEarlyb rdTraff cDec derKey
            )),
          enableFeatureSw ch = None
        )
      )
    )
  }

  @Prov des
  @S ngleton
  def prov desTensorflowBasedEarlyb rdS m lar yEng ne(
    earlyb rdTensorflowBasedS m lar yEng ne: Earlyb rdTensorflowBasedS m lar yEng ne,
    t  outConf g: T  outConf g,
    dec der: CrM xerDec der,
    statsRece ver: StatsRece ver
  ): Earlyb rdS m lar yEng ne[
    Earlyb rdTensorflowBasedS m lar yEng ne.Earlyb rdTensorflowBasedSearchQuery,
    Earlyb rdTensorflowBasedS m lar yEng ne
  ] = {
    new Earlyb rdS m lar yEng ne[
      Earlyb rdTensorflowBasedS m lar yEng ne.Earlyb rdTensorflowBasedSearchQuery,
      Earlyb rdTensorflowBasedS m lar yEng ne
    ](
       mple nt ngStore = earlyb rdTensorflowBasedS m lar yEng ne,
       dent f er = S m lar yEng neType.Earlyb rdTensorflowBasedS m lar yEng ne,
      globalStats =
        statsRece ver.scope(S m lar yEng neType.Earlyb rdTensorflowBasedS m lar yEng ne.na ),
      eng neConf g = S m lar yEng neConf g(
        t  out = t  outConf g.earlyb rdS m lar yEng neT  out,
        gat ngConf g = Gat ngConf g(
          dec derConf g = So (
            Dec derConf g(
              dec der = dec der,
              dec derStr ng = Dec derConstants.enableEarlyb rdTraff cDec derKey
            )),
          enableFeatureSw ch = None
        )
      )
    )
  }

}
