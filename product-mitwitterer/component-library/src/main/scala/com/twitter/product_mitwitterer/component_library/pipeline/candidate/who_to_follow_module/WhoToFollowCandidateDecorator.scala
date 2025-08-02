package com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_follow_module

import com.twitter.product_mitwitterer.component_library.candidate_source.people_discovery.WhoToFollowModuleHeaderFeature
import com.twitter.product_mitwitterer.component_library.candidate_source.people_discovery.WhoToFollowModuleShowMoreFeature
import com.twitter.product_mitwitterer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mitwitterer.component_library.decorator.urt.UrtItemInModuleDecorator
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.user.UserCandidateUrtItemBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.metadata.StaticUrlBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.promoted.FeaturePromotedMetadataBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.social_contetwittert.WhoToFollowSocialContetwittertBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.stringcenter.StrStatic
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.ModuleDynamicShowMoreBehaviorRevealByCountBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.ModuleFooterBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.ModuleHeaderBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mitwitterer.core.functional_component.decorator.Decoration
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleDisplayTypeBuilder
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.DeepLink
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object WhoToFollowCandidateDecorator {
  val ClientEventComponent = "suggest_who_to_follow"
  val EntryNamespaceString = "who-to-follow"
}

case class WhoToFollowCandidateDecorator[-Query <: PipelineQuery](
  moduleDisplayTypeBuilder: BaseModuleDisplayTypeBuilder[Query, UserCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, UserCandidate]
  ]) etwittertends CandidateDecorator[Query, UserCandidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[UserCandidate]]
  ): Stitch[Seq[Decoration]] = {
    val clientEventDetailsBuilder = WhoToFollowClientEventDetailsBuilder(TrackingTokenFeature)
    val clientEventInfoBuilder = ClientEventInfoBuilder[Query, UserCandidate](
      WhoToFollowCandidateDecorator.ClientEventComponent,
      Some(clientEventDetailsBuilder))
    val promotedMetadataBuilder = FeaturePromotedMetadataBuilder(AdImpressionFeature)
    val socialContetwittertBuilder =
      WhoToFollowSocialContetwittertBuilder(SocialTetwittertFeature, HermitContetwittertTypeFeature)
    val userItemBuilder = UserCandidateUrtItemBuilder(
      clientEventInfoBuilder = clientEventInfoBuilder,
      promotedMetadataBuilder = Some(promotedMetadataBuilder),
      socialContetwittertBuilder = Some(socialContetwittertBuilder))
    val userItemDecorator = UrtItemCandidateDecorator(userItemBuilder)

    val whoToFollowModuleBuilder = {
      val whoToFollowHeaderOpt = query.features.map(_.get(WhoToFollowModuleHeaderFeature))
      val whoToFollowModuleHeaderBuilder = whoToFollowHeaderOpt.flatMap(_.title).map { title =>
        ModuleHeaderBuilder(tetwittertBuilder = StrStatic(title.tetwittert), isSticky = Some(true))
      }
      val whoToFollowModuleFooterBuilder = whoToFollowHeaderOpt.flatMap(_.action).map { action =>
        ModuleFooterBuilder(
          tetwittertBuilder = StrStatic(action.title),
          urlBuilder = Some(StaticUrlBuilder(action.actionUrl, DeepLink)))
      }
      val showMoreBehaviorBuilder =
        query.features.flatMap(_.get(WhoToFollowModuleShowMoreFeature)).map { showMore =>
          ModuleDynamicShowMoreBehaviorRevealByCountBuilder(
            showMore.initialToShow,
            showMore.etwittertraToShow)
        }

      TimelineModuleBuilder(
        entryNamespace = EntryNamespace(WhoToFollowCandidateDecorator.EntryNamespaceString),
        clientEventInfoBuilder = clientEventInfoBuilder,
        displayTypeBuilder = moduleDisplayTypeBuilder,
        headerBuilder = whoToFollowModuleHeaderBuilder,
        footerBuilder = whoToFollowModuleFooterBuilder,
        feedbackActionInfoBuilder = feedbackActionInfoBuilder,
        showMoreBehaviorBuilder = showMoreBehaviorBuilder
      )
    }

    UrtItemInModuleDecorator(
      userItemDecorator,
      whoToFollowModuleBuilder
    ).apply(query, candidates)
  }
}
