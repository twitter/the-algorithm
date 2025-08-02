package com.twitter.product_mitwitterer.component_library.candidate_source.audiospace

import com.twitter.periscope.audio_space.thriftscala.CreatedSpacesView
import com.twitter.periscope.audio_space.thriftscala.SpaceSlice
import com.twitter.product_mitwitterer.component_library.model.cursor.NetwittertCursorFeature
import com.twitter.product_mitwitterer.component_library.model.cursor.PreviousCursorFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherWithSourceFeaturesSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.periscope.CreatedSpacesSliceOnUserClientColumn
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CreatedSpacesCandidateSource @Inject() (
  column: CreatedSpacesSliceOnUserClientColumn)
    etwittertends StratoKeyViewFetcherWithSourceFeaturesSource[
      Long,
      CreatedSpacesView,
      SpaceSlice,
      String
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("CreatedSpaces")

  override val fetcher: Fetcher[Long, CreatedSpacesView, SpaceSlice] = column.fetcher

  override def stratoResultTransformer(
    stratoKey: Long,
    stratoResult: SpaceSlice
  ): Seq[String] =
    stratoResult.items

  override protected def etwittertractFeaturesFromStratoResult(
    stratoKey: Long,
    stratoResult: SpaceSlice
  ): FeatureMap = {
    val featureMapBuilder = FeatureMapBuilder()
    stratoResult.sliceInfo.previousCursor.foreach { cursor =>
      featureMapBuilder.add(PreviousCursorFeature, cursor)
    }
    stratoResult.sliceInfo.netwittertCursor.foreach { cursor =>
      featureMapBuilder.add(NetwittertCursorFeature, cursor)
    }
    featureMapBuilder.build()
  }
}
