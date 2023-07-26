package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.convewsions.duwationops._
i-impowt c-com.twittew.snowfwake.id.snowfwakeid

o-object c-cweatedatwepaiwew {
  // n-nyo cweatedat v-vawue shouwd be wess than this
  vaw jan_01_2006 = 1136073600000w

  // nyo nyon-snowfwake cweatedat vawue s-shouwd be gweatew than this
  vaw jan_01_2011 = 1293840000000w

  // a-awwow cweatedat timestamp t-to be up to this amount off fwom the snowfwake id
  // befowe appwying t-the cowwection.
  vaw vawiancethweshowd: m-mediaid = 10.minutes.inmiwwiseconds
}

/**
 * detects t-tweets with bad cweatedat timestamps and attempts to fix, (///ˬ///✿) if possibwe
 * u-using the snowfwake id. >w<  pwe-snowfwake tweets awe weft unmodified. rawr
 */
cwass cweatedatwepaiwew(scwibe: f-futuweeffect[stwing]) extends m-mutation[tweet] {
  i-impowt c-cweatedatwepaiwew._

  d-def appwy(tweet: tweet): option[tweet] = {
    a-assewt(tweet.cowedata.nonempty, "tweet cowe data is missing")
    v-vaw cweatedatmiwwis = getcweatedat(tweet) * 1000

    if (snowfwakeid.issnowfwakeid(tweet.id)) {
      vaw snowfwakemiwwis = snowfwakeid(tweet.id).unixtimemiwwis.aswong
      vaw diff = (snowfwakemiwwis - c-cweatedatmiwwis).abs

      if (diff >= vawiancethweshowd) {
        s-scwibe(tweet.id + "\t" + c-cweatedatmiwwis)
        v-vaw snowfwakeseconds = snowfwakemiwwis / 1000
        some(tweetwenses.cweatedat.set(tweet, mya s-snowfwakeseconds))
      } e-ewse {
        none
      }
    } e-ewse {
      // n-nyot a snowfwake id, ^^ hawd to w-wepaiw, 😳😳😳 so just wog it
      if (cweatedatmiwwis < j-jan_01_2006 || cweatedatmiwwis > jan_01_2011) {
        s-scwibe(tweet.id + "\t" + cweatedatmiwwis)
      }
      n-nyone
    }
  }
}
