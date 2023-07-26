package com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.featuwestowev1

impowt c-com.twittew.mw.api.datawecowd
i-impowt c-com.twittew.mw.featuwestowe.wib.entityid
i-impowt c-com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.featuwemap
i-impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.missingfeatuweexception
i-impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwestowev1.featuwestowev1candidatefeatuwe
impowt c-com.twittew.pwoduct_mixew.cowe.featuwe.featuwestowev1.featuwestowev1candidatefeatuwegwoup
impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwestowev1.featuwestowev1quewyfeatuwe
impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwestowev1.featuwestowev1quewyfeatuwegwoup
impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwestowev1.featuwevawue.featuwestowev1wesponsefeatuwe
impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.univewsawnoun
impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt com.twittew.utiw.twy

o-object featuwestowev1featuwemap {

  /**
   * impwicitwy add convenience accessows fow featuwestowev1 f-featuwes in [[featuwemap]]. (U ᵕ U❁) n-nyote t-that
   * we cannot add these methods diwectwy to [[featuwemap]] because it wouwd intwoduce a ciwcuwaw
   * d-dependency ([[pipewinequewy]] depends on [[featuwemap]], :3 and the methods bewow depend o-on
   * [[pipewinequewy]])
   *
   * @pawam featuwemap t-the featuwemap w-we awe wwapping
   * @note t-the featuwestowev1featuwe::defauwtvawue s-set on the boundfeatuwe is onwy used a-and set
   *       duwing pwedictionwecowd to datawecowd c-convewsion. ( ͡o ω ͡o ) thewefowe, the defauwt wiww nyot be set
   *       on the pwedictionwecowd vawue if weading f-fwom it diwectwy, òωó and as such fow c-convenience
   *       t-the defauwtvawue i-is manuawwy wetuwned duwing wetwievaw fwom pwedictionwecowd. σωσ
   * @note t-the vawue genewic t-type on the methods bewow cannot b-be passed t-to
   *       featuwestowev1quewyfeatuwe's vawue g-genewic type. (U ᵕ U❁) whiwe this is actuawwy t-the same type, (✿oωo)
   *       (note the expwicit type cast back t-to vawue), ^^ we must instead use a-an existentiaw on
   *       featuwestowev1quewyfeatuwe s-since it i-is constwucted with an existentiaw fow the vawue
   *       genewic (see [[featuwestowev1quewyfeatuwe]] and [[featuwestowev1candidatefeatuwe]])
   */
  impwicit cwass featuwestowev1featuwemapaccessows(pwivate v-vaw featuwemap: f-featuwemap) {

    def getfeatuwestowev1quewyfeatuwe[quewy <: p-pipewinequewy, ^•ﻌ•^ v-vawue](
      featuwe: f-featuwestowev1quewyfeatuwe[quewy, XD _ <: entityid, :3 vawue]
    ): vawue =
      g-getowewsefeatuwestowev1quewyfeatuwe(
        featuwe, (ꈍᴗꈍ)
        featuwe.defauwtvawue.getowewse {
          thwow missingfeatuweexception(featuwe)
        })

    d-def getfeatuwestowev1quewyfeatuwetwy[quewy <: pipewinequewy, :3 v-vawue](
      featuwe: f-featuwestowev1quewyfeatuwe[quewy, (U ﹏ U) _ <: entityid, UwU v-vawue]
    ): twy[vawue] =
      t-twy(getfeatuwestowev1quewyfeatuwe(featuwe))

    d-def getowewsefeatuwestowev1quewyfeatuwe[quewy <: p-pipewinequewy, 😳😳😳 v-vawue](
      featuwe: featuwestowev1quewyfeatuwe[quewy, XD _ <: e-entityid, o.O v-vawue], (⑅˘꒳˘)
      d-defauwt: => vawue
    ): v-vawue = {

      /**
       * f-featuwestowev1wesponsefeatuwe shouwd nyevew be missing fwom the featuwemap a-as fsv1 is
       * guawanteed to wetuwn a pwediction wecowd pew featuwe stowe wequest. 😳😳😳 howevew, nyaa~~ t-this may be
       * cawwed on candidates that nyevew hydwated f-fsv1 featuwes. rawr f-fow exampwe by
       * [[com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.featuwestowev1.featuwestowev1featuwevawuesowtew]]
       */
      v-vaw featuwestowev1featuwevawueopt = featuwemap.gettwy(featuwestowev1wesponsefeatuwe).tooption

      vaw d-datawecowdvawue: option[vawue] = f-featuwestowev1featuwevawueopt.fwatmap {
        f-featuwestowev1featuwevawue =>
          featuwestowev1featuwevawue.wichdatawecowd.getfeatuwevawueopt(
            featuwe.boundfeatuwe.mwapifeatuwe)(featuwe.fwomdatawecowdvawue)
      }

      datawecowdvawue.getowewse(defauwt)
    }

    def getfeatuwestowev1candidatefeatuwe[
      quewy <: pipewinequewy, -.-
      c-candidate <: univewsawnoun[any], (✿oωo)
      v-vawue
    ](
      featuwe: f-featuwestowev1candidatefeatuwe[quewy, /(^•ω•^) c-candidate, 🥺 _ <: entityid, ʘwʘ vawue]
    ): vawue =
      g-getowewsefeatuwestowev1candidatefeatuwe(
        f-featuwe, UwU
        featuwe.defauwtvawue.getowewse {
          thwow m-missingfeatuweexception(featuwe)
        })

    d-def getfeatuwestowev1candidatefeatuwetwy[
      quewy <: pipewinequewy, XD
      candidate <: univewsawnoun[any], (✿oωo)
      vawue
    ](
      featuwe: f-featuwestowev1candidatefeatuwe[quewy, :3 c-candidate, (///ˬ///✿) _ <: e-entityid, nyaa~~ vawue]
    ): t-twy[vawue] =
      t-twy(getfeatuwestowev1candidatefeatuwe(featuwe))

    def getowewsefeatuwestowev1candidatefeatuwe[
      q-quewy <: pipewinequewy, >w<
      candidate <: univewsawnoun[any], -.-
      vawue
    ](
      f-featuwe: featuwestowev1candidatefeatuwe[quewy, (✿oωo) c-candidate, (˘ω˘) _ <: entityid, rawr vawue], OwO
      defauwt: => v-vawue
    ): v-vawue = {

      /**
       * featuwestowev1wesponsefeatuwe shouwd nyevew be missing fwom the f-featuwemap as fsv1 is
       * guawanteed to wetuwn a pwediction wecowd pew featuwe s-stowe wequest. ^•ﻌ•^ howevew, UwU this may be
       * c-cawwed on candidates t-that nyevew hydwated fsv1 featuwes. (˘ω˘) fow exampwe by
       * [[com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew.featuwestowev1.featuwestowev1featuwevawuesowtew]]
       */
      v-vaw featuwestowev1featuwevawueopt = f-featuwemap.gettwy(featuwestowev1wesponsefeatuwe).tooption

      vaw datawecowdvawue: option[vawue] = featuwestowev1featuwevawueopt.fwatmap {
        f-featuwestowev1featuwevawue =>
          featuwestowev1featuwevawue.wichdatawecowd.getfeatuwevawueopt(
            featuwe.boundfeatuwe.mwapifeatuwe)(featuwe.fwomdatawecowdvawue)
      }

      d-datawecowdvawue.getowewse(defauwt)
    }

    /**
     * get quewyfeatuwegwoup, which is stowe in t-the featuwemap as a datawecowdinafeatuwe
     * i-it doesn't have t-the mwapifeatuwe as othew weguwaw f-featuwestowev1 featuwes
     * p-pwease wefew to [[com.twittew.pwoduct_mixew.cowe.featuwe.datawecowd.datawecowdinafeatuwe]] s-scawadoc f-fow mowe detaiws
     */
    def getfeatuwestowev1quewyfeatuwegwoup[quewy <: p-pipewinequewy](
      f-featuwegwoup: featuwestowev1quewyfeatuwegwoup[quewy, (///ˬ///✿) _ <: entityid]
    ): d-datawecowd =
      g-getowewsefeatuwestowev1quewyfeatuwegwoup(
        f-featuwegwoup,
        thwow missingfeatuweexception(featuwegwoup)
      )

    d-def getfeatuwestowev1candidatefeatuwegwouptwy[quewy <: pipewinequewy](
      f-featuwegwoup: f-featuwestowev1quewyfeatuwegwoup[quewy, σωσ _ <: entityid]
    ): twy[datawecowd] =
      twy(getfeatuwestowev1quewyfeatuwegwoup(featuwegwoup))

    def getowewsefeatuwestowev1quewyfeatuwegwoup[quewy <: p-pipewinequewy](
      f-featuwegwoup: f-featuwestowev1quewyfeatuwegwoup[quewy, /(^•ω•^) _ <: e-entityid], 😳
      defauwt: => d-datawecowd
    ): datawecowd = {
      featuwemap.gettwy(featuwegwoup).tooption.getowewse(defauwt)
    }

    /**
     * get candidatefeatuwegwoup, 😳 which is s-stowe in the featuwemap as a datawecowdinafeatuwe
     * i-it doesn't have the mwapifeatuwe a-as othew weguwaw featuwestowev1 f-featuwes
     * pwease w-wefew to [[com.twittew.pwoduct_mixew.cowe.featuwe.datawecowd.datawecowdinafeatuwe]] s-scawadoc f-fow mowe detaiws
     */
    d-def g-getfeatuwestowev1candidatefeatuwegwoup[
      quewy <: pipewinequewy, (⑅˘꒳˘)
      candidate <: univewsawnoun[any]
    ](
      featuwegwoup: featuwestowev1candidatefeatuwegwoup[quewy, 😳😳😳 candidate, 😳 _ <: e-entityid]
    ): d-datawecowd =
      g-getowewsefeatuwestowev1candidatefeatuwegwoup(
        featuwegwoup, XD
        t-thwow missingfeatuweexception(featuwegwoup)
      )

    def getfeatuwestowev1candidatefeatuwegwouptwy[
      quewy <: pipewinequewy, mya
      candidate <: u-univewsawnoun[any]
    ](
      f-featuwegwoup: featuwestowev1candidatefeatuwegwoup[quewy, ^•ﻌ•^ c-candidate, ʘwʘ _ <: entityid]
    ): twy[datawecowd] =
      t-twy(getfeatuwestowev1candidatefeatuwegwoup(featuwegwoup))

    d-def getowewsefeatuwestowev1candidatefeatuwegwoup[
      q-quewy <: pipewinequewy, ( ͡o ω ͡o )
      c-candidate <: univewsawnoun[any]
    ](
      featuwegwoup: featuwestowev1candidatefeatuwegwoup[quewy, mya candidate, o.O _ <: entityid], (✿oωo)
      d-defauwt: => d-datawecowd
    ): d-datawecowd = {
      f-featuwemap.gettwy(featuwegwoup).tooption.getowewse(defauwt)
    }

    d-def getowewsefeatuwestowev1featuwedatawecowd(
      defauwt: => d-datawecowd
    ) = {
      vaw f-featuwestowev1featuwevawueopt = featuwemap.gettwy(featuwestowev1wesponsefeatuwe).tooption

      f-featuwestowev1featuwevawueopt
        .map { f-featuwestowev1featuwevawue =>
          featuwestowev1featuwevawue.wichdatawecowd.getwecowd
        }.getowewse(defauwt)
    }
  }
}
