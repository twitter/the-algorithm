package com.twitter.home_mixer.product.list_recommended_users.feature_hydrator

import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.home_mixer.product.list_recommended_users.model.ListFeatures.GizmoduckUserFeature
import com.twitter.product_mixer.component_library.model.candidate.UserCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.spam.rtf.{thriftscala => rtf}
import com.twitter.stitch.Stitch
import com.twitter.stitch.gizmoduck.Gizmoduck
import com.twitter.util.Return

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GizmoduckUserFeatureHydrator @Inject() (gizmoduck: Gizmoduck)
    extends BulkCandidateFeatureHydrator[PipelineQuery, UserCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("GizmoduckUser")

  override val features: Set[Feature[_, _]] = Set(GizmoduckUserFeature)

  private val queryFields: Set[gt.QueryFields] = Set(gt.QueryFields.Safety)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[UserCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val context = gt.LookupContext(
      forUserId = query.getOptionalUserId,
      includeProtected = true,
      safetyLevel = Some(rtf.SafetyLevel.Recommendations)
    )
    val userIds = candidates.map(_.candidate.id)

    Stitch
      .collectToTry(
        userIds.map(userId => gizmoduck.getUserById(userId, queryFields, context))).map {
        userResults =>
          val idToUserMap = userResults
            .collect {
              case Return(user) => user
            }.map(user => user.id -> user).toMap

          candidates.map { candidate =>
            FeatureMapBuilder()
              .add(GizmoduckUserFeature, idToUserMap.get(candidate.candidate.id))
              .build()
          }
      }
  }
}
