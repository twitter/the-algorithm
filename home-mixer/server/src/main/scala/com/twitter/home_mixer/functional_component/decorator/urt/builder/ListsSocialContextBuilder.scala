package com.ExTwitter.home_mixer.functional_component.decorator.urt.builder

import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.UserScreenNameFeature
import com.ExTwitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseSocialContextBuilder
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata._
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.product.guice.scope.ProductScoped
import com.ExTwitter.stringcenter.client.StringCenter
import com.ExTwitter.timelineservice.suggests.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * "Your Lists" will be rendered for the context and a url link for your lists.
 */
@Singleton
case class ListsSocialContextBuilder @Inject() (
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    extends BaseSocialContextBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()
  private val listString = externalStrings.ownedSubscribedListsModuleHeaderString

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContext] = {
    candidateFeatures.get(SuggestTypeFeature) match {
      case Some(suggestType) if suggestType == t.SuggestType.RankedListTweet =>
        val userName = query.features.flatMap(_.getOrElse(UserScreenNameFeature, None))
        Some(
          GeneralContext(
            contextType = ListGeneralContextType,
            text = stringCenter.prepare(listString),
            url = userName.map(name => ""),
            contextImageUrls = None,
            landingUrl = None
          ))
      case _ => None
    }
  }
}
