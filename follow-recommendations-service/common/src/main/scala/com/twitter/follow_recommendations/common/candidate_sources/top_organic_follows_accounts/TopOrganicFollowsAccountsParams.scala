package com.twittew.fowwow_wecommendations.common.candidate_souwces.top_owganic_fowwows_accounts

impowt com.twittew.timewines.configapi.fsboundedpawam
i-impowt com.twittew.timewines.configapi.fsenumseqpawam
i-impowt c-com.twittew.timewines.configapi.fspawam

o-object t-topowganicfowwowsaccountspawams {
  // w-whethew o-ow nyot to fetch t-topowganicfowwowsaccounts candidate souwces
  case object candidatesouwceenabwed
      extends f-fspawam[boowean]("top_owganic_fowwows_accounts_candidate_souwce_enabwed", 🥺 fawse)

  /**
   *   contains the wogic k-key fow account fiwtewing and w-wanking. >_< cuwwentwy we have 3 main wogic keys
   *    - nyew_owganic_fowwows: f-fiwtewing top owganicawwy fowwowed a-accounts fowwowed b-by nyew usews
   *    - nyon_new_owganic_fowwows: fiwtewing top owganicawwy fowwowed accounts f-fowwowed by nyon new usews
   *    - owganic_fowwows: fiwtewing top owganicawwy f-fowwowed accounts fowwowed by a-aww usews
   *    m-mapping of the w-wogic id to wogic k-key is done via @enum accountsfiwtewingandwankingwogic
   */
  case object accountsfiwtewingandwankingwogics
      e-extends fsenumseqpawam[accountsfiwtewingandwankingwogicid.type](
        nyame = "top_owganic_fowwows_accounts_fiwtewing_and_wanking_wogic_ids", >_<
        defauwt = seq(accountsfiwtewingandwankingwogicid.owganicfowwows), (⑅˘꒳˘)
        e-enum = accountsfiwtewingandwankingwogicid)

  case object candidatesouwceweight
      extends fsboundedpawam[doubwe](
        "top_owganic_fowwows_accounts_candidate_souwce_weight", /(^•ω•^)
        defauwt = 1200, rawr x3
        m-min = 0.001, (U ﹏ U)
        max = 2000)
}
