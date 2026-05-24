package com.twitter.follow_recommendations.flows.post_nux_ml

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.clients.dismiss_store.DismissStore
import com.twitter.follow_recommendations.common.clients.geoduck.UserLocationFetcher
import com.twitter.follow_recommendations.common.clients.impression_store.WtfImpressionStore
import com.twitter.follow_recommendations.common.clients.interests_service.InterestServiceClient
import com.twitter.follow_recommendations.common.clients.socialgraph.SocialGraphClient
import com.twitter.follow_recommendations.common.clients.user_state.UserStateClient
import com.twitter.follow_recommendations.common.predicates.dismiss.DismissedCandidatePredicateParams
import com.twitter.follow_recommendations.common.utils.RescueWithStatsUtils._
import com.twitter.follow_recommendations.flows.post_nux_ml.PostNuxMlRequestBuilderParams.DismissedIdScanBudget
import com.twitter.follow_recommendations.flows.post_nux_ml.PostNuxMlRequestBuilderParams.TopicIdFetchBudget
import com.twitter.follow_recommendations.flows.post_nux_ml.PostNuxMlRequestBuilderParams.WTFImpressionsScanBudget
import com.twitter.follow_recommendations.products.common.ProductRequest
import com.twitter.inject.Logging
import com.twitter.stitch.Stitch
import com.twitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostNuxMlRequestBuilder @Inject() (
  socialGraph: SocialGraphClient,
  wtfImpressionStore: WtfImpressionStore,
  dismissStore: DismissStore,
  userLocationFetcher: UserLocationFetcher,
  interestServiceClient: InterestServiceClient,
  userStateClient: UserStateClient,
  statsReceiver: StatsReceiver)
    extends Logging {

  val stats: StatsReceiver = statsReceiver.scope("post_nux_ml_request_builder")
  val invalidRelationshipUsersStats: StatsReceiver = stats.scope("invalidRelationshipUserIds")
  private val invalidRelationshipUsersMaxSizeCounter =
    invalidRelationshipUsersStats.counter("maxSize")
  private val invalidRelationshipUsersNotMaxSizeCounter =
    invalidRelationshipUsersStats.counter("notMaxSize")

  def build(
    req: ProductRequest,
    previouslyRecommendedUserIds: Option[Set[Long]] = None,
    previouslyFollowedUserIds: Option[Set[Long]] = None
  ): Stitch[PostNuxMlRequest] = {
    val dl = req.recommendationRequest.displayLocation
    val resultsStitch = Stitch.collect(
      req.recommendationRequest.clientContext.userId
        .map { userId =>
          val lookBackDuration = req.params(DismissedCandidatePredicateParams.LookBackDuration)
          val negativeStartTs = -(Time.now - lookBackDuration).inMillis
          val recentFollowedUserIdsStitch =
            rescueWithStats(
              socialGraph.getRecentFollowedUserIds(userId),
              stats,
              "recentFollowedUserIds")
          val invalidRelationshipUserIdsStitch =
            if (req.params(PostNuxMlParams.EnableInvalidRelationshipPredicate)) {
              rescueWithStats(
                socialGraph
                  .getInvalidRelationshipUserIds(userId)
                  .onSuccess(ids =>
                    if (ids.size >= SocialGraphClient.MaxNumInvalidRelationship) {
                      invalidRelationshipUsersMaxSizeCounter.incr()
                    } else {
                      invalidRelationshipUsersNotMaxSizeCounter.incr()
                    }),
                stats,
                "invalidRelationshipUserIds"
              )
            } else {
              Stitch.value(Seq.empty)
            }
          // recentFollowedByUserIds are only used in experiment candidate sources
          val recentFollowedByUserIdsStitch = if (req.params(PostNuxMlParams.GetFollowersFromSgs)) {
            rescueWithStats(
              socialGraph.getRecentFollowedByUserIdsFromCachedColumn(userId),
              stats,
              "recentFollowedByUserIds")
          } else Stitch.value(Seq.empty)
          val wtfImpressionsStitch =
            rescueWithStatsWithin(
              wtfImpressionStore.get(userId, dl),
              stats,
              "wtfImpressions",
              req.params(WTFImpressionsScanBudget))
          val dismissedUserIdsStitch =
            rescueWithStatsWithin(
              dismissStore.get(userId, negativeStartTs, None),
              stats,
              "dismissedUserIds",
              req.params(DismissedIdScanBudget))
          val locationStitch =
            rescueOptionalWithStats(
              userLocationFetcher.getGeohashAndCountryCode(
                Some(userId),
                req.recommendationRequest.clientContext.ipAddress),
              stats,
              "userLocation"
            )
          val topicIdsStitch =
            rescueWithStatsWithin(
              interestServiceClient.fetchUttInterestIds(userId),
              stats,
              "topicIds",
              req.params(TopicIdFetchBudget))
          val userStateStitch =
            rescueOptionalWithStats(userStateClient.getUserState(userId), stats, "userState")
          Stitch.join(
            recentFollowedUserIdsStitch,
            invalidRelationshipUserIdsStitch,
            recentFollowedByUserIdsStitch,
            dismissedUserIdsStitch,
            wtfImpressionsStitch,
            locationStitch,
            topicIdsStitch,
            userStateStitch
          )
        })

    resultsStitch.map {
      case Some(
            (
              recentFollowedUserIds,
              invalidRelationshipUserIds,
              recentFollowedByUserIds,
              dismissedUserIds,
              wtfImpressions,
              locationInfo,
              topicIds,
              userState)) =>
        PostNuxMlRequest(
          params = req.params,
          clientContext = req.recommendationRequest.clientContext,
          similarToUserIds = Nil,
          inputExcludeUserIds = req.recommendationRequest.excludedIds.getOrElse(Nil),
          recentFollowedUserIds = Some(recentFollowedUserIds),
          invalidRelationshipUserIds = Some(invalidRelationshipUserIds.toSet),
          recentFollowedByUserIds = Some(recentFollowedByUserIds),
          dismissedUserIds = Some(dismissedUserIds),
          displayLocation = dl,
          maxResults = req.recommendationRequest.maxResults,
          debugOptions = req.recommendationRequest.debugParams.flatMap(_.debugOptions),
          wtfImpressions = Some(wtfImpressions),
          geohashAndCountryCode = locationInfo,
          uttInterestIds = Some(topicIds),
          inputPreviouslyRecommendedUserIds = previouslyRecommendedUserIds,
          inputPreviouslyFollowedUserIds = previouslyFollowedUserIds,
          isSoftUser = req.recommendationRequest.isSoftUser,
          userState = userState
        )
      case _ =>
        PostNuxMlRequest(
          params = req.params,
          clientContext = req.recommendationRequest.clientContext,
          similarToUserIds = Nil,
          inputExcludeUserIds = req.recommendationRequest.excludedIds.getOrElse(Nil),
          recentFollowedUserIds = None,
          invalidRelationshipUserIds = None,
          recentFollowedByUserIds = None,
          dismissedUserIds = None,
          displayLocation = dl,
          maxResults = req.recommendationRequest.maxResults,
          debugOptions = req.recommendationRequest.debugParams.flatMap(_.debugOptions),
          wtfImpressions = None,
          geohashAndCountryCode = None,
          inputPreviouslyRecommendedUserIds = previouslyRecommendedUserIds,
          inputPreviouslyFollowedUserIds = previouslyFollowedUserIds,
          isSoftUser = req.recommendationRequest.isSoftUser,
          userState = None
        )
    }
  }
}
