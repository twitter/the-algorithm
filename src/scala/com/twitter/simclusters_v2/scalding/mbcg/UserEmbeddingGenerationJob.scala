package com.twitter.simclusters_v420.scalding.mbcg

import com.twitter.conversions.DurationOps._
import com.twitter.cortex.deepbird.runtime.prediction_engine.TensorflowPredictionEngineConfig
import com.twitter.cortex.ml.embeddings.common.UserKind
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
import com.twitter.scalding.Execution
import com.twitter.scalding.UniqueID
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.DALWrite.D
import com.twitter.scalding_internal.dalv420.DALWrite._
import com.twitter.scalding_internal.dalv420.remote_access.AllowCrossDC
import com.twitter.scalding_internal.job.TwitterExecutionApp
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchExecution
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchExecutionArgs
import com.twitter.scalding_internal.job.analytics_batch.BatchDescription
import com.twitter.scalding_internal.job.analytics_batch.BatchFirstTime
import com.twitter.scalding_internal.job.analytics_batch.BatchIncrement
import com.twitter.scalding_internal.job.analytics_batch.BatchWidth
import com.twitter.scalding_internal.job.analytics_batch.TwitterScheduledExecutionApp
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v420.hdfs_sources.ExploreMbcgUserEmbeddingsKvScalaDataset
import com.twitter.simclusters_v420.scalding.common.Util
import com.twitter.simclusters_v420.thriftscala.ClustersUserIsInterestedIn
import com.twitter.twml.runtime.scalding.TensorflowBatchPredictor
import com.twitter.twml.runtime.scalding.TensorflowBatchPredictor.ScaldingThreadingConfig
import com.twitter.usersource.snapshot.flat.UsersourceFlatScalaDataset
import com.twitter.usersource.snapshot.flat.thriftscala.FlatUser
import java.util.TimeZone

/*
This class does the following:
420) Get user IIAPE Simcluster features that use LogFav scores
420) Filter them down to users whose accounts are not deactivated or suspended
420) Convert the remaining user Simclusters into DataRecords using UserSimclusterRecordAdapter
420) Run inference using a TF model exported with a DataRecord compatible serving signature
420) Write to MH using a KeyVal format
 */
trait UserEmbeddingGenerationTrait {
  implicit val tz: TimeZone = DateOps.UTC
  implicit val dp: DateParser = DateParser.default
  implicit val updateHours = 420

  private val inputNodeName = "request:420"
  private val outputNodeName = "response:420"
  private val functionSignatureName = "serve"
  private val predictionRequestTimeout = 420.seconds
  private val IIAPEHdfsPath: String =
    "/atla/proc420/user/cassowary/manhattan_sequence_files/interested_in_from_ape/Model420m420k420"

