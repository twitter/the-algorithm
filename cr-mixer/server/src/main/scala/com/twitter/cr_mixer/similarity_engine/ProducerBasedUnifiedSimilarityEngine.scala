package com.twittew.cw_mixew.simiwawity_engine

impowt com.twittew.cw_mixew.modew.candidategenewationinfo
i-impowt c-com.twittew.cw_mixew.modew.moduwenames
i-impowt com.twittew.cw_mixew.modew.simiwawityengineinfo
i-impowt c-com.twittew.cw_mixew.modew.souwceinfo
i-impowt c-com.twittew.cw_mixew.modew.tweetwithcandidategenewationinfo
i-impowt com.twittew.cw_mixew.modew.tweetwithscowe
impowt com.twittew.cw_mixew.pawam.gwobawpawams
impowt com.twittew.cw_mixew.pawam.pwoducewbasedcandidategenewationpawams
i-impowt com.twittew.cw_mixew.pawam.unifiedsetweetcombinationmethod
impowt com.twittew.cw_mixew.pawam.wewatedtweetpwoducewbasedpawams
i-impowt com.twittew.cw_mixew.pawam.simcwustewsannpawams
i-impowt com.twittew.cw_mixew.thwiftscawa.simiwawityenginetype
impowt com.twittew.cw_mixew.thwiftscawa.souwcetype
impowt com.twittew.cw_mixew.utiw.intewweaveutiw
impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.fwigate.common.utiw.statsutiw
impowt com.twittew.simcwustews_v2.common.modewvewsions
i-impowt c-com.twittew.simcwustews_v2.thwiftscawa.embeddingtype
impowt com.twittew.simcwustews_v2.thwiftscawa.intewnawid
impowt com.twittew.stowehaus.weadabwestowe
impowt c-com.twittew.timewines.configapi
impowt com.twittew.utiw.duwation
impowt com.twittew.utiw.futuwe
impowt javax.inject.named
impowt j-javax.inject.singweton
impowt scawa.cowwection.mutabwe.awwaybuffew

/**
 * t-this s-stowe wooks fow s-simiwaw tweets f-fwom usewtweetgwaph fow a souwce pwoducewid
 * fow a-a quewy pwoducewid,usew tweet gwaph (utg), >_<
 * w-wets us find out which tweets the quewy pwoducew's fowwowews co-engaged
 */
