namespace java com.twitter.follow_recommendations.thriftjava
#@namespace scala com.twitter.follow_recommendations.thriftscala
#@namespace strato com.twitter.follow_recommendations

// Proof based on Follow relationship
struct FollowProof {
  1: required list<i64> userIds(personalDataType='UserId')
  2: required i32 numIds(personalDataType='CountOfFollowersAndFollowees')
}(hasPersonalData='true')

// Similar to userIds in the context (e.g. profileId)
struct SimilarToProof {
  1: required list<i64> userIds(personalDataType='UserId')
}(hasPersonalData='true')

// Proof based on geo location
struct PopularInGeoProof {
  1: required string location(personalDataType='InferredLocation')
}(hasPersonalData='true')

// Proof based on ttt interest
struct TttInterestProof {
  1: required i64 interestId(personalDataType='ProvidedInterests')
  2: required string interestDisplayName(personalDataType='ProvidedInterests')
}(hasPersonalData='true')

// Proof based on topics
struct TopicProof {
  1: required i64 topicId(personalDataType='ProvidedInterests')
}(hasPersonalData='true')

// Proof based on custom interest / search queries
struct CustomInterestProof {
  1: required string query(personalDataType='SearchQuery')
}(hasPersonalData='true')

// Proof based on tweet authors
struct TweetsAuthorProof {
  1: required list<i64> tweetIds(personalDataType='TweetId')
}(hasPersonalData='true')

// Proof candidate is of device follow type
struct DeviceFollowProof {
  1: required bool isDeviceFollow(personalDataType='OtherDeviceInfo')
}(hasPersonalData='true')

// Account level proof that should be attached to each candidate
struct AccountProof {
  1: optional FollowProof followProof
  2: optional SimilarToProof similarToProof
  3: optional PopularInGeoProof popularInGeoProof
  4: optional TttInterestProof tttInterestProof
  5: optional TopicProof topicProof
  6: optional CustomInterestProof customInterestProof
  7: optional TweetsAuthorProof tweetsAuthorProof
  8: optional DeviceFollowProof deviceFollowProof
}(hasPersonalData='true')

struct Reason {
  1: optional AccountProof accountProof 
}(hasPersonalData='true')
