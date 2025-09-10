package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.strato.generated.client.timelines.control_ai.storage.TopicEmbeddingMhClientColumn
import com.twitter.strato.generated.client.timelines.control_ai.storage.UserControlMhClientColumn
import com.twitter.stitch.Stitch
import com.twitter.timelines.control_ai.control.{thriftscala => ci}
import javax.inject.Inject
import javax.inject.Singleton

object ControlAiTopicEmbeddings
    extends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Seq[Double]]] {
  override def defaultValue: Seq[Seq[Double]] = Seq.empty
}

@Singleton
class ControlAiTopicEmbeddingQueryFeatureHydrator @Inject() (
  userControlMhStratoColumn: UserControlMhClientColumn,
  topicEmbeddingMhStratoColumn: TopicEmbeddingMhClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ControlAiTopicEmbedding")

  override val features: Set[Feature[_, _]] = Set(ControlAiTopicEmbeddings)

  private val validActions: Set[ci.ActionType] = Set(ci.ActionType.More, ci.ActionType.Only)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {

    userControlMhStratoColumn.fetcher.fetch(query.getRequiredUserId).flatMap { result =>
      val topics: Seq[String] = result.v
        .map(_.actions).map {
          _.flatMap { action =>
            if (validActions.contains(action.actionType) && action.condition.postTopic.nonEmpty)
              Some(action.condition.postTopic.get)
            else
              None
          }
        }.getOrElse(Seq.empty)

      val embeddingsStitch: Stitch[Seq[Seq[Double]]] = Stitch
        .collect {
          topics.map { topic =>
            topicEmbeddingMhStratoColumn.fetcher.fetch(topic).map { result =>
              result.v.map(_.embedding)
            }
          }
        }.map(_.flatMap(_.toList))

      embeddingsStitch.map { embeddings =>
        new FeatureMapBuilder()
          .add(ControlAiTopicEmbeddings, embeddings)
          .build()
      }
    }
  }
}
