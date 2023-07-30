namespace java com.X.tweetypie.unmentions.thriftjava
#@ namespace scala com.X.tweetypie.unmentions.thriftscala
#@ namespace strato com.X.tweetypie.unmentions
namespace py gen.X.tweetypie.unmentions

struct UnmentionData {
    1: optional i64 conversationId (personalDataType = 'TweetConversationId')
    2: optional list<i64> mentionedUsers (personalDataType = 'UserId')
} (strato.space = "Unmentions", persisted='true', hasPersonalData = 'true')