package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.app.Flag
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.StorageUn Ops._
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.S mpleRec p ent
 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.doma n.AckMode
 mport com.tw ter.f natra.kafka.doma n.KafkaGroup d
 mport com.tw ter.f natra.kafka.doma n.KafkaTop c
 mport com.tw ter.f natra.kafka.producers.F nagleKafkaProducerConf g
 mport com.tw ter.f natra.kafka.producers.KafkaProducerConf g
 mport com.tw ter.f natra.kafka.producers.Tw terKafkaProducerConf g
 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.f natra.kafka.serde.UnKeyedSerde
 mport com.tw ter.f natra.kafkastreams.conf g.KafkaStreamsConf g
 mport com.tw ter.f natra.kafkastreams.conf g.SecureKafkaStreamsConf g
 mport com.tw ter.f natra.kafkastreams.dsl.F natraDslToCluster
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.un f ed_user_act ons.enr c r.dr ver.Enr ch ntDr ver
 mport com.tw ter.un f ed_user_act ons.enr c r.hydrator.NoopHydrator
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on.Not f cat onT etEnr ch nt
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on.T etEnr ch nt
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntPlan
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStage
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStageStatus
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStageType
 mport com.tw ter.un f ed_user_act ons.enr c r.part  oner.DefaultPart  oner
 mport com.tw ter.un f ed_user_act ons.enr c r.part  oner.DefaultPart  oner.NullKey
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.ut l.Awa 
 mport com.tw ter.ut l.Future
 mport org.apac .kafka.common.record.Compress onType
 mport org.apac .kafka.streams.StreamsBu lder
 mport org.apac .kafka.streams.scala.kstream.Consu d
 mport org.apac .kafka.streams.scala.kstream.KStream
 mport org.apac .kafka.streams.scala.kstream.Produced
object Enr ch ntPlannerServ ceMa n extends Enr ch ntPlannerServ ce {
  val Appl cat on d = "uua-enr ch nt-planner"
  val  nputTop c = "un f ed_user_act ons"
  val OutputPart  onedTop c = "un f ed_user_act ons_keyed_dev"
  val Sampl ngDec der = "Enr ch ntPlannerSampl ng"
}

/**
 * T  serv ce  s t  f rst step (planner) of t  UUA Enr ch nt process.
 *   does t  follow ng:
 * 1. Read Prod UUA top c un f ed_user_act ons from t  Prod cluster and wr e to (see below) e  r Prod cluster (prod) or Dev cluster (dev/stag ng)
 * 2. For t  wr e,   opt onally randomly downsample t  events w n publ sh ng, controlled by a Dec der
 * 3. T  output's key would be t  f rst step of t  repart  on ng, most l kely t  Enr ch ntKey of t  T et type.
 */
class Enr ch ntPlannerServ ce extends F natraDslToCluster w h SecureKafkaStreamsConf g {
   mport Enr ch ntPlannerServ ceMa n._

  val kafkaOutputCluster: Flag[Str ng] = flag(
    na  = "kafka.output.server",
    default = "",
     lp =
      """T  output Kafka cluster.
        |T   s needed s nce   read from a cluster and potent ally output to a d fferent cluster.
        |""".str pMarg n
  )

  val kafkaOutputEnableTls: Flag[Boolean] = flag(
    na  = "kafka.output.enable.tls",
    default = true,
     lp = ""
  )

  overr de val modules: Seq[Tw terModule] = Seq(
    Dec derModule
  )

