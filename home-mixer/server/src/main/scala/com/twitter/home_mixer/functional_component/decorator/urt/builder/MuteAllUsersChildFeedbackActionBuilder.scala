package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.core.model.marshalling.response.urt.icon
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.BottomSheet
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RichBehavior
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RichFeedbackBehaviorToggleMuteAllUsers
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Builder for the "Mute All" feedback action that appears in the Suggested Mutes dialog.
 * This allows users to batch mute all similar accounts after muting a single account,
 * helping to balance their algorithm recommendations.
 *
 * Similar to the "Follow All" button in Suggested Follows, but for muting.
 */
@Singleton
case class MuteAllUsersChildFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings) {

  /**
   * Builds a ChildFeedbackAction for muting all similar users.
   *
   * @param similarUserIds List of user IDs similar to the user that was just muted
   * @param primaryUserScreenName Screen name of the user that was just muted (for display)
   * @return Option[ChildFeedbackAction] - None if no similar users to mute
   */
  def apply(
    similarUserIds: Seq[Long],
    primaryUserScreenName: String
  ): Option[ChildFeedbackAction] = {
    if (similarUserIds.isEmpty) {
      None
    } else {
      val prompt = stringCenter.prepare(
        externalStrings.muteAllSimilarUsersString,
        Map("username" -> primaryUserScreenName, "count" -> similarUserIds.size.toString)
      )
      val confirmation = stringCenter.prepare(
        externalStrings.muteAllSimilarUsersConfirmationString,
        Map("count" -> similarUserIds.size.toString)
      )
      Some(ChildFeedbackAction(
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
            element = Some("mute_all"),
            details = None,
            action = Some("click"),
            entityToken = None
          )
        ),
        icon = Some(icon.SpeakerOff),
        richBehavior = Some(RichFeedbackBehaviorToggleMuteAllUsers(similarUserIds)),
        childFeedbackActions = None,
        encodedFeedbackRequest = None
      ))
    }
  }
}
