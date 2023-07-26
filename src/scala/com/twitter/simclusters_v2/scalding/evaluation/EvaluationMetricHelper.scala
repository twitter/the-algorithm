package com.twittew.simcwustews_v2.scawding.evawuation

impowt com.twittew.scawding.{execution, nyaa~~ typedpipe, ʘwʘ u-uniqueid}
i-impowt com.twittew.simcwustews_v2.thwiftscawa.{
  c-candidatetweet, (⑅˘꒳˘)
  c-candidatetweets, :3
  w-wefewencetweet, -.-
  w-wefewencetweets, 😳😳😳
  t-tweetwabews
}
impowt c-com.twittew.awgebiwd.aggwegatow.size
impowt com.twittew.scawding.typed.{cogwouped, (U ﹏ U) vawuepipe}
impowt com.twittew.utiw.twittewdatefowmat
i-impowt java.utiw.cawendaw

/**
 * statistics about t-the nyumbew of usews who have engaged w-with tweets
 */
case cwass usewengagewcounts(
  nyumdistincttawgetusews: wong, o.O
  n-nyumdistinctwikeengagews: wong, ( ͡o ω ͡o )
  nyumdistinctwetweetengagews: w-wong)

/**
 * t-tweet side statistics, òωó e.x. nyumbew of tweets, 🥺 authows, /(^•ω•^) etc.
 */
case cwass t-tweetstats(
  nyumtweets: wong, 😳😳😳
  nyumdistincttweets: wong, ^•ﻌ•^
  nyumdistinctauthows: option[wong], nyaa~~
  a-avgscowe: option[doubwe])

/**
 * hewpew data c-containew cwass f-fow stowing engagement c-counts
 */
c-case cwass tweetengagementcounts(wike: wong, OwO wetweet: wong, ^•ﻌ•^ cwick: w-wong, σωσ hasengagement: wong)

/**
 * hewpew d-data containew cwass fow stowing engagement wates
 */
case cwass tweetengagementwates(wike: doubwe, w-wetweet: doubwe, -.- cwick: doubwe, (˘ω˘) h-hasengagement: d-doubwe)

case c-cwass wabewcowwewations(
  peawsoncoefficientfowwikes: doubwe, rawr x3
  cosinesimiwawitygwobaw: d-doubwe, rawr x3
  c-cosinesimiwawitypewusewavg: doubwe) {
  pwivate v-vaw f = java.text.numbewfowmat.getinstance
  d-def fowmat(): stwing = {
    seq(
      s-s"\tpeawson coefficient: ${f.fowmat(peawsoncoefficientfowwikes)}", σωσ
      s-s"\tcosine simiwawity: ${f.fowmat(cosinesimiwawitygwobaw)}", nyaa~~
      s"\tavewage cosine simiwawity f-fow aww usews: ${f.fowmat(cosinesimiwawitypewusewavg)}"
    ).mkstwing("\n")
  }
}

/**
 * hewpew t-tweet data containew that can h-howd both the w-wefewence wabew engagements as weww as the
 * wecommendation awgowithm's scowes. (ꈍᴗꈍ) hewpfuw fow evawuating joint data
 */
c-case cwass w-wabewedtweet(
  tawgetusewid: w-wong, ^•ﻌ•^
  tweetid: w-wong, >_<
  authowid: w-wong, ^^;;
  wabews: tweetwabews, ^^;;
  awgowithmscowe: option[doubwe])

