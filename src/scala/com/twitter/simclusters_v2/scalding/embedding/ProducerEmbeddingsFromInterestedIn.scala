package com.twittew.simcwustews_v2.scawding.embedding

impowt com.twittew.daw.cwient.dataset.keyvawdawdataset
i-impowt c-com.twittew.scawding._
i-impowt c-com.twittew.scawding_intewnaw.dawv2.dawwwite._
i-impowt com.twittew.scawding_intewnaw.muwtifowmat.fowmat.keyvaw.keyvaw
i-impowt com.twittew.simcwustews_v2.common.modewvewsions
i-impowt c-com.twittew.simcwustews_v2.hdfs_souwces._
impowt com.twittew.simcwustews_v2.scawding.embedding.common.embeddingutiw._
impowt com.twittew.simcwustews_v2.scawding.embedding.common.simcwustewsembeddingjob
impowt com.twittew.simcwustews_v2.thwiftscawa._
impowt c-com.twittew.wtf.scawding.jobs.common.{adhocexecutionapp, (˘ω˘) scheduwedexecutionapp}
impowt java.utiw.timezone

object pwoducewembeddingsfwomintewestedinbatchapputiw {
  i-impowt pwoducewembeddingsfwomintewestedin._

  v-vaw usew = system.getenv("usew")

  vaw wootpath: stwing = s-s"/usew/$usew/manhattan_sequence_fiwes"

  // hewps speed up t-the muwtipwication s-step which can get vewy big
  vaw nyumweducewsfowmatwixmuwtipwication: int = 12000

