package com.tw ter.ann.serv ce.loadtest

 mport com.tw ter.ann.annoy.AnnoyCommon
 mport com.tw ter.ann.annoy.AnnoyRunt  Params
 mport com.tw ter.ann.annoy.TypedAnnoy ndex
 mport com.tw ter.ann.common._
 mport com.tw ter.ann.common.thr ftscala.{D stance => Serv ceD stance}
 mport com.tw ter.ann.common.thr ftscala.{Runt  Params => Serv ceRunt  Params}
 mport com.tw ter.ann.fa ss.Fa ssCommon
 mport com.tw ter.ann.fa ss.Fa ssParams
 mport com.tw ter.ann.hnsw.HnswCommon
 mport com.tw ter.ann.hnsw.HnswParams
 mport com.tw ter.ann.hnsw.TypedHnsw ndex
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.cortex.ml.embedd ngs.common.Ent yK nd
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.f natra.mtls.modules.Serv ce dent f erModule
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.ut l._
 mport java.ut l.concurrent.T  Un 

/**
 * To bu ld and upload:
 *  $ ./bazel bundle ann/src/ma n/scala/com/tw ter/ann/serv ce/loadtest:b n --bundle-jvm-arch ve=z p
 *  $ packer add_vers on --cluster=smf1 $USER ann-loadtest d st/ann-loadtest.z p
 */
object AnnLoadTestMa n extends Tw terServer {
  pr vate[t ] val algo =
    flag[Str ng]("algo", "load test server types: [annoy/hnsw]")
  pr vate[t ] val targetQPS =
    flag[ nt]("qps", "target QPS for load test")
  pr vate[t ] val query dType =
    flag[Str ng](
      "query_ d_type",
      "query  d type for load test: [long/str ng/ nt/user/t et/word/url/tfw d]")
  pr vate[t ] val  ndex dType =
    flag[Str ng](
      " ndex_ d_type",
      " ndex  d type for load test: [long/str ng/ nt/user/t et/word/url/tfw d]")
  pr vate[t ] val  tr c =
    flag[Str ng](" tr c", " tr c type for load test: [Cos ne/L2/ nnerProduct]")
  pr vate[t ] val durat onSec =
    flag[ nt]("durat on_sec", "durat on for t  load test  n sec")
  pr vate[t ] val numberOfNe ghbors =
    flag[Seq[ nt]]("number_of_ne ghbors", Seq(), "number of ne ghbors")
  pr vate[t ] val d  ns on = flag[ nt]("embedd ng_d  ns on", "d  ns on of embedd ngs")
  pr vate[t ] val querySetD r =
    flag[Str ng]("query_set_d r", "", "D rectory conta n ng t  quer es")
  pr vate[t ] val  ndexSetD r =
    flag[Str ng](
      " ndex_set_d r",
      "",
      "D rectory conta n ng t  embedd ngs to be  ndexed"
    )
  pr vate[t ] val truthSetD r =
    flag[Str ng]("truth_set_d r", "", "D rectory conta n ng t  truth data")
  pr vate[t ] val loadTestType =
    flag[Str ng]("loadtest_type", "Load test type [server/local]")
  pr vate[t ] val serv ceDest nat on =
    flag[Str ng]("serv ce_dest nat on", "w ly address of remote query serv ce")
  pr vate[t ] val concurrencyLevel =
    flag[ nt]("concurrency_level", 8, "number of concurrent operat ons on t   ndex")

  // Quer es w h random embedd ngs
  pr vate[t ] val w hRandomQuer es =
    flag[Boolean]("w h_random_quer es", false, "query w h random embedd ngs")
  pr vate[t ] val randomQuer esCount =
    flag[ nt]("random_quer es_count", 50000, "total random quer es")
  pr vate[t ] val randomEmbedd ngM nValue =
    flag[Float]("random_embedd ng_m n_value", -1.0f, "M n value of random embedd ngs")
  pr vate[t ] val randomEmbedd ngMaxValue =
    flag[Float]("random_embedd ng_max_value", 1.0f, "Max value of random embedd ngs")

  // para ters for annoy
  pr vate[t ] val numOfNodesToExplore =
    flag[Seq[ nt]]("annoy_num_of_nodes_to_explore", Seq(), "number of nodes to explore")
  pr vate[t ] val numOfTrees =
    flag[ nt]("annoy_num_trees", 0, "number of trees to bu ld")

