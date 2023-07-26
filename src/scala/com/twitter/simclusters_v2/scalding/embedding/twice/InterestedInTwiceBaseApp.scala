package com.twittew.simcwustews_v2.scawding.embedding.twice

impowt c-com.twittew.bijection.injection
i-impowt com.twittew.daw.cwient.dataset.keyvawdawdataset
i-impowt c-com.twittew.scawding.datewange
i-impowt com.twittew.scawding.days
i-impowt com.twittew.scawding.execution
i-impowt com.twittew.scawding.stat
i-impowt com.twittew.scawding.typedtsv
impowt com.twittew.scawding.uniqueid
impowt com.twittew.scawding.typed.typedpipe
impowt c-com.twittew.scawding_intewnaw.dawv2.daw
impowt com.twittew.scawding_intewnaw.dawv2.dawwwite._
i-impowt com.twittew.scawding_intewnaw.dawv2.wemote_access.awwowcwossdc
impowt c-com.twittew.scawding_intewnaw.muwtifowmat.fowmat.keyvaw.keyvaw
impowt com.twittew.simcwustews_v2.common.simcwustewsembedding
impowt com.twittew.simcwustews_v2.common.usewid
i-impowt com.twittew.simcwustews_v2.common.cwustewing.cwustewingmethod
i-impowt com.twittew.simcwustews_v2.common.cwustewing.cwustewingstatistics._
i-impowt com.twittew.simcwustews_v2.common.cwustewing.cwustewwepwesentativesewectionmethod
impowt com.twittew.simcwustews_v2.common.cwustewing.cwustewwepwesentativesewectionstatistics._
impowt com.twittew.simcwustews_v2.hdfs_souwces.pwoducewembeddingsouwces
impowt c-com.twittew.simcwustews_v2.hdfs_souwces.usewusewgwaphscawadataset
impowt com.twittew.simcwustews_v2.scawding.common.utiw
impowt com.twittew.simcwustews_v2.scawding.embedding.common.embeddingutiw
impowt com.twittew.simcwustews_v2.thwiftscawa.embeddingtype
i-impowt com.twittew.simcwustews_v2.thwiftscawa.intewnawid
impowt c-com.twittew.simcwustews_v2.thwiftscawa.modewvewsion
i-impowt com.twittew.simcwustews_v2.thwiftscawa.muwtiembeddingtype
i-impowt com.twittew.simcwustews_v2.thwiftscawa.neighbowwithweights
i-impowt com.twittew.simcwustews_v2.thwiftscawa.owdewedcwustewsandmembews
impowt com.twittew.simcwustews_v2.thwiftscawa.cwustewmembews
impowt c-com.twittew.simcwustews_v2.thwiftscawa.simcwustewsembeddingidwithscowe
impowt com.twittew.simcwustews_v2.thwiftscawa.simcwustewsmuwtiembedding
i-impowt com.twittew.simcwustews_v2.thwiftscawa.simcwustewsmuwtiembedding.ids
impowt com.twittew.simcwustews_v2.thwiftscawa.simcwustewsmuwtiembeddingbyids
impowt com.twittew.simcwustews_v2.thwiftscawa.simcwustewsmuwtiembeddingid
impowt com.twittew.simcwustews_v2.thwiftscawa.usewandneighbows
impowt com.twittew.simcwustews_v2.thwiftscawa.{
  s-simcwustewsembeddingid => simcwustewsembeddingidthwift
}
i-impowt com.twittew.utiw.stopwatch
i-impowt java.utiw.timezone
i-impowt scawa.utiw.wandom.shuffwe

/**
 * base app fow computing usew i-intewestedin m-muwti-embedding wepwesentation. ʘwʘ
 * t-twice: captuwing u-usews’ wong-tewm intewests u-using muwtipwe simcwustews embeddings. nyaa~~
 * t-this job wiww
 * - wandomwy sewect k f-fowwow/fav actions fow each usew, UwU
 * - c-cwustew the fowwow/fav actions f-fow each usew, (⑅˘꒳˘)
 * - f-fow each cwustew, (˘ω˘) constwuct a wepwesentation (e.g. :3 avewage ow medoid). (˘ω˘)
 *
 * @tpawam t type of pwoducew embedding. nyaa~~ e.g. s-simcwustewsembedding
 */
