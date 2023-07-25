package com.twitter.timelines.prediction.features.ppmi

import com.twitter.ml.api.Feature.Continuous

object PpmiDataRecordFeatures {
  val PPMI_SCORE = new Continuous("ppmi.source_author.score")
}
