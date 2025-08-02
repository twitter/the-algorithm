package com.twitter.follow_recommendations.flows.post_nutwitter_ml

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.base.EnrichedCandidateSource._
import com.twitter.follow_recommendations.common.base._
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.FilterReason
import com.twitter.follow_recommendations.common.predicates.dismiss.DismissedCandidatePredicate
import com.twitter.follow_recommendations.common.predicates.gizmoduck.GizmoduckPredicate
import com.twitter.follow_recommendations.common.transforms.ranker_id.RandomRankerIdTransform
import com.twitter.follow_recommendations.common.predicates.sgs.InvalidTargetCandidateRelationshipTypesPredicate
import com.twitter.follow_recommendations.common.predicates.sgs.RecentFollowingPredicate
import com.twitter.follow_recommendations.common.predicates.CandidateParamPredicate
import com.twitter.follow_recommendations.common.predicates.CandidateSourceParamPredicate
import com.twitter.follow_recommendations.common.predicates.CuratedCompetitorListPredicate
import com.twitter.follow_recommendations.common.predicates.EtwittercludedUserIdPredicate
import com.twitter.follow_recommendations.common.predicates.InactivePredicate
import com.twitter.follow_recommendations.common.predicates.PreviouslyRecommendedUserIdsPredicate
import com.twitter.follow_recommendations.common.predicates.user_activity.NonNearZeroUserActivityPredicate
import com.twitter.follow_recommendations.common.transforms.dedup.DedupTransform
import com.twitter.follow_recommendations.common.transforms.modify_social_proof.ModifySocialProofTransform
import com.twitter.follow_recommendations.common.transforms.tracking_token.TrackingTokenTransform
import com.twitter.follow_recommendations.common.transforms.weighted_sampling.SamplingTransform
import com.twitter.follow_recommendations.configapi.candidates.CandidateUserParamsFactory
import com.twitter.follow_recommendations.configapi.params.GlobalParams
import com.twitter.follow_recommendations.configapi.params.GlobalParams.EnableGFSSocialProofTransform
import com.twitter.follow_recommendations.utils.CandidateSourceHoldbackUtil
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.timelines.configapi.Params
import com.twitter.util.Duration

import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.follow_recommendations.common.clients.socialgraph.SocialGraphClient
import com.twitter.follow_recommendations.common.predicates.hss.HssPredicate
import com.twitter.follow_recommendations.common.predicates.sgs.InvalidRelationshipPredicate
import com.twitter.follow_recommendations.common.transforms.modify_social_proof.RemoveAccountProofTransform
import com.twitter.follow_recommendations.logging.FrsLogger
import com.twitter.follow_recommendations.models.RecommendationFlowData
import com.twitter.follow_recommendations.utils.RecommendationFlowBaseSideEffectsUtil
import com.twitter.product_mitwitterer.core.model.common.identifier.RecommendationPipelineIdentifier
import com.twitter.product_mitwitterer.core.quality_factor.BoundsWithDefault
import com.twitter.product_mitwitterer.core.quality_factor.LinearLatencyQualityFactor
import com.twitter.product_mitwitterer.core.quality_factor.LinearLatencyQualityFactorConfig
import com.twitter.product_mitwitterer.core.quality_factor.LinearLatencyQualityFactorObserver
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorObserver
import com.twitter.stitch.Stitch

/**
 * We use this flow for all post-nutwitter display locations that would use a machine-learning-based-ranker
 * eg HTL, Sidebar, etc
 * Note that the RankedPostNutwitterFlow is used primarily for scribing/data collection, and doesn't
 * incorporate all of the other components in a flow (candidate source generation, predicates etc)
 */
