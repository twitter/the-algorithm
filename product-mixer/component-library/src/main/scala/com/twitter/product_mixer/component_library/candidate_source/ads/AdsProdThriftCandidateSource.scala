package com.twitter.product_mixer.component_library.candidate_source.ads

import com.twitter.adserver.thriftscala.AdImpression
import com.twitter.adserver.thriftscala.AdRequestParams
import com.twitter.adserver.thriftscala.NewAdServer
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdsProdThriftCandidateSource @Inject() (
  adServerClient: NewAdServer.MethodPerEndpoint)
    extends CandidateSource[AdRequestParams, AdImpression] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("AdsProdThrift")

  override def apply(request: AdRequestParams): Stitch[Seq[AdImpression]] =
    Stitch.callFuture(adServerClient.makeAdRequest(request)).map(_.impressions)
}