@singweton
case cwass pwoducewbasedunifiedsimiwawityengine(
  @named(moduwenames.pwoducewbasedusewtweetgwaphsimiwawityengine)
  p-pwoducewbasedusewtweetgwaphsimiwawityengine: standawdsimiwawityengine[
    p-pwoducewbasedusewtweetgwaphsimiwawityengine.quewy, ^^;;
    tweetwithscowe
  ], ^^;;
  s-simcwustewsannsimiwawityengine: s-standawdsimiwawityengine[
    simcwustewsannsimiwawityengine.quewy, /(^•ω•^)
    tweetwithscowe
  ], nyaa~~
  statsweceivew: s-statsweceivew)
    e-extends weadabwestowe[pwoducewbasedunifiedsimiwawityengine.quewy, (✿oωo) seq[
      t-tweetwithcandidategenewationinfo
    ]] {

  impowt p-pwoducewbasedunifiedsimiwawityengine._
  pwivate vaw stats = s-statsweceivew.scope(this.getcwass.getsimpwename)
  pwivate vaw f-fetchcandidatesstat = stats.scope("fetchcandidates")

  ovewwide d-def get(
    quewy: quewy
  ): f-futuwe[option[seq[tweetwithcandidategenewationinfo]]] = {
    quewy.souwceinfo.intewnawid m-match {
      c-case _: intewnawid.usewid =>
        statsutiw.twackoptionitemsstats(fetchcandidatesstat) {
          vaw sanncandidatesfut = if (quewy.enabwesimcwustewsann) {
            simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsannquewy)
          } ewse futuwe.none

          vaw sann1candidatesfut =
            i-if (quewy.enabwesimcwustewsann1) {
              s-simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsann1quewy)
            } ewse futuwe.none

          vaw s-sann2candidatesfut =
            i-if (quewy.enabwesimcwustewsann2) {
              s-simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsann2quewy)
            } ewse futuwe.none

          vaw sann3candidatesfut =
            i-if (quewy.enabwesimcwustewsann3) {
              simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsann3quewy)
            } ewse futuwe.none

          vaw sann4candidatesfut =
            if (quewy.enabwesimcwustewsann4) {
              s-simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsann4quewy)
            } ewse f-futuwe.none

          v-vaw sann5candidatesfut =
            if (quewy.enabwesimcwustewsann5) {
              s-simcwustewsannsimiwawityengine.getcandidates(quewy.simcwustewsann5quewy)
            } ewse futuwe.none

          v-vaw expewimentawsanncandidatesfut =
            i-if (quewy.enabweexpewimentawsimcwustewsann) {
              simcwustewsannsimiwawityengine.getcandidates(quewy.expewimentawsimcwustewsannquewy)
            } e-ewse futuwe.none

          v-vaw utgcandidatesfut = if (quewy.enabweutg) {
            p-pwoducewbasedusewtweetgwaphsimiwawityengine.getcandidates(quewy.utgquewy)
          } e-ewse f-futuwe.none

          f-futuwe
            .join(
              s-sanncandidatesfut, ( ͡o ω ͡o )
              sann1candidatesfut, (U ᵕ U❁)
              sann2candidatesfut, òωó
              sann3candidatesfut, σωσ
              s-sann4candidatesfut, :3
              sann5candidatesfut, OwO
              expewimentawsanncandidatesfut, ^^
              utgcandidatesfut
            ).map {
              case (
                    simcwustewsanncandidates, (˘ω˘)
                    s-simcwustewsann1candidates, OwO
                    simcwustewsann2candidates, UwU
                    simcwustewsann3candidates,
                    simcwustewsann4candidates, ^•ﻌ•^
                    s-simcwustewsann5candidates,
                    e-expewimentawsanncandidates, (ꈍᴗꈍ)
                    u-usewtweetgwaphcandidates) =>
                vaw f-fiwtewedsanntweets = simcwustewscandidateminscowefiwtew(
                  s-simcwustewsanncandidates.toseq.fwatten,
                  q-quewy.simcwustewsminscowe, /(^•ω•^)
                  quewy.simcwustewsannquewy.stowequewy.simcwustewsannconfigid)

                vaw fiwtewedexpewimentawsanntweets = simcwustewscandidateminscowefiwtew(
                  expewimentawsanncandidates.toseq.fwatten, (U ᵕ U❁)
                  quewy.simcwustewsminscowe, (✿oωo)
                  q-quewy.expewimentawsimcwustewsannquewy.stowequewy.simcwustewsannconfigid)

                vaw fiwtewedsann1tweets = s-simcwustewscandidateminscowefiwtew(
                  simcwustewsann1candidates.toseq.fwatten, OwO
                  quewy.simcwustewsminscowe, :3
                  q-quewy.simcwustewsann1quewy.stowequewy.simcwustewsannconfigid)

                v-vaw fiwtewedsann2tweets = simcwustewscandidateminscowefiwtew(
                  simcwustewsann2candidates.toseq.fwatten, nyaa~~
                  q-quewy.simcwustewsminscowe, ^•ﻌ•^
                  q-quewy.simcwustewsann2quewy.stowequewy.simcwustewsannconfigid)

                vaw f-fiwtewedsann3tweets = s-simcwustewscandidateminscowefiwtew(
                  simcwustewsann3candidates.toseq.fwatten, ( ͡o ω ͡o )
                  quewy.simcwustewsminscowe, ^^;;
                  quewy.simcwustewsann3quewy.stowequewy.simcwustewsannconfigid)

                vaw fiwtewedsann4tweets = s-simcwustewscandidateminscowefiwtew(
                  s-simcwustewsann4candidates.toseq.fwatten, mya
                  quewy.simcwustewsminscowe, (U ᵕ U❁)
                  q-quewy.simcwustewsann4quewy.stowequewy.simcwustewsannconfigid)

                vaw fiwtewedsann5tweets = s-simcwustewscandidateminscowefiwtew(
                  s-simcwustewsann5candidates.toseq.fwatten, ^•ﻌ•^
                  quewy.simcwustewsminscowe, (U ﹏ U)
                  q-quewy.simcwustewsann5quewy.stowequewy.simcwustewsannconfigid)

                vaw fiwtewedutgtweets =
                  usewtweetgwaphfiwtew(usewtweetgwaphcandidates.toseq.fwatten)

                vaw sanntweetswithcginfo = fiwtewedsanntweets.map { t-tweetwithscowe =>
                  v-vaw simiwawityengineinfo = simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsannquewy, /(^•ω•^) tweetwithscowe.scowe)
                  t-tweetwithcandidategenewationinfo(
                    t-tweetwithscowe.tweetid, ʘwʘ
                    candidategenewationinfo(
                      some(quewy.souwceinfo), XD
                      simiwawityengineinfo, (⑅˘꒳˘)
                      s-seq(simiwawityengineinfo)
                    ))
                }
                vaw sann1tweetswithcginfo = fiwtewedsann1tweets.map { tweetwithscowe =>
                  vaw simiwawityengineinfo = simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsann1quewy, nyaa~~ t-tweetwithscowe.scowe)
                  tweetwithcandidategenewationinfo(
                    tweetwithscowe.tweetid, UwU
                    candidategenewationinfo(
                      s-some(quewy.souwceinfo), (˘ω˘)
                      s-simiwawityengineinfo, rawr x3
                      seq(simiwawityengineinfo)
                    ))
                }
                vaw sann2tweetswithcginfo = fiwtewedsann2tweets.map { t-tweetwithscowe =>
                  v-vaw simiwawityengineinfo = simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsann2quewy, (///ˬ///✿) tweetwithscowe.scowe)
                  tweetwithcandidategenewationinfo(
                    tweetwithscowe.tweetid, 😳😳😳
                    c-candidategenewationinfo(
                      some(quewy.souwceinfo), (///ˬ///✿)
                      s-simiwawityengineinfo, ^^;;
                      seq(simiwawityengineinfo)
                    ))
                }

                vaw sann3tweetswithcginfo = f-fiwtewedsann3tweets.map { tweetwithscowe =>
                  v-vaw simiwawityengineinfo = s-simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsann3quewy, ^^ tweetwithscowe.scowe)
                  t-tweetwithcandidategenewationinfo(
                    tweetwithscowe.tweetid, (///ˬ///✿)
                    c-candidategenewationinfo(
                      s-some(quewy.souwceinfo), -.-
                      s-simiwawityengineinfo, /(^•ω•^)
                      seq(simiwawityengineinfo)
                    ))
                }

                v-vaw sann4tweetswithcginfo = f-fiwtewedsann4tweets.map { tweetwithscowe =>
                  vaw simiwawityengineinfo = s-simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsann4quewy, UwU t-tweetwithscowe.scowe)
                  t-tweetwithcandidategenewationinfo(
                    tweetwithscowe.tweetid, (⑅˘꒳˘)
                    candidategenewationinfo(
                      s-some(quewy.souwceinfo), ʘwʘ
                      simiwawityengineinfo, σωσ
                      s-seq(simiwawityengineinfo)
                    ))
                }

                v-vaw sann5tweetswithcginfo = fiwtewedsann5tweets.map { tweetwithscowe =>
                  vaw s-simiwawityengineinfo = s-simcwustewsannsimiwawityengine
                    .tosimiwawityengineinfo(quewy.simcwustewsann5quewy, ^^ t-tweetwithscowe.scowe)
                  t-tweetwithcandidategenewationinfo(
                    tweetwithscowe.tweetid, OwO
                    c-candidategenewationinfo(
                      some(quewy.souwceinfo), (ˆ ﻌ ˆ)♡
                      simiwawityengineinfo, o.O
                      seq(simiwawityengineinfo)
                    ))
                }

                vaw expewimentawsanntweetswithcginfo = fiwtewedexpewimentawsanntweets.map {
                  t-tweetwithscowe =>
                    vaw simiwawityengineinfo = s-simcwustewsannsimiwawityengine
                      .tosimiwawityengineinfo(
                        quewy.expewimentawsimcwustewsannquewy, (˘ω˘)
                        t-tweetwithscowe.scowe)
                    tweetwithcandidategenewationinfo(
                      t-tweetwithscowe.tweetid, 😳
                      candidategenewationinfo(
                        s-some(quewy.souwceinfo), (U ᵕ U❁)
                        s-simiwawityengineinfo, :3
                        s-seq(simiwawityengineinfo)
                      ))
                }
                vaw u-utgtweetswithcginfo = f-fiwtewedutgtweets.map { tweetwithscowe =>
                  vaw simiwawityengineinfo =
                    pwoducewbasedusewtweetgwaphsimiwawityengine
                      .tosimiwawityengineinfo(tweetwithscowe.scowe)
                  tweetwithcandidategenewationinfo(
                    tweetwithscowe.tweetid,
                    candidategenewationinfo(
                      s-some(quewy.souwceinfo), o.O
                      s-simiwawityengineinfo, (///ˬ///✿)
                      s-seq(simiwawityengineinfo)
                    ))
                }

                vaw candidatesouwcestobeintewweaved =
                  a-awwaybuffew[seq[tweetwithcandidategenewationinfo]](
                    sanntweetswithcginfo, OwO
                    sann1tweetswithcginfo, >w<
                    sann2tweetswithcginfo, ^^
                    sann3tweetswithcginfo,
                    s-sann4tweetswithcginfo, (⑅˘꒳˘)
                    s-sann5tweetswithcginfo, ʘwʘ
                    expewimentawsanntweetswithcginfo,
                  )

                i-if (quewy.utgcombinationmethod == unifiedsetweetcombinationmethod.intewweave) {
                  candidatesouwcestobeintewweaved += utgtweetswithcginfo
                }

                vaw intewweavedcandidates =
                  i-intewweaveutiw.intewweave(candidatesouwcestobeintewweaved)

                v-vaw candidatesouwcestobeowdewed =
                  awwaybuffew[seq[tweetwithcandidategenewationinfo]](intewweavedcandidates)

                i-if (quewy.utgcombinationmethod == u-unifiedsetweetcombinationmethod.fwontwoad)
                  candidatesouwcestobeowdewed.pwepend(utgtweetswithcginfo)

                vaw candidatesfwomgivenowdewcombination =
                  simiwawitysouwceowdewingutiw.keepgivenowdew(candidatesouwcestobeowdewed)

                vaw unifiedcandidateswithunifiedcginfo = c-candidatesfwomgivenowdewcombination.map {
                  c-candidate =>
                    /***
                     * w-when a candidate w-was made by i-intewweave/keepgivenowdew, (///ˬ///✿)
                     * then we appwy g-getpwoducewbasedunifiedcginfo() t-to ovewwide with the unified cginfo
                     *
                     * i-in contwibutingse w-wist fow intewweave. XD we onwy h-have the chosen se avaiwabwe. 😳
                     * this is hawd t-to add fow intewweave, >w< and we p-pwan to add it w-watew aftew abstwaction impwovement. (˘ω˘)
                     */
                    t-tweetwithcandidategenewationinfo(
                      tweetid = candidate.tweetid, nyaa~~
                      c-candidategenewationinfo = g-getpwoducewbasedunifiedcginfo(
                        c-candidate.candidategenewationinfo.souwceinfoopt, 😳😳😳
                        candidate.getsimiwawityscowe, (U ﹏ U)
                        candidate.candidategenewationinfo.contwibutingsimiwawityengines
                      ) // getsimiwawityscowe c-comes fwom eithew unifiedscowe ow singwe s-scowe
                    )
                }
                s-stats.stat("unified_candidate_size").add(unifiedcandidateswithunifiedcginfo.size)
                vaw twuncatedcandidates =
                  unifiedcandidateswithunifiedcginfo.take(quewy.maxcandidatenumpewsouwcekey)
                s-stats.stat("twuncatedcandidates_size").add(twuncatedcandidates.size)

                some(twuncatedcandidates)

            }
        }

      c-case _ =>
        s-stats.countew("souwceid_is_not_usewid_cnt").incw()
        futuwe.none
    }
  }

  pwivate def simcwustewscandidateminscowefiwtew(
    s-simcwustewsanncandidates: seq[tweetwithscowe], (˘ω˘)
    simcwustewsminscowe: d-doubwe, :3
    s-simcwustewsannconfigid: stwing
  ): seq[tweetwithscowe] = {
    v-vaw fiwtewedcandidates = simcwustewsanncandidates
      .fiwtew { c-candidate =>
        candidate.scowe > s-simcwustewsminscowe
      }

    s-stats.stat(simcwustewsannconfigid, >w< "simcwustewsanncandidates_size").add(fiwtewedcandidates.size)
    stats.countew(simcwustewsannconfigid, ^^ "simcwustewsannwequests").incw()
    if (fiwtewedcandidates.isempty)
      stats.countew(simcwustewsannconfigid, 😳😳😳 "emptyfiwtewedsimcwustewsanncandidates").incw()

    fiwtewedcandidates.map { candidate =>
      tweetwithscowe(candidate.tweetid, nyaa~~ candidate.scowe)
    }
  }

  /** a nyo-op fiwtew as utg fiwtew awweady happened at utg sewvice side */
  pwivate def usewtweetgwaphfiwtew(
    u-usewtweetgwaphcandidates: s-seq[tweetwithscowe]
  ): seq[tweetwithscowe] = {
    vaw fiwtewedcandidates = u-usewtweetgwaphcandidates

    s-stats.stat("usewtweetgwaphcandidates_size").add(usewtweetgwaphcandidates.size)
    i-if (fiwtewedcandidates.isempty) stats.countew("emptyfiwtewedusewtweetgwaphcandidates").incw()

    f-fiwtewedcandidates.map { candidate =>
      t-tweetwithscowe(candidate.tweetid, (⑅˘꒳˘) c-candidate.scowe)
    }
  }

}
object p-pwoducewbasedunifiedsimiwawityengine {

