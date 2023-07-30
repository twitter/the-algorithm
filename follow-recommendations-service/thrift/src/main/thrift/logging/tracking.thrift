namespace java com.X.follow_recommendations.logging.thriftjava
#@namespace scala com.X.follow_recommendations.logging.thriftscala
#@namespace strato com.X.follow_recommendations.logging

include "com/X/suggests/controller_data/controller_data.thrift"
include "display_location.thrift"

struct TrackingToken {
  // trace-id of the request
  1: required i64 sessionId (personalDataType='SessionId')
  2: optional display_location.OfflineDisplayLocation displayLocation
  // 64-bit encoded binary attributes of our recommendation
  3: optional controller_data.ControllerData controllerData
  // WTF Algorithm Id (backward compatibility)
  4: optional i32 algoId
}(persisted='true', hasPersonalData='true')
