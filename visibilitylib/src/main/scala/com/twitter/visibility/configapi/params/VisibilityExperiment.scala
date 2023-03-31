package com.twitter.visibility.configapi.params

import com.twitter.timelines.configapi.BucketName
import com.twitter.timelines.configapi.Experiment
import com.twitter.timelines.configapi.UseFeatureContext

object VisibilityExperiment {
  val Control = "control"
  val Treatment = "treatment"
}

abstract class VisibilityExperiment(experimentKey: String)
    extends Experiment(experimentKey)
    with UseFeatureContext {
  val TreatmentBucket: String = VisibilityExperiment.Treatment
  override def experimentBuckets: Set[BucketName] = Set(TreatmentBucket)
  val ControlBucket: String = VisibilityExperiment.Control
  override def controlBuckets: Set[BucketName] = Set(ControlBucket)
}
