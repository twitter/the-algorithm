package com.tw ter.t etyp e
package handler

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * Handler for t  `getT etCounts` endpo nt.
 */
object GetT etCountsHandler {
  type Type = FutureArrow[GetT etCountsRequest, Seq[GetT etCountsResult]]

  def apply(repo: T etCountsRepos ory.Type): Type = {

    def  dToResult( d: T et d, req: GetT etCountsRequest): St ch[GetT etCountsResult] =
      St ch
        .jo n(
          // .l ftToOpt on() converts any fa lures to None result
           f (req. ncludeRet etCount) repo(Ret etsKey( d)).l ftToOpt on() else St ch.None,
           f (req. ncludeReplyCount) repo(Repl esKey( d)).l ftToOpt on() else St ch.None,
           f (req. ncludeFavor eCount) repo(FavsKey( d)).l ftToOpt on() else St ch.None,
           f (req. ncludeQuoteCount) repo(QuotesKey( d)).l ftToOpt on() else St ch.None,
           f (req. ncludeBookmarkCount) repo(BookmarksKey( d)).l ftToOpt on() else St ch.None
        ).map {
          case (ret etCount, replyCount, favor eCount, quoteCount, bookmarkCount) =>
            GetT etCountsResult(
              t et d =  d,
              ret etCount = ret etCount,
              replyCount = replyCount,
              favor eCount = favor eCount,
              quoteCount = quoteCount,
              bookmarkCount = bookmarkCount
            )
        }

    FutureArrow[GetT etCountsRequest, Seq[GetT etCountsResult]] { request =>
      St ch.run(
        St ch.traverse(request.t et ds)( dToResult(_, request))
      )
    }
  }
}
