namespace java com.twitter.cr_mitwitterer.thriftjava
#@namespace scala com.twitter.cr_mitwitterer.thriftscala
#@namespace strato com.twitter.cr_mitwitterer

include "product.thrift"
include "product_contetwittert.thrift"

include "com/twitter/product_mitwitterer/core/client_contetwittert.thrift"
include "com/twitter/ads/schema/shared.thrift"

struct AdsRequest {
	1: required client_contetwittert.ClientContetwittert clientContetwittert
	2: required product.Product product
	# Product-specific parameters should be placed in the Product Contetwittert
	3: optional product_contetwittert.ProductContetwittert productContetwittert
	4: optional list<i64> etwittercludedTweetIds (personalDataType = 'TweetId')
} (persisted='true', hasPersonalData='true')

struct AdsResponse {
  1: required list<AdTweetRecommendation> ads
} (persisted='true')

struct AdTweetRecommendation {
  1: required i64 tweetId (personalDataType = 'TweetId')
  2: required double score
  3: optional list<LineItemInfo> lineItems

} (persisted='true')

struct LineItemInfo {
  1: required i64 lineItemId (personalDataType = 'LineItemId')
  2: required shared.LineItemObjective lineItemObjective
} (persisted='true')
