package com.X.product_mixer.component_library.side_effect

import com.X.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.X.product_mixer.core.model.marshalling.HasMarshalling
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.user_session_store.ReadWriteUserSessionStore
import com.X.user_session_store.WriteRequest

/**
 * A [[PipelineResultSideEffect]] that writes to a [[ReadWriteUserSessionStore]]
 */
trait UserSessionStoreUpdateSideEffect[
  Request <: WriteRequest,
  Query <: PipelineQuery,
  ResponseType <: HasMarshalling]
    extends PipelineResultSideEffect[Query, ResponseType] {

  /**
   * Build the write request from the query
   * @param query PipelineQuery
   * @return WriteRequest
   */
  def buildWriteRequest(query: Query): Option[Request]

  val userSessionStore: ReadWriteUserSessionStore

  final override def apply(
    inputs: PipelineResultSideEffect.Inputs[Query, ResponseType]
  ): Stitch[Unit] = {
    buildWriteRequest(inputs.query)
      .map(userSessionStore.write)
      .getOrElse(Stitch.Unit)
  }
}
