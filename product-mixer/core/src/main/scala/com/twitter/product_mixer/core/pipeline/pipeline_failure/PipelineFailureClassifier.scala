package com.twitter.product_mixer.core.pipeline.pipeline_failure

/** Represents a way to classify a given [[Throwable]] to a [[PipelineFailure]] */
case class PipelineFailureClassifier(
  classifier: PartialFunction[Throwable, PipelineFailure])
    extends PartialFunction[Throwable, PipelineFailure] {
  override def isDefinedAt(throwable: Throwable): Boolean = classifier.isDefinedAt(throwable)
  override def apply(throwable: Throwable): PipelineFailure = classifier.apply(throwable)
}

private[core] object PipelineFailureClassifier {
  val Empty: PipelineFailureClassifier = PipelineFailureClassifier(PartialFunction.empty)
}
