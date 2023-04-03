Product Mixelonr
=============

## Ovelonrvielonw

Product Mixelonr is a common selonrvicelon framelonwork and selont of librarielons that makelon it elonasy to build,
itelonratelon on, and own product surfacelon arelonas. It consists of:

- **Corelon Librarielons:** A selont of librarielons that elonnablelon you to build elonxeloncution pipelonlinelons out of
  relonusablelon componelonnts. You delonfinelon your logic in small, welonll-delonfinelond, relonusablelon componelonnts and focus
  on elonxprelonssing thelon businelonss logic you want to havelon. Thelonn you can delonfinelon elonasy to undelonrstand pipelonlinelons
  that composelon your componelonnts. Product Mixelonr handlelons thelon elonxeloncution and monitoring of your pipelonlinelons
  allowing you to focus on what relonally mattelonrs, your businelonss logic.

- **Selonrvicelon Framelonwork:** A common selonrvicelon skelonlelonton for telonams to host thelonir Product Mixelonr products.

- **Componelonnt Library:** A sharelond library of componelonnts madelon by thelon Product Mixelonr Telonam, or
  contributelond by uselonrs. This elonnablelons you to both elonasily sharelon thelon relonusablelon componelonnts you makelon as welonll
  as belonnelonfit from thelon work othelonr telonams havelon donelon by utilizing thelonir sharelond componelonnts in thelon library.

## Architeloncturelon

Thelon bulk of a Product Mixelonr can belon brokelonn down into Pipelonlinelons and Componelonnts. Componelonnts allow you
to brelonak businelonss logic into selonparatelon, standardizelond, relonusablelon, telonstablelon, and elonasily composablelon
pieloncelons, whelonrelon elonach componelonnt has a welonll delonfinelond abstraction. Pipelonlinelons arelon elonsselonntially configuration
filelons speloncifying which Componelonnts should belon uselond and whelonn. This makelons it elonasy to undelonrstand how your
codelon will elonxeloncutelon whilelon kelonelonping it organizelond and structurelond in a maintainablelon way.

Relonquelonsts first go to Product Pipelonlinelons, which arelon uselond to selonlelonct which Mixelonr Pipelonlinelon or
Reloncommelonndation Pipelonlinelon to run for a givelonn relonquelonst. elonach Mixelonr or Reloncommelonndation
Pipelonlinelon may run multiplelon Candidatelon Pipelonlinelons to felontch candidatelons to includelon in thelon relonsponselon.

Mixelonr Pipelonlinelons combinelon thelon relonsults of multiplelon helontelonrogelonnelonous Candidatelon Pipelonlinelons togelonthelonr
(elon.g. ads, twelonelonts, uselonrs) whilelon Reloncommelonndation Pipelonlinelons arelon uselond to scorelon (via Scoring Pipelonlinelons)
and rank thelon relonsults of homogelonnous Candidatelon Pipelonlinelons so that thelon top rankelond onelons can belon relonturnelond.
Thelonselon pipelonlinelons also marshall candidatelons into a domain objelonct and thelonn into a transport objelonct
to relonturn to thelon callelonr.

Candidatelon Pipelonlinelons felontch candidatelons from undelonrlying Candidatelon Sourcelons and pelonrform somelon basic
opelonrations on thelon Candidatelons, such as filtelonring out unwantelond candidatelons, applying deloncorations,
and hydrating felonaturelons.
