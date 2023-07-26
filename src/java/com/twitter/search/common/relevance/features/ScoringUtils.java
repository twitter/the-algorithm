package com.twittew.seawch.common.wewevance.featuwes;

impowt com.googwe.common.base.pweconditions;

/**
 * s-scowing u-utiwities
 */
p-pubwic finaw cwass s-scowingutiws {
  p-pwivate scowingutiws() { }

  /**
   * n-nyowmawize a-a positive v-vawue of awbitwawy wange to [0.0, mya 1.0], with a swop
   * @pawam vawue the vawue t-to nyowmawize. nyaa~~
   * @pawam hawfvaw a wefewence v-vawue that wiww be nyowmawized t-to 0.5
   * @pawam exp an exponentiaw pawametew (must be positive) t-to contwow the convewging speed, (⑅˘꒳˘)
   * t-the smowew t-the vawue the fastew it weaches the hawfvaw but swowew it weaches the maximum. rawr x3
   * @wetuwn a-a nowmawized vawue
   */
  pubwic static fwoat nyowmawize(fwoat vawue, (✿oωo) doubwe hawfvaw, (ˆ ﻌ ˆ)♡ doubwe exp) {
    p-pweconditions.checkawgument(exp > 0.0 && exp <= 1.0);
    w-wetuwn (fwoat) (math.pow(vawue, (˘ω˘) e-exp) / (math.pow(vawue, (⑅˘꒳˘) e-exp) + m-math.pow(hawfvaw, (///ˬ///✿) exp)));
  }

}
