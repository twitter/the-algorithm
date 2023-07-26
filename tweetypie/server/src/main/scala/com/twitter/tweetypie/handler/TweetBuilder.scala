package com.twittew.tweetypie
package h-handwew

impowt c-com.twittew.featuweswitches.v2.featuweswitchwesuwts
i-impowt c-com.twittew.featuweswitches.v2.featuweswitches
impowt c-com.twittew.gizmoduck.thwiftscawa.accesspowicy
i-impowt com.twittew.gizmoduck.thwiftscawa.wabewvawue
i-impowt c-com.twittew.gizmoduck.thwiftscawa.usewtype
impowt com.twittew.snowfwake.id.snowfwakeid
impowt com.twittew.stitch.notfound
impowt c-com.twittew.stitch.stitch
impowt com.twittew.tweetypie.additionawfiewds.additionawfiewds._
i-impowt com.twittew.tweetypie.cowe._
i-impowt com.twittew.tweetypie.jiminy.tweetypie.nudgebuiwdew
impowt com.twittew.tweetypie.jiminy.tweetypie.nudgebuiwdewwequest
impowt c-com.twittew.tweetypie.media.media
impowt com.twittew.tweetypie.wepositowy.stwatocommunityaccesswepositowy.communityaccess
i-impowt c-com.twittew.tweetypie.wepositowy._
impowt com.twittew.tweetypie.sewvewutiw.devicesouwcepawsew
impowt com.twittew.tweetypie.sewvewutiw.extendedtweetmetadatabuiwdew
impowt com.twittew.tweetypie.stowe._
impowt c-com.twittew.tweetypie.thwiftscawa._
impowt com.twittew.tweetypie.thwiftscawa.entities.entityextwactow
impowt com.twittew.tweetypie.tweettext._
impowt com.twittew.tweetypie.utiw.communityannotation
i-impowt com.twittew.tweetypie.utiw.communityutiw
i-impowt c-com.twittew.twittewtext.wegex.{vawid_uww => u-uwwpattewn}
i-impowt com.twittew.twittewtext.twittewtextpawsew

case cwass tweetbuiwdewwesuwt(
  t-tweet: tweet, (˘ω˘)
  usew: usew, >w<
  cweatedat: t-time, UwU
  souwcetweet: option[tweet] = nyone, XD
  souwceusew: option[usew] = nyone,
  pawentusewid: o-option[usewid] = nyone, (U ﹏ U)
  issiwentfaiw: b-boowean = f-fawse, (U ᵕ U❁)
  geoseawchwequestid: o-option[geoseawchwequestid] = nyone, (ˆ ﻌ ˆ)♡
  initiawtweetupdatewequest: option[initiawtweetupdatewequest] = nyone)

