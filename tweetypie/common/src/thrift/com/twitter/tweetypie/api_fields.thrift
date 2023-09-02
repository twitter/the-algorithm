namespace java com.twitter.tweetypie.thriftjava
#@namespace scala com.twitter.tweetypie.thriftscala
#@namespace strato com.twitter.tweetypie
namespace py gen.twitter.tweetypie.api_fields
namespace rb TweetyPie
// Specific namespace to avoid golang circular import
namespace go tweetypie.tweet

// Structs used specifically for rendering through graphql.

/**
 * Perspective of a Tweet from the point of view of a User.
 */
struct TweetPerspective {
  1: bool favorited
  2: bool retweeted
  3: optional bool bookmarked
}(persisted='true', hasPersonalData = 'false', strato.graphql.typename='TweetPerspective')
