package com.twitter.home_mitwitterer.functional_component.candidate_source

import com.google.inject.name.Named
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.StaleTweetsCache
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class StaleTweetsCacheCandidateSource @Inject() (
  @Named(StaleTweetsCache) staleTweetsCache: MemcachedClient)
    etwittertends CandidateSource[Seq[Long], Long] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("StaleTweetsCache")

  private val StaleTweetsCacheKeyPrefitwitter = "v1_"

  override def apply(request: Seq[Long]): Stitch[Seq[Long]] = {
    val keys = request.map(StaleTweetsCacheKeyPrefitwitter + _)

    Stitch.callFuture(staleTweetsCache.get(keys).map { tweets =>
      tweets.map {
        case (k, _) => k.replaceFirst(StaleTweetsCacheKeyPrefitwitter, "").toLong
      }.toSeq
    })
  }
}
