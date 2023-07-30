package com.X.home_mixer.product.scored_tweets.feature_hydrator

import com.X.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.X.home_mixer.util.CandidatesUtil
import com.X.ml.api.DataRecord
import com.X.ml.api.RichDataRecord
import com.X.ml.api.constant.SharedFeatures
import com.X.ml.api.util.DataRecordConverters._
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.X.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.util.OffloadFuturePools
import com.X.stitch.Stitch
import com.X.timelines.prediction.features.common.TimelinesSharedFeatures
import java.lang.{Long => JLong}

object TweetMetaDataDataRecord
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

object TweetMetaDataFeatureHydrator
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("TweetMetaData")

  override def features: Set[Feature[_, _]] = Set(TweetMetaDataDataRecord)

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = OffloadFuturePools.offload {
    val richDataRecord = new RichDataRecord()
    setFeatures(richDataRecord, candidate, existingFeatures)
    FeatureMapBuilder().add(TweetMetaDataDataRecord, richDataRecord.getRecord).build()
  }

  private def setFeatures(
    richDataRecord: RichDataRecord,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Unit = {
    richDataRecord.setFeatureValue[JLong](SharedFeatures.TWEET_ID, candidate.id)

    richDataRecord.setFeatureValueFromOption(
      TimelinesSharedFeatures.ORIGINAL_AUTHOR_ID,
      CandidatesUtil.getOriginalAuthorId(existingFeatures))

    richDataRecord.setFeatureValueFromOption(
      TimelinesSharedFeatures.CANDIDATE_TWEET_SOURCE_ID,
      existingFeatures.getOrElse(CandidateSourceIdFeature, None).map(_.value.toLong))
  }
}
