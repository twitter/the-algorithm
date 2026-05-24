package com.twitter.product_mixer.component_library.candidate_source.lists

import com.twitter.product_mixer.component_library.model.candidate.TwitterListCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyFetcherSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.recommendations.interests_discovery.recommendations_mh.OrganicPopgeoListsClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrganicPopGeoListsCandidateSource @Inject() (
  organicPopgeoListsClientColumn: OrganicPopgeoListsClientColumn)
    extends StratoKeyFetcherSource[
      OrganicPopgeoListsClientColumn.Key,
      OrganicPopgeoListsClientColumn.Value,
      TwitterListCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "OrganicPopGeoLists")

  override val fetcher: Fetcher[
    OrganicPopgeoListsClientColumn.Key,
    Unit,
    OrganicPopgeoListsClientColumn.Value
  ] =
    organicPopgeoListsClientColumn.fetcher

  override def stratoResultTransformer(
    stratoResult: OrganicPopgeoListsClientColumn.Value
  ): Seq[TwitterListCandidate] = {
    stratoResult.recommendedListsByAlgo.flatMap { topLists =>
      topLists.lists.map { list =>
        TwitterListCandidate(list.listId)
      }
    }
  }
}
