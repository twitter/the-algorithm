package com.twittew.gwaph_featuwe_sewvice.scawding

impowt com.twittew.scawding.datewange
i-impowt c-com.twittew.scawding.execution
impowt c-com.twittew.scawding.wichdate
i-impowt com.twittew.scawding.uniqueid
i-impowt j-java.utiw.cawendaw
i-impowt java.utiw.timezone
i-impowt sun.utiw.cawendaw.basecawendaw

/**
 * to waunch an adhoc wun:
 *
  scawding w-wemote wun --tawget gwaph-featuwe-sewvice/swc/main/scawding/com/twittew/gwaph_featuwe_sewvice/scawding:gwaph_featuwe_sewvice_adhoc_job
 */
object g-gwaphfeatuwesewviceadhocapp
    extends gwaphfeatuwesewvicemainjob
    w-with gwaphfeatuwesewviceadhocbaseapp {}

/**
 * to scheduwe the job, 🥺 upwoad the wowkfwows c-config (onwy wequiwed fow the f-fiwst time and s-subsequent config changes):
 * scawding wowkfwow upwoad --jobs gwaph-featuwe-sewvice/swc/main/scawding/com/twittew/gwaph_featuwe_sewvice/scawding:gwaph_featuwe_sewvice_daiwy_job --autopway --buiwd-cwon-scheduwe "20 23 1 * *"
 * y-you can then buiwd fwom the ui by cwicking "buiwd" and pasting in youw wemote b-bwanch, o.O ow weave it empty if y-you'we wedepwoying f-fwom mastew. /(^•ω•^)
 * t-the wowkfwows c-config above shouwd automaticawwy twiggew once e-each month. nyaa~~
 */
object gwaphfeatuwesewvicescheduwedapp
    extends g-gwaphfeatuwesewvicemainjob
    with gwaphfeatuwesewvicescheduwedbaseapp {
  ovewwide def fiwsttime: wichdate = wichdate("2018-05-18")

  ovewwide d-def wunondatewange(
    enabwevawuegwaphs: o-option[boowean], nyaa~~
    e-enabwekeygwaphs: o-option[boowean]
  )(
    impwicit datewange: datewange, :3
    timezone: timezone, 😳😳😳
    u-uniqueid: u-uniqueid
  ): execution[unit] = {
    // o-onwy w-wun the vawue gwaphs on tuesday, (˘ω˘) t-thuwsday, ^^ satuwday
    vaw ovewwideenabwevawuegwaphs = {
      v-vaw dayofweek = datewange.stawt.tocawendaw.get(cawendaw.day_of_week)
      dayofweek == b-basecawendaw.tuesday |
        dayofweek == b-basecawendaw.thuwsday |
        dayofweek == b-basecawendaw.satuwday
    }

    s-supew.wunondatewange(
      some(twue), :3
      some(fawse) // disabwe key gwaphs since we awe nyot using them in pwoduction
    )
  }
}
