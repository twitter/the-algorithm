package com.ExTwitter.product_mixer.component_library.candidate_source.timeline_service

import com.ExTwitter.product_mixer.component_library.model.cursor.NextCursorFeature
import com.ExTwitter.product_mixer.component_library.model.cursor.PreviousCursorFeature
import com.ExTwitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSourceWithExtractedFeatures
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.stitch.timelineservice.TimelineService
import com.ExTwitter.timelineservice.{thriftscala => t}
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
