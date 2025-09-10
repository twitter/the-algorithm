package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.TransformerByteEmbeddingsAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.TransformerEmbeddingsAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.UserHistoryTransformerEmbeddingsHomeBlueAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.UserHistoryTransformerEmbeddingsHomeGreenAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.UserHistoryTransformerEmbeddingsJointBlueAdapter
import com.twitter.home_mixer_features.thriftscala.HomeMixerFeaturesType
import com.twitter.home_mixer_features.{thriftscala => hmf}
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.util.Future
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

object UserHistoryTransformerEmbeddingHomeBlueFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}
object UserHistoryTransformerEmbeddingHomeGreenFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}
object UserHistoryTransformerEmbeddingJointBlueFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class UserHistoryTransformerEmbeddingQueryFeatureHydratorBuilder @Inject() (
  homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint,
  statsReceiver: StatsReceiver) {
  def buildHomeBlueHydrator(): UserHistoryTransformerFloatEmbeddingQueryFeatureHydrator = {
    UserHistoryTransformerFloatEmbeddingQueryFeatureHydrator(
      FeatureHydratorIdentifier("HomeBlueTransformerEmbeddingQueryFeatureHydrator"),
      homeMixerFeatureService,
      statsReceiver,
      UserHistoryTransformerEmbeddingHomeBlueFeature,
      hmf.Cache.TransformerUserEmbeddings,
      UserHistoryTransformerEmbeddingsHomeBlueAdapter
    )
  }

  def buildHomeGreenHydrator(): UserHistoryTransformerByteEmbeddingQueryFeatureHydrator = {
    UserHistoryTransformerByteEmbeddingQueryFeatureHydrator(
      FeatureHydratorIdentifier("HomeGreenTransformerEmbeddingQueryFeatureHydrator"),
      homeMixerFeatureService,
      statsReceiver,
      UserHistoryTransformerEmbeddingHomeGreenFeature,
      hmf.Cache.TransformerUserEmbeddingsGreen,
      UserHistoryTransformerEmbeddingsHomeGreenAdapter
    )
  }

  def buildJointBlueHydrator(): UserHistoryTransformerByteEmbeddingQueryFeatureHydrator = {
    UserHistoryTransformerByteEmbeddingQueryFeatureHydrator(
      FeatureHydratorIdentifier("JointBlueTransformerEmbeddingQueryFeatureHydrator"),
      homeMixerFeatureService,
      statsReceiver,
      UserHistoryTransformerEmbeddingJointBlueFeature,
      hmf.Cache.TransformerUserJointEmbeddingsBlue,
      UserHistoryTransformerEmbeddingsJointBlueAdapter
    )
  }
}

case class UserHistoryTransformerFloatEmbeddingQueryFeatureHydrator(
  override val identifier: FeatureHydratorIdentifier,
  override val homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint,
  override val statsReceiver: StatsReceiver,
  override val embeddingFeature: DataRecordInAFeature[PipelineQuery],
  override val cacheType: hmf.Cache,
  transformerEmbeddingsAdapter: TransformerEmbeddingsAdapter)
    extends BaseUserHistoryTransformerEmbeddingQueryFeatureHydrator {

  override def responseToDataRecord(homeMixerFeaturesType: HomeMixerFeaturesType): DataRecord = {
    val embedding = homeMixerFeaturesType match {
      case hmf.HomeMixerFeaturesType.RawEmbedding(floatEmbedding) =>
        floatEmbedding
      case other =>
        wrongTypeCounter.incr()
        throw new Exception(
          f"Type not matching. Expected RawEmbedding but got ${other.getClass.getSimpleName}")
    }
    val tensor = ml.FloatTensor(embedding)
    transformerEmbeddingsAdapter.adaptToDataRecords(Some(tensor)).asScala.head
  }
}

case class UserHistoryTransformerByteEmbeddingQueryFeatureHydrator(
  override val identifier: FeatureHydratorIdentifier,
  override val homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint,
  override val statsReceiver: StatsReceiver,
  override val embeddingFeature: DataRecordInAFeature[PipelineQuery],
  override val cacheType: hmf.Cache,
  transformerByteEmbeddingsAdapter: TransformerByteEmbeddingsAdapter)
    extends BaseUserHistoryTransformerEmbeddingQueryFeatureHydrator {

  override def responseToDataRecord(homeMixerFeaturesType: HomeMixerFeaturesType): DataRecord = {
    val embedding = homeMixerFeaturesType match {
      case hmf.HomeMixerFeaturesType.RawByteEmbedding(byteEmbedding) =>
        byteEmbedding
      case other =>
        wrongTypeCounter.incr()
        throw new Exception(
          f"Type not matching. Expected RawByteEmbedding but got ${other.getClass.getSimpleName}")
    }
    val tensor = ml.RawTypedTensor(ml.DataType.Byte, convertToByteBuffer(embedding))
    transformerByteEmbeddingsAdapter.adaptToDataRecords(Some(tensor)).asScala.head
  }

  private def convertToByteBuffer(byteArray: Seq[Byte]): ByteBuffer = {
    val buffer = ByteBuffer.allocate(byteArray.size)
    byteArray.foreach(buffer.put)
    buffer.flip
    buffer
  }
}

trait BaseUserHistoryTransformerEmbeddingQueryFeatureHydrator
    extends QueryFeatureHydrator[PipelineQuery] {

  def homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint
  def statsReceiver: StatsReceiver
  def embeddingFeature: DataRecordInAFeature[PipelineQuery]

  def cacheType: hmf.Cache

  override val features: Set[Feature[_, _]] = Set(embeddingFeature)

  protected val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  protected val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  protected val keyNotFoundCounter = scopedStatsReceiver.counter("key/notFound")
  protected val wrongTypeCounter = scopedStatsReceiver.counter("key/wrongType")

  def responseToDataRecord(homeMixerFeaturesType: HomeMixerFeaturesType): DataRecord

  private def getFeatureMap(
    pipelineQuery: PipelineQuery
  ): Future[FeatureMap] = {
    val keysSerialized = Seq(pipelineQuery.getRequiredUserId.toString)
    val request = hmf.HomeMixerFeaturesRequest(keysSerialized, cacheType)
    val responseFut =
      homeMixerFeatureService.getHomeMixerFeatures(request)
    responseFut
      .map { response =>
        response.homeMixerFeatures.headOption.flatMap { homeMixerFeatureOpt =>
          homeMixerFeatureOpt.homeMixerFeaturesType match {
            case Some(homeMixerFeaturesType) =>
              keyFoundCounter.incr()
              Some(responseToDataRecord(homeMixerFeaturesType))
            case None =>
              keyNotFoundCounter.incr()
              None
          }
        }
      }.handle { case _ => None }
      .map {
        case Some(dataRecord) =>
          FeatureMap(embeddingFeature, dataRecord)
        case None =>
          FeatureMap(embeddingFeature, new DataRecord())
      }
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    Stitch.callFuture(getFeatureMap(query))
  }
}
