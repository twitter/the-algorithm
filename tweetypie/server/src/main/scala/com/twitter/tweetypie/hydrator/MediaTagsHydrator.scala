package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

object  d aTagsHydrator {
  type Type = ValueHydrator[Opt on[T et d aTags], T etCtx]

  /**
   * T et d aTags conta ns a map of  d a d to Seq[ d aTag].
   * T  outer traverse maps over each  d a d, wh le t   nner
   * traverse maps over each  d aTag.
   *
   * A  d aTag has f  f elds:
   *
   *   1:  d aTagType tag_type
   *   2: opt onal  64 user_ d
   *   3: opt onal str ng screen_na 
   *   4: opt onal str ng na 
   *
   * For each  d aTag,  f t  tag type  s  d aTagType.User and t  user  d  s def ned
   * (see  d aTagToKey)   look up t  tagged user, us ng t  tagg ng user (t  t et
   * author) as t  v e r  d (t   ans that v s b l y rules bet en t  tagged user
   * and tagg ng user are appl ed).
   *
   *  f   get a taggable user back,   f ll  n t  screen na  and na  f elds.  f not,
   *   drop t  tag.
   */
  def apply(repo: UserV ewRepos ory.Type): Type =
    ValueHydrator[T et d aTags, T etCtx] { (tags, ctx) =>
      val  d aTagsBy d a d: Seq[( d a d, Seq[ d aTag])] = tags.tagMap.toSeq

      St ch
        .traverse( d aTagsBy d a d) {
          case ( d a d,  d aTags) =>
            St ch.traverse( d aTags)(tag => hydrate d aTag(repo, tag, ctx.user d)).map {
              ValueState.sequence(_).map(tags => ( d a d, tags.flatten))
            }
        }
        .map {
          // Reconstruct T et d aTags(tagMap: Map[ d a d, Seq d aTag])
          ValueState.sequence(_).map(s => T et d aTags(s.toMap))
        }
    }.only f { (_, ctx) =>
      !ctx. sRet et && ctx.t etF eldRequested(T et. d aTagsF eld)
    }.l ftOpt on

  /**
   * A funct on to hydrate a s ngle ` d aTag`. T  return type  s `Opt on[ d aTag]`
   * because   may return `None` to f lter out a ` d aTag`  f t  tagged user doesn't
   * ex st or  sn't taggable.
   */
  pr vate[t ] def hydrate d aTag(
    repo: UserV ewRepos ory.Type,
     d aTag:  d aTag,
    author d: User d
  ): St ch[ValueState[Opt on[ d aTag]]] =
     d aTagToKey( d aTag) match {
      case None => St ch.value(ValueState.unmod f ed(So ( d aTag)))
      case So (key) =>
        repo(toRepoQuery(key, author d))
          .map {
            case user  f user. d aV ew.ex sts(_.can d aTag) =>
              ValueState.mod f ed(
                So (
                   d aTag.copy(
                    user d = So (user. d),
                    screenNa  = user.prof le.map(_.screenNa ),
                    na  = user.prof le.map(_.na )
                  )
                )
              )

            //  f `can d aTag`  s false, drop t  tag
            case _ => ValueState.mod f ed(None)
          }
          .handle {
            //  f user  s not found, drop t  tag
            case NotFound => ValueState.mod f ed(None)
          }
    }

  pr vate[t ] val queryF elds: Set[UserF eld] = Set(UserF eld.Prof le, UserF eld. d aV ew)

  def toRepoQuery(userKey: UserKey, forUser d: User d): UserV ewRepos ory.Query =
    UserV ewRepos ory.Query(
      userKey = userKey,
      // v ew  s based on tagg ng user, not t et v e r
      forUser d = So (forUser d),
      v s b l y = UserV s b l y. d aTaggable,
      queryF elds = queryF elds
    )

  pr vate[t ] def  d aTagToKey( d aTag:  d aTag): Opt on[UserKey] =
     d aTag match {
      case  d aTag( d aTagType.User, So (taggedUser d), _, _) => So (UserKey(taggedUser d))
      case _ => None
    }
}
