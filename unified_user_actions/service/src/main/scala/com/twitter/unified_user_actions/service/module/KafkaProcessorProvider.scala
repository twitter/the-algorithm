package com.tw ter.un f ed_user_act ons.serv ce.module

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.producers.Block ngF nagleKafkaProducer
 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.f natra.kafka.serde.UnKeyedSerde
 mport com.tw ter.kafka.cl ent. aders. mpl c s._
 mport com.tw ter.kafka.cl ent. aders.Zone
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.un f ed_user_act ons.kafka.Cl entConf gs
 mport com.tw ter.un f ed_user_act ons.kafka.Cl entProv ders
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.StorageUn 
 mport com.tw ter.ut l.logg ng.Logg ng
 mport org.apac .kafka.cl ents.consu r.Consu rRecord
 mport org.apac .kafka.cl ents.producer.ProducerRecord
 mport org.apac .kafka.common. ader. aders
 mport org.apac .kafka.common.record.Compress onType
 mport org.apac .kafka.common.ser al zat on.Deser al zer

object KafkaProcessorProv der extends Logg ng {
  lazy val act onTypeStatsCounterMap: collect on.mutable.Map[Str ng, Counter] =
    collect on.mutable.Map.empty
  lazy val productSurfaceTypeStatsCounterMap: collect on.mutable.Map[Str ng, Counter] =
    collect on.mutable.Map.empty

  def updateAct onTypeCounters(
    statsRece ver: StatsRece ver,
    v: Un f edUserAct on,
    top c: Str ng
  ): Un  = {
    val act onType = v.act onType.na 
    val act onTypeAndTop cKey = s"$act onType-$top c"
    act onTypeStatsCounterMap.get(act onTypeAndTop cKey) match {
      case So (act onCounter) => act onCounter. ncr()
      case _ =>
        act onTypeStatsCounterMap(act onTypeAndTop cKey) =
          statsRece ver.counter("uuaAct onType", top c, act onType)
        act onTypeStatsCounterMap(act onTypeAndTop cKey). ncr()
    }
  }

  def updateProductSurfaceTypeCounters(
    statsRece ver: StatsRece ver,
    v: Un f edUserAct on,
    top c: Str ng
  ): Un  = {
    val productSurfaceType = v.productSurface.map(_.na ).getOrElse("null")
    val productSurfaceTypeAndTop cKey = s"$productSurfaceType-$top c"
    productSurfaceTypeStatsCounterMap.get(productSurfaceTypeAndTop cKey) match {
      case So (productSurfaceCounter) => productSurfaceCounter. ncr()
      case _ =>
        productSurfaceTypeStatsCounterMap(productSurfaceTypeAndTop cKey) =
          statsRece ver.counter("uuaProductSurfaceType", top c, productSurfaceType)
        productSurfaceTypeStatsCounterMap(productSurfaceTypeAndTop cKey). ncr()
    }
  }

  def updateProcess ngT  Stats(statsRece ver: StatsRece ver, v: Un f edUserAct on): Un  = {
    statsRece ver
      .stat("uuaProcess ngT  D ff").add(
        v.event tadata.rece vedT  stampMs - v.event tadata.s ceT  stampMs)
  }

