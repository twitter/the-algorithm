package com.X.timelineranker.repository

import com.X.conversions.DurationOps._
import com.X.timelineranker.config.RequestScopes
import com.X.timelineranker.config.RuntimeConfiguration
import com.X.timelineranker.parameters.ConfigBuilder
import com.X.timelineranker.parameters.revchron.ReverseChronTimelineQueryContextBuilder
import com.X.timelineranker.parameters.util.RequestContextBuilderImpl
import com.X.timelineranker.source.ReverseChronHomeTimelineSource
import com.X.timelineranker.visibility.RealGraphFollowGraphDataProvider
import com.X.timelineranker.visibility.SgsFollowGraphDataFields
import com.X.search.earlybird.thriftscala.EarlybirdService
import com.X.timelineranker.decider.DeciderKey
import com.X.timelines.util.stats.RequestScope
import com.X.util.Duration

class ReverseChronHomeTimelineRepositoryBuilder(
  config: RuntimeConfiguration,
  configBuilder: ConfigBuilder)
    extends CandidatesRepositoryBuilder(config) {

  override val clientSubId = "home_materialization"
  override val requestScope: RequestScope = RequestScopes.HomeTimelineMaterialization
  override val followGraphDataFieldsToFetch: SgsFollowGraphDataFields.ValueSet =
    SgsFollowGraphDataFields.ValueSet(
      SgsFollowGraphDataFields.FollowedUserIds,
      SgsFollowGraphDataFields.MutedUserIds,
      SgsFollowGraphDataFields.RetweetsMutedUserIds
    )
  override val searchProcessingTimeout: Duration = 800.milliseconds // [3]

  override def earlybirdClient(scope: String): EarlybirdService.MethodPerEndpoint =
    config.underlyingClients.createEarlybirdClient(
      scope = scope,
      requestTimeout = 1.second, // [1]
      timeout = 1900.milliseconds, // [2]
      retryPolicy = config.underlyingClients.DefaultRetryPolicy
    )

  val realGraphFollowGraphDataProvider = new RealGraphFollowGraphDataProvider(
    followGraphDataProvider,
    config.clientWrapperFactories.realGraphClientFactory
      .scope(RequestScopes.ReverseChronHomeTimelineSource),
    config.clientWrapperFactories.socialGraphClientFactory
      .scope(RequestScopes.ReverseChronHomeTimelineSource),
    config.deciderGateBuilder.idGate(DeciderKey.SupplementFollowsWithRealGraph),
    config.statsReceiver.scope(RequestScopes.ReverseChronHomeTimelineSource.scope)
  )

  def apply(): ReverseChronHomeTimelineRepository = {
    val reverseChronTimelineSource = new ReverseChronHomeTimelineSource(
      searchClient,
      realGraphFollowGraphDataProvider,
      clientFactories.visibilityEnforcerFactory.apply(
        VisibilityRules,
        RequestScopes.ReverseChronHomeTimelineSource
      ),
      config.statsReceiver
    )

    val contextBuilder = new ReverseChronTimelineQueryContextBuilder(
      configBuilder.rootConfig,
      config,
      new RequestContextBuilderImpl(config.configApiConfiguration.requestContextFactory)
    )

    new ReverseChronHomeTimelineRepository(
      reverseChronTimelineSource,
      contextBuilder
    )
  }
}
