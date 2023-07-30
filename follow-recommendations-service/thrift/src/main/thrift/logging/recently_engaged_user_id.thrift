namespace java com.X.follow_recommendations.logging.thriftjava
#@namespace scala com.X.follow_recommendations.logging.thriftscala
#@namespace strato com.X.follow_recommendations.logging

include "engagementType.thrift"

struct RecentlyEngagedUserId {
  1: required i64 id(personalDataType='UserId')
  2: required engagementType.EngagementType engagementType 
}(persisted='true', hasPersonalData='true')
