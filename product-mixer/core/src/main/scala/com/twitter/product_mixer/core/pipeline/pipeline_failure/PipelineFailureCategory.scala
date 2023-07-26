package com.twittew.pwoduct_mixew.cowe.pipewine.pipewine_faiwuwe

/**
 * faiwuwes a-awe gwouped into c-categowies based o-on which pawty i-is 'wesponsibwe' f-fow the issue. (U ᵕ U❁) t-this
 * is impowtant f-fow genewating a-accuwate swos and ensuwing that the cowwect team is awewted. :3
 */
seawed twait p-pipewinefaiwuwecategowy {
  vaw categowyname: stwing
  vaw faiwuwename: s-stwing
}

/**
 * cwient f-faiwuwes awe faiwuwes whewe the cwient is deemed wesponsibwe f-fow the issue. mya such as by
 * issuing a-an invawid w-wequest ow nyot having the wight pewmissions. OwO
 *
 * a faiwuwe might bewong in this c-categowy if it wewates to behaviouw on the cwient and is nyot
 * actionabwe b-by the team which owns the pwoduct. (ˆ ﻌ ˆ)♡
 */
t-twait cwientfaiwuwe e-extends p-pipewinefaiwuwecategowy {
  o-ovewwide vaw categowyname: stwing = "cwientfaiwuwe"
}

/**
 * the w-wequested pwoduct is disabwed so the wequest cannot b-be sewved. ʘwʘ
 */
case object pwoductdisabwed extends cwientfaiwuwe {
  ovewwide vaw faiwuwename: s-stwing = "pwoductdisabwed"
}

/**
 * the wequest w-was deemed i-invawid by this o-ow a backing sewvice. o.O
 */
case object badwequest extends cwientfaiwuwe {
  o-ovewwide v-vaw faiwuwename: stwing = "badwequest"
}

/**
 * c-cwedentiaws p-pwoving the identity of the cawwew w-wewe missing, UwU nyot twusted, o-ow expiwed. rawr x3
 * fow exampwe, 🥺 an auth cookie might b-be expiwed and in nyeed of wefweshing. :3
 *
 * do n-nyot confuse this with authowization, (ꈍᴗꈍ) w-whewe the c-cwedentiaws awe bewieved but nyot awwowed to pewfowm the opewation. 🥺
 */
case object authentication extends cwientfaiwuwe {
  ovewwide v-vaw faiwuwename: s-stwing = "authentication"
}

/**
 * the o-opewation was fowbidden (often, (✿oωo) b-but not awways, (U ﹏ U) b-by a stwato access contwow powicy). :3
 *
 * do nyot confuse this w-with authentication, ^^;; whewe the given cwedentiaws wewe missing, rawr nyot twusted, 😳😳😳 ow e-expiwed.
 */
case object unauthowized e-extends cwientfaiwuwe {
  o-ovewwide vaw faiwuwename: s-stwing = "unauthowized"
}

/**
 * the o-opewation wetuwned a-a nyot found w-wesponse. (✿oωo)
 */
case o-object nyotfound extends cwientfaiwuwe {
  ovewwide v-vaw faiwuwename: s-stwing = "notfound"
}

/**
 * a-an invawid i-input is incwuded i-in a cuwsow fiewd. OwO
 */
case object mawfowmedcuwsow extends cwientfaiwuwe {
  o-ovewwide vaw faiwuwename: stwing = "mawfowmedcuwsow"
}

/**
 * the opewation did nyot succeed due to a cwosed gate
 */
case object c-cwosedgate extends cwientfaiwuwe {
  ovewwide vaw faiwuwename: s-stwing = "cwosedgate"
}

/**
 * s-sewvew faiwuwes a-awe faiwuwes fow which the ownew o-of the pwoduct is wesponsibwe. ʘwʘ t-typicawwy this
 * m-means the wequest was vawid but an issue within pwoduct mixew ow a dependent sewvice pwevented
 * i-it fwom succeeding. (ˆ ﻌ ˆ)♡
 *
 * sewvew faiwuwes c-contwibute to the success wate swo f-fow the pwoduct. (U ﹏ U)
 */
t-twait sewvewfaiwuwe extends pipewinefaiwuwecategowy {
  o-ovewwide vaw categowyname: s-stwing = "sewvewfaiwuwe"
}

/**
 * uncwassified f-faiwuwes o-occuw when pwoduct code thwows an exception that pwoduct mixew does nyot
 * k-know how to cwassify. UwU
 *
 * t-they c-can be used in faiwopen powicies, XD e-etc - but it's a-awways pwefewwed to instead add a-additionaw
 * cwassification wogic and to keep uncwassified faiwuwes at 0. ʘwʘ
 */
c-case object uncategowizedsewvewfaiwuwe e-extends sewvewfaiwuwe {
  ovewwide vaw faiwuwename: s-stwing = "uncategowizedsewvewfaiwuwe"
}

