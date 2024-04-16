package com.tw ter.t  l neranker.common

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l nes.cl ents.g zmoduck.G zmoduckCl ent
 mport com.tw ter.t  l nes.cl ents.g zmoduck.UserProf le nfo
 mport com.tw ter.t  l nes.ut l.Fa lOpenHandler
 mport com.tw ter.ut l.Future

object UserProf le nfoTransform {
  val EmptyUserProf le nfo: UserProf le nfo = UserProf le nfo(None, None, None, None)
  val EmptyUserProf le nfoFuture: Future[UserProf le nfo] = Future.value(EmptyUserProf le nfo)
}

/**
 * FutureArrow wh ch fetc s user prof le  nfo
 *   should be run  n parallel w h t  ma n p pel ne wh ch fetc s and hydrates Cand dateT ets
 */
class UserProf le nfoTransform(handler: Fa lOpenHandler, g zmoduckCl ent: G zmoduckCl ent)
    extends FutureArrow[RecapQuery, UserProf le nfo] {
   mport UserProf le nfoTransform._
  overr de def apply(request: RecapQuery): Future[UserProf le nfo] = {
    handler {
      g zmoduckCl ent.getProf le nfo(request.user d).map { prof le nfoOpt =>
        prof le nfoOpt.getOrElse(EmptyUserProf le nfo)
      }
    } { _: Throwable => EmptyUserProf le nfoFuture }
  }
}
