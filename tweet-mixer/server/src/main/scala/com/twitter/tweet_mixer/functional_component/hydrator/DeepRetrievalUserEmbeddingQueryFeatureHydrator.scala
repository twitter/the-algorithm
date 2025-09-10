package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.hydra.embedding_generation.{thriftscala => eg}
import com.twitter.hydra.common.utils.{Utils => HydraUtils}
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
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalAddUserEmbeddingGaussianNoise
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalUserEmbeddingGaussianNoiseParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalModelName
import com.twitter.tweet_mixer.utils.PipelineFailureCategories.FailedEmbeddingHydrationResponse
import com.twitter.tweet_mixer.utils.PipelineFailureCategories.InvalidEmbeddingHydrationResponse
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random
import scala.jdk.CollectionConverters._

object DeepRetrievalUserEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Seq[Int]]] {
  override def defaultValue: Option[Seq[Int]] = None
}

@Singleton
class DeepRetrievalUserEmbeddingQueryFeatureHydrator @Inject() (
  egsClient: eg.EmbeddingGenerationService.MethodPerEndpoint)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("DeepRetrievalUserEmbedding")

  override val features: Set[Feature[_, _]] = Set(DeepRetrievalUserEmbeddingFeature)

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

  private def addGaussianNoise(
    query: PipelineQuery,
    embedding: Option[Seq[Int]]
  ): Option[Seq[Int]] = {
    if (query.params(DeepRetrievalAddUserEmbeddingGaussianNoise)) {
      val rng = new Random()
      val noiseMultiplier = query.params.getDouble(DeepRetrievalUserEmbeddingGaussianNoiseParam)
      embedding.map { vec =>
        val floatVector = HydraUtils.intBitsSeqToFloatSeq(vec)
        val noisyFloatVector = floatVector.map { value =>
          value + (rng.nextGaussian() * noiseMultiplier).toFloat
        }
        HydraUtils
          .floatToIntegerJList(noisyFloatVector.map(java.lang.Float.valueOf).asJava).asScala.map(
            _.intValue)
      }
    } else {
      embedding
    }
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    getEmbedding(query.getRequiredUserId, query.clientContext, query.params(DeepRetrievalModelName))
      .map { embedding =>
        val userEmbedding = addGaussianNoise(query, embedding)
        new FeatureMapBuilder()
          .add(DeepRetrievalUserEmbeddingFeature, userEmbedding)
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
