package com.tw ter.follow_recom ndat ons.flows.post_nux_ml

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Enr c dCand dateS ce._
 mport com.tw ter.follow_recom ndat ons.common.base._
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.follow_recom ndat ons.common.pred cates.d sm ss.D sm ssedCand datePred cate
 mport com.tw ter.follow_recom ndat ons.common.pred cates.g zmoduck.G zmoduckPred cate
 mport com.tw ter.follow_recom ndat ons.common.transforms.ranker_ d.RandomRanker dTransform
 mport com.tw ter.follow_recom ndat ons.common.pred cates.sgs. nval dTargetCand dateRelat onsh pTypesPred cate
 mport com.tw ter.follow_recom ndat ons.common.pred cates.sgs.RecentFollow ngPred cate
 mport com.tw ter.follow_recom ndat ons.common.pred cates.Cand dateParamPred cate
 mport com.tw ter.follow_recom ndat ons.common.pred cates.Cand dateS ceParamPred cate
 mport com.tw ter.follow_recom ndat ons.common.pred cates.CuratedCompet orL stPred cate
 mport com.tw ter.follow_recom ndat ons.common.pred cates.ExcludedUser dPred cate
 mport com.tw ter.follow_recom ndat ons.common.pred cates. nact vePred cate
 mport com.tw ter.follow_recom ndat ons.common.pred cates.Prev ouslyRecom ndedUser dsPred cate
 mport com.tw ter.follow_recom ndat ons.common.pred cates.user_act v y.NonNearZeroUserAct v yPred cate
 mport com.tw ter.follow_recom ndat ons.common.transforms.dedup.DedupTransform
 mport com.tw ter.follow_recom ndat ons.common.transforms.mod fy_soc al_proof.Mod fySoc alProofTransform
 mport com.tw ter.follow_recom ndat ons.common.transforms.track ng_token.Track ngTokenTransform
 mport com.tw ter.follow_recom ndat ons.common.transforms.  ghted_sampl ng.Sampl ngTransform
 mport com.tw ter.follow_recom ndat ons.conf gap .cand dates.Cand dateUserParamsFactory
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams.EnableGFSSoc alProofTransform
 mport com.tw ter.follow_recom ndat ons.ut ls.Cand dateS ceHoldbackUt l
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.ut l.Durat on

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.Soc alGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.pred cates.hss.HssPred cate
 mport com.tw ter.follow_recom ndat ons.common.pred cates.sgs. nval dRelat onsh pPred cate
 mport com.tw ter.follow_recom ndat ons.common.transforms.mod fy_soc al_proof.RemoveAccountProofTransform
 mport com.tw ter.follow_recom ndat ons.logg ng.FrsLogger
 mport com.tw ter.follow_recom ndat ons.models.Recom ndat onFlowData
 mport com.tw ter.follow_recom ndat ons.ut ls.Recom ndat onFlowBaseS deEffectsUt l
 mport com.tw ter.product_m xer.core.model.common. dent f er.Recom ndat onP pel ne dent f er
 mport com.tw ter.product_m xer.core.qual y_factor.BoundsW hDefault
 mport com.tw ter.product_m xer.core.qual y_factor.L nearLatencyQual yFactor
 mport com.tw ter.product_m xer.core.qual y_factor.L nearLatencyQual yFactorConf g
 mport com.tw ter.product_m xer.core.qual y_factor.L nearLatencyQual yFactorObserver
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.st ch.St ch

/**
 *   use t  flow for all post-nux d splay locat ons that would use a mach ne-learn ng-based-ranker
 * eg HTL, S debar, etc
 * Note that t  RankedPostNuxFlow  s used pr mar ly for scr b ng/data collect on, and doesn't
 *  ncorporate all of t  ot r components  n a flow (cand date s ce generat on, pred cates etc)
 */
