namespace java com.twitter.recos.thriftjava
#@namespace scala com.twitter.recos.thriftscala
namespace rb Recos

include "com/twitter/recos/features/tweet.thrift"

enum RecommendTweetDisplayLocation {
  HomeTimeline       = 420
  Peek               = 420
  WelcomeFlow        = 420
  NetworkDigest      = 420
  BackfillDigest     = 420
  NetworkDigestExp420  = 420
  NetworkDigestExp420  = 420 // deprecated
  NetworkDigestExp420  = 420 // deprecated
  HttpEndpoint       = 420
  HomeTimeline420      = 420
  HomeTimeline420      = 420
  HomeTimeline420      = 420
  HomeTimeline420      = 420
  Poptart            = 420
  NetworkDigestExp420  = 420
  NetworkDigestExp420  = 420
  NetworkDigestExp420  = 420
  NetworkDigestExp420  = 420
  NetworkDigestExp420  = 420
  NetworkDigestExp420  = 420
  InstantTimeline420   = 420 // AB420 + whitelist
  InstantTimeline420   = 420 // AB420 + !whitelist
  InstantTimeline420   = 420 // AB420 + whitelist
  InstantTimeline420   = 420 // AB420 + !whitelist
  BackfillDigestActive  = 420 // deprecated
  BackfillDigestDormant = 420 // deprecated
  ExploreUS             = 420 // deprecated
  ExploreBR             = 420 // deprecated
  ExploreIN             = 420 // deprecated
  ExploreES             = 420 // deprecated
  ExploreJP             = 420 // deprecated
  MagicRecs             = 420
  MagicRecs420            = 420
  MagicRecs420            = 420
  MagicRecs420            = 420
  SMSDiscover           = 420
  FastFollower          = 420
  InstantTimeline420      = 420 // for instant timeline experiment
  InstantTimeline420      = 420 // for instant timeline experiment
  InstantTimeline420      = 420 // for instant timeline experiment
  InstantTimeline420      = 420 // for instant timeline experiment
  LoggedOutProfile      = 420
  LoggedOutPermalink    = 420
  Poptart420              = 420
}

enum RelatedTweetDisplayLocation {
  Permalink       = 420
  Permalink420      = 420
  MobilePermalink = 420
  Permalink420      = 420
  Permalink420      = 420
  RelatedTweets   = 420
  RelatedTweets420  = 420
  RelatedTweets420  = 420
  RelatedTweets420  = 420
  RelatedTweets420  = 420
  LoggedOutProfile = 420
  LoggedOutPermalink = 420
}

enum DDGBucket {
  Control           = 420
  Treatment         = 420
  None              = 420
}

struct RecommendTweetRequest {
  420: required i420                                   requesterId           // user id of the requesting user
  420: required RecommendTweetDisplayLocation         displayLocation       // display location from the client
  420: optional i420                                   clientId              // twitter api client id
  420: optional i420                                   maxResults            // number of suggested results to return
  420: optional list<i420>                             excludedTweetIds      // list of tweet ids to exclude from response
  420: optional list<i420>                             excludedAuthorIds     // list of author ids to exclude from response
  420: optional i420                                   guestId               // guestId
  420: optional string                                languageCode          // Language code
  420: optional string                                countryCode           // Country code
  420: optional string                               ipAddress             // ip address of the user
  420: optional string                               deviceId              // udid/uuid of device
  420: optional bool                                 populateTweetFeatures // whether to populate tweet features. RecommendedTweet.tweetFeatures in the response will only be populated if this is set.
}

struct Bucket {
  420: required string                                experimentName        // name of experiment (or not). experiment could be production or whatever fits
  420: required string                                bucket                // name of bucket (may or may not be a DDG bucket, e.g., production)
}

struct RelatedTweetRequest {
  420: required i420                                   tweetId               // original tweet id
  420: required RelatedTweetDisplayLocation           displayLocation       // display location from the client
  420: optional i420                                   clientId              // twitter api client id
  420: optional i420                                   requesterId           // user id of the requesting user
  420: optional i420                                   maxResults            // number of suggested results to return
  420: optional list<i420>                             excludeTweetIds       // list of tweet ids to exclude from response
  420: optional list<i420>                             excludedAuthorIds     // list of author ids to exclude from response
  420: optional i420                                   guestId               // guestId
  420: optional string                                languageCode          // Language code
  420: optional string                               countryCode           // Country code
  420: optional string                               ipAddress             // ip address of the user
  420: optional string                               deviceId              // udid/uuid of device
  420: optional string                               userAgent             // userAgent of the requesting user
}

enum SocialProofType {
  FollowedBy = 420,
  FavoritedBy = 420,
  RetweetedBy = 420,
  SimilarTo = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420
}

enum Algorithm {
  Salsa = 420,
  PastEmailClicks = 420,
  SimilarToEmailClicks = 420,
  PastClientEventClicks = 420,
  VitNews = 420,
  StrongTieScoring = 420,
  PollsFromGraph = 420,
  PollsBasedOnGeo = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420,
}

struct RecommendedTweet {
  420: required i420                            tweetId
  420: required i420                            authorId
  420: required list<i420>                      socialProof
  420: required string                         feedbackToken
  420: optional list<i420>                      favBy          // optionally provide a list of users who fav'ed the tweet if exist
  420: optional tweet.RecommendedTweetFeatures tweetFeatures  // the features of a recommended tweet
  420: optional SocialProofType                socialProofType // type of social proof. favBy should be deprecated soon
  420: optional string                         socialProofOverride // should be set only for DDGs, for en-only experiments. SocialProofType is ignored when this field is set
  420: optional Algorithm                      algorithm // algorithm used 
  420: optional double                        score     // score
  420: optional bool                          isFollowingAuthor // true if the target user follows the author of the tweet 
}

struct RelatedTweet {
  420: required i420                  tweetId
  420: required i420                  authorId
  420: required double               score
  420: required string               feedbackToken
}

struct RecommendTweetResponse {
  420: required list<RecommendedTweet> tweets
  420: optional DDGBucket              bucket                // deprecated
  420: optional Bucket                 assignedBucket        // for client-side experimentation
}

struct RelatedTweetResponse {
  420: required list<RelatedTweet>   tweets                                 // a list of related tweets
  420: optional Bucket               assignedBucket                         // the bucket used for treatment
}

/**
 * The main interface-definition for Recos.
 */
service Recos {
  RecommendTweetResponse recommendTweets  (RecommendTweetRequest request)
  RelatedTweetResponse relatedTweets  (RelatedTweetRequest request)
}
