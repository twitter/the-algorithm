package com.twitter.home_mixer.functional_component.feature_hydrator.user_history

import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.BaseUserHistoryEventsAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.UserHistoryEventsAdapter
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.UserHistoryEventsLengthParam
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.user_history_transformer.unhydrated_user_history.UnhydratedUserHistoryMHProdClientColumn
import com.twitter.user_history_transformer.thriftscala.SourceType
import com.twitter.user_history_transformer.thriftscala.UnhydratedUserHistory
import com.twitter.user_history_transformer.thriftscala.UserHistory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class ScoredTweetsUserHistoryEventsQueryFeatureHydrator @Inject() (
  unhydratedUserHistoryMHProdClientColumn: UnhydratedUserHistoryMHProdClientColumn)
    extends BaseUserHistoryEventsQueryFeatureHydrator {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "ScoredTweetsUserHistoryEvents")

  override val historyFetcher: Fetcher[Long, Unit, UnhydratedUserHistory] =
    unhydratedUserHistoryMHProdClientColumn.fetcher

  private val allowedSourceTypes: Set[SourceType] = Set(SourceType.Home, SourceType.Uua)
  override val adapter: BaseUserHistoryEventsAdapter = UserHistoryEventsAdapter
  override def filterEvents(
    query: PipelineQuery,
    historyEvents: Seq[UserHistory]
  ): Stitch[Seq[UserHistory]] = {
    val filteredEvents = historyEvents.filter { event =>
      allowedSourceTypes.contains(event.metadata.flatMap(_.source).getOrElse(SourceType.Uua))
    }
    Stitch.value(filteredEvents.takeRight(query.params(UserHistoryEventsLengthParam)))
  }
}
