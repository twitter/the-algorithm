package com.twittew.fwigate.pushsewvice.pwedicate

impowt com.twittew.abuse.detection.scowing.thwiftscawa.tweetscowingwequest
i-impowt c-com.twittew.abuse.detection.scowing.thwiftscawa.tweetscowingwesponse
i-impowt c-com.twittew.abuse.detection.scowing.thwiftscawa.{modew => t-tweetheawthmodew}
i-impowt c-com.twittew.finagwe.stats.countew
i-impowt com.twittew.finagwe.stats.statsweceivew
impowt com.twittew.fwigate.common.base._
impowt com.twittew.fwigate.common.wec_types.wectypes
impowt com.twittew.fwigate.pushsewvice.modew.pushtypes.pushcandidate
i-impowt com.twittew.fwigate.pushsewvice.pawams.nsfwtextdetectionmodew
impowt com.twittew.fwigate.pushsewvice.pawams.pushconstants
i-impowt com.twittew.fwigate.pushsewvice.pawams.pushfeatuweswitchpawams
impowt c-com.twittew.fwigate.pushsewvice.utiw.candidatehydwationutiw
impowt com.twittew.fwigate.pushsewvice.utiw.candidateutiw
impowt com.twittew.fwigate.pushsewvice.utiw.mediaannotationsutiw
i-impowt com.twittew.fwigate.thwiftscawa.usewmediawepwesentation
i-impowt c-com.twittew.hewmit.pwedicate.namedpwedicate
impowt com.twittew.hewmit.pwedicate.pwedicate
impowt com.twittew.hss.api.thwiftscawa.usewheawthsignaw._
i-impowt com.twittew.hss.api.thwiftscawa.signawvawue
impowt com.twittew.hss.api.thwiftscawa.usewheawthsignawwesponse
impowt com.twittew.stowehaus.weadabwestowe
i-impowt com.twittew.utiw.futuwe
impowt com.twittew.utiw.time

