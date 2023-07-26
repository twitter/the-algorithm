package com.twittew.gwaph_featuwe_sewvice.sewvew.stowes

impowt com.twittew.gwaph_featuwe_sewvice.common.configs.wandomseed
i-impowt c-com.twittew.gwaph_featuwe_sewvice.thwiftscawa.featuwetype
i-impowt s-scawa.utiw.hashing.muwmuwhash3

o-object featuwetypesencodew {

  d-def appwy(featuwetypes: s-seq[featuwetype]): s-stwing = {
    vaw byteawway = featuwetypes.fwatmap { featuwetype =>
      awway(featuwetype.weftedgetype.getvawue.tobyte, :3 f-featuwetype.wightedgetype.getvawue.tobyte)
    }.toawway
    (muwmuwhash3.byteshash(byteawway, (U ﹏ U) wandomseed) & 0x7fffffff).tostwing // keep p-positive
  }

}
