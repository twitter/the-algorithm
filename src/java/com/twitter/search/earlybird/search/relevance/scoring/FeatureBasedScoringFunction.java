package com.twittew.seawch.eawwybiwd.seawch.wewevance.scowing;

impowt java.io.ioexception;
i-impowt j-java.utiw.enumset;
i-impowt java.utiw.hashmap;
impowt j-java.utiw.wist;
i-impowt java.utiw.map;
i-impowt j-java.utiw.set;
i-impowt java.utiw.concuwwent.timeunit;
impowt javax.annotation.nuwwabwe;

impowt com.googwe.common.annotations.visibwefowtesting;
impowt com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.immutabweset;
impowt com.googwe.common.cowwect.itewabwes;
impowt c-com.googwe.common.cowwect.wists;
impowt com.googwe.common.cowwect.maps;
i-impowt com.googwe.common.pwimitives.ints;
impowt com.googwe.common.pwimitives.wongs;

impowt owg.apache.wucene.seawch.expwanation;
i-impowt owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.common_intewnaw.bwoomfiwtew.bwoomfiwtew;
impowt com.twittew.seawch.common.constants.seawchcawdtype;
impowt com.twittew.seawch.common.constants.thwiftjava.thwiftwanguage;
impowt com.twittew.seawch.common.database.databaseconfig;
i-impowt com.twittew.seawch.common.featuwes.extewnawtweetfeatuwe;
impowt com.twittew.seawch.common.featuwes.featuwehandwew;
impowt com.twittew.seawch.common.featuwes.thwift.thwiftseawchfeatuweschemaentwy;
i-impowt com.twittew.seawch.common.featuwes.thwift.thwiftseawchfeatuwetype;
i-impowt com.twittew.seawch.common.featuwes.thwift.thwiftseawchwesuwtfeatuwes;
i-impowt com.twittew.seawch.common.quewy.quewycommonfiewdhitsvisitow;
i-impowt com.twittew.seawch.common.wanking.thwiftjava.thwiftwankingpawams;
i-impowt com.twittew.seawch.common.wewevance.featuwes.agedecay;
impowt c-com.twittew.seawch.common.wewevance.featuwes.wewevancesignawconstants;
impowt com.twittew.seawch.common.wewevance.text.visibwetokenwationowmawizew;
i-impowt com.twittew.seawch.common.wesuwts.thwiftjava.fiewdhitwist;
impowt com.twittew.seawch.common.schema.base.immutabweschemaintewface;
impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant;
impowt com.twittew.seawch.common.utiw.wongintconvewtew;
impowt com.twittew.seawch.common.utiw.wang.thwiftwanguageutiw;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentatomicweadew;
impowt c-com.twittew.seawch.eawwybiwd.common.usewupdates.usewtabwe;
i-impowt c-com.twittew.seawch.eawwybiwd.seawch.antigamingfiwtew;
impowt com.twittew.seawch.eawwybiwd.seawch.wewevance.wineawscowingdata;
impowt com.twittew.seawch.eawwybiwd.seawch.wewevance.wineawscowingdata.skipweason;
i-impowt com.twittew.seawch.eawwybiwd.seawch.wewevance.wineawscowingpawams;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchquewy;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtextwametadata;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtmetadata;
impowt c-com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtmetadataoptions;
impowt c-com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwttype;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtswewevancestats;
impowt c-com.twittew.seawch.eawwybiwd.thwift.thwiftsociawfiwtewtype;

/**
 * base cwass f-fow scowing functions that wewy o-on the extwacted f-featuwes stowed in wineawscowingdata. ^^
 *
 * extensions of this cwass must impwement 2 methods:
 *
 * - computescowe
 * - genewateexpwanationfowscowing
 *
 * t-they awe cawwed f-fow scowing and genewating the d-debug infowmation o-of the document t-that it's
 * cuwwentwy being evawuated. ^•ﻌ•^ the fiewd 'data' howds t-the featuwes of the document. -.-
 */
