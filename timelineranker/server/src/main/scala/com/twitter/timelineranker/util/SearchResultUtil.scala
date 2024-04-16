package com.tw ter.t  l neranker.ut l

 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.User d

object SearchResultUt l {
  val DefaultScore = 0.0
  def getScore(result: Thr ftSearchResult): Double = {
    result. tadata.flatMap(_.score).f lterNot(_. sNaN).getOrElse(DefaultScore)
  }

  def  sRet et(result: Thr ftSearchResult): Boolean = {
    result. tadata.flatMap(_. sRet et).getOrElse(false)
  }

  def  sReply(result: Thr ftSearchResult): Boolean = {
    result. tadata.flatMap(_. sReply).getOrElse(false)
  }

  def  sEl g bleReply(result: Thr ftSearchResult): Boolean = {
     sReply(result) && ! sRet et(result)
  }

  def author d(result: Thr ftSearchResult): Opt on[User d] = {
    // fromUser d defaults to 0L  f unset. None  s cleaner
    result. tadata.map(_.fromUser d).f lter(_ != 0L)
  }

  def referencedT etAuthor d(result: Thr ftSearchResult): Opt on[User d] = {
    // referencedT etAuthor d defaults to 0L by default. None  s cleaner
    result. tadata.map(_.referencedT etAuthor d).f lter(_ != 0L)
  }

  /**
   * Extended repl es are repl es, that are not ret ets (see below), from a follo d user d
   * towards a non-follo d user d.
   *
   *  n Thr ft SearchResult    s poss ble to have both  sRet et and  sReply set to true,
   *  n t  case of t  ret eted reply. T   s confus ng edge case as t  ret et object
   *  s not  self a reply, but t  or g nal t et  s reply.
   */
  def  sExtendedReply(follo dUser ds: Seq[User d])(result: Thr ftSearchResult): Boolean = {
     sEl g bleReply(result) &&
    author d(result).ex sts(follo dUser ds.conta ns(_)) && // author  s follo d
    referencedT etAuthor d(result).ex sts(!follo dUser ds.conta ns(_)) // referenced author  s not
  }

  /**
   *  f a t et  s a reply that  s not a ret et, and both t  user follows both t  reply author
   * and t  reply parent's author
   */
  def  s nNetworkReply(follo dUser ds: Seq[User d])(result: Thr ftSearchResult): Boolean = {
     sEl g bleReply(result) &&
    author d(result).ex sts(follo dUser ds.conta ns(_)) && // author  s follo d
    referencedT etAuthor d(result).ex sts(follo dUser ds.conta ns(_)) // referenced author  s
  }

  /**
   *  f a t et  s a ret et, and user follows author of outs de t et but not follow ng author of
   * s ce/ nner t et. T  t et  s also called oon-ret et
   */
  def  sOutOfNetworkRet et(follo dUser ds: Seq[User d])(result: Thr ftSearchResult): Boolean = {
     sRet et(result) &&
    author d(result).ex sts(follo dUser ds.conta ns(_)) && // author  s follo d
    referencedT etAuthor d(result).ex sts(!follo dUser ds.conta ns(_)) // referenced author  s not
  }

  /**
   * From off c al docu ntat on  n thr ft on sharedStatus d:
   * W n  sRet et (or packed features equ valent)  s true, t   s t  status  d of t 
   * or g nal t et. W n  sReply and getReplyS ce are true, t   s t  status  d of t 
   * or g nal t et.  n all ot r c rcumstances t   s 0.
   *
   *  f a t et  s a ret et of a reply, t   s t  status  d of t  reply (t  or g nal t et
   * of t  ret et), not t  reply's  n-reply-to t et status  d.
   */
  def getS ceT et d(result: Thr ftSearchResult): Opt on[T et d] = {
    result. tadata.map(_.sharedStatus d).f lter(_ != 0L)
  }

  def getRet etS ceT et d(result: Thr ftSearchResult): Opt on[T et d] = {
     f ( sRet et(result)) {
      getS ceT et d(result)
    } else {
      None
    }
  }

  def get nReplyToT et d(result: Thr ftSearchResult): Opt on[T et d] = {
     f ( sReply(result)) {
      getS ceT et d(result)
    } else {
      None
    }
  }

  def getReplyRootT et d(result: Thr ftSearchResult): Opt on[T et d] = {
     f ( sEl g bleReply(result)) {
      for {
         ta <- result. tadata
        extra ta <-  ta.extra tadata
        conversat on d <- extra ta.conversat on d
      } y eld {
        conversat on d
      }
    } else {
      None
    }
  }

  /**
   * For ret et: selfT et d + s ceT et d, (ho ver selfT et d  s redundant  re, s nce  alth
   * score ret et by t et d == s ceT et d)
   * For repl es: selfT et d +  m d ate ancestor t et d + root ancestor t et d.
   * Use set to de-dupl cate t  case w n s ce t et == root t et. (l ke A->B, B  s root and s ce).
   */
  def getOr g nalT et dAndAncestorT et ds(searchResult: Thr ftSearchResult): Set[T et d] = {
    Set(searchResult. d) ++
      SearchResultUt l.getS ceT et d(searchResult).toSet ++
      SearchResultUt l.getReplyRootT et d(searchResult).toSet
  }
}
