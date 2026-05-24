package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.home_mixer.functional_component.feature_hydrator.UserLanguagesFeature
import com.twitter.home_mixer.model.HomeFeatures.DeviceLanguageFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetLanguageFromTweetypieFeature
import com.twitter.home_mixer.model.HomeFeatures.UserEngagedLanguagesFeature
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableLanguageFilter
import com.twitter.home_mixer.util.LanguageCode.AllowedLanguageCodes
import com.twitter.home_mixer.util.LanguageCode.languageToISO
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.stitch.Stitch

object LanguageFilter
    extends Filter[ScoredTweetsQuery, TweetCandidate]
    with Filter.Conditionally[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("Language")

  override def onlyIf(
    query: ScoredTweetsQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = query.params(EnableLanguageFilter)

  override def apply(
    query: ScoredTweetsQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val userLanguages = query.features.get
      .getOrElse(UserLanguagesFeature, Seq.empty)
      .flatMap(lang => languageToISO.get(lang.toString.toLowerCase))
      .toSet

    val userEngagedLanguages = query.features.get
      .getOrElse(UserEngagedLanguagesFeature, Set.empty[String])
      .map(_.toLowerCase)

    val deviceLanguage =
      query.features.get.getOrElse(DeviceLanguageFeature, None).map(_.toLowerCase).toSet

    val allUserLanguages = userLanguages ++ userEngagedLanguages ++ deviceLanguage

    val (kept, removed) = if (!allUserLanguages.isEmpty) {
      candidates.partition { candidate =>
        val inNetwork = candidate.features.getOrElse(InNetworkFeature, true)

        val postLanguage = candidate.features
          .getOrElse(TweetLanguageFromTweetypieFeature, None)
          .map(_.toLowerCase)

        postLanguage.forall { lang =>
          inNetwork || allUserLanguages.contains(lang) || AllowedLanguageCodes.contains(lang)
        }
      }
    } else { (candidates, Seq.empty) }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }
}
