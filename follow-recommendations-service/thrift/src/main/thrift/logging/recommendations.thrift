namespace java com.twitter.follow_recommendations.logging.thriftjava
#@namespace scala com.twitter.follow_recommendations.logging.thriftscala
#@namespace strato com.twitter.follow_recommendations.logging

include "com/twitter/ads/adserver/adserver_common.thrift"
include "reasons.thrift"
include "tracking.thrift"
include "scoring.thrift"

// Offline equal of UserRecommendation
struct OfflineUserRecommendation {
    1: required i64 userId(personalDataType='UserId')
    // reason for this suggestions, eg: social context
    2: optional reasons.Reason reason
    // present if it is a promoted account
    3: optional adserver_common.AdImpression adImpression
  // tracking token (unserialized) for attribution
  4: optional tracking.TrackingToken trackingToken
    // scoring details
    5: optional scoring.ScoringDetails scoringDetails
}(persisted='true', hasPersonalData='true')

// Offline equal of Recommendation
union OfflineRecommendation {
    1: OfflineUserRecommendation user
}(persisted='true', hasPersonalData='true')
