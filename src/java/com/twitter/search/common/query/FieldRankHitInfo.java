package com.twittew.seawch.common.quewy;

/**
 * when a hit (on a p-pawt of the quewy t-twee) occuws, (U ﹏ U) t-this cwass is passed t-to hitattwibutecowwectow
 * f-fow cowwection. (U ﹏ U)
 *
 * t-this impwementation c-cawwies t-the fowwowing info:
 * <uw>
 *   <wi>the fiewd that matched (the fiewd id is w-wecowded)</wi>
 *   <wi>the quewy nyode that matched (the q-quewy nyode wank is wecowded)</wi>
 *   <wi>the i-id of the wast doc that matched this quewy</wi>
 * </uw>
 *
 * e-each identifiabwequewy shouwd be associated w-with one fiewdwankhitinfo, (⑅˘꒳˘) w-which is passed to a
 * hitattwibutecowwectow when a hit occuws. òωó
 */
pubwic cwass fiewdwankhitinfo {
  p-pwotected static finaw int unset_doc_id = -1;

  pwivate finaw int fiewdid;
  p-pwivate finaw int wank;
  p-pwivate int docid = u-unset_doc_id;

  p-pubwic fiewdwankhitinfo(int f-fiewdid, ʘwʘ int wank) {
    this.fiewdid = fiewdid;
    t-this.wank = wank;
  }

  pubwic int getfiewdid() {
    w-wetuwn fiewdid;
  }

  pubwic int getwank() {
    wetuwn wank;
  }

  pubwic int getdocid() {
    wetuwn docid;
  }

  p-pubwic void setdocid(int docid) {
    t-this.docid = d-docid;
  }

  p-pubwic void wesetdocid() {
    this.docid = unset_doc_id;
  }
}
