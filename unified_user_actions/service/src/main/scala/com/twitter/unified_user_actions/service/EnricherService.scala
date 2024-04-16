package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.StorageUn Ops._
 mport com.tw ter.dynmap.DynMap
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.doma n.AckMode
 mport com.tw ter.f natra.kafka.doma n.KafkaGroup d
 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter.f natra.kafkastreams.conf g.KafkaStreamsConf g
 mport com.tw ter.f natra.kafkastreams.conf g.SecureKafkaStreamsConf g
 mport com.tw ter.f natra.kafkastreams.part  on ng.Stat cPart  on ng
 mport com.tw ter.f natra.mtls.modules.Serv ce dent f erModule
 mport com.tw ter.f natra.kafkastreams.dsl.F natraDslFlatMapAsync
 mport com.tw ter.graphql.thr ftscala.GraphqlExecut onServ ce
 mport com.tw ter.logg ng.Logg ng
 mport com.tw ter.un f ed_user_act ons.enr c r.dr ver.Enr ch ntDr ver
 mport com.tw ter.un f ed_user_act ons.enr c r.hcac .LocalCac 
 mport com.tw ter.un f ed_user_act ons.enr c r.hydrator.DefaultHydrator
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.un f ed_user_act ons.enr c r.part  oner.DefaultPart  oner
 mport com.tw ter.un f ed_user_act ons.serv ce.module.Cac Module
 mport com.tw ter.un f ed_user_act ons.serv ce.module.Cl ent dModule
 mport com.tw ter.un f ed_user_act ons.serv ce.module.GraphqlCl entProv derModule
 mport com.tw ter.ut l.Future
 mport org.apac .kafka.common.record.Compress onType
 mport org.apac .kafka.streams.StreamsBu lder
 mport org.apac .kafka.streams.processor.RecordContext
 mport org.apac .kafka.streams.processor.Top cNa Extractor
 mport org.apac .kafka.streams.scala.kstream.Consu d
 mport org.apac .kafka.streams.scala.kstream.Produced
 mport com.tw ter.un f ed_user_act ons.enr c r.dr ver.Enr ch ntPlanUt ls._

object Enr c rServ ceMa n extends Enr c rServ ce

class Enr c rServ ce
    extends F natraDslFlatMapAsync
    w h Stat cPart  on ng
    w h SecureKafkaStreamsConf g
    w h Logg ng {
  val  nputTop c = "un f ed_user_act ons_keyed_dev"
  val OutputTop c = "un f ed_user_act ons_enr c d"

  overr de val modules = Seq(
    Cac Module,
    Cl ent dModule,
    GraphqlCl entProv derModule,
    Serv ce dent f erModule
  )

  overr de protected def conf gureKafkaStreams(bu lder: StreamsBu lder): Un  = {
    val graphqlCl ent =  njector. nstance[GraphqlExecut onServ ce.F nagledCl ent]
    val localCac  =  njector. nstance[LocalCac [Enr ch ntKey, DynMap]]
    val statsRece ver =  njector. nstance[StatsRece ver]
    val dr ver = new Enr ch ntDr ver(
      f nalOutputTop c = So (OutputTop c),
      part  onedTop c =  nputTop c,
      hydrator = new DefaultHydrator(
        cac  = localCac ,
        graphqlCl ent = graphqlCl ent,
        scopedStatsRece ver = statsRece ver.scope("DefaultHydrator")),
      part  oner = new DefaultPart  oner
    )

    val kstream = bu lder.asScala
      .stream( nputTop c)(
        Consu d.`w h`(ScalaSerdes.Thr ft[Enr ch ntKey], ScalaSerdes.Thr ft[Enr ch ntEnvelop]))
      .flatMapAsync[Enr ch ntKey, Enr ch ntEnvelop](
        comm  nterval = 5.seconds,
        numWorkers = 10000
      ) { (enr ch ntKey: Enr ch ntKey, enr ch ntEnvelop: Enr ch ntEnvelop) =>
        dr ver
          .execute(So (enr ch ntKey), Future.value(enr ch ntEnvelop))
          .map(tuple => tuple._1.map(key => (key, tuple._2)).seq)
      }

    val top cExtractor: Top cNa Extractor[Enr ch ntKey, Enr ch ntEnvelop] =
      (_: Enr ch ntKey, envelop: Enr ch ntEnvelop, _: RecordContext) =>
        envelop.plan.getLastCompletedStage.outputTop c.getOrElse(
          throw new  llegalStateExcept on("M ss ng output top c  n t  last completed stage"))

    kstream.to(top cExtractor)(
      Produced.`w h`(ScalaSerdes.Thr ft[Enr ch ntKey], ScalaSerdes.Thr ft[Enr ch ntEnvelop]))
  }

  overr de def streamsPropert es(conf g: KafkaStreamsConf g): KafkaStreamsConf g =
    super
      .streamsPropert es(conf g)
      .consu r.group d(KafkaGroup d(appl cat on d()))
      .consu r.cl ent d(s"${appl cat on d()}-consu r")
      .consu r.requestT  out(30.seconds)
      .consu r.sess onT  out(30.seconds)
      .consu r.fetchM n(1. gabyte)
      .consu r.fetchMax(5. gabytes)
      .consu r.rece veBuffer(32. gabytes)
      .consu r.maxPoll nterval(1.m nute)
      .consu r.maxPollRecords(50000)
      .producer.cl ent d(s"${appl cat on d()}-producer")
      .producer.batchS ze(16.k lobytes)
      .producer.buffer moryS ze(256. gabyte)
      .producer.requestT  out(30.seconds)
      .producer.compress onType(Compress onType.LZ4)
      .producer.ackMode(AckMode.ALL)
}
