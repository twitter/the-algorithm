package com.tw ter.follow_recom ndat ons.flows.post_nux_ml

 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base. dent yRanker
 mport com.tw ter.follow_recom ndat ons.common.base. dent yTransform
 mport com.tw ter.follow_recom ndat ons.common.base.Ranker
 mport com.tw ter.follow_recom ndat ons.common.base.Transform
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.HasPreFetc dFeature
 mport com.tw ter.follow_recom ndat ons.common.models._
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d
 mport com.tw ter.follow_recom ndat ons.common.rankers.fat gue_ranker. mpress onBasedFat gueRanker
 mport com.tw ter.follow_recom ndat ons.common.rankers.f rst_n_ranker.F rstNRanker
 mport com.tw ter.follow_recom ndat ons.common.rankers.f rst_n_ranker.F rstNRankerParams
 mport com.tw ter.follow_recom ndat ons.common.rankers. nterleave_ranker. nterleaveRanker
 mport com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.rank ng.HydrateFeaturesTransform
 mport com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.rank ng.MlRanker
 mport com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.rank ng.MlRankerParams
 mport com.tw ter.follow_recom ndat ons.common.rankers.  ghted_cand date_s ce_ranker.  ghtedCand dateS ceRanker
 mport com.tw ter.follow_recom ndat ons.conf gap .cand dates.HydrateCand dateParamsTransform
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.t  l nes.conf gap .HasParams

/**
 * Used to bu ld t  comb ned ranker compr s ng 4 stages of rank ng:
 * -   ghted sampler
 * - truncat ng to t  top N  rged results for rank ng
 * - ML ranker
 * -  nterleav ng ranker for producer-s de exper  nts
 * -  mpress on-based fat gue ng
 */