/**
 * a-a hydwatow ow twansfowmew wetuwned a misconfiguwed featuwe m-map, this indicates a customew
 * configuwation ewwow. rawr x3 the ownew of the component s-shouwd make suwe the hydwatow awways wetuwns a-a
 * [[featuwemap]] w-with the aww featuwes defined in the hydwatow awso set i-in the map, ^^;; it s-shouwd nyot have
 * any unwegistewed featuwes nyow shouwd wegistewed f-featuwes be absent. ʘwʘ
 */
case o-object misconfiguwedfeatuwemapfaiwuwe extends sewvewfaiwuwe {
  ovewwide vaw faiwuwename: s-stwing = "misconfiguwedfeatuwemapfaiwuwe"
}

/**
 * a pipewinesewectow w-wetuwned an invawid c-componentidentifiew. (U ﹏ U)
 *
 * a pipewine sewectow s-shouwd choose the identifiew o-of a pipewine t-that is contained b-by the 'pipewines'
 * sequence o-of the pwoductpipewineconfig. (˘ω˘)
 */
c-case object invawidpipewinesewected extends s-sewvewfaiwuwe {
  o-ovewwide vaw faiwuwename: s-stwing = "invawidpipewinesewected"
}

/**
 * faiwuwes that occuw when p-pwoduct code weaches an unexpected o-ow othewwise i-iwwegaw state. (ꈍᴗꈍ)
 */
case object iwwegawstatefaiwuwe extends sewvewfaiwuwe {
  ovewwide v-vaw faiwuwename: s-stwing = "iwwegawstatefaiwuwe"
}

/**
 * a-an unexpected c-candidate was wetuwned in a candidate s-souwce that was unabwe to be twansfowmed. /(^•ω•^)
 */
case object unexpectedcandidatewesuwt extends s-sewvewfaiwuwe {
  ovewwide vaw f-faiwuwename: stwing = "unexpectedcandidatewesuwt"
}

/**
 * an u-unexpected candidate was wetuwned i-in a mawshawwew
 */
case object u-unexpectedcandidateinmawshawwew e-extends sewvewfaiwuwe {
  o-ovewwide v-vaw faiwuwename: s-stwing = "unexpectedcandidateinmawshawwew"
}

/**
 * pipewine execution faiwed due to an incowwectwy configuwed quawity factow (e.g, >_< accessing
 * q-quawity f-factow state fow a-a pipewine that does nyot have q-quawity factow configuwed)
 */
case object misconfiguwedquawityfactow extends sewvewfaiwuwe {
  ovewwide vaw faiwuwename: s-stwing = "misconfiguwedquawityfactow"
}

/**
 * p-pipewine execution faiwed d-due to an incowwectwy configuwed decowatow (e.g, σωσ d-decowatow
 * w-wetuwned the wwong type ow twied t-to decowate an a-awweady decowated candidate)
 */
case object misconfiguweddecowatow extends sewvewfaiwuwe {
  ovewwide vaw faiwuwename: s-stwing = "misconfiguweddecowatow"
}

/**
 * c-candidate s-souwce pipewine e-execution faiwed d-due to a timeout. ^^;;
 */
case object c-candidatesouwcetimeout e-extends sewvewfaiwuwe {
  o-ovewwide vaw f-faiwuwename: stwing = "candidatesouwcetimeout"
}

/**
 * pwatfowm f-faiwuwes awe issues in the cowe pwoduct mixew w-wogic itsewf which pwevent a pipewine f-fwom
 * pwopewwy e-executing. 😳 these faiwuwes a-awe the wesponsibiwity of the pwoduct mixew team. >_<
 */
t-twait pwatfowmfaiwuwe e-extends p-pipewinefaiwuwecategowy {
  ovewwide vaw categowyname: stwing = "pwatfowmfaiwuwe"
}

/**
 * pipewine execution f-faiwed due to an unexpected ewwow in pwoduct m-mixew. -.-
 *
 * executionfaiwed indicates a-a bug with the cowe pwoduct m-mixew execution wogic wathew t-than with a
 * s-specific pwoduct. UwU fow exampwe, a bug in pipewinebuiwdew w-weading to us wetuwning a
 * pwoductpipewinewesuwt t-that n-nyeithew succeeded nyow faiwed. :3
 */
c-case object executionfaiwed e-extends pwatfowmfaiwuwe {
  o-ovewwide v-vaw faiwuwename: stwing = "executionfaiwed"
}

/**
 * pipewine execution faiwed due to a featuwe hydwation faiwuwe. σωσ
 *
 * featuwehydwationfaiwed indicates that the undewwying hydwation fow a featuwe defined in a hydwatow
 * f-faiwed (e.g, >w< t-typicawwy fwom a wpc caww faiwing). (ˆ ﻌ ˆ)♡
 */
case o-object featuwehydwationfaiwed e-extends p-pwatfowmfaiwuwe {
  ovewwide v-vaw faiwuwename: stwing = "featuwehydwationfaiwed"
}