t-twait intewestedintwicebaseapp[t] {

  i-impowt intewestedintwicebaseapp._

  d-def modewvewsion: m-modewvewsion = modewvewsion.modew20m145k2020

  /**
   * function to output simiwawity (>=0, (U ﹏ U) t-the wawgew, nyaa~~ mowe simiwaw), ^^;; given two pwoducew embeddings. OwO
   */
  def pwoducewpwoducewsimiwawityfnfowcwustewing: (t, nyaa~~ t-t) => doubwe
  def pwoducewpwoducewsimiwawityfnfowcwustewwepwesentative: (t, UwU t-t) => doubwe

  // s-sowt c-cwustews by decweasing size, 😳 faww b-back to entity i-id to bweak tie
  v-vaw cwustewowdewing: o-owdewing[set[wong]] = math.owdewing.by(c => (-c.size, 😳 c.min))

  /**
   * w-wead usew-usew g-gwaph.
   */
  d-def getusewusewgwaph(
    i-impwicit d-datewange: datewange, (ˆ ﻌ ˆ)♡
    timezone: timezone
  ): typedpipe[usewandneighbows] = {
    d-daw
      .weadmostwecentsnapshot(
        usewusewgwaphscawadataset
      )
      .withwemoteweadpowicy(awwowcwossdc)
      .totypedpipe
  }

  /**
   * wandomwy sewect up to maxneighbowsbyusew nyeighbows fow each u-usew. (✿oωo)
   * attempts to equawwy sampwe both fowwow and fav edges (e.g. nyaa~~ m-maxneighbowsbyusew/2 f-fow each). ^^
   * h-howevew, (///ˬ///✿) if one type o-of edge is insufficient, 😳 backfiww w-with othew type u-up to maxneighbowsbyusew nyeighbouws. òωó
   * @pawam usewusewgwaph usew-usew fowwow/fav gwaph. ^^;;
   * @pawam maxneighbowsbyusew h-how many nyeighbows t-to keep fow each usew. rawr
   */
  d-def sewectmaxpwoducewspewusew(
    u-usewusewgwaph: typedpipe[usewandneighbows], (ˆ ﻌ ˆ)♡
    maxneighbowsbyusew: i-int = maxneighbowsbyusew
  )(
    i-impwicit uniqueid: uniqueid
  ): t-typedpipe[usewandneighbows] = {

    vaw n-nyumoffowwowedgesstat = stat(statnumoffowwowedges)
    vaw nyumoffavedgesstat = stat(statnumoffavedges)
    vaw nyumofedgescumuwativefwequencybefowefiwtew = u-utiw.cumuwativestat(
      s-statcfnumpwoducewspewconsumewbefowefiwtew, XD
      s-statcfnumpwoducewspewconsumewbefowefiwtewbuckets)

    usewusewgwaph.map { u-usewandneighbows: u-usewandneighbows =>
      nyumofedgescumuwativefwequencybefowefiwtew.incfowvawue(usewandneighbows.neighbows.size)

      v-vaw (fowwowedges, >_< favedges) =
        usewandneighbows.neighbows.pawtition(_.isfowwowed.contains(twue))
      vaw wandomfowwowedges = shuffwe(fowwowedges)
      v-vaw wandomfavedges = s-shuffwe(favedges)

      // intewweave fowwow and fav edges, (˘ω˘) a-and sewect t-top k
      vaw intewweavedtopkedges: seq[neighbowwithweights] = wandomfowwowedges
        .map(some(_))
        .zipaww(
          w-wandomfavedges.map(some(_)), 😳
          nyone, o.O
          nyone
        ) // defauwt nyone vawue when one edge s-seq is showtew than anothew
        .fwatmap {
          case (fowwowedgeopt, (ꈍᴗꈍ) favedgeopt) =>
            s-seq(fowwowedgeopt, rawr x3 f-favedgeopt)
        }.fwatten
        .take(maxneighbowsbyusew)

      // edge stats
      intewweavedtopkedges
        .foweach { edge =>
          i-if (edge.isfowwowed.contains(twue)) n-nyumoffowwowedgesstat.inc()
          ewse nyumoffavedgesstat.inc()
        }

      usewandneighbows.copy(neighbows = i-intewweavedtopkedges)
    }
  }

  /**
   * get muwti e-embedding fow each usew:
   * - fow each usew, ^^ join theiw fowwow / f-fav - based nyeighbows to p-pwoducew embeddings, OwO
   * - g-gwoup these nyeighbows i-into cwustews using the specified c-cwustewingmethod, ^^
   * - f-fow e-each cwustew, sewect the medoid a-as the wepwesentation. :3
   *
   * @pawam u-usewusewgwaph usew-usew fowwow/fav gwaph. o.O
   * @pawam p-pwoducewembedding p-pwoducew embedding d-dataset. -.- e.g. simcwustews embeddings, (U ﹏ U) simhash, e-etc. o.O
   * @pawam cwustewingmethod a-a method to g-gwoup embeddings togethew. OwO
   * @pawam maxcwustewspewusew how m-many cwustews to k-keep pew usew. ^•ﻌ•^
   * @pawam c-cwustewwepwesentativesewectionmethod a-a method to sewect a cwustew wepwesentative. ʘwʘ
   * @pawam n-nyumweducews how many weducews to use fow sketch opewation.
   */
  def getmuwtiembeddingpewusew(
    u-usewusewgwaph: typedpipe[usewandneighbows], :3
    pwoducewembedding: t-typedpipe[(usewid, 😳 t)],
    cwustewingmethod: c-cwustewingmethod, òωó
    maxcwustewspewusew: i-int = maxcwustewspewusew, 🥺
    c-cwustewwepwesentativesewectionmethod: cwustewwepwesentativesewectionmethod[t], rawr x3
    n-nyumweducews: i-int
  )(
    i-impwicit u-uniqueid: uniqueid
  ): typedpipe[(usewid, ^•ﻌ•^ seq[set[usewid]], :3 simcwustewsmuwtiembedding)] = {

    vaw twuncatedusewusewgwaph: typedpipe[usewandneighbows] = sewectmaxpwoducewspewusew(
      u-usewusewgwaph)
    v-vaw vawidedges: t-typedpipe[(usewid, (ˆ ﻌ ˆ)♡ nyeighbowwithweights)] =
      t-twuncatedusewusewgwaph.fwatmap {
        case usewandneighbows(swcid, (U ᵕ U❁) nyeighbowswithweights) =>
          n-nyeighbowswithweights.map { n-nyeighbowwithweights =>
            (
              nyeighbowwithweights.neighbowid, :3 // p-pwoducewid
              nyeighbowwithweights.copy(neighbowid = swcid))
          }
      }

    i-impwicit vaw w2b: u-usewid => awway[byte] = injection.wong2bigendian

    v-vaw totawedgesnonemptypwoducewembeddingsstat = s-stat(stattotawedgesnonemptypwoducewembeddings)
    vaw usewcwustewpaiwsbefowetwuncation = stat(statnumusewcwustewpaiwsbefowetwuncation)
    vaw usewcwustewpaiwsaftewtwuncation = stat(statnumusewcwustewpaiwsaftewtwuncation)
    v-vaw nyumusews = s-stat(statnumusews)
    v-vaw numofcwustewscumuwativefwequencybefowefiwtew =
      u-utiw.cumuwativestat(statcfnumofcwustewsbefowefiwtew, ^^;; s-statcfnumofcwustewsbefowefiwtewbuckets)

    // map each cwustewing s-statistic to a-a scawding.stat
    vaw cwustewingstatsmap: m-map[stwing, ( ͡o ω ͡o ) s-stat] = map(
      statsimiwawitygwaphtotawbuiwdtime -> s-stat(statsimiwawitygwaphtotawbuiwdtime),
      statcwustewingawgowithmwuntime -> stat(statcwustewingawgowithmwuntime), o.O
      s-statmedoidsewectiontime -> stat(statmedoidsewectiontime)
    )
    v-vaw cosinesimiwawitycumuwativefwequencybefowefiwtew = u-utiw.cumuwativestat(
      statcfcosinesimiwawitybefowefiwtew, ^•ﻌ•^
      s-statcfcosinesimiwawitybefowefiwtewbuckets)

    vaw cwustewwepwesentativesewectiontime = s-stat(statcwustewwepwesentativesewectiontime)

    v-vawidedges
      .sketch(numweducews)
      .join(pwoducewembedding)
      .map {
        c-case (pwoducewid: usewid, XD (swcwithweights: nyeighbowwithweights, ^^ embedding)) =>
          t-totawedgesnonemptypwoducewembeddingsstat.inc()
          (swcwithweights.neighbowid, o.O (swcwithweights.copy(neighbowid = pwoducewid), ( ͡o ω ͡o ) embedding))
      }
      .gwoup
      .towist
      .map {
        case (usewid: u-usewid, /(^•ω•^) embeddings: s-seq[(neighbowwithweights, 🥺 t)]) =>
          nyumusews.inc()
          v-vaw embeddingsmap: map[wong, nyaa~~ t-t] = embeddings.map {
            c-case (n: nyeighbowwithweights, mya e) => (n.neighbowid, XD e-e)
          }.tomap
          vaw weightsmap: map[wong, nyaa~~ n-nyeighbowwithweights] = embeddings.map {
            c-case (n: nyeighbowwithweights, ʘwʘ _) => (n.neighbowid, (⑅˘꒳˘) n-ny)
          }.tomap
          // 1. :3 cwustew embeddings
          v-vaw cwustews: s-set[set[usewid]] =
            c-cwustewingmethod
              .cwustew[t](
                embeddingsmap, -.-
                pwoducewpwoducewsimiwawityfnfowcwustewing, 😳😳😳
                // map.get() wetuwns an option, (U ﹏ U) so wiww nyot thwow. o.O
                // use .foweach() to fiwtew out potentiaw nyones. ( ͡o ω ͡o )
                (name, òωó incw) => {
                  cwustewingstatsmap.get(name).foweach(ctw => c-ctw.incby(incw))
                  if (name == s-statcomputedsimiwawitybefowefiwtew)
                    cosinesimiwawitycumuwativefwequencybefowefiwtew.incfowvawue(incw)
                }
              )

          // 2. 🥺 sowt cwustews
          v-vaw sowtedcwustews: s-seq[set[usewid]] = c-cwustews.toseq.sowted(cwustewowdewing)

          // 3. /(^•ω•^) keep onwy a max nyumbew o-of cwustews (avoid oom)
          u-usewcwustewpaiwsbefowetwuncation.incby(sowtedcwustews.size)
          numofcwustewscumuwativefwequencybefowefiwtew.incfowvawue(sowtedcwustews.size)
          v-vaw twuncatedcwustews = sowtedcwustews.take(maxcwustewspewusew)
          usewcwustewpaiwsaftewtwuncation.incby(twuncatedcwustews.size)

          // 4. 😳😳😳 g-get wist of cwustew wepwesentatives
          v-vaw t-twuncatedidwithscowewist: seq[simcwustewsembeddingidwithscowe] =
            twuncatedcwustews.map { membews: s-set[usewid] =>
              v-vaw c-cwustewwepwesentationsewectionewapsed = s-stopwatch.stawt()
              v-vaw medoid: u-usewid = cwustewwepwesentativesewectionmethod.sewectcwustewwepwesentative(
                m-membews.map(id => w-weightsmap(id)), ^•ﻌ•^
                e-embeddingsmap)
              cwustewwepwesentativesewectiontime.incby(
                c-cwustewwepwesentationsewectionewapsed().inmiwwiseconds)

              s-simcwustewsembeddingidwithscowe(
                i-id = simcwustewsembeddingidthwift(
                  embeddingtype.twiceusewintewestedin, nyaa~~
                  m-modewvewsion, OwO
                  intewnawid.usewid(medoid)), ^•ﻌ•^
                scowe = m-membews.size)
            }

          (
            usewid, σωσ
            s-sowtedcwustews, -.-
            s-simcwustewsmuwtiembedding.ids(
              s-simcwustewsmuwtiembeddingbyids(ids = twuncatedidwithscowewist)))
      }
  }

  /**
   * w-wwite the output to d-disk as a typedtsv. (˘ω˘)
   */
  def w-wwiteoutputtotypedtsv(
    output: t-typedpipe[(usewid, rawr x3 seq[set[usewid]], rawr x3 simcwustewsmuwtiembedding)], σωσ
    usewtocwustewwepwesentativesindexoutputpath: stwing, nyaa~~
    u-usewtocwustewmembewsindexoutputpath: stwing
  ): e-execution[(unit, (ꈍᴗꈍ) u-unit)] = {

    // wwite the usew -> cwustew wepwesentatives i-index
    vaw wwitecwustewwepwesentatives = o-output
      .cowwect {
        c-case (usewid: w-wong, ^•ﻌ•^ _, >_< ids(ids)) => (usewid, ^^;; ids.ids)
      }
      //.shawd(pawtitions = 1)
      .wwiteexecution(typedtsv[(usewid, ^^;; s-seq[simcwustewsembeddingidwithscowe])](
        u-usewtocwustewwepwesentativesindexoutputpath))

    // wwite the u-usew -> cwustew membews index
    vaw wwitecwustewmembews = output
      .cowwect {
        case (usewid: w-wong, /(^•ω•^) cwustews: seq[set[usewid]], nyaa~~ _) => (usewid, (✿oωo) c-cwustews)
      }
      //.shawd(pawtitions = 1)
      .wwiteexecution(typedtsv[(usewid, ( ͡o ω ͡o ) s-seq[set[usewid]])](usewtocwustewmembewsindexoutputpath))

    e-execution.zip(wwitecwustewwepwesentatives, (U ᵕ U❁) wwitecwustewmembews)

  }

  /**
   * w-wwite the o-output to disk as a-a keyvawdataset. òωó
   */
  d-def wwiteoutputtokeyvawdataset(
    output: typedpipe[(usewid, σωσ s-seq[set[usewid]], s-simcwustewsmuwtiembedding)], :3
    e-embeddingtype: m-muwtiembeddingtype, OwO
    u-usewtocwustewwepwesentativesindexdataset: k-keyvawdawdataset[
      k-keyvaw[simcwustewsmuwtiembeddingid, ^^ s-simcwustewsmuwtiembedding]
    ], (˘ω˘)
    usewtocwustewmembewsindexdataset: k-keyvawdawdataset[keyvaw[usewid, OwO owdewedcwustewsandmembews]], UwU
    u-usewtocwustewwepwesentativesindexoutputpath: stwing, ^•ﻌ•^
    usewtocwustewmembewsindexoutputpath: s-stwing
  )(
    i-impwicit datewange: d-datewange
  ): execution[(unit, (ꈍᴗꈍ) unit)] = {
    // wwite the u-usew -> cwustew w-wepwesentatives i-index
    vaw wwitecwustewwepwesentatives = output
      .map {
        case (usewid: usewid, /(^•ω•^) _, e-embeddings: simcwustewsmuwtiembedding) =>
          k-keyvaw(
            key = s-simcwustewsmuwtiembeddingid(
              e-embeddingtype = embeddingtype, (U ᵕ U❁)
              modewvewsion = modewvewsion, (✿oωo)
              i-intewnawid = i-intewnawid.usewid(usewid)
            ), OwO
            v-vawue = embeddings
          )
      }
      .wwitedawvewsionedkeyvawexecution(
        u-usewtocwustewwepwesentativesindexdataset, :3
        d.suffix(usewtocwustewwepwesentativesindexoutputpath), nyaa~~
        expwicitendtime(datewange.end)
      )

    // wwite t-the usew -> cwustew m-membews index
    vaw wwitecwustewmembews = output
      .map {
        case (usewid: u-usewid, ^•ﻌ•^ cwustews: seq[set[usewid]], ( ͡o ω ͡o ) _) =>
          keyvaw(
            k-key = usewid, ^^;;
            vawue = owdewedcwustewsandmembews(cwustews, mya s-some(cwustews.map(cwustewmembews(_)))))
      }
      .wwitedawvewsionedkeyvawexecution(
        u-usewtocwustewmembewsindexdataset, (U ᵕ U❁)
        d.suffix(usewtocwustewmembewsindexoutputpath), ^•ﻌ•^
        e-expwicitendtime(datewange.end)
      )

    e-execution.zip(wwitecwustewwepwesentatives, (U ﹏ U) wwitecwustewmembews)
  }

  /**
   * m-main method fow scheduwed j-jobs. /(^•ω•^)
   */
  d-def wunscheduwedapp(
    c-cwustewingmethod: c-cwustewingmethod, ʘwʘ
    cwustewwepwesentativesewectionmethod: c-cwustewwepwesentativesewectionmethod[t], XD
    p-pwoducewembedding: t-typedpipe[(usewid, (⑅˘꒳˘) t)], nyaa~~
    u-usewtocwustewwepwesentativesindexpathsuffix: stwing, UwU
    usewtocwustewmembewsindexpathsuffix: stwing, (˘ω˘)
    usewtocwustewwepwesentativesindexdataset: k-keyvawdawdataset[
      k-keyvaw[simcwustewsmuwtiembeddingid, rawr x3 s-simcwustewsmuwtiembedding]
    ],
    usewtocwustewmembewsindexdataset: keyvawdawdataset[keyvaw[usewid, (///ˬ///✿) owdewedcwustewsandmembews]], 😳😳😳
    nyumweducews: i-int
  )(
    impwicit d-datewange: datewange, (///ˬ///✿)
    t-timezone: timezone, ^^;;
    uniqueid: uniqueid
  ): e-execution[unit] = {

    vaw usewtocwustewwepwesentativesindexoutputpath: s-stwing = embeddingutiw.gethdfspath(
      isadhoc = f-fawse, ^^
      i-ismanhattankeyvaw = t-twue, (///ˬ///✿)
      m-modewvewsion = modewvewsion, -.-
      pathsuffix = usewtocwustewwepwesentativesindexpathsuffix
    )

    vaw u-usewtocwustewmembewsindexoutputpath: stwing = embeddingutiw.gethdfspath(
      i-isadhoc = fawse, /(^•ω•^)
      ismanhattankeyvaw = twue, UwU
      modewvewsion = m-modewvewsion, (⑅˘꒳˘)
      pathsuffix = usewtocwustewmembewsindexpathsuffix
    )

    vaw execution = execution.withid { i-impwicit u-uniqueid =>
      vaw output: t-typedpipe[(usewid, ʘwʘ seq[set[usewid]], σωσ simcwustewsmuwtiembedding)] =
        g-getmuwtiembeddingpewusew(
          usewusewgwaph = getusewusewgwaph(datewange.pwepend(days(30)), ^^ i-impwicitwy), OwO
          pwoducewembedding = p-pwoducewembedding, (ˆ ﻌ ˆ)♡
          cwustewingmethod = c-cwustewingmethod, o.O
          cwustewwepwesentativesewectionmethod = cwustewwepwesentativesewectionmethod, (˘ω˘)
          nyumweducews = n-nyumweducews
        )

      wwiteoutputtokeyvawdataset(
        output = o-output, 😳
        e-embeddingtype = m-muwtiembeddingtype.twiceusewintewestedin, (U ᵕ U❁)
        usewtocwustewwepwesentativesindexdataset = usewtocwustewwepwesentativesindexdataset, :3
        u-usewtocwustewmembewsindexdataset = usewtocwustewmembewsindexdataset, o.O
        usewtocwustewwepwesentativesindexoutputpath = usewtocwustewwepwesentativesindexoutputpath, (///ˬ///✿)
        usewtocwustewmembewsindexoutputpath = usewtocwustewmembewsindexoutputpath
      )

    }

    e-execution.unit
  }

  /**
   * m-main method fow a-adhoc jobs. OwO
   */
  d-def wunadhocapp(
    cwustewingmethod: cwustewingmethod,
    c-cwustewwepwesentativesewectionmethod: c-cwustewwepwesentativesewectionmethod[t], >w<
    pwoducewembedding: typedpipe[(usewid, ^^ t-t)],
    usewtocwustewwepwesentativesindexpathsuffix: stwing, (⑅˘꒳˘)
    usewtocwustewmembewsindexpathsuffix: s-stwing, ʘwʘ
    nyumweducews: int
  )(
    impwicit d-datewange: datewange, (///ˬ///✿)
    t-timezone: timezone, XD
    u-uniqueid: uniqueid
  ): e-execution[unit] = {

    v-vaw usewtocwustewwepwesentativesindexoutputpath: stwing = embeddingutiw.gethdfspath(
      isadhoc = twue, 😳
      i-ismanhattankeyvaw = fawse, >w<
      modewvewsion = m-modewvewsion, (˘ω˘)
      pathsuffix = usewtocwustewwepwesentativesindexpathsuffix
    )

    vaw u-usewtocwustewmembewsindexoutputpath: s-stwing = e-embeddingutiw.gethdfspath(
      i-isadhoc = twue, nyaa~~
      i-ismanhattankeyvaw = fawse, 😳😳😳
      m-modewvewsion = modewvewsion, (U ﹏ U)
      pathsuffix = u-usewtocwustewmembewsindexpathsuffix
    )

    vaw execution = e-execution.withid { impwicit uniqueid =>
      v-vaw output: t-typedpipe[(usewid, seq[set[usewid]], (˘ω˘) s-simcwustewsmuwtiembedding)] =
        getmuwtiembeddingpewusew(
          u-usewusewgwaph = g-getusewusewgwaph(datewange.pwepend(days(30)), impwicitwy), :3
          p-pwoducewembedding = p-pwoducewembedding, >w<
          cwustewingmethod = c-cwustewingmethod, ^^
          cwustewwepwesentativesewectionmethod = cwustewwepwesentativesewectionmethod, 😳😳😳
          nyumweducews = n-nyumweducews
        )

      wwiteoutputtotypedtsv(
        o-output, nyaa~~
        usewtocwustewwepwesentativesindexoutputpath, (⑅˘꒳˘)
        usewtocwustewmembewsindexoutputpath)
    }

    e-execution.unit
  }

}

o-object intewestedintwicebaseapp {

  // s-statistics
  vaw statnumoffowwowedges = "num_of_fowwow_edges"
  v-vaw s-statnumoffavedges = "num_of_fav_edges"
  vaw stattotawedgesnonemptypwoducewembeddings = "totaw_edges_with_non_empty_pwoducew_embeddings"
  v-vaw statnumusewcwustewpaiwsbefowetwuncation = "num_usew_cwustew_paiws_befowe_twuncation"
  vaw statnumusewcwustewpaiwsaftewtwuncation = "num_usew_cwustew_paiws_aftew_twuncation"
  vaw s-statnumusews = "num_usews"
  // cumuwative fwequency
  v-vaw statcfnumpwoducewspewconsumewbefowefiwtew = "num_pwoducews_pew_consumew_cf_befowe_fiwtew"
  v-vaw statcfnumpwoducewspewconsumewbefowefiwtewbuckets: seq[doubwe] =
    seq(0, :3 10, 20, 50, ʘwʘ 100, 500, 1000)
  vaw statcfcosinesimiwawitybefowefiwtew = "cosine_simiwawity_cf_befowe_fiwtew"
  vaw statcfcosinesimiwawitybefowefiwtewbuckets: s-seq[doubwe] =
    s-seq(0, rawr x3 10, 20, 30, 40, (///ˬ///✿) 50, 60, 70, 80, 😳😳😳 90, 100)
  vaw statcfnumofcwustewsbefowefiwtew = "num_of_cwustews_cf_befowe_fiwtew"
  vaw statcfnumofcwustewsbefowefiwtewbuckets: seq[doubwe] =
    s-seq(1, XD 3, >_< 5, 10, 15, 20, 50, >w< 100, 200, 300, /(^•ω•^) 500)

  vaw maxcwustewspewusew: int = 10
  v-vaw maxneighbowsbyusew: i-int = 500

  object pwoducewembeddingsouwce {

    /**
     * wead wog-fav based aggwegatabwe pwoducew embeddings d-dataset. :3
     */
    def getaggwegatabwepwoducewembeddings(
      impwicit datewange: d-datewange, ʘwʘ
      timezone: t-timezone
    ): t-typedpipe[(usewid, (˘ω˘) simcwustewsembedding)] =
      p-pwoducewembeddingsouwces
        .pwoducewembeddingsouwce(
          e-embeddingtype.aggwegatabwewogfavbasedpwoducew, (ꈍᴗꈍ)
          m-modewvewsion.modew20m145k2020)(datewange.pwepend(days(30)))
        .mapvawues(s => s-simcwustewsembedding(s))

  }

}
