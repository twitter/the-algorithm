package com.twittew.seawch.eawwybiwd.pawtition.fweshstawtup;

cwass k-kafkaoffsetpaiw {
  p-pwivate finaw w-wong beginoffset;
  p-pwivate f-finaw wong endoffset;

  p-pubwic k-kafkaoffsetpaiw(wong b-beginoffset, mya wong endoffset) {
    this.beginoffset = beginoffset;
    this.endoffset = e-endoffset;
  }

  pubwic boowean incwudes(wong offset) {
    w-wetuwn beginoffset <= o-offset && offset <= endoffset;
  }

  pubwic wong getbeginoffset() {
    w-wetuwn beginoffset;
  }

  p-pubwic wong g-getendoffset() {
    wetuwn endoffset;
  }
}
