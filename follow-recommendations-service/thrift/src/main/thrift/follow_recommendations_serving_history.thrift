namespace java com.ExTwitter.follow_recommendations.thriftjava
#@namespace scala com.ExTwitter.follow_recommendations.thriftscala
#@namespace strato com.ExTwitter.follow_recommendations

// struct used for storing the history of computing and serving of recommendations to a user
struct FollowRecommendationsServingHistory {
  1: required i64 lastComputationTimeMs (personalDataType = 'PrivateTimestamp')
  2: required i64 lastServingTimeMs (personalDataType = 'PrivateTimestamp')
}(persisted='true', hasPersonalData='true')