  // para ters for HNSW
  pr vate[t ] val efConstruct on = flag[ nt]("hnsw_ef_construct on", "ef for Hnsw construct on")
  pr vate[t ] val ef = flag[Seq[ nt]]("hnsw_ef", Seq(), "ef for Hnsw query")
  pr vate[t ] val maxM = flag[ nt]("hnsw_max_m", "maxM for Hnsw")

  // FA SS
  pr vate[t ] val nprobe = flag[Seq[ nt]]("fa ss_nprobe", Seq(), "nprobe for fa ss query")
  pr vate[t ] val quant zerEf =
    flag[Seq[ nt]]("fa ss_quant zerEf", Seq(0), "quant zerEf for fa ss query")
  pr vate[t ] val quant zerKfactorRF =
    flag[Seq[ nt]]("fa ss_quant zerKfactorRF", Seq(0), "quant zerEf for fa ss query")
  pr vate[t ] val quant zerNprobe =
    flag[Seq[ nt]]("fa ss_quant zerNprobe", Seq(0), "quant zerNprobe for fa ss query")
  pr vate[t ] val ht =
    flag[Seq[ nt]]("fa ss_ht", Seq(0), "ht for fa ss query")

   mpl c  val t  r: T  r = DefaultT  r

  overr de def start(): Un  = {
    logger. nfo("Start ng load test..")
    logger. nfo(flag.getAll().mkStr ng("\t"))

    assert(numberOfNe ghbors().nonEmpty, "number_of_ne ghbors not def ned")
    assert(d  ns on() > 0, s" nval d d  ns on ${d  ns on()}")

    val  n moryBu ldRecorder = new  n moryLoadTestBu ldRecorder

    val queryableFuture = bu ldQueryable( n moryBu ldRecorder)
    val queryConf g = getQueryRunt  Conf g
    val result = queryableFuture.flatMap { queryable =>
      performQuer es(queryable, queryConf g, getQuer es)
    }

    Awa .result(result)
    System.out.pr ntln(s"Target QPS: ${targetQPS()}")
    System.out.pr ntln(s"Durat on per test: ${durat onSec()}")
    System.out.pr ntln(s"Concurrency Level: ${concurrencyLevel()}")

    LoadTestUt ls
      .pr ntResults( n moryBu ldRecorder, queryConf g)
      .foreach(System.out.pr ntln)

    Awa .result(close())
    System.ex (0)
  }

  pr vate[t ] def getQuer es[Q,  ]: Seq[Query[ ]] = {
     f (w hRandomQuer es()) {
      assert(
        truthSetD r(). sEmpty,
        "Cannot use truth set w n query w h random embedd ngs enabled"
      )
      val quer es = LoadTestUt ls.getRandomQuerySet(
        d  ns on(),
        randomQuer esCount(),
        randomEmbedd ngM nValue(),
        randomEmbedd ngMaxValue()
      )

      quer es.map(Query[ ](_))
    } else {
      assert(querySetD r().nonEmpty, "Query set path  s empty")
      assert(query dType().nonEmpty, "Query  d type  s empty")
      val quer es = LoadTestUt ls.getEmbedd ngsSet[Q](querySetD r(), query dType())

       f (truthSetD r().nonEmpty) {
        // Jo n t  quer es w h truth set data.
        assert( ndex dType().nonEmpty, " ndex  d type  s empty")
        val truthSetMap =
          LoadTestUt ls.getTruthSetMap[Q,  ](truthSetD r(), query dType(),  ndex dType())
        quer es.map(ent y => Query[ ](ent y.embedd ng, truthSetMap(ent y. d)))
      } else {
        quer es.map(ent y => Query[ ](ent y.embedd ng))
      }
    }
  }

