package com.twitter.simclusters_v2.scalding.mbcg

import com.twitter.conversions.DurationOps._
import com.twitter.cortetwitter.deepbird.runtime.prediction_engine.TensorflowPredictionEngineConfig
import com.twitter.cortetwitter.ml.embeddings.common.UserKind
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.ml.api.FeatureUtil
import com.twitter.ml.api.constant.SharedFeatures
import com.twitter.ml.api.embedding.Embedding
import com.twitter.ml.api.thriftscala
import com.twitter.ml.api.thriftscala.{GeneralTensor => ThriftGeneralTensor}
import com.twitter.ml.api.util.FDsl._
import com.twitter.ml.api.util.ScalaToJavaDataRecordConversions
import com.twitter.ml.featurestore.lib.embedding.EmbeddingWithEntity
import com.twitter.scalding.Args
import com.twitter.scalding.DateParser
import com.twitter.scalding.DateRange
import com.twitter.scalding.Etwitterecution
import com.twitter.scalding.UniqueID
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.DALWrite.D
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.dalv2.remote_access.AllowCrossDC
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchEtwitterecution
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchEtwitterecutionArgs
import com.twitter.scalding_internal.job.analytics_batch.BatchDescription
import com.twitter.scalding_internal.job.analytics_batch.BatchFirstTime
import com.twitter.scalding_internal.job.analytics_batch.BatchIncrement
import com.twitter.scalding_internal.job.analytics_batch.BatchWidth
import com.twitter.scalding_internal.job.analytics_batch.TwitterScheduledEtwitterecutionApp
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v2.hdfs_sources.EtwitterploreMbcgUserEmbeddingsKvScalaDataset
import com.twitter.simclusters_v2.scalding.common.Util
import com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn
import com.twitter.twml.runtime.scalding.TensorflowBatchPredictor
import com.twitter.twml.runtime.scalding.TensorflowBatchPredictor.ScaldingThreadingConfig
import com.twitter.usersource.snapshot.flat.UsersourceFlatScalaDataset
import com.twitter.usersource.snapshot.flat.thriftscala.FlatUser
import java.util.TimeZone

/*
This class does the following:
1) Get user IIAPE Simcluster features that use LogFav scores
2) Filter them down to users whose accounts are not deactivated or suspended
3) Convert the remaining user Simclusters into DataRecords using UserSimclusterRecordAdapter
4) Run inference using a TF model etwitterported with a DataRecord compatible serving signature
5) Write to MH using a KeyVal format
 */
trait UserEmbeddingGenerationTrait {
  implicit val tz: TimeZone = DateOps.UTC
  implicit val dp: DateParser = DateParser.default
  implicit val updateHours = 12

  private val inputNodeName = "request:0"
  private val outputNodeName = "response:0"
  private val functionSignatureName = "serve"
  private val predictionRequestTimeout = 5.seconds
  private val IIAPEHdfsPath: String =
    "/atla/proc3/user/cassowary/manhattan_sequence_files/interested_in_from_ape/Model20m145k2020"

  private val DEFAULT_F2V_VECTOR: Embedding[Float] = Embedding(Array.fill[Float](200)(0.0f))

  def getPredictionEngine(modelName: String, modelPath: String): TensorflowBatchPredictor = {
    val config = TensorflowPredictionEngineConfig(
      modelName = modelName,
      modelSource = modelPath,
      threadingConfig = Some(ScaldingThreadingConfig),
      defaultInputNode = inputNodeName,
      defaultOutputNode = outputNodeName,
      functionSignatureName = functionSignatureName,
      statsReceiver = NullStatsReceiver
    )
    TensorflowBatchPredictor(config, predictionRequestTimeout)
  }

  def getEmbeddingWithEntity(userEmbeddingTensor: ThriftGeneralTensor, userId: Long) = {
    userEmbeddingTensor match {
      case ThriftGeneralTensor.RawTypedTensor(rawTensor) =>
        val embedding =
          thriftscala.Embedding(Some(rawTensor))
        KeyVal(userId, embedding)
      case _ => throw new IllegalArgumentEtwitterception("tensor is wrong type!")
    }
  }

  def writeUserEmbedding(
    result: TypedPipe[KeyVal[Long, thriftscala.Embedding]],
    args: Args
  ): Etwitterecution[Unit] = {
    result.writeDALVersionedKeyValEtwitterecution(
      EtwitterploreMbcgUserEmbeddingsKvScalaDataset,
      D.Suffitwitter(
        args.getOrElse("kvs_output_path", "/user/cassowary/etwitterplore_mbcg/user_kvs_store/test")
      )
    )
  }

  def getUserSimclusterFeatures(
    args: Args
  )(
    implicit dateRange: DateRange
  ): TypedPipe[(Long, ClustersUserIsInterestedIn)] = {
    val userSimclusterEmbeddingTypedPipe = TypedPipe
      .from(AdhocKeyValSources.interestedInSource(IIAPEHdfsPath))
      .collect {
        case (
              userId,
              iIAPE: ClustersUserIsInterestedIn
            ) =>
          (userId.toLong, iIAPE)
      }

    userSimclusterEmbeddingTypedPipe
  }

  def getUserSource()(implicit dateRange: DateRange): TypedPipe[FlatUser] = {
    val userSource =
      DAL
        .readMostRecentSnapshotNoOlderThan(UsersourceFlatScalaDataset, Days(7))
        .withRemoteReadPolicy(AllowCrossDC)
        .toTypedPipe

    userSource
  }

