package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.UserEngagedLanguagesFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.language.user.NormalizedEngagedTweetLanguagesOnUserClientColumn
import com.twitter.language.types.{thriftscala => lg}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEngagedLanguagesFeatureHydrator @Inject() (
  engagedLanguageOnUserColumn: NormalizedEngagedTweetLanguagesOnUserClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserEngagedLanguages")

  override def features: Set[Feature[_, _]] = Set(UserEngagedLanguagesFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    engagedLanguageOnUserColumn.fetcher
      .fetch(query.getRequiredUserId, Some(lg.LanguageType.User)).map { result =>
        FeatureMapBuilder()
          .add(UserEngagedLanguagesFeature, result.v.getOrElse(Seq.empty).toSet)
          .build()
      }
  }
}
