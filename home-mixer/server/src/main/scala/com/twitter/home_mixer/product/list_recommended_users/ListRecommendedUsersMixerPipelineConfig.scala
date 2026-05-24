package com.twitter.home_mixer.product.list_recommended_users

import com.twitter.home_mixer.product.list_recommended_users.feature_hydrator.RecentListMembersQueryFeatureHydrator
import com.twitter.home_mixer.product.list_recommended_users.gate.ViewerIsListOwnerGate
import com.twitter.home_mixer.product.list_recommended_users.model.ListRecommendedUsersFeatures.IsGizmoduckValidUserFeature
import com.twitter.home_mixer.product.list_recommended_users.model.ListRecommendedUsersFeatures.IsSGSValidUserFeature
import com.twitter.home_mixer.product.list_recommended_users.model.ListRecommendedUsersQuery
import com.twitter.home_mixer.product.list_recommended_users.param.ListRecommendedUsersParam.ExcludedIdsMaxLengthParam
import com.twitter.home_mixer.product.list_recommended_users.param.ListRecommendedUsersParam.ServerMaxResultsParam
import com.twitter.product_mixer.component_library.premarshaller.urt.UrtDomainMarshaller
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.AddEntriesWithReplaceInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceAllEntries
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceEntryInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.StaticTimelineScribeConfigBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.UnorderedExcludeIdsBottomCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.UrtMetadataBuilder
import com.twitter.product_mixer.component_library.selector.DropFilteredCandidates
import com.twitter.product_mixer.component_library.selector.DropMaxCandidates
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.UrtTransportMarshaller
import com.twitter.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.MixerPipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.UserItem
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mixer.core.pipeline.mixer.MixerPipelineConfig
import com.twitter.timelines.render.{thriftscala => urt}

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRecommendedUsersMixerPipelineConfig @Inject() (
  listMemberBasedUsersCandidatePipelineConfig: ListMemberBasedUsersCandidatePipelineConfig,
  blenderUsersCandidatePipelineConfig: BlenderUsersCandidatePipelineConfig,
  viewerIsListOwnerGate: ViewerIsListOwnerGate,
  recentListMembersQueryFeatureHydrator: RecentListMembersQueryFeatureHydrator,
  urtTransportMarshaller: UrtTransportMarshaller)
    extends MixerPipelineConfig[ListRecommendedUsersQuery, Timeline, urt.TimelineResponse] {

  override val identifier: MixerPipelineIdentifier = MixerPipelineIdentifier("ListRecommendedUsers")

  override val gates = Seq(viewerIsListOwnerGate)

  override val fetchQueryFeatures: Seq[QueryFeatureHydrator[ListRecommendedUsersQuery]] =
    Seq(recentListMembersQueryFeatureHydrator)

  override val candidatePipelines: Seq[
    CandidatePipelineConfig[ListRecommendedUsersQuery, _, _, _]
  ] = Seq(
    listMemberBasedUsersCandidatePipelineConfig,
    blenderUsersCandidatePipelineConfig
  )

  private val candidatePipelineIdentifiers = Set(
    listMemberBasedUsersCandidatePipelineConfig.identifier,
    blenderUsersCandidatePipelineConfig.identifier
  )

  override val resultSelectors: Seq[Selector[ListRecommendedUsersQuery]] = Seq(
    DropFilteredCandidates(
      candidatePipelines = candidatePipelineIdentifiers,
      filter = candidate =>
        candidate.features.getOrElse(IsSGSValidUserFeature, false) &&
          candidate.features.getOrElse(IsGizmoduckValidUserFeature, false)
    ),
    DropMaxCandidates(
      candidatePipelines = candidatePipelineIdentifiers,
      maxSelectionsParam = ServerMaxResultsParam),
    InsertAppendResults(candidatePipelineIdentifiers)
  )

  override val domainMarshaller: DomainMarshaller[ListRecommendedUsersQuery, Timeline] = {
    val instructionBuilders = Seq(
      ReplaceEntryInstructionBuilder(ReplaceAllEntries),
      AddEntriesWithReplaceInstructionBuilder()
    )

    val metadataBuilder = UrtMetadataBuilder(
      title = None,
      scribeConfigBuilder = Some(
        StaticTimelineScribeConfigBuilder(
          TimelineScribeConfig(
            page = Some("list_recommended_users"),
            section = None,
            entityToken = None)))
    )

    val excludeIdsSelector: PartialFunction[UniversalNoun[_], Long] = {
      case item: UserItem => item.id
    }

    val cursorBuilder = UnorderedExcludeIdsBottomCursorBuilder(
      excludedIdsMaxLengthParam = ExcludedIdsMaxLengthParam,
      excludeIdsSelector = excludeIdsSelector)

    UrtDomainMarshaller(
      instructionBuilders = instructionBuilders,
      metadataBuilder = Some(metadataBuilder),
      cursorBuilders = Seq(cursorBuilder)
    )
  }

  override val transportMarshaller: TransportMarshaller[Timeline, urt.TimelineResponse] =
    urtTransportMarshaller
}
