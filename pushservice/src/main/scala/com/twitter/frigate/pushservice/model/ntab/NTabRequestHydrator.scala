package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.not f cat onserv ce.thr ftscala.CreateGener cNot f cat onRequest
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayText
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.not f cat onserv ce.thr ftscala.Gener cType
 mport com.tw ter.not f cat onserv ce.thr ftscala. nl neCard
 mport com.tw ter.not f cat onserv ce.thr ftscala.StoryContext
 mport com.tw ter.not f cat onserv ce.thr ftscala.TapThroughAct on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

tra  NTabRequestHydrator extends NTabRequest w h Cand dateNTabCopy {
  self: PushCand date =>

  // Represents t  sender of t  recom ndat on
  def sender dFut: Future[Long]

  // Cons sts of a sequence represent ng t  soc al context user  ds.
  def facep leUsersFut: Future[Seq[Long]]

  // Story Context  s requ red for T et Recom ndat ons
  // Conta ns t  T et  D of t  recom nded T et
  def storyContext: Opt on[StoryContext]

  //  nl ne card used to render a gener c not f cat on.
  def  nl neCard: Opt on[ nl neCard]

  // Represents w re t  recom ndat on should land w n cl cked
  def tapThroughFut: Future[Str ng]

  // Hydrat on for f elds that are used w h n t  NTab copy
  def d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]]

  // Represents t  soc al proof text that  s needed for spec f c NTab cop es
  def soc alProofD splayText: Opt on[D splayText]

  // Mag cRecs NTab entr es always use RefreshableType as t  Gener c Type
  f nal val gener cType: Gener cType = Gener cType.RefreshableNot f cat on

  def refreshableType: Opt on[Str ng] = ntabCopy.refreshableType

  lazy val ntabRequest: Future[Opt on[CreateGener cNot f cat onRequest]] = {
    Future.jo n(sender dFut, d splayTextEnt  esFut, facep leUsersFut, tapThroughFut).map {
      case (sender d, d splayTextEnt  es, facep leUsers, tapThrough) =>
        So (
          CreateGener cNot f cat onRequest(
            user d = target.target d,
            sender d = sender d,
            gener cType = Gener cType.RefreshableNot f cat on,
            d splayText = D splayText(values = d splayTextEnt  es),
            facep leUsers = facep leUsers,
            t  stampM ll s = T  .now. nM ll s,
            tapThroughAct on = So (TapThroughAct on(So (tapThrough))),
             mpress on d = So ( mpress on d),
            soc alProofText = soc alProofD splayText,
            context = storyContext,
             nl neCard =  nl neCard,
            refreshableType = refreshableType
          ))
    }
  }
}
