namespace java com.twitter.tsp.thriftjava
namespace py gen.twitter.tsp
#@namespace scala com.twitter.tsp.thriftscala
#@namespace strato com.twitter.tsp.strato

struct TspTweetInfo {
  1: required i64 authorId
  2: required i64 favCount
  3: optional string language
  6: optional bool hasImage
  7: optional bool hasVideo
  8: optional bool hasGif
  9: optional bool isNsfwAuthor
  10: optional bool isKGODenylist
  11: optional bool isNullcast
  // available if the tweet contains video
  12: optional i32 videoDurationSeconds
  13: optional bool isHighMediaResolution
  14: optional bool isVerticalAspectRatio
  // health signal scores
  15: optional bool isPassAgathaHealthFilterStrictest
  16: optional bool isPassTweetHealthFilterStrictest
  17: optional bool isReply
  18: optional bool hasMultipleMedia
  23: optional bool hasUrl
}(persisted='false', hasPersonalData='true')
