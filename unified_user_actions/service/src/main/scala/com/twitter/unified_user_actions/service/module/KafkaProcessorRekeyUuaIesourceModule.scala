package com.tw ter.un f ed_user_act ons.serv ce.module

 mport com.google. nject.Prov des
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.S mpleRec p ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.producers.Block ngF nagleKafkaProducer
 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.f natra.kafka.serde.UnKeyedSerde
 mport com.tw ter. es ce.thr ftscala. nteract onEvent
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.kafka.cl ent. aders.Zone
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.un f ed_user_act ons.adapter.uua_aggregates.RekeyUuaFrom nteract onEventsAdapter
 mport com.tw ter.un f ed_user_act ons.kafka.Cl entConf gs
 mport com.tw ter.un f ed_user_act ons.kafka.Cl entProv ders
 mport com.tw ter.un f ed_user_act ons.kafka.Compress onTypeFlag
 mport com.tw ter.un f ed_user_act ons.thr ftscala.KeyedUuaT et
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.StorageUn 
 mport com.tw ter.ut l.logg ng.Logg ng
 mport org.apac .kafka.cl ents.consu r.Consu rRecord
 mport org.apac .kafka.cl ents.producer.ProducerRecord
 mport org.apac .kafka.common. ader. aders
 mport org.apac .kafka.common.record.Compress onType
 mport javax. nject.S ngleton
 mport javax. nject. nject

object KafkaProcessorRekeyUua es ceModule extends Tw terModule w h Logg ng {
  overr de def modules = Seq(FlagsModule)

  pr vate val adapter = new RekeyUuaFrom nteract onEventsAdapter
  // NOTE: T   s a shared processor na   n order to s mpl fy monv z stat computat on.
  pr vate f nal val processorNa  = "uuaProcessor"

