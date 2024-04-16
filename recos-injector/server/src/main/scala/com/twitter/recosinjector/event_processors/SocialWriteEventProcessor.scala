package com.tw ter.recos njector.event_processors

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos njector.edges.{EventTo ssageBu lder, UserUserEdge}
 mport com.tw ter.recos njector.publ s rs.KafkaEventPubl s r
 mport com.tw ter.scrooge.Thr ftStructCodec
 mport com.tw ter.soc algraph.thr ftscala.Wr eEvent
 mport com.tw ter.ut l.Future

/**
 * T  processor l stens to events from soc al graphs serv ces.  n part cular, a major use case  s
 * to l sten to user-user follow events.
 */
class Soc alWr eEventProcessor(
  overr de val eventBusStreamNa : Str ng,
  overr de val thr ftStruct: Thr ftStructCodec[Wr eEvent],
  overr de val serv ce dent f er: Serv ce dent f er,
  kafkaEventPubl s r: KafkaEventPubl s r,
  userUserGraphTop c: Str ng,
  userUserGraph ssageBu lder: EventTo ssageBu lder[Wr eEvent, UserUserEdge]
)(
  overr de  mpl c  val statsRece ver: StatsRece ver)
    extends EventBusProcessor[Wr eEvent] {

  overr de def processEvent(event: Wr eEvent): Future[Un ] = {
    userUserGraph ssageBu lder.processEvent(event).map { edges =>
      edges.foreach { edge =>
        kafkaEventPubl s r.publ sh(edge.convertToRecosHose ssage, userUserGraphTop c)
      }
    }
  }
}
