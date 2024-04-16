Overv ew
========

V s b l y F lter ng  s a central zed rule eng ne that  nstructs cl ents how to alter t  d splay of certa n Tw ter content on read t  . T  V s b l y F lter ng l brary  s respons ble for f lter ng Tw ter content to support legal compl ance,  mprove product qual y,  ncrease user trust, protect revenue through t  use of hard-f lter ng, v s ble product treat nts, and coarse-gra ned downrank ng.

Not ce
======

V s b l y F lter ng l brary  s currently be ng rev e d and rebu lt, and part of t  code has been removed and  s not ready to be shared yet. T  rema n ng part of t  code needs furt r rev ew and w ll be shared once  ’s ready. Also code com nts have been san  zed.

SafetyLevel
===========

Represents t  product context  n wh ch t  V e r  s request ng to v ew t  Content (e.g. T  l ne, Prof le).

Features
========

 nclude safety labels and ot r  tadata of a T et, flags set on a User ( nclud ng t  V e r), relat onsh ps bet en Users (e.g. block, follow), User sett ngs, relat onsh ps bet en Users and Content (e.g. reported for spam).

Act on
======

T  way t  V s b l y Fra work  nstructs t  cl ent to respond to t  V e r’s request for Content, and can  nclude hard f lter ng (e.g. Drop), soft f lter ng (e.g. Labels and  nterst  als), rank ng clues, etc.

Cond  on
=========

Returns a boolean w n g ven map of Features. Cond  ons can be comb ned to determ ne  f a Rule should return an Act on or t  default (Allow).

Pol cy
======

Rules are expressed as a sequence  n pr or y order to create a V s b l y Pol cy. T  l brary has one pol cy
per SafetyLevel.

RuleEng ne
===========

Evaluates t  Act on for a Request.

SafetyLabel
===========

A pr mary label ng  chan sm for Safety. A labeled ent y assoc ates w h t et, user, D rect  ssage,  d a, space etc. Safety labels po r d fferent ways of re d at ons (e.g. apply ng a SafetyLabel that results  n t et  nterst  al or not ce).

SafetyLabelType
===============

Descr bes a part cular pol cy v olat on for a g ven noun  nstance, and usually leads to reduced v s b l y of t 
labeled ent y  n product surfaces. T re are many deprecated, and exper  ntal safety label types. Labels w h t se safety label types have no effect on VF. Add  onally, so  safety label types are not used, and not des gned for VF.
