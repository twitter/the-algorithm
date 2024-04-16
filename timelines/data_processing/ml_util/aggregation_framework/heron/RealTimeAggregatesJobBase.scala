package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron

 mport com.tw ter.algeb rd.Mono d
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.b ject on.thr ft.CompactThr ftCodec
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.EmptyServ ce dent f er
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. ron.ut l.Common tr c
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.scald ng.Args
 mport com.tw ter.storehaus.algebra. rgeableStore
 mport com.tw ter.storehaus.algebra.StoreAlgebra._
 mport com.tw ter.storehaus_ nternal. mcac . mcac 
 mport com.tw ter.storehaus_ nternal.store.Comb nedStore
 mport com.tw ter.storehaus_ nternal.store.Repl cat ngWr ableStore
 mport com.tw ter.summ ngb rd.batch.Batch D
 mport com.tw ter.summ ngb rd.batch.Batc r
 mport com.tw ter.summ ngb rd.onl ne. rgeableStoreFactory
 mport com.tw ter.summ ngb rd.onl ne.opt on._
 mport com.tw ter.summ ngb rd.opt on.Cac S ze
 mport com.tw ter.summ ngb rd.opt on.Job d
 mport com.tw ter.summ ngb rd.storm.opt on.FlatMapStorm tr cs
 mport com.tw ter.summ ngb rd.storm.opt on.Sum rStorm tr cs
 mport com.tw ter.summ ngb rd.storm.Storm
 mport com.tw ter.summ ngb rd.storm.Storm tr c
 mport com.tw ter.summ ngb rd.Opt ons
 mport com.tw ter.summ ngb rd._
 mport com.tw ter.summ ngb rd_ nternal.runner.common.CapT cket
 mport com.tw ter.summ ngb rd_ nternal.runner.common.JobNa 
 mport com.tw ter.summ ngb rd_ nternal.runner.common.TeamEma l
 mport com.tw ter.summ ngb rd_ nternal.runner.common.TeamNa 
 mport com.tw ter.summ ngb rd_ nternal.runner.storm.Product onStormConf g
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.job.AggregatesV2Job
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.job.AggregatesV2Job
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.job.DataRecordFeatureCounter
 mport org.apac . ron.ap .{Conf g =>  ronConf g}
 mport org.apac . ron.common.bas cs.ByteAmount
 mport org.apac .storm.Conf g
 mport scala.collect on.JavaConverters._

object RealT  AggregatesJobBase {
  lazy val common tr c: Storm tr c[Common tr c] =
    Storm tr c(new Common tr c(), Common tr c.NAME, Common tr c.POLL_ NTERVAL)
  lazy val flatMap tr cs: FlatMapStorm tr cs = FlatMapStorm tr cs( erable(common tr c))
  lazy val sum r tr cs: Sum rStorm tr cs = Sum rStorm tr cs( erable(common tr c))
}

