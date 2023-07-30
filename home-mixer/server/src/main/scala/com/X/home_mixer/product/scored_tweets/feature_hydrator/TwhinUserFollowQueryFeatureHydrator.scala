package com.X.home_mixer.product.scored_tweets.feature_hydrator

import com.X.finagle.stats.StatsReceiver
import com.X.home_mixer.param.HomeMixerInjectionNames.TwhinUserFollowFeatureRepository
import com.X.home_mixer.product.scored_tweets.feature_hydrator.adapters.twhin_embeddings.TwhinUserFollowEmbeddingsAdapter
import com.X.ml.api.DataRecord
import com.X.ml.api.{thriftscala => ml}
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.X.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.servo.repository.KeyValueRepository
import com.X.stitch.Stitch
import com.X.util.Return
import com.X.util.Throw
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TwhinUserFollowFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwhinUserFollowQueryFeatureHydrator @Inject() (
  @Named(TwhinUserFollowFeatureRepository)
  client: KeyValueRepository[Seq[Long], Long, ml.FloatTensor],
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TwhinUserFollow")

  override val features: Set[Feature[_, _]] = Set(TwhinUserFollowFeature)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyLossCounter = scopedStatsReceiver.counter("key/loss")
  private val keyFailureCounter = scopedStatsReceiver.counter("key/failure")

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    Stitch.callFuture(client(Seq(userId))).map { results =>
      val embedding: Option[ml.FloatTensor] = results(userId) match {
        case Return(value) =>
          if (value.exists(_.floats.nonEmpty)) keyFoundCounter.incr()
          else keyLossCounter.incr()
          value
        case Throw(_) =>
          keyFailureCounter.incr()
          None
        case _ =>
          None
      }

      val dataRecord = TwhinUserFollowEmbeddingsAdapter.adaptToDataRecords(embedding).asScala.head

      FeatureMapBuilder()
        .add(TwhinUserFollowFeature, dataRecord)
        .build()
    }
  }
}
