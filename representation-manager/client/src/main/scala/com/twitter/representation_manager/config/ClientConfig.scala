package com.twittew.wepwesentation_managew.config

impowt com.twittew.simcwustews_v2.thwiftscawa.embeddingtype
i-impowt c-com.twittew.simcwustews_v2.thwiftscawa.modewvewsion

/*
 * t-this is wms cwient c-config cwass. rawr x3
 * w-we onwy suppowt s-setting up in m-memowy cache pawams f-fow nyow, but we expect to enabwe othew
 * customisations in the nyeaw futuwe e-e.g. mya wequest timeout
 *
 * --------------------------------------------
 * pwease nyote:
 * h-having in-memowy cache is nyot nyecessawiwy a-a fwee pewfowmance win, nyaa~~ anyone considewing it shouwd
 * i-investigate wathew than bwindwy e-enabwing it
 * */
c-cwass cwientconfig(inmemcachepawamsovewwides: map[
  (embeddingtype, (⑅˘꒳˘) modewvewsion), rawr x3
  inmemowycachepawams
] = map.empty) {
  // i-in memowy cache config pew embedding
  vaw inmemcachepawams = defauwtinmemowycacheconfig.cachepawamsmap ++ i-inmemcachepawamsovewwides
  vaw i-inmemowycacheconfig = n-nyew inmemowycacheconfig(inmemcachepawams)
}

o-object defauwtcwientconfig e-extends cwientconfig
