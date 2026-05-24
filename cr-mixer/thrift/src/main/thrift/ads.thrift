namespace java com.twitter.cr_mixer.thriftjava
#@namespace scala com.twitter.cr_mixer.thriftscala
#@namespace strato com.twitter.cr_mixer

include "product.thrift"
include "product_context.thrift"

include "com/twitter/product_mixer/core/client_context.thrift"
include "com/twitter/ads/schema/shared.thrift"

struct AdsRequest {
	1: required client_context.ClientContext clientContext
	2: required product.Product product
	# Product-specific parameters should be placed in the Product Context
	3: optional product_context.ProductContext productContext
	4: optional list<i64> excludedTweetIds (personalDataType = 'TweetId')
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
