package com.twitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts

import com.twitter.escherbird.util.stitchcache.StitchCache
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts.TopOrganicFollowsAccountsParams.AccountsFilteringAndRankingLogics
import com.twitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts.TopOrganicFollowsAccountsParams.CandidateSourceEnabled
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasGeohashAndCountryCode
import com.twitter.hermit.model.Algorithm
import com.twitter.onboarding.relevance.organic_follows_accounts.thriftscala.OrganicFollowsAccounts
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.onboarding.userrecs.OrganicFollowsAccountsClientColumn
import com.twitter.timelines.configapi.HasParams
import com.twitter.util.Duration
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

object AccountsFilteringAndRankingLogicId extends Enumeration {
  type AccountsFilteringAndRankingLogicId = Value

  val NewOrganicFollows: AccountsFilteringAndRankingLogicId = Value("new_organic_follows")
  val NonNewOrganicFollows: AccountsFilteringAndRankingLogicId = Value("non_new_organic_follows")
  val OrganicFollows: AccountsFilteringAndRankingLogicId = Value("organic_follows")
}

object TopOrganicFollowsAccountsSource {
  val MaxCacheSize = 500
  val CacheTTL: Duration = Duration.fromHours(24)

  type Target = HasParams with HasClientContext with HasGeohashAndCountryCode

  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.OrganicFollowAccounts.toString)
}

@Singleton
class TopOrganicFollowsAccountsSource @Inject() (
  organicFollowsAccountsClientColumn: OrganicFollowsAccountsClientColumn,
  statsReceiver: StatsReceiver,
) extends CandidateSource[TopOrganicFollowsAccountsSource.Target, CandidateUser]
    with Logging {

  /** @see [[CandidateSourceIdentifier]] */
  override val identifier: CandidateSourceIdentifier =
    TopOrganicFollowsAccountsSource.Identifier

  private val stats = statsReceiver.scope(identifier.name)
  private val requestsStats = stats.counter("requests")
  private val noCountryCodeStats = stats.counter("no_country_code")
  private val successStats = stats.counter("success")
  private val errorStats = stats.counter("error")

  private val cache = StitchCache[String, Option[OrganicFollowsAccounts]](
    maxCacheSize = TopOrganicFollowsAccountsSource.MaxCacheSize,
    ttl = TopOrganicFollowsAccountsSource.CacheTTL,
    statsReceiver = statsReceiver.scope(identifier.name, "cache"),
    underlyingCall = (k: String) => {
      organicFollowsAccountsClientColumn.fetcher
        .fetch(k)
        .map { result => result.v }
    }
  )

  /** returns a Seq of ''potential'' content */
  override def apply(
    target: TopOrganicFollowsAccountsSource.Target
  ): Stitch[Seq[CandidateUser]] = {
    if (!target.params(CandidateSourceEnabled)) {
      return Stitch.value(Seq[CandidateUser]())
    }
    requestsStats.incr()
    target.getCountryCode
      .orElse(target.geohashAndCountryCode.flatMap(_.countryCode)).map { countryCode =>
        Stitch
          .collect(target
            .params(AccountsFilteringAndRankingLogics).map(logic =>
              cache.readThrough(countryCode.toUpperCase() + "-" + logic)))
          .onSuccess(_ => {
            successStats.incr()
          })
          .onFailure(t => {
            debug("candidate source failed identifier = %s".format(identifier), t)
            errorStats.incr()
          })
          .map(transformOrganicFollowAccountssToCandidateSource)
      }.getOrElse {
        noCountryCodeStats.incr()
        Stitch.value(Seq[CandidateUser]())
      }
  }

  private def transformOrganicFollowAccountssToCandidateSource(
    organicFollowsAccounts: Seq[Option[OrganicFollowsAccounts]]
  ): Seq[CandidateUser] = {
    organicFollowsAccounts
      .flatMap(opt =>
        opt
          .map(accounts =>
            accounts.accounts.map(account =>
              CandidateUser(
                id = account.accountId,
                score = Some(account.followedCountScore),
              ).withCandidateSource(identifier)))
          .getOrElse(Seq[CandidateUser]()))
  }
}
