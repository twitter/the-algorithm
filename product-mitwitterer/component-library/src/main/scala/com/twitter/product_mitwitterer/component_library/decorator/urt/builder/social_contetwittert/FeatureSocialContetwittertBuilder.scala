package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.social_contetwittert

import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.service.{thriftscala => t}

/**
 * Use this Builder to create Product Mitwitterer [[SocialContetwittert]] objects when you have a
 * Timeline Service Thrift [[SocialContetwittert]] feature that you want to convert
 */
case class FeatureSocialContetwittertBuilder(
  socialContetwittertFeature: Feature[_, Option[t.SocialContetwittert]])
    etwittertends BaseSocialContetwittertBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidate: UniversalNoun[Any],
    candidateFeatures: FeatureMap
  ): Option[SocialContetwittert] = {
    candidateFeatures.getOrElse(socialContetwittertFeature, None).map {
      case t.SocialContetwittert.GeneralContetwittert(contetwittert) =>
        val contetwittertType = contetwittert.contetwittertType match {
          case t.ContetwittertType.Like => LikeGeneralContetwittertType
          case t.ContetwittertType.Follow => FollowGeneralContetwittertType
          case t.ContetwittertType.Moment => MomentGeneralContetwittertType
          case t.ContetwittertType.Reply => ReplyGeneralContetwittertType
          case t.ContetwittertType.Conversation => ConversationGeneralContetwittertType
          case t.ContetwittertType.Pin => PinGeneralContetwittertType
          case t.ContetwittertType.TetwittertOnly => TetwittertOnlyGeneralContetwittertType
          case t.ContetwittertType.Facepile => FacePileGeneralContetwittertType
          case t.ContetwittertType.Megaphone => MegaPhoneGeneralContetwittertType
          case t.ContetwittertType.Bird => BirdGeneralContetwittertType
          case t.ContetwittertType.Feedback => FeedbackGeneralContetwittertType
          case t.ContetwittertType.Topic => TopicGeneralContetwittertType
          case t.ContetwittertType.List => ListGeneralContetwittertType
          case t.ContetwittertType.Retweet => RetweetGeneralContetwittertType
          case t.ContetwittertType.Location => LocationGeneralContetwittertType
          case t.ContetwittertType.Community => CommunityGeneralContetwittertType
          case t.ContetwittertType.SmartBlockEtwitterpiration => SmartblockEtwitterpirationGeneralContetwittertType
          case t.ContetwittertType.Trending => TrendingGeneralContetwittertType
          case t.ContetwittertType.Sparkle => SparkleGeneralContetwittertType
          case t.ContetwittertType.Spaces => SpacesGeneralContetwittertType
          case t.ContetwittertType.ReplyPin => ReplyPinGeneralContetwittertType
          case t.ContetwittertType.NewUser => NewUserGeneralContetwittertType
          case t.ContetwittertType.EnumUnknownContetwittertType(field) =>
            throw new UnsupportedOperationEtwitterception(s"Unknown contetwittert type: $field")
        }

        val landingUrl = contetwittert.landingUrl.map { url =>
          val endpointOptions = url.urtEndpointOptions.map { options =>
            UrtEndpointOptions(
              requestParams = options.requestParams.map(_.toMap),
              title = options.title,
              cacheId = options.cacheId,
              subtitle = options.subtitle
            )
          }

          val urlType = url.urlType match {
            case t.UrlType.EtwitterternalUrl => EtwitterternalUrl
            case t.UrlType.DeepLink => DeepLink
            case t.UrlType.UrtEndpoint => UrtEndpoint
            case t.UrlType.EnumUnknownUrlType(field) =>
              throw new UnsupportedOperationEtwitterception(s"Unknown url type: $field")
          }

          Url(urlType = urlType, url = url.url, urtEndpointOptions = endpointOptions)
        }

        GeneralContetwittert(
          tetwittert = contetwittert.tetwittert,
          contetwittertType = contetwittertType,
          url = contetwittert.url,
          contetwittertImageUrls = contetwittert.contetwittertImageUrls.map(_.toList),
          landingUrl = landingUrl
        )
      case t.SocialContetwittert.TopicContetwittert(contetwittert) =>
        val functionalityType = contetwittert.functionalityType match {
          case t.TopicContetwittertFunctionalityType.Basic =>
            BasicTopicContetwittertFunctionalityType
          case t.TopicContetwittertFunctionalityType.Recommendation =>
            RecommendationTopicContetwittertFunctionalityType
          case t.TopicContetwittertFunctionalityType.RecWithEducation =>
            RecWithEducationTopicContetwittertFunctionalityType
          case t.TopicContetwittertFunctionalityType.EnumUnknownTopicContetwittertFunctionalityType(field) =>
            throw new UnsupportedOperationEtwitterception(s"Unknown functionality type: $field")
        }

        TopicContetwittert(
          topicId = contetwittert.topicId,
          functionalityType = Some(functionalityType)
        )
      case t.SocialContetwittert.UnknownUnionField(field) =>
        throw new UnsupportedOperationEtwitterception(s"Unknown social contetwittert: $field")
    }
  }
}
