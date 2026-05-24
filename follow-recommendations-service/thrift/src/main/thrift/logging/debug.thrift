namespace java com.twitter.follow_recommendations.logging.thriftjava
#@namespace scala com.twitter.follow_recommendations.logging.thriftscala
#@namespace strato com.twitter.follow_recommendation.logging

// subset of DebugParams
struct OfflineDebugParams {
    1: optional i64 randomizationSeed // track if the request was randomly ranked or not
}(persisted='true', hasPersonalData='false')
