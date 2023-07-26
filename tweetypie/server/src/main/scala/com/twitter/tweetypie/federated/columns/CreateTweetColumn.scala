package com.twittew.tweetypie
package f-fedewated.cowumns

i-impowt com.twittew.accounts.utiw.safetymetadatautiws
i-impowt c-com.twittew.ads.cawwback.thwiftscawa.engagementwequest
i-impowt c-com.twittew.bouncew.thwiftscawa.{bounce => b-bouncewbounce}
i-impowt com.twittew.eschewbiwd.thwiftscawa.tweetentityannotation
impowt com.twittew.geo.modew.watitudewongitude
impowt c-com.twittew.stitch.stitch
impowt com.twittew.stwato.catawog.opmetadata
i-impowt com.twittew.stwato.config.awwof
i-impowt com.twittew.stwato.config.bouncewaccess
impowt com.twittew.stwato.config.contactinfo
impowt com.twittew.stwato.config.powicy
i-impowt com.twittew.stwato.data.conv
impowt com.twittew.stwato.data.descwiption.pwaintext
i-impowt c-com.twittew.stwato.data.wifecycwe.pwoduction
impowt com.twittew.stwato.fed.stwatofed
impowt com.twittew.stwato.opcontext.opcontext
impowt com.twittew.stwato.wesponse.eww
i-impowt com.twittew.stwato.thwift.scwoogeconv
impowt com.twittew.tweetypie.decidew.ovewwides.tweetypiedecidewovewwides
impowt com.twittew.tweetypie.fedewated.cowumns.apiewwows._
impowt c-com.twittew.tweetypie.fedewated.cowumns.cweatetweetcowumn.tocweatetweeteww
impowt com.twittew.tweetypie.fedewated.context.getwequestcontext
i-impowt com.twittew.tweetypie.fedewated.pwefetcheddata.pwefetcheddatawequest
i-impowt c-com.twittew.tweetypie.fedewated.pwefetcheddata.pwefetcheddatawesponse
i-impowt com.twittew.tweetypie.fedewated.pwomotedcontent.tweetpwomotedcontentwoggew
impowt c-com.twittew.tweetypie.fedewated.pwomotedcontent.tweetpwomotedcontentwoggew._
impowt com.twittew.tweetypie.wepositowy.unmentioninfowepositowy
impowt com.twittew.tweetypie.wepositowy.vibewepositowy
i-impowt com.twittew.tweetypie.thwiftscawa.twansientcweatecontext
impowt com.twittew.tweetypie.thwiftscawa.tweetcweatecontextkey
impowt com.twittew.tweetypie.thwiftscawa.tweetcweatestate._
impowt com.twittew.tweetypie.thwiftscawa.{gwaphqw => gqw}
impowt com.twittew.tweetypie.utiw.communityannotation
i-impowt com.twittew.tweetypie.utiw.convewsationcontwows
impowt c-com.twittew.tweetypie.utiw.twansientcontextutiw
i-impowt com.twittew.tweetypie.{thwiftscawa => t-thwift}
impowt com.twittew.utiw.thwowabwes
impowt com.twittew.weavewbiwd.common.{getwequestcontext => w-wgetwequestcontext}

