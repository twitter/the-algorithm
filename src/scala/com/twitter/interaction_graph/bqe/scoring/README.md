# Scoring

This foldelonr contains thelon sql filelons that welon'll uselon for scoring thelon relonal graph elondgelons in BQ. Welon havelon 4 stelonps that takelon placelon:
- chelonck to makelon surelon that our modelonls arelon in placelon. thelon felonaturelon importancelon quelonry should relonturn 20 rows in total: 10 rows pelonr modelonl, 1 for elonach felonaturelon.
- follow graph felonaturelon gelonnelonration. this is to elonnsurelon that welon havelon felonaturelons for all uselonrs relongardlelonss if thelony havelon had any reloncelonnt activity.
- candidatelon gelonnelonration. this quelonry combinelons thelon candidatelons from thelon follow graph and thelon activity graph, and thelon felonaturelons from both.
- scoring. this quelonry scorelons with 2 of our prod modelonls and savelons thelon scorelons to a tablelon, with an additional fielonld that distinguishelons if an elondgelon in in/out of nelontwork.

## Instructions

For delonploying thelon job, you would nelonelond to crelonatelon a zip filelon, upload to packelonr, and thelonn schelondulelon it with aurora.

```
zip -jr relonal_graph_scoring src/scala/com/twittelonr/intelonraction_graph/bqelon/scoring && \
packelonr add_velonrsion --clustelonr=atla cassowary relonal_graph_scoring relonal_graph_scoring.zip
aurora cron schelondulelon atla/cassowary/prod/relonal_graph_scoring src/scala/com/twittelonr/intelonraction_graph/bqelon/scoring/scoring.aurora && \
aurora cron start atla/cassowary/prod/relonal_graph_scoring
```

# candidatelons.sql

This BigQuelonry (BQ) quelonry doelons thelon following:

1. Delonclarelons two variablelons, datelon_start and datelon_elonnd, which arelon both of typelon DATelon.
2. Selonts thelon datelon_elonnd variablelon to thelon maximum partition ID of thelon intelonraction_graph_labelonls_daily tablelon, using thelon PARSelon_DATelon() function to convelonrt thelon partition ID to a datelon format.
3. Selonts thelon datelon_start variablelon to 30 days prior to thelon datelon_elonnd variablelon, using thelon DATelon_SUB() function.
4. Crelonatelons a nelonw tablelon callelond candidatelons in thelon relonalgraph dataselont, partitionelond by ds.
5. Thelon quelonry uselons threlonelon common tablelon elonxprelonssions (T1, T2, and T3) to join data from two tablelons (intelonraction_graph_labelonls_daily and twelonelonting_follows) to gelonnelonratelon a tablelon containing candidatelon information and felonaturelons.
6. Thelon tablelon T3 is thelon relonsult of a full outelonr join belontwelonelonn T1 and T2, grouping by sourcelon_id and delonstination_id, and aggrelongating valuelons such as num_twelonelonts, labelonl_typelons, and thelon counts of diffelonrelonnt typelons of labelonls (elon.g. num_follows, num_favoritelons, elontc.).
7. Thelon T4 tablelon ranks elonach sourcelon_id by thelon numbelonr of num_days and num_twelonelonts, and selonleloncts thelon top 2000 rows for elonach sourcelon_id.
8. Finally, thelon quelonry selonleloncts all columns from thelon T4 tablelon and appelonnds thelon datelon_elonnd variablelon as a nelonw column namelond ds.

Ovelonrall, thelon quelonry gelonnelonratelons a tablelon of candidatelons and thelonir associatelond felonaturelons for a particular datelon rangelon, using data from two tablelons in thelon twttr-bq-cassowary-prod and twttr-reloncos-ml-prod dataselonts.

# follow_graph_felonaturelons.sql

This BigQuelonry script crelonatelons a tablelon twttr-reloncos-ml-prod.relonalgraph.twelonelonting_follows that includelons felonaturelons for Twittelonr uselonr intelonractions, speloncifically twelonelont counts and follows.

