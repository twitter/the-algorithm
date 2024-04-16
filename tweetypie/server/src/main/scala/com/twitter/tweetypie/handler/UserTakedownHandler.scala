package com.tw ter.t etyp e
package handler

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t etyp e.store.Takedown
 mport com.tw ter.t etyp e.thr ftscala.DataError
 mport com.tw ter.t etyp e.thr ftscala.DataErrorCause
 mport com.tw ter.t etyp e.thr ftscala.SetT etUserTakedownRequest

tra  UserTakedownHandler {
  val setT etUserTakedownRequest: FutureArrow[SetT etUserTakedownRequest, Un ]
}

/**
 * T  handler processes SetT etUserTakedownRequest objects sent to T etyp e's
 * setT etUserTakedown endpo nt.  T se requests or g nate from t etyp e daemon and t 
 * request object spec f es t  user  D of t  user who  s be ng mod f ed, and a boolean value
 * to  nd cate w t r takedown  s be ng added or removed.
 *
 *  f takedown  s be ng added, t  hasTakedown b   s set on all of t  user's t ets.
 *  f takedown  s be ng removed,   can't automat cally unset t  hasTakedown b  on all t ets
 * s nce so  of t  t ets m ght have t et-spec f c takedowns,  n wh ch case t  hasTakedown b 
 * needs to rema n set.   nstead,   flush t  user's t ets from cac , and let t  repa rer
 * unset t  b  w n hydrat ng t ets w re t  b   s set but no user or t et
 * takedown country codes are present.
 */
object UserTakedownHandler {
  type Type = FutureArrow[SetT etUserTakedownRequest, Un ]

  def takedownEvent(userHasTakedown: Boolean): T et => Opt on[Takedown.Event] =
    t et => {
      val t etHasTakedown =
        T etLenses.t etyp eOnlyTakedownCountryCodes(t et).ex sts(_.nonEmpty) ||
          T etLenses.t etyp eOnlyTakedownReasons(t et).ex sts(_.nonEmpty)
      val updatedHasTakedown = userHasTakedown || t etHasTakedown
       f (updatedHasTakedown == T etLenses.hasTakedown(t et))
        None
      else
        So (
          Takedown.Event(
            t et = T etLenses.hasTakedown.set(t et, updatedHasTakedown),
            t  stamp = T  .now,
            eventbusEnqueue = false,
            scr beForAud  = false,
            updateCodesAndReasons = false
          )
        )
    }

  def setHasTakedown(
    t etTakedown: FutureEffect[Takedown.Event],
    userHasTakedown: Boolean
  ): FutureEffect[Seq[T et]] =
    t etTakedown.contramapOpt on(takedownEvent(userHasTakedown)).l ftSeq

  def ver fyT etUser d(expectedUser d: Opt on[User d], t et: T et): Un  = {
    val t etUser d: User d = getUser d(t et)
    val t et d: Long = t et. d
    expectedUser d.f lter(_ != t etUser d).foreach { u =>
      throw DataError(
         ssage =
          s"SetT etUserTakedownRequest user d $u does not match user d $t etUser d for T et: $t et d",
        errorCause = So (DataErrorCause.UserT etRelat onsh p),
      )
    }
  }

  def apply(
    getT et: FutureArrow[T et d, Opt on[T et]],
    t etTakedown: FutureEffect[Takedown.Event],
  ): Type =
    FutureArrow { request =>
      for {
        t et <- getT et(request.t et d)
        _ = t et.foreach(t => ver fyT etUser d(request.user d, t))
        _ <- setHasTakedown(t etTakedown, request.hasTakedown)(t et.toSeq)
      } y eld ()
    }
}
