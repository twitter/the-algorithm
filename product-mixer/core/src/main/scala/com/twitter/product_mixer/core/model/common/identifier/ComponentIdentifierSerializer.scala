package com.tw ter.product_m xer.core.model.common. dent f er

 mport com.fasterxml.jackson.core.JsonGenerator
 mport com.fasterxml.jackson.datab nd.JsonSer al zer
 mport com.fasterxml.jackson.datab nd.Ser al zerProv der

pr vate[ dent f er] class Component dent f erSer al zer()
    extends JsonSer al zer[Component dent f er] {

  pr vate case class Ser al zableComponent dent f er(
     dent f er: Str ng,
    s ceF le: Str ng)

  overr de def ser al ze(
    component dent f er: Component dent f er,
    gen: JsonGenerator,
    ser al zers: Ser al zerProv der
  ): Un  = ser al zers.defaultSer al zeValue(
    Ser al zableComponent dent f er(component dent f er.toStr ng, component dent f er.f le.value),
    gen)
}
