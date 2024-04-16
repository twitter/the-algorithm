package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.thr ftscala.Share

/**
 * W n creat ng a ret et,   set parent_status_ d to t  t et  d that t  user sent (t  t et t y're ret et ng).
 * Old t ets have parent_status_ d set to zero.
 * W n load ng t  old t ets,   should set parent_status_ d to s ce_status_ d  f  's zero.
 */
object Ret etParentStatus dRepa rer {
  pr vate val shareMutat on =
    Mutat on.fromPart al[Opt on[Share]] {
      case So (share)  f share.parentStatus d == 0L =>
        So (share.copy(parentStatus d = share.s ceStatus d))
    }

  pr vate[t etyp e] val t etMutat on = T etLenses.share.mutat on(shareMutat on)
}
