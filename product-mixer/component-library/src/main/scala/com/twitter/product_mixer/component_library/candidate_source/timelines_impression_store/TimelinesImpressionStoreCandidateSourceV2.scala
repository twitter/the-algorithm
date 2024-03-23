package com.ExTwitter.product_mixer.component_library.candidate_source.timelines_impression_store

import com.ExTwitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyFetcherSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.strato.client.Fetcher
import com.ExTwitter.strato.generated.client.timelines.impression_store.TweetImpressionStoreManhattanV2OnUserClientColumn
import com.ExTwitter.timelines.impression.thriftscala.TweetImpressionsEntries
import com.ExTwitter.timelines.impression.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelinesImpressionStoreCandidateSourceV2 @Inject() (
  client: TweetImpressionStoreManhattanV2OnUserClientColumn)
    extends StratoKeyFetcherSource[
      Long,
      t.TweetImpressionsEntries,
      t.TweetImpressionsEntry
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "TimelinesImpressionStore")

  override val fetcher: Fetcher[Long, Unit, TweetImpressionsEntries] = client.fetcher

  override def stratoResultTransformer(
    stratoResult: t.TweetImpressionsEntries
  ): Seq[t.TweetImpressionsEntry] =
    stratoResult.entries
}
