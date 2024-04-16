package com.tw ter.s mclusters_v2.summ ngb rd.storm

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter. ron.ut l.Common tr c
 mport com.tw ter.scald ng.Args
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le.AltSett ng
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le.Env ron nt
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.Ent yClusterScoreReadableStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.TopKClustersForT etReadableStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.TopKT etsForClusterReadableStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.User nterested nReadableStore
 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.summ ngb rd.onl ne.opt on._
 mport com.tw ter.summ ngb rd.opt on._
 mport com.tw ter.summ ngb rd.storm.opt on.FlatMapStorm tr cs
 mport com.tw ter.summ ngb rd.storm.opt on.Sum rStorm tr cs
 mport com.tw ter.summ ngb rd.storm.Storm
 mport com.tw ter.summ ngb rd.storm.Storm tr c
 mport com.tw ter.summ ngb rd.Opt ons
 mport com.tw ter.summ ngb rd.Ta lProducer
 mport com.tw ter.summ ngb rd_ nternal.runner.common.JobNa 
 mport com.tw ter.summ ngb rd_ nternal.runner.common.SBRunConf g
 mport com.tw ter.summ ngb rd_ nternal.runner.storm.Gener cRunner
 mport com.tw ter.summ ngb rd_ nternal.runner.storm.StormConf g
 mport com.tw ter.tor nta_ nternal.spout.eventbus.Subscr ber d
 mport com.tw ter.wtf.summ ngb rd.s ces.storm.T  l neEventS ce
 mport java.lang
 mport org.apac . ron.ap .{Conf g =>  ronConf g}
 mport org.apac . ron.common.bas cs.ByteAmount
 mport org.apac .storm.{Conf g => BTConf g}
 mport scala.collect on.JavaConverters._

object T etJobRunner {
  def ma n(args: Array[Str ng]): Un  = {
    Gener cRunner(args, T etStormJob(_))
  }
}

object T etStormJob {

   mport com.tw ter.s mclusters_v2.summ ngb rd.common. mpl c s._

  def jLong(num: Long): lang.Long = java.lang.Long.valueOf(num)
  def j nt(num:  nt):  nteger = java.lang. nteger.valueOf(num)
  def apply(args: Args): StormConf g = {

    lazy val env: Str ng = args.getOrElse("env", "prod")
    lazy val zone: Str ng = args.getOrElse("dc", "atla")

    // T  only S mClusters ENV  s Alt. W ll clean up soon.
    lazy val prof le = S mClustersProf le.fetchT etJobProf le(Env ron nt(env), AltSett ng.Alt)

    lazy val favor eEventS ce = T  l neEventS ce(
      // Note: do not share t  sa  subsr ber d w h ot r jobs. Apply a new one  f needed
      Subscr ber d(prof le.t  l neEventS ceSubscr ber d)
    ).s ce

    lazy val common tr c =
      Storm tr c(new Common tr c(), Common tr c.NAME, Common tr c.POLL_ NTERVAL)
    lazy val flatMap tr cs = FlatMapStorm tr cs( erable(common tr c))
    lazy val sum r tr cs = Sum rStorm tr cs( erable(common tr c))

    lazy val ent yClusterScoreStore: Storm#Store[
      (S mClusterEnt y, FullCluster dBucket),
      ClustersW hScores
    ] = {
      Storm.store(
        Ent yClusterScoreReadableStore
          .onl ne rgeableStore(prof le.ent yClusterScorePath, prof le.serv ce dent f er(zone)))
    }

