package com.twitter.ann.scalding.offline.indetwitterbuilder

import com.twitter.ann.annoy.TypedAnnoyIndetwitter
import com.twitter.ann.brute_force.SerializableBruteForceIndetwitter
import com.twitter.ann.common.Distance
import com.twitter.ann.common.Metric
import com.twitter.ann.common.ReadWriteFuturePool
import com.twitter.ann.hnsw.TypedHnswIndetwitter
import com.twitter.ann.serialization.thriftscala.PersistedEmbedding
import com.twitter.ann.serialization.PersistedEmbeddingInjection
import com.twitter.ann.serialization.ThriftIteratorIO
import com.twitter.cortetwitter.ml.embeddings.common._
import com.twitter.ml.featurestore.lib.EntityId
import com.twitter.scalding.Args
import com.twitter.scalding.Etwitterecution
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.search.common.file.FileUtils
import com.twitter.util.FuturePool
import java.util.concurrent.Etwitterecutors

trait IndetwitterBuilderEtwitterecutable {
  // This method is used to cast the entityKind and the metric to have parameters.
  def indetwitterBuilderEtwitterecution[T <: EntityId, D <: Distance[D]](
    args: Args
  ): Etwitterecution[Unit] = {
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
    val threadPool = Etwitterecutors.newFitwitteredThreadPool(concurrencyLevel)

    val serialization = args("algo") match {
      case "brute_force" =>
        val PersistedEmbeddingIO = new ThriftIteratorIO[PersistedEmbedding](PersistedEmbedding)
        SerializableBruteForceIndetwitter[T, D](
          metric,
          FuturePool.apply(threadPool),
          new PersistedEmbeddingInjection[T](injection),
          PersistedEmbeddingIO
        )
      case "annoy" =>
        TypedAnnoyIndetwitter.indetwitterBuilder[T, D](
          numDimensions,
          args.int("annoy_num_trees"),
          metric,
          injection,
          FuturePool.apply(threadPool)
        )
      case "hnsw" =>
        val efConstruction = args.int("ef_construction")
        val matwitterM = args.int("matwitter_m")
        val etwitterpectedElements = args.int("etwitterpected_elements")
        TypedHnswIndetwitter.serializableIndetwitter[T, D](
          numDimensions,
          metric,
          efConstruction,
          matwitterM,
          etwitterpectedElements,
          injection,
          ReadWriteFuturePool(FuturePool.apply(threadPool))
        )
    }
    IndetwitterBuilder
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

object IndetwitterBuilderApp etwittertends TwitterEtwitterecutionApp with IndetwitterBuilderEtwitterecutable {
  override def job: Etwitterecution[Unit] = Etwitterecution.getArgs.flatMap { args: Args =>
    indetwitterBuilderEtwitterecution(args)
  }
}
