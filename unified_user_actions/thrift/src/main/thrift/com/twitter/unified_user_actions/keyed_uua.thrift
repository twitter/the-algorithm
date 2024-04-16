na space java com.tw ter.un f ed_user_act ons.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons

 nclude "com/tw ter/un f ed_user_act ons/act on_ nfo.thr ft"
 nclude "com/tw ter/un f ed_user_act ons/common.thr ft"
 nclude "com/tw ter/un f ed_user_act ons/ tadata.thr ft"

/*
 * T   s ma nly for V ew Counts project, wh ch only requ re m n mum f elds for now.
 * T  na  KeyedUuaT et  nd cates t  value  s about a T et, not a Mo nt or ot r ent  es.
 */
struct KeyedUuaT et {
   /* A user refers to e  r a logged  n / logged out user */
   1: requ red common.User dent f er user dent f er
   /* T  t et that rece ved t  act on from t  user */
   2: requ red  64 t et d(personalDataType='T et d')
   /* T  type of act on wh ch took place */
   3: requ red act on_ nfo.Act onType act onType
   /* Useful for event level analys s and jo ns */
   4: requ red  tadata.Event tadata event tadata
}(pers sted='true', hasPersonalData='true')
