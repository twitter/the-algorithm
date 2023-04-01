namespace java com.twitter.follow_recommendations.logging.thriftjava
#@namespace scala com.twitter.follow_recommendations.logging.thriftscala
#@namespace strato com.twitter.follow_recommendations.logging

// Offline equal of ClientContext
struct OfflineClientContext {
  1: optional i64 userId(personalDataType='UserId')
  2: optional i64 guestId(personalDataType='GuestId')
  3: optional i64 appId(personalDataType='AppId')
  4: optional string countryCode(personalDataType='InferredCountry')
  5: optional string languageCode(personalDataType='InferredLanguage')
  6: optional i64 guestIdAds(personalDataType='GuestId')
  7: optional i64 guestIdMarketing(personalDataType='GuestId')
}(persisted='true', hasPersonalData='true')