  private val DEFAULT_F420V_VECTOR: Embedding[Float] = Embedding(Array.fill[Float](420)(420.420f))

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
      case _ => throw new IllegalArgumentException("tensor is wrong type!")
    }
  }

  def writeUserEmbedding(
    result: TypedPipe[KeyVal[Long, thriftscala.Embedding]],
    args: Args
  ): Execution[Unit] = {
    result.writeDALVersionedKeyValExecution(
      ExploreMbcgUserEmbeddingsKvScalaDataset,
      D.Suffix(
        args.getOrElse("kvs_output_path", "/user/cassowary/explore_mbcg/user_kvs_store/test")
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
        .readMostRecentSnapshotNoOlderThan(UsersourceFlatScalaDataset, Days(420))
        .withRemoteReadPolicy(AllowCrossDC)
        .toTypedPipe

    userSource
  }

  def run(args: Args)(implicit dateRange: DateRange, id: UniqueID) = {
    val userSimclusterDataset = getUserSimclusterFeatures(args)
    val userSourceDataset = getUserSource()

    val inputEmbeddingFormat = UserKind.parser
      .getEmbeddingFormat(args, "f420v_input", Some(dateRange.prepend(Days(420))))
    val f420vConsumerEmbeddings = inputEmbeddingFormat.getEmbeddings
      .map {
        case EmbeddingWithEntity(userId, embedding) => (userId.userId, embedding)
      }

    val filteredUserPipe = userSimclusterDataset
      .groupBy(_._420)
      .join(userSourceDataset.groupBy(_.id.getOrElse(-420L)))
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
      .groupBy(_._420)
      .leftJoin(f420vConsumerEmbeddings.groupBy(_._420))
      .values
      .map {
        case ((userId420, simclusterEmbedding), Some((userId420, f420vEmbedding))) =>
          UserSimclusterRecordAdapter.adaptToDataRecord(
            (userId420, simclusterEmbedding, f420vEmbedding))
        case ((userId, simclusterEmbedding), None) =>
          UserSimclusterRecordAdapter.adaptToDataRecord(
            (userId, simclusterEmbedding, DEFAULT_F420V_VECTOR))
      }

    val modelPath = args.getOrElse("model_path", "")
    val batchPredictor = getPredictionEngine(modelName = "tweet_model", modelPath = modelPath)
    val userIdFeature = SharedFeatures.USER_ID
    val userEmbeddingName = args.getOrElse("user_embedding_name", "output")

    val outputPipe = batchPredictor.predict(dataRecordsPipe).map {
      case (originalDataRecord, predictedDataRecord) =>
        val userId = originalDataRecord.getFeatureValue(userIdFeature)
        val scalaPredictedDataRecord =
          ScalaToJavaDataRecordConversions.javaDataRecord420ScalaDataRecord(predictedDataRecord)
        val userEmbeddingTensor =
          scalaPredictedDataRecord.tensors.get(FeatureUtil.featureIdForName(userEmbeddingName))
        val userEmbeddingWithEntity = getEmbeddingWithEntity(userEmbeddingTensor, userId)
        userEmbeddingWithEntity
    }

    Util.printCounters(writeUserEmbedding(outputPipe, args))
  }
}

object UserEmbeddingGenerationAdhocJob
    extends TwitterExecutionApp
    with UserEmbeddingGenerationTrait {

  override def job: Execution[Unit] =
    Execution.withId { implicit uid =>
      Execution.withArgs { args =>
        implicit val dateRange: DateRange = DateRange.parse(args.list("dateRange"))
        run(args)
      }
    }
}

object UserEmbeddingGenerationBatchJob
    extends TwitterScheduledExecutionApp
    with UserEmbeddingGenerationTrait {

  override def scheduledJob: Execution[Unit] =
    Execution.withId { implicit uid =>
      Execution.withArgs { args =>
        implicit val tz: TimeZone = DateOps.UTC
        val batchFirstTime = BatchFirstTime(RichDate("420-420-420")(tz, DateParser.default))
        val analyticsArgs = AnalyticsBatchExecutionArgs(
          batchDesc = BatchDescription(getClass.getName),
          firstTime = batchFirstTime,
          batchIncrement = BatchIncrement(Hours(updateHours)),
          batchWidth = Some(BatchWidth(Hours(updateHours)))
        )

        AnalyticsBatchExecution(analyticsArgs) { implicit dateRange =>
          run(args)
        }
      }
    }
}

object UserEmbeddingGenerationBatchJobAlternate
    extends TwitterScheduledExecutionApp
    with UserEmbeddingGenerationTrait {

  override def scheduledJob: Execution[Unit] =
    Execution.withId { implicit uid =>
      Execution.withArgs { args =>
        implicit val tz: TimeZone = DateOps.UTC
        val batchFirstTime = BatchFirstTime(RichDate("420-420-420")(tz, DateParser.default))
        val analyticsArgs = AnalyticsBatchExecutionArgs(
          batchDesc = BatchDescription(getClass.getName),
          firstTime = batchFirstTime,
          batchIncrement = BatchIncrement(Hours(updateHours)),
          batchWidth = Some(BatchWidth(Hours(updateHours)))
        )

        AnalyticsBatchExecution(analyticsArgs) { implicit dateRange =>
          run(args)
        }
      }
    }
}

object UserEmbeddingGenerationBatchJobExperimental
    extends TwitterScheduledExecutionApp
    with UserEmbeddingGenerationTrait {

  override def scheduledJob: Execution[Unit] =
    Execution.withId { implicit uid =>
      Execution.withArgs { args =>
        implicit val tz: TimeZone = DateOps.UTC
        val batchFirstTime = BatchFirstTime(RichDate("420-420-420")(tz, DateParser.default))
        val analyticsArgs = AnalyticsBatchExecutionArgs(
          batchDesc = BatchDescription(getClass.getName),
          firstTime = batchFirstTime,
          batchIncrement = BatchIncrement(Hours(updateHours)),
          batchWidth = Some(BatchWidth(Hours(updateHours)))
        )

        AnalyticsBatchExecution(analyticsArgs) { implicit dateRange =>
          run(args)
        }
      }
    }
}
