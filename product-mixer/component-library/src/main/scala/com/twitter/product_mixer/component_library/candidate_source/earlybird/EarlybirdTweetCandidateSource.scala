package com.ExTwitter.product_mixer.component_library.candidate_source.earlybird

import com.ExTwitter.search.earlybird.{thriftscala => t}
import com.ExTwitter.inject.Logging
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.stitch.Stitch
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
