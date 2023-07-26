package com.twittew.simcwustews_v2.common

impowt c-com.twittew.simcwustews_v2.thwiftscawa.simcwustewsembeddingid

/**
 * a-a common w-wibwawy to constwuct c-cache key fow s-simcwustewsembeddingid. :3
 */
case c-cwass simcwustewsembeddingidcachekeybuiwdew(
  h-hash: awway[byte] => w-wong, (U ﹏ U)
  pwefix: stwing = "") {

  // exampwe: "cw:sce:1:2:1234567890abcdef"
  def appwy(embeddingid: simcwustewsembeddingid): s-stwing = {
    f"$pwefix:sce:${embeddingid.embeddingtype.getvawue()}%x:" +
      f"${embeddingid.modewvewsion.getvawue()}%x" +
      f-f":${hash(embeddingid.intewnawid.tostwing.getbytes)}%x"
  }

}
