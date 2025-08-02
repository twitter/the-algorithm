namespace java com.twitter.cr_mitwitterer.thriftjava
#@namespace scala com.twitter.cr_mitwitterer.thriftscala
#@namespace strato com.twitter.cr_mitwitterer

include "product.thrift"
include "product_contetwittert.thrift"

include "com/twitter/product_mitwitterer/core/client_contetwittert.thrift"
include "com/twitter/recos/recos_common.thrift"

struct UtegTweetRequest {
	1: required client_contetwittert.ClientContetwittert clientContetwittert
	2: required product.Product product
	# Product-specific parameters should be placed in the Product Contetwittert
	3: optional product_contetwittert.ProductContetwittert productContetwittert
	4: optional list<i64> etwittercludedTweetIds (personalDataType = 'TweetId')
} (persisted='true', hasPersonalData='true')

struct UtegTweet {
  // tweet id
  1: required i64 tweetId(personalDataType = 'TweetId')
  // sum of weights of seed users who engaged with the tweet.
  // If a user engaged with the same tweet twice, liked it and retweeted it, then his/her weight was counted twice.
  2: required double score
  // user social proofs per engagement type
  3: required map<recos_common.SocialProofType, list<i64>> socialProofByType(personalDataTypeKey='EngagementTypePrivate', personalDataTypeValue='UserId')
} (persisted='true', hasPersonalData = 'true')

struct UtegTweetResponse {
  1: required list<UtegTweet> tweets
} (persisted='true')
