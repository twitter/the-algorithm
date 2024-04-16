package com.tw ter.fr gate.pushserv ce.controller

 mport com.google. nject. nject
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f natra.thr ft.Controller
 mport com.tw ter.fr gate.pushserv ce.except on.D splayLocat onNotSupportedExcept on
 mport com.tw ter.fr gate.pushserv ce.refresh_handler.RefreshForPushHandler
 mport com.tw ter.fr gate.pushserv ce.send_handler.SendHandler
 mport com.tw ter.fr gate.pushserv ce.refresh_handler.LoggedOutRefreshForPushHandler
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushServ ce.Loggedout
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushServ ce.Refresh
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushServ ce.Send
 mport com.tw ter.fr gate.pushserv ce.{thr ftscala => t}
 mport com.tw ter.fr gate.thr ftscala.Not f cat onD splayLocat on
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.ut l.Future

class PushServ ceController @ nject() (
  sendHandler: SendHandler,
  refreshForPushHandler: RefreshForPushHandler,
  loggedOutRefreshForPushHandler: LoggedOutRefreshForPushHandler,
  statsRece ver: StatsRece ver)
    extends Controller(t.PushServ ce)
    w h Logg ng {

  pr vate val stats: StatsRece ver = statsRece ver.scope(s"${t .getClass.getS mpleNa }")
  pr vate val fa lureCount = stats.counter("fa lures")
  pr vate val fa lureStatsScope = stats.scope("fa lures")
  pr vate val uncaughtErrorCount = fa lureStatsScope.counter("uncaught")
  pr vate val uncaughtErrorScope = fa lureStatsScope.scope("uncaught")
  pr vate val cl ent dScope = stats.scope("cl ent_ d")

  handle(t.PushServ ce.Send) { request: Send.Args =>
    send(request)
  }

  handle(t.PushServ ce.Refresh) { args: Refresh.Args =>
    refresh(args)
  }

  handle(t.PushServ ce.Loggedout) { request: Loggedout.Args =>
    loggedOutRefresh(request)
  }

  pr vate def loggedOutRefresh(
    request: t.PushServ ce.Loggedout.Args
  ): Future[t.PushServ ce.Loggedout.SuccessType] = {
    val fut = request.request.not f cat onD splayLocat on match {
      case Not f cat onD splayLocat on.PushToMob leDev ce =>
        loggedOutRefreshForPushHandler.refreshAndSend(request.request)
      case _ =>
        Future.except on(
          new D splayLocat onNotSupportedExcept on(
            "Spec f ed not f cat on d splay locat on  s not supported"))
    }
    fut.onFa lure { ex =>
      logger.error(
        s"Fa lure  n push serv ce for logged out refresh request: $request - ${ex.get ssage} - ${ex.getStackTrace
          .mkStr ng(", \n\t")}",
        ex)
      fa lureCount. ncr()
      uncaughtErrorCount. ncr()
      uncaughtErrorScope.counter(ex.getClass.getCanon calNa ). ncr()
    }
  }

  pr vate def refresh(
    request: t.PushServ ce.Refresh.Args
  ): Future[t.PushServ ce.Refresh.SuccessType] = {

    val fut = request.request.not f cat onD splayLocat on match {
      case Not f cat onD splayLocat on.PushToMob leDev ce =>
        val cl ent d: Str ng =
          Cl ent d.current
            .flatMap { c d => Opt on(c d.na ) }
            .getOrElse("none")
        cl ent dScope.counter(cl ent d). ncr()
        refreshForPushHandler.refreshAndSend(request.request)
      case _ =>
        Future.except on(
          new D splayLocat onNotSupportedExcept on(
            "Spec f ed not f cat on d splay locat on  s not supported"))
    }
    fut.onFa lure { ex =>
      logger.error(
        s"Fa lure  n push serv ce for refresh request: $request - ${ex.get ssage} - ${ex.getStackTrace
          .mkStr ng(", \n\t")}",
        ex
      )

      fa lureCount. ncr()
      uncaughtErrorCount. ncr()
      uncaughtErrorScope.counter(ex.getClass.getCanon calNa ). ncr()
    }

  }

  pr vate def send(
    request: t.PushServ ce.Send.Args
  ): Future[t.PushServ ce.Send.SuccessType] = {
    sendHandler(request.request).onFa lure { ex =>
      logger.error(
        s"Fa lure  n push serv ce for send request: $request - ${ex.get ssage} - ${ex.getStackTrace
          .mkStr ng(", \n\t")}",
        ex
      )

      fa lureCount. ncr()
      uncaughtErrorCount. ncr()
      uncaughtErrorScope.counter(ex.getClass.getCanon calNa ). ncr()
    }
  }
}
