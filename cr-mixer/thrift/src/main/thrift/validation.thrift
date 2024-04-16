na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

// Val dat onErrorCode  s used to  dent fy classes of cl ent errors returned from a Product M xer
// serv ce. Use [[P pel neFa lureExcept onMapper]] to adapt p pel ne fa lures  nto thr ft errors.
enum Val dat onErrorCode {
  PRODUCT_D SABLED = 1
  PLACEHOLDER_2 = 2
} (hasPersonalData='false')

except on Val dat onExcept on {
  1: Val dat onErrorCode errorCode
  2: str ng msg
} (hasPersonalData='false')

except on Val dat onExcept onL st {
  1: l st<Val dat onExcept on> errors
} (hasPersonalData='false')
