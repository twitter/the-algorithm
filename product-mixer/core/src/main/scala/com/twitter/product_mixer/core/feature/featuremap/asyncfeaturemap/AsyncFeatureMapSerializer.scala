package com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.asyncfeatuwemap

impowt c-com.fastewxmw.jackson.cowe.jsongenewatow
i-impowt c-com.fastewxmw.jackson.databind.jsonsewiawizew
impowt c-com.fastewxmw.jackson.databind.sewiawizewpwovidew

/**
 * s-since an [[asyncfeatuwemap]] i-is t-typicawwy incompwete, σωσ a-and by the time it's sewiawized, OwO aww the [[com.twittew.pwoduct_mixew.cowe.featuwe.featuwe]]s
 * it wiww typicawwy be compweted a-and pawt of the quewy ow candidate's individuaw [[com.twittew.pwoduct_mixew.cowe.featuwe.featuwe]]s
 * w-we instead opt to pwovide a-a summawy of the featuwes which wouwd be hydwated using [[asyncfeatuwemap.featuwes]]
 *
 * t-this indicates which [[com.twittew.pwoduct_mixew.cowe.featuwe.featuwe]]s w-wiww be w-weady at which steps
 * and which [[com.twittew.pwoduct_mixew.cowe.functionaw_component.featuwe_hydwatow.featuwehydwatow]]
 * awe wesponsibwe fow those [[com.twittew.pwoduct_mixew.cowe.featuwe.featuwe]]
 *
 * @note changes t-to sewiawization wogic can have sewious pewfowmance impwications given how hot t-the
 *       sewiawization path i-is. 😳😳😳 considew benchmawking c-changes w-with [[com.twittew.pwoduct_mixew.cowe.benchmawk.asyncquewyfeatuwemapsewiawizationbenchmawk]]
 */
p-pwivate[asyncfeatuwemap] cwass asyncfeatuwemapsewiawizew() e-extends jsonsewiawizew[asyncfeatuwemap] {
  ovewwide d-def sewiawize(
    asyncfeatuwemap: asyncfeatuwemap, 😳😳😳
    gen: jsongenewatow, o.O
    sewiawizews: s-sewiawizewpwovidew
  ): unit = {
    g-gen.wwitestawtobject()

    a-asyncfeatuwemap.featuwes.foweach {
      c-case (stepidentifiew, ( ͡o ω ͡o ) featuwehydwatows) =>
        gen.wwiteobjectfiewdstawt(stepidentifiew.tostwing)

        featuwehydwatows.foweach {
          case (hydwatowidentifiew, (U ﹏ U) f-featuwesfwomhydwatow) =>
            g-gen.wwiteawwayfiewdstawt(hydwatowidentifiew.tostwing)

            featuwesfwomhydwatow.foweach(featuwe => g-gen.wwitestwing(featuwe.tostwing))

            g-gen.wwiteendawway()
        }

        gen.wwiteendobject()
    }

    g-gen.wwiteendobject()
  }
}
