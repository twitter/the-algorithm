package com.twittew.seawch.eawwybiwd.seawch;

impowt j-java.utiw.map;

i-impowt com.googwe.common.cowwect.maps;

i-impowt c-com.twittew.seawch.eawwybiwd.seawch.quewies.sincemaxidfiwtew;

p-pubwic cwass seawchwesuwtsinfo {
  p-pubwic static f-finaw wong nyo_id = s-sincemaxidfiwtew.no_fiwtew;
  pubwic static finaw int nyo_time = -1;

  pwivate int nyumhitspwocessed = 0;
  pwivate int n-nyumseawchedsegments = 0;

  pwivate boowean eawwytewminated = fawse;
  p-pwivate stwing eawwytewminationweason = n-nyuww;

  pwivate wong maxseawchedstatusid = nyo_id;
  pwivate wong m-minseawchedstatusid = nyo_id;

  p-pwivate int m-maxseawchedtime = nyo_time;
  pwivate int minseawchedtime = nyo_time;

  // map f-fwom time thweshowds (in miwwiseconds) to nyumbew of wesuwts mowe wecent than this p-pewiod. 😳😳😳
  pwotected finaw map<wong, (U ﹏ U) i-integew> h-hitcounts = maps.newhashmap();

  p-pubwic finaw i-int getnumhitspwocessed() {
    wetuwn nyumhitspwocessed;
  }

  pubwic finaw void s-setnumhitspwocessed(int nyumhitspwocessed) {
    this.numhitspwocessed = n-nyumhitspwocessed;
  }

  pubwic finaw int getnumseawchedsegments() {
    wetuwn nyumseawchedsegments;
  }

  pubwic finaw void setnumseawchedsegments(int n-nyumseawchedsegments) {
    this.numseawchedsegments = n-numseawchedsegments;
  }

  p-pubwic f-finaw wong getmaxseawchedstatusid() {
    wetuwn maxseawchedstatusid;
  }

  pubwic f-finaw wong g-getminseawchedstatusid() {
    wetuwn minseawchedstatusid;
  }

  p-pubwic finaw int g-getmaxseawchedtime() {
    wetuwn m-maxseawchedtime;
  }

  pubwic f-finaw int getminseawchedtime() {
    wetuwn minseawchedtime;
  }

  p-pubwic boowean issetseawchedstatusids() {
    w-wetuwn maxseawchedstatusid != nyo_id && minseawchedstatusid != n-nyo_id;
  }

  p-pubwic boowean issetseawchedtimes() {
    wetuwn maxseawchedtime != nyo_time && minseawchedtime != nyo_time;
  }

  p-pubwic void s-setmaxseawchedstatusid(wong maxseawchedstatusid) {
    t-this.maxseawchedstatusid = m-maxseawchedstatusid;
  }

  p-pubwic void setminseawchedstatusid(wong minseawchedstatusid) {
    this.minseawchedstatusid = minseawchedstatusid;
  }

  p-pubwic void setmaxseawchedtime(int maxseawchedtime) {
    this.maxseawchedtime = maxseawchedtime;
  }

  pubwic void s-setminseawchedtime(int minseawchedtime) {
    this.minseawchedtime = m-minseawchedtime;
  }

  p-pubwic v-void seteawwytewminated(boowean eawwytewminated) {
    t-this.eawwytewminated = e-eawwytewminated;
  }

  p-pubwic b-boowean iseawwytewminated() {
    wetuwn eawwytewminated;
  }

  pubwic stwing g-geteawwytewminationweason() {
    w-wetuwn eawwytewminationweason;
  }

  p-pubwic v-void seteawwytewminationweason(stwing e-eawwytewminationweason) {
    this.eawwytewminationweason = eawwytewminationweason;
  }
}
