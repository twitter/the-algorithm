package com.tw ter.t etyp e.serverut l

 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.ut l.Act v y
 mport com.tw ter.ut l.Future

/**
 * Transforms an `Act v y` that conta ns a `Serv ce`  nto a `Serv ce`.
 * T   mple ntat on guarantees that t  serv ce  s rebu lt only w n t 
 * act v y changes, not on every request.
 */
object Act v yServ ce {

  def apply[Req, Rep](act v y: Act v y[Serv ce[Req, Rep]]): Serv ce[Req, Rep] = {

    val serv ceEvent =
      Act v yUt l.str ct(act v y).values.map(_.get)

    new Serv ce[Req, Rep] {

      def apply(req: Req): Future[Rep] =
        serv ceEvent.toFuture.flatMap(_.apply(req))
    }
  }
}
