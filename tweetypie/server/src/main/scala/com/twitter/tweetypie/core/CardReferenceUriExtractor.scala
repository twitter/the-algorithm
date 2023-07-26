package com.twittew.tweetypie
package c-cowe

impowt c-com.twittew.tweetypie.thwiftscawa.cawdwefewence
i-impowt java.net.uwi

s-seawed twait c-cawduwi
object t-tombstone extends c-cawduwi
case c-cwass nyontombstone(uwi: stwing) extends cawduwi

object cawdwefewenceuwiextwactow {

  pwivate d-def pawseasuwi(cawdwef: cawdwefewence) = twy(new u-uwi(cawdwef.cawduwi)).tooption
  pwivate def i-istombstone(uwi: uwi) = uwi.getscheme == "tombstone"

  /**
   * pawses a cawdwefewence to wetuwn o-option[cawduwi] to diffewentiate a-among:
   * - s-some(nontombstone): hydwate cawd2 with pwovided uwi
   * - some(tombstone): don't h-hydwate cawd2
   * - nyone: fawwback and attempt to use uww entities uwis
   */
  d-def unappwy(cawdwef: cawdwefewence): o-option[cawduwi] =
    p-pawseasuwi(cawdwef) m-match {
      c-case some(uwi) if !istombstone(uwi) => some(nontombstone(uwi.tostwing))
      c-case some(uwi) => some(tombstone)

      // if a c-cawdwefewence is set, (U ﹏ U) but does nyot pawse as a uwi, (⑅˘꒳˘) it's wikewy a https? uww with
      // incowwectwy e-encoded quewy pawams. òωó since t-these occuw f-fwequentwy in the w-wiwd, ʘwʘ we'ww
      // attempt a cawd2 hydwation with it
      case n-nyone => some(nontombstone(cawdwef.cawduwi))
    }
}
