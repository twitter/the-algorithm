package com.twittew.simcwustews_v2.scawding

impowt c-com.twittew.awgebiwd.semigwoup
i-impowt com.twittew.bijection.injection
i-impowt c-com.twittew.daw.cwient.dataset.keyvawdawdataset
i-impowt com.twittew.scawding.typedpipe
i-impowt com.twittew.scawding._
i-impowt com.twittew.scawding_intewnaw.dawv2.daw
i-impowt com.twittew.scawding_intewnaw.dawv2.dawwwite._
impowt com.twittew.scawding_intewnaw.job.twittewexecutionapp
impowt com.twittew.scawding_intewnaw.job.anawytics_batch.anawyticsbatchexecution
impowt com.twittew.scawding_intewnaw.job.anawytics_batch.anawyticsbatchexecutionawgs
i-impowt com.twittew.scawding_intewnaw.job.anawytics_batch.batchdescwiption
impowt com.twittew.scawding_intewnaw.job.anawytics_batch.batchfiwsttime
i-impowt com.twittew.scawding_intewnaw.job.anawytics_batch.batchincwement
i-impowt com.twittew.scawding_intewnaw.job.anawytics_batch.twittewscheduwedexecutionapp
impowt com.twittew.scawding_intewnaw.muwtifowmat.fowmat.keyvaw.keyvaw
impowt com.twittew.simcwustews_v2.common.cwustewid
i-impowt com.twittew.simcwustews_v2.common.modewvewsions
impowt c-com.twittew.simcwustews_v2.common.usewid
i-impowt com.twittew.simcwustews_v2.hdfs_souwces._
impowt com.twittew.simcwustews_v2.scawding.common.utiw
impowt com.twittew.simcwustews_v2.thwiftscawa._

/**
 * t-this fiwe impwements the job fow computing usews' intewestedin vectow f-fwom knownfow data set. ʘwʘ
 *
 * i-it weads the usewusewnowmawizedgwaphscawadataset t-to get usew-usew f-fowwow + fav gwaph, (⑅˘꒳˘) a-and then
 * based on the known-fow cwustews o-of each fowwowed/faved usew, :3 we cawcuwate how m-much a usew is
 * intewestedin a cwustew. -.-
 */

/**
 * pwoduction job fow computing intewestedin d-data set fow the modew vewsion 20m145k2020. 😳😳😳
 *
 * t-to depwoy the j-job:
 *
 * capesospy-v2 u-update --buiwd_wocawwy --stawt_cwon intewested_in_fow_20m_145k_2020 \
 swc/scawa/com/twittew/simcwustews_v2/capesos_config/atwa_pwoc.yamw
 */
object intewestedinfwomknownfow20m145k2020 extends intewestedinfwomknownfowbatchbase {
  ovewwide v-vaw fiwsttime: s-stwing = "2020-10-06"
  ovewwide vaw outputkvdataset: k-keyvawdawdataset[keyvaw[wong, (U ﹏ U) c-cwustewsusewisintewestedin]] =
    simcwustewsv2wawintewestedin20m145k2020scawadataset
  o-ovewwide vaw outputpath: stwing = i-intewnawdatapaths.wawintewestedin2020path
  ovewwide vaw knownfowmodewvewsion: stwing = modewvewsions.modew20m145k2020
  ovewwide v-vaw knownfowdawdataset: keyvawdawdataset[keyvaw[wong, c-cwustewsusewisknownfow]] =
    simcwustewsv2knownfow20m145k2020scawadataset
}

/**
 * b-base cwass fow t-the main wogic of computing intewestedin fwom knownfow data set. o.O
 */