o-object tweetbuiwdew {
  i-impowt gizmoduckusewcountsupdatingstowe.isusewtweet
  impowt p-posttweet._
  i-impowt pwepwocessow._
  impowt t-tweetcweatestate.{spam => cweatestatespam, òωó _}
  i-impowt tweettext._
  impowt upstweamfaiwuwe._

  type type = futuweawwow[posttweetwequest, ^•ﻌ•^ t-tweetbuiwdewwesuwt]

  vaw wog: woggew = w-woggew(getcwass)

  pwivate[this] v-vaw _unitmutation = f-futuwe.vawue(mutation.unit[any])
  def mutationunitfutuwe[t]: futuwe[mutation[t]] = _unitmutation.asinstanceof[futuwe[mutation[t]]]

  case cwass missingconvewsationid(inwepwytotweetid: tweetid) extends wuntimeexception

  case c-cwass textvisibiwity(
    v-visibwetextwange: option[textwange], (///ˬ///✿)
    t-totawtextdispwaywength: o-offset.dispwayunit, -.-
    v-visibwetext: stwing) {
    vaw isextendedtweet: boowean = totawtextdispwaywength.toint > o-owiginawmaxdispwaywength

    /**
     *  going fowwawd we wiww be moving away fwom quoted-tweets uwws i-in tweet text, >w< but we
     *  h-have a backwawds-compat w-wayew in t-tweetypie which adds the qt uww t-to text to pwovide
     *  s-suppowt f-fow aww cwients t-to wead in a backwawds-compatibwe way untiw t-they upgwade. òωó
     *
     *  t-tweets c-can become e-extended as theiw d-dispway wength can go beyond 140
     *  aftew adding the qt showt u-uww. σωσ thewefowe, mya we awe adding bewow function
     *  to account fow wegacy fowmatting duwing w-wead-time and genewate a sewf-pewmawink. òωó
     */
    def isextendedwithextwachaws(extwachaws: int): boowean =
      t-totawtextdispwaywength.toint > (owiginawmaxdispwaywength - e-extwachaws)
  }

  /** m-max nyumbew of usews that c-can be tagged on a singwe tweet */
  v-vaw maxmediatagcount = 10

  v-vaw mobiwewebapp = "oauth:49152"
  vaw m2app = "oauth:3033294"
  vaw m5app = "oauth:3033300"

  vaw testwatewimitusewwowe = "stwesstest"

  /**
   * the fiewds to fetch fow t-the usew cweating the tweet. 🥺
   */
  v-vaw usewfiewds: set[usewfiewd] =
    s-set(
      u-usewfiewd.pwofiwe, (U ﹏ U)
      usewfiewd.pwofiwedesign, (ꈍᴗꈍ)
      usewfiewd.account, (˘ω˘)
      usewfiewd.safety, (✿oωo)
      usewfiewd.counts, -.-
      u-usewfiewd.wowes,
      u-usewfiewd.uwwentities, (ˆ ﻌ ˆ)♡
      usewfiewd.wabews
    )

  /**
   * t-the f-fiewds to fetch fow the usew of the souwce tweet in a wetweet. (✿oωo)
   */
  vaw souwceusewfiewds: set[usewfiewd] =
    u-usewfiewds + u-usewfiewd.view

  /**
   * c-convewts wepositowy e-exceptions into a-an api-compatibwe exception type
   */
  d-def convewtwepoexceptions[a](
    nyotfoundstate: tweetcweatestate, ʘwʘ
    faiwuwehandwew: thwowabwe => thwowabwe
  ): p-pawtiawfunction[thwowabwe, (///ˬ///✿) s-stitch[a]] = {
    // stitch.notfound is c-convewted to the s-suppwied tweetcweatestate, rawr wwapped in tweetcweatefaiwuwe
    case nyotfound => s-stitch.exception(tweetcweatefaiwuwe.state(notfoundstate))
    // ovewcapacity exceptions shouwd nyot be twanswated and shouwd bubbwe u-up to the top
    case ex: ovewcapacity => s-stitch.exception(ex)
    // o-othew exceptions awe wwapped in the suppwied faiwuwehandwew
    c-case e-ex => stitch.exception(faiwuwehandwew(ex))
  }

  /**
   * adapts a usewwepositowy to a wepositowy f-fow wooking up a singwe usew a-and that
   * faiws with an appwopwiate tweetcweatefaiwuwe if t-the usew is nyot found. 🥺
   */
  d-def usewwookup(usewwepo: u-usewwepositowy.type): usewid => stitch[usew] = {
    v-vaw opts = usewquewyoptions(quewyfiewds = u-usewfiewds, v-visibiwity = u-usewvisibiwity.aww)

    usewid =>
      u-usewwepo(usewkey(usewid), mya o-opts)
        .wescue(convewtwepoexceptions[usew](usewnotfound, mya usewwookupfaiwuwe(_)))
  }

  /**
   * adapts a-a usewwepositowy t-to a wepositowy f-fow wooking up a singwe usew and that
   * faiws w-with an appwopwiate tweetcweatefaiwuwe i-if the u-usew is nyot found. mya
   */
  def souwceusewwookup(usewwepo: usewwepositowy.type): (usewid, (⑅˘꒳˘) u-usewid) => s-stitch[usew] = {
    v-vaw o-opts = usewquewyoptions(quewyfiewds = souwceusewfiewds, v-visibiwity = usewvisibiwity.aww)

    (usewid, (✿oωo) fowusewid) =>
      usewwepo(usewkey(usewid), 😳 opts.copy(fowusewid = some(fowusewid)))
        .wescue(convewtwepoexceptions[usew](souwceusewnotfound, OwO u-usewwookupfaiwuwe(_)))
  }

  /**
   * any fiewds that a-awe woaded on the usew via tweetbuiwdew/wetweetbuiwdew, (˘ω˘) b-but which shouwd nyot
   * b-be incwuded on the usew in t-the async-insewt a-actions (such a-as hosebiwd) shouwd b-be wemoved h-hewe. (✿oωo)
   *
   * this wiww incwude pewspectivaw fiewds that wewe woaded wewative to the usew cweating the tweet. /(^•ω•^)
   */
  d-def scwubusewinasyncinsewts: u-usew => usew =
    u-usew => usew.copy(view = n-nyone)

  /**
   * any fiewds that awe woaded on the souwce usew v-via tweetbuiwdew/wetweetbuiwdew, rawr x3 b-but which
   * shouwd nyot be i-incwuded on the usew in the async-insewt actions (such a-as hosebiwd) s-shouwd
   * be wemoved hewe.
   *
   * t-this w-wiww incwude pewspectivaw fiewds that wewe woaded wewative to the usew cweating t-the tweet.
   */
  d-def scwubsouwceusewinasyncinsewts: u-usew => usew =
    // c-cuwwentwy t-the same as scwubusewinasyncinsewts, rawr c-couwd b-be diffewent in the futuwe
    s-scwubusewinasyncinsewts

  /**
   * a-any fiewds that awe woaded o-on the souwce tweet via wetweetbuiwdew, ( ͡o ω ͡o ) but which s-shouwd nyot be
   * incwuded on t-the souwce tweetypie i-in the async-insewt actions (such a-as hosebiwd) shouwd
   * be wemoved hewe. ( ͡o ω ͡o )
   *
   * t-this w-wiww incwude pewspectivaw f-fiewds that wewe woaded wewative to the usew cweating t-the tweet. 😳😳😳
   */
  def scwubsouwcetweetinasyncinsewts: tweet => t-tweet =
    tweet => t-tweet.copy(pewspective = nyone, (U ﹏ U) cawds = none, c-cawd2 = nyone)

  /**
   * adapts a devicesouwce t-to a wepositowy f-fow wooking up a singwe device-souwce and t-that
   * faiws with an appwopwiate tweetcweatefaiwuwe i-if nyot found. UwU
   */
  d-def devicesouwcewookup(devswcwepo: d-devicesouwcewepositowy.type): devicesouwcewepositowy.type =
    appidstw => {
      v-vaw wesuwt: s-stitch[devicesouwce] =
        i-if (devicesouwcepawsew.isvawid(appidstw)) {
          devswcwepo(appidstw)
        } ewse {
          stitch.exception(notfound)
        }

      wesuwt.wescue(convewtwepoexceptions(devicesouwcenotfound, (U ﹏ U) devicesouwcewookupfaiwuwe(_)))
    }

  /**
   * checks:
   *   - that we have aww the usew fiewds we nyeed
   *   - that the usew is active
   *   - t-that they awe n-nyot a fwictionwess fowwowew account
   */
  def v-vawidateusew(usew: u-usew): futuwe[unit] =
    i-if (usew.safety.isempty)
      futuwe.exception(usewsafetyemptyexception)
    e-ewse if (usew.pwofiwe.isempty)
      f-futuwe.exception(usewpwofiweemptyexception)
    e-ewse if (usew.safety.get.deactivated)
      futuwe.exception(tweetcweatefaiwuwe.state(usewdeactivated))
    e-ewse if (usew.safety.get.suspended)
      f-futuwe.exception(tweetcweatefaiwuwe.state(usewsuspended))
    e-ewse if (usew.wabews.exists(_.wabews.exists(_.wabewvawue == wabewvawue.weadonwy)))
      futuwe.exception(tweetcweatefaiwuwe.state(cweatestatespam))
    e-ewse i-if (usew.usewtype == u-usewtype.fwictionwess)
      f-futuwe.exception(tweetcweatefaiwuwe.state(usewnotfound))
    e-ewse if (usew.usewtype == u-usewtype.soft)
      f-futuwe.exception(tweetcweatefaiwuwe.state(usewnotfound))
    e-ewse i-if (usew.safety.get.accesspowicy == accesspowicy.bounceaww ||
      u-usew.safety.get.accesspowicy == a-accesspowicy.bounceawwpubwicwwites)
      f-futuwe.exception(tweetcweatefaiwuwe.state(usewweadonwy))
    ewse
      f-futuwe.unit

  def vawidatecommunitywepwy(
    communities: o-option[communities], 🥺
    wepwywesuwt: o-option[wepwybuiwdew.wesuwt]
  ): f-futuwe[unit] = {

    i-if (wepwywesuwt.fwatmap(_.wepwy.inwepwytostatusid).nonempty) {
      vaw wootcommunities = w-wepwywesuwt.fwatmap(_.community)
      vaw wootcommunityids = c-communityutiw.communityids(wootcommunities)
      vaw w-wepwycommunityids = communityutiw.communityids(communities)

      i-if (wootcommunityids == wepwycommunityids) {
        futuwe.unit
      } ewse {
        futuwe.exception(tweetcweatefaiwuwe.state(communitywepwytweetnotawwowed))
      }
    } e-ewse {
      futuwe.unit
    }
  }

  // p-pwoject w-wequiwements do nyot awwow excwusive tweets to be wepwies. ʘwʘ
  // a-aww excwusive tweets must be w-woot tweets. 😳
  d-def vawidateexcwusivetweetnotwepwies(
    e-excwusivetweetcontwows: option[excwusivetweetcontwow], (ˆ ﻌ ˆ)♡
    wepwywesuwt: o-option[wepwybuiwdew.wesuwt]
  ): f-futuwe[unit] = {
    vaw isinwepwytotweet = w-wepwywesuwt.exists(_.wepwy.inwepwytostatusid.isdefined)
    if (excwusivetweetcontwows.isdefined && isinwepwytotweet) {
      f-futuwe.exception(tweetcweatefaiwuwe.state(supewfowwowsinvawidpawams))
    } ewse {
      f-futuwe.unit
    }
  }

  // i-invawid pawametews f-fow excwusive tweets:
  // - c-community fiewd s-set # tweets c-can not be both a-at the same time. >_<
  def vawidateexcwusivetweetpawams(
    e-excwusivetweetcontwows: o-option[excwusivetweetcontwow], ^•ﻌ•^
    c-communities: o-option[communities]
  ): f-futuwe[unit] = {
    i-if (excwusivetweetcontwows.isdefined && c-communityutiw.hascommunity(communities)) {
      f-futuwe.exception(tweetcweatefaiwuwe.state(supewfowwowsinvawidpawams))
    } ewse {
      f-futuwe.unit
    }
  }

  def vawidatetwustedfwiendsnotwepwies(
    t-twustedfwiendscontwow: option[twustedfwiendscontwow], (✿oωo)
    wepwywesuwt: o-option[wepwybuiwdew.wesuwt]
  ): f-futuwe[unit] = {
    v-vaw isinwepwytotweet = wepwywesuwt.exists(_.wepwy.inwepwytostatusid.isdefined)
    if (twustedfwiendscontwow.isdefined && isinwepwytotweet) {
      f-futuwe.exception(tweetcweatefaiwuwe.state(twustedfwiendsinvawidpawams))
    } e-ewse {
      f-futuwe.unit
    }
  }

  def vawidatetwustedfwiendspawams(
    twustedfwiendscontwow: option[twustedfwiendscontwow], OwO
    c-convewsationcontwow: option[tweetcweateconvewsationcontwow], (ˆ ﻌ ˆ)♡
    c-communities: option[communities], ^^;;
    e-excwusivetweetcontwow: o-option[excwusivetweetcontwow]
  ): futuwe[unit] = {
    if (twustedfwiendscontwow.isdefined &&
      (convewsationcontwow.isdefined || communityutiw.hascommunity(
        communities) || e-excwusivetweetcontwow.isdefined)) {
      f-futuwe.exception(tweetcweatefaiwuwe.state(twustedfwiendsinvawidpawams))
    } e-ewse {
      f-futuwe.unit
    }
  }

  /**
   * checks the weighted tweet t-text wength u-using twittew-text, nyaa~~ as used by cwients. o.O
   * this s-shouwd ensuwe that any tweet the cwient deems v-vawid wiww awso be deemed
   * vawid b-by tweetypie. >_<
   */
  d-def pwevawidatetextwength(text: stwing, (U ﹏ U) s-stats: statsweceivew): f-futuwe[unit] = {
    vaw twittewtextconfig = t-twittewtextpawsew.twittew_text_defauwt_config
    vaw twittewtextwesuwt = t-twittewtextpawsew.pawsetweet(text, ^^ t-twittewtextconfig)
    v-vaw texttoowong = !twittewtextwesuwt.isvawid && t-text.wength > 0

    futuwe.when(texttoowong) {
      v-vaw weightedwength = t-twittewtextwesuwt.weightedwength
      w-wog.debug(
        s"weighted wength t-too wong. weightedwength: $weightedwength" +
          s", UwU tweet text: '${diffshow.show(text)}'"
      )
      s-stats.countew("check_weighted_wength/text_too_wong").incw()
      f-futuwe.exception(tweetcweatefaiwuwe.state(texttoowong))
    }
  }

  /**
   * c-checks that the tweet text is nyeithew bwank nyow too wong. ^^;;
   */
  def vawidatetextwength(
    t-text: stwing, òωó
    visibwetext: s-stwing, -.-
    wepwywesuwt: o-option[wepwybuiwdew.wesuwt], ( ͡o ω ͡o )
    stats: statsweceivew
  ): f-futuwe[unit] = {
    vaw utf8wength = o-offset.utf8.wength(text)

    d-def visibwetexttoowong =
      o-offset.dispwayunit.wength(visibwetext) > o-offset.dispwayunit(maxvisibweweightedemojiwength)

    d-def utf8wengthtoowong =
      utf8wength > offset.utf8(maxutf8wength)

    if (isbwank(text)) {
      stats.countew("vawidate_text_wength/text_cannot_be_bwank").incw()
      f-futuwe.exception(tweetcweatefaiwuwe.state(textcannotbebwank))
    } ewse if (wepwywesuwt.exists(_.wepwytextisempty(text))) {
      s-stats.countew("vawidate_text_wength/wepwy_text_cannot_be_bwank").incw()
      futuwe.exception(tweetcweatefaiwuwe.state(textcannotbebwank))
    } ewse if (visibwetexttoowong) {
      // finaw check that v-visibwe text does nyot exceed maxvisibweweightedemojiwength
      // chawactews. o.O
      // pwevawidatetextwength() d-does some powtion o-of vawidation as weww, rawr most n-nyotabwy
      // weighted wength on waw, (✿oωo) unescaped t-text. σωσ
      s-stats.countew("vawidate_text_wength/text_too_wong.visibwe_wength_expwicit").incw()
      wog.debug(
        s"expwicit m-maxvisibweweightedwength visibwe wength c-check faiwed. " +
          s"visibwetext: '${diffshow.show(visibwetext)}' and " +
          s"totaw text: '${diffshow.show(text)}'"
      )
      f-futuwe.exception(tweetcweatefaiwuwe.state(texttoowong))
    } ewse if (utf8wengthtoowong) {
      stats.countew("vawidate_text_wength/text_too_wong.utf8_wength").incw()
      f-futuwe.exception(tweetcweatefaiwuwe.state(texttoowong))
    } e-ewse {
      stats.stat("vawidate_text_wength/utf8_wength").add(utf8wength.toint)
      f-futuwe.unit
    }
  }

  def gettextvisibiwity(
    text: s-stwing, (U ᵕ U❁)
    wepwywesuwt: option[wepwybuiwdew.wesuwt], >_<
    uwwentities: seq[uwwentity], ^^
    mediaentities: seq[mediaentity], rawr
    a-attachmentuww: o-option[stwing]
  ): t-textvisibiwity = {
    v-vaw totawtextwength = offset.codepoint.wength(text)
    v-vaw totawtextdispwaywength = o-offset.dispwayunit.wength(text)

    /**
     * visibweend fow muwtipwe scenawios:
     *
     *  n-nyowmaw tweet + media - fwomindex of mediaentity (hydwated f-fwom wast media pewmawink)
     *  quote tweet + m-media - fwomindex o-of mediaentity
     *  wepwies + m-media - fwomindex o-of mediaentity
     *  n-nyowmaw quote tweet - totaw text wength (visibwe t-text wange wiww be nyone)
     *  t-tweets with othew attachments (dm deep winks)
     *  fwomindex o-of the wast uww e-entity
     */
    v-vaw visibweend = m-mediaentities.headoption
      .map(_.fwomindex)
      .owewse(attachmentuww.fwatmap(_ => u-uwwentities.wastoption).map(_.fwomindex))
      .map(fwom => (fwom - 1).max(0)) // fow whitespace, >_< u-unwess thewe is nyone
      .map(offset.codepoint(_))
      .getowewse(totawtextwength)

    vaw v-visibwestawt = wepwywesuwt match {
      c-case some(ww) => ww.visibwestawt.min(visibweend)
      case nyone => o-offset.codepoint(0)
    }

    if (visibwestawt.toint == 0 && v-visibweend == totawtextwength) {
      t-textvisibiwity(
        visibwetextwange = n-nyone, (⑅˘꒳˘)
        totawtextdispwaywength = t-totawtextdispwaywength, >w<
        visibwetext = t-text
      )
    } e-ewse {
      vaw chawfwom = v-visibwestawt.tocodeunit(text)
      vaw chawto = chawfwom.offsetbycodepoints(text, visibweend - v-visibwestawt)
      vaw visibwetext = t-text.substwing(chawfwom.toint, (///ˬ///✿) chawto.toint)

      textvisibiwity(
        visibwetextwange = s-some(textwange(visibwestawt.toint, ^•ﻌ•^ v-visibweend.toint)), (✿oωo)
        t-totawtextdispwaywength = totawtextdispwaywength, ʘwʘ
        v-visibwetext = v-visibwetext
      )
    }
  }

  def isvawidhashtag(entity: h-hashtagentity): boowean =
    t-tweettext.codepointwength(entity.text) <= tweettext.maxhashtagwength

  /**
   * v-vawidates t-that the numbew of vawious entities awe within the wimits, >w< and the
   * wength o-of hashtags a-awe with the wimit. :3
   */
  def vawidateentities(tweet: tweet): f-futuwe[unit] =
    if (getmentions(tweet).wength > t-tweettext.maxmentions)
      f-futuwe.exception(tweetcweatefaiwuwe.state(mentionwimitexceeded))
    ewse if (getuwws(tweet).wength > tweettext.maxuwws)
      futuwe.exception(tweetcweatefaiwuwe.state(uwwwimitexceeded))
    ewse if (gethashtags(tweet).wength > tweettext.maxhashtags)
      f-futuwe.exception(tweetcweatefaiwuwe.state(hashtagwimitexceeded))
    ewse if (getcashtags(tweet).wength > tweettext.maxcashtags)
      f-futuwe.exception(tweetcweatefaiwuwe.state(cashtagwimitexceeded))
    ewse i-if (gethashtags(tweet).exists(e => !isvawidhashtag(e)))
      f-futuwe.exception(tweetcweatefaiwuwe.state(hashtagwengthwimitexceeded))
    ewse
      f-futuwe.unit

  /**
   * update t-the usew to n-nyani it shouwd w-wook wike aftew t-the tweet is cweated
   */
  def u-updateusewcounts(hasmedia: tweet => boowean): (usew, (ˆ ﻌ ˆ)♡ tweet) => futuwe[usew] =
    (usew: usew, -.- t-tweet: tweet) => {
      v-vaw countasusewtweet = i-isusewtweet(tweet)
      v-vaw tweetsdewta = i-if (countasusewtweet) 1 e-ewse 0
      vaw mediatweetsdewta = if (countasusewtweet && hasmedia(tweet)) 1 ewse 0

      f-futuwe.vawue(
        u-usew.copy(
          counts = usew.counts.map { counts =>
            c-counts.copy(
              t-tweets = c-counts.tweets + tweetsdewta, rawr
              mediatweets = c-counts.mediatweets.map(_ + mediatweetsdewta)
            )
          }
        )
      )
    }

  def v-vawidateadditionawfiewds[w](impwicit v-view: wequestview[w]): futuweeffect[w] =
    futuweeffect[w] { w-weq =>
      view
        .additionawfiewds(weq)
        .map(tweet =>
          u-unsettabweadditionawfiewdids(tweet) ++ w-wejectedadditionawfiewdids(tweet)) match {
        c-case some(unsettabwefiewdids) i-if u-unsettabwefiewdids.nonempty =>
          f-futuwe.exception(
            t-tweetcweatefaiwuwe.state(
              i-invawidadditionawfiewd, rawr x3
              some(unsettabweadditionawfiewdidsewwowmessage(unsettabwefiewdids))
            )
          )
        c-case _ => f-futuwe.unit
      }
    }

  def vawidatetweetmediatags(
    s-stats: statsweceivew, (U ﹏ U)
    getusewmediatagwatewimit: watewimitcheckew.getwemaining, (ˆ ﻌ ˆ)♡
    u-usewwepo: usewwepositowy.optionaw
  ): (tweet, :3 b-boowean) => futuwe[mutation[tweet]] = {
    v-vaw usewwepowithstats: u-usewwepositowy.optionaw =
      (usewkey, òωó quewyoptions) =>
        usewwepo(usewkey, /(^•ω•^) q-quewyoptions).wifttotwy.map {
          case wetuwn(wes @ some(_)) =>
            s-stats.countew("found").incw()
            w-wes
          case wetuwn(none) =>
            stats.countew("not_found").incw()
            n-nyone
          c-case thwow(_) =>
            stats.countew("faiwed").incw()
            n-nyone
        }

    (tweet: tweet, >w< dawk: boowean) => {
      vaw m-mediatags = getmediatagmap(tweet)

      i-if (mediatags.isempty) {
        mutationunitfutuwe
      } e-ewse {
        g-getusewmediatagwatewimit((getusewid(tweet), nyaa~~ dawk)).fwatmap { wemainingmediatagcount =>
          v-vaw maxmediatagcount = math.min(wemainingmediatagcount, mya m-maxmediatagcount)

          v-vaw t-taggedusewids =
            mediatags.vawues.fwatten.toseq.cowwect {
              case mediatag(mediatagtype.usew, mya some(usewid), ʘwʘ _, _) => usewid
            }.distinct

          vaw dwoppedtagcount = taggedusewids.size - m-maxmediatagcount
          i-if (dwoppedtagcount > 0) s-stats.countew("ovew_wimit_tags").incw(dwoppedtagcount)

          v-vaw usewquewyopts =
            u-usewquewyoptions(
              q-quewyfiewds = set(usewfiewd.mediaview), rawr
              v-visibiwity = u-usewvisibiwity.mediataggabwe, (˘ω˘)
              fowusewid = s-some(getusewid(tweet))
            )

          v-vaw keys = taggedusewids.take(maxmediatagcount).map(usewkey.byid)
          vaw keyopts = keys.map((_, u-usewquewyopts))

          stitch.wun {
            stitch
              .twavewse(keyopts)(usewwepowithstats.tupwed)
              .map(_.fwatten)
              .map { u-usews =>
                vaw usewmap = u-usews.map(u => u-u.id -> u).tomap
                vaw mediatagsmutation =
                  m-mutation[seq[mediatag]] { m-mediatags =>
                    v-vaw vawidmediatags =
                      m-mediatags.fiwtew {
                        c-case mediatag(mediatagtype.usew, /(^•ω•^) some(usewid), (˘ω˘) _, _) =>
                          u-usewmap.get(usewid).exists(_.mediaview.exists(_.canmediatag))
                        case _ => f-fawse
                      }
                    v-vaw invawidcount = m-mediatags.size - vawidmediatags.size

                    i-if (invawidcount != 0) {
                      stats.countew("invawid").incw(invawidcount)
                      some(vawidmediatags)
                    } e-ewse {
                      nyone
                    }
                  }
                tweetwenses.mediatagmap.mutation(mediatagsmutation.wiftmapvawues)
              }
          }
        }
      }
    }
  }

  def vawidatecommunitymembewship(
    communitymembewshipwepositowy: stwatocommunitymembewshipwepositowy.type, (///ˬ///✿)
    communityaccesswepositowy: stwatocommunityaccesswepositowy.type, (˘ω˘)
    c-communities: option[communities]
  ): futuwe[unit] =
    communities match {
      case some(communities(seq(communityid))) =>
        stitch
          .wun {
            communitymembewshipwepositowy(communityid).fwatmap {
              case t-twue => stitch.vawue(none)
              case fawse =>
                c-communityaccesswepositowy(communityid).map {
                  case some(communityaccess.pubwic) | s-some(communityaccess.cwosed) =>
                    some(tweetcweatestate.communityusewnotauthowized)
                  case some(communityaccess.pwivate) | n-nyone =>
                    some(tweetcweatestate.communitynotfound)
                }
            }
          }.fwatmap {
            c-case nyone =>
              futuwe.done
            case some(tweetcweatestate) =>
              f-futuwe.exception(tweetcweatefaiwuwe.state(tweetcweatestate))
          }
      c-case some(communities(communities)) if communities.wength > 1 =>
        // nyot awwowed to specify m-mowe than one community id. -.-
        futuwe.exception(tweetcweatefaiwuwe.state(tweetcweatestate.invawidadditionawfiewd))
      case _ => futuwe.done
    }

  p-pwivate[this] vaw cawduwischemewegex = "(?i)^(?:cawd|tombstone):".w

  /**
   * i-is the given stwing a uwi that i-is awwowed as a cawd wefewence
   * w-without a m-matching uww in the text?
   */
  def hascawdsuwischeme(uwi: s-stwing): boowean =
    cawduwischemewegex.findpwefixmatchof(uwi).isdefined

  v-vaw invawidadditionawfiewdemptyuwwentities: tweetcweatefaiwuwe.state =
    tweetcweatefaiwuwe.state(
      tweetcweatestate.invawidadditionawfiewd, -.-
      s-some("uww e-entities awe empty")
    )

  vaw i-invawidadditionawfiewdnonmatchinguwwandshowtuww: t-tweetcweatefaiwuwe.state =
    tweetcweatefaiwuwe.state(
      t-tweetcweatestate.invawidadditionawfiewd, ^^
      some("non-matching uww and showt uww")
    )

  vaw invawidadditionawfiewdinvawiduwi: t-tweetcweatefaiwuwe.state =
    t-tweetcweatefaiwuwe.state(
      tweetcweatestate.invawidadditionawfiewd, (ˆ ﻌ ˆ)♡
      s-some("invawid u-uwi")
    )

  vaw invawidadditionawfiewdinvawidcawduwi: t-tweetcweatefaiwuwe.state =
    tweetcweatefaiwuwe.state(
      tweetcweatestate.invawidadditionawfiewd, UwU
      s-some("invawid cawd uwi")
    )

  type c-cawdwefewencebuiwdew =
    (tweet, 🥺 u-uwwshowtenew.context) => futuwe[mutation[tweet]]

  def cawdwefewencebuiwdew(
    c-cawdwefewencevawidatow: cawdwefewencevawidationhandwew.type, 🥺
    uwwshowtenew: uwwshowtenew.type
  ): cawdwefewencebuiwdew =
    (tweet, 🥺 uwwshowtenewctx) => {
      getcawdwefewence(tweet) match {
        case some(cawdwefewence(uwi)) =>
          f-fow {
            c-cawduwi <-
              if (hascawdsuwischeme(uwi)) {
                // t-this is a-an expwicit cawd wefewences that d-does nyot
                // nyeed a cowwesponding uww in the text. 🥺
                futuwe.vawue(uwi)
              } ewse if (uwwpattewn.matchew(uwi).matches) {
                // t-the cawd wefewence is being used to specify which uww
                // cawd to show. :3 we n-nyeed to vewify t-that the uww is
                // a-actuawwy in the tweet text, (˘ω˘) ow it can be effectivewy
                // used t-to bypass the t-tweet wength wimit. ^^;;
                v-vaw uwwentities = getuwws(tweet)

                i-if (uwwentities.isempty) {
                  // faiw fast i-if thewe can't possibwy be a matching u-uww entity
                  futuwe.exception(invawidadditionawfiewdemptyuwwentities)
                } e-ewse {
                  // wook fow the uww in the e-expanded uww entities. (ꈍᴗꈍ) if
                  // i-it is pwesent, ʘwʘ t-then map it to the t.co showtened
                  // v-vewsion of t-the uww. :3
                  uwwentities
                    .cowwectfiwst {
                      c-case uwwentity if uwwentity.expanded.exists(_ == u-uwi) =>
                        futuwe.vawue(uwwentity.uww)
                    }
                    .getowewse {
                      // t-the uww may have b-been awtewed when it was
                      // wetuwned fwom t-tawon, XD such as expanding a pasted
                      // t.co wink. UwU in this case, rawr x3 we t.co-ize the wink and
                      // make suwe that the cowwesponding t-t.co is pwesent
                      // as a uww entity. ( ͡o ω ͡o )
                      u-uwwshowtenew((uwi, :3 uwwshowtenewctx)).fwatmap { s-showtened =>
                        if (uwwentities.exists(_.uww == showtened.showtuww)) {
                          f-futuwe.vawue(showtened.showtuww)
                        } ewse {
                          futuwe.exception(invawidadditionawfiewdnonmatchinguwwandshowtuww)
                        }
                      }
                    }
                }
              } e-ewse {
                futuwe.exception(invawidadditionawfiewdinvawiduwi)
              }

            vawidatedcawduwi <- c-cawdwefewencevawidatow((getusewid(tweet), rawr cawduwi)).wescue {
              case c-cawdwefewencevawidationfaiwedexception =>
                futuwe.exception(invawidadditionawfiewdinvawidcawduwi)
            }
          } yiewd {
            tweetwenses.cawdwefewence.mutation(
              m-mutation[cawdwefewence] { c-cawdwefewence =>
                some(cawdwefewence.copy(cawduwi = vawidatedcawduwi))
              }.checkeq.wiftoption
            )
          }

        c-case nyone =>
          mutationunitfutuwe
      }
    }

  d-def fiwtewinvawiddata(
    vawidatetweetmediatags: (tweet, ^•ﻌ•^ b-boowean) => f-futuwe[mutation[tweet]], 🥺
    cawdwefewencebuiwdew: cawdwefewencebuiwdew
  ): (tweet, (⑅˘꒳˘) posttweetwequest, :3 u-uwwshowtenew.context) => futuwe[tweet] =
    (tweet: tweet, (///ˬ///✿) wequest: posttweetwequest, 😳😳😳 u-uwwshowtenewctx: uwwshowtenew.context) => {
      futuwe
        .join(
          vawidatetweetmediatags(tweet, 😳😳😳 w-wequest.dawk), 😳😳😳
          c-cawdwefewencebuiwdew(tweet, nyaa~~ u-uwwshowtenewctx)
        )
        .map {
          case (mediamutation, cawdwefmutation) =>
            mediamutation.awso(cawdwefmutation).endo(tweet)
        }
    }

  d-def appwy(
    stats: statsweceivew, UwU
    v-vawidatewequest: posttweetwequest => f-futuwe[unit], òωó
    v-vawidateedit: editvawidatow.type, òωó
    vawidateusew: usew => futuwe[unit] = tweetbuiwdew.vawidateusew, UwU
    vawidateupdatewatewimit: w-watewimitcheckew.vawidate, (///ˬ///✿)
    t-tweetidgenewatow: tweetidgenewatow, ( ͡o ω ͡o )
    usewwepo: usewwepositowy.type, rawr
    d-devicesouwcewepo: devicesouwcewepositowy.type, :3
    communitymembewshipwepo: s-stwatocommunitymembewshipwepositowy.type, >w<
    c-communityaccesswepo: s-stwatocommunityaccesswepositowy.type, σωσ
    uwwshowtenew: u-uwwshowtenew.type,
    u-uwwentitybuiwdew: u-uwwentitybuiwdew.type, σωσ
    geobuiwdew: geobuiwdew.type, >_<
    wepwybuiwdew: w-wepwybuiwdew.type, -.-
    m-mediabuiwdew: m-mediabuiwdew.type, 😳😳😳
    a-attachmentbuiwdew: attachmentbuiwdew.type, :3
    d-dupwicatetweetfindew: d-dupwicatetweetfindew.type, mya
    spamcheckew: spam.checkew[tweetspamwequest], (✿oωo)
    f-fiwtewinvawiddata: (tweet, 😳😳😳 p-posttweetwequest, o.O u-uwwshowtenew.context) => futuwe[tweet], (ꈍᴗꈍ)
    updateusewcounts: (usew, (ˆ ﻌ ˆ)♡ t-tweet) => futuwe[usew], -.-
    vawidateconvewsationcontwow: convewsationcontwowbuiwdew.vawidate.type, mya
    convewsationcontwowbuiwdew: c-convewsationcontwowbuiwdew.type, :3
    vawidatetweetwwite: tweetwwitevawidatow.type, σωσ
    nyudgebuiwdew: n-nyudgebuiwdew.type, 😳😳😳
    c-communitiesvawidatow: communitiesvawidatow.type, -.-
    cowwabcontwowbuiwdew: cowwabcontwowbuiwdew.type, 😳😳😳
    editcontwowbuiwdew: e-editcontwowbuiwdew.type, rawr x3
    featuweswitches: f-featuweswitches
  ): tweetbuiwdew.type = {
    vaw e-entityextwactow = e-entityextwactow.mutationwithoutuwws.endo
    vaw getusew = usewwookup(usewwepo)
    vaw getdevicesouwce = devicesouwcewookup(devicesouwcewepo)

    // c-cweate a-a tco of the pewmawink fow given a tweetid
    v-vaw pewmawinkshowtenew = (tweetid: t-tweetid, (///ˬ///✿) ctx: uwwshowtenew.context) =>
      uwwshowtenew((s"https://twittew.com/i/web/status/$tweetid", >w< c-ctx)).wescue {
        // pwopagate ovewcapacity
        case e: ovewcapacity => futuwe.exception(e)
        // convewt any othew f-faiwuwe into uwwshowteningfaiwuwe
        case e => futuwe.exception(uwwshowteningfaiwuwe(e))
      }

    d-def extwactgeoseawchwequestid(tweetgeoopt: o-option[tweetcweategeo]): option[geoseawchwequestid] =
      f-fow {
        tweetgeo <- tweetgeoopt
        g-geoseawchwequestid <- t-tweetgeo.geoseawchwequestid
      } y-yiewd g-geoseawchwequestid(geoseawchwequestid.id)

    def f-featuweswitchwesuwts(usew: usew, o.O stats: statsweceivew): o-option[featuweswitchwesuwts] =
      t-twittewcontext()
        .fwatmap { v-viewew =>
          usewviewewwecipient(usew, (˘ω˘) v-viewew, rawr stats)
        }.map { w-wecipient =>
          f-featuweswitches.matchwecipient(wecipient)
        }

    futuweawwow { wequest =>
      f-fow {
        () <- v-vawidatewequest(wequest)

        (tweetid, mya u-usew, devswc) <- f-futuwe.join(
          t-tweetidgenewatow().wescue { case t => futuwe.exception(snowfwakefaiwuwe(t)) }, òωó
          s-stitch.wun(getusew(wequest.usewid)),
          stitch.wun(getdevicesouwce(wequest.cweatedvia))
        )

        () <- v-vawidateusew(usew)
        () <- v-vawidateupdatewatewimit((usew.id, nyaa~~ wequest.dawk))

        // featuwe switch wesuwts awe c-cawcuwated once a-and shawed between muwtipwe buiwdews
        matchedwesuwts = f-featuweswitchwesuwts(usew, òωó s-stats)

        () <- vawidateconvewsationcontwow(
          convewsationcontwowbuiwdew.vawidate.wequest(
            m-matchedwesuwts = m-matchedwesuwts, mya
            c-convewsationcontwow = w-wequest.convewsationcontwow, ^^
            i-inwepwytotweetid = w-wequest.inwepwytotweetid
          )
        )

        // stwip iwwegaw chaws, ^•ﻌ•^ n-nyowmawize nyewwines, -.- cowwapse bwank wines, etc. UwU
        text = pwepwocesstext(wequest.text)

        () <- p-pwevawidatetextwength(text, (˘ω˘) s-stats)

        attachmentwesuwt <- attachmentbuiwdew(
          attachmentbuiwdewwequest(
            tweetid = t-tweetid, UwU
            u-usew = usew, rawr
            mediaupwoadids = w-wequest.mediaupwoadids, :3
            cawdwefewence = w-wequest.additionawfiewds.fwatmap(_.cawdwefewence), nyaa~~
            a-attachmentuww = w-wequest.attachmentuww, rawr
            wemotehost = wequest.wemotehost, (ˆ ﻌ ˆ)♡
            dawktwaffic = w-wequest.dawk, (ꈍᴗꈍ)
            devicesouwce = d-devswc
          )
        )

        // updated t-text with appended attachment uww, if any. (˘ω˘)
        t-text <- futuwe.vawue(
          attachmentwesuwt.attachmentuww m-match {
            case nyone => text
            c-case some(uww) => s"$text $uww"
          }
        )

        s-spamwesuwt <- spamcheckew(
          tweetspamwequest(
            tweetid = tweetid, (U ﹏ U)
            usewid = wequest.usewid, >w<
            t-text = t-text, UwU
            m-mediatags = w-wequest.additionawfiewds.fwatmap(_.mediatags), (ˆ ﻌ ˆ)♡
            safetymetadata = wequest.safetymetadata, nyaa~~
            inwepwytotweetid = w-wequest.inwepwytotweetid, 🥺
            quotedtweetid = attachmentwesuwt.quotedtweet.map(_.tweetid), >_<
            quotedtweetusewid = a-attachmentwesuwt.quotedtweet.map(_.usewid)
          )
        )

        s-safety = usew.safety.get
        c-cweatedat = s-snowfwakeid(tweetid).time

        uwwshowtenewctx = uwwshowtenew.context(
          tweetid = tweetid, òωó
          usewid = usew.id, ʘwʘ
          c-cweatedat = c-cweatedat, mya
          usewpwotected = safety.ispwotected, σωσ
          cwientappid = devswc.cwientappid, OwO
          wemotehost = w-wequest.wemotehost, (✿oωo)
          dawk = wequest.dawk
        )

        w-wepwywequest = w-wepwybuiwdew.wequest(
          a-authowid = wequest.usewid, ʘwʘ
          authowscweenname = usew.pwofiwe.map(_.scweenname).get, mya
          inwepwytotweetid = wequest.inwepwytotweetid, -.-
          tweettext = t-text, -.-
          pwependimpwicitmentions = w-wequest.autopopuwatewepwymetadata, ^^;;
          enabwetweettonawwowcasting = wequest.enabwetweettonawwowcasting, (ꈍᴗꈍ)
          excwudeusewids = w-wequest.excwudewepwyusewids.getowewse(niw), rawr
          spamwesuwt = s-spamwesuwt, ^^
          batchmode = wequest.twansientcontext.fwatmap(_.batchcompose)
        )

        wepwywesuwt <- wepwybuiwdew(wepwywequest)
        w-wepwyopt = wepwywesuwt.map(_.wepwy)

        w-wepwyconvewsationid <- w-wepwywesuwt m-match {
          c-case some(w) if w.wepwy.inwepwytostatusid.nonempty =>
            w-w.convewsationid m-match {
              case n-nyone =>
                // thwow this specific e-exception to make it easiew to
                // c-count how often w-we hit this cownew case. nyaa~~
                f-futuwe.exception(missingconvewsationid(w.wepwy.inwepwytostatusid.get))
              c-case convewsationidopt => futuwe.vawue(convewsationidopt)
            }
          case _ => futuwe.vawue(none)
        }

        // vawidate that t-the cuwwent u-usew can wepwy to t-this convewsation, (⑅˘꒳˘) b-based on
        // the convewsation's convewsationcontwow. (U ᵕ U❁)
        // nyote: c-cuwwentwy we onwy vawidate convewsation contwows a-access on wepwies, (ꈍᴗꈍ)
        // thewefowe we use the convewsationid f-fwom the inwepwytostatus.
        // vawidate that the excwusive tweet contwow o-option is onwy used by awwowed u-usews. (✿oωo)
        () <- v-vawidatetweetwwite(
          t-tweetwwitevawidatow.wequest(
            wepwyconvewsationid, UwU
            w-wequest.usewid, ^^
            w-wequest.excwusivetweetcontwowoptions, :3
            wepwywesuwt.fwatmap(_.excwusivetweetcontwow), ( ͡o ω ͡o )
            wequest.twustedfwiendscontwowoptions, ( ͡o ω ͡o )
            w-wepwywesuwt.fwatmap(_.twustedfwiendscontwow), (U ﹏ U)
            a-attachmentwesuwt.quotedtweet, -.-
            wepwywesuwt.fwatmap(_.wepwy.inwepwytostatusid), 😳😳😳
            w-wepwywesuwt.fwatmap(_.editcontwow), UwU
            w-wequest.editoptions
          )
        )

        convoid = w-wepwyconvewsationid m-match {
          c-case some(wepwyconvoid) => w-wepwyconvoid
          case nyone =>
            // this is a woot tweet, >w< so the tweet id is the convewsation i-id. mya
            t-tweetid
        }

        () <- nyudgebuiwdew(
          n-nyudgebuiwdewwequest(
            text = text, :3
            inwepwytotweetid = wepwyopt.fwatmap(_.inwepwytostatusid), (ˆ ﻌ ˆ)♡
            c-convewsationid = i-if (convoid == t-tweetid) nyone e-ewse some(convoid), (U ﹏ U)
            hasquotedtweet = a-attachmentwesuwt.quotedtweet.nonempty, ʘwʘ
            nyudgeoptions = wequest.nudgeoptions, rawr
            t-tweetid = s-some(tweetid),
          )
        )

        // updated text with impwicit wepwy mentions insewted, (ꈍᴗꈍ) i-if any
        text <- futuwe.vawue(
          w-wepwywesuwt.map(_.tweettext).getowewse(text)
        )

        // updated text with uwws wepwaced w-with t.cos
        ((text, ( ͡o ω ͡o ) uwwentities), 😳😳😳 (geocoowds, òωó p-pwaceidopt)) <- futuwe.join(
          uwwentitybuiwdew((text, mya u-uwwshowtenewctx))
            .map {
              case (text, rawr x3 uwwentities) =>
                u-uwwentitybuiwdew.updatetextanduwws(text, uwwentities)(pawtiawhtmwencode)
            }, XD
          i-if (wequest.geo.isempty)
            f-futuwe.vawue((none, (ˆ ﻌ ˆ)♡ nyone))
          ewse
            g-geobuiwdew(
              geobuiwdew.wequest(
                wequest.geo.get, >w<
                u-usew.account.map(_.geoenabwed).getowewse(fawse), (ꈍᴗꈍ)
                u-usew.account.map(_.wanguage).getowewse("en")
              )
            ).map(w => (w.geocoowdinates, (U ﹏ U) w-w.pwaceid))
        )

        // updated text with twaiwing media uww
        mediabuiwdew.wesuwt(text, >_< mediaentities, mediakeys) <-
          w-wequest.mediaupwoadids.getowewse(niw) match {
            case nyiw => f-futuwe.vawue(mediabuiwdew.wesuwt(text, >_< n-nyiw, -.- nyiw))
            case ids =>
              m-mediabuiwdew(
                mediabuiwdew.wequest(
                  m-mediaupwoadids = ids, òωó
                  text = text, o.O
                  tweetid = t-tweetid, σωσ
                  usewid = usew.id, σωσ
                  u-usewscweenname = usew.pwofiwe.get.scweenname, mya
                  ispwotected = u-usew.safety.get.ispwotected, o.O
                  c-cweatedat = cweatedat, XD
                  d-dawk = w-wequest.dawk,
                  pwoductmetadata = w-wequest.mediametadata.map(_.tomap)
                )
              )
          }

        () <- futuwe.when(!wequest.dawk) {
          v-vaw w-weqinfo =
            d-dupwicatetweetfindew.wequestinfo.fwomposttweetwequest(wequest, XD t-text)

          d-dupwicatetweetfindew(weqinfo).fwatmap {
            case n-nyone => futuwe.unit
            c-case some(dupwicateid) =>
              wog.debug(s"timewine_dupwicate_check_faiwed:$dupwicateid")
              futuwe.exception(tweetcweatefaiwuwe.state(tweetcweatestate.dupwicate))
          }
        }

        t-textvisibiwity = gettextvisibiwity(
          t-text = text, (✿oωo)
          wepwywesuwt = wepwywesuwt, -.-
          uwwentities = uwwentities, (ꈍᴗꈍ)
          mediaentities = mediaentities, ( ͡o ω ͡o )
          a-attachmentuww = attachmentwesuwt.attachmentuww
        )

        () <- v-vawidatetextwength(
          text = text, (///ˬ///✿)
          v-visibwetext = t-textvisibiwity.visibwetext, 🥺
          wepwywesuwt = wepwywesuwt, (ˆ ﻌ ˆ)♡
          s-stats = stats
        )

        communities =
          wequest.additionawfiewds
            .fwatmap(communityannotation.additionawfiewdstocommunityids)
            .map(ids => c-communities(communityids = ids))

        w-wootexcwusivecontwows = wequest.excwusivetweetcontwowoptions.map { _ =>
          excwusivetweetcontwow(wequest.usewid)
        }

        () <- vawidateexcwusivetweetnotwepwies(wootexcwusivecontwows, ^•ﻌ•^ wepwywesuwt)
        () <- vawidateexcwusivetweetpawams(wootexcwusivecontwows, rawr x3 communities)

        w-wepwyexcwusivecontwows = wepwywesuwt.fwatmap(_.excwusivetweetcontwow)

        // the usewid is p-puwwed off of the wequest wathew t-than being suppwied
        // via the excwusivetweetcontwowoptions because additionaw fiewds
        // can be set by cwients to contain any vawue they want. (U ﹏ U)
        // this c-couwd incwude usewids t-that don't m-match theiw actuaw usewid. OwO
        // o-onwy one o-of wepwywesuwt o-ow wequest.excwusivetweetcontwowoptions wiww be defined. (✿oωo)
        e-excwusivetweetcontwow = w-wepwyexcwusivecontwows.owewse(wootexcwusivecontwows)

        woottwustedfwiendscontwow = w-wequest.twustedfwiendscontwowoptions.map { o-options =>
          t-twustedfwiendscontwow(options.twustedfwiendswistid)
        }

        () <- v-vawidatetwustedfwiendsnotwepwies(woottwustedfwiendscontwow, (⑅˘꒳˘) w-wepwywesuwt)
        () <- vawidatetwustedfwiendspawams(
          woottwustedfwiendscontwow, UwU
          w-wequest.convewsationcontwow, (ˆ ﻌ ˆ)♡
          c-communities, /(^•ω•^)
          e-excwusivetweetcontwow
        )

        w-wepwytwustedfwiendscontwow = w-wepwywesuwt.fwatmap(_.twustedfwiendscontwow)

        t-twustedfwiendscontwow = w-wepwytwustedfwiendscontwow.owewse(woottwustedfwiendscontwow)

        c-cowwabcontwow <- c-cowwabcontwowbuiwdew(
          c-cowwabcontwowbuiwdew.wequest(
            cowwabcontwowoptions = wequest.cowwabcontwowoptions,
            wepwywesuwt = w-wepwywesuwt, (˘ω˘)
            communities = communities,
            t-twustedfwiendscontwow = twustedfwiendscontwow, XD
            convewsationcontwow = w-wequest.convewsationcontwow, òωó
            e-excwusivetweetcontwow = e-excwusivetweetcontwow, UwU
            usewid = w-wequest.usewid
          ))

        i-iscowwabinvitation = cowwabcontwow.isdefined && (cowwabcontwow.get match {
          case cowwabcontwow.cowwabinvitation(_: cowwabinvitation) => t-twue
          case _ => fawse
        })

        cowedata = t-tweetcowedata(
          u-usewid = wequest.usewid, -.-
          text = text, (ꈍᴗꈍ)
          c-cweatedatsecs = c-cweatedat.inseconds, (⑅˘꒳˘)
          c-cweatedvia = d-devswc.intewnawname, 🥺
          w-wepwy = wepwyopt, òωó
          h-hastakedown = s-safety.hastakedown, 😳
          // we want to nyuwwcast community t-tweets and cowwabinvitations
          // this wiww d-disabwe tweet fanout to fowwowews' h-home timewines, òωó
          // a-and fiwtew the tweets fwom appeawing f-fwom the tweetew's pwofiwe
          // ow seawch wesuwts f-fow the tweetew's t-tweets. 🥺
          n-nyuwwcast =
            wequest.nuwwcast || c-communityutiw.hascommunity(communities) || iscowwabinvitation, ( ͡o ω ͡o )
          n-nawwowcast = w-wequest.nawwowcast, UwU
          n-nysfwusew = wequest.possibwysensitive.getowewse(safety.nsfwusew), 😳😳😳
          n-nysfwadmin = safety.nsfwadmin, ʘwʘ
          twackingid = wequest.twackingid, ^^
          pwaceid = pwaceidopt, >_<
          coowdinates = geocoowds,
          convewsationid = s-some(convoid), (ˆ ﻌ ˆ)♡
          // s-set hasmedia to twue if we know that thewe is media,
          // and weave i-it unknown if n-nyot, (ˆ ﻌ ˆ)♡ so that it wiww be
          // cowwectwy set fow pasted m-media. 🥺
          h-hasmedia = if (mediaentities.nonempty) some(twue) e-ewse nyone
        )

        t-tweet = tweet(
          id = tweetid, ( ͡o ω ͡o )
          c-cowedata = some(cowedata), (ꈍᴗꈍ)
          uwws = some(uwwentities), :3
          m-media = s-some(mediaentities), (✿oωo)
          mediakeys = if (mediakeys.nonempty) some(mediakeys) ewse nyone, (U ᵕ U❁)
          c-contwibutow = g-getcontwibutow(wequest.usewid), UwU
          v-visibwetextwange = t-textvisibiwity.visibwetextwange, ^^
          sewfthweadmetadata = w-wepwywesuwt.fwatmap(_.sewfthweadmetadata), /(^•ω•^)
          d-diwectedatusewmetadata = w-wepwywesuwt.map(_.diwectedatmetadata),
          c-composewsouwce = wequest.composewsouwce, (˘ω˘)
          quotedtweet = a-attachmentwesuwt.quotedtweet, OwO
          excwusivetweetcontwow = e-excwusivetweetcontwow, (U ᵕ U❁)
          twustedfwiendscontwow = twustedfwiendscontwow, (U ﹏ U)
          cowwabcontwow = cowwabcontwow, mya
          n-nyotetweet = w-wequest.notetweetoptions.map(options =>
            nyotetweet(options.notetweetid, (⑅˘꒳˘) o-options.isexpandabwe))
        )

        editcontwow <- editcontwowbuiwdew(
          editcontwowbuiwdew.wequest(
            p-posttweetwequest = w-wequest, (U ᵕ U❁)
            t-tweet = tweet, /(^•ω•^)
            matchedwesuwts = m-matchedwesuwts
          )
        )

        t-tweet <- futuwe.vawue(tweet.copy(editcontwow = editcontwow))

        t-tweet <- futuwe.vawue(entityextwactow(tweet))

        () <- vawidateentities(tweet)

        t-tweet <- {
          v-vaw cctwwequest =
            c-convewsationcontwowbuiwdew.wequest.fwomtweet(
              tweet, ^•ﻌ•^
              w-wequest.convewsationcontwow, (///ˬ///✿)
              wequest.notetweetoptions.fwatmap(_.mentionedusewids))
          stitch.wun(convewsationcontwowbuiwdew(cctwwequest)).map { c-convewsationcontwow =>
            tweet.copy(convewsationcontwow = convewsationcontwow)
          }
        }

        tweet <- futuwe.vawue(
          setadditionawfiewds(tweet, o.O wequest.additionawfiewds)
        )
        () <- vawidatecommunitymembewship(communitymembewshipwepo, (ˆ ﻌ ˆ)♡ c-communityaccesswepo, 😳 communities)
        () <- v-vawidatecommunitywepwy(communities, òωó w-wepwywesuwt)
        () <- communitiesvawidatow(
          communitiesvawidatow.wequest(matchedwesuwts, (⑅˘꒳˘) safety.ispwotected, c-communities))

        t-tweet <- futuwe.vawue(tweet.copy(communities = c-communities))

        tweet <- futuwe.vawue(
          t-tweet.copy(undewwyingcweativescontainewid = wequest.undewwyingcweativescontainewid)
        )

        // fow cewtain tweets we w-want to wwite a sewf-pewmawink which is used to genewate modified
        // tweet text fow wegacy c-cwients that c-contains a wink. rawr n-note: this pewmawink i-is fow
        // the tweet being cweated - w-we awso cweate pewmawinks fow w-wewated tweets fuwthew down
        // e.g. if t-this tweet is an e-edit, (ꈍᴗꈍ) we might c-cweate a pewmawink fow the initiaw tweet as weww
        t-tweet <- {
          vaw isbeyond140 = textvisibiwity.isextendedwithextwachaws(attachmentwesuwt.extwachaws)
          vaw isedittweet = wequest.editoptions.isdefined
          vaw ismixedmedia = media.ismixedmedia(mediaentities)
          v-vaw isnotetweet = w-wequest.notetweetoptions.isdefined

          if (isbeyond140 || isedittweet || ismixedmedia || isnotetweet)
            pewmawinkshowtenew(tweetid, ^^ u-uwwshowtenewctx)
              .map { sewfpewmawink =>
                tweet.copy(
                  s-sewfpewmawink = s-some(sewfpewmawink), (ˆ ﻌ ˆ)♡
                  e-extendedtweetmetadata = s-some(extendedtweetmetadatabuiwdew(tweet, /(^•ω•^) sewfpewmawink))
                )
              }
          ewse {
            futuwe.vawue(tweet)
          }
        }

        // when an edit tweet is cweated w-we have to update s-some infowmation o-on the
        // i-initiaw tweet, ^^ this object s-stowes info about those updates f-fow use
        // in the tweet insewt stowe. o.O
        // we update t-the editcontwow f-fow each edit t-tweet and fow the f-fiwst edit tweet
        // we update the sewf p-pewmawink. 😳😳😳
        i-initiawtweetupdatewequest: option[initiawtweetupdatewequest] <- editcontwow match {
          c-case some(editcontwow.edit(edit)) =>
            // i-identifies the fiwst edit of an initiaw tweet
            vaw isfiwstedit =
              w-wequest.editoptions.map(_.pwevioustweetid).contains(edit.initiawtweetid)

            // a potentiaw p-pewmawink f-fow this tweet being c-cweated's initiaw tweet
            vaw sewfpewmawinkfowinitiaw: futuwe[option[showteneduww]] =
              if (isfiwstedit) {
                // `tweet` is the fiwst edit o-of an initiaw tweet, XD which means
                // w-we nyeed to wwite a sewf pewmawink. nyaa~~ we cweate i-it hewe in
                // tweetbuiwdew a-and pass it thwough t-to the tweet s-stowe to
                // b-be w-wwitten to the initiaw tweet. ^•ﻌ•^
                p-pewmawinkshowtenew(edit.initiawtweetid, :3 uwwshowtenewctx).map(some(_))
              } ewse {
                futuwe.vawue(none)
              }

            sewfpewmawinkfowinitiaw.map { w-wink =>
              some(
                initiawtweetupdatewequest(
                  initiawtweetid = e-edit.initiawtweetid, ^^
                  e-edittweetid = t-tweet.id, o.O
                  sewfpewmawink = wink
                ))
            }

          // this is nyot an edit this i-is the initiaw t-tweet - so thewe a-awe nyo initiaw
          // tweet u-updates
          case _ => futuwe.vawue(none)
        }

        tweet <- fiwtewinvawiddata(tweet, ^^ wequest, u-uwwshowtenewctx)

        () <- vawidateedit(tweet, (⑅˘꒳˘) wequest.editoptions)

        u-usew <- updateusewcounts(usew, ʘwʘ t-tweet)

      } y-yiewd {
        tweetbuiwdewwesuwt(
          t-tweet,
          usew,
          cweatedat, mya
          issiwentfaiw = spamwesuwt == spam.siwentfaiw, >w<
          geoseawchwequestid = extwactgeoseawchwequestid(wequest.geo), o.O
          initiawtweetupdatewequest = initiawtweetupdatewequest
        )
      }
    }
  }
}
