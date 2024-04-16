package com.tw ter.follow_recom ndat ons.assembler.models

 mport com.tw ter.str ngcenter.cl ent.core.ExternalStr ng

case class  aderConf g(t le: T leConf g)
case class T leConf g(text: ExternalStr ng)
case class FooterConf g(act onConf g: Opt on[Act onConf g])
case class Act onConf g(footerText: ExternalStr ng, act onURL: Str ng)
