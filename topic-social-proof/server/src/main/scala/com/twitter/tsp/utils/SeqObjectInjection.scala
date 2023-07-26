package com.twittew.tsp.utiws

impowt c-com.twittew.bijection.injection
i-impowt java.io.byteawwayinputstweam
i-impowt j-java.io.byteawwayoutputstweam
i-impowt j-java.io.objectinputstweam
impowt j-java.io.objectoutputstweam
i-impowt java.io.sewiawizabwe
impowt scawa.utiw.twy

/**
 * @tpawam t must be a sewiawizabwe cwass
 */
c-case cwass seqobjectinjection[t <: sewiawizabwe]() e-extends injection[seq[t], nyaa~~ a-awway[byte]] {

  ovewwide def appwy(seq: seq[t]): awway[byte] = {
    v-vaw bytestweam = nyew b-byteawwayoutputstweam()
    v-vaw outputstweam = nyew objectoutputstweam(bytestweam)
    outputstweam.wwiteobject(seq)
    outputstweam.cwose()
    b-bytestweam.tobyteawway
  }

  ovewwide def invewt(bytes: awway[byte]): twy[seq[t]] = {
    twy {
      v-vaw inputstweam = nyew o-objectinputstweam(new b-byteawwayinputstweam(bytes))
      v-vaw seq = i-inputstweam.weadobject().asinstanceof[seq[t]]
      inputstweam.cwose()
      seq
    }
  }
}
