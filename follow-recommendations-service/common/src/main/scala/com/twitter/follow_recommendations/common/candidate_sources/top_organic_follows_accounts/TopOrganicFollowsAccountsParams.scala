packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.top_organic_follows_accounts

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSelonnumSelonqParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct TopOrganicFollowsAccountsParams {
  // whelonthelonr or not to felontch TopOrganicFollowsAccounts candidatelon sourcelons
  caselon objelonct CandidatelonSourcelonelonnablelond
      elonxtelonnds FSParam[Boolelonan]("top_organic_follows_accounts_candidatelon_sourcelon_elonnablelond", falselon)

  /**
   *   Contains thelon logic kelony for account filtelonring and ranking. Currelonntly welon havelon 3 main logic kelonys
   *    - nelonw_organic_follows: filtelonring top organically followelond accounts followelond by nelonw uselonrs
   *    - non_nelonw_organic_follows: filtelonring top organically followelond accounts followelond by non nelonw uselonrs
   *    - organic_follows: filtelonring top organically followelond accounts followelond by all uselonrs
   *    Mapping of thelon Logic Id to Logic kelony is donelon via @elonnum AccountsFiltelonringAndRankingLogic
   */
  caselon objelonct AccountsFiltelonringAndRankingLogics
      elonxtelonnds FSelonnumSelonqParam[AccountsFiltelonringAndRankingLogicId.typelon](
        namelon = "top_organic_follows_accounts_filtelonring_and_ranking_logic_ids",
        delonfault = Selonq(AccountsFiltelonringAndRankingLogicId.OrganicFollows),
        elonnum = AccountsFiltelonringAndRankingLogicId)

  caselon objelonct CandidatelonSourcelonWelonight
      elonxtelonnds FSBoundelondParam[Doublelon](
        "top_organic_follows_accounts_candidatelon_sourcelon_welonight",
        delonfault = 1200,
        min = 0.001,
        max = 2000)
}
