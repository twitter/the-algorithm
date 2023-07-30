package com.X.timelineranker.in_network_tweets

import com.X.conversions.DurationOps._
import com.X.finagle.service.RetryPolicy
import com.X.search.earlybird.thriftscala.EarlybirdService
import com.X.timelineranker.config.RequestScopes
import com.X.timelineranker.config.RuntimeConfiguration
import com.X.timelineranker.parameters.ConfigBuilder
import com.X.timelineranker.repository.CandidatesRepositoryBuilder
import com.X.timelineranker.visibility.SgsFollowGraphDataFields
import com.X.timelines.util.stats.RequestScope
import com.X.timelines.visibility.model.CheckedUserActorType
import com.X.timelines.visibility.model.ExclusionReason
import com.X.timelines.visibility.model.VisibilityCheckStatus
import com.X.timelines.visibility.model.VisibilityCheckUser
import com.X.util.Duration

object InNetworkTweetRepositoryBuilder {
  val VisibilityRuleExclusions: Set[ExclusionReason] = Set[ExclusionReason](
    ExclusionReason(
      CheckedUserActorType(Some(false), VisibilityCheckUser.SourceUser),
      Set(VisibilityCheckStatus.Blocked)
    )
  )

  private val EarlybirdTimeout = 600.milliseconds
  private val EarlybirdRequestTimeout = 600.milliseconds

  /**
   * The timeouts below are only used for the Earlybird Cluster Migration
   */
  private val EarlybirdRealtimeCGTimeout = 600.milliseconds
  private val EarlybirdRealtimeCGRequestTimeout = 600.milliseconds
}

class InNetworkTweetRepositoryBuilder(config: RuntimeConfiguration, configBuilder: ConfigBuilder)
    extends CandidatesRepositoryBuilder(config) {
  import InNetworkTweetRepositoryBuilder._

  override val clientSubId = "recycled_tweets"
  override val requestScope: RequestScope = RequestScopes.InNetworkTweetSource
  override val followGraphDataFieldsToFetch: SgsFollowGraphDataFields.ValueSet =
    SgsFollowGraphDataFields.ValueSet(
      SgsFollowGraphDataFields.FollowedUserIds,
      SgsFollowGraphDataFields.MutuallyFollowingUserIds,
      SgsFollowGraphDataFields.MutedUserIds,
      SgsFollowGraphDataFields.RetweetsMutedUserIds
    )
  override val searchProcessingTimeout: Duration = 200.milliseconds

  override def earlybirdClient(scope: String): EarlybirdService.MethodPerEndpoint =
    config.underlyingClients.createEarlybirdClient(
      scope = scope,
      requestTimeout = EarlybirdRequestTimeout,
      timeout = EarlybirdTimeout,
      retryPolicy = RetryPolicy.Never
    )

  private lazy val searchClientForSourceTweets =
    newSearchClient(clientId = clientSubId + "_source_tweets")

  /** The RealtimeCG clients below are only used for the Earlybird Cluster Migration */
  private def earlybirdRealtimeCGClient(scope: String): EarlybirdService.MethodPerEndpoint =
    config.underlyingClients.createEarlybirdRealtimeCgClient(
      scope = scope,
      requestTimeout = EarlybirdRealtimeCGRequestTimeout,
      timeout = EarlybirdRealtimeCGTimeout,
      retryPolicy = RetryPolicy.Never
    )
  private val realtimeCGClientSubId = "realtime_cg_recycled_tweets"
  private lazy val searchRealtimeCGClient =
    newSearchClient(earlybirdRealtimeCGClient, clientId = realtimeCGClientSubId)

  def apply(): InNetworkTweetRepository = {
    val inNetworkTweetSource = new InNetworkTweetSource(
      gizmoduckClient,
      searchClient,
      searchClientForSourceTweets,
      tweetyPieHighQoSClient,
      userMetadataClient,
      followGraphDataProvider,
      config.underlyingClients.contentFeaturesCache,
      clientFactories.visibilityEnforcerFactory.apply(
        VisibilityRules,
        RequestScopes.InNetworkTweetSource,
        reasonsToExclude = InNetworkTweetRepositoryBuilder.VisibilityRuleExclusions
      ),
      config.statsReceiver
    )

    val inNetworkTweetRealtimeCGSource = new InNetworkTweetSource(
      gizmoduckClient,
      searchRealtimeCGClient,
      searchClientForSourceTweets, // do not migrate source_tweets as they are sharded by TweetID
      tweetyPieHighQoSClient,
      userMetadataClient,
      followGraphDataProvider,
      config.underlyingClients.contentFeaturesCache,
      clientFactories.visibilityEnforcerFactory.apply(
        VisibilityRules,
        RequestScopes.InNetworkTweetSource,
        reasonsToExclude = InNetworkTweetRepositoryBuilder.VisibilityRuleExclusions
      ),
      config.statsReceiver.scope("replacementRealtimeCG")
    )

    new InNetworkTweetRepository(inNetworkTweetSource, inNetworkTweetRealtimeCGSource)
  }
}
