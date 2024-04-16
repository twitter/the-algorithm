package com.tw ter.un f ed_user_act ons.kafka

 mport com.tw ter.convers ons.StorageUn Ops._
 mport com.tw ter.f natra.kafka.consu rs.F nagleKafkaConsu rBu lder
 mport com.tw ter.f natra.kafka.doma n.AckMode
 mport com.tw ter.f natra.kafka.doma n.KafkaGroup d
 mport com.tw ter.f natra.kafka.producers.Block ngF nagleKafkaProducer
 mport com.tw ter.f natra.kafka.producers.F nagleKafkaProducerBu lder
 mport com.tw ter.kafka.cl ent.processor.ThreadSafeKafkaConsu rCl ent
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.StorageUn 
 mport org.apac .kafka.cl ents.CommonCl entConf gs
 mport org.apac .kafka.cl ents.producer.ProducerConf g
 mport org.apac .kafka.common.conf g.SaslConf gs
 mport org.apac .kafka.common.conf g.SslConf gs
 mport org.apac .kafka.common.record.Compress onType
 mport org.apac .kafka.common.secur y.auth.Secur yProtocol
 mport org.apac .kafka.common.ser al zat on.Deser al zer
 mport org.apac .kafka.common.ser al zat on.Ser al zer

/**
 * A Ut l y class ma nly prov des raw Kafka producer/consu r supports
 */
object Cl entProv ders extends Logg ng {

  /**
   * Prov de a F nagle-thread-safe-and-compat ble Kafka consu r.
   * For t  params and t  r s gn f cance, please see [[Cl entConf gs]]
   */
  def mkConsu r[CK, CV](
    bootstrapServer: Str ng,
    keySerde: Deser al zer[CK],
    valueSerde: Deser al zer[CV],
    group d: Str ng,
    autoComm : Boolean = false,
    maxPollRecords:  nt = Cl entConf gs.consu rMaxPollRecordsDefault,
    maxPoll nterval: Durat on = Cl entConf gs.consu rMaxPoll ntervalDefault,
    autoComm  nterval: Durat on = Cl entConf gs.kafkaComm  ntervalDefault,
    sess onT  out: Durat on = Cl entConf gs.consu rSess onT  outDefault,
    fetchMax: StorageUn  = Cl entConf gs.consu rFetchMaxDefault,
    fetchM n: StorageUn  = Cl entConf gs.consu rFetchM nDefault,
    rece veBuffer: StorageUn  = Cl entConf gs.consu rRece veBufferS zeDefault,
    trustStoreLocat onOpt: Opt on[Str ng] = So (Cl entConf gs.trustStoreLocat onDefault)
  ): ThreadSafeKafkaConsu rCl ent[CK, CV] = {
    val baseBu lder =
      F nagleKafkaConsu rBu lder[CK, CV]()
        .keyDeser al zer(keySerde)
        .valueDeser al zer(valueSerde)
        .dest(bootstrapServer)
        .group d(KafkaGroup d(group d))
        .enableAutoComm (autoComm )
        .maxPollRecords(maxPollRecords)
        .maxPoll nterval(maxPoll nterval)
        .autoComm  nterval(autoComm  nterval)
        .rece veBuffer(rece veBuffer)
        .sess onT  out(sess onT  out)
        .fetchMax(fetchMax)
        .fetchM n(fetchM n)
        .w hConf g(
          CommonCl entConf gs.SECUR TY_PROTOCOL_CONF G,
          Secur yProtocol.PLA NTEXT.toStr ng)

    trustStoreLocat onOpt
      .map { trustStoreLocat on =>
        new ThreadSafeKafkaConsu rCl ent[CK, CV](
          baseBu lder
            .w hConf g(
              CommonCl entConf gs.SECUR TY_PROTOCOL_CONF G,
              Secur yProtocol.SASL_SSL.toStr ng)
            .w hConf g(SslConf gs.SSL_TRUSTSTORE_LOCAT ON_CONF G, trustStoreLocat on)
            .w hConf g(SaslConf gs.SASL_MECHAN SM, SaslConf gs.GSSAP _MECHAN SM)
            .w hConf g(SaslConf gs.SASL_KERBEROS_SERV CE_NAME, "kafka")
            .w hConf g(SaslConf gs.SASL_KERBEROS_SERVER_NAME, "kafka")
            .conf g)
      }.getOrElse {
        new ThreadSafeKafkaConsu rCl ent[CK, CV](
          baseBu lder
            .w hConf g(
              CommonCl entConf gs.SECUR TY_PROTOCOL_CONF G,
              Secur yProtocol.PLA NTEXT.toStr ng)
            .conf g)
      }
  }

  /**
   * Prov de a F nagle-compat ble Kafka producer.
   * For t  params and t  r s gn f cance, please see [[Cl entConf gs]]
   */
  def mkProducer[PK, PV](
    bootstrapServer: Str ng,
    keySerde: Ser al zer[PK],
    valueSerde: Ser al zer[PV],
    cl ent d: Str ng,
     dempotence: Boolean = Cl entConf gs.producer dempotenceDefault,
    batchS ze: StorageUn  = Cl entConf gs.producerBatchS zeDefault,
    l nger: Durat on = Cl entConf gs.producerL ngerDefault,
    buffer m: StorageUn  = Cl entConf gs.producerBuffer mDefault,
    compress onType: Compress onType = Cl entConf gs.compress onDefault.compress onType,
    retr es:  nt = Cl entConf gs.retr esDefault,
    retryBackoff: Durat on = Cl entConf gs.retryBackoffDefault,
    requestT  out: Durat on = Cl entConf gs.producerRequestT  outDefault,
    trustStoreLocat onOpt: Opt on[Str ng] = So (Cl entConf gs.trustStoreLocat onDefault)
  ): Block ngF nagleKafkaProducer[PK, PV] = {
    val baseBu lder = F nagleKafkaProducerBu lder[PK, PV]()
      .keySer al zer(keySerde)
      .valueSer al zer(valueSerde)
      .dest(bootstrapServer)
      .cl ent d(cl ent d)
      .batchS ze(batchS ze)
      .l nger(l nger)
      .buffer moryS ze(buffer m)
      .maxRequestS ze(4. gabytes)
      .compress onType(compress onType)
      .enable dempotence( dempotence)
      .ackMode(AckMode.ALL)
      .max nFl ghtRequestsPerConnect on(5)
      .retr es(retr es)
      .retryBackoff(retryBackoff)
      .requestT  out(requestT  out)
      .w hConf g(ProducerConf g.DEL VERY_T MEOUT_MS_CONF G, requestT  out + l nger)
    trustStoreLocat onOpt
      .map { trustStoreLocat on =>
        baseBu lder
          .w hConf g(
            CommonCl entConf gs.SECUR TY_PROTOCOL_CONF G,
            Secur yProtocol.SASL_SSL.toStr ng)
          .w hConf g(SslConf gs.SSL_TRUSTSTORE_LOCAT ON_CONF G, trustStoreLocat on)
          .w hConf g(SaslConf gs.SASL_MECHAN SM, SaslConf gs.GSSAP _MECHAN SM)
          .w hConf g(SaslConf gs.SASL_KERBEROS_SERV CE_NAME, "kafka")
          .w hConf g(SaslConf gs.SASL_KERBEROS_SERVER_NAME, "kafka")
          .bu ld()
      }.getOrElse {
        baseBu lder
          .w hConf g(
            CommonCl entConf gs.SECUR TY_PROTOCOL_CONF G,
            Secur yProtocol.PLA NTEXT.toStr ng)
          .bu ld()
      }
  }
}
