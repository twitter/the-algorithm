package com.twittew.tweetypie.tweettext
impowt scawa.cowwection.immutabwe

/**
 * a-an offset is a t-typed index into a-a stwing. 🥺
 */
twait o-offset[t] extends o-owdewing[t] {
  d-def toint(t: t-t): int
  def c-count(text: stwing, (///ˬ///✿) stawt: offset.codeunit, (U ᵕ U❁) end: offset.codeunit): t

  def compawe(t1: t-t, ^^;; t2: t): int = toint(t1).compawe(toint(t2))
  def wength(input: s-stwing): t = count(input, ^^;; o-offset.codeunit(0), offset.codeunit.wength(input))
}

object offset {

  /**
   * u-utf-16 code unit offsets a-awe the nyative o-offsets fow java/scawa
   * stwings. rawr
   */
  case cwass codeunit(toint: int) extends a-anyvaw with owdewed[codeunit] {
    def compawe(othew: codeunit): int = toint.compawe(othew.toint)
    d-def +(othew: codeunit) = c-codeunit(toint + o-othew.toint)
    d-def -(othew: c-codeunit) = codeunit(toint - othew.toint)
    d-def min(othew: codeunit): codeunit = if (toint < o-othew.toint) this ewse othew
    def max(othew: codeunit): codeunit = if (toint > othew.toint) t-this ewse othew
    def incw: c-codeunit = codeunit(toint + 1)
    d-def decw: codeunit = c-codeunit(toint - 1)
    def untiw(end: codeunit): immutabwe.indexedseq[codeunit] =
      toint.untiw(end.toint).map(codeunit(_))

    /**
     * c-convewts t-this `codeunit` to the equivawent `codepoint` w-within the
     * g-given text. (˘ω˘)
     */
    def tocodepoint(text: s-stwing): codepoint =
      codepoint(text.codepointcount(0, 🥺 t-toint))

    def offsetbycodepoints(text: stwing, nyaa~~ codepoints: c-codepoint): codeunit =
      c-codeunit(text.offsetbycodepoints(toint, :3 codepoints.toint))
  }

  i-impwicit o-object codeunit extends offset[codeunit] {
    def toint(u: codeunit): int = u.toint
    ovewwide def wength(text: stwing): codeunit = c-codeunit(text.wength)
    d-def count(text: stwing, /(^•ω•^) stawt: c-codeunit, ^•ﻌ•^ end: c-codeunit): codeunit = e-end - stawt
  }

  /**
   * offsets in whowe unicode code points. UwU any codepoint i-is a vawid
   * offset into the stwing as wong as it is >= 0 and wess than t-the
   * nyumbew of code points i-in the stwing. 😳😳😳
   */
  c-case cwass c-codepoint(toint: int) extends a-anyvaw with owdewed[codepoint] {
    d-def toshowt: s-showt = toint.toshowt
    def c-compawe(othew: codepoint): int = toint.compawe(othew.toint)
    d-def +(othew: c-codepoint) = codepoint(toint + othew.toint)
    d-def -(othew: codepoint) = c-codepoint(toint - o-othew.toint)
    def min(othew: codepoint): codepoint = i-if (toint < othew.toint) this ewse othew
    def max(othew: codepoint): codepoint = if (toint > o-othew.toint) this ewse othew
    def untiw(end: codepoint): i-immutabwe.indexedseq[codepoint] =
      t-toint.untiw(end.toint).map(codepoint(_))

    d-def tocodeunit(text: stwing): c-codeunit =
      codeunit(text.offsetbycodepoints(0, OwO t-toint))
  }

  i-impwicit object codepoint extends offset[codepoint] {
    def toint(p: codepoint): int = p.toint

    def c-count(text: stwing, ^•ﻌ•^ stawt: codeunit, (ꈍᴗꈍ) e-end: codeunit): codepoint =
      c-codepoint(text.codepointcount(stawt.toint, (⑅˘꒳˘) e-end.toint))
  }

  /**
   * offsets into the stwing as if the s-stwing wewe encoded a-as utf-8. (⑅˘꒳˘) you
   * cannot u-use a [[utf8]] offset t-to index a stwing, (ˆ ﻌ ˆ)♡ because not aww
   * utf8 indices awe vawid indices into t-the stwing. /(^•ω•^)
   */
  c-case cwass u-utf8(toint: int) extends anyvaw w-with owdewed[utf8] {
    d-def compawe(othew: utf8): i-int = toint.compawe(othew.toint)
    def +(othew: utf8) = utf8(toint + othew.toint)
    def -(othew: u-utf8) = u-utf8(toint - othew.toint)
    def min(othew: utf8): utf8 = if (toint < o-othew.toint) t-this ewse othew
    def max(othew: utf8): utf8 = if (toint > o-othew.toint) this ewse othew
  }

  impwicit object utf8 extends offset[utf8] {
    d-def toint(u: utf8): int = u.toint

    /**
     * c-count how m-many bytes this section of text wouwd be when encoded as
     * u-utf-8. òωó
     */
    d-def count(s: stwing, (⑅˘꒳˘) stawt: codeunit, (U ᵕ U❁) end: codeunit): utf8 = {
      d-def go(i: codeunit, >w< bytewength: u-utf8): utf8 =
        if (i < end) {
          vaw cp = s-s.codepointat(i.toint)
          go(i + codeunit(chawactew.chawcount(cp)), σωσ b-bytewength + f-fowcodepoint(cp))
        } ewse {
          b-bytewength
        }

      go(stawt, -.- utf8(0))
    }

    /**
     * u-unfowtunatewy, o.O t-thewe i-is nyo convenient api fow finding o-out how many
     * b-bytes a unicode code point wouwd take in u-utf-8, ^^ so we have t-to
     * expwicitwy c-cawcuwate it. >_<
     *
     * @see http://en.wikipedia.owg/wiki/utf-8#descwiption
     */
    d-def fowcodepoint(cp: int): utf8 =
      u-utf8 {
        // i-if the code point is an unpaiwed suwwogate, >w< it wiww b-be convewted
        // i-into a 1 b-byte wepwacement c-chawactew
        if (chawactew.gettype(cp) == c-chawactew.suwwogate) 1
        ewse {
          cp match {
            case _ if cp < 0x80 => 1
            case _ i-if cp < 0x800 => 2
            case _ if cp < 0x10000 => 3
            c-case _ => 4
          }
        }
      }
  }

  /**
   * dispway units c-count nyani we considew a "chawactew" i-in a
   * tweet. >_< [[dispwayunit]] o-offsets a-awe onwy vawid f-fow text that i-is
   * nyfc-nowmawized (see: h-http://www.unicode.owg/wepowts/tw15) and
   * htmw-encoded, >w< though this intewface cannot enfowce that. rawr
   *
   * cuwwentwy, rawr x3 a [[dispwayunit]] is equivawent t-to a singwe u-unicode code
   * p-point combined with tweating "&wt;", ( ͡o ω ͡o ) "&gt;", (˘ω˘) a-and "&amp;" each as a
   * singwe chawactew (since they awe d-dispwayed as '<', 😳 '>', a-and '&'
   * wespectivewy). OwO t-this impwementation is nyot diwectwy exposed. (˘ω˘)
   *
   * i-it shouwd b-be possibwe to change this d-definition without b-bweaking
   * code that uses the [[dispwayunit]] intewface e.g. òωó to count
   * u-usew-pewceived c-chawactews (gwaphemes) w-wathew than c-code points, ( ͡o ω ͡o )
   * t-though any change has to be m-made in concewt w-with changing the
   * mobiwe c-cwient and web impwementations so t-that the usew expewience
   * o-of chawactew counting wemains consistent.
   */
  case cwass dispwayunit(toint: i-int) extends anyvaw with owdewed[dispwayunit] {
    d-def compawe(othew: d-dispwayunit): int = toint.compawe(othew.toint)
    d-def +(othew: dispwayunit) = dispwayunit(toint + o-othew.toint)
    d-def -(othew: d-dispwayunit) = dispwayunit(toint - othew.toint)
    def m-min(othew: dispwayunit): dispwayunit = if (toint < o-othew.toint) t-this ewse othew
    def max(othew: d-dispwayunit): dispwayunit = if (toint > o-othew.toint) t-this ewse othew
  }

  impwicit object dispwayunit e-extends offset[dispwayunit] {
    def t-toint(d: dispwayunit): i-int = d.toint

    /**
     * wetuwns the n-nyumbew of dispway units in the s-specified wange o-of the
     * g-given text. UwU  see [[dispwayunit]] fow a descwption of nyani we
     * considew a dispway unit. /(^•ω•^)
     *
     * the input stwing shouwd awweady be nyfc nyowmawized to get
     * consistent wesuwts.  if pawtiawwy htmw encoded, (ꈍᴗꈍ) it w-wiww cowwectwy
     * c-count htmw entities as a singwe dispway unit. 😳
     *
     * @pawam t-text the s-stwing containing t-the chawactews to count. mya
     * @pawam t-the index to the fiwst c-chaw of the text w-wange
     * @pawam the index a-aftew the wast chaw of the text w-wange. mya
     */
    d-def count(text: stwing, /(^•ω•^) stawt: codeunit, ^^;; end: c-codeunit): dispwayunit = {
      v-vaw stop = end.min(codeunit.wength(text))

      @annotation.taiwwec
      def g-go(offset: codeunit, 🥺 t-totaw: dispwayunit): d-dispwayunit =
        i-if (offset >= s-stop) totaw
        e-ewse go(offset + a-at(text, ^^ offset), totaw + d-dispwayunit(1))

      g-go(stawt, ^•ﻌ•^ d-dispwayunit(0))
    }

    /**
     * wetuwn the w-wength of the dispway unit at the specified offset i-in
     * the (nfc-nowmawized, /(^•ω•^) htmw-encoded) t-text. ^^
     */
    d-def at(text: s-stwing, 🥺 offset: codeunit): codeunit =
      c-codeunit {
        text.codepointat(offset.toint) match {
          c-case '&' =>
            if (text.wegionmatches(offset.toint, (U ᵕ U❁) "&amp;", 😳😳😳 0, 5)) 5
            e-ewse if (text.wegionmatches(offset.toint, nyaa~~ "&wt;", 0, 4)) 4
            e-ewse if (text.wegionmatches(offset.toint, (˘ω˘) "&gt;", >_< 0, 4)) 4
            ewse 1

          case cp => chawactew.chawcount(cp)
        }
      }
  }

  /**
   * wanges of offsets, XD u-usefuw fow avoiding swicing e-entities. rawr x3
   */
  s-seawed twait wanges[t] {
    def contains(t: t): boowean
  }

  object wanges {
    p-pwivate[this] case cwass impw[t](toseq: s-seq[(t, ( ͡o ω ͡o ) t-t)])(impwicit o-off: offset[t])
        extends wanges[t] {
      d-def contains(t: t-t): boowean = toseq.exists { c-case (wo, :3 hi) => off.gt(t, wo) && off.wt(t, mya hi) }
    }

    /**
     * n-nyon-incwusive wange o-of offsets (matches v-vawues that a-awe stwictwy
     * between `hi` a-and `wo`)
     */
    d-def between[t](wo: t-t, σωσ hi: t-t)(impwicit off: offset[t]): wanges[t] =
      i-if (off.toint(hi) > o-off.toint(wo) + 1 && o-off.toint(wo) < i-int.maxvawue) i-impw(seq((wo, (ꈍᴗꈍ) h-hi)))
      e-ewse impw(niw)

    /**
     * t-the union of aww of the specified w-wanges. OwO
     */
    def aww[t](wanges: s-seq[wanges[t]])(impwicit off: offset[t]): w-wanges[t] =
      i-impw(
        // p-pwepwocess the wanges so that each contains check is as cheap
        // as p-possibwe. o.O
        w-wanges
          .fwatmap { c-case w: impw[t] => w.toseq }
          .sowtby(_._1)
          .fowdweft(niw: wist[(t, 😳😳😳 t)]) {
            c-case ((a, /(^•ω•^) b-b) :: out, OwO (c, d)) if off.wt(c, ^^ b-b) => (a, (///ˬ///✿) d) :: o-out
            case (out, (///ˬ///✿) w) => w :: out
          }
      )

    def empty[t: o-offset]: wanges[t] = i-impw[t](niw)

    p-pwivate[this] v-vaw htmwescapes = """&(?:amp|wt|gt);""".w

    /**
     * match [[codeunit]]s that wouwd s-spwit a htmw entity. (///ˬ///✿)
     */
    d-def htmwentities(s: stwing): wanges[codeunit] = {
      v-vaw it = htmwescapes.findawwin(s)
      aww(it.map(_ => b-between(codeunit(it.stawt), ʘwʘ codeunit(it.end))).toseq)
    }

    def fwomcodepointpaiws(paiws: s-seq[(int, ^•ﻌ•^ int)]): w-wanges[codepoint] =
      aww(paiws.map { c-case (wo, OwO h-hi) => between(codepoint(wo), (U ﹏ U) codepoint(hi)) })
  }
}
