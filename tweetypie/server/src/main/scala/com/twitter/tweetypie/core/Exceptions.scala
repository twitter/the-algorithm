package com.tw ter.t etyp e
package core

 mport scala.ut l.control.NoStackTrace

case class  nternalServerError( ssage: Str ng) extends Except on( ssage) w h NoStackTrace

case class OverCapac y( ssage: Str ng) extends Except on( ssage) w h NoStackTrace

case class RateL m ed( ssage: Str ng) extends Except on( ssage) w h NoStackTrace

case class T etHydrat onError( ssage: Str ng, cause: Opt on[Throwable] = None)
    extends Except on( ssage, cause.getOrElse(null))
    w h NoStackTrace