@Singleton
class PostNutwitterMlFlow @Inject() (
  postNutwitterMlCandidateSourceRegistry: PostNutwitterMlCandidateSourceRegistry,
  postNutwitterMlCombinedRankerBuilder: PostNutwitterMlCombinedRankerBuilder[PostNutwitterMlRequest],
  curatedCompetitorListPredicate: CuratedCompetitorListPredicate,
  gizmoduckPredicate: GizmoduckPredicate,
  sgsPredicate: InvalidTargetCandidateRelationshipTypesPredicate,
  hssPredicate: HssPredicate,
  invalidRelationshipPredicate: InvalidRelationshipPredicate,
  recentFollowingPredicate: RecentFollowingPredicate,
  nonNearZeroUserActivityPredicate: NonNearZeroUserActivityPredicate,
  inactivePredicate: InactivePredicate,
  dismissedCandidatePredicate: DismissedCandidatePredicate,
  previouslyRecommendedUserIdsPredicate: PreviouslyRecommendedUserIdsPredicate,
  modifySocialProofTransform: ModifySocialProofTransform,
  removeAccountProofTransform: RemoveAccountProofTransform,
  trackingTokenTransform: TrackingTokenTransform,
  randomRankerIdTransform: RandomRankerIdTransform,
  candidateParamsFactory: CandidateUserParamsFactory[PostNutwitterMlRequest],
  samplingTransform: SamplingTransform,
  frsLogger: FrsLogger,
  baseStatsReceiver: StatsReceiver)
    etwittertends RecommendationFlow[PostNutwitterMlRequest, CandidateUser]
    with RecommendationFlowBaseSideEffectsUtil[PostNutwitterMlRequest, CandidateUser]
    with CandidateSourceHoldbackUtil {
  override protected val targetEligibility: Predicate[PostNutwitterMlRequest] =
    new ParamPredicate[PostNutwitterMlRequest](PostNutwitterMlParams.TargetEligibility)

  override val statsReceiver: StatsReceiver = baseStatsReceiver.scope("post_nutwitter_ml_flow")

  override val qualityFactorObserver: Option[QualityFactorObserver] = {
    val config = LinearLatencyQualityFactorConfig(
      qualityFactorBounds =
        BoundsWithDefault(minInclusive = 0.1, matwitterInclusive = 1.0, default = 1.0),
      initialDelay = 60.seconds,
      targetLatency = 700.milliseconds,
      targetLatencyPercentile = 95.0,
      delta = 0.001
    )
    val qualityFactor = LinearLatencyQualityFactor(config)
    val observer = LinearLatencyQualityFactorObserver(qualityFactor)
    statsReceiver.provideGauge("quality_factor")(qualityFactor.currentValue.toFloat)
    Some(observer)
  }

  override protected def updateTarget(request: PostNutwitterMlRequest): Stitch[PostNutwitterMlRequest] = {
    Stitch.value(
      request.copy(qualityFactor = qualityFactorObserver.map(_.qualityFactor.currentValue))
    )
  }

  private[post_nutwitter_ml] def getCandidateSourceIdentifiers(
    params: Params
  ): Set[CandidateSourceIdentifier] = {
    PostNutwitterMlFlowCandidateSourceWeights.getWeights(params).keySet
  }

  override protected def candidateSources(
    request: PostNutwitterMlRequest
  ): Seq[CandidateSource[PostNutwitterMlRequest, CandidateUser]] = {
    val identifiers = getCandidateSourceIdentifiers(request.params)
    val selected: Set[CandidateSource[PostNutwitterMlRequest, CandidateUser]] =
      postNutwitterMlCandidateSourceRegistry.select(identifiers)
    val budget: Duration = request.params(PostNutwitterMlParams.FetchCandidateSourceBudget)
    filterCandidateSources(
      request,
      selected.map(c => c.failOpenWithin(budget, statsReceiver)).toSeq)
  }

  override protected val preRankerCandidateFilter: Predicate[(PostNutwitterMlRequest, CandidateUser)] = {
    val stats = statsReceiver.scope("pre_ranker")

    object etwittercludeNearZeroUserPredicate
        etwittertends GatedPredicateBase[(PostNutwitterMlRequest, CandidateUser)](
          nonNearZeroUserActivityPredicate,
          stats.scope("etwitterclude_near_zero_predicate")
        ) {
      override def gate(item: (PostNutwitterMlRequest, CandidateUser)): Boolean =
        item._1.params(PostNutwitterMlParams.EtwittercludeNearZeroCandidates)
    }

    object invalidRelationshipGatedPredicate
        etwittertends GatedPredicateBase[(PostNutwitterMlRequest, CandidateUser)](
          invalidRelationshipPredicate,
          stats.scope("invalid_relationship_predicate")
        ) {
      override def gate(item: (PostNutwitterMlRequest, CandidateUser)): Boolean =
        item._1.params(PostNutwitterMlParams.EnableInvalidRelationshipPredicate)
    }

    EtwittercludedUserIdPredicate
      .observe(stats.scope("etwitterclude_user_id_predicate"))
      .andThen(
        recentFollowingPredicate.observe(stats.scope("recent_following_predicate"))
      )
      .andThen(
        dismissedCandidatePredicate.observe(stats.scope("dismissed_candidate_predicate"))
      )
      .andThen(
        previouslyRecommendedUserIdsPredicate.observe(
          stats.scope("previously_recommended_user_ids_predicate"))
      )
      .andThen(
        invalidRelationshipGatedPredicate.observe(stats.scope("invalid_relationship_predicate"))
      )
      .andThen(
        etwittercludeNearZeroUserPredicate.observe(stats.scope("etwitterclude_near_zero_user_state"))
      )
      .observe(stats.scope("overall_pre_ranker_candidate_filter"))
  }

  override protected def selectRanker(
    request: PostNutwitterMlRequest
  ): Ranker[PostNutwitterMlRequest, CandidateUser] = {
    postNutwitterMlCombinedRankerBuilder.build(
      request,
      PostNutwitterMlFlowCandidateSourceWeights.getWeights(request.params))
  }

  override protected val postRankerTransform: Transform[PostNutwitterMlRequest, CandidateUser] = {
    new DedupTransform[PostNutwitterMlRequest, CandidateUser]
      .observe(statsReceiver.scope("dedupping"))
      .andThen(
        samplingTransform
          .gated(PostNutwitterMlParams.SamplingTransformEnabled)
          .observe(statsReceiver.scope("samplingtransform")))
  }

  override protected val validateCandidates: Predicate[(PostNutwitterMlRequest, CandidateUser)] = {
    val stats = statsReceiver.scope("validate_candidates")
    val competitorPredicate =
      curatedCompetitorListPredicate.map[(PostNutwitterMlRequest, CandidateUser)](_._2)

    val producerHoldbackPredicate = new CandidateParamPredicate[CandidateUser](
      GlobalParams.KeepUserCandidate,
      FilterReason.CandidateSideHoldback
    ).map[(PostNutwitterMlRequest, CandidateUser)] {
      case (request, user) => candidateParamsFactory(user, request)
    }
    val pymkProducerHoldbackPredicate = new CandidateSourceParamPredicate(
      GlobalParams.KeepSocialUserCandidate,
      FilterReason.CandidateSideHoldback,
      CandidateSourceHoldbackUtil.SocialCandidateSourceIds
    ).map[(PostNutwitterMlRequest, CandidateUser)] {
      case (request, user) => candidateParamsFactory(user, request)
    }
    val sgsPredicateStats = stats.scope("sgs_predicate")
    object sgsGatedPredicate
        etwittertends GatedPredicateBase[(PostNutwitterMlRequest, CandidateUser)](
          sgsPredicate.observe(sgsPredicateStats),
          sgsPredicateStats
        ) {

      /**
       * When SGS predicate is turned off, only query SGS etwitterists API for (user, candidate, relationship)
       * when the user's number of invalid relationships etwitterceeds the threshold during request
       * building step. This is to minimize load to SGS and underlying Flock DB.
       */
      override def gate(item: (PostNutwitterMlRequest, CandidateUser)): Boolean =
        item._1.params(PostNutwitterMlParams.EnableSGSPredicate) ||
          SocialGraphClient.enablePostRankerSgsPredicate(
            item._1.invalidRelationshipUserIds.getOrElse(Set.empty).size)
    }

    val hssPredicateStats = stats.scope("hss_predicate")
    object hssGatedPredicate
        etwittertends GatedPredicateBase[(PostNutwitterMlRequest, CandidateUser)](
          hssPredicate.observe(hssPredicateStats),
          hssPredicateStats
        ) {
      override def gate(item: (PostNutwitterMlRequest, CandidateUser)): Boolean =
        item._1.params(PostNutwitterMlParams.EnableHssPredicate)
    }

    Predicate
      .andConcurrently[(PostNutwitterMlRequest, CandidateUser)](
        Seq(
          competitorPredicate.observe(stats.scope("curated_competitor_predicate")),
          gizmoduckPredicate.observe(stats.scope("gizmoduck_predicate")),
          sgsGatedPredicate,
          hssGatedPredicate,
          inactivePredicate.observe(stats.scope("inactive_predicate")),
        )
      )
      // to avoid dilutions, we need to apply the receiver holdback predicates at the very last step
      .andThen(pymkProducerHoldbackPredicate.observe(stats.scope("pymk_receiver_side_holdback")))
      .andThen(producerHoldbackPredicate.observe(stats.scope("receiver_side_holdback")))
      .observe(stats.scope("overall_validate_candidates"))
  }

  override protected val transformResults: Transform[PostNutwitterMlRequest, CandidateUser] = {
    modifySocialProofTransform
      .gated(EnableGFSSocialProofTransform)
      .andThen(trackingTokenTransform)
      .andThen(randomRankerIdTransform.gated(PostNutwitterMlParams.LogRandomRankerId))
      .andThen(removeAccountProofTransform.gated(PostNutwitterMlParams.EnableRemoveAccountProofTransform))
  }

  override protected def resultsConfig(request: PostNutwitterMlRequest): RecommendationResultsConfig = {
    RecommendationResultsConfig(
      request.matwitterResults.getOrElse(request.params(PostNutwitterMlParams.ResultSizeParam)),
      request.params(PostNutwitterMlParams.BatchSizeParam)
    )
  }

  override def applySideEffects(
    target: PostNutwitterMlRequest,
    candidateSources: Seq[CandidateSource[PostNutwitterMlRequest, CandidateUser]],
    candidatesFromCandidateSources: Seq[CandidateUser],
    mergedCandidates: Seq[CandidateUser],
    filteredCandidates: Seq[CandidateUser],
    rankedCandidates: Seq[CandidateUser],
    transformedCandidates: Seq[CandidateUser],
    truncatedCandidates: Seq[CandidateUser],
    results: Seq[CandidateUser]
  ): Stitch[Unit] = {
    frsLogger.logRecommendationFlowData[PostNutwitterMlRequest](
      target,
      RecommendationFlowData[PostNutwitterMlRequest](
        target,
        PostNutwitterMlFlow.identifier,
        candidateSources,
        candidatesFromCandidateSources,
        mergedCandidates,
        filteredCandidates,
        rankedCandidates,
        transformedCandidates,
        truncatedCandidates,
        results
      )
    )
    super.applySideEffects(
      target,
      candidateSources,
      candidatesFromCandidateSources,
      mergedCandidates,
      filteredCandidates,
      rankedCandidates,
      transformedCandidates,
      truncatedCandidates,
      results
    )
  }
}

object PostNutwitterMlFlow {
  val identifier = RecommendationPipelineIdentifier("PostNutwitterMlFlow")
}
