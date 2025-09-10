package com.twitter.tweet_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.conversions.StorageUnitOps.richStorageUnitFromInt
import com.twitter.finagle._
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.tracing.DefaultTracer
import com.twitter.finagle.tracing.Tracer
import com.twitter.finagle.Service
import com.twitter.finagle.http.Fields
import com.twitter.finagle.http.Method
import com.twitter.finagle.http.Request
import com.twitter.finagle.http.Response
import com.twitter.finagle.http.Status
import com.twitter.finagle.http.Version
import com.twitter.inject.TwitterModule
import com.twitter.io.Buf
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.util.Future
import com.twitter.util.Duration
import com.twitter.util.jackson.JSON
import javax.inject.Named
import javax.inject.Singleton

object GPURetrievalHttpClientModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.GPURetrievalProdHttpClient)
  def providesGPURetrievalProdHttpClientModule(
    serviceIdentifier: ServiceIdentifier
  ): GPURetrievalHttpClient = {

    GPURetrievalHttpClientBuilder(serviceIdentifier = serviceIdentifier).build()
  }

  @Provides
  @Singleton
  @Named(ModuleNames.GPURetrievalDevelHttpClient)
  def providesGPURetrievalDevelHttpClientModule(
    serviceIdentifier: ServiceIdentifier
  ): GPURetrievalHttpClient = {

    GPURetrievalHttpClientBuilder(
      environment = "devel",
      serviceIdentifier = serviceIdentifier
    ).build()
  }
}

class GPURetrievalHttpClient(
  client: Service[Request, Response],
  baseStatsReceiver: StatsReceiver) {

  import GPURetrievalHttpClient._

  val statsReceiver = baseStatsReceiver.scope("GPURetrievalHttpClientModule")

  def getNeighbors(
    embeddings: Seq[Int],
  ): Future[Seq[(Long, Double)]] = {

    val payload = Payload(embeddings)
    val jsonInput = JSON.write(payload)

    val httpResponseFut =
      postJsonRequest("/inference", jsonInput)
        .flatMap(response => {
          if (response.status == Status.Ok) {
            statsReceiver.counter("StatusOk").incr
            Future.value(response.contentString)
          } else {
            statsReceiver.counter("StatusError").incr
            Future.exception(
              new Exception(s"${response.statusCode} Error: ${response.contentString}"))
          }
        })
    httpResponseFut.map { response =>
      val responseOpt: Option[Result] = JSON.parse[Result](response)
      responseOpt.getOrElse(Result(Seq.empty)).result
    }
  }

  private def postJsonRequest(path: String, payload: String): Future[Response] = {
    val req = Request(Version.Http11, Method.Post, path)
    val headers = req.headerMap
    headers.set(Fields.ContentType, "application/json")
    req.content = Buf.Utf8(payload)
    req.headerMap.set(Fields.ContentLength, payload.length.toString)
    // This is required by the server due to strict HTTP/1.1 impl. Otherwise 400 invalid request will be thrown
    req.headerMap.set("Host", "")
    client(req)
  }
}

case class GPURetrievalHttpClientBuilder(
  name: String = "gpu-retrieval",
  role: String = "gpu-retrieval",
  host: String = "gpu-retrieval",
  environment: String = "prod",
  maxRequestPerSec: Option[Int] = None,
  hostConnectionLimit: Int = 10,
  requestTimeout: Duration = 300.millis,
  serviceIdentifier: ServiceIdentifier,
  tracer: Tracer = DefaultTracer,
  statsReceiver: StatsReceiver = DefaultStatsReceiver,
  hostStatsReceiver: StatsReceiver = new NullStatsReceiver(),
  numOfRetries: Int = 3) {

  def withRequestTimeout(requestTimeout: Duration) =
    this.copy(requestTimeout = requestTimeout)

  def build(): GPURetrievalHttpClient = {
    val dest = if (environment == "prod") s"/s/$role/$host" else s"/srv#/devel/local/$role/$host"
    val builder = ClientBuilder()
      .name(name)
      .dest(dest)
      .reportTo(this.statsReceiver)
      .reportHostStats(this.hostStatsReceiver)
      .hostConnectionLimit(this.hostConnectionLimit)
      .stack(Http.client.withMaxResponseSize(512.megabytes))
      .requestTimeout(this.requestTimeout)
      .retries(this.numOfRetries)
      .tracer(this.tracer)

    new GPURetrievalHttpClient(builder.build(), statsReceiver)
  }
}

object GPURetrievalHttpClient {
  case class Payload(embeddings: Seq[Int])
  case class Result(result: Seq[(Long, Double)])
}
