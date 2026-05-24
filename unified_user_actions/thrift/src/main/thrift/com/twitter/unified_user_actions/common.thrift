namespace java com.twitter.unified_user_actions.thriftjava
#@namespace scala com.twitter.unified_user_actions.thriftscala
#@namespace strato com.twitter.unified_user_actions

/*
 * Uniquely identifies a user. A user identifier
 * for a logged in user should contain a user id
 * and a user identifier for a logged out user should
 * contain some guest id. A user may have multiple ids.
 */
struct UserIdentifier {
  1: optional i64 userId(personalDataType='UserId')
  /*
   * See http://go/guest-id-cookie-tdd. As of Dec 2021,
   * guest id is intended only for essential use cases
   * (e.g. logged out preferences, security). Guest id
   * marketing is intended for recommendation use cases.
   */
  2: optional i64 guestIdMarketing(personalDataType='GuestId')
}(persisted='true', hasPersonalData='true')
