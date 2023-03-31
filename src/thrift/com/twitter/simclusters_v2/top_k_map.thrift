namespace java com.twitter.simclusters_v2.thriftjava
namespace py gen.twitter.simclusters_v2.top_k_map
#@namespace scala com.twitter.simclusters_v2.thriftscala
#@namespace strato com.twitter.simclusters_v2

include "com/twitter/algebird_internal/algebird.thrift"

struct TopKClusters {
	1: required map<i32, algebird.DecayedValue> topK(personalDataTypeKey = 'InferredInterests')
}(hasPersonalData = 'true')

struct TopKTweets {
	1: required map<i64, algebird.DecayedValue> topK(personalDataTypeKey='TweetId')
}(hasPersonalData = 'true') 
