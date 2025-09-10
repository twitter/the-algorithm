package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityIdFeature
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityNameFeature
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
 * Builds social context for tweets that have a 1:1 relationship
 * between served type and social context. e.g. Lists/Communities
 */
@Singleton
case class ServedTypeSocialContextBuilder @Inject() (
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    extends BaseSocialContextBuilder[PipelineQuery, TweetCandidate] {

  private val ListsUrl = ""
  private val CommunitiesUrl = ""

  private val stringCenter = stringCenterProvider.get()
  private val listString = externalStrings.ownedSubscribedListsModuleHeaderString
  private val popularGeoString = externalStrings.socialContextPopularInYourAreaString

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContext] = candidateFeatures.get(ServedTypeFeature) match {

    case t.ServedType.ForYouCommunity =>
      val communityId = candidateFeatures.getOrElse(CommunityIdFeature, None)
      val communityName = candidateFeatures.getOrElse(CommunityNameFeature, None)
      val context = GeneralContext(
        contextType = CommunityGeneralContextType,
        text = communityName.getOrElse(""),
        url = None,
        contextImageUrls = None,
        landingUrl = communityId.map(id => Url(ExternalUrl, CommunitiesUrl + id))
      )
      Some(context)

    case t.ServedType.ForYouPopularGeo =>
      val context = GeneralContext(
        contextType = LocationGeneralContextType,
        text = stringCenter.prepare(popularGeoString),
        url = None,
        contextImageUrls = None,
        landingUrl = None
      )
      Some(context)

    case _ => None
  }
}
