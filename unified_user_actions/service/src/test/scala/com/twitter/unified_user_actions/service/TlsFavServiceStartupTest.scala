package com.tw ter.un f ed_user_act ons.serv ce

 mport com.google. nject.Stage
 mport com.tw ter.app.GlobalFlag
 mport com.tw ter.f natra.kafka.consu rs.F nagleKafkaConsu rBu lder
 mport com.tw ter.f natra.kafka.doma n.AckMode
 mport com.tw ter.f natra.kafka.doma n.KafkaGroup d
 mport com.tw ter.f natra.kafka.doma n.KafkaTop c
 mport com.tw ter.f natra.kafka.doma n.SeekStrategy
 mport com.tw ter.f natra.kafka.producers.F nagleKafkaProducerBu lder
 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.f natra.kafka.serde.UnKeyedSerde
 mport com.tw ter.f natra.kafka.test.KafkaFeatureTest
 mport com.tw ter. nject.server.EmbeddedTw terServer
 mport com.tw ter.kafka.cl ent.processor.KafkaConsu rCl ent
 mport com.tw ter.t  l neserv ce.thr ftscala.Contextual zedFavor eEvent
 mport com.tw ter.t  l neserv ce.thr ftscala.Favor eEvent
 mport com.tw ter.t  l neserv ce.thr ftscala.Favor eEventUn on
 mport com.tw ter.t  l neserv ce.thr ftscala.LogEventContext
 mport com.tw ter.un f ed_user_act ons.kafka.Cl entConf gs
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorTlsFavsModule
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.StorageUn 

class TlsFavServ ceStartupTest extends KafkaFeatureTest {
  pr vate val  nputTop c =
    kafkaTop c(UnKeyedSerde, ScalaSerdes.Thr ft[Contextual zedFavor eEvent], na  = "s ce")
  pr vate val outputTop c =
    kafkaTop c(UnKeyedSerde, ScalaSerdes.Thr ft[Un f edUserAct on], na  = "s nk")

  val startupFlags = Map(
    "kafka.group. d" -> "tls",
    "kafka.producer.cl ent. d" -> "uua",
    "kafka.s ce.top c" ->  nputTop c.top c,
    "kafka.s nk.top cs" -> outputTop c.top c,
    "kafka.max.pend ng.requests" -> "100",
    "kafka.worker.threads" -> "1",
    "kafka.trust.store.enable" -> "false",
    "kafka.producer.batch.s ze" -> "0.byte",
    "cluster" -> "atla",
  )

  val dec derFlags = Map(
    "dec der.base" -> "/dec der.yml"
  )

  overr de protected def kafkaBootstrapFlag: Map[Str ng, Str ng] = {
    Map(
      Cl entConf gs.kafkaBootstrapServerConf g -> kafkaCluster.bootstrapServers(),
      Cl entConf gs.kafkaBootstrapServerRemoteDestConf g -> kafkaCluster.bootstrapServers(),
    )
  }

  overr de val server: EmbeddedTw terServer = new EmbeddedTw terServer(
    tw terServer = new TlsFavsServ ce() {
      overr de def warmup(): Un  = {
        // noop
      }

      overr de val overr deModules = Seq(
        KafkaProcessorTlsFavsModule
      )
    },
    globalFlags = Map[GlobalFlag[_], Str ng](
      com.tw ter.f natra.kafka.consu rs.enableTlsAndKerberos -> "false",
    ),
    flags = startupFlags ++ kafkaBootstrapFlag ++ dec derFlags,
    stage = Stage.PRODUCT ON
  )

  pr vate def getConsu r(
    seekStrategy: SeekStrategy = SeekStrategy.BEG NN NG,
  ) = {
    val bu lder = F nagleKafkaConsu rBu lder()
      .dest(brokers.map(_.brokerL st()).mkStr ng(","))
      .cl ent d("consu r")
      .group d(KafkaGroup d("val dator"))
      .keyDeser al zer(UnKeyedSerde.deser al zer)
      .valueDeser al zer(ScalaSerdes.Thr ft[Contextual zedFavor eEvent].deser al zer)
      .requestT  out(Durat on.fromSeconds(1))
      .enableAutoComm (false)
      .seekStrategy(seekStrategy)

    new KafkaConsu rCl ent(bu lder.conf g)
  }

  pr vate def getProducer(cl ent d: Str ng = "producer") = {
    F nagleKafkaProducerBu lder()
      .dest(brokers.map(_.brokerL st()).mkStr ng(","))
      .cl ent d(cl ent d)
      .ackMode(AckMode.ALL)
      .batchS ze(StorageUn .zero)
      .keySer al zer(UnKeyedSerde.ser al zer)
      .valueSer al zer(ScalaSerdes.Thr ft[Contextual zedFavor eEvent].ser al zer)
      .bu ld()
  }

  pr vate def getUUAConsu r(
    seekStrategy: SeekStrategy = SeekStrategy.BEG NN NG,
  ) = {
    val bu lder = F nagleKafkaConsu rBu lder()
      .dest(brokers.map(_.brokerL st()).mkStr ng(","))
      .cl ent d("consu r_uua")
      .group d(KafkaGroup d("val dator_uua"))
      .keyDeser al zer(UnKeyedSerde.deser al zer)
      .valueDeser al zer(ScalaSerdes.Thr ft[Un f edUserAct on].deser al zer)
      .requestT  out(Durat on.fromSeconds(1))
      .enableAutoComm (false)
      .seekStrategy(seekStrategy)

    new KafkaConsu rCl ent(bu lder.conf g)
  }

  test("TlsFavServ ce starts") {
    server.assert althy()
  }

  test("TlsFavServ ce should process  nput events") {
    val producer = getProducer()
    val  nputConsu r = getConsu r()
    val uuaConsu r = getUUAConsu r()

    val favor eEvent = Favor eEventUn on.Favor e(Favor eEvent(123L, 123L, 123L, 123L))
    val value =
      Contextual zedFavor eEvent(favor eEvent, LogEventContext("localhost", 123L))

    try {
      server.assert althy()

      // before, should be empty
       nputConsu r.subscr be(Set(KafkaTop c( nputTop c.top c)))
      assert( nputConsu r.poll().count() == 0)

      // after, should conta n at least a  ssage
      awa (producer.send( nputTop c.top c, new UnKeyed, value, System.currentT  M ll s))
      producer.flush()
      assert( nputConsu r.poll().count() == 1)

      uuaConsu r.subscr be(Set(KafkaTop c(outputTop c.top c)))
      // T   s tr cky:    s not guaranteed that t  TlsFavsServ ce can process and output t 
      // event to output top c faster than t  below consu r. So  'd use a t  r  re wh ch may
      // not be t  best pract ce.
      //  f so one f nds t  below test  s flaky, please just remove t  below test completely.
      Thread.sleep(5000L)
      assert(uuaConsu r.poll().count() == 1)
    } f nally {
      awa (producer.close())
       nputConsu r.close()
    }
  }
}
