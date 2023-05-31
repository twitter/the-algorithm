namespace java com.twitter.tweetypie.thriftjava
namespace py gen.twitter.tweetypie.tweet_audit
#@namespace scala com.twitter.tweetypie.thriftscala
#@namespace strato com.twitter.tweetypie
namespace rb TweetyPie
namespace go tweetypie

// Copied from UserActionReason in guano.thrift - this should be kept in sync (though upper cased)
enum AuditUserActionReason {
  SPAM
  CHURNING
  OTHER
  PHISHING
  BOUNCING

  RESERVED_1
  RESERVED_2
}

// This struct contains all fields of DestroyStatus in guano.thrift that can be set per remove/deleteTweets invocation
// Values are passed through TweetyPie as-is to guano scribe and not used by TweetyPie.
struct AuditDeleteTweet { 
  1: optional string host (personalDataType = 'IpAddress')
  2: optional string bulk_id
  3: optional AuditUserActionReason reason
  4: optional string note
  5: optional bool done
  6: optional string run_id
  // OBSOLETE 7: optional i64 id
  8: optional i64 client_application_id (personalDataType = 'AppId')
  9: optional string user_agent (personalDataType = 'UserAgent') 
}(persisted = 'true', hasPersonalData = 'true')
