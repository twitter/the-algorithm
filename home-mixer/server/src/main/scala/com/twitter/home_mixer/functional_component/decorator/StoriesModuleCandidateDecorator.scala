package com.twitter.home_mixer.functional_component.decorator

import com.twitter.home_mixer.functional_component.decorator.StoriesModuleCandidateDecorator.Component
import com.twitter.home_mixer.functional_component.decorator.StoriesModuleCandidateDecorator.TrendsEntryNamespace
import com.twitter.home_mixer.functional_component.decorator.StoriesModuleCandidateDecorator.TrendsUrl
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemInModuleDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.trend.AiTrendCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.StaticUrlBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.Str
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ModuleFooterBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ModuleHeaderBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.StaticModuleDisplayTypeBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.twitter.product_mixer.component_library.model.candidate.trends_events.UnifiedTrendCandidate
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.DeepLink
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Vertical
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Singleton

object StoriesModuleCandidateDecorator {
  val TrendsEntryNamespace = EntryNamespace("stories")
  private val Component = "stories"
  private val TrendsUrl = ""
}

@Singleton
class StoriesModuleCandidateDecorator @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings) {
  private val clientEventsBuilder =
    ClientEventInfoBuilder[PipelineQuery, UnifiedTrendCandidate](Component)

  private val trendItemBuilder = AiTrendCandidateUrtItemBuilder(clientEventsBuilder)
  private val trendItemDecorator = UrtItemCandidateDecorator(trendItemBuilder)

  private val moduleHeaderBuilder = ModuleHeaderBuilder(
    textBuilder = Str(
      text = externalStrings.NewsHeaderString,
      stringCenter = stringCenter
    ),
    isSticky = Some(false),
    urlBuilder = Some(StaticUrlBuilder(TrendsUrl, DeepLink))
  )

  private val moduleFooterBuilder = ModuleFooterBuilder(
    textBuilder = Str(
      text = externalStrings.NewsFooterString,
      stringCenter = stringCenter
    ),
    urlBuilder = Some(StaticUrlBuilder(TrendsUrl, DeepLink))
  )

  private val moduleBuilder = TimelineModuleBuilder(
    entryNamespace = TrendsEntryNamespace,
    displayTypeBuilder = StaticModuleDisplayTypeBuilder(Vertical),
    clientEventInfoBuilder = clientEventsBuilder,
    headerBuilder = Some(moduleHeaderBuilder),
    footerBuilder = Some(moduleFooterBuilder)
  )

  val moduleDecorator: UrtItemInModuleDecorator[PipelineQuery, UnifiedTrendCandidate, Nothing] =
    UrtItemInModuleDecorator(trendItemDecorator, moduleBuilder)
}
