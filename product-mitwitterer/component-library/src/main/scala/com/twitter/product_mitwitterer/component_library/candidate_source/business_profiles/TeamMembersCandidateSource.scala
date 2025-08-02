package com.twitter.product_mitwitterer.component_library.candidate_source.business_profiles

import com.twitter.product_mitwitterer.component_library.model.cursor.NetwittertCursorFeature
import com.twitter.product_mitwitterer.component_library.model.cursor.PreviousCursorFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherWithSourceFeaturesSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.consumer_identity.business_profiles.BusinessProfileTeamMembersOnUserClientColumn
import com.twitter.strato.generated.client.consumer_identity.business_profiles.BusinessProfileTeamMembersOnUserClientColumn.{
  Value => TeamMembersSlice
}
import com.twitter.strato.generated.client.consumer_identity.business_profiles.BusinessProfileTeamMembersOnUserClientColumn.{
  View => TeamMembersView
}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TeamMembersCandidateSource @Inject() (
  column: BusinessProfileTeamMembersOnUserClientColumn)
    etwittertends StratoKeyViewFetcherWithSourceFeaturesSource[
      Long,
      TeamMembersView,
      TeamMembersSlice,
      Long
    ] {
  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "BusinessProfileTeamMembers")

  override val fetcher: Fetcher[Long, TeamMembersView, TeamMembersSlice] = column.fetcher

  override def stratoResultTransformer(
    stratoKey: Long,
    stratoResult: TeamMembersSlice
  ): Seq[Long] =
    stratoResult.members

  override protected def etwittertractFeaturesFromStratoResult(
    stratoKey: Long,
    stratoResult: TeamMembersSlice
  ): FeatureMap = {
    val featureMapBuilder = FeatureMapBuilder()
    stratoResult.previousCursor.foreach { cursor =>
      featureMapBuilder.add(PreviousCursorFeature, cursor.toString)
    }
    stratoResult.netwittertCursor.foreach { cursor =>
      featureMapBuilder.add(NetwittertCursorFeature, cursor.toString)
    }
    featureMapBuilder.build()
  }
}
