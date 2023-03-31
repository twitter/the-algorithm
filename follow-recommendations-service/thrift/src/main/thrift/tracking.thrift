namespace java com.twitter.follow_recommendations.thriftjava
#@namespace scala com.twitter.follow_recommendations.thriftscala
#@namespace strato com.twitter.follow_recommendations

include "com/twitter/suggests/controller_data/controller_data.thrift"
include "display_location.thrift"

// struct used for tracking/attribution purposes in our offline pipelines
struct TrackingToken {
  // trace-id of the request
  1: required i64 sessionId (personalDataType='SessionId')
  2: optional display_location.DisplayLocation displayLocation
  // 64-bit encoded binary attributes of our recommendation
  3: optional controller_data.ControllerData controllerData
  // WTF Algorithm Id (backward compatibility)
  4: optional i32 algoId
}(hasPersonalData='true')
