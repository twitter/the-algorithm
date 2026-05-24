package com.twitter.home_mixer.functional_component.decorator

import com.twitter.home_mixer.functional_component.decorator.builder.HomeClientEventInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.builder.HomeTimelinesScoreInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.PinnedTweetsModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.urt.builder.TweetCarouselModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.urt.builder.TweetCarouselType
import com.twitter.home_mixer.param.HomeGlobalParams.EnableLandingPage
import com.twitter.home_mixer.param.HomeGlobalParams.EnablePinnedTweetsCarouselParam
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.social_context.GeneralSocialContextBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.Str
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.decorator.Decoration
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ExternalUrl
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.PinGeneralContextType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stitch.Stitch
import com.twitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class PinnedTweetBroadcastCandidateDecorator @Inject() (
  tweetCarouselModuleCandidateDecorator: TweetCarouselModuleCandidateDecorator,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  externalStrings: HomeMixerExternalStrings,
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder)
    extends CandidateDecorator[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[Decoration]] = {
    if (candidates.size > 1 && query.params(EnablePinnedTweetsCarouselParam)) {
      tweetCarouselModuleCandidateDecorator
        .build(TweetCarouselType.PinnedTweets).apply(query, candidates)
    } else {
      val clientEventInfoBuilder = HomeClientEventInfoBuilder[PipelineQuery, TweetCandidate]()

      val landingUrl = if (query.params(EnableLandingPage)) {
        PinnedTweetsModuleCandidateDecorator.headerLink.map(link => Url(ExternalUrl, link))
      } else None

      val socialContextBuilder = GeneralSocialContextBuilder(
        textBuilder = Str(externalStrings.BroadcastedPinnedTweetSocialContextString, stringCenter),
        contextType = PinGeneralContextType,
        landingUrl = landingUrl
      )

      val tweetItemBuilder = TweetCandidateUrtItemBuilder[PipelineQuery, TweetCandidate](
        clientEventInfoBuilder = clientEventInfoBuilder,
        socialContextBuilder = Some(socialContextBuilder),
        timelinesScoreInfoBuilder = Some(HomeTimelinesScoreInfoBuilder),
        feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder)
      )

      UrtItemCandidateDecorator(tweetItemBuilder).apply(query, candidates)
    }
  }
}
