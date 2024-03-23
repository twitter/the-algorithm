package com.ExTwitter.follow_recommendations.common.candidate_sources.addressbook

import com.ExTwitter.cds.contact_consent_state.thriftscala.PurposeOfProcessing
import com.ExTwitter.finagle.stats.NullStatsReceiver
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.clients.addressbook.AddressbookClient
import com.ExTwitter.follow_recommendations.common.clients.addressbook.models.EdgeType
import com.ExTwitter.follow_recommendations.common.clients.addressbook.models.RecordIdentifier
import com.ExTwitter.follow_recommendations.common.clients.email_storage_service.EmailStorageServiceClient
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.utils.RescueWithStatsUtils.rescueOptionalWithStats
import com.ExTwitter.follow_recommendations.common.utils.RescueWithStatsUtils.rescueWithStats
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.strato.generated.client.onboarding.userrecs.ReverseEmailContactsClientColumn
import com.ExTwitter.timelines.configapi.HasParams
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReverseEmailBookSource @Inject() (
  reverseEmailContactsClientColumn: ReverseEmailContactsClientColumn,
  essClient: EmailStorageServiceClient,
  addressBookClient: AddressbookClient,
  statsReceiver: StatsReceiver = NullStatsReceiver)
    extends CandidateSource[HasParams with HasClientContext, CandidateUser] {
  override val identifier: CandidateSourceIdentifier = ReverseEmailBookSource.Identifier
  private val rescueStats = statsReceiver.scope("ReverseEmailBookSource")

  /**
   * Generate a list of candidates for the target
   */
  override def apply(target: HasParams with HasClientContext): Stitch[Seq[CandidateUser]] = {
    val reverseCandidatesFromEmail = target.getOptionalUserId
      .map { userId =>
        val verifiedEmailStitchOpt =
          rescueOptionalWithStats(
            essClient.getVerifiedEmail(userId, PurposeOfProcessing.ContentRecommendations),
            rescueStats,
            "getVerifiedEmail")
        verifiedEmailStitchOpt.flatMap { emailOpt =>
          rescueWithStats(
            addressBookClient.getUsers(
              userId = userId,
              identifiers = emailOpt
                .map(email =>
                  RecordIdentifier(userId = None, email = Some(email), phoneNumber = None)).toSeq,
              batchSize = ReverseEmailBookSource.NumEmailBookEntries,
              edgeType = ReverseEmailBookSource.DefaultEdgeType,
              fetcherOption =
                if (target.params(AddressBookParams.ReadFromABV2Only)) None
                else Some(reverseEmailContactsClientColumn.fetcher)
            ),
            rescueStats,
            "AddressBookClient"
          )
        }
      }.getOrElse(Stitch.Nil)

    reverseCandidatesFromEmail.map(
      _.take(ReverseEmailBookSource.NumEmailBookEntries)
        .map(
          CandidateUser(_, score = Some(CandidateUser.DefaultCandidateScore))
            .withCandidateSource(identifier))
    )
  }
}

object ReverseEmailBookSource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.ReverseEmailBookIbis.toString)
  val NumEmailBookEntries: Int = 500
  val IsPhone = false
  val DefaultEdgeType: EdgeType = EdgeType.Reverse
}
