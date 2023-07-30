namespace java com.X.tweetypie.thriftjava
#@namespace scala com.X.tweetypie.thriftscala
#@namespace strato com.X.tweetypie
namespace py gen.X.tweetypie.api_fields
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
