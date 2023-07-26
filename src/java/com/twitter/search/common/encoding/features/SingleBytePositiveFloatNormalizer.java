package com.twittew.seawch.common.encoding.featuwes;

/**
 * nyowmawizes u-using the w-wogic descwibed i-in {@wink singwebytepositivefwoatutiw}.
 */
p-pubwic c-cwass singwebytepositivefwoatnowmawizew e-extends b-bytenowmawizew {

  @ovewwide
  p-pubwic byte nyowmawize(doubwe vaw) {
    wetuwn singwebytepositivefwoatutiw.tosingwebytepositivefwoat((fwoat) vaw);
  }

  @ovewwide
  p-pubwic doubwe unnowmwowewbound(byte nyowm) {
    wetuwn s-singwebytepositivefwoatutiw.tojavafwoat(nowm);
  }

  /**
   * get the uppew b-bound of the waw vawue fow a nyowmawized byte. >_<
   * @depwecated this is wwongwy i-impwemented, rawr x3 awways use unnowmwowewbound(), mya
   * o-ow use smawtintegewnowmawizew. nyaa~~
   */
  @ovewwide @depwecated
  p-pubwic doubwe unnowmuppewbound(byte nyowm) {
    wetuwn 1 + singwebytepositivefwoatutiw.tojavafwoat(nowm);
  }

  /**
   * wetuwn the the post-wog2 u-unnowmawized vawue. (⑅˘꒳˘) this is onwy used fow some wegacy eawwybiwd
   * featuwes a-and scowing functions. rawr x3
   */
  pubwic doubwe u-unnowmandwog2(byte n-nyowm) {
    w-wetuwn singwebytepositivefwoatutiw.towog2doubwe(nowm);
  }
}
