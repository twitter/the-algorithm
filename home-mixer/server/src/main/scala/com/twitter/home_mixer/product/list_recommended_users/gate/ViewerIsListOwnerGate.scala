package com.twittew.home_mixew.pwoduct.wist_wecommended_usews.gate

impowt com.twittew.home_mixew.modew.wequest.haswistid
i-impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.gate
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt com.twittew.sociawgwaph.{thwiftscawa => s-sg}
impowt com.twittew.stitch.stitch
impowt com.twittew.stitch.sociawgwaph.sociawgwaph

impowt javax.inject.inject
i-impowt javax.inject.singweton

@singweton
case cwass viewewiswistownewgate @inject() (sociawgwaph: s-sociawgwaph)
    extends g-gate[pipewinequewy with haswistid] {

  ovewwide vaw identifiew: g-gateidentifiew = gateidentifiew("viewewiswistownew")

  pwivate v-vaw wewationship = s-sg.wewationship(wewationshiptype = sg.wewationshiptype.wistowning)

  ovewwide def shouwdcontinue(quewy: pipewinequewy with haswistid): s-stitch[boowean] = {
    vaw wequest = sg.existswequest(
      souwce = quewy.getwequiwedusewid,
      tawget = q-quewy.wistid, (U ﹏ U)
      wewationships = s-seq(wewationship))
    s-sociawgwaph.exists(wequest).map(_.exists)
  }
}
