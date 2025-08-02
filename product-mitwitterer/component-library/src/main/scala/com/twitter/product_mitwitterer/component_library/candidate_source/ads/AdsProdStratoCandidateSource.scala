package com.twitter.product_mitwitterer.component_library.candidate_source.ads

import com.twitter.adserver.thriftscala.AdImpression
import com.twitter.adserver.thriftscala.AdRequestParams
import com.twitter.adserver.thriftscala.AdRequestResponse
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.strato.StratoKeyFetcherSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.ads.admitwitterer.MakeAdRequestClientColumn
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class AdsProdStratoCandidateSource @Inject() (adsClient: MakeAdRequestClientColumn)
    etwittertends StratoKeyFetcherSource[
      AdRequestParams,
      AdRequestResponse,
      AdImpression
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("AdsProdStrato")

  override val fetcher: Fetcher[AdRequestParams, Unit, AdRequestResponse] = adsClient.fetcher

  override protected def stratoResultTransformer(
    stratoResult: AdRequestResponse
  ): Seq[AdImpression] =
    stratoResult.impressions
}
