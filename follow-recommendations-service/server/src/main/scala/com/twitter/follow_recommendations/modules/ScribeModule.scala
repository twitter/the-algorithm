package com.tw ter.follow_recom ndat ons.modules

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.google. nject.na .Na d
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.logg ng.BareFormatter
 mport com.tw ter.logg ng.HandlerFactory
 mport com.tw ter.logg ng.Level
 mport com.tw ter.logg ng.LoggerFactory
 mport com.tw ter.logg ng.NullHandler
 mport com.tw ter.logg ng.Queue ngHandler
 mport com.tw ter.logg ng.Scr beHandler

object Scr beModule extends Tw terModule {
  val useProdLogger = flag(
    na  = "scr be.use_prod_loggers",
    default = false,
     lp = "w t r to use product on logg ng for serv ce"
  )

  @Prov des
  @S ngleton
  @Na d(Gu ceNa dConstants.CL ENT_EVENT_LOGGER)
  def prov deCl entEventsLoggerFactory(stats: StatsRece ver): LoggerFactory = {
    val loggerCategory = "cl ent_event"
    val cl entEventsHandler: HandlerFactory =  f (useProdLogger()) {
      Queue ngHandler(
        maxQueueS ze = 10000,
        handler = Scr beHandler(
          category = loggerCategory,
          formatter = BareFormatter,
          level = So (Level. NFO),
          statsRece ver = stats.scope("cl ent_event_scr be")
        )
      )
    } else { () => NullHandler }
    LoggerFactory(
      node = "abdec der",
      level = So (Level. NFO),
      useParents = false,
      handlers = cl entEventsHandler :: N l
    )
  }

  @Prov des
  @S ngleton
  @Na d(Gu ceNa dConstants.REQUEST_LOGGER)
  def prov deFollowRecom ndat onsLoggerFactory(stats: StatsRece ver): LoggerFactory = {
    val loggerCategory = "follow_recom ndat ons_logs"
    val handlerFactory: HandlerFactory =  f (useProdLogger()) {
      Queue ngHandler(
        maxQueueS ze = 10000,
        handler = Scr beHandler(
          category = loggerCategory,
          formatter = BareFormatter,
          level = So (Level. NFO),
          statsRece ver = stats.scope("follow_recom ndat ons_logs_scr be")
        )
      )
    } else { () => NullHandler }
    LoggerFactory(
      node = loggerCategory,
      level = So (Level. NFO),
      useParents = false,
      handlers = handlerFactory :: N l
    )
  }

  @Prov des
  @S ngleton
  @Na d(Gu ceNa dConstants.FLOW_LOGGER)
  def prov deFrsRecom ndat onFlowLoggerFactory(stats: StatsRece ver): LoggerFactory = {
    val loggerCategory = "frs_recom ndat on_flow_logs"
    val handlerFactory: HandlerFactory =  f (useProdLogger()) {
      Queue ngHandler(
        maxQueueS ze = 10000,
        handler = Scr beHandler(
          category = loggerCategory,
          formatter = BareFormatter,
          level = So (Level. NFO),
          statsRece ver = stats.scope("frs_recom ndat on_flow_logs_scr be")
        )
      )
    } else { () => NullHandler }
    LoggerFactory(
      node = loggerCategory,
      level = So (Level. NFO),
      useParents = false,
      handlers = handlerFactory :: N l
    )
  }
}
