package com.X.product_mixer.component_library.candidate_source.audiospace

import com.X.periscope.audio_space.thriftscala.CreatedSpacesView
import com.X.periscope.audio_space.thriftscala.SpaceSlice
import com.X.product_mixer.component_library.model.cursor.NextCursorFeature
import com.X.product_mixer.component_library.model.cursor.PreviousCursorFeature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherWithSourceFeaturesSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.strato.client.Fetcher
import com.X.strato.generated.client.periscope.CreatedSpacesSliceOnUserClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreatedSpacesCandidateSource @Inject() (
  column: CreatedSpacesSliceOnUserClientColumn)
    extends StratoKeyViewFetcherWithSourceFeaturesSource[
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

  override protected def extractFeaturesFromStratoResult(
    stratoKey: Long,
    stratoResult: SpaceSlice
  ): FeatureMap = {
    val featureMapBuilder = FeatureMapBuilder()
    stratoResult.sliceInfo.previousCursor.foreach { cursor =>
      featureMapBuilder.add(PreviousCursorFeature, cursor)
    }
    stratoResult.sliceInfo.nextCursor.foreach { cursor =>
      featureMapBuilder.add(NextCursorFeature, cursor)
    }
    featureMapBuilder.build()
  }
}
