package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings.TwhinRebuildUserEngagementEmbeddingsAdapter
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinRebuildUserEngagementFeatureRepository
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.stitch.Stitch
import com.twitter.util.Return
import com.twitter.util.Throw
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TwhinRebuildUserEngagementFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwhinRebuildUserEngagementQueryFeatureHydrator @Inject() (
  @Named(TwhinRebuildUserEngagementFeatureRepository)
  client: KeyValueRepository[Seq[Long], Long, ml.FloatTensor],
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TwhinRebuildUserEngagement")

  override val features: Set[Feature[_, _]] = Set(TwhinRebuildUserEngagementFeature)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("key/notFound")
  private val keyFailureCounter = scopedStatsReceiver.counter("key/failure")
  private val keyTotalCounter = scopedStatsReceiver.counter("key/total")

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    Stitch.callFuture(client(Seq(userId))).map { results =>
      keyTotalCounter.incr()
      val embedding: Option[ml.FloatTensor] = results(userId) match {
        case Return(value) =>
          if (value.exists(_.floats.nonEmpty)) keyFoundCounter.incr()
          else keyNotFoundCounter.incr()
          value
        case Throw(_) =>
          keyFailureCounter.incr()
          None
        case _ =>
          None
      }

      val dataRecord =
        TwhinRebuildUserEngagementEmbeddingsAdapter.adaptToDataRecords(embedding).asScala.head

      FeatureMapBuilder()
        .add(TwhinRebuildUserEngagementFeature, dataRecord)
        .build()
    }
  }

}
