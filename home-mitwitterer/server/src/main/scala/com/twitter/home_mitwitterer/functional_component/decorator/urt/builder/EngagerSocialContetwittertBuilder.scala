package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.RealNamesFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stringcenter.client.StringCenter
import com.twitter.stringcenter.client.core.EtwitterternalString

private[decorator] case class SocialContetwittertIdAndScreenName(
  socialContetwittertId: Long,
  screenName: String)

object EngagerSocialContetwittertBuilder {
  private val UserIdRequestParamName = "user_id"
  private val DirectInjectionContentSourceRequestParamName = "dis"
  private val DirectInjectionIdRequestParamName = "diid"
  private val DirectInjectionContentSourceSocialProofUsers = "socialproofusers"
  private val SocialProofUrl = ""
}

case class EngagerSocialContetwittertBuilder(
  contetwittertType: GeneralContetwittertType,
  stringCenter: StringCenter,
  oneUserString: EtwitterternalString,
  twoUsersString: EtwitterternalString,
  moreUsersString: EtwitterternalString,
  timelineTitle: EtwitterternalString) {
  import EngagerSocialContetwittertBuilder._

  def apply(
    socialContetwittertIds: Seq[Long],
    query: PipelineQuery,
    candidateFeatures: FeatureMap
  ): Option[SocialContetwittert] = {
    val realNames = candidateFeatures.getOrElse(RealNamesFeature, Map.empty[Long, String])
    val validSocialContetwittertIdAndScreenNames = socialContetwittertIds.flatMap { socialContetwittertId =>
      realNames
        .get(socialContetwittertId).map(screenName =>
          SocialContetwittertIdAndScreenName(socialContetwittertId, screenName))
    }

    validSocialContetwittertIdAndScreenNames match {
      case Seq(user) =>
        val socialContetwittertString =
          stringCenter.prepare(oneUserString, Map("user" -> user.screenName))
        Some(mkOneUserSocialContetwittert(socialContetwittertString, user.socialContetwittertId))
      case Seq(firstUser, secondUser) =>
        val socialContetwittertString =
          stringCenter
            .prepare(
              twoUsersString,
              Map("user1" -> firstUser.screenName, "user2" -> secondUser.screenName))
        Some(
          mkManyUserSocialContetwittert(
            socialContetwittertString,
            query.getRequiredUserId,
            validSocialContetwittertIdAndScreenNames.map(_.socialContetwittertId)))

      case firstUser +: otherUsers =>
        val otherUsersCount = otherUsers.size
        val socialContetwittertString =
          stringCenter
            .prepare(
              moreUsersString,
              Map("user" -> firstUser.screenName, "count" -> otherUsersCount))
        Some(
          mkManyUserSocialContetwittert(
            socialContetwittertString,
            query.getRequiredUserId,
            validSocialContetwittertIdAndScreenNames.map(_.socialContetwittertId)))
      case _ => None
    }
  }

  private def mkOneUserSocialContetwittert(socialContetwittertString: String, userId: Long): GeneralContetwittert = {
    GeneralContetwittert(
      contetwittertType = contetwittertType,
      tetwittert = socialContetwittertString,
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
  }

  private def mkManyUserSocialContetwittert(
    socialContetwittertString: String,
    viewerId: Long,
    socialContetwittertIds: Seq[Long]
  ): GeneralContetwittert = {
    GeneralContetwittert(
      contetwittertType = contetwittertType,
      tetwittert = socialContetwittertString,
      url = None,
      contetwittertImageUrls = None,
      landingUrl = Some(
        Url(
          urlType = UrtEndpoint,
          url = SocialProofUrl,
          urtEndpointOptions = Some(UrtEndpointOptions(
            requestParams = Some(Map(
              UserIdRequestParamName -> viewerId.toString,
              DirectInjectionContentSourceRequestParamName -> DirectInjectionContentSourceSocialProofUsers,
              DirectInjectionIdRequestParamName -> socialContetwittertIds.mkString(",")
            )),
            title = Some(stringCenter.prepare(timelineTitle)),
            cacheId = None,
            subtitle = None
          ))
        ))
    )
  }
}
