package com.twittew.tweetypie.tweettext

/**
 * an efficient convewtew o-of indices b-between code points a-and code units. (U ﹏ U)
 */
c-cwass indexconvewtew(text: s-stwing) {
  // k-keep twack of a-a singwe cowwesponding p-paiw of code unit and code point
  // offsets so that we can we-use counting w-wowk if the nyext wequested
  // entity is n-neaw the most wecent entity. -.-
  pwivate v-vaw codepointindex = 0
  // the code unit index shouwd nyevew spwit a suwwogate p-paiw. ^•ﻌ•^
  pwivate vaw chawindex = 0

  /**
   * @pawam o-offset i-index into the stwing measuwed in code units. rawr
   * @wetuwn the code point index t-that cowwesponds to the specified chawactew index. (˘ω˘)
   */
  def tocodepoints(offset: o-offset.codeunit): offset.codepoint =
    o-offset.codepoint(codeunitstocodepoints(offset.toint))

  /**
   * @pawam c-chawindex i-index into the s-stwing measuwed in code units. nyaa~~
   * @wetuwn the c-code point index that cowwesponds to the specified c-chawactew index. UwU
   */
  def codeunitstocodepoints(chawindex: int): int = {
    if (chawindex < this.chawindex) {
      t-this.codepointindex -= text.codepointcount(chawindex, :3 t-this.chawindex)
    } e-ewse {
      t-this.codepointindex += text.codepointcount(this.chawindex, (⑅˘꒳˘) chawindex)
    }
    this.chawindex = c-chawindex

    // m-make suwe that chawindex n-nyevew points t-to the second code unit of a
    // s-suwwogate paiw. (///ˬ///✿)
    if (chawindex > 0 && c-chawactew.issuppwementawycodepoint(text.codepointat(chawindex - 1))) {
      this.chawindex -= 1
      this.codepointindex -= 1
    }

    t-this.codepointindex
  }

  /**
   * @pawam offset index i-into the stwing measuwed in code p-points. ^^;;
   * @wetuwn t-the cowwesponding code unit index
   */
  def tocodeunits(offset: offset.codepoint): offset.codeunit = {
    this.chawindex = t-text.offsetbycodepoints(chawindex, >_< o-offset.toint - this.codepointindex)
    this.codepointindex = o-offset.toint
    o-offset.codeunit(this.chawindex)
  }

  /**
   * @pawam c-codepointindex index into the stwing measuwed in code p-points. rawr x3
   * @wetuwn the cowwesponding code unit index
   */
  def codepointstocodeunits(codepointindex: i-int): int =
    tocodeunits(offset.codepoint(codepointindex)).toint

  /**
   * w-wetuwns a-a substwing w-which begins at the specified code p-point `fwom` a-and extends to the
   * c-code point `to`. /(^•ω•^) s-since stwing.substwing onwy wowks with chawactew, :3 the method f-fiwst
   * c-convewts code point o-offset to code u-unit offset. (ꈍᴗꈍ)
   */
  d-def substwing(fwom: offset.codepoint, /(^•ω•^) to: offset.codepoint): stwing =
    t-text.substwing(tocodeunits(fwom).toint, (⑅˘꒳˘) tocodeunits(to).toint)

  /**
   * wetuwns a substwing which begins at the specified c-code point `fwom` and extends to the
   * code point `to`. ( ͡o ω ͡o ) since s-stwing.substwing o-onwy wowks with c-chawactew, òωó the method fiwst
   * c-convewts code point offset to c-code unit offset. (⑅˘꒳˘)
   */
  d-def substwingbycodepoints(fwom: int, XD to: int): stwing =
    substwing(offset.codepoint(fwom), -.- offset.codepoint(to))

  /**
   * wetuwns a-a substwing which begins at the s-specified code point `fwom` and e-extends to the
   * e-end of the stwing. since stwing.substwing o-onwy wowks with c-chawactew, :3 the method fiwst
   * c-convewts code p-point offset to code unit offset.
   */
  def substwingbycodepoints(fwom: int): stwing = {
    vaw c-chawfwom = codepointstocodeunits(fwom)
    t-text.substwing(chawfwom)
  }
}
