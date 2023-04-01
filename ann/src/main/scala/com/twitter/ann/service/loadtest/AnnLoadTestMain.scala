package com.twitter.ann.service.loadtest

import com.twitter.ann.annoy.AnnoyCommon
import com.twitter.ann.annoy.AnnoyRuntimeParams
import com.twitter.ann.annoy.TypedAnnoyIndex
import com.twitter.ann.common._
import com.twitter.ann.common.thriftscala.{Distance => ServiceDistance}
import com.twitter.ann.common.thriftscala.{RuntimeParams => ServiceRuntimeParams}
import com.twitter.ann.faiss.FaissCommon
import com.twitter.ann.faiss.FaissParams
import com.twitter.ann.hnsw.HnswCommon
import com.twitter.ann.hnsw.HnswParams
import com.twitter.ann.hnsw.TypedHnswIndex
import com.twitter.bijection.Injection
import com.twitter.cortex.ml.embeddings.common.EntityKind
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.util.DefaultTimer
import com.twitter.finatra.mtls.modules.ServiceIdentifierModule
import com.twitter.inject.server.TwitterServer
import com.twitter.util._
import java.util.concurrent.TimeUnit

/**
 * To build and upload:
 *  $ ./bazel bundle ann/src/main/scala/com/twitter/ann/service/loadtest:bin --bundle-jvm-archive=zip
 *  $ packer add_version --cluster=smf1 $USER ann-loadtest dist/ann-loadtest.zip
 */
object AnnLoadTestMain extends TwitterServer {
  private[this] val algo =
    flag[String]("algo", "load test server types: [annoy/hnsw]")
  private[this] val targetQPS =
    flag[Int]("qps", "target QPS for load test")
  private[this] val queryIdType =
    flag[String](
      "query_id_type",
      "query id type for load test: [long/string/int/user/tweet/word/url/tfwId]")
  private[this] val indexIdType =
    flag[String](
      "index_id_type",
      "index id type for load test: [long/string/int/user/tweet/word/url/tfwId]")
  private[this] val metric =
    flag[String]("metric", "metric type for load test: [Cosine/L2/InnerProduct]")
  private[this] val durationSec =
    flag[Int]("duration_sec", "duration for the load test in sec")
  private[this] val numberOfNeighbors =
    flag[Seq[Int]]("number_of_neighbors", Seq(), "number of neighbors")
  private[this] val dimension = flag[Int]("embedding_dimension", "dimension of embeddings")
  private[this] val querySetDir =
    flag[String]("query_set_dir", "", "Directory containing the queries")
  private[this] val indexSetDir =
    flag[String](
      "index_set_dir",
      "",
      "Directory containing the embeddings to be indexed"
    )
  private[this] val truthSetDir =
    flag[String]("truth_set_dir", "", "Directory containing the truth data")
  private[this] val loadTestType =
    flag[String]("loadtest_type", "Load test type [server/local]")
  private[this] val serviceDestination =
    flag[String]("service_destination", "wily address of remote query service")
  private[this] val concurrencyLevel =
    flag[Int]("concurrency_level", 8, "number of concurrent operations on the index")

  // Queries with random embeddings
  private[this] val withRandomQueries =
    flag[Boolean]("with_random_queries", false, "query with random embeddings")
  private[this] val randomQueriesCount =
    flag[Int]("random_queries_count", 50000, "total random queries")
  private[this] val randomEmbeddingMinValue =
    flag[Float]("random_embedding_min_value", -1.0f, "Min value of random embeddings")
  private[this] val randomEmbeddingMaxValue =
    flag[Float]("random_embedding_max_value", 1.0f, "Max value of random embeddings")

  // parameters for annoy
  private[this] val numOfNodesToExplore =
    flag[Seq[Int]]("annoy_num_of_nodes_to_explore", Seq(), "number of nodes to explore")
  private[this] val numOfTrees =
    flag[Int]("annoy_num_trees", 0, "number of trees to build")

