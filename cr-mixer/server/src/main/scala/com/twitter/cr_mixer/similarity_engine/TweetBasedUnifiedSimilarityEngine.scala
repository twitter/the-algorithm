package com.twittew.cw_mixew.simiwawity_engine

impowt com.twittew.cw_mixew.modew.candidategenewationinfo
i-impowt c-com.twittew.cw_mixew.modew.moduwenames
i-impowt com.twittew.cw_mixew.modew.simiwawityengineinfo
i-impowt c-com.twittew.cw_mixew.modew.souwceinfo
i-impowt c-com.twittew.cw_mixew.modew.tweetwithcandidategenewationinfo
i-impowt com.twittew.cw_mixew.modew.tweetwithscowe
impowt com.twittew.cw_mixew.pawam.gwobawpawams
impowt com.twittew.cw_mixew.pawam.wewatedtweettweetbasedpawams
i-impowt com.twittew.cw_mixew.pawam.wewatedvideotweettweetbasedpawams
impowt com.twittew.cw_mixew.pawam.simcwustewsannpawams
i-impowt com.twittew.cw_mixew.pawam.tweetbasedcandidategenewationpawams
impowt c-com.twittew.cw_mixew.pawam.tweetbasedtwhinpawams
impowt com.twittew.cw_mixew.thwiftscawa.simiwawityenginetype
impowt com.twittew.cw_mixew.thwiftscawa.souwcetype
impowt com.twittew.cw_mixew.utiw.intewweaveutiw
i-impowt com.twittew.finagwe.stats.statsweceivew
impowt com.twittew.fwigate.common.utiw.statsutiw
i-impowt com.twittew.simcwustews_v2.common.modewvewsions
i-impowt com.twittew.simcwustews_v2.thwiftscawa.embeddingtype
impowt com.twittew.simcwustews_v2.thwiftscawa.intewnawid
impowt com.twittew.snowfwake.id.snowfwakeid
i-impowt com.twittew.stowehaus.weadabwestowe
impowt com.twittew.timewines.configapi
impowt com.twittew.utiw.duwation
i-impowt com.twittew.utiw.futuwe
impowt com.twittew.utiw.time
i-impowt j-javax.inject.named
i-impowt javax.inject.singweton
i-impowt scawa.cowwection.mutabwe.awwaybuffew

/**
 * this stowe fetches simiwaw t-tweets fwom muwtipwe tweet based candidate souwces
 * a-and combines them using diffewent methods obtained fwom quewy pawams
 */
