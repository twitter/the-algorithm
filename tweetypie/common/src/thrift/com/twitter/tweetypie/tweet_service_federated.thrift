namespace java com.twitter.tweetypie.thriftjava.federated
#@namespace scala com.twitter.tweetypie.thriftscala.federated
#@namespace strato com.twitter.tweetypie.federated

include "com/twitter/tweetypie/stored_tweet_info.thrift"

typedef i16 FieldId

struct GetStoredTweetsView {
  1: bool bypass_visibility_filtering = 0
  2: optional i64 for_user_id
  3: list<FieldId> additional_field_ids = []
}

struct GetStoredTweetsResponse {
  1: stored_tweet_info.StoredTweetInfo stored_tweet
}

struct GetStoredTweetsByUserView {
  1: bool bypass_visibility_filtering = 0
  2: bool set_for_user_id = 0
  3: optional i64 start_time_msec
  4: optional i64 end_time_msec
  5: optional i64 cursor
  6: bool start_from_oldest = 0
  7: list<FieldId> additional_field_ids = []
}

struct GetStoredTweetsByUserResponse {
  1: required list<stored_tweet_info.StoredTweetInfo> stored_tweets
  2: optional i64 cursor
}