@S ngleton
class PostNuxMlFlow @ nject() (
  postNuxMlCand dateS ceReg stry: PostNuxMlCand dateS ceReg stry,
  postNuxMlComb nedRankerBu lder: PostNuxMlComb nedRankerBu lder[PostNuxMlRequest],
  curatedCompet orL stPred cate: CuratedCompet orL stPred cate,
  g zmoduckPred cate: G zmoduckPred cate,
  sgsPred cate:  nval dTargetCand dateRelat onsh pTypesPred cate,
  hssPred cate: HssPred cate,
   nval dRelat onsh pPred cate:  nval dRelat onsh pPred cate,
  recentFollow ngPred cate: RecentFollow ngPred cate,
  nonNearZeroUserAct v yPred cate: NonNearZeroUserAct v yPred cate,
   nact vePred cate:  nact vePred cate,
  d sm ssedCand datePred cate: D sm ssedCand datePred cate,
  prev ouslyRecom ndedUser dsPred cate: Prev ouslyRecom ndedUser dsPred cate,
  mod fySoc alProofTransform: Mod fySoc alProofTransform,
  removeAccountProofTransform: RemoveAccountProofTransform,
  track ngTokenTransform: Track ngTokenTransform,
  randomRanker dTransform: RandomRanker dTransform,
  cand dateParamsFactory: Cand dateUserParamsFactory[PostNuxMlRequest],
  sampl ngTransform: Sampl ngTransform,
  frsLogger: FrsLogger,
  baseStatsRece ver: StatsRece ver)
    extends Recom ndat onFlow[PostNuxMlRequest, Cand dateUser]
    w h Recom ndat onFlowBaseS deEffectsUt l[PostNuxMlRequest, Cand dateUser]
    w h Cand dateS ceHoldbackUt l {
  overr de protected val targetEl g b l y: Pred cate[PostNuxMlRequest] =
    new ParamPred cate[PostNuxMlRequest](PostNuxMlParams.TargetEl g b l y)

  overr de val statsRece ver: StatsRece ver = baseStatsRece ver.scope("post_nux_ml_flow")

  overr de val qual yFactorObserver: Opt on[Qual yFactorObserver] = {
    val conf g = L nearLatencyQual yFactorConf g(
      qual yFactorBounds =
        BoundsW hDefault(m n nclus ve = 0.1, max nclus ve = 1.0, default = 1.0),
       n  alDelay = 60.seconds,
      targetLatency = 700.m ll seconds,
      targetLatencyPercent le = 95.0,
      delta = 0.001
    )
    val qual yFactor = L nearLatencyQual yFactor(conf g)
    val observer = L nearLatencyQual yFactorObserver(qual yFactor)
    statsRece ver.prov deGauge("qual y_factor")(qual yFactor.currentValue.toFloat)
    So (observer)
  }

  overr de protected def updateTarget(request: PostNuxMlRequest): St ch[PostNuxMlRequest] = {
    St ch.value(
      request.copy(qual yFactor = qual yFactorObserver.map(_.qual yFactor.currentValue))
    )
  }

  pr vate[post_nux_ml] def getCand dateS ce dent f ers(
    params: Params
  ): Set[Cand dateS ce dent f er] = {
    PostNuxMlFlowCand dateS ce  ghts.get  ghts(params).keySet
  }

  overr de protected def cand dateS ces(
    request: PostNuxMlRequest
  ): Seq[Cand dateS ce[PostNuxMlRequest, Cand dateUser]] = {
    val  dent f ers = getCand dateS ce dent f ers(request.params)
    val selected: Set[Cand dateS ce[PostNuxMlRequest, Cand dateUser]] =
      postNuxMlCand dateS ceReg stry.select( dent f ers)
    val budget: Durat on = request.params(PostNuxMlParams.FetchCand dateS ceBudget)
    f lterCand dateS ces(
      request,
      selected.map(c => c.fa lOpenW h n(budget, statsRece ver)).toSeq)
  }

  overr de protected val preRankerCand dateF lter: Pred cate[(PostNuxMlRequest, Cand dateUser)] = {
    val stats = statsRece ver.scope("pre_ranker")

    object excludeNearZeroUserPred cate
        extends GatedPred cateBase[(PostNuxMlRequest, Cand dateUser)](
          nonNearZeroUserAct v yPred cate,
          stats.scope("exclude_near_zero_pred cate")
        ) {
      overr de def gate( em: (PostNuxMlRequest, Cand dateUser)): Boolean =
         em._1.params(PostNuxMlParams.ExcludeNearZeroCand dates)
    }

    object  nval dRelat onsh pGatedPred cate
        extends GatedPred cateBase[(PostNuxMlRequest, Cand dateUser)](
           nval dRelat onsh pPred cate,
          stats.scope(" nval d_relat onsh p_pred cate")
        ) {
      overr de def gate( em: (PostNuxMlRequest, Cand dateUser)): Boolean =
         em._1.params(PostNuxMlParams.Enable nval dRelat onsh pPred cate)
    }

    ExcludedUser dPred cate
      .observe(stats.scope("exclude_user_ d_pred cate"))
      .andT n(
        recentFollow ngPred cate.observe(stats.scope("recent_follow ng_pred cate"))
      )
      .andT n(
        d sm ssedCand datePred cate.observe(stats.scope("d sm ssed_cand date_pred cate"))
      )
      .andT n(
        prev ouslyRecom ndedUser dsPred cate.observe(
          stats.scope("prev ously_recom nded_user_ ds_pred cate"))
      )
      .andT n(
         nval dRelat onsh pGatedPred cate.observe(stats.scope(" nval d_relat onsh p_pred cate"))
      )
      .andT n(
        excludeNearZeroUserPred cate.observe(stats.scope("exclude_near_zero_user_state"))
      )
      .observe(stats.scope("overall_pre_ranker_cand date_f lter"))
  }

  overr de protected def selectRanker(
    request: PostNuxMlRequest
  ): Ranker[PostNuxMlRequest, Cand dateUser] = {
    postNuxMlComb nedRankerBu lder.bu ld(
      request,
      PostNuxMlFlowCand dateS ce  ghts.get  ghts(request.params))
  }

  overr de protected val postRankerTransform: Transform[PostNuxMlRequest, Cand dateUser] = {
    new DedupTransform[PostNuxMlRequest, Cand dateUser]
      .observe(statsRece ver.scope("dedupp ng"))
      .andT n(
        sampl ngTransform
          .gated(PostNuxMlParams.Sampl ngTransformEnabled)
          .observe(statsRece ver.scope("sampl ngtransform")))
  }

  overr de protected val val dateCand dates: Pred cate[(PostNuxMlRequest, Cand dateUser)] = {
    val stats = statsRece ver.scope("val date_cand dates")
    val compet orPred cate =
      curatedCompet orL stPred cate.map[(PostNuxMlRequest, Cand dateUser)](_._2)

    val producerHoldbackPred cate = new Cand dateParamPred cate[Cand dateUser](
      GlobalParams.KeepUserCand date,
      F lterReason.Cand dateS deHoldback
    ).map[(PostNuxMlRequest, Cand dateUser)] {
      case (request, user) => cand dateParamsFactory(user, request)
    }
    val pymkProducerHoldbackPred cate = new Cand dateS ceParamPred cate(
      GlobalParams.KeepSoc alUserCand date,
      F lterReason.Cand dateS deHoldback,
      Cand dateS ceHoldbackUt l.Soc alCand dateS ce ds
    ).map[(PostNuxMlRequest, Cand dateUser)] {
      case (request, user) => cand dateParamsFactory(user, request)
    }
    val sgsPred cateStats = stats.scope("sgs_pred cate")
    object sgsGatedPred cate
        extends GatedPred cateBase[(PostNuxMlRequest, Cand dateUser)](
          sgsPred cate.observe(sgsPred cateStats),
          sgsPred cateStats
        ) {

      /**
       * W n SGS pred cate  s turned off, only query SGS ex sts AP  for (user, cand date, relat onsh p)
       * w n t  user's number of  nval d relat onsh ps exceeds t  threshold dur ng request
       * bu ld ng step. T   s to m n m ze load to SGS and underly ng Flock DB.
       */
      overr de def gate( em: (PostNuxMlRequest, Cand dateUser)): Boolean =
         em._1.params(PostNuxMlParams.EnableSGSPred cate) ||
          Soc alGraphCl ent.enablePostRankerSgsPred cate(
             em._1. nval dRelat onsh pUser ds.getOrElse(Set.empty).s ze)
    }

    val hssPred cateStats = stats.scope("hss_pred cate")
    object hssGatedPred cate
        extends GatedPred cateBase[(PostNuxMlRequest, Cand dateUser)](
          hssPred cate.observe(hssPred cateStats),
          hssPred cateStats
        ) {
      overr de def gate( em: (PostNuxMlRequest, Cand dateUser)): Boolean =
         em._1.params(PostNuxMlParams.EnableHssPred cate)
    }

    Pred cate
      .andConcurrently[(PostNuxMlRequest, Cand dateUser)](
        Seq(
          compet orPred cate.observe(stats.scope("curated_compet or_pred cate")),
          g zmoduckPred cate.observe(stats.scope("g zmoduck_pred cate")),
          sgsGatedPred cate,
          hssGatedPred cate,
           nact vePred cate.observe(stats.scope(" nact ve_pred cate")),
        )
      )
      // to avo d d lut ons,   need to apply t  rece ver holdback pred cates at t  very last step
      .andT n(pymkProducerHoldbackPred cate.observe(stats.scope("pymk_rece ver_s de_holdback")))
      .andT n(producerHoldbackPred cate.observe(stats.scope("rece ver_s de_holdback")))
      .observe(stats.scope("overall_val date_cand dates"))
  }

  overr de protected val transformResults: Transform[PostNuxMlRequest, Cand dateUser] = {
    mod fySoc alProofTransform
      .gated(EnableGFSSoc alProofTransform)
      .andT n(track ngTokenTransform)
      .andT n(randomRanker dTransform.gated(PostNuxMlParams.LogRandomRanker d))
      .andT n(removeAccountProofTransform.gated(PostNuxMlParams.EnableRemoveAccountProofTransform))
  }

  overr de protected def resultsConf g(request: PostNuxMlRequest): Recom ndat onResultsConf g = {
    Recom ndat onResultsConf g(
      request.maxResults.getOrElse(request.params(PostNuxMlParams.ResultS zeParam)),
      request.params(PostNuxMlParams.BatchS zeParam)
    )
  }

  overr de def applyS deEffects(
    target: PostNuxMlRequest,
    cand dateS ces: Seq[Cand dateS ce[PostNuxMlRequest, Cand dateUser]],
    cand datesFromCand dateS ces: Seq[Cand dateUser],
     rgedCand dates: Seq[Cand dateUser],
    f lteredCand dates: Seq[Cand dateUser],
    rankedCand dates: Seq[Cand dateUser],
    transfor dCand dates: Seq[Cand dateUser],
    truncatedCand dates: Seq[Cand dateUser],
    results: Seq[Cand dateUser]
  ): St ch[Un ] = {
    frsLogger.logRecom ndat onFlowData[PostNuxMlRequest](
      target,
      Recom ndat onFlowData[PostNuxMlRequest](
        target,
        PostNuxMlFlow. dent f er,
        cand dateS ces,
        cand datesFromCand dateS ces,
         rgedCand dates,
        f lteredCand dates,
        rankedCand dates,
        transfor dCand dates,
        truncatedCand dates,
        results
      )
    )
    super.applyS deEffects(
      target,
      cand dateS ces,
      cand datesFromCand dateS ces,
       rgedCand dates,
      f lteredCand dates,
      rankedCand dates,
      transfor dCand dates,
      truncatedCand dates,
      results
    )
  }
}

object PostNuxMlFlow {
  val  dent f er = Recom ndat onP pel ne dent f er("PostNuxMlFlow")
}
