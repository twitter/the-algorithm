namespace java com.twitter.cr_mixer.thriftjava
#@namespace scala com.twitter.cr_mixer.thriftscala
#@namespace strato com.twitter.cr_mixer

include "ads.thrift"
include "candidate_generation_key.thrift"
include "product.thrift"
include "product_context.thrift"
include "validation.thrift"
include "metric_tags.thrift"
include "related_tweet.thrift"
include "uteg.thrift"
include "frs_based_tweet.thrift"
include "related_video_tweet.thrift"
include "topic_tweet.thrift"

include "com/twitter/product_mixer/core/client_context.thrift"
include "com/twitter/timelines/render/response.thrift"
include "finatra-thrift/finatra_thrift_exceptions.thrift"
include "com/twitter/strato/graphql/slice.thrift"

struct CrMixerTweetRequest {
	1: required client_context.ClientContext clientContext
	2: required product.Product product
	# Product-specific parameters should be placed in the Product Context
	3: optional product_context.ProductContext productContext
	4: optional list<i64> excludedTweetIds (personalDataType = 'TweetId')
} (persisted='true', hasPersonalData='true')

struct TweetRecommendation {
  1: required i64 tweetId (personalDataType = 'TweetId')
  2: required double score
  3: optional list<metric_tags.MetricTag> metricTags
  # 4: the author of the tweet candidate. To be used by Content-Mixer to unblock the Hydra experiment.
  4: optional i64 authorId (personalDataType = 'UserId')
  # 5: extra info about candidate generation. To be used by Content-Mixer to unblock the Hydra experiment.
  5: optional candidate_generation_key.CandidateGenerationKey candidateGenerationKey
  # 1001: the latest timestamp of fav signals. If null, the candidate is not generated from fav signals
  1001: optional i64 latestSourceSignalTimestampInMillis(personalDataType = 'PublicTimestamp')
} (persisted='true', hasPersonalData = 'true')

struct CrMixerTweetResponse {
 1: required list<TweetRecommendation> tweets
} (persisted='true')

service CrMixer {
  CrMixerTweetResponse getTweetRecommendations(1: CrMixerTweetRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationExceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_exceptions.ServerError serverError
  )

  # getRelatedTweetsForQueryTweet and getRelatedTweetsForQueryAuthor do very similar things
  # We can merge these two endpoints into one unified endpoint
  related_tweet.RelatedTweetResponse getRelatedTweetsForQueryTweet(1: related_tweet.RelatedTweetRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationExceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_exceptions.ServerError serverError
  )

  related_tweet.RelatedTweetResponse getRelatedTweetsForQueryAuthor(1: related_tweet.RelatedTweetRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationExceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_exceptions.ServerError serverError
  )

  uteg.UtegTweetResponse getUtegTweetRecommendations(1: uteg.UtegTweetRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationExceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_exceptions.ServerError serverError
  )

  frs_based_tweet.FrsTweetResponse getFrsBasedTweetRecommendations(1: frs_based_tweet.FrsTweetRequest request) throws (
     # Validation errors - the details of which will be reported to clients on failure
     1: validation.ValidationExceptionList validationErrors;
     # Server errors - the details of which will not be reported to clients
     2: finatra_thrift_exceptions.ServerError serverError
  )

  related_video_tweet.RelatedVideoTweetResponse getRelatedVideoTweetsForQueryTweet(1: related_video_tweet.RelatedVideoTweetRequest request) throws (
      # Validation errors - the details of which will be reported to clients on failure
      1: validation.ValidationExceptionList validationErrors;
      # Server errors - the details of which will not be reported to clients
      2: finatra_thrift_exceptions.ServerError serverError
  )

  ads.AdsResponse getAdsRecommendations(1: ads.AdsRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationExceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_exceptions.ServerError serverError
  )

  topic_tweet.TopicTweetResponse getTopicTweetRecommendations(1: topic_tweet.TopicTweetRequest request) throws (
    # Validation errors - the details of which will be reported to clients on failure
    1: validation.ValidationExceptionList validationErrors;
    # Server errors - the details of which will not be reported to clients
    2: finatra_thrift_exceptions.ServerError serverError
  )
}
