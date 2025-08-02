package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.UserScreenNameFeature
import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelineservice.suggests.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Provider
import javatwitter.inject.Singleton

/**
 * "Your Lists" will be rendered for the contetwittert and a url link for your lists.
 */
@Singleton
case class ListsSocialContetwittertBuilder @Inject() (
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    etwittertends BaseSocialContetwittertBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()
  private val listString = etwitterternalStrings.ownedSubscribedListsModuleHeaderString

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContetwittert] = {
    candidateFeatures.get(SuggestTypeFeature) match {
      case Some(suggestType) if suggestType == t.SuggestType.RankedListTweet =>
        val userName = query.features.flatMap(_.getOrElse(UserScreenNameFeature, None))
        Some(
          GeneralContetwittert(
            contetwittertType = ListGeneralContetwittertType,
            tetwittert = stringCenter.prepare(listString),
            url = userName.map(name => ""),
            contetwittertImageUrls = None,
            landingUrl = None
          ))
      case _ => None
    }
  }
}
