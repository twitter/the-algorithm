namespace java com.ExTwitter.cr_mixer.thriftjava
#@namespace scala com.ExTwitter.cr_mixer.thriftscala
#@namespace strato com.ExTwitter.cr_mixer

include "product.thrift"
include "com/ExTwitter/product_mixer/core/client_context.thrift"
include "com/ExTwitter/simclusters_v2/identifier.thrift"

struct RelatedTweetRequest {
  1: required identifier.InternalId internalId
	2: required product.Product product
	3: required client_context.ClientContext clientContext # RUX LogOut will have clientContext.userId = None
	4: optional list<i64> excludedTweetIds (personalDataType = 'TweetId')
} (persisted='true', hasPersonalData='true')

struct RelatedTweet {
  1: required i64 tweetId (personalDataType = 'TweetId')
  2: optional double score
  3: optional i64 authorId (personalDataType = 'UserId')
} (persisted='true', hasPersonalData='true')

struct RelatedTweetResponse {
  1: required list<RelatedTweet> tweets
} (persisted='true')