    lazy val t etTopKStore: Storm#Store[Ent yW hVers on, TopKClustersW hScores] = {
      Storm.store(
        TopKClustersForT etReadableStore
          .onl ne rgeableStore(prof le.t etTopKClustersPath, prof le.serv ce dent f er(zone)))
    }

    lazy val clusterTopKT etsStore: Storm#Store[FullCluster d, TopKT etsW hScores] = {
      Storm.store(
        TopKT etsForClusterReadableStore
          .onl ne rgeableStore(prof le.clusterTopKT etsPath, prof le.serv ce dent f er(zone)))
    }

    lazy val clusterTopKT etsL ghtStore: Opt on[
      Storm#Store[FullCluster d, TopKT etsW hScores]
    ] = {
      prof le.clusterTopKT etsL ghtPath.map { l ghtPath =>
        Storm.store(
          TopKT etsForClusterReadableStore
            .onl ne rgeableStore(l ghtPath, prof le.serv ce dent f er(zone)))
      }
    }

    lazy val user nterested nServ ce: Storm#Serv ce[Long, ClustersUser s nterested n] = {
      Storm.serv ce(
        User nterested nReadableStore.defaultStoreW hMtls(
          ManhattanKVCl entMtlsParams(prof le.serv ce dent f er(zone)),
          modelVers on = prof le.modelVers onStr
        ))
    }

    new StormConf g {

      val jobNa : JobNa  = JobNa (prof le.jobNa )

       mpl c  val job D: Job d = Job d(jobNa .toStr ng)

      /**
       * Add reg strars for ch ll ser al zat on for user-def ned types.
       */
      overr de def reg strars =
        L st(
          SBRunConf g.reg ster[S mClusterEnt y],
          SBRunConf g.reg ster[FullCluster dBucket],
          SBRunConf g.reg ster[ClustersW hScores],
          SBRunConf g.reg ster[Ent yW hVers on],
          SBRunConf g.reg ster[FullCluster d],
          SBRunConf g.reg ster[Ent yW hVers on],
          SBRunConf g.reg ster[TopKEnt  esW hScores],
          SBRunConf g.reg ster[TopKClustersW hScores],
          SBRunConf g.reg ster[TopKT etsW hScores]
        )

      /***** Job conf gurat on sett ngs *****/
      /**
       * Use vmSett ngs to conf gure t  VM
       */
      overr de def vmSett ngs: Seq[Str ng] = Seq()

      pr vate val S cePerWorker = 1
      pr vate val FlatMapPerWorker = 3
      pr vate val Sum rPerWorker = 3

      pr vate val TotalWorker = 150

      /**
       * Use transformConf g to set  ron opt ons.
       */
      overr de def transformConf g(conf g: Map[Str ng, AnyRef]): Map[Str ng, AnyRef] = {
        val  ronConf g = new  ronConf g()

        /**
        Component na s (subject to change  f   add more components, make sure to update t )
          S ce: Ta l-FlatMap-FlatMap-Sum r-FlatMap-S ce
          FlatMap: Ta l-FlatMap-FlatMap-Sum r-FlatMap, Ta l-FlatMap-FlatMap, Ta l-FlatMap-FlatMap,
          Ta l-FlatMap
          Sum r: Ta l-FlatMap-FlatMap-Sum r * 2, Ta l, Ta l.2
         */
        val s ceNa  = "Ta l-FlatMap-FlatMap-Sum r-FlatMap-S ce"
        val flatMapFlatMapSum rFlatMapNa  = "Ta l-FlatMap-FlatMap-Sum r-FlatMap"

        // 1 CPU per node, 1 for StreamMgr
        // By default, numCpus per component = totalCPUs / total number of components.
        // To add more CPUs for a spec f c component, use  ronConf g.setComponentCpu(na , numCPUs)
        // add 20% more CPUs to address back pressure  ssue
        val TotalCPU = jLong(
          (1.2 * (S cePerWorker * 1 + FlatMapPerWorker * 4 + Sum rPerWorker * 6 + 1)).ce l.to nt)
         ronConf g.setConta nerCpuRequested(TotalCPU.toDouble)

        // RAM sett ngs
        val RamPerS ceGB = 8
        val RamPerSum rFlatMap = 8
        val RamDefaultPerComponent = 4

        // T  extra 4GB  s not expl c ly ass gned to t  StreamMgr, so   gets 2GB by default, and
        // t  rema n ng 2GB  s shared among components. Keep ng t  conf gurat on for now, s nce
        //   seems stable
        val TotalRamRB =
          RamPerS ceGB * S cePerWorker * 1 +
            RamDefaultPerComponent * FlatMapPerWorker * 4 +
            RamDefaultPerComponent * Sum rPerWorker * 6 +
            4 // reserve 4GB for t  StreamMgr

        // By default, ramGB per component = totalRAM / total number of components.
        // To adjust RAMs for a spec f c component, use  ronConf g.setComponentRam(na , ramGB)
         ronConf g.setComponentRam(s ceNa , ByteAmount.fromG gabytes(RamPerS ceGB))
         ronConf g.setComponentRam(
          flatMapFlatMapSum rFlatMapNa ,
          ByteAmount.fromG gabytes(RamPerSum rFlatMap))
         ronConf g.setConta nerRamRequested(ByteAmount.fromG gabytes(TotalRamRB))

        super.transformConf g(conf g) ++ L st(
          BTConf g.TOPOLOGY_TEAM_NAME -> "cassowary",
          BTConf g.TOPOLOGY_TEAM_EMA L -> "no-reply@tw ter.com",
          BTConf g.TOPOLOGY_WORKERS -> j nt(TotalWorker),
          BTConf g.TOPOLOGY_ACKER_EXECUTORS -> j nt(0),
          BTConf g.TOPOLOGY_MESSAGE_T MEOUT_SECS -> j nt(30),
          BTConf g.TOPOLOGY_WORKER_CH LDOPTS -> L st(
            "-XX:Max taspaceS ze=256M",
            "-Djava.secur y.auth.log n.conf g=conf g/jaas.conf",
            "-Dsun.secur y.krb5.debug=true",
            "-Dcom.tw ter.eventbus.cl ent.EnableKafkaSaslTls=true",
            "-Dcom.tw ter.eventbus.cl ent.zoneNa =" + zone
          ).mkStr ng(" "),
          "storm.job.un que d" -> job D.get
        ) ++  ronConf g.asScala.toMap
      }

      /**
       * Use getNa dOpt ons to set Summ ngb rd runt   opt ons
       * T  l st of ava lable opt ons: com.tw ter.summ ngb rd.onl ne.opt on
       */
      overr de def getNa dOpt ons: Map[Str ng, Opt ons] = Map(
        "DEFAULT" -> Opt ons()
          .set(FlatMapParallel sm(TotalWorker * FlatMapPerWorker))
          .set(S ceParallel sm(TotalWorker))
          .set(Sum rBatchMult pl er(1000))
          .set(Cac S ze(10000))
          .set(flatMap tr cs)
          .set(sum r tr cs),
        T etJob.NodeNa .T etClusterUpdatedScoresFlatMapNodeNa  -> Opt ons()
          .set(FlatMapParallel sm(TotalWorker * FlatMapPerWorker)),
        T etJob.NodeNa .T etClusterScoreSum rNodeNa  -> Opt ons()
        // Most expens ve step. Double t  capac y.
          .set(Sum rParallel sm(TotalWorker * Sum rPerWorker * 4))
          .set(FlushFrequency(30.seconds)),
        T etJob.NodeNa .ClusterTopKT etsNodeNa  -> Opt ons()
          .set(Sum rParallel sm(TotalWorker * Sum rPerWorker))
          .set(FlushFrequency(30.seconds)),
        T etJob.NodeNa .ClusterTopKT etsL ghtNodeNa  -> Opt ons()
          .set(Sum rParallel sm(TotalWorker * Sum rPerWorker))
          .set(FlushFrequency(30.seconds)),
        T etJob.NodeNa .T etTopKNodeNa  -> Opt ons()
          .set(Sum rParallel sm(TotalWorker * Sum rPerWorker))
          .set(FlushFrequency(30.seconds))
      )

      /** Requ red job generat on call for y  job, def ned  n Job.scala */
      overr de def graph: Ta lProducer[Storm, Any] = T etJob.generate[Storm](
        prof le,
        favor eEventS ce,
        user nterested nServ ce,
        ent yClusterScoreStore,
        t etTopKStore,
        clusterTopKT etsStore,
        clusterTopKT etsL ghtStore
      )
    }
  }
}