  pr vate[t ] def getQueryRunt  Conf g[
    T,
    P <: Runt  Params
  ]: Seq[QueryT  Conf gurat on[T, P]] = {
    val queryT  Conf g = algo() match {
      case "annoy" =>
        assert(numOfNodesToExplore().nonEmpty, "Must spec fy t  num_of_nodes_to_explore")
        logger. nfo(s"Query ng annoy  ndex w h num_of_nodes_to_explore ${numOfNodesToExplore()}")
        for {
          numNodes <- numOfNodesToExplore()
          numOfNe ghbors <- numberOfNe ghbors()
        } y eld {
          bu ldQueryT  Conf g[T, AnnoyRunt  Params](
            numOfNe ghbors,
            AnnoyRunt  Params(So (numNodes)),
            Map(
              "numNodes" -> numNodes.toStr ng,
              "numberOfNe ghbors" -> numOfNe ghbors.toStr ng
            )
          ).as nstanceOf[QueryT  Conf gurat on[T, P]]
        }
      case "hnsw" =>
        assert(ef().nonEmpty, "Must spec fy ef")
        logger. nfo(s"Query ng hnsw  ndex w h ef ${ef()}")
        for {
          ef <- ef()
          numOfNe ghbors <- numberOfNe ghbors()
        } y eld {
          bu ldQueryT  Conf g[T, HnswParams](
            numOfNe ghbors,
            HnswParams(ef),
            Map(
              "efConstruct on" -> ef.toStr ng,
              "numberOfNe ghbors" -> numOfNe ghbors.toStr ng
            )
          ).as nstanceOf[QueryT  Conf gurat on[T, P]]
        }
      case "fa ss" =>
        assert(nprobe().nonEmpty, "Must spec fy nprobe")
        def toNonZeroOpt onal(x:  nt): Opt on[ nt] =  f (x != 0) So (x) else None
        for {
          numOfNe ghbors <- numberOfNe ghbors()
          runNProbe <- nprobe()
          runQEF <- quant zerEf()
          runKFactorEF <- quant zerKfactorRF()
          runQNProbe <- quant zerNprobe()
          runHT <- ht()
        } y eld {
          val params = Fa ssParams(
            So (runNProbe),
            toNonZeroOpt onal(runQEF),
            toNonZeroOpt onal(runKFactorEF),
            toNonZeroOpt onal(runQNProbe),
            toNonZeroOpt onal(runHT))
          bu ldQueryT  Conf g[T, Fa ssParams](
            numOfNe ghbors,
            params,
            Map(
              "nprobe" -> params.nprobe.toStr ng,
              "quant zer_efSearch" -> params.quant zerEf.toStr ng,
              "quant zer_k_factor_rf" -> params.quant zerKFactorRF.toStr ng,
              "quant zer_nprobe" -> params.quant zerNprobe.toStr ng,
              "ht" -> params.ht.toStr ng,
              "numberOfNe ghbors" -> numOfNe ghbors.toStr ng,
            )
          ).as nstanceOf[QueryT  Conf gurat on[T, P]]
        }
      case _ => throw new  llegalArgu ntExcept on(s"server type: $algo  s not supported yet")
    }

    queryT  Conf g
  }

  pr vate def bu ldQueryable[T, P <: Runt  Params, D <: D stance[D]](
     n moryBu ldRecorder:  n moryLoadTestBu ldRecorder
  ): Future[Queryable[T, P, D]] = {
    val queryable = loadTestType() match {
      case "remote" => {
        assert(serv ceDest nat on().nonEmpty, "Serv ce dest nat on not def ned")
        logger. nfo(s"Runn ng load test w h remote serv ce ${serv ceDest nat on()}")
        LoadTestUt ls.bu ldRemoteServ ceQueryCl ent[T, P, D](
          serv ceDest nat on(),
          "ann-load-test",
          statsRece ver,
           njector. nstance[Serv ce dent f er],
          getRunt  Param nject on[P],
          getD stance nject on[D],
          get ndex d nject on[T]
        )
      }
      case "local" => {
        logger. nfo("Runn ng load test locally..")
        assert( ndexSetD r().nonEmpty, " ndex set path  s empty")
        val statsLoadTestBu ldRecorder = new StatsLoadTestBu ldRecorder(statsRece ver)
        val bu ldRecorder =
          new ComposedLoadTestBu ldRecorder(Seq( n moryBu ldRecorder, statsLoadTestBu ldRecorder))
         ndexEmbedd ngsAndGetQueryable[T, P, D](
          bu ldRecorder,
          LoadTestUt ls.getEmbedd ngsSet( ndexSetD r(),  ndex dType())
        )
      }
    }
    queryable
  }

