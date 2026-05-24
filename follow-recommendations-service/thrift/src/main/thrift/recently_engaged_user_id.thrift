namespace java com.twitter.follow_recommendations.thriftjava
#@namespace scala com.twitter.follow_recommendations.thriftscala
#@namespace strato com.twitter.follow_recommendations

include "engagementType.thrift"

struct RecentlyEngagedUserId {
  1: required i64 id(personalDataType='UserId')
  2: required engagementType.EngagementType engagementType 
}(persisted='true', hasPersonalData='true')
