package com.tw ter.cr_m xer.module.s m lar y_eng ne

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.T etW hScoreAndSoc alProof
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.cr_m xer.param.dec der.Dec derConstants
 mport com.tw ter.cr_m xer.s m lar y_eng ne.UserT etEnt yGraphS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.Dec derConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.Gat ngConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.S m lar yEng neConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.StandardS m lar yEng ne
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.UserT etEnt yGraph
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object UserT etEnt yGraphS m lar yEng neModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.UserT etEnt yGraphS m lar yEng ne)
  def prov desUserT etEnt yGraphS m lar yEng ne(
    userT etEnt yGraphServ ce: UserT etEnt yGraph. thodPerEndpo nt,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
    dec der: CrM xerDec der
  ): StandardS m lar yEng ne[
    UserT etEnt yGraphS m lar yEng ne.Query,
    T etW hScoreAndSoc alProof
  ] = {
    new StandardS m lar yEng ne[
      UserT etEnt yGraphS m lar yEng ne.Query,
      T etW hScoreAndSoc alProof
    ](
       mple nt ngStore =
        UserT etEnt yGraphS m lar yEng ne(userT etEnt yGraphServ ce, statsRece ver),
       dent f er = S m lar yEng neType.Uteg,
      globalStats = statsRece ver,
      eng neConf g = S m lar yEng neConf g(
        t  out = t  outConf g.utegS m lar yEng neT  out,
        gat ngConf g = Gat ngConf g(
          dec derConf g = So (
            Dec derConf g(dec der, Dec derConstants.enableUserT etEnt yGraphTraff cDec derKey)),
          enableFeatureSw ch = None
        )
      ),
      //   cannot use t  key to cac  anyth ng  n UTEG because t  key conta ns a long l st of user ds
       mCac Conf g = None
    )
  }
}