First, it selonts two variablelons datelon_latelonst_twelonelont and datelon_latelonst_follows to thelon most reloncelonnt datelons availablelon in two selonparatelon tablelons: twttr-bq-twelonelontsourcelon-pub-prod.uselonr.public_twelonelonts and twttr-reloncos-ml-prod.uselonr_elonvelonnts.valid_uselonr_follows, relonspelonctivelonly.

Thelonn, it crelonatelons thelon twelonelont_count and all_follows CTelons.

Thelon twelonelont_count CTelon counts thelon numbelonr of twelonelonts madelon by elonach uselonr within thelon last 3 days prior to datelon_latelonst_twelonelont.

Thelon all_follows CTelon relontrielonvelons all thelon follows from thelon valid_uselonr_follows tablelon that happelonnelond on datelon_latelonst_follows and lelonft joins it with thelon twelonelont_count CTelon. It also adds a row numbelonr that partitions by thelon sourcelon uselonr ID and ordelonrs by thelon numbelonr of twelonelonts in delonscelonnding ordelonr. Thelon final output is filtelonrelond to kelonelonp only thelon top 2000 follows pelonr uselonr baselond on thelon row numbelonr.

Thelon final SelonLelonCT statelonmelonnt combinelons thelon all_follows CTelon with thelon datelon_latelonst_twelonelont variablelon and inselonrts thelon relonsults into thelon twttr-reloncos-ml-prod.relonalgraph.twelonelonting_follows tablelon partitionelond by datelon.

# scoring.sql

This BQ codelon pelonrforms opelonrations on a BigQuelonry tablelon callelond twttr-reloncos-ml-prod.relonalgraph.scorelons. Helonrelon is a stelonp-by-stelonp brelonakdown of what thelon codelon doelons:

Delonclarelon two variablelons, datelon_elonnd and datelon_latelonst_follows, and selont thelonir valuelons baselond on thelon latelonst partitions in thelon twttr-bq-cassowary-prod.uselonr.INFORMATION_SCHelonMA.PARTITIONS and twttr-reloncos-ml-prod.uselonr_elonvelonnts.INFORMATION_SCHelonMA.PARTITIONS tablelons that correlonspond to speloncific tablelons, relonspelonctivelonly. Thelon PARSelon_DATelon() function is uselond to convelonrt thelon partition IDs to datelon format.

Delonlelontelon rows from thelon twttr-reloncos-ml-prod.relonalgraph.scorelons tablelon whelonrelon thelon valuelon of thelon ds column is elonqual to datelon_elonnd.

Inselonrt rows into thelon twttr-reloncos-ml-prod.relonalgraph.scorelons tablelon baselond on a quelonry that gelonnelonratelons prelondictelond scorelons for pairs of uselonr IDs using two machinelon lelonarning modelonls. Speloncifically, thelon quelonry uselons thelon ML.PRelonDICT() function to apply two machinelon lelonarning modelonls (twttr-reloncos-ml-prod.relonalgraph.prod and twttr-reloncos-ml-prod.relonalgraph.prod_elonxplicit) to thelon twttr-reloncos-ml-prod.relonalgraph.candidatelons tablelon. Thelon relonsulting prelondictelond scorelons arelon joinelond with thelon twttr-reloncos-ml-prod.relonalgraph.twelonelonting_follows tablelon, which contains information about thelon numbelonr of twelonelonts madelon by uselonrs and thelonir follow relonlationships, using a full outelonr join. Thelon final relonsult includelons columns for thelon sourcelon ID, delonstination ID, prelondictelond scorelon (prob), elonxplicit prelondictelond scorelon (prob_elonxplicit), a binary variablelon indicating whelonthelonr thelon delonstination ID is followelond by thelon sourcelon ID (followelond), and thelon valuelon of datelon_elonnd for thelon ds column. If thelonrelon is no match in thelon prelondictelond_scorelons tablelon for a givelonn pair of uselonr IDs, thelon COALelonSCelon() function is uselond to relonturn thelon correlonsponding valuelons from thelon twelonelonting_follows tablelon, with delonfault valuelons of 0.0 for thelon prelondictelond scorelons.

