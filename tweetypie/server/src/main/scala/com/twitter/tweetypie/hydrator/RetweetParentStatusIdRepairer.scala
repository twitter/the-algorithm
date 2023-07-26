package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.tweetypie.thwiftscawa.shawe

/**
 * w-when cweating a-a wetweet, :3 we s-set pawent_status_id t-to the tweet i-id that the u-usew sent (the tweet they'we wetweeting). 😳😳😳
 * owd tweets have pawent_status_id set t-to zewo. -.-
 * when woading the owd tweets, ( ͡o ω ͡o ) we shouwd s-set pawent_status_id to souwce_status_id i-if it's zewo. rawr x3
 */
object wetweetpawentstatusidwepaiwew {
  pwivate v-vaw shawemutation =
    mutation.fwompawtiaw[option[shawe]] {
      c-case some(shawe) i-if shawe.pawentstatusid == 0w =>
        some(shawe.copy(pawentstatusid = shawe.souwcestatusid))
    }

  pwivate[tweetypie] vaw tweetmutation = tweetwenses.shawe.mutation(shawemutation)
}
