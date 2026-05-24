package com.twitter.home_mixer.product.for_you.candidate_source

import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyFetcherSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Client
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.trendsai.ordered.TrendsModuleOnUserClientColumn
import com.twitter.strato.generated.client.trendsai.ordered.TrendsModuleOnUserClientColumn.Key
import com.twitter.strato.generated.client.trendsai.ordered.TrendsModuleOnUserClientColumn.Value
import com.twitter.strato.graphql.thriftscala.ApiImage
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

case class StoryCandidate(
  id: Long,
  title: String,
  context: String,
  hook: Option[String],
  thumbnail: Option[ApiImage],
  socialProof: Seq[String])

@Singleton
class StoriesModuleCandidateSource @Inject() (
  @Named(BatchedStratoClientWithModerateTimeout) stratoClient: Client)
    extends StratoKeyFetcherSource[
      TrendsModuleOnUserClientColumn.Key,
      TrendsModuleOnUserClientColumn.Value,
      StoryCandidate
    ] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(name = "StoriesModule")

  val fetcher: Fetcher[Key, Unit, Value] = stratoClient.fetcher[
    TrendsModuleOnUserClientColumn.Key,
    TrendsModuleOnUserClientColumn.View,
    TrendsModuleOnUserClientColumn.Value
  ](TrendsModuleOnUserClientColumn.Path)

  override protected def stratoResultTransformer(stratoResult: Value): Seq[StoryCandidate] =
    stratoResult.items.map { v =>
      StoryCandidate(
        v.id,
        v.title,
        v.context,
        v.hook,
        v.thumbnail.map { t => ApiImage(t.url, t.width, t.height) },
        v.facepile
      )
    }
}
