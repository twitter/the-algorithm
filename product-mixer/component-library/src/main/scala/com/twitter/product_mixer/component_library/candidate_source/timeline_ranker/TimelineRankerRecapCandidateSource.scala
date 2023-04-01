package com.twitter.product_mixer.component_library.candidate_source.timeline_ranker

import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.timelineranker.{thriftscala => t}

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineRankerRecapCandidateSource @Inject() (
  timelineRankerClient: t.TimelineRanker.MethodPerEndpoint)
    extends CandidateSource[t.RecapQuery, t.CandidateTweet] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("TimelineRankerRecap")

  override def apply(
    request: t.RecapQuery
  ): Stitch[Seq[t.CandidateTweet]] = {
    Stitch
      .callFuture(timelineRankerClient.getRecapCandidatesFromAuthors(Seq(request)))
      .map { response: Seq[t.GetCandidateTweetsResponse] =>
        response.headOption.flatMap(_.candidates).getOrElse(Seq.empty).filter(_.tweet.nonEmpty)
      }
  }
}
