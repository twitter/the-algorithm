package com.tw ter.t etyp e.core

 mport scala.ut l.control.NoStackTrace

/**
 * Parent except on class for fa lures wh le talk ng to upstream serv ces.  T se w ll
 * be counted and t n converted to servo.ServerError.DependencyError
 */
sealed abstract class UpstreamFa lure(msg: Str ng) extends Except on(msg) w h NoStackTrace

object UpstreamFa lure {
  case class SnowflakeFa lure(t: Throwable) extends UpstreamFa lure(t.toStr ng)

  case object UserProf leEmptyExcept on extends UpstreamFa lure("User.prof le  s empty")

  case object UserV ewEmptyExcept on extends UpstreamFa lure("User.v ew  s empty")

  case object UserSafetyEmptyExcept on extends UpstreamFa lure("User.safety  s empty")

  case class T etLookupFa lure(t: Throwable) extends UpstreamFa lure(t.toStr ng)

  case class UserLookupFa lure(t: Throwable) extends UpstreamFa lure(t.toStr ng)

  case class Dev ceS ceLookupFa lure(t: Throwable) extends UpstreamFa lure(t.toStr ng)

  case class TFlockLookupFa lure(t: Throwable) extends UpstreamFa lure(t.toStr ng)

  case class UrlShorten ngFa lure(t: Throwable) extends UpstreamFa lure(t.toStr ng)

  case object  d aShortenUrlMalfor dFa lure
      extends UpstreamFa lure(" d a shortened url  s malfor d")

  case object  d aExpandedUrlNotVal dFa lure
      extends UpstreamFa lure("Talon returns bad nput on  d a expanded url")

  case class  d aServ ceServerError(t: Throwable) extends UpstreamFa lure(t.toStr ng)
}
