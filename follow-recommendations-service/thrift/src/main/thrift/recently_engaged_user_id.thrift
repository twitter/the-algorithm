namespace java com.ExTwitter.follow_recommendations.thriftjava
#@namespace scala com.ExTwitter.follow_recommendations.thriftscala
#@namespace strato com.ExTwitter.follow_recommendations

include "engagementType.thrift"

struct RecentlyEngagedUserId {
  1: required i64 id(personalDataType='UserId')
  2: required engagementType.EngagementType engagementType 
}(persisted='true', hasPersonalData='true')
