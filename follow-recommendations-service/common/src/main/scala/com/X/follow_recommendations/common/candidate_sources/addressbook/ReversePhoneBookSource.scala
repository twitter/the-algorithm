package com.X.follow_recommendations.common.candidate_sources.addressbook

import com.X.cds.contact_consent_state.thriftscala.PurposeOfProcessing
import com.X.finagle.stats.NullStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.clients.addressbook.AddressbookClient
import com.X.follow_recommendations.common.clients.addressbook.models.EdgeType
import com.X.follow_recommendations.common.clients.addressbook.models.RecordIdentifier
import com.X.follow_recommendations.common.clients.phone_storage_service.PhoneStorageServiceClient
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.utils.RescueWithStatsUtils.rescueWithStats
import com.X.hermit.model.Algorithm
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.stitch.Stitch
import com.X.strato.generated.client.onboarding.userrecs.ReversePhoneContactsClientColumn
import com.X.timelines.configapi.HasParams
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReversePhoneBookSource @Inject() (
  reversePhoneContactsClientColumn: ReversePhoneContactsClientColumn,
  pssClient: PhoneStorageServiceClient,
  addressBookClient: AddressbookClient,
  statsReceiver: StatsReceiver = NullStatsReceiver)
    extends CandidateSource[HasParams with HasClientContext, CandidateUser] {

  override val identifier: CandidateSourceIdentifier = ReversePhoneBookSource.Identifier
  private val stats: StatsReceiver = statsReceiver.scope(this.getClass.getSimpleName)

  /**
   * Generate a list of candidates for the target
   */
  override def apply(target: HasParams with HasClientContext): Stitch[Seq[CandidateUser]] = {
    val reverseCandidatesFromPhones: Stitch[Seq[Long]] = target.getOptionalUserId
      .map { userId =>
        pssClient
          .getPhoneNumbers(userId, PurposeOfProcessing.ContentRecommendations)
          .flatMap { phoneNumbers =>
            rescueWithStats(
              addressBookClient.getUsers(
                userId = userId,
                identifiers = phoneNumbers.map(phoneNumber =>
                  RecordIdentifier(userId = None, email = None, phoneNumber = Some(phoneNumber))),
                batchSize = ReversePhoneBookSource.NumPhoneBookEntries,
                edgeType = ReversePhoneBookSource.DefaultEdgeType,
                fetcherOption =
                  if (target.params(AddressBookParams.ReadFromABV2Only)) None
                  else Some(reversePhoneContactsClientColumn.fetcher),
                queryOption = AddressbookClient.createQueryOption(
                  edgeType = ReversePhoneBookSource.DefaultEdgeType,
                  isPhone = ReversePhoneBookSource.IsPhone)
              ),
              stats,
              "AddressBookClient"
            )
          }
      }.getOrElse(Stitch.Nil)

    reverseCandidatesFromPhones.map(
      _.take(ReversePhoneBookSource.NumPhoneBookEntries)
        .map(
          CandidateUser(_, score = Some(CandidateUser.DefaultCandidateScore))
            .withCandidateSource(identifier))
    )
  }
}

object ReversePhoneBookSource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.ReversePhoneBook.toString)
  val NumPhoneBookEntries: Int = 500
  val IsPhone = true
  val DefaultEdgeType: EdgeType = EdgeType.Reverse
}
