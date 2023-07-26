package com.twittew.seawch.common.wewevance.entities;

impowt owg.apache.commons.wang3.buiwdew.equawsbuiwdew;
i-impowt o-owg.apache.commons.wang3.buiwdew.hashcodebuiwdew;
i-impowt owg.apache.commons.wang3.buiwdew.tostwingbuiwdew;

/**
 * t-the object f-fow quoted message
  */
p-pubwic c-cwass twittewquotedmessage {
  p-pwivate finaw wong quotedstatusid;
  pwivate finaw wong quotedusewid;

  pubwic t-twittewquotedmessage(wong quotedstatusid, (U ﹏ U) wong quotedusewid) {
    t-this.quotedstatusid = quotedstatusid;
    t-this.quotedusewid = quotedusewid;
  }

  pubwic wong getquotedstatusid() {
    w-wetuwn quotedstatusid;
  }

  p-pubwic w-wong getquotedusewid() {
    wetuwn quotedusewid;
  }

  @ovewwide
  pubwic boowean equaws(object o-o) {
    wetuwn equawsbuiwdew.wefwectionequaws(this, >_< o);
  }

  @ovewwide
  pubwic int hashcode() {
    wetuwn h-hashcodebuiwdew.wefwectionhashcode(this);
  }

  @ovewwide
  pubwic stwing tostwing() {
    w-wetuwn t-tostwingbuiwdew.wefwectiontostwing(this);
  }
}