  @Prov des
  @S ngleton
  @ nject
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
    @Flag(FlagsModule.rece veBuffer) rece veBuffer: StorageUn ,
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
  ): AtLeastOnceProcessor[UnKeyed,  nteract onEvent] = {
    prov deAtLeastOnceProcessor(
      na  = processorNa ,
      kafkaS ceCluster = kafkaS ceCluster,
      kafkaGroup d = kafkaGroup d,
      kafkaS ceTop c = kafkaS ceTop c,
      comm  nterval = comm  nterval,
      maxPollRecords = maxPollRecords,
      maxPoll nterval = maxPoll nterval,
      sess onT  out = sess onT  out,
      fetchMax = fetchMax,
      rece veBuffer = rece veBuffer,
      processorMaxPend ngRequests = kafkaMaxPend ngRequests,
      processorWorkerThreads = kafkaWorkerThreads,
      adapter = adapter,
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
      maybeProcess = ZoneF lter ng.noF lter ng
    )
  }

  def producer(
    producer: Block ngF nagleKafkaProducer[Long, KeyedUuaT et],
    k: Long,
    v: KeyedUuaT et,
    s nkTop c: Str ng,
     aders:  aders,
    statsRece ver: StatsRece ver,
    dec der: Dec der,
  ): Future[Un ] =
     f (dec der. sAva lable(feature = s"RekeyUUA es ce${v.act onType}", So (S mpleRec p ent(k))))
      //  f    re to enable xDC repl cator, t n   can safely remove t  Zone  ader s nce xDC
      // repl cator works  n t  follow ng way:
      //  -  f t   ssage does not have a  ader, t  repl cator w ll assu     s local and
      //    set t   ader, copy t   ssage
      //  -  f t   ssage has a  ader that  s t  local zone, t  repl cator w ll copy t   ssage
      //  -  f t   ssage has a  ader for a d fferent zone, t  repl cator w ll drop t   ssage
      producer
        .send(new ProducerRecord[Long, KeyedUuaT et](s nkTop c, null, k, v,  aders))
        .onSuccess { _ => statsRece ver.counter("publ shSuccess", s nkTop c). ncr() }
        .onFa lure { e: Throwable =>
          statsRece ver.counter("publ shFa lure", s nkTop c). ncr()
          error(s"Publ sh error to top c $s nkTop c: $e")
        }.un 
    else Future.Un 

  def prov deAtLeastOnceProcessor(
    na : Str ng,
    kafkaS ceCluster: Str ng,
    kafkaGroup d: Str ng,
    kafkaS ceTop c: Str ng,
    comm  nterval: Durat on = Cl entConf gs.kafkaComm  ntervalDefault,
    maxPollRecords:  nt = Cl entConf gs.consu rMaxPollRecordsDefault,
    maxPoll nterval: Durat on = Cl entConf gs.consu rMaxPoll ntervalDefault,
    sess onT  out: Durat on = Cl entConf gs.consu rSess onT  outDefault,
    fetchMax: StorageUn  = Cl entConf gs.consu rFetchMaxDefault,
    fetchM n: StorageUn  = Cl entConf gs.consu rFetchM nDefault,
    rece veBuffer: StorageUn  = Cl entConf gs.consu rRece veBufferS zeDefault,
    processorMaxPend ngRequests:  nt,
    processorWorkerThreads:  nt,
    adapter: AbstractAdapter[ nteract onEvent, Long, KeyedUuaT et],
    kafkaS nkTop cs: Seq[Str ng],
    kafkaDestCluster: Str ng,
    kafkaProducerCl ent d: Str ng,
    batchS ze: StorageUn  = Cl entConf gs.producerBatchS zeDefault,
    l nger: Durat on = Cl entConf gs.producerL ngerDefault,
    buffer m: StorageUn  = Cl entConf gs.producerBuffer mDefault,
    compress onType: Compress onType = Cl entConf gs.compress onDefault.compress onType,
    retr es:  nt = Cl entConf gs.retr esDefault,
    retryBackoff: Durat on = Cl entConf gs.retryBackoffDefault,
    requestT  out: Durat on = Cl entConf gs.producerRequestT  outDefault,
    produceOpt: Opt on[
      (Block ngF nagleKafkaProducer[Long, KeyedUuaT et], Long, KeyedUuaT et, Str ng,  aders,
        StatsRece ver, Dec der) => Future[Un ]
    ] = So (producer),
    trustStoreLocat onOpt: Opt on[Str ng] = So (Cl entConf gs.trustStoreLocat onDefault),
    statsRece ver: StatsRece ver,
    dec der: Dec der,
    zone: Zone,
    maybeProcess: (Consu rRecord[UnKeyed,  nteract onEvent], Zone) => Boolean,
  ): AtLeastOnceProcessor[UnKeyed,  nteract onEvent] = {

    lazy val s ngletonProducer = Cl entProv ders.mkProducer[Long, KeyedUuaT et](
      bootstrapServer = kafkaDestCluster,
      cl ent d = kafkaProducerCl ent d,
      keySerde = ScalaSerdes.Long.ser al zer,
      valueSerde = ScalaSerdes.Thr ft[KeyedUuaT et].ser al zer,
       dempotence = false,
      batchS ze = batchS ze,
      l nger = l nger,
      buffer m = buffer m,
      compress onType = compress onType,
      retr es = retr es,
      retryBackoff = retryBackoff,
      requestT  out = requestT  out,
      trustStoreLocat onOpt = trustStoreLocat onOpt,
    )

    KafkaProcessorProv der.mkAtLeastOnceProcessor[UnKeyed,  nteract onEvent, Long, KeyedUuaT et](
      na  = na ,
      kafkaS ceCluster = kafkaS ceCluster,
      kafkaGroup d = kafkaGroup d,
      kafkaS ceTop c = kafkaS ceTop c,
      s ceKeyDeser al zer = UnKeyedSerde.deser al zer,
      s ceValueDeser al zer = ScalaSerdes.CompactThr ft[ nteract onEvent].deser al zer,
      comm  nterval = comm  nterval,
      maxPollRecords = maxPollRecords,
      maxPoll nterval = maxPoll nterval,
      sess onT  out = sess onT  out,
      fetchMax = fetchMax,
      fetchM n = fetchM n,
      rece veBuffer = rece veBuffer,
      processorMaxPend ngRequests = processorMaxPend ngRequests,
      processorWorkerThreads = processorWorkerThreads,
      adapter = adapter,
      kafkaProducersAndS nkTop cs =
        kafkaS nkTop cs.map(s nkTop c => (s ngletonProducer, s nkTop c)),
      produce = produceOpt.getOrElse(producer),
      trustStoreLocat onOpt = trustStoreLocat onOpt,
      statsRece ver = statsRece ver,
      dec der = dec der,
      zone = zone,
      maybeProcess = maybeProcess,
    )
  }
}
