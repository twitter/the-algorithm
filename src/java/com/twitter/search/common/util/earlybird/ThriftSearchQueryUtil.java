package com.twittew.seawch.common.utiw.eawwybiwd;

impowt com.twittew.seawch.common.quewy.thwiftjava.cowwectowpawams;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchquewy;

/**
 * u-utiwity c-cwass fwom constwucting t-thwiftseawchquewy. rawr x3
 */
pubwic f-finaw cwass t-thwiftseawchquewyutiw {
  p-pwivate thwiftseawchquewyutiw() { }

  /**
   * convenience methods fow constwucting a-a thwiftseawchquewy. mya
   */
  pubwic static thwiftseawchquewy n-nyewseawchquewy(stwing sewiawizedquewy, nyaa~~ i-int nyumwesuwts) {
    thwiftseawchquewy seawchquewy = nyew thwiftseawchquewy();
    s-seawchquewy.setsewiawizedquewy(sewiawizedquewy);
    seawchquewy.setcowwectowpawams(new c-cowwectowpawams().setnumwesuwtstowetuwn(numwesuwts));
    w-wetuwn seawchquewy;
  }

  /** detewmines if the given wequest was i-initiated by a wogged in usew. (⑅˘꒳˘) */
  pubwic static boowean wequestinitiatedbywoggedinusew(eawwybiwdwequest wequest) {
    t-thwiftseawchquewy seawchquewy = w-wequest.getseawchquewy();
    w-wetuwn (seawchquewy != n-nyuww) && s-seawchquewy.issetseawchewid()
      && (seawchquewy.getseawchewid() > 0);
  }
}
