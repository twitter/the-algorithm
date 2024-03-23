package com.ExTwitter.product_mixer.component_library.candidate_source.ads

import com.ExTwitter.adserver.thriftscala.AdImpression
import com.ExTwitter.adserver.thriftscala.AdRequestParams
import com.ExTwitter.adserver.thriftscala.AdRequestResponse
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyFetcherSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.strato.client.Fetcher
import com.ExTwitter.strato.generated.client.ads.admixer.MakeAdRequestStagingClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdsStagingCandidateSource @Inject() (adsClient: MakeAdRequestStagingClientColumn)
    extends StratoKeyFetcherSource[
      AdRequestParams,
      AdRequestResponse,
      AdImpression
    ] {
  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("AdsStaging")

  override val fetcher: Fetcher[AdRequestParams, Unit, AdRequestResponse] = adsClient.fetcher

  override protected def stratoResultTransformer(
    stratoResult: AdRequestResponse
  ): Seq[AdImpression] =
    stratoResult.impressions
}
