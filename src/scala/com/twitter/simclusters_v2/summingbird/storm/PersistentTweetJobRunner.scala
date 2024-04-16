package com.tw ter.s mclusters_v2.summ ngb rd.storm

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter. rm .store.common.ObservedCac dReadableStore
 mport com.tw ter.scald ng.Args
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.Mono ds.Pers stentS mClustersEmbedd ngLongestL2NormMono d
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le.AltSett ng
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le.Env ron nt
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.Cl entConf gs
 mport com.tw ter.s mclusters_v2.summ ngb rd.common. mpl c s
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.Pers stentT etEmbedd ngStore.Pers stentT etEmbedd ng d
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.Pers stentT etEmbedd ngStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.TopKClustersForT etKeyReadableStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.T etKey
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.T etStatusCountsStore
 mport com.tw ter.s mclusters_v2.thr ftscala.Pers stentS mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.thr ftscala.{S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng}
 mport com.tw ter.storehaus.FutureCollector
 mport com.tw ter.summ ngb rd.onl ne.opt on._
 mport com.tw ter.summ ngb rd.opt on._
 mport com.tw ter.summ ngb rd.storm.Storm
 mport com.tw ter.summ ngb rd.Opt ons
 mport com.tw ter.summ ngb rd.Ta lProducer
 mport com.tw ter.summ ngb rd_ nternal.runner.common.JobNa 
 mport com.tw ter.summ ngb rd_ nternal.runner.common.SBRunConf g
 mport com.tw ter.summ ngb rd_ nternal.runner.storm.Gener cRunner
 mport com.tw ter.summ ngb rd_ nternal.runner.storm.StormConf g
 mport com.tw ter.tor nta_ nternal.spout.eventbus.Subscr ber d
 mport com.tw ter.t etyp e.thr ftscala.StatusCounts
 mport com.tw ter.wtf.summ ngb rd.s ces.storm.T  l neEventS ce
 mport java.lang
 mport java.ut l.{HashMap => JMap}
 mport org.apac . ron.ap .{Conf g =>  ronConf g}
 mport org.apac .storm.{Conf g => BTConf g}

object Pers stentT etJobRunner {
  def ma n(args: Array[Str ng]): Un  = {
    Gener cRunner(args, Pers stentT etStormJob(_))
  }
}

object Pers stentT etStormJob {

   mport com.tw ter.s mclusters_v2.summ ngb rd.common. mpl c s._

  def jLong(num: Long): lang.Long = java.lang.Long.valueOf(num)
  def j nt(num:  nt):  nteger = java.lang. nteger.valueOf(num)
  def jFloat(num: Float): lang.Float = java.lang.Float.valueOf(num)

  def apply(args: Args): StormConf g = {

    lazy val env: Str ng = args.getOrElse("env", "prod")
    lazy val zone: Str ng = args.getOrElse("dc", "atla")
    lazy val alt: Str ng = args.getOrElse("alt", default = "normal")

    lazy val prof le =
      S mClustersProf le.fetchPers stentJobProf le(Env ron nt(env), AltSett ng(alt))

    lazy val stratoCl ent = Cl entConf gs.stratoCl ent(prof le.serv ce dent f er(zone))

    lazy val favor eEventS ce = T  l neEventS ce(
      // Note: do not share t  sa  subsr ber d w h ot r jobs. Apply a new one  f needed
      Subscr ber d(prof le.t  l neEventS ceSubscr ber d)
    ).kafkaS ce

    lazy val pers stentT etEmbedd ngStore =
      Pers stentT etEmbedd ngStore
        .pers stentT etEmbedd ngStore(stratoCl ent, prof le.pers stentT etStratoPath)

    lazy val pers stentT etEmbedd ngStoreW hLatestAggregat on: Storm#Store[
      Pers stentT etEmbedd ng d,
      Pers stentS mClustersEmbedd ng
    ] = {
       mport com.tw ter.storehaus.algebra.StoreAlgebra._

      lazy val  rgeableStore =
        pers stentT etEmbedd ngStore.to rgeable(
          mon =  mpl c s.pers stentS mClustersEmbedd ngMono d,
          fc =  mpl c ly[FutureCollector])

      Storm.onl neOnlyStore( rgeableStore)
    }