  def run(args: Args)(implicit dateRange: DateRange, id: UniqueID) = {
    val userSimclusterDataset = getUserSimclusterFeatures(args)
    val userSourceDataset = getUserSource()

    val inputEmbeddingFormat = UserKind.parser
      .getEmbeddingFormat(args, "f2v_input", Some(dateRange.prepend(Days(14))))
    val f2vConsumerEmbeddings = inputEmbeddingFormat.getEmbeddings
      .map {
        case EmbeddingWithEntity(userId, embedding) => (userId.userId, embedding)
      }

    val filteredUserPipe = userSimclusterDataset
      .groupBy(_._1)
      .join(userSourceDataset.groupBy(_.id.getOrElse(-1L)))
      .map {
        case (userId, ((_, simclusterEmbedding), userInfo)) =>
          (userId, simclusterEmbedding, userInfo)
      }
      .filter {
        case (_, _, userInfo) =>
          !userInfo.deactivated.contains(true) && !userInfo.suspended
            .contains(true)
      }
      .map {
        case (userId, simclusterEmbedding, _) =>
          (userId, simclusterEmbedding)
      }

    val dataRecordsPipe = filteredUserPipe
      .groupBy(_._1)
      .leftJoin(f2vConsumerEmbeddings.groupBy(_._1))
      .values
      .map {
        case ((userId1, simclusterEmbedding), Some((userId2, f2vEmbedding))) =>
          UserSimclusterRecordAdapter.adaptToDataRecord(
            (userId1, simclusterEmbedding, f2vEmbedding))
        case ((userId, simclusterEmbedding), None) =>
          UserSimclusterRecordAdapter.adaptToDataRecord(
            (userId, simclusterEmbedding, DEFAULT_F2V_VECTOR))
      }

    val modelPath = args.getOrElse("model_path", "")
    val batchPredictor = getPredictionEngine(modelName = "tweet_model", modelPath = modelPath)
    val userIdFeature = SharedFeatures.USER_ID
    val userEmbeddingName = args.getOrElse("user_embedding_name", "output")

    val outputPipe = batchPredictor.predict(dataRecordsPipe).map {
      case (originalDataRecord, predictedDataRecord) =>
        val userId = originalDataRecord.getFeatureValue(userIdFeature)
        val scalaPredictedDataRecord =
          ScalaToJavaDataRecordConversions.javaDataRecord2ScalaDataRecord(predictedDataRecord)
        val userEmbeddingTensor =
          scalaPredictedDataRecord.tensors.get(FeatureUtil.featureIdForName(userEmbeddingName))
        val userEmbeddingWithEntity = getEmbeddingWithEntity(userEmbeddingTensor, userId)
        userEmbeddingWithEntity
    }

    Util.printCounters(writeUserEmbedding(outputPipe, args))
  }
}

object UserEmbeddingGenerationAdhocJob
    etwittertends TwitterEtwitterecutionApp
    with UserEmbeddingGenerationTrait {

  override def job: Etwitterecution[Unit] =
    Etwitterecution.withId { implicit uid =>
      Etwitterecution.withArgs { args =>
        implicit val dateRange: DateRange = DateRange.parse(args.list("dateRange"))
        run(args)
      }
    }
}

object UserEmbeddingGenerationBatchJob
    etwittertends TwitterScheduledEtwitterecutionApp
    with UserEmbeddingGenerationTrait {

  override def scheduledJob: Etwitterecution[Unit] =
    Etwitterecution.withId { implicit uid =>
      Etwitterecution.withArgs { args =>
        implicit val tz: TimeZone = DateOps.UTC
        val batchFirstTime = BatchFirstTime(RichDate("2021-12-04")(tz, DateParser.default))
        val analyticsArgs = AnalyticsBatchEtwitterecutionArgs(
          batchDesc = BatchDescription(getClass.getName),
          firstTime = batchFirstTime,
          batchIncrement = BatchIncrement(Hours(updateHours)),
          batchWidth = Some(BatchWidth(Hours(updateHours)))
        )

        AnalyticsBatchEtwitterecution(analyticsArgs) { implicit dateRange =>
          run(args)
        }
      }
    }
}

object UserEmbeddingGenerationBatchJobAlternate
    etwittertends TwitterScheduledEtwitterecutionApp
    with UserEmbeddingGenerationTrait {

  override def scheduledJob: Etwitterecution[Unit] =
    Etwitterecution.withId { implicit uid =>
      Etwitterecution.withArgs { args =>
        implicit val tz: TimeZone = DateOps.UTC
        val batchFirstTime = BatchFirstTime(RichDate("2022-03-28")(tz, DateParser.default))
        val analyticsArgs = AnalyticsBatchEtwitterecutionArgs(
          batchDesc = BatchDescription(getClass.getName),
          firstTime = batchFirstTime,
          batchIncrement = BatchIncrement(Hours(updateHours)),
          batchWidth = Some(BatchWidth(Hours(updateHours)))
        )

        AnalyticsBatchEtwitterecution(analyticsArgs) { implicit dateRange =>
          run(args)
        }
      }
    }
}

object UserEmbeddingGenerationBatchJobEtwitterperimental
    etwittertends TwitterScheduledEtwitterecutionApp
    with UserEmbeddingGenerationTrait {

  override def scheduledJob: Etwitterecution[Unit] =
    Etwitterecution.withId { implicit uid =>
      Etwitterecution.withArgs { args =>
        implicit val tz: TimeZone = DateOps.UTC
        val batchFirstTime = BatchFirstTime(RichDate("2021-12-12")(tz, DateParser.default))
        val analyticsArgs = AnalyticsBatchEtwitterecutionArgs(
          batchDesc = BatchDescription(getClass.getName),
          firstTime = batchFirstTime,
          batchIncrement = BatchIncrement(Hours(updateHours)),
          batchWidth = Some(BatchWidth(Hours(updateHours)))
        )

        AnalyticsBatchEtwitterecution(analyticsArgs) { implicit dateRange =>
          run(args)
        }
      }
    }
}
