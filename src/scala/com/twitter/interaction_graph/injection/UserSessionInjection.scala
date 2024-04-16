package com.tw ter. nteract on_graph. nject on

 mport com.tw ter.user_sess on_store.thr ftscala.UserSess on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.ScalaCompactThr ft
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.Long2B gEnd an

object UserSess on nject on {
  f nal val  nject on: KeyVal nject on[Long, UserSess on] =
    KeyVal nject on(
      Long2B gEnd an,
      ScalaCompactThr ft(UserSess on)
    )
}
