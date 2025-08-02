namespace java com.twitter.cr_mitwitterer.thriftjava
#@namespace scala com.twitter.cr_mitwitterer.thriftscala
#@namespace strato com.twitter.cr_mitwitterer

include "ads.thrift"
include "candidate_generation_key.thrift"
include "product.thrift"
include "product_contetwittert.thrift"
include "validation.thrift"
include "metric_tags.thrift"
include "related_tweet.thrift"
include "uteg.thrift"
include "frs_based_tweet.thrift"
include "related_video_tweet.thrift"
include "topic_tweet.thrift"

include "com/twitter/product_mitwitterer/core/client_contetwittert.thrift"
include "com/twitter/timelines/render/response.thrift"
include "finatra-thrift/finatra_thrift_etwitterceptions.thrift"
include "com/twitter/strato/graphql/slice.thrift"

struct CrMitwittererTweetRequest {
	1: required client_contetwittert.ClientContetwittert clientContetwittert
	2: required product.Product product
	# Product-specific parameters should be placed in the Product Contetwittert
	3: optional product_contetwittert.ProductContetwittert productContetwittert
	4: optional list<i64> etwittercludedTweetIds (personalDataType = 'TweetId')
} (persisted='true', hasPersonalData='true')

struct TweetRecommendation {
  1: required i64 tweetId (personalDataType = 'TweetId')
  2: required double score
  3: optional list<metric_tags.MetricTag> metricTags
  # 4: the author of the tweet candidate. To be used by Content-Mitwitterer to unblock the Hydra etwitterperiment.
  4: optional i64 authorId (personalDataType = 'UserId')
  # 5: etwittertra info about candidate generation. To be used by Content-Mitwitterer to unblock the Hydra etwitterperiment.
  5: optional candidate_generation_key.CandidateGenerationKey candidateGenerationKey
  # 1001: the latest timestamp of fav signals. If null, the candidate is not generated from fav signals
  1001: optional i64 latestSourceSignalTimestampInMillis(personalDataType = 'PublicTimestamp')
} (persisted='true', hasPersonalData = 'true')

struct CrMitwittererTweetResponse {
 1: required list<TweetRecommendation> tweets
} (persisted='true')

service CrMitwitterer {
  CrMitwittererTweetResponse getTweetRecommendations(1: CrMitwittererTweetRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationEtwitterceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_etwitterceptions.ServerError serverError
  )

  # getRelatedTweetsForQueryTweet and getRelatedTweetsForQueryAuthor do very similar things
  # We can merge these two endpoints into one unified endpoint
  related_tweet.RelatedTweetResponse getRelatedTweetsForQueryTweet(1: related_tweet.RelatedTweetRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationEtwitterceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_etwitterceptions.ServerError serverError
  )

  related_tweet.RelatedTweetResponse getRelatedTweetsForQueryAuthor(1: related_tweet.RelatedTweetRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationEtwitterceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_etwitterceptions.ServerError serverError
  )

  uteg.UtegTweetResponse getUtegTweetRecommendations(1: uteg.UtegTweetRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationEtwitterceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_etwitterceptions.ServerError serverError
  )

  frs_based_tweet.FrsTweetResponse getFrsBasedTweetRecommendations(1: frs_based_tweet.FrsTweetRequest request) throws (
     # Validation errors - the details of which will be reported to clients on failure
     1: validation.ValidationEtwitterceptionList validationErrors;
     # Server errors - the details of which will not be reported to clients
     2: finatra_thrift_etwitterceptions.ServerError serverError
  )

  related_video_tweet.RelatedVideoTweetResponse getRelatedVideoTweetsForQueryTweet(1: related_video_tweet.RelatedVideoTweetRequest request) throws (
      # Validation errors - the details of which will be reported to clients on failure
      1: validation.ValidationEtwitterceptionList validationErrors;
      # Server errors - the details of which will not be reported to clients
      2: finatra_thrift_etwitterceptions.ServerError serverError
  )

  ads.AdsResponse getAdsRecommendations(1: ads.AdsRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationEtwitterceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_etwitterceptions.ServerError serverError
  )

  topic_tweet.TopicTweetResponse getTopicTweetRecommendations(1: topic_tweet.TopicTweetRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationEtwitterceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_etwitterceptions.ServerError serverError
  )
}
