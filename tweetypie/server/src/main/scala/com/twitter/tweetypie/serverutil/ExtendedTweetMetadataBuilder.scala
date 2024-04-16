package com.tw ter.t etyp e.serverut l

 mport com.tw ter.t etyp e.getCashtags
 mport com.tw ter.t etyp e.getHashtags
 mport com.tw ter.t etyp e.get d a
 mport com.tw ter.t etyp e.get nt ons
 mport com.tw ter.t etyp e.getText
 mport com.tw ter.t etyp e.getUrls
 mport com.tw ter.t etyp e.thr ftscala.ExtendedT et tadata
 mport com.tw ter.t etyp e.thr ftscala.ShortenedUrl
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.t etyp e.t ettext.Offset
 mport com.tw ter.t etyp e.t ettext.TextEnt y
 mport com.tw ter.t etyp e.t ettext.Truncator
 mport com.tw ter.t etyp e.t ettext.T etText
 mport com.tw ter.t etyp e.thr ftscala.ent  es. mpl c s._

/**
 * Computes t  appropr ate truncat on  ndex to support render ng on legacy cl ents.
 */
object ExtendedT et tadataBu lder {
   mport T etText._

  def apply(t et: T et, selfPermal nk: ShortenedUrl): ExtendedT et tadata = {

    def ent yRanges[T: TextEnt y](ent  es: Seq[T]): Seq[( nt,  nt)] =
      ent  es.map(e => (TextEnt y.from ndex(e).to nt, TextEnt y.to ndex(e).to nt))

    val allEnt yRanges =
      Offset.Ranges.fromCodePo ntPa rs(
        ent yRanges(getUrls(t et)) ++
          ent yRanges(get nt ons(t et)) ++
          ent yRanges(get d a(t et)) ++
          ent yRanges(getHashtags(t et)) ++
          ent yRanges(getCashtags(t et))
      )

    val text = getText(t et)

    val ap Compat bleTruncat on ndex =
      // need to leave enough space for ell ps s, space, and self-permal nk
      Truncator.truncat onPo nt(
        text = text,
        maxD splayLength = Or g nalMaxD splayLength - selfPermal nk.shortUrl.length - 2,
        atom cUn s = allEnt yRanges
      )

    ExtendedT et tadata(
      ap Compat bleTruncat on ndex = ap Compat bleTruncat on ndex.codePo ntOffset.to nt
    )
  }
}
