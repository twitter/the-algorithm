package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

pr vate[real_t  ] sealed tra  Event[T] { def event: T }

pr vate[real_t  ] case class Ho Event[T](overr de val event: T) extends Event[T]

pr vate[real_t  ] case class Prof leEvent[T](overr de val event: T) extends Event[T]

pr vate[real_t  ] case class SearchEvent[T](overr de val event: T) extends Event[T]

pr vate[real_t  ] case class UuaEvent[T](overr de val event: T) extends Event[T]
