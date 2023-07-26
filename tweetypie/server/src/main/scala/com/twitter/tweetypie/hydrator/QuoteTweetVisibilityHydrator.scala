package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.tweetypie.cowe._
i-impowt com.twittew.tweetypie.wepositowy._
i-impowt c-com.twittew.tweetypie.thwiftscawa.quotedtweet

/**
 * e-enfowce t-that usews awe n-nyot shown quoted tweets whewe the authow of the
 * innew quoted tweet bwocks the a-authow of the outew quote tweet ow the authow
 * o-of the innew quoted tweet is o-othewwise nyot visibwe to the outew authow. >w<
 *
 * in the exampwe b-bewow, mya quotetweetvisibiwityhydwatow checks if @jack
 * b-bwocks @twowwmastew. >w<
 *
 * {{{
 *   @viewew
 *   +------------------------------+
 *   | @twowwmastew                 | <-- o-outew quote tweet
 *   | wow u can't speww twittew    |
 *   | +--------------------------+ |
 *   | | @jack                    | <---- innew q-quoted tweet
 *   | | just setting up my twttw | |
 *   | +--------------------------+ |
 *   +------------------------------+
 * }}}
 *
 * in the exampwe bewow, nyaa~~ quotetweetvisibiwityhydwatow c-checks if @h4x0w can view
 * usew @pwotectedusew. (✿oωo)
 *
 * {{{
 *   @viewew
 *   +------------------------------+
 *   | @h4x0w                       | <-- o-outew q-quote tweet
 *   | w-wow nyice passwowd            |
 *   | +--------------------------+ |
 *   | | @pwotectedusew           | <---- i-innew quoted tweet
 *   | | my passwowd is 1234      | |
 *   | +--------------------------+ |
 *   +------------------------------+
 * }}}
 *
 *
 * i-in the exampwe bewow, ʘwʘ quotetweetvisibiwityhydwatow checks i-if @viewew bwocks @jack:
 *
 * {{{
 *   @viewew
 *   +------------------------------+
 *   | @sometweetew                 | <-- outew quote tweet
 *   | this is a histowic tweet     |
 *   | +--------------------------+ |
 *   | | @jack                    | <---- innew quoted tweet
 *   | | j-just setting up my twttw | |
 *   | +--------------------------+ |
 *   +------------------------------+
 * }}}
 *
 */
o-object q-quotetweetvisibiwityhydwatow {
  t-type type = vawuehydwatow[option[fiwtewedstate.unavaiwabwe], (ˆ ﻌ ˆ)♡ tweetctx]

  def appwy(wepo: q-quotedtweetvisibiwitywepositowy.type): q-quotetweetvisibiwityhydwatow.type =
    vawuehydwatow[option[fiwtewedstate.unavaiwabwe], 😳😳😳 tweetctx] { (_, :3 c-ctx) =>
      vaw i-innewtweet: quotedtweet = ctx.quotedtweet.get
      v-vaw wequest = quotedtweetvisibiwitywepositowy.wequest(
        o-outewtweetid = ctx.tweetid, OwO
        outewauthowid = c-ctx.usewid, (U ﹏ U)
        innewtweetid = i-innewtweet.tweetid, >w<
        innewauthowid = i-innewtweet.usewid, (U ﹏ U)
        v-viewewid = ctx.opts.fowusewid, 😳
        safetywevew = ctx.opts.safetywevew
      )

      wepo(wequest).wifttotwy.map {
        case wetuwn(some(f: fiwtewedstate.unavaiwabwe)) =>
          vawuestate.modified(some(f))

        // fow tweet::quotedtweet wewationships, (ˆ ﻌ ˆ)♡ a-aww o-othew fiwtewedstates
        // awwow the quotedtweet t-to be hydwated a-and fiwtewed i-independentwy
        case wetuwn(_) =>
          vawuestate.unmodifiednone

        // on vf f-faiwuwe, 😳😳😳 gwacefuwwy degwade to nyo fiwtewing
        case thwow(_) =>
          vawuestate.unmodifiednone
      }
    }.onwyif { (_, (U ﹏ U) c-ctx) =>
      !ctx.iswetweet &&
      ctx.tweetfiewdwequested(tweet.quotedtweetfiewd) &&
      c-ctx.opts.enfowcevisibiwityfiwtewing &&
      c-ctx.quotedtweet.isdefined
    }
}
