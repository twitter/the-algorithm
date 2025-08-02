package com.twitter.product_mitwitterer.core.pipeline.product

import com.twitter.product_mitwitterer.core.functional_component.common.access_policy.AccessPolicy
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ProductPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product
import com.twitter.product_mitwitterer.core.model.marshalling.request.Request
import com.twitter.product_mitwitterer.core.pipeline.PipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.PipelineConfigCompanion
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.product.ProductParamConfig
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorConfig
import com.twitter.timelines.configapi.Params

trait ProductPipelineConfig[TRequest <: Request, Query <: PipelineQuery, Response]
    etwittertends PipelineConfig {

  override val identifier: ProductPipelineIdentifier

  val product: Product
  val paramConfig: ProductParamConfig

  /**
   * Product Pipeline Gates will be etwitterecuted before any other step (including retrieval from mitwitterer
   * pipelines). They're etwitterecuted sequentially, and any "Stop" result will prevent pipeline etwitterecution.
   */
  def gates: Seq[Gate[Query]] = Seq.empty

  def pipelineQueryTransformer(request: TRequest, params: Params): Query

  /**
   * A list of all pipelines that power this product directly (there is no need to include pipelines
   * called by those pipelines).
   *
   * Only pipeline from this list should referenced from the pipelineSelector
   */
  def pipelines: Seq[PipelineConfig]

  /**
   * A pipeline selector selects a pipeline (from the list in `def pipelines`) to handle the
   * current request.
   */
  def pipelineSelector(query: Query): ComponentIdentifier

  /**
   ** [[qualityFactorConfigs]] associates [[QualityFactorConfig]]s to specific pipelines
   * using [[ComponentIdentifier]].
   */
  def qualityFactorConfigs: Map[ComponentIdentifier, QualityFactorConfig] =
    Map.empty

  /**
   * By default (for safety), product mitwitterer pipelines do not allow logged out requests.
   * A "DenyLoggedOutUsersGate" will be generated and added to the pipeline.
   *
   * You can disable this behavior by overriding `denyLoggedOutUsers` with False.
   */
  val denyLoggedOutUsers: Boolean = true

  /**
   * A pipeline can define a partial function to rescue failures here. They will be treated as failures
   * from a monitoring standpoint, and cancellation etwitterceptions will always be propagated (they cannot be caught here).
   */
  def failureClassifier: PartialFunction[Throwable, PipelineFailure] = PartialFunction.empty

  /**
   * Alerts can be used to indicate the pipeline's service level objectives. Alerts and
   * dashboards will be automatically created based on this information.
   */
  val alerts: Seq[Alert] = Seq.empty

  /**
   * Access Policies can be used to gate who can query a product from Product Mitwitterer's query tool
   * (go/turntable).
   *
   * This will typically be gated by an LDAP group associated with your team. For etwitterample:
   *
   * {{{
   *   override val debugAccessPolicies: Set[AccessPolicy] = Set(AllowedLdapGroups("NAME"))
   * }}}
   *
   * You can disable all queries by using the [[com.twitter.product_mitwitterer.core.functional_component.common.access_policy.BlockEverything]] policy.
   */
  val debugAccessPolicies: Set[AccessPolicy]
}

object ProductPipelineConfig etwittertends PipelineConfigCompanion {
  val pipelineQueryTransformerStep: PipelineStepIdentifier = PipelineStepIdentifier(
    "PipelineQueryTransformer")
  val qualityFactorStep: PipelineStepIdentifier = PipelineStepIdentifier("QualityFactor")
  val gatesStep: PipelineStepIdentifier = PipelineStepIdentifier("Gates")
  val pipelineSelectorStep: PipelineStepIdentifier = PipelineStepIdentifier("PipelineSelector")
  val pipelineEtwitterecutionStep: PipelineStepIdentifier = PipelineStepIdentifier("PipelineEtwitterecution")

  /** All the Steps which are etwitterecuted by a [[ProductPipeline]] in the order in which they are run */
  override val stepsInOrder: Seq[PipelineStepIdentifier] = Seq(
    pipelineQueryTransformerStep,
    qualityFactorStep,
    gatesStep,
    pipelineSelectorStep,
    pipelineEtwitterecutionStep
  )
}
