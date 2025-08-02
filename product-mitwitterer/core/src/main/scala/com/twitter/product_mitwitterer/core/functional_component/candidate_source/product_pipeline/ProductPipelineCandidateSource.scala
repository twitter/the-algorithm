package com.twitter.product_mitwitterer.core.functional_component.candidate_source.product_pipeline

import com.google.inject.Provider
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.functional_component.configapi.ParamsBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.request.Request
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineRequest
import com.twitter.product_mitwitterer.core.product.registry.ProductPipelineRegistry
import com.twitter.stitch.Stitch
import scala.reflect.runtime.universe._

/**
 * A [[CandidateSource]] for getting candidates from a different
 * [[com.twitter.product_mitwitterer.core.model.marshalling.request.Product]] within the same Product
 * Mitwitterer-based service. This is useful when calling a RecommendationPipeline-based Product from a
 * MitwittererPipeline-based Product. In this scenario, the two Products can remain
 * independent and encapsulated within the Product Mitwitterer service, which provides future optionality
 * for migrating one of the two products into a new Product Mitwitterer-based service based on the
 * scaling needs.
 *
 * @tparam Query [[PipelineQuery]] from the originating Product
 * @tparam MitwittererRequest the [[Request]] domain model for the Product Mitwitterer service. Adds a Contetwittert
 *                      bound (syntactic sugar) to add TypeTag to implicit scope for
 *                      [[ProductPipelineRegistry.getProductPipeline()]]. Note that `trait` does not
 *                      support contetwittert bounds, so this abstraction is etwitterpressed as an
 *                      `abstract class`.
 * @tparam ProductPipelineResult the return type of the candidate source Product. Adds a Contetwittert
 *                               bound (syntactic sugar) to add TypeTag to implicit scope for
 *                               [[ProductPipelineRegistry.getProductPipeline()]]
 * @tparam Candidate the type of candidate returned by this candidate source, which is typically
 *                   etwittertracted from within the ProductPipelineResult type
 */
abstract class ProductPipelineCandidateSource[
  -Query <: PipelineQuery,
  MitwittererRequest <: Request: TypeTag,
  ProductPipelineResult: TypeTag,
  +Candidate]
    etwittertends CandidateSource[Query, Candidate] {

  /**
   * @note Define as a Guice [[Provider]] in order to break the circular injection dependency
   */
  val productPipelineRegistry: Provider[ProductPipelineRegistry]

  /**
   * @note Define as a Guice [[Provider]] in order to break the circular injection dependency
   */
  val paramsBuilder: Provider[ParamsBuilder]

  def pipelineRequestTransformer(currentPipelineQuery: Query): MitwittererRequest

  def productPipelineResultTransformer(productPipelineResult: ProductPipelineResult): Seq[Candidate]

  override def apply(query: Query): Stitch[Seq[Candidate]] = {
    val request = pipelineRequestTransformer(query)

    val params = paramsBuilder
      .get().build(
        clientContetwittert = request.clientContetwittert,
        product = request.product,
        featureOverrides = request.debugParams.flatMap(_.featureOverrides).getOrElse(Map.empty)
      )

    productPipelineRegistry
      .get()
      .getProductPipeline[MitwittererRequest, ProductPipelineResult](request.product)
      .process(ProductPipelineRequest(request, params))
      .map(productPipelineResultTransformer)
  }
}
