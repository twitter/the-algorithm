package com.twitter.cr_mitwitterer.source_signal

import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.param.decider.DeciderConstants
import com.twitter.cr_mitwitterer.source_signal.FrsStore.Query
import com.twitter.cr_mitwitterer.source_signal.FrsStore.FrsQueryResult
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.thriftscala.ClientContetwittert
import com.twitter.follow_recommendations.thriftscala.DisplayLocation
import com.twitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.twitter.follow_recommendations.thriftscala.Recommendation
import com.twitter.follow_recommendations.thriftscala.RecommendationRequest
import com.twitter.storehaus.ReadableStore
import javatwitter.inject.Singleton
import com.twitter.simclusters_v2.common.UserId
import com.twitter.util.Future

@Singleton
case class FrsStore(
  frsClient: FollowRecommendationsThriftService.MethodPerEndpoint,
  statsReceiver: StatsReceiver,
  decider: CrMitwittererDecider)
    etwittertends ReadableStore[Query, Seq[FrsQueryResult]] {

  override def get(
    query: Query
  ): Future[Option[Seq[FrsQueryResult]]] = {
    if (decider.isAvailable(DeciderConstants.enableFRSTrafficDeciderKey)) {
      val recommendationRequest =
        buildFollowRecommendationRequest(query)

      frsClient
        .getRecommendations(recommendationRequest).map { recommendationResponse =>
          Some(recommendationResponse.recommendations.collect {
            case recommendation: Recommendation.User =>
              FrsQueryResult(
                recommendation.user.userId,
                recommendation.user.scoringDetails
                  .flatMap(_.score).getOrElse(0.0),
                recommendation.user.scoringDetails
                  .flatMap(_.candidateSourceDetails.flatMap(_.primarySource)),
                recommendation.user.scoringDetails
                  .flatMap(_.candidateSourceDetails.flatMap(_.candidateSourceScores)).map(_.toMap)
              )
          })
        }
    } else {
      Future.None
    }
  }

  private def buildFollowRecommendationRequest(
    query: Query
  ): RecommendationRequest = {
    RecommendationRequest(
      clientContetwittert = ClientContetwittert(
        userId = Some(query.userId),
        countryCode = query.countryCodeOpt,
        languageCode = query.languageCodeOpt),
      displayLocation = query.displayLocation,
      matwitterResults = Some(query.matwitterConsumerSeedsNum),
      etwittercludedIds = Some(query.etwittercludedUserIds)
    )
  }
}

object FrsStore {
  case class Query(
    userId: UserId,
    matwitterConsumerSeedsNum: Int,
    displayLocation: DisplayLocation = DisplayLocation.ContentRecommender,
    etwittercludedUserIds: Seq[UserId] = Seq.empty,
    languageCodeOpt: Option[String] = None,
    countryCodeOpt: Option[String] = None)

  case class FrsQueryResult(
    userId: UserId,
    score: Double,
    primarySource: Option[Int],
    sourceWithScores: Option[Map[String, Double]])
}