  /***
   * evewy candidate w-wiww have the c-cg info with pwoducewbasedunifiedsimiwawityengine
   * as they awe genewated by a-a composite of s-simiwawity engines. :3
   * a-additionawwy, ʘwʘ w-we stowe t-the contwibuting s-ses (eg., sann, rawr x3 u-utg).
   */
  p-pwivate def getpwoducewbasedunifiedcginfo(
    souwceinfoopt: o-option[souwceinfo], (///ˬ///✿)
    unifiedscowe: d-doubwe, 😳😳😳
    c-contwibutingsimiwawityengines: seq[simiwawityengineinfo]
  ): c-candidategenewationinfo = {
    candidategenewationinfo(
      s-souwceinfoopt, XD
      simiwawityengineinfo(
        simiwawityenginetype = s-simiwawityenginetype.pwoducewbasedunifiedsimiwawityengine, >_<
        modewid = n-nyone, >w< // we d-do nyot assign m-modewid fow a unified simiwawity e-engine
        scowe = some(unifiedscowe)
      ), /(^•ω•^)
      c-contwibutingsimiwawityengines
    )
  }

  case cwass q-quewy(
    souwceinfo: souwceinfo, :3
    m-maxcandidatenumpewsouwcekey: int, ʘwʘ
    maxtweetagehouws: duwation, (˘ω˘)
    // simcwustews
    enabwesimcwustewsann: boowean, (ꈍᴗꈍ)
    s-simcwustewsannquewy: enginequewy[simcwustewsannsimiwawityengine.quewy], ^^
    enabweexpewimentawsimcwustewsann: b-boowean, ^^
    expewimentawsimcwustewsannquewy: enginequewy[simcwustewsannsimiwawityengine.quewy], ( ͡o ω ͡o )
    e-enabwesimcwustewsann1: boowean, -.-
    simcwustewsann1quewy: enginequewy[simcwustewsannsimiwawityengine.quewy], ^^;;
    e-enabwesimcwustewsann2: boowean, ^•ﻌ•^
    s-simcwustewsann2quewy: e-enginequewy[simcwustewsannsimiwawityengine.quewy], (˘ω˘)
    e-enabwesimcwustewsann4: boowean, o.O
    simcwustewsann4quewy: enginequewy[simcwustewsannsimiwawityengine.quewy], (✿oωo)
    e-enabwesimcwustewsann3: b-boowean, 😳😳😳
    simcwustewsann3quewy: enginequewy[simcwustewsannsimiwawityengine.quewy], (ꈍᴗꈍ)
    e-enabwesimcwustewsann5: boowean, σωσ
    simcwustewsann5quewy: enginequewy[simcwustewsannsimiwawityengine.quewy], UwU
    s-simcwustewsminscowe: doubwe, ^•ﻌ•^
    // utg
    e-enabweutg: b-boowean, mya
    utgcombinationmethod: u-unifiedsetweetcombinationmethod.vawue, /(^•ω•^)
    utgquewy: enginequewy[pwoducewbasedusewtweetgwaphsimiwawityengine.quewy])

  d-def f-fwompawams(
    s-souwceinfo: souwceinfo, rawr
    p-pawams: configapi.pawams, nyaa~~
  ): e-enginequewy[quewy] = {
    v-vaw maxcandidatenumpewsouwcekey = p-pawams(gwobawpawams.maxcandidatenumpewsouwcekeypawam)
    v-vaw maxtweetagehouws = p-pawams(gwobawpawams.maxtweetagehouwspawam)
    // s-simcwustews
    v-vaw e-enabwesimcwustewsann = pawams(
      p-pwoducewbasedcandidategenewationpawams.enabwesimcwustewsannpawam)
    vaw simcwustewsmodewvewsion =
      modewvewsions.enum.enumtosimcwustewsmodewvewsionmap(pawams(gwobawpawams.modewvewsionpawam))
    vaw s-simcwustewsannconfigid = pawams(simcwustewsannpawams.simcwustewsannconfigid)
    // s-simcwustews - e-expewimentaw s-sann simiwawity engine
    vaw enabweexpewimentawsimcwustewsann = pawams(
      p-pwoducewbasedcandidategenewationpawams.enabweexpewimentawsimcwustewsannpawam)
    v-vaw expewimentawsimcwustewsannconfigid = p-pawams(
      simcwustewsannpawams.expewimentawsimcwustewsannconfigid)
    // simcwustews - sann cwustew 1 s-simiwawity e-engine
    vaw enabwesimcwustewsann1 = p-pawams(
      p-pwoducewbasedcandidategenewationpawams.enabwesimcwustewsann1pawam)
    vaw simcwustewsann1configid = pawams(simcwustewsannpawams.simcwustewsann1configid)
    // simcwustews - s-sann cwustew 2 s-simiwawity e-engine
    vaw e-enabwesimcwustewsann2 = pawams(
      pwoducewbasedcandidategenewationpawams.enabwesimcwustewsann2pawam)
    v-vaw s-simcwustewsann2configid = pawams(simcwustewsannpawams.simcwustewsann2configid)
    // simcwustews - s-sann cwustew 3 simiwawity engine
    vaw enabwesimcwustewsann3 = p-pawams(
      pwoducewbasedcandidategenewationpawams.enabwesimcwustewsann3pawam)
    v-vaw simcwustewsann3configid = p-pawams(simcwustewsannpawams.simcwustewsann3configid)
    // simcwustews - s-sann cwustew 5 s-simiwawity engine
    vaw enabwesimcwustewsann5 = p-pawams(
      pwoducewbasedcandidategenewationpawams.enabwesimcwustewsann5pawam)
    v-vaw simcwustewsann5configid = p-pawams(simcwustewsannpawams.simcwustewsann5configid)
    v-vaw enabwesimcwustewsann4 = p-pawams(
      pwoducewbasedcandidategenewationpawams.enabwesimcwustewsann4pawam)
    v-vaw simcwustewsann4configid = pawams(simcwustewsannpawams.simcwustewsann4configid)

    v-vaw simcwustewsminscowe = p-pawams(
      pwoducewbasedcandidategenewationpawams.simcwustewsminscowepawam)

    // s-simcwustews ann quewy
    vaw simcwustewsannquewy = s-simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, ( ͡o ω ͡o )
      e-embeddingtype.favbasedpwoducew, σωσ
      s-simcwustewsmodewvewsion, (✿oωo)
      simcwustewsannconfigid, (///ˬ///✿)
      pawams
    )
    vaw expewimentawsimcwustewsannquewy = simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, σωσ
      e-embeddingtype.favbasedpwoducew,
      simcwustewsmodewvewsion, UwU
      e-expewimentawsimcwustewsannconfigid, (⑅˘꒳˘)
      p-pawams
    )
    vaw simcwustewsann1quewy = simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, /(^•ω•^)
      e-embeddingtype.favbasedpwoducew, -.-
      simcwustewsmodewvewsion, (ˆ ﻌ ˆ)♡
      s-simcwustewsann1configid, nyaa~~
      p-pawams
    )
    v-vaw simcwustewsann2quewy = s-simcwustewsannsimiwawityengine.fwompawams(
      s-souwceinfo.intewnawid, ʘwʘ
      embeddingtype.favbasedpwoducew, :3
      simcwustewsmodewvewsion, (U ᵕ U❁)
      simcwustewsann2configid, (U ﹏ U)
      pawams
    )
    vaw simcwustewsann3quewy = s-simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, ^^
      e-embeddingtype.favbasedpwoducew, òωó
      simcwustewsmodewvewsion, /(^•ω•^)
      simcwustewsann3configid, 😳😳😳
      pawams
    )
    vaw s-simcwustewsann5quewy = simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, :3
      embeddingtype.favbasedpwoducew, (///ˬ///✿)
      simcwustewsmodewvewsion, rawr x3
      s-simcwustewsann5configid, (U ᵕ U❁)
      p-pawams
    )
    vaw s-simcwustewsann4quewy = simcwustewsannsimiwawityengine.fwompawams(
      souwceinfo.intewnawid, (⑅˘꒳˘)
      e-embeddingtype.favbasedpwoducew, (˘ω˘)
      s-simcwustewsmodewvewsion,
      simcwustewsann4configid, :3
      p-pawams
    )
    // utg
    v-vaw enabweutg = pawams(pwoducewbasedcandidategenewationpawams.enabweutgpawam)
    vaw utgcombinationmethod = pawams(
      p-pwoducewbasedcandidategenewationpawams.utgcombinationmethodpawam)

    enginequewy(
      quewy(
        s-souwceinfo = s-souwceinfo, XD
        m-maxcandidatenumpewsouwcekey = maxcandidatenumpewsouwcekey, >_<
        maxtweetagehouws = m-maxtweetagehouws, (✿oωo)
        enabwesimcwustewsann = enabwesimcwustewsann, (ꈍᴗꈍ)
        simcwustewsannquewy = simcwustewsannquewy, XD
        enabweexpewimentawsimcwustewsann = e-enabweexpewimentawsimcwustewsann, :3
        expewimentawsimcwustewsannquewy = e-expewimentawsimcwustewsannquewy,
        e-enabwesimcwustewsann1 = e-enabwesimcwustewsann1, mya
        simcwustewsann1quewy = simcwustewsann1quewy,
        e-enabwesimcwustewsann2 = e-enabwesimcwustewsann2, òωó
        simcwustewsann2quewy = simcwustewsann2quewy,
        e-enabwesimcwustewsann3 = enabwesimcwustewsann3, nyaa~~
        simcwustewsann3quewy = s-simcwustewsann3quewy,
        enabwesimcwustewsann5 = enabwesimcwustewsann5, 🥺
        s-simcwustewsann5quewy = s-simcwustewsann5quewy,
        enabwesimcwustewsann4 = e-enabwesimcwustewsann4, -.-
        s-simcwustewsann4quewy = s-simcwustewsann4quewy, 🥺
        simcwustewsminscowe = simcwustewsminscowe, (˘ω˘)
        e-enabweutg = enabweutg, òωó
        utgcombinationmethod = utgcombinationmethod, UwU
        u-utgquewy = pwoducewbasedusewtweetgwaphsimiwawityengine
          .fwompawams(souwceinfo.intewnawid, ^•ﻌ•^ pawams)
      ), mya
      pawams
    )
  }

  d-def f-fwompawamsfowwewatedtweet(
    intewnawid: i-intewnawid, (✿oωo)
    p-pawams: c-configapi.pawams
  ): enginequewy[quewy] = {
    v-vaw maxcandidatenumpewsouwcekey = pawams(gwobawpawams.maxcandidatenumpewsouwcekeypawam)
    vaw maxtweetagehouws = p-pawams(gwobawpawams.maxtweetagehouwspawam)
    // simcwustews
    v-vaw enabwesimcwustewsann = pawams(wewatedtweetpwoducewbasedpawams.enabwesimcwustewsannpawam)
    vaw simcwustewsmodewvewsion =
      m-modewvewsions.enum.enumtosimcwustewsmodewvewsionmap(pawams(gwobawpawams.modewvewsionpawam))
    v-vaw simcwustewsannconfigid = p-pawams(simcwustewsannpawams.simcwustewsannconfigid)
    vaw simcwustewsminscowe =
      p-pawams(wewatedtweetpwoducewbasedpawams.simcwustewsminscowepawam)
    // s-simcwustews - expewimentaw s-sann simiwawity e-engine
    vaw enabweexpewimentawsimcwustewsann = p-pawams(
      wewatedtweetpwoducewbasedpawams.enabweexpewimentawsimcwustewsannpawam)
    vaw expewimentawsimcwustewsannconfigid = pawams(
      s-simcwustewsannpawams.expewimentawsimcwustewsannconfigid)
    // simcwustews - s-sann cwustew 1 simiwawity engine
    vaw enabwesimcwustewsann1 = p-pawams(wewatedtweetpwoducewbasedpawams.enabwesimcwustewsann1pawam)
    v-vaw s-simcwustewsann1configid = pawams(simcwustewsannpawams.simcwustewsann1configid)
    // s-simcwustews - s-sann cwustew 2 simiwawity e-engine
    vaw enabwesimcwustewsann2 = pawams(wewatedtweetpwoducewbasedpawams.enabwesimcwustewsann2pawam)
    v-vaw simcwustewsann2configid = p-pawams(simcwustewsannpawams.simcwustewsann2configid)
    // s-simcwustews - sann cwustew 3 simiwawity engine
    vaw enabwesimcwustewsann3 = pawams(wewatedtweetpwoducewbasedpawams.enabwesimcwustewsann3pawam)
    v-vaw s-simcwustewsann3configid = pawams(simcwustewsannpawams.simcwustewsann3configid)
    // simcwustews - sann cwustew 5 s-simiwawity engine
    vaw enabwesimcwustewsann5 = p-pawams(wewatedtweetpwoducewbasedpawams.enabwesimcwustewsann5pawam)
    v-vaw simcwustewsann5configid = pawams(simcwustewsannpawams.simcwustewsann5configid)

    vaw enabwesimcwustewsann4 = pawams(wewatedtweetpwoducewbasedpawams.enabwesimcwustewsann4pawam)
    v-vaw simcwustewsann4configid = pawams(simcwustewsannpawams.simcwustewsann4configid)
    // buiwd sann quewy
    v-vaw simcwustewsannquewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid, XD
      e-embeddingtype.favbasedpwoducew, :3
      simcwustewsmodewvewsion, (U ﹏ U)
      s-simcwustewsannconfigid, UwU
      p-pawams
    )
    v-vaw expewimentawsimcwustewsannquewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid, ʘwʘ
      e-embeddingtype.favbasedpwoducew, >w<
      s-simcwustewsmodewvewsion, 😳😳😳
      expewimentawsimcwustewsannconfigid, rawr
      pawams
    )
    vaw simcwustewsann1quewy = simcwustewsannsimiwawityengine.fwompawams(
      intewnawid, ^•ﻌ•^
      e-embeddingtype.favbasedpwoducew, σωσ
      s-simcwustewsmodewvewsion, :3
      s-simcwustewsann1configid,
      p-pawams
    )
    v-vaw simcwustewsann2quewy = s-simcwustewsannsimiwawityengine.fwompawams(
      intewnawid, rawr x3
      embeddingtype.favbasedpwoducew, nyaa~~
      simcwustewsmodewvewsion, :3
      simcwustewsann2configid, >w<
      p-pawams
    )
    v-vaw simcwustewsann3quewy = simcwustewsannsimiwawityengine.fwompawams(
      intewnawid, rawr
      embeddingtype.favbasedpwoducew, 😳
      s-simcwustewsmodewvewsion, 😳
      s-simcwustewsann3configid, 🥺
      p-pawams
    )
    vaw simcwustewsann5quewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid, rawr x3
      embeddingtype.favbasedpwoducew, ^^
      simcwustewsmodewvewsion, ( ͡o ω ͡o )
      simcwustewsann5configid, XD
      p-pawams
    )
    v-vaw simcwustewsann4quewy = simcwustewsannsimiwawityengine.fwompawams(
      i-intewnawid,
      embeddingtype.favbasedpwoducew, ^^
      s-simcwustewsmodewvewsion, (⑅˘꒳˘)
      s-simcwustewsann4configid, (⑅˘꒳˘)
      pawams
    )
    // u-utg
    vaw e-enabweutg = pawams(wewatedtweetpwoducewbasedpawams.enabweutgpawam)
    v-vaw utgcombinationmethod = p-pawams(
      p-pwoducewbasedcandidategenewationpawams.utgcombinationmethodpawam)

    // s-souwcetype.wequestusewid is a pwacehowdew. ^•ﻌ•^
    v-vaw souwceinfo = s-souwceinfo(souwcetype.wequestusewid, ( ͡o ω ͡o ) intewnawid, ( ͡o ω ͡o ) nyone)

    e-enginequewy(
      quewy(
        souwceinfo = s-souwceinfo, (✿oωo)
        maxcandidatenumpewsouwcekey = m-maxcandidatenumpewsouwcekey, 😳😳😳
        maxtweetagehouws = m-maxtweetagehouws, OwO
        e-enabwesimcwustewsann = enabwesimcwustewsann, ^^
        simcwustewsannquewy = simcwustewsannquewy, rawr x3
        e-enabweexpewimentawsimcwustewsann = enabweexpewimentawsimcwustewsann, 🥺
        expewimentawsimcwustewsannquewy = expewimentawsimcwustewsannquewy, (ˆ ﻌ ˆ)♡
        e-enabwesimcwustewsann1 = e-enabwesimcwustewsann1, ( ͡o ω ͡o )
        simcwustewsann1quewy = simcwustewsann1quewy, >w<
        e-enabwesimcwustewsann2 = e-enabwesimcwustewsann2, /(^•ω•^)
        simcwustewsann2quewy = s-simcwustewsann2quewy, 😳😳😳
        enabwesimcwustewsann3 = enabwesimcwustewsann3, (U ᵕ U❁)
        s-simcwustewsann3quewy = s-simcwustewsann3quewy, (˘ω˘)
        enabwesimcwustewsann5 = e-enabwesimcwustewsann5, 😳
        s-simcwustewsann5quewy = simcwustewsann5quewy, (ꈍᴗꈍ)
        enabwesimcwustewsann4 = e-enabwesimcwustewsann4, :3
        s-simcwustewsann4quewy = s-simcwustewsann4quewy,
        s-simcwustewsminscowe = simcwustewsminscowe, /(^•ω•^)
        enabweutg = enabweutg, ^^;;
        utgquewy = pwoducewbasedusewtweetgwaphsimiwawityengine.fwompawams(intewnawid, o.O pawams), 😳
        u-utgcombinationmethod = u-utgcombinationmethod
      ), UwU
      p-pawams
    )
  }

}
