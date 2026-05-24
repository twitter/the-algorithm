package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.home_mixer.functional_component.decorator.builder.HomeClientEventInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.builder.HomeTimelinesScoreInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.TweetCarouselType.BookmarkedTweets
import com.twitter.home_mixer.functional_component.decorator.urt.builder.TweetCarouselType.CarouselType
import com.twitter.home_mixer.functional_component.decorator.urt.builder.TweetCarouselType.PinnedTweets
import com.twitter.home_mixer.param.HomeGlobalParams.EnableLandingPage
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemInModuleDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.Str
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.StaticModuleDisplayTypeBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleHeaderBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.Frown
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.CondensedTweet
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Carousel
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Classic
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleHeader
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.stringcenter.client.core.ExternalString
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

trait ClientComponents {
  val clientEventComponent: String
  val entryNamespaceString: String
  val headerLink: Option[String] = None
}

object BookmarksModuleCandidateDecorator extends ClientComponents {
  override val clientEventComponent = "bookmarked-tweet"
  override val entryNamespaceString = "bookmarked-tweet"
  override val headerLink = Some("")
}

object PinnedTweetsModuleCandidateDecorator extends ClientComponents {
  override val clientEventComponent = "pinned-tweets"
  override val entryNamespaceString = "pinned-tweets"
  override val headerLink = Some("")
}

object TweetCarouselType extends Enumeration {
  type CarouselType = Value
  val BookmarkedTweets, PinnedTweets = Value
}

@Singleton
class TweetCarouselModuleCandidateDecorator @Inject() (
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  externalStrings: HomeMixerExternalStrings,
  feedbackStrings: FeedbackStrings) {

  private val stringCenter = stringCenterProvider.get()

  private def getClientComponents(carouselType: CarouselType): ClientComponents = {
    carouselType match {
      case BookmarkedTweets => BookmarksModuleCandidateDecorator
      case PinnedTweets => PinnedTweetsModuleCandidateDecorator
    }
  }

  def build(
    carouselType: CarouselType
  ): UrtItemInModuleDecorator[PipelineQuery, TweetCandidate, Nothing] = {
    val components = getClientComponents(carouselType)

    val clientEventInfoBuilder = HomeClientEventInfoBuilder[PipelineQuery, TweetCandidate]()

    val tweetItemBuilder = TweetCandidateUrtItemBuilder[PipelineQuery, TweetCandidate](
      clientEventInfoBuilder = clientEventInfoBuilder,
      displayType = CondensedTweet,
      timelinesScoreInfoBuilder = Some(HomeTimelinesScoreInfoBuilder),
      feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder)
    )

    val tweetItemDecorator = UrtItemCandidateDecorator(tweetItemBuilder)

    val headerBuilder =
      TweetCarouselModuleHeaderBuilder(carouselType, components, stringCenter, externalStrings)

    val tweetCarouselModuleBuilder = TimelineModuleBuilder(
      entryNamespace = EntryNamespace(components.entryNamespaceString),
      clientEventInfoBuilder = clientEventInfoBuilder,
      displayTypeBuilder = StaticModuleDisplayTypeBuilder(Carousel),
      headerBuilder = Some(headerBuilder),
      footerBuilder = None,
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

    UrtItemInModuleDecorator(tweetItemDecorator, tweetCarouselModuleBuilder)
  }
}

case class TweetCarouselModuleHeaderBuilder(
  carouselType: CarouselType,
  components: ClientComponents,
  stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends BaseModuleHeaderBuilder[PipelineQuery, TweetCandidate] {

  private def getLandingUrl(query: PipelineQuery): Option[Url] = {
    val landingPageUrl = components.headerLink.map { url => Url(ExternalUrl, url) }

    carouselType match {
      case BookmarkedTweets => landingPageUrl
      case PinnedTweets if query.params(EnableLandingPage) => landingPageUrl
      case _ => None
    }
  }

  def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Option[ModuleHeader] = {
    candidates.headOption.map { candidate =>
      val title = carouselType match {
        case BookmarkedTweets =>
          Str(externalStrings.BookmarksHeaderString, stringCenter)
            .apply(query, candidate.candidate, candidate.features)
        case PinnedTweets =>
          Str(externalStrings.PinnedTweetsHeaderString, stringCenter)
            .apply(query, candidate.candidate, candidate.features)
      }

      val landingUrl = getLandingUrl(query)

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

case class FeedbackActionInfoBuilder(
  seeLessOftenFeedbackString: ExternalString,
  seeLessOftenConfirmationFeedbackString: ExternalString,
  stringCenter: StringCenter,
  encodedFeedbackRequest: Option[String])
    extends BaseFeedbackActionInfoBuilder[PipelineQuery, TweetCandidate] {

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[FeedbackActionInfo] = Some(
    FeedbackActionInfo(
      feedbackActions = Seq(
        FeedbackAction(
          feedbackType = SeeFewer,
          prompt = Some(
            Str(seeLessOftenFeedbackString, stringCenter, None)
              .apply(query, candidate, candidateFeatures)),
          confirmation = Some(
            Str(seeLessOftenConfirmationFeedbackString, stringCenter, None)
              .apply(query, candidate, candidateFeatures)),
          childFeedbackActions = None,
          feedbackUrl = None,
          confirmationDisplayType = None,
          clientEventInfo = None,
          richBehavior = None,
          subprompt = None,
          icon = Some(Frown),
          hasUndoAction = Some(true),
          encodedFeedbackRequest = encodedFeedbackRequest
        )
      ),
      feedbackMetadata = None,
      displayContext = None,
      clientEventInfo = None
    )
  )
}
