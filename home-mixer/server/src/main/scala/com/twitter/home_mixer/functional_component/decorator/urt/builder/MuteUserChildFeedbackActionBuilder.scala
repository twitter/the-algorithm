package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.ScreenNamesFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.marshalling.response.urt.icon
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.BottomSheet
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RichBehavior
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RichFeedbackBehaviorToggleMuteUser
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class MuteUserChildFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings) {

  def apply(
    candidateFeatures: FeatureMap
  ): Option[ChildFeedbackAction] = {
    val userIdOpt =
      if (candidateFeatures.getOrElse(IsRetweetFeature, false))
        candidateFeatures.getOrElse(SourceUserIdFeature, None)
      else candidateFeatures.getOrElse(AuthorIdFeature, None)

    userIdOpt.flatMap { userId =>
      val screenNamesMap = candidateFeatures.getOrElse(ScreenNamesFeature, Map.empty[Long, String])
      val userScreenNameOpt = screenNamesMap.get(userId)
      userScreenNameOpt.map { userScreenName =>
        val prompt = stringCenter.prepare(
          externalStrings.muteUserString,
          Map("username" -> userScreenName)
        )
        val confirmation = stringCenter.prepare(
          externalStrings.muteUserConfirmationString,
          Map("username" -> userScreenName)
        )
        ChildFeedbackAction(
          feedbackType = RichBehavior,
          prompt = Some(prompt),
          confirmation = Some(confirmation),
          subprompt = None,
          feedbackUrl = None,
          hasUndoAction = Some(true),
          confirmationDisplayType = Some(BottomSheet),
          clientEventInfo = Some(
            ClientEventInfo(
              component = None,
              element = Some("mute"),
              details = None,
              action = Some("click"),
              entityToken = None
            )
          ),
          icon = Some(icon.SpeakerOff),
          richBehavior = Some(RichFeedbackBehaviorToggleMuteUser(userId)),
        )
      }
    }
  }
}
