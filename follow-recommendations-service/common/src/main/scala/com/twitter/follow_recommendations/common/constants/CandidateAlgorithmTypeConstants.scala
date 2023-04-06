package com.twitter.follow_recommendations.common.constants

import com.twitter.hermit.constants.AlgorithmFeedbackTokens.AlgorithmToFeedbackTokenMap
import com.twitter.hermit.model.Algorithm._
import com.twitter.follow_recommendations.common.models.AlgorithmType

object AlgorithmTypeConstants {
  type AlgorithmTypeSet = Set[AlgorithmType.Value]
  type AlgorithmToTypeMap = Map[String, AlgorithmTypeSet]

  // Map algorithm IDs to their types
  private val AlgorithmIdToType: AlgorithmToTypeMap = Map(
    // Activity Algorithms:
    AlgorithmToFeedbackTokenMap(NewFollowingSimilarUser) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(Sims) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(NewFollowingSimilarUserSalsa) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(RecentEngagementNonDirectFollow) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(RecentEngagementSimilarUser) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(RecentEngagementSarusOcCur) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(RecentSearchBasedRec) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(TwistlyTweetAuthors) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(Follow2VecNearestNeighbors) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(EmailTweetClick) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(RepeatedProfileVisits) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(GoodTweetClickEngagements) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(TweetShareEngagements) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(TweetSharerToShareRecipientEngagements) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(TweetAuthorToShareRecipientEngagements) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(LinearRegressionFollow2VecNearestNeighbors) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(NUXLOHistory) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(TrafficAttributionAccounts) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(RealGraphOonV2) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(MagicRecsRecentEngagements) -> Set(AlgorithmType.Activity),
    AlgorithmToFeedbackTokenMap(NotificationEngagement) -> Set(AlgorithmType.Activity),

    // Social Algorithms:
    AlgorithmToFeedbackTokenMap(TwoHopRandomWalk) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(RealTimeMutualFollow) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(ForwardPhoneBook) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(ForwardEmailBook) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(NewFollowingNewFollowingExpansion) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(NewFollowingSarusCoOccurSocialProof) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(ReverseEmailBookIbis) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(ReversePhoneBook) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(StrongTiePredictionRec) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(StrongTiePredictionRecWithSocialProof) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(OnlineStrongTiePredictionRec) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(OnlineStrongTiePredictionRecNoCaching) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(TriangularLoop) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(StrongTiePredictionPmi) -> Set(AlgorithmType.Social),
    AlgorithmToFeedbackTokenMap(OnlineStrongTiePredictionRAB) -> Set(AlgorithmType.Social),

    // Geo Algorithms:
    AlgorithmToFeedbackTokenMap(PopCountryBackFill) -> Set(AlgorithmType.Geo),
    AlgorithmToFeedbackTokenMap(PopCountry) -> Set(AlgorithmType.Geo),
    AlgorithmToFeedbackTokenMap(PopGeohash) -> Set(AlgorithmType.Geo),
    AlgorithmToFeedbackTokenMap(PopGeohashRealGraph) -> Set(AlgorithmType.Geo),
    AlgorithmToFeedbackTokenMap(EngagedFollowerRatio) -> Set(AlgorithmType.Geo),
    AlgorithmToFeedbackTokenMap(CrowdSearchAccounts) -> Set(AlgorithmType.Geo),
    AlgorithmToFeedbackTokenMap(OrganicFollowAccounts) -> Set(AlgorithmType.Geo),
    AlgorithmToFeedbackTokenMap(PopGeohashQualityFollow) -> Set(AlgorithmType.Geo),
    AlgorithmToFeedbackTokenMap(PPMILocaleFollow) -> Set(AlgorithmType.Geo),

    // Interest Algorithms:
    AlgorithmToFeedbackTokenMap(TttInterest) -> Set(AlgorithmType.Interest),
    AlgorithmToFeedbackTokenMap(UttInterestRelatedUsers) -> Set(AlgorithmType.Interest),
    AlgorithmToFeedbackTokenMap(UttSeedAccounts) -> Set(AlgorithmType.Interest),
    AlgorithmToFeedbackTokenMap(UttProducerExpansion) -> Set(AlgorithmType.Interest),

    // Hybrid (more than one type) Algorithms:
    AlgorithmToFeedbackTokenMap(UttProducerOfflineMbcgV1) -> Set(AlgorithmType.Interest, AlgorithmType.Geo),
    AlgorithmToFeedbackTokenMap(CuratedAccounts) -> Set(AlgorithmType.Interest, AlgorithmType.Geo),
    AlgorithmToFeedbackTokenMap(UserUserGraph) -> Set(AlgorithmType.Social, AlgorithmType.Activity),
  )

  /**
   * Returns the types of algorithms associated with a given algorithm ID.
   * @param algoId the ID of the algorithm
   * @return a set of strings representing the algorithm types
   */
  def getAlgorithmTypes(algoId: String): Set[String] =
    AlgorithmIdToType.getOrElse(algoId, Set.empty).map(_.toString)
}
