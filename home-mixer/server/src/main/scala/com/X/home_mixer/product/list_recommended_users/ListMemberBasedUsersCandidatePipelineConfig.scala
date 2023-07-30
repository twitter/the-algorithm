package com.X.home_mixer.product.list_recommended_users

import com.X.hermit.candidate.{thriftscala => t}
import com.X.home_mixer.product.list_recommended_users.candidate_source.SimilarityBasedUsersCandidateSource
import com.X.home_mixer.product.list_recommended_users.feature_hydrator.IsGizmoduckValidUserFeatureHydrator
import com.X.home_mixer.product.list_recommended_users.feature_hydrator.IsListMemberFeatureHydrator
import com.X.home_mixer.product.list_recommended_users.feature_hydrator.IsSGSValidUserFeatureHydrator
import com.X.home_mixer.product.list_recommended_users.feature_hydrator.RecentListMembersFeature
import com.X.home_mixer.product.list_recommended_users.filter.DropMaxCandidatesByAggregatedScoreFilter
import com.X.home_mixer.product.list_recommended_users.filter.PreviouslyServedUsersFilter
import com.X.home_mixer.product.list_recommended_users.model.ListRecommendedUsersFeatures.IsListMemberFeature
import com.X.home_mixer.product.list_recommended_users.model.ListRecommendedUsersQuery
import com.X.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.X.product_mixer.component_library.decorator.urt.builder.item.user.UserCandidateUrtItemBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.X.product_mixer.component_library.filter.PredicateFeatureFilter
import com.X.product_mixer.component_library.gate.NonEmptySeqFeatureGate
import com.X.product_mixer.component_library.model.candidate.UserCandidate
import com.X.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.X.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.X.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListMemberBasedUsersCandidatePipelineConfig @Inject() (
  similarityBasedUsersCandidateSource: SimilarityBasedUsersCandidateSource,
  isGizmoduckValidUserFeatureHydrator: IsGizmoduckValidUserFeatureHydrator,
  isListMemberFeatureHydrator: IsListMemberFeatureHydrator,
  isSGSValidUserFeatureHydrator: IsSGSValidUserFeatureHydrator)
    extends CandidatePipelineConfig[
      ListRecommendedUsersQuery,
      Seq[Long],
      t.Candidate,
      UserCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ListMemberBasedUsers")

  override val gates: Seq[Gate[ListRecommendedUsersQuery]] =
    Seq(NonEmptySeqFeatureGate(RecentListMembersFeature))

  override val queryTransformer: CandidatePipelineQueryTransformer[ListRecommendedUsersQuery, Seq[
    Long
  ]] = { query =>
    query.features.map(_.getOrElse(RecentListMembersFeature, Seq.empty)).getOrElse(Seq.empty)
  }

  override val candidateSource: BaseCandidateSource[Seq[Long], t.Candidate] =
    similarityBasedUsersCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.Candidate]
  ] = Seq(ListMemberBasedUsersResponseFeatureTransfromer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.Candidate,
    UserCandidate
  ] = { candidate =>
    UserCandidate(id = candidate.userId)
  }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ListRecommendedUsersQuery, UserCandidate, _]
  ] = Seq(isListMemberFeatureHydrator)

  override val filters: Seq[Filter[ListRecommendedUsersQuery, UserCandidate]] =
    Seq(
      PreviouslyServedUsersFilter,
      PredicateFeatureFilter.fromPredicate(
        FilterIdentifier("IsListMember"),
        shouldKeepCandidate = { features => !features.getOrElse(IsListMemberFeature, false) }
      ),
      DropMaxCandidatesByAggregatedScoreFilter
    )

  override val postFilterFeatureHydration: Seq[
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
