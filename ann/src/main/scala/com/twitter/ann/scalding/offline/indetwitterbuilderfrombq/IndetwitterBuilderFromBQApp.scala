package com.twitter.ann.scalding.offline.indetwitterbuilderfrombq

import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.bigquery.BigQueryOptions
import com.google.cloud.bigquery.QueryJobConfiguration
import com.twitter.ann.annoy.TypedAnnoyIndetwitter
import com.twitter.ann.brute_force.SerializableBruteForceIndetwitter
import com.twitter.ann.common.Distance
import com.twitter.ann.common.Metric
import com.twitter.ann.common.ReadWriteFuturePool
import com.twitter.ann.hnsw.TypedHnswIndetwitter
import com.twitter.ann.serialization.PersistedEmbeddingInjection
import com.twitter.ann.serialization.ThriftIteratorIO
import com.twitter.ann.serialization.thriftscala.PersistedEmbedding
import com.twitter.cortetwitter.ml.embeddings.common._
import com.twitter.ml.api.embedding.Embedding
import com.twitter.ml.featurestore.lib._
import com.twitter.ml.featurestore.lib.embedding.EmbeddingWithEntity
import com.twitter.scalding.Args
import com.twitter.scalding.Etwitterecution
import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding_internal.bigquery.BigQueryConfig
import com.twitter.scalding_internal.bigquery.BigQuerySource
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.search.common.file.FileUtils
import com.twitter.util.FuturePool
import java.io.FileInputStream
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.Etwitterecutors
import org.apache.avro.generic.GenericRecord
import scala.collection.JavaConverters._

/**
 * Scalding etwitterecution app for building ANN indetwitter from embeddings present in BigQuery table.
 * The output indetwitter is written to a GCS file.
 *
 * Note:
 * - Assumes input data has the fields entityId
 * - Assumes input data has the fields embedding
 *
 * Command for running the app (from source repo root):
 * scalding remote run \
 *   --target ann/src/main/scala/com/twitter/ann/scalding/offline/indetwitterbuilderfrombq:ann-indetwitter-builder-binary
 */
trait IndetwitterBuilderFromBQEtwitterecutable {
  // This method is used to cast the entityKind and the metric to have parameters.
  def indetwitterBuilderEtwitterecution[T <: EntityId, D <: Distance[D]](
    args: Args
  ): Etwitterecution[Unit] = {
    // parse the arguments for this job
    val uncastEntityKind = EntityKind.getEntityKind(args("entity_kind"))
    val uncastMetric = Metric.fromString(args("metric"))
    val entityKind = uncastEntityKind.asInstanceOf[EntityKind[T]]
    val metric = uncastMetric.asInstanceOf[Metric[D]]
    val injection = entityKind.byteInjection
    val numDimensions = args.int("num_dimensions")
    val embeddingLimit = args.optional("embedding_limit").map(_.toInt)
    val concurrencyLevel = args.int("concurrency_level")

    val bigQuery =
      BigQueryOptions
        .newBuilder().setProjectId(args.required("bq_gcp_job_project")).setCredentials(
          ServiceAccountCredentials.fromStream(
            new FileInputStream(args.required("gcp_service_account_key_json")))).build().getService

    // Query to get the latest partition of the BigQuery table.
    val query =
      s"SELECT MAtwitter(ts) AS RecentPartition FROM ${args.required("bq_gcp_table_project")}.${args
        .required("bq_dataset")}.${args.required("bq_table")}"
    val queryConfig = QueryJobConfiguration
      .newBuilder(query)
      .setUseLegacySql(false)
      .build
    val recentPartition =
      bigQuery
        .query(queryConfig).iterateAll().asScala.map(field => {
          field.get(0).getStringValue
        }).toArray.apply(0)

    // Query to etwittertract the embeddings from the latest partition of the BigQuery table
    val bigQueryConfig = BigQueryConfig(
      args.required("bq_gcp_table_project"),
      args
        .required("bq_dataset"),
      args.required("bq_table"))
      .withServiceAccountKey(args.required("gcp_service_account_key_json"))

    val bqFilter = Some(
      s"ts >= '${recentPartition}' AND DATE(TIMESTAMP_MILLIS(createdAt)) >= DATE_SUB(DATE('${recentPartition}'), INTERVAL 1 DAY) AND DATE(TIMESTAMP_MILLIS(createdAt)) <= DATE('${recentPartition}')")
    val withFilterBigQueryConfig = bqFilter
      .map { filter: String =>
        bigQueryConfig.withFilter(filter)
      }.getOrElse(bigQueryConfig)
    val source = new BigQuerySource(withFilterBigQueryConfig)
      .andThen(avroMapper)

    val sourcePipe = TypedPipe
      .from(source)
      .map(transform[T](entityKind))

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

    // Output directory for the ANN indetwitter. We place the indetwitter under a timestamped directory which
    // will be used by the ANN service to read the latest indetwitter
    val timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    val outputDirectory = FileUtils.getFileHandle(args("output_dir") + "/" + timestamp)
    IndetwitterBuilder
      .run(
        sourcePipe,
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

  def avroMapper(row: GenericRecord): KeyVal[Long, java.util.List[Double]] = {
    val entityId = row.get("entityId")
    val embedding = row.get("embedding")

    KeyVal(
      entityId.toString.toLong,
      embedding.asInstanceOf[java.util.List[Double]]
    )
  }

  def transform[T <: EntityId](
    entityKind: EntityKind[T]
  )(
    bqRecord: KeyVal[Long, java.util.List[Double]]
  ): EmbeddingWithEntity[T] = {
    val embeddingArray = bqRecord.value.asScala.map(_.floatValue()).toArray
    val entity_id = entityKind match {
      case UserKind => UserId(bqRecord.key).toThrift
      case TweetKind => TweetId(bqRecord.key).toThrift
      case TfwKind => TfwId(bqRecord.key).toThrift
      case SemanticCoreKind => SemanticCoreId(bqRecord.key).toThrift
      case _ => throw new IllegalArgumentEtwitterception(s"Unsupported embedding kind: $entityKind")
    }
    EmbeddingWithEntity[T](
      EntityId.fromThrift(entity_id).asInstanceOf[T],
      Embedding(embeddingArray))
  }
}

/*
scalding remote run \
--target ann/src/main/scala/com/twitter/ann/scalding/offline/indetwitterbuilderfrombq:ann-indetwitter-builder-binary
 */
object IndetwitterBuilderFromBQApp etwittertends TwitterEtwitterecutionApp with IndetwitterBuilderFromBQEtwitterecutable {
  override def job: Etwitterecution[Unit] = Etwitterecution.getArgs.flatMap { args: Args =>
    indetwitterBuilderEtwitterecution(args)
  }
}
