package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.FocalTweetAuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.FocalTweetInNetworkFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.FocalTweetRealNamesFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javatwitter.inject.Inject
import javatwitter.inject.Provider
import javatwitter.inject.Singleton

/**
 * Use '@A replied' when the root tweet is out-of-network and the reply is in network.
 *
 * This function should only be called for the root Tweet of convo modules. This is enforced by
 * [[HomeTweetSocialContetwittertBuilder]].
 */
@Singleton
case class EtwittertendedReplySocialContetwittertBuilder @Inject() (
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    etwittertends BaseSocialContetwittertBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()
  private val etwittertendedReplyString = etwitterternalStrings.socialContetwittertEtwittertendedReply

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContetwittert] = {

    // If these values are missing default to not showing an etwittertended reply banner
    val inNetworkRoot = candidateFeatures.getOrElse(InNetworkFeature, true)

    val inNetworkFocalTweet =
      candidateFeatures.getOrElse(FocalTweetInNetworkFeature, None).getOrElse(false)

    if (!inNetworkRoot && inNetworkFocalTweet) {

      val focalTweetAuthorIdOpt = candidateFeatures.getOrElse(FocalTweetAuthorIdFeature, None)
      val focalTweetRealNames =
        candidateFeatures
          .getOrElse(FocalTweetRealNamesFeature, None).getOrElse(Map.empty[Long, String])
      val focalTweetAuthorNameOpt = focalTweetAuthorIdOpt.flatMap(focalTweetRealNames.get)

      (focalTweetAuthorIdOpt, focalTweetAuthorNameOpt) match {
        case (Some(focalTweetAuthorId), Some(focalTweetAuthorName)) =>
          Some(
            GeneralContetwittert(
              contetwittertType = ConversationGeneralContetwittertType,
              tetwittert = stringCenter
                .prepare(etwittertendedReplyString, placeholders = Map("user1" -> focalTweetAuthorName)),
              url = None,
              contetwittertImageUrls = None,
              landingUrl = Some(
                Url(
                  urlType = DeepLink,
                  url = "",
                  urtEndpointOptions = None
                ))
            ))
        case _ =>
          None
      }
    } else {
      None
    }
  }
}
