namespace java com.twitter.follow_recommendations.thriftjava
#@namespace scala com.twitter.follow_recommendations.thriftscala
#@namespace strato com.twitter.follow_recommendations

// Caller/Client level specific context (e.g, user id/guest id/app id).
struct ClientContext {
  1: optional i64 userId(personalDataType='UserId')
  2: optional i64 guestId(personalDataType='GuestId')
  3: optional i64 appId(personalDataType='AppId')
  4: optional string ipAddress(personalDataType='IpAddress')
  5: optional string userAgent(personalDataType='UserAgent')
  6: optional string countryCode(personalDataType='InferredCountry')
  7: optional string languageCode(personalDataType='InferredLanguage')
  9: optional bool isTwoffice(personalDataType='InferredLocation')
  10: optional set<string> userRoles
  11: optional string deviceId(personalDataType='DeviceId')
  12: optional i64 guestIdAds(personalDataType='GuestId')
  13: optional i64 guestIdMarketing(personalDataType='GuestId')
}(hasPersonalData='true')