c-case cwass wabewedtweetswesuwts(
  tweetstats: tweetstats, /(^•ω•^)
  usewengagewcounts: usewengagewcounts, nyaa~~
  t-tweetengagementcounts: tweetengagementcounts, (✿oωo)
  t-tweetengagementwates: tweetengagementwates, ( ͡o ω ͡o )
  w-wabewcowwewations: o-option[wabewcowwewations] = nyone) {
  p-pwivate vaw f = j-java.text.numbewfowmat.getinstance

  d-def fowmat(titwe: s-stwing = ""): stwing = {
    vaw stw = s-seq(
      s"numbew o-of tweets: ${f.fowmat(tweetstats.numtweets)}", (U ᵕ U❁)
      s-s"numbew o-of distinct tweets: ${f.fowmat(tweetstats.numdistincttweets)}", òωó
      s-s"numbew of distinct usews tawgeted: ${f.fowmat(usewengagewcounts.numdistincttawgetusews)}", σωσ
      s"numbew o-of distinct authows: ${tweetstats.numdistinctauthows.map(f.fowmat).getowewse("n/a")}", :3
      s"avewage awgowithm scowe of tweets: ${tweetstats.avgscowe.map(f.fowmat).getowewse("n/a")}", OwO
      s"engagew counts:", ^^
      s"\tnumbew o-of usews who wiked tweets: ${f.fowmat(usewengagewcounts.numdistinctwikeengagews)}", (˘ω˘)
      s"\tnumbew of usews who wetweeted t-tweets: ${f.fowmat(usewengagewcounts.numdistinctwetweetengagews)}", OwO
      s"tweet e-engagement c-counts:", UwU
      s"\tnumbew of w-wikes: ${f.fowmat(tweetengagementcounts.wike)}", ^•ﻌ•^
      s"\tnumbew o-of wetweets: ${f.fowmat(tweetengagementcounts.wetweet)}", (ꈍᴗꈍ)
      s-s"\tnumbew of cwicks: ${f.fowmat(tweetengagementcounts.cwick)}", /(^•ω•^)
      s"\tnumbew of tweets with any engagements: ${f.fowmat(tweetengagementcounts.hasengagement)}", (U ᵕ U❁)
      s"tweet e-engagement wates:", (✿oωo)
      s"\twate o-of wikes: ${f.fowmat(tweetengagementwates.wike * 100)}%", OwO
      s"\twate o-of wetweets: ${f.fowmat(tweetengagementwates.wetweet * 100)}%", :3
      s-s"\twate of cwicks: ${f.fowmat(tweetengagementwates.cwick * 100)}%", nyaa~~
      s"\twate of any e-engagement: ${f.fowmat(tweetengagementwates.hasengagement * 100)}%"
    ).mkstwing("\n")

    v-vaw cowwewations = wabewcowwewations.map("\n" + _.fowmat()).getowewse("")

    s"$titwe\n$stw$cowwewations"
  }
}

c-case cwass candidatewesuwts(tweetstats: t-tweetstats, ^•ﻌ•^ nyumdistincttawgetusews: wong) {
  pwivate vaw f = java.text.numbewfowmat.getinstance

  def fowmat(titwe: s-stwing = ""): s-stwing = {
    vaw s-stw = seq(
      s"numbew of t-tweets: ${f.fowmat(tweetstats.numtweets)}", ( ͡o ω ͡o )
      s-s"numbew of distinct tweets: ${f.fowmat(tweetstats.numdistincttweets)}", ^^;;
      s-s"numbew of distinct usews tawgeted: ${f.fowmat(numdistincttawgetusews)}", mya
      s"numbew of distinct authows: ${tweetstats.numdistinctauthows.map(f.fowmat).getowewse("n/a")}", (U ᵕ U❁)
      s"avewage a-awgowithm scowe o-of tweets: ${tweetstats.avgscowe.map(f.fowmat).getowewse("n/a")}"
    ).mkstwing("\n")
    s"$titwe\n$stw"
  }
}

/**
 * hewpew c-cwass fow evawuating a-a given candidate tweet set against a wefewence tweet set. ^•ﻌ•^
 * i-it pwovides aggwegation evawuation metwics such as sum of engagements, wate o-of engagements, (U ﹏ U) etc.
 */
object evawuationmetwichewpew {
  p-pwivate d-def towong(boow: boowean): wong = {
    if (boow) 1w ewse 0w
  }

  /**
   * c-cowe engagements a-awe usew actions that count towawds cowe metwics, /(^•ω•^) e.x. wike, wt, ʘwʘ e-etc
   */
  pwivate def hascoweengagements(wabews: t-tweetwabews): boowean = {
    wabews.iswetweeted ||
    wabews.iswiked ||
    w-wabews.isquoted ||
    wabews.iswepwied
  }

  /**
   * w-whethew t-thewe awe cowe engagements ow c-cwick on the tweet
   */
  pwivate d-def hascoweengagementsowcwick(wabews: t-tweetwabews): b-boowean = {
    hascoweengagements(wabews) || w-wabews.iscwicked
  }

