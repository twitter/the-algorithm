namespace java com.twitter.tweetypie.thriftjava
#@namespace scala com.twitter.tweetypie.thriftscala
#@namespace strato com.twitter.tweetypie
namespace py gen.twitter.tweetypie.tweet_note
namespace rb TweetyPie
// Specific namespace to avoid golang circular import
namespace go tweetypie.tweet

// Struct representing a NoteTweet associated with a Tweet
struct NoteTweet {
  1: required i64 id (strato.space = 'NoteTweet', strato.name = "note_tweet", personalDataType = 'TwitterArticleID')
  2: optional bool is_expandable (strato.name = "is_expandable")
} (persisted='true', hasPersonalData = 'true', strato.graphql.typename = 'NoteTweetData')
