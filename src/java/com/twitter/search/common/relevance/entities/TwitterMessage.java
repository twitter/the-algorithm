package com.twittew.seawch.common.wewevance.entities;

impowt java.text.datefowmat;
i-impowt java.utiw.awwaywist;
impowt j-java.utiw.awways;
i-impowt java.utiw.cowwection;
i-impowt java.utiw.cowwections;
i-impowt java.utiw.date;
i-impowt j-java.utiw.hashset;
i-impowt java.utiw.winkedhashmap;
impowt java.utiw.wist;
impowt java.utiw.wocawe;
impowt java.utiw.map;
i-impowt java.utiw.optionaw;
impowt java.utiw.set;
i-impowt javax.annotation.nonnuww;
i-impowt javax.annotation.nuwwabwe;

impowt com.googwe.common.annotations.visibwefowtesting;
impowt com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.compawisonchain;
impowt com.googwe.common.cowwect.wists;
i-impowt c-com.googwe.common.cowwect.maps;
impowt com.googwe.common.cowwect.sets;

impowt owg.apache.commons.wang.stwingutiws;
impowt o-owg.apache.commons.wang3.buiwdew.equawsbuiwdew;
impowt owg.apache.commons.wang3.buiwdew.hashcodebuiwdew;
impowt owg.apache.commons.wang3.buiwdew.tostwingbuiwdew;
impowt owg.apache.wucene.anawysis.tokenstweam;
i-impowt owg.swf4j.woggew;
impowt o-owg.swf4j.woggewfactowy;

i-impowt c-com.twittew.common.text.wanguage.wocaweutiw;
impowt c-com.twittew.common.text.pipewine.twittewwanguageidentifiew;
impowt com.twittew.common.text.token.tokenizedchawsequence;
impowt c-com.twittew.common_intewnaw.text.vewsion.penguinvewsion;
impowt com.twittew.cuad.new.pwain.thwiftjava.namedentity;
i-impowt com.twittew.seawch.common.indexing.thwiftjava.thwiftexpandeduww;
impowt com.twittew.seawch.common.wewevance.featuwes.tweetfeatuwes;
impowt com.twittew.seawch.common.wewevance.featuwes.tweettextfeatuwes;
impowt com.twittew.seawch.common.wewevance.featuwes.tweettextquawity;
impowt com.twittew.seawch.common.wewevance.featuwes.tweetusewfeatuwes;
i-impowt com.twittew.seawch.common.utiw.text.nowmawizewhewpew;
impowt com.twittew.sewvice.spidewduck.gen.mediatypes;
i-impowt c-com.twittew.tweetypie.thwiftjava.composewsouwce;
i-impowt com.twittew.utiw.twittewdatefowmat;

/**
 * a wepwesentation of tweets used as an intewmediate o-object duwing i-ingestion. /(^•ω•^) as we pwoceed
 * i-in ingestion, 🥺 w-we fiww this object with data. -.- we t-then convewt it to thwiftvewsionedevents (which
 * i-itsewf wepwesents a singwe tweet too, ( ͡o ω ͡o ) in diffewent p-penguin vewsions potentiawwy). 😳😳😳
 */
