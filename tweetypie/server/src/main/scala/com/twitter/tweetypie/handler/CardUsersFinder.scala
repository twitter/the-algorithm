package com.tw ter.t etyp e
package handler

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.CardReferenceUr Extractor
 mport com.tw ter.t etyp e.core.NonTombstone
 mport com.tw ter.t etyp e.core.Tombstone
 mport com.tw ter.t etyp e.repos ory.CardUsersRepos ory
 mport com.tw ter.t etyp e.repos ory.CardUsersRepos ory.Context
 mport com.tw ter.t etyp e.thr ftscala.CardReference

/**
 * F nds a set of User d that may be  nt oned w n reply ng to a t et that has a card.
 *
 * Repl es created w hout 'auto_populate_reply_ tadata'  nclude both 's e' and 'author' users to
 * have a more exhaust ve l st of  nt ons to match aga nst.  T   s needed because  OS and Andro d
 * have had d fferent  mple ntat ons cl ent-s de for years.
 */
object CardUsersF nder {

  case class Request(
    cardReference: Opt on[CardReference],
    urls: Seq[Str ng],
    perspect veUser d: User d) {
    val ur s: Seq[Str ng] = cardReference match {
      case So (CardReferenceUr Extractor(cardUr )) =>
        cardUr  match {
          case NonTombstone(ur ) => Seq(ur )
          case Tombstone => N l
        }
      case _ => urls
    }

    val context: CardUsersRepos ory.Context = Context(perspect veUser d)
  }

  type Type = Request => St ch[Set[User d]]

  /**
   * From a card-related argu nts  n [[Request]] select t  set of user  ds assoc ated w h t 
   * card.
   *
   * Note that t  uses t  sa  "wh ch card do   use?" log c from Card2Hydrator wh ch
   * pr or  zes CardReferenceUr  and t n falls back to t  last resolvable (non-None) url ent y.
   */
  def apply(cardUserRepo: CardUsersRepos ory.Type): Type =
    request =>
      St ch
        .traverse(request.ur s) { ur  => cardUserRepo(ur , request.context) }
        // select t  last, non-None Set of users  ds
        .map(r => r.flatten.reverse. adOpt on.getOrElse(Set.empty))
}
