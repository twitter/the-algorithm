package com.tw ter.fr gate.pushserv ce.take.pred cates

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter. rm .pred cate.Na dPred cate

tra  Bas cSendHandlerPred cates[C <: PushCand date] {

  // spec f c pred cates per cand date type before bas c SendHandler pred cates
  val preCand dateSpec f cPred cates: L st[Na dPred cate[C]] = L st.empty

  // spec f c pred cates per cand date type after bas c SendHandler pred cates, could be empty
  val postCand dateSpec f cPred cates: L st[Na dPred cate[C]] = L st.empty
}
