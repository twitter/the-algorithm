package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.constant.SharedFeatures
import com.twitter.ml.api.util.DataRecordConverters._
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import java.lang.{Long => JLong}

object TweetMetaDataDataRecord
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

object TweetMetaDataFeatureHydrator
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("TweetMetaData")

  override def features: Set[Feature[_, _]] = Set(TweetMetaDataDataRecord)

  private val batchSize = 64

  def getFeatureMap(candidate: CandidateWithFeatures[TweetCandidate]): FeatureMap = {
    val richDataRecord = new RichDataRecord()
    setFeatures(richDataRecord, candidate.candidate, candidate.features)
    FeatureMap(TweetMetaDataDataRecord, richDataRecord.getRecord)
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    OffloadFuturePools.offloadBatchElementToElement(candidates, getFeatureMap, batchSize)
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
  }
}
