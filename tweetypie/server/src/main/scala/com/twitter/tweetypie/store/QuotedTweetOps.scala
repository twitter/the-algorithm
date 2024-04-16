package com.tw ter.t etyp e
package store

/**
 * M x n that  mple nts publ c quoted t et and publ c quoted user
 * f lter ng for t et events that have quoted t ets and users.
 */
tra  QuotedT etOps {
  def quotedT et: Opt on[T et]
  def quotedUser: Opt on[User]

  /**
   * Do   have ev dence that t  quoted user  s unprotected?
   */
  def quotedUser sPubl c: Boolean =
    // T  quoted user should  nclude t  `safety` struct, but  f  
    // doesn't for any reason t n t  quoted t et and quoted user
    // should not be  ncluded  n t  events. T   s a safety  asure to
    // avo d leak ng pr vate  nformat on.
    quotedUser.ex sts(_.safety.ex sts(!_. sProtected))

  /**
   * T  quoted t et, f ltered as   should appear through publ c AP s.
   */
  def publ cQuotedT et: Opt on[T et] =
     f (quotedUser sPubl c) quotedT et else None

  /**
   * T  quoted user, f ltered as   should appear through publ c AP s.
   */
  def publ cQuotedUser: Opt on[User] =
     f (quotedUser sPubl c) quotedUser else None
}
