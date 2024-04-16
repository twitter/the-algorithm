package com.tw ter.cr_m xer.module.core

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.thr ftscala.GetT etsRecom ndat onsScr be
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f natra.kafka.producers.F nagleKafkaProducerBu lder
 mport com.tw ter.f natra.kafka.producers.KafkaProducerBase
 mport com.tw ter.f natra.kafka.producers.NullKafkaProducer
 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.S ngleton
 mport org.apac .kafka.cl ents.CommonCl entConf gs
 mport org.apac .kafka.common.conf g.SaslConf gs
 mport org.apac .kafka.common.conf g.SslConf gs
 mport org.apac .kafka.common.record.Compress onType
 mport org.apac .kafka.common.secur y.auth.Secur yProtocol
 mport org.apac .kafka.common.ser al zat on.Serdes

object KafkaProducerModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov deT etRecsLoggerFactory(
    serv ce dent f er: Serv ce dent f er,
  ): KafkaProducerBase[Str ng, GetT etsRecom ndat onsScr be] = {
    KafkaProducerFactory.getKafkaProducer(serv ce dent f er.env ron nt)
  }
}

object KafkaProducerFactory {
  pr vate val jaasConf g =
    """com.sun.secur y.auth.module.Krb5Log nModule
      |requ red 
      |pr nc pal="cr-m xer@TW TTER.B Z" 
      |debug=true 
      |useKeyTab=true 
      |storeKey=true 
      |keyTab="/var/l b/tss/keys/fluffy/keytabs/cl ent/cr-m xer.keytab" 
      |doNotPrompt=true;
    """.str pMarg n.replaceAll("\n", " ")

  pr vate val trustStoreLocat on = "/etc/tw_truststore/ ssag ng/kafka/cl ent.truststore.jks"

  def getKafkaProducer(
    env ron nt: Str ng
  ): KafkaProducerBase[Str ng, GetT etsRecom ndat onsScr be] = {
     f (env ron nt == "prod") {
      F nagleKafkaProducerBu lder()
        .dest("/s/kafka/recom ndat ons:kafka-tls")
        // kerberos params
        .w hConf g(SaslConf gs.SASL_JAAS_CONF G, jaasConf g)
        .w hConf g(
          CommonCl entConf gs.SECUR TY_PROTOCOL_CONF G,
          Secur yProtocol.SASL_SSL.toStr ng)
        .w hConf g(SslConf gs.SSL_TRUSTSTORE_LOCAT ON_CONF G, trustStoreLocat on)
        .w hConf g(SaslConf gs.SASL_MECHAN SM, SaslConf gs.GSSAP _MECHAN SM)
        .w hConf g(SaslConf gs.SASL_KERBEROS_SERV CE_NAME, "kafka")
        .w hConf g(SaslConf gs.SASL_KERBEROS_SERVER_NAME, "kafka")
        // Kafka params
        .keySer al zer(Serdes.Str ng.ser al zer)
        .valueSer al zer(ScalaSerdes.CompactThr ft[GetT etsRecom ndat onsScr be].ser al zer())
        .cl ent d("cr-m xer")
        .enable dempotence(true)
        .compress onType(Compress onType.LZ4)
        .bu ld()
    } else {
      new NullKafkaProducer[Str ng, GetT etsRecom ndat onsScr be]
    }
  }
}
