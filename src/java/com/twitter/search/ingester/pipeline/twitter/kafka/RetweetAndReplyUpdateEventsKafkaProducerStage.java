package com.tw ter.search. ngester.p pel ne.tw ter.kafka;

 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search. ngester.model. ngesterThr ftVers onedEvents;

@Consu dTypes(Thr ftVers onedEvents.class)
publ c class Ret etAndReplyUpdateEventsKafkaProducerStage extends KafkaProducerStage
    < ngesterThr ftVers onedEvents> {
  publ c Ret etAndReplyUpdateEventsKafkaProducerStage(Str ng kafkaTop c, Str ng cl ent d,
                                            Str ng clusterPath) {
    super(kafkaTop c, cl ent d, clusterPath);
  }

  publ c Ret etAndReplyUpdateEventsKafkaProducerStage() {
    super();
  }

  @Overr de
  protected vo d  nnerRunF nalStageOfBranchV2( ngesterThr ftVers onedEvents events) {
    super.tryToSendEventsToKafka(events);
  }
}
