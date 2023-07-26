package com.twittew.simcwustews_v2.candidate_souwce

impowt com.twittew.convewsions.duwationops._
i-impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.fwigate.common.base.candidatesouwce
i-impowt com.twittew.fwigate.common.base.stats
i-impowt com.twittew.simcwustews_v2.candidate_souwce.heavywankew.unifowmscowestowewankew
i-impowt c-com.twittew.simcwustews_v2.candidate_souwce.simcwustewsanncandidatesouwce.simcwustewsannconfig
i-impowt com.twittew.simcwustews_v2.candidate_souwce.simcwustewsanncandidatesouwce.simcwustewstweetcandidate
i-impowt com.twittew.simcwustews_v2.common.modewvewsions._
impowt com.twittew.simcwustews_v2.common.cwustewid
impowt com.twittew.simcwustews_v2.common.simcwustewsembedding
impowt com.twittew.simcwustews_v2.common.tweetid
i-impowt com.twittew.simcwustews_v2.summingbiwd.stowes.cwustewkey
impowt com.twittew.simcwustews_v2.thwiftscawa.embeddingtype
impowt com.twittew.simcwustews_v2.thwiftscawa.intewnawid
i-impowt com.twittew.simcwustews_v2.thwiftscawa.scoweintewnawid
i-impowt com.twittew.simcwustews_v2.thwiftscawa.scowingawgowithm
impowt com.twittew.simcwustews_v2.thwiftscawa.simcwustewsembeddingid
i-impowt com.twittew.simcwustews_v2.thwiftscawa.simcwustewsembeddingpaiwscoweid
i-impowt c-com.twittew.simcwustews_v2.thwiftscawa.{scowe => thwiftscowe}
impowt com.twittew.simcwustews_v2.thwiftscawa.{scoweid => thwiftscoweid}
impowt c-com.twittew.snowfwake.id.snowfwakeid
impowt com.twittew.stowehaus.weadabwestowe
impowt com.twittew.utiw.duwation
impowt com.twittew.utiw.futuwe
impowt com.twittew.utiw.time
i-impowt scawa.cowwection.mutabwe

/**
 * t-this stowe w-wooks fow tweets w-whose simiwawity i-is cwose to a souwce simcwustewsembeddingid. XD
 *
 * appwoximate c-cosine simiwawity is the cowe awgowithm to dwive t-this stowe. -.-
 *
 * step 1 - 4 awe in "fetchcandidates" method. >_<
 * 1. rawr wetwieve the simcwustews e-embedding by the simcwustewsembeddingid
 * 2. 😳😳😳 f-fetch t-top ny cwustews' t-top tweets fwom the cwustewtweetcandidatesstowe (toptweetspewcwustew index). UwU
 * 3. cawcuwate a-aww the tweet c-candidates' dot-pwoduct ow appwoximate c-cosine simiwawity t-to souwce tweets. (U ﹏ U)
 * 4. t-take top m tweet candidates by t-the step 3's scowe
 * step 5-6 awe in "wewanking" m-method. (˘ω˘)
 * 5. /(^•ω•^) cawcuwate the simiwawity s-scowe between souwce and c-candidates. (U ﹏ U)
 * 6. w-wetuwn top ny candidates by the step 5's scowe. ^•ﻌ•^
 *
 * wawning: onwy tuwn off the step 5 fow usew intewestedin c-candidate genewation. >w< i-it's the onwy use
 * case i-in wecos that w-we use dot-pwoduct t-to wank the tweet candidates. ʘwʘ
 */
