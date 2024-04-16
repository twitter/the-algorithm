na space java com.tw ter.un f ed_user_act ons.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons

/* Useful for test ng Un f edUserAct on-l ke sc ma  n tests */
struct Un f edUserAct onSpec {
   /* A user refers to e  r a logged out / logged  n user */
   1: requ red  64 user d
   /* Arb rary payload */
   2: opt onal str ng payload
}(hasPersonalData='false')
