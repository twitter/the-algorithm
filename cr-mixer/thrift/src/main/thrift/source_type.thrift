namespace java com.twitter.cr_mixer.thriftjava
#@namespace scala com.twitter.cr_mixer.thriftscala
#@namespace strato com.twitter.cr_mixer

// Due to legacy reason, SourceType used to represent both SourceSignalType and SimilarityEngineType
// Hence, you can see several SourceType such as UserInterestedIn, HashSpace, etc.
// Moving forward, SourceType will be used for SourceSignalType ONLY. eg., TweetFavorite, UserFollow
// We will create a new SimilarityEngineType to separate them. eg., SimClustersANN
enum SourceType {
  // Tweet based Source Signal
  TweetFavorite       = 0
  Retweet             = 1
  TrafficAttribution  = 2 // Traffic Attribution will be migrated over in Q3
  OriginalTweet       = 3
  Reply               = 4
  TweetShare          = 5
  GoodTweetClick      = 6 // total dwell time > N seconds after click on the tweet
  VideoTweetQualityView = 7
  VideoTweetPlayback50  = 8

  // UserId based Source Signal (includes both Producer/Consumer)
  UserFollow               = 101
  UserRepeatedProfileVisit = 102

  CurrentUser_DEPRECATED   = 103

  RealGraphOon             = 104
  FollowRecommendation     = 105

  TwiceUserId              = 106
  UserTrafficAttributionProfileVisit = 107
  GoodProfileClick         = 108 // total dwell time > N seconds after click into the profile page

  // (Notification) Tweet based Source Signal
  NotificationClick   = 201

  // (Home) Tweet based Source Signal
  HomeTweetClick       = 301
  HomeVideoView        = 302
  HomeSongbirdShowMore = 303

  // Topic based Source Signal
  TopicFollow         = 401 // Deprecated
  PopularTopic        = 402 // Deprecated

  // Old CR code
  UserInterestedIn    = 501 // Deprecated
  TwiceInterestedIn   = 502 // Deprecated
  MBCG                = 503 // Deprecated
  HashSpace           = 504 // Deprecated

  // Old CR code
  Cluster             = 601 // Deprecated

  // Search based Source Signal
  SearchProfileClick  = 701 // Deprecated
  SearchTweetClick    = 702 // Deprecated

  // Graph based Source
  StrongTiePrediction      = 801 // STP
  TwiceClustersMembers     = 802
  Lookalike                = 803 // Deprecated
  RealGraphIn              = 804

  // Current requester User Id. It is only used for scribing. Placeholder value
  RequestUserId       = 1001
  // Current request Tweet Id used in RelatedTweet. Placeholder value
  RequestTweetId      = 1002

  // Negative Signals
  TweetReport = 1101
  TweetDontLike = 1102
  TweetSeeFewer = 1103
  AccountBlock = 1104
  AccountMute = 1105

  // Aggregated Signals
  TweetAggregation = 1201
  ProducerAggregation = 1202
} (persisted='true', hasPersonalData='true')

enum SimilarityEngineType {
  SimClustersANN              = 1
  TweetBasedUserTweetGraph    = 2
  TweetBasedTwHINANN          = 3
  Follow2VecANN               = 4 // ConsumerEmbeddingBasedFollow2Vec
  QIG                         = 5
  OfflineSimClustersANN       = 6
  LookalikeUTG_DEPRECATED     = 7
  ProducerBasedUserTweetGraph = 8
  FrsUTG_DEPRECATED           = 9
  RealGraphOonUTG_DEPRECATED  = 10
  ConsumerEmbeddingBasedTwHINANN = 11
  TwhinCollabFilter           = 12
  TwiceUTG_DEPRECATED         = 13
  ConsumerEmbeddingBasedTwoTowerANN = 14
  TweetBasedBeTANN            = 15
  StpUTG_DEPRECATED           = 16
  UTEG                        = 17
  ROMR                        = 18
  ConsumersBasedUserTweetGraph  = 19
  TweetBasedUserVideoGraph    = 20
  CertoTopicTweet             = 24
  ConsumersBasedUserAdGraph   = 25
  TweetBasedUserAdGraph       = 26
  SkitTfgTopicTweet           = 27
  ConsumerBasedWalsANN        = 28
  ProducerBasedUserAdGraph    = 29
  SkitHighPrecisionTopicTweet = 30
  SkitInterestBrowserTopicTweet = 31
  SkitProducerBasedTopicTweet   = 32
  ExploreTripOfflineSimClustersTweets = 33
  DiffusionBasedTweet = 34
  ConsumersBasedUserVideoGraph  = 35

  // In network
  EarlybirdRecencyBasedSimilarityEngine = 21
  EarlybirdModelBasedSimilarityEngine = 22
  EarlybirdTensorflowBasedSimilarityEngine = 23
  // Composite
  TweetBasedUnifiedSimilarityEngine    = 1001
  ProducerBasedUnifiedSimilarityEngine = 1002
} (persisted='true')
