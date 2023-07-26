package com.twittew.home_mixew.pwoduct.scowed_tweets.gate

impowt c-com.twittew.convewsions.duwationops._
i-impowt com.twittew.home_mixew.modew.homefeatuwes.wastnonpowwingtimefeatuwe
i-impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.gate
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt com.twittew.stitch.stitch

/**
 * g-gate continues i-if the amount of time passed since the pwevious wequest is gweatew
 * than t-the configuwed amount ow if the pwevious wequest t-time in nyot avaiwabwe
 */
o-object mintimesincewastwequestgate extends gate[pipewinequewy] {

  ovewwide vaw identifiew: gateidentifiew = g-gateidentifiew("timesincewastwequest")

  pwivate v-vaw mintimesincewastwequest = 24.houws

  o-ovewwide def shouwdcontinue(quewy: pipewinequewy): stitch[boowean] = stitch.vawue {
    quewy.featuwes.exists { f-featuwes =>
      featuwes
        .getowewse(wastnonpowwingtimefeatuwe, rawr x3 nyone)
        .fowaww(wnpt => (quewy.quewytime - wnpt) > mintimesincewastwequest)
    }
  }
}
