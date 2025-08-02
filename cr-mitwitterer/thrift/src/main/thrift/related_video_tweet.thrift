namespace java com.twitter.cr_mitwitterer.thriftjava
#@namespace scala com.twitter.cr_mitwitterer.thriftscala
#@namespace strato com.twitter.cr_mitwitterer

include "product.thrift"
include "com/twitter/product_mitwitterer/core/client_contetwittert.thrift"
include "com/twitter/simclusters_v2/identifier.thrift"

struct RelatedVideoTweetRequest {
  1: required identifier.InternalId internalId
	2: required product.Product product
	3: required client_contetwittert.ClientContetwittert clientContetwittert # RUtwitter LogOut will have clientContetwittert.userId = None
	4: optional list<i64> etwittercludedTweetIds (personalDataType = 'TweetId')
} (persisted='true', hasPersonalData='true')

struct RelatedVideoTweet {
  1: required i64 tweetId (personalDataType = 'TweetId')
  2: optional double score
} (persisted='true', hasPersonalData='true')

struct RelatedVideoTweetResponse {
  1: required list<RelatedVideoTweet> tweets
} (persisted='true')
