package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.home_mixer.model.HomeFeatures.GrokTranslatedPostIsCachedFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetLanguageFromTweetypieFeature
import com.twitter.home_mixer.model.HomeFeatures.UserUnderstandableLanguagesFeature
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableGrokAutoTranslateLanguageFilter
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.stitch.Stitch

object GrokAutoTranslateLanguageFilter
    extends Filter[ScoredTweetsQuery, TweetCandidate]
    with Filter.Conditionally[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("GrokAutoTranslateLanguage")

  override def onlyIf(
    query: ScoredTweetsQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = query.params(EnableGrokAutoTranslateLanguageFilter)

  override def apply(
    query: ScoredTweetsQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val allUserUnderstandableLanguages: Seq[String] =
      query.features
        .getOrElse(FeatureMap.empty).getOrElse(
          UserUnderstandableLanguagesFeature,
          Seq.empty[String])

    val (kept, removed) = {
      if (!allUserUnderstandableLanguages.isEmpty) {
        candidates.partition { candidate =>
          val inNetwork =
            candidate.features.getOrElse(InNetworkFeature, true)
          val postLanguageOpt =
            candidate.features.getOrElse(TweetLanguageFromTweetypieFeature, None).map(_.toLowerCase)
          val grokTranslateCacheExists =
            candidate.features.getOrElse(GrokTranslatedPostIsCachedFeature, true)

          postLanguageOpt.forall { postLanguage =>
            inNetwork || grokTranslateCacheExists ||
            allUserUnderstandableLanguages.contains(postLanguage)
          }
        }
      } else { (candidates, Seq.empty) }
    }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }
}
