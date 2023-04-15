namespace java com.twitter.usersignalservice.thriftjava
namespace py gen.twitter.usersignalservice.service
#@namespace scala com.twitter.usersignalservice.thriftscala
#@namespace strato com.twitter.usersignalservice.strato

include "signal.thrift"
include "client_identifier.thrift"

struct SignalRequest {
  1: optional i64 maxResults
  2: required signal.SignalType signalType
}

struct BatchSignalRequest {
  1: required i64 userId(personalDataType = "UserId")
  2: required list<SignalRequest> signalRequest
  # make sure to populate the clientId, otherwise the service would throw exceptions
  3: optional client_identifier.ClientIdentifier clientId
}(hasPersonalData='true')

struct BatchSignalResponse {
  1: required map<signal.SignalType, list<signal.Signal>> signalResponse
}
