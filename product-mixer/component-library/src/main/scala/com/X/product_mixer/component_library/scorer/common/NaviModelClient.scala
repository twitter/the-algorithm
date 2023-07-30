package com.X.product_mixer.component_library.scorer.common

import com.X.finagle.Http
import com.X.finagle.grpc.FinagleChannelBuilder
import com.X.finagle.grpc.FutureConverters
import com.X.mlserving.frontend.TFServingInferenceServiceImpl
import com.X.stitch.Stitch
import tensorflow.serving.PredictionServiceGrpc
import inference.GrpcService.ModelInferRequest
import inference.GrpcService.ModelInferResponse
import io.grpc.ManagedChannel
import io.grpc.Status

/**
 * Client wrapper for calling a Navi Inference Service (go/navi).
 * @param httpClient Finagle HTTP Client to use for connection.
 * @param modelPath Wily path to the ML Model service (e.g. /s/role/service).
 */
case class NaviModelClient(
  httpClient: Http.Client,
  modelPath: String)
    extends MLModelInferenceClient {

  private val channel: ManagedChannel =
    FinagleChannelBuilder
      .forTarget(modelPath)
      .httpClient(httpClient)
      // Navi enforces an authority name.
      .overrideAuthority("rustserving")
      // certain GRPC errors need to be retried.
      .enableRetryForStatus(Status.UNKNOWN)
      .enableRetryForStatus(Status.RESOURCE_EXHAUSTED)
      // this is required at channel level as mTLS is enabled at httpClient level
      .usePlaintext()
      .build()

  private val inferenceServiceStub = PredictionServiceGrpc.newFutureStub(channel)

  def score(request: ModelInferRequest): Stitch[ModelInferResponse] = {
    val tfServingRequest = TFServingInferenceServiceImpl.adaptModelInferRequest(request)
    Stitch
      .callFuture(
        FutureConverters
          .RichListenableFuture(inferenceServiceStub.predict(tfServingRequest)).toX
          .map { response =>
            TFServingInferenceServiceImpl.adaptModelInferResponse(response)
          }
      )
  }
}
