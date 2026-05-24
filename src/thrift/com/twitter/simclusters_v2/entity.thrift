namespace java com.twitter.simclusters_v2.thriftjava
namespace py gen.twitter.simclusters_v2.entity
#@namespace scala com.twitter.simclusters_v2.thriftscala
#@namespace strato com.twitter.simclusters_v2

include "com/twitter/algebird_internal/algebird.thrift"

/**
 * Penguin text entity. All fields are required as this is used as a part of a memcache key.
 **/
struct PenguinKey {
  1: required string textEntity
}(hasPersonalData = 'false')

/**
 * NER text entity. All fields are required as this is used as a part of a memcache key.
 **/
struct NerKey {
  1: required string textEntity
  2: required i32 wholeEntityType
}(hasPersonalData = 'false')

/**
 * Semantic Core text entity. All fields are required as this is used as a part of a memcache key.
 **/
struct SemanticCoreKey {
  1: required i64 entityId(personalDataType = 'SemanticcoreClassification')
}(hasPersonalData = 'true')

/**
 * Represents an entity extracted from a tweet.
 **/
union TweetTextEntity {
  1: string hashtag
  2: PenguinKey penguin
  3: NerKey ner
  4: SemanticCoreKey semanticCore
}(hasPersonalData = 'true')

struct SpaceId {
  1: string id
}(hasPersonalData = 'true')

/**
 * All possible entities that simclusters are associated with.
 **/
union SimClusterEntity {
  1: i64 tweetId(personalDataType = 'TweetId')
  2: TweetTextEntity tweetEntity
  3: SpaceId spaceId
}(hasPersonalData = 'true')
