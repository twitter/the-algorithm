package com.X.product_mixer.component_library.candidate_source.ads

import com.X.adserver.thriftscala.AdImpression
import com.X.adserver.thriftscala.AdRequestParams
import com.X.adserver.thriftscala.NewAdServer
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.stitch.Stitch
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
