package com.tw ter.t etyp e
package serv ce

 mport com.tw ter.f nagle. nd v dualRequestT  outExcept on
 mport com.tw ter.servo.except on.thr ftscala._
 mport com.tw ter.t etyp e.core.OverCapac y
 mport com.tw ter.t etyp e.core.RateL m ed
 mport com.tw ter.t etyp e.core.T etHydrat onError
 mport com.tw ter.t etyp e.core.UpstreamFa lure
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.ut l.T  outExcept on

object RescueExcept ons {
  pr vate val log = Logger("com.tw ter.t etyp e.serv ce.T etServ ce")

  /**
   * rescue to servo except ons
   */
  def rescueToServoFa lure(
    na : Str ng,
    cl ent d: Str ng
  ): Part alFunct on[Throwable, Future[Noth ng]] = {
    translateToServoFa lure(formatError(na , cl ent d, _)).andT n(Future.except on)
  }

  pr vate def translateToServoFa lure(
    toMsg: Str ng => Str ng
  ): Part alFunct on[Throwable, Throwable] = {
    case e: AccessDen ed  f suspendedOrDeact vated(e) =>
      e.copy( ssage = toMsg(e. ssage))
    case e: Cl entError =>
      e.copy( ssage = toMsg(e. ssage))
    case e: Unauthor zedExcept on =>
      Cl entError(Cl entErrorCause.Unauthor zed, toMsg(e.msg))
    case e: AccessDen ed =>
      Cl entError(Cl entErrorCause.Unauthor zed, toMsg(e. ssage))
    case e: RateL m ed =>
      Cl entError(Cl entErrorCause.RateL m ed, toMsg(e. ssage))
    case e: ServerError =>
      e.copy( ssage = toMsg(e. ssage))
    case e: T  outExcept on =>
      ServerError(ServerErrorCause.RequestT  out, toMsg(e.toStr ng))
    case e:  nd v dualRequestT  outExcept on =>
      ServerError(ServerErrorCause.RequestT  out, toMsg(e.toStr ng))
    case e: UpstreamFa lure =>
      ServerError(ServerErrorCause.DependencyError, toMsg(e.toStr ng))
    case e: OverCapac y =>
      ServerError(ServerErrorCause.Serv ceUnava lable, toMsg(e. ssage))
    case e: T etHydrat onError =>
      ServerError(ServerErrorCause.DependencyError, toMsg(e.toStr ng))
    case e =>
      log.warn("caught unexpected except on", e)
      ServerError(ServerErrorCause. nternalServerError, toMsg(e.toStr ng))
  }

  pr vate def suspendedOrDeact vated(e: AccessDen ed): Boolean =
    e.errorCause.ex sts { c =>
      c == AccessDen edCause.UserDeact vated || c == AccessDen edCause.UserSuspended
    }

  pr vate def formatError(na : Str ng, cl ent d: Str ng, msg: Str ng): Str ng =
    s"($cl ent d, $na ) $msg"
}
