package com.tw ter.cr_m xer.cand date_generat on

 mport com.tw ter.contentrecom nder.thr ftscala.T et nfo
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.model.FrsT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.T etW hAuthor
 mport com.tw ter.cr_m xer.param.FrsParams
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Earlyb rdS m lar yEng neRouter
 mport com.tw ter.cr_m xer.s ce_s gnal.FrsStore
 mport com.tw ter.cr_m xer.s ce_s gnal.FrsStore.FrsQueryResult
 mport com.tw ter.cr_m xer.thr ftscala.FrsT et
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter. rm .constants.Algor hmFeedbackTokens
 mport com.tw ter. rm .constants.Algor hmFeedbackTokens.Algor hmToFeedbackTokenMap
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

/**
 * T etCand dateGenerator based on FRS seed users. For now t  cand date generator fetc s seed
 * users from FRS, and retr eves t  seed users' past t ets from Earlyb rd w h Earlyb rd l ght
 * rank ng models.
 */
@S ngleton
class FrsT etCand dateGenerator @ nject() (
  @Na d(ModuleNa s.FrsStore) frsStore: ReadableStore[FrsStore.Query, Seq[FrsQueryResult]],
  frsBasedS m lar yEng ne: Earlyb rdS m lar yEng neRouter,
  t et nfoStore: ReadableStore[T et d, T et nfo],
  t  outConf g: T  outConf g,
  globalStats: StatsRece ver) {
   mport FrsT etCand dateGenerator._

  pr vate val t  r = DefaultT  r
  pr vate val stats: StatsRece ver = globalStats.scope(t .getClass.getCanon calNa )
  pr vate val fetchSeedsStats = stats.scope("fetchSeeds")
  pr vate val fetchCand datesStats = stats.scope("fetchCand dates")
  pr vate val f lterCand datesStats = stats.scope("f lterCand dates")
  pr vate val hydrateCand datesStats = stats.scope("hydrateCand dates")
  pr vate val getCand datesStats = stats.scope("getCand dates")

  /**
   * T  funct on retr eves t  cand date for t  g ven user as follows:
   * 1. Seed user fetch from FRS.
   * 2. Cand date fetch from Earlyb rd.
   * 3. F lter ng.
   * 4. Cand date hydrat on.
   * 5. Truncat on.
   */
  def get(
    frsT etCand dateGeneratorQuery: FrsT etCand dateGeneratorQuery
  ): Future[Seq[FrsT et]] = {
    val user d = frsT etCand dateGeneratorQuery.user d
    val product = frsT etCand dateGeneratorQuery.product
    val allStats = stats.scope("all")
    val perProductStats = stats.scope("perProduct", product.na )
    StatsUt l.track emsStats(allStats) {
      StatsUt l.track emsStats(perProductStats) {
        val result = for {
          seedAuthorW hScores <- StatsUt l.trackOpt on emMapStats(fetchSeedsStats) {
            fetchSeeds(
              user d,
              frsT etCand dateGeneratorQuery. mpressedUserL st,
              frsT etCand dateGeneratorQuery.languageCodeOpt,
              frsT etCand dateGeneratorQuery.countryCodeOpt,
              frsT etCand dateGeneratorQuery.params,
            )
          }
          t etCand dates <- StatsUt l.trackOpt on emsStats(fetchCand datesStats) {
            fetchCand dates(
              user d,
              seedAuthorW hScores.map(_.keys.toSeq).getOrElse(Seq.empty),
              frsT etCand dateGeneratorQuery. mpressedT etL st,
              seedAuthorW hScores.map(_.mapValues(_.score)).getOrElse(Map.empty),
              frsT etCand dateGeneratorQuery.params
            )
          }
          f lteredT etCand dates <- StatsUt l.trackOpt on emsStats(f lterCand datesStats) {
            f lterCand dates(
              t etCand dates,
              frsT etCand dateGeneratorQuery.params
            )
          }
          hydratedT etCand dates <- StatsUt l.trackOpt on emsStats(hydrateCand datesStats) {
            hydrateCand dates(
              seedAuthorW hScores,
              f lteredT etCand dates
            )
          }
        } y eld {
          hydratedT etCand dates
            .map(_.take(frsT etCand dateGeneratorQuery.maxNumResults)).getOrElse(Seq.empty)
        }
        result.ra seW h n(t  outConf g.frsBasedT etEndpo ntT  out)(t  r)
      }
    }
  }

  /**
   * Fetch recom nded seed users from FRS
   */
  pr vate def fetchSeeds(
    user d: User d,
    userDenyL st: Set[User d],
    languageCodeOpt: Opt on[Str ng],
    countryCodeOpt: Opt on[Str ng],
    params: Params
  ): Future[Opt on[Map[User d, FrsQueryResult]]] = {
    frsStore
      .get(
        FrsStore.Query(
          user d,
          params(FrsParams.FrsBasedCand dateGenerat onMaxSeedsNumParam),
          params(FrsParams.FrsBasedCand dateGenerat onD splayLocat onParam).d splayLocat on,
          userDenyL st.toSeq,
          languageCodeOpt,
          countryCodeOpt
        )).map {
        _.map { seedAuthors =>
          seedAuthors.map(user => user.user d -> user).toMap
        }
      }
  }

  /**
   * Fetch t et cand dates from Earlyb rd
   */
  pr vate def fetchCand dates(
    searc rUser d: User d,
    seedAuthors: Seq[User d],
     mpressedT etL st: Set[T et d],
    frsUserToScores: Map[User d, Double],
    params: Params
  ): Future[Opt on[Seq[T etW hAuthor]]] = {
     f (seedAuthors.nonEmpty) {
      // call earlyb rd
      val query = Earlyb rdS m lar yEng neRouter.queryFromParams(
        So (searc rUser d),
        seedAuthors,
         mpressedT etL st,
        frsUserToScoresForScoreAdjust nt = So (frsUserToScores),
        params
      )
      frsBasedS m lar yEng ne.get(query)
    } else Future.None
  }

  /**
   * F lter cand dates that do not pass v s b l y f lter pol cy
   */
  pr vate def f lterCand dates(
    cand dates: Opt on[Seq[T etW hAuthor]],
    params: Params
  ): Future[Opt on[Seq[T etW hAuthor]]] = {
    val t et ds = cand dates.map(_.map(_.t et d).toSet).getOrElse(Set.empty)
     f (params(FrsParams.FrsBasedCand dateGenerat onEnableV s b l yF lter ngParam))
      Future
        .collect(t et nfoStore.mult Get(t et ds)).map { t et nfos =>
          cand dates.map {
            //  f t et nfo does not ex st,   w ll f lter out t  t et cand date.
            _.f lter(cand date => t et nfos.getOrElse(cand date.t et d, None). sDef ned)
          }
        }
    else {
      Future.value(cand dates)
    }
  }

  /**
   * Hydrate t  cand dates w h t  FRS cand date s ces and scores
   */
  pr vate def hydrateCand dates(
    frsAuthorW hScores: Opt on[Map[User d, FrsQueryResult]],
    cand dates: Opt on[Seq[T etW hAuthor]]
  ): Future[Opt on[Seq[FrsT et]]] = {
    Future.value {
      cand dates.map {
        _.map { t etW hAuthor =>
          val frsQueryResult = frsAuthorW hScores.flatMap(_.get(t etW hAuthor.author d))
          FrsT et(
            t et d = t etW hAuthor.t et d,
            author d = t etW hAuthor.author d,
            frsPr maryS ce = frsQueryResult.flatMap(_.pr maryS ce),
            frsAuthorScore = frsQueryResult.map(_.score),
            frsCand dateS ceScores = frsQueryResult.flatMap { result =>
              result.s ceW hScores.map {
                _.collect {
                  // see TokenStrToAlgor hmMap @ https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/ rm / rm -core/src/ma n/scala/com/tw ter/ rm /constants/Algor hmFeedbackTokens.scala
                  // see Algor hm @ https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/ rm / rm -core/src/ma n/scala/com/tw ter/ rm /model/Algor hm.scala
                  case (cand dateS ceAlgoStr, score)
                       f Algor hmFeedbackTokens.TokenStrToAlgor hmMap.conta ns(
                        cand dateS ceAlgoStr) =>
                    Algor hmToFeedbackTokenMap.getOrElse(
                      Algor hmFeedbackTokens.TokenStrToAlgor hmMap
                        .getOrElse(cand dateS ceAlgoStr, DefaultAlgo),
                      DefaultAlgoToken) -> score
                }
              }
            }
          )
        }
      }
    }
  }

}

object FrsT etCand dateGenerator {
  val DefaultAlgo: Algor hm.Value = Algor hm.Ot r
  // 9999  s t  token for Algor hm.Ot r
  val DefaultAlgoToken:  nt = Algor hmToFeedbackTokenMap.getOrElse(DefaultAlgo, 9999)
}
