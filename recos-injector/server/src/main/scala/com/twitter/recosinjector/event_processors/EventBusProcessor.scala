package com.tw ter.recos njector.event_processors

 mport com.tw ter.eventbus.cl ent.{EventBusSubscr ber, EventBusSubscr berBu lder}
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.scrooge.{Thr ftStruct, Thr ftStructCodec}
 mport com.tw ter.ut l.Future

/**
 * Ma n processor class that handles  ncom ng EventBus events, wh ch take forms of a Thr ftStruct.
 * T  class  s respons ble for sett ng up t  EventBus streams, and prov des a processEvent()
 * w re ch ld classes can dec de what to do w h  ncom ng events
 */
tra  EventBusProcessor[Event <: Thr ftStruct] {
  pr vate val log = Logger()

   mpl c  def statsRece ver: StatsRece ver

  /**
   * Full na  of t  EventBus stream t  processor l stens to
   */
  val eventBusStreamNa : Str ng

  /**
   * t  thr ftStruct def n  on of t  objects passed  n from t  EventBus streams, such as
   * T etEvent, Wr eEvent, etc.
   */
  val thr ftStruct: Thr ftStructCodec[Event]

  val serv ce dent f er: Serv ce dent f er

  def processEvent(event: Event): Future[Un ]

  pr vate def getEventBusSubscr berBu lder: EventBusSubscr berBu lder[Event] =
    EventBusSubscr berBu lder()
      .subscr ber d(eventBusStreamNa )
      .serv ce dent f er(serv ce dent f er)
      .thr ftStruct(thr ftStruct)
      .numThreads(8)
      .fromAllZones(true) // Rece ves traff c from all data centers
      .sk pToLatest(false) // Ensures   don't m ss out on events dur ng restart
      .statsRece ver(statsRece ver)

  // lazy val ensures t  subscr ber  s only  n  al zed w n start()  s called
  pr vate lazy val eventBusSubscr ber = getEventBusSubscr berBu lder.bu ld(processEvent)

  def start(): EventBusSubscr ber[Event] = eventBusSubscr ber

  def stop(): Un  = {
    eventBusSubscr ber
      .close()
      .onSuccess { _ =>
        log. nfo(s"EventBus processor ${t .getClass.getS mpleNa }  s stopped")
      }
      .onFa lure { ex: Throwable =>
        log.error(ex, s"Except on wh le stopp ng EventBus processor ${t .getClass.getS mpleNa }")
      }
  }
}
