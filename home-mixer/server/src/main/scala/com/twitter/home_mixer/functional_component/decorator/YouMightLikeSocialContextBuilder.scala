try {
package com.twitter.home_mixer.functional_component.decorator

import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseSocialContextBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Renders a fixed 'You Might Like' string above all OON Tweets.
 */
@Singleton
case class YouMightLikeSocialContextBuilder @Inject() (
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    extends BaseSocialContextBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()
  private val youMightLikeString = externalStrings.socialContextYouMightLikeString

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContext] = {
    val isInNetwork = candidateFeatures.getOrElse(InNetworkFeature, true)
    val isRetweet = candidateFeatures.getOrElse(IsRetweetFeature, false)
    if (!isInNetwork && !isRetweet) {
      Some(
        GeneralContext(
          contextType = SparkleGeneralContextType,
          text = stringCenter.prepare(youMightLikeString),
          url = None,
          contextImageUrls = None,
          landingUrl = None
        ))
    } else {
      None
    }
  }
}

} catch {
  case e: Exception =>
}
