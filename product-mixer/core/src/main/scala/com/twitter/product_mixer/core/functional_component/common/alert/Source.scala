package com.twitter.product_mixer.core.functional_component.common.alert

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * where the metric originates from, such as from the server or from a client
 *
 * @note implementations must be simple case classes with unique structures for serialization
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[Server], name = "Server"),
    new JsonSubTypes.Type(value = classOf[Strato], name = "Strato"),
    new JsonSubTypes.Type(value = classOf[GenericClient], name = "GenericClient")
  )
)
sealed trait Source

/** metrics for the Product Mixer server */
case class Server() extends Source

/** metrics from the perspective of a Strato column */
case class Strato(stratoColumnPath: String, stratoColumnOp: String) extends Source

/**
 * metrics from the perspective of a generic client
 *
 * @param displayName human readable name for the client
 * @param service service referenced in the query, of the form <role>.<env>.<job>
 * @param metricSource the source of the metric query, usually of the form sd.<role>.<env>.<job>
 * @param failureMetric the name of the metric indicating a client failure
 * @param requestMetric the name of the metric indicating a request has been made
 * @param latencyMetric the name of the metric measuring a request's latency
 *
 * @note We strongly recommend the use of [[Strato]] where possible. [[GenericClient]] is provided as a
 *       catch-all source for teams that have unusual legacy call paths (such as Macaw).
 */
case class GenericClient(
  displayName: String,
  service: String,
  metricSource: String,
  failureMetric: String,
  requestMetric: String,
  latencyMetric: String)
    extends Source
