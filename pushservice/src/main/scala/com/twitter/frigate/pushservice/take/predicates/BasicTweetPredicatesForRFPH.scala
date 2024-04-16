package com.tw ter.fr gate.pushserv ce.take.pred cates

 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.base.T etDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter. rm .pred cate.Na dPred cate

tra  Bas cT etPred catesForRFPH[C <: PushCand date w h T etCand date w h T etDeta ls]
    extends Bas cT etPred cates
    w h Bas cRFPHPred cates[C] {

  // spec f c pred cates per cand date type before bas c t et pred cates
  def preCand dateSpec f cPred cates: L st[Na dPred cate[C]] = L st.empty

  // spec f c pred cates per cand date type after bas c t et pred cates
  def postCand dateSpec f cPred cates: L st[Na dPred cate[C]] = L st.empty

  overr de lazy val pred cates: L st[Na dPred cate[C]] =
    preCand dateSpec f cPred cates ++ bas cT etPred cates ++ postCand dateSpec f cPred cates
}

/**
 * T  tra   s a new vers on of Bas cT etPred catesForRFPH
 * D fference from old vers on  s that bas cT etPred cates are d fferent
 * bas cT etPred cates  re don't  nclude Soc al Graph Serv ce related pred cates
 */
tra  Bas cT etPred catesForRFPHW houtSGSPred cates[
  C <: PushCand date w h T etCand date w h T etDeta ls]
    extends Bas cT etPred catesW houtSGSPred cates
    w h Bas cRFPHPred cates[C] {

  // spec f c pred cates per cand date type before bas c t et pred cates
  def preCand dateSpec f cPred cates: L st[Na dPred cate[C]] = L st.empty

  // spec f c pred cates per cand date type after bas c t et pred cates
  def postCand dateSpec f cPred cates: L st[Na dPred cate[C]] = L st.empty

  overr de lazy val pred cates: L st[Na dPred cate[C]] =
    preCand dateSpec f cPred cates ++ bas cT etPred cates ++ postCand dateSpec f cPred cates

}
