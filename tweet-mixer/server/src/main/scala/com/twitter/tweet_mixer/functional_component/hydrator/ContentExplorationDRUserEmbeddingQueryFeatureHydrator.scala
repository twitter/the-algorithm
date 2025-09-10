package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.hydra.embedding_generation.{thriftscala => eg}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.marshaller.request.ClientContextMarshaller
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.utils.PipelineFailureCategories.FailedEmbeddingHydrationResponse
import com.twitter.tweet_mixer.utils.PipelineFailureCategories.InvalidEmbeddingHydrationResponse
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationDRUserEmbeddingModelName
import javax.inject.Inject
import javax.inject.Singleton

object ContentExplorationDRUserEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Seq[Int]]] {
  override def defaultValue: Option[Seq[Int]] = None
}

@Singleton
class ContentExplorationDRUserEmbeddingQueryFeatureHydrator @Inject() (
  egsClient: eg.EmbeddingGenerationService.MethodPerEndpoint)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ContentExplorationDRUserEmbedding")

  override val features: Set[Feature[_, _]] = Set(ContentExplorationDRUserEmbeddingFeature)

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

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    getEmbedding(
      query.getRequiredUserId,
      query.clientContext,
      query.params(ContentExplorationDRUserEmbeddingModelName))
      .map { embedding =>
        new FeatureMapBuilder()
          .add(ContentExplorationDRUserEmbeddingFeature, embedding)
          .build()
      }
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
                error.msg.getOrElse("Unknown validation error in EmbeddingsGenerationService")))
          case _ => InvalidResponseException
        }
      case _ => FailedResponseException
    }
  }
}
