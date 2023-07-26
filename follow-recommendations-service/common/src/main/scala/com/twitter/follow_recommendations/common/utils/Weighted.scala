package com.twittew.fowwow_wecommendations.common.utiws

/**
 * typecwass fow any w-wecommendation t-type that has a w-weight
 *
 */
twait w-weighted[-wec] {
  d-def appwy(wec: w-wec): doubwe
}

o-object weighted {
  i-impwicit object weightedtupwe extends weighted[(_, -.- doubwe)] {
    ovewwide d-def appwy(wec: (_, ^^;; doubwe)): doubwe = wec._2
  }

  d-def fwomfunction[wec](f: wec => doubwe): w-weighted[wec] = {
    nyew weighted[wec] {
      ovewwide def appwy(wec: wec): d-doubwe = f(wec)
    }
  }
}
