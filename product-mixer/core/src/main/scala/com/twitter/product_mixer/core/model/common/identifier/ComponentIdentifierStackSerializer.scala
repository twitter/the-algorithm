package com.tw ter.product_m xer.core.model.common. dent f er

 mport com.fasterxml.jackson.core.JsonGenerator
 mport com.fasterxml.jackson.datab nd.JsonSer al zer
 mport com.fasterxml.jackson.datab nd.Ser al zerProv der

pr vate[ dent f er] class Component dent f erStackSer al zer()
    extends JsonSer al zer[Component dent f erStack] {
  overr de def ser al ze(
    component dent f erStack: Component dent f erStack,
    gen: JsonGenerator,
    ser al zers: Ser al zerProv der
  ): Un  = ser al zers.defaultSer al zeValue(component dent f erStack.component dent f ers, gen)
}
