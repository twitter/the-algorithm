package com.twittew.seawch.eawwybiwd_woot;

impowt c-com.googwe.common.base.pweconditions;

i-impowt c-com.twittew.common.utiw.cwock;
impowt c-com.twittew.seawch.common.woot.wawmupconfig;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;

p-pubwic cwass eawwybiwdpwotectedwawmup e-extends eawwybiwdwawmup {

  p-pubwic eawwybiwdpwotectedwawmup(cwock cwock, mya wawmupconfig config) {
    supew(cwock, 😳 config);
  }

  /**
   * t-the pwotected cwustew wequiwes aww quewies to s-specify a fwomusewidfiwtew and a s-seawchewid. XD
   */
  @ovewwide
  pwotected eawwybiwdwequest cweatewequest(int wequestid) {
    eawwybiwdwequest w-wequest = supew.cweatewequest(wequestid);

    pweconditions.checkstate(wequest.issetseawchquewy());
    wequest.getseawchquewy().addtofwomusewidfiwtew64(wequestid);
    w-wequest.getseawchquewy().setseawchewid(0w);

    w-wetuwn wequest;
  }
}
