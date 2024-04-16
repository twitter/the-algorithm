na space java com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons.enr c r. nternal

/*
 *  nternal key used for control ng UUA enr ch nt & cach ng process.   conta ns very m n mal
 *  nformat on to allow for eff c ent serde, fast data look-up and to dr ve t  part on ng log cs.
 *
 * NOTE: Don't depend on    n y  appl cat on.
 * NOTE: T   s used  nternally by UUA and may change at anyt  . T re's no guarantee for
 * backward / forward-compat b l y.
 * NOTE: Don't add any ot r  tadata unless    s needed for part  on ng log c. Extra enr ch nt
 *  tdata can go  nto t  envelop.
 */
struct Enr ch ntKey {
   /*
   * T   nternal type of t  pr mary  D used for part  on ng UUA data.
   *
   * Each type should d rectly correspond to an ent y-level  D  n UUA.
   * For example, T et nfo.act onT et d & T etNot f cat on.t et d are all t et-ent y level
   * and should correspond to t  sa  pr mary  D type.
   **/
   1: requ red Enr ch nt dType keyType

   /**
   * T  pr mary  D. T   s usually a long, for ot r  ncompat ble data type such as str ng or
   * a bytes array, t y can be converted  nto a long us ng t  r nat ve hashCode() funct on.
   **/
   2: requ red  64  d
}(pers sted='true', hasPersonalData='true')

/**
* T  type of t  pr mary  D. For example, t et d on a t et & t et d on a not f cat on are
* all T et d type. S m larly, User D of a v e r and Author D of a t et are all User D type.
*
* T  type  re ensures that   w ll part  on UUA data correctly across d fferent ent y-type
* (user, t ets, not f cat on, etc.)
**/
enum Enr ch nt dType {
  T et d = 0
}
