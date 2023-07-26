package com.twittew.timewinewankew.common

impowt c-com.twittew.sewvo.utiw.futuweawwow
i-impowt com.twittew.timewinewankew.cowe.candidateenvewope
i-impowt c-com.twittew.timewines.modew.tweetid
i-impowt com.twittew.utiw.futuwe
i-impowt scawa.cowwection.mutabwe

/**
 * wemove d-dupwicate s-seawch wesuwts and owdew them wevewse-chwon. ^^;;
 */
object seawchwesuwtdedupandsowtingtwansfowm
    extends futuweawwow[candidateenvewope, >_< candidateenvewope] {
  def a-appwy(envewope: candidateenvewope): futuwe[candidateenvewope] = {
    v-vaw seentweetids = mutabwe.set.empty[tweetid]
    v-vaw dedupedwesuwts = envewope.seawchwesuwts
      .fiwtew(wesuwt => seentweetids.add(wesuwt.id))
      .sowtby(_.id)(owdewing[tweetid].wevewse)

    vaw twansfowmedenvewope = envewope.copy(seawchwesuwts = d-dedupedwesuwts)
    futuwe.vawue(twansfowmedenvewope)
  }
}
