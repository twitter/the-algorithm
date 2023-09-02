package com.twitter.home_mixer.product.scored_tweets.scoring_pipeline

import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.scorer.HeuristicScorer
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mixer.core.pipeline.scoring.ScoringPipelineConfig

object ScoredTweetsHeuristicScoringPipelineConfig
    extends ScoringPipelineConfig[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: ScoringPipelineIdentifier =
    ScoringPipelineIdentifier("ScoredTweetsHeuristic")

  override val selectors: Seq[Selector[ScoredTweetsQuery]] = Seq(InsertAppendResults(AllPipelines))

  override val scorers: Seq[Scorer[ScoredTweetsQuery, TweetCandidate]] =
    Seq(HeuristicScorer)
}
