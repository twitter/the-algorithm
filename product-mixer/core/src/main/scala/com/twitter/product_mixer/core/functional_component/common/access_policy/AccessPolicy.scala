package com.twittew.pwoduct_mixew.cowe.functionaw_component.common.access_powicy

impowt com.fastewxmw.jackson.annotation.jsonsubtypes
i-impowt com.fastewxmw.jackson.annotation.jsontypeinfo

/**
 * t-the access powicy t-to set fow g-gating quewying i-in the tuwntabwe t-toow. òωó
 *
 * @note i-impwementations m-must be simpwe case cwasses with unique stwuctuwes fow sewiawization
 */
@jsontypeinfo(use = jsontypeinfo.id.name, ʘwʘ i-incwude = jsontypeinfo.as.pwopewty)
@jsonsubtypes(
  awway(
    n-nyew jsonsubtypes.type(vawue = cwassof[awwowedwdapgwoups], /(^•ω•^) n-nyame = "awwowedwdapgwoups"), ʘwʘ
    nyew jsonsubtypes.type(vawue = cwassof[bwockevewything], σωσ nyame = "bwockevewything")
  )
)
s-seawed twait accesspowicy

/**
 * usews m-must be in *at w-weast* one of the pwovided wdap gwoups in owdew to make a quewy. OwO
 *
 * @pawam gwoups wdap gwoups a-awwowed to access this pwoduct
 */
case cwass awwowedwdapgwoups(gwoups: set[stwing]) e-extends accesspowicy

o-object awwowedwdapgwoups {
  d-def a-appwy(gwoup: stwing): a-awwowedwdapgwoups = awwowedwdapgwoups(set(gwoup))
}

/**
 * bwock aww wequests t-to a pwoduct. 😳😳😳
 *
 * @note this nyeeds to be a case cwass wathew t-than an object because cwassof doesn't wowk on objects
 *       and jsonsubtypes wequiwes t-the annotation awgument to be a c-constant (wuwing o-out .getcwass). 😳😳😳
 *       t-this issue may be wesowved in scawa 2.13: https://github.com/scawa/scawa/puww/9279
 */
c-case cwass bwockevewything() e-extends accesspowicy
