package com.twitter.tweet_mixer.candidate_source.curated_user_tls_per_language

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.stitch.timelineservice.TimelineService
import com.twitter.timelineservice.{thriftscala => tls}

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CuratedUserTlsPerLanguageCandidateSource @Inject() (timelineService: TimelineService)
    extends CandidateSource[Seq[tls.TimelineQuery], TweetCandidate] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "CuratedUserTlsPerLanguage")
  override def apply(requests: Seq[tls.TimelineQuery]): Stitch[Seq[TweetCandidate]] = Stitch
    .traverse(requests) { request =>
      timelineService.getTimeline(request).map { response =>
        response.entries.collect {
          case tls.TimelineEntry.Tweet(tweet) => TweetCandidate(tweet.statusId)
        }
      }
    }.map {
      // Round-robin interleave across authors
      _.filter(_.nonEmpty) // Remove empty lists
      .transpose.flatten
    }
}
