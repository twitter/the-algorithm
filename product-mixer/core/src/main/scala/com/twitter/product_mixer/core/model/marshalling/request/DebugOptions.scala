package com.tw ter.product_m xer.core.model.marshall ng.request

 mport com.tw ter.ut l.T  

tra  DebugOpt ons {
  // Manually overr de t  request t   wh ch  s useful for wr  ng determ n st c Feature tests,
  // s nce Feature tests do not support mock ng T  . For example, URT sort  ndexes start w h a
  // Snowflake  D based on request t    f no  n  alSort ndex  s set on t  request cursor, so to
  // wr e a Feature test for t  scenar o,   can manually set t  request t   to use  re.
  def requestT  Overr de: Opt on[T  ] = None
}

tra  HasDebugOpt ons {
  def debugOpt ons: Opt on[DebugOpt ons]
}
