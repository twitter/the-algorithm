na space java com.tw ter.t etyp e.thr ftjava
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e
na space py gen.tw ter.t etyp e.ed _control
na space rb T etyP e
// Spec f c na space to avo d golang c rcular  mport
na space go t etyp e.t et

/**
 * Ed Control n  al  s present on all new T ets.  n  ally, ed _t et_ ds w ll only conta n t   d of t  new T et.
 * Subsequent ed s w ll append t  ed ed T et  ds to ed _t et_ ds.
**/
struct Ed Control n  al {
 /**
  * A l st of all ed s of t   n  al T et,  nclud ng t   n  al T et  d,
  * and  n ascend ng t   order (t  oldest rev s on f rst).
  */
  1: requ red l st< 64> ed _t et_ ds = [] (personalDataType = 'T et d', strato.json.numbers.type = 'str ng')
 /**
  * Epoch t  stamp  n m ll -seconds (UTC) after wh ch t  t et w ll no longer be ed able.
  */
  2: opt onal  64 ed able_unt l_msecs (strato.json.numbers.type = 'str ng')
 /**
  * Number of ed s that are ava lable for t  T et. T  starts at 5 and decre nts w h each ed .
  */
  3: opt onal  64 ed s_rema n ng (strato.json.numbers.type = 'str ng')

  /**
   * Spec f es w t r t  T et has any  ntr ns c propert es that  an   can't be ed ed
   * (for example,   have a bus ness rule that poll T ets can't be ed ed).
   *
   *  f a T et ed  exp res due to t   fra  or number of ed s, t  f eld st ll  s set
   * to true for T ets that could have been ed ed.
   */
  4: opt onal bool  s_ed _el g ble
}(pers sted='true', hasPersonalData = 'true', strato.graphql.typena ='Ed Control n  al')

/**
 * Ed ControlEd   s present for any T ets that are an ed  of anot r T et. T  full l st of ed s can be retr eved
 * from t  ed _control_ n  al f eld, wh ch w ll always be hydrated.
**/
struct Ed ControlEd  {
  /**
   * T   d of t   n  al T et  n an ed  cha n
   */
  1: requ red  64  n  al_t et_ d (personalDataType = 'T et d', strato.json.numbers.type = 'str ng')
  /**
  * T  f eld  s only used dur ng hydrat on to return t  Ed Control of t   n  al T et for
  * a subsequently ed ed vers on.
  */
  2: opt onal Ed Control n  al ed _control_ n  al
}(pers sted='true', hasPersonalData = 'true', strato.graphql.typena ='Ed ControlEd ')


/**
 * T et  tadata about ed s of a T et. A l st of ed s to a T et are represented as a cha n of
 * T ets l nked to each ot r us ng t  Ed Control f eld.
 *
 * Ed Control can be e  r Ed Control n  al wh ch  ans that t  T et  s uned ed or t  f rst T et  n
 * an ed  cha n, or Ed ControlEd  wh ch  ans    s a T et  n t  ed  cha n after t  f rst
 * T et.
 */
un on Ed Control {
  1: Ed Control n  al  n  al
  2: Ed ControlEd  ed 
}(pers sted='true', hasPersonalData = 'true', strato.graphql.typena ='Ed Control')


serv ce FederatedServ ceBase {
  Ed Control getEd Control(1: requ red  64 t et d)
}
