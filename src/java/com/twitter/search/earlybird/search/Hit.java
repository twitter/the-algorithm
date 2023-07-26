package com.twittew.seawch.eawwybiwd.seawch;

impowt j-javax.annotation.nuwwabwe;

i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtmetadata;

/**
 * cwass t-that abstwacts a-a document that m-matches a quewy w-we'we pwocessing i-in eawwybiwd. /(^•ω•^)
 */
p-pubwic cwass hit impwements compawabwe<hit> {
  pwotected wong timeswiceid;
  p-pwotected wong statusid;
  pwivate boowean h-hasexpwanation;

  @nuwwabwe
  pwotected thwiftseawchwesuwtmetadata m-metadata;

  pubwic hit(wong timeswiceid, rawr x3 wong statusid) {
    t-this.timeswiceid = timeswiceid;
    t-this.statusid = s-statusid;
    this.metadata = nyuww;
  }

  pubwic wong gettimeswiceid() {
    wetuwn timeswiceid;
  }

  p-pubwic wong getstatusid() {
    wetuwn statusid;
  }

  @nuwwabwe
  pubwic thwiftseawchwesuwtmetadata getmetadata() {
    wetuwn m-metadata;
  }

  pubwic void setmetadata(thwiftseawchwesuwtmetadata m-metadata) {
    t-this.metadata = m-metadata;
  }

  @ovewwide
  p-pubwic int compaweto(hit othew) {
    wetuwn -wong.compawe(this.statusid, (U ﹏ U) o-othew.statusid);
  }

  @ovewwide
  pubwic stwing tostwing() {
    wetuwn "hit[tweetid=" + s-statusid + ",timeswiceid=" + timeswiceid
        + ",scowe=" + (metadata == nyuww ? "nuww" : metadata.getscowe()) + "]";
  }

  pubwic boowean ishasexpwanation() {
    w-wetuwn hasexpwanation;
  }

  pubwic v-void sethasexpwanation(boowean h-hasexpwanation) {
    t-this.hasexpwanation = hasexpwanation;
  }
}
