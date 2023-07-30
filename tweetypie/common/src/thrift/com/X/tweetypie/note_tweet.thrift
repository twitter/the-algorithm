namespace java com.X.tweetypie.thriftjava
#@namespace scala com.X.tweetypie.thriftscala
#@namespace strato com.X.tweetypie
namespace py gen.X.tweetypie.tweet_note
namespace rb TweetyPie
// Specific namespace to avoid golang circular import
namespace go tweetypie.tweet

// Struct representing a NoteTweet associated with a Tweet
struct NoteTweet {
  1: required i64 id (strato.space = 'NoteTweet', strato.name = "note_tweet", personalDataType = 'XArticleID')
  2: optional bool is_expandable (strato.name = "is_expandable")
} (persisted='true', hasPersonalData = 'true', strato.graphql.typename = 'NoteTweetData')
