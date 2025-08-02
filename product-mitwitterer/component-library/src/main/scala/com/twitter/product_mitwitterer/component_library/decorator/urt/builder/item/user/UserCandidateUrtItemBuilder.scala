package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.user

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.user.UserCandidateUrtItemBuilder.UserClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.BaseUserCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.IsMarkUnreadFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.user.BaseUserReactiveTriggersBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.promoted.BasePromotedMetadataBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.user.User
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.user.UserDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.user.UserItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object UserCandidateUrtItemBuilder {
  val UserClientEventInfoElement: String = "user"
}

case class UserCandidateUrtItemBuilder[Query <: PipelineQuery, UserCandidate <: BaseUserCandidate](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, UserCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, UserCandidate]
  ] = None,
  displayType: UserDisplayType = User,
  promotedMetadataBuilder: Option[BasePromotedMetadataBuilder[Query, UserCandidate]] = None,
  socialContetwittertBuilder: Option[BaseSocialContetwittertBuilder[Query, UserCandidate]] = None,
  reactiveTriggersBuilder: Option[BaseUserReactiveTriggersBuilder[Query, UserCandidate]] = None,
  enableReactiveBlending: Option[Boolean] = None)
    etwittertends CandidateUrtEntryBuilder[Query, UserCandidate, UserItem] {

  override def apply(
    query: Query,
    userCandidate: UserCandidate,
    candidateFeatures: FeatureMap
  ): UserItem = {
    val isMarkUnread = candidateFeatures.getTry(IsMarkUnreadFeature).toOption

    UserItem(
      id = userCandidate.id,
      sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
      clientEventInfo = clientEventInfoBuilder(
        query,
        userCandidate,
        candidateFeatures,
        Some(UserClientEventInfoElement)),
      feedbackActionInfo =
        feedbackActionInfoBuilder.flatMap(_.apply(query, userCandidate, candidateFeatures)),
      isMarkUnread = isMarkUnread,
      displayType = displayType,
      promotedMetadata =
        promotedMetadataBuilder.flatMap(_.apply(query, userCandidate, candidateFeatures)),
      socialContetwittert =
        socialContetwittertBuilder.flatMap(_.apply(query, userCandidate, candidateFeatures)),
      reactiveTriggers =
        reactiveTriggersBuilder.flatMap(_.apply(query, userCandidate, candidateFeatures)),
      enableReactiveBlending = enableReactiveBlending
    )
  }
}
