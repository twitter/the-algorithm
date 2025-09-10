package com.twitter.home_mixer.product.for_you

import com.twitter.communities_mixer.{thriftscala => cmt}
import com.twitter.home_mixer.functional_component.decorator.urt.builder.FeedbackStrings
import com.twitter.home_mixer.functional_component.filter.DropMaxCandidatesFilter
import com.twitter.home_mixer.functional_component.gate.DismissFatigueGate
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.model.HomeFeatures.DismissInfoFeature
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.product.for_you.param.ForYouParam.CommunitiesToJoinDisplayTypeIdParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.CommunitiesToJoinMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableCommunitiesToJoinCandidatePipelineParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.MaxCommunitiesToJoinCandidatesParam
import com.twitter.product_mixer.component_library.candidate_source.communities.CommunitiesToJoinCandidateSource
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.IsAvailableToJoinFeature
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.communities.IsAvailableToJoinFeatureHydrator
import com.twitter.product_mixer.component_library.filter.FeatureFilter
import com.twitter.product_mixer.component_library.gate.DefinedUserIdGate
import com.twitter.product_mixer.component_library.gate.communities.CommunitiesJoinLimitGate
import com.twitter.product_mixer.component_library.model.candidate.CommunityCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.communities_to_join.CommunitiesToJoinCandidateDecorator
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.marshaller.request.ClientContextMarshaller
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.HasPipelineCursor
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.timelineservice.suggests.{thriftscala => st}
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ForYouCommunitiesToJoinCandidatePipelineConfig @Inject() (
  communitiesToJoinCandidateSource: CommunitiesToJoinCandidateSource,
  communitiesJoinLimitGate: CommunitiesJoinLimitGate,
  isAvailableToJoinFeatureHydrator: IsAvailableToJoinFeatureHydrator,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  externalStrings: HomeMixerExternalStrings,
  feedbackStrings: FeedbackStrings)
    extends CandidatePipelineConfig[
      PipelineQuery with HasPipelineCursor[_],
      cmt.CommunitiesMixerRequest,
      Long,
      CommunityCandidate
    ] {

  private val stringCenter = stringCenterProvider.get()

  private val MaxCommunities = 10

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(
    EnableCommunitiesToJoinCandidatePipelineParam)

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouCommunitiesToJoin")

  private val IsAvailableToJoinFilterId = "IsAvailableToJoin"

  override val gates = Seq(
    DefinedUserIdGate,
    RateLimitGate,
    TimelinesPersistenceStoreLastInjectionGate(
      CommunitiesToJoinMinInjectionIntervalParam,
      EntityIdType.CommunityModule
    ),
    DismissFatigueGate(st.SuggestType.CommunityToJoin, DismissInfoFeature),
    communitiesJoinLimitGate
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    cmt.CommunitiesMixerRequest
  ] = { query =>
    cmt.CommunitiesMixerRequest(
      clientContext = ClientContextMarshaller(query.clientContext),
      product = cmt.Product.CommunityRecs,
      productContext = Some(
        cmt.ProductContext.CommunityRecs(
          cmt.CommunityRecs(displayFormat = cmt.DisplayFormat.Module))),
      cursor = None,
      maxResults = Some(MaxCommunities),
      debugParams = None,
      displayLocation = None
    )
  }

  override val candidateSource: CandidateSource[cmt.CommunitiesMixerRequest, Long] =
    communitiesToJoinCandidateSource

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[PipelineQuery, CommunityCandidate, _]
  ] = Seq(isAvailableToJoinFeatureHydrator)

  override def filters: Seq[Filter[PipelineQuery, CommunityCandidate]] = Seq(
    FeatureFilter
      .fromFeature(FilterIdentifier(IsAvailableToJoinFilterId), IsAvailableToJoinFeature),
    DropMaxCandidatesFilter(MaxCommunitiesToJoinCandidatesParam)
  )

  override val resultTransformer: CandidatePipelineResultsTransformer[Long, CommunityCandidate] = {
    communityResult => CommunityCandidate(id = communityResult)
  }

  override val decorator: Option[
    CandidateDecorator[PipelineQuery, CommunityCandidate]
  ] = {
    Some(
      CommunitiesToJoinCandidateDecorator(
        moduleDisplayTypeParam = CommunitiesToJoinDisplayTypeIdParam,
        stringCenter = stringCenter,
        headerString = externalStrings.CommunityToJoinHeaderString,
        footerString = Some(externalStrings.CommunityToJoinFooterString),
        seeLessOftenString = Some(feedbackStrings.seeLessOftenFeedbackString),
        seeLessOftenConfirmationString =
          Some(feedbackStrings.seeLessOftenConfirmationFeedbackString)
      ))
  }
}
