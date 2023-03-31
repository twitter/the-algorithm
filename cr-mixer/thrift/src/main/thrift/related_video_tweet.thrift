namespace java com.twitter.cr_mixer.thriftjava
#@namespace scala com.twitter.cr_mixer.thriftscala
#@namespace strato com.twitter.cr_mixer

include "product.thrift"
include "com/twitter/product_mixer/core/client_context.thrift"
include "com/twitter/simclusters_v2/identifier.thrift"

struct RelatedVideoTweetRequest {
  1: required identifier.InternalId internalId
	2: required product.Product product
	3: required client_context.ClientContext clientContext # RUX LogOut will have clientContext.userId = None
	4: optional list<i64> excludedTweetIds (personalDataType = 'TweetId')
} (persisted='true', hasPersonalData='true')

struct RelatedVideoTweet {
  1: required i64 tweetId (personalDataType = 'TweetId')
  2: optional double score
} (persisted='true', hasPersonalData='true')

struct RelatedVideoTweetResponse {
  1: required list<RelatedVideoTweet> tweets
} (persisted='true')
