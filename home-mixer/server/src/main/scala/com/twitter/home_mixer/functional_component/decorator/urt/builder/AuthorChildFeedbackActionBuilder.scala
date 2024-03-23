package com.ExTwitter.home_mixer.functional_component.decorator.urt.builder

import com.ExTwitter.home_mixer.model.HomeFeatures.ScreenNamesFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.ExTwitter.home_mixer.util.CandidatesUtil
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.ExTwitter.product_mixer.core.product.guice.scope.ProductScoped
import com.ExTwitter.stringcenter.client.StringCenter
import com.ExTwitter.timelines.service.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class AuthorChildFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings) {

  def apply(candidateFeatures: FeatureMap): Option[ChildFeedbackAction] = {
    CandidatesUtil.getOriginalAuthorId(candidateFeatures).flatMap { authorId =>
      FeedbackUtil.buildUserSeeFewerChildFeedbackAction(
        userId = authorId,
        namesByUserId = candidateFeatures.getOrElse(ScreenNamesFeature, Map.empty[Long, String]),
        promptExternalString = externalStrings.showFewerTweetsString,
        confirmationExternalString = externalStrings.showFewerTweetsConfirmationString,
        engagementType = t.FeedbackEngagementType.Tweet,
        stringCenter = stringCenter,
        injectionType = candidateFeatures.getOrElse(SuggestTypeFeature, None)
      )
    }
  }
}
