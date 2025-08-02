package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TwhinUserEngagementFeatureRepository
import com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.adapters.twhin_embeddings.TwhinUserEngagementEmbeddingsAdapter
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.stitch.Stitch
import com.twitter.util.Return
import com.twitter.util.Throw
import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton
import scala.collection.JavaConverters._

object TwhinUserEngagementFeature
    etwittertends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwhinUserEngagementQueryFeatureHydrator @Inject() (
  @Named(TwhinUserEngagementFeatureRepository)
  client: KeyValueRepository[Seq[Long], Long, ml.FloatTensor],
  statsReceiver: StatsReceiver)
    etwittertends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TwhinUserEngagement")

  override val features: Set[Feature[_, _]] = Set(TwhinUserEngagementFeature)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyLossCounter = scopedStatsReceiver.counter("key/loss")
  private val keyFailureCounter = scopedStatsReceiver.counter("key/failure")

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    Stitch.callFuture(client(Seq(userId))).map { results =>
      val embedding: Option[ml.FloatTensor] = results(userId) match {
        case Return(value) =>
          if (value.etwitterists(_.floats.nonEmpty)) keyFoundCounter.incr()
          else keyLossCounter.incr()
          value
        case Throw(_) =>
          keyFailureCounter.incr()
          None
        case _ =>
          None
      }

      val dataRecord =
        TwhinUserEngagementEmbeddingsAdapter.adaptToDataRecords(embedding).asScala.head

      FeatureMapBuilder()
        .add(TwhinUserEngagementFeature, dataRecord)
        .build()
    }
  }

}
