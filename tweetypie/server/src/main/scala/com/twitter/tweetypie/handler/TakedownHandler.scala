package com.tw ter.t etyp e
package handler

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.takedown.ut l.TakedownReasons._
 mport com.tw ter.t etyp e.store.Takedown
 mport com.tw ter.t etyp e.thr ftscala.TakedownRequest
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.t etyp e.ut l.Takedowns

/**
 * T  handler processes TakedownRequest objects sent to T etyp e's takedown endpo nt.
 * T  request object spec f es wh ch takedown countr es are be ng added and wh ch are
 * be ng removed.    also  ncludes s de effect flags for sett ng t  t et's has_takedown
 * b , scr b ng to Guano, and enqueu ng to EventBus.  For more  nformat on about  nputs
 * to t  takedown endpo nt, see t  TakedownRequest docu ntat on  n t  thr ft def n  on.
 */
object TakedownHandler {
  type Type = FutureArrow[TakedownRequest, Un ]

  def apply(
    getT et: FutureArrow[T et d, T et],
    getUser: FutureArrow[User d, User],
    wr eTakedown: FutureEffect[Takedown.Event]
  ): Type = {
    FutureArrow { request =>
      for {
        t et <- getT et(request.t et d)
        user <- getUser(getUser d(t et))
        userHasTakedowns = user.takedowns.map(userTakedownsToReasons).ex sts(_.nonEmpty)

        ex st ngT etReasons = Takedowns.fromT et(t et).reasons

        reasonsToRemove = (request.countr esToRemove.map(countryCodeToReason) ++
            request.reasonsToRemove.map(normal zeReason)).d st nct.sortBy(_.toStr ng)

        reasonsToAdd = (request.countr esToAdd.map(countryCodeToReason) ++
            request.reasonsToAdd.map(normal zeReason)).d st nct.sortBy(_.toStr ng)

        updatedT etTakedowns =
          (ex st ngT etReasons ++ reasonsToAdd)
            .f lterNot(reasonsToRemove.conta ns)
            .toSeq
            .sortBy(_.toStr ng)

        (cs, rs) = Takedowns.part  onReasons(updatedT etTakedowns)

        updatedT et = Lens.setAll(
          t et,
          // t se f elds are cac d on t  T et  n Cach ngT etStore and wr ten  n
          // ManhattanT etStore
          T etLenses.hasTakedown -> (updatedT etTakedowns.nonEmpty || userHasTakedowns),
          T etLenses.t etyp eOnlyTakedownCountryCodes -> So (cs).f lter(_.nonEmpty),
          T etLenses.t etyp eOnlyTakedownReasons -> So (rs).f lter(_.nonEmpty)
        )

        _ <- wr eTakedown.w n(t et != updatedT et) {
          Takedown.Event(
            t et = updatedT et,
            t  stamp = T  .now,
            user = So (user),
            takedownReasons = updatedT etTakedowns,
            reasonsToAdd = reasonsToAdd,
            reasonsToRemove = reasonsToRemove,
            aud Note = request.aud Note,
            host = request.host,
            byUser d = request.byUser d,
            eventbusEnqueue = request.eventbusEnqueue,
            scr beForAud  = request.scr beForAud ,
            updateCodesAndReasons = true
          )
        }
      } y eld ()
    }
  }
}
