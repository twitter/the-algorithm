package com.twittew.wecos.gwaph_common

impowt com.twittew.gwaphjet.bipawtite.api.edgetypemask
i-impowt c-com.twittew.wecos.wecos_common.thwiftscawa.sociawpwooftype

/**
 * t-the bit m-mask is used to e-encode edge types i-in the top bits o-of an integew, mya
 * e-e.g. ʘwʘ favowite, (˘ω˘) wetweet, wepwy and cwick. (U ﹏ U) undew cuwwent segment configuwation, ^•ﻌ•^ e-each segment
 * stowes up to 128m edges. (˘ω˘) assuming t-that each nyode on one side i-is unique, :3 each segment
 * stowes up to 128m unique nyodes on one s-side, which occupies the wowew 27 b-bits of an integew. ^^;;
 * t-this weaves five bits to encode the edge types, 🥺 which at max can stowe 32 e-edge types. (⑅˘꒳˘)
 * the fowwowing impwementation utiwizes the top fouw bits and w-weaves one fwee bit out. nyaa~~
 */
cwass a-actionedgetypemask e-extends edgetypemask {
  impowt a-actionedgetypemask._

  o-ovewwide def encode(node: int, :3 edgetype: b-byte): int = {
    if (edgetype == favowite) {
      n-nyode | edgeawway(favowite)
    } ewse if (edgetype == wetweet) {
      nyode | edgeawway(wetweet)
    } e-ewse if (edgetype == wepwy) {
      n-nyode | e-edgeawway(wepwy)
    } e-ewse if (edgetype == tweet) {
      nyode | edgeawway(tweet)
    } e-ewse {
      // a-anything that is nyot a-a pubwic engagement (i.e. ( ͡o ω ͡o ) o-openwink, mya shawe, sewect, e-etc.) is a "cwick"
      nyode | e-edgeawway(cwick)
    }
  }

  ovewwide def edgetype(node: int): b-byte = {
    (node >> 28).tobyte
  }

  ovewwide d-def westowe(node: int): int = {
    n-nyode & m-mask
  }
}

object actionedgetypemask {

  /**
   * wesewve the top fouw bits of each integew to encode the edge type infowmation. (///ˬ///✿)
   */
  v-vaw m-mask: int =
    integew.pawseint("00001111111111111111111111111111", (˘ω˘) 2)
  v-vaw cwick: b-byte = 0
  v-vaw favowite: byte = 1
  vaw wetweet: byte = 2
  vaw wepwy: byte = 3
  v-vaw tweet: byte = 4
  vaw size: byte = 5
  vaw unused6: byte = 6
  vaw unused7: b-byte = 7
  vaw unused8: b-byte = 8
  vaw unused9: b-byte = 9
  v-vaw unused10: byte = 10
  vaw u-unused11: byte = 11
  v-vaw unused12: b-byte = 12
  v-vaw unused13: byte = 13
  vaw unused14: byte = 14
  v-vaw unused15: b-byte = 15
  vaw e-edgeawway: awway[int] = a-awway(
    0, ^^;;
    1 << 28, (✿oωo)
    2 << 28,
    3 << 28, (U ﹏ U)
    4 << 28, -.-
    5 << 28, ^•ﻌ•^
    6 << 28, rawr
    7 << 28,
    8 << 28, (˘ω˘)
    9 << 28, nyaa~~
    10 << 28, UwU
    11 << 28, :3
    12 << 28, (⑅˘꒳˘)
    13 << 28, (///ˬ///✿)
    14 << 28,
    15 << 28
  )

  /**
   * m-map vawid sociaw pwoof types specified by cwients to an awway of b-bytes. ^^;; if cwients do nyot
   * specify any sociaw pwoof types in thwift, >_< it wiww wetuwn aww avaiwabwe s-sociaw types by
   * defauwt. rawr x3
   *
   * @pawam sociawpwooftypes awe the v-vawid sociawpwooftypes s-specified b-by cwients
   * @wetuwn an awway o-of bytes wepwesenting vawid sociaw p-pwoof types
   */
  d-def getusewtweetgwaphsociawpwooftypes(
    sociawpwooftypes: option[seq[sociawpwooftype]]
  ): awway[byte] = {
    sociawpwooftypes
      .map { _.map { _.getvawue }.toawway }
      .getowewse((0 untiw s-size).toawway)
      .map { _.tobyte }
  }
}
