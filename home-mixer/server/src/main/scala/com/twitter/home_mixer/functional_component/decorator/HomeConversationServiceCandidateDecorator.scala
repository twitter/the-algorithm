package com.twitter.home_mixer.functional_component.decorator

import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.functional_component.decorator.builder.HomeConversationModuleMetadataBuilder
import com.twitter.home_mixer.functional_component.decorator.builder.HomeTimelinesScoreInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.{HomeFeedbackActionInfoBuilder, HomeTweetContextBuilder}
import com.twitter.home_mixer.model.HomeFeatures.ConversationModuleFocalTweetIdFeature
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.UrtMultipleModulesDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.social_context.CommunitiesSocialContextBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.StaticModuleDisplayTypeBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.VerticalConversation
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object HomeConversationServiceCandidateDecorator {

  private val ConversationModuleNamespace = EntryNamespace("home-conversation")

  def apply(
    homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder,
    homeTweetContextBuilder: HomeTweetContextBuilder,
    servedType: hmt.ServedType
  ): Some[UrtMultipleModulesDecorator[PipelineQuery, TweetCandidate, Long]] = {
    val component = servedType.originalName
    val clientEventInfoBuilder = ClientEventInfoBuilder(component)
    val tweetItemBuilder = TweetCandidateUrtItemBuilder(
      clientEventInfoBuilder = clientEventInfoBuilder,
      timelinesScoreInfoBuilder = Some(HomeTimelinesScoreInfoBuilder),
      feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder),
      socialContextBuilder = Some(CommunitiesSocialContextBuilder),
      tweetContext = Some(homeTweetContextBuilder)
    )

    val moduleBuilder = TimelineModuleBuilder(
      entryNamespace = ConversationModuleNamespace,
      clientEventInfoBuilder = clientEventInfoBuilder,
      displayTypeBuilder = StaticModuleDisplayTypeBuilder(VerticalConversation),
      metadataBuilder = Some(HomeConversationModuleMetadataBuilder())
    )

    Some(
      UrtMultipleModulesDecorator(
        urtItemCandidateDecorator = UrtItemCandidateDecorator(tweetItemBuilder),
        moduleBuilder = moduleBuilder,
        groupByKey = (_, _, candidateFeatures) =>
          candidateFeatures.getOrElse(ConversationModuleFocalTweetIdFeature, None)
      ))
  }
}
