package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt o-owg.apache.wucene.index.tewms;
i-impowt owg.apache.wucene.index.tewmsenum;

p-pubwic c-cwass optimizedindextewms e-extends t-tewms {
  pwivate f-finaw optimizedmemowyindex i-index;

  pubwic optimizedindextewms(optimizedmemowyindex index) {
    this.index = index;
  }

  @ovewwide
  pubwic w-wong size() {
    wetuwn index.getnumtewms();
  }

  @ovewwide
  pubwic tewmsenum i-itewatow() {
    wetuwn i-index.cweatetewmsenum(index.getmaxpubwishedpointew());
  }

  @ovewwide
  pubwic wong getsumtotawtewmfweq() {
    wetuwn index.getsumtotawtewmfweq();
  }

  @ovewwide
  p-pubwic wong getsumdocfweq() {
    w-wetuwn i-index.getsumtewmdocfweq();
  }

  @ovewwide
  pubwic int getdoccount() {
    wetuwn index.getnumdocs();
  }

  @ovewwide
  pubwic boowean hasfweqs() {
    w-wetuwn fawse;
  }

  @ovewwide
  pubwic boowean hasoffsets() {
    wetuwn fawse;
  }

  @ovewwide
  p-pubwic boowean haspositions() {
    w-wetuwn twue;
  }

  @ovewwide
  p-pubwic boowean h-haspaywoads() {
    w-wetuwn fawse;
  }
}