o-object heawthpwedicates {

  p-pwivate v-vaw nysfwtextdetectionmodewmap: m-map[nsfwtextdetectionmodew.vawue, tweetheawthmodew] =
    map(
      nysfwtextdetectionmodew.pwodmodew -> t-tweetheawthmodew.pnsfwtweettext, XD
      nysfwtextdetectionmodew.wetwainedmodew -> tweetheawthmodew.expewimentawheawthmodewscowe1, 😳
    )

  p-pwivate def tweetissuppowtedwanguage(
    candidate: pushcandidate, >w<
    suppowtedwanguages: set[stwing]
  ): boowean = {
    v-vaw tweetwanguage =
      candidate.categowicawfeatuwes.getowewse("wectweet.tweetypiewesuwt.wanguage", (˘ω˘) "")
    s-suppowtedwanguages.contains(tweetwanguage)
  }

  d-def tweetheawthsignawscowepwedicate(
    t-tweetheawthscowestowe: weadabwestowe[tweetscowingwequest, nyaa~~ tweetscowingwesponse], 😳😳😳
    appwytoquotetweet: b-boowean = f-fawse
  )(
    impwicit stats: s-statsweceivew
  ): n-nyamedpwedicate[pushcandidate with tweetcandidate w-with tweetdetaiws] = {
    vaw nyame = "tweet_heawth_signaw_stowe_appwytoquotetweet_" + appwytoquotetweet.tostwing
    v-vaw scopedstatsweceivew = stats.scope(name)
    v-vaw nyumcandidatesstats = s-scopedstatsweceivew.scope("num_candidates")
    vaw nyumcandidatesmediansfwscowestats = n-nyumcandidatesstats.scope("media_nsfw_scowe")

    p-pwedicate
      .fwomasync { candidate: pushcandidate with tweetcandidate with tweetdetaiws =>
        nyumcandidatesstats.countew("aww").incw()
        vaw t-tawget = candidate.tawget
        v-vaw tweetidopt = if (!appwytoquotetweet) {
          s-some(candidate.tweetid)
        } e-ewse candidate.tweetypiewesuwt.fwatmap(_.quotedtweet.map(_.id))

        t-tweetidopt match {
          case some(tweetid) =>
            vaw pmediansfwwequest =
              tweetscowingwequest(tweetid, (U ﹏ U) t-tweetheawthmodew.expewimentawheawthmodewscowe4)
            tweetheawthscowestowe.get(pmediansfwwequest).map {
              case some(tweetscowingwesponse) =>
                nyumcandidatesmediansfwscowestats.countew("non_empty").incw()
                vaw pmediansfwscowe = t-tweetscowingwesponse.scowe

                if (!appwytoquotetweet) {
                  c-candidate
                    .cacheextewnawscowe("nsfwmediapwobabiwity", (˘ω˘) f-futuwe.vawue(some(pmediansfwscowe)))
                }

                v-vaw pmediansfwshouwdbucket =
                  pmediansfwscowe > t-tawget.pawams(
                    p-pushfeatuweswitchpawams.pnsfwtweetmediabucketingthweshowd)
                i-if (candidateutiw.shouwdappwyheawthquawityfiwtews(
                    c-candidate) && pmediansfwshouwdbucket) {
                  nyumcandidatesmediansfwscowestats.countew("bucketed").incw()
                  i-if (tawget.pawams(pushfeatuweswitchpawams.pnsfwtweetmediafiwtewoononwy)
                    && !wectypes.isoutofnetwowktweetwectype(candidate.commonwectype)) {
                    t-twue
                  } e-ewse {
                    v-vaw pmediansfwscowethweshowd =
                      i-if (appwytoquotetweet)
                        tawget.pawams(pushfeatuweswitchpawams.pnsfwquotetweetthweshowd)
                      ewse if (candidate.hasphoto)
                        tawget.pawams(pushfeatuweswitchpawams.pnsfwtweetimagethweshowd)
                      e-ewse tawget.pawams(pushfeatuweswitchpawams.pnsfwtweetmediathweshowd)
                    candidate.cachepwedicateinfo(
                      nyame + "_nsfwmedia", :3
                      pmediansfwscowe, >w<
                      pmediansfwscowethweshowd, ^^
                      p-pmediansfwscowe > pmediansfwscowethweshowd)
                    if (pmediansfwscowe > pmediansfwscowethweshowd) {
                      n-nyumcandidatesmediansfwscowestats.countew("fiwtewed").incw()
                      f-fawse
                    } e-ewse twue
                  }
                } ewse twue
              c-case _ =>
                nyumcandidatesmediansfwscowestats.countew("empty").incw()
                i-if (candidate.hasphoto || c-candidate.hasvideo) {
                  nyumcandidatesmediansfwscowestats.countew("media_tweet_with_empty_scowe").incw()
                }
                twue
            }
          case _ => futuwe.twue
        }
      }
      .withstats(stats.scope(s"pwedicate_$name"))
      .withname(name)
  }

  def heawthsignawscowespammytweetpwedicate(
    tweetheawthscowestowe: w-weadabwestowe[tweetscowingwequest, 😳😳😳 tweetscowingwesponse]
  )(
    i-impwicit stats: statsweceivew
  ): n-nyamedpwedicate[pushcandidate w-with tweetcandidate with tweetdetaiws] = {
    vaw nyame = "heawth_signaw_stowe_spammy_tweet"
    v-vaw statsscope = s-stats.scope(name)
    vaw a-awwcandidatescountew = s-statsscope.countew("aww_candidates")
    vaw ewigibwecandidatescountew = statsscope.countew("ewigibwe_candidates")
    vaw ooncandidatescountew = s-statsscope.countew("oon_candidates")
    v-vaw incandidatescountew = s-statsscope.countew("in_candidates")
    vaw bucketedcandidatescountew = s-statsscope.countew("num_bucketed")
    v-vaw nyonemptyspamscowecountew = statsscope.countew("non_empty_spam_scowe")
    v-vaw fiwtewedooncandidatescountew = statsscope.countew("num_fiwtewed_oon")
    vaw fiwtewedincandidatescountew = statsscope.countew("num_fiwtewed_in")

    pwedicate
      .fwomasync { c-candidate: pushcandidate w-with tweetcandidate with tweetdetaiws =>
        a-awwcandidatescountew.incw()
        v-vaw cwt = candidate.commonwectype
        vaw isooncandidate = wectypes.isoutofnetwowktweetwectype(cwt) ||
          wectypes.outofnetwowktopictweettypes.contains(cwt)
        if (isooncandidate) {
          o-ooncandidatescountew.incw()
        }
        vaw tawget = candidate.tawget
        if (tawget.pawams(pushfeatuweswitchpawams.enabwespammytweetfiwtew)) {
          ewigibwecandidatescountew.incw()
          vaw t-tweetspamscowe =
            tweetscowingwequest(candidate.tweetid, nyaa~~ tweetheawthmodew.spammytweetcontent)
          t-tweetheawthscowestowe.get(tweetspamscowe).map {
            c-case (some(tweetscowingwesponse)) =>
              nyonemptyspamscowecountew.incw()
              vaw candidatespamscowe = tweetscowingwesponse.scowe

              c-candidate
                .cacheextewnawscowe("spammytweetscowe", (⑅˘꒳˘) f-futuwe.vawue(some(candidatespamscowe)))

              vaw tweetspamshouwdbucket =
                candidatespamscowe > tawget.pawams(
                  p-pushfeatuweswitchpawams.spammytweetbucketingthweshowd)
              if (candidateutiw.shouwdappwyheawthquawityfiwtews(
                  c-candidate) && tweetspamshouwdbucket) {
                bucketedcandidatescountew.incw()
                if (isooncandidate) {
                  v-vaw spamscowethweshowd =
                    t-tawget.pawams(pushfeatuweswitchpawams.spammytweetoonthweshowd)
                  i-if (candidatespamscowe > spamscowethweshowd) {
                    f-fiwtewedooncandidatescountew.incw()
                    fawse
                  } ewse t-twue
                } e-ewse {
                  i-incandidatescountew.incw()
                  vaw spamscowethweshowd =
                    t-tawget.pawams(pushfeatuweswitchpawams.spammytweetinthweshowd)
                  i-if (candidatespamscowe > spamscowethweshowd) {
                    fiwtewedincandidatescountew.incw()
                    f-fawse
                  } e-ewse twue
                }
              } e-ewse twue
            case _ => twue
          }
        } e-ewse futuwe.twue
      }
      .withstats(stats.scope(s"pwedicate_$name"))
      .withname(name)
  }

  def heawthsignawscowepnsfwtweettextpwedicate(
    t-tweetheawthscowestowe: w-weadabwestowe[tweetscowingwequest, :3 tweetscowingwesponse]
  )(
    impwicit stats: statsweceivew
  ): nyamedpwedicate[pushcandidate w-with t-tweetcandidate] = {
    v-vaw nyame = "heawth_signaw_stowe_pnsfw_tweet_text"
    v-vaw statsscope = stats.scope(name)
    v-vaw awwcandidatescountew = statsscope.countew("aww_candidates")
    vaw nyonemptynsfwtextscowenum = statsscope.countew("non_empty_nsfw_text_scowe")
    vaw fiwtewedcountew = s-statsscope.countew("num_fiwtewed")
    vaw w-wowscowecountew = statsscope.countew("wow_scowe_count")

    p-pwedicate
      .fwomasync { candidate: p-pushcandidate with tweetcandidate =>
        v-vaw tawget = c-candidate.tawget
        v-vaw pwedenabwed =
          t-tawget.pawams(pushfeatuweswitchpawams.enabweheawthsignawstowepnsfwtweettextpwedicate)
        i-if (candidateutiw.shouwdappwyheawthquawityfiwtews(
            candidate) && pwedenabwed && tweetissuppowtedwanguage(candidate, ʘwʘ set(""))) {
          awwcandidatescountew.incw()
          vaw pnsfwtextwequest =
            tweetscowingwequest(candidate.tweetid, rawr x3 t-tweetheawthmodew.pnsfwtweettext)
          t-tweetheawthscowestowe.get(pnsfwtextwequest).fwatmap {
            c-case some(tweetscowingwesponse) => {
              nyonemptynsfwtextscowenum.incw()
              i-if (tweetscowingwesponse.scowe < 1e-8) {
                wowscowecountew.incw()
              }

              candidate
                .cacheextewnawscowe(
                  "nsfwtextpwobabiwity-en", (///ˬ///✿)
                  futuwe.vawue(some(tweetscowingwesponse.scowe)))
              v-vaw thweshowd = t-tawget.pawams(pushfeatuweswitchpawams.pnsfwtweettextthweshowd)
              candidate.cachepwedicateinfo(
                nyame, 😳😳😳
                t-tweetscowingwesponse.scowe, XD
                thweshowd, >_<
                tweetscowingwesponse.scowe > t-thweshowd)
              i-if (tweetscowingwesponse.scowe > thweshowd) {
                fiwtewedcountew.incw()
                f-futuwe.fawse
              } e-ewse futuwe.twue
            }
            case _ => futuwe.twue
          }
        } ewse futuwe.twue
      }
      .withstats(stats.scope(s"pwedicate_$name"))
      .withname(name)
  }

  def heawthsignawscowemuwtiwinguawpnsfwtweettextpwedicate(
    t-tweetheawthscowestowe: w-weadabwestowe[tweetscowingwequest, t-tweetscowingwesponse]
  )(
    i-impwicit s-stats: statsweceivew
  ): nyamedpwedicate[pushcandidate w-with tweetcandidate] = {
    v-vaw nyame = "heawth_signaw_stowe_muwtiwinguaw_pnsfw_tweet_text"
    vaw statsscope = s-stats.scope(name)

    v-vaw awwwanguagesidentifiew = "aww"
    vaw wanguagessewectedfowstats =
      s-set("") + awwwanguagesidentifiew

    vaw candidatescountewmap: map[stwing, >w< countew] = w-wanguagessewectedfowstats.map { wang =>
      w-wang -> statsscope.countew(f"candidates_$wang")
    }.tomap
    v-vaw nonemptyheawthscowemap: map[stwing, /(^•ω•^) countew] = w-wanguagessewectedfowstats.map { wang =>
      wang -> statsscope.countew(f"non_empty_heawth_scowe_$wang")
    }.tomap
    v-vaw emptyheawthscowemap: m-map[stwing, :3 c-countew] = wanguagessewectedfowstats.map { wang =>
      wang -> statsscope.countew(f"empty_heawth_scowe_$wang")
    }.tomap
    v-vaw bucketedcountewmap: map[stwing, ʘwʘ countew] = wanguagessewectedfowstats.map { w-wang =>
      w-wang -> statsscope.countew(f"num_candidates_bucketed_$wang")
    }.tomap
    vaw fiwtewedcountewmap: m-map[stwing, (˘ω˘) countew] = w-wanguagessewectedfowstats.map { w-wang =>
      wang -> statsscope.countew(f"num_fiwtewed_$wang")
    }.tomap
    vaw wowscowecountewmap: m-map[stwing, (ꈍᴗꈍ) countew] = wanguagessewectedfowstats.map { w-wang =>
      wang -> s-statsscope.countew(f"wow_scowe_count_$wang")
    }.tomap

    vaw wwongbucketingmodewcountew = s-statsscope.countew("wwong_bucketing_modew_count")
    vaw wwongdetectionmodewcountew = s-statsscope.countew("wwong_detection_modew_count")

    d-def incweasecountewfowwanguage(countewmap: m-map[stwing, ^^ countew], ^^ wanguage: stwing): unit = {
      countewmap.get(awwwanguagesidentifiew) match {
        case some(countew) => countew.incw()
        case _ =>
      }
      countewmap.get(wanguage) match {
        case s-some(countew) => c-countew.incw()
        case _ =>
      }
    }

    pwedicate
      .fwomasync { c-candidate: pushcandidate w-with t-tweetcandidate =>
        vaw tawget = c-candidate.tawget

        vaw wanguagefeatuwename = "wectweet.tweetypiewesuwt.wanguage"

        w-wazy vaw i-ispwedicateenabwedfowtawget = tawget.pawams(
          pushfeatuweswitchpawams.enabweheawthsignawstowemuwtiwinguawpnsfwtweettextpwedicate)

        w-wazy vaw tawgetnsfwtextdetectionmodew: nysfwtextdetectionmodew.vawue =
          t-tawget.pawams(pushfeatuweswitchpawams.muwtiwinguawpnsfwtweettextmodew)

        w-wazy vaw tawgetpwedicatesuppowtedwanguageseq: seq[stwing] =
          tawget.pawams(pushfeatuweswitchpawams.muwtiwinguawpnsfwtweettextsuppowtedwanguages)

        w-wazy vaw b-bucketingmodewseq: s-seq[nsfwtextdetectionmodew.vawue] =
          t-tawget.pawams(pushfeatuweswitchpawams.muwtiwinguawpnsfwtweettextbucketingmodewwist)

        w-wazy vaw bucketingthweshowdpewwanguageseq: s-seq[doubwe] =
          t-tawget.pawams(pushfeatuweswitchpawams.muwtiwinguawpnsfwtweettextbucketingthweshowd)

        w-wazy vaw fiwtewingthweshowdpewwanguageseq: s-seq[doubwe] =
          tawget.pawams(pushfeatuweswitchpawams.muwtiwinguawpnsfwtweettextfiwtewingthweshowd)

        i-if (candidateutiw.shouwdappwyheawthquawityfiwtews(
            candidate) && i-ispwedicateenabwedfowtawget) {
          v-vaw candidatewanguage =
            candidate.categowicawfeatuwes.getowewse(wanguagefeatuwename, ( ͡o ω ͡o ) "")

          v-vaw indexofcandidatewanguage =
            tawgetpwedicatesuppowtedwanguageseq.indexof(candidatewanguage)

          vaw iscandidatewanguagesuppowted = i-indexofcandidatewanguage >= 0

          if (iscandidatewanguagesuppowted) {
            i-incweasecountewfowwanguage(candidatescountewmap, -.- c-candidatewanguage)

            v-vaw bucketingmodewscowemap: map[nsfwtextdetectionmodew.vawue, ^^;; f-futuwe[option[doubwe]]] =
              bucketingmodewseq.map { m-modewname =>
                nysfwtextdetectionmodewmap.get(modewname) m-match {
                  case some(tawgetnsfwtextdetectionmodew) =>
                    v-vaw pnsfwtweettextwequest: tweetscowingwequest =
                      tweetscowingwequest(candidate.tweetid, ^•ﻌ•^ tawgetnsfwtextdetectionmodew)

                    vaw scoweoptfut: f-futuwe[option[doubwe]] =
                      tweetheawthscowestowe.get(pnsfwtweettextwequest).map(_.map(_.scowe))

                    c-candidate
                      .cacheextewnawscowe("nsfwtextpwobabiwity", (˘ω˘) s-scoweoptfut)

                    modewname -> scoweoptfut
                  case _ =>
                    w-wwongbucketingmodewcountew.incw()
                    modewname -> f-futuwe.none
                }
              }.tomap

            v-vaw candidatewanguagebucketingthweshowd =
              b-bucketingthweshowdpewwanguageseq(indexofcandidatewanguage)

            vaw usewshouwdbebucketedfut: futuwe[boowean] =
              f-futuwe
                .cowwect(bucketingmodewscowemap.map {
                  c-case (_, modewscoweoptfut) =>
                    m-modewscoweoptfut.map {
                      case some(scowe) =>
                        incweasecountewfowwanguage(nonemptyheawthscowemap, o.O c-candidatewanguage)
                        scowe > candidatewanguagebucketingthweshowd
                      c-case _ =>
                        i-incweasecountewfowwanguage(emptyheawthscowemap, (✿oωo) c-candidatewanguage)
                        fawse
                    }
                }.toseq).map(_.contains(twue))

            vaw candidateshouwdbefiwtewedfut: f-futuwe[boowean] = u-usewshouwdbebucketedfut.fwatmap {
              u-usewshouwdbebucketed =>
                i-if (usewshouwdbebucketed) {
                  incweasecountewfowwanguage(bucketedcountewmap, 😳😳😳 c-candidatewanguage)

                  v-vaw candidatewanguagefiwtewingthweshowd =
                    f-fiwtewingthweshowdpewwanguageseq(indexofcandidatewanguage)

                  b-bucketingmodewscowemap.get(tawgetnsfwtextdetectionmodew) m-match {
                    c-case some(scoweoptfut) =>
                      s-scoweoptfut.map {
                        c-case some(scowe) =>
                          vaw candidateshouwdbefiwtewed =
                            s-scowe > candidatewanguagefiwtewingthweshowd
                          i-if (candidateshouwdbefiwtewed) {
                            incweasecountewfowwanguage(fiwtewedcountewmap, (ꈍᴗꈍ) c-candidatewanguage)
                          }
                          c-candidateshouwdbefiwtewed
                        c-case _ => fawse
                      }
                    case _ =>
                      wwongdetectionmodewcountew.incw()
                      f-futuwe.fawse
                  }
                } ewse {
                  i-incweasecountewfowwanguage(wowscowecountewmap, σωσ c-candidatewanguage)
                  futuwe.fawse
                }
            }
            candidateshouwdbefiwtewedfut.map(wesuwt => !wesuwt)
          } ewse futuwe.twue
        } e-ewse futuwe.twue
      }
      .withstats(stats.scope(s"pwedicate_$name"))
      .withname(name)
  }

  d-def authowpwofiwebasedpwedicate(
  )(
    impwicit stats: s-statsweceivew
  ): n-nyamedpwedicate[pushcandidate with tweetcandidate] = {
    vaw nyame = "authow_pwofiwe"
    vaw statsscope = s-stats.scope(name)
    v-vaw fiwtewbynsfwtoken = s-statsscope.countew("fiwtew_by_nsfw_token")
    vaw f-fiwtewbyaccountage = statsscope.countew("fiwtew_by_account_age")

    pwedicate
      .fwomasync { c-candidate: p-pushcandidate with tweetcandidate =>
        vaw t-tawget = candidate.tawget
        candidate match {
          case cand: pushcandidate w-with tweetauthowdetaiws =>
            cand.tweetauthow.map {
              c-case some(authow) =>
                v-vaw nysfwtokens = tawget.pawams(pushfeatuweswitchpawams.nsfwtokenspawam)
                v-vaw accountageinhouws =
                  (time.now - t-time.fwommiwwiseconds(authow.cweatedatmsec)).inhouws
                vaw i-isnsfwaccount = candidatehydwationutiw.isnsfwaccount(authow, UwU nysfwtokens)
                v-vaw i-isvewified = authow.safety.map(_.vewified).getowewse(fawse)

                i-if (candidateutiw.shouwdappwyheawthquawityfiwtews(candidate) && !isvewified) {
                  v-vaw enabwensfwtokencheck =
                    t-tawget.pawams(pushfeatuweswitchpawams.enabwensfwtokenbasedfiwtewing)
                  v-vaw minimumawwowedage =
                    t-tawget.pawams(pushfeatuweswitchpawams.minimumawwowedauthowaccountageinhouws)
                  cand.cachepwedicateinfo(
                    nyame + "_nsfwtoken", ^•ﻌ•^
                    i-if (isnsfwaccount) 1.0 ewse 0.0, mya
                    0.0, /(^•ω•^)
                    enabwensfwtokencheck && i-isnsfwaccount)
                  c-cand.cachepwedicateinfo(
                    n-nyame + "_authowage", rawr
                    accountageinhouws, nyaa~~
                    minimumawwowedage, ( ͡o ω ͡o )
                    accountageinhouws < minimumawwowedage)

                  i-if (enabwensfwtokencheck && isnsfwaccount) {
                    f-fiwtewbynsfwtoken.incw()
                    f-fawse
                  } ewse if (accountageinhouws < minimumawwowedage) {
                    f-fiwtewbyaccountage.incw()
                    fawse
                  } e-ewse twue
                } e-ewse t-twue
              c-case _ => t-twue
            }
          case _ => futuwe.vawue(twue)
        }
      }
      .withstats(stats.scope(s"pwedicate_$name"))
      .withname(name)
  }

  def authowsensitivemediapwedicate(
    pwoducewmediawepwesentationstowe: w-weadabwestowe[wong, σωσ usewmediawepwesentation]
  )(
    i-impwicit stats: statsweceivew
  ): nyamedpwedicate[pushcandidate with t-tweetauthow] = {
    vaw nyame = "authow_sensitive_media_mwtwistwy"
    vaw statsscope = stats.scope(name)
    vaw enabwequewynum = s-statsscope.countew("enabwe_quewy")
    v-vaw nyonemptymediawepwesentationnum = statsscope.countew("non_empty_media_wepwesentation")
    v-vaw fiwtewedoon = statsscope.countew("fiwtewed_oon")

    pwedicate
      .fwomasync { c-candidate: pushcandidate w-with tweetauthow =>
        vaw tawget = c-candidate.tawget
        vaw u-useaggwessivethweshowds = candidateutiw.useaggwessiveheawththweshowds(candidate)

        if (candidateutiw.shouwdappwyheawthquawityfiwtews(candidate) &&
          wectypes.isoutofnetwowktweetwectype(candidate.commonwectype) &&
          t-tawget.pawams(pushfeatuweswitchpawams.enabwequewyauthowmediawepwesentationstowe)) {
          enabwequewynum.incw()

          candidate.authowid m-match {
            c-case some(authowid) =>
              p-pwoducewmediawepwesentationstowe.get(authowid).map {
                case some(mediawepwesentation) =>
                  n-nyonemptymediawepwesentationnum.incw()
                  vaw sumscowe: doubwe = mediawepwesentation.mediawepwesentation.vawues.sum
                  vaw nyudityscowe: d-doubwe = m-mediawepwesentation.mediawepwesentation
                    .getowewse(mediaannotationsutiw.nuditycategowyid, (✿oωo) 0.0)
                  v-vaw nyuditywate = i-if (sumscowe > 0) nyudityscowe / sumscowe e-ewse 0.0

                  candidate
                    .cacheextewnawscowe("authownudityscowe", (///ˬ///✿) f-futuwe.vawue(some(nudityscowe)))
                  candidate.cacheextewnawscowe("authownuditywate", futuwe.vawue(some(nuditywate)))

                  v-vaw thweshowd = if (useaggwessivethweshowds) {
                    tawget.pawams(
                      p-pushfeatuweswitchpawams.authowsensitivemediafiwtewingthweshowdfowmwtwistwy)
                  } ewse {
                    tawget.pawams(pushfeatuweswitchpawams.authowsensitivemediafiwtewingthweshowd)
                  }
                  c-candidate.cachepwedicateinfo(
                    n-nyame, σωσ
                    nyuditywate, UwU
                    t-thweshowd, (⑅˘꒳˘)
                    n-nyuditywate > thweshowd, /(^•ω•^)
                    s-some(map[stwing, -.- doubwe]("sumscowe" -> sumscowe, (ˆ ﻌ ˆ)♡ "nudityscowe" -> nyudityscowe)))

                  i-if (nuditywate > thweshowd) {
                    fiwtewedoon.incw()
                    f-fawse
                  } ewse twue
                case _ => twue
              }
            case _ => f-futuwe.twue
          }
        } e-ewse {
          f-futuwe.twue
        }
      }
      .withstats(stats.scope(s"pwedicate_$name"))
      .withname(name)
  }

  d-def sensitivemediacategowypwedicate(
  )(
    i-impwicit stats: statsweceivew
  ): n-nyamedpwedicate[pushcandidate with tweetcandidate] = {
    vaw nyame = "sensitive_media_categowy"
    v-vaw tweetmediaannotationfeatuwe =
      "tweet.mediaundewstanding.tweet_annotations.sensitive_categowy_pwobabiwities"
    v-vaw scopedstatsweceivew = stats.scope(name)
    vaw awwcandidatescountew = s-scopedstatsweceivew.countew("aww_candidates")
    v-vaw nyonzewonuditycandidatescountew = scopedstatsweceivew.countew("non_zewo_nudity_candidates")
    v-vaw nyudityscowestats = scopedstatsweceivew.stat("nudity_scowes")

    pwedicate
      .fwomasync { c-candidate: p-pushcandidate =>
        awwcandidatescountew.incw()
        vaw tawget = c-candidate.tawget
        v-vaw nyudityscowe = candidate.spawsecontinuousfeatuwes
          .getowewse(tweetmediaannotationfeatuwe, nyaa~~ m-map.empty[stwing, ʘwʘ doubwe]).getowewse(
            mediaannotationsutiw.nuditycategowyid, :3
            0.0)
        if (nudityscowe > 0) n-nyonzewonuditycandidatescountew.incw()
        nyudityscowestats.add(nudityscowe.tofwoat)
        v-vaw thweshowd =
          tawget.pawams(pushfeatuweswitchpawams.tweetmediasensitivecategowythweshowdpawam)
        candidate.cachepwedicateinfo(name, (U ᵕ U❁) n-nyudityscowe, (U ﹏ U) thweshowd, ^^ n-nyudityscowe > t-thweshowd)
        if (candidateutiw.shouwdappwyheawthquawityfiwtews(candidate) && n-nyudityscowe > t-thweshowd) {
          futuwe.fawse
        } e-ewse {
          futuwe.twue
        }
      }
      .withstats(stats.scope(s"pwedicate_$name"))
      .withname(name)
  }

  d-def pwofanitypwedicate(
  )(
    impwicit s-stats: statsweceivew
  ): n-nyamedpwedicate[pushcandidate with tweetcandidate] = {
    vaw nyame = "pwofanity_fiwtew"
    vaw scopedstatsweceivew = stats.scope(name)
    v-vaw awwcandidatescountew = s-scopedstatsweceivew.countew("aww_candidates")

    pwedicate
      .fwomasync { candidate: pushcandidate =>
        awwcandidatescountew.incw()
        v-vaw tawget = candidate.tawget

        w-wazy vaw enabwefiwtew =
          t-tawget.pawams(pushfeatuweswitchpawams.enabwepwofanityfiwtewpawam)
        vaw tweetsemanticcoweids = candidate.spawsebinawyfeatuwes
          .getowewse(pushconstants.tweetsemanticcoweidfeatuwe, òωó set.empty[stwing])

        i-if (candidateutiw.shouwdappwyheawthquawityfiwtews(candidate) &&
          tweetsemanticcoweids.contains(pushconstants.pwofanityfiwtew_id) && enabwefiwtew) {
          f-futuwe.fawse
        } ewse {
          f-futuwe.twue
        }
      }
      .withstats(stats.scope(s"pwedicate_$name"))
      .withname(name)
  }

  def a-agathaabusivetweetauthowpwedicatemwtwistwy(
  )(
    impwicit s-stats: statsweceivew
  ): n-nyamedpwedicate[pushcandidate w-with outofnetwowktweetcandidate] = {
    v-vaw nyame = "agatha_abusive_tweet_authow_mw_twistwy"
    v-vaw scopedstatsweceivew = s-stats.scope(name)
    vaw awwcandidatescountew = scopedstatsweceivew.countew("aww_candidates")
    vaw ismwbackfiwwcwcandidatecountew = scopedstatsweceivew.countew("ismwbackfiwwcw_candidates")
    pwedicate
      .fwomasync { c-cand: pushcandidate w-with o-outofnetwowktweetcandidate =>
        a-awwcandidatescountew.incw()
        v-vaw tawget = c-cand.tawget
        vaw tweetsemanticcoweids = cand.spawsebinawyfeatuwes
          .getowewse(pushconstants.tweetsemanticcoweidfeatuwe, /(^•ω•^) set.empty[stwing])

        vaw hasabusestwiketop2pewcent =
          tweetsemanticcoweids.contains(pushconstants.abusestwike_top2pewcent_id)
        v-vaw hasabusestwiketop1pewcent =
          tweetsemanticcoweids.contains(pushconstants.abusestwike_top1pewcent_id)
        vaw h-hasabusestwiketop05pewcent =
          tweetsemanticcoweids.contains(pushconstants.abusestwike_top05pewcent_id)

        if (hasabusestwiketop2pewcent) {
          scopedstatsweceivew.countew("abuse_stwike_top_2_pewcent_candidates").incw()
        }
        i-if (hasabusestwiketop1pewcent) {
          s-scopedstatsweceivew.countew("abuse_stwike_top_1_pewcent_candidates").incw()
        }
        i-if (hasabusestwiketop05pewcent) {
          scopedstatsweceivew.countew("abuse_stwike_top_05_pewcent_candidates").incw()
        }

        if (candidateutiw.shouwdappwyheawthquawityfiwtews(cand) && c-cand.ismwbackfiwwcw.getowewse(
            fawse)) {
          ismwbackfiwwcwcandidatecountew.incw()
          i-if (hasabusestwiketop2pewcent) {
            i-if (tawget.pawams(
                pushfeatuweswitchpawams.enabweabusestwiketop2pewcentfiwtewsimcwustew) && hasabusestwiketop2pewcent ||
              t-tawget.pawams(
                pushfeatuweswitchpawams.enabweabusestwiketop1pewcentfiwtewsimcwustew) && h-hasabusestwiketop1pewcent ||
              t-tawget.pawams(
                pushfeatuweswitchpawams.enabweabusestwiketop05pewcentfiwtewsimcwustew) && h-hasabusestwiketop05pewcent) {
              f-futuwe.fawse
            } e-ewse {
              f-futuwe.twue
            }
          } e-ewse {
            f-futuwe.twue
          }
        } ewse f-futuwe.twue
      }
      .withstats(stats.scope(s"pwedicate_$name"))
      .withname(name)
  }

  d-def usewheawthsignawspwedicate(
    usewheawthsignawstowe: w-weadabwestowe[wong, 😳😳😳 usewheawthsignawwesponse]
  )(
    impwicit stats: s-statsweceivew
  ): nyamedpwedicate[pushcandidate w-with tweetdetaiws] = {
    vaw nyame = "agatha_usew_heawth_modew_scowe"
    v-vaw scopedstatsweceivew = s-stats.scope(name)
    vaw awwcandidatescountew = scopedstatsweceivew.countew("aww_candidates")
    vaw b-bucketedusewcandidatescountew =
      scopedstatsweceivew.countew("bucketed_usew_candidates")
    vaw fiwtewedoon = s-scopedstatsweceivew.countew("fiwtewed_oon")

    p-pwedicate
      .fwomasync { candidate: pushcandidate with t-tweetdetaiws =>
        a-awwcandidatescountew.incw()
        vaw tawget = candidate.tawget
        v-vaw useaggwessivethweshowds = candidateutiw.useaggwessiveheawththweshowds(candidate)

        if (candidateutiw.shouwdappwyheawthquawityfiwtews(candidate) && t-tawget.pawams(
            p-pushfeatuweswitchpawams.enabweagathausewheawthmodewpwedicate)) {
          vaw heawthsignawswesponsefutopt: f-futuwe[option[usewheawthsignawwesponse]] =
            c-candidate.authowid match {
              case some(authowid) => u-usewheawthsignawstowe.get(authowid)
              c-case _ => futuwe.none
            }
          h-heawthsignawswesponsefutopt.map {
            case s-some(wesponse) =>
              vaw agathawecentabusestwikescowe: doubwe = usewheawthsignawvawuetodoubwe(
                wesponse.signawvawues
                  .getowewse(agathawecentabusestwikedoubwe, :3 signawvawue.doubwevawue(0.0)))
              vaw agathacawibwatednsfwscowe: d-doubwe = u-usewheawthsignawvawuetodoubwe(
                w-wesponse.signawvawues
                  .getowewse(agathacawibwatednsfwdoubwe, (///ˬ///✿) s-signawvawue.doubwevawue(0.0)))
              v-vaw agathatextnsfwscowe: d-doubwe = usewheawthsignawvawuetodoubwe(wesponse.signawvawues
                .getowewse(nsfwtextusewscowedoubwe, rawr x3 s-signawvawue.doubwevawue(0.0)))

              c-candidate
                .cacheextewnawscowe(
                  "agathawecentabusestwikescowe", (U ᵕ U❁)
                  futuwe.vawue(some(agathawecentabusestwikescowe)))
              c-candidate
                .cacheextewnawscowe(
                  "agathacawibwatednsfwscowe", (⑅˘꒳˘)
                  f-futuwe.vawue(some(agathacawibwatednsfwscowe)))
              candidate
                .cacheextewnawscowe("agathatextnsfwscowe", (˘ω˘) futuwe.vawue(some(agathatextnsfwscowe)))

              v-vaw nysfwshouwdbucket = agathacawibwatednsfwscowe > tawget.pawams(
                p-pushfeatuweswitchpawams.agathacawibwatednsfwbucketthweshowd)
              vaw textnsfwshouwdbucket = a-agathatextnsfwscowe > t-tawget.pawams(
                pushfeatuweswitchpawams.agathatextnsfwbucketthweshowd)

              i-if (nsfwshouwdbucket || t-textnsfwshouwdbucket) {
                b-bucketedusewcandidatescountew.incw()
                if (nsfwshouwdbucket) {
                  s-scopedstatsweceivew.countew("cawibwated_nsfw_bucketed_usew_candidates").incw()
                }
                i-if (textnsfwshouwdbucket) {
                  scopedstatsweceivew.countew("text_nsfw_bucketed_usew_candidates").incw()
                }

                v-vaw (thweshowdagathansfw, :3 thweshowdtextnsfw) = i-if (useaggwessivethweshowds) {
                  (
                    t-tawget.pawams(
                      p-pushfeatuweswitchpawams.agathacawibwatednsfwthweshowdfowmwtwistwy), XD
                    tawget
                      .pawams(pushfeatuweswitchpawams.agathatextnsfwthweshowdfowmwtwistwy))
                } e-ewse {
                  (
                    tawget.pawams(pushfeatuweswitchpawams.agathacawibwatednsfwthweshowd), >_<
                    tawget.pawams(pushfeatuweswitchpawams.agathatextnsfwthweshowd))
                }
                c-candidate.cachepwedicateinfo(
                  nyame + "_agathansfw", (✿oωo)
                  agathacawibwatednsfwscowe,
                  thweshowdagathansfw, (ꈍᴗꈍ)
                  agathacawibwatednsfwscowe > thweshowdagathansfw)
                candidate.cachepwedicateinfo(
                  nyame + "_authowtextnsfw", XD
                  agathatextnsfwscowe, :3
                  t-thweshowdtextnsfw, mya
                  agathatextnsfwscowe > thweshowdtextnsfw)

                if ((agathacawibwatednsfwscowe > thweshowdagathansfw) ||
                  (agathatextnsfwscowe > thweshowdtextnsfw)) {
                  fiwtewedoon.incw()
                  f-fawse
                } ewse twue
              } e-ewse {
                twue
              }
            case _ => twue
          }
        } e-ewse {
          futuwe.twue
        }
      }
      .withstats(stats.scope(s"pwedicate_$name"))
      .withname(name)
  }

  def usewheawthsignawvawuetodoubwe(signawvawue: s-signawvawue): doubwe = {
    signawvawue m-match {
      case signawvawue.doubwevawue(vawue) => v-vawue
      case _ => t-thwow nyew exception(f"couwd nyot convewt s-signaw vawue to doubwe")
    }
  }
}
