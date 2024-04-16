package com.tw ter.v s b l y.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng._

object Logg ngUt l {

  val Exper  ntat onLog: Str ng = "vf_abdec der"

  def mkDefaultHandlerFactory(statsRece ver: StatsRece ver): () => Handler = {
    Queue ngHandler(
      maxQueueS ze = 10000,
      handler = Scr beHandler(
        category = "cl ent_event",
        formatter = BareFormatter,
        statsRece ver = statsRece ver.scope("cl ent_event_scr be"),
        level = So (Level. NFO)
      )
    )
  }

  def mkDefaultLoggerFactory(statsRece ver: StatsRece ver): LoggerFactory = {
    LoggerFactory(
      node = Exper  ntat onLog,
      level = So (Level. NFO),
      useParents = false,
      handlers = L st(mkDefaultHandlerFactory(statsRece ver))
    )
  }

  def mkDefaultLogger(statsRece ver: StatsRece ver): Logger = {
    mkDefaultLoggerFactory(statsRece ver)()
  }

}