  def defaultProducer(
    producer: Block ngF nagleKafkaProducer[UnKeyed, Un f edUserAct on],
    k: UnKeyed,
    v: Un f edUserAct on,
    s nkTop c: Str ng,
     aders:  aders,
    statsRece ver: StatsRece ver,
    dec der: Dec der,
  ): Future[Un ] =
     f (DefaultDec derUt ls.shouldPubl sh(dec der = dec der, uua = v, s nkTop c = s nkTop c)) {
      updateAct onTypeCounters(statsRece ver, v, s nkTop c)
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

  /**
   * T  default AtLeastOnceProcessor ma nly for consum ng from a s ngle Kafka top c -> process/adapt -> publ sh to
   * t  s ngle s nk Kafka top c.
   *
   *  mportant Note: Currently all s nk top cs share t  sa  Kafka producer!!!  f   need to create d fferent
   * producers for d fferent top cs,   would need to create a custom zed funct on l ke t  one.
   */
  def prov deDefaultAtLeastOnceProcessor[K, V](
    na : Str ng,
    kafkaS ceCluster: Str ng,
    kafkaGroup d: Str ng,
    kafkaS ceTop c: Str ng,
    s ceKeyDeser al zer: Deser al zer[K],
    s ceValueDeser al zer: Deser al zer[V],
    comm  nterval: Durat on = Cl entConf gs.kafkaComm  ntervalDefault,
    maxPollRecords:  nt = Cl entConf gs.consu rMaxPollRecordsDefault,
    maxPoll nterval: Durat on = Cl entConf gs.consu rMaxPoll ntervalDefault,
    sess onT  out: Durat on = Cl entConf gs.consu rSess onT  outDefault,
    fetchMax: StorageUn  = Cl entConf gs.consu rFetchMaxDefault,
    fetchM n: StorageUn  = Cl entConf gs.consu rFetchM nDefault,
    rece veBuffer: StorageUn  = Cl entConf gs.consu rRece veBufferS zeDefault,
    processorMaxPend ngRequests:  nt,
    processorWorkerThreads:  nt,
    adapter: AbstractAdapter[V, UnKeyed, Un f edUserAct on],
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
      (Block ngF nagleKafkaProducer[UnKeyed, Un f edUserAct on], UnKeyed, Un f edUserAct on, Str ng,
         aders, StatsRece ver, Dec der) => Future[Un ]
    ] = None,
    trustStoreLocat onOpt: Opt on[Str ng] = So (Cl entConf gs.trustStoreLocat onDefault),
    statsRece ver: StatsRece ver,
    dec der: Dec der,
    zone: Zone,
    maybeProcess: (Consu rRecord[K, V], Zone) => Boolean = ZoneF lter ng.localDCF lter ng[K, V] _,
  ): AtLeastOnceProcessor[K, V] = {

    lazy val s ngletonProducer = Cl entProv ders.mkProducer[UnKeyed, Un f edUserAct on](
      bootstrapServer = kafkaDestCluster,
      cl ent d = kafkaProducerCl ent d,
      keySerde = UnKeyedSerde.ser al zer,
      valueSerde = ScalaSerdes.Thr ft[Un f edUserAct on].ser al zer,
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

    mkAtLeastOnceProcessor[K, V, UnKeyed, Un f edUserAct on](
      na  = na ,
      kafkaS ceCluster = kafkaS ceCluster,
      kafkaGroup d = kafkaGroup d,
      kafkaS ceTop c = kafkaS ceTop c,
      s ceKeyDeser al zer = s ceKeyDeser al zer,
      s ceValueDeser al zer = s ceValueDeser al zer,
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
      produce = produceOpt.getOrElse(defaultProducer),
      trustStoreLocat onOpt = trustStoreLocat onOpt,
      statsRece ver = statsRece ver,
      dec der = dec der,
      zone = zone,
      maybeProcess = maybeProcess,
    )
  }

  /**
   * A common AtLeastOnceProcessor prov der
   */
  def mkAtLeastOnceProcessor[K, V, OUTK, OUTV](
    na : Str ng,
    kafkaS ceCluster: Str ng,
    kafkaGroup d: Str ng,
    kafkaS ceTop c: Str ng,
    s ceKeyDeser al zer: Deser al zer[K],
    s ceValueDeser al zer: Deser al zer[V],
    comm  nterval: Durat on = Cl entConf gs.kafkaComm  ntervalDefault,
    maxPollRecords:  nt = Cl entConf gs.consu rMaxPollRecordsDefault,
    maxPoll nterval: Durat on = Cl entConf gs.consu rMaxPoll ntervalDefault,
    sess onT  out: Durat on = Cl entConf gs.consu rSess onT  outDefault,
    fetchMax: StorageUn  = Cl entConf gs.consu rFetchMaxDefault,
    fetchM n: StorageUn  = Cl entConf gs.consu rFetchM nDefault,
    rece veBuffer: StorageUn  = Cl entConf gs.consu rRece veBufferS zeDefault,
    processorMaxPend ngRequests:  nt,
    processorWorkerThreads:  nt,
    adapter: AbstractAdapter[V, OUTK, OUTV],
    kafkaProducersAndS nkTop cs: Seq[(Block ngF nagleKafkaProducer[OUTK, OUTV], Str ng)],
    produce: (Block ngF nagleKafkaProducer[OUTK, OUTV], OUTK, OUTV, Str ng,  aders, StatsRece ver,
      Dec der) => Future[Un ],
    trustStoreLocat onOpt: Opt on[Str ng] = So (Cl entConf gs.trustStoreLocat onDefault),
    statsRece ver: StatsRece ver,
    dec der: Dec der,
    zone: Zone,
    maybeProcess: (Consu rRecord[K, V], Zone) => Boolean = ZoneF lter ng.localDCF lter ng[K, V] _,
  ): AtLeastOnceProcessor[K, V] = {
    val threadSafeKafkaCl ent =
      Cl entProv ders.mkConsu r[K, V](
        bootstrapServer = kafkaS ceCluster,
        keySerde = s ceKeyDeser al zer,
        valueSerde = s ceValueDeser al zer,
        group d = kafkaGroup d,
        autoComm  = false,
        maxPollRecords = maxPollRecords,
        maxPoll nterval = maxPoll nterval,
        sess onT  out = sess onT  out,
        fetchMax = fetchMax,
        fetchM n = fetchM n,
        rece veBuffer = rece veBuffer,
        trustStoreLocat onOpt = trustStoreLocat onOpt
      )

    def publ sh(
      event: Consu rRecord[K, V]
    ): Future[Un ] = {
      statsRece ver.counter("consu dEvents"). ncr()

       f (maybeProcess(event, zone))
        Future
          .collect(
            adapter
              .adaptOneToKeyedMany(event.value, statsRece ver)
              .flatMap {
                case (k, v) =>
                  kafkaProducersAndS nkTop cs.map {
                    case (producer, s nkTop c) =>
                      produce(producer, k, v, s nkTop c, event. aders(), statsRece ver, dec der)
                  }
              }).un 
      else
        Future.Un 
    }

    AtLeastOnceProcessor[K, V](
      na  = na ,
      top c = kafkaS ceTop c,
      consu r = threadSafeKafkaCl ent,
      processor = publ sh,
      maxPend ngRequests = processorMaxPend ngRequests,
      workerThreads = processorWorkerThreads,
      comm  ntervalMs = comm  nterval. nM ll seconds,
      statsRece ver = statsRece ver
    )
  }
}