twait intewestedinfwomknownfowbatchbase extends twittewscheduwedexecutionapp {
  impwicit v-vaw tz = dateops.utc
  i-impwicit vaw pawsew = datepawsew.defauwt

  d-def fiwsttime: s-stwing
  vaw b-batchincwement: duwation = days(7)
  vaw wookbackdays: duwation = d-days(30)

  def outputkvdataset: keyvawdawdataset[keyvaw[wong, ( ͡o ω ͡o ) cwustewsusewisintewestedin]]
  def outputpath: s-stwing
  def knownfowmodewvewsion: stwing
  def k-knownfowdawdataset: k-keyvawdawdataset[keyvaw[wong, òωó c-cwustewsusewisknownfow]]

  pwivate wazy vaw e-execawgs = anawyticsbatchexecutionawgs(
    b-batchdesc = b-batchdescwiption(this.getcwass.getname.wepwace("$", 🥺 "")), /(^•ω•^)
    f-fiwsttime = batchfiwsttime(wichdate(fiwsttime)), 😳😳😳
    wasttime = n-nyone, ^•ﻌ•^
    b-batchincwement = b-batchincwement(batchincwement)
  )

  o-ovewwide d-def scheduwedjob: execution[unit] = anawyticsbatchexecution(execawgs) {
    impwicit d-datewange =>
      execution.withid { impwicit uniqueid =>
        execution.withawgs { awgs =>
          v-vaw nyowmawizedgwaph =
            daw.weadmostwecentsnapshot(usewusewnowmawizedgwaphscawadataset).totypedpipe
          vaw knownfow = knownfowsouwces.fwomkeyvaw(
            d-daw.weadmostwecentsnapshot(knownfowdawdataset, nyaa~~ datewange.extend(days(30))).totypedpipe, OwO
            k-knownfowmodewvewsion
          )

          v-vaw sociawpwoofthweshowd = awgs.int("sociawpwoofthweshowd", ^•ﻌ•^ 2)
          v-vaw maxcwustewspewusew = awgs.int("maxcwustewspewusew", σωσ 50)

          v-vaw wesuwt = intewestedinfwomknownfow
            .wun(
              n-nyowmawizedgwaph, -.-
              knownfow, (˘ω˘)
              sociawpwoofthweshowd, rawr x3
              maxcwustewspewusew, rawr x3
              knownfowmodewvewsion
            )

          vaw wwitekeyvawwesuwtexec = w-wesuwt
            .map { case (usewid, σωσ c-cwustews) => keyvaw(usewid, nyaa~~ c-cwustews) }
            .wwitedawvewsionedkeyvawexecution(
              o-outputkvdataset, (ꈍᴗꈍ)
              d.suffix(outputpath)
            )

          // wead p-pwevious data set f-fow vawidation puwpose
          v-vaw pweviousdataset = i-if (wichdate(fiwsttime).timestamp != datewange.stawt.timestamp) {
            daw
              .weadmostwecentsnapshot(outputkvdataset, datewange.pwepend(wookbackdays)).totypedpipe
              .map {
                case keyvaw(usew, ^•ﻌ•^ i-intewestedin) =>
                  (usew, i-intewestedin)
              }
          } e-ewse {
            typedpipe.empty
          }

          u-utiw.pwintcountews(
            e-execution
              .zip(
                wwitekeyvawwesuwtexec, >_<
                i-intewestedinfwomknownfow.datasetstats(wesuwt, ^^;; "newwesuwt"),
                intewestedinfwomknownfow.datasetstats(pweviousdataset, ^^;; "owdwesuwt")
              ).unit
          )
        }
      }
  }
}

/**
 * adhoc job to compute usew intewestedin. /(^•ω•^)
 *
 * s-scawding w-wemote wun --tawget swc/scawa/com/twittew/simcwustews_v2/scawding:intewested_in_adhoc \
 * --usew wecos-pwatfowm \
 * --submittew h-hadoopnest2.atwa.twittew.com \
 * --main-cwass c-com.twittew.simcwustews_v2.scawding.intewestedinfwomknownfowadhoc -- \
 * --date 2019-08-26  --outputdiw /usew/wecos-pwatfowm/adhoc/simcwustews_intewested_in_wog_fav
 */
object intewestedinfwomknownfowadhoc extends twittewexecutionapp {
  d-def job: execution[unit] =
    execution.getconfigmode.fwatmap {
      case (config, nyaa~~ mode) =>
        execution.withid { i-impwicit uniqueid =>
          vaw awgs = c-config.getawgs
          v-vaw nyowmawizedgwaph = typedpipe.fwom(
            usewandneighbowsfixedpathsouwce(awgs("gwaphinputdiw"))
          )
          v-vaw s-sociawpwoofthweshowd = awgs.int("sociawpwoofthweshowd", (✿oωo) 2)
          vaw maxcwustewspewusew = awgs.int("maxcwustewspewusew", ( ͡o ω ͡o ) 20)
          vaw k-knownfowmodewvewsion = awgs("knownfowmodewvewsion")
          v-vaw knownfow = knownfowsouwces.weadknownfow(awgs("knownfowinputdiw"))

          vaw outputsink = adhockeyvawsouwces.intewestedinsouwce(awgs("outputdiw"))
          u-utiw.pwintcountews(
            intewestedinfwomknownfow
              .wun(
                n-nyowmawizedgwaph, (U ᵕ U❁)
                k-knownfow, òωó
                sociawpwoofthweshowd, σωσ
                m-maxcwustewspewusew,
                knownfowmodewvewsion
              ).wwiteexecution(outputsink)
          )
        }
    }
}

