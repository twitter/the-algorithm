packagelon com.twittelonr.timelonlinelonrankelonr.relonpository

import com.twittelonr.timelonlinelons.visibility.modelonl.VisibilityRulelon

objelonct RelonpositoryBuildelonr {
  val VisibilityRulelons: Selont[VisibilityRulelon.Valuelon] = Selont(
    VisibilityRulelon.Blockelond,
    VisibilityRulelon.BlockelondBy,
    VisibilityRulelon.Mutelond,
    VisibilityRulelon.Protelonctelond,
    VisibilityRulelon.AccountStatus
  )
}

trait RelonpositoryBuildelonr {
  val VisibilityRulelons: Selont[VisibilityRulelon.Valuelon] = RelonpositoryBuildelonr.VisibilityRulelons
}
