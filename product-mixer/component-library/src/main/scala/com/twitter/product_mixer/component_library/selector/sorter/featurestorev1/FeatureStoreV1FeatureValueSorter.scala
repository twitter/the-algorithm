package com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.featuwestowev1

impowt com.twittew.mw.featuwestowe.wib.entityid
i-impowt com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.ascending
i-impowt com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.descending
i-impowt c-com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.featuwevawuesowtew.featuwevawuesowtdefauwtvawue
i-impowt com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.sowtewfwomowdewing
i-impowt com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.sowtewpwovidew
i-impowt c-com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.featuwestowev1.featuwestowev1featuwemap._
impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwestowev1.featuwestowev1candidatefeatuwe
impowt com.twittew.pwoduct_mixew.cowe.modew.common.univewsawnoun
impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.pwesentation.candidatewithdetaiws
impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt scawa.wefwect.wuntime.univewse._

/**
 * f-featuwe stowe v1 vewsion of [[com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.featuwevawuesowtew]]
 */
object f-featuwestowev1featuwevawuesowtew {

  /**
   * sowt by a featuwe s-stowe v1 featuwe v-vawue ascending. :3 if the featuwe faiwed ow is missing, ( ͡o ω ͡o ) use an
   * infewwed d-defauwt based on the type of [[featuwevawue]]. σωσ fow nyumewic vawues this is the minvawue
   * (e.g. >w< w-wong.minvawue, 😳😳😳 doubwe.minvawue). OwO
   *
   * @pawam f-featuwe featuwe s-stowe v1 f-featuwe with vawue t-to sowt by
   * @pawam typetag awwows fow infewwing d-defauwt vawue fwom the featuwevawue type. 😳
   *                s-see [[com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.featuwevawuesowtew.featuwevawuesowtdefauwtvawue]]
   * @tpawam candidate candidate fow the featuwe
   * @tpawam featuwevawue featuwe vawue w-with an [[owdewing]] context bound
   */
  d-def a-ascending[candidate <: u-univewsawnoun[any], featuwevawue: owdewing](
    featuwe: f-featuwestowev1candidatefeatuwe[pipewinequewy, 😳😳😳 c-candidate, (˘ω˘) _ <: entityid, ʘwʘ featuwevawue]
  )(
    i-impwicit typetag: t-typetag[featuwevawue]
  ): sowtewpwovidew = {
    v-vaw defauwtfeatuwevawue: featuwevawue = f-featuwevawuesowtdefauwtvawue(featuwe, ( ͡o ω ͡o ) ascending)

    ascending(featuwe, o.O d-defauwtfeatuwevawue)
  }

  /**
   * sowt b-by a featuwe stowe v1 featuwe vawue a-ascending. >w< if t-the featuwe faiwed ow is missing, 😳 use
   * the pwovided defauwt. 🥺
   *
   * @pawam featuwe featuwe stowe v1 featuwe with vawue t-to sowt by
   * @tpawam c-candidate candidate fow t-the featuwe
   * @tpawam f-featuwevawue f-featuwe vawue with an [[owdewing]] context bound
   */
  def a-ascending[candidate <: univewsawnoun[any], rawr x3 featuwevawue: owdewing](
    featuwe: f-featuwestowev1candidatefeatuwe[pipewinequewy, o.O candidate, rawr _ <: e-entityid, ʘwʘ featuwevawue], 😳😳😳
    defauwtfeatuwevawue: f-featuwevawue
  ): s-sowtewpwovidew = {
    vaw o-owdewing = owdewing.by[candidatewithdetaiws, ^^;; f-featuwevawue](
      _.featuwes.getowewsefeatuwestowev1candidatefeatuwe(featuwe, o.O defauwtfeatuwevawue))

    s-sowtewfwomowdewing(owdewing, (///ˬ///✿) a-ascending)
  }

  /**
   * sowt by a featuwe stowe v1 featuwe v-vawue descending. σωσ i-if the featuwe f-faiwed ow i-is missing, nyaa~~ use
   * a-an infewwed defauwt based on the type of [[featuwevawue]]. ^^;; fow nyumewic vawues t-this is the
   * maxvawue (e.g. ^•ﻌ•^ wong.maxvawue, σωσ doubwe.maxvawue). -.-
   *
   * @pawam featuwe featuwe stowe v1 featuwe w-with vawue to sowt by
   * @pawam typetag awwows fow infewwing d-defauwt vawue f-fwom the featuwevawue t-type. ^^;;
   *                see [[com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.featuwevawuesowtew.featuwevawuesowtdefauwtvawue]]
   * @tpawam c-candidate candidate fow the f-featuwe
   * @tpawam f-featuwevawue featuwe vawue with an [[owdewing]] context bound
   */
  def descending[candidate <: u-univewsawnoun[any], XD featuwevawue: o-owdewing](
    featuwe: f-featuwestowev1candidatefeatuwe[pipewinequewy, 🥺 c-candidate, òωó _ <: entityid, featuwevawue]
  )(
    impwicit typetag: t-typetag[featuwevawue]
  ): s-sowtewpwovidew = {
    vaw defauwtfeatuwevawue: f-featuwevawue = f-featuwevawuesowtdefauwtvawue(featuwe, (ˆ ﻌ ˆ)♡ descending)

    descending(featuwe, -.- defauwtfeatuwevawue)
  }

  /**
   * sowt b-by a featuwe stowe v-v1 featuwe v-vawue descending. :3 if the featuwe f-faiwed ow is missing, ʘwʘ u-use
   * the pwovided defauwt. 🥺
   *
   * @pawam f-featuwe featuwe stowe v1 featuwe with vawue to sowt by
   * @tpawam candidate c-candidate fow t-the featuwe
   * @tpawam featuwevawue featuwe v-vawue with an [[owdewing]] c-context bound
   */
  def descending[candidate <: univewsawnoun[any], >_< f-featuwevawue: owdewing](
    featuwe: featuwestowev1candidatefeatuwe[pipewinequewy, ʘwʘ candidate, (˘ω˘) _ <: entityid, (✿oωo) f-featuwevawue], (///ˬ///✿)
    defauwtfeatuwevawue: featuwevawue
  ): s-sowtewpwovidew = {
    v-vaw owdewing = owdewing.by[candidatewithdetaiws, rawr x3 featuwevawue](
      _.featuwes.getowewsefeatuwestowev1candidatefeatuwe(featuwe, -.- defauwtfeatuwevawue))

    s-sowtewfwomowdewing(owdewing, ^^ d-descending)
  }
}
