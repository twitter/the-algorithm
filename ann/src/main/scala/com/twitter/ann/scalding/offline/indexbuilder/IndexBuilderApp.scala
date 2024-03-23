package com.ExTwitter.ann.scalding.offline.indexbuilder

import com.ExTwitter.ann.annoy.TypedAnnoyIndex
import com.ExTwitter.ann.brute_force.SerializableBruteForceIndex
import com.ExTwitter.ann.common.Distance
import com.ExTwitter.ann.common.Metric
import com.ExTwitter.ann.common.ReadWriteFuturePool
import com.ExTwitter.ann.hnsw.TypedHnswIndex
import com.ExTwitter.ann.serialization.thriftscala.PersistedEmbedding
import com.ExTwitter.ann.serialization.PersistedEmbeddingInjection
import com.ExTwitter.ann.serialization.ThriftIteratorIO
import com.ExTwitter.cortex.ml.embeddings.common._
import com.ExTwitter.ml.featurestore.lib.EntityId
import com.ExTwitter.scalding.Args
import com.ExTwitter.scalding.Execution
import com.ExTwitter.scalding_internal.job.ExTwitterExecutionApp
import com.ExTwitter.search.common.file.FileUtils
import com.ExTwitter.util.FuturePool
import java.util.concurrent.Executors

trait IndexBuilderExecutable {
  // This method is used to cast the entityKind and the metric to have parameters.
  def indexBuilderExecution[T <: EntityId, D <: Distance[D]](
    args: Args
  ): Execution[Unit] = {
    // parse the arguments for this job
    val uncastEntityKind = EntityKind.getEntityKind(args("entity_kind"))
    val uncastMetric = Metric.fromString(args("metric"))
    val entityKind = uncastEntityKind.asInstanceOf[EntityKind[T]]
    val metric = uncastMetric.asInstanceOf[Metric[D]]
    val embeddingFormat = entityKind.parser.getEmbeddingFormat(args, "input")
    val injection = entityKind.byteInjection
    val numDimensions = args.int("num_dimensions")
    val embeddingLimit = args.optional("embedding_limit").map(_.toInt)
    val concurrencyLevel = args.int("concurrency_level")
    val outputDirectory = FileUtils.getFileHandle(args("output_dir"))

    println(s"Job args: ${args.toString}")
    val threadPool = Executors.newFixedThreadPool(concurrencyLevel)

    val serialization = args("algo") match {
      case "brute_force" =>
        val PersistedEmbeddingIO = new ThriftIteratorIO[PersistedEmbedding](PersistedEmbedding)
        SerializableBruteForceIndex[T, D](
          metric,
          FuturePool.apply(threadPool),
          new PersistedEmbeddingInjection[T](injection),
          PersistedEmbeddingIO
        )
      case "annoy" =>
        TypedAnnoyIndex.indexBuilder[T, D](
          numDimensions,
          args.int("annoy_num_trees"),
          metric,
          injection,
          FuturePool.apply(threadPool)
        )
      case "hnsw" =>
        val efConstruction = args.int("ef_construction")
        val maxM = args.int("max_m")
        val expectedElements = args.int("expected_elements")
        TypedHnswIndex.serializableIndex[T, D](
          numDimensions,
          metric,
          efConstruction,
          maxM,
          expectedElements,
          injection,
          ReadWriteFuturePool(FuturePool.apply(threadPool))
        )
    }
    IndexBuilder
      .run(
        embeddingFormat,
        embeddingLimit,
        serialization,
        concurrencyLevel,
        outputDirectory,
        numDimensions
      ).onComplete { _ =>
        threadPool.shutdown()
        Unit
      }
  }
}

object IndexBuilderApp extends ExTwitterExecutionApp with IndexBuilderExecutable {
  override def job: Execution[Unit] = Execution.getArgs.flatMap { args: Args =>
    indexBuilderExecution(args)
  }
}
