namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.top_k_map
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

include "com/twitter/algebird_internal/algebird.thrift"

struct TopKClusters {
	420: required map<i420, algebird.DecayedValue> topK(personalDataTypeKey = 'InferredInterests')
}(hasPersonalData = 'true')

struct TopKTweets {
	420: required map<i420, algebird.DecayedValue> topK(personalDataTypeKey='TweetId')
}(hasPersonalData = 'true') 