    lazy val pers stentT etEmbedd ngStoreW hLongestL2NormAggregat on: Storm#Store[
      Pers stentT etEmbedd ng d,
      Pers stentS mClustersEmbedd ng
    ] = {
       mport com.tw ter.storehaus.algebra.StoreAlgebra._

      val longestL2NormMono d = new Pers stentS mClustersEmbedd ngLongestL2NormMono d()
      lazy val  rgeableStore =
        pers stentT etEmbedd ngStore.to rgeable(
          mon = longestL2NormMono d,
          fc =  mpl c ly[FutureCollector])

      Storm.onl neOnlyStore( rgeableStore)
    }

    lazy val t etStatusCountsServ ce: Storm#Serv ce[T et d, StatusCounts] =
      Storm.serv ce(
        ObservedCac dReadableStore.from[T et d, StatusCounts](
          T etStatusCountsStore.t etStatusCountsStore(stratoCl ent, "t etyp e/core.T et"),
          ttl = 1.m nute,
          maxKeys = 10000, // 10K  s enough for  ron Job.
          cac Na  = "t et_status_count",
          w ndowS ze = 10000L
        )(NullStatsRece ver)
      )

    lazy val t etEmbedd ngServ ce: Storm#Serv ce[T et d, Thr ftS mClustersEmbedd ng] =
      Storm.serv ce(
        TopKClustersForT etKeyReadableStore
          .overr deL m DefaultStore(50, prof le.serv ce dent f er(zone))
          .composeKeyMapp ng { t et d: T et d =>
            T etKey(t et d, prof le.modelVers onStr, prof le.coreEmbedd ngType)
          }.mapValues { value => S mClustersEmbedd ng(value).toThr ft })

    new StormConf g {

      val jobNa : JobNa  = JobNa (prof le.jobNa )

       mpl c  val job D: Job d = Job d(jobNa .toStr ng)

      /**
       * Add reg strars for ch ll ser al zat on for user-def ned types.
       */
      overr de def reg strars =
        L st(
          SBRunConf g.reg ster[StatusCounts],
          SBRunConf g.reg ster[Thr ftS mClustersEmbedd ng],
          SBRunConf g.reg ster[Pers stentS mClustersEmbedd ng]
        )

      /***** Job conf gurat on sett ngs *****/
      /**
       * Use vmSett ngs to conf gure t  VM
       */
      overr de def vmSett ngs: Seq[Str ng] = Seq()

      pr vate val S cePerWorker = 1
      pr vate val FlatMapPerWorker = 1
      pr vate val Sum rPerWorker = 1

      pr vate val TotalWorker = 60

      /**
       * Use transformConf g to set  ron opt ons.
       */
      overr de def transformConf g(conf g: Map[Str ng, AnyRef]): Map[Str ng, AnyRef] = {

        val  ronJvmOpt ons = new JMap[Str ng, AnyRef]()

        val  taspaceS ze = jLong(256L * 1024 * 1024)
        val Default apS ze = jLong(2L * 1024 * 1024 * 1024)
        val H gh apS ze = jLong(4L * 1024 * 1024 * 1024)

        val TotalCPU = jLong(
          S cePerWorker * 1 + FlatMapPerWorker * 4 + Sum rPerWorker * 3 + 1
        )

        // reserve 4GB for t  StreamMgr
        val TotalRam = jLong(
          Default apS ze * (S cePerWorker * 1 + FlatMapPerWorker * 4)
            + H gh apS ze * Sum rPerWorker * 3
            +  taspaceS ze * 8 // Appl es to all workers
            + 4L * 1024 * 1024 * 1024)

        // T se sett ngs  lp prevent GC  ssues  n t  most  mory  ntens ve steps of t  job by
        // ded cat ng more  mory to t  new gen  ap des gnated by t  -Xmn flag.
        Map(
          "Ta l" -> H gh apS ze
        ).foreach {
          case (stage,  ap) =>
             ronConf g.setComponentJvmOpt ons(
               ronJvmOpt ons,
              stage,
              s"-Xmx$ ap -Xms$ ap -Xmn${ ap / 2}"
            )
        }

        super.transformConf g(conf g) ++ L st(
          BTConf g.TOPOLOGY_TEAM_NAME -> "cassowary",
          BTConf g.TOPOLOGY_TEAM_EMA L -> "no-reply@tw ter.com",
          BTConf g.TOPOLOGY_WORKERS -> j nt(TotalWorker),
          BTConf g.TOPOLOGY_ACKER_EXECUTORS -> j nt(0),
          BTConf g.TOPOLOGY_MESSAGE_T MEOUT_SECS -> j nt(30),
          BTConf g.TOPOLOGY_WORKER_CH LDOPTS -> L st(
            "-Djava.secur y.auth.log n.conf g=conf g/jaas.conf",
            "-Dsun.secur y.krb5.debug=true",
            "-Dcom.tw ter.eventbus.cl ent.EnableKafkaSaslTls=true",
            "-Dcom.tw ter.eventbus.cl ent.zoneNa =" + zone,
            s"-XX:Max taspaceS ze=$ taspaceS ze"
          ).mkStr ng(" "),
           ronConf g.TOPOLOGY_CONTA NER_CPU_REQUESTED -> TotalCPU,
           ronConf g.TOPOLOGY_CONTA NER_RAM_REQUESTED -> TotalRam,
          "storm.job.un que d" -> job D.get
        )
      }

      /**
       * Use getNa dOpt ons to set Summ ngb rd runt   opt ons
       * T  l st of ava lable opt ons: com.tw ter.summ ngb rd.onl ne.opt on
       */
      overr de def getNa dOpt ons: Map[Str ng, Opt ons] = Map(
        "DEFAULT" -> Opt ons()
          .set(Sum rParallel sm(TotalWorker * Sum rPerWorker))
          .set(FlatMapParallel sm(TotalWorker * FlatMapPerWorker))
          .set(S ceParallel sm(TotalWorker * S cePerWorker))
          .set(Cac S ze(10000))
          .set(FlushFrequency(30.seconds))
      )

      /** Requ red job generat on call for y  job, def ned  n Job.scala */
      overr de def graph: Ta lProducer[Storm, Any] = Pers stentT etJob.generate[Storm](
        favor eEventS ce,
        t etStatusCountsServ ce,
        t etEmbedd ngServ ce,
        pers stentT etEmbedd ngStoreW hLatestAggregat on,
        pers stentT etEmbedd ngStoreW hLongestL2NormAggregat on
      )
    }
  }
}
