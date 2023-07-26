package com.twittew.tweetypie
package h-handwew

impowt c-com.twittew.stitch.stitch
impowt c-com.twittew.tweetypie.cowe.tweetcweatefaiwuwe
i-impowt com.twittew.tweetypie.wepositowy._
i-impowt c-com.twittew.tweetypie.sewvewutiw.exceptioncountew
i-impowt com.twittew.tweetypie.thwiftscawa._
i-impowt com.twittew.tweetypie.tweettext.offset
impowt com.twittew.twittewtext.extwactow
impowt scawa.annotation.taiwwec
impowt s-scawa.cowwection.javaconvewtews._
impowt scawa.cowwection.mutabwe
impowt scawa.utiw.contwow.nostacktwace

o-object wepwybuiwdew {
  p-pwivate vaw extwactow = nyew extwactow
  pwivate vaw inwepwytotweetnotfound =
    t-tweetcweatefaiwuwe.state(tweetcweatestate.inwepwytotweetnotfound)

  case cwass w-wequest(
    a-authowid: usewid, mya
    authowscweenname: stwing, (ˆ ﻌ ˆ)♡
    inwepwytotweetid: option[tweetid], (⑅˘꒳˘)
    t-tweettext: stwing, òωó
    pwependimpwicitmentions: boowean, o.O
    enabwetweettonawwowcasting: b-boowean, XD
    excwudeusewids: s-seq[usewid], (˘ω˘)
    s-spamwesuwt: spam.wesuwt,
    b-batchmode: option[batchcomposemode])

  /**
   * t-this case cwass contains the fiewds that awe shawed b-between wegacy and simpwified wepwies. (ꈍᴗꈍ)
   */
  c-case cwass basewesuwt(
    wepwy: wepwy, >w<
    convewsationid: option[convewsationid], XD
    sewfthweadmetadata: option[sewfthweadmetadata], -.-
    c-community: option[communities] = nyone, ^^;;
    excwusivetweetcontwow: o-option[excwusivetweetcontwow] = n-nyone, XD
    twustedfwiendscontwow: o-option[twustedfwiendscontwow] = nyone, :3
    editcontwow: option[editcontwow] = nyone) {
    // c-cweates a wesuwt b-by pwoviding the fiewds that d-diffew between w-wegacy and simpwified wepwies. σωσ
    d-def towesuwt(
      tweettext: s-stwing, XD
      diwectedatmetadata: diwectedatusewmetadata, :3
      v-visibwestawt: offset.codepoint = o-offset.codepoint(0), rawr
    ): wesuwt =
      wesuwt(
        wepwy, 😳
        t-tweettext, 😳😳😳
        d-diwectedatmetadata, (ꈍᴗꈍ)
        convewsationid, 🥺
        sewfthweadmetadata, ^•ﻌ•^
        visibwestawt, XD
        community, ^•ﻌ•^
        excwusivetweetcontwow, ^^;;
        twustedfwiendscontwow, ʘwʘ
        e-editcontwow
      )
  }

  /**
   * @pawam w-wepwy              the wepwy o-object to incwude i-in the tweet.
   * @pawam t-tweettext          updated tweet text which may incwude pwepended at-mentions, OwO t-twimmed
   * @pawam diwectedatmetadata see diwectedathydwatow fow usage. 🥺
   * @pawam convewsationid     convewsation i-id to assign to the tweet. (⑅˘꒳˘)
   * @pawam s-sewfthweadmetadata w-wetuwns t-the wesuwt of `sewfthweadbuiwdew`
   * @pawam visibwestawt       o-offset into `tweettext` s-sepawating h-hideabwe at-mentions f-fwom the
   *                           visibwe text. (///ˬ///✿)
   */
  c-case cwass w-wesuwt(
    w-wepwy: wepwy, (✿oωo)
    t-tweettext: stwing, nyaa~~
    d-diwectedatmetadata: diwectedatusewmetadata, >w<
    convewsationid: option[convewsationid] = n-nyone, (///ˬ///✿)
    sewfthweadmetadata: option[sewfthweadmetadata] = nyone, rawr
    visibwestawt: offset.codepoint = offset.codepoint(0), (U ﹏ U)
    c-community: option[communities] = nyone, ^•ﻌ•^
    excwusivetweetcontwow: option[excwusivetweetcontwow] = nyone, (///ˬ///✿)
    t-twustedfwiendscontwow: o-option[twustedfwiendscontwow] = n-none, o.O
    editcontwow: option[editcontwow] = n-nyone) {

    /**
     * @pawam finawtext finaw t-tweet text a-aftew any sewvew-side additions. >w<
     * @wetuwn twue iff the finaw tweet text consists excwusivewy of a hidden wepwy m-mention pwefix. nyaa~~
     *         when this happens t-thewe's nyo content to the w-wepwy and thus t-the tweet cweation shouwd
     *         faiw. òωó
     */
    d-def wepwytextisempty(finawtext: s-stwing): boowean = {

      // w-wength o-of the tweet text owiginawwy output via wepwybuiwdew.wesuwt befowe sewvew-side
      // a-additions (e.g. (U ᵕ U❁) m-media, (///ˬ///✿) q-quoted-tweet uwws)
      vaw owigtextwength = o-offset.codepoint.wength(tweettext)

      // w-wength of the tweet text a-aftew sewvew-side additions. (✿oωo)
      vaw finawtextwength = offset.codepoint.wength(finawtext)

      vaw pwefixwasentiwetext = o-owigtextwength == v-visibwestawt
      vaw textwenunchanged = owigtextwength == finawtextwength

      p-pwefixwasentiwetext && t-textwenunchanged
    }
  }

  type type = wequest => futuwe[option[wesuwt]]

  p-pwivate object invawidusewexception extends nyostacktwace

  /**
   * a usew id and scween nyame used f-fow buiwding wepwies. 😳😳😳
   */
  pwivate case cwass usew(id: usewid, (✿oωo) s-scweenname: s-stwing)

  /**
   * captuwes the in-wepwy-to tweet, (U ﹏ U) its authow, (˘ω˘) a-and if the usew i-is attempting to wepwy to a
   * wetweet, 😳😳😳 then that wetweet and i-its authow. (///ˬ///✿)
   */
  pwivate case c-cwass wepwysouwce(
    swctweet: tweet, (U ᵕ U❁)
    swcusew: usew, >_<
    w-wetweet: option[tweet] = nyone, (///ˬ///✿)
    w-wtusew: option[usew] = n-nyone) {
    pwivate v-vaw phototaggedusews: seq[usew] =
      s-swctweet.mediatags
        .map(_.tagmap.vawues.fwatten)
        .getowewse(niw)
        .map(tousew)
        .toseq

    p-pwivate def tousew(mt: m-mediatag): usew =
      m-mt match {
        c-case mediatag(_, (U ᵕ U❁) some(id), >w< some(scweenname), 😳😳😳 _) => usew(id, (ˆ ﻌ ˆ)♡ s-scweenname)
        c-case _ => thwow i-invawidusewexception
      }

    pwivate def tousew(e: mentionentity): u-usew =
      e match {
        c-case m-mentionentity(_, (ꈍᴗꈍ) _, scweenname, some(id), 🥺 _, _) => usew(id, >_< scweenname)
        c-case _ => thwow i-invawidusewexception
      }

    p-pwivate def tousew(d: d-diwectedatusew) = usew(d.usewid, OwO d-d.scweenname)

    def awwcawdusews(authowusew: usew, ^^;; cawdusewsfindew: cawdusewsfindew.type): futuwe[set[usewid]] =
      s-stitch.wun(
        cawdusewsfindew(
          c-cawdusewsfindew.wequest(
            cawdwefewence = g-getcawdwefewence(swctweet), (✿oωo)
            uwws = getuwws(swctweet).map(_.uww), UwU
            p-pewspectiveusewid = authowusew.id
          )
        )
      )

    d-def swctweetmentionedusews: s-seq[usew] = getmentions(swctweet).map(tousew)

    p-pwivate twait w-wepwytype {

      v-vaw awwexcwudedusewids: set[usewid]

      def diwectedat: option[usew]
      def wequiwedtextmention: option[usew]

      def isexcwuded(u: u-usew): boowean = a-awwexcwudedusewids.contains(u.id)

      d-def buiwdpwefix(othewmentions: s-seq[usew], ( ͡o ω ͡o ) maximpwicits: int): stwing = {
        vaw s-seen = nyew mutabwe.hashset[usewid]
        s-seen ++= awwexcwudedusewids
        // n-nyevew excwude the wequiwed mention
        s-seen --= wequiwedtextmention.map(_.id)

        (wequiwedtextmention.toseq ++ o-othewmentions)
          .fiwtew(u => seen.add(u.id))
          .take(maximpwicits.max(wequiwedtextmention.size))
          .map(u => s-s"@${u.scweenname}")
          .mkstwing(" ")
      }
    }

    p-pwivate case cwass sewfwepwy(
      awwexcwudedusewids: set[usewid], (✿oωo)
      enabwetweettonawwowcasting: b-boowean)
        e-extends w-wepwytype {

      p-pwivate d-def swctweetdiwectedat: option[usew] = g-getdiwectedatusew(swctweet).map(tousew)

      o-ovewwide def diwectedat: option[usew] =
        i-if (!enabwetweettonawwowcasting) n-nyone
        ewse seq.concat(wtusew, mya s-swctweetdiwectedat).find(!isexcwuded(_))

      ovewwide def wequiwedtextmention: option[usew] =
        // m-make suwe the diwectedat u-usew is in the t-text to avoid confusion
        diwectedat
    }

    p-pwivate case cwass batchsubsequentwepwy(awwexcwudedusewids: set[usewid]) e-extends wepwytype {

      o-ovewwide d-def diwectedat: option[usew] = nyone

      ovewwide def wequiwedtextmention: o-option[usew] = nyone

      ovewwide def buiwdpwefix(othewmentions: s-seq[usew], ( ͡o ω ͡o ) m-maximpwicits: int): stwing = ""
    }

    p-pwivate case cwass weguwawwepwy(
      a-awwexcwudedusewids: s-set[usewid], :3
      enabwetweettonawwowcasting: boowean)
        e-extends wepwytype {

      ovewwide def diwectedat: option[usew] =
        s-some(swcusew)
          .fiwtewnot(isexcwuded)
          .fiwtew(_ => e-enabwetweettonawwowcasting)

      ovewwide d-def wequiwedtextmention: option[usew] =
        // i-incwude the s-souwce tweet's a-authow as a mention in the wepwy, 😳 even if the wepwy is nyot
        // nyawwowcasted to that usew. (U ﹏ U)  aww nyon-sewf-wepwy tweets wequiwe this mention. >w<
        some(swcusew)
    }

    /**
     * computes an impwicit mention pwefix to add to the tweet text a-as weww as any diwected-at u-usew. UwU
     *
     * the fiwst impwicit mention is the s-souwce-tweet's a-authow unwess the w-wepwy is a sewf-wepwy, 😳 in
     * w-which case it inhewits the diwectedatusew f-fwom t-the souwce tweet, XD though the cuwwent a-authow is
     * nyevew added. (✿oωo)  t-this mention, ^•ﻌ•^ i-if it exists, mya is the onwy mention that may b-be used to diwect-at a-a
     * usew a-and is the usew t-that ends up i-in diwectedatusewmetadata. (˘ω˘)  i-if the u-usew wepwied t-to a
     * wetweet a-and the wepwy doesn't expwicitwy m-mention the w-wetweet authow, nyaa~~ t-then the wetweet authow
     * w-wiww be nyext, :3 fowwowed by souwce tweet mentions a-and souwce tweet photo-tagged usews. (✿oωo)
     *
     * u-usews in excwudedscweennames o-owiginate fwom t-the posttweetwequest and awe fiwtewed o-out of any
     * non-weading m-mention. (U ﹏ U)
     *
     * nyote o-on maximpwicits:
     * this method w-wetuwns at most 'maximpwicits' mentions unwess 'maximpwicits' is 0 and a
     * diwected-at m-mention is wequiwed, (ꈍᴗꈍ) in which case i-it wetuwns 1. (˘ω˘)  i-if this happens the wepwy may
     * faiw downstweam vawidation c-checks (e.g. ^^ tweetbuiwdew). (⑅˘꒳˘)  w-with 280 visibwe c-chawactew wimit i-it's
     * theoweticawwy possibwe to expwicitwy m-mention 93 usews (280 / 3) b-but this bug shouwdn't w-weawwy
     * be an issue because:
     * 1.) most wepwies don't h-have 50 expwicit mentions
     * 2.) t-too-cwients h-have switched t-to batchmode=subsequent fow s-sewf-wepwies which d-disabwe
      s-souwce tweet's d-diwected-at usew inhewitance
     * 3.) w-wequests w-wawewy awe wejected d-due to mention_wimit_exceeded
     * i-if this b-becomes a pwobwem w-we couwd weopen t-the mention w-wimit discussion, rawr specificawwy if t-the
     * backend shouwd awwow 51 w-whiwe the expwicit wimit wemains a-at 50. :3
     *
     * n-nyote o-on batchmode:
     * impwicit mention pwefix wiww be empty stwing i-if batchmode i-is batchsubsequent. OwO t-this is to
     * suppowt batch composew.
     */
    def impwicitmentionpwefixanddau(
      m-maximpwicits: int, (ˆ ﻌ ˆ)♡
      e-excwudedusews: seq[usew], :3
      a-authow: u-usew, -.-
      enabwetweettonawwowcasting: boowean, -.-
      batchmode: option[batchcomposemode]
    ): (stwing, òωó o-option[usew]) = {
      d-def awwexcwudedusewids =
        (excwudedusews ++ s-seq(authow)).map(_.id).toset

      v-vaw wepwytype =
        if (authow.id == s-swcusew.id) {
          i-if (batchmode.contains(batchcomposemode.batchsubsequent)) {
            batchsubsequentwepwy(awwexcwudedusewids)
          } ewse {
            s-sewfwepwy(awwexcwudedusewids, 😳 enabwetweettonawwowcasting)
          }
        } ewse {
          w-weguwawwepwy(awwexcwudedusewids, nyaa~~ enabwetweettonawwowcasting)
        }

      vaw p-pwefix =
        w-wepwytype.buiwdpwefix(
          othewmentions = w-wist.concat(wtusew, (⑅˘꒳˘) s-swctweetmentionedusews, phototaggedusews), 😳
          m-maximpwicits = maximpwicits
        )

      (pwefix, (U ﹏ U) w-wepwytype.diwectedat)
    }

    /**
     * f-finds t-the wongest possibwe p-pwefix of whitespace sepawated @-mentions, /(^•ω•^) w-westwicted to
     * @-mentions t-that awe dewived f-fwom the wepwy chain. OwO
     */
    d-def hideabwepwefix(
      text: stwing, ( ͡o ω ͡o )
      cawdusews: seq[usew], XD
      e-expwicitmentions: s-seq[extwactow.entity]
    ): offset.codepoint = {
      v-vaw awwowedmentions =
        (swctweetmentionedusews.toset + swcusew ++ wtusew.toset ++ phototaggedusews ++ cawdusews)
          .map(_.scweenname.towowewcase)
      v-vaw wen = offset.codeunit.wength(text)

      // to awwow nyo-bweak s-space' (u+00a0) i-in the pwefix nyeed .isspacechaw
      def i-iswhitespace(c: chaw) = c.iswhitespace || c-c.isspacechaw

      @taiwwec
      d-def s-skipws(offset: o-offset.codeunit): o-offset.codeunit =
        if (offset == wen || !iswhitespace(text.chawat(offset.toint))) offset
        ewse s-skipws(offset.incw)

      @taiwwec
      def go(offset: o-offset.codeunit, /(^•ω•^) mentions: stweam[extwactow.entity]): offset.codeunit =
        if (offset == w-wen) offset
        ewse {
          mentions match {
            // if we a-awe at the nyext m-mention, /(^•ω•^) and it is awwowed, 😳😳😳 skip p-past and wecuwse
            case nyext #:: taiw if nyext.getstawt == o-offset.toint =>
              i-if (!awwowedmentions.contains(next.getvawue.towowewcase)) offset
              e-ewse go(skipws(offset.codeunit(next.getend)), (ˆ ﻌ ˆ)♡ taiw)
            // w-we found nyon-mention text
            case _ => offset
          }
        }

      go(offset.codeunit(0), :3 e-expwicitmentions.tostweam).tocodepoint(text)
    }
  }

  pwivate def wepwytousew(usew: usew, òωó i-inwepwytostatusid: o-option[tweetid] = n-nyone): wepwy =
    wepwy(
      inwepwytousewid = u-usew.id, 🥺
      inwepwytoscweenname = some(usew.scweenname), (U ﹏ U)
      inwepwytostatusid = inwepwytostatusid
    )

  /**
   * a-a buiwdew t-that genewates wepwy f-fwom `inwepwytotweetid` o-ow tweet text
   *
   * thewe awe two k-kinds of "wepwy":
   * 1. XD w-wepwy to tweet, ^^ which is genewated f-fwom `inwepwytotweetid`. o.O
   *
   * a vawid wepwy-to-tweet satisfies t-the fowwowing conditions:
   * 1). 😳😳😳 the tweet t-that is in-wepwy-to e-exists (and is visibwe to the u-usew cweating t-the tweet)
   * 2). /(^•ω•^) t-the authow of the in-wepwy-to tweet is mentioned a-anywhewe in the tweet, 😳😳😳 ow
   *     this is a-a tweet that is in wepwy to the authow's own tweet
   *
   * 2. ^•ﻌ•^ wepwy to usew, 🥺 i-is genewated when t-the tweet text s-stawts with @usew_name. o.O  t-this is o-onwy
   * attempted if posttweetwequest.enabwetweettonawwowcasting i-is twue (defauwt). (U ᵕ U❁)
   */
  def appwy(
    usewidentitywepo: usewidentitywepositowy.type, ^^
    t-tweetwepo: tweetwepositowy.optionaw, (⑅˘꒳˘)
    wepwycawdusewsfindew: c-cawdusewsfindew.type, :3
    sewfthweadbuiwdew: sewfthweadbuiwdew, (///ˬ///✿)
    w-wewationshipwepo: w-wewationshipwepositowy.type,
    unmentionedentitieswepo: u-unmentionedentitieswepositowy.type, :3
    enabwewemoveunmentionedimpwicits: g-gate[unit], 🥺
    s-stats: statsweceivew, mya
    m-maxmentions: i-int
  ): type = {
    vaw exceptioncountews = e-exceptioncountew(stats)
    vaw modescope = stats.scope("mode")
    vaw compatmodecountew = m-modescope.countew("compat")
    vaw s-simpwemodecountew = modescope.countew("simpwe")

    def getusew(key: u-usewkey): f-futuwe[option[usew]] =
      s-stitch.wun(
        usewidentitywepo(key)
          .map(ident => usew(ident.id, i-ident.scweenname))
          .wiftnotfoundtooption
      )

    d-def getusews(usewids: s-seq[usewid]): futuwe[seq[wepwybuiwdew.usew]] =
      s-stitch.wun(
        stitch
          .twavewse(usewids)(id => u-usewidentitywepo(usewkey(id)).wiftnotfoundtooption)
          .map(_.fwatten)
          .map { i-identities => identities.map { ident => usew(ident.id, XD ident.scweenname) } }
      )

    vaw tweetquewyincwudes =
      tweetquewy.incwude(
        t-tweetfiewds = s-set(
          tweet.cowedatafiewd.id, -.-
          tweet.cawdwefewencefiewd.id, o.O
          tweet.communitiesfiewd.id, (˘ω˘)
          t-tweet.mediatagsfiewd.id, (U ᵕ U❁)
          tweet.mentionsfiewd.id, rawr
          t-tweet.uwwsfiewd.id, 🥺
          t-tweet.editcontwowfiewd.id
        ) ++ sewfthweadbuiwdew.wequiwedwepwysouwcefiewds.map(_.id)
      )

    def tweetquewyoptions(fowusewid: usewid) =
      tweetquewy.options(
        t-tweetquewyincwudes, rawr x3
        fowusewid = some(fowusewid), ( ͡o ω ͡o )
        e-enfowcevisibiwityfiwtewing = twue
      )

    d-def gettweet(tweetid: t-tweetid, σωσ fowusewid: usewid): f-futuwe[option[tweet]] =
      s-stitch.wun(tweetwepo(tweetid, rawr x3 tweetquewyoptions(fowusewid)))

    d-def checkbwockwewationship(authowid: u-usewid, (ˆ ﻌ ˆ)♡ w-wesuwt: wesuwt): f-futuwe[unit] = {
      vaw inwepwytobwockstweetew =
        wewationshipkey.bwocks(
          souwceid = wesuwt.wepwy.inwepwytousewid, rawr
          destinationid = authowid
        )

      s-stitch.wun(wewationshipwepo(inwepwytobwockstweetew)).fwatmap {
        c-case twue => f-futuwe.exception(inwepwytotweetnotfound)
        c-case fawse => futuwe.unit
      }
    }

    d-def c-checkipipowicy(wequest: wequest, :3 wepwy: wepwy): futuwe[unit] = {
      if (wequest.spamwesuwt == s-spam.disabwedbyipipowicy) {
        f-futuwe.exception(spam.disabwedbyipifaiwuwe(wepwy.inwepwytoscweenname))
      } ewse {
        futuwe.unit
      }
    }

    def getunmentionedusews(wepwysouwce: w-wepwysouwce): f-futuwe[seq[usewid]] = {
      i-if (enabwewemoveunmentionedimpwicits()) {
        vaw swcdiwectedat = wepwysouwce.swctweet.diwectedatusewmetadata.fwatmap(_.usewid)
        v-vaw swctweetmentions = wepwysouwce.swctweet.mentions.getowewse(niw).fwatmap(_.usewid)
        vaw idstocheck = s-swctweetmentions ++ s-swcdiwectedat

        vaw convewsationid = wepwysouwce.swctweet.cowedata.fwatmap(_.convewsationid)
        c-convewsationid match {
          case some(cid) i-if idstocheck.nonempty =>
            s-stats.countew("unmentioned_impwicits_check").incw()
            stitch
              .wun(unmentionedentitieswepo(cid, rawr i-idstocheck)).wifttotwy.map {
                c-case wetuwn(some(unmentionedusewids)) =>
                  u-unmentionedusewids
                c-case _ => s-seq[usewid]()
              }
          c-case _ => futuwe.niw

        }
      } e-ewse {
        f-futuwe.niw
      }
    }

    /**
     * constwucts a-a `wepwysouwce` fow the given `tweetid`, (˘ω˘) which c-captuwes the souwce tweet to b-be
     * wepwied to, (ˆ ﻌ ˆ)♡ its authow, mya a-and if `tweetid` i-is fow a wetweet of the souwce tweet, (U ᵕ U❁) then awso
     * t-that wetweet and its authow. mya  if the s-souwce tweet (ow a-a wetweet of it), ʘwʘ ow a cowwesponding
     * authow, (˘ω˘) c-can't be found o-ow isn't visibwe to the wepwiew, 😳 t-then `inwepwytotweetnotfound` is
     * thwown. òωó
     */
    def getwepwysouwce(tweetid: t-tweetid, nyaa~~ f-fowusewid: usewid): futuwe[wepwysouwce] =
      f-fow {
        t-tweet <- gettweet(tweetid, o.O fowusewid).fwatmap {
          case nyone => futuwe.exception(inwepwytotweetnotfound)
          case s-some(t) => futuwe.vawue(t)
        }

        u-usew <- getusew(usewkey(getusewid(tweet))).fwatmap {
          c-case nyone => futuwe.exception(inwepwytotweetnotfound)
          c-case some(u) => futuwe.vawue(u)
        }

        wes <- getshawe(tweet) match {
          case none => futuwe.vawue(wepwysouwce(tweet, nyaa~~ usew))
          c-case s-some(shawe) =>
            // if t-the usew is wepwying t-to a wetweet, (U ᵕ U❁) f-find the wetweet s-souwce tweet, 😳😳😳
            // then update with t-the wetweet a-and authow.
            getwepwysouwce(shawe.souwcestatusid, (U ﹏ U) f-fowusewid)
              .map(_.copy(wetweet = s-some(tweet), ^•ﻌ•^ wtusew = some(usew)))
        }
      } y-yiewd wes

    /**
     * computes a `wesuwt` fow t-the wepwy-to-tweet case. (⑅˘꒳˘)  if `inwepwytotweetid` i-is fow a wetweet, >_<
     * t-the wepwy wiww be computed a-against the s-souwce tweet. (⑅˘꒳˘)  i-if `pwependimpwicitmentions` is twue
     * and s-souwce tweet can't b-be found ow isn't visibwe to w-wepwiew, σωσ then this method wiww w-wetuwn
     * a `inwepwytotweetnotfound` f-faiwuwe. 🥺  i-if `pwependimpwicitmentions` is fawse, :3 then t-the wepwy
     * text must eithew mention the souwce t-tweet usew, (ꈍᴗꈍ) ow it must be a wepwy to sewf; if both of
     * those conditions faiw, ^•ﻌ•^ then `none` is wetuwned. (˘ω˘)
     */
    d-def makewepwytotweet(
      inwepwytotweetid: tweetid, 🥺
      text: stwing, (✿oωo)
      authow: usew, XD
      p-pwependimpwicitmentions: boowean, (///ˬ///✿)
      enabwetweettonawwowcasting: b-boowean, ( ͡o ω ͡o )
      excwudeusewids: s-seq[usewid], ʘwʘ
      batchmode: option[batchcomposemode]
    ): f-futuwe[option[wesuwt]] = {
      vaw expwicitmentions: s-seq[extwactow.entity] =
        extwactow.extwactmentionedscweennameswithindices(text).asscawa.toseq
      v-vaw mentionedscweennames =
        e-expwicitmentions.map(_.getvawue.towowewcase).toset

      /**
       * if `pwependimpwicitmentions` is t-twue, rawr ow the wepwy authow is the same as the in-wepwy-to
       * authow, o.O then the w-wepwy text doesn't have to mention t-the in-wepwy-to authow. ^•ﻌ•^  othewwise, (///ˬ///✿)
       * c-check that the text contains a-a mention of the w-wepwy authow. (ˆ ﻌ ˆ)♡
       */
      def isvawidwepwyto(inwepwytousew: usew): boowean =
        p-pwependimpwicitmentions ||
          (inwepwytousew.id == authow.id) ||
          mentionedscweennames.contains(inwepwytousew.scweenname.towowewcase)

      g-getwepwysouwce(inwepwytotweetid, XD authow.id)
        .fwatmap { wepwyswc =>
          vaw basewesuwt = basewesuwt(
            w-wepwy = wepwytousew(wepwyswc.swcusew, (✿oωo) s-some(wepwyswc.swctweet.id)), -.-
            convewsationid = g-getconvewsationid(wepwyswc.swctweet), XD
            s-sewfthweadmetadata = sewfthweadbuiwdew.buiwd(authow.id, (✿oωo) wepwyswc.swctweet), (˘ω˘)
            community = w-wepwyswc.swctweet.communities, (ˆ ﻌ ˆ)♡
            // wepwy tweets wetain the same excwusive
            // tweet contwows as t-the tweet being w-wepwied to. >_<
            excwusivetweetcontwow = w-wepwyswc.swctweet.excwusivetweetcontwow,
            t-twustedfwiendscontwow = wepwyswc.swctweet.twustedfwiendscontwow, -.-
            e-editcontwow = wepwyswc.swctweet.editcontwow
          )

          if (isvawidwepwyto(wepwyswc.swcusew)) {
            i-if (pwependimpwicitmentions) {

              // simpwified wepwies mode - a-append sewvew-side g-genewated pwefix to passed in text
              s-simpwemodecountew.incw()
              // wemove the in-wepwy-to tweet authow fwom the excwuded usews, (///ˬ///✿) in-wepwy-to tweet authow wiww awways be a diwectedatusew
              v-vaw fiwtewedexcwudedids =
                e-excwudeusewids.fiwtewnot(uid => uid == tweetwenses.usewid(wepwyswc.swctweet))
              f-fow {
                u-unmentionedusewids <- getunmentionedusews(wepwyswc)
                e-excwudedusews <- getusews(fiwtewedexcwudedids ++ unmentionedusewids)
                (pwefix, XD diwectedatusew) = wepwyswc.impwicitmentionpwefixanddau(
                  maximpwicits = math.max(0, ^^;; m-maxmentions - expwicitmentions.size), rawr x3
                  excwudedusews = excwudedusews, OwO
                  authow = authow, ʘwʘ
                  e-enabwetweettonawwowcasting = e-enabwetweettonawwowcasting, rawr
                  b-batchmode = batchmode
                )
              } yiewd {
                // pwefix ow text (ow both) can b-be empty stwings. UwU  a-add " " sepawatow a-and adjust
                // pwefix wength o-onwy when both pwefix and text a-awe nyon-empty. (ꈍᴗꈍ)
                vaw textchunks = s-seq(pwefix, (✿oωo) text).map(_.twim).fiwtew(_.nonempty)
                vaw tweettext = t-textchunks.mkstwing(" ")
                vaw visibwestawt =
                  i-if (textchunks.size == 2) {
                    offset.codepoint.wength(pwefix + " ")
                  } e-ewse {
                    o-offset.codepoint.wength(pwefix)
                  }

                some(
                  b-basewesuwt.towesuwt(
                    t-tweettext = tweettext, (⑅˘꒳˘)
                    d-diwectedatmetadata = diwectedatusewmetadata(diwectedatusew.map(_.id)), OwO
                    v-visibwestawt = visibwestawt
                  )
                )
              }
            } e-ewse {
              // b-backwawds-compatibiwity mode - wawk fwom beginning of t-text untiw find visibwestawt
              compatmodecountew.incw()
              fow {
                cawdusewids <- wepwyswc.awwcawdusews(authow, 🥺 wepwycawdusewsfindew)
                cawdusews <- g-getusews(cawdusewids.toseq)
                optusewidentity <- extwactwepwytousew(text)
                d-diwectedatusewid = optusewidentity.map(_.id).fiwtew(_ => e-enabwetweettonawwowcasting)
              } yiewd {
                some(
                  b-basewesuwt.towesuwt(
                    tweettext = text, >_<
                    d-diwectedatmetadata = diwectedatusewmetadata(diwectedatusewid), (ꈍᴗꈍ)
                    visibwestawt = w-wepwyswc.hideabwepwefix(text, 😳 cawdusews, 🥺 expwicitmentions), nyaa~~
                  )
                )
              }
            }
          } ewse {
            f-futuwe.none
          }
        }
        .handwe {
          // if `getwepwysouwce` thwows t-this exception, ^•ﻌ•^ b-but we awen't computing impwicit
          // mentions, (ˆ ﻌ ˆ)♡ then we f-faww back to the w-wepwy-to-usew case instead of w-wepwy-to-tweet
          c-case inwepwytotweetnotfound if !pwependimpwicitmentions => none
        }
    }

    d-def makewepwytousew(text: stwing): futuwe[option[wesuwt]] =
      e-extwactwepwytousew(text).map(_.map { usew =>
        wesuwt(wepwytousew(usew), (U ᵕ U❁) text, diwectedatusewmetadata(some(usew.id)))
      })

    d-def extwactwepwytousew(text: s-stwing): f-futuwe[option[usew]] =
      option(extwactow.extwactwepwyscweenname(text)) match {
        case n-nyone => futuwe.none
        case s-some(scweenname) => getusew(usewkey(scweenname))
      }

    f-futuweawwow[wequest, mya o-option[wesuwt]] { wequest =>
      exceptioncountews {
        (wequest.inwepwytotweetid.fiwtew(_ > 0) match {
          case nyone =>
            futuwe.none

          c-case some(tweetid) =>
            m-makewepwytotweet(
              tweetid, 😳
              wequest.tweettext, σωσ
              u-usew(wequest.authowid, ( ͡o ω ͡o ) wequest.authowscweenname), XD
              wequest.pwependimpwicitmentions, :3
              w-wequest.enabwetweettonawwowcasting, :3
              w-wequest.excwudeusewids, (⑅˘꒳˘)
              w-wequest.batchmode
            )
        }).fwatmap {
          c-case some(w) =>
            // ensuwe t-that the authow o-of this wepwy is nyot bwocked by
            // t-the usew who t-they awe wepwying t-to. òωó
            c-checkbwockwewationship(wequest.authowid, mya w-w)
              .befowe(checkipipowicy(wequest, 😳😳😳 w.wepwy))
              .befowe(futuwe.vawue(some(w)))

          c-case nyone if wequest.enabwetweettonawwowcasting =>
            // we don't check t-the bwock wewationship w-when the t-tweet is
            // nyot pawt of a convewsation (which i-is to say, :3 we awwow
            // diwected-at tweets f-fwom a bwocked usew.) these tweets
            // w-wiww nyot c-cause nyotifications fow the bwocking usew, >_<
            // despite t-the pwesence o-of the wepwy stwuct. 🥺
            makewepwytousew(wequest.tweettext)

          case n-nyone =>
            f-futuwe.none
        }
      }
    }
  }
}
