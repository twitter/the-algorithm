package com.X.home_mixer.product.scored_tweets.feature_hydrator

import com.X.finagle.stats.StatsReceiver
import com.X.home_mixer.param.HomeMixerInjectionNames.UserLanguagesRepository
import com.X.home_mixer.util.ObservedKeyValueResultHandler
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.search.common.constants.{thriftscala => scc}
import com.X.servo.repository.KeyValueRepository
import com.X.stitch.Stitch
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object UserLanguagesFeature extends Feature[PipelineQuery, Seq[scc.ThriftLanguage]]

@Singleton
case class UserLanguagesFeatureHydrator @Inject() (
  @Named(UserLanguagesRepository) client: KeyValueRepository[Seq[Long], Long, Seq[
    scc.ThriftLanguage
  ]],
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("UserLanguages")

  override val features: Set[Feature[_, _]] = Set(UserLanguagesFeature)

  override val statScope: String = identifier.toString

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val key = query.getRequiredUserId
    Stitch.callFuture(client(Seq(key))).map { result =>
      val feature =
        observedGet(key = Some(key), keyValueResult = result).map(_.getOrElse(Seq.empty))
      FeatureMapBuilder()
        .add(UserLanguagesFeature, feature)
        .build()
    }
  }
}
