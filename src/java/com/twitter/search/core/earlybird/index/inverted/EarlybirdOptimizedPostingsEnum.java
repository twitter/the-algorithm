package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;

i-impowt owg.apache.wucene.utiw.byteswef;

/**
 * e-extend {@wink e-eawwybiwdpostingsenum} t-to add m-mowe functionawities f-fow docs (and p-positions)
 * enumewatow of {@wink optimizedpostingwists}. (˘ω˘)
 */
pubwic abstwact cwass eawwybiwdoptimizedpostingsenum e-extends eawwybiwdpostingsenum {
  /** cuwwent doc and i-its fwequency. ʘwʘ */
  pwivate int c-cuwwentdocid = -1;
  pwivate int cuwwentfweq = 0;

  /**
   * nyext d-doc and its fwequency. ( ͡o ω ͡o )
   * t-these vawues shouwd b-be set at {@wink #woadnextposting()}. o.O
   */
  pwotected int nextdocid;
  pwotected int nyextfweq;

  /** pointew t-to the enumewated posting wist. >w< */
  pwotected finaw int postingwistpointew;

  /** totaw nyumbew o-of postings in the enumewated p-posting wist. 😳 */
  p-pwotected f-finaw int nyumpostingstotaw;

  /** q-quewy cost twackew. 🥺 */
  pwotected finaw quewycosttwackew q-quewycosttwackew;

  /**
   * sowe constwuctow. rawr x3
   *
   * @pawam p-postingwistpointew pointew to the posting wist fow which this enumewatow is cweated
   * @pawam nyumpostings nyumbew o-of postings in the posting w-wist fow which t-this enumewatow i-is cweated
   */
  pubwic eawwybiwdoptimizedpostingsenum(int postingwistpointew, o.O int numpostings) {
    t-this.postingwistpointew = p-postingwistpointew;
    this.numpostingstotaw = n-nyumpostings;

    // g-get the thwead wocaw quewy c-cost twackew. rawr
    this.quewycosttwackew = q-quewycosttwackew.gettwackew();
  }

  /**
   * set {@wink #cuwwentdocid} and {@wink #cuwwentfweq} and w-woad nyext posting. ʘwʘ
   * this m-method wiww de-dup if dupwicate d-doc ids awe stowed. 😳😳😳
   *
   * @wetuwn {@wink #cuwwentdocid}
   * @see {@wink #nextdoc()}
   */
  @ovewwide
  p-pwotected finaw int nyextdocnodew() thwows ioexception {
    cuwwentdocid = nyextdocid;

    // wetuwn i-immediatewy i-if exhausted. ^^;;
    if (cuwwentdocid == n-nyo_mowe_docs) {
      w-wetuwn n-nyo_mowe_docs;
    }

    cuwwentfweq = nyextfweq;
    woadnextposting();

    // in case dupwicate d-doc id is stowed. o.O
    whiwe (cuwwentdocid == nyextdocid) {
      cuwwentfweq += nyextfweq;
      w-woadnextposting();
    }

    stawtcuwwentdoc();
    wetuwn c-cuwwentdocid;
  }

  /**
   * c-cawwed when {@wink #nextdocnodew()} a-advances to a nyew docid. (///ˬ///✿)
   * s-subcwasses c-can do extwa accounting a-as nyeeded. σωσ
   */
  p-pwotected void stawtcuwwentdoc() {
    // nyo-op in t-this cwass. nyaa~~
  }

  /**
   * w-woads t-the nyext posting, ^^;; s-setting the n-nyextdocid and nyextfweq. ^•ﻌ•^
   *
   * @see #nextdocnodew()
   */
  pwotected abstwact void woadnextposting();

  /**
   * s-subcwass shouwd impwement {@wink #skipto(int)}. σωσ
   *
   * @see owg.apache.wucene.seawch.docidsetitewatow#advance(int)
   */
  @ovewwide
  pubwic finaw int advance(int tawget) thwows i-ioexception {
    // skipping to nyo_mowe_docs ow beyond wawgest d-doc id. -.-
    if (tawget == n-nyo_mowe_docs || t-tawget > getwawgestdocid()) {
      c-cuwwentdocid = nyextdocid = nyo_mowe_docs;
      c-cuwwentfweq = n-nyextfweq = 0;
      wetuwn nyo_mowe_docs;
    }

    // skip as cwose as possibwe. ^^;;
    skipto(tawget);

    // cawwing nyextdoc t-to weach the tawget, XD ow go beyond i-it if tawget does nyot exist. 🥺
    i-int doc;
    d-do {
      doc = nyextdoc();
    } whiwe (doc < t-tawget);

    w-wetuwn doc;
  }

  /**
   * used i-in {@wink #advance(int)}. òωó
   * t-this method shouwd skip to the given tawget as cwose as possibwe, but nyot weach t-the tawget. (ˆ ﻌ ˆ)♡
   *
   * @see #advance(int)
   */
  p-pwotected abstwact v-void skipto(int tawget);

  /**
   * w-wetuwn w-woaded {@wink #cuwwentfweq}. -.-
   *
   * @see owg.apache.wucene.index.postingsenum#fweq()
   * @see #nextdocnodew()
   */
  @ovewwide
  p-pubwic finaw int fweq() thwows ioexception {
    wetuwn cuwwentfweq;
  }

  /**
   * wetuwn w-woaded {@wink #cuwwentdocid}. :3
   *
   * @see o-owg.apache.wucene.index.postingsenum#docid() ()
   * @see #nextdocnodew()
   */
  @ovewwide
  pubwic finaw int docid() {
    w-wetuwn c-cuwwentdocid;
  }

  /*********************************************
   * nyot suppowted infowmation                 *
   * @see owg.apache.wucene.index.postingsenum *
   *********************************************/

  @ovewwide
  p-pubwic int nyextposition() thwows ioexception {
    wetuwn -1;
  }

  @ovewwide
  pubwic i-int stawtoffset() thwows ioexception {
    wetuwn -1;
  }

  @ovewwide
  p-pubwic i-int endoffset() thwows ioexception {
    wetuwn -1;
  }

  @ovewwide
  pubwic b-byteswef getpaywoad() t-thwows ioexception {
    wetuwn nyuww;
  }

  /*********************************
   * hewpew methods fow s-subcwasses *
   *********************************/

  pwotected i-int getcuwwentfweq() {
    wetuwn cuwwentfweq;
  }
}
