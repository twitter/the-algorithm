package com.twitter.tweet_mixer.functional_component.side_effect

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hydra.common.utils.{Utils => HydraUtils}
import com.twitter.hydra.embedding_generation.{thriftscala => eg}
import com.twitter.product_mixer.core.functional_component.marshaller.request.ClientContextMarshaller
import com.twitter.vecdb.{thriftscala => t}
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.tweet_mixer.model.response.TweetMixerResponse
import com.twitter.tweet_mixer.module.GPURetrievalHttpClient
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableDeepRetrievalAdhocDecider
import com.twitter.tweet_mixer.utils.PipelineFailureCategories.FailedEmbeddingHydrationResponse
import com.twitter.tweet_mixer.utils.PipelineFailureCategories.InvalidEmbeddingHydrationResponse
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Side effect that calls deep retrieval and GPU retrieval and compares both
 */
@Singleton
class DeepRetrievalAdHocSideEffect @Inject() (
  egsClient: eg.EmbeddingGenerationService.MethodPerEndpoint,
  @Named(ModuleNames.VecDBAnnServiceClient)
  annClient: t.VecDB.MethodPerEndpoint,
  @Named(ModuleNames.GPURetrievalDevelHttpClient)
  gpuRetrievalHttpClient: GPURetrievalHttpClient,
  statsReceiver: StatsReceiver)
    extends PipelineResultSideEffect[PipelineQuery, TweetMixerResponse]
    with PipelineResultSideEffect.Conditionally[
      PipelineQuery,
      TweetMixerResponse
    ] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("DeepRetrievalAdhoc")

  private val scopedStatsReceiver = statsReceiver.scope(identifier.toString)
  private val vecdbResCounter = scopedStatsReceiver.counter("vecDbResSize")
  private val gpuResCounter = scopedStatsReceiver.counter("gpuResSize")
  private val commonCounter = scopedStatsReceiver.counter("commonResSize")

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: TweetMixerResponse
  ): Boolean = query.params(EnableDeepRetrievalAdhocDecider)

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, TweetMixerResponse]
  ): Stitch[Unit] = {
    val query = inputs.query
    getEmbedding(query.getRequiredUserId, query.clientContext, "deep_retrieval_exp4")
      .flatMap { embeddingOpt =>
        embeddingOpt match {
          case Some(embedding) =>
            val vecdbFut = annClient
              .search(
                dataset = "tweet-deep-retrieval-exp4",
                vector = HydraUtils.intBitsSeqToFloatSeq(embedding).map(_.toDouble),
                params = Some(t.SearchParams(limit = Some(1000)))
              ).map { response: t.SearchResponse =>
                response.points match {
                  case points: Seq[t.ScoredPoint] =>
                    points.map { point => (point.id, point.score) }
                  case _ =>
                    Seq.empty
                }
              }
            val gpuFut = gpuRetrievalHttpClient.getNeighbors(embedding)
            Stitch.callFuture {
              Future.join(vecdbFut, gpuFut).map {
                case (vecdbRes, gpuRes) =>
                  val vecdbTweets = vecdbRes.map(_._1)
                  val gpuTweets = gpuRes.map(_._1)
                  vecdbResCounter.incr(vecdbTweets.size)
                  gpuResCounter.incr(gpuTweets.size)
                  commonCounter.incr(vecdbTweets.toSet.intersect(gpuTweets.toSet).size)
              }
            }
          case None => Stitch.Unit
        }
      }.flatMap { _ => Stitch.Unit }
  }

  private val InvalidResponseException = Stitch.exception(
    PipelineFailure(InvalidEmbeddingHydrationResponse, "Invalid embedding hydration response"))

  private val FailedResponseException = Stitch.exception(
    PipelineFailure(FailedEmbeddingHydrationResponse, "Failed embedding hydration response"))

  /**
   * The thrift response has been designed to support multiple embeddings for different model configs. For now
   * we only expect to use a single config, so we will take the first one and ignore anything else in the response
   */
  private object FirstResultInResponse {
    def unapply(uer: eg.UserEmbeddingsResponse): Option[eg.UserEmbeddingsResult] =
      uer.results.flatMap(_.values.headOption)
  }

  private def getEmbedding(
    userId: Long,
    clientContext: ClientContext,
    modelName: String
  ): Stitch[Option[Seq[Int]]] = {
    val egsQuery = eg.EmbeddingsGenerationRequest(
      clientContext = ClientContextMarshaller(clientContext),
      product = eg.Product.UserEmbeddings,
      productContext = Some(
        eg.ProductContext.UserEmbeddingsContext(
          eg.UserEmbeddingsContext(userIds = Seq(userId), modelNames = Some(Seq(modelName)))))
    )

    Stitch.callFuture(egsClient.generateEmbeddings(egsQuery)).flatMap {
      case eg.EmbeddingsGenerationResponse
            .UserEmbeddingsResponse(FirstResultInResponse(userEmbeddingsResult)) =>
        userEmbeddingsResult match {
          case eg.UserEmbeddingsResult.UserEmbeddings(embeddings) =>
            // We expect the embeddings to be keyed on the user id that we passed
            embeddings.embeddingsByUserId.flatMap(_.get(userId)) match {
              case Some(embeddings) =>
                Stitch.value(Some(embeddings))
              case _ => Stitch.value(None)
            }
          case eg.UserEmbeddingsResult.ValidationError(error) =>
            Stitch.exception(
              PipelineFailure(
                InvalidEmbeddingHydrationResponse,
                error.msg.getOrElse("Unknown validation error in EmbeddingsGenerationService")
              )
            )
          case _ => InvalidResponseException
        }
      case _ => FailedResponseException
    }
  }
}
