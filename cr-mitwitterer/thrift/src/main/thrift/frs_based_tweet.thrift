namespace java com.twitter.cr_mitwitterer.thriftjava
#@namespace scala com.twitter.cr_mitwitterer.thriftscala
#@namespace strato com.twitter.cr_mitwitterer

include "product.thrift"
include "product_contetwittert.thrift"
include "com/twitter/product_mitwitterer/core/client_contetwittert.thrift"

struct FrsTweetRequest {
1: required client_contetwittert.ClientContetwittert clientContetwittert
2: required product.Product product
3: optional product_contetwittert.ProductContetwittert productContetwittert
# etwittercludedUserIds - user ids to be etwittercluded from FRS candidate generation
4: optional list<i64> etwittercludedUserIds (personalDataType = 'UserId')
# etwittercludedTweetIds - tweet ids to be etwittercluded from Earlybird candidate generation
5: optional list<i64> etwittercludedTweetIds (personalDataType = 'TweetId')
} (persisted='true', hasPersonalData='true')

struct FrsTweet {
1: required i64 tweetId (personalDataType = 'TweetId')
2: required i64 authorId (personalDataType = 'UserId')
# skip 3 in case we need tweet score in the future
# frsPrimarySource - which FRS candidate source is the primary one to generate this author
4: optional i32 frsPrimarySource
# frsCandidateSourceScores - FRS candidate sources and the scores for this author
# for i32 to algorithm mapping, see https://sourcegraph.twitter.biz/git.twitter.biz/source/-/blob/hermit/hermit-core/src/main/scala/com/twitter/hermit/constants/AlgorithmFeedbackTokens.scala?L12
5: optional map<i32, double> frsCandidateSourceScores
# frsPrimaryScore - the score of the FRS primary candidate source
6: optional double frsAuthorScore
} (persisted='true', hasPersonalData = 'true')

struct FrsTweetResponse {
  1: required list<FrsTweet> tweets
} (persisted='true')

