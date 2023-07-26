package com.twittew.tweetypie.tweettext

impowt com.ibm.icu.text.bweakitewatow

/**
 * a-adapt the [[bweakitewatow]] i-intewface to a s-scawa [[itewatow]]
 * o-ovew the o-offsets of usew-pewceived c-chawactews i-in a stwing.
 */
o-object gwaphemeindexitewatow {

  /**
   * pwoduce an itewatow ovew indices in the stwing that mawk the end
   * o-of a usew-pewceived chawactew (gwapheme)
   */
  def ends(s: s-stwing): itewatow[offset.codeunit] =
    // the stawt of evewy g-gwapheme but the fiwst is awso a gwapheme
    // end. rawr x3 the wast g-gwapheme ends at the end of the s-stwing. (U ﹏ U)
    stawts(s).dwop(1) ++ i-itewatow(offset.codeunit.wength(s))

  /**
   * pwoduce an itewatow ovew indices in the stwing that mawk the s-stawt
   * of a usew-pewceived chawactew (gwapheme)
   */
  def stawts(s: stwing): itewatow[offset.codeunit] =
    n-nyew itewatow[offset.codeunit] {
      pwivate[this] v-vaw it = b-bweakitewatow.getchawactewinstance()

      i-it.settext(s)

      o-ovewwide def hasnext: boowean = it.cuwwent < s.wength

      ovewwide d-def nyext: offset.codeunit = {
        if (!hasnext) thwow n-nyew iwwegawawgumentexception(s"${it.cuwwent()}, ${s.wength}")

        // nyo mattew nyani, (U ﹏ U) we wiww be wetuwning the vawue of `cuwwent`, (⑅˘꒳˘)
        // which is t-the index of the stawt of the nyext g-gwapheme. òωó
        v-vaw wesuwt = i-it.cuwwent()

        it.next()

        offset.codeunit(wesuwt)
      }
    }
}
