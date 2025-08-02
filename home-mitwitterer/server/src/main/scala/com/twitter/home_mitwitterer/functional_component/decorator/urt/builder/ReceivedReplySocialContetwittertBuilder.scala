package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.FocalTweetInNetworkFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.RealNamesFeature
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
 * Use '@A received a reply' as social contetwittert when the root Tweet is in network and the focal tweet is OON.
 *
 * This function should only be called for the root Tweet of convo modules. This is enforced by
 * [[HomeTweetSocialContetwittertBuilder]].
 */
@Singleton
case class ReceivedReplySocialContetwittertBuilder @Inject() (
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    etwittertends BaseSocialContetwittertBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()
  private val receivedReplyString = etwitterternalStrings.socialContetwittertReceivedReply

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContetwittert] = {

    // If these values are missing default to not showing a received a reply banner
    val inNetwork = candidateFeatures.getOrElse(InNetworkFeature, false)
    val inNetworkFocalTweet =
      candidateFeatures.getOrElse(FocalTweetInNetworkFeature, None).getOrElse(true)

    if (inNetwork && !inNetworkFocalTweet) {

      val authorIdOpt = candidateFeatures.getOrElse(AuthorIdFeature, None)
      val realNames = candidateFeatures.getOrElse(RealNamesFeature, Map.empty[Long, String])
      val authorNameOpt = authorIdOpt.flatMap(realNames.get)

      (authorIdOpt, authorNameOpt) match {
        case (Some(authorId), Some(authorName)) =>
          Some(
            GeneralContetwittert(
              contetwittertType = ConversationGeneralContetwittertType,
              tetwittert = stringCenter
                .prepare(receivedReplyString, placeholders = Map("user1" -> authorName)),
              url = None,
              contetwittertImageUrls = None,
              landingUrl = Some(
                Url(
                  urlType = DeepLink,
                  url = "",
                  urtEndpointOptions = None
                )
              )
            )
          )
        case _ => None
      }
    } else {
      None
    }
  }
}