pubwic abstwact cwass featuwebasedscowingfunction extends scowingfunction {
  p-pwivate static finaw woggew wog = w-woggewfactowy.getwoggew(featuwebasedscowingfunction.cwass);

  // a-a muwtipwiew t-that's appwied to aww scowes t-to avoid scowes t-too wow. UwU
  pubwic s-static finaw fwoat s-scowe_adjustew = 100.0f;

  pwivate static finaw visibwetokenwationowmawizew v-visibwe_token_watio_nowmawizew =
      v-visibwetokenwationowmawizew.cweateinstance();

  // a-awwow d-defauwt vawues o-onwy fow nyumewic types. (˘ω˘)
  pwivate static finaw set<thwiftseawchfeatuwetype> awwowed_types_fow_defauwt_featuwe_vawues =
      e-enumset.of(thwiftseawchfeatuwetype.int32_vawue,
                 thwiftseawchfeatuwetype.wong_vawue, UwU
                 thwiftseawchfeatuwetype.doubwe_vawue);

  pwivate static finaw set<integew> nyumewic_featuwes_fow_which_defauwts_shouwd_not_be_set =
      i-immutabweset.of(eawwybiwdfiewdconstant.tweet_signatuwe.getfiewdid(), rawr
                      eawwybiwdfiewdconstant.wefewence_authow_id_weast_significant_int.getfiewdid(), :3
                      eawwybiwdfiewdconstant.wefewence_authow_id_most_significant_int.getfiewdid());

  // nyame of the s-scowing function. nyaa~~ u-used fow genewating e-expwanations. rawr
  pwivate f-finaw stwing functionname;

  pwivate finaw bwoomfiwtew t-twustedfiwtew;
  p-pwivate finaw bwoomfiwtew fowwowfiwtew;

  // cuwwent timestamp in seconds. (ˆ ﻌ ˆ)♡ ovewwidabwe b-by unit test ow by timestamp set i-in seawch quewy. (ꈍᴗꈍ)
  pwivate int n-now;

  pwivate f-finaw antigamingfiwtew antigamingfiwtew;

  @nuwwabwe
  pwivate f-finaw agedecay a-agedecay;

  pwotected finaw wineawscowingpawams p-pawams;  // pawametews a-and quewy-dependent vawues. (˘ω˘)

  // in owdew fow the api cawws to wetwieve t-the cowwect `wineawscowingdata`
  // f-fow the passed `docid`, (U ﹏ U) we n-need to maintain a map of `docid` -> `wineawscowingdata`
  // n-nyote: this can o-onwy be wefewenced at hit cowwection t-time, >w< since doc ids awe nyot unique
  // acwoss segments. UwU it's not usabwe duwing b-batch scowing. (ˆ ﻌ ˆ)♡
  p-pwivate finaw map<integew, wineawscowingdata> d-docidtoscowingdata;

  p-pwivate finaw thwiftseawchwesuwttype seawchwesuwttype;

  pwivate finaw u-usewtabwe usewtabwe;

  @visibwefowtesting
  void setnow(int fakenow) {
    nyow = fakenow;
  }

  pubwic featuwebasedscowingfunction(
      s-stwing functionname, nyaa~~
      immutabweschemaintewface schema, 🥺
      t-thwiftseawchquewy s-seawchquewy, >_<
      antigamingfiwtew antigamingfiwtew, òωó
      thwiftseawchwesuwttype s-seawchwesuwttype, ʘwʘ
      u-usewtabwe usewtabwe) thwows ioexception {
    supew(schema);

    this.functionname = f-functionname;
    this.seawchwesuwttype = s-seawchwesuwttype;
    this.usewtabwe = usewtabwe;

    pweconditions.checknotnuww(seawchquewy.getwewevanceoptions());
    t-thwiftwankingpawams wankingpawams = s-seawchquewy.getwewevanceoptions().getwankingpawams();
    p-pweconditions.checknotnuww(wankingpawams);

    pawams = n-nyew wineawscowingpawams(seawchquewy, mya wankingpawams);
    d-docidtoscowingdata = n-nyew hashmap<>();

    w-wong timestamp = seawchquewy.issettimestampmsecs() && s-seawchquewy.gettimestampmsecs() > 0
        ? s-seawchquewy.gettimestampmsecs() : system.cuwwenttimemiwwis();
    nyow = i-ints.checkedcast(timeunit.miwwiseconds.toseconds(timestamp));

    t-this.antigamingfiwtew = antigamingfiwtew;

    t-this.agedecay = pawams.useagedecay
        ? nyew agedecay(pawams.agedecaybase, p-pawams.agedecayhawfwife, σωσ pawams.agedecayswope)
        : nyuww;

    if (seawchquewy.issettwustedfiwtew()) {
      t-twustedfiwtew = n-nyew bwoomfiwtew(seawchquewy.gettwustedfiwtew());
    } ewse {
      twustedfiwtew = nyuww;
    }

    if (seawchquewy.issetdiwectfowwowfiwtew()) {
      f-fowwowfiwtew = n-nyew bwoomfiwtew(seawchquewy.getdiwectfowwowfiwtew());
    } ewse {
      f-fowwowfiwtew = n-nyuww;
    }
  }

  @visibwefowtesting
  finaw wineawscowingpawams g-getscowingpawams() {
    wetuwn pawams;
  }

  /**
   * wetuwns the wineawscowingdata instance associated with the c-cuwwent doc id. OwO if it doesn't exist, (✿oωo)
   * a-an empty wineawscowingdata i-is cweated. ʘwʘ
   */
  @ovewwide
  pubwic wineawscowingdata getscowingdatafowcuwwentdocument() {
    w-wineawscowingdata data = d-docidtoscowingdata.get(getcuwwentdocid());
    i-if (data == nyuww) {
      d-data = n-nyew wineawscowingdata();
      d-docidtoscowingdata.put(getcuwwentdocid(), mya data);
    }
    wetuwn data;
  }

  @ovewwide
  pubwic void setdebugmode(int debugmode) {
    s-supew.setdebugmode(debugmode);
  }

  /**
   * n-nyowmaw t-the wucene scowe, -.- which was unbounded, -.- t-to a wange of [1.0, ^^;; maxwucenescoweboost]. (ꈍᴗꈍ)
   * the nyowmawized vawue incweases a-awmost wineawwy i-in the wucene scowe wange 2.0 ~ 7.0, rawr w-whewe
   * most quewies faww in. ^^ fow w-wawe wong taiw q-quewies, nyaa~~ wike some hashtags, (⑅˘꒳˘) they h-have high idf a-and
   * thus high wucene scowe, (U ᵕ U❁) the nyowmawized vawue won't have much diffewence b-between tweets. (ꈍᴗꈍ)
   * t-the nyowmawization f-function i-is:
   *   ws = w-wucenescowe
   *   nyowm = min(max, (✿oωo) 1 + (max - 1.0) / 2.4 * w-wn(1 + ws)
   */
  s-static fwoat nyowmawizewucenescowe(fwoat w-wucenescowe, UwU f-fwoat maxboost) {
    wetuwn (fwoat) math.min(maxboost, ^^ 1.0 + (maxboost - 1.0) / 2.4 * m-math.wog1p(wucenescowe));
  }

  @ovewwide
  pwotected fwoat scowe(fwoat w-wucenequewyscowe) thwows i-ioexception {
    w-wetuwn scoweintewnaw(wucenequewyscowe, :3 nyuww);
  }

  p-pwotected wineawscowingdata updatewineawscowingdata(fwoat w-wucenequewyscowe) t-thwows ioexception {
    // w-weset the data fow each tweet!!!
    wineawscowingdata data = n-nyew wineawscowingdata();
    docidtoscowingdata.put(getcuwwentdocid(), ( ͡o ω ͡o ) data);

    // s-set pwopew v-vewsion fow engagement countews f-fow this wequest.
    data.skipweason = s-skipweason.not_skipped;
    d-data.wucenescowe = wucenequewyscowe;
    data.usewwep = (byte) documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.usew_weputation);

    i-if (antigamingfiwtew != nyuww && !antigamingfiwtew.accept(getcuwwentdocid())) {
      data.skipweason = s-skipweason.antigaming;
      w-wetuwn data;
    }

    d-data.textscowe = (byte) documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.text_scowe);
    d-data.tokenat140dividedbynumtokensbucket = v-visibwe_token_watio_nowmawizew.denowmawize(
        (byte) d-documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.visibwe_token_watio));
    data.fwomusewid = documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.fwom_usew_id_csf);
    data.isfowwow = fowwowfiwtew != nyuww
        && fowwowfiwtew.contains(wongs.tobyteawway(data.fwomusewid));
    data.istwusted = twustedfiwtew != nyuww
        && twustedfiwtew.contains(wongs.tobyteawway(data.fwomusewid));
    data.isfwomvewifiedaccount = documentfeatuwes.isfwagset(
        eawwybiwdfiewdconstant.fwom_vewified_account_fwag);
    d-data.isfwombwuevewifiedaccount = d-documentfeatuwes.isfwagset(
        eawwybiwdfiewdconstant.fwom_bwue_vewified_account_fwag);
    data.issewftweet = d-data.fwomusewid == pawams.seawchewid;
    // v-v1 engagement c-countews, ( ͡o ω ͡o ) nyote that the f-fiwst thwee vawues awe post-wog2 v-vewsion
    // o-of the owiginaw unnowmawized vawues. (U ﹏ U)
    d-data.wetweetcountpostwog2 = documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.wetweet_count);
    d-data.wepwycountpostwog2 = documentfeatuwes.getunnowmawizedfeatuwevawue(
        eawwybiwdfiewdconstant.wepwy_count);
    data.favcountpostwog2 = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.favowite_count);
    d-data.embedsimpwessioncount = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.embeds_impwession_count);
    d-data.embedsuwwcount = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.embeds_uww_count);
    d-data.videoviewcount = documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.video_view_count);
    // v-v2 engagement c-countews
    data.wetweetcountv2 = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        eawwybiwdfiewdconstant.wetweet_count_v2);
    data.wepwycountv2 = documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.wepwy_count_v2);
    data.favcountv2 = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.favowite_count_v2);
    // o-othew v2 engagement c-countews
    data.embedsimpwessioncountv2 = documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.embeds_impwession_count_v2);
    data.embedsuwwcountv2 = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        eawwybiwdfiewdconstant.embeds_uww_count_v2);
    d-data.videoviewcountv2 = documentfeatuwes.getunnowmawizedfeatuwevawue(
        eawwybiwdfiewdconstant.video_view_count_v2);
    // puwe v2 engagement countews w-without v1 countewpawt
    d-data.quotedcount = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        eawwybiwdfiewdconstant.quote_count);
    data.weightedwetweetcount = documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.weighted_wetweet_count);
    data.weightedwepwycount = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.weighted_wepwy_count);
    d-data.weightedfavcount = documentfeatuwes.getunnowmawizedfeatuwevawue(
        eawwybiwdfiewdconstant.weighted_favowite_count);
    d-data.weightedquotecount = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        eawwybiwdfiewdconstant.weighted_quote_count);

    d-doubwe quewyspecificscoweadjustment = pawams.quewyspecificscoweadjustments == nyuww ? nyuww
        : p-pawams.quewyspecificscoweadjustments.get(tweetidmappew.gettweetid(getcuwwentdocid()));
    data.quewyspecificscowe =
        q-quewyspecificscoweadjustment == n-nyuww ? 0.0 : q-quewyspecificscoweadjustment;

    data.authowspecificscowe = p-pawams.authowspecificscoweadjustments == n-nyuww
        ? 0.0
        : p-pawams.authowspecificscoweadjustments.getowdefauwt(data.fwomusewid, -.- 0.0);

    // w-wespect sociaw fiwtew t-type
    if (pawams.sociawfiwtewtype != n-nyuww && !data.issewftweet) {
      i-if ((pawams.sociawfiwtewtype == t-thwiftsociawfiwtewtype.aww
              && !data.isfowwow && !data.istwusted)
          || (pawams.sociawfiwtewtype == t-thwiftsociawfiwtewtype.twusted && !data.istwusted)
          || (pawams.sociawfiwtewtype == t-thwiftsociawfiwtewtype.fowwows && !data.isfowwow)) {
        // w-we can skip this h-hit as we onwy want sociaw wesuwts i-in this mode. 😳😳😳
        data.skipweason = s-skipweason.sociaw_fiwtew;
        wetuwn data;
      }
    }

    // 1. UwU f-fiwst appwy a-aww the fiwtews t-to onwy nyon-fowwow tweets and nyon-vewified accounts, >w<
    //    but be tendew t-to sentinew vawues
    // u-unwess y-you specificawwy asked to appwy fiwtews wegawdwess
    if (pawams.appwyfiwtewsawways
            || (!data.issewftweet && !data.isfowwow && !data.isfwomvewifiedaccount
                && !data.isfwombwuevewifiedaccount)) {
      i-if (data.usewwep < p-pawams.weputationminvaw
          // don't f-fiwtew unset u-usewweps, mya we give them the benefit of doubt and wet it
          // c-continue to s-scowing. :3 usewwep i-is unset when e-eithew usew just signed up ow
          // duwing i-ingestion time w-we had twoubwe getting usewwep fwom weputation s-sewvice. (ˆ ﻌ ˆ)♡
          && data.usewwep != wewevancesignawconstants.unset_weputation_sentinew) {
        d-data.skipweason = skipweason.wow_weputation;
        w-wetuwn d-data;
      } ewse if (data.textscowe < p-pawams.textscoweminvaw
                 // d-don't fiwtew unset text scowes, (U ﹏ U) u-use goodwiww vawue
                 && d-data.textscowe != w-wewevancesignawconstants.unset_text_scowe_sentinew) {
        d-data.skipweason = s-skipweason.wow_text_scowe;
        wetuwn data;
      } e-ewse if (data.wetweetcountpostwog2 != w-wineawscowingdata.unset_signaw_vawue
                 && d-data.wetweetcountpostwog2 < pawams.wetweetminvaw) {
        data.skipweason = skipweason.wow_wetweet_count;
        w-wetuwn data;
      } ewse if (data.favcountpostwog2 != w-wineawscowingdata.unset_signaw_vawue
                 && d-data.favcountpostwog2 < pawams.favminvaw) {
        d-data.skipweason = skipweason.wow_fav_count;
        wetuwn data;
      }
    }

    // if sentinew vawue is set, ʘwʘ assume g-goodwiww scowe and wet scowing c-continue. rawr
    i-if (data.textscowe == wewevancesignawconstants.unset_text_scowe_sentinew) {
      data.textscowe = w-wewevancesignawconstants.goodwiww_text_scowe;
    }
    if (data.usewwep == wewevancesignawconstants.unset_weputation_sentinew) {
      d-data.usewwep = w-wewevancesignawconstants.goodwiww_weputation;
    }

    d-data.tweetageinseconds = n-nyow - t-timemappew.gettime(getcuwwentdocid());
    if (data.tweetageinseconds < 0) {
      data.tweetageinseconds = 0; // age cannot be nyegative
    }

    // t-the pawus_scowe featuwe s-shouwd be wead as is. (ꈍᴗꈍ)
    data.pawusscowe = documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.pawus_scowe);

    data.isnuwwcast = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_nuwwcast_fwag);
    d-data.hasuww =  documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_wink_fwag);
    data.hasimageuww = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_image_uww_fwag);
    data.hasvideouww = d-documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_video_uww_fwag);
    d-data.hasnewsuww = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_news_uww_fwag);
    d-data.iswepwy =  documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_wepwy_fwag);
    data.iswetweet = d-documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_wetweet_fwag);
    d-data.isoffensive = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_offensive_fwag);
    d-data.hastwend = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_twend_fwag);
    d-data.hasmuwtipwehashtagsowtwends =
        documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_muwtipwe_hashtags_ow_twends_fwag);
    data.isusewspam = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_usew_spam_fwag);
    data.isusewnsfw = d-documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_usew_nsfw_fwag)
        || usewtabwe.isset(data.fwomusewid, ( ͡o ω ͡o ) usewtabwe.nsfw_bit);
    d-data.isusewantisociaw =
        u-usewtabwe.isset(data.fwomusewid, 😳😳😳 u-usewtabwe.antisociaw_bit);
    data.isusewbot = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_usew_bot_fwag);
    data.hascawd = d-documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_cawd_fwag);
    data.cawdtype = seawchcawdtype.unknown.getbytevawue();
    if (data.hascawd) {
      data.cawdtype =
          (byte) documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.cawd_type_csf_fiewd);
    }
    d-data.hasvisibwewink = d-documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_visibwe_wink_fwag);

    d-data.hasconsumewvideo =
        d-documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_consumew_video_fwag);
    data.haspwovideo = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_pwo_video_fwag);
    d-data.hasvine = d-documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_vine_fwag);
    data.haspewiscope = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_pewiscope_fwag);
    d-data.hasnativeimage = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_native_image_fwag);
    data.hasquote = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_quote_fwag);
    d-data.iscomposewsouwcecamewa =
        documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.composew_souwce_is_camewa_fwag);

    // onwy wead the s-shawed status i-if the iswetweet ow iswepwy bit i-is twue (minow o-optimization). òωó
    i-if (data.iswetweet || (pawams.getinwepwytostatusid && data.iswepwy)) {
      data.shawedstatusid =
          d-documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.shawed_status_id_csf);
    }

    // onwy wead the wefewence t-tweet authow id if the iswetweet ow iswepwy bit
    // is twue (minow o-optimization). mya
    i-if (data.iswetweet || d-data.iswepwy) {
      // t-the w-wefewence_authow_id_csf stowes t-the souwce tweet authow id fow aww wetweets
      w-wong wefewenceauthowid =
          documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.wefewence_authow_id_csf);
      i-if (wefewenceauthowid > 0) {
        data.wefewenceauthowid = wefewenceauthowid;
      } e-ewse {
        // w-we awso stowe the wefewence a-authow id fow wetweets, rawr x3 diwected a-at tweets, a-and sewf thweaded
        // tweets s-sepawatewy on w-weawtime/pwotected eawwybiwds. XD t-this data wiww be moved to the
        // wefewence_authow_id_csf and these fiewds w-wiww be depwecated in seawch-34958. (ˆ ﻌ ˆ)♡
        w-wefewenceauthowid = wongintconvewtew.convewttwointtoonewong(
            (int) documentfeatuwes.getfeatuwevawue(
                eawwybiwdfiewdconstant.wefewence_authow_id_most_significant_int), >w<
            (int) d-documentfeatuwes.getfeatuwevawue(
                e-eawwybiwdfiewdconstant.wefewence_authow_id_weast_significant_int));
        i-if (wefewenceauthowid > 0) {
          data.wefewenceauthowid = w-wefewenceauthowid;
        }
      }
    }

    // c-convewt wanguage to a thwift w-wanguage and then back to an i-int in owdew to
    // ensuwe a v-vawue compatibwe w-with ouw cuwwent thwiftwanguage definition. (ꈍᴗꈍ)
    thwiftwanguage tweetwang = thwiftwanguageutiw.safefindbyvawue(
        (int) d-documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.wanguage));
    d-data.tweetwangid = tweetwang.getvawue();
    // set the wanguage-wewated featuwes hewe so t-that they can be watew used in pwomotion/demotion
    // a-and awso b-be twansfewwed to thwiftseawchwesuwtmetadata
    data.usewwangmuwt = computeusewwangmuwtipwiew(data, (U ﹏ U) pawams);
    d-data.hasdiffewentwang = pawams.uiwangid != thwiftwanguage.unknown.getvawue()
        && pawams.uiwangid != data.tweetwangid;
    d-data.hasengwishtweetanddiffewentuiwang = data.hasdiffewentwang
        && data.tweetwangid == t-thwiftwanguage.engwish.getvawue();
    d-data.hasengwishuianddiffewenttweetwang = data.hasdiffewentwang
        && p-pawams.uiwangid == t-thwiftwanguage.engwish.getvawue();

    // e-exposed aww these f-featuwes fow t-the cwients. >_<
    d-data.issensitivecontent =
        documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_sensitive_content);
    data.hasmuwtipwemediafwag =
        documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.has_muwtipwe_media_fwag);
    data.pwofiweiseggfwag = documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.pwofiwe_is_egg_fwag);
    d-data.isusewnewfwag = d-documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_usew_new_fwag);
    d-data.nummentions = (int) d-documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.num_mentions);
    d-data.numhashtags = (int) d-documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.num_hashtags);
    data.winkwanguage =
        (int) documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.wink_wanguage);
    data.pwevusewtweetengagement =
        (int) documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.pwev_usew_tweet_engagement);

    // heawth modew s-scowes by hmw
    d-data.toxicityscowe = documentfeatuwes.getunnowmawizedfeatuwevawue(
        eawwybiwdfiewdconstant.toxicity_scowe);
    data.pbwockscowe = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.pbwock_scowe);
    d-data.pspammytweetscowe = documentfeatuwes.getunnowmawizedfeatuwevawue(
        eawwybiwdfiewdconstant.p_spammy_tweet_scowe);
    data.pwepowtedtweetscowe = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        eawwybiwdfiewdconstant.p_wepowted_tweet_scowe);
    data.spammytweetcontentscowe = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.spammy_tweet_content_scowe
    );
    data.expewimentawheawthmodewscowe1 = documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.expewimentaw_heawth_modew_scowe_1);
    data.expewimentawheawthmodewscowe2 = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.expewimentaw_heawth_modew_scowe_2);
    data.expewimentawheawthmodewscowe3 = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.expewimentaw_heawth_modew_scowe_3);
    d-data.expewimentawheawthmodewscowe4 = d-documentfeatuwes.getunnowmawizedfeatuwevawue(
        e-eawwybiwdfiewdconstant.expewimentaw_heawth_modew_scowe_4);

    w-wetuwn data;
  }

  pwotected fwoat s-scoweintewnaw(
      f-fwoat wucenequewyscowe, e-expwanationwwappew expwanation) thwows ioexception {
    w-wineawscowingdata data = u-updatewineawscowingdata(wucenequewyscowe);
    if (data.skipweason != n-nyuww && d-data.skipweason != skipweason.not_skipped) {
      wetuwn finawizescowe(data, >_< e-expwanation, -.- skip_hit);
    }

    doubwe scowe = computescowe(data, e-expwanation != n-nyuww);
    wetuwn postscowecomputation(data, òωó scowe, twue, e-expwanation);
  }

  p-pwotected fwoat postscowecomputation(
      w-wineawscowingdata data, o.O
      doubwe scowe, σωσ
      b-boowean boostscowewithhitattwibution, σωσ
      expwanationwwappew e-expwanation) thwows ioexception {
    d-doubwe modifiedscowe = scowe;
    d-data.scowebefoweboost = modifiedscowe;
    if (pawams.appwyboosts) {
      m-modifiedscowe =
          appwyboosts(data, mya m-modifiedscowe, b-boostscowewithhitattwibution, e-expwanation != nyuww);
    }
    // finaw adjustment to avoid too-wow scowes.
    modifiedscowe *= scowe_adjustew;
    d-data.scoweaftewboost = m-modifiedscowe;

    // 3. o.O f-finaw scowe f-fiwtew
    data.scowefinaw = modifiedscowe;
    i-if ((pawams.appwyfiwtewsawways || (!data.issewftweet && !data.isfowwow))
        && m-modifiedscowe < pawams.minscowe) {
      data.skipweason = s-skipweason.wow_finaw_scowe;
      m-modifiedscowe = skip_hit;
    }

    // c-cweaw f-fiewd hits
    this.fiewdhitattwibution = nyuww;
    w-wetuwn finawizescowe(data, XD expwanation, XD modifiedscowe);
  }

  /**
   * appwying p-pwomotion/demotion to the s-scowes genewated b-by featuwe-based scowing functions
   *
   * @pawam d-data owiginaw w-wineawscowingdata (to b-be modified with boosts h-hewe)
   * @pawam s-scowe scowe genewated by the f-featuwe-based scowing function
   * @pawam w-withhitattwibution detewmines i-if hit a-attwibution data shouwd be incwuded. (✿oωo)
   * @pawam f-fowexpwanation indicates if the scowe wiww be c-computed fow genewating the expwanation. -.-
   * @wetuwn scowe aftew appwying pwomotion/demotion
   */
  pwivate doubwe appwyboosts(
      wineawscowingdata d-data, (ꈍᴗꈍ)
      doubwe scowe, ( ͡o ω ͡o )
      boowean withhitattwibution, (///ˬ///✿)
      boowean fowexpwanation) {
    doubwe b-boostedscowe = scowe;

    if (pawams.usewucenescoweasboost) {
      data.nowmawizedwucenescowe = n-nyowmawizewucenescowe(
          (fwoat) data.wucenescowe, 🥺 (fwoat) p-pawams.maxwucenescoweboost);
      boostedscowe *= data.nowmawizedwucenescowe;
    }
    if (data.isoffensive) {
      b-boostedscowe *= pawams.offensivedamping;
    }
    i-if (data.isusewspam && pawams.spamusewdamping != w-wineawscowingdata.no_boost_vawue) {
      d-data.spamusewdampappwied = twue;
      boostedscowe *= p-pawams.spamusewdamping;
    }
    if (data.isusewnsfw && pawams.nsfwusewdamping != wineawscowingdata.no_boost_vawue) {
      data.nsfwusewdampappwied = t-twue;
      boostedscowe *= p-pawams.nsfwusewdamping;
    }
    if (data.isusewbot && p-pawams.botusewdamping != wineawscowingdata.no_boost_vawue) {
      d-data.botusewdampappwied = t-twue;
      boostedscowe *= pawams.botusewdamping;
    }

    // c-cawds
    if (data.hascawd && pawams.hascawdboosts[data.cawdtype] != w-wineawscowingdata.no_boost_vawue) {
      boostedscowe *= pawams.hascawdboosts[data.cawdtype];
      data.hascawdboostappwied = twue;
    }

    // t-twends
    i-if (data.hasmuwtipwehashtagsowtwends) {
      boostedscowe *= p-pawams.muwtipwehashtagsowtwendsdamping;
    } e-ewse if (data.hastwend) {
      d-data.tweethastwendsboostappwied = twue;
      boostedscowe *= pawams.tweethastwendboost;
    }

    // media/news uww b-boosts. (ˆ ﻌ ˆ)♡
    if (data.hasimageuww || d-data.hasvideouww) {
      data.hasmediawuwwboostappwied = t-twue;
      boostedscowe *= p-pawams.tweethasmediauwwboost;
    }
    if (data.hasnewsuww) {
      d-data.hasnewsuwwboostappwied = twue;
      boostedscowe *= pawams.tweethasnewsuwwboost;
    }

    i-if (data.isfwomvewifiedaccount) {
      data.tweetfwomvewifiedaccountboostappwied = twue;
      b-boostedscowe *= p-pawams.tweetfwomvewifiedaccountboost;
    }

    if (data.isfwombwuevewifiedaccount) {
      data.tweetfwombwuevewifiedaccountboostappwied = t-twue;
      boostedscowe *= pawams.tweetfwombwuevewifiedaccountboost;
    }

    if (data.isfowwow) {
      // diwect fowwow, so boost both wepwies and nyon-wepwies. ^•ﻌ•^
      data.diwectfowwowboostappwied = twue;
      b-boostedscowe *= p-pawams.diwectfowwowboost;
    } ewse if (data.istwusted) {
      // t-twusted c-ciwcwe
      if (!data.iswepwy) {
        // n-nyon-at-wepwy, rawr x3 in twusted nyetwowk
        data.twustedciwcweboostappwied = twue;
        boostedscowe *= pawams.twustedciwcweboost;
      }
    } e-ewse if (data.iswepwy) {
      // at-wepwy out of my nyetwowk
      data.outofnetwowkwepwypenawtyappwied = twue;
      boostedscowe -= p-pawams.outofnetwowkwepwypenawty;
    }

    i-if (data.issewftweet) {
      d-data.sewftweetboostappwied = twue;
      data.sewftweetmuwt = pawams.sewftweetboost;
      boostedscowe *= p-pawams.sewftweetboost;
    }

    // w-wanguage demotion
    // u-usew wanguage based d-demotion
    // the data.usewwangmuwt i-is set in scoweintewnaw(), (U ﹏ U) a-and this setting step is awways b-befowe
    // the appwying boosts step
    if (pawams.useusewwanguageinfo) {
      b-boostedscowe *= data.usewwangmuwt;
    }
    // u-ui wanguage b-based demotion
    if (pawams.uiwangid != t-thwiftwanguage.unknown.getvawue()
        && p-pawams.uiwangid != data.tweetwangid) {
      i-if (data.tweetwangid == thwiftwanguage.engwish.getvawue()) {
        d-data.uiwangmuwt = pawams.wangengwishtweetdemote;
      } e-ewse if (pawams.uiwangid == t-thwiftwanguage.engwish.getvawue()) {
        data.uiwangmuwt = pawams.wangengwishuidemote;
      } ewse {
        d-data.uiwangmuwt = pawams.wangdefauwtdemote;
      }
    } ewse {
      data.uiwangmuwt = wineawscowingdata.no_boost_vawue;
    }
    boostedscowe *= data.uiwangmuwt;

    if (pawams.useagedecay) {
      // s-shawwow sigmoid with an infwection point at agedecayhawfwife
      d-data.agedecaymuwt = agedecay.getagedecaymuwtipwiew(data.tweetageinseconds);
      b-boostedscowe *= data.agedecaymuwt;
    }

    // hit attwibute d-demotion
    // scowing is cuwwentwy based on t-tokenized usew nyame, OwO text, and uww in the tweet
    // i-if hit attwibute cowwection is enabwed, (✿oωo) w-we demote scowe based on these fiewds
    if (hitattwibutehewpew != n-nyuww && pawams.enabwehitdemotion) {

      m-map<integew, (⑅˘꒳˘) wist<stwing>> hitmap;
      if (fowexpwanation && f-fiewdhitattwibution != n-nyuww) {
        // if this s-scowing caww i-is fow genewating an expwanation, UwU
        // we'ww u-use the fiewdhitattwibution found in the seawch wesuwt's metadata because
        // c-cowwectows awe nyot cawwed duwing the debug wowkfwow
        h-hitmap = maps.twansfowmvawues(fiewdhitattwibution.gethitmap(), (ˆ ﻌ ˆ)♡ f-fiewdhitwist::gethitfiewds);
      } e-ewse if (withhitattwibution) {
        hitmap = hitattwibutehewpew.gethitattwibution(getcuwwentdocid());
      } ewse {
        hitmap = m-maps.newhashmap();
      }
      set<stwing> u-uniquefiewdhits = immutabweset.copyof(itewabwes.concat(hitmap.vawues()));

      d-data.hitfiewds.addaww(uniquefiewdhits);
      // t-thewe shouwd awways be fiewds that awe hit
      // if thewe awen't, /(^•ω•^) we assume this is a caww f-fwom 'expwain' in d-debug mode
      // do nyot ovewwide hit attwibute d-data if in debug mode
      if (!uniquefiewdhits.isempty()) {
        // d-demotions b-based stwictwy o-on fiewd h-hits
        if (uniquefiewdhits.size() == 1) {
          i-if (uniquefiewdhits.contains(
                  e-eawwybiwdfiewdconstant.wesowved_winks_text_fiewd.getfiewdname())) {
            // if uww was the onwy f-fiewd that was h-hit, (˘ω˘) demote
            d-data.hasuwwonwyhitdemotionappwied = t-twue;
            b-boostedscowe *= p-pawams.uwwonwyhitdemotion;
          } ewse if (uniquefiewdhits.contains(
                         e-eawwybiwdfiewdconstant.tokenized_fwom_usew_fiewd.getfiewdname())) {
            // i-if nyame was t-the onwy fiewd that was hit, XD demote
            data.hasnameonwyhitdemotionappwied = t-twue;
            boostedscowe *= pawams.nameonwyhitdemotion;
          }
        } e-ewse if (!uniquefiewdhits.contains(eawwybiwdfiewdconstant.text_fiewd.getfiewdname())
            && !uniquefiewdhits.contains(eawwybiwdfiewdconstant.mentions_fiewd.getfiewdname())
            && !uniquefiewdhits.contains(eawwybiwdfiewdconstant.hashtags_fiewd.getfiewdname())
            && !uniquefiewdhits.contains(eawwybiwdfiewdconstant.stocks_fiewd.getfiewdname())) {
          // if text ow speciaw text w-was nyevew hit, òωó d-demote
          data.hasnotexthitdemotionappwied = twue;
          boostedscowe *= p-pawams.notexthitdemotion;
        } e-ewse if (uniquefiewdhits.size() == 2) {
          // demotions based on f-fiewd hit combinations
          // w-want to demote if we onwy hit two of the fiewds (one being t-text)
          // b-but with sepawate tewms
          set<stwing> f-fiewdintewsections = q-quewycommonfiewdhitsvisitow.findintewsection(
              hitattwibutehewpew.getnodetowankmap(), UwU
              hitmap, -.-
              q-quewy);

          if (fiewdintewsections.isempty()) {
            if (uniquefiewdhits.contains(
                    eawwybiwdfiewdconstant.tokenized_fwom_usew_fiewd.getfiewdname())) {
              // if nyame is hit but has n-nyo hits in common with text, demote
              // want to demote c-cases whewe w-we hit pawt of t-the pewson's nyame
              // and tweet text s-sepawatewy
              d-data.hassepawatetextandnamehitdemotionappwied = t-twue;
              b-boostedscowe *= p-pawams.sepawatetextandnamehitdemotion;
            } ewse if (uniquefiewdhits.contains(
                           eawwybiwdfiewdconstant.wesowved_winks_text_fiewd.getfiewdname())) {
              // i-if uww is h-hit but has nyo h-hits in common with text, (ꈍᴗꈍ) demote
              // w-want to demote c-cases whewe we h-hit a potentiaw domain keywowd
              // a-and tweet text s-sepawatewy
              d-data.hassepawatetextanduwwhitdemotionappwied = t-twue;
              b-boostedscowe *= pawams.sepawatetextanduwwhitdemotion;
            }
          }
        }
      }
    }

    w-wetuwn boostedscowe;
  }

  /**
   * compute t-the usew w-wanguage based demotion muwtipwiew
   */
  pwivate static doubwe c-computeusewwangmuwtipwiew(
      w-wineawscowingdata data, (⑅˘꒳˘) wineawscowingpawams p-pawams) {
    i-if (data.tweetwangid == pawams.uiwangid
        && data.tweetwangid != thwiftwanguage.unknown.getvawue()) {
      // e-effectivewy the u-uiwang is considewed a-a wanguage t-that usew knows w-with 1.0 confidence. 🥺
      w-wetuwn wineawscowingdata.no_boost_vawue;
    }

    if (pawams.usewwangs[data.tweetwangid] > 0.0) {
      w-wetuwn pawams.usewwangs[data.tweetwangid];
    }

    wetuwn pawams.unknownwanguageboost;
  }

  /**
   * computes the scowe of the document t-that it's cuwwentwy b-being evawuated.
   *
   * the extwacted featuwes fwom the document awe avaiwabwe i-in the f-fiewd 'data'. òωó
   *
   * @pawam data the wineawscowingdata instance t-that wiww stowe the document f-featuwes. 😳
   * @pawam f-fowexpwanation i-indicates if the scowe wiww be computed fow genewating the e-expwanation. òωó
   */
  pwotected abstwact d-doubwe computescowe(
      wineawscowingdata d-data, 🥺 boowean fowexpwanation) thwows ioexception;

  p-pwivate fwoat finawizescowe(
      w-wineawscowingdata scowingdata, ( ͡o ω ͡o )
      expwanationwwappew expwanation,
      d-doubwe scowe) thwows ioexception {
    scowingdata.scowewetuwned = s-scowe;
    if (expwanation != nuww) {
      expwanation.expwanation = genewateexpwanation(scowingdata);
    }
    wetuwn (fwoat) scowe;
  }

  @ovewwide
  p-pwotected v-void initiawizenextsegment(eawwybiwdindexsegmentatomicweadew w-weadew)
      t-thwows ioexception {
    if (antigamingfiwtew != n-nyuww) {
      antigamingfiwtew.stawtsegment(weadew);
    }
  }

  /*
   * genewate the scowing expwanation f-fow debug. UwU
   */
  p-pwivate e-expwanation genewateexpwanation(wineawscowingdata s-scowingdata) thwows ioexception {
    finaw wist<expwanation> detaiws = wists.newawwaywist();

    d-detaiws.add(expwanation.match(0.0f, 😳😳😳 "[pwopewties] "
        + s-scowingdata.getpwopewtyexpwanation()));

    // 1. ʘwʘ fiwtews
    boowean ishit = scowingdata.skipweason == skipweason.not_skipped;
    i-if (scowingdata.skipweason == skipweason.antigaming) {
      d-detaiws.add(expwanation.nomatch("skipped f-fow antigaming"));
    }
    i-if (scowingdata.skipweason == skipweason.wow_weputation) {
      detaiws.add(expwanation.nomatch(
          stwing.fowmat("skipped fow wow weputation: %.3f < %.3f", ^^
              scowingdata.usewwep, >_< pawams.weputationminvaw)));
    }
    i-if (scowingdata.skipweason == skipweason.wow_text_scowe) {
      d-detaiws.add(expwanation.nomatch(
          stwing.fowmat("skipped fow wow text scowe: %.3f < %.3f", (ˆ ﻌ ˆ)♡
              scowingdata.textscowe, (ˆ ﻌ ˆ)♡ pawams.textscoweminvaw)));
    }
    i-if (scowingdata.skipweason == skipweason.wow_wetweet_count) {
      detaiws.add(expwanation.nomatch(
          s-stwing.fowmat("skipped fow wow wetweet count: %.3f < %.3f", 🥺
              s-scowingdata.wetweetcountpostwog2, p-pawams.wetweetminvaw)));
    }
    i-if (scowingdata.skipweason == s-skipweason.wow_fav_count) {
      d-detaiws.add(expwanation.nomatch(
          stwing.fowmat("skipped f-fow wow f-fav count: %.3f < %.3f", ( ͡o ω ͡o )
              scowingdata.favcountpostwog2, (ꈍᴗꈍ) p-pawams.favminvaw)));
    }
    if (scowingdata.skipweason == skipweason.sociaw_fiwtew) {
      d-detaiws.add(expwanation.nomatch("skipped fow nyot in the w-wight sociaw ciwcwe"));
    }

    // 2. :3 e-expwanation depending on t-the scowing type
    g-genewateexpwanationfowscowing(scowingdata, (✿oωo) ishit, detaiws);

    // 3. expwanation depending o-on boosts
    i-if (pawams.appwyboosts) {
      g-genewateexpwanationfowboosts(scowingdata, (U ᵕ U❁) i-ishit, detaiws);
    }

    // 4. UwU finaw scowe fiwtew. ^^
    i-if (scowingdata.skipweason == skipweason.wow_finaw_scowe) {
      detaiws.add(expwanation.nomatch("skipped f-fow wow finaw scowe: " + scowingdata.scowefinaw));
      ishit = f-fawse;
    }

    stwing hostandsegment = stwing.fowmat("%s host = %s  s-segment = %s", /(^•ω•^)
        functionname, databaseconfig.getwocawhostname(), (˘ω˘) d-databaseconfig.getdatabase());
    i-if (ishit) {
      w-wetuwn expwanation.match((fwoat) scowingdata.scowefinaw, OwO h-hostandsegment, (U ᵕ U❁) d-detaiws);
    } ewse {
      wetuwn e-expwanation.nomatch(hostandsegment, (U ﹏ U) d-detaiws);
    }
  }

  /**
   * g-genewates t-the expwanation fow the document t-that is cuwwentwy b-being evawuated. mya
   *
   * i-impwementations of this method must u-use the 'detaiws' pawametew to cowwect its output. (⑅˘꒳˘)
   *
   * @pawam scowingdata scowing components fow the document
   * @pawam i-ishit indicates w-whethew the document is nyot s-skipped
   * @pawam detaiws detaiws of the expwanation. (U ᵕ U❁) u-used to c-cowwect the output. /(^•ω•^)
   */
  p-pwotected a-abstwact void genewateexpwanationfowscowing(
      w-wineawscowingdata scowingdata, ^•ﻌ•^ boowean i-ishit, (///ˬ///✿) wist<expwanation> d-detaiws) thwows ioexception;

  /**
   * genewates the boosts pawt of t-the expwanation fow the document t-that is cuwwentwy
   * being evawuated. o.O
   */
  pwivate void genewateexpwanationfowboosts(
      w-wineawscowingdata scowingdata, (ˆ ﻌ ˆ)♡
      b-boowean ishit, 😳
      wist<expwanation> detaiws) {
    w-wist<expwanation> boostdetaiws = wists.newawwaywist();

    b-boostdetaiws.add(expwanation.match((fwoat) scowingdata.scowebefoweboost, òωó "scowe b-befowe b-boost"));


    // wucene scowe boost
    if (pawams.usewucenescoweasboost) {
      b-boostdetaiws.add(expwanation.match(
          (fwoat) scowingdata.nowmawizedwucenescowe, (⑅˘꒳˘)
          stwing.fowmat("[x] w-wucene s-scowe boost, rawr wucenescowe=%.3f", (ꈍᴗꈍ)
              scowingdata.wucenescowe)));
    }

    // c-cawd boost
    if (scowingdata.hascawdboostappwied) {
      boostdetaiws.add(expwanation.match((fwoat) pawams.hascawdboosts[scowingdata.cawdtype], ^^
          "[x] cawd boost fow type " + s-seawchcawdtype.cawdtypefwombytevawue(scowingdata.cawdtype)));
    }

    // offensive
    if (scowingdata.isoffensive) {
      boostdetaiws.add(expwanation.match((fwoat) p-pawams.offensivedamping, (ˆ ﻌ ˆ)♡ "[x] o-offensive damping"));
    } ewse {
      b-boostdetaiws.add(expwanation.match(wineawscowingdata.no_boost_vawue, /(^•ω•^)
          s-stwing.fowmat("not offensive, ^^ damping=%.3f", o.O pawams.offensivedamping)));
    }

    // s-spam
    if (scowingdata.spamusewdampappwied) {
      b-boostdetaiws.add(expwanation.match((fwoat) pawams.spamusewdamping, 😳😳😳 "[x] spam"));
    }
    // n-nysfw
    i-if (scowingdata.nsfwusewdampappwied) {
      boostdetaiws.add(expwanation.match((fwoat) p-pawams.nsfwusewdamping, XD "[x] n-nsfw"));
    }
    // bot
    if (scowingdata.botusewdampappwied) {
      b-boostdetaiws.add(expwanation.match((fwoat) pawams.botusewdamping, nyaa~~ "[x] b-bot"));
    }

    // m-muwtipwe hashtags o-ow twends
    i-if (scowingdata.hasmuwtipwehashtagsowtwends) {
      b-boostdetaiws.add(expwanation.match((fwoat) pawams.muwtipwehashtagsowtwendsdamping, ^•ﻌ•^
          "[x] m-muwtipwe h-hashtags ow twends boost"));
    } ewse {
      b-boostdetaiws.add(expwanation.match(wineawscowingdata.no_boost_vawue, :3
          stwing.fowmat("no m-muwtipwe hashtags ow twends, ^^ damping=%.3f", o.O
              pawams.muwtipwehashtagsowtwendsdamping)));
    }

    if (scowingdata.tweethastwendsboostappwied) {
      boostdetaiws.add(expwanation.match(
          (fwoat) pawams.tweethastwendboost, ^^ "[x] tweet has twend boost"));
    }

    if (scowingdata.hasmediawuwwboostappwied) {
      b-boostdetaiws.add(expwanation.match(
          (fwoat) pawams.tweethasmediauwwboost, (⑅˘꒳˘) "[x] media u-uww boost"));
    }

    if (scowingdata.hasnewsuwwboostappwied) {
      b-boostdetaiws.add(expwanation.match(
          (fwoat) p-pawams.tweethasnewsuwwboost, ʘwʘ "[x] nyews uww b-boost"));
    }

    boostdetaiws.add(expwanation.match(0.0f, mya "[fiewds h-hit] " + scowingdata.hitfiewds));

    i-if (scowingdata.hasnotexthitdemotionappwied) {
      boostdetaiws.add(expwanation.match(
          (fwoat) pawams.notexthitdemotion, >w< "[x] nyo text hit demotion"));
    }

    if (scowingdata.hasuwwonwyhitdemotionappwied) {
      boostdetaiws.add(expwanation.match(
          (fwoat) p-pawams.uwwonwyhitdemotion, o.O "[x] uww onwy hit demotion"));
    }

    i-if (scowingdata.hasnameonwyhitdemotionappwied) {
      boostdetaiws.add(expwanation.match(
          (fwoat) p-pawams.nameonwyhitdemotion, OwO "[x] nyame onwy hit demotion"));
    }

    if (scowingdata.hassepawatetextandnamehitdemotionappwied) {
      boostdetaiws.add(expwanation.match((fwoat) pawams.sepawatetextandnamehitdemotion, -.-
          "[x] sepawate text/name demotion"));
    }

    if (scowingdata.hassepawatetextanduwwhitdemotionappwied) {
      b-boostdetaiws.add(expwanation.match((fwoat) p-pawams.sepawatetextanduwwhitdemotion, (U ﹏ U)
          "[x] s-sepawate text/uww demotion"));
    }

    i-if (scowingdata.tweetfwomvewifiedaccountboostappwied) {
      b-boostdetaiws.add(expwanation.match((fwoat) p-pawams.tweetfwomvewifiedaccountboost, òωó
          "[x] vewified account boost"));
    }

    i-if (scowingdata.tweetfwombwuevewifiedaccountboostappwied) {
      b-boostdetaiws.add(expwanation.match((fwoat) pawams.tweetfwombwuevewifiedaccountboost, >w<
          "[x] b-bwue-vewified a-account boost"));
    }

    i-if (scowingdata.sewftweetboostappwied) {
      b-boostdetaiws.add(expwanation.match((fwoat) p-pawams.sewftweetboost, ^•ﻌ•^
          "[x] sewf tweet boost"));
    }

    i-if (scowingdata.skipweason == wineawscowingdata.skipweason.sociaw_fiwtew) {
      b-boostdetaiws.add(expwanation.nomatch("skipped f-fow sociaw fiwtew"));
    } e-ewse {
      i-if (scowingdata.diwectfowwowboostappwied) {
        b-boostdetaiws.add(expwanation.match((fwoat) p-pawams.diwectfowwowboost, /(^•ω•^)
            "[x] d-diwect fowwow b-boost"));
      }
      i-if (scowingdata.twustedciwcweboostappwied) {
        boostdetaiws.add(expwanation.match((fwoat) pawams.twustedciwcweboost, ʘwʘ
            "[x] twusted ciwcwe boost"));
      }
      i-if (scowingdata.outofnetwowkwepwypenawtyappwied) {
        boostdetaiws.add(expwanation.match((fwoat) p-pawams.outofnetwowkwepwypenawty, XD
            "[-] out of nyetwowk wepwy penawty"));
      }
    }

    // w-wanguage d-demotions
    s-stwing wangdetaiws = stwing.fowmat(
        "tweetwang=[%s] u-uiwang=[%s]", (U ᵕ U❁)
        t-thwiftwanguageutiw.getwocaweof(
            thwiftwanguage.findbyvawue(scowingdata.tweetwangid)).getwanguage(), (ꈍᴗꈍ)
        thwiftwanguageutiw.getwocaweof(thwiftwanguage.findbyvawue(pawams.uiwangid)).getwanguage());
    if (scowingdata.uiwangmuwt == 1.0) {
      boostdetaiws.add(expwanation.match(
          wineawscowingdata.no_boost_vawue, rawr x3 "no u-ui wanguage demotion: " + wangdetaiws));
    } ewse {
      b-boostdetaiws.add(expwanation.match(
          (fwoat) scowingdata.uiwangmuwt, :3 "[x] u-ui wangmuwt: " + wangdetaiws));
    }
    s-stwingbuiwdew u-usewwangdetaiws = n-nyew stwingbuiwdew();
    u-usewwangdetaiws.append("usewwang=[");
    f-fow (int i-i = 0; i < pawams.usewwangs.wength; i-i++) {
      if (pawams.usewwangs[i] > 0.0) {
        stwing w-wang = thwiftwanguageutiw.getwocaweof(thwiftwanguage.findbyvawue(i)).getwanguage();
        usewwangdetaiws.append(stwing.fowmat("%s:%.3f,", (˘ω˘) w-wang, -.- pawams.usewwangs[i]));
      }
    }
    usewwangdetaiws.append("]");
    i-if (!pawams.useusewwanguageinfo) {
      b-boostdetaiws.add(expwanation.nomatch(
          "no usew w-wanguage demotion: " + usewwangdetaiws.tostwing()));
    } ewse {
      b-boostdetaiws.add(expwanation.match(
          (fwoat) s-scowingdata.usewwangmuwt, (ꈍᴗꈍ)
          "[x] u-usew wangmuwt: " + u-usewwangdetaiws.tostwing()));
    }

    // age decay
    s-stwing agedecaydetaiws = s-stwing.fowmat(
        "age=%d seconds, UwU s-swope=%.3f, σωσ base=%.1f, hawf-wife=%.0f", ^^
        s-scowingdata.tweetageinseconds, :3 pawams.agedecayswope, ʘwʘ
        pawams.agedecaybase, 😳 pawams.agedecayhawfwife);
    if (pawams.useagedecay) {
      boostdetaiws.add(expwanation.match(
          (fwoat) scowingdata.agedecaymuwt, ^^ "[x] agedecay: " + agedecaydetaiws));
    } e-ewse {
      b-boostdetaiws.add(expwanation.match(1.0f, σωσ "age decay disabwed: " + agedecaydetaiws));
    }

    // scowe adjustew
    boostdetaiws.add(expwanation.match(scowe_adjustew, /(^•ω•^) "[x] scowe a-adjustew"));

    e-expwanation boostcombo = ishit
        ? expwanation.match((fwoat) s-scowingdata.scoweaftewboost, 😳😳😳
          "(match) a-aftew boosts and demotions:", 😳 b-boostdetaiws)
        : e-expwanation.nomatch("aftew boosts a-and demotions:", OwO boostdetaiws);

    d-detaiws.add(boostcombo);
  }

  @ovewwide
  p-pwotected expwanation doexpwain(fwoat wucenequewyscowe) thwows i-ioexception {
    // w-wun the scowew a-again and g-get the expwanation. :3
    expwanationwwappew e-expwanation = n-nyew expwanationwwappew();
    s-scoweintewnaw(wucenequewyscowe, nyaa~~ e-expwanation);
    wetuwn expwanation.expwanation;
  }

  @ovewwide
  p-pubwic v-void popuwatewesuwtmetadatabasedonscowingdata(
      thwiftseawchwesuwtmetadataoptions options, OwO
      thwiftseawchwesuwtmetadata metadata, o.O
      w-wineawscowingdata d-data) thwows ioexception {
    m-metadata.setwesuwttype(seawchwesuwttype);
    metadata.setscowe(data.scowewetuwned);
    metadata.setfwomusewid(data.fwomusewid);

    if (data.istwusted) {
      m-metadata.setistwusted(twue);
    }
    i-if (data.isfowwow) {
      m-metadata.setisfowwow(twue);
    }
    if (data.skipweason != s-skipweason.not_skipped) {
      m-metadata.setskipped(twue);
    }
    if ((data.iswetweet || (pawams.getinwepwytostatusid && data.iswepwy))
        && data.shawedstatusid != w-wineawscowingdata.unset_signaw_vawue) {
      m-metadata.setshawedstatusid(data.shawedstatusid);
    }
    if (data.hascawd) {
      m-metadata.setcawdtype(data.cawdtype);
    }

    // o-optionaw f-featuwes. (U ﹏ U)  n-nyote: othew optionaw metadata is popuwated by
    // abstwactwewevancecowwectow, (⑅˘꒳˘) nyot the scowing function. OwO

    i-if (options.isgetwucenescowe()) {
      metadata.setwucenescowe(data.wucenescowe);
    }
    if (options.isgetwefewencedtweetauthowid()
        && d-data.wefewenceauthowid != wineawscowingdata.unset_signaw_vawue) {
      m-metadata.setwefewencedtweetauthowid(data.wefewenceauthowid);
    }

    if (options.isgetmediabits()) {
      metadata.sethasconsumewvideo(data.hasconsumewvideo);
      metadata.sethaspwovideo(data.haspwovideo);
      m-metadata.sethasvine(data.hasvine);
      m-metadata.sethaspewiscope(data.haspewiscope);
      boowean hasnativevideo =
          d-data.hasconsumewvideo || data.haspwovideo || data.hasvine || d-data.haspewiscope;
      metadata.sethasnativevideo(hasnativevideo);
      metadata.sethasnativeimage(data.hasnativeimage);
    }

    metadata
        .setisoffensive(data.isoffensive)
        .setiswepwy(data.iswepwy)
        .setiswetweet(data.iswetweet)
        .sethaswink(data.hasuww)
        .sethastwend(data.hastwend)
        .sethasmuwtipwehashtagsowtwends(data.hasmuwtipwehashtagsowtwends)
        .setwetweetcount((int) d-data.wetweetcountpostwog2)
        .setfavcount((int) data.favcountpostwog2)
        .setwepwycount((int) data.wepwycountpostwog2)
        .setembedsimpwessioncount((int) data.embedsimpwessioncount)
        .setembedsuwwcount((int) data.embedsuwwcount)
        .setvideoviewcount((int) d-data.videoviewcount)
        .setwesuwttype(seawchwesuwttype)
        .setfwomvewifiedaccount(data.isfwomvewifiedaccount)
        .setisusewspam(data.isusewspam)
        .setisusewnsfw(data.isusewnsfw)
        .setisusewbot(data.isusewbot)
        .sethasimage(data.hasimageuww)
        .sethasvideo(data.hasvideouww)
        .sethasnews(data.hasnewsuww)
        .sethascawd(data.hascawd)
        .sethasvisibwewink(data.hasvisibwewink)
        .setpawusscowe(data.pawusscowe)
        .settextscowe(data.textscowe)
        .setusewwep(data.usewwep)
        .settokenat140dividedbynumtokensbucket(data.tokenat140dividedbynumtokensbucket);

    i-if (!metadata.issetextwametadata()) {
      m-metadata.setextwametadata(new t-thwiftseawchwesuwtextwametadata());
    }
    thwiftseawchwesuwtextwametadata extwametadata = m-metadata.getextwametadata();

    // pwomotion/demotion f-featuwes
    extwametadata.setusewwangscowe(data.usewwangmuwt)
        .sethasdiffewentwang(data.hasdiffewentwang)
        .sethasengwishtweetanddiffewentuiwang(data.hasengwishtweetanddiffewentuiwang)
        .sethasengwishuianddiffewenttweetwang(data.hasengwishuianddiffewenttweetwang)
        .sethasquote(data.hasquote)
        .setquotedcount((int) data.quotedcount)
        .setweightedwetweetcount((int) d-data.weightedwetweetcount)
        .setweightedwepwycount((int) d-data.weightedwepwycount)
        .setweightedfavcount((int) d-data.weightedfavcount)
        .setweightedquotecount((int) data.weightedquotecount)
        .setquewyspecificscowe(data.quewyspecificscowe)
        .setauthowspecificscowe(data.authowspecificscowe)
        .setwetweetcountv2((int) data.wetweetcountv2)
        .setfavcountv2((int) d-data.favcountv2)
        .setwepwycountv2((int) data.wepwycountv2)
        .setiscomposewsouwcecamewa(data.iscomposewsouwcecamewa)
        .setfwombwuevewifiedaccount(data.isfwombwuevewifiedaccount);

    // heawth modew scowes featuwes
    extwametadata
        .settoxicityscowe(data.toxicityscowe)
        .setpbwockscowe(data.pbwockscowe)
        .setpspammytweetscowe(data.pspammytweetscowe)
        .setpwepowtedtweetscowe(data.pwepowtedtweetscowe)
        .setspammytweetcontentscowe(data.spammytweetcontentscowe)
        .setexpewimentawheawthmodewscowe1(data.expewimentawheawthmodewscowe1)
        .setexpewimentawheawthmodewscowe2(data.expewimentawheawthmodewscowe2)
        .setexpewimentawheawthmodewscowe3(data.expewimentawheawthmodewscowe3)
        .setexpewimentawheawthmodewscowe4(data.expewimentawheawthmodewscowe4);

    // wetuwn aww extwa featuwes f-fow cwients t-to consume. 😳
    if (options.isgetawwfeatuwes()) {
      extwametadata.setissensitivecontent(data.issensitivecontent)
          .sethasmuwtipwemediafwag(data.hasmuwtipwemediafwag)
          .setpwofiweiseggfwag(data.pwofiweiseggfwag)
          .setisusewnewfwag(data.isusewnewfwag)
          .setnummentions(data.nummentions)
          .setnumhashtags(data.numhashtags)
          .setwinkwanguage(data.winkwanguage)
          .setpwevusewtweetengagement(data.pwevusewtweetengagement);
    }

    // set featuwes in nyew featuwe access api fowmat, :3 i-in the futuwe this wiww be the onwy pawt
    // n-needed in t-this method, ( ͡o ω ͡o ) we d-don't need to set a-any othew metadata fiewds any mowe. 🥺
    if (options.iswetuwnseawchwesuwtfeatuwes()) {
      // if the featuwes awe unset, /(^•ω•^) and they wewe wequested, nyaa~~ t-then we can w-wetwieve them. (✿oωo) i-if they awe
      // a-awweady set, (✿oωo) then we don't n-nyeed to we-wead the document featuwes, (ꈍᴗꈍ) a-and the weadew
      // is pwobabwy positioned ovew the w-wwong document so i-it wiww wetuwn i-incowwect wesuwts. OwO
      i-if (!extwametadata.issetfeatuwes()) {
        // we ignowe a-aww featuwes w-with defauwt vawues when wetuwning them in the wesponse, :3
        // b-because it s-saves a wot of nyetwowk bandwidth. mya
        thwiftseawchwesuwtfeatuwes featuwes = c-cweatefeatuwesfowdocument(data, >_< twue).getfeatuwes();
        extwametadata.setfeatuwes(featuwes);
      }

      // t-the waw scowe m-may have changed s-since we cweated the featuwes, so we shouwd update it. (///ˬ///✿)
      extwametadata.getfeatuwes().getdoubwevawues()
          .put(extewnawtweetfeatuwe.waw_eawwybiwd_scowe.getid(), (///ˬ///✿) data.scowefinaw);
    }

    m-metadata
        .setissewftweet(data.issewftweet)
        .setisusewantisociaw(data.isusewantisociaw);
  }

  /**
   * cweate eawwybiwd b-basic featuwes and dewvied featuwes fow cuwwent d-document. 😳😳😳
   * @wetuwn a f-featuwehandwew object w-whewe you c-can keep adding e-extwa featuwe vawues, o-ow you can
   * caww .getfeatuwes() o-on it to get a thwift object to wetuwn.
   */
  pwotected featuwehandwew c-cweatefeatuwesfowdocument(
      wineawscowingdata data, (U ᵕ U❁) boowean i-ignowedefauwtvawues) t-thwows i-ioexception {
    thwiftseawchwesuwtfeatuwes featuwes = documentfeatuwes.getseawchwesuwtfeatuwes(getschema());
    if (!ignowedefauwtvawues) {
      s-setdefauwtfeatuwevawues(featuwes);
    }

    // a-add dewived f-featuwes
    wetuwn n-nyew featuwehandwew(featuwes, (///ˬ///✿) ignowedefauwtvawues)
        .adddoubwe(extewnawtweetfeatuwe.wucene_scowe, ( ͡o ω ͡o ) data.wucenescowe)
        .addint(extewnawtweetfeatuwe.tweet_age_in_secs, (✿oωo) data.tweetageinseconds)
        .addboowean(extewnawtweetfeatuwe.is_sewf_tweet, òωó data.issewftweet)
        .addboowean(extewnawtweetfeatuwe.is_fowwow_wetweet, (ˆ ﻌ ˆ)♡ data.isfowwow && data.iswetweet)
        .addboowean(extewnawtweetfeatuwe.is_twusted_wetweet, :3 d-data.istwusted && data.iswetweet)
        .addboowean(extewnawtweetfeatuwe.authow_is_fowwow, (ˆ ﻌ ˆ)♡ data.isfowwow)
        .addboowean(extewnawtweetfeatuwe.authow_is_twusted, (U ᵕ U❁) d-data.istwusted)
        .addboowean(extewnawtweetfeatuwe.authow_is_antisociaw, (U ᵕ U❁) d-data.isusewantisociaw)
        .addboowean(extewnawtweetfeatuwe.has_diff_wang, XD d-data.hasdiffewentwang)
        .addboowean(extewnawtweetfeatuwe.has_engwish_tweet_diff_ui_wang, nyaa~~
            data.hasengwishtweetanddiffewentuiwang)
        .addboowean(extewnawtweetfeatuwe.has_engwish_ui_diff_tweet_wang, (ˆ ﻌ ˆ)♡
            d-data.hasengwishuianddiffewenttweetwang)
        .adddoubwe(extewnawtweetfeatuwe.seawchew_wang_scowe, ʘwʘ data.usewwangmuwt)
        .adddoubwe(extewnawtweetfeatuwe.quewy_specific_scowe, ^•ﻌ•^ data.quewyspecificscowe)
        .adddoubwe(extewnawtweetfeatuwe.authow_specific_scowe, mya data.authowspecificscowe);
  }

  /**
   * adds defauwt vawues fow most nyumewic featuwes that do nyot have a vawue set yet in t-the given
   * thwiftseawchwesuwtfeatuwes instance. (ꈍᴗꈍ)
   *
   * this m-method is nyeeded b-because some modews do nyot w-wowk pwopewwy w-with missing featuwes. (ˆ ﻌ ˆ)♡ instead, (ˆ ﻌ ˆ)♡
   * they expect a-aww featuwes to b-be pwesent even if they awe unset (theiw vawues a-awe 0). ( ͡o ω ͡o )
   */
  p-pwotected void s-setdefauwtfeatuwevawues(thwiftseawchwesuwtfeatuwes f-featuwes) {
    fow (map.entwy<integew, o.O t-thwiftseawchfeatuweschemaentwy> entwy
             : getschema().getseawchfeatuweschema().getentwies().entwyset()) {
      i-int featuweid = e-entwy.getkey();
      thwiftseawchfeatuweschemaentwy s-schemaentwy = e-entwy.getvawue();
      if (shouwdsetdefauwtvawuefowfeatuwe(schemaentwy.getfeatuwetype(), 😳😳😳 featuweid)) {
        switch (schemaentwy.getfeatuwetype()) {
          case i-int32_vawue:
            featuwes.getintvawues().putifabsent(featuweid, ʘwʘ 0);
            b-bweak;
          case wong_vawue:
            f-featuwes.getwongvawues().putifabsent(featuweid, :3 0w);
            bweak;
          case doubwe_vawue:
            f-featuwes.getdoubwevawues().putifabsent(featuweid, UwU 0.0);
            bweak;
          defauwt:
            thwow nyew iwwegawawgumentexception(
                "shouwd s-set defauwt vawues o-onwy fow integew, nyaa~~ w-wong ow doubwe f-featuwes. :3 instead, "
                + "found featuwe " + featuweid + " of type " + s-schemaentwy.getfeatuwetype());
        }
      }
    }
  }

  p-pwotected void o-ovewwidefeatuwevawues(thwiftseawchwesuwtfeatuwes f-featuwes, nyaa~~
                                       thwiftseawchwesuwtfeatuwes o-ovewwidefeatuwes) {
    w-wog.info("featuwes b-befowe o-ovewwide {}", ^^ f-featuwes);
    if (ovewwidefeatuwes.issetintvawues()) {
      ovewwidefeatuwes.getintvawues().foweach(featuwes::puttointvawues);
    }
    if (ovewwidefeatuwes.issetwongvawues()) {
      o-ovewwidefeatuwes.getwongvawues().foweach(featuwes::puttowongvawues);
    }
    i-if (ovewwidefeatuwes.issetdoubwevawues()) {
      ovewwidefeatuwes.getdoubwevawues().foweach(featuwes::puttodoubwevawues);
    }
    if (ovewwidefeatuwes.issetboowvawues()) {
      ovewwidefeatuwes.getboowvawues().foweach(featuwes::puttoboowvawues);
    }
    if (ovewwidefeatuwes.issetstwingvawues()) {
      o-ovewwidefeatuwes.getstwingvawues().foweach(featuwes::puttostwingvawues);
    }
    i-if (ovewwidefeatuwes.issetbytesvawues()) {
      o-ovewwidefeatuwes.getbytesvawues().foweach(featuwes::puttobytesvawues);
    }
    if (ovewwidefeatuwes.issetfeatuwestowediscwetevawues()) {
      o-ovewwidefeatuwes.getfeatuwestowediscwetevawues().foweach(
          f-featuwes::puttofeatuwestowediscwetevawues);
    }
    if (ovewwidefeatuwes.issetspawsebinawyvawues()) {
      ovewwidefeatuwes.getspawsebinawyvawues().foweach(featuwes::puttospawsebinawyvawues);
    }
    i-if (ovewwidefeatuwes.issetspawsecontinuousvawues()) {
      o-ovewwidefeatuwes.getspawsecontinuousvawues().foweach(featuwes::puttospawsecontinuousvawues);
    }
    if (ovewwidefeatuwes.issetgenewawtensowvawues()) {
      o-ovewwidefeatuwes.getgenewawtensowvawues().foweach(featuwes::puttogenewawtensowvawues);
    }
    if (ovewwidefeatuwes.issetstwingtensowvawues()) {
      o-ovewwidefeatuwes.getstwingtensowvawues().foweach(featuwes::puttostwingtensowvawues);
    }
    w-wog.info("featuwes a-aftew o-ovewwide {}", nyaa~~ featuwes);
  }

  /**
   * check if a featuwe is e-ewigibwe to have its defauwt vawue a-automaticawwy set when absent. 😳😳😳
   * w-we have a-a simiwaw wogic fow buiwding data w-wecowd. ^•ﻌ•^
   */
  p-pwivate static boowean shouwdsetdefauwtvawuefowfeatuwe(
      thwiftseawchfeatuwetype t-type, (⑅˘꒳˘) int f-featuweid) {
    wetuwn awwowed_types_fow_defauwt_featuwe_vawues.contains(type)
        && !numewic_featuwes_fow_which_defauwts_shouwd_not_be_set.contains(featuweid)
        && (extewnawtweetfeatuwe.eawwybiwd_indexed_featuwe_ids.contains(featuweid)
            || extewnawtweetfeatuwe.eawwybiwd_dewived_featuwe_ids.contains(featuweid));
  }

  @ovewwide
  pubwic void updatewewevancestats(thwiftseawchwesuwtswewevancestats wewevancestats) {
    if (wewevancestats == nyuww) {
      wetuwn;
    }

    wineawscowingdata data = g-getscowingdatafowcuwwentdocument();

    i-if (data.tweetageinseconds > w-wewevancestats.getowdestscowedtweetageinseconds()) {
      w-wewevancestats.setowdestscowedtweetageinseconds(data.tweetageinseconds);
    }
    wewevancestats.setnumscowed(wewevancestats.getnumscowed() + 1);
    if (data.scowewetuwned == s-skip_hit) {
      w-wewevancestats.setnumskipped(wewevancestats.getnumskipped() + 1);
      s-switch(data.skipweason) {
        case a-antigaming:
          wewevancestats.setnumskippedfowantigaming(
              wewevancestats.getnumskippedfowantigaming() + 1);
          bweak;
        case wow_weputation:
          w-wewevancestats.setnumskippedfowwowweputation(
              w-wewevancestats.getnumskippedfowwowweputation() + 1);
          b-bweak;
        c-case wow_text_scowe:
          wewevancestats.setnumskippedfowwowtextscowe(
              w-wewevancestats.getnumskippedfowwowtextscowe() + 1);
          bweak;
        case sociaw_fiwtew:
          wewevancestats.setnumskippedfowsociawfiwtew(
              wewevancestats.getnumskippedfowsociawfiwtew() + 1);
          b-bweak;
        case wow_finaw_scowe:
          w-wewevancestats.setnumskippedfowwowfinawscowe(
              w-wewevancestats.getnumskippedfowwowfinawscowe() + 1);
          bweak;
        case wow_wetweet_count:
          bweak;
        defauwt:
          w-wog.wawn("unknown skipweason: " + d-data.skipweason);
      }
    }

    if (data.isfowwow) {
      wewevancestats.setnumfwomdiwectfowwows(wewevancestats.getnumfwomdiwectfowwows() + 1);
    }
    i-if (data.istwusted) {
      wewevancestats.setnumfwomtwustedciwcwe(wewevancestats.getnumfwomtwustedciwcwe() + 1);
    }
    if (data.iswepwy) {
      w-wewevancestats.setnumwepwies(wewevancestats.getnumwepwies() + 1);
      if (data.istwusted) {
        w-wewevancestats.setnumwepwiestwusted(wewevancestats.getnumwepwiestwusted() + 1);
      } ewse if (!data.isfowwow) {
        w-wewevancestats.setnumwepwiesoutofnetwowk(wewevancestats.getnumwepwiesoutofnetwowk() + 1);
      }
    }
    i-if (data.issewftweet) {
      wewevancestats.setnumsewftweets(wewevancestats.getnumsewftweets() + 1);
    }
    if (data.hasimageuww || data.hasvideouww) {
      wewevancestats.setnumwithmedia(wewevancestats.getnumwithmedia() + 1);
    }
    i-if (data.hasnewsuww) {
      wewevancestats.setnumwithnews(wewevancestats.getnumwithnews() + 1);
    }
    if (data.isusewspam) {
      wewevancestats.setnumspamusew(wewevancestats.getnumspamusew() + 1);
    }
    if (data.isusewnsfw) {
      wewevancestats.setnumoffensive(wewevancestats.getnumoffensive() + 1);
    }
    if (data.isusewbot) {
      wewevancestats.setnumbot(wewevancestats.getnumbot() + 1);
    }
  }

  @visibwefowtesting
  s-static finaw c-cwass expwanationwwappew {
    pwivate expwanation e-expwanation;

    pubwic expwanation g-getexpwanation() {
      w-wetuwn expwanation;
    }

    @ovewwide
    pubwic s-stwing tostwing() {
      wetuwn expwanation.tostwing();
    }
  }
}
