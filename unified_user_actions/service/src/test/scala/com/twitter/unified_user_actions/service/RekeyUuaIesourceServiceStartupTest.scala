package com.tw ter.un f ed_user_act ons.serv ce

 mport com.google. nject.Stage
 mport com.tw ter.adserver.thr ftscala.D splayLocat on
 mport com.tw ter.app.GlobalFlag
 mport com.tw ter.f natra.kafka.consu rs.F nagleKafkaConsu rBu lder
 mport com.tw ter.f natra.kafka.doma n.AckMode
 mport com.tw ter.f natra.kafka.doma n.KafkaGroup d
 mport com.tw ter.f natra.kafka.doma n.KafkaTop c
 mport com.tw ter.f natra.kafka.doma n.SeekStrategy
 mport com.tw ter.f natra.kafka.producers.F nagleKafkaProducerBu lder
 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter.f natra.kafka.serde.UnKeyedSerde
 mport com.tw ter.f natra.kafka.test.KafkaFeatureTest
 mport com.tw ter. es ce.thr ftscala.Cl entEventContext
 mport com.tw ter. es ce.thr ftscala.T et mpress on
 mport com.tw ter. es ce.thr ftscala.Cl entType
 mport com.tw ter. es ce.thr ftscala.ContextualEventNa space
 mport com.tw ter. es ce.thr ftscala.Engag ngContext
 mport com.tw ter. es ce.thr ftscala.EventS ce
 mport com.tw ter. es ce.thr ftscala. nteract onDeta ls
 mport com.tw ter. es ce.thr ftscala. nteract onEvent
 mport com.tw ter. es ce.thr ftscala. nteract onType
 mport com.tw ter. es ce.thr ftscala. nteract onTargetType
 mport com.tw ter. es ce.thr ftscala.User dent f er
 mport com.tw ter. nject.server.EmbeddedTw terServer
 mport com.tw ter.kafka.cl ent.processor.KafkaConsu rCl ent
 mport com.tw ter.un f ed_user_act ons.kafka.Cl entConf gs
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorRekeyUua es ceModule
 mport com.tw ter.un f ed_user_act ons.thr ftscala.KeyedUuaT et
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.StorageUn 

class RekeyUua es ceServ ceStartupTest extends KafkaFeatureTest {
  pr vate val  nputTop c =
    kafkaTop c(ScalaSerdes.Long, ScalaSerdes.CompactThr ft[ nteract onEvent], na  = "s ce")
  pr vate val outputTop c =
    kafkaTop c(ScalaSerdes.Long, ScalaSerdes.Thr ft[KeyedUuaT et], na  = "s nk")

  val startupFlags = Map(
    "kafka.group. d" -> "cl ent-event",
    "kafka.producer.cl ent. d" -> "uua",
    "kafka.s ce.top c" ->  nputTop c.top c,
    "kafka.s nk.top cs" -> outputTop c.top c,
    "kafka.consu r.fetch.m n" -> "6. gabytes",
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
    tw terServer = new RekeyUua es ceServ ce() {
      overr de def warmup(): Un  = {
        // noop
      }

      overr de val overr deModules = Seq(
        KafkaProcessorRekeyUua es ceModule
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
      .keyDeser al zer(ScalaSerdes.Long.deser al zer)
      .valueDeser al zer(ScalaSerdes.CompactThr ft[ nteract onEvent].deser al zer)
      .requestT  out(Durat on.fromSeconds(1))
      .enableAutoComm (false)
      .seekStrategy(seekStrategy)

    new KafkaConsu rCl ent(bu lder.conf g)
  }

  pr vate def getUUAConsu r(
    seekStrategy: SeekStrategy = SeekStrategy.BEG NN NG,
  ) = {
    val bu lder = F nagleKafkaConsu rBu lder()
      .dest(brokers.map(_.brokerL st()).mkStr ng(","))
      .cl ent d("consu r_uua")
      .group d(KafkaGroup d("val dator_uua"))
      .keyDeser al zer(UnKeyedSerde.deser al zer)
      .valueDeser al zer(ScalaSerdes.Thr ft[KeyedUuaT et].deser al zer)
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
      .keySer al zer(ScalaSerdes.Long.ser al zer)
      .valueSer al zer(ScalaSerdes.CompactThr ft[ nteract onEvent].ser al zer)
      .bu ld()
  }

  test("RekeyUua es ceServ ce starts") {
    server.assert althy()
  }

  test("RekeyUua es ceServ ce should process  nput events") {
    val producer = getProducer()
    val  nputConsu r = getConsu r()
    val uuaConsu r = getUUAConsu r()

    val value:  nteract onEvent =  nteract onEvent(
      target d = 1L,
      targetType =  nteract onTargetType.T et,
      engag ngUser d = 11L,
      eventS ce = EventS ce.Cl entEvent,
      t  stampM ll s = 123456L,
       nteract onType = So ( nteract onType.T etRender mpress on),
      deta ls =  nteract onDeta ls.T etRender mpress on(T et mpress on()),
      add  onalEngag ngUser dent f ers = User dent f er(),
      engag ngContext = Engag ngContext.Cl entEventContext(
        Cl entEventContext(
          cl entEventNa space = ContextualEventNa space(),
          cl entType = Cl entType. phone,
          d splayLocat on = D splayLocat on(1)))
    )

    try {
      server.assert althy()

      // before, should be empty
       nputConsu r.subscr be(Set(KafkaTop c( nputTop c.top c)))
      assert( nputConsu r.poll().count() == 0)

      // after, should conta n at least a  ssage
      awa (producer.send( nputTop c.top c, value.target d, value, System.currentT  M ll s))
      producer.flush()
      assert( nputConsu r.poll().count() == 1)

      uuaConsu r.subscr be(Set(KafkaTop c(outputTop c.top c)))
      // T   s tr cky:    s not guaranteed that t  srv ce can process and output t 
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
