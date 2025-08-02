package com.twitter.visibility.configapi.params

import com.twitter.timelines.configapi.BucketName
import com.twitter.timelines.configapi.Etwitterperiment
import com.twitter.timelines.configapi.UseFeatureContetwittert

object VisibilityEtwitterperiment {
  val Control = "control"
  val Treatment = "treatment"
}

abstract class VisibilityEtwitterperiment(etwitterperimentKey: String)
    etwittertends Etwitterperiment(etwitterperimentKey)
    with UseFeatureContetwittert {
  val TreatmentBucket: String = VisibilityEtwitterperiment.Treatment
  override def etwitterperimentBuckets: Set[BucketName] = Set(TreatmentBucket)
  val ControlBucket: String = VisibilityEtwitterperiment.Control
  override def controlBuckets: Set[BucketName] = Set(ControlBucket)
}
