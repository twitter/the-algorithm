package com.twitter.product_mitwitterer.core.service.debug_query

import com.fastertwitterml.jackson.databind.SerializationFeature
import com.twitter.finagle.Service
import com.twitter.finagle.contetwittert.Contetwitterts
import com.twitter.finagle.tracing.Trace.traceLocal
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.transport.Transport
import com.twitter.product_mitwitterer.core.functional_component.configapi.ParamsBuilder
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifierStack
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product
import com.twitter.product_mitwitterer.core.model.marshalling.request.Request
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipeline
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineRequest
import com.twitter.product_mitwitterer.core.product.registry.ProductPipelineRegistry
import com.twitter.product_mitwitterer.core.{thriftscala => t}
import com.twitter.scrooge.ThriftStruct
import com.twitter.scrooge.{Request => ScroogeRequest}
import com.twitter.scrooge.{Response => ScroogeResponse}
import com.twitter.stitch.Stitch
import com.twitter.turntable.contetwittert.TurntableRequestContetwittertKey
import com.twitter.util.jackson.ScalaObjectMapper
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.reflect.runtime.universe.TypeTag

/**
 * Returns the complete etwitterecution log for a pipeline query. These endpoints are intended for
 * debugging (primarily through Turntable).
 */
@Singleton
class DebugQueryService @Inject() (
  productPipelineRegistry: ProductPipelineRegistry,
  paramsBuilder: ParamsBuilder,
  authorizationService: AuthorizationService) {

  private val mapper =
    ScalaObjectMapper.builder
      .withAdditionalJacksonModules(Seq(ParamsSerializerModule))
      .withSerializationConfig(
        Map(
          // These are copied from the default serialization config.
          SerializationFeature.WRITE_DATES_AS_TIMESTAMPS -> false,
          SerializationFeature.WRITE_ENUMS_USING_TO_STRING -> true,
          // Generally we want to be defensive when serializing since we don't control everything that's
          // serialized. This issue also came up when trying to serialize Unit as part of sync side effects.
          SerializationFeature.FAIL_ON_EMPTY_BEANS -> false,
        ))
      // The default implementation represents numbers as JSON Numbers (i.e. Double with 53 bit precision
      // which leads to Snowflake IDs being cropped in the case of tweets.
      .withNumbersAsStrings(true)
      .objectMapper

  def apply[
    ThriftRequest <: ThriftStruct with Product1[MitwittererServiceRequest],
    MitwittererServiceRequest <: ThriftStruct,
    MitwittererRequest <: Request
  ](
    unmarshaller: MitwittererServiceRequest => MitwittererRequest
  )(
    implicit requestTypeTag: TypeTag[MitwittererRequest]
  ): Service[ScroogeRequest[ThriftRequest], ScroogeResponse[t.PipelineEtwitterecutionResult]] = {
    (thriftRequest: ScroogeRequest[ThriftRequest]) =>
      {

        val request = unmarshaller(thriftRequest.args._1)
        val params = paramsBuilder.build(
          clientContetwittert = request.clientContetwittert,
          product = request.product,
          featureOverrides = request.debugParams.flatMap(_.featureOverrides).getOrElse(Map.empty)
        )

        val productPipeline = productPipelineRegistry
          .getProductPipeline[MitwittererRequest, Any](request.product)
        verifyRequestAuthorization(request.product, productPipeline)
        Contetwitterts.broadcast.letClear(TurntableRequestContetwittertKey) {
          Stitch
            .run(productPipeline
              .arrow(ProductPipelineRequest(request, params)).map { detailedResult =>
                // Serialization can be slow so a trace is useful both for optimization by the Promitwitter
                // team and to give visibility to customers.
                val serializedJSON =
                  traceLocal("serialize_debug_response")(mapper.writeValueAsString(detailedResult))
                t.PipelineEtwitterecutionResult(serializedJSON)
              })
            .map(ScroogeResponse(_))
        }
      }
  }

  private def verifyRequestAuthorization(
    product: Product,
    productPipeline: ProductPipeline[_, _]
  ): Unit = {
    val serviceIdentifier = ServiceIdentifier.fromCertificate(Transport.peerCertificate)
    val requestContetwittert = Contetwitterts.broadcast
      .get(TurntableRequestContetwittertKey).getOrElse(throw MissingTurntableRequestContetwittertEtwitterception)

    val componentStack = ComponentIdentifierStack(productPipeline.identifier, product.identifier)
    authorizationService.verifyRequestAuthorization(
      componentStack,
      serviceIdentifier,
      productPipeline.debugAccessPolicies,
      requestContetwittert)
  }
}

object MissingTurntableRequestContetwittertEtwitterception
    etwittertends Etwitterception("Request is missing turntable request contetwittert")
