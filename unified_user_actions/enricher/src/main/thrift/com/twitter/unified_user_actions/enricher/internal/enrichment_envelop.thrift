na space java com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons.enr c r. nternal

 nclude "com/tw ter/un f ed_user_act ons/un f ed_user_act ons.thr ft"
 nclude "enr ch nt_plan.thr ft"

struct Enr ch ntEnvelop {
  /**
  * An  nternal  D that un quely  dent f es t  event created dur ng t  early stages of enr ch nt.
  *    s useful for detect ng debugg ng, trac ng & prof l ng t  events throughout t  process.
  **/
  1: requ red  64 envelop d

  /**
  * T  UUA event to be enr c d / currently be ng enr c d / has been enr c d depend ng on t 
  * stages of t  enr ch nt process.
  **/
  2: un f ed_user_act ons.Un f edUserAct on uua

  /**
  * T  current enr ch nt plan.   keeps track of what  s currently be ng enr c d, what st ll
  * needs to be done so that   can br ng t  enr ch nt process to complet on.
  **/
  3: enr ch nt_plan.Enr ch ntPlan plan
}(pers sted='true', hasPersonalData='true')
