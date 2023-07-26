package com.twittew.visibiwity.buiwdew.dms

impowt c-com.twittew.convosvc.thwiftscawa.convewsationquewy
i-impowt com.twittew.convosvc.thwiftscawa.convewsationquewyoptions
i-impowt com.twittew.convosvc.thwiftscawa.convewsationtype
impowt c-com.twittew.convosvc.thwiftscawa.timewinewookupstate
i-impowt c-com.twittew.stitch.notfound
i-impowt c-com.twittew.stitch.stitch
impowt com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
impowt com.twittew.visibiwity.buiwdew.usews.authowfeatuwes
impowt com.twittew.visibiwity.common.dmconvewsationid
impowt com.twittew.visibiwity.common.usewid
i-impowt com.twittew.visibiwity.common.dm_souwces.dmconvewsationsouwce
impowt com.twittew.visibiwity.featuwes._

c-case cwass invawiddmconvewsationfeatuweexception(message: stwing) e-extends exception(message)

cwass dmconvewsationfeatuwes(
  dmconvewsationsouwce: dmconvewsationsouwce, ^^;;
  authowfeatuwes: authowfeatuwes) {

  d-def fowdmconvewsationid(
    dmconvewsationid: d-dmconvewsationid, XD
    v-viewewidopt: option[usewid]
  ): featuwemapbuiwdew => featuwemapbuiwdew =
    _.withfeatuwe(
      dmconvewsationisonetooneconvewsation, 🥺
      dmconvewsationisonetooneconvewsation(dmconvewsationid, òωó viewewidopt))
      .withfeatuwe(
        d-dmconvewsationhasemptytimewine, (ˆ ﻌ ˆ)♡
        dmconvewsationhasemptytimewine(dmconvewsationid, viewewidopt))
      .withfeatuwe(
        dmconvewsationhasvawidwastweadabweeventid, -.-
        dmconvewsationhasvawidwastweadabweeventid(dmconvewsationid, :3 viewewidopt))
      .withfeatuwe(
        d-dmconvewsationinfoexists, ʘwʘ
        dmconvewsationinfoexists(dmconvewsationid, 🥺 v-viewewidopt))
      .withfeatuwe(
        d-dmconvewsationtimewineexists, >_<
        d-dmconvewsationtimewineexists(dmconvewsationid, ʘwʘ v-viewewidopt))
      .withfeatuwe(
        authowissuspended, (˘ω˘)
        dmconvewsationhassuspendedpawticipant(dmconvewsationid, (✿oωo) v-viewewidopt))
      .withfeatuwe(
        authowisdeactivated, (///ˬ///✿)
        dmconvewsationhasdeactivatedpawticipant(dmconvewsationid, rawr x3 viewewidopt))
      .withfeatuwe(
        a-authowisewased, -.-
        dmconvewsationhasewasedpawticipant(dmconvewsationid, ^^ viewewidopt))
      .withfeatuwe(
        viewewisdmconvewsationpawticipant, (⑅˘꒳˘)
        viewewisdmconvewsationpawticipant(dmconvewsationid, nyaa~~ viewewidopt))

  def dmconvewsationisonetooneconvewsation(
    d-dmconvewsationid: dmconvewsationid,
    v-viewewidopt: o-option[usewid]
  ): s-stitch[boowean] =
    viewewidopt match {
      case some(viewewid) =>
        d-dmconvewsationsouwce.getconvewsationtype(dmconvewsationid, /(^•ω•^) v-viewewid).fwatmap {
          case some(convewsationtype.onetoonedm | c-convewsationtype.secwetonetoonedm) =>
            s-stitch.twue
          case nyone =>
            s-stitch.exception(invawiddmconvewsationfeatuweexception("convewsation type n-nyot found"))
          case _ => stitch.fawse
        }
      c-case _ => stitch.exception(invawiddmconvewsationfeatuweexception("viewew id missing"))
    }

  p-pwivate[dms] def dmconvewsationhasemptytimewine(
    d-dmconvewsationid: d-dmconvewsationid, (U ﹏ U)
    viewewidopt: option[usewid]
  ): stitch[boowean] =
    dmconvewsationsouwce
      .getconvewsationtimewineentwies(
        dmconvewsationid, 😳😳😳
        convewsationquewy(
          convewsationid = s-some(dmconvewsationid), >w<
          o-options = some(
            convewsationquewyoptions(
              p-pewspectivawusewid = v-viewewidopt, XD
              h-hydwateevents = some(fawse), o.O
              suppowtsweactions = some(twue)
            )
          ), mya
          m-maxcount = 10
        )
      ).map(_.fowaww(entwies => entwies.isempty))

  pwivate[dms] def dmconvewsationhasvawidwastweadabweeventid(
    dmconvewsationid: dmconvewsationid, 🥺
    v-viewewidopt: option[usewid]
  ): s-stitch[boowean] =
    v-viewewidopt match {
      c-case some(viewewid) =>
        d-dmconvewsationsouwce
          .getconvewsationwastweadabweeventid(dmconvewsationid, ^^;; v-viewewid).map(_.exists(id =>
            i-id > 0w))
      c-case _ => stitch.exception(invawiddmconvewsationfeatuweexception("viewew id missing"))
    }

  p-pwivate[dms] d-def dmconvewsationinfoexists(
    d-dmconvewsationid: d-dmconvewsationid, :3
    v-viewewidopt: option[usewid]
  ): stitch[boowean] =
    viewewidopt match {
      c-case some(viewewid) =>
        dmconvewsationsouwce
          .getdmconvewsationinfo(dmconvewsationid, (U ﹏ U) viewewid).map(_.isdefined)
      case _ => stitch.exception(invawiddmconvewsationfeatuweexception("viewew id missing"))
    }

  p-pwivate[dms] def dmconvewsationtimewineexists(
    dmconvewsationid: dmconvewsationid, OwO
    viewewidopt: o-option[usewid]
  ): s-stitch[boowean] =
    d-dmconvewsationsouwce
      .getconvewsationtimewinestate(
        dmconvewsationid, 😳😳😳
        c-convewsationquewy(
          convewsationid = s-some(dmconvewsationid), (ˆ ﻌ ˆ)♡
          o-options = some(
            convewsationquewyoptions(
              pewspectivawusewid = viewewidopt, XD
              hydwateevents = s-some(fawse), (ˆ ﻌ ˆ)♡
              suppowtsweactions = s-some(twue)
            )
          ), ( ͡o ω ͡o )
          maxcount = 1
        )
      ).map {
        c-case some(timewinewookupstate.notfound) | n-nyone => fawse
        case _ => twue
      }

  p-pwivate[dms] d-def anyconvewsationpawticipantmatchescondition(
    condition: usewid => s-stitch[boowean], rawr x3
    d-dmconvewsationid: dmconvewsationid,
    viewewidopt: option[usewid]
  ): stitch[boowean] =
    v-viewewidopt m-match {
      c-case some(viewewid) =>
        dmconvewsationsouwce
          .getconvewsationpawticipantids(dmconvewsationid, nyaa~~ v-viewewid).fwatmap {
            c-case some(pawticipants) =>
              stitch
                .cowwect(pawticipants.map(condition)).map(_.contains(twue)).wescue {
                  case n-notfound =>
                    stitch.exception(invawiddmconvewsationfeatuweexception("usew nyot found"))
                }
            case _ => stitch.fawse
          }
      c-case _ => stitch.exception(invawiddmconvewsationfeatuweexception("viewew i-id missing"))
    }

  def dmconvewsationhassuspendedpawticipant(
    d-dmconvewsationid: d-dmconvewsationid, >_<
    viewewidopt: option[usewid]
  ): stitch[boowean] =
    a-anyconvewsationpawticipantmatchescondition(
      pawticipant => authowfeatuwes.authowissuspended(pawticipant), ^^;;
      dmconvewsationid, (ˆ ﻌ ˆ)♡
      viewewidopt)

  d-def dmconvewsationhasdeactivatedpawticipant(
    dmconvewsationid: dmconvewsationid, ^^;;
    v-viewewidopt: o-option[usewid]
  ): stitch[boowean] =
    anyconvewsationpawticipantmatchescondition(
      pawticipant => a-authowfeatuwes.authowisdeactivated(pawticipant), (⑅˘꒳˘)
      d-dmconvewsationid, rawr x3
      viewewidopt)

  def dmconvewsationhasewasedpawticipant(
    dmconvewsationid: d-dmconvewsationid,
    viewewidopt: o-option[usewid]
  ): stitch[boowean] =
    anyconvewsationpawticipantmatchescondition(
      pawticipant => a-authowfeatuwes.authowisewased(pawticipant), (///ˬ///✿)
      dmconvewsationid, 🥺
      v-viewewidopt)

  d-def viewewisdmconvewsationpawticipant(
    dmconvewsationid: d-dmconvewsationid, >_<
    viewewidopt: o-option[usewid]
  ): s-stitch[boowean] =
    v-viewewidopt match {
      case some(viewewid) =>
        d-dmconvewsationsouwce
          .getconvewsationpawticipantids(dmconvewsationid, UwU v-viewewid).map {
            case some(pawticipants) => pawticipants.contains(viewewid)
            c-case _ => f-fawse
          }
      c-case _ => stitch.exception(invawiddmconvewsationfeatuweexception("viewew id missing"))
    }
}
