package com.twitter.home_mixer.product.for_you.candidate_source

import com.twitter.strato.columns.jetfuel.thriftscala.JetfuelRouteData
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.events.entryPoint.JetfuelEntryPointByCountryClientColumn

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Strato column to fetch entry point for sports to display in search results.
 */
@Singleton
class JetfuelFrameCandidateSource @Inject() (
  entryPointClientColumn: JetfuelEntryPointByCountryClientColumn)
  extends StratoKeyViewFetcherSource[
    JetfuelEntryPointByCountryClientColumn.Key,
    JetfuelEntryPointByCountryClientColumn.View,
    JetfuelEntryPointByCountryClientColumn.Value,
    JetfuelRouteData
  ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("JetfuelFrame")

  override val fetcher: Fetcher[
    JetfuelEntryPointByCountryClientColumn.Key,
    JetfuelEntryPointByCountryClientColumn.View,
    JetfuelEntryPointByCountryClientColumn.Value,
  ] = entryPointClientColumn.fetcher

  override def stratoResultTransformer(
    stratoKey: JetfuelEntryPointByCountryClientColumn.Key,
    stratoResult: JetfuelEntryPointByCountryClientColumn.Value
  ): Seq[JetfuelRouteData] = stratoResult
}
