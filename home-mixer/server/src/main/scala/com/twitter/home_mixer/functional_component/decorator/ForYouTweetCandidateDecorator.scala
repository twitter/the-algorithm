package com.twitter.home_mixer.functional_component.decorator

import com.twitter.home_mixer.functional_component.decorator.ForYouTweetCandidateDecorator.ConvoModuleEntryNamespace
import com.twitter.home_mixer.functional_component.decorator.builder.HomeClientEventInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.builder.HomeConversationModuleMetadataBuilder
import com.twitter.home_mixer.functional_component.decorator.builder.HomeTimelinesScoreInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeTweetContextBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeTweetSocialContextBuilder
import com.twitter.home_mixer.model.HomeFeatures.ConversationModuleFocalTweetIdFeature
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.UrtMultipleModulesDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ManualModuleId
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.StaticModuleDisplayTypeBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.VerticalConversation
import com.twitter.product_mixer.core.pipeline.PipelineQuery

import javax.inject.Inject
import javax.inject.Singleton

object ForYouTweetCandidateDecorator {
  val ConvoModuleEntryNamespace = EntryNamespace("home-conversation")
}

@Singleton
class ForYouTweetCandidateDecorator @Inject() (
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder,
  homeTweetSocialContextBuilder: HomeTweetSocialContextBuilder,
  homeTweetContextBuilder: HomeTweetContextBuilder) {

  val clientEventInfoBuilder = HomeClientEventInfoBuilder()

  val tweetItemBuilder = TweetCandidateUrtItemBuilder(
    clientEventInfoBuilder = clientEventInfoBuilder,
    socialContextBuilder = Some(homeTweetSocialContextBuilder),
    timelinesScoreInfoBuilder = Some(HomeTimelinesScoreInfoBuilder),
    feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder),
    tweetContext = Some(homeTweetContextBuilder)
  )

  val tweetDecorator = UrtItemCandidateDecorator(tweetItemBuilder)

  val moduleBuilder = TimelineModuleBuilder(
    entryNamespace = ConvoModuleEntryNamespace,
    clientEventInfoBuilder = clientEventInfoBuilder,
    moduleIdGeneration = ManualModuleId(0L),
    displayTypeBuilder = StaticModuleDisplayTypeBuilder(VerticalConversation),
    metadataBuilder = Some(HomeConversationModuleMetadataBuilder())
  )

  val decorator: UrtMultipleModulesDecorator[PipelineQuery, TweetCandidate, Long] =
    UrtMultipleModulesDecorator(
      urtItemCandidateDecorator = tweetDecorator,
      moduleBuilder = moduleBuilder,
      groupByKey = (_, _, candidateFeatures) =>
        candidateFeatures.getOrElse(ConversationModuleFocalTweetIdFeature, None)
    )

  def build(): UrtMultipleModulesDecorator[PipelineQuery, TweetCandidate, Long] = decorator
}
