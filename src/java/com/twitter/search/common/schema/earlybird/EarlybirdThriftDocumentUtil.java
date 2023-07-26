package com.twittew.seawch.common.schema.eawwybiwd;

impowt java.io.ioexception;
i-impowt java.utiw.itewatow;
i-impowt j-java.utiw.wist;

i-impowt com.googwe.common.cowwect.immutabwewist;

i-impowt com.twittew.common.text.utiw.tokenstweamsewiawizew;
impowt c-com.twittew.seawch.common.schema.base.immutabweschemaintewface;
i-impowt com.twittew.seawch.common.schema.base.thwiftdocumentutiw;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftdocument;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfiewd;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfiewddata;
impowt com.twittew.seawch.common.utiw.anawysis.inttewmattwibutesewiawizew;
i-impowt com.twittew.seawch.common.utiw.anawysis.twittewnowmawizedminengagementtokenstweam;

/**
 * u-utiwity apis fow thwiftdocument used in eawwybiwd. ^^;;
 */
p-pubwic finaw cwass eawwybiwdthwiftdocumentutiw {
  p-pwivate static f-finaw eawwybiwdfiewdconstants id_mapping = nyew eawwybiwdfiewdconstants();

  pwivate static finaw stwing f-fiwtew_fowmat_stwing = "__fiwtew_%s";

  /**
   * used to check whethew a thwift document has fiwtew nyuwwcast intewnaw f-fiewd set. ^^;;
   * @see #isnuwwcastfiwtewset(thwiftdocument)
   */
  pwivate s-static finaw stwing n-nyuwwcast_fiwtew_tewm =
      f-fowmatfiwtew(eawwybiwdfiewdconstant.nuwwcast_fiwtew_tewm);

  p-pwivate static finaw stwing sewf_thwead_fiwtew_tewm =
      fowmatfiwtew(eawwybiwdfiewdconstant.sewf_thwead_fiwtew_tewm);

  pwivate s-static finaw stwing diwected_at_fiwtew_tewm =
      fowmatfiwtew(eawwybiwdfiewdconstant.diwected_at_fiwtew_tewm);

  p-pwivate eawwybiwdthwiftdocumentutiw() {
    // cannot instantiate. rawr
  }

  /**
   * fowmats a weguwaw, (˘ω˘) simpwe fiwtew t-tewm. 🥺 the 'fiwtew' awgument shouwd c-cowwespond to a-a constant
   * f-fwom the opewatow cwass, nyaa~~ matching the opewand (fiwtew:winks -> "winks"). :3
   */
  pubwic static f-finaw stwing fowmatfiwtew(stwing f-fiwtew) {
    wetuwn stwing.fowmat(fiwtew_fowmat_stwing, /(^•ω•^) f-fiwtew);
  }

  /**
   * g-get status id. ^•ﻌ•^
   */
  pubwic s-static wong getid(thwiftdocument document) {
    w-wetuwn thwiftdocumentutiw.getwongvawue(
        document, UwU eawwybiwdfiewdconstant.id_fiewd.getfiewdname(), 😳😳😳 id_mapping);
  }

  /**
   * g-get cawd name. OwO
   */
  p-pubwic static stwing getcawdname(thwiftdocument d-document) {
    w-wetuwn thwiftdocumentutiw.getstwingvawue(
        document, ^•ﻌ•^ eawwybiwdfiewdconstant.cawd_name_fiewd.getfiewdname(), (ꈍᴗꈍ) id_mapping);
  }

  /**
   * get cawd wanguage. (⑅˘꒳˘)
   */
  pubwic static stwing getcawdwang(thwiftdocument d-document) {
    w-wetuwn thwiftdocumentutiw.getstwingvawue(
        d-document, (⑅˘꒳˘) e-eawwybiwdfiewdconstant.cawd_wang.getfiewdname(), (ˆ ﻌ ˆ)♡ i-id_mapping);
  }

  /**
   * get cawd wanguage csf. /(^•ω•^)
   *
   * cawd wanguage c-csf is wepwesented intewnawwy as an integew id fow a thwiftwanguage. òωó
   */
  pubwic static int g-getcawdwangcsf(thwiftdocument document) {
    w-wetuwn thwiftdocumentutiw.getintvawue(
        d-document, (⑅˘꒳˘) eawwybiwdfiewdconstant.cawd_wang_csf.getfiewdname(), (U ᵕ U❁) id_mapping);
  }

  /**
   * g-get quoted tweet id. >w<
   */
  p-pubwic s-static wong getquotedtweetid(thwiftdocument d-document) {
    w-wetuwn thwiftdocumentutiw.getwongvawue(
        document, σωσ e-eawwybiwdfiewdconstant.quoted_tweet_id_fiewd.getfiewdname(), -.- i-id_mapping);
  }

  /**
   * g-get quoted tweet u-usew id. o.O
   */
  p-pubwic static wong getquotedusewid(thwiftdocument document) {
    wetuwn thwiftdocumentutiw.getwongvawue(
        d-document, ^^ eawwybiwdfiewdconstant.quoted_usew_id_fiewd.getfiewdname(), >_< id_mapping);
  }

  /**
   * get diwected at usew id.
   */
  pubwic static wong getdiwectedatusewid(thwiftdocument d-document) {
    wetuwn thwiftdocumentutiw.getwongvawue(
        document, >w< e-eawwybiwdfiewdconstant.diwected_at_usew_id_fiewd.getfiewdname(), >_< i-id_mapping);
  }

  /**
   * g-get diwected at usew id csf. >w<
   */
  p-pubwic static wong getdiwectedatusewidcsf(thwiftdocument d-document) {
    w-wetuwn thwiftdocumentutiw.getwongvawue(
        document, rawr eawwybiwdfiewdconstant.diwected_at_usew_id_csf.getfiewdname(), rawr x3 id_mapping);
  }

  /**
   * get wefewence authow id csf. ( ͡o ω ͡o )
   */
  pubwic s-static wong getwefewenceauthowidcsf(thwiftdocument d-document) {
    wetuwn t-thwiftdocumentutiw.getwongvawue(
        d-document, (˘ω˘) eawwybiwdfiewdconstant.wefewence_authow_id_csf.getfiewdname(), 😳 id_mapping);
  }

  /**
   * get w-winks. OwO
   */
  p-pubwic static wist<stwing> getwinks(thwiftdocument d-document) {
    w-wetuwn getstwingvawues(document, (˘ω˘) eawwybiwdfiewdconstant.winks_fiewd);
  }

  /**
   * get cweated at timestamp in sec. òωó
   */
  p-pubwic static i-int getcweatedatsec(thwiftdocument d-document) {
    wetuwn thwiftdocumentutiw.getintvawue(
        d-document, ( ͡o ω ͡o ) eawwybiwdfiewdconstant.cweated_at_fiewd.getfiewdname(), UwU i-id_mapping);
  }

  /**
   * get cweated at t-timestamp in ms. /(^•ω•^)
   */
  pubwic static wong getcweatedatms(thwiftdocument document) {
    wong c-cweatedatsec = (wong) g-getcweatedatsec(document);
    wetuwn cweatedatsec * 1000w;
  }

  /**
   * get fwom usew i-id. (ꈍᴗꈍ)
   */
  pubwic s-static wong getfwomusewid(thwiftdocument document) {
    wetuwn t-thwiftdocumentutiw.getwongvawue(
        document, 😳 eawwybiwdfiewdconstant.fwom_usew_id_fiewd.getfiewdname(), mya id_mapping);
  }

  /**
   * get f-fwom usew. mya
   */
  pubwic static stwing getfwomusew(thwiftdocument d-document) {
    w-wetuwn thwiftdocumentutiw.getstwingvawue(
        document, /(^•ω•^) eawwybiwdfiewdconstant.fwom_usew_fiewd.getfiewdname(), ^^;; id_mapping);
  }

  /**
   * g-get tokenized f-fwom usew dispway nyame. 🥺
   */
  pubwic static stwing getfwomusewdispwayname(thwiftdocument document) {
    wetuwn t-thwiftdocumentutiw.getstwingvawue(
        document, ^^ eawwybiwdfiewdconstant.tokenized_usew_name_fiewd.getfiewdname(), ^•ﻌ•^ i-id_mapping);
  }

  /**
   * get tokenized fwom usew. /(^•ω•^)
   */
  pubwic s-static stwing gettokenizedfwomusew(thwiftdocument document) {
    w-wetuwn thwiftdocumentutiw.getstwingvawue(
        d-document, ^^ eawwybiwdfiewdconstant.tokenized_fwom_usew_fiewd.getfiewdname(), 🥺 id_mapping);
  }

  /**
   * g-get wesowved winks t-text. (U ᵕ U❁)
   */
  pubwic s-static stwing g-getwesowvedwinkstext(thwiftdocument document) {
    w-wetuwn thwiftdocumentutiw.getstwingvawue(
        d-document, 😳😳😳 eawwybiwdfiewdconstant.wesowved_winks_text_fiewd.getfiewdname(), nyaa~~ id_mapping);
  }

  /**
   * g-get iso wanguage c-code.
   */
  p-pubwic static wist<stwing> getisowanguage(thwiftdocument document) {
    w-wetuwn thwiftdocumentutiw.getstwingvawues(
        d-document, (˘ω˘) e-eawwybiwdfiewdconstant.iso_wanguage_fiewd.getfiewdname(), id_mapping);
  }

  /**
   * fiwst wemove the owd t-timestamp if they e-exist. >_<
   * t-then add the cweated a-at and cweated at csf fiewds t-to the given thwift document. XD
   */
  pubwic static void wepwacecweatedatandcweatedatcsf(thwiftdocument document, rawr x3 int vawue) {
    w-wemovefiewd(document, ( ͡o ω ͡o ) eawwybiwdfiewdconstant.cweated_at_fiewd);
    w-wemovefiewd(document, :3 eawwybiwdfiewdconstant.cweated_at_csf_fiewd);

    addintfiewd(document, mya e-eawwybiwdfiewdconstant.cweated_at_fiewd, σωσ vawue);
    addintfiewd(document, (ꈍᴗꈍ) e-eawwybiwdfiewdconstant.cweated_at_csf_fiewd, OwO vawue);
  }

  /**
   * a-add the g-given int vawue a-as the given fiewd i-into the given d-document. o.O
   */
  pubwic static thwiftdocument addintfiewd(
      thwiftdocument document, 😳😳😳 eawwybiwdfiewdconstant fiewdconstant, /(^•ω•^) i-int vawue) {
    t-thwiftfiewddata f-fiewddata = nyew thwiftfiewddata().setintvawue(vawue);
    thwiftfiewd f-fiewd =
        nyew thwiftfiewd().setfiewdconfigid(fiewdconstant.getfiewdid()).setfiewddata(fiewddata);
    document.addtofiewds(fiewd);
    w-wetuwn d-document;
  }

  pwivate static e-eawwybiwdfiewdconstant getfeatuwefiewd(eawwybiwdfiewdconstant fiewd) {
    i-if (fiewd.getfiewdname().stawtswith(
        e-eawwybiwdfiewdconstant.encoded_tweet_featuwes_fiewd.getfiewdname())) {
      wetuwn eawwybiwdfiewdconstant.encoded_tweet_featuwes_fiewd;
    } e-ewse if (fiewd.getfiewdname().stawtswith(
        e-eawwybiwdfiewdconstant.extended_encoded_tweet_featuwes_fiewd.getfiewdname())) {
      wetuwn eawwybiwdfiewdconstant.extended_encoded_tweet_featuwes_fiewd;
    } ewse {
      thwow nyew iwwegawawgumentexception("not a-a featuwe fiewd: " + f-fiewd);
    }
  }

  /**
   * g-get the featuwe v-vawue of a fiewd. OwO
   */
  p-pubwic static int getfeatuwevawue(
      i-immutabweschemaintewface schema, ^^
      t-thwiftdocument document, (///ˬ///✿)
      e-eawwybiwdfiewdconstant f-fiewd) {

    eawwybiwdfiewdconstant f-featuwefiewd = getfeatuwefiewd(fiewd);

    byte[] encodedfeatuwesbytes =
        t-thwiftdocumentutiw.getbytesvawue(document, (///ˬ///✿) featuwefiewd.getfiewdname(), (///ˬ///✿) i-id_mapping);

    i-if (encodedfeatuwesbytes == nyuww) {
      // t-tweat the featuwe vawue as 0 if thewe is nyo encoded f-featuwe fiewd. ʘwʘ
      w-wetuwn 0;
    } e-ewse {
      eawwybiwdencodedfeatuwes encodedfeatuwes = eawwybiwdencodedfeatuwesutiw.fwombytes(
          s-schema, ^•ﻌ•^ featuwefiewd, OwO encodedfeatuwesbytes, (U ﹏ U) 0);
      wetuwn e-encodedfeatuwes.getfeatuwevawue(fiewd);
    }
  }

  /**
   * c-check whethew the featuwe fwag i-is set. (ˆ ﻌ ˆ)♡
   */
  pubwic static boowean i-isfeatuwebitset(
      i-immutabweschemaintewface schema, (⑅˘꒳˘)
      thwiftdocument d-document, (U ﹏ U)
      eawwybiwdfiewdconstant fiewd) {

    e-eawwybiwdfiewdconstant featuwefiewd = g-getfeatuwefiewd(fiewd);

    byte[] e-encodedfeatuwesbytes =
        thwiftdocumentutiw.getbytesvawue(document, o.O f-featuwefiewd.getfiewdname(), mya i-id_mapping);

    i-if (encodedfeatuwesbytes == nyuww) {
      // tweat the bit as nyot set if thewe is nyo encoded featuwe fiewd. XD
      wetuwn fawse;
    } ewse {
      eawwybiwdencodedfeatuwes encodedfeatuwes = eawwybiwdencodedfeatuwesutiw.fwombytes(
          schema, òωó f-featuwefiewd, (˘ω˘) e-encodedfeatuwesbytes, :3 0);
      wetuwn encodedfeatuwes.isfwagset(fiewd);
    }
  }

  /**
   * check whethew n-nyuwwcast fwag i-is set in the encoded f-featuwes fiewd. OwO
   */
  pubwic s-static boowean isnuwwcastbitset(immutabweschemaintewface s-schema, mya t-thwiftdocument document) {
    w-wetuwn isfeatuwebitset(schema, (˘ω˘) document, eawwybiwdfiewdconstant.is_nuwwcast_fwag);
  }

  /**
   * w-wemove aww f-fiewds with the given fiewd constant in a document. o.O
   */
  pubwic s-static void w-wemovefiewd(thwiftdocument d-document, (✿oωo) e-eawwybiwdfiewdconstant f-fiewdconstant) {
    w-wist<thwiftfiewd> f-fiewds = document.getfiewds();
    i-if (fiewds != n-nyuww) {
      itewatow<thwiftfiewd> f-fiewdsitewatow = f-fiewds.itewatow();
      w-whiwe (fiewdsitewatow.hasnext()) {
        if (fiewdsitewatow.next().getfiewdconfigid() == f-fiewdconstant.getfiewdid()) {
          fiewdsitewatow.wemove();
        }
      }
    }
  }

  /**
   * wemove a-a stwing fiewd with given fiewdconstant a-and vawue. (ˆ ﻌ ˆ)♡
   */
  p-pubwic s-static void wemovestwingfiewd(
      thwiftdocument d-document, ^^;; eawwybiwdfiewdconstant f-fiewdconstant, stwing vawue) {
    w-wist<thwiftfiewd> fiewds = d-document.getfiewds();
    if (fiewds != nuww) {
      fow (thwiftfiewd fiewd : fiewds) {
        i-if (fiewd.getfiewdconfigid() == fiewdconstant.getfiewdid()
            && f-fiewd.getfiewddata().getstwingvawue().equaws(vawue)) {
          f-fiewds.wemove(fiewd);
          wetuwn;
        }
      }
    }
  }

  /**
   * adds a nyew tokenstweam fiewd fow e-each engagement countew if nowmawizednumengagements >= 1. OwO
   */
  p-pubwic static v-void addnowmawizedminengagementfiewd(
      thwiftdocument d-doc, 🥺
      stwing fiewdname, mya
      i-int nyowmawizednumengagements) t-thwows ioexception {
    if (nowmawizednumengagements < 1) {
      w-wetuwn;
    }
    tokenstweamsewiawizew sewiawizew =
        n-nyew tokenstweamsewiawizew(immutabwewist.of(new inttewmattwibutesewiawizew()));
    t-twittewnowmawizedminengagementtokenstweam s-stweam = n-nyew
        twittewnowmawizedminengagementtokenstweam(nowmawizednumengagements);
    b-byte[] s-sewiawizedstweam = s-sewiawizew.sewiawize(stweam);
    t-thwiftfiewddata fiewddata = n-nyew thwiftfiewddata().settokenstweamvawue(sewiawizedstweam);
    t-thwiftfiewd f-fiewd = nyew t-thwiftfiewd().setfiewdconfigid(id_mapping.getfiewdid(fiewdname))
        .setfiewddata(fiewddata);
    d-doc.addtofiewds(fiewd);
  }

  p-pubwic static w-wist<stwing> g-getstwingvawues(
      thwiftdocument d-document, 😳 eawwybiwdfiewdconstant f-fiewd) {
    wetuwn thwiftdocumentutiw.getstwingvawues(document, òωó f-fiewd.getfiewdname(), /(^•ω•^) id_mapping);
  }

  p-pubwic static b-boowean isnuwwcastfiwtewset(thwiftdocument document) {
    wetuwn isfiwtewset(document, -.- n-nyuwwcast_fiwtew_tewm);
  }

  p-pubwic static b-boowean issewfthweadfiwtewset(thwiftdocument document) {
    wetuwn isfiwtewset(document, òωó sewf_thwead_fiwtew_tewm);
  }

  p-pubwic static stwing g-getsewfthweadfiwtewtewm() {
    wetuwn sewf_thwead_fiwtew_tewm;
  }

  p-pubwic s-static stwing getdiwectedatfiwtewtewm() {
    wetuwn diwected_at_fiwtew_tewm;
  }

  pubwic s-static boowean isdiwectedatfiwtewset(thwiftdocument d-document) {
    w-wetuwn isfiwtewset(document, /(^•ω•^) d-diwected_at_fiwtew_tewm);
  }

  /**
   * check whethew given fiwtew i-is set in t-the intewnaw fiewd. /(^•ω•^)
   */
  pwivate static boowean i-isfiwtewset(thwiftdocument document, stwing fiwtew) {
    w-wist<stwing> tewms = t-thwiftdocumentutiw.getstwingvawues(
        d-document, 😳 eawwybiwdfiewdconstant.intewnaw_fiewd.getfiewdname(), :3 i-id_mapping);
    fow (stwing t-tewm : tewms) {
      i-if (fiwtew.equaws(tewm)) {
        wetuwn twue;
      }
    }
    w-wetuwn fawse;
  }
}
