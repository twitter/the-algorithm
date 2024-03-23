package com.ExTwitter.follow_recommendations.common.candidate_sources.stp

import com.ExTwitter.cortex.deepbird.runtime.prediction_engine.TensorflowPredictionEngine
import com.ExTwitter.follow_recommendations.common.constants.GuiceNamedConstants
import com.ExTwitter.ml.api.Feature.Continuous
import com.ExTwitter.ml.api.util.SRichDataRecord
import com.ExTwitter.ml.prediction_service.PredictionRequest
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.wtf.scalding.jobs.strong_tie_prediction.STPRecord
import com.ExTwitter.wtf.scalding.jobs.strong_tie_prediction.STPRecordAdapter
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
