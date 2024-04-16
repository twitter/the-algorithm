package com.tw ter.ho _m xer.model.request

/**
 * [[HasSeenT et ds]] enables shared components to access t  l st of  mpressed t et  Ds
 * sent by cl ents across d fferent Ho  M xer query types (e.g. Follow ngQuery, For Query)
 */
tra  HasSeenT et ds {
  def seenT et ds: Opt on[Seq[Long]]
}
