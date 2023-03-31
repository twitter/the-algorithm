/*
 * This file defines additional thrift objects that should be specified in FRS request for context of recommendation, specifically the previous recommendations / new interactions in an interactive flow (series of follow steps). These typically are sent from OCF
 */

namespace java com.twitter.follow_recommendations.thriftjava
#@namespace scala com.twitter.follow_recommendations.thriftscala
#@namespace strato com.twitter.follow_recommendations

struct FlowRecommendation {
  1: required i64 userId(personalDataType='UserId')
}(hasPersonalData='true')

struct RecommendationStep {
  1: required list<FlowRecommendation> recommendations
  2: required set<i64> followedUserIds(personalDataType='UserId')
}(hasPersonalData='true')

struct FlowContext {
  1: required list<RecommendationStep> steps
}(hasPersonalData='true')