  /**
   * w-wetuwn outew join of wefewence tweets and c-candidate tweets, XD k-keyed by (tawgetusewid, (⑅˘꒳˘) t-tweetid).
   * the output of this can t-then be weused to fetch the innew j-join / weft / w-wight join, nyaa~~
   * without having to wedo the expensive join
   *
   * n-nyote: assumes t-the uniqueness o-of keys (i.e. UwU (tawgetid, (˘ω˘) t-tweetid)). rawr x3 make suwe t-to dedup tweetids
   * fow each tawgetid, (///ˬ///✿) othewwise .join() wiww yiewd dupwicate wesuwts. 😳😳😳
   */
  def outewjoinwefewenceandcandidate(
    w-wefewencepipe: typedpipe[wefewencetweets], (///ˬ///✿)
    c-candidatepipe: typedpipe[candidatetweets]
  ): c-cogwouped[(wong, ^^;; wong), ^^ (option[wefewencetweet], (///ˬ///✿) o-option[candidatetweet])] = {

    vaw w-wefewences = wefewencepipe
      .fwatmap { w-weftweets =>
        w-weftweets.impwessedtweets.map { w-weftweet =>
          ((weftweets.tawgetusewid, -.- w-weftweet.tweetid), /(^•ω•^) weftweet)
        }
      }

    vaw candidates = candidatepipe
      .fwatmap { candtweets =>
        candtweets.wecommendedtweets.map { candtweet =>
          ((candtweets.tawgetusewid, UwU c-candtweet.tweetid), (⑅˘꒳˘) c-candtweet)
        }
      }

    w-wefewences.outewjoin(candidates).withweducews(50)
  }

  /**
   * convewt w-wefewence tweets to wabewed tweets. ʘwʘ we do this so that we can w-we-use the common
   * m-metwic cawcuwations fow wabewed t-tweets on wefewence tweets
   */
  def getwabewedwefewence(wefewencepipe: t-typedpipe[wefewencetweets]): t-typedpipe[wabewedtweet] = {
    wefewencepipe
      .fwatmap { w-weftweets =>
        w-weftweets.impwessedtweets.map { tweet =>
          // wefewence tweets do nyot have scowes
          w-wabewedtweet(weftweets.tawgetusewid, t-tweet.tweetid, σωσ t-tweet.authowid, ^^ t-tweet.wabews, OwO n-nyone)
        }
      }
  }

  def getuniquecount[t](pipe: t-typedpipe[t])(impwicit o-owd: scawa.owdewing[t]): e-execution[wong] = {
    p-pipe.distinct
      .aggwegate(size)
      .tooptionexecution
      .map(_.getowewse(0w))
  }

  def c-countuniqueengagedusewsby(
    wabewedtweetspipe: typedpipe[wabewedtweet], (ˆ ﻌ ˆ)♡
    f-f: tweetwabews => boowean
  ): e-execution[wong] = {
    g-getuniquecount[wong](wabewedtweetspipe.cowwect { case t i-if f(t.wabews) => t.tawgetusewid })
  }

  def countuniquewabewedtawgetusews(wabewedtweetspipe: t-typedpipe[wabewedtweet]): e-execution[wong] = {
    g-getuniquecount[wong](wabewedtweetspipe.map(_.tawgetusewid))
  }

  def countuniquecandtawgetusews(candidatepipe: typedpipe[candidatetweets]): execution[wong] = {
    g-getuniquecount[wong](candidatepipe.map(_.tawgetusewid))
  }

  def countuniquewabewedauthows(wabewedtweetpipe: typedpipe[wabewedtweet]): e-execution[wong] = {
    g-getuniquecount[wong](wabewedtweetpipe.map(_.authowid))
  }

  /**
   * hewpew function t-to cawcuwate the basic engagement w-wates
   */
  d-def getengagementwate(
    basicstats: tweetstats, o.O
    e-engagementcount: tweetengagementcounts
  ): tweetengagementwates = {
    v-vaw nyumtweets = b-basicstats.numtweets.todoubwe
    if (numtweets <= 0) t-thwow new iwwegawawgumentexception("invawid t-tweet counts")
    v-vaw wikewate = e-engagementcount.wike / nyumtweets
    vaw wtwate = engagementcount.wetweet / nyumtweets
    vaw cwickwate = engagementcount.cwick / nyumtweets
    vaw engagementwate = engagementcount.hasengagement / nyumtweets
    tweetengagementwates(wikewate, (˘ω˘) wtwate, 😳 c-cwickwate, engagementwate)
  }

  /**
   * h-hewpew function to cawcuwate the basic s-stats fow a p-pipe of candidate t-tweets
   */
  def gettweetstatsfowcandidateexec(
    c-candidatepipe: typedpipe[candidatetweets]
  ): e-execution[tweetstats] = {
    v-vaw pipe = candidatepipe.map { c-candtweets =>
      (candtweets.tawgetusewid, (U ᵕ U❁) candtweets.wecommendedtweets)
    }.sumbykey // d-dedup by tawgetid, :3 i-in case thewe exists muwtipwe entwies. o.O

    v-vaw distincttweetpipe = p-pipe.fwatmap(_._2.map(_.tweetid)).distinct.aggwegate(size)

    v-vaw othewstats = p-pipe
      .map {
        c-case (uid, (///ˬ///✿) w-wecommendedtweets) =>
          v-vaw scowesum = wecommendedtweets.fwatmap(_.scowe).sum
          (wecommendedtweets.size.towong, OwO s-scowesum)
      }
      .sum
      .map {
        c-case (numtweets, >w< scowesum) =>
          i-if (numtweets <= 0) t-thwow n-nyew iwwegawawgumentexception("invawid tweet c-counts")
          vaw avgscowe = scowesum / nyumtweets.todoubwe
          (numtweets, ^^ a-avgscowe)
      }
    vawuepipe
      .fowd(distincttweetpipe, (⑅˘꒳˘) o-othewstats) {
        c-case (numdistincttweet, ʘwʘ (numtweets, (///ˬ///✿) a-avgscowe)) =>
          // nyo a-authow side infowmation fow candidate t-tweets yet
          tweetstats(numtweets, XD n-nyumdistincttweet, nyone, 😳 some(avgscowe))
      }.getowewseexecution(tweetstats(0w, >w< 0w, n-nyone, nyone))
  }

  /**
   * hewpew function to count the totaw nyumbew o-of engagements
   */
  def getwabewedengagementcountexec(
    w-wabewedtweets: t-typedpipe[wabewedtweet]
  ): execution[tweetengagementcounts] = {
    wabewedtweets
      .map { wabewedtweet =>
        v-vaw wike = towong(wabewedtweet.wabews.iswiked)
        v-vaw wetweet = towong(wabewedtweet.wabews.iswetweeted)
        v-vaw c-cwick = towong(wabewedtweet.wabews.iscwicked)
        vaw hasengagement = towong(hascoweengagementsowcwick(wabewedtweet.wabews))

        (wike, (˘ω˘) w-wetweet, nyaa~~ cwick, 😳😳😳 h-hasengagement)
      }
      .sum
      .map {
        case (wike, (U ﹏ U) w-wetweet, (˘ω˘) cwick, hasengagement) =>
          tweetengagementcounts(wike, w-wetweet, :3 cwick, >w< hasengagement)
      }
      .getowewseexecution(tweetengagementcounts(0w, ^^ 0w, 😳😳😳 0w, 0w))
  }

  /**
   * c-count the t-totaw nyumbew of u-unique usews who have engaged with t-tweets
   */
  d-def gettawgetusewstatsfowwabewedtweetsexec(
    w-wabewedtweetspipe: t-typedpipe[wabewedtweet]
  ): execution[usewengagewcounts] = {
    v-vaw nyumuniquetawgetusewsexec = c-countuniquewabewedtawgetusews(wabewedtweetspipe)
    v-vaw n-nyumuniquewikeusewsexec =
      c-countuniqueengagedusewsby(wabewedtweetspipe, nyaa~~ w-wabews => w-wabews.iswiked)
    v-vaw nyumuniquewetweetusewsexec =
      c-countuniqueengagedusewsby(wabewedtweetspipe, (⑅˘꒳˘) wabews => wabews.iswetweeted)

    e-execution
      .zip(
        nyumuniquetawgetusewsexec, :3
        n-nyumuniquewikeusewsexec, ʘwʘ
        n-nyumuniquewetweetusewsexec
      )
      .map {
        c-case (numtawget, rawr x3 wike, wetweet) =>
          usewengagewcounts(
            n-nyumdistincttawgetusews = n-nyumtawget, (///ˬ///✿)
            n-nyumdistinctwikeengagews = wike, 😳😳😳
            nyumdistinctwetweetengagews = wetweet
          )
      }
  }

  /**
   * h-hewpew function t-to cawcuwate the basic stats fow a-a pipe of wabewed t-tweets. XD
   */
  def gettweetstatsfowwabewedtweetsexec(
    wabewedtweetpipe: typedpipe[wabewedtweet]
  ): execution[tweetstats] = {
    v-vaw u-uniqueauthowsexec = c-countuniquewabewedauthows(wabewedtweetpipe)

    v-vaw uniquetweetexec =
      wabewedtweetpipe.map(_.tweetid).distinct.aggwegate(size).getowewseexecution(0w)
    vaw scowesexec = w-wabewedtweetpipe
      .map { t-t => (t.tawgetusewid, >_< (1, t.awgowithmscowe.getowewse(0.0))) }
      .sumbykey // dedup by tawgetid, >w< in case t-thewe exists muwtipwe entwies. /(^•ω•^)
      .map {
        case (uid, :3 (c1, c-c2)) =>
          (c1.towong, ʘwʘ c2)
      }
      .sum
      .map {
        case (numtweets, (˘ω˘) s-scowesum) =>
          i-if (numtweets <= 0) thwow n-nyew iwwegawawgumentexception("invawid t-tweet counts")
          vaw avgscowe = s-scowesum / nyumtweets.todoubwe
          (numtweets, (ꈍᴗꈍ) option(avgscowe))
      }
      .getowewseexecution((0w, ^^ n-nyone))

    e-execution
      .zip(uniqueauthowsexec, ^^ u-uniquetweetexec, ( ͡o ω ͡o ) s-scowesexec)
      .map {
        case (numdistinctauthows, -.- nyumuniquetweets, ^^;; (numtweets, ^•ﻌ•^ a-avgscowes)) =>
          t-tweetstats(numtweets, (˘ω˘) n-nyumuniquetweets, some(numdistinctauthows), o.O a-avgscowes)
      }
  }

  /**
   * pwint a update message t-to the stdout w-when a step is done. (✿oωo)
   */
  p-pwivate def pwintoncompwetemsg(stepdescwiption: stwing, 😳😳😳 stawttimemiwwis: wong): unit = {
    v-vaw fowmatdate = twittewdatefowmat("yyyy-mm-dd h-hh:mm:ss")
    v-vaw nyow = cawendaw.getinstance().gettime

    vaw secondsspent = (now.gettime - s-stawttimemiwwis) / 1000
    pwintwn(
      s-s"- ${fowmatdate.fowmat(now)}\tstep c-compwete: $stepdescwiption\t " +
        s-s"time spent: ${secondsspent / 60}m${secondsspent % 60}s"
    )
  }

  /**
   * c-cawcuwate the metwics o-of a pipe of [[candidatetweets]]
   */
  pwivate def getevawuationwesuwtsfowcandidates(
    candidatepipe: typedpipe[candidatetweets]
  ): e-execution[candidatewesuwts] = {
    vaw tweetstatsexec = g-gettweetstatsfowcandidateexec(candidatepipe)
    vaw nyumdistincttawgetusewsexec = countuniquecandtawgetusews(candidatepipe)

    e-execution
      .zip(tweetstatsexec, (ꈍᴗꈍ) nyumdistincttawgetusewsexec)
      .map {
        case (tweetstats, σωσ nyumdistincttawgetusews) =>
          candidatewesuwts(tweetstats, UwU n-nyumdistincttawgetusews)
      }
  }

  /**
   * c-cawcuwate the metwics o-of a pipe of [[wabewedtweet]]
   */
  pwivate def getevawuationwesuwtsfowwabewedtweets(
    w-wabewedtweetpipe: t-typedpipe[wabewedtweet], ^•ﻌ•^
    getwabewcowwewations: b-boowean = fawse
  ): execution[wabewedtweetswesuwts] = {
    v-vaw tweetstatsexec = gettweetstatsfowwabewedtweetsexec(wabewedtweetpipe)
    vaw usewstatsexec = gettawgetusewstatsfowwabewedtweetsexec(wabewedtweetpipe)
    v-vaw engagementcountexec = getwabewedengagementcountexec(wabewedtweetpipe)

    v-vaw cowwewationsexec = i-if (getwabewcowwewations) {
      e-execution
        .zip(
          wabewcowwewationshewpew.peawsoncoefficientfowwike(wabewedtweetpipe), mya
          wabewcowwewationshewpew.cosinesimiwawityfowwike(wabewedtweetpipe),
          w-wabewcowwewationshewpew.cosinesimiwawityfowwikepewusew(wabewedtweetpipe)
        ).map {
          case (peawsoncoeff, /(^•ω•^) gwobawcos, avgcos) =>
            some(wabewcowwewations(peawsoncoeff, rawr g-gwobawcos, nyaa~~ avgcos))
        }
    } e-ewse {
      v-vawuepipe(none).getowewseexecution(none) // e-empty pipe with a nyone vawue
    }

    e-execution
      .zip(tweetstatsexec, ( ͡o ω ͡o ) e-engagementcountexec, σωσ usewstatsexec, (✿oωo) cowwewationsexec)
      .map {
        case (tweetstats, (///ˬ///✿) e-engagementcount, σωσ engagewcount, cowwewationsopt) =>
          v-vaw engagementwate = getengagementwate(tweetstats, UwU engagementcount)
          w-wabewedtweetswesuwts(
            t-tweetstats, (⑅˘꒳˘)
            engagewcount, /(^•ω•^)
            e-engagementcount, -.-
            e-engagementwate, (ˆ ﻌ ˆ)♡
            c-cowwewationsopt)
      }
  }

  pwivate def wunawwevawfowcandidates(
    candidatepipe: t-typedpipe[candidatetweets], nyaa~~
    outewjoinpipe: typedpipe[((wong, ʘwʘ w-wong), :3 (option[wefewencetweet], (U ᵕ U❁) option[candidatetweet]))]
  ): execution[(candidatewesuwts, (U ﹏ U) candidatewesuwts)] = {
    v-vaw t0 = system.cuwwenttimemiwwis()

    v-vaw candidatenotinintewsectionpipe =
      o-outewjoinpipe
        .cowwect {
          case ((tawgetusewid, ^^ _), (none, some(candtweet))) => (tawgetusewid, òωó s-seq(candtweet))
        }
        .sumbykey
        .map { c-case (tawgetusewid, /(^•ω•^) candtweets) => c-candidatetweets(tawgetusewid, 😳😳😳 candtweets) }
        .fowcetodisk

    execution
      .zip(
        getevawuationwesuwtsfowcandidates(candidatepipe), :3
        getevawuationwesuwtsfowcandidates(candidatenotinintewsectionpipe)
      ).oncompwete(_ => p-pwintoncompwetemsg("wunawwevawfowcandidates()", (///ˬ///✿) t0))
  }

  p-pwivate def wunawwevawfowintewsection(
    outewjoinpipe: typedpipe[((wong, rawr x3 w-wong), (U ᵕ U❁) (option[wefewencetweet], (⑅˘꒳˘) o-option[candidatetweet]))]
  )(
    impwicit uniqueid: u-uniqueid
  ): execution[(wabewedtweetswesuwts, (˘ω˘) w-wabewedtweetswesuwts, :3 w-wabewedtweetswesuwts)] = {
    vaw t0 = s-system.cuwwenttimemiwwis()
    v-vaw intewsectiontweetspipe = outewjoinpipe.cowwect {
      c-case ((tawgetusewid, XD tweetid), >_< (some(weftweet), (✿oωo) some(candtweet))) =>
        wabewedtweet(tawgetusewid, (ꈍᴗꈍ) t-tweetid, XD weftweet.authowid, :3 weftweet.wabews, mya c-candtweet.scowe)
    }.fowcetodisk

    vaw wikedtweetspipe = intewsectiontweetspipe.fiwtew(_.wabews.iswiked)
    v-vaw nyotwikedtweetspipe = intewsectiontweetspipe.fiwtew(!_.wabews.iswiked)

    e-execution
      .zip(
        g-getevawuationwesuwtsfowwabewedtweets(intewsectiontweetspipe, òωó getwabewcowwewations = t-twue), nyaa~~
        g-getevawuationwesuwtsfowwabewedtweets(wikedtweetspipe), 🥺
        getevawuationwesuwtsfowwabewedtweets(notwikedtweetspipe)
      ).oncompwete(_ => p-pwintoncompwetemsg("wunawwevawfowintewsection()", t0))
  }

  p-pwivate def wunawwevawfowwefewences(
    w-wefewencepipe: t-typedpipe[wefewencetweets], -.-
    outewjoinpipe: typedpipe[((wong, 🥺 wong), (˘ω˘) (option[wefewencetweet], òωó option[candidatetweet]))]
  ): e-execution[(wabewedtweetswesuwts, UwU w-wabewedtweetswesuwts)] = {
    vaw t0 = system.cuwwenttimemiwwis()
    vaw wabewedwefewencenotinintewsectionpipe =
      o-outewjoinpipe.cowwect {
        case ((tawgetusewid, ^•ﻌ•^ _), mya (some(weftweet), (✿oωo) n-none)) =>
          w-wabewedtweet(tawgetusewid, XD weftweet.tweetid, :3 weftweet.authowid, (U ﹏ U) weftweet.wabews, UwU nyone)
      }.fowcetodisk

    execution
      .zip(
        g-getevawuationwesuwtsfowwabewedtweets(getwabewedwefewence(wefewencepipe)), ʘwʘ
        getevawuationwesuwtsfowwabewedtweets(wabewedwefewencenotinintewsectionpipe)
      ).oncompwete(_ => pwintoncompwetemsg("wunawwevawfowwefewences()", >w< t-t0))
  }

  def wunawwevawuations(
    w-wefewencepipe: typedpipe[wefewencetweets], 😳😳😳
    candidatepipe: t-typedpipe[candidatetweets]
  )(
    impwicit uniqueid: u-uniqueid
  ): e-execution[stwing] = {
    v-vaw t-t0 = system.cuwwenttimemiwwis()

    // f-fowce evewything t-to disk to maximize data we-use
    execution
      .zip(
        wefewencepipe.fowcetodiskexecution, rawr
        candidatepipe.fowcetodiskexecution
      ).fwatmap {
        case (wefewencediskpipe, ^•ﻌ•^ c-candidatediskpipe) =>
          o-outewjoinwefewenceandcandidate(wefewencediskpipe, c-candidatediskpipe).fowcetodiskexecution
            .fwatmap { outewjoinpipe =>
              v-vaw w-wefewencewesuwtsexec = w-wunawwevawfowwefewences(wefewencediskpipe, σωσ outewjoinpipe)
              vaw intewsectionwesuwtsexec = wunawwevawfowintewsection(outewjoinpipe)
              vaw candidatewesuwtsexec = wunawwevawfowcandidates(candidatediskpipe, :3 o-outewjoinpipe)

              e-execution
                .zip(
                  wefewencewesuwtsexec, rawr x3
                  intewsectionwesuwtsexec, nyaa~~
                  candidatewesuwtsexec
                ).map {
                  case (
                        (awwwefewence, :3 w-wefewencenotinintewsection), >w<
                        (awwintewsection, rawr i-intewsectionwiked, 😳 i-intewsectionnotwiked), 😳
                        (awwcandidate, 🥺 candidatenotinintewsection)) =>
                    vaw timespent = (system.cuwwenttimemiwwis() - t-t0) / 1000
                    vaw wesuwtstw = seq(
                      "===================================================", rawr x3
                      s-s"evawuation c-compwete. ^^ took ${timespent / 60}m${timespent % 60}s ", ( ͡o ω ͡o )
                      awwwefewence.fowmat("-----metwics f-fow aww wefewence tweets-----"), XD
                      w-wefewencenotinintewsection.fowmat(
                        "-----metwics f-fow wefewence tweets that a-awe nyot in the i-intewsection-----"
                      ), ^^
                      a-awwintewsection.fowmat("-----metwics f-fow aww i-intewsection tweets-----"), (⑅˘꒳˘)
                      i-intewsectionwiked.fowmat("-----metwics fow wiked i-intewsection t-tweets-----"), (⑅˘꒳˘)
                      intewsectionnotwiked.fowmat(
                        "-----metwics f-fow nyot wiked intewsection tweets-----"), ^•ﻌ•^
                      a-awwcandidate.fowmat("-----metwics fow a-aww candidate tweets-----"), ( ͡o ω ͡o )
                      candidatenotinintewsection.fowmat(
                        "-----metwics f-fow c-candidate tweets that awe nyot in the intewsection-----"
                      ), ( ͡o ω ͡o )
                      "===================================================\n"
                    ).mkstwing("\n")
                    p-pwintwn(wesuwtstw)
                    wesuwtstw
                }
                .oncompwete(_ =>
                  pwintoncompwetemsg(
                    "evawuation c-compwete. (✿oωo) check s-stdout ow output wogs fow wesuwts.", 😳😳😳
                    t0))
            }
      }
  }
}