tra  RealT  AggregatesJobBase extends Ser al zable {
   mport RealT  AggregatesJobBase._
   mport com.tw ter.summ ngb rd_ nternal.b ject on.BatchPa r mpl c s._

  def statsRece ver: StatsRece ver

  def aggregatesToCompute: Set[TypedAggregateGroup[_]]

  def jobConf gs: RealT  AggregatesJobConf gs

   mpl c  lazy val dataRecordCodec:  nject on[DataRecord, Array[Byte]] =
    CompactThr ftCodec[DataRecord]
   mpl c  lazy val mono d: Mono d[DataRecord] = DataRecordAggregat onMono d(aggregatesToCompute)
   mpl c  lazy val aggregat onKey nject on:  nject on[Aggregat onKey, Array[Byte]] =
    Aggregat onKey nject on

  val clusters: Set[Str ng] = Set("atla", "pdxa")

  def bu ldAggregateStoreToStorm(
     sProd: Boolean,
    serv ce dent f er: Serv ce dent f er,
    jobConf g: RealT  AggregatesJobConf g
  ): (AggregateStore => Opt on[Storm#Store[Aggregat onKey, DataRecord]]) = {
    (store: AggregateStore) =>
      store match {
        case rtaStore: RealT  AggregateStore  f rtaStore. sProd ==  sProd => {
          lazy val pr maryStore:  rgeableStore[(Aggregat onKey, Batch D), DataRecord] =
             mcac .get mcac Store[(Aggregat onKey, Batch D), DataRecord](
              rtaStore.onl ne(serv ce dent f er))

          lazy val  rgeableStore:  rgeableStore[(Aggregat onKey, Batch D), DataRecord] =
             f (jobConf g.enableUserRe ndex ngN ghthawkBtreeStore
              || jobConf g.enableUserRe ndex ngN ghthawkHashStore) {
              val re ndex ngN ghthawkBtreeWr ableDataRecordStoreL st =
                 f (jobConf g.enableUserRe ndex ngN ghthawkBtreeStore) {
                  lazy val cac Cl entN ghthawkConf g =
                    jobConf g.userRe ndex ngN ghthawkBtreeStoreConf g.onl ne(serv ce dent f er)
                  L st(
                    UserRe ndex ngN ghthawkWr ableDataRecordStore.getBtreeStore(
                      n ghthawkCac Conf g = cac Cl entN ghthawkConf g,
                      // Choose a reasonably large target s ze as t  w ll be equ valent to t  number of un que (user, t  stamp)
                      // keys that are returned on read on t  pKey, and   may have dupl cate authors and assoc ated records.
                      targetS ze = 512,
                      statsRece ver = statsRece ver,
                      // Assum ng tr ms are relat vely expens ve, choose a tr mRate that's not as aggress ve.  n t  case   tr m on
                      // 10% of all wr es.
                      tr mRate = 0.1
                    ))
                } else { N l }
              val re ndex ngN ghthawkHashWr ableDataRecordStoreL st =
                 f (jobConf g.enableUserRe ndex ngN ghthawkHashStore) {
                  lazy val cac Cl entN ghthawkConf g =
                    jobConf g.userRe ndex ngN ghthawkHashStoreConf g.onl ne(serv ce dent f er)
                  L st(
                    UserRe ndex ngN ghthawkWr ableDataRecordStore.getHashStore(
                      n ghthawkCac Conf g = cac Cl entN ghthawkConf g,
                      // Choose a reasonably large target s ze as t  w ll be equ valent to t  number of un que (user, t  stamp)
                      // keys that are returned on read on t  pKey, and   may have dupl cate authors and assoc ated records.
                      targetS ze = 512,
                      statsRece ver = statsRece ver,
                      // Assum ng tr ms are relat vely expens ve, choose a tr mRate that's not as aggress ve.  n t  case   tr m on
                      // 10% of all wr es.
                      tr mRate = 0.1
                    ))
                } else { N l }

              lazy val repl cat ngWr ableStore = new Repl cat ngWr ableStore(
                stores = L st(pr maryStore) ++ re ndex ngN ghthawkBtreeWr ableDataRecordStoreL st
                  ++ re ndex ngN ghthawkHashWr ableDataRecordStoreL st
              )

              lazy val comb nedStoreW hRe ndex ng = new Comb nedStore(
                read = pr maryStore,
                wr e = repl cat ngWr ableStore
              )

              comb nedStoreW hRe ndex ng.to rgeable
            } else {
              pr maryStore
            }

          lazy val storeFactory:  rgeableStoreFactory[(Aggregat onKey, Batch D), DataRecord] =
            Storm.store( rgeableStore)(Batc r.un )
          So (storeFactory)
        }
        case _ => None
      }
  }

  def bu ldDataRecordS ceToStorm(
    jobConf g: RealT  AggregatesJobConf g
  ): (AggregateS ce => Opt on[Producer[Storm, DataRecord]]) = { (s ce: AggregateS ce) =>
    {
      s ce match {
        case stormAggregateS ce: StormAggregateS ce =>
          So (stormAggregateS ce.bu ld(statsRece ver, jobConf g))
        case _ => None
      }
    }
  }

  def apply(args: Args): Product onStormConf g = {
    lazy val  sProd = args.boolean("product on")
    lazy val cluster = args.getOrElse("cluster", "")
    lazy val  sDebug = args.boolean("debug")
    lazy val role = args.getOrElse("role", "")
    lazy val serv ce =
      args.getOrElse(
        "serv ce_na ",
        ""
      ) // don't use t  argu nt serv ce, wh ch  s a reserved  ron argu nt
    lazy val env ron nt =  f ( sProd) "prod" else "devel"
    lazy val s2sEnabled = args.boolean("s2s")
    lazy val keyedByUserEnabled = args.boolean("keyed_by_user")
    lazy val keyedByAuthorEnabled = args.boolean("keyed_by_author")

    requ re(clusters.conta ns(cluster))
     f (s2sEnabled) {
      requ re(role.length() > 0)
      requ re(serv ce.length() > 0)
    }

    lazy val serv ce dent f er =  f (s2sEnabled) {
      Serv ce dent f er(
        role = role,
        serv ce = serv ce,
        env ron nt = env ron nt,
        zone = cluster
      )
    } else EmptyServ ce dent f er

    lazy val jobConf g = {
      val jobConf g =  f ( sProd) jobConf gs.Prod else jobConf gs.Devel
      jobConf g.copy(
        serv ce dent f er = serv ce dent f er,
        keyedByUserEnabled = keyedByUserEnabled,
        keyedByAuthorEnabled = keyedByAuthorEnabled)
    }

    lazy val dataRecordS ceToStorm = bu ldDataRecordS ceToStorm(jobConf g)
    lazy val aggregateStoreToStorm =
      bu ldAggregateStoreToStorm( sProd, serv ce dent f er, jobConf g)

    lazy val JaasConf gFlag = "-Djava.secur y.auth.log n.conf g=res ces/jaas.conf"
    lazy val JaasDebugFlag = "-Dsun.secur y.krb5.debug=true"
    lazy val JaasConf gStr ng =
       f ( sDebug) { "%s %s".format(JaasConf gFlag, JaasDebugFlag) }
      else JaasConf gFlag

    new Product onStormConf g {
       mpl c  val job d: Job d = Job d(jobConf g.na )
      overr de val jobNa  = JobNa (jobConf g.na )
      overr de val teamNa  = TeamNa (jobConf g.teamNa )
      overr de val teamEma l = TeamEma l(jobConf g.teamEma l)
      overr de val capT cket = CapT cket("n/a")

      val conf gure ronJvmSett ngs = {
        val  ronJvmOpt ons = new java.ut l.HashMap[Str ng, AnyRef]()
        jobConf g.componentToRamG gaBytesMap.foreach {
          case (component, g gabytes) =>
             ronConf g.setComponentRam(
               ronJvmOpt ons,
              component,
              ByteAmount.fromG gabytes(g gabytes))
        }

         ronConf g.setConta nerRamRequested(
           ronJvmOpt ons,
          ByteAmount.fromG gabytes(jobConf g.conta nerRamG gaBytes)
        )

        jobConf g.componentsToKerber ze.foreach { component =>
           ronConf g.setComponentJvmOpt ons(
             ronJvmOpt ons,
            component,
            JaasConf gStr ng
          )
        }

        jobConf g.componentTo taSpaceS zeMap.foreach {
          case (component,  taspaceS ze) =>
             ronConf g.setComponentJvmOpt ons(
               ronJvmOpt ons,
              component,
               taspaceS ze
            )
        }

         ronJvmOpt ons.asScala.toMap ++ AggregatesV2Job
          .aggregateNa s(aggregatesToCompute).map {
            case (pref x, aggNa s) => (s"extras.aggregateNa s.${pref x}", aggNa s)
          }
      }

      overr de def transformConf g(m: Map[Str ng, AnyRef]): Map[Str ng, AnyRef] = {
        super.transformConf g(m) ++ L st(
          /**
           * D sable ack ng by sett ng acker executors to 0. Tuples that co  off t 
           * spout w ll be  m d ately acked wh ch effect vely d sables retr es on tuple
           * fa lures. T  should  lp topology throughput/ava lab l y by relax ng cons stency.
           */
          Conf g.TOPOLOGY_ACKER_EXECUTORS ->  nt2 nteger(0),
          Conf g.TOPOLOGY_WORKERS ->  nt2 nteger(jobConf g.topologyWorkers),
           ronConf g.TOPOLOGY_CONTA NER_CPU_REQUESTED ->  nt2 nteger(8),
           ronConf g.TOPOLOGY_DROPTUPLES_UPON_BACKPRESSURE -> java.lang.Boolean.valueOf(true),
           ronConf g.TOPOLOGY_WORKER_CH LDOPTS -> L st(
            JaasConf gStr ng,
            s"-Dcom.tw ter.eventbus.cl ent.zoneNa =${cluster}",
            "-Dcom.tw ter.eventbus.cl ent.EnableKafkaSaslTls=true"
          ).mkStr ng(" "),
          "storm.job.un que d" -> job d.get
        ) ++ conf gure ronJvmSett ngs

      }

      overr de lazy val getNa dOpt ons: Map[Str ng, Opt ons] = jobConf g.topologyNa dOpt ons ++
        Map(
          "DEFAULT" -> Opt ons()
            .set(flatMap tr cs)
            .set(sum r tr cs)
            .set(MaxWa  ngFutures(1000))
            .set(FlushFrequency(30.seconds))
            .set(UseAsyncCac (true))
            .set(AsyncPoolS ze(4))
            .set(S ceParallel sm(jobConf g.s ceCount))
            .set(Sum rBatchMult pl er(1000)),
          "FLATMAP" -> Opt ons()
            .set(FlatMapParallel sm(jobConf g.flatMapCount))
            .set(Cac S ze(0)),
          "SUMMER" -> Opt ons()
            .set(Sum rParallel sm(jobConf g.sum rCount))
            /**
             * Sets number of tuples a Sum r awa s before aggregat on. Set h g r
             *  f   need to lo r qps to  mcac  at t  expense of  ntroduc ng
             * so  (stable) latency.
             */
            .set(Cac S ze(jobConf g.cac S ze))
        )

      val featureCounters: Seq[DataRecordFeatureCounter] =
        Seq(DataRecordFeatureCounter.any(Counter(Group("feature_counter"), Na ("num_records"))))

      overr de def graph: Ta lProducer[Storm, Any] = AggregatesV2Job.generateJobGraph[Storm](
        aggregateSet = aggregatesToCompute,
        aggregateS ceToSumm ngb rd = dataRecordS ceToStorm,
        aggregateStoreToSumm ngb rd = aggregateStoreToStorm,
        featureCounters = featureCounters
      )
    }
  }
}
