package com.twitter.home_mixer.functional_component.decorator

import com.twitter.home_mixer.functional_component.decorator.KeywordTrendsModuleCandidateDecorator.Component
import com.twitter.home_mixer.functional_component.decorator.KeywordTrendsModuleCandidateDecorator.KeywordTrendsEntryNamespace
import com.twitter.home_mixer.functional_component.decorator.KeywordTrendsModuleCandidateDecorator.TrendsUrl
import com.twitter.home_mixer.functional_component.decorator.builder.KeywordTrendMetaDescriptionBuilder
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemInModuleDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.trend.TrendCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.trend.TrendPromotedMetadataBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.trend.TrendRankBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.StaticUrlBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.Str
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ModuleHeaderBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.StaticModuleDisplayTypeBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.twitter.product_mixer.component_library.model.candidate.trends_events.UnifiedTrendCandidate
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ExternalUrl
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Vertical
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Singleton

object KeywordTrendsModuleCandidateDecorator {
  val KeywordTrendsEntryNamespace = EntryNamespace("keyword-trends")
  private val Component = "trend"
  private val TrendsUrl = ""
}

@Singleton
class KeywordTrendsModuleCandidateDecorator @Inject() (
  keywordTrendMetaDescriptionBuilder: KeywordTrendMetaDescriptionBuilder,
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings) {

  private val clientEventInfoBuilder =
    ClientEventInfoBuilder[PipelineQuery, UnifiedTrendCandidate](Component)

  private val trendItemBuilder = TrendCandidateUrtItemBuilder(
    keywordTrendMetaDescriptionBuilder,
    TrendPromotedMetadataBuilder,
    clientEventInfoBuilder,
    Some(TrendRankBuilder[PipelineQuery, UnifiedTrendCandidate]())
  )
  private val trendItemDecorator = UrtItemCandidateDecorator(trendItemBuilder)

  private val moduleHeaderBuilder = ModuleHeaderBuilder(
    textBuilder = Str(
      text = externalStrings.TrendingString,
      stringCenter = stringCenter
    ),
    isSticky = Some(false),
    urlBuilder = Some(StaticUrlBuilder(TrendsUrl, ExternalUrl))
  )
  private val moduleBuilder = TimelineModuleBuilder(
    entryNamespace = KeywordTrendsEntryNamespace,
    displayTypeBuilder = StaticModuleDisplayTypeBuilder(Vertical),
    clientEventInfoBuilder = clientEventInfoBuilder,
    headerBuilder = Some(moduleHeaderBuilder)
  )

  val moduleDecorator = UrtItemInModuleDecorator(trendItemDecorator, moduleBuilder)
}
