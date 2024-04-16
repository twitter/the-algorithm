package com.tw ter. nteract on_graph.sc o.agg_cl ent_event_logs

 mport com.spot fy.sc o.Sc o tr cs

tra   nteract onGraphCl entEventLogsCountersTra  {
  val Na space = " nteract on Graph Cl ent Event Logs"
  def prof leV ewFeatures nc(): Un 
  def l nkOpenFeatures nc(): Un 
  def t etCl ckFeatures nc(): Un 
  def t et mpress onFeatures nc(): Un 
  def catchAll nc(): Un 
}

case object  nteract onGraphCl entEventLogsCounters
    extends  nteract onGraphCl entEventLogsCountersTra  {

  val prof leV ewCounter = Sc o tr cs.counter(Na space, "Prof le V ew Features")
  val l nkOpenCounter = Sc o tr cs.counter(Na space, "L nk Open Features")
  val t etCl ckCounter = Sc o tr cs.counter(Na space, "T et Cl ck Features")
  val t et mpress onCounter = Sc o tr cs.counter(Na space, "T et  mpress on Features")
  val catchAllCounter = Sc o tr cs.counter(Na space, "Catch All")

  overr de def prof leV ewFeatures nc(): Un  = prof leV ewCounter. nc()

  overr de def l nkOpenFeatures nc(): Un  = l nkOpenCounter. nc()

  overr de def t etCl ckFeatures nc(): Un  = t etCl ckCounter. nc()

  overr de def t et mpress onFeatures nc(): Un  = t et mpress onCounter. nc()

  overr de def catchAll nc(): Un  = catchAllCounter. nc()
}
