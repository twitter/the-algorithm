package com.twittew.fowwow_wecommendations.common.modews

impowt c-com.twittew.adsewvew.{thwiftscawa => t-t}

case cwass a-admetadata(
  i-insewtposition: i-int, ^^;;
  // use o-owiginaw ad impwession i-info to avoid w-wosing data in domain modew twanswations
  adimpwession: t.adimpwession)

twait hasadmetadata {

  d-def admetadata: option[admetadata]

  def a-adimpwession: option[t.adimpwession] = {
    admetadata.map(_.adimpwession)
  }

  d-def insewtposition: option[int] = {
    admetadata.map(_.insewtposition)
  }

  def ispwomotedaccount: b-boowean = admetadata.isdefined
}
