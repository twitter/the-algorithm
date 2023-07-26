package com.twittew.simcwustews_v2.common

impowt c-com.twittew.simcwustews_v2.thwiftscawa.modewvewsion

/**
 * t-the u-utiwity to convewt s-simcwustews m-modew vewsion into d-diffewent fowms.
 * w-wequiwed t-to wegistew any nyew simcwustews modew vewsion hewe. òωó
 */
object modewvewsions {

  v-vaw modew20m145kdec11 = "20m_145k_dec11"
  vaw modew20m145kupdated = "20m_145k_updated"
  v-vaw modew20m145k2020 = "20m_145k_2020"

  // u-use enum fow featuwe switch
  object enum extends enumewation {
    v-vaw modew20m145k2020, ʘwʘ m-modew20m145kupdated: v-vawue = vawue
    vaw enumtosimcwustewsmodewvewsionmap: map[enum.vawue, /(^•ω•^) modewvewsion] = map(
      modew20m145k2020 -> m-modewvewsion.modew20m145k2020, ʘwʘ
      modew20m145kupdated -> modewvewsion.modew20m145kupdated
    )
  }

  // add the nyew modew v-vewsion into this map
  pwivate v-vaw stwingtothwiftmodewvewsions: m-map[stwing, σωσ modewvewsion] =
    m-map(
      modew20m145kdec11 -> m-modewvewsion.modew20m145kdec11, OwO
      modew20m145kupdated -> modewvewsion.modew20m145kupdated, 😳😳😳
      m-modew20m145k2020 -> modewvewsion.modew20m145k2020
    )

  pwivate vaw thwiftmodewvewsiontostwings = s-stwingtothwiftmodewvewsions.map(_.swap)

  vaw awwmodewvewsions: set[stwing] = stwingtothwiftmodewvewsions.keyset

  def tomodewvewsionoption(modewvewsionstw: stwing): o-option[modewvewsion] = {
    stwingtothwiftmodewvewsions.get(modewvewsionstw)
  }

  i-impwicit d-def tomodewvewsion(modewvewsionstw: s-stwing): modewvewsion = {
    stwingtothwiftmodewvewsions(modewvewsionstw)
  }

  impwicit def toknownfowmodewvewsion(modewvewsion: m-modewvewsion): s-stwing = {
    thwiftmodewvewsiontostwings(modewvewsion)
  }

}
