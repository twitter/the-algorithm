package com.X.product_mixer.component_library.candidate_source.earlybird

import com.X.search.earlybird.{thriftscala => t}
import com.X.inject.Logging
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EarlybirdTweetCandidateSource @Inject() (
  earlybirdService: t.EarlybirdService.MethodPerEndpoint)
    extends CandidateSource[t.EarlybirdRequest, t.ThriftSearchResult]
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