  pr vate def  ndexEmbedd ngsAndGetQueryable[T, P <: Runt  Params, D <: D stance[D]](
    bu ldRecorder: LoadTestBu ldRecorder,
     ndexSet: Seq[Ent yEmbedd ng[T]]
  ): Future[Queryable[T, P, D]] = {
    logger. nfo(s" ndex ng ent y embedd ngs  n  ndex set w h s ze ${ ndexSet.s ze}")
    val  tr c = getD stance tr c[D]
    val  ndex d nject on = get ndex d nject on[T]
    val  ndexBu lder = new Ann ndexBu ldLoadTest(bu ldRecorder)
    val appendable = algo() match {
      case "annoy" =>
        assert(numOfTrees() > 0, "Must spec fy t  number of trees for annoy")
        logger. nfo(
          s"Creat ng annoy  ndex locally w h num_of_trees: ${numOfTrees()}"
        )
        TypedAnnoy ndex
          . ndexBu lder(
            d  ns on(),
            numOfTrees(),
             tr c,
             ndex d nject on,
            FuturePool. nterrupt bleUnboundedPool
          )
      case "hnsw" =>
        assert(efConstruct on() > 0 && maxM() > 0, "Must spec fy ef_construct on and max_m")
        logger. nfo(
          s"Creat ng hnsw  ndex locally w h max_m: ${maxM()} and ef_construct on: ${efConstruct on()}"
        )
        TypedHnsw ndex
          . ndex[T, D](
            d  ns on(),
             tr c,
            efConstruct on(),
            maxM(),
             ndexSet.s ze,
            ReadWr eFuturePool(FuturePool. nterrupt bleUnboundedPool)
          )
    }

     ndexBu lder
      . ndexEmbedd ngs(appendable,  ndexSet, concurrencyLevel())
      .as nstanceOf[Future[Queryable[T, P, D]]]
  }

  pr vate[t ] def performQuer es[T, P <: Runt  Params, D <: D stance[D]](
    queryable: Queryable[T, P, D],
    queryT  Conf g: Seq[QueryT  Conf gurat on[T, P]],
    quer es: Seq[Query[T]]
  ): Future[Un ] = {
    val  ndexQuery = new Ann ndexQueryLoadTest()
    val durat on = Durat on(durat onSec().toLong, T  Un .SECONDS)
     ndexQuery.performQuer es(
      queryable,
      targetQPS(),
      durat on,
      quer es,
      concurrencyLevel(),
      queryT  Conf g
    )
  }

  // prov de  ndex  d  nject on based on argu nt
  pr vate[t ] def get ndex d nject on[T]:  nject on[T, Array[Byte]] = {
    val  nject on =  ndex dType() match {
      case "long" => Ann nject ons.Long nject on
      case "str ng" => Ann nject ons.Str ng nject on
      case " nt" => Ann nject ons. nt nject on
      case ent yK nd => Ent yK nd.getEnt yK nd(ent yK nd).byte nject on
    }
     nject on.as nstanceOf[ nject on[T, Array[Byte]]]
  }

  pr vate[t ] def getRunt  Param nject on[
    P <: Runt  Params
  ]:  nject on[P, Serv ceRunt  Params] = {
    val  nject on = algo() match {
      case "annoy" => AnnoyCommon.Runt  Params nject on
      case "hnsw" => HnswCommon.Runt  Params nject on
      case "fa ss" => Fa ssCommon.Runt  Params nject on
    }

     nject on.as nstanceOf[ nject on[P, Serv ceRunt  Params]]
  }

  // prov de d stance  nject on based on argu nt
  pr vate[t ] def getD stance nject on[D <: D stance[D]]:  nject on[D, Serv ceD stance] = {
     tr c.fromStr ng( tr c()).as nstanceOf[ nject on[D, Serv ceD stance]]
  }

  pr vate[t ] def getD stance tr c[D <: D stance[D]]:  tr c[D] = {
     tr c.fromStr ng( tr c()).as nstanceOf[ tr c[D]]
  }

  pr vate[t ] def bu ldQueryT  Conf g[T, P <: Runt  Params](
    numOfNe ghbors:  nt,
    params: P,
    conf g: Map[Str ng, Str ng]
  ): QueryT  Conf gurat on[T, P] = {
    val pr ntableQueryRecorder = new  n moryLoadTestQueryRecorder[T]()
    val scope = conf g.flatMap { case (key, value) => Seq(key, value.toStr ng) }.toSeq
    val statsLoadTestQueryRecorder = new StatsLoadTestQueryRecorder[T](
      // Put t  run t   params  n t  stats rece ver na s so that   can tell t  d fference w n
      //   look at t m later.
      statsRece ver.scope(algo()).scope(scope: _*)
    )
    val queryRecorder = new ComposedLoadTestQueryRecorder(
      Seq(pr ntableQueryRecorder, statsLoadTestQueryRecorder)
    )
    QueryT  Conf gurat on(
      queryRecorder,
      params,
      numOfNe ghbors,
      pr ntableQueryRecorder
    )
  }

  overr de protected def modules: Seq[com.google. nject.Module] = Seq(Serv ce dent f erModule)
}
