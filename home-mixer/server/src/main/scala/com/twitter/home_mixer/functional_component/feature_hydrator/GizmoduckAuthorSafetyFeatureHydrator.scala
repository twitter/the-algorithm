package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIsBlueVerifiedFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableGizmoduckAuthorSafetyFeatureHydratorParam
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.stitch.gizmoduck.Gizmoduck
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GizmoduckAuthorSafetyFeatureHydrator @Inject() (gizmoduck: Gizmoduck)
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("GizmoduckAuthorSafety")

  override val features: Set[Feature[_, _]] = Set(AuthorIsBlueVerifiedFeature)

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableGizmoduckAuthorSafetyFeatureHydratorParam)

  private val queryFields: Set[gt.QueryFields] = Set(gt.QueryFields.Safety)

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    val authorIdOption = existingFeatures.getOrElse(AuthorIdFeature, None)

    val blueVerifiedStitch = authorIdOption
      .map { authorId =>
        gizmoduck
          .getUserById(
            userId = authorId,
            queryFields = queryFields
          )
          .map { _.safety.flatMap(_.isBlueVerified).getOrElse(false) }
      }.getOrElse(Stitch.False)

    blueVerifiedStitch.map { isBlueVerified =>
      FeatureMapBuilder()
        .add(AuthorIsBlueVerifiedFeature, isBlueVerified)
        .build()
    }
  }
}
