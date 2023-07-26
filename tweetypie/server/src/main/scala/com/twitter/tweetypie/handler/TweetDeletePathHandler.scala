package com.twittew.tweetypie
package h-handwew

impowt c-com.twittew.convewsions.duwationops.wichduwation
i-impowt com.twittew.sewvo.exception.thwiftscawa.cwientewwow
i-impowt com.twittew.sewvo.exception.thwiftscawa.cwientewwowcause
i-impowt com.twittew.sewvo.utiw.futuweawwow
i-impowt c-com.twittew.snowfwake.id.snowfwakeid
i-impowt com.twittew.stitch.stitch
impowt com.twittew.stitch.notfound
impowt com.twittew.timewinesewvice.thwiftscawa.pewspectivewesuwt
impowt c-com.twittew.timewinesewvice.{thwiftscawa => tws}
impowt com.twittew.tweetypie.cowe._
impowt com.twittew.tweetypie.wepositowy._
i-impowt com.twittew.tweetypie.stowe._
impowt com.twittew.tweetypie.thwiftscawa._
i-impowt com.twittew.utiw.time
impowt com.twittew.utiw.twy
impowt twy._
impowt com.twittew.spam.wtf.thwiftscawa.safetywabewtype
i-impowt com.twittew.tweetypie.backends.timewinesewvice.getpewspectives
impowt com.twittew.tweetypie.utiw.editcontwowutiw
i-impowt scawa.utiw.contwow.nostacktwace

c-case cwass cascadeddewetenotavaiwabwe(wetweetid: tweetid) extends exception with nyostacktwace {
  ovewwide def g-getmessage: stwing =
    s"""|cascaded dewete tweet faiwed because tweet $wetweetid
        |is n-not pwesent in cache ow manhattan.""".stwipmawgin
}

o-object tweetdewetepathhandwew {

  t-type dewetetweets =
    (dewetetweetswequest, (✿oωo) b-boowean) => f-futuwe[seq[dewetetweetwesuwt]]

  type unwetweetedits = (option[editcontwow], òωó tweetid, (˘ω˘) usewid) => f-futuwe[unit]

  /** the infowmation fwom a dewetetweet w-wequest that can be inspected by a dewetetweets vawidatow */
  case cwass dewetetweetscontext(
    b-byusewid: option[usewid], (ˆ ﻌ ˆ)♡
    a-authenticatedusewid: o-option[usewid], ( ͡o ω ͡o )
    t-tweetauthowid: usewid,
    usews: map[usewid, rawr x3 usew],
    isusewewasuwe: b-boowean, (˘ω˘)
    e-expectedewasuweusewid: option[usewid], òωó
    t-tweetisbounced: b-boowean, ( ͡o ω ͡o )
    isbouncedewete: b-boowean)

  /** pwovides weason a-a tweet dewetion was awwowed */
  seawed twait d-deweteauthowization { def byusewid: o-option[usewid] }

  case cwass a-authowizedbytweetownew(usewid: u-usewid) extends deweteauthowization {
    def byusewid: option[usewid] = some(usewid)
  }
  case cwass authowizedbytweetcontwibutow(contwibutowusewid: usewid) e-extends deweteauthowization {
    d-def byusewid: option[usewid] = s-some(contwibutowusewid)
  }
  c-case cwass authowizedbyadmin(adminusewid: u-usewid) extends deweteauthowization {
    def byusewid: option[usewid] = s-some(adminusewid)
  }
  case object authowizedbyewasuwe extends deweteauthowization {
    d-def byusewid: nyone.type = n-nyone
  }

  // t-type fow a-a method that weceives aww the w-wewevant infowmation a-about a pwoposed i-intewnaw t-tweet
  // dewetion and can wetuwn futuwe.exception t-to cancew the d-dewete due to a-a vawidation ewwow o-ow
  // wetuwn a-a [[deweteauthowization]] specifying the weason the dewetion is a-awwowed. σωσ
  type vawidatedewetetweets = futuweawwow[dewetetweetscontext, (U ﹏ U) deweteauthowization]

  vaw usewfiewdsfowdewete: set[usewfiewd] =
    s-set(usewfiewd.account, rawr usewfiewd.pwofiwe, -.- usewfiewd.wowes, ( ͡o ω ͡o ) usewfiewd.safety)

  v-vaw usewquewyoptions: u-usewquewyoptions =
    u-usewquewyoptions(
      usewfiewdsfowdewete, >_<
      u-usewvisibiwity.aww
    )

  // usew_agent pwopewty o-owiginates fwom t-the cwient so twuncate to a weasonabwe wength
  vaw maxusewagentwength = 1000

  // age undew which we tweat n-nyot found tweets in
  // cascaded_dewete_tweet a-as a tempowawy condition (the most w-wikewy
  // expwanation b-being that the tweet has nyot yet been
  // w-wepwicated). o.O t-tweets owdew than this we assume a-awe due to
  // *pewmanentwy* i-inconsistent data, σωσ eithew spuwious edges in tfwock ow
  // tweets that awe nyot w-woadabwe fwom m-manhattan. -.-
  vaw m-maxcascadeddewetetweettempowawyinconsistencyage: duwation =
    10.minutes
}

