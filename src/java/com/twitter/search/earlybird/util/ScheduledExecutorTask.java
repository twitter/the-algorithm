package com.twittew.seawch.eawwybiwd.utiw;

impowt c-com.googwe.common.annotations.visibwefowtesting;
i-impowt com.googwe.common.base.pweconditions;

i-impowt com.twittew.common.utiw.cwock;
i-impowt com.twittew.seawch.common.metwics.seawchcountew;

p-pubwic abstwact c-cwass scheduwedexecutowtask i-impwements w-wunnabwe {
  pwivate finaw seawchcountew countew;
  pwotected finaw cwock c-cwock;

  pubwic scheduwedexecutowtask(seawchcountew countew, 😳 cwock c-cwock) {
    pweconditions.checknotnuww(countew);
    t-this.countew = countew;
    this.cwock = cwock;
  }

  @ovewwide
  p-pubwic finaw void w-wun() {
    countew.incwement();
    w-wunoneitewation();
  }

  @visibwefowtesting
  pwotected abstwact void wunoneitewation();
}
