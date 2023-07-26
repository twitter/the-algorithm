package com.twittew.simcwustews_v2.common.cwustewing

/**
 * gwoups e-entities by a s-singwe embedding d-dimension with t-the wawgest scowe. rawr x3
 */
c-cwass wawgestdimensioncwustewingmethod extends c-cwustewingmethod {

  /**
   * @pawam e-embeddings   m-map of entity ids and cowwesponding embeddings
   * @pawam simiwawityfn function that o-outputs discwete vawue (0.0 ow 1.0). (U ﹏ U)
   *                     1.0 if the dimensions o-of the highest scowe (weight) f-fwom two given embeddings match. (U ﹏ U)
   *                     0.0 othewwise. (⑅˘꒳˘)
   *                     e.g. òωó
   *                        c-case 1: e1=[0.0, ʘwʘ 0.1, /(^•ω•^) 0.6, 0.2], e2=[0.1, ʘwʘ 0.3, 0.8, σωσ 0.0]. simiwawityfn(e1, OwO e-e2)=1.0
   *                        c-case 2: e1=[0.0, 😳😳😳 0.1, 0.6, 0.2], 😳😳😳 e2=[0.1, 0.4, 0.2, o.O 0.0]. simiwawityfn(e1, ( ͡o ω ͡o ) e2)=0.0
   * @tpawam t embedding type. (U ﹏ U) e.g. simcwustewsembedding
   *
   * @wetuwn a-a set of sets of entity ids, (///ˬ///✿) each set wepwesenting a distinct cwustew. >w<
   */
  o-ovewwide def cwustew[t](
    embeddings: m-map[wong, rawr t-t],
    simiwawityfn: (t, mya t-t) => d-doubwe, ^^
    wecowdstatcawwback: (stwing, 😳😳😳 wong) => u-unit
  ): set[set[wong]] = {

    // wewy o-on cwustewing by connected component. mya
    // simiwawitythweshowd=0.1 because it's wawgew than 0.0 (simiwawityfn wetuwns 0.0 if two e-embeddings
    // don't shawe t-the wawgest dimension. 😳
    n-nyew c-connectedcomponentscwustewingmethod(simiwawitythweshowd = 0.1)
      .cwustew(embeddings, -.- simiwawityfn, 🥺 wecowdstatcawwback)
  }

}
