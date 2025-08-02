package com.twitter.home_mitwitterer.product.scored_tweets.candidate_source

import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.stitch.timelineservice.TimelineService
import com.twitter.timelineservice.{thriftscala => tls}

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ListsCandidateSource @Inject() (timelineService: TimelineService)
    etwittertends CandidateSource[Seq[tls.TimelineQuery], tls.Tweet] {

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