  // parameters for HNSW
  private[this] val efConstruction = flag[Int]("hnsw_ef_construction", "ef for Hnsw construction")
  private[this] val ef = flag[Seq[Int]]("hnsw_ef", Seq(), "ef for Hnsw query")
  private[this] val maxM = flag[Int]("hnsw_max_m", "maxM for Hnsw")

  // FAISS
  private[this] val nprobe = flag[Seq[Int]]("faiss_nprobe", Seq(), "nprobe for faiss query")
  private[this] val quantizerEf =
    flag[Seq[Int]]("faiss_quantizerEf", Seq(0), "quantizerEf for faiss query")
  private[this] val quantizerKfactorRF =
    flag[Seq[Int]]("faiss_quantizerKfactorRF", Seq(0), "quantizerEf for faiss query")
  private[this] val quantizerNprobe =
    flag[Seq[Int]]("faiss_quantizerNprobe", Seq(0), "quantizerNprobe for faiss query")
  private[this] val ht =
    flag[Seq[Int]]("faiss_ht", Seq(0), "ht for faiss query")

  implicit val timer: Timer = DefaultTimer

  override def start(): Unit = {
    logger.info("Starting load test..")
    logger.info(flag.getAll().mkString("\t"))

    assert(numberOfNeighbors().nonEmpty, "number_of_neighbors not defined")
    assert(dimension() > 0, s"Invalid dimension ${dimension()}")

    val inMemoryBuildRecorder = new InMemoryLoadTestBuildRecorder

    val queryableFuture = buildQueryable(inMemoryBuildRecorder)
    val queryConfig = getQueryRuntimeConfig
    val result = queryableFuture.flatMap { queryable =>
      performQueries(queryable, queryConfig, getQueries)
    }

    Await.result(result)
    System.out.println(s"Target QPS: ${targetQPS()}")
    System.out.println(s"Duration per test: ${durationSec()}")
    System.out.println(s"Concurrency Level: ${concurrencyLevel()}")

    LoadTestUtils
      .printResults(inMemoryBuildRecorder, queryConfig)
      .foreach(System.out.println)

    Await.result(close())
    System.exit(0)
  }

  private[this] def getQueries[Q, I]: Seq[Query[I]] = {
    if (withRandomQueries()) {
      assert(
        truthSetDir().isEmpty,
        "Cannot use truth set when query with random embeddings enabled"
      )
      val queries = LoadTestUtils.getRandomQuerySet(
        dimension(),
        randomQueriesCount(),
        randomEmbeddingMinValue(),
        randomEmbeddingMaxValue()
      )

      queries.map(Query[I](_))
    } else {
      assert(querySetDir().nonEmpty, "Query set path is empty")
      assert(queryIdType().nonEmpty, "Query id type is empty")
      val queries = LoadTestUtils.getEmbeddingsSet[Q](querySetDir(), queryIdType())

      if (truthSetDir().nonEmpty) {
        // Join the queries with truth set data.
        assert(indexIdType().nonEmpty, "Index id type is empty")
        val truthSetMap =
          LoadTestUtils.getTruthSetMap[Q, I](truthSetDir(), queryIdType(), indexIdType())
        queries.map(entity => Query[I](entity.embedding, truthSetMap(entity.id)))
      } else {
        queries.map(entity => Query[I](entity.embedding))
      }
    }
  }

