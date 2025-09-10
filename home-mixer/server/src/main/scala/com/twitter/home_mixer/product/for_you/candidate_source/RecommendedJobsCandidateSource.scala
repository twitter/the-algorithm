package com.twitter.home_mixer.product.for_you.candidate_source

import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherSource
import com.twitter.strato.generated.client.recruiting.candidate_service.JobRecommendationCandidatesOnUserClientColumn
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.candidateservice.core_io.FetchJobRecommendationsView
import com.twitter.strato.client.Client

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class RecommendedJobsCandidateSource @Inject() (
  @Named(BatchedStratoClientWithModerateTimeout) stratoClient: Client)
    extends StratoKeyViewFetcherSource[
      Long,
      FetchJobRecommendationsView,
      JobRecommendationCandidatesOnUserClientColumn.Value,
      Long
    ] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("RecommendedJobs")

  val fetcher = stratoClient.fetcher[
    Long,
    FetchJobRecommendationsView,
    JobRecommendationCandidatesOnUserClientColumn.Value
  ](JobRecommendationCandidatesOnUserClientColumn.Path)

  override protected def stratoResultTransformer(
    stratoKey: Long,
    stratoResult: JobRecommendationCandidatesOnUserClientColumn.Value
  ): Seq[Long] = stratoResult.map(_.apiJob)
}
