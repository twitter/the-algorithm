package com.X.visibility.configapi.params

import com.X.timelines.configapi.BucketName
import com.X.timelines.configapi.Experiment
import com.X.timelines.configapi.UseFeatureContext

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
