packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.crowd_selonarch_accounts

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSelonnumSelonqParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct CrowdSelonarchAccountsParams {
  // whelonthelonr or not to felontch CrowdSelonarchAccounts candidatelon sourcelons
  caselon objelonct CandidatelonSourcelonelonnablelond
      elonxtelonnds FSParam[Boolelonan]("crowd_selonarch_accounts_candidatelon_sourcelon_elonnablelond", falselon)

  /**
   *   Contains thelon logic kelony for account filtelonring and ranking. Currelonntly welon havelon 3 main logic kelonys
   *    - nelonw_daily: filtelonring top selonarchelond accounts with max daily selonarchelons baselond on nelonw uselonrs
   *    - nelonw_welonelonkly: filtelonring top selonarchelond accounts with max welonelonkly selonarchelons baselond on nelonw uselonrs
   *    - daily: filtelonring top selonarchelond accounts with max daily selonarchelons
   *    - welonelonkly: filtelonring top selonarchelond accounts with max welonelonkly selonarchelons
   *    Mapping of thelon Logic Id to Logic kelony is donelon via @elonnum AccountsFiltelonringAndRankingLogic
   */
  caselon objelonct AccountsFiltelonringAndRankingLogics
      elonxtelonnds FSelonnumSelonqParam[AccountsFiltelonringAndRankingLogicId.typelon](
        namelon = "crowd_selonarch_accounts_filtelonring_and_ranking_logic_ids",
        delonfault = Selonq(AccountsFiltelonringAndRankingLogicId.SelonarchelonsWelonelonkly),
        elonnum = AccountsFiltelonringAndRankingLogicId)

  caselon objelonct CandidatelonSourcelonWelonight
      elonxtelonnds FSBoundelondParam[Doublelon](
        "crowd_selonarch_accounts_candidatelon_sourcelon_welonight",
        delonfault = 1200,
        min = 0.001,
        max = 2000)
}
