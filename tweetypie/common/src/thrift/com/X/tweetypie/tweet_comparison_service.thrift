namespace java com.X.tweetypiecomparison.thriftjava
#@namespace scala com.X.tweetypiecomparison.thriftscala
#@namespace strato com.X.tweetypiecomparison

include "com/X/tweetypie/tweet_service.thrift"
include "com/X/context/viewer.thrift"

service TweetComparisonService {
  void compare_retweet(
    1: tweet_service.RetweetRequest request,
    2: optional viewer.Viewer viewer
  )

  void compare_post_tweet(
    1: tweet_service.PostTweetRequest request,
    2: optional viewer.Viewer viewer
  )

  void compare_unretweet(
    1: tweet_service.UnretweetRequest request,
    2: optional viewer.Viewer viewer
  )

  void compare_delete_tweets(
    1: tweet_service.DeleteTweetsRequest request,
    2: optional viewer.Viewer viewer
  )
}