  /**
   * given the pwoducew x-x cwustew matwix, :3 key by pwoducew / cwustew individuawwy, >w< and wwite output
   * t-to individuaw daw datasets
   */
  d-def wwiteoutput(
    pwoducewcwustewembedding: t-typedpipe[((cwustewid, ^^ u-usewid), doubwe)], 😳😳😳
    p-pwoducewtopkembeddingsdataset: keyvawdawdataset[keyvaw[wong, nyaa~~ topsimcwustewswithscowe]], (⑅˘꒳˘)
    c-cwustewtopkpwoducewsdataset: keyvawdawdataset[
      keyvaw[pewsistedfuwwcwustewid, :3 t-toppwoducewswithscowe]
    ], ʘwʘ
    pwoducewtopkembeddingspath: stwing, rawr x3
    cwustewtopkpwoducewspath: stwing, (///ˬ///✿)
    modewvewsion: m-modewvewsion
  ): execution[unit] = {
    vaw k-keyedbypwoducew =
      t-tosimcwustewembedding(pwoducewcwustewembedding, 😳😳😳 t-topkcwustewstokeep, XD modewvewsion)
        .map { case (usewid, >_< cwustews) => keyvaw(usewid, >w< c-cwustews) }
        .wwitedawvewsionedkeyvawexecution(
          p-pwoducewtopkembeddingsdataset, /(^•ω•^)
          d.suffix(pwoducewtopkembeddingspath)
        )

    v-vaw keyedbysimcwustew = f-fwomsimcwustewembedding(
      pwoducewcwustewembedding, :3
      t-topkusewstokeep, ʘwʘ
      modewvewsion
    ).map {
        c-case (cwustewid, (˘ω˘) toppwoducews) => keyvaw(cwustewid, (ꈍᴗꈍ) t-toppwoducewstothwift(toppwoducews))
      }
      .wwitedawvewsionedkeyvawexecution(
        cwustewtopkpwoducewsdataset, ^^
        d-d.suffix(cwustewtopkpwoducewspath)
      )

    execution.zip(keyedbypwoducew, ^^ k-keyedbysimcwustew).unit
  }
}

/**
 * b-base cwass fow fav based pwoducew embeddings. ( ͡o ω ͡o ) hewps weuse the code fow diffewent modew vewsions
 */
t-twait pwoducewembeddingsfwomintewestedinbyfavscowebase e-extends scheduwedexecutionapp {
  i-impowt p-pwoducewembeddingsfwomintewestedin._
  i-impowt pwoducewembeddingsfwomintewestedinbatchapputiw._

  def modewvewsion: modewvewsion

  v-vaw pwoducewtopkembeddingsbyfavscowepathpwefix: stwing =
    "/pwoducew_top_k_simcwustew_embeddings_by_fav_scowe_"

  vaw cwustewtopkpwoducewsbyfavscowepathpwefix: stwing =
    "/simcwustew_embedding_top_k_pwoducews_by_fav_scowe_"

  v-vaw minnumfavews: int = minnumfavewsfowpwoducew

  d-def pwoducewtopksimcwustewembeddingsbyfavscowedataset: k-keyvawdawdataset[
    k-keyvaw[wong, -.- topsimcwustewswithscowe]
  ]

  def s-simcwustewembeddingtopkpwoducewsbyfavscowedataset: k-keyvawdawdataset[
    k-keyvaw[pewsistedfuwwcwustewid, ^^;; t-toppwoducewswithscowe]
  ]

  def getintewestedinfn: (datewange, ^•ﻌ•^ timezone) => t-typedpipe[(wong, (˘ω˘) c-cwustewsusewisintewestedin)]

  o-ovewwide d-def wunondatewange(
    a-awgs: awgs
  )(
    impwicit datewange: datewange, o.O
    t-timezone: timezone, (✿oωo)
    uniqueid: uniqueid
  ): execution[unit] = {

    vaw pwoducewtopkembeddingsbyfavscowepathupdated: stwing =
      w-wootpath + pwoducewtopkembeddingsbyfavscowepathpwefix + modewvewsions
        .toknownfowmodewvewsion(modewvewsion)

    vaw cwustewtopkpwoducewsbyfavscowepathupdated: s-stwing =
      w-wootpath + cwustewtopkpwoducewsbyfavscowepathpwefix + m-modewvewsions
        .toknownfowmodewvewsion(modewvewsion)

    vaw pwoducewcwustewembeddingbyfavscowe = g-getpwoducewcwustewembedding(
      getintewestedinfn(datewange.embiggen(days(5)), 😳😳😳 t-timezone), (ꈍᴗꈍ)
      d-datasouwces.usewusewnowmawizedgwaphsouwce, σωσ
      datasouwces.usewnowmsandcounts, UwU
      usewtopwoducewfavscowe,
      usewtocwustewfavscowe, ^•ﻌ•^ // fav scowe
      _.favewcount.exists(_ > minnumfavews), mya
      nyumweducewsfowmatwixmuwtipwication, /(^•ω•^)
      m-modewvewsion, rawr
      cosinesimiwawitythweshowd
    ).fowcetodisk

    wwiteoutput(
      p-pwoducewcwustewembeddingbyfavscowe, nyaa~~
      pwoducewtopksimcwustewembeddingsbyfavscowedataset, ( ͡o ω ͡o )
      s-simcwustewembeddingtopkpwoducewsbyfavscowedataset, σωσ
      p-pwoducewtopkembeddingsbyfavscowepathupdated, (✿oωo)
      cwustewtopkpwoducewsbyfavscowepathupdated, (///ˬ///✿)
      modewvewsion
    )
  }
}

/**
 * b-base cwass fow f-fowwow based pwoducew embeddings. σωσ h-hewps weuse t-the code fow diffewent modew vewsions
 */
twait pwoducewembeddingsfwomintewestedinbyfowwowscowebase extends scheduwedexecutionapp {
  i-impowt pwoducewembeddingsfwomintewestedin._
  i-impowt pwoducewembeddingsfwomintewestedinbatchapputiw._

  def m-modewvewsion: modewvewsion

  v-vaw pwoducewtopkembeddingsbyfowwowscowepathpwefix: s-stwing =
    "/pwoducew_top_k_simcwustew_embeddings_by_fowwow_scowe_"

  vaw c-cwustewtopkpwoducewsbyfowwowscowepathpwefix: stwing =
    "/simcwustew_embedding_top_k_pwoducews_by_fowwow_scowe_"

  def pwoducewtopksimcwustewembeddingsbyfowwowscowedataset: keyvawdawdataset[
    keyvaw[wong, UwU t-topsimcwustewswithscowe]
  ]

  d-def simcwustewembeddingtopkpwoducewsbyfowwowscowedataset: keyvawdawdataset[
    keyvaw[pewsistedfuwwcwustewid, (⑅˘꒳˘) t-toppwoducewswithscowe]
  ]

  d-def getintewestedinfn: (datewange, /(^•ω•^) timezone) => typedpipe[(wong, -.- cwustewsusewisintewestedin)]

  v-vaw minnumfowwowews: int = minnumfowwowewsfowpwoducew

  ovewwide def wunondatewange(
    awgs: a-awgs
  )(
    impwicit datewange: datewange, (ˆ ﻌ ˆ)♡
    t-timezone: timezone, nyaa~~
    u-uniqueid: uniqueid
  ): execution[unit] = {

    vaw p-pwoducewtopkembeddingsbyfowwowscowepath: s-stwing =
      wootpath + pwoducewtopkembeddingsbyfowwowscowepathpwefix + modewvewsions
        .toknownfowmodewvewsion(modewvewsion)

    v-vaw cwustewtopkpwoducewsbyfowwowscowepath: stwing =
      wootpath + c-cwustewtopkpwoducewsbyfowwowscowepathpwefix + modewvewsions
        .toknownfowmodewvewsion(modewvewsion)

    vaw pwoducewcwustewembeddingbyfowwowscowe = getpwoducewcwustewembedding(
      g-getintewestedinfn(datewange.embiggen(days(5)), ʘwʘ timezone), :3
      d-datasouwces.usewusewnowmawizedgwaphsouwce, (U ᵕ U❁)
      d-datasouwces.usewnowmsandcounts,
      usewtopwoducewfowwowscowe, (U ﹏ U)
      usewtocwustewfowwowscowe, ^^ // f-fowwow scowe
      _.fowwowewcount.exists(_ > m-minnumfowwowews), òωó
      n-nyumweducewsfowmatwixmuwtipwication, /(^•ω•^)
      m-modewvewsion, 😳😳😳
      cosinesimiwawitythweshowd
    ).fowcetodisk

    w-wwiteoutput(
      p-pwoducewcwustewembeddingbyfowwowscowe,
      pwoducewtopksimcwustewembeddingsbyfowwowscowedataset, :3
      simcwustewembeddingtopkpwoducewsbyfowwowscowedataset, (///ˬ///✿)
      p-pwoducewtopkembeddingsbyfowwowscowepath, rawr x3
      c-cwustewtopkpwoducewsbyfowwowscowepath, (U ᵕ U❁)
      m-modewvewsion
    )
  }
}

/**
 capesospy-v2 update --buiwd_wocawwy --stawt_cwon \
 --stawt_cwon p-pwoducew_embeddings_fwom_intewested_in_by_fav_scowe \
 swc/scawa/com/twittew/simcwustews_v2/capesos_config/atwa_pwoc3.yamw
 */
o-object pwoducewembeddingsfwomintewestedinbyfavscowebatchapp
    e-extends pwoducewembeddingsfwomintewestedinbyfavscowebase {
  ovewwide def modewvewsion: modewvewsion = modewvewsion.modew20m145kupdated

  o-ovewwide def getintewestedinfn: (
    d-datewange, (⑅˘꒳˘)
    t-timezone
  ) => t-typedpipe[(usewid, cwustewsusewisintewestedin)] =
    i-intewestedinsouwces.simcwustewsintewestedinupdatedsouwce

  ovewwide vaw fiwsttime: wichdate = wichdate("2019-09-10")

  ovewwide vaw batchincwement: d-duwation = days(7)

  ovewwide d-def pwoducewtopksimcwustewembeddingsbyfavscowedataset: keyvawdawdataset[
    k-keyvaw[wong, (˘ω˘) topsimcwustewswithscowe]
  ] =
    p-pwoducewtopksimcwustewembeddingsbyfavscoweupdatedscawadataset

  ovewwide d-def simcwustewembeddingtopkpwoducewsbyfavscowedataset: k-keyvawdawdataset[
    k-keyvaw[pewsistedfuwwcwustewid, :3 t-toppwoducewswithscowe]
  ] =
    s-simcwustewembeddingtopkpwoducewsbyfavscoweupdatedscawadataset
}

/**
capesospy-v2 update --buiwd_wocawwy --stawt_cwon \
 --stawt_cwon pwoducew_embeddings_fwom_intewested_in_by_fav_scowe_2020 \
 swc/scawa/com/twittew/simcwustews_v2/capesos_config/atwa_pwoc3.yamw
 */
object pwoducewembeddingsfwomintewestedinbyfavscowe2020batchapp
    e-extends pwoducewembeddingsfwomintewestedinbyfavscowebase {
  ovewwide d-def modewvewsion: m-modewvewsion = modewvewsion.modew20m145k2020

  o-ovewwide def getintewestedinfn: (
    datewange, XD
    timezone
  ) => typedpipe[(usewid, >_< cwustewsusewisintewestedin)] =
    i-intewestedinsouwces.simcwustewsintewestedin2020souwce

  o-ovewwide vaw fiwsttime: w-wichdate = wichdate("2021-03-01")

  ovewwide v-vaw batchincwement: d-duwation = days(7)

  ovewwide d-def pwoducewtopksimcwustewembeddingsbyfavscowedataset: k-keyvawdawdataset[
    keyvaw[wong, (✿oωo) topsimcwustewswithscowe]
  ] =
    pwoducewtopksimcwustewembeddingsbyfavscowe2020scawadataset

  ovewwide def simcwustewembeddingtopkpwoducewsbyfavscowedataset: k-keyvawdawdataset[
    k-keyvaw[pewsistedfuwwcwustewid, (ꈍᴗꈍ) t-toppwoducewswithscowe]
  ] =
    s-simcwustewembeddingtopkpwoducewsbyfavscowe2020scawadataset
}

/**
c-capesospy-v2 update --buiwd_wocawwy --stawt_cwon \
 --stawt_cwon p-pwoducew_embeddings_fwom_intewested_in_by_fav_scowe_dec11 \
 s-swc/scawa/com/twittew/simcwustews_v2/capesos_config/atwa_pwoc3.yamw
 */
object pwoducewembeddingsfwomintewestedinbyfavscowedec11batchapp
    e-extends pwoducewembeddingsfwomintewestedinbyfavscowebase {
  o-ovewwide def modewvewsion: modewvewsion = m-modewvewsion.modew20m145kdec11

  ovewwide def getintewestedinfn: (
    d-datewange, XD
    timezone
  ) => t-typedpipe[(usewid, c-cwustewsusewisintewestedin)] =
    intewestedinsouwces.simcwustewsintewestedindec11souwce

  o-ovewwide vaw fiwsttime: wichdate = wichdate("2019-11-18")

  o-ovewwide vaw batchincwement: d-duwation = d-days(7)

  ovewwide def pwoducewtopksimcwustewembeddingsbyfavscowedataset: keyvawdawdataset[
    k-keyvaw[wong, :3 topsimcwustewswithscowe]
  ] =
    pwoducewtopksimcwustewembeddingsbyfavscowescawadataset

  o-ovewwide def s-simcwustewembeddingtopkpwoducewsbyfavscowedataset: keyvawdawdataset[
    k-keyvaw[pewsistedfuwwcwustewid, mya toppwoducewswithscowe]
  ] =
    s-simcwustewembeddingtopkpwoducewsbyfavscowescawadataset
}

/**
c-capesospy-v2 update --buiwd_wocawwy --stawt_cwon \
 --stawt_cwon pwoducew_embeddings_fwom_intewested_in_by_fowwow_scowe \
 s-swc/scawa/com/twittew/simcwustews_v2/capesos_config/atwa_pwoc3.yamw
 */
object pwoducewembeddingsfwomintewestedinbyfowwowscowebatchapp
    e-extends p-pwoducewembeddingsfwomintewestedinbyfowwowscowebase {
  ovewwide d-def modewvewsion: modewvewsion = m-modewvewsion.modew20m145kupdated

  o-ovewwide d-def getintewestedinfn: (
    datewange, òωó
    timezone
  ) => typedpipe[(usewid, nyaa~~ cwustewsusewisintewestedin)] =
    intewestedinsouwces.simcwustewsintewestedinupdatedsouwce

  ovewwide vaw fiwsttime: wichdate = wichdate("2019-09-10")

  ovewwide vaw batchincwement: duwation = days(7)

  ovewwide def pwoducewtopksimcwustewembeddingsbyfowwowscowedataset: k-keyvawdawdataset[
    k-keyvaw[wong, 🥺 topsimcwustewswithscowe]
  ] =
    pwoducewtopksimcwustewembeddingsbyfowwowscoweupdatedscawadataset

  ovewwide d-def simcwustewembeddingtopkpwoducewsbyfowwowscowedataset: k-keyvawdawdataset[
    k-keyvaw[pewsistedfuwwcwustewid, -.- toppwoducewswithscowe]
  ] =
    s-simcwustewembeddingtopkpwoducewsbyfowwowscoweupdatedscawadataset
}

/**
capesospy-v2 update --buiwd_wocawwy --stawt_cwon \
 --stawt_cwon p-pwoducew_embeddings_fwom_intewested_in_by_fowwow_scowe_2020 \
 s-swc/scawa/com/twittew/simcwustews_v2/capesos_config/atwa_pwoc3.yamw
 */
object pwoducewembeddingsfwomintewestedinbyfowwowscowe2020batchapp
    extends p-pwoducewembeddingsfwomintewestedinbyfowwowscowebase {
  ovewwide def modewvewsion: m-modewvewsion = m-modewvewsion.modew20m145k2020

  ovewwide def getintewestedinfn: (
    d-datewange, 🥺
    timezone
  ) => typedpipe[(usewid, (˘ω˘) c-cwustewsusewisintewestedin)] =
    i-intewestedinsouwces.simcwustewsintewestedin2020souwce

  o-ovewwide v-vaw fiwsttime: w-wichdate = w-wichdate("2021-03-01")

  o-ovewwide v-vaw batchincwement: duwation = d-days(7)

  ovewwide d-def pwoducewtopksimcwustewembeddingsbyfowwowscowedataset: k-keyvawdawdataset[
    keyvaw[wong, t-topsimcwustewswithscowe]
  ] =
    pwoducewtopksimcwustewembeddingsbyfowwowscowe2020scawadataset

  ovewwide d-def simcwustewembeddingtopkpwoducewsbyfowwowscowedataset: keyvawdawdataset[
    k-keyvaw[pewsistedfuwwcwustewid, òωó t-toppwoducewswithscowe]
  ] =
    s-simcwustewembeddingtopkpwoducewsbyfowwowscowe2020scawadataset
}

/**
capesospy-v2 u-update --buiwd_wocawwy --stawt_cwon \
 --stawt_cwon pwoducew_embeddings_fwom_intewested_in_by_fowwow_scowe_dec11 \
 s-swc/scawa/com/twittew/simcwustews_v2/capesos_config/atwa_pwoc3.yamw
 */
object pwoducewembeddingsfwomintewestedinbyfowwowscowedec11batchapp
    e-extends pwoducewembeddingsfwomintewestedinbyfowwowscowebase {
  ovewwide d-def modewvewsion: modewvewsion = modewvewsion.modew20m145kdec11

  ovewwide def getintewestedinfn: (
    d-datewange, UwU
    timezone
  ) => t-typedpipe[(usewid, ^•ﻌ•^ c-cwustewsusewisintewestedin)] =
    intewestedinsouwces.simcwustewsintewestedindec11souwce

  ovewwide vaw fiwsttime: w-wichdate = wichdate("2019-11-18")

  ovewwide vaw b-batchincwement: d-duwation = days(7)

  o-ovewwide def pwoducewtopksimcwustewembeddingsbyfowwowscowedataset: keyvawdawdataset[
    k-keyvaw[wong, mya topsimcwustewswithscowe]
  ] =
    p-pwoducewtopksimcwustewembeddingsbyfowwowscowescawadataset

  ovewwide d-def simcwustewembeddingtopkpwoducewsbyfowwowscowedataset: keyvawdawdataset[
    keyvaw[pewsistedfuwwcwustewid, (✿oωo) t-toppwoducewswithscowe]
  ] =
    simcwustewembeddingtopkpwoducewsbyfowwowscowescawadataset
}

/**
 * a-adhoc j-job to cawcuwate p-pwoducew's simcwustew embeddings, XD w-which essentiawwy a-assigns intewestedin
 * s-simcwustews t-to each pwoducew, :3 wegawdwess o-of whethew t-the pwoducew has a-a knownfow assignment. (U ﹏ U)
 *
$ ./bazew b-bundwe swc/scawa/com/twittew/simcwustews_v2/scawding/embedding:pwoducew_embeddings_fwom_intewested_in-adhoc

 $ s-scawding w-wemote wun \
 --main-cwass c-com.twittew.simcwustews_v2.scawding.embedding.pwoducewembeddingsfwomintewestedinadhocapp \
 --tawget s-swc/scawa/com/twittew/simcwustews_v2/scawding/embedding:pwoducew_embeddings_fwom_intewested_in-adhoc \
 --usew cassowawy --cwustew bwuebiwd-qus1 \
 --keytab /vaw/wib/tss/keys/fwoofy/keytabs/cwient/cassowawy.keytab \
 --pwincipaw s-sewvice_acoount@twittew.biz \
 -- --date 2020-08-25 --modew_vewsion 20m_145k_updated \
 --outputdiw /gcs/usew/cassowawy/adhoc/pwoducewembeddings/

 */
object p-pwoducewembeddingsfwomintewestedinadhocapp extends a-adhocexecutionapp {

  i-impowt p-pwoducewembeddingsfwomintewestedin._

  pwivate vaw nyumweducewsfowmatwixmuwtipwication = 12000

  /**
   * cawcuwate the embedding a-and wwites t-the wesuwts keyed b-by pwoducews and cwustews sepawatewy into
   * individuaw wocations
   */
  p-pwivate def wunadhocbyscowe(
    i-intewestedincwustews: typedpipe[(wong, UwU c-cwustewsusewisintewestedin)], ʘwʘ
    u-usewusewnowmawgwaph: typedpipe[usewandneighbows], >w<
    usewnowmsandcounts: typedpipe[nowmsandcounts], 😳😳😳
    k-keyedbypwoducewsinkpath: s-stwing, rawr
    k-keyedbycwustewsinkpath: s-stwing, ^•ﻌ•^
    usewtopwoducewscowingfn: nyeighbowwithweights => doubwe, σωσ
    u-usewtocwustewscowingfn: u-usewtointewestedincwustewscowes => doubwe, :3
    usewfiwtew: nyowmsandcounts => b-boowean, rawr x3
    modewvewsion: modewvewsion
  )(
    impwicit uniqueid: u-uniqueid
  ): execution[unit] = {

    v-vaw pwoducewcwustewembedding = g-getpwoducewcwustewembedding(
      intewestedincwustews, nyaa~~
      u-usewusewnowmawgwaph, :3
      u-usewnowmsandcounts, >w<
      usewtopwoducewscowingfn, rawr
      u-usewtocwustewscowingfn, 😳
      usewfiwtew, 😳
      n-nyumweducewsfowmatwixmuwtipwication, 🥺
      m-modewvewsion, rawr x3
      c-cosinesimiwawitythweshowd
    ).fowcetodisk

    v-vaw keybypwoducewexec =
      t-tosimcwustewembedding(pwoducewcwustewembedding, ^^ t-topkcwustewstokeep, ( ͡o ω ͡o ) modewvewsion)
        .wwiteexecution(
          a-adhockeyvawsouwces.toppwoducewtocwustewembeddingssouwce(keyedbypwoducewsinkpath))

    vaw keybycwustewexec =
      f-fwomsimcwustewembedding(pwoducewcwustewembedding, XD topkusewstokeep, ^^ modewvewsion)
        .map { c-case (cwustewid, (⑅˘꒳˘) t-toppwoducews) => (cwustewid, (⑅˘꒳˘) t-toppwoducewstothwift(toppwoducews)) }
        .wwiteexecution(
          adhockeyvawsouwces.topcwustewembeddingstopwoducewsouwce(keyedbycwustewsinkpath))

    execution.zip(keybypwoducewexec, ^•ﻌ•^ keybycwustewexec).unit
  }

  // cawcuwate the e-embeddings using fowwow scowes
  p-pwivate def wunfowwowscowe(
    i-intewestedincwustews: typedpipe[(wong, ( ͡o ω ͡o ) cwustewsusewisintewestedin)], ( ͡o ω ͡o )
    u-usewusewnowmawgwaph: typedpipe[usewandneighbows], (✿oωo)
    u-usewnowmsandcounts: t-typedpipe[nowmsandcounts], 😳😳😳
    m-modewvewsion: m-modewvewsion, OwO
    o-outputdiw: stwing
  )(
    impwicit uniqueid: uniqueid
  ): execution[unit] = {
    vaw keybycwustewsinkpath = o-outputdiw + "keyedbycwustew/byfowwowscowe_" + modewvewsion
    v-vaw keybypwoducewsinkpath = outputdiw + "keyedbypwoducew/byfowwowscowe_" + modewvewsion

    wunadhocbyscowe(
      intewestedincwustews, ^^
      u-usewusewnowmawgwaph, rawr x3
      usewnowmsandcounts, 🥺
      keyedbypwoducewsinkpath = keybypwoducewsinkpath, (ˆ ﻌ ˆ)♡
      keyedbycwustewsinkpath = k-keybycwustewsinkpath, ( ͡o ω ͡o )
      u-usewtopwoducewscowingfn = usewtopwoducewfowwowscowe, >w<
      u-usewtocwustewscowingfn = usewtocwustewfowwowscowe, /(^•ω•^)
      _.fowwowewcount.exists(_ > minnumfowwowewsfowpwoducew), 😳😳😳
      m-modewvewsion
    )
  }

  // c-cawcuwate the embeddings using f-fav scowes
  pwivate def wunfavscowe(
    i-intewestedincwustews: typedpipe[(wong, (U ᵕ U❁) cwustewsusewisintewestedin)],
    usewusewnowmawgwaph: t-typedpipe[usewandneighbows], (˘ω˘)
    usewnowmsandcounts: typedpipe[nowmsandcounts], 😳
    m-modewvewsion: m-modewvewsion, (ꈍᴗꈍ)
    o-outputdiw: stwing
  )(
    impwicit u-uniqueid: uniqueid
  ): execution[unit] = {
    vaw keybycwustewsinkpath = outputdiw + "keyedbycwustew/byfavscowe_" + modewvewsion
    v-vaw keybypwoducewsinkpath = o-outputdiw + "keyedbypwoducew/byfavscowe_" + m-modewvewsion

    w-wunadhocbyscowe(
      intewestedincwustews, :3
      usewusewnowmawgwaph, /(^•ω•^)
      u-usewnowmsandcounts, ^^;;
      k-keyedbypwoducewsinkpath = keybypwoducewsinkpath, o.O
      keyedbycwustewsinkpath = k-keybycwustewsinkpath, 😳
      usewtopwoducewscowingfn = usewtopwoducewfavscowe,
      u-usewtocwustewscowingfn = usewtocwustewfavscowe, UwU
      _.favewcount.exists(_ > minnumfavewsfowpwoducew),
      m-modewvewsion
    )
  }

  o-ovewwide def wunondatewange(
    a-awgs: awgs
  )(
    i-impwicit d-datewange: datewange, >w<
    timezone: timezone, o.O
    u-uniqueid: uniqueid
  ): execution[unit] = {
    vaw outputdiw = a-awgs("outputdiw")

    vaw modewvewsion =
      modewvewsions.tomodewvewsion(awgs.wequiwed("modew_vewsion"))

    v-vaw intewestedincwustews = m-modewvewsion m-match {
      case m-modewvewsion.modew20m145k2020 =>
        i-intewestedinsouwces.simcwustewsintewestedin2020souwce(datewange, (˘ω˘) timezone).fowcetodisk
      c-case modewvewsion.modew20m145kupdated =>
        intewestedinsouwces.simcwustewsintewestedinupdatedsouwce(datewange, òωó timezone).fowcetodisk
      c-case _ =>
        intewestedinsouwces.simcwustewsintewestedindec11souwce(datewange, nyaa~~ t-timezone).fowcetodisk
    }

    execution
      .zip(
        wunfavscowe(
          intewestedincwustews, ( ͡o ω ͡o )
          d-datasouwces.usewusewnowmawizedgwaphsouwce, 😳😳😳
          d-datasouwces.usewnowmsandcounts, ^•ﻌ•^
          modewvewsion, (˘ω˘)
          o-outputdiw
        ), (˘ω˘)
        wunfowwowscowe(
          i-intewestedincwustews, -.-
          d-datasouwces.usewusewnowmawizedgwaphsouwce, ^•ﻌ•^
          datasouwces.usewnowmsandcounts, /(^•ω•^)
          m-modewvewsion, (///ˬ///✿)
          o-outputdiw
        )
      ).unit
  }
}

/**
 * computes t-the pwoducew's intewestedin cwustew embedding. mya i.e. if a tweet a-authow (pwoducew) is nyot
 * associated w-with a knownfow cwustew, o.O do a cwoss-pwoduct b-between
 * [usew, ^•ﻌ•^ i-intewestedin] a-and [usew, (U ᵕ U❁) pwoducew] to find t-the simiwawity m-matwix [intewestedin, :3 pwoducew]. (///ˬ///✿)
 */
o-object pwoducewembeddingsfwomintewestedin {
  vaw minnumfowwowewsfowpwoducew: i-int = 100
  vaw minnumfavewsfowpwoducew: i-int = 100
  v-vaw topkusewstokeep: int = 300
  vaw topkcwustewstokeep: int = 60
  vaw cosinesimiwawitythweshowd: d-doubwe = 0.01

  t-type cwustewid = int

  def toppwoducewstothwift(pwoducewswithscowe: seq[(usewid, doubwe)]): t-toppwoducewswithscowe = {
    vaw thwift = p-pwoducewswithscowe.map { p-pwoducew =>
      toppwoducewwithscowe(pwoducew._1, (///ˬ///✿) pwoducew._2)
    }
    toppwoducewswithscowe(thwift)
  }

  def usewtopwoducewfavscowe(neighbow: n-nyeighbowwithweights): doubwe = {
    neighbow.favscowehawfwife100daysnowmawizedbyneighbowfavewsw2.getowewse(0.0)
  }

  d-def usewtopwoducewfowwowscowe(neighbow: nyeighbowwithweights): d-doubwe = {
    n-neighbow.fowwowscowenowmawizedbyneighbowfowwowewsw2.getowewse(0.0)
  }

  def usewtocwustewfavscowe(cwustewscowe: u-usewtointewestedincwustewscowes): d-doubwe = {
    c-cwustewscowe.favscowecwustewnowmawizedonwy.getowewse(0.0)
  }

  d-def u-usewtocwustewfowwowscowe(cwustewscowe: u-usewtointewestedincwustewscowes): doubwe = {
    cwustewscowe.fowwowscowecwustewnowmawizedonwy.getowewse(0.0)
  }

  def getusewsimcwustewsmatwix(
    simcwustewssouwce: typedpipe[(usewid, 🥺 c-cwustewsusewisintewestedin)], -.-
    e-extwactscowe: u-usewtointewestedincwustewscowes => d-doubwe, nyaa~~
    m-modewvewsion: m-modewvewsion
  ): typedpipe[(usewid, (///ˬ///✿) seq[(int, 🥺 doubwe)])] = {
    simcwustewssouwce.cowwect {
      c-case (usewid, >w< c-cwustews)
          if modewvewsions.tomodewvewsion(cwustews.knownfowmodewvewsion).equaws(modewvewsion) =>
        usewid -> cwustews.cwustewidtoscowes
          .map {
            c-case (cwustewid, rawr x3 c-cwustewscowes) =>
              (cwustewid, (⑅˘꒳˘) e-extwactscowe(cwustewscowes))
          }.toseq.fiwtew(_._2 > 0)
    }
  }

  /**
   * given a weighted usew-pwoducew e-engagement histowy matwix, σωσ as weww as a-a
   * weighted u-usew-intewestedincwustew matwix, XD do the matwix m-muwtipwication to yiewd a weighted
   * p-pwoducew-cwustew e-embedding matwix
   */
  d-def getpwoducewcwustewembedding(
    i-intewestedincwustews: t-typedpipe[(usewid, -.- c-cwustewsusewisintewestedin)], >_<
    u-usewpwoducewengagementgwaph: typedpipe[usewandneighbows], rawr
    u-usewnowmsandcounts: typedpipe[nowmsandcounts], 😳😳😳
    u-usewtopwoducewscowingfn: n-nyeighbowwithweights => doubwe, UwU
    u-usewtocwustewscowingfn: usewtointewestedincwustewscowes => doubwe, (U ﹏ U)
    u-usewfiwtew: nyowmsandcounts => b-boowean, (˘ω˘) // function to decide w-whethew to c-compute embeddings fow the usew ow nyot
    numweducewsfowmatwixmuwtipwication: i-int, /(^•ω•^)
    modewvewsion: modewvewsion, (U ﹏ U)
    thweshowd: d-doubwe
  )(
    i-impwicit uid: uniqueid
  ): typedpipe[((cwustewid, ^•ﻌ•^ u-usewid), d-doubwe)] = {
    vaw usewsimcwustewsmatwix = g-getusewsimcwustewsmatwix(
      intewestedincwustews, >w<
      usewtocwustewscowingfn, ʘwʘ
      m-modewvewsion
    )

    vaw u-usewusewnowmawizedgwaph = getfiwtewedusewusewnowmawizedgwaph(
      u-usewpwoducewengagementgwaph, òωó
      u-usewnowmsandcounts, o.O
      usewtopwoducewscowingfn, ( ͡o ω ͡o )
      usewfiwtew
    )

    s-simcwustewsembeddingjob
      .wegacymuwtipwymatwices(
        u-usewusewnowmawizedgwaph, mya
        u-usewsimcwustewsmatwix, >_<
        n-nyumweducewsfowmatwixmuwtipwication
      )
      .fiwtew(_._2 >= thweshowd)
  }

  def getfiwtewedusewusewnowmawizedgwaph(
    usewpwoducewengagementgwaph: typedpipe[usewandneighbows], rawr
    usewnowmsandcounts: t-typedpipe[nowmsandcounts], >_<
    u-usewtopwoducewscowingfn: n-nyeighbowwithweights => d-doubwe, (U ﹏ U)
    u-usewfiwtew: n-nyowmsandcounts => boowean
  )(
    i-impwicit uid: u-uniqueid
  ): typedpipe[(usewid, rawr (usewid, (U ᵕ U❁) d-doubwe))] = {
    v-vaw nyumusewscount = stat("num_usews_with_engagements")
    vaw u-usewusewfiwtewededgecount = stat("num_fiwtewed_usew_usew_engagements")
    vaw vawidusewscount = s-stat("num_vawid_usews")

    vaw v-vawidusews = usewnowmsandcounts.cowwect {
      c-case usew if usewfiwtew(usew) =>
        vawidusewscount.inc()
        u-usew.usewid
    }

    u-usewpwoducewengagementgwaph
      .fwatmap { u-usewandneighbows =>
        nyumusewscount.inc()
        u-usewandneighbows.neighbows
          .map { n-nyeighbow =>
            usewusewfiwtewededgecount.inc()
            (neighbow.neighbowid, (ˆ ﻌ ˆ)♡ (usewandneighbows.usewid, >_< u-usewtopwoducewscowingfn(neighbow)))
          }
          .fiwtew(_._2._2 > 0.0)
      }
      .join(vawidusews.askeys)
      .map {
        case (neighbowid, ^^;; ((usewid, s-scowe), ʘwʘ _)) =>
          (usewid, (neighbowid, 😳😳😳 scowe))
      }
  }

  d-def fwomsimcwustewembedding[t, UwU e-e](
    wesuwtmatwix: typedpipe[((cwustewid, OwO t-t), doubwe)], :3
    topk: int, -.-
    modewvewsion: m-modewvewsion
  ): typedpipe[(pewsistedfuwwcwustewid, 🥺 seq[(t, -.- doubwe)])] = {
    wesuwtmatwix
      .map {
        case ((cwustewid, -.- inputid), (U ﹏ U) scowe) => (cwustewid, rawr (inputid, mya scowe))
      }
      .gwoup
      .sowtedwevewsetake(topk)(owdewing.by(_._2))
      .map {
        case (cwustewid, ( ͡o ω ͡o ) t-topentitieswithscowe) =>
          pewsistedfuwwcwustewid(modewvewsion, /(^•ω•^) cwustewid) -> topentitieswithscowe
      }
  }

  def tosimcwustewembedding[t](
    wesuwtmatwix: typedpipe[((cwustewid, >_< t-t), (✿oωo) doubwe)],
    topk: int, 😳😳😳
    modewvewsion: m-modewvewsion
  )(
    impwicit o-owdewing: owdewing[t]
  ): typedpipe[(t, (ꈍᴗꈍ) topsimcwustewswithscowe)] = {
    w-wesuwtmatwix
      .map {
        case ((cwustewid, 🥺 i-inputid), scowe) => (inputid, mya (cwustewid, scowe))
      }
      .gwoup
      //.withweducews(3000) // u-uncomment f-fow pwoducew-simcwustews job
      .sowtedwevewsetake(topk)(owdewing.by(_._2))
      .map {
        case (inputid, (ˆ ﻌ ˆ)♡ t-topsimcwustewswithscowe) =>
          vaw topsimcwustews = topsimcwustewswithscowe.map {
            case (cwustewid, (⑅˘꒳˘) s-scowe) => simcwustewwithscowe(cwustewid, òωó s-scowe)
          }
          inputid -> topsimcwustewswithscowe(topsimcwustews, m-modewvewsion)
      }
  }
}
