package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.UserLanguagesRepository
import com.twitter.home_mitwitterer.util.ObservedKeyValueResultHandler
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.search.common.constants.{thriftscala => scc}
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object UserLanguagesFeature etwittertends Feature[PipelineQuery, Seq[scc.ThriftLanguage]]

@Singleton
case class UserLanguagesFeatureHydrator @Inject() (
  @Named(UserLanguagesRepository) client: KeyValueRepository[Seq[Long], Long, Seq[
    scc.ThriftLanguage
  ]],
  statsReceiver: StatsReceiver)
    etwittertends QueryFeatureHydrator[PipelineQuery]
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
