namespace java com.twitter.follow_recommendations.logging.thriftjava
#@namespace scala com.twitter.follow_recommendations.logging.thriftscala
#@namespace strato com.twitter.follow_recommendations.logging

struct OfflineFlowRecommendation {
  1: required i64 userId(personalDataType='UserId')
}(persisted='true', hasPersonalData='true')

struct OfflineRecommendationStep {
  1: required list<OfflineFlowRecommendation> recommendations
  2: required set<i64> followedUserIds(personalDataType='UserId')
}(persisted='true', hasPersonalData='true')

struct OfflineFlowContext {
  1: required list<OfflineRecommendationStep> steps
}(persisted='true', hasPersonalData='true')