c-cwass cweatetweetcowumn(
  posttweet: thwift.posttweetwequest => f-futuwe[thwift.posttweetwesuwt], nyaa~~
  g-getwequestcontext: getwequestcontext, ^^
  pwefetcheddatawepositowy: p-pwefetcheddatawequest => stitch[pwefetcheddatawesponse], (///ˬ///✿)
  u-unmentioninfowepositowy: unmentioninfowepositowy.type,
  vibewepositowy: v-vibewepositowy.type, 😳
  wogtweetpwomotedcontent: t-tweetpwomotedcontentwoggew.type, òωó
  statsweceivew: s-statsweceivew, ^^;;
  e-enabwecommunitytweetcweatesdecidew: gate[unit], rawr
) extends stwatofed.cowumn(cweatetweetcowumn.path)
    with stwatofed.exekawaii~.stitchwithcontext
    with stwatofed.handwedawkwequests {

  o-ovewwide vaw p-powicy: powicy = awwof(
    seq(accesspowicy.tweetmutationcommonaccesspowicies, (ˆ ﻌ ˆ)♡ b-bouncewaccess()))

  // t-the undewwying c-caww to thwifttweetsewvice.postwetweet is nyot idempotent
  ovewwide vaw i-isidempotent: boowean = fawse

  ovewwide type awg = gqw.cweatetweetwequest
  ovewwide t-type wesuwt = gqw.cweatetweetwesponsewithsubquewypwefetchitems

  o-ovewwide v-vaw awgconv: conv[awg] = s-scwoogeconv.fwomstwuct
  ovewwide vaw w-wesuwtconv: conv[wesuwt] = s-scwoogeconv.fwomstwuct

  o-ovewwide vaw c-contactinfo: contactinfo = tweetypiecontactinfo
  ovewwide vaw m-metadata: opmetadata =
    o-opmetadata(
      some(pwoduction), XD
      s-some(
        p-pwaintext(
          """
    c-cweates a tweet using the cawwing authenticated twittew usew as a-authow. >_< 
    nyote, (˘ω˘) nyot aww tweet space fiewds awe gwaphqw quewyabwe in the cweatetweet mutation w-wesponse. 
    see http://go/missing-cweate-tweet-fiewds.
    """))
    )

  pwivate vaw getweavewbiwdctx = nyew wgetwequestcontext()

  o-ovewwide d-def exekawaii~(wequest: a-awg, 😳 opcontext: opcontext): s-stitch[wesuwt] = {

    vaw ctx = getwequestcontext(opcontext)

    // f-fiwst, o.O do any wequest p-pawametew vawidation that can wesuwt in an ewwow
    // pwiow to cawwing into thwifttweetsewvice.posttweet. (ꈍᴗꈍ)
    v-vaw safetywevew = ctx.safetywevew.getowewse(thwow s-safetywevewmissingeww)

    vaw twackingid = w-wequest.engagementwequest m-match {
      case some(engagementwequest: engagementwequest) i-if c-ctx.haspwiviwegepwomotedtweetsintimewine =>
        twackingid.pawse(engagementwequest.impwessionid, s-statsweceivew)
      c-case some(e: engagementwequest) =>
        thwow cwientnotpwiviwegedeww
      case nyone =>
        nyone
    }

    vaw devicesouwce = c-ctx.devicesouwce.getowewse(thwow g-genewicaccessdeniedeww)

    i-if (wequest.nuwwcast && !ctx.haspwiviwegenuwwcastingaccess) {
      thwow genewicaccessdeniedeww
    }

    v-vaw s-safetymetadata = safetymetadatautiws.makesafetymetadata(
      s-sessionhash = ctx.sessionhash, rawr x3
      knowndevicetoken = ctx.knowndevicetoken, ^^
      contwibutowid = ctx.contwibutowid
    )

    v-vaw cawdwefewence: o-option[thwift.cawdwefewence] =
      wequest.cawduwi.fiwtew(_.nonempty).map(thwift.cawdwefewence(_))

    vaw e-eschewbiwdentityannotations: option[thwift.eschewbiwdentityannotations] =
      w-wequest.semanticannotationids
        .fiwtew(_.nonempty)
        .map((seq: seq[gqw.tweetannotation]) => seq.map(pawsetweetentityannotation))
        .map(thwift.eschewbiwdentityannotations(_))

    vaw mediaentities = wequest.media.map(_.mediaentities)
    v-vaw mediaupwoadids = mediaentities.map(_.map(_.mediaid)).fiwtew(_.nonempty)

    vaw mediatags: option[thwift.tweetmediatags] = {
      vaw m-mediatagsauthowized = !ctx.iscontwibutowwequest

      vaw tagmap: map[mediaid, OwO s-seq[thwift.mediatag]] =
        m-mediaentities
          .getowewse(niw)
          .fiwtew(_ => mediatagsauthowized)
          .fiwtew(_.taggedusews.nonempty)
          .map(mediaentity =>
            mediaentity.mediaid ->
              mediaentity.taggedusews
                .map(usew_id => t-thwift.mediatag(thwift.mediatagtype.usew, ^^ s-some(usew_id))))
          .tomap

      option(tagmap)
        .fiwtew(_.nonempty)
        .map(thwift.tweetmediatags(_))
    }

    // can nyot have both convewsation c-contwows and communities d-defined fow a tweet
    // as they have confwicting pewmissions o-on who can wepwy to the tweet. :3
    v-vaw communities = p-pawsecommunityids(eschewbiwdentityannotations)
    if (wequest.convewsationcontwow.isdefined && c-communities.nonempty) {
      thwow cannotconvocontwowandcommunitieseww
    }

    // c-cuwwentwy w-we do nyot s-suppowt posting to muwtipwe communities. o.O
    if (communities.wength > 1) {
      t-thwow toomanycommunitieseww
    }

    // k-kiww switch fow community tweets in c-case we nyeed to d-disabwe them fow a-app secuwity. -.-
    if (communities.nonempty && !enabwecommunitytweetcweatesdecidew()) {
      thwow communityusewnotauthowizedeww
    }

    // a-additionawfiewds is used to mawshaw m-muwtipwe input p-pawams and
    // shouwd onwy be defined if one ow mowe of t-those pawams awe d-defined. (U ﹏ U)
    vaw a-additionawfiewds: o-option[tweet] =
      cawdwefewence
        .owewse(eschewbiwdentityannotations)
        .owewse(mediatags)
        .map(_ =>
          t-thwift.tweet(
            0w, o.O
            cawdwefewence = cawdwefewence, OwO
            eschewbiwdentityannotations = eschewbiwdentityannotations, ^•ﻌ•^
            mediatags = mediatags
          ))

    v-vaw twansientcontext: option[twansientcweatecontext] =
      p-pawsetwansientcontext(
        wequest.batchcompose,
        w-wequest.pewiscope, ʘwʘ
        ctx.twittewusewid, :3
      )

    // p-posttweetwequest.additionawcontext is mawked a-as depwecated i-in favow of .twansientcontext, 😳
    // b-but the w-west api stiww s-suppowts it and it is stiww passed awong thwough tweetypie, òωó and
    // fanoutsewvice and nyotifications stiww depend o-on it. 🥺
    v-vaw additionawcontext: o-option[map[tweetcweatecontextkey, rawr x3 stwing]] =
      t-twansientcontext.map(twansientcontextutiw.toadditionawcontext)

    vaw thwiftposttweetwequest = thwift.posttweetwequest(
      u-usewid = c-ctx.twittewusewid, ^•ﻌ•^
      text = w-wequest.tweettext,
      cweatedvia = devicesouwce, :3
      i-inwepwytotweetid = w-wequest.wepwy.map(_.inwepwytotweetid), (ˆ ﻌ ˆ)♡
      geo = w-wequest.geo.fwatmap(pawsetweetcweategeo), (U ᵕ U❁)
      a-autopopuwatewepwymetadata = wequest.wepwy.isdefined, :3
      excwudewepwyusewids = wequest.wepwy.map(_.excwudewepwyusewids).fiwtew(_.nonempty), ^^;;
      nyuwwcast = wequest.nuwwcast, ( ͡o ω ͡o )
      // s-send a-a dawk wequest t-to tweetypie if t-the dawk_wequest d-diwective is set ow
      // i-if the tweet is u-undo-abwe. o.O
      dawk = ctx.isdawkwequest || w-wequest.undooptions.exists(_.isundo), ^•ﻌ•^
      h-hydwationoptions = some(hydwationoptions.wwitepathhydwationoptions(ctx.cawdspwatfowmkey)), XD
      w-wemotehost = ctx.wemotehost, ^^
      safetymetadata = s-some(safetymetadata), o.O
      attachmentuww = w-wequest.attachmentuww, ( ͡o ω ͡o )
      m-mediaupwoadids = mediaupwoadids, /(^•ω•^)
      m-mediametadata = nyone, 🥺
      twansientcontext = t-twansientcontext,
      a-additionawcontext = a-additionawcontext, nyaa~~
      convewsationcontwow = wequest.convewsationcontwow.map(pawsetweetcweateconvewsationcontwow), mya
      excwusivetweetcontwowoptions = w-wequest.excwusivetweetcontwowoptions.map { _ =>
        thwift.excwusivetweetcontwowoptions()
      }, XD
      twustedfwiendscontwowoptions =
        w-wequest.twustedfwiendscontwowoptions.map(pawsetwustedfwiendscontwowoptions), nyaa~~
      e-editoptions = wequest.editoptions.fwatmap(_.pwevioustweetid.map(thwift.editoptions(_))), ʘwʘ
      c-cowwabcontwowoptions = wequest.cowwabcontwowoptions.map(pawsecowwabcontwowoptions), (⑅˘꒳˘)
      a-additionawfiewds = a-additionawfiewds, :3
      twackingid = twackingid, -.-
      nyotetweetoptions = w-wequest.notetweetoptions.map(options =>
        thwift.notetweetoptions(
          options.notetweetid, 😳😳😳
          o-options.mentionedscweennames, (U ﹏ U)
          o-options.mentionedusewids, o.O
          options.isexpandabwe))
    )

    v-vaw stitchposttweet =
      stitch.cawwfutuwe {
        t-tweetypiedecidewovewwides.convewsationcontwowusefeatuweswitchwesuwts.on {
          p-posttweet(thwiftposttweetwequest)
        }
      }

    f-fow {
      engagement <- wequest.engagementwequest
      if !wequest.wepwy.exists(_.inwepwytotweetid == 0) // nyo op pew go/wb/845242
      engagementtype = if (wequest.wepwy.isdefined) wepwyengagement ewse tweetengagement
    } wogtweetpwomotedcontent(engagement, ( ͡o ω ͡o ) engagementtype, òωó ctx.isdawkwequest)

    s-stitchposttweet.fwatmap { w-wesuwt: thwift.posttweetwesuwt =>
      wesuwt.state match {

        c-case thwift.tweetcweatestate.ok =>
          v-vaw unmentionsuccesscountew = s-statsweceivew.countew("unmention_info_success")
          vaw u-unmentionfaiwuwescountew = statsweceivew.countew("unmention_info_faiwuwes")
          v-vaw unmentionfaiwuwesscope = s-statsweceivew.scope("unmention_info_faiwuwes")

          vaw unmentioninfostitch = w-wesuwt.tweet match {
            c-case some(tweet) =>
              u-unmentioninfowepositowy(tweet)
                .onfaiwuwe { t =>
                  unmentionfaiwuwescountew.incw()
                  unmentionfaiwuwesscope.countew(thwowabwes.mkstwing(t): _*).incw()
                }
                .onsuccess { _ =>
                  u-unmentionsuccesscountew.incw()
                }
                .wescue {
                  c-case _ =>
                    s-stitch.none
                }
            c-case _ =>
              s-stitch.none
          }

          v-vaw vibesuccesscountew = s-statsweceivew.countew("vibe_success")
          v-vaw vibefaiwuwescountew = s-statsweceivew.countew("vibe_faiwuwes")
          vaw v-vibefaiwuwesscope = s-statsweceivew.scope("vibe_faiwuwes")

          v-vaw vibestitch = wesuwt.tweet m-match {
            case some(tweet) =>
              vibewepositowy(tweet)
                .onsuccess { _ =>
                  v-vibesuccesscountew.incw()
                }
                .onfaiwuwe { t =>
                  v-vibefaiwuwescountew.incw()
                  v-vibefaiwuwesscope.countew(thwowabwes.mkstwing(t): _*).incw()
                }
                .wescue {
                  c-case _ =>
                    stitch.none
                }
            c-case _ =>
              stitch.none
          }

          s-stitch
            .join(unmentioninfostitch, 🥺 vibestitch)
            .wifttooption()
            .fwatmap { p-pwefetchfiewds =>
              vaw w = p-pwefetcheddatawequest(
                tweet = wesuwt.tweet.get, /(^•ω•^)
                souwcetweet = wesuwt.souwcetweet, 😳😳😳
                q-quotedtweet = wesuwt.quotedtweet, ^•ﻌ•^
                s-safetywevew = s-safetywevew, nyaa~~
                unmentioninfo = pwefetchfiewds.fwatmap(pawams => pawams._1), OwO
                v-vibe = pwefetchfiewds.fwatmap(pawams => pawams._2), ^•ﻌ•^
                w-wequestcontext = g-getweavewbiwdctx()
              )

              p-pwefetcheddatawepositowy(w)
                .wifttooption()
                .map((pwefetcheddata: option[pwefetcheddatawesponse]) => {
                  gqw.cweatetweetwesponsewithsubquewypwefetchitems(
                    d-data = some(gqw.cweatetweetwesponse(wesuwt.tweet.map(_.id))), σωσ
                    s-subquewypwefetchitems = pwefetcheddata.map(_.vawue)
                  )
                })
            }

        c-case ewwstate =>
          thwow tocweatetweeteww(ewwstate, -.- wesuwt.bounce, (˘ω˘) w-wesuwt.faiwuweweason)
      }
    }
  }

  pwivate[this] def p-pawsetweetcweategeo(gqwgeo: g-gqw.tweetgeo): o-option[thwift.tweetcweategeo] = {
    vaw coowdinates: o-option[thwift.geocoowdinates] =
      g-gqwgeo.coowdinates.map { c-coowds =>
        w-watitudewongitude.of(coowds.watitude, rawr x3 coowds.wongitude) m-match {
          case w-wetuwn(watwon: w-watitudewongitude) =>
            t-thwift.geocoowdinates(
              w-watitude = w-watwon.watitudedegwees, rawr x3
              w-wongitude = w-watwon.wongitudedegwees, σωσ
              geopwecision = w-watwon.pwecision, nyaa~~
              dispway = c-coowds.dispwaycoowdinates
            )
          case thwow(_) =>
            t-thwow invawidcoowdinateseww
        }
      }

    v-vaw geoseawchwequestid = g-gqwgeo.geoseawchwequestid.map { id =>
      if (id.isempty) {
        thwow invawidgeoseawchwequestideww
      }
      thwift.tweetgeoseawchwequestid(id)
    }

    i-if (coowdinates.isempty && g-gqwgeo.pwaceid.isempty) {
      n-nyone
    } ewse {
      some(
        thwift.tweetcweategeo(
          coowdinates = c-coowdinates, (ꈍᴗꈍ)
          pwaceid = g-gqwgeo.pwaceid, ^•ﻌ•^
          geoseawchwequestid = g-geoseawchwequestid
        ))
    }
  }

  p-pwivate[this] def pawsetweetcweateconvewsationcontwow(
    gqwcc: gqw.tweetconvewsationcontwow
  ): t-thwift.tweetcweateconvewsationcontwow =
    g-gqwcc.mode match {
      c-case g-gqw.convewsationcontwowmode.byinvitation =>
        convewsationcontwows.cweate.byinvitation()
      case gqw.convewsationcontwowmode.community =>
        c-convewsationcontwows.cweate.community()
      c-case gqw.convewsationcontwowmode.enumunknownconvewsationcontwowmode(_) =>
        thwow convewsationcontwownotsuppowtedeww
    }

  p-pwivate[this] def pawsetweetentityannotation(
    g-gqwtweetannotation: gqw.tweetannotation
  ): t-tweetentityannotation =
    t-tweetentityannotation(
      gqwtweetannotation.gwoupid,
      g-gqwtweetannotation.domainid, >_<
      g-gqwtweetannotation.entityid
    )

  pwivate[this] def p-pawsecommunityids(
    eschewbiwdannotations: o-option[thwift.eschewbiwdentityannotations]
  ): s-seq[wong] =
    e-eschewbiwdannotations
      .map(_.entityannotations).getowewse(niw)
      .fwatmap {
        c-case communityannotation(id) => s-seq(id)
        c-case _ => n-nyiw
      }

  pwivate[this] d-def pawsebatchmode(
    gqwbatchcomposemode: gqw.batchcomposemode
  ): t-thwift.batchcomposemode = {

    g-gqwbatchcomposemode m-match {
      case gqw.batchcomposemode.batchfiwst =>
        thwift.batchcomposemode.batchfiwst
      case gqw.batchcomposemode.batchsubsequent =>
        thwift.batchcomposemode.batchsubsequent
      c-case gqw.batchcomposemode.enumunknownbatchcomposemode(_) =>
        t-thwow invawidbatchmodepawameteweww
    }
  }

  p-pwivate[this] def pawsetwansientcontext(
    gqwbatchcomposemode: o-option[gqw.batchcomposemode], ^^;;
    gqwpewiscope: o-option[gqw.tweetpewiscopecontext], ^^;;
    t-twittewusewid: u-usewid, /(^•ω•^)
  ): o-option[twansientcweatecontext] = {
    v-vaw batchcomposemode = gqwbatchcomposemode.map(pawsebatchmode)

    // pew c.t.fanoutsewvice.modew.tweet#devicefowwowtype, nyaa~~ iswive=none a-and some(fawse) awe
    // equivawent a-and the cweatowid is discawded in both cases. (✿oωo)
    vaw pewiscopeiswive = g-gqwpewiscope.map(_.iswive).fiwtew(_ == twue)
    vaw pewiscopecweatowid = if (pewiscopeiswive.isdefined) some(twittewusewid) e-ewse n-nyone

    if (batchcomposemode.isdefined || pewiscopeiswive.isdefined) {
      s-some(
        thwift.twansientcweatecontext(
          batchcompose = b-batchcomposemode, ( ͡o ω ͡o )
          p-pewiscopeiswive = pewiscopeiswive, (U ᵕ U❁)
          p-pewiscopecweatowid = pewiscopecweatowid
        )
      )
    } e-ewse {
      nyone
    }
  }

  pwivate[this] def pawsetwustedfwiendscontwowoptions(
    gqwtwustedfwiendscontwowoptions: g-gqw.twustedfwiendscontwowoptions
  ): thwift.twustedfwiendscontwowoptions = {
    thwift.twustedfwiendscontwowoptions(
      t-twustedfwiendswistid = g-gqwtwustedfwiendscontwowoptions.twustedfwiendswistid
    )
  }

  p-pwivate[this] def pawsecowwabcontwowoptions(
    gqwcowwabcontwowoptions: g-gqw.cowwabcontwowoptions
  ): thwift.cowwabcontwowoptions = {
    gqwcowwabcontwowoptions.cowwabcontwowtype match {
      case gqw.cowwabcontwowtype.cowwabinvitation =>
        t-thwift.cowwabcontwowoptions.cowwabinvitation(
          t-thwift.cowwabinvitationoptions(
            c-cowwabowatowusewids = g-gqwcowwabcontwowoptions.cowwabowatowusewids
          )
        )
      case gqw.cowwabcontwowtype.enumunknowncowwabcontwowtype(_) =>
        thwow cowwabtweetinvawidpawamseww
    }
  }
}

o-object cweatetweetcowumn {
  v-vaw path = "tweetypie/cweatetweet.tweet"

  def tocweatetweeteww(
    e-ewwstate: thwift.tweetcweatestate, òωó
    bounce: o-option[bouncewbounce], σωσ
    faiwuweweason: option[stwing]
  ): e-eww = ewwstate m-match {
    case textcannotbebwank =>
      tweetcannotbebwankeww
    c-case texttoowong =>
      t-tweettexttoowongeww
    c-case dupwicate =>
      dupwicatestatuseww
    c-case mawwaweuww =>
      mawwawetweeteww
    case usewdeactivated | u-usewsuspended =>
      // shouwd nyot occuw since this condition is c-caught by access p-powicy fiwtews
      c-cuwwentusewsuspendedeww
    c-case watewimitexceeded =>
      w-watewimitexceededeww
    case u-uwwspam =>
      tweetuwwspameww
    case spam | u-usewweadonwy =>
      tweetspammeweww
    c-case spamcaptcha =>
      captchachawwengeeww
    case s-safetywatewimitexceeded =>
      s-safetywatewimitexceededeww
    case bounce i-if bounce.isdefined =>
      accessdeniedbybounceweww(bounce.get)
    c-case mentionwimitexceeded =>
      m-mentionwimitexceededeww
    case uwwwimitexceeded =>
      u-uwwwimitexceededeww
    c-case hashtagwimitexceeded =>
      hashtagwimitexceededeww
    c-case cashtagwimitexceeded =>
      cashtagwimitexceededeww
    case hashtagwengthwimitexceeded =>
      h-hashtagwengthwimitexceededeww
    case toomanyattachmenttypes =>
      t-toomanyattachmenttypeseww
    case invawiduww =>
      invawiduwweww
    c-case disabwedbyipipowicy =>
      f-faiwuweweason
        .map(tweetengagementwimitedeww)
        .getowewse(genewictweetcweateeww)
    c-case invawidadditionawfiewd =>
      faiwuweweason
        .map(invawidadditionawfiewdwithweasoneww)
        .getowewse(invawidadditionawfiewdeww)
    // i-invawidimage h-has been depwecated by tweetypie. :3 u-use invawidmedia instead. OwO
    c-case invawidmedia | invawidimage | m-medianotfound =>
      i-invawidmediaeww(faiwuweweason)
    case inwepwytotweetnotfound =>
      inwepwytotweetnotfoundeww
    case invawidattachmentuww =>
      i-invawidattachmentuwweww
    case c-convewsationcontwownotawwowed =>
      convewsationcontwownotauthowizedeww
    case invawidconvewsationcontwow =>
      convewsationcontwowinvawideww
    c-case wepwytweetnotawwowed =>
      c-convewsationcontwowwepwywestwicted
    c-case excwusivetweetengagementnotawwowed =>
      excwusivetweetengagementnotawwowedeww
    case communitywepwytweetnotawwowed =>
      communitywepwytweetnotawwowedeww
    case communityusewnotauthowized =>
      communityusewnotauthowizedeww
    case c-communitynotfound =>
      communitynotfoundeww
    case supewfowwowsinvawidpawams =>
      supewfowwowinvawidpawamseww
    c-case supewfowwowscweatenotauthowized =>
      supewfowwowcweatenotauthowizedeww
    c-case communitypwotectedusewcannottweet =>
      c-communitypwotectedusewcannottweeteww
    case t-twustedfwiendsinvawidpawams =>
      t-twustedfwiendsinvawidpawamseww
    c-case twustedfwiendsengagementnotawwowed =>
      t-twustedfwiendsengagementnotawwowedeww
    c-case twustedfwiendscweatenotawwowed =>
      t-twustedfwiendscweatenotawwowedeww
    case twustedfwiendsquotetweetnotawwowed =>
      twustedfwiendsquotetweetnotawwowedeww
    case cowwabtweetinvawidpawams =>
      cowwabtweetinvawidpawamseww
    case stawetweetengagementnotawwowed =>
      s-stawetweetengagementnotawwowedeww
    c-case s-stawetweetquotetweetnotawwowed =>
      s-stawetweetquotetweetnotawwowedeww
    c-case fiewdeditnotawwowed =>
      f-fiewdeditnotawwowedeww
    case notewigibwefowedit =>
      nyotewigibwefowediteww
    case _ =>
      g-genewictweetcweateeww
  }
}
