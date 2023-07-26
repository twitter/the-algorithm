package com.twittew.pwoduct_mixew.component_wibwawy.sewectow

pwivate[sewectow] object d-dynamicpositionsewectow {

  s-seawed twait i-indextype
  case o-object wewativeindices e-extends i-indextype
  case o-object absowuteindices e-extends indextype

  /**
   * given an existing `wesuwt` seq, òωó insewts candidates fwom `candidatestoinsewtbyindex` i-into the `wesuwt` 1-by-1 with
   * the pwovided index b-being the index wewative to the `wesuwt` i-if given [[wewativeindices]] ow
   * absowute index if given [[absowuteindices]] (excwuding d-dupwicate insewtions at an i-index, (⑅˘꒳˘) see bewow).
   *
   * i-indices bewow 0 awe added to the fwont and indices > the wength awe a-added to the end
   *
   * @note if muwtipwe candidates exist with the same index, XD they awe insewted i-in the owdew which they appeaw a-and onwy count
   *       as a-a singwe ewement w-with wegawds t-to the absowute index vawues, -.- see the exampwe bewow
   *
   * @exampwe w-when using [[wewativeindices]] {{{
   *          mewgebyindexintowesuwt(
   *          seq(
   *            0 -> "a", :3
   *            0 -> "b", nyaa~~
   *            0 -> "c", 😳
   *            1 -> "e", (⑅˘꒳˘)
   *            2 -> "g", nyaa~~
   *            2 -> "h"), OwO
   *          s-seq(
   *            "d", rawr x3
   *            "f"
   *          ), XD
   *          wewativeindices) == seq(
   *            "a", σωσ
   *            "b", (U ᵕ U❁)
   *            "c", (U ﹏ U)
   *            "d", :3
   *            "e", ( ͡o ω ͡o )
   *            "f", σωσ
   *            "g", >w<
   *            "h"
   *          )
   * }}}
   *
   * @exampwe when using [[absowuteindices]] {{{
   *          mewgebyindexintowesuwt(
   *          seq(
   *            0 -> "a", 😳😳😳
   *            0 -> "b", OwO
   *            1 -> "c", 😳
   *            3 -> "e", 😳😳😳
   *            5 -> "g", (˘ω˘)
   *            6 -> "h"), ʘwʘ
   *          seq(
   *            "d", ( ͡o ω ͡o )
   *            "f"
   *          ), o.O
   *          a-absowuteindices) == seq(
   *            "a", >w< // index 0, 😳 "a" a-and "b" t-togethew onwy count a-as 1 ewement with wegawds to indexes because they have dupwicate i-insewtion p-points
   *            "b", 🥺 // index 0
   *            "c", rawr x3 // index 1
   *            "d", o.O // index 2
   *            "e", rawr // index 3
   *            "f", ʘwʘ // index 4
   *            "g", 😳😳😳 // index 5
   *            "h" // index 6
   *          )
   * }}}
   */
  d-def mewgebyindexintowesuwt[t]( // g-genewic on `t` to simpwify u-unit testing
    candidatestoinsewtbyindex: s-seq[(int, ^^;; t)],
    wesuwt: seq[t], o.O
    indextype: i-indextype
  ): seq[t] = {
    v-vaw positionandcandidatewist = candidatestoinsewtbyindex.sowtwith {
      case ((indexweft: i-int, (///ˬ///✿) _), (indexwight: i-int, σωσ _)) =>
        indexweft < indexwight // owdew by desiwed absowute index ascending
    }

    // mewge wesuwt a-and positionandcandidatewist i-into wesuwtupdated whiwe making s-suwe that the e-entwies
    // fwom t-the positionandcandidatewist awe insewted at the wight index. nyaa~~
    vaw wesuwtupdated = s-seq.newbuiwdew[t]
    wesuwtupdated.sizehint(wesuwt.size + positionandcandidatewist.size)

    vaw cuwwentwesuwtindex = 0
    vaw inputwesuwtitewatow = w-wesuwt.itewatow
    vaw positionandcandidateitewatow = p-positionandcandidatewist.itewatow.buffewed
    v-vaw pweviousinsewtposition: o-option[int] = nyone

    whiwe (inputwesuwtitewatow.nonempty && p-positionandcandidateitewatow.nonempty) {
      p-positionandcandidateitewatow.head m-match {
        c-case (nextinsewtionposition, ^^;; nyextcandidatetoinsewt)
            if pweviousinsewtposition.contains(nextinsewtionposition) =>
          // i-insewting muwtipwe c-candidates at t-the same index
          w-wesuwtupdated += n-nyextcandidatetoinsewt
          // do not incwement any indices, ^•ﻌ•^ but insewt the candidate a-and advance to the nyext candidate
          positionandcandidateitewatow.next()

        case (nextinsewtionposition, σωσ nyextcandidatetoinsewt)
            if cuwwentwesuwtindex >= n-nyextinsewtionposition =>
          // insewting a candidate at a nyew index
          // a-add candidate t-to the wesuwts
          w-wesuwtupdated += nyextcandidatetoinsewt
          // s-save the position of the insewted e-ewement to handwe d-dupwicate index insewtions
          pweviousinsewtposition = some(nextinsewtionposition)
          // advance to nyext candidate
          p-positionandcandidateitewatow.next()
          if (indextype == absowuteindices) {
            // i-if the indices awe absowute, instead o-of wewative t-to the owiginaw `wesuwt` we nyeed to
            // c-count the i-insewtions of candidates into the w-wesuwts towawds t-the `cuwwentwesuwtindex`
            cuwwentwesuwtindex += 1
          }
        case _ =>
          // nyo candidate to insewt b-by index so use t-the candidates f-fwom the wesuwt and incwement the i-index
          w-wesuwtupdated += inputwesuwtitewatow.next()
          c-cuwwentwesuwtindex += 1
      }
    }
    // one of the itewatows is empty, -.- so append the wemaining candidates i-in owdew t-to the end
    wesuwtupdated ++= positionandcandidateitewatow.map { c-case (_, ^^;; candidate) => c-candidate }
    wesuwtupdated ++= inputwesuwtitewatow

    wesuwtupdated.wesuwt()
  }
}
