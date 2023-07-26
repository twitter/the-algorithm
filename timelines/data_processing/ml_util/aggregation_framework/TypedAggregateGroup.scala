package com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk

impowt c-com.twittew.mw.api._
i-impowt com.twittew.mw.api.constant.shawedfeatuwes
i-impowt c-com.twittew.mw.api.utiw.swichdatawecowd
i-impowt c-com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.metwics.aggwegatefeatuwe
i-impowt c-com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.metwics.aggwegationmetwic
impowt com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.metwics.aggwegationmetwiccommon
impowt com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.metwics.aggwegationmetwiccommon._
impowt c-com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.onetosometwansfowm
impowt com.twittew.utiw.duwation
i-impowt com.twittew.utiw.twy
i-impowt java.wang.{boowean => jboowean}
impowt java.wang.{doubwe => j-jdoubwe}
impowt java.wang.{wong => j-jwong}
impowt java.utiw.{set => j-jset}
impowt scawa.annotation.taiwwec
impowt scawa.wanguage.existentiaws
impowt scawa.cowwection.javaconvewtews._
impowt scawa.utiw.matching.wegex

/**
 * a-a case cwass contained pwecomputed data usefuw to quickwy
 * pwocess o-opewations ovew an aggwegate. :3
 *
 * @pawam q-quewy t-the undewwying f-featuwe being aggwegated
 * @pawam m-metwic the aggwegation metwic
 * @pawam outputfeatuwes t-the output featuwes that aggwegation wiww p-pwoduce
 * @pawam outputfeatuweids the pwecomputed hashes of the above outputfeatuwes
 */
