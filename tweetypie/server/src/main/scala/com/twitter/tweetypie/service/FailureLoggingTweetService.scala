package com.tw ter.t etyp e
package serv ce

 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.coreserv ces.fa led_task.wr er.Fa ledTaskWr er
 mport com.tw ter.scrooge.Thr ftExcept on
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.scrooge.Thr ftStructCodec
 mport com.tw ter.t etyp e.serverut l.Bor ngStackTrace
 mport com.tw ter.t etyp e.thr ftscala._
 mport scala.ut l.control.NoStackTrace

object Fa lureLogg ngT etServ ce {

  /**
   * Def nes t  un verse of except on types for wh ch   should scr be
   * t  fa lure.
   */
  pr vate def shouldWr e(t: Throwable): Boolean =
    t match {
      case _: Thr ftExcept on => true
      case _: PostT etFa lure => true
      case _ => !Bor ngStackTrace. sBor ng(t)
    }

  /**
   * Holds fa lure  nformat on from a fa l ng PostT etResult.
   *
   * Fa ledTaskWr er logs an except on w h t  fa led request, so  
   * need to package up any fa lure that   want to log  nto an
   * except on.
   */
  pr vate class PostT etFa lure(state: T etCreateState, reason: Opt on[Str ng])
      extends Except on
      w h NoStackTrace {
    overr de def toStr ng: Str ng = s"PostT etFa lure($state, $reason)"
  }
}

/**
 * Wraps a t et serv ce w h scr b ng of fa led requests  n order to
 * enable analys s of fa lures for d agnos ng problems.
 */
class Fa lureLogg ngT etServ ce(
  fa ledTaskWr er: Fa ledTaskWr er[Array[Byte]],
  protected val underly ng: Thr ftT etServ ce)
    extends T etServ ceProxy {
   mport Fa lureLogg ngT etServ ce._

  pr vate[t ] object wr ers {
    pr vate[t ] def wr er[T <: Thr ftStruct](
      na : Str ng,
      codec: Thr ftStructCodec[T]
    ): (T, Throwable) => Future[Un ] = {
      val taskWr er = fa ledTaskWr er(na , B naryScalaCodec(codec).apply)

      (t, exc) =>
        Future.w n(shouldWr e(exc)) {
          taskWr er.wr eFa lure(t, exc)
        }
    }

    val postT et: (PostT etRequest, Throwable) => Future[Un ] =
      wr er("post_t et", PostT etRequest)
  }

  overr de def postT et(request: PostT etRequest): Future[PostT etResult] =
    underly ng.postT et(request).respond {
      // Log requests for states ot r than OK to enable debugg ng creat on fa lures
      case Return(res)  f res.state != T etCreateState.Ok =>
        wr ers.postT et(request, new PostT etFa lure(res.state, res.fa lureReason))
      case Throw(exc) =>
        wr ers.postT et(request, exc)
      case _ =>
    }
}
