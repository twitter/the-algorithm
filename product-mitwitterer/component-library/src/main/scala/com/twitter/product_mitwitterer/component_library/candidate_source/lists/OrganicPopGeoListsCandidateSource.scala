package com.twitter.product_mitwitterer.component_library.candidate_source.lists

import com.twitter.product_mitwitterer.component_library.model.candidate.TwitterListCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.strato.StratoKeyFetcherSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.recommendations.interests_discovery.recommendations_mh.OrganicPopgeoListsClientColumn
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class OrganicPopGeoListsCandidateSource @Inject() (
  organicPopgeoListsClientColumn: OrganicPopgeoListsClientColumn)
    etwittertends StratoKeyFetcherSource[
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
