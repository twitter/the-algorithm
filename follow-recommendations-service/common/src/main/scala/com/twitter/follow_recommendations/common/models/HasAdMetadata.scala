package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.adserver.{thr ftscala => t}

case class Ad tadata(
   nsertPos  on:  nt,
  // use or g nal ad  mpress on  nfo to avo d los ng data  n doma n model translat ons
  ad mpress on: t.Ad mpress on)

tra  HasAd tadata {

  def ad tadata: Opt on[Ad tadata]

  def ad mpress on: Opt on[t.Ad mpress on] = {
    ad tadata.map(_.ad mpress on)
  }

  def  nsertPos  on: Opt on[ nt] = {
    ad tadata.map(_. nsertPos  on)
  }

  def  sPromotedAccount: Boolean = ad tadata. sDef ned
}
