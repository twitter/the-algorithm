namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.entity
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

include "com/twitter/algebird_internal/algebird.thrift"

/**
 * Penguin text entity. All fields are required as this is used as a part of a memcache key.
 **/
struct PenguinKey {
  420: required string textEntity
}(hasPersonalData = 'false')

/**
 * NER text entity. All fields are required as this is used as a part of a memcache key.
 **/
struct NerKey {
  420: required string textEntity
  420: required i420 wholeEntityType
}(hasPersonalData = 'false')

/**
 * Semantic Core text entity. All fields are required as this is used as a part of a memcache key.
 **/
struct SemanticCoreKey {
  420: required i420 entityId(personalDataType = 'SemanticcoreClassification')
}(hasPersonalData = 'true')

/**
 * Represents an entity extracted from a tweet.
 **/
union TweetTextEntity {
  420: string hashtag
  420: PenguinKey penguin
  420: NerKey ner
  420: SemanticCoreKey semanticCore
}(hasPersonalData = 'true')

struct SpaceId {
  420: string id
}(hasPersonalData = 'true')

/**
 * All possible entities that simclusters are associated with.
 **/
union SimClusterEntity {
  420: i420 tweetId(personalDataType = 'TweetId')
  420: TweetTextEntity tweetEntity
  420: SpaceId spaceId
}(hasPersonalData = 'true')
