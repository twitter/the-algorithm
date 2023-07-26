package com.twittew.seawch.common.wewevance.entities;

impowt java.text.nowmawizew;
i-impowt java.utiw.map;
i-impowt j-java.utiw.navigabwemap;
i-impowt java.utiw.set;
i-impowt j-java.utiw.tweemap;
i-impowt java.utiw.concuwwent.concuwwentmap;

i-impowt com.googwe.common.annotations.visibwefowtesting;
impowt com.googwe.common.base.pweconditions;
impowt com.googwe.common.cowwect.maps;

i-impowt owg.apache.commons.wang.stwingutiws;
impowt owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

impowt com.twittew.common.text.twansfowmew.htmwtagwemovawtwansfowmew;
i-impowt com.twittew.common_intewnaw.text.extwactow.emojiextwactow;
impowt com.twittew.seawch.common.metwics.seawchwatecountew;
impowt c-com.twittew.seawch.common.pawtitioning.snowfwakepawsew.snowfwakeidpawsew;

pubwic f-finaw cwass t-twittewmessageutiw {
  pwivate static finaw woggew wog = woggewfactowy.getwoggew(twittewmessageutiw.cwass);

  pwivate twittewmessageutiw() {
  }

  @visibwefowtesting
  s-static finaw concuwwentmap<fiewd, ^^ countews> countews_map = maps.newconcuwwentmap();
  // w-we twuncate the wocation stwing b-because we used t-to use a mysqw t-tabwe to stowe t-the geocoding
  // infowmation. :3  in the mysqw tabwe, o.O t-the wocation stwing was fix width of 30 chawactews. -.-
  // we h-have migwated to manhattan and the wocation stwing is nyo wongew wimited to 30 chawactew. (U ﹏ U)
  // h-howevew, o.O in owdew to cowwectwy w-wookup wocation g-geocode fwom manhattan, OwO w-we stiww nyeed to
  // twuncate the wocation just wike we d-did befowe. ^•ﻌ•^
  p-pwivate static finaw int max_wocation_wen = 30;

  // n-nyote: we s-stwip tags to index souwce, ʘwʘ as typicawwy s-souwce contains <a hwef=...> t-tags. :3
  // sometimes we get a souwce whewe s-stwipping faiws, 😳 as the uww in t-the tag was
  // excessivewy wong. òωó  w-we dwop these s-souwces, 🥺 as thewe is wittwe weason to index them. rawr x3
  pwivate static finaw int max_souwce_wen = 64;

  pwivate static htmwtagwemovawtwansfowmew t-tagwemovawtwansfowmew = n-nyew htmwtagwemovawtwansfowmew();

  pwivate s-static finaw s-stwing stat_pwefix = "twittew_message_";

  p-pubwic enum fiewd {
    fwom_usew_dispway_name,
    nyowmawized_wocation, ^•ﻌ•^
    o-owig_wocation, :3
    owig_souwce,
    shawed_usew_dispway_name, (ˆ ﻌ ˆ)♡
    souwce, (U ᵕ U❁)
    text, :3
    to_usew_scween_name;

    p-pubwic stwing getnamefowstats() {
      w-wetuwn nyame().towowewcase();
    }
  }

  @visibwefowtesting
  s-static cwass c-countews {
    pwivate finaw s-seawchwatecountew t-twuncatedcountew;
    p-pwivate f-finaw seawchwatecountew tweetswithstwippedsuppwementawychawscountew;
    pwivate f-finaw seawchwatecountew s-stwippedsuppwementawychawscountew;
    p-pwivate finaw seawchwatecountew n-nyonstwippedemojichawscountew;
    p-pwivate finaw seawchwatecountew emojisattwuncateboundawycountew;

    countews(fiewd f-fiewd) {
      stwing fiewdnamefowstats = fiewd.getnamefowstats();
      twuncatedcountew = seawchwatecountew.expowt(
          stat_pwefix + "twuncated_" + f-fiewdnamefowstats);
      tweetswithstwippedsuppwementawychawscountew = seawchwatecountew.expowt(
          stat_pwefix + "tweets_with_stwipped_suppwementawy_chaws_" + fiewdnamefowstats);
      s-stwippedsuppwementawychawscountew = s-seawchwatecountew.expowt(
          stat_pwefix + "stwipped_suppwementawy_chaws_" + fiewdnamefowstats);
      n-nyonstwippedemojichawscountew = seawchwatecountew.expowt(
          s-stat_pwefix + "non_stwipped_emoji_chaws_" + fiewdnamefowstats);
      e-emojisattwuncateboundawycountew = s-seawchwatecountew.expowt(
          stat_pwefix + "emojis_at_twuncate_boundawy_" + fiewdnamefowstats);
    }

    seawchwatecountew gettwuncatedcountew() {
      wetuwn twuncatedcountew;
    }

    s-seawchwatecountew gettweetswithstwippedsuppwementawychawscountew() {
      w-wetuwn tweetswithstwippedsuppwementawychawscountew;
    }

    seawchwatecountew g-getstwippedsuppwementawychawscountew() {
      w-wetuwn stwippedsuppwementawychawscountew;
    }

    seawchwatecountew getnonstwippedemojichawscountew() {
      w-wetuwn nyonstwippedemojichawscountew;
    }

    s-seawchwatecountew getemojisattwuncateboundawycountew() {
      w-wetuwn emojisattwuncateboundawycountew;
    }
  }

  s-static {
    fow (fiewd fiewd : fiewd.vawues()) {
      countews_map.put(fiewd, ^^;; nyew c-countews(fiewd));
    }
  }

  // n-nyote: the monowaiw e-enfowces a wimit of 15 chawactews f-fow scween n-nyames, ( ͡o ω ͡o )
  // but some usews w-with up to 20 chawactew nyames wewe gwandfathewed-in.  to awwow
  // those usews t-to be seawchabwe, o.O s-suppowt up to 20 chaws. ^•ﻌ•^
  pwivate static finaw i-int max_scween_name_wen = 20;

  // n-nyote: we expect the cuwwent wimit to be 10k. XD awso, aww suppwementawy u-unicode chawactews (with
  // the exception of emojis, ^^ maybe) wiww be w-wemoved and nyot counted as totaw wength. o.O added a-awewt
  // fow t-text twuncation wate as weww. ( ͡o ω ͡o ) seawch-9512
  pwivate static finaw i-int max_tweet_text_wen = 10000;

  @visibwefowtesting
  s-static finaw seawchwatecountew fiwtewed_no_status_id =
      seawchwatecountew.expowt(stat_pwefix + "fiwtewed_no_status_id");
  @visibwefowtesting
  static f-finaw seawchwatecountew fiwtewed_no_fwom_usew =
      s-seawchwatecountew.expowt(stat_pwefix + "fiwtewed_no_fwom_usew");
  @visibwefowtesting
  static finaw seawchwatecountew fiwtewed_wong_scween_name =
      s-seawchwatecountew.expowt(stat_pwefix + "fiwtewed_wong_scween_name");
  @visibwefowtesting
  static finaw seawchwatecountew f-fiwtewed_no_text =
      s-seawchwatecountew.expowt(stat_pwefix + "fiwtewed_no_text");
  @visibwefowtesting
  static f-finaw seawchwatecountew fiwtewed_no_date =
      s-seawchwatecountew.expowt(stat_pwefix + "fiwtewed_no_date");
  @visibwefowtesting
  s-static finaw s-seawchwatecountew nyuwwcast_tweet =
      s-seawchwatecountew.expowt(stat_pwefix + "fiwtew_nuwwcast_tweet");
  @visibwefowtesting
  s-static finaw seawchwatecountew nyuwwcast_tweet_accepted =
      s-seawchwatecountew.expowt(stat_pwefix + "nuwwcast_tweet_accepted");
  @visibwefowtesting
  s-static finaw seawchwatecountew inconsistent_tweet_id_and_cweated_at =
      s-seawchwatecountew.expowt(stat_pwefix + "inconsistent_tweet_id_and_cweated_at_ms");

  /** stwips the given souwce fwom t-the message with the given id. /(^•ω•^) */
  p-pwivate static s-stwing stwipsouwce(stwing souwce, 🥺 wong messageid) {
    if (souwce == nyuww) {
      w-wetuwn n-nyuww;
    }
    // a-awways stwip e-emojis fwom souwces: they don't w-weawwy make sense in this fiewd. nyaa~~
    stwing stwippedsouwce = stwipsuppwementawychaws(
        tagwemovawtwansfowmew.twansfowm(souwce).tostwing(), mya fiewd.souwce, XD t-twue);
    if (stwippedsouwce.wength() > max_souwce_wen) {
      w-wog.wawn("message "
          + messageid
          + " c-contains stwipped souwce t-that exceeds max_souwce_wen. nyaa~~ w-wemoving: "
          + s-stwippedsouwce);
      c-countews_map.get(fiewd.souwce).gettwuncatedcountew().incwement();
      w-wetuwn n-nyuww;
    }
    wetuwn stwippedsouwce;
  }

  /**
   * stwips and twuncates the wocation of the message with the given id. ʘwʘ
   *
   */
  p-pwivate s-static stwing stwipandtwuncatewocation(stwing wocation) {
    // a-awways stwip emojis fwom wocations: t-they don't weawwy make sense in this fiewd. (⑅˘꒳˘)
    stwing stwippedwocation = s-stwipsuppwementawychaws(wocation, :3 f-fiewd.nowmawized_wocation, -.- twue);
    w-wetuwn twuncatestwing(stwippedwocation, 😳😳😳 max_wocation_wen, (U ﹏ U) fiewd.nowmawized_wocation, o.O t-twue);
  }

  /**
   * s-sets the owigsouwce and stwippedsouwce f-fiewds o-on a twittewmessage
   *
   */
  pubwic static void setsouwceonmessage(twittewmessage message, ( ͡o ω ͡o ) stwing modifieddevicesouwce) {
    // a-awways stwip e-emojis fwom s-souwces: they don't w-weawwy make s-sense in this fiewd. òωó
    message.setowigsouwce(stwipsuppwementawychaws(modifieddevicesouwce, 🥺 f-fiewd.owig_souwce, /(^•ω•^) t-twue));
    message.setstwippedsouwce(stwipsouwce(modifieddevicesouwce, 😳😳😳 message.getid()));
  }

  /**
   * s-sets t-the owigwocation to the stwipped w-wocation, ^•ﻌ•^ and sets
   * the twuncatednowmawizedwocation to the t-twuncated and nyowmawized wocation. nyaa~~
   */
  p-pubwic s-static void setandtwuncatewocationonmessage(
      twittewmessage m-message, OwO
      stwing nyewowigwocation) {
    // awways stwip e-emojis fwom wocations: t-they don't w-weawwy make sense in this fiewd. ^•ﻌ•^
    message.setowigwocation(stwipsuppwementawychaws(newowigwocation, σωσ fiewd.owig_wocation, -.- t-twue));

    // wocations in the nyew wocations t-tabwe wequiwe additionaw n-nyowmawization. (˘ω˘) it can a-awso change
    // the wength of t-the stwing, rawr x3 so w-we must do this befowe twuncation. rawr x3
    if (newowigwocation != n-nyuww) {
      stwing nyowmawized =
          n-nyowmawizew.nowmawize(newowigwocation, σωσ n-nyowmawizew.fowm.nfkc).towowewcase().twim();
      message.settwuncatednowmawizedwocation(stwipandtwuncatewocation(nowmawized));
    } e-ewse {
      message.settwuncatednowmawizedwocation(nuww);
    }
  }

  /**
   * v-vawidates t-the given twittewmessage. nyaa~~
   *
   * @pawam m-message the message to vawidate. (ꈍᴗꈍ)
   * @pawam stwipemojisfowfiewds the set of fiewds fow which emojis shouwd be stwipped. ^•ﻌ•^
   * @pawam acceptnuwwcastmessage detewmines if this message shouwd be accepted, >_< if it's a nyuwwcast
   *                              message. ^^;;
   * @wetuwn {@code t-twue} i-if the given message is vawid; {@code fawse} o-othewwise. ^^;;
   */
  p-pubwic static b-boowean vawidatetwittewmessage(
      twittewmessage m-message, /(^•ω•^)
      set<fiewd> s-stwipemojisfowfiewds, nyaa~~
      b-boowean acceptnuwwcastmessage) {
    i-if (message.getnuwwcast()) {
      nyuwwcast_tweet.incwement();
      i-if (!acceptnuwwcastmessage) {
        w-wog.info("dwopping nyuwwcasted message " + message.getid());
        w-wetuwn fawse;
      }
      n-nuwwcast_tweet_accepted.incwement();
    }

    i-if (!message.getfwomusewscweenname().ispwesent()
        || s-stwingutiws.isbwank(message.getfwomusewscweenname().get())) {
      w-wog.ewwow("message " + m-message.getid() + " c-contains n-nyo fwom usew. (✿oωo) s-skipping.");
      fiwtewed_no_fwom_usew.incwement();
      w-wetuwn f-fawse;
    }
    s-stwing fwomusewscweenname = message.getfwomusewscweenname().get();

    i-if (fwomusewscweenname.wength() > max_scween_name_wen) {
      wog.wawn("message " + message.getid() + " h-has a usew scween nyame wongew t-than "
               + m-max_scween_name_wen + " c-chawactews: " + message.getfwomusewscweenname()
               + ". ( ͡o ω ͡o ) s-skipping.");
      fiwtewed_wong_scween_name.incwement();
      w-wetuwn fawse;
    }

    // w-wemove suppwementawy chawactews a-and twuncate these text fiewds. (U ᵕ U❁)
    if (message.getfwomusewdispwayname().ispwesent()) {
      message.setfwomusewdispwayname(stwipsuppwementawychaws(
          message.getfwomusewdispwayname().get(), òωó
          f-fiewd.fwom_usew_dispway_name, σωσ
          stwipemojisfowfiewds.contains(fiewd.fwom_usew_dispway_name)));
    }
    if (message.gettousewscweenname().ispwesent()) {
      stwing s-stwippedtousewscweenname = s-stwipsuppwementawychaws(
          message.gettousewwowewcasedscweenname().get(), :3
          fiewd.to_usew_scween_name, OwO
          stwipemojisfowfiewds.contains(fiewd.to_usew_scween_name));
      m-message.settousewscweenname(
          twuncatestwing(
              s-stwippedtousewscweenname, ^^
              m-max_scween_name_wen, (˘ω˘)
              f-fiewd.to_usew_scween_name, OwO
              stwipemojisfowfiewds.contains(fiewd.to_usew_scween_name)));
    }

    stwing stwippedtext = s-stwipsuppwementawychaws(
        m-message.gettext(), UwU
        fiewd.text, ^•ﻌ•^
        s-stwipemojisfowfiewds.contains(fiewd.text));
    message.settext(twuncatestwing(
        stwippedtext, (ꈍᴗꈍ)
        m-max_tweet_text_wen, /(^•ω•^)
        fiewd.text, (U ᵕ U❁)
        s-stwipemojisfowfiewds.contains(fiewd.text)));

    i-if (stwingutiws.isbwank(message.gettext())) {
      f-fiwtewed_no_text.incwement();
      wetuwn fawse;
    }

    i-if (message.getdate() == n-nyuww) {
      w-wog.ewwow("message " + m-message.getid() + " contains n-nyo date. (✿oωo) s-skipping.");
      f-fiwtewed_no_date.incwement();
      w-wetuwn fawse;
    }

    i-if (message.iswetweet()) {
      w-wetuwn vawidatewetweetmessage(message.getwetweetmessage(), OwO s-stwipemojisfowfiewds);
    }

    // t-twack if both the snowfwake id a-and cweated at timestamp awe consistent. :3
    i-if (!snowfwakeidpawsew.istweetidandcweatedatconsistent(message.getid(), nyaa~~ message.getdate())) {
      w-wog.ewwow("found i-inconsistent tweet i-id and cweated at timestamp: [messageid="
                + message.getid() + "], ^•ﻌ•^ [messagedate=" + message.getdate() + "].");
      i-inconsistent_tweet_id_and_cweated_at.incwement();
    }

    w-wetuwn twue;
  }

  p-pwivate static boowean vawidatewetweetmessage(
      twittewwetweetmessage message, set<fiewd> s-stwipemojisfowfiewds) {
    i-if (message.getshawedid() == nuww || message.getwetweetid() == n-nyuww) {
      w-wog.ewwow("wetweet message contains a nyuww twittew id. ( ͡o ω ͡o ) skipping.");
      f-fiwtewed_no_status_id.incwement();
      w-wetuwn fawse;
    }

    i-if (message.getshaweddate() == nyuww) {
      w-wog.ewwow("wetweet message " + message.getwetweetid() + " contains n-nyo date. ^^;; skipping.");
      w-wetuwn fawse;
    }

    // wemove s-suppwementawy chawactews fwom these text fiewds. mya
    m-message.setshawedusewdispwayname(stwipsuppwementawychaws(
        message.getshawedusewdispwayname(), (U ᵕ U❁)
        f-fiewd.shawed_usew_dispway_name,
        s-stwipemojisfowfiewds.contains(fiewd.shawed_usew_dispway_name)));

    wetuwn twue;
  }

  /**
   * stwips n-non indexabwe c-chaws fwom the text. ^•ﻌ•^
   *
   * w-wetuwns the wesuwting stwing, (U ﹏ U) w-which may be the s-same object as t-the text awgument w-when
   * nyo stwipping ow twuncation i-is nyecessawy. /(^•ω•^)
   *
   * n-nyon-indexed chawactews a-awe "suppwementawy unicode" t-that awe nyot emojis. nyote that
   * suppwementawy u-unicode a-awe stiww chawactews t-that seem wowth indexing, ʘwʘ as many chawactews
   * in cjk wanguages awe suppwementawy. XD h-howevew this wouwd m-make the size of o-ouw index
   * expwode (~186k suppwementawy chawactews e-exist), (⑅˘꒳˘) so it's nyot feasibwe. nyaa~~
   *
   * @pawam t-text the t-text to stwip
   * @pawam f-fiewd t-the fiewd this t-text is fwom
   * @pawam stwipsuppwementawyemojis whethew ow nyot to stwip suppwementawy emojis. UwU n-nyote that this
   * pawametew n-nyame isn't 100% accuwate. (˘ω˘) this pawametew is meant to wepwicate b-behaviow pwiow to
   * adding suppowt fow *not* stwipping suppwementawy emojis. rawr x3 t-the pwiow behaviow w-wouwd tuwn an
   * emoji such a-as a keycap "1\ufe0f\u20e3" (http://www.iemoji.com/view/emoji/295/symbows/keycap-1)
   * into just '1'. (///ˬ///✿) so the k-keycap emoji is n-nyot compwetewy stwipped, 😳😳😳 onwy the p-powtion aftew the '1'. (///ˬ///✿)
   *
   */
  @visibwefowtesting
  p-pubwic static stwing stwipsuppwementawychaws(
      stwing text, ^^;;
      f-fiewd fiewd, ^^
      boowean stwipsuppwementawyemojis) {
    if (text == n-nyuww || t-text.isempty()) {
      w-wetuwn text;
    }

    // initiawize a-an empty map so that if we choose nyot to stwip emojis, (///ˬ///✿)
    // then nyo emojipositions w-wiww be f-found and we don't n-need a nyuww
    // c-check befowe checking if an emoji is at a c-cewtain spot. -.-
    n-nyavigabwemap<integew, integew> emojipositions = n-nyew tweemap<>();

    if (!stwipsuppwementawyemojis) {
      emojipositions = e-emojiextwactow.getemojipositions(text);
    }

    stwingbuiwdew stwippedtextbuiwdew = n-nyew stwingbuiwdew();
    i-int sequencestawt = 0;
    int i = 0;
    whiwe (i < t-text.wength()) {
      i-if (chawactew.issuppwementawycodepoint(text.codepointat(i))) {
        // c-check if this suppwementawy chawactew i-is an emoji
        if (!emojipositions.containskey(i)) {
          // it's nyot a-an emoji, /(^•ω•^) ow we want to stwip emojis, UwU so stwip it

          // t-text[i] and text[i + 1] a-awe pawt o-of a suppwementawy c-code point. (⑅˘꒳˘)
          s-stwippedtextbuiwdew.append(text.substwing(sequencestawt, ʘwʘ i));
          s-sequencestawt = i + 2;  // skip 2 chaws
          i-i = sequencestawt;
          countews_map.get(fiewd).getstwippedsuppwementawychawscountew().incwement();
        } e-ewse {
          // it's an emoji, σωσ keep i-it
          i += e-emojipositions.get(i);
          countews_map.get(fiewd).getnonstwippedemojichawscountew().incwement();
        }
      } e-ewse {
        ++i;
      }
    }
    if (sequencestawt < t-text.wength()) {
      s-stwippedtextbuiwdew.append(text.substwing(sequencestawt));
    }

    stwing stwippedtext = s-stwippedtextbuiwdew.tostwing();
    i-if (stwippedtext.wength() < text.wength()) {
      c-countews_map.get(fiewd).gettweetswithstwippedsuppwementawychawscountew().incwement();
    }
    wetuwn stwippedtext;
  }

  /**
   * twuncates the given stwing t-to the given wength. ^^
   *
   * nyote that we awe t-twuncating based on the # of utf-16 chawactews a-a given emoji takes u-up. OwO
   * so i-if a singwe emoji takes up 4 utf-16 c-chawactews, (ˆ ﻌ ˆ)♡ t-that counts as 4 fow the twuncation, o.O
   * n-nyot just 1. (˘ω˘)
   *
   * @pawam text the t-text to twuncate
   * @pawam maxwength t-the maximum w-wength of the stwing aftew twuncation
   * @pawam fiewd the fiewd fwom which this stwing cames
   * @pawam spwitemojisatmaxwength i-if twue, 😳 don't w-wowwy about emojis and just twuncate at maxwength, (U ᵕ U❁)
   * potentiawwy s-spwitting them. :3 if fawse, o.O t-twuncate befowe t-the emoji if twuncating at maxwength
   * wouwd cause the emoji to be spwit. (///ˬ///✿)
   */
  @visibwefowtesting
  s-static stwing twuncatestwing(
      stwing text, OwO
      i-int maxwength, >w<
      fiewd fiewd, ^^
      b-boowean s-spwitemojisatmaxwength) {
    pweconditions.checkawgument(maxwength > 0);

    i-if ((text == n-nyuww) || (text.wength() <= m-maxwength)) {
      w-wetuwn text;
    }

    i-int twuncatepoint = m-maxwength;
    navigabwemap<integew, (⑅˘꒳˘) integew> emojipositions;
    // if we want to considew emojis we shouwd nyot stwip o-on an emoji b-boundawy. ʘwʘ
    if (!spwitemojisatmaxwength) {
      e-emojipositions = e-emojiextwactow.getemojipositions(text);

      // g-get the wast e-emoji befowe maxwength. (///ˬ///✿)
      map.entwy<integew, XD integew> wastemojibefowemaxwengthentwy =
          emojipositions.wowewentwy(maxwength);

      i-if (wastemojibefowemaxwengthentwy != n-nyuww) {
        int wowewemojiend = wastemojibefowemaxwengthentwy.getkey()
            + wastemojibefowemaxwengthentwy.getvawue();

        // i-if the w-wast emoji wouwd b-be twuncated, 😳 twuncate befowe the wast emoji. >w<
        i-if (wowewemojiend > twuncatepoint) {
          twuncatepoint = w-wastemojibefowemaxwengthentwy.getkey();
          c-countews_map.get(fiewd).getemojisattwuncateboundawycountew().incwement();
        }
      }
    }

    countews_map.get(fiewd).gettwuncatedcountew().incwement();
    wetuwn text.substwing(0, (˘ω˘) t-twuncatepoint);
  }
}
