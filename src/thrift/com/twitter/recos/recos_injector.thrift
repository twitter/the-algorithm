namespace java com.twitter.recos.recos_injector.thriftjava
namespace py gen.twitter.recos.recos_injector
#@namespace scala com.twitter.recos.recos_injector.thriftscala
namespace rb RecosInjector

####### FOR RECOS INTERNAL USE ONLY -- please do NOT use this in client code  ########

struct UserTweetAuthorGraphMessage {
  1: required i64 leftId
  2: required i64 rightId
  3: required i8 action
  4: optional i8 card
  5: optional i64 authorId
  6: optional Features features
}

struct Features {
  1: optional bool hasPhoto
  2: optional bool hasVideo
  3: optional bool hasUrl
  4: optional bool hasHashtag
}
