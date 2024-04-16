package com.tw ter.t etyp e
package core

 mport com.tw ter.t etyp e.thr ftscala.CardReference
 mport java.net.UR 

sealed tra  CardUr 
object Tombstone extends CardUr 
case class NonTombstone(ur : Str ng) extends CardUr 

object CardReferenceUr Extractor {

  pr vate def parseAsUr (cardRef: CardReference) = Try(new UR (cardRef.cardUr )).toOpt on
  pr vate def  sTombstone(ur : UR ) = ur .getSc   == "tombstone"

  /**
   * Parses a CardReference to return Opt on[CardUr ] to d fferent ate among:
   * - So (NonTombstone): hydrate card2 w h prov ded ur 
   * - So (Tombstone): don't hydrate card2
   * - None: fallback and attempt to use url ent  es ur s
   */
  def unapply(cardRef: CardReference): Opt on[CardUr ] =
    parseAsUr (cardRef) match {
      case So (ur )  f ! sTombstone(ur ) => So (NonTombstone(ur .toStr ng))
      case So (ur ) => So (Tombstone)

      //  f a cardReference  s set, but does not parse as a UR ,  's l kely a https? URL w h
      //  ncorrectly encoded query params. S nce t se occur frequently  n t  w ld,  'll
      // attempt a card2 hydrat on w h  
      case None => So (NonTombstone(cardRef.cardUr ))
    }
}
