package com.X.follow_recommendations.common.candidate_sources.stp

import com.X.cortex.deepbird.runtime.prediction_engine.TensorflowPredictionEngine
import com.X.follow_recommendations.common.constants.GuiceNamedConstants
import com.X.ml.api.Feature.Continuous
import com.X.ml.api.util.SRichDataRecord
import com.X.ml.prediction_service.PredictionRequest
import com.X.stitch.Stitch
import com.X.wtf.scalding.jobs.strong_tie_prediction.STPRecord
import com.X.wtf.scalding.jobs.strong_tie_prediction.STPRecordAdapter
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * STP ML ranker trained using DeepBirdV2
 */
@Singleton
class Dbv2StpScorer @Inject() (
  @Named(GuiceNamedConstants.STP_DBV2_SCORER) tfPredictionEngine: TensorflowPredictionEngine) {
  def getScoredResponse(record: STPRecord): Stitch[Option[Double]] = {
    val request: PredictionRequest = new PredictionRequest(
      STPRecordAdapter.adaptToDataRecord(record))
    val responseStitch = Stitch.callFuture(tfPredictionEngine.getPrediction(request))
    responseStitch.map { response =>
      val richDr = SRichDataRecord(response.getPrediction)
      richDr.getFeatureValueOpt(new Continuous("output"))
    }
  }
}
