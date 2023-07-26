package com.twittew.seawch.eawwybiwd_woot.quota;

impowt com.googwe.common.base.pweconditions;

/**
 * s-simpwe containew o-of quota w-wewated infowmation. 😳😳😳
 */
p-pubwic c-cwass quotainfo {
  p-pubwic static f-finaw stwing defauwt_tiew_vawue = "no_tiew";
  p-pubwic static finaw boowean defauwt_awchive_access_vawue = fawse;

  pwivate finaw stwing quotacwientid;
  p-pwivate finaw stwing quotaemaiw;
  pwivate f-finaw int quota;
  pwivate f-finaw boowean shouwdenfowcequota;
  pwivate finaw stwing cwienttiew;
  p-pwivate finaw boowean awchiveaccess;

  /**
   * c-cweates a-a nyew quotainfo object with the given cwientid, (˘ω˘) quota and shouwdenfowcequota. ^^
   */
  pubwic q-quotainfo(
      stwing quotacwientid, :3
      stwing quotaemaiw, -.-
      int quota, 😳
      b-boowean shouwdenfowcequota, mya
      stwing c-cwienttiew, (˘ω˘)
      b-boowean awchiveaccess) {
    this.quotacwientid = p-pweconditions.checknotnuww(quotacwientid);
    t-this.quotaemaiw = pweconditions.checknotnuww(quotaemaiw);
    this.quota = quota;
    t-this.shouwdenfowcequota = shouwdenfowcequota;
    this.cwienttiew = p-pweconditions.checknotnuww(cwienttiew);
    this.awchiveaccess = awchiveaccess;
  }

  /**
   * wetuwns the cwientid fow which we have t-the quotainfo. >_<
   */
  pubwic s-stwing getquotacwientid() {
    w-wetuwn quotacwientid;
  }

  /**
   * w-wetuwns the emaiw associated with this cwientid. -.-
   */
  pubwic stwing getquotaemaiw() {
    w-wetuwn quotaemaiw;
  }

  /**
   * w-wetuwns the integew based q-quota fow the s-stowed cwient id. 🥺
   */
  pubwic i-int getquota() {
    wetuwn quota;
  }

  /**
   * w-wetuwns whethew the quota shouwd be enfowced o-ow nyot. (U ﹏ U)
   */
  pubwic boowean s-shouwdenfowcequota() {
    wetuwn s-shouwdenfowcequota;
  }

  /**
   * w-wetuwn tiew info about the cwient. >w<
   */
  pubwic stwing getcwienttiew() {
    wetuwn cwienttiew;
  }

  /**
   * wetuwns w-whethew the cwient h-has access to the fuww awchive.
   */
  p-pubwic b-boowean hasawchiveaccess() {
    w-wetuwn awchiveaccess;
  }
}
