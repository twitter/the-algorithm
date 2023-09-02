namespace java com.twitter.tweetypie.media.thriftjava
#@namespace scala com.twitter.tweetypie.media.thriftscala
#@namespace strato com.twitter.tweetypie.media
namespace py gen.twitter.tweetypie.media
namespace rb TweetyPie


/**
* A MediaRef represents a reference to a piece of media in MediaInfoService, along with metadata
* about the source Tweet that the media came from in case of pasted media.
**/
struct MediaRef {
  1: string generic_media_key (personalDataType = 'MediaId')

  // For Tweets with pasted media, the id of the Tweet where this media was copied from
  2: optional i64 source_tweet_id (personalDataType = 'TweetId')

  // The author of source_tweet_id
  3: optional i64 source_user_id (personalDataType = 'UserId')
}(persisted='true', hasPersonalData='true')
