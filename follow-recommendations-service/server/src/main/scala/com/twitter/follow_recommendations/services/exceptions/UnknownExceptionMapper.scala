package com.tw ter.follow_recom ndat ons.serv ce.except ons

 mport com.tw ter.f natra.thr ft.except ons.Except onMapper
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

@S ngleton
class UnknownLogg ngExcept onMapper extends Except onMapper[Except on, Throwable] w h Logg ng {
  def handleExcept on(throwable: Except on): Future[Throwable] = {
    error(
      s"Unmapped Except on: ${throwable.get ssage} - ${throwable.getStackTrace.mkStr ng(", \n\t")}",
      throwable
    )

    Future.except on(throwable)
  }
}
