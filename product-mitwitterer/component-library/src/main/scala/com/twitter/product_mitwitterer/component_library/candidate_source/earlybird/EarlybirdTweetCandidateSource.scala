package com.twitter.product_mitwitterer.component_library.candidate_source.earlybird

import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.inject.Logging
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class EarlybirdTweetCandidateSource @Inject() (
  earlybirdService: t.EarlybirdService.MethodPerEndpoint)
    etwittertends CandidateSource[t.EarlybirdRequest, t.ThriftSearchResult]
    with Logging {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("EarlybirdTweets")

  override def apply(request: t.EarlybirdRequest): Stitch[Seq[t.ThriftSearchResult]] = {
    Stitch
      .callFuture(earlybirdService.search(request))
      .map { response: t.EarlybirdResponse =>
        response.searchResults.map(_.results).getOrElse(Seq.empty)
      }
  }
}