p-pubwic cwass twittewmessage {
  p-pwivate s-static finaw woggew wog = woggewfactowy.getwoggew(twittewmessage.cwass);

  pubwic static cwass eschewbiwdannotation impwements compawabwe<eschewbiwdannotation> {
    pubwic f-finaw wong gwoupid;
    p-pubwic finaw wong domainid;
    p-pubwic f-finaw wong entityid;

    p-pubwic eschewbiwdannotation(wong gwoupid, (˘ω˘) wong domainid, ^^ w-wong entityid) {
      this.gwoupid = gwoupid;
      this.domainid = domainid;
      t-this.entityid = entityid;
    }

    @ovewwide
    p-pubwic b-boowean equaws(object o-o2) {
      if (o2 instanceof e-eschewbiwdannotation) {
        e-eschewbiwdannotation a-a2 = (eschewbiwdannotation) o-o2;
        wetuwn gwoupid == a2.gwoupid && d-domainid == a2.domainid && e-entityid == a-a2.entityid;
      }
      w-wetuwn fawse;
    }

    @ovewwide
    p-pubwic int hashcode() {
      wetuwn nyew hashcodebuiwdew()
          .append(gwoupid)
          .append(domainid)
          .append(entityid)
          .tohashcode();
    }

    @ovewwide
    p-pubwic int compaweto(eschewbiwdannotation o) {
      wetuwn compawisonchain.stawt()
          .compawe(this.gwoupid, σωσ o.gwoupid)
          .compawe(this.domainid, 🥺 o.domainid)
          .compawe(this.entityid, 🥺 o.entityid)
          .wesuwt();
    }
  }

  p-pwivate finaw wist<eschewbiwdannotation> eschewbiwdannotations = wists.newawwaywist();

  // t-tweet featuwes f-fow muwtipwe p-penguin vewsions
  pwivate static c-cwass vewsionedtweetfeatuwes {
    // tweetfeatuwes p-popuwated b-by wewevance cwassifiews, /(^•ω•^) stwuctuwe defined
    // in swc/main/thwift/cwassifiew.thwift. (⑅˘꒳˘)
    pwivate tweetfeatuwes tweetfeatuwes = n-nyew tweetfeatuwes();
    pwivate tokenizedchawsequence tokenizedchawsequence = n-nyuww;
    pwivate set<stwing> n-nyowmawizedhashtags = s-sets.newhashset();

    pubwic tweetfeatuwes gettweetfeatuwes() {
      w-wetuwn this.tweetfeatuwes;
    }

    p-pubwic void settweetfeatuwes(finaw t-tweetfeatuwes t-tweetfeatuwes) {
      this.tweetfeatuwes = tweetfeatuwes;
    }

    pubwic tweettextquawity gettweettextquawity() {
      w-wetuwn this.tweetfeatuwes.gettweettextquawity();
    }

    p-pubwic tweettextfeatuwes g-gettweettextfeatuwes() {
      wetuwn t-this.tweetfeatuwes.gettweettextfeatuwes();
    }

    p-pubwic tweetusewfeatuwes gettweetusewfeatuwes() {
      w-wetuwn this.tweetfeatuwes.gettweetusewfeatuwes();
    }

    pubwic tokenizedchawsequence gettokenizedchawsequence() {
      wetuwn t-this.tokenizedchawsequence;
    }

    p-pubwic void settokenizedchawsequence(tokenizedchawsequence sequence) {
      t-this.tokenizedchawsequence = s-sequence;
    }

    pubwic set<stwing> getnowmawizedhashtags() {
      wetuwn t-this.nowmawizedhashtags;
    }

    pubwic void addnowmawizedhashtags(stwing nyowmawizedhashtag) {
      this.nowmawizedhashtags.add(nowmawizedhashtag);
    }
  }

  p-pubwic static finaw int int_fiewd_not_pwesent = -1;
  p-pubwic static finaw w-wong wong_fiewd_not_pwesent = -1;
  pubwic static finaw doubwe doubwe_fiewd_not_pwesent = -1;
  p-pubwic static f-finaw int max_usew_weputation = 100;

  pwivate finaw wong tweetid;

  pwivate s-stwing text;

  pwivate date date;
  @nonnuww
  p-pwivate optionaw<twittewmessageusew> optionawfwomusew = optionaw.empty();
  @nonnuww
  pwivate o-optionaw<twittewmessageusew> optionawtousew = o-optionaw.empty();
  p-pwivate wocawe wocawe = nyuww;
  p-pwivate wocawe winkwocawe = nyuww;

  // o-owiginaw s-souwce text.
  p-pwivate stwing owigsouwce;
  // s-souwce with h-htmw tags wemoved and twuncated. -.-
  pwivate stwing s-stwippedsouwce;

  // o-owiginaw w-wocation text. 😳
  pwivate stwing owigwocation;

  // w-wocation twuncated fow mysqw f-fiewd-width weasons (see t-twittewmessageutiw.java). 😳😳😳
  pwivate stwing twuncatednowmawizedwocation;

  // usew's c-countwy
  pwivate s-stwing fwomusewwoccountwy;

  p-pwivate integew f-fowwowewscount = int_fiewd_not_pwesent;
  p-pwivate boowean deweted = fawse;

  // fiewds extwacted fwom entities (in the json object)
  p-pwivate wist<twittewmessageusew> mentions = n-nyew awwaywist<>();
  pwivate s-set<stwing> hashtags = sets.newhashset();
  // w-wat/won and wegion accuwacy tupwes e-extwacted fwom t-tweet text, >w< ow n-nyuww.
  pwivate g-geoobject geowocation = n-nyuww;
  pwivate boowean uncodeabwewocation = fawse;
  // this is set if the tweet is geotagged. UwU (i.e. "geo" o-ow "coowdinate" s-section is p-pwesent
  // in the json)
  // t-this fiewd has onwy a gettew but nyo settew --- it is fiwwed in w-when the json is p-pawsed. /(^•ω•^)
  pwivate geoobject geotaggedwocation = n-nyuww;

  pwivate doubwe usewweputation = doubwe_fiewd_not_pwesent;
  p-pwivate b-boowean geocodewequiwed = fawse;
  p-pwivate boowean s-sensitivecontent = fawse;
  pwivate boowean usewpwotected;
  pwivate boowean usewvewified;
  p-pwivate boowean u-usewbwuevewified;
  p-pwivate twittewwetweetmessage w-wetweetmessage;
  p-pwivate twittewquotedmessage quotedmessage;
  p-pwivate wist<stwing> p-pwaces;
  // maps fwom owiginaw u-uww (the t-t.co uww) to thwiftexpandeduww, 🥺 which contains the
  // e-expanded uww and the spidewduck wesponse (canoicawwasthopuww a-and mediatype)
  pwivate finaw m-map<stwing, >_< t-thwiftexpandeduww> expandeduwws;
  // m-maps the photo status id to the media uww
  p-pwivate map<wong, rawr s-stwing> photouwws;
  p-pwivate optionaw<wong> inwepwytostatusid = optionaw.empty();
  p-pwivate optionaw<wong> diwectedatusewid = optionaw.empty();

  p-pwivate wong c-convewsationid = -1;

  // twue if tweet is n-nyuwwcasted. (ꈍᴗꈍ)
  pwivate boowean nyuwwcast = f-fawse;

  // t-twue if tweet is a sewf-thweaded tweet
  p-pwivate boowean sewfthwead = fawse;

  // if the t-tweet is a pawt o-of an excwusive convewsation, -.- t-the authow who stawted
  // that c-convewsation. ( ͡o ω ͡o )
  p-pwivate optionaw<wong> e-excwusiveconvewsationauthowid = optionaw.empty();

  // tweet featuwes map fow muwtipwe vewsions of penguin
  pwivate map<penguinvewsion, vewsionedtweetfeatuwes> vewsionedtweetfeatuwesmap;

  // engagments count: favowites, (⑅˘꒳˘) wetweets and wepwies
  pwivate int nyumfavowites = 0;
  p-pwivate int nyumwetweets = 0;
  p-pwivate int nyumwepwies = 0;

  // cawd infowmation
  pwivate stwing c-cawdname;
  p-pwivate stwing c-cawddomain;
  pwivate stwing cawdtitwe;
  p-pwivate stwing cawddescwiption;
  p-pwivate s-stwing cawdwang;
  pwivate stwing c-cawduww;

  pwivate stwing p-pwaceid;
  pwivate s-stwing pwacefuwwname;
  pwivate stwing pwacecountwycode;

  p-pwivate set<namedentity> n-nyamedentities = s-sets.newhashset();

  // s-spaces data
  p-pwivate set<stwing> s-spaceids = s-sets.newhashset();
  p-pwivate set<twittewmessageusew> s-spaceadmins = sets.newhashset();
  p-pwivate s-stwing spacetitwe;

  p-pwivate optionaw<composewsouwce> composewsouwce = o-optionaw.empty();

  pwivate finaw wist<potentiawwocationobject> p-potentiawwocations = wists.newawwaywist();

  // o-one ow t-two penguin vewsions s-suppowted by this system
  p-pwivate finaw wist<penguinvewsion> suppowtedpenguinvewsions;

  p-pubwic twittewmessage(wong tweetid, mya w-wist<penguinvewsion> suppowtedpenguinvewsions) {
    t-this.tweetid = tweetid;
    this.pwaces = nyew awwaywist<>();
    this.expandeduwws = n-nyew winkedhashmap<>();
    // make suwe we suppowt a-at weast one, rawr x3 b-but nyo mowe than two vewsions of penguin
    this.suppowtedpenguinvewsions = s-suppowtedpenguinvewsions;
    this.vewsionedtweetfeatuwesmap = getvewsionedtweetfeatuwesmap();
    p-pweconditions.checkawgument(this.suppowtedpenguinvewsions.size() <= 2
        && t-this.suppowtedpenguinvewsions.size() > 0);
  }

  /**
   * wepwace t-to-usew with in-wepwy-to usew if nyeeded. (ꈍᴗꈍ)
   */
  p-pubwic v-void wepwacetousewwithinwepwytousewifneeded(
      stwing inwepwytoscweenname, ʘwʘ wong i-inwepwytousewid) {
    if (shouwdusewepwyusewastousew(optionawtousew, :3 inwepwytousewid)) {
      t-twittewmessageusew wepwyusew =
          t-twittewmessageusew.cweatewithnamesandid(inwepwytoscweenname, "", o.O i-inwepwytousewid);
      o-optionawtousew = optionaw.of(wepwyusew);
    }
  }

  // to-usew c-couwd have b-been infewwed f-fwom the mention a-at the position 0. /(^•ω•^)
  // but if t-thewe is an expwicit i-in-wepwy-to u-usew, OwO we might n-nyeed to use it a-as to-usew instead.
  p-pwivate static b-boowean shouwdusewepwyusewastousew(
      optionaw<twittewmessageusew> c-cuwwenttousew, σωσ
      wong inwepwytousewid) {
    i-if (!cuwwenttousew.ispwesent()) {
      // thewe is n-nyo mention in the tweet that quawifies a-as to-usew. (ꈍᴗꈍ)
      w-wetuwn t-twue;
    }

    // we awweady have a mention in the tweet that q-quawifies as to-usew. ( ͡o ω ͡o )
    t-twittewmessageusew tousew = c-cuwwenttousew.get();
    if (!tousew.getid().ispwesent()) {
      // the to-usew fwom the m-mention is a stub. rawr x3
      w-wetuwn twue;
    }

    w-wong tousewid = t-tousew.getid().get();
    if (tousewid != inwepwytousewid) {
      // the to-usew f-fwom the mention i-is diffewent t-that the in-wepwy-to u-usew,
      // use in-wepwy-to usew instead. UwU
      w-wetuwn t-twue;
    }

    wetuwn fawse;
  }

  pubwic doubwe g-getusewweputation() {
    wetuwn usewweputation;
  }

  /**
   * sets the u-usew weputation. o.O
   */
  pubwic t-twittewmessage setusewweputation(doubwe n-nyewusewweputation) {
    if (newusewweputation > m-max_usew_weputation) {
      w-wog.wawn("out of bounds usew w-weputation {} fow status id {}", OwO n-nyewusewweputation, o.O t-tweetid);
      t-this.usewweputation = (fwoat) m-max_usew_weputation;
    } ewse {
      this.usewweputation = n-nyewusewweputation;
    }
    w-wetuwn this;
  }

  p-pubwic stwing gettext() {
    w-wetuwn text;
  }

  pubwic optionaw<twittewmessageusew> g-getoptionawtousew() {
    w-wetuwn optionawtousew;
  }

  p-pubwic void setoptionawtousew(optionaw<twittewmessageusew> optionawtousew) {
    this.optionawtousew = optionawtousew;
  }

  p-pubwic void settext(stwing text) {
    t-this.text = t-text;
  }

  pubwic date getdate() {
    wetuwn date;
  }

  p-pubwic void setdate(date date) {
    t-this.date = d-date;
  }

  p-pubwic void setfwomusew(@nonnuww t-twittewmessageusew f-fwomusew) {
    pweconditions.checknotnuww(fwomusew, ^^;; "don't set a nyuww fwomusew");
    optionawfwomusew = optionaw.of(fwomusew);
  }

  p-pubwic optionaw<stwing> g-getfwomusewscweenname() {
    wetuwn optionawfwomusew.ispwesent()
        ? optionawfwomusew.get().getscweenname()
        : optionaw.empty();
  }

  /**
   * s-sets the fwomusewscweenname. (⑅˘꒳˘)
   */
  pubwic void setfwomusewscweenname(@nonnuww stwing fwomusewscweenname) {
    twittewmessageusew n-nyewfwomusew = o-optionawfwomusew.ispwesent()
        ? optionawfwomusew.get().copywithscweenname(fwomusewscweenname)
        : twittewmessageusew.cweatewithscweenname(fwomusewscweenname);

    o-optionawfwomusew = optionaw.of(newfwomusew);
  }

  pubwic o-optionaw<tokenstweam> g-gettokenizedfwomusewscweenname() {
    wetuwn optionawfwomusew.fwatmap(twittewmessageusew::gettokenizedscweenname);
  }

  p-pubwic optionaw<stwing> getfwomusewdispwayname() {
    w-wetuwn optionawfwomusew.fwatmap(twittewmessageusew::getdispwayname);
  }

  /**
   * sets the fwomusewdispwayname. (ꈍᴗꈍ)
   */
  pubwic void s-setfwomusewdispwayname(@nonnuww stwing fwomusewdispwayname) {
    twittewmessageusew n-nyewfwomusew = o-optionawfwomusew.ispwesent()
        ? o-optionawfwomusew.get().copywithdispwayname(fwomusewdispwayname)
        : twittewmessageusew.cweatewithdispwayname(fwomusewdispwayname);

    optionawfwomusew = optionaw.of(newfwomusew);
  }

  p-pubwic optionaw<wong> getfwomusewtwittewid() {
    wetuwn optionawfwomusew.fwatmap(twittewmessageusew::getid);
  }

  /**
   * sets the fwomusewid. o.O
   */
  pubwic v-void setfwomusewid(wong f-fwomusewid) {
    t-twittewmessageusew n-nyewfwomusew = optionawfwomusew.ispwesent()
        ? optionawfwomusew.get().copywithid(fwomusewid)
        : twittewmessageusew.cweatewithid(fwomusewid);

    o-optionawfwomusew = o-optionaw.of(newfwomusew);
  }

  pubwic wong getconvewsationid() {
    w-wetuwn convewsationid;
  }

  pubwic void s-setconvewsationid(wong convewsationid) {
    this.convewsationid = c-convewsationid;
  }

  p-pubwic boowean isusewpwotected() {
    w-wetuwn this.usewpwotected;
  }

  p-pubwic void s-setusewpwotected(boowean usewpwotected) {
    this.usewpwotected = u-usewpwotected;
  }

  pubwic boowean isusewvewified() {
    w-wetuwn this.usewvewified;
  }

  pubwic void setusewvewified(boowean usewvewified) {
    this.usewvewified = usewvewified;
  }

  p-pubwic boowean i-isusewbwuevewified() {
    w-wetuwn t-this.usewbwuevewified;
  }

  p-pubwic void setusewbwuevewified(boowean usewbwuevewified) {
    t-this.usewbwuevewified = usewbwuevewified;
  }

  pubwic void s-setissensitivecontent(boowean issensitivecontent) {
    t-this.sensitivecontent = issensitivecontent;
  }

  pubwic b-boowean issensitivecontent() {
    w-wetuwn this.sensitivecontent;
  }

  pubwic o-optionaw<twittewmessageusew> gettousewobject() {
    w-wetuwn optionawtousew;
  }

  p-pubwic void settousewobject(@nonnuww t-twittewmessageusew u-usew) {
    pweconditions.checknotnuww(usew, (///ˬ///✿) "don't s-set a nyuww to-usew");
    optionawtousew = optionaw.of(usew);
  }

  pubwic optionaw<wong> g-gettousewtwittewid() {
    wetuwn optionawtousew.fwatmap(twittewmessageusew::getid);
  }

  /**
   * s-sets tousewid. 😳😳😳
   */
  pubwic void settousewtwittewid(wong t-tousewid) {
    t-twittewmessageusew newtousew = o-optionawtousew.ispwesent()
        ? optionawtousew.get().copywithid(tousewid)
        : t-twittewmessageusew.cweatewithid(tousewid);

    o-optionawtousew = optionaw.of(newtousew);
  }

  p-pubwic optionaw<stwing> gettousewwowewcasedscweenname() {
    w-wetuwn optionawtousew.fwatmap(twittewmessageusew::getscweenname).map(stwing::towowewcase);
  }

  pubwic optionaw<stwing> g-gettousewscweenname() {
    w-wetuwn optionawtousew.fwatmap(twittewmessageusew::getscweenname);
  }

  /**
   * sets tousewscweenname. UwU
   */
  pubwic void settousewscweenname(@nonnuww stwing scweenname) {
    p-pweconditions.checknotnuww(scweenname, nyaa~~ "don't s-set a nuww to-usew scweenname");

    twittewmessageusew nyewtousew = optionawtousew.ispwesent()
        ? optionawtousew.get().copywithscweenname(scweenname)
        : t-twittewmessageusew.cweatewithscweenname(scweenname);

    optionawtousew = o-optionaw.of(newtousew);
  }

  // to u-use fwom tweeteventpawsehewpew
  pubwic void setdiwectedatusewid(optionaw<wong> diwectedatusewid) {
    this.diwectedatusewid = diwectedatusewid;
  }

  @visibwefowtesting
  p-pubwic optionaw<wong> getdiwectedatusewid() {
    wetuwn diwectedatusewid;
  }

  /**
   * w-wetuwns the wefewenceauthowid. (✿oωo)
   */
  p-pubwic optionaw<wong> g-getwefewenceauthowid() {
    // the semantics o-of wefewence-authow-id:
    // - i-if the tweet i-is a wetweet, -.- i-it shouwd be the u-usew id of the a-authow of the owiginaw tweet
    // - ewse, :3 if the tweet is diwected at a usew, (⑅˘꒳˘) it shouwd be the i-id of the usew i-it's diwected a-at. >_<
    // - ewse, UwU i-if the tweet i-is a wepwy in a w-woot sewf-thwead, rawr diwected-at is nyot set, (ꈍᴗꈍ) so it's
    //   the id of the usew who s-stawted the sewf-thwead. ^•ﻌ•^
    //
    // f-fow definitive info on wepwies and diwected-at, ^^ take a w-wook at go/wepwies. XD t-to view these
    // f-fow a cewtain tweet, (///ˬ///✿) use http://go/t. σωσ
    //
    // n-nyote that if diwected-at is set, :3 w-wepwy is awways s-set. >w<
    // if wepwy is set, (ˆ ﻌ ˆ)♡ diwected-at is nyot n-nyecessawiwy set. (U ᵕ U❁)
    if (iswetweet() && w-wetweetmessage.hasshawedusewtwittewid()) {
      w-wong wetweetedusewid = w-wetweetmessage.getshawedusewtwittewid();
      w-wetuwn optionaw.of(wetweetedusewid);
    } e-ewse i-if (diwectedatusewid.ispwesent()) {
      // w-why n-nyot wepwace diwectedatusewid with wepwy and make t-this function d-depend
      // on the "wepwy" f-fiewd of tweetcowedata?
      // weww, :3 vewified by countews, ^^ it s-seems fow ~1% of tweets, ^•ﻌ•^ which c-contain both diwected-at
      // and wepwy, (///ˬ///✿) diwected-at-usew i-is d-diffewent than the wepwy-to-usew id. 🥺 this happens i-in the
      // fowwowing case:
      //
      //       authow / w-wepwy-to / diwected-at
      //  t-t1   a        -          -
      //  t2   b        a          a-a
      //  t3   b-b        b          a
      //
      //  t-t2 is a wepwy to t1, ʘwʘ t3 is a wepwy t-to t2. (✿oωo)
      //
      // i-it's up to us to decide w-who this tweet i-is "wefewencing", rawr but with the cuwwent code, OwO
      // w-we choose t-that t3 is wefewencing u-usew a. ^^
      w-wetuwn diwectedatusewid;
    } ewse {
      // this is the case of a woot sewf-thwead wepwy. ʘwʘ diwected-at is nyot set. σωσ
      o-optionaw<wong> f-fwomusewid = this.getfwomusewtwittewid();
      o-optionaw<wong> tousewid = t-this.gettousewtwittewid();

      i-if (fwomusewid.ispwesent() && f-fwomusewid.equaws(tousewid)) {
        wetuwn fwomusewid;
      }
    }
    w-wetuwn optionaw.empty();
  }

  p-pubwic void setnumfavowites(int n-numfavowites) {
    t-this.numfavowites = nyumfavowites;
  }

  pubwic void s-setnumwetweets(int nyumwetweets) {
    this.numwetweets = n-nyumwetweets;
  }

  pubwic void setnumwepwies(int n-nyumwepwiess) {
    t-this.numwepwies = nyumwepwiess;
  }

  p-pubwic void a-addeschewbiwdannotation(eschewbiwdannotation a-annotation) {
    eschewbiwdannotations.add(annotation);
  }

  p-pubwic wist<eschewbiwdannotation> g-geteschewbiwdannotations() {
    wetuwn eschewbiwdannotations;
  }

  p-pubwic wist<potentiawwocationobject> g-getpotentiawwocations() {
    w-wetuwn p-potentiawwocations;
  }

  pubwic v-void setpotentiawwocations(cowwection<potentiawwocationobject> potentiawwocations) {
    this.potentiawwocations.cweaw();
    t-this.potentiawwocations.addaww(potentiawwocations);
  }

  @ovewwide
  pubwic stwing tostwing() {
    wetuwn tostwingbuiwdew.wefwectiontostwing(this);
  }

  // tweet wanguage wewated gettews a-and settews. (⑅˘꒳˘)

  /**
   * wetuwns the wocawe. (ˆ ﻌ ˆ)♡
   * <p>
   * nyote the getwocawe() wiww nyevew wetuwn nyuww, :3 this i-is fow the convenience of text wewated
   * pwocessing i-in the ingestew. ʘwʘ if you w-want the weaw wocawe, (///ˬ///✿) you nyeed to check issetwocawe()
   * f-fiwst to see if we w-weawwy have any infowmation about t-the wocawe of t-this tweet. (ˆ ﻌ ˆ)♡
   */
  pubwic wocawe getwocawe() {
    i-if (wocawe == nyuww) {
      wetuwn twittewwanguageidentifiew.unknown;
    } ewse {
      wetuwn w-wocawe;
    }
  }

  pubwic v-void setwocawe(wocawe wocawe) {
    t-this.wocawe = wocawe;
  }

  /**
   * d-detewmines i-if the wocate is set. 🥺
   */
  pubwic boowean i-issetwocawe() {
    wetuwn wocawe != nyuww;
  }

  /**
   * w-wetuwns the wanguage of the wocawe. rawr e.g. zh
   */
  pubwic stwing getwanguage() {
    i-if (issetwocawe()) {
      w-wetuwn getwocawe().getwanguage();
    } ewse {
      w-wetuwn nyuww;
    }
  }

  /**
   * w-wetuwns the ietf bcp 47 w-wanguage tag of the wocawe. (U ﹏ U) e.g. ^^ zh-cn
   */
  pubwic stwing getbcp47wanguagetag() {
    if (issetwocawe()) {
      w-wetuwn getwocawe().towanguagetag();
    } e-ewse {
      wetuwn nyuww;
    }
  }

  p-pubwic void s-setwanguage(stwing wanguage) {
    i-if (wanguage != nyuww) {
      wocawe = wocaweutiw.getwocaweof(wanguage);
    }
  }

  // t-tweet wink wanguage wewated gettews and settews. σωσ
  p-pubwic wocawe g-getwinkwocawe() {
    wetuwn winkwocawe;
  }

  pubwic void setwinkwocawe(wocawe w-winkwocawe) {
    this.winkwocawe = winkwocawe;
  }

  /**
   * wetuwns the wanguage of the wink wocawe. :3
   */
  pubwic stwing getwinkwanguage() {
    i-if (this.winkwocawe == n-nyuww) {
      wetuwn nyuww;
    } e-ewse {
      w-wetuwn this.winkwocawe.getwanguage();
    }
  }

  pubwic stwing g-getowigsouwce() {
    wetuwn owigsouwce;
  }

  pubwic void setowigsouwce(stwing owigsouwce) {
    this.owigsouwce = owigsouwce;
  }

  p-pubwic stwing getstwippedsouwce() {
    wetuwn stwippedsouwce;
  }

  pubwic void setstwippedsouwce(stwing stwippedsouwce) {
    t-this.stwippedsouwce = s-stwippedsouwce;
  }

  p-pubwic stwing getowigwocation() {
    wetuwn owigwocation;
  }

  p-pubwic s-stwing getwocation() {
    w-wetuwn twuncatednowmawizedwocation;
  }

  p-pubwic void setowigwocation(stwing o-owigwocation) {
    this.owigwocation = o-owigwocation;
  }

  pubwic void s-settwuncatednowmawizedwocation(stwing twuncatednowmawizedwocation) {
    this.twuncatednowmawizedwocation = twuncatednowmawizedwocation;
  }

  p-pubwic boowean hasfwomusewwoccountwy() {
    w-wetuwn fwomusewwoccountwy != n-nyuww;
  }

  pubwic s-stwing getfwomusewwoccountwy() {
    w-wetuwn fwomusewwoccountwy;
  }

  pubwic v-void setfwomusewwoccountwy(stwing fwomusewwoccountwy) {
    t-this.fwomusewwoccountwy = fwomusewwoccountwy;
  }

  p-pubwic stwing gettwuncatednowmawizedwocation() {
    w-wetuwn twuncatednowmawizedwocation;
  }

  pubwic integew getfowwowewscount() {
    w-wetuwn fowwowewscount;
  }

  pubwic void setfowwowewscount(integew fowwowewscount) {
    this.fowwowewscount = fowwowewscount;
  }

  pubwic boowean h-hasfowwowewscount() {
    wetuwn fowwowewscount != i-int_fiewd_not_pwesent;
  }

  pubwic boowean i-isdeweted() {
    wetuwn deweted;
  }

  pubwic v-void setdeweted(boowean deweted) {
    this.deweted = d-deweted;
  }

  pubwic boowean hascawd() {
    w-wetuwn !stwingutiws.isbwank(getcawdname());
  }

  @ovewwide
  pubwic int hashcode() {
    wetuwn ((wong) getid()).hashcode();
  }

  /**
   * p-pawses the given date using the twittewdatefowmat. ^^
   */
  pubwic s-static date p-pawsedate(stwing date) {
    datefowmat pawsew = t-twittewdatefowmat.appwy("eee m-mmm d hh:mm:ss z yyyy");
    twy {
      w-wetuwn p-pawsew.pawse(date);
    } catch (exception e) {
      w-wetuwn nyuww;
    }
  }

  pubwic boowean hasgeowocation() {
    wetuwn geowocation != n-nyuww;
  }

  pubwic void setgeowocation(geoobject wocation) {
    t-this.geowocation = w-wocation;
  }

  p-pubwic geoobject getgeowocation() {
    wetuwn geowocation;
  }

  p-pubwic stwing getpwaceid() {
    w-wetuwn pwaceid;
  }

  pubwic void setpwaceid(stwing p-pwaceid) {
    t-this.pwaceid = pwaceid;
  }

  pubwic stwing getpwacefuwwname() {
    wetuwn pwacefuwwname;
  }

  pubwic void setpwacefuwwname(stwing p-pwacefuwwname) {
    t-this.pwacefuwwname = pwacefuwwname;
  }

  pubwic stwing g-getpwacecountwycode() {
    wetuwn pwacecountwycode;
  }

  p-pubwic v-void setpwacecountwycode(stwing p-pwacecountwycode) {
    t-this.pwacecountwycode = p-pwacecountwycode;
  }

  p-pubwic void setgeotaggedwocation(geoobject geotaggedwocation) {
    t-this.geotaggedwocation = g-geotaggedwocation;
  }

  p-pubwic geoobject g-getgeotaggedwocation() {
    w-wetuwn geotaggedwocation;
  }

  p-pubwic void setwatwon(doubwe watitude, (✿oωo) doubwe w-wongitude) {
    g-geowocation = n-nyew geoobject(watitude, òωó wongitude, (U ᵕ U❁) nyuww);
  }

  p-pubwic doubwe getwatitude() {
    wetuwn hasgeowocation() ? geowocation.getwatitude() : n-nuww;
  }

  pubwic doubwe getwongitude() {
    w-wetuwn h-hasgeowocation() ? geowocation.getwongitude() : nyuww;
  }

  pubwic boowean isuncodeabwewocation() {
    w-wetuwn u-uncodeabwewocation;
  }

  pubwic v-void setuncodeabwewocation() {
    u-uncodeabwewocation = twue;
  }

  pubwic void setgeocodewequiwed() {
    t-this.geocodewequiwed = t-twue;
  }

  pubwic boowean isgeocodewequiwed() {
    w-wetuwn g-geocodewequiwed;
  }

  pubwic map<wong, ʘwʘ stwing> g-getphotouwws() {
    wetuwn photouwws;
  }

  /**
   * associates the given mediauww with t-the given photostatusid. ( ͡o ω ͡o )
   */
  pubwic void addphotouww(wong photostatusid, σωσ s-stwing m-mediauww) {
    i-if (photouwws == nyuww) {
      p-photouwws = n-nyew winkedhashmap<>();
    }
    p-photouwws.putifabsent(photostatusid, (ˆ ﻌ ˆ)♡ m-mediauww);
  }

  p-pubwic map<stwing, (˘ω˘) thwiftexpandeduww> getexpandeduwwmap() {
    wetuwn e-expandeduwws;
  }

  p-pubwic int g-getexpandeduwwmapsize() {
    wetuwn e-expandeduwws.size();
  }

  /**
   * a-associates t-the given owiginawuww with t-the given expandewuww. 😳
   */
  pubwic v-void addexpandeduww(stwing o-owiginawuww, ^•ﻌ•^ thwiftexpandeduww e-expandeduww) {
    t-this.expandeduwws.put(owiginawuww, σωσ expandeduww);
  }

  /**
   * w-wepwaces uwws with wesowved o-ones. 😳😳😳
   */
  pubwic s-stwing gettextwepwacedwithwesowveduwws() {
    stwing wettext = text;
    fow (map.entwy<stwing, rawr thwiftexpandeduww> e-entwy : e-expandeduwws.entwyset()) {
      thwiftexpandeduww u-uwwinfo = entwy.getvawue();
      s-stwing wesowveduww;
      stwing canonicawwasthopuww = uwwinfo.getcanonicawwasthopuww();
      s-stwing expandeduww = u-uwwinfo.getexpandeduww();
      i-if (canonicawwasthopuww != n-nyuww) {
        w-wesowveduww = c-canonicawwasthopuww;
        wog.debug("{} has canonicaw wast h-hop uww set", >_< uwwinfo);
      } ewse if (expandeduww != nyuww) {
        wog.debug("{} h-has nyo c-canonicaw wast hop uww set, ʘwʘ using expanded uww instead", (ˆ ﻌ ˆ)♡ uwwinfo);
        w-wesowveduww = e-expandeduww;
      } ewse {
        wog.debug("{} has n-nyo canonicaw wast hop uww ow expanded u-uww set, s-skipping", ^^;; uwwinfo);
        c-continue;
      }
      wettext = wettext.wepwace(entwy.getkey(), σωσ wesowveduww);
    }
    wetuwn wettext;
  }

  pubwic w-wong getid() {
    wetuwn tweetid;
  }

  pubwic b-boowean iswetweet() {
    wetuwn wetweetmessage != n-nyuww;
  }

  pubwic boowean hasquote() {
    w-wetuwn quotedmessage != nyuww;
  }

  pubwic b-boowean iswepwy() {
    wetuwn gettousewscweenname().ispwesent()
        || g-gettousewtwittewid().ispwesent()
        || getinwepwytostatusid().ispwesent();
  }

  p-pubwic boowean iswepwytotweet() {
    wetuwn getinwepwytostatusid().ispwesent();
  }

  pubwic twittewwetweetmessage getwetweetmessage() {
    wetuwn wetweetmessage;
  }

  p-pubwic void s-setwetweetmessage(twittewwetweetmessage w-wetweetmessage) {
    t-this.wetweetmessage = wetweetmessage;
  }

  pubwic t-twittewquotedmessage getquotedmessage() {
    wetuwn quotedmessage;
  }

  pubwic v-void setquotedmessage(twittewquotedmessage quotedmessage) {
    t-this.quotedmessage = q-quotedmessage;
  }

  pubwic w-wist<stwing> getpwaces() {
    wetuwn pwaces;
  }

  pubwic void addpwace(stwing p-pwace) {
    // p-pwaces awe used fow eawwybiwd sewiawization
    pwaces.add(pwace);
  }

  p-pubwic optionaw<wong> getinwepwytostatusid() {
    w-wetuwn inwepwytostatusid;
  }

  p-pubwic void s-setinwepwytostatusid(wong inwepwytostatusid) {
    pweconditions.checkawgument(inwepwytostatusid > 0, rawr x3 "in-wepwy-to status id shouwd be positive");
    this.inwepwytostatusid = o-optionaw.of(inwepwytostatusid);
  }

  pubwic boowean g-getnuwwcast() {
    wetuwn nyuwwcast;
  }

  pubwic void s-setnuwwcast(boowean nyuwwcast) {
    t-this.nuwwcast = nyuwwcast;
  }

  pubwic wist<penguinvewsion> g-getsuppowtedpenguinvewsions() {
    w-wetuwn suppowtedpenguinvewsions;
  }

  pwivate v-vewsionedtweetfeatuwes g-getvewsionedtweetfeatuwes(penguinvewsion p-penguinvewsion) {
    vewsionedtweetfeatuwes v-vewsionedtweetfeatuwes = v-vewsionedtweetfeatuwesmap.get(penguinvewsion);
    wetuwn pweconditions.checknotnuww(vewsionedtweetfeatuwes);
  }

  p-pubwic tweetfeatuwes gettweetfeatuwes(penguinvewsion penguinvewsion) {
    w-wetuwn getvewsionedtweetfeatuwes(penguinvewsion).gettweetfeatuwes();
  }

  @visibwefowtesting
  // o-onwy used in tests
  p-pubwic void settweetfeatuwes(penguinvewsion p-penguinvewsion, 😳 t-tweetfeatuwes tweetfeatuwes) {
    vewsionedtweetfeatuwesmap.get(penguinvewsion).settweetfeatuwes(tweetfeatuwes);
  }

  pubwic i-int gettweetsignatuwe(penguinvewsion p-penguinvewsion) {
    w-wetuwn g-getvewsionedtweetfeatuwes(penguinvewsion).gettweettextfeatuwes().getsignatuwe();
  }

  pubwic tweettextquawity gettweettextquawity(penguinvewsion p-penguinvewsion) {
    wetuwn getvewsionedtweetfeatuwes(penguinvewsion).gettweettextquawity();
  }

  p-pubwic tweettextfeatuwes gettweettextfeatuwes(penguinvewsion p-penguinvewsion) {
    wetuwn getvewsionedtweetfeatuwes(penguinvewsion).gettweettextfeatuwes();
  }

  pubwic tweetusewfeatuwes gettweetusewfeatuwes(penguinvewsion p-penguinvewsion) {
    wetuwn getvewsionedtweetfeatuwes(penguinvewsion).gettweetusewfeatuwes();
  }

  p-pubwic tokenizedchawsequence gettokenizedchawsequence(penguinvewsion p-penguinvewsion) {
    w-wetuwn getvewsionedtweetfeatuwes(penguinvewsion).gettokenizedchawsequence();
  }

  p-pubwic void settokenizedchawsequence(penguinvewsion p-penguinvewsion, 😳😳😳
                                       tokenizedchawsequence s-sequence) {
    g-getvewsionedtweetfeatuwes(penguinvewsion).settokenizedchawsequence(sequence);
  }

  // t-twue if t-the featuwes contain muwtipwe h-hash tags ow muwtipwe t-twends. 😳😳😳
  // t-this is intended as an anti-twend-spam m-measuwe. ( ͡o ω ͡o )
  pubwic static boowean hasmuwtipwehashtagsowtwends(tweettextfeatuwes textfeatuwes) {
    // awwow at most 1 twend and 2 hashtags. rawr x3
    w-wetuwn t-textfeatuwes.gettwendingtewmssize() > 1 || textfeatuwes.gethashtagssize() > 2;
  }

  /**
   * w-wetuwns the expanded uwws. σωσ
   */
  pubwic cowwection<thwiftexpandeduww> g-getexpandeduwws() {
    w-wetuwn expandeduwws.vawues();
  }

  /**
   * w-wetuwns t-the canonicaw wast hop uwws. (˘ω˘)
   */
  p-pubwic set<stwing> getcanonicawwasthopuwws() {
    set<stwing> w-wesuwt = n-nyew hashset<>(expandeduwws.size());
    fow (thwiftexpandeduww uww : expandeduwws.vawues()) {
      wesuwt.add(uww.getcanonicawwasthopuww());
    }
    w-wetuwn wesuwt;
  }

  p-pubwic stwing getcawdname() {
    wetuwn cawdname;
  }

  p-pubwic void setcawdname(stwing c-cawdname) {
    this.cawdname = cawdname;
  }

  p-pubwic stwing getcawddomain() {
    w-wetuwn cawddomain;
  }

  pubwic v-void setcawddomain(stwing c-cawddomain) {
    this.cawddomain = cawddomain;
  }

  pubwic stwing g-getcawdtitwe() {
    wetuwn cawdtitwe;
  }

  pubwic v-void setcawdtitwe(stwing c-cawdtitwe) {
    this.cawdtitwe = c-cawdtitwe;
  }

  pubwic stwing getcawddescwiption() {
    wetuwn cawddescwiption;
  }

  pubwic v-void setcawddescwiption(stwing cawddescwiption) {
    this.cawddescwiption = c-cawddescwiption;
  }

  p-pubwic stwing getcawdwang() {
    wetuwn cawdwang;
  }

  p-pubwic void setcawdwang(stwing cawdwang) {
    this.cawdwang = cawdwang;
  }

  p-pubwic stwing getcawduww() {
    wetuwn cawduww;
  }

  pubwic void setcawduww(stwing c-cawduww) {
    this.cawduww = c-cawduww;
  }

  pubwic wist<twittewmessageusew> getmentions() {
    w-wetuwn this.mentions;
  }

  p-pubwic void setmentions(wist<twittewmessageusew> m-mentions) {
    t-this.mentions = mentions;
  }

  p-pubwic wist<stwing> getwowewcasedmentions() {
    w-wetuwn w-wists.twansfowm(getmentions(), >w< usew -> {
      // t-this condition i-is awso checked i-in addusewtomentions(). UwU
      pweconditions.checkstate(usew.getscweenname().ispwesent(), XD "invawid mention");
      w-wetuwn usew.getscweenname().get().towowewcase();
    });
  }

  p-pubwic set<stwing> gethashtags() {
    wetuwn t-this.hashtags;
  }

  pubwic set<stwing> g-getnowmawizedhashtags(penguinvewsion penguinvewsion) {
    wetuwn getvewsionedtweetfeatuwes(penguinvewsion).getnowmawizedhashtags();
  }

  pubwic void addnowmawizedhashtag(stwing nyowmawizedhashtag, (U ﹏ U) penguinvewsion penguinvewsion) {
    g-getvewsionedtweetfeatuwes(penguinvewsion).addnowmawizedhashtags(nowmawizedhashtag);
  }

  pubwic optionaw<composewsouwce> g-getcomposewsouwce() {
    wetuwn c-composewsouwce;
  }

  p-pubwic void setcomposewsouwce(composewsouwce c-composewsouwce) {
    pweconditions.checknotnuww(composewsouwce, (U ᵕ U❁) "composewsouwce s-shouwd nyot be nyuww");
    t-this.composewsouwce = optionaw.of(composewsouwce);
  }

  pubwic boowean issewfthwead() {
    wetuwn sewfthwead;
  }

  pubwic void setsewfthwead(boowean sewfthwead) {
    this.sewfthwead = s-sewfthwead;
  }

  pubwic boowean isexcwusive() {
    w-wetuwn excwusiveconvewsationauthowid.ispwesent();
  }

  p-pubwic wong getexcwusiveconvewsationauthowid() {
    wetuwn excwusiveconvewsationauthowid.get();
  }

  pubwic void setexcwusiveconvewsationauthowid(wong excwusiveconvewsationauthowid) {
    this.excwusiveconvewsationauthowid = optionaw.of(excwusiveconvewsationauthowid);
  }

  /**
   * adds an expanded media uww based o-on the given p-pawametews. (ˆ ﻌ ˆ)♡
   */
  p-pubwic void addexpandedmediauww(stwing o-owiginawuww, òωó
                                  s-stwing e-expandeduww,
                                  @nuwwabwe mediatypes mediatype) {
    i-if (!stwingutiws.isbwank(owiginawuww) && !stwingutiws.isbwank(expandeduww)) {
      t-thwiftexpandeduww thwiftexpandeduww = n-nyew thwiftexpandeduww();
      i-if (mediatype != n-nyuww) {
        t-thwiftexpandeduww.setmediatype(mediatype);
      }
      t-thwiftexpandeduww.setowiginawuww(owiginawuww);
      thwiftexpandeduww.setexpandeduww(expandeduww);  // t-this wiww be t-tokenized and indexed
      // nyote t-that the mediauww i-is nyot indexed. w-we couwd a-awso index it, ^•ﻌ•^ b-but it is nyot indexed
      // t-to weduce wam usage. (///ˬ///✿)
      t-thwiftexpandeduww.setcanonicawwasthopuww(expandeduww); // t-this wiww be tokenized and indexed
      addexpandeduww(owiginawuww, -.- thwiftexpandeduww);
      t-thwiftexpandeduww.setconsumewmedia(twue);
    }
  }

  /**
   * adds an expanded n-nyon-media uww based on the given pawametews. >w<
   */
  p-pubwic v-void addexpandednonmediauww(stwing o-owiginawuww, stwing expandeduww) {
    i-if (!stwingutiws.isbwank(owiginawuww)) {
      t-thwiftexpandeduww thwiftexpandeduww = new thwiftexpandeduww(owiginawuww);
      if (!stwingutiws.isbwank(expandeduww)) {
        thwiftexpandeduww.setexpandeduww(expandeduww);
      }
      addexpandeduww(owiginawuww, òωó t-thwiftexpandeduww);
      thwiftexpandeduww.setconsumewmedia(fawse);
    }
  }

  /**
   * onwy used in tests. σωσ
   *
   * simuwates wesowving c-compwessed uwws, mya w-which is usuawwy done by wesowvecompwesseduwwsstage.
   */
  @visibwefowtesting
  p-pubwic void w-wepwaceuwwswithwesowveduwws(map<stwing, òωó s-stwing> w-wesowveduwws) {
    f-fow (map.entwy<stwing, 🥺 t-thwiftexpandeduww> uwwentwy : e-expandeduwws.entwyset()) {
      stwing tcouww = uwwentwy.getkey();
      i-if (wesowveduwws.containskey(tcouww)) {
        thwiftexpandeduww e-expandeduww = uwwentwy.getvawue();
        e-expandeduww.setcanonicawwasthopuww(wesowveduwws.get(tcouww));
      }
    }
  }

  /**
   * a-adds a mention fow a-a usew with the given scween nyame. (U ﹏ U)
   */
  pubwic v-void addmention(stwing s-scweenname) {
    t-twittewmessageusew usew = t-twittewmessageusew.cweatewithscweenname(scweenname);
    addusewtomentions(usew);
  }

  /**
   * adds the g-given usew to mentions. (ꈍᴗꈍ)
   */
  p-pubwic void addusewtomentions(twittewmessageusew u-usew) {
    pweconditions.checkawgument(usew.getscweenname().ispwesent(), (˘ω˘) "don't add invawid mentions");
    this.mentions.add(usew);
  }

  /**
   * a-adds the given hashtag.
   */
  pubwic void addhashtag(stwing hashtag) {
    this.hashtags.add(hashtag);
    fow (penguinvewsion penguinvewsion : suppowtedpenguinvewsions) {
      a-addnowmawizedhashtag(nowmawizewhewpew.nowmawize(hashtag, (✿oωo) g-getwocawe(), -.- penguinvewsion), (ˆ ﻌ ˆ)♡
          penguinvewsion);
    }
  }

  pwivate map<penguinvewsion, (✿oωo) v-vewsionedtweetfeatuwes> getvewsionedtweetfeatuwesmap() {
    m-map<penguinvewsion, vewsionedtweetfeatuwes> vewsionedmap =
        maps.newenummap(penguinvewsion.cwass);
    f-fow (penguinvewsion p-penguinvewsion : getsuppowtedpenguinvewsions()) {
      v-vewsionedmap.put(penguinvewsion, ʘwʘ nyew v-vewsionedtweetfeatuwes());
    }

    wetuwn v-vewsionedmap;
  }

  pubwic int g-getnumfavowites() {
    w-wetuwn nyumfavowites;
  }

  pubwic int getnumwetweets() {
    w-wetuwn nyumwetweets;
  }

  p-pubwic int getnumwepwies() {
    w-wetuwn nyumwepwies;
  }

  p-pubwic set<namedentity> getnamedentities() {
    w-wetuwn nyamedentities;
  }

  pubwic v-void addnamedentity(namedentity n-nyamedentity) {
    n-nyamedentities.add(namedentity);
  }

  pubwic set<stwing> getspaceids() {
    w-wetuwn s-spaceids;
  }

  pubwic void setspaceids(set<stwing> spaceids) {
    this.spaceids = sets.newhashset(spaceids);
  }

  p-pubwic set<twittewmessageusew> g-getspaceadmins() {
    wetuwn s-spaceadmins;
  }

  pubwic void addspaceadmin(twittewmessageusew admin) {
    s-spaceadmins.add(admin);
  }

  p-pubwic stwing getspacetitwe() {
    w-wetuwn spacetitwe;
  }

  pubwic void setspacetitwe(stwing s-spacetitwe) {
    t-this.spacetitwe = spacetitwe;
  }

  pwivate static b-boowean equaws(wist<eschewbiwdannotation> w-w1, (///ˬ///✿) wist<eschewbiwdannotation> w2) {
    e-eschewbiwdannotation[] a-aww1 = w1.toawway(new e-eschewbiwdannotation[w1.size()]);
    a-awways.sowt(aww1);
    eschewbiwdannotation[] aww2 = w1.toawway(new eschewbiwdannotation[w2.size()]);
    awways.sowt(aww2);
    w-wetuwn awways.equaws(aww1, a-aww2);
  }

  /**
   * compawes t-the given messages using wefwection and detewmines if they'we a-appwoximatewy e-equaw. rawr
   */
  pubwic static b-boowean wefwectionappwoxequaws(
      twittewmessage a-a, 🥺
      twittewmessage b, mya
      wist<stwing> additionawexcwudefiewds) {
    w-wist<stwing> excwudefiewds = wists.newawwaywist(
        "vewsionedtweetfeatuwesmap", mya
        "geowocation", mya
        "geotaggedwocation", (⑅˘꒳˘)
        "eschewbiwdannotations"
    );
    excwudefiewds.addaww(additionawexcwudefiewds);

    wetuwn e-equawsbuiwdew.wefwectionequaws(a, (✿oωo) b-b, 😳 excwudefiewds)
        && g-geoobject.appwoxequaws(a.getgeowocation(), OwO b-b.getgeowocation())
        && geoobject.appwoxequaws(a.getgeotaggedwocation(), (˘ω˘) b.getgeotaggedwocation())
        && e-equaws(a.geteschewbiwdannotations(), (✿oωo) b.geteschewbiwdannotations());
  }

  p-pubwic static boowean wefwectionappwoxequaws(twittewmessage a-a, twittewmessage b-b) {
    w-wetuwn wefwectionappwoxequaws(a, /(^•ω•^) b, cowwections.emptywist());
  }
}
