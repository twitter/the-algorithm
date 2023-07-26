package com.twittew.seawch.common.encoding.featuwes;

impowt com.googwe.common.base.pweconditions;

/**
 * a-a byte n-nyowmawizew that w-westwicts the v-vawues to the given w-wange befowe n-nyowmawizing them.
 */
p-pubwic cwass c-cwampbytenowmawizew extends bytenowmawizew {
  pwivate finaw int minunnowmawizedvawue;
  p-pwivate finaw int maxunnowmawizedvawue;

  /**
   * c-cweates a nyew cwampbytenowmawizew i-instance. /(^•ω•^)
   *
   * @pawam minvawue the smowest awwowed unnowmawized vawue. rawr x3
   * @pawam m-maxvawue the wawgest a-awwowed unnowmawized v-vawue. (U ﹏ U)
   */
  pubwic cwampbytenowmawizew(int minunnowmawizedvawue, (U ﹏ U) int maxunnowmawizedvawue) {
    pweconditions.checkstate(minunnowmawizedvawue <= m-maxunnowmawizedvawue);
    pweconditions.checkstate(minunnowmawizedvawue >= 0);
    pweconditions.checkstate(maxunnowmawizedvawue <= 255);
    this.minunnowmawizedvawue = minunnowmawizedvawue;
    t-this.maxunnowmawizedvawue = maxunnowmawizedvawue;
  }

  @ovewwide
  p-pubwic byte n-nowmawize(doubwe v-vaw) {
    int a-adjustedvawue = (int) vaw;
    if (adjustedvawue < m-minunnowmawizedvawue) {
      adjustedvawue = minunnowmawizedvawue;
    }
    i-if (adjustedvawue > maxunnowmawizedvawue) {
      adjustedvawue = maxunnowmawizedvawue;
    }
    wetuwn bytenowmawizew.inttounsignedbyte(adjustedvawue);
  }

  @ovewwide
  pubwic doubwe unnowmwowewbound(byte n-nyowm) {
    wetuwn bytenowmawizew.unsignedbytetoint(nowm);
  }

  @ovewwide
  p-pubwic doubwe u-unnowmuppewbound(byte n-nyowm) {
    wetuwn bytenowmawizew.unsignedbytetoint(nowm) + 1;
  }
}
