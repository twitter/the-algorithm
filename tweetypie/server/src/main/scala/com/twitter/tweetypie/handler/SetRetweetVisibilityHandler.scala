package com.tw ter.t etyp e
package handler

 mport com.tw ter.t etyp e.store.SetRet etV s b l y
 mport com.tw ter.t etyp e.thr ftscala.SetRet etV s b l yRequest
 mport com.tw ter.t etyp e.thr ftscala.Share
 mport com.tw ter.t etyp e.thr ftscala.T et

/**
 * Create a [[SetRet etV s b l y.Event]] from a [[SetRet etV s b l yRequest]] and t n
 * p pe t  event to [[store.SetRet etV s b l y]]. T  event conta ns t   nformat on
 * to determ ne  f a ret et should be  ncluded  n  s s ce t et's ret et count.
 *
 * Show ng/h d ng a ret et count  s done by call ng TFlock to mod fy an edge's state bet en
 * `Pos  ve` <--> `Arch ved`  n t  Ret etsGraph(6) and mod fy ng t  count  n cac  d rectly.
 */
object SetRet etV s b l yHandler {
  type Type = SetRet etV s b l yRequest => Future[Un ]

  def apply(
    t etGetter: T et d => Future[Opt on[T et]],
    setRet etV s b l yStore: SetRet etV s b l y.Event => Future[Un ]
  ): Type =
    req =>
      t etGetter(req.ret et d).map {
        case So (ret et) =>
          getShare(ret et).map { share: Share =>
            val event = SetRet etV s b l y.Event(
              ret et d = req.ret et d,
              v s ble = req.v s ble,
              src d = share.s ceStatus d,
              ret etUser d = getUser d(ret et),
              srcT etUser d = share.s ceUser d,
              t  stamp = T  .now
            )
            setRet etV s b l yStore(event)
          }

        case None =>
          // No-op  f e  r t  ret et has been deleted or has no s ce  d.
          //  f deleted, t n   do not want to acc dentally undelete a leg  mately deleted ret ets.
          //  f no s ce  d, t n   do not know t  s ce t et to mod fy  s count.
          Un 
      }
}
