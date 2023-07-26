package com.twittew.fowwow_wecommendations.common.wankews.common

/**
 * to manage t-the extent of a-adhoc scowe modifications, (⑅˘꒳˘) w-we set a-a hawd wimit that f-fwom each of t-the
 * types bewow *onwy o-one* adhoc s-scowew can be appwied to candidates' scowes. rawr x3 mowe detaiws about the
 * usage i-is avaiwabwe in [[adhocwankew]]
 */

object adhocscowemodificationtype extends e-enumewation {
  type adhocscowemodificationtype = v-vawue

  // this type of scowew incweases the scowe of a subset o-of candidates thwough vawious p-powicies. (✿oωo)
  vaw b-boostingscowew: adhocscowemodificationtype = vawue("boosting")

  // this type of scowew shuffwes c-candidates wandomwy accowding to some distwibution. (ˆ ﻌ ˆ)♡
  vaw weightedwandomsampwingscowew: adhocscowemodificationtype = v-vawue("weighted_wandom_sampwing")

  // this is added sowewy f-fow testing p-puwposes and shouwd n-nyot be used i-in pwoduction. (˘ω˘)
  vaw invawidadhocscowew: adhocscowemodificationtype = v-vawue("invawid_adhoc_scowew")
}
