package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.ScreenNamesFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.icon
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.BottomSheet
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RichBehavior
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RichFeedbackBehaviorBlockUser
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class BlockUserChildFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings) {

  def apply(candidateFeatures: FeatureMap): Option[ChildFeedbackAction] = {
    val userIdOpt =
      if (candidateFeatures.getOrElse(IsRetweetFeature, false))
        candidateFeatures.getOrElse(SourceUserIdFeature, None)
      else candidateFeatures.getOrElse(AuthorIdFeature, None)

    userIdOpt.flatMap { userId =>
      val screenNamesMap = candidateFeatures.getOrElse(ScreenNamesFeature, Map.empty[Long, String])
      val userScreenNameOpt = screenNamesMap.get(userId)
      userScreenNameOpt.map { userScreenName =>
        val prompt = stringCenter.prepare(
          etwitterternalStrings.blockUserString,
          Map("username" -> userScreenName)
        )
        ChildFeedbackAction(
          feedbackType = RichBehavior,
          prompt = Some(prompt),
          confirmation = None,
          feedbackUrl = None,
          hasUndoAction = Some(true),
          confirmationDisplayType = Some(BottomSheet),
          clientEventInfo = None,
          icon = Some(icon.No),
          richBehavior = Some(RichFeedbackBehaviorBlockUser(userId)),
          subprompt = None
        )
      }
    }
  }
}