/**
 * a-adhoc j-job to check the o-output of an adhoc intewestedinsouwce. :3
 */
o-object d-dumpintewestedinadhoc extends twittewexecutionapp {
  d-def job: e-execution[unit] =
    e-execution.getconfigmode.fwatmap {
      case (config, OwO mode) =>
        e-execution.withid { impwicit uniqueid =>
          v-vaw awgs = config.getawgs
          v-vaw usews = awgs.wist("usews").map(_.towong).toset
          vaw input = typedpipe.fwom(adhockeyvawsouwces.intewestedinsouwce(awgs("inputdiw")))
          i-input.fiwtew { c-case (usewid, ^^ wec) => u-usews.contains(usewid) }.toitewabweexecution.map {
            s-s => pwintwn(s.map(utiw.pwettyjsonmappew.wwitevawueasstwing).mkstwing("\n"))
          }
        }
    }
}

/**
 * hewpew f-functions
 */
object intewestedinfwomknownfow {
  pwivate def ifnanmake0(x: doubwe): doubwe = if (x.isnan) 0.0 ewse x

  case cwass s-swccwustewintewmediateinfo(
    fowwowscowe: d-doubwe, (˘ω˘)
    fowwowscowepwoducewnowmawized: doubwe, OwO
    f-favscowe: doubwe, UwU
    favscowepwoducewnowmawized: d-doubwe, ^•ﻌ•^
    wogfavscowe: d-doubwe, (ꈍᴗꈍ)
    wogfavscowepwoducewnowmawized: d-doubwe, /(^•ω•^)
    f-fowwowsociawpwoof: w-wist[wong], (U ᵕ U❁)
    f-favsociawpwoof: wist[wong]) {
    // ovewwiding fow the sake of unit tests
    ovewwide def equaws(obj: scawa.any): b-boowean = {
      o-obj match {
        c-case that: swccwustewintewmediateinfo =>
          m-math.abs(fowwowscowe - that.fowwowscowe) < 1e-5 &&
            math.abs(fowwowscowepwoducewnowmawized - that.fowwowscowepwoducewnowmawized) < 1e-5 &&
            m-math.abs(favscowe - t-that.favscowe) < 1e-5 &&
            math.abs(favscowepwoducewnowmawized - t-that.favscowepwoducewnowmawized) < 1e-5 &&
            math.abs(wogfavscowe - that.wogfavscowe) < 1e-5 &&
            m-math.abs(wogfavscowepwoducewnowmawized - t-that.wogfavscowepwoducewnowmawized) < 1e-5 &&
            fowwowsociawpwoof.toset == that.fowwowsociawpwoof.toset &&
            f-favsociawpwoof.toset == t-that.favsociawpwoof.toset
        case _ => fawse
      }
    }
  }

  impwicit object swccwustewintewmediateinfosemigwoup
      extends semigwoup[swccwustewintewmediateinfo] {
    o-ovewwide d-def pwus(
      w-weft: swccwustewintewmediateinfo, (✿oωo)
      w-wight: s-swccwustewintewmediateinfo
    ): swccwustewintewmediateinfo = {
      s-swccwustewintewmediateinfo(
        f-fowwowscowe = weft.fowwowscowe + w-wight.fowwowscowe, OwO
        f-fowwowscowepwoducewnowmawized =
          weft.fowwowscowepwoducewnowmawized + w-wight.fowwowscowepwoducewnowmawized, :3
        favscowe = weft.favscowe + wight.favscowe, nyaa~~
        f-favscowepwoducewnowmawized =
          weft.favscowepwoducewnowmawized + wight.favscowepwoducewnowmawized, ^•ﻌ•^
        w-wogfavscowe = w-weft.wogfavscowe + wight.wogfavscowe, ( ͡o ω ͡o )
        w-wogfavscowepwoducewnowmawized =
          weft.wogfavscowepwoducewnowmawized + wight.wogfavscowepwoducewnowmawized, ^^;;
        fowwowsociawpwoof =
          semigwoup.pwus(weft.fowwowsociawpwoof, mya w-wight.fowwowsociawpwoof).distinct, (U ᵕ U❁)
        f-favsociawpwoof = s-semigwoup.pwus(weft.favsociawpwoof, ^•ﻌ•^ wight.favsociawpwoof).distinct
      )
    }
  }

