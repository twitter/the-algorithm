package com.X.home_mixer.functional_component.decorator.urt.builder

import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.home_mixer.product.following.model.HomeMixerExternalStrings
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseSocialContextBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.metadata._
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.product.guice.scope.ProductScoped
import com.X.stringcenter.client.StringCenter
import com.X.timelineservice.suggests.{thriftscala => st}
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
case class PopularVideoSocialContextBuilder @Inject() (
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    extends BaseSocialContextBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()
  private val popularVideoString = externalStrings.socialContextPopularVideoString

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContext] = {
    val suggestTypeOpt = candidateFeatures.getOrElse(SuggestTypeFeature, None)
    if (suggestTypeOpt.contains(st.SuggestType.MediaTweet)) {
      Some(
        GeneralContext(
          contextType = SparkleGeneralContextType,
          text = stringCenter.prepare(popularVideoString),
          url = None,
          contextImageUrls = None,
          landingUrl = Some(
            Url(
              urlType = DeepLink,
              url = ""
            )
          )
        ))
    } else None
  }
}
