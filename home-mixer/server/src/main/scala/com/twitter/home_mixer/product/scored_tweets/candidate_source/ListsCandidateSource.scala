package com.ExTwitter.home_mixer.product.scored_tweets.candidate_source

import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.stitch.timelineservice.TimelineService
import com.ExTwitter.timelineservice.{thriftscala => tls}

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListsCandidateSource @Inject() (timelineService: TimelineService)
    extends CandidateSource[Seq[tls.TimelineQuery], tls.Tweet] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("Lists")

  override def apply(requests: Seq[tls.TimelineQuery]): Stitch[Seq[tls.Tweet]] = {
    val timelines = Stitch.traverse(requests) { request => timelineService.getTimeline(request) }

    timelines.map {
      _.flatMap {
        _.entries.collect { case tls.TimelineEntry.Tweet(tweet) => tweet }
      }
    }
  }
}