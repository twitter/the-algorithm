namespace java com.twitter.tweetypie.unmentions.thriftjava
#@ namespace scala com.twitter.tweetypie.unmentions.thriftscala
#@ namespace strato com.twitter.tweetypie.unmentions
namespace py gen.twitter.tweetypie.unmentions

struct UnmentionData {
    1: optional i64 conversationId (personalDataType = 'TweetConversationId')
    2: optional list<i64> mentionedUsers (personalDataType = 'UserId')
} (strato.space = "Unmentions", persisted='true', hasPersonalData = 'true')