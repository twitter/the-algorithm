package com.tw ter.recos njector.publ s rs

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f natra.kafka.producers.F nagleKafkaProducerBu lder
 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter.recos. nternal.thr ftscala.RecosHose ssage
 mport org.apac .kafka.cl ents.CommonCl entConf gs
 mport org.apac .kafka.cl ents.producer.ProducerRecord
 mport org.apac .kafka.common.conf g.SaslConf gs
 mport org.apac .kafka.common.conf g.SslConf gs
 mport org.apac .kafka.common.secur y.auth.Secur yProtocol
 mport org.apac .kafka.common.ser al zat on.Str ngSer al zer

case class KafkaEventPubl s r(
  kafkaDest: Str ng,
  outputKafkaTop cPref x: Str ng,
  cl ent d: Cl ent d,
  truststoreLocat on: Str ng) {

  pr vate val producer = F nagleKafkaProducerBu lder[Str ng, RecosHose ssage]()
    .dest(kafkaDest)
    .cl ent d(cl ent d.na )
    .keySer al zer(new Str ngSer al zer)
    .valueSer al zer(ScalaSerdes.Thr ft[RecosHose ssage].ser al zer)
    .w hConf g(CommonCl entConf gs.SECUR TY_PROTOCOL_CONF G, Secur yProtocol.SASL_SSL.toStr ng)
    .w hConf g(SslConf gs.SSL_TRUSTSTORE_LOCAT ON_CONF G, truststoreLocat on)
    .w hConf g(SaslConf gs.SASL_MECHAN SM, SaslConf gs.GSSAP _MECHAN SM)
    .w hConf g(SaslConf gs.SASL_KERBEROS_SERV CE_NAME, "kafka")
    .w hConf g(SaslConf gs.SASL_KERBEROS_SERVER_NAME, "kafka")
    // Use Nat ve Kafka Cl ent
    .bu ldCl ent()

  def publ sh(
     ssage: RecosHose ssage,
    top c: Str ng
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Un  = {
    val top cNa  = s"${outputKafkaTop cPref x}_$top c"
    // Kafka Producer  s thread-safe. No extra Future-pool protect.
    producer.send(new ProducerRecord(top cNa ,  ssage))
    statsRece ver.counter(top cNa  + "_wr ten_msg_success"). ncr()
  }
}

object KafkaEventPubl s r {
  // Kafka top cs ava lable for publ sh ng
  val UserV deoTop c = "user_v deo"
  val UserT etEnt yTop c = "user_t et_ent y"
  val UserUserTop c = "user_user"
  val UserAdTop c = "user_t et"
  val UserT etPlusTop c = "user_t et_plus"
}
