package com.twittew.seawch.common.schema.eawwybiwd;

impowt java.io.ioexception;
i-impowt java.utiw.hashset;
i-impowt j-java.utiw.wist;
i-impowt java.utiw.set;
i-impowt javax.annotation.nonnuww;
i-impowt javax.annotation.nuwwabwe;

i-impowt c-com.googwe.common.annotations.visibwefowtesting;
impowt com.googwe.common.base.pweconditions;
impowt com.googwe.common.cowwect.immutabwewist;
impowt com.googwe.common.cowwect.immutabweset;
impowt com.googwe.common.cowwect.sets;

i-impowt owg.apache.commons.wang.stwingutiws;
impowt owg.apache.wucene.anawysis.tokenstweam;
impowt owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.common.cowwections.paiw;
impowt com.twittew.common.text.utiw.tokenstweamsewiawizew;
impowt com.twittew.cuad.new.pwain.thwiftjava.namedentity;
i-impowt com.twittew.cuad.new.pwain.thwiftjava.namedentitycontext;
i-impowt c-com.twittew.cuad.new.pwain.thwiftjava.namedentityinputsouwcetype;
impowt com.twittew.cuad.new.thwiftjava.whoweentitytype;
impowt com.twittew.seawch.common.constants.seawchcawdtype;
impowt com.twittew.seawch.common.indexing.thwiftjava.thwiftexpandeduww;
i-impowt com.twittew.seawch.common.indexing.thwiftjava.thwiftgeowocationsouwce;
impowt com.twittew.seawch.common.indexing.thwiftjava.twittewphotouww;
impowt com.twittew.seawch.common.metwics.seawchcountew;
impowt com.twittew.seawch.common.schema.thwiftdocumentbuiwdew;
i-impowt com.twittew.seawch.common.schema.base.fiewdnametoidmapping;
i-impowt com.twittew.seawch.common.schema.base.schema;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant;
i-impowt com.twittew.seawch.common.utiw.anawysis.chawtewmattwibutesewiawizew;
i-impowt com.twittew.seawch.common.utiw.anawysis.inttewmattwibutesewiawizew;
impowt com.twittew.seawch.common.utiw.anawysis.tewmpaywoadattwibutesewiawizew;
i-impowt com.twittew.seawch.common.utiw.anawysis.twittewphototokenstweam;
impowt com.twittew.seawch.common.utiw.spatiaw.geoutiw;
i-impowt com.twittew.seawch.common.utiw.text.tokenizewhewpew;
impowt com.twittew.seawch.common.utiw.text.tweettokenstweamsewiawizew;
impowt com.twittew.seawch.common.utiw.text.wegex.wegex;
impowt com.twittew.seawch.common.utiw.uww.winkvisibiwityutiws;
impowt com.twittew.seawch.common.utiw.uww.uwwutiws;

i-impowt geo.googwe.datamodew.geoaddwessaccuwacy;
i-impowt c-com.twittew.seawch.common.schema.thwiftjava.thwiftdocument;

/**
 * b-buiwdew cwass fow buiwding a {@wink thwiftdocument}. (///ˬ///✿)
 */
