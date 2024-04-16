package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er

/**
 * conta ns  nformat on  f a cand date  s from a cand date s ce generated us ng t  follow ng s gnals.
 */
case class AddressBook tadata(
   nForwardPhoneBook: Boolean,
   nReversePhoneBook: Boolean,
   nForwardEma lBook: Boolean,
   nReverseEma lBook: Boolean)

object AddressBook tadata {

  val ForwardPhoneBookCand dateS ce = Cand dateS ce dent f er(
    Algor hm.ForwardPhoneBook.toStr ng)

  val ReversePhoneBookCand dateS ce = Cand dateS ce dent f er(
    Algor hm.ReversePhoneBook.toStr ng)

  val ForwardEma lBookCand dateS ce = Cand dateS ce dent f er(
    Algor hm.ForwardEma lBook.toStr ng)

  val ReverseEma lBookCand dateS ce = Cand dateS ce dent f er(
    Algor hm.ReverseEma lBook b s.toStr ng)

}
