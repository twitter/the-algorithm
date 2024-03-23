package com.ExTwitter.follow_recommendations.common.candidate_sources.addressbook

import com.ExTwitter.cds.contact_consent_state.thriftscala.PurposeOfProcessing
import com.ExTwitter.finagle.stats.NullStatsReceiver
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.clients.addressbook.AddressbookClient
import com.ExTwitter.follow_recommendations.common.clients.addressbook.models.EdgeType
import com.ExTwitter.follow_recommendations.common.clients.addressbook.models.RecordIdentifier
import com.ExTwitter.follow_recommendations.common.clients.phone_storage_service.PhoneStorageServiceClient
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.utils.RescueWithStatsUtils.rescueWithStats
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.strato.generated.client.onboarding.userrecs.ReversePhoneContactsClientColumn
import com.ExTwitter.timelines.configapi.HasParams
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
