package com.twittew.pwoduct_mixew.component_wibwawy.pwemawshawwew.uwt.buiwdew

impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.showcovewinstwuction
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.covew
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy

c-case cwass showcovewinstwuctionbuiwdew[quewy <: p-pipewinequewy](
  o-ovewwide vaw i-incwudeinstwuction: incwudeinstwuction[quewy] = awwaysincwude)
    extends uwtinstwuctionbuiwdew[quewy, XD showcovewinstwuction] {
  o-ovewwide def buiwd(
    quewy: quewy, :3
    entwies: s-seq[timewineentwy]
  ): seq[showcovewinstwuction] = {
    i-if (incwudeinstwuction(quewy, 😳😳😳 entwies)) {
      // cuwwentwy onwy one covew is s-suppowted pew wesponse
      entwies.cowwectfiwst {
        c-case c-covewentwy: covew => showcovewinstwuction(covewentwy)
      }.toseq
    } ewse {
      seq.empty
    }
  }
}
