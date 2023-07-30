namespace java com.X.unified_user_actions.thriftjava
#@namespace scala com.X.unified_user_actions.thriftscala
#@namespace strato com.X.unified_user_actions

/* Useful for testing UnifiedUserAction-like schema in tests */
struct UnifiedUserActionSpec {
   /* A user refers to either a logged out / logged in user */
   1: required i64 userId
   /* Arbitrary payload */
   2: optional string payload
}(hasPersonalData='false')
