include "logging/flows.thrift"
include "logging/recently_engaged_user_id.thrift"

namespace java com.twitter.follow_recommendations.logging.thriftjava
#@namespace scala com.twitter.follow_recommendations.logging.thriftscala
#@namespace strato com.twitter.follow_recommendations.logging

// Offline equal of Profile DisplayContetwittert
struct OfflineProfile {
    1: required i64 profileId(personalDataType='UserId')
}(persisted='true', hasPersonalData='true')

// Offline equal of Search DisplayContetwittert
struct OfflineSearch {
    1: required string searchQuery(personalDataType='SearchQuery')
}(persisted='true', hasPersonalData='true')

// Offline equal of Rutwitter Landing Page DisplayContetwittert
struct OfflineRutwitter {
  1: required i64 focalAuthorId(personalDataType="UserId")
}(persisted='true', hasPersonalData='true')

// Offline equal of Topic DisplayContetwittert
struct OfflineTopic {
  1: required i64 topicId(personalDataType = 'TopicFollow')
}(persisted='true', hasPersonalData='true')

struct OfflineReactiveFollow {
    1: required list<i64> followedUserIds(personalDataType='UserId')
}(persisted='true', hasPersonalData='true')

struct OfflineNutwitterInterests {
    1: optional flows.OfflineFlowContetwittert flowContetwittert // set for recommendation inside an interactive flow
}(persisted='true', hasPersonalData='true')

struct OfflineAdCampaignTarget {
    1: required list<i64> similarToUserIds(personalDataType='UserId')
}(persisted='true', hasPersonalData='true')

struct OfflineConnectTab {
    1: required list<i64> byfSeedUserIds(personalDataType='UserId')
    2: required list<i64> similarToUserIds(personalDataType='UserId')
    3: required list<recently_engaged_user_id.RecentlyEngagedUserId> recentlyEngagedUserIds
}(persisted='true', hasPersonalData='true')

struct OfflineSimilarToUser {
    1: required i64 similarToUserId(personalDataType='UserId')
}(persisted='true', hasPersonalData='true')

struct OfflinePostNutwitterFollowTask {
    1: optional flows.OfflineFlowContetwittert flowContetwittert // set for recommendation inside an interactive flow
}(persisted='true', hasPersonalData='true')

// Offline equal of DisplayContetwittert
union OfflineDisplayContetwittert {
    1: OfflineProfile profile
    2: OfflineSearch search
    3: OfflineRutwitter rutwitter
    4: OfflineTopic topic
    5: OfflineReactiveFollow reactiveFollow
    6: OfflineNutwitterInterests nutwitterInterests
    7: OfflineAdCampaignTarget adCampaignTarget
    8: OfflineConnectTab connectTab
    9: OfflineSimilarToUser similarToUser
    10: OfflinePostNutwitterFollowTask postNutwitterFollowTask
}(persisted='true', hasPersonalData='true')
