package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.param.HomeMixerInjectionNames.UserLanguagesStore
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.search.common.constants.{thriftscala => scc}
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object UserLanguagesFeature extends Feature[PipelineQuery, Seq[scc.ThriftLanguage]]

@Singleton
case class UserLanguagesFeatureHydrator @Inject() (
  @Named(UserLanguagesStore) store: ReadableStore[Long, Seq[scc.ThriftLanguage]])
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("UserLanguages")

  override val features: Set[Feature[_, _]] = Set(UserLanguagesFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    Stitch.callFuture(store.get(query.getRequiredUserId)).map { languages =>
      FeatureMapBuilder()
        .add(UserLanguagesFeature, languages.getOrElse(Seq.empty))
        .build()
    }
  }
}
