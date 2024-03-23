package com.ExTwitter.follow_recommendations.common.candidate_sources.addressbook

import com.ExTwitter.finagle.stats.NullStatsReceiver
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.candidate_sources.addressbook.AddressBookParams.ReadFromABV2Only
import com.ExTwitter.follow_recommendations.common.clients.addressbook.AddressbookClient
import com.ExTwitter.follow_recommendations.common.clients.addressbook.models.EdgeType
import com.ExTwitter.follow_recommendations.common.clients.addressbook.models.RecordIdentifier
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.utils.RescueWithStatsUtils.rescueWithStats
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.strato.generated.client.onboarding.userrecs.ForwardEmailBookClientColumn
import com.ExTwitter.timelines.configapi.HasParams
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForwardEmailBookSource @Inject() (
  forwardEmailBookClientColumn: ForwardEmailBookClientColumn,
  addressBookClient: AddressbookClient,
  statsReceiver: StatsReceiver = NullStatsReceiver)
    extends CandidateSource[HasParams with HasClientContext, CandidateUser] {

  override val identifier: CandidateSourceIdentifier =
    ForwardEmailBookSource.Identifier
  private val stats: StatsReceiver = statsReceiver.scope(this.getClass.getSimpleName)

  /**
   * Generate a list of candidates for the target
   */
  override def apply(
    target: HasParams with HasClientContext
  ): Stitch[Seq[CandidateUser]] = {
    val candidateUsers: Stitch[Seq[Long]] = target.getOptionalUserId
      .map { userId =>
        rescueWithStats(
          addressBookClient.getUsers(
            userId = userId,
            identifiers =
              Seq(RecordIdentifier(userId = Some(userId), email = None, phoneNumber = None)),
            batchSize = AddressbookClient.AddressBook2BatchSize,
            edgeType = ForwardEmailBookSource.DefaultEdgeType,
            fetcherOption =
              if (target.params.apply(ReadFromABV2Only)) None
              else Some(forwardEmailBookClientColumn.fetcher),
            queryOption = AddressbookClient
              .createQueryOption(
                edgeType = ForwardEmailBookSource.DefaultEdgeType,
                isPhone = ForwardEmailBookSource.IsPhone)
          ),
          stats,
          "AddressBookClient"
        )
      }.getOrElse(Stitch.Nil)

    candidateUsers
      .map(
        _.take(ForwardEmailBookSource.NumEmailBookEntries)
          .map(CandidateUser(_, score = Some(CandidateUser.DefaultCandidateScore))
            .withCandidateSource(identifier)))
  }
}

object ForwardEmailBookSource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.ForwardEmailBook.toString)
  val NumEmailBookEntries: Int = 1000
  val IsPhone = false
  val DefaultEdgeType: EdgeType = EdgeType.Forward
}
