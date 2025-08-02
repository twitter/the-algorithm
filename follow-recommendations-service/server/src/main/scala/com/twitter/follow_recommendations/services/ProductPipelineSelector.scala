package com.twitter.follow_recommendations.services

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.base.StatsUtil
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.DebugOptions
import com.twitter.follow_recommendations.models.DebugParams
import com.twitter.follow_recommendations.models.RecommendationRequest
import com.twitter.follow_recommendations.models.RecommendationResponse
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Params
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.util.Random

@Singleton
class ProductPipelineSelector @Inject() (
  recommendationsService: RecommendationsService,
  productMitwittererRecommendationService: ProductMitwittererRecommendationService,
  productPipelineSelectorConfig: ProductPipelineSelectorConfig,
  baseStats: StatsReceiver) {

  private val frsStats = baseStats.scope("follow_recommendations_service")
  private val stats = frsStats.scope("product_pipeline_selector_parity")

  private val readFromProductMitwittererCounter = stats.counter("select_product_mitwitterer")
  private val readFromOldFRSCounter = stats.counter("select_old_frs")

  def selectPipeline(
    request: RecommendationRequest,
    params: Params
  ): Stitch[RecommendationResponse] = {
    productPipelineSelectorConfig
      .getDarkReadAndEtwitterpParams(request.displayLocation).map { darkReadAndEtwitterpParam =>
        if (params(darkReadAndEtwitterpParam.etwitterpParam)) {
          readFromProductMitwittererPipeline(request, params)
        } else if (params(darkReadAndEtwitterpParam.darkReadParam)) {
          darkReadAndReturnResult(request, params)
        } else {
          readFromOldFrsPipeline(request, params)
        }
      }.getOrElse(readFromOldFrsPipeline(request, params))
  }

  private def readFromProductMitwittererPipeline(
    request: RecommendationRequest,
    params: Params
  ): Stitch[RecommendationResponse] = {
    readFromProductMitwittererCounter.incr()
    productMitwittererRecommendationService.get(request, params)
  }

  private def readFromOldFrsPipeline(
    request: RecommendationRequest,
    params: Params
  ): Stitch[RecommendationResponse] = {
    readFromOldFRSCounter.incr()
    recommendationsService.get(request, params)
  }

  private def darkReadAndReturnResult(
    request: RecommendationRequest,
    params: Params
  ): Stitch[RecommendationResponse] = {
    val darkReadStats = stats.scope("select_dark_read", request.displayLocation.toFsName)
    darkReadStats.counter("count").incr()

    // If no seed is set, create a random one that both requests will use to remove differences
    // in randomness for the WeightedCandidateSourceRanker
    val randomizationSeed = new Random().netwittertLong()

    val oldFRSPiplelineRequest = request.copy(
      debugParams = Some(
        request.debugParams.getOrElse(
          DebugParams(None, Some(DebugOptions(randomizationSeed = Some(randomizationSeed))))))
    )
    val productMitwittererPipelineRequest = request.copy(
      debugParams = Some(
        request.debugParams.getOrElse(
          DebugParams(
            None,
            Some(DebugOptions(doNotLog = true, randomizationSeed = Some(randomizationSeed))))))
    )

    StatsUtil
      .profileStitch(
        readFromOldFrsPipeline(oldFRSPiplelineRequest, params),
        darkReadStats.scope("frs_timing")).applyEffect { frsOldPipelineResponse =>
        Stitch.async(
          StatsUtil
            .profileStitch(
              readFromProductMitwittererPipeline(productMitwittererPipelineRequest, params),
              darkReadStats.scope("product_mitwitterer_timing")).liftToOption().map {
              case Some(frsProductMitwittererResponse) =>
                darkReadStats.counter("product_mitwitterer_pipeline_success").incr()
                compare(request, frsOldPipelineResponse, frsProductMitwittererResponse)
              case None =>
                darkReadStats.counter("product_mitwitterer_pipeline_failure").incr()
            }
        )
      }
  }

  def compare(
    request: RecommendationRequest,
    frsOldPipelineResponse: RecommendationResponse,
    frsProductMitwittererResponse: RecommendationResponse
  ): Unit = {
    val compareStats = stats.scope("pipeline_comparison", request.displayLocation.toFsName)
    compareStats.counter("total-comparisons").incr()

    val oldFrsMap = frsOldPipelineResponse.recommendations.map { user => user.id -> user }.toMap
    val productMitwittererMap = frsProductMitwittererResponse.recommendations.map { user =>
      user.id -> user
    }.toMap

    compareTopNResults(3, frsOldPipelineResponse, frsProductMitwittererResponse, compareStats)
    compareTopNResults(5, frsOldPipelineResponse, frsProductMitwittererResponse, compareStats)
    compareTopNResults(25, frsOldPipelineResponse, frsProductMitwittererResponse, compareStats)
    compareTopNResults(50, frsOldPipelineResponse, frsProductMitwittererResponse, compareStats)
    compareTopNResults(75, frsOldPipelineResponse, frsProductMitwittererResponse, compareStats)

    // Compare individual matching candidates
    oldFrsMap.keys.foreach(userId => {
      if (productMitwittererMap.contains(userId)) {
        (oldFrsMap(userId), productMitwittererMap(userId)) match {
          case (oldFrsUser: CandidateUser, productMitwittererUser: CandidateUser) =>
            compareStats.counter("matching-user-count").incr()
            compareUser(oldFrsUser, productMitwittererUser, compareStats)
          case _ =>
            compareStats.counter("unknown-user-type-count").incr()
        }
      } else {
        compareStats.counter("missing-user-count").incr()
      }
    })
  }

  private def compareTopNResults(
    n: Int,
    frsOldPipelineResponse: RecommendationResponse,
    frsProductMitwittererResponse: RecommendationResponse,
    compareStats: StatsReceiver
  ): Unit = {
    if (frsOldPipelineResponse.recommendations.size >= n && frsProductMitwittererResponse.recommendations.size >= n) {
      val oldFrsPipelineFirstN = frsOldPipelineResponse.recommendations.take(n).map(_.id)
      val productMitwittererPipelineFirstN = frsProductMitwittererResponse.recommendations.take(n).map(_.id)

      if (oldFrsPipelineFirstN.sorted == productMitwittererPipelineFirstN.sorted)
        compareStats.counter(s"first-$n-sorted-equal-ids").incr()
      if (oldFrsPipelineFirstN == productMitwittererPipelineFirstN)
        compareStats.counter(s"first-$n-unsorted-ids-equal").incr()
      else
        compareStats.counter(s"first-$n-unsorted-ids-unequal").incr()
    }
  }

  private def compareUser(
    oldFrsUser: CandidateUser,
    productMitwittererUser: CandidateUser,
    stats: StatsReceiver
  ): Unit = {
    val userStats = stats.scope("matching-user")

    if (oldFrsUser.score != productMitwittererUser.score)
      userStats.counter("mismatch-score").incr()
    if (oldFrsUser.reason != productMitwittererUser.reason)
      userStats.counter("mismatch-reason").incr()
    if (oldFrsUser.userCandidateSourceDetails != productMitwittererUser.userCandidateSourceDetails)
      userStats.counter("mismatch-userCandidateSourceDetails").incr()
    if (oldFrsUser.adMetadata != productMitwittererUser.adMetadata)
      userStats.counter("mismatch-adMetadata").incr()
    if (oldFrsUser.trackingToken != productMitwittererUser.trackingToken)
      userStats.counter("mismatch-trackingToken").incr()
    if (oldFrsUser.dataRecord != productMitwittererUser.dataRecord)
      userStats.counter("mismatch-dataRecord").incr()
    if (oldFrsUser.scores != productMitwittererUser.scores)
      userStats.counter("mismatch-scores").incr()
    if (oldFrsUser.infoPerRankingStage != productMitwittererUser.infoPerRankingStage)
      userStats.counter("mismatch-infoPerRankingStage").incr()
    if (oldFrsUser.params != productMitwittererUser.params)
      userStats.counter("mismatch-params").incr()
    if (oldFrsUser.engagements != productMitwittererUser.engagements)
      userStats.counter("mismatch-engagements").incr()
    if (oldFrsUser.recommendationFlowIdentifier != productMitwittererUser.recommendationFlowIdentifier)
      userStats.counter("mismatch-recommendationFlowIdentifier").incr()
  }
}
