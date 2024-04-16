package com.tw ter.recos njector.edges

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Stats.track
 mport com.tw ter.ut l.Future

/**
 * T   s t  gener c  nterface that converts  ncom ng Events (ex. T etEvent, FavEvent, etc)
 *  nto Edge for a spec f c output graph.   appl es t  follow ng flow:
 *
 * event -> update event stats -> bu ld edges -> f lter edges
 *
 * Top-level stat st cs are prov ded for each step, such as latency and number of events
 */
tra  EventTo ssageBu lder[Event, E <: Edge] {
   mpl c  val statsRece ver: StatsRece ver

  pr vate lazy val processEventStats = statsRece ver.scope("process_event")
  pr vate lazy val numEventsStats = statsRece ver.counter("num_process_event")
  pr vate lazy val rejectEventStats = statsRece ver.counter("num_reject_event")
  pr vate lazy val bu ldEdgesStats = statsRece ver.scope("bu ld")
  pr vate lazy val numAllEdgesStats = bu ldEdgesStats.counter("num_all_edges")
  pr vate lazy val f lterEdgesStats = statsRece ver.scope("f lter")
  pr vate lazy val numVal dEdgesStats = statsRece ver.counter("num_val d_edges")
  pr vate lazy val numRecosHose ssageStats = statsRece ver.counter("num_RecosHose ssage")

  /**
   * G ven an  ncom ng event, process and convert    nto a sequence of RecosHose ssages
   * @param event
   * @return
   */
  def processEvent(event: Event): Future[Seq[Edge]] = {
    track(processEventStats) {
      shouldProcessEvent(event).flatMap {
        case true =>
          numEventsStats. ncr()
          updateEventStatus(event)
          for {
            allEdges <- track(bu ldEdgesStats)(bu ldEdges(event))
            f lteredEdges <- track(f lterEdgesStats)(f lterEdges(event, allEdges))
          } y eld {
            numAllEdgesStats. ncr(allEdges.s ze)
            numVal dEdgesStats. ncr(f lteredEdges.s ze)
            numRecosHose ssageStats. ncr(f lteredEdges.s ze)
            f lteredEdges
          }
        case false =>
          rejectEventStats. ncr()
          Future.N l
      }
    }
  }

  /**
   * Pre-process f lter that determ nes w t r t  g ven event should be used to bu ld edges.
   * @param event
   * @return
   */
  def shouldProcessEvent(event: Event): Future[Boolean]

  /**
   * Update cac /event logg ng related to t  spec f c event.
   * By default, no act on w ll be taken. Overr de w n necessary
   * @param event
   */
  def updateEventStatus(event: Event): Un  = {}

  /**
   * G ven an event, extract  nfo and bu ld a sequence of edges
   * @param event
   * @return
   */
  def bu ldEdges(event: Event): Future[Seq[E]]

  /**
   * G ven a sequence of edges, f lter and return t  val d edges
   * @param event
   * @param edges
   * @return
   */
  def f lterEdges(event: Event, edges: Seq[E]): Future[Seq[E]]
}