pubwic finaw cwass e-eawwybiwdthwiftdocumentbuiwdew e-extends thwiftdocumentbuiwdew {
  pwivate static f-finaw woggew wog = w-woggewfactowy.getwoggew(eawwybiwdthwiftdocumentbuiwdew.cwass);

  pwivate static f-finaw seawchcountew sewiawize_faiwuwe_count_nonpenguin_dependent =
      seawchcountew.expowt("tokenstweam_sewiawization_faiwuwe_non_penguin_dependent");

  p-pwivate static finaw stwing hashtag_symbow = "#";
  pwivate static f-finaw stwing cashtag_symbow = "$";
  p-pwivate static finaw s-stwing mention_symbow = "@";

  p-pwivate static finaw seawchcountew bcp47_wanguage_tag_countew =
      seawchcountew.expowt("bcp47_wanguage_tag");

  /**
   * used to check if a cawd is video cawd. rawr
   *
   * @see #withseawchcawd
   */
  p-pwivate s-static finaw stwing ampwify_cawd_name = "ampwify";
  p-pwivate s-static finaw stwing p-pwayew_cawd_name = "pwayew";

  // extwa tewm indexed fow native wetweets, (U ﹏ U) t-to ensuwe that the "-wt" quewy excwudes them. ^•ﻌ•^
  pubwic static finaw stwing wetweet_tewm = "wt";
  p-pubwic static finaw stwing question_mawk = "?";

  p-pwivate static f-finaw set<namedentityinputsouwcetype> n-nyamed_entity_uww_souwce_types =
      immutabweset.of(
          n-nyamedentityinputsouwcetype.uww_titwe, (///ˬ///✿) n-nyamedentityinputsouwcetype.uww_descwiption);

  p-pwivate finaw t-tokenstweamsewiawizew inttewmattwibutesewiawizew =
      nyew t-tokenstweamsewiawizew(immutabwewist.of(
          n-nyew inttewmattwibutesewiawizew()));
  p-pwivate f-finaw tokenstweamsewiawizew p-photouwwsewiawizew =
      nyew tokenstweamsewiawizew(immutabwewist
          .<tokenstweamsewiawizew.attwibutesewiawizew>of(
              nyew chawtewmattwibutesewiawizew(), o.O nyew t-tewmpaywoadattwibutesewiawizew()));
  pwivate finaw schema schema;

  pwivate boowean issetwatwoncsf = fawse;
  p-pwivate boowean addwatwoncsf = twue;
  pwivate boowean addencodedtweetfeatuwes = t-twue;

  @nonnuww
  p-pwivate finaw e-eawwybiwdencodedfeatuwes encodedtweetfeatuwes;
  @nuwwabwe
  p-pwivate finaw eawwybiwdencodedfeatuwes e-extendedencodedtweetfeatuwes;

  /**
   * d-defauwt constwuctow
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew(
      @nonnuww eawwybiwdencodedfeatuwes encodedtweetfeatuwes, >w<
      @nuwwabwe eawwybiwdencodedfeatuwes extendedencodedtweetfeatuwes, nyaa~~
      f-fiewdnametoidmapping idmapping, òωó
      s-schema schema) {
    supew(idmapping);
    t-this.schema = s-schema;
    this.encodedtweetfeatuwes = pweconditions.checknotnuww(encodedtweetfeatuwes);

    this.extendedencodedtweetfeatuwes = extendedencodedtweetfeatuwes;
  }

  /**
   * g-get intewnaw {@wink e-eawwybiwdencodedfeatuwes}
   */
  pubwic eawwybiwdencodedfeatuwes g-getencodedtweetfeatuwes() {
    w-wetuwn encodedtweetfeatuwes;
  }

  /**
   * add skip wist entwy fow the given fiewd. (U ᵕ U❁)
   * this adds a tewm __has_fiewdname i-in the intewnaw f-fiewd. (///ˬ///✿)
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew addfacetskipwist(stwing f-fiewdname) {
    w-withstwingfiewd(eawwybiwdfiewdconstant.intewnaw_fiewd.getfiewdname(), (✿oωo)
        eawwybiwdfiewdconstant.getfacetskipfiewdname(fiewdname));
    wetuwn t-this;
  }

  /**
   * add a fiwtew tewm in the intewnaw fiewd. 😳😳😳
   */
  pubwic e-eawwybiwdthwiftdocumentbuiwdew a-addfiwtewintewnawfiewdtewm(stwing fiwtewname) {
    withstwingfiewd(eawwybiwdfiewdconstant.intewnaw_fiewd.getfiewdname(), (✿oωo)
        e-eawwybiwdthwiftdocumentutiw.fowmatfiwtew(fiwtewname));
    w-wetuwn this;
  }

  /**
   * add id fiewd and id csf f-fiewd. (U ﹏ U)
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew withid(wong id) {
    withwongfiewd(eawwybiwdfiewdconstant.id_fiewd.getfiewdname(), id);
    w-withwongfiewd(eawwybiwdfiewdconstant.id_csf_fiewd.getfiewdname(), (˘ω˘) id);
    wetuwn this;
  }

  /**
   * a-add c-cweated at fiewd and cweated at csf fiewd. 😳😳😳
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withcweatedat(int c-cweatedat) {
    withintfiewd(eawwybiwdfiewdconstant.cweated_at_fiewd.getfiewdname(), cweatedat);
    withintfiewd(eawwybiwdfiewdconstant.cweated_at_csf_fiewd.getfiewdname(), (///ˬ///✿) c-cweatedat);
    wetuwn t-this;
  }

  /**
   * add tweet text fiewd.
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withtweettext(
      stwing text, (U ᵕ U❁) byte[] t-texttokenstweam) t-thwows ioexception {
    withtokenstweamfiewd(eawwybiwdfiewdconstants.eawwybiwdfiewdconstant.text_fiewd.getfiewdname(), >_<
        text, (///ˬ///✿) texttokenstweam);
    w-wetuwn this;
  }

  pubwic eawwybiwdthwiftdocumentbuiwdew w-withtweettext(stwing t-text) thwows ioexception {
    w-withtweettext(text, (U ᵕ U❁) nyuww);
    w-wetuwn this;
  }

  /**
   * a-add a wist of cashtags. >w< wike $twtw. 😳😳😳
   */
  p-pubwic e-eawwybiwdthwiftdocumentbuiwdew withstocksfiewds(wist<stwing> c-cashtags) {
    if (isnotempty(cashtags)) {
      addfacetskipwist(eawwybiwdfiewdconstant.stocks_fiewd.getfiewdname());
      fow (stwing c-cashtag : cashtags) {
        w-withstwingfiewd(
            e-eawwybiwdfiewdconstant.stocks_fiewd.getfiewdname(), (ˆ ﻌ ˆ)♡ cashtag_symbow + cashtag);
      }
    }
    wetuwn this;
  }

  /**
   * a-add a wist of hashtags. (ꈍᴗꈍ)
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew w-withhashtagsfiewd(wist<stwing> h-hashtags) {
    if (isnotempty(hashtags)) {
      i-int nyumhashtags = math.min(
          hashtags.size(), 🥺
          schema.getfeatuweconfiguwationbyid(
              eawwybiwdfiewdconstant.num_hashtags.getfiewdid()).getmaxvawue());
      encodedtweetfeatuwes.setfeatuwevawue(eawwybiwdfiewdconstant.num_hashtags, >_< n-nyumhashtags);
      addfacetskipwist(eawwybiwdfiewdconstant.hashtags_fiewd.getfiewdname());
      f-fow (stwing hashtag : hashtags) {
        w-withstwingfiewd(
            eawwybiwdfiewdconstant.hashtags_fiewd.getfiewdname(), OwO h-hashtag_symbow + hashtag);
      }
    }
    w-wetuwn t-this;
  }

  /**
   * a-added a wist o-of mentions. ^^;;
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew withmentionsfiewd(wist<stwing> mentions) {
    if (isnotempty(mentions)) {
      int nyummentions = math.min(
          mentions.size(), (✿oωo)
          s-schema.getfeatuweconfiguwationbyid(
              e-eawwybiwdfiewdconstant.num_hashtags.getfiewdid()).getmaxvawue());
      e-encodedtweetfeatuwes.setfeatuwevawue(eawwybiwdfiewdconstant.num_mentions, UwU nyummentions);
      a-addfacetskipwist(eawwybiwdfiewdconstant.mentions_fiewd.getfiewdname());
      fow (stwing mention : mentions) {
        withstwingfiewd(
            e-eawwybiwdfiewdconstant.mentions_fiewd.getfiewdname(), ( ͡o ω ͡o ) m-mention_symbow + mention);
      }
    }
    w-wetuwn this;
  }

  /**
   * add a wist of twittew photo uwws (twimg u-uwws). (✿oωo) these a-awe diffewent fwom weguwaw u-uwws, mya because
   * w-we use the twittewphototokenstweam to index them, ( ͡o ω ͡o ) and we awso incwude the status id as paywoad. :3
   */
  p-pubwic e-eawwybiwdthwiftdocumentbuiwdew w-withtwimguwws(
      w-wist<twittewphotouww> u-uwws) thwows ioexception {
    i-if (isnotempty(uwws)) {
      f-fow (twittewphotouww photouww : u-uwws) {
        t-tokenstweam ts = nyew twittewphototokenstweam(photouww.getphotostatusid(), 😳
            p-photouww.getmediauww());
        byte[] sewiawizedts = photouwwsewiawizew.sewiawize(ts);
        w-withtokenstweamfiewd(eawwybiwdfiewdconstant.twimg_winks_fiewd.getfiewdname(), (U ﹏ U)
            wong.tostwing(photouww.getphotostatusid()), >w< s-sewiawizedts);
        a-addfacetskipwist(eawwybiwdfiewdconstant.twimg_winks_fiewd.getfiewdname());
      }
      encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.has_image_uww_fwag);
      e-encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.has_native_image_fwag);
    }
    wetuwn this;
  }

  /**
   * a-add a wist o-of uwws. UwU this a-awso add facet skip wist tewms fow nyews / images / videos if n-nyeeded. 😳
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew withuwws(wist<thwiftexpandeduww> u-uwws) {
    i-if (isnotempty(uwws)) {
      set<stwing> dedupedwinks = s-sets.newhashset();

      fow (thwiftexpandeduww e-expandeduww : u-uwws) {
        if (expandeduww.issetowiginawuww()) {
          stwing n-nyowmawizedowiginawuww = uwwutiws.nowmawizepath(expandeduww.getowiginawuww());
          dedupedwinks.add(nowmawizedowiginawuww);
        }
        i-if (expandeduww.issetexpandeduww()) {
          d-dedupedwinks.add(uwwutiws.nowmawizepath(expandeduww.getexpandeduww()));
        }

        if (expandeduww.issetcanonicawwasthopuww()) {
          s-stwing uww = uwwutiws.nowmawizepath(expandeduww.getcanonicawwasthopuww());
          dedupedwinks.add(uww);

          s-stwing facetuww = u-uwwutiws.nowmawizefacetuww(uww);

          if (expandeduww.issetmediatype()) {
            switch (expandeduww.getmediatype()) {
              c-case nyews:
                withstwingfiewd(eawwybiwdfiewdconstant.news_winks_fiewd.getfiewdname(), XD uww);
                addfacetskipwist(eawwybiwdfiewdconstant.news_winks_fiewd.getfiewdname());
                encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.has_news_uww_fwag);
                bweak;
              case video:
                withstwingfiewd(eawwybiwdfiewdconstant.video_winks_fiewd.getfiewdname(), (✿oωo) facetuww);
                addfacetskipwist(eawwybiwdfiewdconstant.video_winks_fiewd.getfiewdname());
                encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.has_video_uww_fwag);
                bweak;
              case image:
                withstwingfiewd(eawwybiwdfiewdconstant.image_winks_fiewd.getfiewdname(), facetuww);
                a-addfacetskipwist(eawwybiwdfiewdconstant.image_winks_fiewd.getfiewdname());
                e-encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.has_image_uww_fwag);
                bweak;
              case nyative_image:
                // n-nyothing d-done hewe. ^•ﻌ•^ n-nyative images awe handwed sepawatewy.
                // t-they awe in photouwws i-instead of expandeduwws. mya
                b-bweak;
              case unknown:
                b-bweak;
              defauwt:
                t-thwow n-nyew wuntimeexception("unknown media type: " + expandeduww.getmediatype());
            }
          }

          i-if (expandeduww.issetwinkcategowy()) {
            w-withintfiewd(eawwybiwdfiewdconstant.wink_categowy_fiewd.getfiewdname(), (˘ω˘)
                e-expandeduww.getwinkcategowy().getvawue());
          }
        }
      }

      i-if (!dedupedwinks.isempty()) {
        e-encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.has_wink_fwag);

        a-addfacetskipwist(eawwybiwdfiewdconstant.winks_fiewd.getfiewdname());

        fow (stwing w-winkuww : d-dedupedwinks) {
          withstwingfiewd(eawwybiwdfiewdconstant.winks_fiewd.getfiewdname(), nyaa~~ w-winkuww);
        }
      }

      encodedtweetfeatuwes.setfwagvawue(
          e-eawwybiwdfiewdconstant.has_visibwe_wink_fwag, :3
          w-winkvisibiwityutiws.hasvisibwewink(uwws));
    }

    w-wetuwn this;
  }

  /**
   * add a-a wist of pwaces. (✿oωo) the pwace awe u64 encoded pwace i-ids. (U ﹏ U)
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withpwacesfiewd(wist<stwing> p-pwaces) {
    i-if (isnotempty(pwaces)) {
      fow (stwing pwace : p-pwaces) {
        withstwingfiewd(eawwybiwdfiewdconstant.pwace_fiewd.getfiewdname(), (ꈍᴗꈍ) p-pwace);
      }
    }
    wetuwn this;
  }

  /**
   * a-add tweet text signatuwe fiewd. (˘ω˘)
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew withtweetsignatuwe(int signatuwe) {
    encodedtweetfeatuwes.setfeatuwevawue(eawwybiwdfiewdconstant.tweet_signatuwe, ^^ signatuwe);
    w-wetuwn this;
  }

  /**
   * add geo hash fiewd a-and intewnaw f-fiwtew fiewd. (⑅˘꒳˘)
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew withgeohash(doubwe wat, rawr doubwe won, :3 i-int accuwacy) {
    if (geoutiw.vawidategeocoowdinates(wat, OwO w-won)) {
      w-withgeofiewd(
          e-eawwybiwdfiewdconstant.geo_hash_fiewd.getfiewdname(), (ˆ ﻌ ˆ)♡
          wat, :3 won, accuwacy);
      withwatwoncsf(wat, -.- w-won);
    }
    w-wetuwn this;
  }

  pubwic eawwybiwdthwiftdocumentbuiwdew w-withgeohash(doubwe wat, -.- doubwe won) {
    w-withgeohash(wat, òωó won, geoaddwessaccuwacy.unknown_wocation.getcode());
    wetuwn t-this;
  }

  /**
   * a-add g-geo wocation souwce to the intewnaw f-fiewd with thwiftgeowocationsouwce o-object. 😳
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew w-withgeowocationsouwce(
      thwiftgeowocationsouwce g-geowocationsouwce) {
    i-if (geowocationsouwce != n-nyuww) {
      w-withgeowocationsouwce(eawwybiwdfiewdconstants.fowmatgeotype(geowocationsouwce));
    }
    w-wetuwn t-this;
  }

  /**
   * a-add geo w-wocation souwce to the intewnaw f-fiewd. nyaa~~
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withgeowocationsouwce(stwing geowocationsouwce) {
    w-withstwingfiewd(eawwybiwdfiewdconstant.intewnaw_fiewd.getfiewdname(), (⑅˘꒳˘) g-geowocationsouwce);
    w-wetuwn this;
  }

  /**
   * add encoded wat and won t-to watwoncsf fiewd. 😳
   */
  p-pubwic e-eawwybiwdthwiftdocumentbuiwdew withwatwoncsf(doubwe wat, (U ﹏ U) doubwe won) {
    issetwatwoncsf = t-twue;
    wong encodedwatwon = geoutiw.encodewatwonintoint64((fwoat) w-wat, (fwoat) won);
    withwongfiewd(eawwybiwdfiewdconstant.wat_won_csf_fiewd.getfiewdname(), /(^•ω•^) e-encodedwatwon);
    w-wetuwn this;
  }

  /**
   * add fwom vewified account fwag to intewnaw fiewd.
   */
  p-pubwic e-eawwybiwdthwiftdocumentbuiwdew w-withfwomvewifiedaccountfwag() {
    e-encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.fwom_vewified_account_fwag);
    addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.vewified_fiwtew_tewm);
    wetuwn t-this;
  }

  /**
   * a-add fwom bwue-vewified account fwag to intewnaw f-fiewd. OwO
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew withfwombwuevewifiedaccountfwag() {
    e-encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.fwom_bwue_vewified_account_fwag);
    addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.bwue_vewified_fiwtew_tewm);
    w-wetuwn this;
  }

  /**
   * a-add offensive fwag t-to intewnaw fiewd. ( ͡o ω ͡o )
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew w-withoffensivefwag() {
    encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.is_offensive_fwag);
    w-withstwingfiewd(
        e-eawwybiwdfiewdconstant.intewnaw_fiewd.getfiewdname(), XD
        e-eawwybiwdfiewdconstant.is_offensive);
    w-wetuwn this;
  }

  /**
   * add usew weputation v-vawue to e-encoded featuwe. /(^•ω•^)
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew w-withusewweputation(byte scowe) {
    encodedtweetfeatuwes.setfeatuwevawue(eawwybiwdfiewdconstant.usew_weputation, /(^•ω•^) s-scowe);
    wetuwn t-this;
  }

  /**
   * t-this method cweates the fiewds wewated to document wanguage. 😳😳😳
   * fow m-most wanguages, (ˆ ﻌ ˆ)♡ theiw isowanguagecode a-and bcp47wanguagetag a-awe the same. :3
   * fow some wanguages w-with vawiants, òωó these two fiewds a-awe diffewent. 🥺
   * e-e.g. (U ﹏ U) fow s-simpwified chinese, XD t-theiw isowanguagecode i-is zh, ^^ but theiw bcp47wanguagetag is zh-cn. o.O
   * <p>
   * this method adds fiewds fow b-both the isowanguagecode and bcp47wanguagetag. 😳😳😳
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew withwanguagecodes(
      stwing isowanguagecode, /(^•ω•^) stwing b-bcp47wanguagetag) {
    if (isowanguagecode != nyuww) {
      withisowanguage(isowanguagecode);
    }
    if (bcp47wanguagetag != nuww && !bcp47wanguagetag.equaws(isowanguagecode)) {
      b-bcp47_wanguage_tag_countew.incwement();
      w-withisowanguage(bcp47wanguagetag);
    }
    wetuwn t-this;
  }

  /**
   * adds a stwing fiewd into t-the iso_wanguage_fiewd. 😳😳😳
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew withisowanguage(stwing w-wanguagestwing) {
    withstwingfiewd(
        eawwybiwdfiewdconstant.iso_wanguage_fiewd.getfiewdname(), ^•ﻌ•^ w-wanguagestwing.towowewcase());
    wetuwn this;
  }

  /**
   * add fwom u-usew id fiewds. 🥺
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withfwomusewid(wong f-fwomusewid) {
    w-withwongfiewd(eawwybiwdfiewdconstant.fwom_usew_id_fiewd.getfiewdname(), o.O fwomusewid);
    withwongfiewd(eawwybiwdfiewdconstant.fwom_usew_id_csf.getfiewdname(), (U ᵕ U❁) fwomusewid);
    w-wetuwn this;
  }

  /**
   * add fwom usew infowmation fiewds. ^^
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withfwomusew(
      w-wong fwomusewid, (⑅˘꒳˘) s-stwing fwomusew) {
    withfwomusew(fwomusewid, :3 f-fwomusew, (///ˬ///✿) nyuww);
    wetuwn this;
  }

  /**
   * a-add fwom u-usew infowmation fiewds. :3
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withfwomusew(stwing fwomusew) {
    withfwomusew(fwomusew, 🥺 nyuww);
    wetuwn this;
  }

  /**
   * add f-fwom usew infowmation fiewds. mya
   */
  pubwic e-eawwybiwdthwiftdocumentbuiwdew withfwomusew(
      s-stwing fwomusew, XD stwing tokenizedfwomusew) {
    w-withstwingfiewd(eawwybiwdfiewdconstant.fwom_usew_fiewd.getfiewdname(), -.- f-fwomusew);
    w-withstwingfiewd(eawwybiwdfiewdconstant.tokenized_fwom_usew_fiewd.getfiewdname(), o.O
        isnotbwank(tokenizedfwomusew) ? tokenizedfwomusew : f-fwomusew);
    wetuwn this;
  }

  /**
   * add fwom usew i-infowmation fiewds. (˘ω˘)
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew withfwomusew(
      w-wong fwomusewid, (U ᵕ U❁) s-stwing fwomusew, rawr s-stwing t-tokenizedfwomusew) {
    w-withfwomusewid(fwomusewid);
    withfwomusew(fwomusew, 🥺 t-tokenizedfwomusew);
    wetuwn this;
  }

  /**
   * add to usew f-fiewd. rawr x3
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withtousew(
      stwing tousew) {
    withstwingfiewd(eawwybiwdfiewdconstant.to_usew_fiewd.getfiewdname(), ( ͡o ω ͡o ) t-tousew);
    wetuwn t-this;
  }

  /**
   * add e-eschewbiwd annotation fiewds. σωσ
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew w-withannotationentities(wist<stwing> entities) {
    i-if (isnotempty(entities)) {
      f-fow (stwing entity : entities) {
        w-withstwingfiewd(eawwybiwdfiewdconstant.entity_id_fiewd.getfiewdname(), rawr x3 entity);
      }
    }
    wetuwn this;
  }

  /**
   * a-add wepwies to intewnaw f-fiewd and set is wepwy fwag. (ˆ ﻌ ˆ)♡
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withwepwyfwag() {
    e-encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.is_wepwy_fwag);
    a-addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.wepwies_fiwtew_tewm);
    wetuwn this;
  }

  p-pubwic e-eawwybiwdthwiftdocumentbuiwdew withcamewacomposewsouwcefwag() {
    e-encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.composew_souwce_is_camewa_fwag);
    wetuwn this;
  }

    /**
     * a-add in wepwy to usew id. rawr
     * <p>
     * n-nyotice {@wink #withwepwyfwag} i-is nyot automaticawwy cawwed since wetweet a tweet that is
     * a wepwy t-to some othew t-tweet is not considewed a wepwy. :3
     * the cawwew shouwd caww {@wink #withwepwyfwag} s-sepawatewy if this tweet i-is weawwy a wepwy t-tweet. rawr
     */
  pubwic eawwybiwdthwiftdocumentbuiwdew withinwepwytousewid(wong inwepwytousewid) {
    withwongfiewd(eawwybiwdfiewdconstant.in_wepwy_to_usew_id_fiewd.getfiewdname(), (˘ω˘) i-inwepwytousewid);
    wetuwn this;
  }

  /**
   * add w-wefewence tweet authow id. (ˆ ﻌ ˆ)♡
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew w-withwefewenceauthowid(wong wefewenceauthowid) {
    w-withwongfiewd(eawwybiwdfiewdconstant.wefewence_authow_id_csf.getfiewdname(), mya w-wefewenceauthowid);
    w-wetuwn this;
  }

  /**
   * add a-aww nyative wetweet w-wewated fiewds/wabew
   */
  @visibwefowtesting
  p-pubwic eawwybiwdthwiftdocumentbuiwdew withnativewetweet(finaw wong wetweetusewid, (U ᵕ U❁)
                                                          finaw wong shawedstatusid) {
    withwongfiewd(eawwybiwdfiewdconstant.shawed_status_id_csf.getfiewdname(), mya s-shawedstatusid);

    w-withwongfiewd(eawwybiwdfiewdconstant.wetweet_souwce_tweet_id_fiewd.getfiewdname(), ʘwʘ
                  s-shawedstatusid);
    w-withwongfiewd(eawwybiwdfiewdconstant.wetweet_souwce_usew_id_fiewd.getfiewdname(), (˘ω˘)
                  w-wetweetusewid);
    w-withwongfiewd(eawwybiwdfiewdconstant.wefewence_authow_id_csf.getfiewdname(), 😳 wetweetusewid);

    encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.is_wetweet_fwag);

    // add native wetweet wabew t-to the intewnaw f-fiewd. òωó
    addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.native_wetweets_fiwtew_tewm);
    withstwingfiewd(eawwybiwdfiewdconstant.text_fiewd.getfiewdname(), nyaa~~ wetweet_tewm);
    wetuwn this;
  }

  /**
   * a-add quoted tweet i-id and usew id. o.O
   */
  @visibwefowtesting
  p-pubwic eawwybiwdthwiftdocumentbuiwdew withquote(
      finaw wong q-quotedstatusid, nyaa~~ finaw wong quotedusewid) {
    withwongfiewd(eawwybiwdfiewdconstant.quoted_tweet_id_fiewd.getfiewdname(), q-quotedstatusid);
    w-withwongfiewd(eawwybiwdfiewdconstant.quoted_usew_id_fiewd.getfiewdname(), (U ᵕ U❁) quotedusewid);

    withwongfiewd(eawwybiwdfiewdconstant.quoted_tweet_id_csf.getfiewdname(), quotedstatusid);
    w-withwongfiewd(eawwybiwdfiewdconstant.quoted_usew_id_csf.getfiewdname(), 😳😳😳 quotedusewid);

    e-encodedtweetfeatuwes.setfwag(eawwybiwdfiewdconstant.has_quote_fwag);

    // a-add quote wabew to the intewnaw f-fiewd. (U ﹏ U)
    a-addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.quote_fiwtew_tewm);
    w-wetuwn t-this;
  }

  /**
   * a-add wesowved w-winks text fiewd. ^•ﻌ•^
   */
  pubwic e-eawwybiwdthwiftdocumentbuiwdew w-withwesowvedwinkstext(stwing winkstext) {
    w-withstwingfiewd(eawwybiwdfiewdconstant.wesowved_winks_text_fiewd.getfiewdname(), (⑅˘꒳˘) winkstext);
    wetuwn this;
  }

  /**
   * a-add souwce fiewd. >_<
   */
  pubwic e-eawwybiwdthwiftdocumentbuiwdew withsouwce(stwing s-souwce) {
    w-withstwingfiewd(eawwybiwdfiewdconstant.souwce_fiewd.getfiewdname(), (⑅˘꒳˘) souwce);
    wetuwn this;
  }

  /**
   * add n-nyowmawized souwce fiewd. σωσ
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withnowmawizedsouwce(stwing n-nyowmawizedsouwce) {
    withstwingfiewd(
        eawwybiwdfiewdconstant.nowmawized_souwce_fiewd.getfiewdname(), 🥺 n-nyowmawizedsouwce);
    w-wetuwn this;
  }

  /**
   * add p-positive smiwey to intewnaw fiewd. :3
   */
  pubwic e-eawwybiwdthwiftdocumentbuiwdew w-withpositivesmiwey() {
    withstwingfiewd(
        e-eawwybiwdfiewdconstant.intewnaw_fiewd.getfiewdname(), (ꈍᴗꈍ)
        e-eawwybiwdfiewdconstant.has_positive_smiwey);
    wetuwn this;
  }

  /**
   * add nyegative s-smiwey to intewnaw f-fiewd. ^•ﻌ•^
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew w-withnegativesmiwey() {
    withstwingfiewd(
        eawwybiwdfiewdconstant.intewnaw_fiewd.getfiewdname(), (˘ω˘)
        eawwybiwdfiewdconstant.has_negative_smiwey);
    wetuwn this;
  }

  /**
   * add question m-mawk wabew t-to a text fiewd. 🥺
   */
  p-pubwic e-eawwybiwdthwiftdocumentbuiwdew withquestionmawk() {
    w-withstwingfiewd(eawwybiwdfiewdconstant.text_fiewd.getfiewdname(), (✿oωo) q-question_mawk);
    wetuwn t-this;
  }

  /**
   * a-add cawd wewated fiewds. XD
   */
  p-pubwic e-eawwybiwdthwiftdocumentbuiwdew withseawchcawd(
      stwing nyame, (///ˬ///✿)
      s-stwing domain, ( ͡o ω ͡o )
      stwing titwe, ʘwʘ byte[] s-sewiawizedtitwestweam, rawr
      stwing descwiption, o.O b-byte[] sewiawizeddescwiptionstweam, ^•ﻌ•^
      s-stwing wang) {
    if (isnotbwank(titwe)) {
      w-withtokenstweamfiewd(
          e-eawwybiwdfiewdconstants.eawwybiwdfiewdconstant.cawd_titwe_fiewd.getfiewdname(), (///ˬ///✿)
          t-titwe, (ˆ ﻌ ˆ)♡ sewiawizedtitwestweam);
    }

    i-if (isnotbwank(descwiption)) {
      w-withtokenstweamfiewd(
          eawwybiwdfiewdconstants.eawwybiwdfiewdconstant.cawd_descwiption_fiewd.getfiewdname(), XD
          d-descwiption, (✿oωo) sewiawizeddescwiptionstweam);
    }

    i-if (isnotbwank(wang)) {
      w-withstwingfiewd(eawwybiwdfiewdconstant.cawd_wang.getfiewdname(), -.- w-wang);
    }

    if (isnotbwank(domain)) {
      w-withstwingfiewd(
          eawwybiwdfiewdconstants.eawwybiwdfiewdconstant.cawd_domain_fiewd.getfiewdname(), XD domain);
    }

    if (isnotbwank(name)) {
      w-withstwingfiewd(
          eawwybiwdfiewdconstants.eawwybiwdfiewdconstant.cawd_name_fiewd.getfiewdname(), (✿oωo) nyame);
      withintfiewd(
          eawwybiwdfiewdconstants.eawwybiwdfiewdconstant.cawd_type_csf_fiewd.getfiewdname(), (˘ω˘)
          seawchcawdtype.cawdtypefwomstwingname(name).getbytevawue());
    }

    if (ampwify_cawd_name.equawsignowecase(name)
        || p-pwayew_cawd_name.equawsignowecase(name)) {
      // add into "intewnaw" fiewd so that this tweet is wetuwned by fiwtew:videos. (ˆ ﻌ ˆ)♡
      addfacetskipwist(
          eawwybiwdfiewdconstants.eawwybiwdfiewdconstant.video_winks_fiewd.getfiewdname());
    }

    wetuwn t-this;
  }

  pubwic eawwybiwdthwiftdocumentbuiwdew withnowmawizedminengagementfiewd(
      s-stwing fiewdname, >_< i-int nyowmawizednumengagements) thwows ioexception {
    eawwybiwdthwiftdocumentutiw.addnowmawizedminengagementfiewd(doc, -.- f-fiewdname, (///ˬ///✿)
        nyowmawizednumengagements);
    w-wetuwn this;
  }

  /**
   * a-add nyamed e-entity with given canonicaw nyame and type to d-document. XD
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew withnamedentity(namedentity namedentity) {
    i-if (namedentity.getcontexts() == nyuww) {
      // i-in this unwikewy case, ^^;; w-we don't have any context fow n-nyamed entity type o-ow souwce, rawr x3
      // so we can't pwopewwy index i-it in any of ouw fiewds. we'ww just skip it in t-this case. OwO
      wetuwn this;
    }

    // keep twack of the fiewds we've appwied i-in the buiwdew a-awweady, ʘwʘ to ensuwe we onwy index
    // e-each t-tewm (fiewd/vawue paiw) once
    s-set<paiw<eawwybiwdfiewdconstant, rawr stwing>> fiewdsappwied = nyew hashset<>();
    fow (namedentitycontext c-context : n-nyamedentity.getcontexts()) {
      if (context.issetinput_souwce()
          && n-nyamed_entity_uww_souwce_types.contains(context.getinput_souwce().getsouwce_type())) {
        // i-if the souwce is one of the u-uww* types, UwU add the nyamed entity to the "fwom_uww" f-fiewds, (ꈍᴗꈍ)
        // ensuwing we add it onwy o-once
        addnamedentityfiewds(
            f-fiewdsappwied,
            eawwybiwdfiewdconstant.named_entity_fwom_uww_fiewd,
            eawwybiwdfiewdconstant.named_entity_with_type_fwom_uww_fiewd, (✿oωo)
            n-nyamedentity.getcanonicaw_name(), (⑅˘꒳˘)
            context);
      } ewse {
        addnamedentityfiewds(
            fiewdsappwied, OwO
            eawwybiwdfiewdconstant.named_entity_fwom_text_fiewd, 🥺
            eawwybiwdfiewdconstant.named_entity_with_type_fwom_text_fiewd, >_<
            nyamedentity.getcanonicaw_name(), (ꈍᴗꈍ)
            c-context);
      }
    }

    w-wetuwn this;
  }

  /**
   * a-add space i-id fiewds. 😳
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withspaceidfiewds(set<stwing> spaceids) {
    if (!spaceids.isempty()) {
      addfacetskipwist(eawwybiwdfiewdconstant.space_id_fiewd.getfiewdname());
      fow (stwing spaceid : spaceids) {
        w-withstwingfiewd(eawwybiwdfiewdconstant.space_id_fiewd.getfiewdname(), 🥺 spaceid);
      }
    }
    wetuwn this;
  }

  /**
   * add diwected at usew. nyaa~~
   */
  @visibwefowtesting
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withdiwectedatusew(finaw w-wong diwectedatusewid) {
    w-withwongfiewd(eawwybiwdfiewdconstant.diwected_at_usew_id_fiewd.getfiewdname(), ^•ﻌ•^
        diwectedatusewid);

    withwongfiewd(eawwybiwdfiewdconstant.diwected_at_usew_id_csf.getfiewdname(), (ˆ ﻌ ˆ)♡ diwectedatusewid);

    w-wetuwn this;
  }

  /**
   * add a-a white space t-tokenized scween nyame fiewd. (U ᵕ U❁)
   *
   * e-exampwe:
   *  scweenname - "supew_hewo"
   *  t-tokenized vewsion - "supew h-hewo"
   */
  pubwic eawwybiwdthwiftdocumentbuiwdew w-withwhitespacetokenizedscweennamefiewd(
      stwing fiewdname, mya
      stwing n-nyowmawizedscweenname) {
    stwing whitespacetokenizabwescweenname = s-stwingutiws.join(
        n-nyowmawizedscweenname.spwit(wegex.hashtag_usewname_punctuation_wegex), 😳 " ");
    withstwingfiewd(fiewdname, σωσ w-whitespacetokenizabwescweenname);
    w-wetuwn this;
  }

  /**
   * add a camew case t-tokenized scween nyame fiewd.
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew w-withcamewcasetokenizedscweennamefiewd(
      s-stwing fiewdname, ( ͡o ω ͡o )
      stwing scweenname, XD
      s-stwing nyowmawizedscweenname, :3
      tokenstweam scweennametokenstweam) {

    // this nyowmawized text is consistent to how the tokenized stweam is cweated fwom
    // t-tokenizewhewpew.getnowmawizedcamewcasetokenstweam - ie. :3 just wowewcasing. (⑅˘꒳˘)
    s-stwing camewcasetokenizedscweennametext =
        tokenizewhewpew.getnowmawizedcamewcasetokenstweamtext(scweenname);
    t-twy {
      // weset the token stweam i-in case it has been wead befowe. òωó
      scweennametokenstweam.weset();
      byte[] c-camewcasetokenizedscweenname =
          tweettokenstweamsewiawizew.gettweettokenstweamsewiawizew()
              .sewiawize(scweennametokenstweam);

      withtokenstweamfiewd(
          fiewdname, mya
          c-camewcasetokenizedscweennametext.isempty()
              ? nyowmawizedscweenname : camewcasetokenizedscweennametext, 😳😳😳
          c-camewcasetokenizedscweenname);
    } catch (ioexception e) {
      w-wog.ewwow("twittewtokenstweam s-sewiawization ewwow! :3 couwd nyot sewiawize: " + s-scweenname);
      s-sewiawize_faiwuwe_count_nonpenguin_dependent.incwement();
    }
    wetuwn t-this;
  }

  p-pwivate void addnamedentityfiewds(
      set<paiw<eawwybiwdfiewdconstant, >_< stwing>> f-fiewdsappwied, 🥺
      eawwybiwdfiewdconstant nyameonwyfiewd, (ꈍᴗꈍ)
      eawwybiwdfiewdconstant n-nyamewithtypefiewd, rawr x3
      stwing nyame, (U ﹏ U)
      nyamedentitycontext context) {
    withonetimestwingfiewd(fiewdsappwied, ( ͡o ω ͡o ) n-nyameonwyfiewd, 😳😳😳 n-nyame, 🥺 fawse);
    i-if (context.issetentity_type()) {
      withonetimestwingfiewd(fiewdsappwied, òωó nyamewithtypefiewd, XD
          fowmatnamedentitystwing(name, XD context.getentity_type()), ( ͡o ω ͡o ) t-twue);
    }
  }

  pwivate void withonetimestwingfiewd(
      s-set<paiw<eawwybiwdfiewdconstant, >w< stwing>> f-fiewdsappwied, mya e-eawwybiwdfiewdconstant fiewd, (ꈍᴗꈍ)
      stwing vawue, -.- boowean addtofacets) {
    paiw<eawwybiwdfiewdconstant, stwing> f-fiewdvawuepaiw = n-nyew paiw<>(fiewd, vawue);
    if (!fiewdsappwied.contains(fiewdvawuepaiw)) {
      i-if (addtofacets) {
        addfacetskipwist(fiewd.getfiewdname());
      }
      withstwingfiewd(fiewd.getfiewdname(), (⑅˘꒳˘) v-vawue);
      f-fiewdsappwied.add(fiewdvawuepaiw);
    }
  }

  p-pwivate stwing fowmatnamedentitystwing(stwing n-name, (U ﹏ U) w-whoweentitytype t-type) {
    wetuwn stwing.fowmat("%s:%s", σωσ name, t-type).towowewcase();
  }

  /**
   * s-set whethew s-set wat_won_csf_fiewd o-ow not b-befowe buiwd
   * i-if wat_won_csf_fiewd is nyot s-set dewibewatewy. :3
   *
   * @see #pwepawetobuiwd()
   */
  p-pubwic e-eawwybiwdthwiftdocumentbuiwdew setaddwatwoncsf(boowean isset) {
    a-addwatwoncsf = isset;
    wetuwn this;
  }

  /**
   * s-set if add encoded tweet featuwe fiewd i-in the end. /(^•ω•^)
   *
   * @see #pwepawetobuiwd()
   */
  p-pubwic eawwybiwdthwiftdocumentbuiwdew setaddencodedtweetfeatuwes(boowean isset) {
    a-addencodedtweetfeatuwes = i-isset;
    wetuwn this;
  }

  @ovewwide
  p-pwotected void p-pwepawetobuiwd() {
    if (!issetwatwoncsf && addwatwoncsf) {
      // in wucene a-awchives, σωσ this c-csf is nyeeded wegawdwess of whethew geowocation i-is set. (U ᵕ U❁)
      w-withwatwoncsf(geoutiw.iwwegaw_watwon, 😳 geoutiw.iwwegaw_watwon);
    }

    if (addencodedtweetfeatuwes) {
      // a-add encoded_tweet_featuwes befowe buiwding the document. ʘwʘ
      withbytesfiewd(
          eawwybiwdfiewdconstant.encoded_tweet_featuwes_fiewd.getfiewdname(), (⑅˘꒳˘)
          eawwybiwdencodedfeatuwesutiw.tobytesfowthwiftdocument(encodedtweetfeatuwes));
    }

    i-if (extendedencodedtweetfeatuwes != nyuww) {
      // add extended_encoded_tweet_featuwes befowe b-buiwding the d-document. ^•ﻌ•^
      w-withbytesfiewd(
          eawwybiwdfiewdconstant.extended_encoded_tweet_featuwes_fiewd.getfiewdname(), nyaa~~
          e-eawwybiwdencodedfeatuwesutiw.tobytesfowthwiftdocument(extendedencodedtweetfeatuwes));
    }
  }

  p-pwivate static b-boowean isnotbwank(stwing v-vawue) {
    wetuwn v-vawue != nyuww && !vawue.isempty();
  }

  pwivate static boowean isnotempty(wist<?> v-vawue) {
    w-wetuwn vawue != n-nyuww && !vawue.isempty();
  }
}
