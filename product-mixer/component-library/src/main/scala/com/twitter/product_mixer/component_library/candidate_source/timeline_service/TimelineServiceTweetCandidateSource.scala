package com.twitter.product_mixer.component_library.candidate_source.timeline_service

import com.twitter.product_mixer.component_library.model.cursor.NextCursorFeature
import com.twitter.product_mixer.component_library.model.cursor.PreviousCursorFeature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSourceWithExtractedFeatures
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.stitch.timelineservice.TimelineService
import com.twitter.timelineservice.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

case object TimelineServiceResponseWasTruncatedFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Boolean] {
  override val defaultValue: Boolean = false
}

@Singleton
class TimelineServiceTweetCandidateSource @Inject() (
  timelineService: TimelineService)
    extends CandidateSourceWithExtractedFeatures[t.TimelineQuery, t.Tweet] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("TimelineServiceTweet")

  override def apply(request: t.TimelineQuery): Stitch[CandidatesWithSourceFeatures[t.Tweet]] = {
    timelineService
      .getTimeline(request).map { timeline =>
        val candidates = timeline.entries.collect {
          case t.TimelineEntry.Tweet(tweet) => tweet
        }

        val candidateSourceFeatures =
          FeatureMapBuilder()
            .add(TimelineServiceResponseWasTruncatedFeature, timeline.wasTruncated.getOrElse(false))
            .add(PreviousCursorFeature, timeline.responseCursor.flatMap(_.top).getOrElse(""))
            .add(NextCursorFeature, timeline.responseCursor.flatMap(_.bottom).getOrElse(""))
            .build()

        CandidatesWithSourceFeatures(candidates = candidates, features = candidateSourceFeatures)
      }
  }

}
