package com.twitter.home_mixer.functional_component.scorer

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.NaviClientConfigFeature
import com.twitter.home_mixer.model.HomeFeatures.PredictionRequestIdFeature
import com.twitter.home_mixer.model.PredictedScoreFeature.PredictedScoreFeatureSet
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelIdParam
import com.twitter.home_mixer.util.NaviScorerStatsHandler
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.module.TestUserMapper
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.feature.featuremap.datarecord.AllFeatures
import com.twitter.product_mixer.core.feature.featuremap.datarecord.DataRecordConverter
import com.twitter.product_mixer.core.feature.featuremap.datarecord.DataRecordExtractor
import com.twitter.product_mixer.core.feature.featuremap.datarecord.FeatureMapSanitizer
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.util.Future
import com.twitter.util.Return
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.product_mixer.core.feature.datarecord.BaseDataRecordFeature

object CommonFeaturesDataRecordFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

object CandidateFeaturesDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
case class NaviModelScorer @Inject() (
  predictClientFactory: PredictClientFactory,
  testUserMapper: TestUserMapper,
  statsReceiver: StatsReceiver)
    extends Scorer[PipelineQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("NaviModel")

  override val features: Set[Feature[_, _]] = Set(
    CommonFeaturesDataRecordFeature,
    CandidateFeaturesDataRecordFeature,
    PredictionRequestIdFeature,
  ) ++ PredictedScoreFeatureSet.asInstanceOf[Set[Feature[_, _]]]

  private val queryDataRecordAdapter = new DataRecordConverter(AllFeatures())
  private val candidatesDataRecordAdapter = new DataRecordConverter(AllFeatures())

  private val resultDataRecordExtractor = new DataRecordExtractor(PredictedScoreFeatureSet)
  private val modelStatsHandler = new NaviScorerStatsHandler(statsReceiver, getClass.getSimpleName)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val modelId = query.params(ModelIdParam)
    val naviClientConfig =
      query.features.map(_.get(NaviClientConfigFeature)).get // Should always be present

    val modelStats =
      modelStatsHandler.getModelStats(query)

    val modelClient =
      predictClientFactory.getClient(
        naviClientConfig.clientName,
        naviClientConfig.customizedBatchSize)

    val predictionRequestId = UUID.randomUUID.getMostSignificantBits
    val candidateAdapter = candidatesDataRecordAdapter.toDataRecord(_)

    val commonRecord = query.features.map(queryDataRecordAdapter.toDataRecord)

    def getScores(
      candidates: Seq[CandidateWithFeatures[TweetCandidate]]
    ): Future[Seq[FeatureMap]] = {
      val features = candidates.map(_.features)
      val records = features.map(candidateAdapter)

      val responsesFut =
        modelClient.getPredictionsForBatch(records, commonRecord, modelId = Some(modelId))
      responsesFut.map { responses =>
        modelStats.failuresStat.add(responses.count(_.isThrow))
        modelStats.responsesStat.add(responses.size)

        if (responses.size == candidates.size) {
          val predictedScoreFeatureMaps = responses.map {
            case Return(dataRecord) => resultDataRecordExtractor.fromDataRecord(dataRecord)
            case _ => resultDataRecordExtractor.fromDataRecord(new DataRecord())
          }

          val featureMapSanitizer = new FeatureMapSanitizer[BaseDataRecordFeature[_, _]](
            includeFeatures = PredictedScoreFeatureSet.toSet, // Ensure this is a Set[DRFeature]
            statsReceiver = statsReceiver
          )

          // Sanitize the FeatureMaps
          val sanitizedFeatureMaps = featureMapSanitizer.sanitize(predictedScoreFeatureMaps)

          // Add Data Record to candidate Feature Map for logging in later stages
          sanitizedFeatureMaps.zip(records).map {
            case (predictedScoreFeatureMap, candidateRecord) =>
              predictedScoreFeatureMap ++
                FeatureMapBuilder()
                  .add(CandidateFeaturesDataRecordFeature, candidateRecord)
                  .add(CommonFeaturesDataRecordFeature, commonRecord.getOrElse(new DataRecord()))
                  .add(PredictionRequestIdFeature, Some(predictionRequestId))
                  .build()
          }
        } else {
          modelStats.invalidResponsesCounter.incr()
          throw PipelineFailure(IllegalStateFailure, "Result size mismatched candidates size")
        }
      }
    }

    val scores = OffloadFuturePools.offloadBatchSeqToFutureSeq(
      candidates,
      getScores(_),
      naviClientConfig.customizedBatchSize.getOrElse(predictClientFactory.DefaultRequestBatchSize),
      offload = true
    )
    Stitch.callFuture(scores)
  }

}
