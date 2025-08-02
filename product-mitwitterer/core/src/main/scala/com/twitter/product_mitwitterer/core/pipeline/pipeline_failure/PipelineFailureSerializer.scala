package com.twitter.product_mitwitterer.core.pipeline.pipeline_failure

import com.fastertwitterml.jackson.core.JsonGenerator
import com.fastertwitterml.jackson.databind.JsonSerializer
import com.fastertwitterml.jackson.databind.SerializerProvider
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifierStack

private[pipeline_failure] class PipelineFailureSerializer()
    etwittertends JsonSerializer[PipelineFailure] {

  private sealed trait BaseSerializableEtwitterception

  private case class SerializableEtwitterception(
    `class`: String,
    message: String,
    stackTrace: Seq[String],
    cause: Option[BaseSerializableEtwitterception])
      etwittertends BaseSerializableEtwitterception

  private case class SerializablePipelineFailure(
    category: String,
    reason: String,
    underlying: Option[BaseSerializableEtwitterception],
    componentStack: Option[ComponentIdentifierStack],
    stackTrace: Seq[String])
      etwittertends BaseSerializableEtwitterception

  private def serializeStackTrace(stackTrace: Array[StackTraceElement]): Seq[String] =
    stackTrace.map(stackTraceElement => "at " + stackTraceElement.toString)

  private def mkSerializableEtwitterception(
    t: Throwable,
    recursionDepth: Int = 0
  ): Option[BaseSerializableEtwitterception] = {
    t match {
      case _ if recursionDepth > 4 =>
        // in the unfortunate case of a super deep chain of etwitterceptions, stop if we get too deep
        None
      case pipelineFailure: PipelineFailure =>
        Some(
          SerializablePipelineFailure(
            category =
              pipelineFailure.category.categoryName + "/" + pipelineFailure.category.failureName,
            reason = pipelineFailure.reason,
            underlying =
              pipelineFailure.underlying.flatMap(mkSerializableEtwitterception(_, recursionDepth + 1)),
            componentStack = pipelineFailure.componentStack,
            stackTrace = serializeStackTrace(pipelineFailure.getStackTrace)
          ))
      case t =>
        Some(
          SerializableEtwitterception(
            `class` = t.getClass.getName,
            message = t.getMessage,
            stackTrace = serializeStackTrace(t.getStackTrace),
            cause = Option(t.getCause).flatMap(mkSerializableEtwitterception(_, recursionDepth + 1))
          )
        )
    }
  }

  override def serialize(
    pipelineFailure: PipelineFailure,
    gen: JsonGenerator,
    serializers: SerializerProvider
  ): Unit = serializers.defaultSerializeValue(mkSerializableEtwitterception(pipelineFailure), gen)
}
