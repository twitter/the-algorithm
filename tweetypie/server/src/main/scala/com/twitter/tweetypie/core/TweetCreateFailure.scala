package com.tw ter.t etyp e.core

 mport com.tw ter.bouncer.thr ftscala.Bounce
 mport com.tw ter.t etyp e.T et d
 mport com.tw ter. ncent ves.j m ny.thr ftscala.T etNudge
 mport com.tw ter.t etyp e.thr ftscala.PostT etResult
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState

sealed abstract class T etCreateFa lure extends Except on {
  def toPostT etResult: PostT etResult
}

object T etCreateFa lure {
  case class Bounced(bounce: Bounce) extends T etCreateFa lure {
    overr de def toPostT etResult: PostT etResult =
      PostT etResult(state = T etCreateState.Bounce, bounce = So (bounce))
  }

  case class AlreadyRet eted(ret et d: T et d) extends T etCreateFa lure {
    overr de def toPostT etResult: PostT etResult =
      PostT etResult(state = T etCreateState.AlreadyRet eted)
  }

  case class Nudged(nudge: T etNudge) extends T etCreateFa lure {
    overr de def toPostT etResult: PostT etResult =
      PostT etResult(state = T etCreateState.Nudge, nudge = So (nudge))
  }

  case class State(state: T etCreateState, reason: Opt on[Str ng] = None)
      extends T etCreateFa lure {
    requ re(state != T etCreateState.Bounce)
    requ re(state != T etCreateState.Ok)
    requ re(state != T etCreateState.Nudge)

    overr de def toPostT etResult: PostT etResult =
      PostT etResult(state = state, fa lureReason = reason)
    overr de def toStr ng: Str ng = s"T etCreateFa lure$$State($state, $reason)"
  }
}
