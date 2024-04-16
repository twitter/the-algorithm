package com.tw ter.t etyp e.handler

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.T etCreateFa lure
 mport com.tw ter.t etyp e.repos ory.Conversat onControlRepos ory
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.thr ftscala.Exclus veT etControl
 mport com.tw ter.t etyp e.thr ftscala.Exclus veT etControlOpt ons
 mport com.tw ter.t etyp e.thr ftscala.QuotedT et
 mport com.tw ter.t etyp e.thr ftscala.TrustedFr endsControl
 mport com.tw ter.t etyp e.thr ftscala.TrustedFr endsControlOpt ons
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState
 mport com.tw ter.t etyp e.FutureEffect
 mport com.tw ter.t etyp e.Gate
 mport com.tw ter.t etyp e.T et d
 mport com.tw ter.t etyp e.User d
 mport com.tw ter.t etyp e.thr ftscala.Ed Control
 mport com.tw ter.t etyp e.thr ftscala.Ed Opt ons
 mport com.tw ter.v s b l y.wr er. nterfaces.t ets.T etWr eEnforce ntL brary
 mport com.tw ter.v s b l y.wr er. nterfaces.t ets.T etWr eEnforce ntRequest
 mport com.tw ter.v s b l y.wr er.models.ActorContext
 mport com.tw ter.v s b l y.wr er.Allow
 mport com.tw ter.v s b l y.wr er.Deny
 mport com.tw ter.v s b l y.wr er.DenyExclus veT etReply
 mport com.tw ter.v s b l y.wr er.DenyStaleT etQuoteT et
 mport com.tw ter.v s b l y.wr er.DenyStaleT etReply
 mport com.tw ter.v s b l y.wr er.DenySuperFollowsCreate
 mport com.tw ter.v s b l y.wr er.DenyTrustedFr endsCreate
 mport com.tw ter.v s b l y.wr er.DenyTrustedFr endsQuoteT et
 mport com.tw ter.v s b l y.wr er.DenyTrustedFr endsReply

object T etWr eVal dator {
  case class Request(
    conversat on d: Opt on[T et d],
    user d: User d,
    exclus veT etControlOpt ons: Opt on[Exclus veT etControlOpt ons],
    replyToExclus veT etControl: Opt on[Exclus veT etControl],
    trustedFr endsControlOpt ons: Opt on[TrustedFr endsControlOpt ons],
     nReplyToTrustedFr endsControl: Opt on[TrustedFr endsControl],
    quotedT etOpt: Opt on[QuotedT et],
     nReplyToT et d: Opt on[T et d],
     nReplyToEd Control: Opt on[Ed Control],
    ed Opt ons: Opt on[Ed Opt ons])

  type Type = FutureEffect[Request]

  def apply(
    convoCtlRepo: Conversat onControlRepos ory.Type,
    t etWr eEnforce ntL brary: T etWr eEnforce ntL brary,
    enableExclus veT etControlVal dat on: Gate[Un ],
    enableTrustedFr endsControlVal dat on: Gate[Un ],
    enableStaleT etVal dat on: Gate[Un ]
  ): FutureEffect[Request] =
    FutureEffect[Request] { request =>
      //   are creat ng up an empty T etQuery.Opt ons  re so   can use t  default
      // Cac Control value and avo d hard cod ng    re.
      val queryOpt ons = T etQuery.Opt ons(T etQuery. nclude())
      St ch.run {
        for {
          convoCtl <- request.conversat on d match {
            case So (convo d) =>
              convoCtlRepo(
                convo d,
                queryOpt ons.cac Control
              )
            case None =>
              St ch.value(None)
          }

          result <- t etWr eEnforce ntL brary(
            T etWr eEnforce ntRequest(
              rootConversat onControl = convoCtl,
              convo d = request.conversat on d,
              exclus veT etControlOpt ons = request.exclus veT etControlOpt ons,
              replyToExclus veT etControl = request.replyToExclus veT etControl,
              trustedFr endsControlOpt ons = request.trustedFr endsControlOpt ons,
               nReplyToTrustedFr endsControl = request. nReplyToTrustedFr endsControl,
              quotedT etOpt = request.quotedT etOpt,
              actorContext = ActorContext(request.user d),
               nReplyToT et d = request. nReplyToT et d,
               nReplyToEd Control = request. nReplyToEd Control,
              ed Opt ons = request.ed Opt ons
            ),
            enableExclus veT etControlVal dat on = enableExclus veT etControlVal dat on,
            enableTrustedFr endsControlVal dat on = enableTrustedFr endsControlVal dat on,
            enableStaleT etVal dat on = enableStaleT etVal dat on
          )
          _ <- result match {
            case Allow =>
              St ch.Done
            case Deny =>
              St ch.except on(T etCreateFa lure.State(T etCreateState.ReplyT etNotAllo d))
            case DenyExclus veT etReply =>
              St ch.except on(
                T etCreateFa lure.State(T etCreateState.Exclus veT etEngage ntNotAllo d))
            case DenySuperFollowsCreate =>
              St ch.except on(
                T etCreateFa lure.State(T etCreateState.SuperFollowsCreateNotAuthor zed))
            case DenyTrustedFr endsReply =>
              St ch.except on(
                T etCreateFa lure.State(T etCreateState.TrustedFr endsEngage ntNotAllo d))
            case DenyTrustedFr endsCreate =>
              St ch.except on(
                T etCreateFa lure.State(T etCreateState.TrustedFr endsCreateNotAllo d))
            case DenyTrustedFr endsQuoteT et =>
              St ch.except on(
                T etCreateFa lure.State(T etCreateState.TrustedFr endsQuoteT etNotAllo d))
            case DenyStaleT etReply =>
              St ch.except on(
                T etCreateFa lure.State(T etCreateState.StaleT etEngage ntNotAllo d))
            case DenyStaleT etQuoteT et =>
              St ch.except on(
                T etCreateFa lure.State(T etCreateState.StaleT etQuoteT etNotAllo d))
          }
        } y eld ()
      }
    }
}