  overr de protected def conf gureKafkaStreams(bu lder: StreamsBu lder): Un  = {
    val dec der =  njector. nstance[Dec der]
    val dr ver = new Enr ch ntDr ver(
      f nalOutputTop c = NoopHydrator.OutputTop c,
      part  onedTop c = OutputPart  onedTop c,
      hydrator = new NoopHydrator,
      part  oner = new DefaultPart  oner)

    val bu lderW houtOutput = bu lder.asScala
      .stream( nputTop c)(Consu d.`w h`(UnKeyedSerde, ScalaSerdes.Thr ft[Un f edUserAct on]))
      // t  maps and f lters out t  n l envelop before furt r process ng
      .flatMapValues { uua =>
        (uua. em match {
          case  em.T et nfo(_) =>
            So (Enr ch ntEnvelop(
              envelop d = uua.hashCode.toLong,
              uua = uua,
              plan = Enr ch ntPlan(Seq(
                Enr ch ntStage(
                  status = Enr ch ntStageStatus. n  al zed,
                  stageType = Enr ch ntStageType.Repart  on,
                   nstruct ons = Seq(T etEnr ch nt)
                ),
                Enr ch ntStage(
                  status = Enr ch ntStageStatus. n  al zed,
                  stageType = Enr ch ntStageType.Hydrat on,
                   nstruct ons = Seq(T etEnr ch nt)
                ),
              ))
            ))
          case  em.Not f cat on nfo(_) =>
            So (Enr ch ntEnvelop(
              envelop d = uua.hashCode.toLong,
              uua = uua,
              plan = Enr ch ntPlan(Seq(
                Enr ch ntStage(
                  status = Enr ch ntStageStatus. n  al zed,
                  stageType = Enr ch ntStageType.Repart  on,
                   nstruct ons = Seq(Not f cat onT etEnr ch nt)
                ),
                Enr ch ntStage(
                  status = Enr ch ntStageStatus. n  al zed,
                  stageType = Enr ch ntStageType.Hydrat on,
                   nstruct ons = Seq(Not f cat onT etEnr ch nt)
                ),
              ))
            ))
          case _ => None
        }).seq
      }
      // execute   dr ver log cs
      .flatMap((_: UnKeyed, envelop: Enr ch ntEnvelop) => {
        // flatMap and Awa .result  s used  re because   dr ver  nterface allows for
        // both synchronous (repart  on log c) and async operat ons (hydrat on log c), but  n  re
        //   purely just need to repart  on synchronously, and thus t  flatMap + Awa .result
        //  s used to s mpl fy and make test ng much eas er.
        val (keyOpt, value) = Awa .result(dr ver.execute(NullKey, Future.value(envelop)))
        keyOpt.map(key => (key, value)).seq
      })
      // t n f nally   sample based on t  output keys
      .f lter((key, _) =>
        dec der. sAva lable(feature = Sampl ngDec der, So (S mpleRec p ent(key. d))))

    conf gureOutput(bu lderW houtOutput)
  }

  pr vate def conf gureOutput(kstream: KStream[Enr ch ntKey, Enr ch ntEnvelop]): Un  = {
     f (kafkaOutputCluster().nonEmpty && kafkaOutputCluster() != bootstrapServer()) {
      kstream.toCluster(
        cluster = kafkaOutputCluster(),
        top c = KafkaTop c(OutputPart  onedTop c),
        cl ent d = s"$Appl cat on d-output-producer",
        kafkaProducerConf g =
           f (kafkaOutputEnableTls())
            F nagleKafkaProducerConf g[Enr ch ntKey, Enr ch ntEnvelop](kafkaProducerConf g =
              KafkaProducerConf g(Tw terKafkaProducerConf g().requestT  out(1.m nute).conf gMap))
          else
            F nagleKafkaProducerConf g[Enr ch ntKey, Enr ch ntEnvelop](
              kafkaProducerConf g = KafkaProducerConf g()
                .requestT  out(1.m nute)),
        statsRece ver = statsRece ver,
        comm  nterval = 15.seconds
      )(Produced.`w h`(ScalaSerdes.Thr ft[Enr ch ntKey], ScalaSerdes.Thr ft[Enr ch ntEnvelop]))
    } else {
      kstream.to(OutputPart  onedTop c)(
        Produced.`w h`(ScalaSerdes.Thr ft[Enr ch ntKey], ScalaSerdes.Thr ft[Enr ch ntEnvelop]))
    }
  }

  overr de def streamsPropert es(conf g: KafkaStreamsConf g): KafkaStreamsConf g = {
    super
      .streamsPropert es(conf g)
      .consu r.group d(KafkaGroup d(Appl cat on d))
      .consu r.cl ent d(s"$Appl cat on d-consu r")
      .consu r.requestT  out(30.seconds)
      .consu r.sess onT  out(30.seconds)
      .consu r.fetchM n(1. gabyte)
      .consu r.fetchMax(5. gabyte)
      .consu r.rece veBuffer(32. gabytes)
      .consu r.maxPoll nterval(1.m nute)
      .consu r.maxPollRecords(50000)
      .producer.cl ent d(s"$Appl cat on d-producer")
      .producer.batchS ze(16.k lobytes)
      .producer.buffer moryS ze(256. gabyte)
      .producer.requestT  out(30.seconds)
      .producer.compress onType(Compress onType.LZ4)
      .producer.ackMode(AckMode.ALL)
  }
}
