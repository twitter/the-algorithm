package com.X.product_mixer.component_library.candidate_source.lists

import com.X.product_mixer.component_library.model.candidate.XListCandidate
import com.X.product_mixer.core.functional_component.candidate_source.strato.StratoKeyFetcherSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.strato.client.Fetcher
import com.X.strato.generated.client.recommendations.interests_discovery.recommendations_mh.OrganicPopgeoListsClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrganicPopGeoListsCandidateSource @Inject() (
  organicPopgeoListsClientColumn: OrganicPopgeoListsClientColumn)
    extends StratoKeyFetcherSource[
      OrganicPopgeoListsClientColumn.Key,
      OrganicPopgeoListsClientColumn.Value,
      XListCandidate
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
  ): Seq[XListCandidate] = {
    stratoResult.recommendedListsByAlgo.flatMap { topLists =>
      topLists.lists.map { list =>
        XListCandidate(list.listId)
      }
    }
  }
}