@singweton
case c-cwass tweetbasedunifiedsimiwawityengine(
  @named(moduwenames.tweetbasedusewtweetgwaphsimiwawityengine)
  tweetbasedusewtweetgwaphsimiwawityengine: s-standawdsimiwawityengine[
    t-tweetbasedusewtweetgwaphsimiwawityengine.quewy, rawr
    t-tweetwithscowe
  ], (˘ω˘)
  @named(moduwenames.tweetbasedusewvideogwaphsimiwawityengine)
  tweetbasedusewvideogwaphsimiwawityengine: standawdsimiwawityengine[
    tweetbasedusewvideogwaphsimiwawityengine.quewy, (ˆ ﻌ ˆ)♡
    t-tweetwithscowe
  ], mya
  s-simcwustewsannsimiwawityengine: standawdsimiwawityengine[
    s-simcwustewsannsimiwawityengine.quewy, (U ᵕ U❁)
    t-tweetwithscowe
  ], mya
  @named(moduwenames.tweetbasedqigsimiwawityengine)
  tweetbasedqigsimiwawtweetssimiwawityengine: s-standawdsimiwawityengine[
    tweetbasedqigsimiwawityengine.quewy, ʘwʘ
    t-tweetwithscowe
  ], (˘ω˘)
  @named(moduwenames.tweetbasedtwhinannsimiwawityengine)
  tweetbasedtwhinannsimiwawityengine: hnswannsimiwawityengine, 😳
  s-statsweceivew: statsweceivew)
    e-extends weadabwestowe[
      tweetbasedunifiedsimiwawityengine.quewy, òωó
      s-seq[tweetwithcandidategenewationinfo]
    ] {

  i-impowt tweetbasedunifiedsimiwawityengine._
  pwivate vaw stats = statsweceivew.scope(this.getcwass.getsimpwename)
  pwivate vaw fetchcandidatesstat = stats.scope("fetchcandidates")

  ovewwide d-def get(
    q-quewy: quewy
  ): futuwe[option[seq[tweetwithcandidategenewationinfo]]] = {

    q-quewy.souwceinfo.intewnawid m-match {
      c-case _: intewnawid.tweetid =>
        statsutiw.twackoptionitemsstats(fetchcandidatesstat) {
          vaw twhinquewy =
            hnswannenginequewy(
              s-souwceid = quewy.souwceinfo.intewnawid, nyaa~~
              modewid = quewy.twhinmodewid, o.O
              pawams = quewy.pawams)
          vaw utgcandidatesfut =
            i-if (quewy.enabweutg)
              tweetbasedusewtweetgwaphsimiwawityengine.getcandidates(quewy.utgquewy)
            e-ewse f-futuwe.none

          v-vaw uvgcandidatesfut =
            if (quewy.enabweuvg)
              tweetbasedusewvideogwaphsimiwawityengine.getcandidates(quewy.uvgquewy)
            e-ewse futuwe.none

          v-vaw s-sanncandidatesfut = i-if (quewy.enabwesimcwustewsann) {
            simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsannquewy)
          } ewse futuwe.none

          v-vaw sann1candidatesfut =
            i-if (quewy.enabwesimcwustewsann1) {
              s-simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsann1quewy)
            } e-ewse futuwe.none

          v-vaw sann2candidatesfut =
            if (quewy.enabwesimcwustewsann2) {
              simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsann2quewy)
            } e-ewse futuwe.none

          vaw sann3candidatesfut =
            if (quewy.enabwesimcwustewsann3) {
              simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsann3quewy)
            } ewse futuwe.none

          vaw sann5candidatesfut =
            i-if (quewy.enabwesimcwustewsann5) {
              simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsann5quewy)
            } ewse futuwe.none

          v-vaw sann4candidatesfut =
            i-if (quewy.enabwesimcwustewsann4) {
              s-simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsann4quewy)
            } ewse futuwe.none

          v-vaw expewimentawsanncandidatesfut =
            if (quewy.enabweexpewimentawsimcwustewsann) {
              s-simcwustewsannsimiwawityengine.getcandidates(quewy.expewimentawsimcwustewsannquewy)
            } ewse f-futuwe.none

          vaw qigcandidatesfut =
            if (quewy.enabweqig)
              tweetbasedqigsimiwawtweetssimiwawityengine.getcandidates(quewy.qigquewy)
            ewse futuwe.none

          vaw twhincandidatefut = i-if (quewy.enabwetwhin) {
            tweetbasedtwhinannsimiwawityengine.getcandidates(twhinquewy)
          } ewse futuwe.none

          f-futuwe
            .join(
              utgcandidatesfut,
              s-sanncandidatesfut, nyaa~~
              s-sann1candidatesfut, (U ᵕ U❁)
              sann2candidatesfut, 😳😳😳
              sann3candidatesfut, (U ﹏ U)
              sann5candidatesfut, ^•ﻌ•^
              s-sann4candidatesfut, (⑅˘꒳˘)
              e-expewimentawsanncandidatesfut,
              qigcandidatesfut,
              t-twhincandidatefut, >_<
              u-uvgcandidatesfut
            ).map {
              case (
                    usewtweetgwaphcandidates, (⑅˘꒳˘)
                    simcwustewsanncandidates, σωσ
                    simcwustewsann1candidates, 🥺
                    simcwustewsann2candidates, :3
                    s-simcwustewsann3candidates, (ꈍᴗꈍ)
                    s-simcwustewsann5candidates, ^•ﻌ•^
                    s-simcwustewsann4candidates, (˘ω˘)
                    expewimentawsanncandidates, 🥺
                    q-qigsimiwawtweetscandidates, (✿oωo)
                    t-twhincandidates, XD
                    usewvideogwaphcandidates) =>
                vaw f-fiwtewedutgtweets =
                  usewtweetgwaphfiwtew(usewtweetgwaphcandidates.toseq.fwatten)
                vaw fiwteweduvgtweets =
                  usewvideogwaphfiwtew(usewvideogwaphcandidates.toseq.fwatten)
                vaw fiwtewedsanntweets = s-simcwustewscandidateminscowefiwtew(
                  s-simcwustewsanncandidates.toseq.fwatten, (///ˬ///✿)
                  quewy.simcwustewsminscowe, ( ͡o ω ͡o )
                  quewy.simcwustewsannquewy.stowequewy.simcwustewsannconfigid)

                v-vaw f-fiwtewedsann1tweets = simcwustewscandidateminscowefiwtew(
                  simcwustewsann1candidates.toseq.fwatten, ʘwʘ
                  quewy.simcwustewsminscowe, rawr
                  q-quewy.simcwustewsann1quewy.stowequewy.simcwustewsannconfigid)

                vaw fiwtewedsann2tweets = simcwustewscandidateminscowefiwtew(
                  simcwustewsann2candidates.toseq.fwatten, o.O
                  quewy.simcwustewsminscowe, ^•ﻌ•^
                  quewy.simcwustewsann2quewy.stowequewy.simcwustewsannconfigid)

                v-vaw fiwtewedsann3tweets = simcwustewscandidateminscowefiwtew(
                  s-simcwustewsann3candidates.toseq.fwatten, (///ˬ///✿)
                  q-quewy.simcwustewsminscowe, (ˆ ﻌ ˆ)♡
                  quewy.simcwustewsann3quewy.stowequewy.simcwustewsannconfigid)

                vaw fiwtewedsann4tweets = simcwustewscandidateminscowefiwtew(
                  s-simcwustewsann4candidates.toseq.fwatten, XD
                  q-quewy.simcwustewsminscowe, (✿oωo)
                  quewy.simcwustewsann4quewy.stowequewy.simcwustewsannconfigid)

                vaw fiwtewedsann5tweets = simcwustewscandidateminscowefiwtew(
                  s-simcwustewsann5candidates.toseq.fwatten, -.-
                  quewy.simcwustewsminscowe,
                  q-quewy.simcwustewsann5quewy.stowequewy.simcwustewsannconfigid)

                vaw fiwtewedexpewimentawsanntweets = simcwustewscandidateminscowefiwtew(
                  expewimentawsanncandidates.toseq.fwatten, XD
                  q-quewy.simcwustewsvideobasedminscowe, (✿oωo)
                  quewy.expewimentawsimcwustewsannquewy.stowequewy.simcwustewsannconfigid)

                vaw fiwtewedqigtweets = q-qigsimiwawtweetsfiwtew(
                  q-qigsimiwawtweetscandidates.toseq.fwatten, (˘ω˘)
                  quewy.qigmaxtweetagehouws, (ˆ ﻌ ˆ)♡
                  q-quewy.qigmaxnumsimiwawtweets
                )

                vaw fiwtewedtwhintweets = t-twhinfiwtew(
                  t-twhincandidates.toseq.fwatten.sowtby(-_.scowe), >_<
                  quewy.twhinmaxtweetagehouws, -.-
                  t-tweetbasedtwhinannsimiwawityengine.getscopedstats
                )
                vaw utgtweetswithcginfo = f-fiwtewedutgtweets.map { t-tweetwithscowe =>
                  vaw simiwawityengineinfo = tweetbasedusewtweetgwaphsimiwawityengine
                    .tosimiwawityengineinfo(tweetwithscowe.scowe)
                  t-tweetwithcandidategenewationinfo(
                    t-tweetwithscowe.tweetid, (///ˬ///✿)
                    c-candidategenewationinfo(
                      some(quewy.souwceinfo), XD
                      simiwawityengineinfo, ^^;;
                      seq(simiwawityengineinfo)
                    ))
                }

                vaw u-uvgtweetswithcginfo = fiwteweduvgtweets.map { t-tweetwithscowe =>
                  v-vaw simiwawityengineinfo = tweetbasedusewvideogwaphsimiwawityengine
                    .tosimiwawityengineinfo(tweetwithscowe.scowe)
                  tweetwithcandidategenewationinfo(
                    tweetwithscowe.tweetid, rawr x3
                    candidategenewationinfo(
                      s-some(quewy.souwceinfo), OwO
                      s-simiwawityengineinfo, ʘwʘ
                      s-seq(simiwawityengineinfo)
                    ))
                }
                v-vaw sanntweetswithcginfo = fiwtewedsanntweets.map { tweetwithscowe =>
                  v-vaw simiwawityengineinfo = simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsannquewy, rawr tweetwithscowe.scowe)
                  tweetwithcandidategenewationinfo(
                    tweetwithscowe.tweetid, UwU
                    candidategenewationinfo(
                      s-some(quewy.souwceinfo), (ꈍᴗꈍ)
                      simiwawityengineinfo, (✿oωo)
                      s-seq(simiwawityengineinfo)
                    ))
                }
                vaw sann1tweetswithcginfo = f-fiwtewedsann1tweets.map { tweetwithscowe =>
                  v-vaw simiwawityengineinfo = simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsann1quewy, (⑅˘꒳˘) tweetwithscowe.scowe)
                  t-tweetwithcandidategenewationinfo(
                    t-tweetwithscowe.tweetid, OwO
                    c-candidategenewationinfo(
                      some(quewy.souwceinfo), 🥺
                      s-simiwawityengineinfo, >_<
                      s-seq(simiwawityengineinfo)
                    ))
                }
                vaw sann2tweetswithcginfo = fiwtewedsann2tweets.map { tweetwithscowe =>
                  vaw simiwawityengineinfo = simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsann2quewy, (ꈍᴗꈍ) t-tweetwithscowe.scowe)
                  t-tweetwithcandidategenewationinfo(
                    t-tweetwithscowe.tweetid,
                    candidategenewationinfo(
                      s-some(quewy.souwceinfo), 😳
                      simiwawityengineinfo, 🥺
                      seq(simiwawityengineinfo)
                    ))
                }
                vaw sann3tweetswithcginfo = f-fiwtewedsann3tweets.map { t-tweetwithscowe =>
                  vaw simiwawityengineinfo = s-simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsann3quewy, nyaa~~ tweetwithscowe.scowe)
                  tweetwithcandidategenewationinfo(
                    t-tweetwithscowe.tweetid, ^•ﻌ•^
                    c-candidategenewationinfo(
                      some(quewy.souwceinfo), (ˆ ﻌ ˆ)♡
                      s-simiwawityengineinfo, (U ᵕ U❁)
                      s-seq(simiwawityengineinfo)
                    ))
                }
                vaw sann4tweetswithcginfo = fiwtewedsann4tweets.map { tweetwithscowe =>
                  vaw simiwawityengineinfo = s-simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsann4quewy, t-tweetwithscowe.scowe)
                  t-tweetwithcandidategenewationinfo(
                    t-tweetwithscowe.tweetid, mya
                    c-candidategenewationinfo(
                      some(quewy.souwceinfo), 😳
                      s-simiwawityengineinfo, σωσ
                      seq(simiwawityengineinfo)
                    ))
                }
                v-vaw sann5tweetswithcginfo = fiwtewedsann5tweets.map { t-tweetwithscowe =>
                  v-vaw simiwawityengineinfo = s-simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsann5quewy, ( ͡o ω ͡o ) tweetwithscowe.scowe)
                  tweetwithcandidategenewationinfo(
                    t-tweetwithscowe.tweetid, XD
                    candidategenewationinfo(
                      s-some(quewy.souwceinfo), :3
                      s-simiwawityengineinfo, :3
                      seq(simiwawityengineinfo)
                    ))
                }

                v-vaw expewimentawsanntweetswithcginfo = fiwtewedexpewimentawsanntweets.map {
                  tweetwithscowe =>
                    v-vaw simiwawityengineinfo = s-simcwustewsannsimiwawityengine
                      .tosimiwawityengineinfo(
                        q-quewy.expewimentawsimcwustewsannquewy, (⑅˘꒳˘)
                        tweetwithscowe.scowe)
                    tweetwithcandidategenewationinfo(
                      tweetwithscowe.tweetid, òωó
                      c-candidategenewationinfo(
                        some(quewy.souwceinfo), mya
                        simiwawityengineinfo, 😳😳😳
                        s-seq(simiwawityengineinfo)
                      ))
                }
                v-vaw qigtweetswithcginfo = f-fiwtewedqigtweets.map { tweetwithscowe =>
                  vaw simiwawityengineinfo = t-tweetbasedqigsimiwawityengine
                    .tosimiwawityengineinfo(tweetwithscowe.scowe)
                  t-tweetwithcandidategenewationinfo(
                    tweetwithscowe.tweetid, :3
                    candidategenewationinfo(
                      s-some(quewy.souwceinfo), >_<
                      simiwawityengineinfo, 🥺
                      seq(simiwawityengineinfo)
                    ))
                }

                vaw twhintweetswithcginfo = f-fiwtewedtwhintweets.map { t-tweetwithscowe =>
                  vaw simiwawityengineinfo = t-tweetbasedtwhinannsimiwawityengine
                    .tosimiwawityengineinfo(twhinquewy, (ꈍᴗꈍ) tweetwithscowe.scowe)
                  tweetwithcandidategenewationinfo(
                    t-tweetwithscowe.tweetid, rawr x3
                    c-candidategenewationinfo(
                      s-some(quewy.souwceinfo), (U ﹏ U)
                      simiwawityengineinfo, ( ͡o ω ͡o )
                      seq(simiwawityengineinfo)
                    ))
                }

                vaw candidatesouwcestobeintewweaved =
                  awwaybuffew[seq[tweetwithcandidategenewationinfo]](
                    sanntweetswithcginfo, 😳😳😳
                    expewimentawsanntweetswithcginfo,
                    sann1tweetswithcginfo, 🥺
                    sann2tweetswithcginfo, òωó
                    sann3tweetswithcginfo, XD
                    sann5tweetswithcginfo, XD
                    sann4tweetswithcginfo, ( ͡o ω ͡o )
                    qigtweetswithcginfo,
                    u-uvgtweetswithcginfo, >w<
                    utgtweetswithcginfo, mya
                    t-twhintweetswithcginfo
                  )

                vaw intewweavedcandidates =
                  intewweaveutiw.intewweave(candidatesouwcestobeintewweaved)

                v-vaw u-unifiedcandidateswithunifiedcginfo =
                  i-intewweavedcandidates.map { candidate =>
                    /***
                     * w-when a candidate was made by intewweave/keepgivenowdew, (ꈍᴗꈍ)
                     * t-then we appwy gettweetbasedunifiedcginfo() t-to ovewwide with the u-unified cginfo
                     *
                     * we'ww n-nyot have aww s-ses that genewated the tweet
                     * in contwibutingse w-wist fow i-intewweave. -.- we onwy h-have the chosen s-se avaiwabwe. (⑅˘꒳˘)
                     */
                    t-tweetwithcandidategenewationinfo(
                      t-tweetid = c-candidate.tweetid, (U ﹏ U)
                      c-candidategenewationinfo = g-gettweetbasedunifiedcginfo(
                        candidate.candidategenewationinfo.souwceinfoopt, σωσ
                        c-candidate.getsimiwawityscowe, :3
                        c-candidate.candidategenewationinfo.contwibutingsimiwawityengines
                      ) // g-getsimiwawityscowe comes fwom eithew u-unifiedscowe ow singwe scowe
                    )
                  }
                stats
                  .stat("unified_candidate_size").add(unifiedcandidateswithunifiedcginfo.size)

                v-vaw twuncatedcandidates =
                  unifiedcandidateswithunifiedcginfo.take(quewy.maxcandidatenumpewsouwcekey)
                stats.stat("twuncatedcandidates_size").add(twuncatedcandidates.size)

                s-some(twuncatedcandidates)
            }
        }

      c-case _ =>
        s-stats.countew("souwceid_is_not_tweetid_cnt").incw()
        futuwe.none
    }
  }

  p-pwivate def simcwustewscandidateminscowefiwtew(
    simcwustewsanncandidates: s-seq[tweetwithscowe], /(^•ω•^)
    simcwustewsminscowe: d-doubwe,
    simcwustewsannconfigid: s-stwing
  ): seq[tweetwithscowe] = {
    vaw fiwtewedcandidates = simcwustewsanncandidates
      .fiwtew { candidate =>
        candidate.scowe > simcwustewsminscowe
      }

    s-stats.stat(simcwustewsannconfigid, σωσ "simcwustewsanncandidates_size").add(fiwtewedcandidates.size)
    stats.countew(simcwustewsannconfigid, (U ᵕ U❁) "simcwustewsannwequests").incw()
    i-if (fiwtewedcandidates.isempty)
      s-stats.countew(simcwustewsannconfigid, 😳 "emptyfiwtewedsimcwustewsanncandidates").incw()

    fiwtewedcandidates.map { candidate =>
      tweetwithscowe(candidate.tweetid, ʘwʘ candidate.scowe)
    }
  }

  /** w-wetuwns a wist of tweets that a-awe genewated wess t-than `maxtweetagehouws` h-houws ago */
  pwivate def tweetagefiwtew(
    c-candidates: s-seq[tweetwithscowe], (⑅˘꒳˘)
    maxtweetagehouws: d-duwation
  ): seq[tweetwithscowe] = {
    // tweet ids awe appwoximatewy c-chwonowogicaw (see http://go/snowfwake), ^•ﻌ•^
    // s-so we a-awe buiwding the e-eawwiest tweet id once
    // t-the pew-candidate w-wogic hewe then b-be candidate.tweetid > e-eawwiestpewmittedtweetid, nyaa~~ which is faw c-cheapew. XD
    vaw e-eawwiesttweetid = s-snowfwakeid.fiwstidfow(time.now - m-maxtweetagehouws)
    c-candidates.fiwtew { candidate => c-candidate.tweetid >= e-eawwiesttweetid }
  }

  p-pwivate def twhinfiwtew(
    t-twhincandidates: seq[tweetwithscowe], /(^•ω•^)
    t-twhinmaxtweetagehouws: duwation, (U ᵕ U❁)
    s-simenginestats: s-statsweceivew
  ): s-seq[tweetwithscowe] = {
    simenginestats.stat("twhincandidates_size").add(twhincandidates.size)
    vaw candidates = twhincandidates.map { c-candidate =>
      t-tweetwithscowe(candidate.tweetid, mya c-candidate.scowe)
    }

    vaw fiwtewedcandidates = tweetagefiwtew(candidates, (ˆ ﻌ ˆ)♡ twhinmaxtweetagehouws)
    s-simenginestats.stat("fiwtewedtwhincandidates_size").add(fiwtewedcandidates.size)
    i-if (fiwtewedcandidates.isempty) simenginestats.countew("emptyfiwtewedtwhincandidates").incw()

    f-fiwtewedcandidates
  }

  /** a-a nyo-op fiwtew as utg fiwtewing awweady happens on u-utg sewvice side */
  p-pwivate def u-usewtweetgwaphfiwtew(
    u-usewtweetgwaphcandidates: seq[tweetwithscowe]
  ): seq[tweetwithscowe] = {
    vaw fiwtewedcandidates = u-usewtweetgwaphcandidates

    s-stats.stat("usewtweetgwaphcandidates_size").add(usewtweetgwaphcandidates.size)
    if (fiwtewedcandidates.isempty) stats.countew("emptyfiwtewedusewtweetgwaphcandidates").incw()

    f-fiwtewedcandidates.map { candidate =>
      tweetwithscowe(candidate.tweetid, (✿oωo) c-candidate.scowe)
    }
  }

  /** a nyo-op f-fiwtew as uvg fiwtewing a-awweady happens on uvg s-sewvice side */
  p-pwivate def usewvideogwaphfiwtew(
    usewvideogwaphcandidates: s-seq[tweetwithscowe]
  ): seq[tweetwithscowe] = {
    v-vaw fiwtewedcandidates = u-usewvideogwaphcandidates

    s-stats.stat("usewvideogwaphcandidates_size").add(usewvideogwaphcandidates.size)
    i-if (fiwtewedcandidates.isempty) stats.countew("emptyfiwtewedusewvideogwaphcandidates").incw()

    f-fiwtewedcandidates.map { c-candidate =>
      t-tweetwithscowe(candidate.tweetid, (✿oωo) candidate.scowe)
    }
  }
  pwivate d-def qigsimiwawtweetsfiwtew(
    qigsimiwawtweetscandidates: seq[tweetwithscowe], òωó
    q-qigmaxtweetagehouws: d-duwation, (˘ω˘)
    qigmaxnumsimiwawtweets: i-int
  ): seq[tweetwithscowe] = {
    vaw agefiwtewedcandidates = tweetagefiwtew(qigsimiwawtweetscandidates, (ˆ ﻌ ˆ)♡ q-qigmaxtweetagehouws)
    stats.stat("agefiwtewedqigsimiwawtweetscandidates_size").add(agefiwtewedcandidates.size)

    v-vaw fiwtewedcandidates = a-agefiwtewedcandidates.take(qigmaxnumsimiwawtweets)
    if (fiwtewedcandidates.isempty) stats.countew("emptyfiwtewedqigsimiwawtweetscandidates").incw()

    fiwtewedcandidates
  }

  /***
   * e-evewy candidate wiww have the c-cg info with tweetbasedunifiedsimiwawityengine
   * a-as they awe g-genewated by a c-composite of simiwawity e-engines. ( ͡o ω ͡o )
   * additionawwy, we stowe the contwibuting ses (eg., sann, rawr x3 utg).
   */
  p-pwivate def gettweetbasedunifiedcginfo(
    s-souwceinfoopt: option[souwceinfo], (˘ω˘)
    unifiedscowe: doubwe, òωó
    contwibutingsimiwawityengines: s-seq[simiwawityengineinfo]
  ): candidategenewationinfo = {
    candidategenewationinfo(
      souwceinfoopt, ( ͡o ω ͡o )
      simiwawityengineinfo(
        s-simiwawityenginetype = s-simiwawityenginetype.tweetbasedunifiedsimiwawityengine, σωσ
        modewid = nyone, (U ﹏ U) // w-we do nyot assign modewid fow a unified simiwawity e-engine
        s-scowe = some(unifiedscowe)
      ), rawr
      contwibutingsimiwawityengines
    )
  }
}

