package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelineservice.suggests.{thriftscala => st}
import javatwitter.inject.Inject
import javatwitter.inject.Provider
import javatwitter.inject.Singleton

@Singleton
case class PopularVideoSocialContetwittertBuilder @Inject() (
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    etwittertends BaseSocialContetwittertBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()
  private val popularVideoString = etwitterternalStrings.socialContetwittertPopularVideoString

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContetwittert] = {
    val suggestTypeOpt = candidateFeatures.getOrElse(SuggestTypeFeature, None)
    if (suggestTypeOpt.contains(st.SuggestType.MediaTweet)) {
      Some(
        GeneralContetwittert(
          contetwittertType = SparkleGeneralContetwittertType,
          tetwittert = stringCenter.prepare(popularVideoString),
          url = None,
          contetwittertImageUrls = None,
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
