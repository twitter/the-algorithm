package com.tw ter.un f ed_user_act ons.serv ce.module

 mport com.google. nject.Prov des
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.f natra.kafka.serde.UnKeyedSerde
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.soc algraph.thr ftscala.Wr eEvent
 mport com.tw ter.un f ed_user_act ons.adapter.soc al_graph_event.Soc alGraphAdapter
 mport com.tw ter.un f ed_user_act ons.kafka.Compress onTypeFlag
 mport com.tw ter.un f ed_user_act ons.kafka.serde.NullableScalaSerdes
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.StorageUn 
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject.S ngleton

class KafkaProcessorSoc alGraphModule {}

object KafkaProcessorSoc alGraphModule extends Tw terModule w h Logg ng {
  overr de def modules = Seq(FlagsModule)

  pr vate val soc alGraphAdapter = new Soc alGraphAdapter
  // NOTE: T   s a shared processor na   n order to s mpl fy monv z stat computat on.
  pr vate f nal val processorNa  = "uuaProcessor"

  @Prov des
  @S ngleton
  def prov desKafkaProcessor(
    dec der: Dec der,
    @Flag(FlagsModule.cluster) cluster: Str ng,
    @Flag(FlagsModule.kafkaS ceCluster) kafkaS ceCluster: Str ng,
    @Flag(FlagsModule.kafkaDestCluster) kafkaDestCluster: Str ng,
    @Flag(FlagsModule.kafkaS ceTop c) kafkaS ceTop c: Str ng,
    @Flag(FlagsModule.kafkaS nkTop cs) kafkaS nkTop cs: Seq[Str ng],
    @Flag(FlagsModule.kafkaGroup d) kafkaGroup d: Str ng,
    @Flag(FlagsModule.kafkaProducerCl ent d) kafkaProducerCl ent d: Str ng,
    @Flag(FlagsModule.kafkaMaxPend ngRequests) kafkaMaxPend ngRequests:  nt,
    @Flag(FlagsModule.kafkaWorkerThreads) kafkaWorkerThreads:  nt,
    @Flag(FlagsModule.comm  nterval) comm  nterval: Durat on,
    @Flag(FlagsModule.maxPollRecords) maxPollRecords:  nt,
    @Flag(FlagsModule.maxPoll nterval) maxPoll nterval: Durat on,
    @Flag(FlagsModule.sess onT  out) sess onT  out: Durat on,
    @Flag(FlagsModule.fetchMax) fetchMax: StorageUn ,
    @Flag(FlagsModule.batchS ze) batchS ze: StorageUn ,
    @Flag(FlagsModule.l nger) l nger: Durat on,
    @Flag(FlagsModule.buffer m) buffer m: StorageUn ,
    @Flag(FlagsModule.compress onType) compress onTypeFlag: Compress onTypeFlag,
    @Flag(FlagsModule.retr es) retr es:  nt,
    @Flag(FlagsModule.retryBackoff) retryBackoff: Durat on,
    @Flag(FlagsModule.requestT  out) requestT  out: Durat on,
    @Flag(FlagsModule.enableTrustStore) enableTrustStore: Boolean,
    @Flag(FlagsModule.trustStoreLocat on) trustStoreLocat on: Str ng,
    statsRece ver: StatsRece ver,
  ): AtLeastOnceProcessor[UnKeyed, Wr eEvent] = {
    KafkaProcessorProv der.prov deDefaultAtLeastOnceProcessor(
      na  = processorNa ,
      kafkaS ceCluster = kafkaS ceCluster,
      kafkaGroup d = kafkaGroup d,
      kafkaS ceTop c = kafkaS ceTop c,
      s ceKeyDeser al zer = UnKeyedSerde.deser al zer,
      s ceValueDeser al zer = NullableScalaSerdes
        .Thr ft[Wr eEvent](statsRece ver.counter("deser al zerErrors")).deser al zer,
      comm  nterval = comm  nterval,
      maxPollRecords = maxPollRecords,
      maxPoll nterval = maxPoll nterval,
      sess onT  out = sess onT  out,
      fetchMax = fetchMax,
      processorMaxPend ngRequests = kafkaMaxPend ngRequests,
      processorWorkerThreads = kafkaWorkerThreads,
      adapter = soc alGraphAdapter,
      kafkaS nkTop cs = kafkaS nkTop cs,
      kafkaDestCluster = kafkaDestCluster,
      kafkaProducerCl ent d = kafkaProducerCl ent d,
      batchS ze = batchS ze,
      l nger = l nger,
      buffer m = buffer m,
      compress onType = compress onTypeFlag.compress onType,
      retr es = retr es,
      retryBackoff = retryBackoff,
      requestT  out = requestT  out,
      statsRece ver = statsRece ver,
      trustStoreLocat onOpt =  f (enableTrustStore) So (trustStoreLocat on) else None,
      dec der = dec der,
      zone = ZoneF lter ng.zoneMapp ng(cluster),
    )
  }
}
