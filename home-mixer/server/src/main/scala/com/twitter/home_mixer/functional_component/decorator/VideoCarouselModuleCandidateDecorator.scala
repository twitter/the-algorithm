package com.twitter.home_mixer.functional_component.decorator

import com.twitter.home_mixer.functional_component.decorator.builder.HomeClientEventInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.FeedbackActionInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.FeedbackStrings
import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mixer.model.HomeFeatures.VideoDisplayTypeFeature
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemInModuleDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.Str
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.FeatureModuleDisplayTypeBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.model.presentation.urt.UrtItemPresentation
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.decorator.Decoration
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleFooterBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleHeaderBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.Media
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.MediaShort
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Carousel
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Classic
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.CompactCarousel
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.MediaHighCarousel
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleFooter
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleHeader
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stitch.Stitch
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

object VideoCarouselModuleCandidateDecorator {
  val entryNamespaceString = "video-carousel"
  val deepLink = "https://twitter.com/i/video"
}

@Singleton
class VideoCarouselModuleCandidateDecorator @Inject() (
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  externalStrings: HomeMixerExternalStrings,
  feedbackStrings: FeedbackStrings) {
  import VideoCarouselModuleCandidateDecorator._

  private val stringCenter = stringCenterProvider.get()

  def build(
    enableVideoCarouselFooter: Param[Boolean]
  ): UrtItemInModuleDecorator[PipelineQuery, TweetCandidate, Nothing] = {
    val clientEventInfoBuilder = HomeClientEventInfoBuilder[PipelineQuery, TweetCandidate]()
    val tweetItemDecorator =
      VideoItemCandidateDecorator(clientEventInfoBuilder, homeFeedbackActionInfoBuilder)
    val headerBuilder =
      VideoCarouselModuleHeaderBuilder(
        stringCenter,
        externalStrings,
        deepLink,
        enableVideoCarouselFooter)

    val footerBuilder =
      VideoCarouselModuleFooterBuilder(
        stringCenter,
        externalStrings,
        deepLink,
        enableVideoCarouselFooter)

    val videoCarouselModuleBuilder = TimelineModuleBuilder(
      entryNamespace = EntryNamespace(entryNamespaceString),
      clientEventInfoBuilder = clientEventInfoBuilder,
      displayTypeBuilder =
        FeatureModuleDisplayTypeBuilder(VideoDisplayTypeFeature, MediaHighCarousel),
      headerBuilder = Some(headerBuilder),
      footerBuilder = Some(footerBuilder),
      feedbackActionInfoBuilder = Some(
        FeedbackActionInfoBuilder(
          seeLessOftenFeedbackString = feedbackStrings.seeLessOftenFeedbackString,
          seeLessOftenConfirmationFeedbackString = feedbackStrings.seeLessOftenFeedbackString,
          stringCenter = stringCenter,
          encodedFeedbackRequest = None
        )
      ),
      showMoreBehaviorBuilder = None
    )

    UrtItemInModuleDecorator(tweetItemDecorator, videoCarouselModuleBuilder)
  }
}

case class VideoItemCandidateDecorator(
  clientEventInfoBuilder: HomeClientEventInfoBuilder[PipelineQuery, TweetCandidate],
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder)
    extends CandidateDecorator[PipelineQuery, TweetCandidate] {

  private val MediaVideoItemBuilder = TweetCandidateUrtItemBuilder[PipelineQuery, TweetCandidate](
    clientEventInfoBuilder = clientEventInfoBuilder,
    displayType = Media,
    feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder)
  )
  private val MediaShortVideoItemBuilder =
    TweetCandidateUrtItemBuilder[PipelineQuery, TweetCandidate](
      clientEventInfoBuilder = clientEventInfoBuilder,
      displayType = MediaShort,
      feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder)
    )

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[Decoration]] = {
    val moduleDisplayType = candidates
      .flatMap { candidate =>
        candidate.features.getOrElse(VideoDisplayTypeFeature, None)
      }.headOption.getOrElse(Carousel)
    // If display type of first video is Carousel, we need a horizontal video module
    // If display type of first video is CompactCarousel, we need a vertical video module
    val builder = moduleDisplayType match {
      case CompactCarousel => MediaShortVideoItemBuilder
      case _ => MediaVideoItemBuilder
    }
    val candidatePresentations = candidates.map { candidate =>
      val itemPresentation = UrtItemPresentation(
        timelineItem = builder(query, candidate.candidate, candidate.features)
      )

      Decoration(candidate.candidate, itemPresentation)
    }

    Stitch.value(candidatePresentations)
  }
}

case class VideoCarouselModuleHeaderBuilder(
  stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings,
  deepLink: String,
  enableVideoCarouselFooter: Param[Boolean])
    extends BaseModuleHeaderBuilder[PipelineQuery, TweetCandidate] {

  def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Option[ModuleHeader] = {
    candidates.headOption.map { candidate =>
      val title = Str(externalStrings.VideoCarouselHeaderString, stringCenter)
        .apply(query, candidate.candidate, candidate.features)
      // If footer is enabled, do not add landing URL so footer can have the deep link
      val landingUrl = if (!query.params(enableVideoCarouselFooter)) {
        Some(Url(ExternalUrl, deepLink))
      } else {
        None
      }

      ModuleHeader(
        text = title,
        sticky = Some(false),
        customIcon = None,
        socialContext = None,
        icon = None,
        moduleHeaderDisplayType = Classic,
        landingUrl = landingUrl
      )
    }
  }
}

case class VideoCarouselModuleFooterBuilder(
  stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings,
  deepLink: String,
  enableVideoCarouselFooter: Param[Boolean])
    extends BaseModuleFooterBuilder[PipelineQuery, TweetCandidate] {

  def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Option[ModuleFooter] = {
    candidates.headOption.flatMap { candidate =>
      // Add footer only if footer is enabled
      if (query.params(enableVideoCarouselFooter)) {
        val footerText = Str(externalStrings.VideoCarouselFooterString, stringCenter)
          .apply(query, candidate.candidate, candidate.features)
        Some(
          ModuleFooter(
            text = footerText,
            landingUrl = Some(Url(ExternalUrl, deepLink))
          ))
      } else {
        None
      }
    }
  }
}