  private[this] def getQueryRuntimeConfig[
    T,
    P <: RuntimeParams
  ]: Seq[QueryTimeConfiguration[T, P]] = {
    val queryTimeConfig = algo() match {
      case "annoy" =>
        assert(numOfNodesToExplore().nonEmpty, "Must specify the num_of_nodes_to_explore")
        logger.info(s"Querying annoy index with num_of_nodes_to_explore ${numOfNodesToExplore()}")
        for {
          numNodes <- numOfNodesToExplore()
          numOfNeighbors <- numberOfNeighbors()
        } yield {
          buildQueryTimeConfig[T, AnnoyRuntimeParams](
            numOfNeighbors,
            AnnoyRuntimeParams(Some(numNodes)),
            Map(
              "numNodes" -> numNodes.toString,
              "numberOfNeighbors" -> numOfNeighbors.toString
            )
          ).asInstanceOf[QueryTimeConfiguration[T, P]]
        }
      case "hnsw" =>
        assert(ef().nonEmpty, "Must specify ef")
        logger.info(s"Querying hnsw index with ef ${ef()}")
        for {
          ef <- ef()
          numOfNeighbors <- numberOfNeighbors()
        } yield {
          buildQueryTimeConfig[T, HnswParams](
            numOfNeighbors,
            HnswParams(ef),
            Map(
              "efConstruction" -> ef.toString,
              "numberOfNeighbors" -> numOfNeighbors.toString
            )
          ).asInstanceOf[QueryTimeConfiguration[T, P]]
        }
      case "faiss" =>
        assert(nprobe().nonEmpty, "Must specify nprobe")
        def toNonZeroOptional(x: Int): Option[Int] = if (x != 0) Some(x) else None
        for {
          numOfNeighbors <- numberOfNeighbors()
          runNProbe <- nprobe()
          runQEF <- quantizerEf()
          runKFactorEF <- quantizerKfactorRF()
          runQNProbe <- quantizerNprobe()
          runHT <- ht()
        } yield {
          val params = FaissParams(
            Some(runNProbe),
            toNonZeroOptional(runQEF),
            toNonZeroOptional(runKFactorEF),
            toNonZeroOptional(runQNProbe),
            toNonZeroOptional(runHT))
          buildQueryTimeConfig[T, FaissParams](
            numOfNeighbors,
            params,
            Map(
              "nprobe" -> params.nprobe.toString,
              "quantizer_efSearch" -> params.quantizerEf.toString,
              "quantizer_k_factor_rf" -> params.quantizerKFactorRF.toString,
              "quantizer_nprobe" -> params.quantizerNprobe.toString,
              "ht" -> params.ht.toString,
              "numberOfNeighbors" -> numOfNeighbors.toString,
            )
          ).asInstanceOf[QueryTimeConfiguration[T, P]]
        }
      case _ => throw new IllegalArgumentException(s"server type: $algo is not supported yet")
    }

    queryTimeConfig
  }

  private def buildQueryable[T, P <: RuntimeParams, D <: Distance[D]](
    inMemoryBuildRecorder: InMemoryLoadTestBuildRecorder
  ): Future[Queryable[T, P, D]] = {
    val queryable = loadTestType() match {
      case "remote" => {
        assert(serviceDestination().nonEmpty, "Service destination not defined")
        logger.info(s"Running load test with remote service ${serviceDestination()}")
        LoadTestUtils.buildRemoteServiceQueryClient[T, P, D](
          serviceDestination(),
          "ann-load-test",
          statsReceiver,
          injector.instance[ServiceIdentifier],
          getRuntimeParamInjection[P],
          getDistanceInjection[D],
          getIndexIdInjection[T]
        )
      }
      case "local" => {
        logger.info("Running load test locally..")
        assert(indexSetDir().nonEmpty, "Index set path is empty")
        val statsLoadTestBuildRecorder = new StatsLoadTestBuildRecorder(statsReceiver)
        val buildRecorder =
          new ComposedLoadTestBuildRecorder(Seq(inMemoryBuildRecorder, statsLoadTestBuildRecorder))
        indexEmbeddingsAndGetQueryable[T, P, D](
          buildRecorder,
          LoadTestUtils.getEmbeddingsSet(indexSetDir(), indexIdType())
        )
      }
    }
    queryable
  }

