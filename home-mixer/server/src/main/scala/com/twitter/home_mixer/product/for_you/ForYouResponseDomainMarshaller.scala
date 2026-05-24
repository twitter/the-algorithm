package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.decorator.urt.builder.AddEntriesWithReplaceAndShowAlertAndCoverInstructionBuilder
import com.twitter.home_mixer.model.ClearCacheIncludeInstruction
import com.twitter.home_mixer.model.NavigationIncludeInstruction
import com.twitter.home_mixer.model.request.DeviceContext.RequestContext
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.product.for_you.param.ForYouParam.ClearCache
import com.twitter.home_mixer.product.for_you.param.ForYouParam.Navigation
import com.twitter.product_mixer.component_library.premarshaller.urt.UrtDomainMarshaller
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ClearCacheInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.NavigationInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedBottomCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedCursorIdSelector.TweetIdSelector
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedTopCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceAllEntries
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceEntryInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ShowAlertInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ShowCoverInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.StaticTimelineScribeConfigBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.UrtMetadataBuilder
import com.twitter.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mixer.core.model.common.identifier.DomainMarshallerIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig

object ForYouResponseDomainMarshaller extends DomainMarshaller[ForYouQuery, Timeline] {

  override val identifier: DomainMarshallerIdentifier =
    DomainMarshallerIdentifier("ForYouResponse")

  override def apply(
    query: ForYouQuery,
    selections: Seq[CandidateWithDetails]
  ): Timeline = {
    val coldStart =
      query.deviceContext.flatMap(_.requestContextValue).contains(RequestContext.Launch)
    val retainViewportItems =
      if (coldStart && query.params(ClearCache.ColdStartRetainViewportParam)) Some(true) else None

    val instructionBuilders = Seq(
      ClearCacheInstructionBuilder(
        includeInstruction = ClearCacheIncludeInstruction(
          ClearCache.PtrEnableParam,
          ClearCache.ColdStartEnableParam,
          ClearCache.WarmStartEnableParam,
          ClearCache.ManualRefreshEnableParam,
          ClearCache.NavigateEnableParam,
          ClearCache.MinEntriesParam
        ),
        retainViewportItems = retainViewportItems
      ),
      ReplaceEntryInstructionBuilder(ReplaceAllEntries),
      // excludes alert, cover, and replace candidates
      AddEntriesWithReplaceAndShowAlertAndCoverInstructionBuilder(),
      ShowAlertInstructionBuilder(),
      ShowCoverInstructionBuilder(),
      NavigationInstructionBuilder(
        NavigationIncludeInstruction(
          Navigation.PtrEnableParam,
          Navigation.ColdStartEnableParam,
          Navigation.WarmStartEnableParam,
          Navigation.ManualRefreshEnableParam,
          Navigation.NavigateEnableParam
        ))
    )

    val topCursorBuilder = OrderedTopCursorBuilder(TweetIdSelector)
    val bottomCursorBuilder = OrderedBottomCursorBuilder(TweetIdSelector)

    val scribeConfigBuilder =
      StaticTimelineScribeConfigBuilder(TimelineScribeConfig(page = Some("for_you"), None, None))
    val metadataBuilder = UrtMetadataBuilder(scribeConfigBuilder = Some(scribeConfigBuilder))

    val domainMarshaller = UrtDomainMarshaller(
      instructionBuilders = instructionBuilders,
      metadataBuilder = Some(metadataBuilder),
      cursorBuilders = Seq(topCursorBuilder, bottomCursorBuilder)
    )

    domainMarshaller(query, selections)
  }
}
