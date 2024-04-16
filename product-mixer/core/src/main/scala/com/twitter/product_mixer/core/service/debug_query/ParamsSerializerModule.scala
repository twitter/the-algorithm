package com.tw ter.product_m xer.core.serv ce.debug_query

 mport com.fasterxml.jackson.core.JsonGenerator
 mport com.fasterxml.jackson.datab nd.Ser al zerProv der
 mport com.fasterxml.jackson.datab nd.ser.std.StdSer al zer
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.fasterxml.jackson.datab nd.module.S mpleModule
 mport com.tw ter.t  l nes.conf gap .Conf g

object ParamsSer al zerModule extends S mpleModule {
  addSer al zer(ParamsConf gSer al zer)
  addSer al zer(ParamsStdSer al zer)
}

object ParamsStdSer al zer extends StdSer al zer[Params](classOf[Params]) {
  overr de def ser al ze(
    value: Params,
    gen: JsonGenerator,
    prov der: Ser al zerProv der
  ): Un  = {
    gen.wr eStartObject()
    gen.wr eObjectF eld("appl ed_params", value.allAppl edValues)
    gen.wr eEndObject()
  }
}

object ParamsConf gSer al zer extends StdSer al zer[Conf g](classOf[Conf g]) {
  overr de def ser al ze(
    value: Conf g,
    gen: JsonGenerator,
    prov der: Ser al zerProv der
  ): Un  = {
    gen.wr eStr ng(value.s mpleNa )
  }
}