  private def indexEmbeddingsAndGetQueryable[T, P <: RuntimeParams, D <: Distance[D]](
    buildRecorder: LoadTestBuildRecorder,
    indexSet: Seq[EntityEmbedding[T]]
  ): Future[Queryable[T, P, D]] = {
    logger.info(s"Indexing entity embeddings in index set with size ${indexSet.size}")
    val metric = getDistanceMetric[D]
    val indexIdInjection = getIndexIdInjection[T]
    val indexBuilder = new AnnIndexBuildLoadTest(buildRecorder)
    val appendable = algo() match {
      case "annoy" =>
        assert(numOfTrees() > 0, "Must specify the number of trees for annoy")
        logger.info(
          s"Creating annoy index locally with num_of_trees: ${numOfTrees()}"
        )
        TypedAnnoyIndex
          .indexBuilder(
            dimension(),
            numOfTrees(),
            metric,
            indexIdInjection,
            FuturePool.interruptibleUnboundedPool
          )
      case "hnsw" =>
        assert(efConstruction() > 0 && maxM() > 0, "Must specify ef_construction and max_m")
        logger.info(
          s"Creating hnsw index locally with max_m: ${maxM()} and ef_construction: ${efConstruction()}"
        )
        TypedHnswIndex
          .index[T, D](
            dimension(),
            metric,
            efConstruction(),
            maxM(),
            indexSet.size,
            ReadWriteFuturePool(FuturePool.interruptibleUnboundedPool)
          )
    }

    indexBuilder
      .indexEmbeddings(appendable, indexSet, concurrencyLevel())
      .asInstanceOf[Future[Queryable[T, P, D]]]
  }

  private[this] def performQueries[T, P <: RuntimeParams, D <: Distance[D]](
    queryable: Queryable[T, P, D],
    queryTimeConfig: Seq[QueryTimeConfiguration[T, P]],
    queries: Seq[Query[T]]
  ): Future[Unit] = {
    val indexQuery = new AnnIndexQueryLoadTest()
    val duration = Duration(durationSec().toLong, TimeUnit.SECONDS)
    indexQuery.performQueries(
      queryable,
      targetQPS(),
      duration,
      queries,
      concurrencyLevel(),
      queryTimeConfig
    )
  }

  // provide index id injection based on argument
  private[this] def getIndexIdInjection[T]: Injection[T, Array[Byte]] = {
    val injection = indexIdType() match {
      case "long" => AnnInjections.LongInjection
      case "string" => AnnInjections.StringInjection
      case "int" => AnnInjections.IntInjection
      case entityKind => EntityKind.getEntityKind(entityKind).byteInjection
    }
    injection.asInstanceOf[Injection[T, Array[Byte]]]
  }

  private[this] def getRuntimeParamInjection[
    P <: RuntimeParams
  ]: Injection[P, ServiceRuntimeParams] = {
    val injection = algo() match {
      case "annoy" => AnnoyCommon.RuntimeParamsInjection
      case "hnsw" => HnswCommon.RuntimeParamsInjection
      case "faiss" => FaissCommon.RuntimeParamsInjection
    }

    injection.asInstanceOf[Injection[P, ServiceRuntimeParams]]
  }

  // provide distance injection based on argument
  private[this] def getDistanceInjection[D <: Distance[D]]: Injection[D, ServiceDistance] = {
    Metric.fromString(metric()).asInstanceOf[Injection[D, ServiceDistance]]
  }

  private[this] def getDistanceMetric[D <: Distance[D]]: Metric[D] = {
    Metric.fromString(metric()).asInstanceOf[Metric[D]]
  }

  private[this] def buildQueryTimeConfig[T, P <: RuntimeParams](
    numOfNeighbors: Int,
    params: P,
    config: Map[String, String]
  ): QueryTimeConfiguration[T, P] = {
    val printableQueryRecorder = new InMemoryLoadTestQueryRecorder[T]()
    val scope = config.flatMap { case (key, value) => Seq(key, value.toString) }.toSeq
    val statsLoadTestQueryRecorder = new StatsLoadTestQueryRecorder[T](
      // Put the run time params in the stats receiver names so that we can tell the difference when
      // we look at them later.
      statsReceiver.scope(algo()).scope(scope: _*)
    )
    val queryRecorder = new ComposedLoadTestQueryRecorder(
      Seq(printableQueryRecorder, statsLoadTestQueryRecorder)
    )
    QueryTimeConfiguration(
      queryRecorder,
      params,
      numOfNeighbors,
      printableQueryRecorder
    )
  }

  override protected def modules: Seq[com.google.inject.Module] = Seq(ServiceIdentifierModule)
}
