package com.X.follow_recommendations.common.candidate_sources.addressbook

import com.X.finagle.stats.NullStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.candidate_sources.addressbook.AddressBookParams.ReadFromABV2Only
import com.X.follow_recommendations.common.clients.addressbook.AddressbookClient
import com.X.follow_recommendations.common.clients.addressbook.models.EdgeType
import com.X.follow_recommendations.common.clients.addressbook.models.RecordIdentifier
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.utils.RescueWithStatsUtils.rescueWithStats
import com.X.hermit.model.Algorithm
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.stitch.Stitch
import com.X.strato.generated.client.onboarding.userrecs.ForwardPhoneContactsClientColumn
import com.X.timelines.configapi.HasParams
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForwardPhoneBookSource @Inject() (
  forwardPhoneContactsClientColumn: ForwardPhoneContactsClientColumn,
  addressBookClient: AddressbookClient,
  statsReceiver: StatsReceiver = NullStatsReceiver)
    extends CandidateSource[HasParams with HasClientContext, CandidateUser] {

  override val identifier: CandidateSourceIdentifier =
    ForwardPhoneBookSource.Identifier
  private val stats: StatsReceiver = statsReceiver.scope(this.getClass.getSimpleName)

  /**
   * Generate a list of candidates for the target
   */
  override def apply(target: HasParams with HasClientContext): Stitch[Seq[CandidateUser]] = {
    val candidateUsers: Stitch[Seq[Long]] = target.getOptionalUserId
      .map { userId =>
        rescueWithStats(
          addressBookClient.getUsers(
            userId,
            identifiers =
              Seq(RecordIdentifier(userId = Some(userId), email = None, phoneNumber = None)),
            batchSize = AddressbookClient.AddressBook2BatchSize,
            edgeType = ForwardPhoneBookSource.DefaultEdgeType,
            fetcherOption =
              if (target.params.apply(ReadFromABV2Only)) None
              else Some(forwardPhoneContactsClientColumn.fetcher),
            queryOption = AddressbookClient
              .createQueryOption(
                edgeType = ForwardPhoneBookSource.DefaultEdgeType,
                isPhone = ForwardPhoneBookSource.IsPhone)
          ),
          stats,
          "AddressBookClient"
        )
      }.getOrElse(Stitch.Nil)

    candidateUsers
      .map(
        _.take(ForwardPhoneBookSource.NumPhoneBookEntries)
          .map(CandidateUser(_, score = Some(CandidateUser.DefaultCandidateScore))
            .withCandidateSource(identifier)))
  }
}

object ForwardPhoneBookSource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.ForwardPhoneBook.toString)
  val NumPhoneBookEntries: Int = 1000
  val IsPhone = true
  val DefaultEdgeType: EdgeType = EdgeType.Forward
}
