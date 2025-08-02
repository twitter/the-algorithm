package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.ScreenNamesFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelines.service.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class AuthorChildFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings) {

  def apply(candidateFeatures: FeatureMap): Option[ChildFeedbackAction] = {
    CandidatesUtil.getOriginalAuthorId(candidateFeatures).flatMap { authorId =>
      FeedbackUtil.buildUserSeeFewerChildFeedbackAction(
        userId = authorId,
        namesByUserId = candidateFeatures.getOrElse(ScreenNamesFeature, Map.empty[Long, String]),
        promptEtwitterternalString = etwitterternalStrings.showFewerTweetsString,
        confirmationEtwitterternalString = etwitterternalStrings.showFewerTweetsConfirmationString,
        engagementType = t.FeedbackEngagementType.Tweet,
        stringCenter = stringCenter,
        injectionType = candidateFeatures.getOrElse(SuggestTypeFeature, None)
      )
    }
  }
}
