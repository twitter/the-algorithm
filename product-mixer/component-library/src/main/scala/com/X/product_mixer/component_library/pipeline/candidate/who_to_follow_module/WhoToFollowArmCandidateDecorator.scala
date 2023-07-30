package com.X.product_mixer.component_library.pipeline.candidate.who_to_follow_module

import com.X.product_mixer.component_library.candidate_source.account_recommendations_mixer.WhoToFollowModuleFooterFeature
import com.X.product_mixer.component_library.candidate_source.account_recommendations_mixer.WhoToFollowModuleHeaderFeature
import com.X.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.X.product_mixer.component_library.decorator.urt.UrtItemInModuleDecorator
import com.X.product_mixer.component_library.decorator.urt.builder.item.user.UserCandidateUrtItemBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.metadata.StaticUrlBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.promoted.FeaturePromotedMetadataBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.social_context.WhoToFollowSocialContextBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.stringcenter.StrStatic
import com.X.product_mixer.component_library.decorator.urt.builder.timeline_module.ModuleFooterBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.timeline_module.ModuleHeaderBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.X.product_mixer.component_library.model.candidate.UserCandidate
import com.X.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.X.product_mixer.core.functional_component.decorator.Decoration
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleDisplayTypeBuilder
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.DeepLink
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

object WhoToFollowArmCandidateDecorator {
  val ClientEventComponent = "suggest_who_to_follow"
  val EntryNamespaceString = "who-to-follow"
}

case class WhoToFollowArmCandidateDecorator[-Query <: PipelineQuery](
  moduleDisplayTypeBuilder: BaseModuleDisplayTypeBuilder[Query, UserCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, UserCandidate]
  ]) extends CandidateDecorator[Query, UserCandidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[UserCandidate]]
  ): Stitch[Seq[Decoration]] = {
    val clientEventDetailsBuilder = WhoToFollowClientEventDetailsBuilder(TrackingTokenFeature)
    val clientEventInfoBuilder = ClientEventInfoBuilder[Query, UserCandidate](
      WhoToFollowArmCandidateDecorator.ClientEventComponent,
      Some(clientEventDetailsBuilder))
    val promotedMetadataBuilder = FeaturePromotedMetadataBuilder(AdImpressionFeature)
    val socialContextBuilder =
      WhoToFollowSocialContextBuilder(SocialTextFeature, HermitContextTypeFeature)
    val userItemBuilder = UserCandidateUrtItemBuilder(
      clientEventInfoBuilder = clientEventInfoBuilder,
      promotedMetadataBuilder = Some(promotedMetadataBuilder),
      socialContextBuilder = Some(socialContextBuilder))
    val userItemDecorator = UrtItemCandidateDecorator(userItemBuilder)

    val whoToFollowModuleBuilder = {
      val whoToFollowHeaderOpt = query.features.map(_.get(WhoToFollowModuleHeaderFeature))
      val whoToFollowFooterOpt = query.features.flatMap(_.get(WhoToFollowModuleFooterFeature))
      val whoToFollowModuleHeaderBuilder = whoToFollowHeaderOpt.flatMap(_.title).map { title =>
        ModuleHeaderBuilder(textBuilder = StrStatic(title), isSticky = Some(true))
      }
      val whoToFollowModuleFooterBuilder = whoToFollowFooterOpt.flatMap(_.action).map { action =>
        ModuleFooterBuilder(
          textBuilder = StrStatic(action.title),
          urlBuilder = Some(StaticUrlBuilder(action.actionUrl, DeepLink)))
      }

      TimelineModuleBuilder(
        entryNamespace = EntryNamespace(WhoToFollowArmCandidateDecorator.EntryNamespaceString),
        clientEventInfoBuilder = clientEventInfoBuilder,
        displayTypeBuilder = moduleDisplayTypeBuilder,
        headerBuilder = whoToFollowModuleHeaderBuilder,
        footerBuilder = whoToFollowModuleFooterBuilder,
        feedbackActionInfoBuilder = feedbackActionInfoBuilder,
      )
    }

    UrtItemInModuleDecorator(
      userItemDecorator,
      whoToFollowModuleBuilder
    ).apply(query, candidates)
  }
}
