na space java com.tw ter.un f ed_user_act ons.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons

 nclude "com/tw ter/un f ed_user_act ons/act on_ nfo.thr ft"
 nclude "com/tw ter/un f ed_user_act ons/common.thr ft"
 nclude "com/tw ter/un f ed_user_act ons/ em.thr ft"
 nclude "com/tw ter/un f ed_user_act ons/ tadata.thr ft"
 nclude "com/tw ter/un f ed_user_act ons/product_surface_ nfo.thr ft"

/*
 * A Un f ed User Act on (UUA)  s essent ally a tuple of
 * (user,  em, act on type, so   tadata) w h more opt onal
 *  nformat on un que to product surfaces w n ava lable.
 *   represents a user (logged  n / out) tak ng so  act on (e.g. engage nt,
 *  mpress on) on an  em (e.g. t et, prof le).
 */
struct Un f edUserAct on {
   /* A user refers to e  r a logged  n / logged out user */
   1: requ red common.User dent f er user dent f er
   /* T   em that rece ved t  act on from t  user */
   2: requ red  em. em  em
   /* T  type of act on wh ch took place */
   3: requ red act on_ nfo.Act onType act onType
   /* Useful for event level analys s and jo ns */
   4: requ red  tadata.Event tadata event tadata
   /* 
    * Product surface on wh ch t  act on occurred.  f None,
    *    ans   can not capture t  product surface (e.g. for server-s de events).
    */
   5: opt onal product_surface_ nfo.ProductSurface productSurface
   /* 
    * Product spec f c  nformat on l ke jo n keys.  f None,
    *    ans   can not capture t  product surface  nformat on.
    */
   6: opt onal product_surface_ nfo.ProductSurface nfo productSurface nfo
}(pers sted='true', hasPersonalData='true')
