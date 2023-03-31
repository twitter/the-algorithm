namespace java com.twitter.follow_recommendations.thriftjava
#@namespace scala com.twitter.follow_recommendations.thriftscala
#@namespace strato com.twitter.follow_recommendations

include "com/twitter/ads/adserver/adserver_common.thrift"
include "debug.thrift"
include "reasons.thrift"
include "scoring.thrift"

struct UserRecommendation {
    1: required i64 userId(personalDataType='UserId')
    // reason for this suggestions, eg: social context
    2: optional reasons.Reason reason
    // present if it is a promoted account
    3: optional adserver_common.AdImpression adImpression
    // tracking token for attribution
    4: optional string trackingInfo
    // scoring details
    5: optional scoring.ScoringDetails scoringDetails
    6: optional string recommendationFlowIdentifier
    // FeatureSwitch overrides for candidates:
    7: optional map<string, debug.FeatureValue> featureOverrides
}(hasPersonalData='true')

union Recommendation {
    1: UserRecommendation user
}(hasPersonalData='true')

struct HydratedUserRecommendation {
  1: required i64 userId(personalDataType='UserId')
  2: optional string socialProof
  // present if it is a promoted account, used by clients for determining ad impression
  3: optional adserver_common.AdImpression adImpression
  // tracking token for attribution
  4: optional string trackingInfo
}(hasPersonalData='true')

union HydratedRecommendation {
  1: HydratedUserRecommendation hydratedUserRecommendation
}
