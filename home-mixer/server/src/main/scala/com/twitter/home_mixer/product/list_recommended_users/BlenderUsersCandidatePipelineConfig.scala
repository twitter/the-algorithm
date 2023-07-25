package com.twitter.home_mixer.product.list_recommended_users

import com.twitter.home_mixer.product.list_recommended_users.candidate_source.BlenderUsersCandidateSource
import com.twitter.home_mixer.product.list_recommended_users.feature_hydrator.IsGizmoduckValidUserFeatureHydrator
import com.twitter.home_mixer.product.list_recommended_users.feature_hydrator.IsSGSValidUserFeatureHydrator
import com.twitter.home_mixer.product.list_recommended_users.feature_hydrator.RecentListMembersFeature
import com.twitter.home_mixer.product.list_recommended_users.model.ListRecommendedUsersQuery
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.user.UserCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.gate.EmptySeqFeatureGate
import com.twitter.product_mixer.component_library.model.candidate.UserCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.search.blender.thriftscala.ThriftBlenderRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlenderUsersCandidatePipelineConfig @Inject() (
  blenderUsersCandidateSource: BlenderUsersCandidateSource,
  isGizmoduckValidUserFeatureHydrator: IsGizmoduckValidUserFeatureHydrator,
  isSGSValidUserFeatureHydrator: IsSGSValidUserFeatureHydrator)
    extends CandidatePipelineConfig[
      ListRecommendedUsersQuery,
      ThriftBlenderRequest,
      Long,
      UserCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("BlenderUsers")

  override val gates: Seq[Gate[ListRecommendedUsersQuery]] =
    Seq(EmptySeqFeatureGate(RecentListMembersFeature))

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ListRecommendedUsersQuery,
    ThriftBlenderRequest
  ] = BlenderUsersCandidatePipelineQueryTransformer

  override val candidateSource: BaseCandidateSource[ThriftBlenderRequest, Long] =
    blenderUsersCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[
    Long,
    UserCandidate
  ] = { candidate => UserCandidate(id = candidate) }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ListRecommendedUsersQuery, UserCandidate, _]
  ] = Seq(
    isGizmoduckValidUserFeatureHydrator,
    isSGSValidUserFeatureHydrator
  )

  override val decorator: Option[CandidateDecorator[ListRecommendedUsersQuery, UserCandidate]] = {
    val clientEventInfoBuilder = ClientEventInfoBuilder("user")
    val userItemBuilder = UserCandidateUrtItemBuilder(clientEventInfoBuilder)
    Some(UrtItemCandidateDecorator(userItemBuilder))
  }
}
