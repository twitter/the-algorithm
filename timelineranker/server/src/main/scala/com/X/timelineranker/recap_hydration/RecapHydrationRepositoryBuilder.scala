package com.X.timelineranker.recap_hydration

import com.X.conversions.DurationOps._
import com.X.finagle.service.RetryPolicy
import com.X.timelineranker.config.RequestScopes
import com.X.timelineranker.config.RuntimeConfiguration
import com.X.timelineranker.parameters.ConfigBuilder
import com.X.timelineranker.repository.CandidatesRepositoryBuilder
import com.X.timelineranker.visibility.SgsFollowGraphDataFields
import com.X.search.earlybird.thriftscala.EarlybirdService
import com.X.timelines.util.stats.RequestScope
import com.X.util.Duration

class RecapHydrationRepositoryBuilder(config: RuntimeConfiguration, configBuilder: ConfigBuilder)
    extends CandidatesRepositoryBuilder(config) {

  override val clientSubId = "feature_hydration"
  override val requestScope: RequestScope = RequestScopes.RecapHydrationSource
  override val followGraphDataFieldsToFetch: SgsFollowGraphDataFields.ValueSet =
    SgsFollowGraphDataFields.ValueSet(
      SgsFollowGraphDataFields.FollowedUserIds,
      SgsFollowGraphDataFields.MutuallyFollowingUserIds
    )
  override val searchProcessingTimeout: Duration = 200.milliseconds //[2]

  override def earlybirdClient(scope: String): EarlybirdService.MethodPerEndpoint =
    config.underlyingClients.createEarlybirdClient(
      scope = scope,
      requestTimeout = 500.milliseconds, // [1]
      timeout = 500.milliseconds, // [1]
      retryPolicy = RetryPolicy.Never
    )

  def apply(): RecapHydrationRepository = {
    val recapHydrationSource = new RecapHydrationSource(
      gizmoduckClient,
      searchClient,
      tweetyPieLowQoSClient,
      userMetadataClient,
      followGraphDataProvider,
      config.underlyingClients.contentFeaturesCache,
      config.statsReceiver
    )

    new RecapHydrationRepository(recapHydrationSource)
  }
}