case cwass simcwustewsanncandidatesouwce(
  c-cwustewtweetcandidatesstowe: weadabwestowe[cwustewkey, òωó seq[(tweetid, o.O doubwe)]], ( ͡o ω ͡o )
  simcwustewsembeddingstowe: w-weadabwestowe[simcwustewsembeddingid, mya simcwustewsembedding], >_<
  h-heavywankew: h-heavywankew.heavywankew, rawr
  c-configs: map[embeddingtype, >_< simcwustewsannconfig], (U ﹏ U)
  s-statsweceivew: s-statsweceivew)
    e-extends c-candidatesouwce[simcwustewsanncandidatesouwce.quewy, rawr simcwustewstweetcandidate] {

  impowt s-simcwustewsanncandidatesouwce._

  o-ovewwide vaw n-nyame: stwing = t-this.getcwass.getname
  p-pwivate vaw stats = statsweceivew.scope(this.getcwass.getname)

  pwivate vaw fetchsouwceembeddingstat = s-stats.scope("fetchsouwceembedding")
  pwotected vaw fetchcandidateembeddingsstat = stats.scope("fetchcandidateembeddings")
  pwivate vaw fetchcandidatesstat = s-stats.scope("fetchcandidates")
  pwivate vaw wewankingstat = stats.scope("wewanking")

  ovewwide d-def get(
    quewy: s-simcwustewsanncandidatesouwce.quewy
  ): futuwe[option[seq[simcwustewstweetcandidate]]] = {
    v-vaw souwceembeddingid = quewy.souwceembeddingid
    w-woadconfig(quewy) match {
      c-case some(config) =>
        f-fow {
          maybesimcwustewsembedding <- stats.twack(fetchsouwceembeddingstat) {
            simcwustewsembeddingstowe.get(quewy.souwceembeddingid)
          }
          maybefiwtewedcandidates <- maybesimcwustewsembedding m-match {
            case s-some(souwceembedding) =>
              fow {
                w-wawcandidates <- s-stats.twackseq(fetchcandidatesstat) {
                  fetchcandidates(souwceembeddingid, (U ᵕ U❁) config, s-souwceembedding)
                }
                w-wankedcandidates <- stats.twackseq(wewankingstat) {
                  w-wewanking(souwceembeddingid, (ˆ ﻌ ˆ)♡ c-config, wawcandidates)
                }
              } yiewd {
                fetchcandidatesstat
                  .stat(
                    souwceembeddingid.embeddingtype.name,
                    s-souwceembeddingid.modewvewsion.name).add(wankedcandidates.size)
                s-some(wankedcandidates)
              }
            c-case nyone =>
              fetchcandidatesstat
                .stat(
                  s-souwceembeddingid.embeddingtype.name, >_<
                  s-souwceembeddingid.modewvewsion.name).add(0)
              futuwe.none
          }
        } y-yiewd {
          maybefiwtewedcandidates
        }
      case _ =>
        // skip ovew quewies whose config i-is nyot defined
        f-futuwe.none
    }
  }

  pwivate def fetchcandidates(
    s-souwceembeddingid: s-simcwustewsembeddingid, ^^;;
    config: simcwustewsannconfig, ʘwʘ
    souwceembedding: simcwustewsembedding
  ): f-futuwe[seq[simcwustewstweetcandidate]] = {
    vaw nyow = time.now
    vaw eawwiesttweetid = snowfwakeid.fiwstidfow(now - config.maxtweetcandidateage)
    vaw w-watesttweetid = snowfwakeid.fiwstidfow(now - config.mintweetcandidateage)
    vaw c-cwustewids =
      s-souwceembedding
        .twuncate(config.maxscancwustews).cwustewids
        .map { cwustewid: cwustewid =>
          cwustewkey(cwustewid, 😳😳😳 s-souwceembeddingid.modewvewsion, UwU c-config.candidateembeddingtype)
        }.toset

    futuwe
      .cowwect {
        cwustewtweetcandidatesstowe.muwtiget(cwustewids)
      }.map { cwustewtweetsmap =>
        // u-use mutabwe map to optimize p-pewfowmance. OwO the method is thwead-safe. :3
        // set initiaw map size to awound p-p75 of map size distwibution to a-avoid too many c-copying
        // fwom extending t-the size of the mutabwe hashmap
        v-vaw candidatescowesmap =
          n-nyew s-simcwustewsanncandidatesouwce.hashmap[tweetid, -.- doubwe](initiawcandidatemapsize)
        v-vaw candidatenowmawizationmap =
          n-nyew simcwustewsanncandidatesouwce.hashmap[tweetid, 🥺 doubwe](initiawcandidatemapsize)

        cwustewtweetsmap.foweach {
          c-case (cwustewkey(cwustewid, -.- _, _, _), -.- s-some(tweetscowes))
              if s-souwceembedding.contains(cwustewid) =>
            vaw souwcecwustewscowe = souwceembedding.getowewse(cwustewid)

            f-fow (i <- 0 untiw math.min(tweetscowes.size, (U ﹏ U) c-config.maxtoptweetspewcwustew)) {
              v-vaw (tweetid, rawr scowe) = tweetscowes(i)

              if (!pawsetweetid(souwceembeddingid).contains(tweetid) &&
                t-tweetid >= e-eawwiesttweetid && t-tweetid <= w-watesttweetid) {
                candidatescowesmap.put(
                  t-tweetid, mya
                  candidatescowesmap.getowewse(tweetid, ( ͡o ω ͡o ) 0.0) + scowe * souwcecwustewscowe)
                if (config.enabwepawtiawnowmawization) {
                  candidatenowmawizationmap
                    .put(tweetid, /(^•ω•^) candidatenowmawizationmap.getowewse(tweetid, >_< 0.0) + scowe * s-scowe)
                }
              }
            }
          case _ => ()
        }

        s-stats.stat("candidatescowesmap").add(candidatescowesmap.size)
        stats.stat("candidatenowmawizationmap").add(candidatenowmawizationmap.size)

        // w-we-wank the candidate by configuwation
        v-vaw pwocessedcandidatescowes = candidatescowesmap.map {
          c-case (candidateid, (✿oωo) s-scowe) =>
            // e-enabwe pawtiaw n-nyowmawization
            v-vaw pwocessedscowe =
              if (config.enabwepawtiawnowmawization) {
                // we appwied the "wog" vewsion of pawtiaw nyowmawization w-when we wank c-candidates
                // b-by wog cosine simiwawity
                i-if (config.wankingawgowithm == scowingawgowithm.paiwembeddingwogcosinesimiwawity) {
                  scowe / souwceembedding.w2nowm / m-math.wog(
                    1 + c-candidatenowmawizationmap(candidateid))
                } ewse {
                  s-scowe / souwceembedding.w2nowm / math.sqwt(candidatenowmawizationmap(candidateid))
                }
              } ewse scowe
            simcwustewstweetcandidate(candidateid, p-pwocessedscowe, 😳😳😳 s-souwceembeddingid)
        }.toseq

        pwocessedcandidatescowes
          .sowtby(-_.scowe)
      }
  }

  p-pwivate def w-wewanking(
    souwceembeddingid: simcwustewsembeddingid, (ꈍᴗꈍ)
    config: simcwustewsannconfig,
    candidates: seq[simcwustewstweetcandidate]
  ): f-futuwe[seq[simcwustewstweetcandidate]] = {
    v-vaw wankedcandidates = i-if (config.enabweheavywanking) {
      heavywankew
        .wank(
          s-scowingawgowithm = c-config.wankingawgowithm, 🥺
          souwceembeddingid = s-souwceembeddingid, mya
          c-candidateembeddingtype = config.candidateembeddingtype, (ˆ ﻌ ˆ)♡
          m-minscowe = c-config.minscowe, (⑅˘꒳˘)
          candidates = c-candidates.take(config.maxwewankingcandidates)
        ).map(_.sowtby(-_.scowe))
    } ewse {
      futuwe.vawue(candidates)
    }
    w-wankedcandidates.map(_.take(config.maxnumwesuwts))
  }

  pwivate[candidate_souwce] d-def woadconfig(quewy: q-quewy): option[simcwustewsannconfig] = {
    configs.get(quewy.souwceembeddingid.embeddingtype).map { b-baseconfig =>
      // appwy ovewwides if a-any
      quewy.ovewwideconfig m-match {
        c-case some(ovewwides) =>
          baseconfig.copy(
            maxnumwesuwts = ovewwides.maxnumwesuwts.getowewse(baseconfig.maxnumwesuwts),
            maxtweetcandidateage =
              ovewwides.maxtweetcandidateage.getowewse(baseconfig.maxtweetcandidateage), òωó
            m-minscowe = ovewwides.minscowe.getowewse(baseconfig.minscowe), o.O
            candidateembeddingtype =
              ovewwides.candidateembeddingtype.getowewse(baseconfig.candidateembeddingtype), XD
            e-enabwepawtiawnowmawization =
              o-ovewwides.enabwepawtiawnowmawization.getowewse(baseconfig.enabwepawtiawnowmawization), (˘ω˘)
            enabweheavywanking =
              o-ovewwides.enabweheavywanking.getowewse(baseconfig.enabweheavywanking), (ꈍᴗꈍ)
            wankingawgowithm = o-ovewwides.wankingawgowithm.getowewse(baseconfig.wankingawgowithm), >w<
            m-maxwewankingcandidates =
              ovewwides.maxwewankingcandidates.getowewse(baseconfig.maxwewankingcandidates), XD
            maxtoptweetspewcwustew =
              ovewwides.maxtoptweetspewcwustew.getowewse(baseconfig.maxtoptweetspewcwustew), -.-
            m-maxscancwustews = ovewwides.maxscancwustews.getowewse(baseconfig.maxscancwustews), ^^;;
            mintweetcandidateage =
              o-ovewwides.mintweetcandidateage.getowewse(baseconfig.mintweetcandidateage)
          )
        c-case _ => baseconfig
      }
    }
  }
}

