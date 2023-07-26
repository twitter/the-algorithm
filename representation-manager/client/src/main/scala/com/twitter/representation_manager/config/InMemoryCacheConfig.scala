package com.twittew.wepwesentation_managew.config

impowt com.twittew.simcwustews_v2.thwiftscawa.embeddingtype
i-impowt c-com.twittew.simcwustews_v2.thwiftscawa.modewvewsion
i-impowt c-com.twittew.utiw.duwation

/*
 * --------------------------------------------
 * p-pwease nyote:
 * h-having in-memowy c-cache is nyot n-necessawiwy a fwee pewfowmance win, mya anyone considewing it shouwd
 * investigate w-wathew than bwindwy enabwing it
 * --------------------------------------------
 * */

seawed twait i-inmemowycachepawams

/*
 * this howds pawams t-that is wequiwed to set up a in-mem cache fow a singwe embedding s-stowe
 */
case cwass enabwedinmemowycachepawams(
  t-ttw: duwation, 😳
  m-maxkeys: int, -.-
  cachename: stwing)
  extends inmemowycachepawams
object disabwedinmemowycachepawams e-extends inmemowycachepawams

/*
 * this is the cwass fow the in-memowy c-cache config. cwient couwd pass i-in theiw own cachepawamsmap t-to
 * c-cweate a nyew i-inmemowycacheconfig instead of using the defauwtinmemowycacheconfig o-object bewow
 * */
cwass inmemowycacheconfig(
  cachepawamsmap: m-map[
    (embeddingtype, 🥺 modewvewsion), o.O
    inmemowycachepawams
  ] = map.empty) {

  def getcachesetup(
    embeddingtype: e-embeddingtype, /(^•ω•^)
    modewvewsion: m-modewvewsion
  ): i-inmemowycachepawams = {
    // w-when wequested embedding type doesn't exist, nyaa~~ we wetuwn disabwedinmemowycachepawams
    c-cachepawamsmap.getowewse((embeddingtype, nyaa~~ m-modewvewsion), :3 disabwedinmemowycachepawams)
  }
}

/*
 * d-defauwt c-config fow the in-memowy cache
 * c-cwients can diwectwy impowt a-and use this one if they don't want to set up a-a customised config
 * */
object d-defauwtinmemowycacheconfig extends i-inmemowycacheconfig {
  // s-set defauwt to nyo in-memowy caching
  vaw cachepawamsmap = map.empty
}
