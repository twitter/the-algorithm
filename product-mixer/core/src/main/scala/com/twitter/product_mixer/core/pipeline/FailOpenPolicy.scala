package com.twittew.pwoduct_mixew.cowe.pipewine

impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewine_faiwuwe.misconfiguwedfeatuwemapfaiwuwe
impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.pipewine_faiwuwe.pipewinefaiwuwecategowy

/**
 * [[faiwopenpowicy]] d-detewmines nyani s-shouwd happen i-in the event that a-a candidate pipewine f-faiws
 * t-to exekawaii~ successfuwwy. OwO
 *
 * e-exewcise caution when cweating nyew faiw open powicies. 😳😳😳 pwoduct mixew wiww faiw o-open by defauwt in
 * cewtain ewwow cases (e.g. 😳😳😳 c-cwosed gate on a candidate pipewine) b-but these might inadvewtentwy be
 * excwuded by a nyew powicy. o.O
 */
t-twait faiwopenpowicy {
  d-def appwy(faiwuwecategowy: pipewinefaiwuwecategowy): b-boowean
}

object faiwopenpowicy {

  /**
   * awways faiw open on candidate pipewine faiwuwes e-except
   * fow [[misconfiguwedfeatuwemapfaiwuwe]]s because it's a pwogwammew ewwow
   * a-and shouwd awways faiw woudwy, e-even with an [[awways]] p-p[[faiwopenpowicy]]
   */
  v-vaw awways: f-faiwopenpowicy = (categowy: pipewinefaiwuwecategowy) => {
    categowy != m-misconfiguwedfeatuwemapfaiwuwe
  }

  /**
   * nyevew faiw open on candidate p-pipewine faiwuwes. ( ͡o ω ͡o )
   *
   * @note this is mowe westwictive than the defauwt behaviow which i-is to awwow gate cwosed
   *       f-faiwuwes.
   */
  v-vaw nyevew: f-faiwopenpowicy = (_: pipewinefaiwuwecategowy) => fawse

  // buiwd a powicy t-that wiww faiw open f-fow a given set of categowies
  d-def appwy(categowies: s-set[pipewinefaiwuwecategowy]): faiwopenpowicy =
    (faiwuwecategowy: p-pipewinefaiwuwecategowy) =>
      categowies
        .contains(faiwuwecategowy)
}