  /**
   * @pawam adjacencywists usew-usew f-fowwow/fav gwaph
   * @pawam knownfow knownfow data set. (U ﹏ U) each u-usew can be known f-fow sevewaw cwustews with cewtain
   *                 k-knownfow weights. /(^•ω•^)
   * @pawam s-sociawpwoofthweshowd a-a usew wiww onwy be intewested in a c-cwustew if they fowwow/fav at
   *                             weast cewtain nyumbew o-of usews known f-fow this cwustew. ʘwʘ
   * @pawam uniqueid wequiwed f-fow these stat
   * @wetuwn
   */
  def usewcwustewpaiwswithoutnowmawization(
    a-adjacencywists: t-typedpipe[usewandneighbows],
    k-knownfow: typedpipe[(wong, XD awway[(int, fwoat)])], (⑅˘꒳˘)
    sociawpwoofthweshowd: int
  )(
    impwicit uniqueid: uniqueid
  ): typedpipe[((wong, nyaa~~ int), swccwustewintewmediateinfo)] = {
    vaw edgestousewswithknownfow = stat("num_edges_to_usews_with_known_fow")
    vaw swcdestcwustewtwipwes = s-stat("num_swc_dest_cwustew_twipwes")
    v-vaw swccwustewpaiwsbefowesociawpwoofthweshowding =
      stat("num_swc_cwustew_paiws_befowe_sociaw_pwoof_thweshowding")
    vaw s-swccwustewpaiwsaftewsociawpwoofthweshowding =
      s-stat("num_swc_cwustew_paiws_aftew_sociaw_pwoof_thweshowding")

    v-vaw edges = adjacencywists.fwatmap {
      c-case usewandneighbows(swcid, UwU nyeighbowswithweights) =>
        n-nyeighbowswithweights.map { n-nyeighbowwithweights =>
          (
            nyeighbowwithweights.neighbowid, (˘ω˘)
            n-nyeighbowwithweights.copy(neighbowid = swcid)
          )
        }
    }

    i-impwicit v-vaw w2b: wong => awway[byte] = injection.wong2bigendian

    e-edges
      .sketch(4000)
      .join(knownfow)
      .fwatmap {
        c-case (destid, rawr x3 (swcwithweights, c-cwustewawway)) =>
          e-edgestousewswithknownfow.inc()
          c-cwustewawway.towist.map {
            c-case (cwustewid, (///ˬ///✿) k-knownfowscowef) =>
              v-vaw knownfowscowe = m-math.max(0.0, 😳😳😳 knownfowscowef.todoubwe)

              swcdestcwustewtwipwes.inc()
              v-vaw fowwowscowe =
                i-if (swcwithweights.isfowwowed.contains(twue)) k-knownfowscowe ewse 0.0
              v-vaw fowwowscowepwoducewnowmawizedonwy =
                swcwithweights.fowwowscowenowmawizedbyneighbowfowwowewsw2.getowewse(
                  0.0) * k-knownfowscowe
              vaw favscowe =
                swcwithweights.favscowehawfwife100days.getowewse(0.0) * k-knownfowscowe

              v-vaw favscowepwoducewnowmawizedonwy =
                s-swcwithweights.favscowehawfwife100daysnowmawizedbyneighbowfavewsw2.getowewse(
                  0.0) * knownfowscowe

              v-vaw wogfavscowe = swcwithweights.wogfavscowe.getowewse(0.0) * k-knownfowscowe

              vaw wogfavscowepwoducewnowmawizedonwy = s-swcwithweights.wogfavscowew2nowmawized
                .getowewse(0.0) * knownfowscowe

              v-vaw fowwowsociawpwoof = if (swcwithweights.isfowwowed.contains(twue)) {
                wist(destid)
              } ewse nyiw
              v-vaw favsociawpwoof = if (swcwithweights.favscowehawfwife100days.exists(_ > 0)) {
                w-wist(destid)
              } e-ewse nyiw

              (
                (swcwithweights.neighbowid, (///ˬ///✿) cwustewid), ^^;;
                swccwustewintewmediateinfo(
                  fowwowscowe, ^^
                  f-fowwowscowepwoducewnowmawizedonwy, (///ˬ///✿)
                  favscowe, -.-
                  f-favscowepwoducewnowmawizedonwy, /(^•ω•^)
                  w-wogfavscowe, UwU
                  w-wogfavscowepwoducewnowmawizedonwy, (⑅˘꒳˘)
                  fowwowsociawpwoof, ʘwʘ
                  favsociawpwoof
                )
              )
          }
      }
      .sumbykey
      .withweducews(10000)
      .fiwtew {
        c-case ((_, σωσ _), ^^ s-swccwustewintewmediateinfo(_, OwO _, _, _, _, _, fowwowpwoof, (ˆ ﻌ ˆ)♡ favpwoof)) =>
          s-swccwustewpaiwsbefowesociawpwoofthweshowding.inc()
          vaw distinctsociawpwoof = (fowwowpwoof ++ favpwoof).toset
          v-vaw wesuwt = distinctsociawpwoof.size >= sociawpwoofthweshowd
          i-if (wesuwt) {
            s-swccwustewpaiwsaftewsociawpwoofthweshowding.inc()
          }
          w-wesuwt
      }
  }

  /**
   * add the cwustew-wevew w-w2 nyowm scowes, o.O a-and use them t-to nyowmawize f-fowwow/fav scowes. (˘ω˘)
   */
  def a-attachnowmawizedscowes(
    i-intewmediate: t-typedpipe[((wong, 😳 i-int), s-swccwustewintewmediateinfo)]
  )(
    i-impwicit u-uniqueid: uniqueid
  ): t-typedpipe[(wong, (U ᵕ U❁) wist[(int, :3 u-usewtointewestedincwustewscowes)])] = {

    def squawe(x: d-doubwe): doubwe = x * x

    vaw c-cwustewcountsandnowms =
      intewmediate
        .map {
          c-case (
                (_, o.O c-cwustewid), (///ˬ///✿)
                swccwustewintewmediateinfo(
                  fowwowscowe, OwO
                  fowwowscowepwoducewnowmawizedonwy, >w<
                  f-favscowe, ^^
                  f-favscowepwoducewnowmawizedonwy, (⑅˘꒳˘)
                  w-wogfavscowe, ʘwʘ
                  wogfavscowepwoducewnowmawizedonwy,
                  _, (///ˬ///✿)
                  _
                )
              ) =>
            (
              cwustewid, XD
              (
                1, 😳
                squawe(fowwowscowe), >w<
                s-squawe(fowwowscowepwoducewnowmawizedonwy), (˘ω˘)
                s-squawe(favscowe), nyaa~~
                squawe(favscowepwoducewnowmawizedonwy), 😳😳😳
                s-squawe(wogfavscowe), (U ﹏ U)
                s-squawe(wogfavscowepwoducewnowmawizedonwy)
              )
            )
        }
        .sumbykey
        //        .withweducews(100)
        .map {
          case (
                cwustewid, (˘ω˘)
                (
                  cnt, :3
                  s-squawefowwowscowe, >w<
                  s-squawefowwowscowepwoducewnowmawizedonwy,
                  s-squawefavscowe, ^^
                  s-squawefavscowepwoducewnowmawizedonwy, 😳😳😳
                  squawewogfavscowe, nyaa~~
                  squawewogfavscowepwoducewnowmawizedonwy
                )) =>
            (
              c-cwustewid, (⑅˘꒳˘)
              (
                c-cnt, :3
                math.sqwt(squawefowwowscowe), ʘwʘ
                math.sqwt(squawefowwowscowepwoducewnowmawizedonwy), rawr x3
                math.sqwt(squawefavscowe),
                m-math.sqwt(squawefavscowepwoducewnowmawizedonwy), (///ˬ///✿)
                math.sqwt(squawewogfavscowe), 😳😳😳
                math.sqwt(squawewogfavscowepwoducewnowmawizedonwy)
              ))
        }

    i-impwicit vaw i2b: int => a-awway[byte] = i-injection.int2bigendian

    intewmediate
      .map {
        c-case ((swcid, c-cwustewid), XD cwustewscowestupwe) =>
          (cwustewid, >_< (swcid, >w< cwustewscowestupwe))
      }
      .sketch(weducews = 900)
      .join(cwustewcountsandnowms)
      .map {
        c-case (
              cwustewid, /(^•ω•^)
              (
                (
                  s-swcid, :3
                  s-swccwustewintewmediateinfo(
                    f-fowwowscowe, ʘwʘ
                    f-fowwowscowepwoducewnowmawizedonwy, (˘ω˘)
                    favscowe, (ꈍᴗꈍ)
                    f-favscowepwoducewnowmawizedonwy, ^^
                    w-wogfavscowe, ^^
                    w-wogfavscowepwoducewnowmawizedonwy, ( ͡o ω ͡o ) // nyot used fow n-nyow
                    fowwowpwoof, -.-
                    favpwoof
                  )
                ), ^^;;
                (
                  c-cnt, ^•ﻌ•^
                  f-fowwownowm, (˘ω˘)
                  f-fowwowpwoducewnowmawizednowm, o.O
                  favnowm, (✿oωo)
                  favpwoducewnowmawizednowm, 😳😳😳
                  wogfavnowm, (ꈍᴗꈍ)
                  wogfavpwoducewnowmawizednowm // n-nyot used fow nyow
                )
              )
            ) =>
          (
            s-swcid, σωσ
            w-wist(
              (
                cwustewid, UwU
                usewtointewestedincwustewscowes(
                  f-fowwowscowe = some(ifnanmake0(fowwowscowe)), ^•ﻌ•^
                  f-fowwowscowecwustewnowmawizedonwy = s-some(ifnanmake0(fowwowscowe / f-fowwownowm)), mya
                  f-fowwowscowepwoducewnowmawizedonwy =
                    s-some(ifnanmake0(fowwowscowepwoducewnowmawizedonwy)), /(^•ω•^)
                  fowwowscowecwustewandpwoducewnowmawized = some(
                    ifnanmake0(fowwowscowepwoducewnowmawizedonwy / fowwowpwoducewnowmawizednowm)), rawr
                  f-favscowe = some(ifnanmake0(favscowe)), nyaa~~
                  favscowecwustewnowmawizedonwy = s-some(ifnanmake0(favscowe / favnowm)), ( ͡o ω ͡o )
                  favscowepwoducewnowmawizedonwy = some(ifnanmake0(favscowepwoducewnowmawizedonwy)), σωσ
                  f-favscowecwustewandpwoducewnowmawized =
                    some(ifnanmake0(favscowepwoducewnowmawizedonwy / favpwoducewnowmawizednowm)), (✿oωo)
                  usewsbeingfowwowed = some(fowwowpwoof), (///ˬ///✿)
                  u-usewsthatwewefaved = s-some(favpwoof), σωσ
                  nyumusewsintewestedinthiscwustewuppewbound = s-some(cnt), UwU
                  wogfavscowe = some(ifnanmake0(wogfavscowe)), (⑅˘꒳˘)
                  w-wogfavscowecwustewnowmawizedonwy = s-some(ifnanmake0(wogfavscowe / wogfavnowm))
                ))
            )
          )
      }
      .sumbykey
      //      .withweducews(1000)
      .totypedpipe
  }

  /**
   * a-aggwegate cwustew scowes fow e-each usew, /(^•ω•^) to be used instead of attachnowmawizedscowes
   * when we donot want t-to compute cwustew-wevew w2 nyowm scowes
   */
  d-def gwoupcwustewscowes(
    intewmediate: t-typedpipe[((wong, -.- int), s-swccwustewintewmediateinfo)]
  )(
    impwicit uniqueid: uniqueid
  ): t-typedpipe[(wong, (ˆ ﻌ ˆ)♡ wist[(int, nyaa~~ usewtointewestedincwustewscowes)])] = {

    intewmediate
      .map {
        case (
              (swcid, ʘwʘ c-cwustewid), :3
              s-swccwustewintewmediateinfo(
                f-fowwowscowe, (U ᵕ U❁)
                f-fowwowscowepwoducewnowmawizedonwy, (U ﹏ U)
                favscowe, ^^
                favscowepwoducewnowmawizedonwy, òωó
                w-wogfavscowe, /(^•ω•^)
                w-wogfavscowepwoducewnowmawizedonwy, 😳😳😳
                fowwowpwoof, :3
                favpwoof
              )
            ) =>
          (
            s-swcid, (///ˬ///✿)
            wist(
              (
                cwustewid, rawr x3
                u-usewtointewestedincwustewscowes(
                  fowwowscowe = some(ifnanmake0(fowwowscowe)), (U ᵕ U❁)
                  f-fowwowscowepwoducewnowmawizedonwy =
                    s-some(ifnanmake0(fowwowscowepwoducewnowmawizedonwy)), (⑅˘꒳˘)
                  favscowe = s-some(ifnanmake0(favscowe)), (˘ω˘)
                  f-favscowepwoducewnowmawizedonwy = s-some(ifnanmake0(favscowepwoducewnowmawizedonwy)), :3
                  usewsbeingfowwowed = some(fowwowpwoof), XD
                  u-usewsthatwewefaved = some(favpwoof), >_<
                  wogfavscowe = s-some(ifnanmake0(wogfavscowe)), (✿oωo)
                ))
            )
          )
      }
      .sumbykey
      .withweducews(1000)
      .totypedpipe
  }

  /**
   * fow each usew, (ꈍᴗꈍ) onwy keep up to a cewtain nyumbew o-of cwustews. XD
   * @pawam a-awwintewests usew w-with a wist of i-intewestedin cwustews. :3
   * @pawam m-maxcwustewspewusew nyumbew of c-cwustews to keep fow each usew
   * @pawam knownfowmodewvewsion k-known fow modew vewsion
   * @pawam u-uniqueid wequiwed fow these stat
   * @wetuwn
   */
  d-def k-keeponwytopcwustews(
    awwintewests: t-typedpipe[(wong, mya wist[(int, òωó u-usewtointewestedincwustewscowes)])], nyaa~~
    m-maxcwustewspewusew: int, 🥺
    knownfowmodewvewsion: stwing
  )(
    impwicit u-uniqueid: u-uniqueid
  ): typedpipe[(wong, -.- c-cwustewsusewisintewestedin)] = {
    vaw usewcwustewpaiwsbefoweusewtwuncation =
      stat("num_usew_cwustew_paiws_befowe_usew_twuncation")
    vaw usewcwustewpaiwsaftewusewtwuncation =
      s-stat("num_usew_cwustew_paiws_aftew_usew_twuncation")
    vaw usewswithawotofcwustews =
      s-stat(s"num_usews_with_mowe_than_${maxcwustewspewusew}_cwustews")

    awwintewests
      .map {
        case (swcid, 🥺 f-fuwwcwustewwist) =>
          u-usewcwustewpaiwsbefoweusewtwuncation.incby(fuwwcwustewwist.size)
          v-vaw twuncatedcwustews = i-if (fuwwcwustewwist.size > maxcwustewspewusew) {
            u-usewswithawotofcwustews.inc()
            fuwwcwustewwist
              .sowtby {
                c-case (_, (˘ω˘) cwustewscowes) =>
                  (
                    -cwustewscowes.favscowe.getowewse(0.0), òωó
                    -cwustewscowes.wogfavscowe.getowewse(0.0), UwU
                    -cwustewscowes.fowwowscowe.getowewse(0.0), ^•ﻌ•^
                    -cwustewscowes.wogfavscowecwustewnowmawizedonwy.getowewse(0.0), mya
                    -cwustewscowes.fowwowscowepwoducewnowmawizedonwy.getowewse(0.0)
                  )
              }
              .take(maxcwustewspewusew)
          } ewse {
            fuwwcwustewwist
          }
          u-usewcwustewpaiwsaftewusewtwuncation.incby(twuncatedcwustews.size)
          (swcid, (✿oωo) cwustewsusewisintewestedin(knownfowmodewvewsion, XD t-twuncatedcwustews.tomap))
      }
  }

  d-def wun(
    adjacencywists: typedpipe[usewandneighbows], :3
    knownfow: typedpipe[(usewid, (U ﹏ U) awway[(cwustewid, UwU f-fwoat)])], ʘwʘ
    sociawpwoofthweshowd: i-int, >w<
    maxcwustewspewusew: int, 😳😳😳
    knownfowmodewvewsion: stwing
  )(
    impwicit uniqueid: u-uniqueid
  ): typedpipe[(usewid, rawr c-cwustewsusewisintewestedin)] = {
    k-keeponwytopcwustews(
      attachnowmawizedscowes(
        usewcwustewpaiwswithoutnowmawization(
          adjacencywists, ^•ﻌ•^
          knownfow, σωσ
          sociawpwoofthweshowd
        )
      ), :3
      m-maxcwustewspewusew, rawr x3
      knownfowmodewvewsion
    )
  }

  /**
   * wun the intewestedin j-job, nyaa~~ cwustew nyowmawized s-scowes awe nyot a-attached to usew's cwustews. :3
   */
  d-def wunwithoutcwustewnowmawizedscowes(
    a-adjacencywists: t-typedpipe[usewandneighbows], >w<
    k-knownfow: typedpipe[(usewid, rawr a-awway[(cwustewid, 😳 f-fwoat)])], 😳
    sociawpwoofthweshowd: int, 🥺
    maxcwustewspewusew: int, rawr x3
    knownfowmodewvewsion: stwing
  )(
    i-impwicit uniqueid: u-uniqueid
  ): t-typedpipe[(usewid, ^^ c-cwustewsusewisintewestedin)] = {
    k-keeponwytopcwustews(
      g-gwoupcwustewscowes(
        usewcwustewpaiwswithoutnowmawization(
          adjacencywists, ( ͡o ω ͡o )
          knownfow, XD
          sociawpwoofthweshowd
        )
      ), ^^
      maxcwustewspewusew, (⑅˘꒳˘)
      k-knownfowmodewvewsion
    )
  }

  /**
   * p-pwint out some basic stats of the data set to make suwe things a-awe nyot bwoken
   */
  d-def d-datasetstats(
    intewestedindata: typedpipe[(usewid, (⑅˘꒳˘) c-cwustewsusewisintewestedin)], ^•ﻌ•^
    datasetname: stwing = ""
  ): e-execution[unit] = {

    e-execution
      .zip(
        utiw.pwintsummawyofnumewiccowumn(
          intewestedindata.map {
            case (usew, ( ͡o ω ͡o ) i-intewestedin) =>
              intewestedin.cwustewidtoscowes.size
          }, ( ͡o ω ͡o )
          s-some(s"$datasetname u-usewintewestedin size")
        ), (✿oωo)
        u-utiw.pwintsummawyofnumewiccowumn(
          intewestedindata.fwatmap {
            c-case (usew, 😳😳😳 i-intewestedin) =>
              i-intewestedin.cwustewidtoscowes.map {
                c-case (_, OwO scowes) =>
                  s-scowes.favscowe.getowewse(0.0)
              }
          }, ^^
          some(s"$datasetname u-usewintewestedin f-favscowe")
        ), rawr x3
        utiw.pwintsummawyofnumewiccowumn(
          i-intewestedindata.fwatmap {
            case (usew, 🥺 intewestedin) =>
              i-intewestedin.cwustewidtoscowes.map {
                case (_, (ˆ ﻌ ˆ)♡ s-scowes) =>
                  scowes.favscowecwustewnowmawizedonwy.getowewse(0.0)
              }
          }, ( ͡o ω ͡o )
          s-some(s"$datasetname u-usewintewestedin favscowecwustewnowmawizedonwy")
        ), >w<
        utiw.pwintsummawyofnumewiccowumn(
          i-intewestedindata.fwatmap {
            case (usew, /(^•ω•^) intewestedin) =>
              intewestedin.cwustewidtoscowes.map {
                c-case (_, 😳😳😳 scowes) =>
                  s-scowes.wogfavscowecwustewnowmawizedonwy.getowewse(0.0)
              }
          }, (U ᵕ U❁)
          some(s"$datasetname usewintewestedin w-wogfavscowecwustewnowmawizedonwy")
        )
      ).unit
  }
}
