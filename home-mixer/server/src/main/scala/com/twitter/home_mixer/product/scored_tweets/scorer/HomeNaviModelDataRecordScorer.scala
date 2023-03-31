package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.dal.personal_data.{thriftjava => pd}
import com.twitter.finagle.stats.Stat
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.Scoring.ModelWeights
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.BaseDataRecordFeature
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.datarecord.DataRecordOptionalFeature
import com.twitter.product_mixer.core.feature.datarecord.DoubleDataRecordCompatible
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.datarecord.AllFeatures
import com.twitter.product_mixer.core.feature.featuremap.datarecord.DataRecordConverter
import com.twitter.product_mixer.core.feature.featuremap.datarecord.DataRecordExtractor
import com.twitter.product_mixer.core.feature.featuremap.datarecord.FeaturesScope
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.clients.predictionservice.PredictionServiceGRPCClient
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.prediction.features.recap.RecapFeatures
import com.twitter.util.Future
import com.twitter.util.Return

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

case class HomeNaviModelDataRecordScorer[
  Query <: PipelineQuery,
  Candidate <: UniversalNoun[Any],
  CandidateFeatures <: BaseDataRecordFeature[Candidate, _],
  ResultFeatures <: BaseDataRecordFeature[Candidate, _]
](
  override val identifier: ScorerIdentifier,
  modelClient: PredictionServiceGRPCClient,
  candidateFeatures: FeaturesScope[CandidateFeatures],
  resultFeatures: Set[ResultFeatures],
  statsReceiver: StatsReceiver)
    extends Scorer[Query, Candidate] {

  require(resultFeatures.nonEmpty, "Result features cannot be empty")

  override val features: Set[Feature[_, _]] =
    resultFeatures.asInstanceOf[
      Set[Feature[_, _]]] + CommonFeaturesDataRecordFeature + CandidateFeaturesDataRecordFeature

  private val queryDataRecordAdapter = new DataRecordConverter(AllFeatures())
  private val candidatesDataRecordAdapter = new DataRecordConverter(candidateFeatures)
  private val resultDataRecordExtractor = new DataRecordExtractor(resultFeatures)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val failuresStat = scopedStatsReceiver.stat("failures")
  private val responsesStat = scopedStatsReceiver.stat("responses")
  private val invalidResponsesSizeCounter = scopedStatsReceiver.counter("invalidResponsesSize")
  private val candidatesDataRecordAdapterLatencyStat =
    scopedStatsReceiver.scope("candidatesDataRecordAdapter").stat("latency_ms")

  private val DataRecordConstructionParallelism = 32

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val commonRecord = query.features.map(queryDataRecordAdapter.toDataRecord)
    val candidateRecords: Future[Seq[DataRecord]] =
      Stat.time(candidatesDataRecordAdapterLatencyStat) {
        OffloadFuturePools.parallelize[FeatureMap, DataRecord](
          candidates.map(_.features),
          candidatesDataRecordAdapter.toDataRecord(_),
          DataRecordConstructionParallelism,
          new DataRecord
        )
      }

    Stitch.callFuture {
      candidateRecords.flatMap { records =>
        val predictionResponses =
          modelClient.getPredictions(
            records = records,
            commonFeatures = commonRecord,
            modelId = Some("Home")
          )

        predictionResponses.map { responses =>
          failuresStat.add(responses.count(_.isThrow))
          responsesStat.add(responses.size)

          if (responses.size == candidates.size) {
            val predictedScoreFeatureMaps = responses.map {
              case Return(dataRecord) =>
                resultDataRecordExtractor.fromDataRecord(dataRecord)
              case _ =>
                resultDataRecordExtractor.fromDataRecord(new DataRecord())
            }

            // add Data Record to feature map, which will be used for logging in later stage
            predictedScoreFeatureMaps.zip(records).map {
              case (predictedScoreFeatureMap, candidateRecord) =>
                predictedScoreFeatureMap +
                  (key = CandidateFeaturesDataRecordFeature, value = candidateRecord) +
                  (key = CommonFeaturesDataRecordFeature, value =
                    commonRecord.getOrElse(new DataRecord()))
            }
          } else {
            invalidResponsesSizeCounter.incr()
            throw PipelineFailure(IllegalStateFailure, "Result Size mismatched candidates size")
          }
        }
      }
    }
  }
}