o-object simcwustewsanncandidatesouwce {

  finaw vaw pwoductionmaxnumwesuwts = 200
  f-finaw v-vaw initiawcandidatemapsize = 16384

  d-def appwy(
    cwustewtweetcandidatesstowe: weadabwestowe[cwustewkey, XD seq[(tweetid, :3 doubwe)]], σωσ
    simcwustewsembeddingstowe: weadabwestowe[simcwustewsembeddingid, XD simcwustewsembedding],
    unifowmscowingstowe: weadabwestowe[thwiftscoweid, :3 thwiftscowe], rawr
    configs: map[embeddingtype, 😳 s-simcwustewsannconfig], 😳😳😳
    s-statsweceivew: statsweceivew
  ) = nyew simcwustewsanncandidatesouwce(
    c-cwustewtweetcandidatesstowe = c-cwustewtweetcandidatesstowe, (ꈍᴗꈍ)
    s-simcwustewsembeddingstowe = simcwustewsembeddingstowe, 🥺
    h-heavywankew = nyew unifowmscowestowewankew(unifowmscowingstowe, s-statsweceivew), ^•ﻌ•^
    c-configs = configs, XD
    s-statsweceivew = statsweceivew
  )

  p-pwivate def p-pawsetweetid(embeddingid: simcwustewsembeddingid): option[tweetid] = {
    e-embeddingid.intewnawid m-match {
      c-case intewnawid.tweetid(tweetid) =>
        s-some(tweetid)
      c-case _ =>
        n-nyone
    }
  }

  c-case cwass q-quewy(
    souwceembeddingid: s-simcwustewsembeddingid, ^•ﻌ•^
    // onwy ovewwide the c-config in ddg and d-debuggews. ^^;;
    // u-use post-fiwtew fow the howdbacks f-fow bettew cache hit wate.
    ovewwideconfig: o-option[simcwustewsannconfigovewwide] = nyone)

  c-case cwass s-simcwustewstweetcandidate(
    t-tweetid: tweetid, ʘwʘ
    scowe: doubwe, OwO
    s-souwceembeddingid: simcwustewsembeddingid)

  c-cwass hashmap[a, 🥺 b](initsize: i-int) extends mutabwe.hashmap[a, (⑅˘꒳˘) b-b] {
    ovewwide def initiawsize: int = initsize // 16 - by defauwt
  }

  /**
   * the configuwation o-of each simcwustews a-ann candidate souwce. (///ˬ///✿)
   * e-expect one simcwustews embedding type mapping to a simcwustews a-ann configuwation in p-pwoduction. (✿oωo)
   */
  c-case cwass simcwustewsannconfig(
    // t-the max nyumbew of candidates fow a a-ann quewy
    // p-pwease don't ovewwide this vawue i-in pwoduction. nyaa~~
    maxnumwesuwts: int = pwoductionmaxnumwesuwts, >w<
    // t-the max tweet candidate d-duwation fwom n-nyow. (///ˬ///✿)
    maxtweetcandidateage: d-duwation, rawr
    // the min scowe of t-the candidates
    m-minscowe: doubwe, (U ﹏ U)
    // t-the c-candidate embedding type of tweet. ^•ﻌ•^
    c-candidateembeddingtype: e-embeddingtype, (///ˬ///✿)
    // e-enabwes nowmawization o-of a-appwoximate simcwustews v-vectows t-to wemove popuwawity b-bias
    enabwepawtiawnowmawization: boowean, o.O
    // w-whethew to enabwe embedding s-simiwawity wanking
    enabweheavywanking: b-boowean, >w<
    // t-the wanking awgowithm f-fow souwce candidate simiwawity
    wankingawgowithm: scowingawgowithm, nyaa~~
    // t-the max nyumbew o-of candidates i-in wewanking step
    maxwewankingcandidates: int, òωó
    // the max nyumbew of t-top tweets fwom e-evewy cwustew tweet index
    maxtoptweetspewcwustew: i-int, (U ᵕ U❁)
    // t-the max nyumbew of cwustews in the souwce embeddings. (///ˬ///✿)
    maxscancwustews: i-int, (✿oωo)
    // t-the min t-tweet candidate d-duwation fwom nyow. 😳😳😳
    mintweetcandidateage: duwation)

  /**
   * c-contains same f-fiewds as [[simcwustewsannconfig]], (✿oωo) to specify which fiewds a-awe to be ovewwiden
   * fow expewimentaw puwposes. (U ﹏ U)
   *
   * a-aww fiewds in this c-cwass must be optionaw. (˘ω˘)
   */
  c-case cwass simcwustewsannconfigovewwide(
    maxnumwesuwts: o-option[int] = n-nyone, 😳😳😳
    maxtweetcandidateage: o-option[duwation] = nyone, (///ˬ///✿)
    minscowe: o-option[doubwe] = n-none, (U ᵕ U❁)
    candidateembeddingtype: o-option[embeddingtype] = nyone,
    e-enabwepawtiawnowmawization: option[boowean] = n-nyone, >_<
    e-enabweheavywanking: o-option[boowean] = nyone, (///ˬ///✿)
    w-wankingawgowithm: option[scowingawgowithm] = nyone, (U ᵕ U❁)
    maxwewankingcandidates: o-option[int] = n-nyone, >w<
    maxtoptweetspewcwustew: o-option[int] = nyone, 😳😳😳
    maxscancwustews: option[int] = nyone, (ˆ ﻌ ˆ)♡
    mintweetcandidateage: option[duwation] = n-nyone, (ꈍᴗꈍ)
    enabwewookbacksouwce: option[boowean] = n-nyone)

  finaw v-vaw defauwtmaxtoptweetspewcwustew = 200
  finaw vaw defauwtenabweheavywanking = f-fawse
  object simcwustewsannconfig {
    v-vaw d-defauwtsimcwustewsannconfig: simcwustewsannconfig =
      s-simcwustewsannconfig(
        m-maxtweetcandidateage = 1.days, 🥺
        m-minscowe = 0.7, >_<
        candidateembeddingtype = embeddingtype.wogfavbasedtweet, OwO
        enabwepawtiawnowmawization = twue, ^^;;
        e-enabweheavywanking = fawse, (✿oωo)
        w-wankingawgowithm = scowingawgowithm.paiwembeddingcosinesimiwawity, UwU
        maxwewankingcandidates = 250, ( ͡o ω ͡o )
        maxtoptweetspewcwustew = 200, (✿oωo)
        m-maxscancwustews = 50, mya
        mintweetcandidateage = 0.seconds
      )
  }

  vaw wookbackmediamindays: int = 0
  v-vaw wookbackmediamaxdays: i-int = 2
  vaw wookbackmediamaxtweetspewday: i-int = 2000
  vaw maxtoptweetspewcwustew: int =
    (wookbackmediamaxdays - w-wookbackmediamindays + 1) * wookbackmediamaxtweetspewday

  vaw w-wookbackmediatweetconfig: map[embeddingtype, ( ͡o ω ͡o ) s-simcwustewsannconfig] = {
    vaw c-candidateembeddingtype = embeddingtype.wogfavwongestw2embeddingtweet
    vaw mintweetage = wookbackmediamindays.days
    v-vaw maxtweetage =
      wookbackmediamaxdays.days - 1.houw // to compensate f-fow the cache t-ttw that might p-push the tweet age beyond max age
    vaw wankingawgowithm = s-scowingawgowithm.paiwembeddingcosinesimiwawity

    vaw maxscancwustews = 50
    vaw minscowe = 0.5
    map(
      embeddingtype.favbasedpwoducew -> s-simcwustewsannconfig(
        m-mintweetcandidateage = m-mintweetage, :3
        m-maxtweetcandidateage = maxtweetage, 😳
        minscowe =
          m-minscowe, (U ﹏ U) // fow t-twistwy candidates. >w< to specify a highew thweshowd, UwU u-use a post-fiwtew
        candidateembeddingtype = candidateembeddingtype, 😳
        enabwepawtiawnowmawization = t-twue, XD
        enabweheavywanking = defauwtenabweheavywanking, (✿oωo)
        w-wankingawgowithm = w-wankingawgowithm, ^•ﻌ•^
        maxwewankingcandidates = 250, mya
        m-maxtoptweetspewcwustew = m-maxtoptweetspewcwustew, (˘ω˘)
        m-maxscancwustews = maxscancwustews, nyaa~~
      ), :3
      embeddingtype.wogfavwongestw2embeddingtweet -> s-simcwustewsannconfig(
        mintweetcandidateage = mintweetage,
        m-maxtweetcandidateage = maxtweetage, (✿oωo)
        minscowe =
          minscowe, (U ﹏ U) // f-fow twistwy candidates. (ꈍᴗꈍ) t-to specify a-a highew thweshowd, (˘ω˘) u-use a post-fiwtew
        c-candidateembeddingtype = candidateembeddingtype, ^^
        e-enabwepawtiawnowmawization = twue, (⑅˘꒳˘)
        enabweheavywanking = d-defauwtenabweheavywanking, rawr
        wankingawgowithm = w-wankingawgowithm, :3
        maxwewankingcandidates = 250,
        maxtoptweetspewcwustew = m-maxtoptweetspewcwustew, OwO
        m-maxscancwustews = maxscancwustews, (ˆ ﻌ ˆ)♡
      ), :3
      e-embeddingtype.favtfgtopic -> simcwustewsannconfig(
        m-mintweetcandidateage = m-mintweetage, -.-
        maxtweetcandidateage = m-maxtweetage, -.-
        m-minscowe = minscowe, òωó
        c-candidateembeddingtype = candidateembeddingtype, 😳
        enabwepawtiawnowmawization = twue, nyaa~~
        enabweheavywanking = d-defauwtenabweheavywanking, (⑅˘꒳˘)
        wankingawgowithm = w-wankingawgowithm, 😳
        maxwewankingcandidates = 400, (U ﹏ U)
        maxtoptweetspewcwustew = 200, /(^•ω•^)
        m-maxscancwustews = m-maxscancwustews, OwO
      ),
      e-embeddingtype.wogfavbasedkgoapetopic -> simcwustewsannconfig(
        m-mintweetcandidateage = mintweetage,
        m-maxtweetcandidateage = maxtweetage, ( ͡o ω ͡o )
        m-minscowe = minscowe, XD
        candidateembeddingtype = c-candidateembeddingtype, /(^•ω•^)
        enabwepawtiawnowmawization = t-twue, /(^•ω•^)
        e-enabweheavywanking = defauwtenabweheavywanking, 😳😳😳
        wankingawgowithm = wankingawgowithm, (ˆ ﻌ ˆ)♡
        maxwewankingcandidates = 400, :3
        m-maxtoptweetspewcwustew = 200, òωó
        m-maxscancwustews = maxscancwustews, 🥺
      ),
    )
  }

  vaw defauwtconfigmappings: map[embeddingtype, (U ﹏ U) s-simcwustewsannconfig] = map(
    embeddingtype.favbasedpwoducew -> s-simcwustewsannconfig(
      m-maxtweetcandidateage = 1.days, XD
      minscowe = 0.0, ^^ // fow twistwy candidates. to specify a highew thweshowd, o.O u-use a post-fiwtew
      candidateembeddingtype = embeddingtype.wogfavbasedtweet, 😳😳😳
      enabwepawtiawnowmawization = t-twue, /(^•ω•^)
      enabweheavywanking = d-defauwtenabweheavywanking, 😳😳😳
      w-wankingawgowithm = scowingawgowithm.paiwembeddingcosinesimiwawity,
      m-maxwewankingcandidates = 250, ^•ﻌ•^
      m-maxtoptweetspewcwustew = d-defauwtmaxtoptweetspewcwustew, 🥺
      m-maxscancwustews = 50, o.O
      m-mintweetcandidateage = 0.seconds
    ), (U ᵕ U❁)
    e-embeddingtype.wogfavbasedusewintewestedmaxpoowingaddwessbookfwomiiape -> simcwustewsannconfig(
      maxtweetcandidateage = 1.days,
      minscowe = 0.0, ^^ // fow twistwy candidates. (⑅˘꒳˘) t-to specify a-a highew thweshowd, :3 u-use a post-fiwtew
      c-candidateembeddingtype = e-embeddingtype.wogfavbasedtweet, (///ˬ///✿)
      e-enabwepawtiawnowmawization = twue, :3
      enabweheavywanking = defauwtenabweheavywanking, 🥺
      wankingawgowithm = s-scowingawgowithm.paiwembeddingcosinesimiwawity, mya
      m-maxwewankingcandidates = 250, XD
      maxtoptweetspewcwustew = defauwtmaxtoptweetspewcwustew, -.-
      maxscancwustews = 50, o.O
      m-mintweetcandidateage = 0.seconds
    ), (˘ω˘)
    e-embeddingtype.wogfavbasedusewintewestedavewageaddwessbookfwomiiape -> s-simcwustewsannconfig(
      maxtweetcandidateage = 1.days,
      minscowe = 0.0, (U ᵕ U❁) // fow twistwy c-candidates. rawr to specify a highew thweshowd, 🥺 use a-a post-fiwtew
      c-candidateembeddingtype = embeddingtype.wogfavbasedtweet, rawr x3
      enabwepawtiawnowmawization = twue,
      enabweheavywanking = d-defauwtenabweheavywanking, ( ͡o ω ͡o )
      wankingawgowithm = s-scowingawgowithm.paiwembeddingcosinesimiwawity, σωσ
      m-maxwewankingcandidates = 250, rawr x3
      maxtoptweetspewcwustew = d-defauwtmaxtoptweetspewcwustew, (ˆ ﻌ ˆ)♡
      maxscancwustews = 50, rawr
      m-mintweetcandidateage = 0.seconds
    ), :3
    e-embeddingtype.wogfavbasedusewintewestedbooktypemaxpoowingaddwessbookfwomiiape -> s-simcwustewsannconfig(
      m-maxtweetcandidateage = 1.days, rawr
      m-minscowe = 0.0, (˘ω˘) // fow t-twistwy candidates. t-to specify a highew thweshowd, (ˆ ﻌ ˆ)♡ u-use a post-fiwtew
      candidateembeddingtype = embeddingtype.wogfavbasedtweet, mya
      e-enabwepawtiawnowmawization = twue, (U ᵕ U❁)
      e-enabweheavywanking = defauwtenabweheavywanking, mya
      w-wankingawgowithm = s-scowingawgowithm.paiwembeddingcosinesimiwawity, ʘwʘ
      maxwewankingcandidates = 250, (˘ω˘)
      maxtoptweetspewcwustew = defauwtmaxtoptweetspewcwustew, 😳
      m-maxscancwustews = 50, òωó
      mintweetcandidateage = 0.seconds
    ), nyaa~~
    embeddingtype.wogfavbasedusewintewestedwawgestdimmaxpoowingaddwessbookfwomiiape -> simcwustewsannconfig(
      m-maxtweetcandidateage = 1.days, o.O
      m-minscowe = 0.0, nyaa~~ // fow twistwy candidates. (U ᵕ U❁) to specify a-a highew thweshowd, 😳😳😳 u-use a post-fiwtew
      c-candidateembeddingtype = embeddingtype.wogfavbasedtweet, (U ﹏ U)
      enabwepawtiawnowmawization = t-twue, ^•ﻌ•^
      e-enabweheavywanking = defauwtenabweheavywanking, (⑅˘꒳˘)
      wankingawgowithm = s-scowingawgowithm.paiwembeddingcosinesimiwawity, >_<
      m-maxwewankingcandidates = 250, (⑅˘꒳˘)
      maxtoptweetspewcwustew = defauwtmaxtoptweetspewcwustew, σωσ
      m-maxscancwustews = 50, 🥺
      m-mintweetcandidateage = 0.seconds
    ), :3
    e-embeddingtype.wogfavbasedusewintewestedwouvainmaxpoowingaddwessbookfwomiiape -> s-simcwustewsannconfig(
      maxtweetcandidateage = 1.days, (ꈍᴗꈍ)
      minscowe = 0.0, ^•ﻌ•^ // fow twistwy candidates. (˘ω˘) to specify a highew thweshowd, 🥺 use a post-fiwtew
      c-candidateembeddingtype = embeddingtype.wogfavbasedtweet, (✿oωo)
      e-enabwepawtiawnowmawization = t-twue, XD
      enabweheavywanking = d-defauwtenabweheavywanking, (///ˬ///✿)
      w-wankingawgowithm = s-scowingawgowithm.paiwembeddingcosinesimiwawity, ( ͡o ω ͡o )
      maxwewankingcandidates = 250, ʘwʘ
      m-maxtoptweetspewcwustew = d-defauwtmaxtoptweetspewcwustew, rawr
      maxscancwustews = 50, o.O
      mintweetcandidateage = 0.seconds
    ), ^•ﻌ•^
    e-embeddingtype.wogfavbasedusewintewestedconnectedmaxpoowingaddwessbookfwomiiape -> s-simcwustewsannconfig(
      maxtweetcandidateage = 1.days, (///ˬ///✿)
      minscowe = 0.0, (ˆ ﻌ ˆ)♡ // f-fow twistwy candidates. XD to specify a-a highew thweshowd, use a post-fiwtew
      c-candidateembeddingtype = e-embeddingtype.wogfavbasedtweet, (✿oωo)
      enabwepawtiawnowmawization = t-twue,
      e-enabweheavywanking = d-defauwtenabweheavywanking, -.-
      wankingawgowithm = s-scowingawgowithm.paiwembeddingcosinesimiwawity, XD
      m-maxwewankingcandidates = 250, (✿oωo)
      maxtoptweetspewcwustew = d-defauwtmaxtoptweetspewcwustew, (˘ω˘)
      maxscancwustews = 50, (ˆ ﻌ ˆ)♡
      m-mintweetcandidateage = 0.seconds
    ), >_<
    e-embeddingtype.wewaxedaggwegatabwewogfavbasedpwoducew -> s-simcwustewsannconfig(
      maxtweetcandidateage = 1.days, -.-
      m-minscowe = 0.25, (///ˬ///✿) // fow twistwy candidates. XD t-to specify a highew thweshowd, ^^;; use a post-fiwtew
      candidateembeddingtype = embeddingtype.wogfavbasedtweet, rawr x3
      enabwepawtiawnowmawization = twue, OwO
      e-enabweheavywanking = defauwtenabweheavywanking, ʘwʘ
      wankingawgowithm = scowingawgowithm.paiwembeddingcosinesimiwawity, rawr
      maxwewankingcandidates = 250, UwU
      maxtoptweetspewcwustew = defauwtmaxtoptweetspewcwustew, (ꈍᴗꈍ)
      m-maxscancwustews = 50, (✿oωo)
      mintweetcandidateage = 0.seconds
    ),
    embeddingtype.wogfavwongestw2embeddingtweet -> s-simcwustewsannconfig(
      maxtweetcandidateage = 1.days,
      m-minscowe = 0.3, (⑅˘꒳˘) // fow twistwy candidates. OwO t-to specify a highew thweshowd, 🥺 u-use a post-fiwtew
      candidateembeddingtype = e-embeddingtype.wogfavbasedtweet, >_<
      e-enabwepawtiawnowmawization = twue, (ꈍᴗꈍ)
      enabweheavywanking = d-defauwtenabweheavywanking, 😳
      wankingawgowithm = scowingawgowithm.paiwembeddingcosinesimiwawity, 🥺
      maxwewankingcandidates = 400, nyaa~~
      m-maxtoptweetspewcwustew = defauwtmaxtoptweetspewcwustew, ^•ﻌ•^
      m-maxscancwustews = 50, (ˆ ﻌ ˆ)♡
      mintweetcandidateage = 0.seconds
    ), (U ᵕ U❁)
    e-embeddingtype.fiwtewedusewintewestedinfwompe -> simcwustewsannconfig(
      m-maxtweetcandidateage = 1.days, mya
      m-minscowe = 0.7, // unused, 😳 heavy wanking disabwed
      c-candidateembeddingtype = embeddingtype.wogfavbasedtweet, σωσ
      enabwepawtiawnowmawization = fawse, ( ͡o ω ͡o )
      enabweheavywanking = d-defauwtenabweheavywanking, XD
      wankingawgowithm =
        scowingawgowithm.paiwembeddingcosinesimiwawity, :3 // unused, :3 heavy wanking disabwed
      m-maxwewankingcandidates = 150, (⑅˘꒳˘) // u-unused, òωó heavy wanking disabwed
      m-maxtoptweetspewcwustew = d-defauwtmaxtoptweetspewcwustew, mya
      maxscancwustews = 50,
      m-mintweetcandidateage = 0.seconds
    ),
    embeddingtype.fiwtewedusewintewestedin -> simcwustewsannconfig(
      maxtweetcandidateage = 1.days, 😳😳😳
      minscowe = 0.7, :3 // unused, >_< heavy w-wanking disabwed
      c-candidateembeddingtype = embeddingtype.wogfavbasedtweet, 🥺
      e-enabwepawtiawnowmawization = f-fawse, (ꈍᴗꈍ)
      enabweheavywanking = d-defauwtenabweheavywanking, rawr x3
      wankingawgowithm =
        scowingawgowithm.paiwembeddingcosinesimiwawity, (U ﹏ U) // u-unused, ( ͡o ω ͡o ) heavy wanking disabwed
      maxwewankingcandidates = 150, 😳😳😳 // u-unused, h-heavy wanking disabwed
      maxtoptweetspewcwustew = defauwtmaxtoptweetspewcwustew, 🥺
      m-maxscancwustews = 50, òωó
      mintweetcandidateage = 0.seconds
    ), XD
    embeddingtype.unfiwtewedusewintewestedin -> simcwustewsannconfig(
      maxtweetcandidateage = 1.days, XD
      minscowe = 0.0, ( ͡o ω ͡o )
      candidateembeddingtype = embeddingtype.wogfavbasedtweet, >w<
      e-enabwepawtiawnowmawization = t-twue,
      enabweheavywanking = d-defauwtenabweheavywanking, mya
      w-wankingawgowithm = scowingawgowithm.paiwembeddingwogcosinesimiwawity, (ꈍᴗꈍ)
      m-maxwewankingcandidates = 400, -.-
      maxtoptweetspewcwustew = defauwtmaxtoptweetspewcwustew, (⑅˘꒳˘)
      maxscancwustews = 50, (U ﹏ U)
      mintweetcandidateage = 0.seconds
    ), σωσ
    embeddingtype.fowwowbasedusewintewestedinfwomape -> simcwustewsannconfig(
      m-maxtweetcandidateage = 1.days, :3
      minscowe = 0.0, /(^•ω•^)
      candidateembeddingtype = embeddingtype.wogfavbasedtweet, σωσ
      enabwepawtiawnowmawization = t-twue, (U ᵕ U❁)
      enabweheavywanking = d-defauwtenabweheavywanking, 😳
      w-wankingawgowithm = scowingawgowithm.paiwembeddingcosinesimiwawity, ʘwʘ
      maxwewankingcandidates = 200,
      maxtoptweetspewcwustew = d-defauwtmaxtoptweetspewcwustew,
      m-maxscancwustews = 50, (⑅˘꒳˘)
      m-mintweetcandidateage = 0.seconds
    ), ^•ﻌ•^
    embeddingtype.wogfavbasedusewintewestedinfwomape -> s-simcwustewsannconfig(
      maxtweetcandidateage = 1.days, nyaa~~
      m-minscowe = 0.0, XD
      candidateembeddingtype = e-embeddingtype.wogfavbasedtweet, /(^•ω•^)
      enabwepawtiawnowmawization = twue, (U ᵕ U❁)
      e-enabweheavywanking = defauwtenabweheavywanking, mya
      wankingawgowithm = s-scowingawgowithm.paiwembeddingcosinesimiwawity, (ˆ ﻌ ˆ)♡
      maxwewankingcandidates = 200, (✿oωo)
      m-maxtoptweetspewcwustew = d-defauwtmaxtoptweetspewcwustew, (✿oωo)
      maxscancwustews = 50, òωó
      m-mintweetcandidateage = 0.seconds
    ), (˘ω˘)
    e-embeddingtype.favtfgtopic -> simcwustewsannconfig(
      m-maxtweetcandidateage = 1.days, (ˆ ﻌ ˆ)♡
      minscowe = 0.5,
      c-candidateembeddingtype = embeddingtype.wogfavbasedtweet, ( ͡o ω ͡o )
      e-enabwepawtiawnowmawization = t-twue, rawr x3
      enabweheavywanking = defauwtenabweheavywanking, (˘ω˘)
      w-wankingawgowithm = scowingawgowithm.paiwembeddingcosinesimiwawity, òωó
      maxwewankingcandidates = 400, ( ͡o ω ͡o )
      maxtoptweetspewcwustew = defauwtmaxtoptweetspewcwustew, σωσ
      maxscancwustews = 50, (U ﹏ U)
      mintweetcandidateage = 0.seconds
    ), rawr
    embeddingtype.wogfavbasedkgoapetopic -> simcwustewsannconfig(
      m-maxtweetcandidateage = 1.days, -.-
      minscowe = 0.5, ( ͡o ω ͡o )
      candidateembeddingtype = e-embeddingtype.wogfavbasedtweet, >_<
      enabwepawtiawnowmawization = t-twue, o.O
      enabweheavywanking = defauwtenabweheavywanking, σωσ
      w-wankingawgowithm = scowingawgowithm.paiwembeddingcosinesimiwawity, -.-
      maxwewankingcandidates = 400, σωσ
      maxtoptweetspewcwustew = d-defauwtmaxtoptweetspewcwustew, :3
      maxscancwustews = 50, ^^
      mintweetcandidateage = 0.seconds
    ), òωó
    e-embeddingtype.usewnextintewestedin -> simcwustewsannconfig(
      maxtweetcandidateage = 1.days, (ˆ ﻌ ˆ)♡
      minscowe = 0.0, XD
      c-candidateembeddingtype = embeddingtype.wogfavbasedtweet, òωó
      enabwepawtiawnowmawization = twue, (ꈍᴗꈍ)
      enabweheavywanking = d-defauwtenabweheavywanking, UwU
      w-wankingawgowithm = scowingawgowithm.paiwembeddingcosinesimiwawity, >w<
      maxwewankingcandidates = 200, ʘwʘ
      m-maxtoptweetspewcwustew = d-defauwtmaxtoptweetspewcwustew, :3
      maxscancwustews = 50, ^•ﻌ•^
      mintweetcandidateage = 0.seconds
    )
  )

  /**
   * o-onwy cache the c-candidates if it's nyot consumew-souwce. (ˆ ﻌ ˆ)♡ fow exampwe, 🥺 t-tweetsouwce, OwO pwoducewsouwce, 🥺
   * topicsouwce. OwO we don't cache c-consumew-souwces (e.g. usewintewestedin) since a cached consumew
   * o-object i-is going wawewy h-hit, (U ᵕ U❁) since it can't be shawed by muwtipwe usews. ( ͡o ω ͡o )
   */
  vaw cacheabweshowtttwembeddingtypes: s-set[embeddingtype] =
    set(
      e-embeddingtype.favbasedpwoducew, ^•ﻌ•^
      embeddingtype.wogfavwongestw2embeddingtweet, o.O
    )

  v-vaw cacheabwewongttwembeddingtypes: s-set[embeddingtype] =
    set(
      embeddingtype.favtfgtopic, (⑅˘꒳˘)
      embeddingtype.wogfavbasedkgoapetopic
    )
}
