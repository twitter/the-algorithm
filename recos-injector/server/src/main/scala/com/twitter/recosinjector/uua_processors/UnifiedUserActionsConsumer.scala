package com.tw ter.recos njector.uua_processors

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.consu rs.F nagleKafkaConsu rBu lder
 mport com.tw ter.f natra.kafka.doma n.KafkaGroup d
 mport com.tw ter.f natra.kafka.doma n.SeekStrategy
 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.f natra.kafka.serde.UnKeyedSerde
 mport org.apac .kafka.cl ents.CommonCl entConf gs
 mport org.apac .kafka.common.conf g.SaslConf gs
 mport org.apac .kafka.common.conf g.SslConf gs
 mport org.apac .kafka.common.secur y.auth.Secur yProtocol
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.kafka.cl ent.processor.ThreadSafeKafkaConsu rCl ent
 mport com.tw ter.convers ons.StorageUn Ops._

class Un f edUserAct onsConsu r(
  processor: Un f edUserAct onProcessor,
  truststoreLocat on: Str ng
)(
   mpl c  statsRece ver: StatsRece ver) {
   mport Un f edUserAct onsConsu r._

  pr vate val kafkaCl ent = new ThreadSafeKafkaConsu rCl ent[UnKeyed, Un f edUserAct on](
    F nagleKafkaConsu rBu lder[UnKeyed, Un f edUserAct on]()
      .group d(KafkaGroup d(uuaRecos njectorGroup d))
      .keyDeser al zer(UnKeyedSerde.deser al zer)
      .valueDeser al zer(ScalaSerdes.Thr ft[Un f edUserAct on].deser al zer)
      .dest(uuaDest)
      .maxPollRecords(maxPollRecords)
      .maxPoll nterval(maxPoll nterval)
      .fetchMax(fetchMax)
      .seekStrategy(SeekStrategy.END)
      .enableAutoComm (false) // AtLeastOnceProcessor performs comm s manually
      .w hConf g(CommonCl entConf gs.SECUR TY_PROTOCOL_CONF G, Secur yProtocol.SASL_SSL.toStr ng)
      .w hConf g(SslConf gs.SSL_TRUSTSTORE_LOCAT ON_CONF G, truststoreLocat on)
      .w hConf g(SaslConf gs.SASL_MECHAN SM, SaslConf gs.GSSAP _MECHAN SM)
      .w hConf g(SaslConf gs.SASL_KERBEROS_SERV CE_NAME, "kafka")
      .w hConf g(SaslConf gs.SASL_KERBEROS_SERVER_NAME, "kafka")
      .conf g)

  val atLeastOnceProcessor: AtLeastOnceProcessor[UnKeyed, Un f edUserAct on] = {
    AtLeastOnceProcessor[UnKeyed, Un f edUserAct on](
      na  = processorNa ,
      top c = uuaTop c,
      consu r = kafkaCl ent,
      processor = processor.apply,
      maxPend ngRequests = maxPend ngRequests,
      workerThreads = workerThreads,
      comm  ntervalMs = comm  ntervalMs,
      statsRece ver = statsRece ver.scope(processorNa )
    )
  }

}

object Un f edUserAct onsConsu r {
  val maxPollRecords = 1000
  val maxPoll nterval = 5.m nutes
  val fetchMax = 1. gabytes
  val maxPend ngRequests = 1000
  val workerThreads = 16
  val comm  ntervalMs = 10.seconds. nM ll seconds
  val processorNa  = "un f ed_user_act ons_processor"
  val uuaTop c = "un f ed_user_act ons_engage nts"
  val uuaDest = "/s/kafka/blueb rd-1:kafka-tls"
  val uuaRecos njectorGroup d = "recos- njector"
}
