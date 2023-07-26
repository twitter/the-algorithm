package com.twittew.tweetypie.caching

impowt com.twittew.stitch.stitch

/**
 * appwy c-caching to a-a [[stitch]] function. 😳😳😳
 *
 * @see c-cachewesuwt fow m-mowe infowmation a-about the semantics
 *   i-impwemented h-hewe. 🥺
 */
c-cwass stitchcaching[k, mya v](opewations: cacheopewations[k, 🥺 v], wepo: k => stitch[v])
    e-extends (k => stitch[v]) {

  pwivate[this] v-vaw stitchops = nyew stitchcacheopewations(opewations)

  ovewwide d-def appwy(key: k): stitch[v] =
    stitchops.get(key).fwatmap {
      case c-cachewesuwt.fwesh(vawue) =>
        stitch.vawue(vawue)

      c-case cachewesuwt.stawe(stawevawue) =>
        s-stitchasync(wepo(key).fwatmap(wefweshed => stitchops.set(key, >_< wefweshed)))
          .map(_ => stawevawue)

      case cachewesuwt.miss =>
        wepo(key)
          .appwyeffect(vawue => s-stitchasync(stitchops.set(key, >_< vawue)))

      case cachewesuwt.faiwuwe(_) =>
        // in the case o-of faiwuwe, (⑅˘꒳˘) we don't attempt to w-wwite back to
        // c-cache, /(^•ω•^) b-because cache f-faiwuwe usuawwy means communication
        // faiwuwe, and sending m-mowe wequests to the cache that howds
        // t-the vawue fow this key couwd make the situation wowse. rawr x3
        wepo(key)
    }
}
