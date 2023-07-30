package com.X.frigate.pushservice.predicate.ntab_caret_fatigue

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.predicate.ntab_caret_fatigue.NtabCaretClickFatiguePredicateHelper
import com.X.frigate.common.rec_types.RecTypes
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.hermit.predicate.NamedPredicate

object MagicFanoutNtabCaretFatiguePredicate {
  val name = "MagicFanoutNtabCaretFatiguePredicateForCandidate"

  private val MomentsCategory = "Moments"
  private val MomentsViaMagicRecsCategory = "MomentsViaMagicRecs"

  def apply()(implicit globalStats: StatsReceiver): NamedPredicate[PushCandidate] = {
    val scopedStats = globalStats.scope(name)
    val genericTypeCategories = Seq(MomentsCategory, MomentsViaMagicRecsCategory)
    val crts = RecTypes.magicFanoutEventTypes
    RecTypeNtabCaretClickFatiguePredicate
      .apply(
        genericTypeCategories,
        crts,
        NtabCaretClickFatiguePredicateHelper.calculateFatiguePeriodMagicRecs,
        useMostRecentDislikeTime = true,
        name = name
      ).withStats(scopedStats).withName(name)
  }
}
