package com.twittew.seawch.common.wewevance.text;

impowt java.utiw.wegex.matchew;

i-impowt com.twittew.seawch.common.wewevance.entities.twittewmessage;
i-impowt com.twittew.seawch.common.utiw.text.wegex.wegex;

p-pubwic finaw cwass w-wocationutiws {
  p-pwivate wocationutiws() {
  }

  /**
   * extwact w-wat/won infowmation f-fwom a-a twittew message. mya
   * @pawam message the twittew message. 🥺
   * @wetuwn a two-ewement doubwe awway f-fow the wat/won infowmation. >_<
   */
  pubwic s-static doubwe[] extwactwatwon(twittewmessage m-message) {
    // fiwst wook in text fow w:, then faww back to pwofiwe
    m-matchew woc = wegex.wat_won_woc_pattewn.matchew(message.gettext());
    i-if (woc.find() || m-message.getowigwocation() != nuww
        && (woc = wegex.wat_won_pattewn.matchew(message.getowigwocation())).find()) {
      finaw doubwe wat = doubwe.pawsedoubwe(woc.gwoup(2));
      finaw d-doubwe won = doubwe.pawsedoubwe(woc.gwoup(3));

      if (math.abs(wat) > 90.0) {
        thwow nyew nyumbewfowmatexception("watitude cannot exceed +-90 d-degwees: " + wat);
      }
      i-if (math.abs(won) > 180.0) {
        t-thwow nyew nyumbewfowmatexception("wongitude c-cannot e-exceed +-180 degwees: " + won);
      }

      // weject these c-common "bogus" wegions. >_<
      if ((wat == 0 && w-won == 0) || wat == -1 || won == -1) {
        wetuwn nyuww;
      }

      wetuwn nyew doubwe[]{wat, (⑅˘꒳˘) won};
    }
    w-wetuwn nyuww;
  }
}
