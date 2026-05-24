namespace java com.twitter.unified_user_actions.thriftjava
#@namespace scala com.twitter.unified_user_actions.thriftscala
#@namespace strato com.twitter.unified_user_actions

include "com/twitter/unified_user_actions/action_info.thrift"
include "com/twitter/unified_user_actions/common.thrift"
include "com/twitter/unified_user_actions/metadata.thrift"

/*
 * This is mainly for View Counts project, which only require minimum fields for now.
 * The name KeyedUuaTweet indicates the value is about a Tweet, not a Moment or other entities.
 */
struct KeyedUuaTweet {
   /* A user refers to either a logged in / logged out user */
   1: required common.UserIdentifier userIdentifier
   /* The tweet that received the action from the user */
   2: required i64 tweetId(personalDataType='TweetId')
   /* The type of action which took place */
   3: required action_info.ActionType actionType
   /* Useful for event level analysis and joins */
   4: required metadata.EventMetadata eventMetadata
}(persisted='true', hasPersonalData='true')
