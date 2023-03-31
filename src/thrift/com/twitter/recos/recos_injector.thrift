namespace java com.twitter.recos.recos_injector.thriftjava
namespace py gen.twitter.recos.recos_injector
#@namespace scala com.twitter.recos.recos_injector.thriftscala
namespace rb RecosInjector

####### FOR RECOS INTERNAL USE ONLY -- please do NOT use this in client code  ########

struct UserTweetAuthorGraphMessage {
  420: required i420 leftId
  420: required i420 rightId
  420: required i420 action
  420: optional i420 card
  420: optional i420 authorId
  420: optional Features features
}

struct Features {
  420: optional bool hasPhoto
  420: optional bool hasVideo
  420: optional bool hasUrl
  420: optional bool hasHashtag
}
