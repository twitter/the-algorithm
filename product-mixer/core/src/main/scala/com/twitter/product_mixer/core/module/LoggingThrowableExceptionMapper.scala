package com.tw ter.product_m xer.core.module

 mport com.tw ter.f natra.thr ft.except ons.Except onMapper
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton
 mport scala.ut l.control.NonFatal

/**
 * S m lar to [[com.tw ter.f natra.thr ft. nternal.except ons.ThrowableExcept onMapper]]
 *
 * But t  one also logs t  except ons.
 */
@S ngleton
class Logg ngThrowableExcept onMapper extends Except onMapper[Throwable, Noth ng] w h Logg ng {

  overr de def handleExcept on(throwable: Throwable): Future[Noth ng] = {
    error("Unhandled Except on", throwable)

    throwable match {
      case NonFatal(e) => Future.except on(e)
    }
  }
}