o-object tweetbasedunifiedsimiwawityengine {

  c-case cwass quewy(
    souwceinfo: souwceinfo, -.-
    maxcandidatenumpewsouwcekey: i-int, ( ͡o ω ͡o )
    enabwesimcwustewsann: boowean, >_<
    s-simcwustewsannquewy: e-enginequewy[simcwustewsannsimiwawityengine.quewy], o.O
    e-enabweexpewimentawsimcwustewsann: boowean, σωσ
    expewimentawsimcwustewsannquewy: enginequewy[simcwustewsannsimiwawityengine.quewy], -.-
    e-enabwesimcwustewsann1: boowean, σωσ
    simcwustewsann1quewy: enginequewy[simcwustewsannsimiwawityengine.quewy], :3
    enabwesimcwustewsann2: boowean, ^^
    s-simcwustewsann2quewy: e-enginequewy[simcwustewsannsimiwawityengine.quewy], òωó
    e-enabwesimcwustewsann3: b-boowean, (ˆ ﻌ ˆ)♡
    simcwustewsann3quewy: enginequewy[simcwustewsannsimiwawityengine.quewy], XD
    enabwesimcwustewsann5: b-boowean, òωó
    simcwustewsann5quewy: e-enginequewy[simcwustewsannsimiwawityengine.quewy], (ꈍᴗꈍ)
    enabwesimcwustewsann4: boowean, UwU
    simcwustewsann4quewy: e-enginequewy[simcwustewsannsimiwawityengine.quewy],
    simcwustewsminscowe: doubwe, >w<
    simcwustewsvideobasedminscowe: d-doubwe, ʘwʘ
    twhinmodewid: stwing, :3
    e-enabwetwhin: boowean, ^•ﻌ•^
    t-twhinmaxtweetagehouws: duwation, (ˆ ﻌ ˆ)♡
    q-qigmaxtweetagehouws: d-duwation, 🥺
    q-qigmaxnumsimiwawtweets: int, OwO
    enabweutg: b-boowean, 🥺
    utgquewy: enginequewy[tweetbasedusewtweetgwaphsimiwawityengine.quewy], OwO
    enabweuvg: b-boowean, (U ᵕ U❁)
    uvgquewy: enginequewy[tweetbasedusewvideogwaphsimiwawityengine.quewy], ( ͡o ω ͡o )
    enabweqig: boowean, ^•ﻌ•^
    q-qigquewy: enginequewy[tweetbasedqigsimiwawityengine.quewy], o.O
    p-pawams: configapi.pawams)

  d-def fwompawams(
    s-souwceinfo: s-souwceinfo, (⑅˘꒳˘)
    pawams: configapi.pawams, (ˆ ﻌ ˆ)♡
  ): enginequewy[quewy] = {
    // s-simcwustews
    vaw enabwesimcwustewsann =
      p-pawams(tweetbasedcandidategenewationpawams.enabwesimcwustewsannpawam)

    vaw simcwustewsmodewvewsion =
      m-modewvewsions.enum.enumtosimcwustewsmodewvewsionmap(pawams(gwobawpawams.modewvewsionpawam))
    vaw simcwustewsminscowe = p-pawams(tweetbasedcandidategenewationpawams.simcwustewsminscowepawam)
    v-vaw simcwustewsvideobasedminscowe = pawams(
      t-tweetbasedcandidategenewationpawams.simcwustewsvideobasedminscowepawam)
    vaw s-simcwustewsannconfigid = p-pawams(simcwustewsannpawams.simcwustewsannconfigid)
    // simcwustews - e-expewimentaw s-sann simiwawity engine (video based s-se)
    vaw enabweexpewimentawsimcwustewsann =
      pawams(tweetbasedcandidategenewationpawams.enabweexpewimentawsimcwustewsannpawam)

    vaw expewimentawsimcwustewsannconfigid = p-pawams(
      simcwustewsannpawams.expewimentawsimcwustewsannconfigid)
    // s-simcwustews - sann cwustew 1 simiwawity e-engine
    vaw enabwesimcwustewsann1 =
      p-pawams(tweetbasedcandidategenewationpawams.enabwesimcwustewsann1pawam)

    v-vaw simcwustewsann1configid = pawams(simcwustewsannpawams.simcwustewsann1configid)
    // s-simcwustews - s-sann cwustew 2 simiwawity engine
    v-vaw enabwesimcwustewsann2 =
      pawams(tweetbasedcandidategenewationpawams.enabwesimcwustewsann2pawam)
    v-vaw simcwustewsann2configid = pawams(simcwustewsannpawams.simcwustewsann2configid)
    // s-simcwustews - s-sann cwustew 3 simiwawity engine
    vaw enabwesimcwustewsann3 =
      pawams(tweetbasedcandidategenewationpawams.enabwesimcwustewsann3pawam)
    v-vaw s-simcwustewsann3configid = pawams(simcwustewsannpawams.simcwustewsann3configid)
    // simcwustews - sann cwustew 5 s-simiwawity engine
    vaw enabwesimcwustewsann5 =
      p-pawams(tweetbasedcandidategenewationpawams.enabwesimcwustewsann5pawam)
    v-vaw simcwustewsann5configid = pawams(simcwustewsannpawams.simcwustewsann5configid)
    // simcwustews - sann cwustew 4 simiwawity engine
    v-vaw enabwesimcwustewsann4 =
      pawams(tweetbasedcandidategenewationpawams.enabwesimcwustewsann4pawam)
    vaw simcwustewsann4configid = pawams(simcwustewsannpawams.simcwustewsann4configid)
    // s-simcwustews ann quewies f-fow diffewent s-sann cwustews
    vaw simcwustewsannquewy = s-simcwustewsannsimiwawityengine.fwompawams(
      s-souwceinfo.intewnawid, :3
      e-embeddingtype.wogfavwongestw2embeddingtweet, /(^•ω•^)
      s-simcwustewsmodewvewsion, òωó
      s-simcwustewsannconfigid, :3
      p-pawams
    )
    vaw expewimentawsimcwustewsannquewy = simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, (˘ω˘)
      embeddingtype.wogfavwongestw2embeddingtweet, 😳
      s-simcwustewsmodewvewsion, σωσ
      e-expewimentawsimcwustewsannconfigid, UwU
      p-pawams
    )
    v-vaw s-simcwustewsann1quewy = s-simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, -.-
      embeddingtype.wogfavwongestw2embeddingtweet, 🥺
      simcwustewsmodewvewsion, 😳😳😳
      simcwustewsann1configid, 🥺
      p-pawams
    )
    v-vaw simcwustewsann2quewy = simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, ^^
      embeddingtype.wogfavwongestw2embeddingtweet, ^^;;
      s-simcwustewsmodewvewsion, >w<
      s-simcwustewsann2configid, σωσ
      p-pawams
    )
    vaw simcwustewsann3quewy = simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, >w<
      e-embeddingtype.wogfavwongestw2embeddingtweet, (⑅˘꒳˘)
      simcwustewsmodewvewsion, òωó
      simcwustewsann3configid, (⑅˘꒳˘)
      pawams
    )
    v-vaw s-simcwustewsann5quewy = simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, (ꈍᴗꈍ)
      e-embeddingtype.wogfavwongestw2embeddingtweet, rawr x3
      simcwustewsmodewvewsion, ( ͡o ω ͡o )
      s-simcwustewsann5configid, UwU
      p-pawams
    )
    vaw simcwustewsann4quewy = s-simcwustewsannsimiwawityengine.fwompawams(
      s-souwceinfo.intewnawid, ^^
      e-embeddingtype.wogfavwongestw2embeddingtweet, (˘ω˘)
      s-simcwustewsmodewvewsion, (ˆ ﻌ ˆ)♡
      s-simcwustewsann4configid, OwO
      p-pawams
    )
    // tweetbasedcandidategenewation
    v-vaw maxcandidatenumpewsouwcekey = p-pawams(gwobawpawams.maxcandidatenumpewsouwcekeypawam)
    // twhin
    v-vaw twhinmodewid = pawams(tweetbasedtwhinpawams.modewidpawam)
    vaw enabwetwhin =
      p-pawams(tweetbasedcandidategenewationpawams.enabwetwhinpawam)

    vaw t-twhinmaxtweetagehouws = pawams(gwobawpawams.maxtweetagehouwspawam)

    // q-qig
    v-vaw enabweqig =
      pawams(tweetbasedcandidategenewationpawams.enabweqigsimiwawtweetspawam)
    vaw qigmaxtweetagehouws = p-pawams(gwobawpawams.maxtweetagehouwspawam)
    vaw qigmaxnumsimiwawtweets = pawams(
      t-tweetbasedcandidategenewationpawams.qigmaxnumsimiwawtweetspawam)

    // u-utg
    vaw enabweutg =
      pawams(tweetbasedcandidategenewationpawams.enabweutgpawam)
    // u-uvg
    vaw e-enabweuvg =
      pawams(tweetbasedcandidategenewationpawams.enabweuvgpawam)
    e-enginequewy(
      quewy(
        souwceinfo = s-souwceinfo, 😳
        m-maxcandidatenumpewsouwcekey = maxcandidatenumpewsouwcekey, UwU
        e-enabwesimcwustewsann = e-enabwesimcwustewsann,
        simcwustewsannquewy = simcwustewsannquewy, 🥺
        enabweexpewimentawsimcwustewsann = e-enabweexpewimentawsimcwustewsann, 😳😳😳
        e-expewimentawsimcwustewsannquewy = e-expewimentawsimcwustewsannquewy, ʘwʘ
        e-enabwesimcwustewsann1 = enabwesimcwustewsann1, /(^•ω•^)
        simcwustewsann1quewy = simcwustewsann1quewy, :3
        enabwesimcwustewsann2 = enabwesimcwustewsann2, :3
        simcwustewsann2quewy = simcwustewsann2quewy, mya
        enabwesimcwustewsann3 = e-enabwesimcwustewsann3, (///ˬ///✿)
        s-simcwustewsann3quewy = s-simcwustewsann3quewy, (⑅˘꒳˘)
        e-enabwesimcwustewsann5 = e-enabwesimcwustewsann5, :3
        s-simcwustewsann5quewy = simcwustewsann5quewy, /(^•ω•^)
        e-enabwesimcwustewsann4 = enabwesimcwustewsann4, ^^;;
        s-simcwustewsann4quewy = simcwustewsann4quewy, (U ᵕ U❁)
        s-simcwustewsminscowe = s-simcwustewsminscowe, (U ﹏ U)
        simcwustewsvideobasedminscowe = simcwustewsvideobasedminscowe, mya
        t-twhinmodewid = twhinmodewid, ^•ﻌ•^
        enabwetwhin = e-enabwetwhin, (U ﹏ U)
        twhinmaxtweetagehouws = t-twhinmaxtweetagehouws, :3
        q-qigmaxtweetagehouws = qigmaxtweetagehouws, rawr x3
        qigmaxnumsimiwawtweets = q-qigmaxnumsimiwawtweets, 😳😳😳
        e-enabweutg = e-enabweutg, >w<
        utgquewy = t-tweetbasedusewtweetgwaphsimiwawityengine
          .fwompawams(souwceinfo.intewnawid, p-pawams), òωó
        enabweqig = e-enabweqig, 😳
        qigquewy = t-tweetbasedqigsimiwawityengine.fwompawams(souwceinfo.intewnawid, (✿oωo) p-pawams),
        e-enabweuvg = enabweuvg, OwO
        u-uvgquewy =
          tweetbasedusewvideogwaphsimiwawityengine.fwompawams(souwceinfo.intewnawid, (U ﹏ U) pawams),
        p-pawams = pawams
      ), (ꈍᴗꈍ)
      pawams
    )
  }

  def fwompawamsfowwewatedtweet(
    intewnawid: intewnawid, rawr
    pawams: configapi.pawams, ^^
  ): enginequewy[quewy] = {
    // s-simcwustews
    vaw enabwesimcwustewsann = pawams(wewatedtweettweetbasedpawams.enabwesimcwustewsannpawam)
    vaw simcwustewsmodewvewsion =
      modewvewsions.enum.enumtosimcwustewsmodewvewsionmap(pawams(gwobawpawams.modewvewsionpawam))
    vaw simcwustewsminscowe = pawams(wewatedtweettweetbasedpawams.simcwustewsminscowepawam)
    vaw simcwustewsannconfigid = p-pawams(simcwustewsannpawams.simcwustewsannconfigid)
    vaw enabweexpewimentawsimcwustewsann =
      pawams(wewatedtweettweetbasedpawams.enabweexpewimentawsimcwustewsannpawam)
    v-vaw expewimentawsimcwustewsannconfigid = pawams(
      s-simcwustewsannpawams.expewimentawsimcwustewsannconfigid)
    // simcwustews - sann cwustew 1 s-simiwawity engine
    vaw e-enabwesimcwustewsann1 = pawams(wewatedtweettweetbasedpawams.enabwesimcwustewsann1pawam)
    v-vaw s-simcwustewsann1configid = pawams(simcwustewsannpawams.simcwustewsann1configid)
    // simcwustews - s-sann cwustew 2 simiwawity engine
    vaw enabwesimcwustewsann2 = pawams(wewatedtweettweetbasedpawams.enabwesimcwustewsann2pawam)
    v-vaw simcwustewsann2configid = pawams(simcwustewsannpawams.simcwustewsann2configid)
    // s-simcwustews - sann cwustew 3 s-simiwawity engine
    vaw enabwesimcwustewsann3 = p-pawams(wewatedtweettweetbasedpawams.enabwesimcwustewsann3pawam)
    v-vaw simcwustewsann3configid = pawams(simcwustewsannpawams.simcwustewsann3configid)
    // simcwustews - sann c-cwustew 5 simiwawity engine
    vaw enabwesimcwustewsann5 = pawams(wewatedtweettweetbasedpawams.enabwesimcwustewsann5pawam)
    v-vaw simcwustewsann5configid = pawams(simcwustewsannpawams.simcwustewsann5configid)
    // simcwustews - sann cwustew 4 simiwawity e-engine
    v-vaw enabwesimcwustewsann4 = pawams(wewatedtweettweetbasedpawams.enabwesimcwustewsann4pawam)
    v-vaw simcwustewsann4configid = p-pawams(simcwustewsannpawams.simcwustewsann4configid)
    // simcwustews a-ann quewies fow diffewent sann cwustews
    vaw simcwustewsannquewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid, rawr
      embeddingtype.wogfavwongestw2embeddingtweet, nyaa~~
      s-simcwustewsmodewvewsion, nyaa~~
      simcwustewsannconfigid, o.O
      pawams
    )
    v-vaw expewimentawsimcwustewsannquewy = s-simcwustewsannsimiwawityengine.fwompawams(
      intewnawid, òωó
      e-embeddingtype.wogfavwongestw2embeddingtweet,
      simcwustewsmodewvewsion, ^^;;
      expewimentawsimcwustewsannconfigid, rawr
      p-pawams
    )
    vaw simcwustewsann1quewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid, ^•ﻌ•^
      e-embeddingtype.wogfavwongestw2embeddingtweet, nyaa~~
      simcwustewsmodewvewsion,
      simcwustewsann1configid, nyaa~~
      p-pawams
    )
    vaw simcwustewsann2quewy = simcwustewsannsimiwawityengine.fwompawams(
      intewnawid,
      embeddingtype.wogfavwongestw2embeddingtweet, 😳😳😳
      simcwustewsmodewvewsion, 😳😳😳
      simcwustewsann2configid, σωσ
      pawams
    )
    v-vaw simcwustewsann3quewy = s-simcwustewsannsimiwawityengine.fwompawams(
      intewnawid, o.O
      e-embeddingtype.wogfavwongestw2embeddingtweet, σωσ
      s-simcwustewsmodewvewsion, nyaa~~
      simcwustewsann3configid, rawr x3
      p-pawams
    )
    vaw simcwustewsann5quewy = simcwustewsannsimiwawityengine.fwompawams(
      intewnawid, (///ˬ///✿)
      embeddingtype.wogfavwongestw2embeddingtweet, o.O
      simcwustewsmodewvewsion, òωó
      s-simcwustewsann5configid, OwO
      pawams
    )
    vaw simcwustewsann4quewy = simcwustewsannsimiwawityengine.fwompawams(
      intewnawid, σωσ
      e-embeddingtype.wogfavwongestw2embeddingtweet, nyaa~~
      s-simcwustewsmodewvewsion, OwO
      s-simcwustewsann4configid, ^^
      pawams
    )
    // tweetbasedcandidategenewation
    vaw maxcandidatenumpewsouwcekey = p-pawams(gwobawpawams.maxcandidatenumpewsouwcekeypawam)
    // t-twhin
    v-vaw twhinmodewid = pawams(tweetbasedtwhinpawams.modewidpawam)
    v-vaw enabwetwhin = pawams(wewatedtweettweetbasedpawams.enabwetwhinpawam)
    v-vaw twhinmaxtweetagehouws = pawams(gwobawpawams.maxtweetagehouwspawam)
    // q-qig
    vaw enabweqig = pawams(wewatedtweettweetbasedpawams.enabweqigsimiwawtweetspawam)
    v-vaw qigmaxtweetagehouws = pawams(gwobawpawams.maxtweetagehouwspawam)
    v-vaw qigmaxnumsimiwawtweets = pawams(
      t-tweetbasedcandidategenewationpawams.qigmaxnumsimiwawtweetspawam)
    // u-utg
    vaw enabweutg = p-pawams(wewatedtweettweetbasedpawams.enabweutgpawam)
    // u-uvg
    vaw enabweuvg = p-pawams(wewatedtweettweetbasedpawams.enabweuvgpawam)
    // souwcetype.wequesttweetid is a pwacehowdew. (///ˬ///✿)
    vaw s-souwceinfo = souwceinfo(souwcetype.wequesttweetid, σωσ i-intewnawid, rawr x3 n-nyone)

    enginequewy(
      quewy(
        souwceinfo = souwceinfo, (ˆ ﻌ ˆ)♡
        m-maxcandidatenumpewsouwcekey = maxcandidatenumpewsouwcekey, 🥺
        enabwesimcwustewsann = enabwesimcwustewsann, (⑅˘꒳˘)
        simcwustewsminscowe = simcwustewsminscowe, 😳😳😳
        simcwustewsvideobasedminscowe = simcwustewsminscowe, /(^•ω•^)
        simcwustewsannquewy = simcwustewsannquewy, >w<
        enabweexpewimentawsimcwustewsann = enabweexpewimentawsimcwustewsann, ^•ﻌ•^
        e-expewimentawsimcwustewsannquewy = expewimentawsimcwustewsannquewy, 😳😳😳
        enabwesimcwustewsann1 = e-enabwesimcwustewsann1, :3
        simcwustewsann1quewy = s-simcwustewsann1quewy, (ꈍᴗꈍ)
        enabwesimcwustewsann2 = enabwesimcwustewsann2, ^•ﻌ•^
        s-simcwustewsann2quewy = simcwustewsann2quewy, >w<
        enabwesimcwustewsann3 = e-enabwesimcwustewsann3, ^^;;
        simcwustewsann3quewy = simcwustewsann3quewy,
        e-enabwesimcwustewsann5 = enabwesimcwustewsann5, (✿oωo)
        simcwustewsann5quewy = simcwustewsann5quewy, òωó
        e-enabwesimcwustewsann4 = enabwesimcwustewsann4, ^^
        simcwustewsann4quewy = s-simcwustewsann4quewy, ^^
        t-twhinmodewid = twhinmodewid, rawr
        enabwetwhin = enabwetwhin, XD
        t-twhinmaxtweetagehouws = t-twhinmaxtweetagehouws, rawr
        qigmaxtweetagehouws = qigmaxtweetagehouws, 😳
        q-qigmaxnumsimiwawtweets = q-qigmaxnumsimiwawtweets, 🥺
        enabweutg = enabweutg, (U ᵕ U❁)
        u-utgquewy = tweetbasedusewtweetgwaphsimiwawityengine
          .fwompawams(souwceinfo.intewnawid, 😳 pawams), 🥺
        enabweqig = e-enabweqig, (///ˬ///✿)
        qigquewy = tweetbasedqigsimiwawityengine.fwompawams(souwceinfo.intewnawid, mya pawams), (✿oωo)
        e-enabweuvg = e-enabweuvg, ^•ﻌ•^
        u-uvgquewy =
          tweetbasedusewvideogwaphsimiwawityengine.fwompawams(souwceinfo.intewnawid, pawams), o.O
        pawams = p-pawams, o.O
      ),
      pawams
    )
  }
  d-def fwompawamsfowwewatedvideotweet(
    intewnawid: intewnawid, XD
    p-pawams: c-configapi.pawams, ^•ﻌ•^
  ): enginequewy[quewy] = {
    // simcwustews
    vaw enabwesimcwustewsann = pawams(wewatedvideotweettweetbasedpawams.enabwesimcwustewsannpawam)
    vaw s-simcwustewsmodewvewsion =
      m-modewvewsions.enum.enumtosimcwustewsmodewvewsionmap(pawams(gwobawpawams.modewvewsionpawam))
    vaw simcwustewsminscowe = pawams(wewatedvideotweettweetbasedpawams.simcwustewsminscowepawam)
    v-vaw simcwustewsannconfigid = pawams(simcwustewsannpawams.simcwustewsannconfigid)
    vaw enabweexpewimentawsimcwustewsann = pawams(
      w-wewatedvideotweettweetbasedpawams.enabweexpewimentawsimcwustewsannpawam)
    v-vaw expewimentawsimcwustewsannconfigid = p-pawams(
      s-simcwustewsannpawams.expewimentawsimcwustewsannconfigid)
    // s-simcwustews - sann c-cwustew 1 simiwawity engine
    vaw enabwesimcwustewsann1 = p-pawams(wewatedvideotweettweetbasedpawams.enabwesimcwustewsann1pawam)
    v-vaw simcwustewsann1configid = p-pawams(simcwustewsannpawams.simcwustewsann1configid)
    // s-simcwustews - s-sann cwustew 2 s-simiwawity engine
    vaw enabwesimcwustewsann2 = p-pawams(wewatedvideotweettweetbasedpawams.enabwesimcwustewsann2pawam)
    v-vaw simcwustewsann2configid = p-pawams(simcwustewsannpawams.simcwustewsann2configid)
    // simcwustews - sann cwustew 3 s-simiwawity engine
    vaw enabwesimcwustewsann3 = pawams(wewatedvideotweettweetbasedpawams.enabwesimcwustewsann3pawam)
    v-vaw simcwustewsann3configid = pawams(simcwustewsannpawams.simcwustewsann3configid)
    // s-simcwustews - s-sann cwustew 5 simiwawity engine
    vaw enabwesimcwustewsann5 = pawams(wewatedvideotweettweetbasedpawams.enabwesimcwustewsann5pawam)
    vaw s-simcwustewsann5configid = p-pawams(simcwustewsannpawams.simcwustewsann5configid)

    // simcwustews - s-sann cwustew 4 s-simiwawity engine
    vaw enabwesimcwustewsann4 = pawams(wewatedvideotweettweetbasedpawams.enabwesimcwustewsann4pawam)
    v-vaw simcwustewsann4configid = p-pawams(simcwustewsannpawams.simcwustewsann4configid)
    // simcwustews ann quewies f-fow diffewent s-sann cwustews
    vaw simcwustewsannquewy = simcwustewsannsimiwawityengine.fwompawams(
      intewnawid, ʘwʘ
      e-embeddingtype.wogfavwongestw2embeddingtweet, (U ﹏ U)
      simcwustewsmodewvewsion, 😳😳😳
      simcwustewsannconfigid, 🥺
      pawams
    )
    vaw expewimentawsimcwustewsannquewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid, (///ˬ///✿)
      embeddingtype.wogfavwongestw2embeddingtweet, (˘ω˘)
      simcwustewsmodewvewsion, :3
      e-expewimentawsimcwustewsannconfigid, /(^•ω•^)
      p-pawams
    )
    v-vaw simcwustewsann1quewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid, :3
      e-embeddingtype.wogfavwongestw2embeddingtweet, mya
      s-simcwustewsmodewvewsion,
      s-simcwustewsann1configid, XD
      p-pawams
    )
    vaw simcwustewsann2quewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid, (///ˬ///✿)
      e-embeddingtype.wogfavwongestw2embeddingtweet, 🥺
      s-simcwustewsmodewvewsion, o.O
      simcwustewsann2configid, mya
      p-pawams
    )
    v-vaw simcwustewsann3quewy = s-simcwustewsannsimiwawityengine.fwompawams(
      intewnawid, rawr x3
      e-embeddingtype.wogfavwongestw2embeddingtweet, 😳
      s-simcwustewsmodewvewsion, 😳😳😳
      s-simcwustewsann3configid, >_<
      p-pawams
    )
    v-vaw simcwustewsann5quewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid, >w<
      embeddingtype.wogfavwongestw2embeddingtweet, rawr x3
      s-simcwustewsmodewvewsion, XD
      s-simcwustewsann5configid, ^^
      pawams
    )

    vaw simcwustewsann4quewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid, (✿oωo)
      e-embeddingtype.wogfavwongestw2embeddingtweet, >w<
      simcwustewsmodewvewsion, 😳😳😳
      s-simcwustewsann4configid, (ꈍᴗꈍ)
      p-pawams
    )
    // tweetbasedcandidategenewation
    vaw m-maxcandidatenumpewsouwcekey = p-pawams(gwobawpawams.maxcandidatenumpewsouwcekeypawam)
    // t-twhin
    v-vaw twhinmodewid = p-pawams(tweetbasedtwhinpawams.modewidpawam)
    v-vaw enabwetwhin = pawams(wewatedvideotweettweetbasedpawams.enabwetwhinpawam)
    vaw twhinmaxtweetagehouws = p-pawams(gwobawpawams.maxtweetagehouwspawam)
    // qig
    vaw enabweqig = pawams(wewatedvideotweettweetbasedpawams.enabweqigsimiwawtweetspawam)
    vaw qigmaxtweetagehouws = p-pawams(gwobawpawams.maxtweetagehouwspawam)
    v-vaw qigmaxnumsimiwawtweets = pawams(
      tweetbasedcandidategenewationpawams.qigmaxnumsimiwawtweetspawam)
    // utg
    vaw enabweutg = pawams(wewatedvideotweettweetbasedpawams.enabweutgpawam)

    // souwcetype.wequesttweetid i-is a pwacehowdew. (✿oωo)
    vaw s-souwceinfo = souwceinfo(souwcetype.wequesttweetid, (˘ω˘) intewnawid, n-nyone)

    vaw enabweuvg = pawams(wewatedvideotweettweetbasedpawams.enabweuvgpawam)
    e-enginequewy(
      q-quewy(
        s-souwceinfo = souwceinfo, nyaa~~
        maxcandidatenumpewsouwcekey = maxcandidatenumpewsouwcekey, ( ͡o ω ͡o )
        e-enabwesimcwustewsann = enabwesimcwustewsann, 🥺
        s-simcwustewsminscowe = simcwustewsminscowe, (U ﹏ U)
        s-simcwustewsvideobasedminscowe = simcwustewsminscowe, ( ͡o ω ͡o )
        simcwustewsannquewy = s-simcwustewsannquewy, (///ˬ///✿)
        enabweexpewimentawsimcwustewsann = e-enabweexpewimentawsimcwustewsann, (///ˬ///✿)
        expewimentawsimcwustewsannquewy = expewimentawsimcwustewsannquewy, (✿oωo)
        e-enabwesimcwustewsann1 = enabwesimcwustewsann1,
        s-simcwustewsann1quewy = simcwustewsann1quewy, (U ᵕ U❁)
        enabwesimcwustewsann2 = enabwesimcwustewsann2, ʘwʘ
        simcwustewsann2quewy = simcwustewsann2quewy, ʘwʘ
        enabwesimcwustewsann3 = enabwesimcwustewsann3, XD
        s-simcwustewsann3quewy = s-simcwustewsann3quewy, (✿oωo)
        e-enabwesimcwustewsann5 = e-enabwesimcwustewsann5, ^•ﻌ•^
        simcwustewsann5quewy = simcwustewsann5quewy, ^•ﻌ•^
        e-enabwesimcwustewsann4 = enabwesimcwustewsann4, >_<
        simcwustewsann4quewy = simcwustewsann4quewy, mya
        twhinmodewid = t-twhinmodewid, σωσ
        e-enabwetwhin = e-enabwetwhin, rawr
        t-twhinmaxtweetagehouws = twhinmaxtweetagehouws, (✿oωo)
        qigmaxtweetagehouws = qigmaxtweetagehouws, :3
        qigmaxnumsimiwawtweets = qigmaxnumsimiwawtweets, rawr x3
        e-enabweutg = e-enabweutg, ^^
        utgquewy = tweetbasedusewtweetgwaphsimiwawityengine
          .fwompawams(souwceinfo.intewnawid, pawams), ^^
        e-enabweuvg = enabweuvg, OwO
        u-uvgquewy =
          t-tweetbasedusewvideogwaphsimiwawityengine.fwompawams(souwceinfo.intewnawid, ʘwʘ p-pawams), /(^•ω•^)
        enabweqig = enabweqig,
        qigquewy = tweetbasedqigsimiwawityengine.fwompawams(souwceinfo.intewnawid, ʘwʘ pawams),
        p-pawams = pawams
      ), (⑅˘꒳˘)
      pawams
    )
  }
}
