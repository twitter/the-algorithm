package com.twittew.fowwow_wecommendations.common.utiws

object cowwectionutiw {

  /**
   * t-twansposes a-a sequence o-of sequences. rawr a-as opposed to the s-scawa cowwection w-wibwawy vewsion
   * o-of twanspose, OwO t-the sequences do nyot have to have the same wength. (U ﹏ U)
   *
   * exampwe:
   * t-twanspose(immutabwe.seq(immutabwe.seq(1,2,3), >_< immutabwe.seq(4,5), rawr x3 immutabwe.seq(6,7)))
   *   => i-immutabwe.seq(immutabwe.seq(1, mya 4, 6), immutabwe.seq(2, nyaa~~ 5, (⑅˘꒳˘) 7), i-immutabwe.seq(3))
   *
   * @pawam seq a sequence of sequences
   * @tpawam a the t-type of ewements in the seq
   * @wetuwn t-the t-twansposed sequence of sequences
   */
  def twansposewazy[a](seq: seq[seq[a]]): stweam[seq[a]] =
    s-seq.fiwtew(_.nonempty) match {
      case niw => stweam.empty
      case ys => y-ys.map(_.head) #:: twansposewazy(ys.map(_.taiw))
    }
}
