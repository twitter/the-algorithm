package com.tw ter.ho _m xer.ut l

 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures

object ReplyRet etUt l {

  def  sEl g bleReply(cand date: Cand dateW hFeatures[T etCand date]): Boolean = {
    cand date.features.getOrElse( nReplyToT et dFeature, None).nonEmpty &&
    !cand date.features.getOrElse( sRet etFeature, false)
  }

  /**
   * Bu lds a map from reply t et to all ancestors that are also hydrated cand dates.  f a reply
   * does not have any ancestors wh ch are also cand dates,   w ll not add to t  returned Map.
   * Make sure ancestors are bottom-up ordered such that:
   * (1)  f parent t et  s a cand date,   should be t  f rst  em at t  returned ancestors;
   * (2)  f root t et  s a cand date,   should be t  last  em at t  returned ancestors.
   * Ret ets of repl es or repl es to ret ets are not  ncluded.
   */
  def replyToAncestorT etCand datesMap(
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Map[Long, Seq[Cand dateW hFeatures[T etCand date]]] = {
    val replyToAncestorT et dsMap: Map[Long, Seq[Long]] =
      cand dates.flatMap { cand date =>
         f ( sEl g bleReply(cand date)) {
          val ancestor ds =
             f (cand date.features.getOrElse(AncestorsFeature, Seq.empty).nonEmpty) {
              cand date.features.getOrElse(AncestorsFeature, Seq.empty).map(_.t et d)
            } else {
              Seq(
                cand date.features.getOrElse( nReplyToT et dFeature, None),
                cand date.features.getOrElse(Conversat onModule dFeature, None)
              ).flatten.d st nct
            }
          So (cand date.cand date. d -> ancestor ds)
        } else {
          None
        }
      }.toMap

    val ancestorT et ds = replyToAncestorT et dsMap.values.flatten.toSet
    val ancestorT etsMapBy d: Map[Long, Cand dateW hFeatures[T etCand date]] = cand dates
      .f lter { maybeAncestor =>
        ancestorT et ds.conta ns(maybeAncestor.cand date. d)
      }.map { ancestor =>
        ancestor.cand date. d -> ancestor
      }.toMap

    replyToAncestorT et dsMap
      .mapValues { ancestorT et ds =>
        ancestorT et ds.flatMap { ancestorT et d =>
          ancestorT etsMapBy d.get(ancestorT et d)
        }
      }.f lter {
        case (reply, ancestors) =>
          ancestors.nonEmpty
      }
  }

  /**
   * T  map  s t  oppos e of [[replyToAncestorT etCand datesMap]].
   * Bu lds a map from ancestor t et to all descendant repl es that are also hydrated cand dates.
   * Currently,   only return two ancestors at most: one  s  nReplyToT et d and t  ot r
   *  s conversat on d.
   * Ret ets of repl es are not  ncluded.
   */
  def ancestorT et dToDescendantRepl esMap(
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Map[Long, Seq[Cand dateW hFeatures[T etCand date]]] = {
    val t etToCand dateMap = cand dates.map(c => c.cand date. d -> c).toMap
    replyToAncestorT etCand datesMap(cand dates).toSeq
      .flatMap {
        case (reply, ancestorT ets) =>
          ancestorT ets.map { ancestor =>
            (ancestor.cand date. d, reply)
          }
      }.groupBy { case (ancestor, reply) => ancestor }
      .mapValues { ancestorReplyPa rs =>
        ancestorReplyPa rs.map(_._2).d st nct
      }.mapValues(t et ds => t et ds.map(t d => t etToCand dateMap(t d)))
  }

  /**
   * Bu lds a map from reply t et to  nReplyToT et wh ch  s also a cand date.
   * Ret ets of repl es or repl es to ret ets are not  ncluded
   */
  def replyT et dTo nReplyToT etMap(
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Map[Long, Cand dateW hFeatures[T etCand date]] = {
    val el g bleReplyCand dates = cand dates.f lter { cand date =>
       sEl g bleReply(cand date) && cand date.features
        .getOrElse( nReplyToT et dFeature, None)
        .nonEmpty
    }

    val  nReplyToT et ds = el g bleReplyCand dates
      .flatMap(_.features.getOrElse( nReplyToT et dFeature, None))
      .toSet

    val  nReplyToT et dToT etMap: Map[Long, Cand dateW hFeatures[T etCand date]] = cand dates
      .f lter { maybe nReplyToT et =>
         nReplyToT et ds.conta ns(maybe nReplyToT et.cand date. d)
      }.map {  nReplyToT et =>
         nReplyToT et.cand date. d ->  nReplyToT et
      }.toMap

    el g bleReplyCand dates.flatMap { reply =>
      val  nReplyToT et d = reply.features.getOrElse( nReplyToT et dFeature, None)
       f ( nReplyToT et d.nonEmpty) {
         nReplyToT et dToT etMap.get( nReplyToT et d.get).map {  nReplyToT et =>
          reply.cand date. d ->  nReplyToT et
        }
      } else {
        None
      }
    }.toMap
  }
}
