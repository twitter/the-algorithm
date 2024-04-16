package com.tw ter.un f ed_user_act ons.serv ce.module

 mport com.google. nject.Prov des
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.producers.Block ngF nagleKafkaProducer
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.f natra.kafka.serde.UnKeyedSerde
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.kafka.cl ent. aders.Zone
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.un f ed_user_act ons.adapter.cl ent_event.Cl entEventAdapter
 mport com.tw ter.un f ed_user_act ons.kafka.Compress onTypeFlag
 mport com.tw ter.un f ed_user_act ons.kafka.serde.NullableScalaSerdes
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorProv der.updateAct onTypeCounters
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorProv der.updateProcess ngT  Stats
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorProv der.updateProductSurfaceTypeCounters
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.StorageUn 
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject.S ngleton
 mport org.apac .kafka.cl ents.producer.ProducerRecord
 mport org.apac .kafka.common. ader. aders

object KafkaProcessorCl entEventModule extends Tw terModule w h Logg ng {
  overr de def modules: Seq[FlagsModule.type] = Seq(FlagsModule)

  pr vate val cl entEventAdapter = new Cl entEventAdapter
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
    @Flag(FlagsModule.fetchM n) fetchM n: StorageUn ,
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
  ): AtLeastOnceProcessor[UnKeyed, LogEvent] = {
    KafkaProcessorProv der.prov deDefaultAtLeastOnceProcessor(
      na  = processorNa ,
      kafkaS ceCluster = kafkaS ceCluster,
      kafkaGroup d = kafkaGroup d,
      kafkaS ceTop c = kafkaS ceTop c,
      s ceKeyDeser al zer = UnKeyedSerde.deser al zer,
      s ceValueDeser al zer = NullableScalaSerdes
        .Thr ft[LogEvent](statsRece ver.counter("deser al zerErrors")).deser al zer,
      comm  nterval = comm  nterval,
      maxPollRecords = maxPollRecords,
      maxPoll nterval = maxPoll nterval,
      sess onT  out = sess onT  out,
      fetchMax = fetchMax,
      fetchM n = fetchM n,
      processorMaxPend ngRequests = kafkaMaxPend ngRequests,
      processorWorkerThreads = kafkaWorkerThreads,
      adapter = cl entEventAdapter,
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
      produceOpt = So (cl entEventProducer),
      trustStoreLocat onOpt =  f (enableTrustStore) So (trustStoreLocat on) else None,
      dec der = dec der,
      zone = ZoneF lter ng.zoneMapp ng(cluster),
    )
  }

  /**
   * Cl entEvent producer  s d fferent from t  defaultProducer.
   * Wh le t  defaultProducer publ s s every event to all s nk top cs, Cl entEventProducer (t  producer) requ res
   * exactly 2 s nk top cs: Top c w h all events ( mpress ons and engage nts) and Top c w h engage nts only.
   * And t  publ sh ng  s based t  act on type.
   */
  def cl entEventProducer(
    producer: Block ngF nagleKafkaProducer[UnKeyed, Un f edUserAct on],
    k: UnKeyed,
    v: Un f edUserAct on,
    s nkTop c: Str ng,
     aders:  aders,
    statsRece ver: StatsRece ver,
    dec der: Dec der
  ): Future[Un ] =
     f (Cl entEventDec derUt ls.shouldPubl sh(dec der = dec der, uua = v, s nkTop c = s nkTop c)) {
      updateAct onTypeCounters(statsRece ver, v, s nkTop c)
      updateProductSurfaceTypeCounters(statsRece ver, v, s nkTop c)
      updateProcess ngT  Stats(statsRece ver, v)

      //  f    re to enable xDC repl cator, t n   can safely remove t  Zone  ader s nce xDC
      // repl cator works  n t  follow ng way:
      //  -  f t   ssage does not have a  ader, t  repl cator w ll assu     s local and
      //    set t   ader, copy t   ssage
      //  -  f t   ssage has a  ader that  s t  local zone, t  repl cator w ll copy t   ssage
      //  -  f t   ssage has a  ader for a d fferent zone, t  repl cator w ll drop t   ssage
      producer
        .send(
          new ProducerRecord[UnKeyed, Un f edUserAct on](
            s nkTop c,
            null,
            k,
            v,
             aders.remove(Zone.Key)))
        .onSuccess { _ => statsRece ver.counter("publ shSuccess", s nkTop c). ncr() }
        .onFa lure { e: Throwable =>
          statsRece ver.counter("publ shFa lure", s nkTop c). ncr()
          error(s"Publ sh error to top c $s nkTop c: $e")
        }.un 
    } else Future.Un 
}
