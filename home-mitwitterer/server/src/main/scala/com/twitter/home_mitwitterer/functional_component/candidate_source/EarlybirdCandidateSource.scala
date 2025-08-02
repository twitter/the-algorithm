package com.twitter.home_mitwitterer.functional_component.candidate_source

import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSourceWithEtwittertractedFeatures
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

case object EarlybirdResponseTruncatedFeature
    etwittertends FeatureWithDefaultOnFailure[t.EarlybirdRequest, Boolean] {
  override val defaultValue: Boolean = false
}

case object EarlybirdBottomTweetFeature
    etwittertends FeatureWithDefaultOnFailure[t.EarlybirdRequest, Option[Long]] {
  override val defaultValue: Option[Long] = None
}

@Singleton
case class EarlybirdCandidateSource @Inject() (
  earlybird: t.EarlybirdService.MethodPerEndpoint)
    etwittertends CandidateSourceWithEtwittertractedFeatures[t.EarlybirdRequest, t.ThriftSearchResult] {

  override val identifier = CandidateSourceIdentifier("Earlybird")

  override def apply(
    request: t.EarlybirdRequest
  ): Stitch[CandidatesWithSourceFeatures[t.ThriftSearchResult]] = {
    Stitch.callFuture(earlybird.search(request)).map { response =>
      val candidates = response.searchResults.map(_.results).getOrElse(Seq.empty)

      val features = FeatureMapBuilder()
        .add(EarlybirdResponseTruncatedFeature, candidates.size == request.searchQuery.numResults)
        .add(EarlybirdBottomTweetFeature, candidates.lastOption.map(_.id))
        .build()

      CandidatesWithSourceFeatures(candidates, features)
    }
  }
}