t-twait tweetdewetepathhandwew {
  i-impowt tweetdewetepathhandwew.vawidatedewetetweets

  def cascadeddewetetweet(wequest: c-cascadeddewetetweetwequest): futuwe[unit]

  def dewetetweets(
    wequest: dewetetweetswequest, σωσ
    i-isunwetweetedits: boowean = f-fawse, :3
  ): futuwe[seq[dewetetweetwesuwt]]

  def intewnawdewetetweets(
    w-wequest: dewetetweetswequest, ^^
    b-byusewid: option[usewid], òωó
    authenticatedusewid: option[usewid], (ˆ ﻌ ˆ)♡
    v-vawidate: vawidatedewetetweets, XD
    isunwetweetedits: boowean = fawse
  ): futuwe[seq[dewetetweetwesuwt]]

  d-def unwetweetedits(
    opteditcontwow: option[editcontwow], òωó
    e-excwudedtweetid: t-tweetid,
    byusewid: usewid
  ): futuwe[unit]
}

/**
 * i-impwementation o-of tweetdewetepathhandwew
 */
cwass defauwttweetdewetepathhandwew(
  stats: statsweceivew, (ꈍᴗꈍ)
  t-tweetwesuwtwepo: tweetwesuwtwepositowy.type,
  u-usewwepo: usewwepositowy.optionaw, UwU
  stwatosafetywabewswepo: stwatosafetywabewswepositowy.type, >w<
  wastquoteofquotewwepo: wastquoteofquotewwepositowy.type, ʘwʘ
  tweetstowe: t-totawtweetstowe,
  getpewspectives: getpewspectives)
    e-extends tweetdewetepathhandwew {
  i-impowt tweetdewetepathhandwew._

  vaw tweetwepo: t-tweetwepositowy.type = tweetwepositowy.fwomtweetwesuwt(tweetwesuwtwepo)

  // a-attempt to d-dewete tweets w-was made by someone othew than the t-tweet ownew ow a-an admin usew
  object dewetetweetspewmissionexception extends e-exception with n-nyostacktwace
  o-object expectedusewidmismatchexception extends exception with nyostacktwace

  pwivate[this] v-vaw wog = woggew("com.twittew.tweetypie.stowe.tweetdewetions")

  pwivate[this] v-vaw c-cascadeeditdewete = stats.scope("cascade_edit_dewete")
  pwivate[this] vaw cascadeeditdewetesenqueued = c-cascadeeditdewete.countew("enqueued")
  p-pwivate[this] vaw c-cascadeeditdewetetweets = c-cascadeeditdewete.countew("tweets")
  pwivate[this] v-vaw cascadeeditdewetefaiwuwes = cascadeeditdewete.countew("faiwuwes")

  pwivate[this] vaw cascadeddewetetweet = stats.scope("cascaded_dewete_tweet")
  pwivate[this] v-vaw cascadeddewetetweetfaiwuwes = cascadeddewetetweet.countew("faiwuwes")
  p-pwivate[this] vaw cascadeddewetetweetsouwcematch = c-cascadeddewetetweet.countew("souwce_match")
  pwivate[this] v-vaw cascadeddewetetweetsouwcemismatch =
    cascadeddewetetweet.countew("souwce_mismatch")
  pwivate[this] v-vaw c-cascadeddewetetweettweetnotfound =
    c-cascadeddewetetweet.countew("tweet_not_found")
  p-pwivate[this] v-vaw cascadeddewetetweettweetnotfoundage =
    cascadeddewetetweet.stat("tweet_not_found_age")
  pwivate[this] vaw cascadeddewetetweetusewnotfound = cascadeddewetetweet.countew("usew_not_found")

  pwivate[this] vaw dewetetweets = s-stats.scope("dewete_tweets")
  p-pwivate[this] v-vaw dewetetweetsauth = dewetetweets.scope("pew_tweet_auth")
  p-pwivate[this] vaw dewetetweetsauthattempts = dewetetweetsauth.countew("attempts")
  pwivate[this] v-vaw dewetetweetsauthfaiwuwes = d-dewetetweetsauth.countew("faiwuwes")
  pwivate[this] vaw d-dewetetweetsauthsuccessadmin = dewetetweetsauth.countew("success_admin")
  pwivate[this] v-vaw dewetetweetsauthsuccessbyusew = dewetetweetsauth.countew("success_by_usew")
  p-pwivate[this] vaw dewetetweetstweets = d-dewetetweets.countew("tweets")
  p-pwivate[this] vaw dewetetweetsfaiwuwes = dewetetweets.countew("faiwuwes")
  pwivate[this] vaw dewetetweetstweetnotfound = dewetetweets.countew("tweet_not_found")
  p-pwivate[this] v-vaw dewetetweetsusewnotfound = d-dewetetweets.countew("usew_not_found")
  pwivate[this] v-vaw u-usewidmismatchintweetdewete =
    dewetetweets.countew("expected_actuaw_usew_id_mismatch")
  p-pwivate[this] v-vaw bouncedewetefwagnotset =
    d-dewetetweets.countew("bounce_dewete_fwag_not_set")

  p-pwivate[this] def getusew(usewid: u-usewid): futuwe[option[usew]] =
    stitch.wun(usewwepo(usewkey(usewid), :3 usewquewyoptions))

  p-pwivate[this] def getusewsfowdewetetweets(usewids: s-seq[usewid]): f-futuwe[map[usewid, ^•ﻌ•^ usew]] =
    s-stitch.wun(
      stitch
        .twavewse(usewids) { usewid =>
          usewwepo(usewkey(usewid), (ˆ ﻌ ˆ)♡ u-usewquewyoptions).map {
            c-case s-some(u) => some(usewid -> u)
            case nyone => dewetetweetsusewnotfound.incw(); n-nyone
          }
        }
        .map(_.fwatten.tomap)
    )

  pwivate[this] def gettweet(tweetid: t-tweetid): futuwe[tweet] =
    stitch.wun(tweetwepo(tweetid, 🥺 w-wwitepathquewyoptions.dewetetweetswithouteditcontwow))

  pwivate[this] d-def getsingwedewetedtweet(
    id: tweetid, OwO
    i-iscascadededittweetdewetion: b-boowean = fawse
  ): stitch[option[tweetdata]] = {
    vaw opts = i-if (iscascadededittweetdewetion) {
      // disabwe edit contwow hydwation if t-this is cascade d-dewete of edits. 🥺
      // when e-edit contwow is hydwated, OwO the tweet w-wiww actuawwy b-be considewed a-awweady deweted.
      wwitepathquewyoptions.dewetetweetswithouteditcontwow
    } ewse {
      wwitepathquewyoptions.dewetetweets
    }
    tweetwesuwtwepo(id, (U ᵕ U❁) opts)
      .map(_.vawue)
      .wifttooption {
        // we tweat the wequest the same whethew the tweet nyevew
        // existed ow is in one of the awweady-deweted s-states b-by
        // just fiwtewing out those tweets. ( ͡o ω ͡o ) a-any tweets that w-we
        // wetuwn s-shouwd be deweted. if the tweet h-has been
        // bounce-deweted, ^•ﻌ•^ w-we nyevew w-want to soft-dewete it, o.O and
        // v-vice vewsa. (⑅˘꒳˘)
        case n-nyotfound | fiwtewedstate.unavaiwabwe.tweetdeweted |
            f-fiwtewedstate.unavaiwabwe.bouncedeweted =>
          twue
      }
  }

  pwivate[this] d-def gettweetsfowdewetetweets(
    i-ids: s-seq[tweetid], (ˆ ﻌ ˆ)♡
    i-iscascadededittweetdewetion: b-boowean
  ): futuwe[map[tweetid, t-tweetdata]] =
    s-stitch
      .wun {
        s-stitch.twavewse(ids) { i-id =>
          getsingwedewetedtweet(id, :3 i-iscascadededittweetdewetion)
            .map {
              // w-when deweting a-a tweet that has been edited, /(^•ω•^) we w-want to instead dewete the initiaw vewsion. òωó
              // b-because the initiaw t-tweet wiww be h-hydwated in evewy w-wequest, :3 if it is deweted, (˘ω˘) watew
              // w-wevisions wiww be hidden, 😳 and c-cweaned up asynchwonouswy by tp d-daemons

              // howevew, σωσ w-we don't nyeed to do a second wookup if it's awweady the owiginaw tweet
              // o-ow if we'we doing a-a cascading edit t-tweet dewete (deweting the entiwe tweet histowy)
              case some(tweetdata)
                  i-if editcontwowutiw.isinitiawtweet(tweetdata.tweet) ||
                    iscascadededittweetdewetion =>
                s-stitch.vawue(some(tweetdata))
              c-case s-some(tweetdata) =>
                getsingwedewetedtweet(editcontwowutiw.getinitiawtweetid(tweetdata.tweet))
              case n-nyone =>
                s-stitch.vawue(none)
              // we n-nyeed to pwesewve the input tweetid, UwU and the initiaw t-tweetdata
            }.fwatten.map(tweetdata => (id, tweetdata))
        }
      }
      .map(_.cowwect { c-case (tweetid, -.- s-some(tweetdata)) => (tweetid, 🥺 t-tweetdata) }.tomap)

  pwivate[this] d-def getstwatobouncestatuses(
    i-ids: seq[wong], 😳😳😳
    i-isusewewasuwe: b-boowean, 🥺
    iscascadededitedtweetdewetion: b-boowean
  ): f-futuwe[map[tweetid, ^^ b-boowean]] = {
    // d-don't woad b-bounce wabew f-fow usew ewasuwe t-tweet dewetion. ^^;;
    // u-usew ewasuwe dewetions c-cause unnecessawy spikes of twaffic
    // t-to stwato when we wead t-the bounce wabew t-that we don't u-use. >w<

    // we awso want to awways dewete a bounced tweet if the w-west of the
    // e-edit chain i-is being deweted in a cascaded edit tweet dewete
    if (isusewewasuwe || i-iscascadededitedtweetdewetion) {
      f-futuwe.vawue(ids.map(id => id -> f-fawse).tomap)
    } e-ewse {
      stitch.wun(
        stitch
          .twavewse(ids) { id =>
            s-stwatosafetywabewswepo(id, σωσ s-safetywabewtype.bounce).map { w-wabew =>
              i-id -> wabew.isdefined
            }
          }
          .map(_.tomap)
      )
    }
  }

  /** a suspended/deactivated u-usew can't d-dewete tweets */
  pwivate[this] def usewnotsuspendedowdeactivated(usew: u-usew): twy[usew] =
    usew.safety match {
      c-case none => thwow(upstweamfaiwuwe.usewsafetyemptyexception)
      c-case s-some(safety) if safety.deactivated =>
        t-thwow(
          a-accessdenied(
            s"usew d-deactivated usewid: ${usew.id}", >w<
            ewwowcause = some(accessdeniedcause.usewdeactivated)
          )
        )
      c-case some(safety) i-if safety.suspended =>
        t-thwow(
          a-accessdenied(
            s"usew s-suspended usewid: ${usew.id}",
            e-ewwowcause = s-some(accessdeniedcause.usewsuspended)
          )
        )
      case _ => w-wetuwn(usew)
    }

  /**
   * ensuwe that byusew has pewmission t-to dewete t-tweet eithew by v-viwtue of owning the tweet ow being
   * an admin usew. (⑅˘꒳˘)  wetuwns the weason as a-a deweteauthowization ow ewse thwows a-an exception i-if nyot
   * authowized. òωó
   */
  pwivate[this] d-def usewauthowizedtodewetetweet(
    byusew: usew, (⑅˘꒳˘)
    o-optauthenticatedusewid: o-option[usewid], (ꈍᴗꈍ)
    t-tweetauthowid: u-usewid
  ): t-twy[deweteauthowization] = {

    def hasadminpwiviwege =
      byusew.wowes.exists(_.wights.contains("dewete_usew_tweets"))

    dewetetweetsauthattempts.incw()
    if (byusew.id == t-tweetauthowid) {
      dewetetweetsauthsuccessbyusew.incw()
      o-optauthenticatedusewid match {
        case some(uid) =>
          wetuwn(authowizedbytweetcontwibutow(uid))
        c-case nyone =>
          wetuwn(authowizedbytweetownew(byusew.id))
      }
    } ewse if (optauthenticatedusewid.isempty && h-hasadminpwiviwege) { // c-contwibutow may not assume admin w-wowe
      dewetetweetsauthsuccessadmin.incw()
      wetuwn(authowizedbyadmin(byusew.id))
    } ewse {
      dewetetweetsauthfaiwuwes.incw()
      t-thwow(dewetetweetspewmissionexception)
    }
  }

  /**
   * e-expected usew id is the id pwovided o-on the dewetetweetswequest that the indicates w-which usew
   * owns the tweets they want to dewete. rawr x3 the actuawusewid i-is the actuaw usewid on the tweet we awe a-about to dewete. ( ͡o ω ͡o )
   * w-we check t-to ensuwe they awe the same as a safety check a-against accidentaw dewetion of tweets eithew fwom usew mistakes
   * ow fwom cowwupted d-data (e.g b-bad tfwock edges)
   */
  p-pwivate[this] d-def expectedusewidmatchesactuawusewid(
    expectedusewid: usewid, UwU
    a-actuawusewid: usewid
  ): t-twy[unit] =
    if (expectedusewid == actuawusewid) {
      w-wetuwn.unit
    } ewse {
      usewidmismatchintweetdewete.incw()
      t-thwow(expectedusewidmismatchexception)
    }

  /**
   * vawidation fow the nyowmaw p-pubwic tweet dewete c-case, ^^ the usew must be found a-and must
   * n-nyot be suspended o-ow deactivated. (˘ω˘)
   */
  vaw vawidatetweetsfowpubwicdewete: vawidatedewetetweets = f-futuweawwow {
    ctx: dewetetweetscontext =>
      futuwe.const(
        fow {

          // b-byusewid must be pwesent
          byusewid <- ctx.byusewid.owthwow(
            c-cwientewwow(cwientewwowcause.badwequest, (ˆ ﻌ ˆ)♡ "missing b-byusewid")
          )

          // t-the byusew m-must be found
          b-byusewopt = ctx.usews.get(byusewid)
          b-byusew <- byusewopt.owthwow(
            cwientewwow(cwientewwowcause.badwequest, OwO s-s"usew $byusewid nyot found")
          )

          _ <- u-usewnotsuspendedowdeactivated(byusew)

          _ <- vawidatebounceconditions(
            ctx.tweetisbounced,
            c-ctx.isbouncedewete
          )

          // i-if thewe's a contwibutow, 😳 make s-suwe the usew is found and nyot s-suspended ow deactivated
          _ <-
            c-ctx.authenticatedusewid
              .map { uid =>
                c-ctx.usews.get(uid) m-match {
                  case nyone =>
                    t-thwow(cwientewwow(cwientewwowcause.badwequest, UwU s"contwibutow $uid nyot found"))
                  case some(authusew) =>
                    u-usewnotsuspendedowdeactivated(authusew)
                }
              }
              .getowewse(wetuwn.unit)

          // if the expected u-usew id is pwesent, 🥺 make suwe it matches the usew i-id on the tweet
          _ <-
            ctx.expectedewasuweusewid
              .map { e-expectedusewid =>
                e-expectedusewidmatchesactuawusewid(expectedusewid, 😳😳😳 ctx.tweetauthowid)
              }
              .getowewse(wetuwn.unit)

          // u-usew must o-own the tweet ow be an admin
          d-deweteauth <- usewauthowizedtodewetetweet(
            b-byusew, ʘwʘ
            ctx.authenticatedusewid, /(^•ω•^)
            c-ctx.tweetauthowid
          )
        } y-yiewd deweteauth
      )
  }

  pwivate def vawidatebounceconditions(
    tweetisbounced: boowean, :3
    isbouncedewete: b-boowean
  ): t-twy[unit] = {
    if (tweetisbounced && !isbouncedewete) {
      bouncedewetefwagnotset.incw()
      thwow(cwientewwow(cwientewwowcause.badwequest, :3 "cannot n-nyowmaw dewete a bounced tweet"))
    } e-ewse {
      w-wetuwn.unit
    }
  }

  /**
   * vawidation fow the usew ewasuwe case. mya usew may be missing. (///ˬ///✿)
   */
  v-vaw vawidatetweetsfowusewewasuwedaemon: vawidatedewetetweets = f-futuweawwow {
    ctx: d-dewetetweetscontext =>
      futuwe
        .const(
          f-fow {
            expectedusewid <- c-ctx.expectedewasuweusewid.owthwow(
              c-cwientewwow(
                c-cwientewwowcause.badwequest, (⑅˘꒳˘)
                "expectedusewid is w-wequiwed fow dewetetweetwequests"
              )
            )

            // i-it's cwiticaw t-to awways check that the usewid on the tweet we want to dewete matches the
            // usewid o-on the ewasuwe w-wequest. :3 this pwevents u-us fwom accidentawwy d-deweting t-tweets nyot o-owned by the
            // ewased usew, /(^•ω•^) even if tfwock sewves us bad data. ^^;;
            v-vawidationwesuwt <- e-expectedusewidmatchesactuawusewid(expectedusewid, (U ᵕ U❁) ctx.tweetauthowid)
          } yiewd vawidationwesuwt
        )
        .map(_ => authowizedbyewasuwe)
  }

  /**
   * f-fiww in missing v-vawues of a-auditdewetetweet with vawues fwom twittewcontext. (U ﹏ U)
   */
  d-def enwichmissingfwomtwittewcontext(owig: auditdewetetweet): auditdewetetweet = {
    v-vaw viewew = twittewcontext()
    o-owig.copy(
      host = owig.host.owewse(viewew.fwatmap(_.auditip)), mya
      cwientappwicationid = o-owig.cwientappwicationid.owewse(viewew.fwatmap(_.cwientappwicationid)), ^•ﻌ•^
      usewagent = owig.usewagent.owewse(viewew.fwatmap(_.usewagent)).map(_.take(maxusewagentwength))
    )
  }

  /**
   * c-cowe dewete t-tweets impwementation. (U ﹏ U)
   *
   * the [[dewetetweets]] m-method wwaps t-this method a-and pwovides vawidation w-wequiwed
   * f-fow a pubwic e-endpoint. :3
   */
  ovewwide def i-intewnawdewetetweets(
    w-wequest: dewetetweetswequest, rawr x3
    byusewid: o-option[usewid], 😳😳😳
    authenticatedusewid: option[usewid], >w<
    v-vawidate: vawidatedewetetweets, òωó
    i-isunwetweetedits: boowean = f-fawse
  ): f-futuwe[seq[dewetetweetwesuwt]] = {

    vaw auditdewetetweet =
      enwichmissingfwomtwittewcontext(wequest.auditpassthwough.getowewse(auditdewetetweet()))
    d-dewetetweetstweets.incw(wequest.tweetids.size)
    fow {
      tweetdatamap <- g-gettweetsfowdewetetweets(
        w-wequest.tweetids, 😳
        wequest.cascadededitedtweetdewetion.getowewse(fawse)
      )

      usewids: seq[usewid] = (tweetdatamap.vawues.map { t-td =>
          g-getusewid(td.tweet)
        } ++ byusewid ++ a-authenticatedusewid).toseq.distinct

      usews <- getusewsfowdewetetweets(usewids)

      s-stwatobouncestatuses <- g-getstwatobouncestatuses(
        tweetdatamap.keys.toseq, (✿oωo)
        w-wequest.isusewewasuwe, OwO
        w-wequest.cascadededitedtweetdewetion.getowewse(fawse))

      wesuwts <- futuwe.cowwect {
        wequest.tweetids.map { t-tweetid =>
          t-tweetdatamap.get(tweetid) m-match {
            // a-awweady deweted, (U ﹏ U) so nyothing to do
            case nyone =>
              dewetetweetstweetnotfound.incw()
              futuwe.vawue(dewetetweetwesuwt(tweetid, (ꈍᴗꈍ) tweetdewetestate.ok))
            c-case some(tweetdata) =>
              v-vaw t-tweet: tweet = t-tweetdata.tweet
              v-vaw t-tweetisbounced = stwatobouncestatuses(tweetid)
              vaw o-optsouwcetweet: o-option[tweet] = tweetdata.souwcetweetwesuwt.map(_.vawue.tweet)

              v-vaw vawidation: f-futuwe[(boowean, rawr deweteauthowization)] = fow {
                i-iswastquoteofquotew <- isfinawquoteofquotew(tweet)
                deweteauth <- v-vawidate(
                  dewetetweetscontext(
                    b-byusewid = b-byusewid, ^^
                    authenticatedusewid = authenticatedusewid, rawr
                    t-tweetauthowid = g-getusewid(tweet), nyaa~~
                    u-usews = usews, nyaa~~
                    isusewewasuwe = w-wequest.isusewewasuwe, o.O
                    e-expectedewasuweusewid = wequest.expectedusewid, òωó
                    t-tweetisbounced = tweetisbounced, ^^;;
                    i-isbouncedewete = w-wequest.isbouncedewete
                  )
                )
                _ <- o-optsouwcetweet match {
                  c-case some(souwcetweet) if !isunwetweetedits =>
                    // if t-this is a wetweet and this dewetion was nyot twiggewed by
                    // unwetweetedits, rawr unwetweet edits of the souwce tweet
                    // b-befowe deweting the wetweet. ^•ﻌ•^
                    //
                    // deweteauth wiww awways contain a byusewid except fow ewasuwe d-dewetion, nyaa~~
                    // in which case the wetweets w-wiww be deweted individuawwy. nyaa~~
                    d-deweteauth.byusewid match {
                      case some(usewid) =>
                        u-unwetweetedits(souwcetweet.editcontwow, 😳😳😳 souwcetweet.id, 😳😳😳 u-usewid)
                      case none => f-futuwe.unit
                    }
                  c-case _ => futuwe.unit
                }
              } yiewd {
                (iswastquoteofquotew, σωσ d-deweteauth)
              }

              vawidation
                .fwatmap {
                  case (iswastquoteofquotew: boowean, o.O d-deweteauth: deweteauthowization) =>
                    v-vaw isadmindewete = d-deweteauth match {
                      case authowizedbyadmin(_) => t-twue
                      c-case _ => fawse
                    }

                    vaw event =
                      dewetetweet.event(
                        t-tweet = tweet, σωσ
                        timestamp = time.now, nyaa~~
                        usew = u-usews.get(getusewid(tweet)), rawr x3
                        byusewid = deweteauth.byusewid, (///ˬ///✿)
                        auditpassthwough = some(auditdewetetweet), o.O
                        i-isusewewasuwe = w-wequest.isusewewasuwe, òωó
                        isbouncedewete = w-wequest.isbouncedewete && t-tweetisbounced, OwO
                        iswastquoteofquotew = i-iswastquoteofquotew, σωσ
                        isadmindewete = isadmindewete
                      )
                    vaw nyumbewofedits: int = tweet.editcontwow
                      .cowwect {
                        c-case editcontwow.initiaw(initiaw) =>
                          i-initiaw.edittweetids.count(_ != tweet.id)
                      }
                      .getowewse(0)
                    c-cascadeeditdewetesenqueued.incw(numbewofedits)
                    t-tweetstowe
                      .dewetetweet(event)
                      .map(_ => dewetetweetwesuwt(tweetid, nyaa~~ t-tweetdewetestate.ok))
                }
                .onfaiwuwe { _ =>
                  dewetetweetsfaiwuwes.incw()
                }
                .handwe {
                  case e-expectedusewidmismatchexception =>
                    dewetetweetwesuwt(tweetid, OwO tweetdewetestate.expectedusewidmismatch)
                  c-case d-dewetetweetspewmissionexception =>
                    dewetetweetwesuwt(tweetid, ^^ tweetdewetestate.pewmissionewwow)
                }
          }
        }
      }
    } y-yiewd wesuwts
  }

  pwivate def isfinawquoteofquotew(tweet: tweet): futuwe[boowean] = {
    tweet.quotedtweet match {
      case some(qt) =>
        s-stitch.wun {
          w-wastquoteofquotewwepo
            .appwy(qt.tweetid, (///ˬ///✿) getusewid(tweet))
            .wifttotwy
            .map(_.getowewse(fawse))
        }
      case n-nyone => futuwe(fawse)
    }
  }

  /**
   *  v-vawidations fow the pubwic dewetetweets e-endpoint. σωσ
   *   - ensuwes that the byusewid usew can be found and is in the cowwect usew s-state
   *   - ensuwes that the tweet is being deweted by the tweet's ownew, rawr x3 ow b-by an admin
   *  i-if thewe is a-a vawidation ewwow, (ˆ ﻌ ˆ)♡ a futuwe.exception is wetuwned
   *
   *  if t-the dewete wequest i-is pawt of a u-usew ewasuwe, 🥺 vawidations awe wewaxed (the u-usew is awwowed to be m-missing). (⑅˘꒳˘)
   */
  vaw dewetetweetsvawidatow: vawidatedewetetweets =
    f-futuweawwow { context =>
      i-if (context.isusewewasuwe) {
        vawidatetweetsfowusewewasuwedaemon(context)
      } ewse {
        v-vawidatetweetsfowpubwicdewete(context)
      }
    }

  ovewwide d-def dewetetweets(
    w-wequest: dewetetweetswequest, 😳😳😳
    i-isunwetweetedits: b-boowean = fawse, /(^•ω•^)
  ): f-futuwe[seq[dewetetweetwesuwt]] = {

    // fow c-compawison testing we onwy want t-to compawe the d-dewetetweetswequests that awe genewated
    // in dewetetweets path a-and nyot the caww that comes fwom the unwetweet path
    vaw context = twittewcontext()
    intewnawdewetetweets(
      wequest, >w<
      byusewid = w-wequest.byusewid.owewse(context.fwatmap(_.usewid)), ^•ﻌ•^
      context.fwatmap(_.authenticatedusewid),
      dewetetweetsvawidatow, 😳😳😳
      i-isunwetweetedits
    )
  }

  // cascade d-dewete tweet is the wogic fow wemoving tweets t-that awe detached
  // fwom theiw dependency which h-has been deweted. :3 they awe awweady fiwtewed
  // o-out fwom sewving, (ꈍᴗꈍ) so this opewation weconciwes s-stowage with the view
  // pwesented by tweetypie. ^•ﻌ•^
  // t-this w-wpc caww is dewegated fwom daemons ow batch jobs. >w< c-cuwwentwy thewe
  // a-awe two use-cases when t-this caww is issued:
  // *   d-deweting detached wetweets aftew the s-souwce tweet was deweted. ^^;;
  //     this is done thwough wetweetsdewetion d-daemon and the
  //     cweanupdetachedwetweets job. (✿oωo)
  // *   d-deweting e-edits of an initiaw t-tweet that has been deweted. òωó
  //     this is done by cascadededitedtweetdewete d-daemon. ^^
  //     nyote that, w-when sewving the owiginaw dewete w-wequest fow a-an edit, ^^
  //     the initiaw tweet is onwy deweted, rawr which makes aww edits hidden. XD
  ovewwide def c-cascadeddewetetweet(wequest: c-cascadeddewetetweetwequest): futuwe[unit] = {
    vaw contextviewew = t-twittewcontext()
    gettweet(wequest.tweetid)
      .twansfowm {
        case thwow(
              f-fiwtewedstate.unavaiwabwe.tweetdeweted | f-fiwtewedstate.unavaiwabwe.bouncedeweted) =>
          // t-the w-wetweet ow edit w-was awweady deweted v-via some othew mechanism
          futuwe.unit

        c-case t-thwow(notfound) =>
          c-cascadeddewetetweettweetnotfound.incw()
          v-vaw wecentwycweated =
            i-if (snowfwakeid.issnowfwakeid(wequest.tweetid)) {
              v-vaw age = time.now - snowfwakeid(wequest.tweetid).time
              c-cascadeddewetetweettweetnotfoundage.add(age.inmiwwiseconds)
              a-age < maxcascadeddewetetweettempowawyinconsistencyage
            } e-ewse {
              fawse
            }

          if (wecentwycweated) {
            // tweat t-the nyotfound as a tempowawy condition, rawr most
            // w-wikewy due to wepwication wag. 😳
            futuwe.exception(cascadeddewetenotavaiwabwe(wequest.tweetid))
          } e-ewse {
            // t-tweat the nyotfound as a pewmanent inconsistenty, 🥺 eithew
            // s-spuwious edges i-in tfwock ow invawid data in m-manhattan. (U ᵕ U❁) this
            // was h-happening a few times an houw duwing the time that we
            // w-wewe nyot t-tweating it speciawwy. 😳 fow nyow, we wiww just w-wog that
            // i-it happened, 🥺 but in the wongew tewm, (///ˬ///✿) it w-wouwd be good
            // to cowwect this data and wepaiw the cowwuption. mya
            wog.wawn(
              s-seq(
                "cascaded_dewete_tweet_owd_not_found", (✿oωo)
                wequest.tweetid,
                wequest.cascadedfwomtweetid
              ).mkstwing("\t")
            )
            f-futuwe.done
          }

        // a-any othew f-fiwtewedstates shouwd nyot be thwown b-because of
        // t-the o-options that we u-used to woad the t-tweet, ^•ﻌ•^ so we wiww just
        // wet them bubbwe u-up as an intewnaw s-sewvew ewwow
        c-case thwow(othew) =>
          futuwe.exception(othew)

        c-case wetuwn(tweet) =>
          f-futuwe
            .join(
              i-isfinawquoteofquotew(tweet), o.O
              getusew(getusewid(tweet))
            )
            .fwatmap {
              c-case (iswastquoteofquotew, o.O u-usew) =>
                i-if (usew.isempty) {
                  c-cascadeddewetetweetusewnotfound.incw()
                }
                v-vaw tweetsouwceid = g-getshawe(tweet).map(_.souwcestatusid)
                vaw initiaweditid = t-tweet.editcontwow.cowwect {
                  c-case editcontwow.edit(edit) => edit.initiawtweetid
                }
                if (initiaweditid.contains(wequest.cascadedfwomtweetid)) {
                  cascadeeditdewetetweets.incw()
                }
                i-if (tweetsouwceid.contains(wequest.cascadedfwomtweetid)
                  || i-initiaweditid.contains(wequest.cascadedfwomtweetid)) {
                  cascadeddewetetweetsouwcematch.incw()
                  v-vaw deweteevent =
                    d-dewetetweet.event(
                      tweet = tweet, XD
                      t-timestamp = t-time.now, ^•ﻌ•^
                      u-usew = u-usew, ʘwʘ
                      b-byusewid = c-contextviewew.fwatmap(_.usewid), (U ﹏ U)
                      cascadedfwomtweetid = some(wequest.cascadedfwomtweetid), 😳😳😳
                      auditpassthwough = w-wequest.auditpassthwough, 🥺
                      isusewewasuwe = fawse, (///ˬ///✿)
                      // cascaded dewetes of wetweets ow e-edits have nyot b-been thwough a bouncew fwow, (˘ω˘)
                      // so awe nyot considewed to b-be "bounce deweted".
                      i-isbouncedewete = fawse,
                      iswastquoteofquotew = i-iswastquoteofquotew, :3
                      isadmindewete = f-fawse
                    )
                  t-tweetstowe
                    .dewetetweet(deweteevent)
                    .onfaiwuwe { _ =>
                      i-if (initiaweditid.contains(wequest.cascadedfwomtweetid)) {
                        cascadeeditdewetefaiwuwes.incw()
                      }
                    }
                } ewse {
                  cascadeddewetetweetsouwcemismatch.incw()
                  w-wog.wawn(
                    seq(
                      "cascaded_fwom_tweet_id_souwce_mismatch", /(^•ω•^)
                      wequest.tweetid, :3
                      w-wequest.cascadedfwomtweetid, mya
                      tweetsouwceid.owewse(initiaweditid).getowewse("-")
                    ).mkstwing("\t")
                  )
                  f-futuwe.done
                }
            }
      }
      .onfaiwuwe(_ => cascadeddewetetweetfaiwuwes.incw())
  }

  // given a wist of edit t-tweet ids and a usew id, XD find t-the wetweet ids of those edit ids fwom the given u-usew
  pwivate def edittweetidwetweetsfwomusew(
    e-edittweetids: seq[tweetid], (///ˬ///✿)
    byusewid: usewid
  ): futuwe[seq[tweetid]] = {
    if (edittweetids.isempty) {
      futuwe.vawue(seq())
    } ewse {
      g-getpewspectives(
        s-seq(tws.pewspectivequewy(byusewid, 🥺 edittweetids))
      ).map { w-wes: s-seq[pewspectivewesuwt] =>
        wes.headoption.toseq
          .fwatmap(_.pewspectives.fwatmap(_.wetweetid))
      }
    }
  }

  /* this function i-is cawwed fwom thwee pwaces -
   * 1. o.O when tweetypie gets a-a wequest to wetweet t-the watest v-vewsion of an edit c-chain, mya aww the
   * pwevious wevisons shouwd be unwetweeted. rawr x3
   * i.e. on wetweet o-of the watest t-tweet - unwetweets aww the pwevious wevisions fow this usew. 😳
   * - c-cweate a
   * - wetweet a'(wetweet o-of a)
   * - c-cweate edit b-b(edit of a)
   * - wetweet b' => dewetes a'
   *
   * 2. 😳😳😳 when tweetypie gets an unwetweet wequest f-fow a souwce tweet that is a-an edit tweet, >_< aww
   * the vewsions of the edit chain is wetweeted. >w<
   * i-i.e. rawr x3 on unwetweet of a-any vewsion in the edit chain - unwetweets aww the w-wevisions fow t-this usew
   * - c-cweate a
   * - w-wetweet a'
   * - c-cweate b
   * - unwetweet b => d-dewetes a' (& a-awso any b' if it existed)
   *
   * 3. XD w-when tweetypie gets a dewete wequest fow a-a wetweet, ^^ say a1. & if a happens t-to the souwce
   * t-tweet fow a1 & if a is an e-edit tweet, (✿oωo) then t-the entiwe edit chain shouwd be unwetweeted & nyot
   * a. >w< i.e. 😳😳😳 o-on dewete of a w-wetweet - unwetweet a-aww the wevisions f-fow this usew.
   * - cweate a
   * - wetweet a'
   * - cweate b-b
   * - dewete a' => dewetes a' (& awso any b-b' if it existed)
   *
   * the fowwowing function has two faiwuwe s-scenawios -
   * i. (ꈍᴗꈍ) when it faiws to get pewspectives of any o-of the edit tweets. (✿oωo)
   * ii. (˘ω˘) t-the dewetion of a-any of the wetweets o-of these edits faiw. nyaa~~
   *
   * i-in eithew of t-this scenawio, ( ͡o ω ͡o ) we faiw the entiwe w-wequest & the e-ewwow bubbwes up t-to the top. 🥺
   * n-nyote: the above unwetweet of e-edits onwy happens f-fow the cuwwent u-usew. (U ﹏ U)
   * in nyowmaw ciwcumstances, ( ͡o ω ͡o ) a-a maximum of one tweet in the edit chain wiww have been wetweeted, (///ˬ///✿)
   * but we don't know w-which one it was. (///ˬ///✿) a-additionawwy, (✿oωo) thewe may be ciwcumstances w-whewe
   * unwetweet faiwed, (U ᵕ U❁) and we e-end up with muwtipwe v-vewsions wetweeted. f-fow these w-weasons, ʘwʘ
   * we awways unwetweet a-aww the wevisions (except fow `excwudedtweetid`). ʘwʘ
   * this i-is a nyo-op if n-nyone of these vewsions have been wetweeted. XD
   * */
  ovewwide d-def unwetweetedits(
    opteditcontwow: o-option[editcontwow], (✿oωo)
    excwudedtweetid: tweetid,
    b-byusewid: usewid
  ): futuwe[unit] = {

    v-vaw edittweetids: seq[tweetid] =
      editcontwowutiw.getedittweetids(opteditcontwow).get().fiwtew(_ != e-excwudedtweetid)

    (edittweetidwetweetsfwomusew(edittweetids, byusewid).fwatmap { t-tweetids =>
      if (tweetids.nonempty) {
        d-dewetetweets(
          d-dewetetweetswequest(tweetids = tweetids, ^•ﻌ•^ byusewid = some(byusewid)), ^•ﻌ•^
          i-isunwetweetedits = twue
        )
      } ewse {
        f-futuwe.niw
      }
    }).unit
  }
}
