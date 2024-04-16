#@na space scala com.tw ter.servo.cac .thr ftscala
#@ na space strato com.tw ter.servo.cac 
// t  java na space  s unused, but appeases t  thr ft L nter gods
na space java com.tw ter.servo.cac .thr ftjava

enum Cac dValueStatus {
  FOUND = 0,
  NOT_FOUND = 1,
  DELETED = 2,
  SER AL ZAT ON_FA LED = 3
  DESER AL ZAT ON_FA LED = 4,
  EV CTED = 5,
  DO_NOT_CACHE = 6
}

/**
 * Cach ng  tadata for an b nary cac  value
 */
struct Cac dValue {
  1: opt onal b nary value
  // can be used to d st ngu sh bet en delet on tombstones and not-found tombstones
  2: Cac dValueStatus status
  // w n was t  cac  value wr ten
  3:  64 cac d_at_msec
  // set  f t  cac  was read through
  4: opt onal  64 read_through_at_msec
  // set  f t  cac  was wr ten through
  5: opt onal  64 wr ten_through_at_msec
  // T  opt onal f eld  s only read w n t  Cac ValueStatus  s DO_NOT_CACHE.
  // W n Cac ValueStatus  s DO_NOT_CACHE and t  f eld  s not set, t  key
  // w ll not be cac d w hout a t   l m .  f t  cl ent wants to cac 
  //  m d ately, t y would not set DO_NOT_CACHE.
  6: opt onal  64 do_not_cac _unt l_msec
  //  nd cates how many t  s  've successfully c cked
  // t  cac d value aga nst t  back ng store. Should be  n  ally set to 0.
  // T  cl ent may choose to  ncrease t  soft TTL durat on based on t  value.
  // See http://go/gd-dynam c-cac -ttls and http://go/strato-progress ve-ttls for so  use cases
  7: opt onal  16 soft_ttl_step
} (pers sted='true')
