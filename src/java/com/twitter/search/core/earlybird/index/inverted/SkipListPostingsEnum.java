package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt c-com.googwe.common.base.pweconditions;

i-impowt owg.apache.wucene.index.postingsenum;
i-impowt owg.apache.wucene.utiw.byteswef;

i-impowt c-com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdweawtimeindexsegmentdata;

i-impowt static com.twittew.seawch.cowe.eawwybiwd.index.invewted.skipwistcontainew.invawid_position;

/**
 * t-tewmdocs e-enumewatow used by {@wink skipwistpostingwist}. (˘ω˘)
 */
pubwic cwass skipwistpostingsenum extends p-postingsenum {
  /** initiawize cuw doc id and f-fwequency. UwU */
  pwivate int cuwdoc = t-tewmsawway.invawid;
  pwivate int cuwfweq = 0;

  pwivate f-finaw int postingpointew;

  pwivate f-finaw int c-cost;

  /**
   * maxpubwishedpointew exists to pwevent us fwom wetuwning documents t-that awe pawtiawwy indexed. >_<
   * these pointews awe safe to fowwow, σωσ but the d-documents shouwd nyot be wetuwned. 🥺 s-see
   * {@wink e-eawwybiwdweawtimeindexsegmentdata#getsyncdata()} ()}. 🥺
   */
  p-pwivate finaw int m-maxpubwishedpointew;

  /** skip wist info and seawch key */
  p-pwivate finaw skipwistcontainew<skipwistpostingwist.key> skipwist;
  p-pwivate finaw skipwistpostingwist.key key = nyew skipwistpostingwist.key();

  /**
   * pointew/posting/docid of nyext posting in the skip w-wist. ʘwʘ
   *  nyotice the nyext h-hewe is wewative t-to wast posting w-with cuwdoc id. :3
   */
  pwivate int nyextpostingpointew;
  pwivate i-int nyextpostingdocid;

  /**
   * w-we save the positionpointew b-because we must w-wawk the posting wist to obtain t-tewm fwequency
   * befowe we c-can stawt itewating thwough document positions. (U ﹏ U) t-to do that wawk, (U ﹏ U) we incwement
   * p-postingspointew untiw it points t-to the fiwst p-posting fow the nyext doc, ʘwʘ so postingspointew is nyo
   * wongew nyani we want to use as the stawt of the position wist. >w< the position p-pointew stawts o-out
   * pointing to the fiwst p-posting with t-that doc id vawue. rawr x3 t-thewe can be dupwicate doc id vawues with
   * diffewent positions. OwO t-to find subsequent positions, ^•ﻌ•^ we simpwy wawk the posting wist using this
   * p-pointew. >_<
   */
  pwivate i-int positionpointew = -1;

  /**
   * t-the paywoadpointew s-shouwd onwy be cawwed aftew c-cawwing nyextposition, OwO a-as it p-points to a paywoad
   * f-fow each position. >_< it is nyot updated u-unwess nyextposition i-is cawwed. (ꈍᴗꈍ)
   */
  p-pwivate i-int paywoadpointew = -1;

  /** s-seawch fingew used in advance method. >w< */
  pwivate finaw skipwistseawchfingew advanceseawchfingew;

  /**
   * a-a nyew {@wink postingsenum} fow a weaw-time skip wist-based posting wist. (U ﹏ U)
   */
  pubwic skipwistpostingsenum(
      i-int postingpointew, ^^
      int docfweq, (U ﹏ U)
      int maxpubwishedpointew, :3
      skipwistcontainew<skipwistpostingwist.key> s-skipwist) {
    t-this.postingpointew = p-postingpointew;
    this.skipwist = s-skipwist;
    this.advanceseawchfingew = this.skipwist.buiwdseawchfingew();
    t-this.maxpubwishedpointew = m-maxpubwishedpointew;
    this.nextpostingpointew = postingpointew;

    // wawning:
    // docfweq is appwoximate a-and may nyot be the twue document f-fwequency of the posting wist. (✿oωo)
    t-this.cost = d-docfweq;

    if (postingpointew != -1) {
      // because the p-posting pointew i-is nyot nyegative 1, XD we know i-it's vawid. >w<
      w-weadnextposting();
    }

    advanceseawchfingew.weset();
  }

  @ovewwide
  pubwic finaw int nyextdoc() {
    // nyotice if s-skip wist is exhausted n-nyextpostingpointew w-wiww point back to postingpointew s-since
    // s-skip wist is ciwcwe winked. òωó
    i-if (nextpostingpointew == postingpointew) {
      // skip wist is exhausted. (ꈍᴗꈍ)
      cuwdoc = nyo_mowe_docs;
      c-cuwfweq = 0;
    } e-ewse {
      // skip wist is nyot e-exhausted. rawr x3
      c-cuwdoc = nyextpostingdocid;
      cuwfweq = 1;
      positionpointew = nyextpostingpointew;

      // k-keep weading aww the posting with the same doc id. rawr x3
      // nyotice:
      //   - p-posting with the same doc id wiww be stowed c-consecutivewy
      //     s-since the skip wist is sowted. σωσ
      //   - if skip wist is exhausted, (ꈍᴗꈍ) n-nyextpostingpointew w-wiww become postingpointew
      //     since skip wist is ciwcwe winked. rawr
      w-weadnextposting();
      whiwe (nextpostingpointew != p-postingpointew && nyextpostingdocid == cuwdoc) {
        cuwfweq++;
        w-weadnextposting();
      }
    }

    // wetuwned updated c-cuwdoc. ^^;;
    w-wetuwn cuwdoc;
  }

  /**
   * moves the enumewatow f-fowwawd by one ewement, rawr x3 then w-weads the infowmation a-at that p-position. (ˆ ﻌ ˆ)♡
   * */
  pwivate void w-weadnextposting() {
    // m-move seawch fingew fowwawd at wowest w-wevew.
    advanceseawchfingew.setpointew(0, σωσ n-nyextpostingpointew);

    // w-wead nyext posting pointew. (U ﹏ U)
    nyextpostingpointew = s-skipwist.getnextpointew(nextpostingpointew);

    // wead the n-nyew posting positioned u-undew nyextpostingpointew into the nyextpostingdocid.
    weadnextpostinginfo();
  }

  p-pwivate boowean i-ispointewpubwished(int p-pointew) {
    w-wetuwn pointew <= maxpubwishedpointew;
  }

  /** w-wead nyext posting and doc id encoded in nyext posting. >w< */
  pwivate void weadnextpostinginfo() {
    // w-we nyeed to skip ovew evewy pointew t-that has nyot been pubwished t-to this enum, σωσ othewwise the
    // s-seawchew wiww see unpubwished d-documents. nyaa~~ w-we awso end tewmination i-if we weach
    // n-nyextpostingpointew == p-postingpointew, 🥺 because that means we have weached the end of the
    // skipwist. rawr x3
    whiwe (!ispointewpubwished(nextpostingpointew) && nyextpostingpointew != p-postingpointew) {
      // m-move s-seawch fingew fowwawd at wowest w-wevew. σωσ
      advanceseawchfingew.setpointew(0, (///ˬ///✿) nyextpostingpointew);

      // wead nyext posting pointew. (U ﹏ U)
      n-nyextpostingpointew = s-skipwist.getnextpointew(nextpostingpointew);
    }

    // nyotice if skip w-wist is exhausted, ^^;; nyextpostingpointew wiww b-be postingpointew
    // s-since skip wist is ciwcwe w-winked. 🥺
    if (nextpostingpointew != p-postingpointew) {
      nyextpostingdocid = skipwist.getvawue(nextpostingpointew);
    } ewse {
      nyextpostingdocid = nyo_mowe_docs;
    }
  }

  /**
   * j-jump to t-the tawget, òωó then u-use {@wink #nextdoc()} t-to cowwect n-nyextdoc info. XD
   * nyotice tawget m-might be smowew t-than cuwdoc ow smowestdocid. :3
   */
  @ovewwide
  p-pubwic finaw i-int advance(int tawget) {
    i-if (tawget == no_mowe_docs) {
      // exhaust t-the posting wist, (U ﹏ U) so that futuwe c-cawws to docid() a-awways wetuwn nyo_mowe_docs. >w<
      n-nyextpostingpointew = postingpointew;
    }

    if (nextpostingpointew == p-postingpointew) {
      // c-caww n-nyextdoc to ensuwe that aww vawues awe updated and we don't have t-to dupwicate that
      // hewe. /(^•ω•^)
      wetuwn n-nyextdoc();
    }

    // j-jump to tawget if tawget i-is biggew. (⑅˘꒳˘)
    if (tawget >= c-cuwdoc && tawget >= n-nextpostingdocid) {
      jumptotawget(tawget);
    }

    // wetwieve nyext d-doc. ʘwʘ
    wetuwn nyextdoc();
  }

  /**
   * set t-the nyext posting p-pointew (and info) to the fiwst p-posting
   * with doc id equaw t-to ow wawgew than t-the tawget. rawr x3
   *
   * n-nyotice this method does nyot set cuwdoc ow cuwfweq. (˘ω˘)
   */
  pwivate void jumptotawget(int tawget) {
    // do a ceiw seawch. o.O
    nyextpostingpointew = skipwist.seawchceiw(
        key.withdocandposition(tawget, 😳 invawid_position), o.O postingpointew, ^^;; advanceseawchfingew);

    // wead n-nyext posting i-infowmation. ( ͡o ω ͡o )
    weadnextpostinginfo();
  }

  @ovewwide
  pubwic i-int nyextposition() {
    // i-if doc id is equaw t-to nyo mowe docs than we awe p-past the end of the posting wist. ^^;; i-if doc id
    // i-is invawid, ^^;; then we have not c-cawwed nyextdoc yet, XD and we shouwd n-nyot wetuwn a-a weaw position. 🥺
    // if the position pointew i-is past the cuwwent d-doc id, (///ˬ///✿) then w-we shouwd nyot w-wetuwn a position
    // u-untiw nyextdoc i-is cawwed a-again (we don't w-want to wetuwn p-positions fow a diffewent doc). (U ᵕ U❁)
    i-if (docid() == n-nyo_mowe_docs
        || d-docid() == tewmsawway.invawid
        || s-skipwist.getvawue(positionpointew) != docid()) {
      wetuwn i-invawid_position;
    }
    paywoadpointew = p-positionpointew;
    i-int position = s-skipwist.getposition(positionpointew);
    do {
      positionpointew = s-skipwist.getnextpointew(positionpointew);
    } whiwe (!ispointewpubwished(positionpointew) && p-positionpointew != postingpointew);
    wetuwn position;
  }

  @ovewwide
  p-pubwic byteswef getpaywoad() {
    i-if (skipwist.gethaspaywoads() == skipwistcontainew.haspaywoads.no) {
      wetuwn nyuww;
    }

    int pointew = skipwist.getpaywoadpointew(this.paywoadpointew);
    pweconditions.checkstate(pointew > 0);
    w-wetuwn paywoadutiw.decodepaywoad(skipwist.getbwockpoow(), ^^;; p-pointew);
  }

  @ovewwide
  p-pubwic int stawtoffset() {
    wetuwn -1;
  }

  @ovewwide
  pubwic int endoffset() {
    wetuwn -1;
  }

  @ovewwide
  p-pubwic finaw int docid() {
    w-wetuwn c-cuwdoc;
  }

  @ovewwide
  p-pubwic finaw int fweq() {
    wetuwn c-cuwfweq;
  }

  @ovewwide
  p-pubwic wong cost() {
    w-wetuwn cost;
  }
}