/**
 * Features for results returned by Navi user-tweet prediction models.
 */
object HomeNaviModelDataRecordScorer {
  val RequestBatchSize = 32

  sealed trait PredictedScoreFeature
      extends DataRecordOptionalFeature[TweetCandidate, Double]
      with DoubleDataRecordCompatible {
    def statName: String

    def modelWeightParam: FSBoundedParam[Double]
  }

  object PredictedFavoriteScoreFeature extends PredictedScoreFeature {
    override val featureName: String = RecapFeatures.PREDICTED_IS_FAVORITED.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
    override val statName = "fav"
    override val modelWeightParam = ModelWeights.FavParam
  }

  object PredictedReplyScoreFeature extends PredictedScoreFeature {
    override val featureName: String = RecapFeatures.PREDICTED_IS_REPLIED.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
    override val statName = "reply"
    override val modelWeightParam = ModelWeights.ReplyParam
  }

  object PredictedRetweetScoreFeature extends PredictedScoreFeature {
    override val featureName: String = RecapFeatures.PREDICTED_IS_RETWEETED.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
    override val statName = "retweet"
    override val modelWeightParam = ModelWeights.RetweetParam
  }

  object PredictedReplyEngagedByAuthorScoreFeature extends PredictedScoreFeature {
    override val featureName: String =
      RecapFeatures.PREDICTED_IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
    override val statName = "reply_engaged_by_author"
    override val modelWeightParam = ModelWeights.ReplyEngagedByAuthorParam
  }

  object PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature extends PredictedScoreFeature {
    override val featureName: String = RecapFeatures.PREDICTED_IS_GOOD_CLICKED_V1.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
    override val statName = "good_click_convo_desc_favorited_or_replied"
    override val modelWeightParam = ModelWeights.GoodClickParam
  }

  object PredictedGoodClickConvoDescUamGt2ScoreFeature extends PredictedScoreFeature {
    override val featureName: String = RecapFeatures.PREDICTED_IS_GOOD_CLICKED_V2.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
    override val statName = "good_click_convo_desc_uam_gt_2"
    override val modelWeightParam = ModelWeights.GoodClickV2Param
  }

  object PredictedNegativeFeedbackV2ScoreFeature extends PredictedScoreFeature {
    override val featureName: String =
      RecapFeatures.PREDICTED_IS_NEGATIVE_FEEDBACK_V2.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
    override val statName = "negative_feedback_v2"
    override val modelWeightParam = ModelWeights.NegativeFeedbackV2Param
  }

  object PredictedGoodProfileClickScoreFeature extends PredictedScoreFeature {
    override val featureName: String =
      RecapFeatures.PREDICTED_IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
    override val statName = "good_profile_click"
    override val modelWeightParam = ModelWeights.GoodProfileClickParam
  }

  object PredictedReportedScoreFeature extends PredictedScoreFeature {
    override val featureName: String =
      RecapFeatures.PREDICTED_IS_REPORT_TWEET_CLICKED.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
    override val statName = "reported"
    override val modelWeightParam = ModelWeights.ReportParam
  }

  object PredictedVideoPlayback50ScoreFeature extends PredictedScoreFeature {
    override val featureName: String = RecapFeatures.PREDICTED_IS_VIDEO_PLAYBACK_50.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
    override val statName = "video_playback_50"
    override val modelWeightParam = ModelWeights.VideoPlayback50Param
  }

  val PredictedScoreFeatures: Seq[PredictedScoreFeature] = Seq(
    PredictedFavoriteScoreFeature,
    PredictedReplyScoreFeature,
    PredictedRetweetScoreFeature,
    PredictedReplyEngagedByAuthorScoreFeature,
    PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature,
    PredictedGoodClickConvoDescUamGt2ScoreFeature,
    PredictedNegativeFeedbackV2ScoreFeature,
    PredictedGoodProfileClickScoreFeature,
    PredictedReportedScoreFeature,
    PredictedVideoPlayback50ScoreFeature,
  )
}
