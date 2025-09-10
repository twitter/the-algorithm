package com.twitter.home_mixer.functional_component.decorator

import com.twitter.home_mixer.functional_component.decorator.TuneFeedModuleCandidateDecorator.EntryNamespaceString
import com.twitter.home_mixer.functional_component.decorator.TuneFeedModuleCandidateDecorator.GrokTopicBaseUrl
import com.twitter.home_mixer.functional_component.decorator.builder.HomeClientEventInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.TuneFeedFeedbackActionInfoBuilder
import com.twitter.home_mixer.model.HomeFeatures.CurrentDisplayedGrokTopicFeature
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemInModuleDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.icon.HorizonIconBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.StaticUrlBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.social_context.GeneralModuleSocialContextBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.ModuleStrStatic
import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.StrStatic
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ModuleFooterBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ModuleFooterDisplayTypeBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ModuleHeaderBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ModuleHeaderDisplayTypeBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.StaticModuleDisplayTypeBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.decorator.Decoration
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.FeedbackStroke
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ExternalUrl
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.TextOnlyGeneralContextType
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Feedback
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.FeedbackList
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Separator
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.util.Base64UrlSafeStringEncoder
import java.nio.charset.Charset
import javax.inject.Inject
import javax.inject.Singleton

object TuneFeedModuleCandidateDecorator {
  val EntryNamespaceString = "tune-feed"
  val GrokTopicBaseUrl = ""
}

@Singleton
class TuneFeedModuleCandidateDecorator @Inject() (
  tuneFeedFeedbackActionInfoBuilder: TuneFeedFeedbackActionInfoBuilder)
    extends CandidateDecorator[PipelineQuery, TweetCandidate] {

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[Decoration]] = {

    val clientEventInfoBuilder = HomeClientEventInfoBuilder[PipelineQuery, TweetCandidate]()

    val tuneFeedItemBuilder =
      TweetCandidateUrtItemBuilder[PipelineQuery, TweetCandidate](
        clientEventInfoBuilder = clientEventInfoBuilder,
        feedbackActionInfoBuilder = Some(tuneFeedFeedbackActionInfoBuilder)
      )

    val tuneFeedItemDecorator =
      UrtItemCandidateDecorator(tuneFeedItemBuilder)

    val tuneFeedModuleBuilder = {
      val generalModuleSocialContextBuilder = GeneralModuleSocialContextBuilder(
        textBuilder = ModuleStrStatic(text = "Tune your feed"),
        contextType = TextOnlyGeneralContextType
      )

      // Safe .get, enforced in TuneFeedModuleGate
      val topicId = query.features.get.getOrElse(CurrentDisplayedGrokTopicFeature, None).get._1
      val topicUrl = GrokTopicBaseUrl

      val tuneFeedModuleHeaderBuilder = ModuleHeaderBuilder(
        textBuilder = StrStatic(text = query.features.get
          .getOrElse(CurrentDisplayedGrokTopicFeature, None).map(_._2).getOrElse(
            "Help us show you more of what you love")),
        moduleSocialContextBuilder = Some(generalModuleSocialContextBuilder),
        moduleHeaderIconBuilder = Some(HorizonIconBuilder(FeedbackStroke)),
        moduleHeaderDisplayTypeBuilder = ModuleHeaderDisplayTypeBuilder(Feedback),
        urlBuilder = Some(StaticUrlBuilder(topicUrl, ExternalUrl)),
        isSticky = Some(false)
      )

      val tuneFeedModuleFooterBuilder = ModuleFooterBuilder(
        textBuilder = StrStatic(text = ""),
        urlBuilder = None,
        moduleFooterDisplayTypeBuilder = ModuleFooterDisplayTypeBuilder(Separator)
      )

      TimelineModuleBuilder(
        entryNamespace = EntryNamespace(EntryNamespaceString),
        clientEventInfoBuilder = clientEventInfoBuilder,
        displayTypeBuilder = StaticModuleDisplayTypeBuilder(FeedbackList),
        headerBuilder = Some(tuneFeedModuleHeaderBuilder),
        footerBuilder = Some(tuneFeedModuleFooterBuilder)
      )
    }
    UrtItemInModuleDecorator(tuneFeedItemDecorator, tuneFeedModuleBuilder)
      .apply(query, candidates)
  }
}