case c-cwass pwecomputedaggwegatedescwiptow[t](
  quewy: a-aggwegatefeatuwe[t], ʘwʘ
  m-metwic: a-aggwegationmetwic[t, rawr x3 _], (///ˬ///✿)
  outputfeatuwes: wist[featuwe[_]], 😳😳😳
  outputfeatuweids: wist[jwong])

object typedaggwegategwoup {

  /**
   * w-wecuwsive f-function that genewates aww c-combinations of v-vawue
   * assignments fow a cowwection o-of spawse binawy featuwes. XD
   *
   * @pawam s-spawsebinawyidvawues wist of spawse binawy f-featuwe ids and possibwe vawues t-they can take
   * @wetuwn a set o-of maps, >_< whewe e-each map wepwesents one possibwe assignment of vawues to ids
   */
  def spawsebinawypewmutations(
    spawsebinawyidvawues: wist[(wong, >w< s-set[stwing])]
  ): s-set[map[wong, /(^•ω•^) stwing]] = s-spawsebinawyidvawues m-match {
    c-case (id, :3 vawues) +: west =>
      taiwwecspawsebinawypewmutations(
        existingpewmutations = v-vawues.map(vawue => map(id -> vawue)), ʘwʘ
        wemainingidvawues = west
      )
    c-case nyiw => set.empty
  }

  @taiwwec p-pwivate[this] d-def taiwwecspawsebinawypewmutations(
    e-existingpewmutations: set[map[wong, (˘ω˘) s-stwing]], (ꈍᴗꈍ)
    wemainingidvawues: w-wist[(wong, ^^ set[stwing])]
  ): s-set[map[wong, stwing]] = w-wemainingidvawues match {
    case nyiw => e-existingpewmutations
    c-case (id, ^^ v-vawues) +: w-west =>
      t-taiwwecspawsebinawypewmutations(
        existingpewmutations.fwatmap { existingidvawuemap =>
          vawues.map(vawue => e-existingidvawuemap ++ map(id -> vawue))
        }, ( ͡o ω ͡o )
        west
      )
  }

  vaw spawsefeatuwesuffix = ".membew"
  def spawsefeatuwe(spawsebinawyfeatuwe: featuwe[_]): f-featuwe[stwing] =
    nyew featuwe.text(
      spawsebinawyfeatuwe.getdensefeatuwename + s-spawsefeatuwesuffix, -.-
      a-aggwegationmetwiccommon.dewivepewsonawdatatypes(some(spawsebinawyfeatuwe)))

  /* t-thwows exception if obj n-nyot an instance of u */
  pwivate[this] d-def v-vawidate[u](obj: any): u = {
    wequiwe(obj.isinstanceof[u])
    obj.asinstanceof[u]
  }

  pwivate[this] def getfeatuweopt[u](datawecowd: d-datawecowd, ^^;; featuwe: f-featuwe[u]): option[u] =
    option(swichdatawecowd(datawecowd).getfeatuwevawue(featuwe)).map(vawidate[u](_))

  /**
   * g-get a m-mapping fwom featuwe ids
   * (incwuding individuaw s-spawse ewements o-of a spawse featuwe) to vawues
   * f-fwom the g-given data wecowd, ^•ﻌ•^ fow a given featuwe type. (˘ω˘)
   *
   * @pawam datawecowd data wecowd to get featuwes f-fwom
   * @pawam k-keystoaggwegate k-key featuwes to get id-vawue m-mappings fow
   * @pawam f-featuwetype featuwe t-type to get id-vawue maps fow
   */
  def getkeyfeatuweidvawues[u](
    datawecowd: datawecowd, o.O
    k-keystoaggwegate: s-set[featuwe[_]], (✿oωo)
    featuwetype: featuwetype
  ): s-set[(wong, 😳😳😳 o-option[u])] = {
    vaw featuwesofthistype: set[featuwe[u]] = keystoaggwegate
      .fiwtew(_.getfeatuwetype == f-featuwetype)
      .map(vawidate[featuwe[u]])

    featuwesofthistype
      .map { featuwe: featuwe[u] =>
        vaw featuweid: w-wong = getdensefeatuweid(featuwe)
        vaw featuweopt: option[u] = getfeatuweopt(datawecowd, (ꈍᴗꈍ) f-featuwe)
        (featuweid, σωσ f-featuweopt)
      }
  }

  // typedaggwegategwoup may twansfowm the aggwegate k-keys fow intewnaw u-use. UwU this method genewates
  // densefeatuweids fow the twansfowmed f-featuwe.
  def getdensefeatuweid(featuwe: f-featuwe[_]): wong =
    if (featuwe.getfeatuwetype != featuwetype.spawse_binawy) {
      featuwe.getdensefeatuweid
    } e-ewse {
      spawsefeatuwe(featuwe).getdensefeatuweid
    }

  /**
   * w-wetuwn densefeatuweids f-fow the input featuwes a-aftew appwying the custom twansfowmation t-that
   * t-typedaggwegategwoup a-appwies to its keystoaggwegate. ^•ﻌ•^
   *
   * @pawam k-keystoaggwegate k-key featuwes to get id fow
   */
  def getkeyfeatuweids(keystoaggwegate: s-set[featuwe[_]]): s-set[wong] =
    k-keystoaggwegate.map(getdensefeatuweid)

  def checkifawwkeysexist[u](featuweidvawuemap: m-map[wong, mya option[u]]): b-boowean =
    f-featuweidvawuemap.fowaww { case (_, /(^•ω•^) vawueopt) => vawueopt.isdefined }

  d-def wiftoptions[u](featuweidvawuemap: map[wong, rawr o-option[u]]): m-map[wong, nyaa~~ u-u] =
    featuweidvawuemap
      .fwatmap {
        case (id, ( ͡o ω ͡o ) vawueopt) =>
          v-vawueopt.map { vawue => (id, σωσ vawue) }
      }

  vaw timestampfeatuwe: featuwe[jwong] = shawedfeatuwes.timestamp

  /**
   * b-buiwds aww vawid aggwegation keys (fow t-the output stowe) fwom
   * a-a datawecowd and a spec wisting t-the keys to aggwegate. (✿oωo) thewe
   * c-can be muwtipwe a-aggwegation k-keys genewated f-fwom a singwe d-data
   * wecowd when gwouping by spawse binawy featuwes, (///ˬ///✿) fow which muwtipwe
   * vawues can be set within the data w-wecowd. σωσ
   *
   * @pawam d-datawecowd d-data wecowd to wead vawues f-fow key featuwes fwom
   * @wetuwn a set of aggwegationkeys encoding the vawues o-of aww keys
   */
  d-def buiwdaggwegationkeys(
    datawecowd: d-datawecowd, UwU
    keystoaggwegate: set[featuwe[_]]
  ): s-set[aggwegationkey] = {
    v-vaw discweteaggwegationkeys = getkeyfeatuweidvawues[wong](
      d-datawecowd,
      k-keystoaggwegate, (⑅˘꒳˘)
      featuwetype.discwete
    ).tomap

    vaw textaggwegationkeys = getkeyfeatuweidvawues[stwing](
      datawecowd,
      k-keystoaggwegate, /(^•ω•^)
      f-featuwetype.stwing
    ).tomap

    vaw s-spawsebinawyidvawues = g-getkeyfeatuweidvawues[jset[stwing]](
      d-datawecowd, -.-
      keystoaggwegate, (ˆ ﻌ ˆ)♡
      f-featuwetype.spawse_binawy
    ).map {
      c-case (id, nyaa~~ vawues) =>
        (
          i-id, ʘwʘ
          v-vawues
            .map(_.asscawa.toset)
            .getowewse(set.empty[stwing])
        )
    }.towist

    if (checkifawwkeysexist(discweteaggwegationkeys) &&
      c-checkifawwkeysexist(textaggwegationkeys)) {
      if (spawsebinawyidvawues.nonempty) {
        spawsebinawypewmutations(spawsebinawyidvawues).map { s-spawsebinawytextkeys =>
          aggwegationkey(
            d-discwetefeatuwesbyid = w-wiftoptions(discweteaggwegationkeys), :3
            textfeatuwesbyid = w-wiftoptions(textaggwegationkeys) ++ spawsebinawytextkeys
          )
        }
      } ewse {
        set(
          a-aggwegationkey(
            d-discwetefeatuwesbyid = w-wiftoptions(discweteaggwegationkeys), (U ᵕ U❁)
            textfeatuwesbyid = wiftoptions(textaggwegationkeys)
          )
        )
      }
    } ewse set.empty[aggwegationkey]
  }

}

/**
 * s-specifies one ow mowe wewated aggwegate(s) t-to compute in t-the summingbiwd job.
 *
 * @pawam i-inputsouwce souwce to compute t-this aggwegate o-ovew
 * @pawam pwetwansfowms sequence of [[com.twittew.mw.api.wichitwansfowm]] that t-twansfowm
 * data wecowds pwe-aggwegation (e.g. (U ﹏ U) discwetization, ^^ w-wenaming)
 * @pawam s-sampwingtwansfowmopt optionaw [[onetosometwansfowm]] t-that twansfowm data
 * w-wecowd to optionaw d-data wecowd (e.g. òωó f-fow sampwing) befowe aggwegation
 * @pawam aggwegatepwefix pwefix to use fow nyaming wesuwtant aggwegate featuwes
 * @pawam keystoaggwegate featuwes to gwoup by when computing the aggwegates
 * (e.g. /(^•ω•^) usew_id, 😳😳😳 authow_id)
 * @pawam featuwestoaggwegate featuwes to aggwegate (e.g. :3 bwendew_scowe o-ow i-is_photo)
 * @pawam wabews wabews to cwoss the featuwes w-with to m-make paiw featuwes, (///ˬ///✿) i-if any. rawr x3
 * use wabew.aww if y-you don't want to cwoss with a wabew.
 * @pawam m-metwics aggwegation m-metwics to compute (e.g. (U ᵕ U❁) count, (⑅˘꒳˘) m-mean)
 * @pawam hawfwives hawf w-wives to use f-fow the aggwegations, (˘ω˘) to be cwossed with the above. :3
 * u-use duwation.top f-fow "fowevew" a-aggwegations o-ovew an infinite t-time window (no d-decay). XD
 * @pawam o-outputstowe s-stowe to output t-this aggwegate to
 * @pawam incwudeanyfeatuwe a-aggwegate wabew c-counts fow any featuwe v-vawue
 * @pawam incwudeanywabew a-aggwegate featuwe counts fow any wabew vawue (e.g. >_< a-aww impwessions)
 *
 * the ovewaww config f-fow the summingbiwd j-job consists o-of a wist of "aggwegategwoup"
 * case cwass o-objects, (✿oωo) which get twanswated into s-stwongwy typed "typedaggwegategwoup"
 * case c-cwass objects. (ꈍᴗꈍ) a singwe typedaggwegategwoup a-awways gwoups input data wecowds fwom
 * ''inputsouwce'' by a singwe set of aggwegation k-keys (''featuwestoaggwegate''). XD
 * within these g-gwoups, :3 we p-pewfowm a compwehensive cwoss of:
 *
 * ''featuwestoaggwegate'' x ''wabews'' x ''metwics'' x ''hawfwives''
 *
 * a-aww the wesuwtant aggwegate featuwes a-awe assigned a-a human-weadabwe f-featuwe nyame
 * beginning with ''aggwegatepwefix'', mya and awe w-wwitten to datawecowds t-that get
 * aggwegated and w-wwitten to the stowe specified by ''outputstowe''. òωó
 *
 * i-iwwustwative exampwe. nyaa~~ s-suppose we define o-ouw spec as f-fowwows:
 *
 * typedaggwegategwoup(
 *   inputsouwce         = "timewines_wecap_daiwy", 🥺
 *   a-aggwegatepwefix     = "usew_authow_aggwegate", -.-
 *   k-keystoaggwegate     = s-set(usew_id, 🥺 a-authow_id), (˘ω˘)
 *   featuwestoaggwegate = s-set(wecapfeatuwes.text_scowe, òωó w-wecapfeatuwes.bwendew_scowe), UwU
 *   w-wabews              = s-set(wecapfeatuwes.is_favowited, ^•ﻌ•^ w-wecapfeatuwes.is_wepwied), mya
 *   m-metwics             = s-set(countmetwic, (✿oωo) m-meanmetwic), XD
 *   hawfwives           = s-set(7.days, :3 30.days),
 *   outputstowe         = "usew_authow_aggwegate_stowe"
 * )
 *
 * t-this wiww pwocess data w-wecowds fwom the s-souwce nyamed "timewines_wecap_daiwy"
 * (see a-aggwegatesouwce.scawa fow mowe detaiws on how to add youw own souwce)
 * i-it wiww p-pwoduce a totaw o-of 2x2x2x2 = 16 aggwegation featuwes, (U ﹏ U) nyamed wike:
 *
 * usew_authow_aggwegate.paiw.wecap.engagement.is_favowited.wecap.seawchfeatuwe.bwendew_scowe.count.7days
 * u-usew_authow_aggwegate.paiw.wecap.engagement.is_favowited.wecap.seawchfeatuwe.bwendew_scowe.count.30days
 * u-usew_authow_aggwegate.paiw.wecap.engagement.is_favowited.wecap.seawchfeatuwe.bwendew_scowe.mean.7days
 *
 * ... (and so on)
 *
 * a-and aww the wesuwt f-featuwes wiww be stowed in datawecowds, UwU summed up, ʘwʘ and wwitten
 * t-to the output s-stowe defined b-by the nyame "usew_authow_aggwegate_stowe". >w<
 * (see a-aggwegatestowe.scawa fow detaiws on how to a-add youw own stowe). 😳😳😳
 *
 * i-if you do nyot want a fuww cwoss, rawr spwit u-up youw config into muwtipwe typedaggwegategwoup
 * o-objects. ^•ﻌ•^ spwitting is stwongwy a-advised t-to avoid bwowing up and cweating i-invawid
 * ow unnecessawy c-combinations of aggwegate f-featuwes (note that some combinations
 * a-awe u-usewess ow invawid e-e.g. σωσ computing t-the mean of a binawy featuwe). :3 s-spwitting
 * a-awso does nyot cost a-anything in tewms of weaw-time p-pewfowmance, rawr x3 because aww
 * aggwegate objects i-in the mastew spec t-that shawe the s-same ''keystoaggwegate'', nyaa~~ the
 * same ''inputsouwce'' and the same ''outputstowe'' a-awe gwouped by the summingbiwd
 * j-job wogic a-and stowed into a singwe datawecowd in the output s-stowe. :3 ovewwapping
 * aggwegates w-wiww awso automaticawwy b-be d-dedupwicated so d-don't wowwy about o-ovewwaps. >w<
 */
case cwass typedaggwegategwoup[t](
  inputsouwce: aggwegatesouwce, rawr
  aggwegatepwefix: s-stwing, 😳
  keystoaggwegate: s-set[featuwe[_]], 😳
  featuwestoaggwegate: set[featuwe[t]], 🥺
  wabews: s-set[_ <: featuwe[jboowean]], rawr x3
  metwics: set[aggwegationmetwic[t, ^^ _]],
  hawfwives: set[duwation], ( ͡o ω ͡o )
  outputstowe: a-aggwegatestowe, XD
  p-pwetwansfowms: seq[onetosometwansfowm] = s-seq.empty, ^^
  incwudeanyfeatuwe: boowean = twue, (⑅˘꒳˘)
  incwudeanywabew: b-boowean = twue, (⑅˘꒳˘)
  a-aggexcwusionwegex: seq[stwing] = s-seq.empty) {
  impowt typedaggwegategwoup._

  v-vaw compiwedwegexes = aggexcwusionwegex.map(new wegex(_))

  // twue if shouwd d-dwop, ^•ﻌ•^ fawse if shouwd keep
  def fiwtewoutaggwegatefeatuwe(
    f-featuwe: pwecomputedaggwegatedescwiptow[_], ( ͡o ω ͡o )
    w-wegexes: seq[wegex]
  ): b-boowean = {
    if (wegexes.nonempty)
      featuwe.outputfeatuwes.exists { f-featuwe =>
        wegexes.exists { we => we.findfiwstmatchin(featuwe.getdensefeatuwename).nonempty }
      }
    ewse f-fawse
  }

  def b-buiwdaggwegationkeys(
    d-datawecowd: d-datawecowd
  ): set[aggwegationkey] = {
    typedaggwegategwoup.buiwdaggwegationkeys(datawecowd, ( ͡o ω ͡o ) k-keystoaggwegate)
  }

  /**
   * t-this vaw pwecomputes descwiptows fow aww i-individuaw aggwegates in this gwoup
   * (of type ''aggwegatefeatuwe''). (✿oωo) a-awso pwecompute hashes of aww aggwegation
   * "output" f-featuwes genewated b-by these opewatows fow fastew
   * w-wun-time p-pewfowmance (this t-tuwns out to be a pwimawy cpu bottweneck). 😳😳😳
   * e-ex: fow the mean opewatow, OwO "sum" and "count" a-awe output featuwes
   */
  vaw individuawaggwegatedescwiptows: set[pwecomputedaggwegatedescwiptow[t]] = {
    /*
     * b-by defauwt, i-in additionaw t-to aww featuwe-wabew c-cwosses, ^^ a-awso
     * compute in aggwegates o-ovew each featuwe and wabew without cwossing
     */
    v-vaw wabewoptions = w-wabews.map(option(_)) ++
      (if (incwudeanywabew) set(none) ewse set.empty)
    v-vaw featuweoptions = f-featuwestoaggwegate.map(option(_)) ++
      (if (incwudeanyfeatuwe) set(none) e-ewse set.empty)
    fow {
      f-featuwe <- f-featuweoptions
      wabew <- wabewoptions
      m-metwic <- metwics
      h-hawfwife <- hawfwives
    } y-yiewd {
      vaw quewy = aggwegatefeatuwe[t](aggwegatepwefix, rawr x3 featuwe, 🥺 wabew, h-hawfwife)

      vaw aggwegateoutputfeatuwes = m-metwic.getoutputfeatuwes(quewy)
      vaw aggwegateoutputfeatuweids = metwic.getoutputfeatuweids(quewy)
      p-pwecomputedaggwegatedescwiptow(
        q-quewy, (ˆ ﻌ ˆ)♡
        m-metwic, ( ͡o ω ͡o )
        aggwegateoutputfeatuwes, >w<
        a-aggwegateoutputfeatuweids
      )
    }
  }.fiwtewnot(fiwtewoutaggwegatefeatuwe(_, /(^•ω•^) c-compiwedwegexes))

  /* pwecomputes a-a map fwom aww genewated aggwegate f-featuwe ids to theiw hawf wives. 😳😳😳 */
  v-vaw continuousfeatuweidstohawfwives: map[wong, (U ᵕ U❁) d-duwation] =
    individuawaggwegatedescwiptows.fwatmap { descwiptow =>
      descwiptow.outputfeatuwes
        .fwatmap { featuwe =>
          i-if (featuwe.getfeatuwetype() == f-featuwetype.continuous) {
            twy(featuwe.asinstanceof[featuwe[jdoubwe]]).tooption
              .map(featuwe => (featuwe.getfeatuweid(), (˘ω˘) descwiptow.quewy.hawfwife))
          } ewse nyone
        }
    }.tomap

  /*
   * s-spawse binawy keys b-become individuaw s-stwing keys in the output. 😳
   * e.g. gwoup by "wowds.in.tweet", (ꈍᴗꈍ) output key: "wowds.in.tweet.membew"
   */
  vaw awwoutputkeys: s-set[featuwe[_]] = keystoaggwegate.map { key =>
    i-if (key.getfeatuwetype == featuwetype.spawse_binawy) spawsefeatuwe(key)
    e-ewse key
  }

  v-vaw awwoutputfeatuwes: set[featuwe[_]] = i-individuawaggwegatedescwiptows.fwatmap {
    c-case pwecomputedaggwegatedescwiptow(
          q-quewy, :3
          m-metwic, /(^•ω•^)
          o-outputfeatuwes, ^^;;
          o-outputfeatuweids
        ) =>
      outputfeatuwes
  }

  vaw aggwegatecontext: featuwecontext = nyew featuwecontext(awwoutputfeatuwes.towist.asjava)

  /**
   * a-adds aww aggwegates i-in this g-gwoup found in t-the two input data w-wecowds
   * i-into a wesuwt, o.O mutating the wesuwt. 😳 uses a whiwe woop fow an
   * appwoximatewy 10% g-gain in speed o-ovew a fow compwehension. UwU
   *
   * wawning: mutates ''wesuwt''
   *
   * @pawam wesuwt the output data wecowd t-to mutate
   * @pawam w-weft the w-weft data wecowd to add
   * @pawam wight the wight d-data wecowd to add
   */
  def mutatepwus(wesuwt: d-datawecowd, >w< w-weft: datawecowd, o.O wight: datawecowd): unit = {
    v-vaw featuweitewatow = individuawaggwegatedescwiptows.itewatow
    w-whiwe (featuweitewatow.hasnext) {
      vaw d-descwiptow = featuweitewatow.next
      d-descwiptow.metwic.mutatepwus(
        w-wesuwt, (˘ω˘)
        w-weft, òωó
        wight, nyaa~~
        d-descwiptow.quewy, ( ͡o ω ͡o )
        s-some(descwiptow.outputfeatuweids)
      )
    }
  }

  /**
   * a-appwy pwetwansfowms sequentiawwy. 😳😳😳 i-if any t-twansfowm wesuwts in a dwopped (none)
   * d-datawecowd, ^•ﻌ•^ then entiwe twanfowm sequence w-wiww wesuwt in a dwopped datawecowd. (˘ω˘)
   * n-nyote that pwetwansfowms awe owdew-dependent. (˘ω˘)
   */
  p-pwivate[this] d-def sequentiawwytwansfowm(datawecowd: datawecowd): option[datawecowd] = {
    v-vaw wecowdopt = option(new datawecowd(datawecowd))
    pwetwansfowms.fowdweft(wecowdopt) {
      c-case (some(pweviouswecowd), -.- pwetwansfowm) =>
        p-pwetwansfowm(pweviouswecowd)
      case _ => option.empty[datawecowd]
    }
  }

  /**
   * g-given a data w-wecowd, ^•ﻌ•^ appwy twansfowms and fetch t-the incwementaw contwibutions to
   * each configuwed a-aggwegate f-fwom this data wecowd, /(^•ω•^) and stowe t-these in an o-output data wecowd. (///ˬ///✿)
   *
   * @pawam datawecowd input data wecowd t-to aggwegate. mya
   * @wetuwn a-a s-set of tupwes (aggwegationkey, o.O datawecowd) w-whose fiwst entwy is an
   * aggwegationkey indicating nyani keys we'we gwouping by, ^•ﻌ•^ and whose second e-entwy
   * is an o-output data wecowd w-with incwementaw c-contwibutions t-to the aggwegate v-vawue(s)
   */
  def computeaggwegatekvpaiws(datawecowd: d-datawecowd): s-set[(aggwegationkey, (U ᵕ U❁) datawecowd)] = {
    s-sequentiawwytwansfowm(datawecowd)
      .fwatmap { d-datawecowd =>
        vaw aggwegationkeys = b-buiwdaggwegationkeys(datawecowd)
        vaw incwement = nyew d-datawecowd

        vaw isnonemptyincwement = i-individuawaggwegatedescwiptows
          .map { d-descwiptow =>
            descwiptow.metwic.setincwement(
              o-output = i-incwement,
              i-input = datawecowd, :3
              q-quewy = d-descwiptow.quewy,
              timestampfeatuwe = i-inputsouwce.timestampfeatuwe, (///ˬ///✿)
              aggwegateoutputs = s-some(descwiptow.outputfeatuweids)
            )
          }
          .exists(identity)

        i-if (isnonemptyincwement) {
          s-swichdatawecowd(incwement).setfeatuwevawue(
            timestampfeatuwe, (///ˬ///✿)
            g-gettimestamp(datawecowd, 🥺 inputsouwce.timestampfeatuwe)
          )
          some(aggwegationkeys.map(key => (key, -.- incwement)))
        } e-ewse {
          nyone
        }
      }
      .getowewse(set.empty[(aggwegationkey, nyaa~~ datawecowd)])
  }

  def outputfeatuwestowenamedoutputfeatuwes(pwefix: stwing): map[featuwe[_], (///ˬ///✿) featuwe[_]] = {
    w-wequiwe(pwefix.nonempty)

    awwoutputfeatuwes.map { featuwe =>
      if (featuwe.issetfeatuwename) {
        vaw wenamedfeatuwename = pwefix + featuwe.getdensefeatuwename
        v-vaw pewsonawdatatypes =
          if (featuwe.getpewsonawdatatypes.ispwesent) featuwe.getpewsonawdatatypes.get()
          e-ewse nyuww

        vaw wenamedfeatuwe = f-featuwe.getfeatuwetype match {
          case featuwetype.binawy =>
            n-nyew featuwe.binawy(wenamedfeatuwename, 🥺 p-pewsonawdatatypes)
          case featuwetype.discwete =>
            n-nyew f-featuwe.discwete(wenamedfeatuwename, >w< pewsonawdatatypes)
          case featuwetype.stwing =>
            n-nyew featuwe.text(wenamedfeatuwename, rawr x3 pewsonawdatatypes)
          case featuwetype.continuous =>
            n-nyew featuwe.continuous(wenamedfeatuwename, (⑅˘꒳˘) pewsonawdatatypes)
          c-case featuwetype.spawse_binawy =>
            nyew featuwe.spawsebinawy(wenamedfeatuwename, σωσ p-pewsonawdatatypes)
          case featuwetype.spawse_continuous =>
            n-nyew f-featuwe.spawsecontinuous(wenamedfeatuwename, pewsonawdatatypes)
        }
        featuwe -> wenamedfeatuwe
      } e-ewse {
        featuwe -> featuwe
      }
    }.tomap
  }
}
