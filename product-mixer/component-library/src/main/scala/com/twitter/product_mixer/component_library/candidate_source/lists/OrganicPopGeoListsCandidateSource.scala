package com.ExTwitter.product_mixer.component_library.candidate_source.lists

import com.ExTwitter.product_mixer.component_library.model.candidate.ExTwitterListCandidate
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyFetcherSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.strato.client.Fetcher
import com.ExTwitter.strato.generated.client.recommendations.interests_discovery.recommendations_mh.OrganicPopgeoListsClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrganicPopGeoListsCandidateSource @Inject() (
  organicPopgeoListsClientColumn: OrganicPopgeoListsClientColumn)
    extends StratoKeyFetcherSource[
      OrganicPopgeoListsClientColumn.Key,
      OrganicPopgeoListsClientColumn.Value,
      ExTwitterListCandidate
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
  ): Seq[ExTwitterListCandidate] = {
    stratoResult.recommendedListsByAlgo.flatMap { topLists =>
      topLists.lists.map { list =>
        ExTwitterListCandidate(list.listId)
      }
    }
  }
}
