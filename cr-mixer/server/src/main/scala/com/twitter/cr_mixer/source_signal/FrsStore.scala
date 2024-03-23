package com.ExTwitter.cr_mixer.source_signal

import com.ExTwitter.cr_mixer.param.decider.CrMixerDecider
import com.ExTwitter.cr_mixer.param.decider.DeciderConstants
import com.ExTwitter.cr_mixer.source_signal.FrsStore.Query
import com.ExTwitter.cr_mixer.source_signal.FrsStore.FrsQueryResult
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.thriftscala.ClientContext
import com.ExTwitter.follow_recommendations.thriftscala.DisplayLocation
import com.ExTwitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.ExTwitter.follow_recommendations.thriftscala.Recommendation
import com.ExTwitter.follow_recommendations.thriftscala.RecommendationRequest
import com.ExTwitter.storehaus.ReadableStore
import javax.inject.Singleton
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.util.Future

@Singleton
case class FrsStore(
  frsClient: FollowRecommendationsThriftService.MethodPerEndpoint,
  statsReceiver: StatsReceiver,
  decider: CrMixerDecider)
    extends ReadableStore[Query, Seq[FrsQueryResult]] {

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
      clientContext = ClientContext(
        userId = Some(query.userId),
        countryCode = query.countryCodeOpt,
        languageCode = query.languageCodeOpt),
      displayLocation = query.displayLocation,
      maxResults = Some(query.maxConsumerSeedsNum),
      excludedIds = Some(query.excludedUserIds)
    )
  }
}

object FrsStore {
  case class Query(
    userId: UserId,
    maxConsumerSeedsNum: Int,
    displayLocation: DisplayLocation = DisplayLocation.ContentRecommender,
    excludedUserIds: Seq[UserId] = Seq.empty,
    languageCodeOpt: Option[String] = None,
    countryCodeOpt: Option[String] = None)

  case class FrsQueryResult(
    userId: UserId,
    score: Double,
    primarySource: Option[Int],
    sourceWithScores: Option[Map[String, Double]])
}
