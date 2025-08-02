package com.twitter.product_mitwitterer.component_library.candidate_source.timeline_service

import com.twitter.product_mitwitterer.component_library.model.cursor.NetwittertCursorFeature
import com.twitter.product_mitwitterer.component_library.model.cursor.PreviousCursorFeature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSourceWithEtwittertractedFeatures
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.stitch.timelineservice.TimelineService
import com.twitter.timelineservice.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

case object TimelineServiceResponseWasTruncatedFeature
    etwittertends FeatureWithDefaultOnFailure[PipelineQuery, Boolean] {
  override val defaultValue: Boolean = false
}

@Singleton
class TimelineServiceTweetCandidateSource @Inject() (
  timelineService: TimelineService)
    etwittertends CandidateSourceWithEtwittertractedFeatures[t.TimelineQuery, t.Tweet] {

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
            .add(NetwittertCursorFeature, timeline.responseCursor.flatMap(_.bottom).getOrElse(""))
            .build()

        CandidatesWithSourceFeatures(candidates = candidates, features = candidateSourceFeatures)
      }
  }

}
