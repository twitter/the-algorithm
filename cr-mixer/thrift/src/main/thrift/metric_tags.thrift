namespace java com.twitter.cr_mixer.thriftjava
#@namespace scala com.twitter.cr_mixer.thriftscala
#@namespace strato com.twitter.cr_mixer


// NOTE: DO NOT depend on MetricTags for important ML Features or business logic.
// MetricTags are meant for stats tracking & debugging purposes ONLY.
// cr-mixer may change its definitions & how each candidate is tagged without public notice.
// NOTE: TSPS needs the caller (Home) to specify which signal it uses to make Personalized Topics
enum MetricTag {
  // Source Signal Tags
  TweetFavorite         = 0
  Retweet               = 1
  TrafficAttribution    = 2
  OriginalTweet         = 3
  Reply                 = 4
  TweetShare            = 5

  UserFollow            = 101
  UserRepeatedProfileVisit = 102

  PushOpenOrNtabClick   = 201

  HomeTweetClick        = 301
  HomeVideoView         = 302

  // sim engine types
  SimClustersANN        = 401
  TweetBasedUserTweetGraph    = 402
  TweetBasedTwHINANN          = 403
  ConsumerEmbeddingBasedTwHINANN = 404


  // combined engine types
  UserInterestedIn      = 501 // Will deprecate soon
  LookalikeUTG          = 502
  TwhinCollabFilter     = 503

  // Offline Twice
  TwiceUserId           = 601

  // Other Metric Tags
  RequestHealthFilterPushOpenBasedTweetEmbedding = 701
} (persisted='true', hasPersonalData='true')