@S ngleton
class PostNuxMlComb nedRankerBu lder[
  T <: HasParams w h HasS m larToContext w h HasCl entContext w h HasExcludedUser ds w h HasD splayLocat on w h HasDebugOpt ons w h HasPreFetc dFeature w h HasD sm ssedUser ds w h HasQual yFactor] @ nject() (
  f rstNRanker: F rstNRanker[T],
  hydrateFeaturesTransform: HydrateFeaturesTransform[T],
  hydrateCand dateParamsTransform: HydrateCand dateParamsTransform[T],
  mlRanker: MlRanker[T],
  statsRece ver: StatsRece ver) {
  pr vate[t ] val stats: StatsRece ver = statsRece ver.scope("post_nux_ml_ranker")

  //   construct each ranker  ndependently and cha n t m toget r
  def bu ld(
    request: T,
    cand dateS ce  ghts: Map[Cand dateS ce dent f er, Double]
  ): Ranker[T, Cand dateUser] = {
    val d splayLocat onStats = stats.scope(request.d splayLocat on.toStr ng)
    val   ghtedRankerStats: StatsRece ver =
      d splayLocat onStats.scope("  ghted_cand date_s ce_ranker")
    val f rstNRankerStats: StatsRece ver =
      d splayLocat onStats.scope("f rst_n_ranker")
    val hydrateCand dateParamsStats =
      d splayLocat onStats.scope("hydrate_cand date_params")
    val fat gueRankerStats = d splayLocat onStats.scope("fat gue_ranker")
    val  nterleaveRankerStats =
      d splayLocat onStats.scope(" nterleave_ranker")
    val allRankersStats = d splayLocat onStats.scope("all_rankers")

    // C ck ng  f t   avy-ranker  s an exper  ntal model.
    //  f    s,  nterleaveRanker and cand date para ter hydrat on are d sabled.
    // *NOTE* that consu r-s de exper  nts should at any t   take a small % of traff c, less
    // than 20% for  nstance, to leave enough room for producer exper  nts.  ncreas ng bucket
    // s ze for producer exper  nts lead to ot r  ssues and  s not a v able opt on for faster
    // exper  nts.
    val requestRanker d = request.params(MlRankerParams.RequestScorer dParam)
     f (requestRanker d != Ranker d.PostNuxProdRanker) {
      hydrateCand dateParamsStats.counter(s"d sabled_by_${requestRanker d.toStr ng}"). ncr()
       nterleaveRankerStats.counter(s"d sabled_by_${requestRanker d.toStr ng}"). ncr()
    }

    //   ghted ranker that samples from t  cand date s ces
    val   ghtedRanker =   ghtedCand dateS ceRanker
      .bu ld[T](
        cand dateS ce  ghts,
        request.params(PostNuxMlParams.Cand dateShuffler).shuffle(request.getRandom zat onSeed),
        randomSeed = request.getRandom zat onSeed
      ).observe(  ghtedRankerStats)

    // ranker that takes t  f rst n results ( e truncates output) wh le  rg ng dupl cates
    val f rstNRankerObs = f rstNRanker.observe(f rstNRankerStats)
    // e  r ML ranker that uses deepb rdv2 to score or no rank ng
    val ma nRanker: Ranker[T, Cand dateUser] =
      bu ldMa nRanker(request, requestRanker d == Ranker d.PostNuxProdRanker, d splayLocat onStats)
    // fat gue ranker that uses wtf  mpress ons to fat gue
    val fat gueRanker = bu ldFat gueRanker(request, fat gueRankerStats).observe(fat gueRankerStats)

    //  nterleaveRanker comb nes rank ngs from several rankers and enforces cand dates' ranks  n
    // exper  nt buckets accord ng to t  r ass gned ranker model.
    val  nterleaveRanker =
      bu ld nterleaveRanker(
        request,
        requestRanker d == Ranker d.PostNuxProdRanker,
         nterleaveRankerStats)
        .observe( nterleaveRankerStats)

      ghtedRanker
      .andT n(f rstNRankerObs)
      .andT n(ma nRanker)
      .andT n(fat gueRanker)
      .andT n( nterleaveRanker)
      .observe(allRankersStats)
  }

  def bu ldMa nRanker(
    request: T,
     sMa nRankerPostNuxProd: Boolean,
    d splayLocat onStats: StatsRece ver
  ): Ranker[T, Cand dateUser] = {

    // note that   may be d sabl ng  avy ranker for users not bucketed
    // (due to empty results from t  new cand date s ce)
    // need a better solut on  n t  future
    val mlRankerStats = d splayLocat onStats.scope("ml_ranker")
    val noMlRankerStats = d splayLocat onStats.scope("no_ml_ranker")
    val hydrateFeaturesStats =
      d splayLocat onStats.scope("hydrate_features")
    val hydrateCand dateParamsStats =
      d splayLocat onStats.scope("hydrate_cand date_params")
    val notHydrateCand dateParamsStats =
      d splayLocat onStats.scope("not_hydrate_cand date_params")
    val rankerStats = d splayLocat onStats.scope("ranker")
    val mlRankerD sabledByExper  ntsCounter =
      mlRankerStats.counter("d sabled_by_exper  nts")
    val mlRankerD sabledByQual yFactorCounter =
      mlRankerStats.counter("d sabled_by_qual y_factor")

    val d sabledByQual yFactor = request.qual yFactor
      .ex sts(_ <= request.params(PostNuxMlParams.TurnoffMLScorerQFThreshold))

     f (d sabledByQual yFactor)
      mlRankerD sabledByQual yFactorCounter. ncr()

     f (request.params(PostNuxMlParams.UseMlRanker) && !d sabledByQual yFactor) {

      val hydrateFeatures = hydrateFeaturesTransform
        .observe(hydrateFeaturesStats)

      val opt onalHydratedParamsTransform: Transform[T, Cand dateUser] = {
        //   d sable cand date para ter hydrat on for exper  ntal  avy-ranker models.
         f ( sMa nRankerPostNuxProd &&
          request.params(PostNuxMlParams.EnableCand dateParamHydrat on)) {
          hydrateCand dateParamsTransform
            .observe(hydrateCand dateParamsStats)
        } else {
          new  dent yTransform[T, Cand dateUser]()
            .observe(notHydrateCand dateParamsStats)
        }
      }
      val cand dateS ze = request.params(F rstNRankerParams.Cand datesToRank)
      Ranker
        .cha n(
          hydrateFeatures.andT n(opt onalHydratedParamsTransform),
          mlRanker.observe(mlRankerStats),
        )
        .w h n(
          request.params(PostNuxMlParams.MlRankerBudget),
          rankerStats.scope(s"n$cand dateS ze"))
    } else {
      new  dent yRanker[T, Cand dateUser].observe(noMlRankerStats)
    }
  }

  def bu ld nterleaveRanker(
    request: T,
     sMa nRankerPostNuxProd: Boolean,
     nterleaveRankerStats: StatsRece ver
  ): Ranker[T, Cand dateUser] = {
    //  nterleaveRanker  s enabled only for d splay locat ons po red by t  PostNux  avy-ranker.
     f (request.params(PostNuxMlParams.Enable nterleaveRanker) &&
      //  nterleaveRanker  s d sabled for requests w h exper  ntal  avy-rankers.
       sMa nRankerPostNuxProd) {
      new  nterleaveRanker[T]( nterleaveRankerStats)
    } else {
      new  dent yRanker[T, Cand dateUser]()
    }
  }

  def bu ldFat gueRanker(
    request: T,
    fat gueRankerStats: StatsRece ver
  ): Ranker[T, Cand dateUser] = {
     f (request.params(PostNuxMlParams.EnableFat gueRanker)) {
       mpress onBasedFat gueRanker
        .bu ld[T](
          fat gueRankerStats
        ).w h n(request.params(PostNuxMlParams.Fat gueRankerBudget), fat gueRankerStats)
    } else {
      new  dent yRanker[T, Cand dateUser]()
    }
  }
}
