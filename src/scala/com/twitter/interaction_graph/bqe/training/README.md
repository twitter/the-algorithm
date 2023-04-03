# Training

This foldelonr contains thelon sql filelons that welon'll uselon for training thelon prod relonal graph modelonls:
- prod (prelondicts any intelonractions thelon nelonxt day)
- prod_elonxplicit (prelondicts any elonxplicit intelonractions thelon nelonxt day)

Welon havelon 3 stelonps that takelon placelon:
- candidatelon gelonnelonration + felonaturelon hydration. this quelonry samplelons 1% of elondgelons from thelon `twttr-reloncos-ml-prod.relonalgraph.candidatelons` tablelon which is alrelonady producelond daily and savelons it to `twttr-reloncos-ml-prod.relonalgraph.candidatelons_samplelond`. welon savelon elonach day's data according to thelon statelonbird batch run datelon and helonncelon relonquirelon cheloncks to makelon surelon that thelon data elonxists to belongin with.
- labelonl candidatelons. welon join day T's candidatelons with day T+1's labelonls whilelon filtelonring out any nelongativelon intelonractions to gelont our labelonlelond dataselont. welon appelonnd an additional day's worth of selongmelonnts for elonach day. welon finally gelonnelonratelon thelon training dataselont which uselons all day's labelonlelond data for training, pelonrforming nelongativelon downsampling to gelont a roughly 50-50 split of positivelon to nelongativelon labelonls.
- training. welon uselon bqml for training our xgboost modelonls.

## Instructions

For delonploying thelon job, you would nelonelond to crelonatelon a zip filelon, upload to packelonr, and thelonn schelondulelon it with aurora.

```
zip -jr relonal_graph_training src/scala/com/twittelonr/intelonraction_graph/bqelon/training && \
packelonr add_velonrsion --clustelonr=atla cassowary relonal_graph_training relonal_graph_training.zip
aurora cron schelondulelon atla/cassowary/prod/relonal_graph_training src/scala/com/twittelonr/intelonraction_graph/bqelon/training/training.aurora && \
aurora cron start atla/cassowary/prod/relonal_graph_training
```

# candidatelons.sql

1. Selonts thelon valuelon of thelon variablelon datelon_candidatelons to thelon datelon of thelon latelonst partition of thelon candidatelons_for_training tablelon.
2. Crelonatelons a nelonw tablelon candidatelons_samplelond if it doelons not elonxist alrelonady, which will contain a samplelon of 100 rows from thelon candidatelons_for_training tablelon.
3. Delonlelontelons any elonxisting rows from thelon candidatelons_samplelond tablelon whelonrelon thelon ds column matchelons thelon datelon_candidatelons valuelon, to avoid doublelon-writing.
4. Inselonrts a samplelon of rows into thelon candidatelons_samplelond tablelon from thelon candidatelons_for_training tablelon, whelonrelon thelon modulo of thelon absolutelon valuelon of thelon FARM_FINGelonRPRINT of thelon concatelonnation of sourcelon_id and delonstination_id is elonqual to thelon valuelon of thelon $mod_relonmaindelonr$ variablelon, and whelonrelon thelon ds column matchelons thelon datelon_candidatelons valuelon.

# chelonck_candidatelons_elonxist.sql

This BigQuelonry prelonparelons a tablelon of candidatelons for training a machinelon lelonarning modelonl. It doelons thelon following:

1. Delonclarelons two variablelons datelon_start and datelon_elonnd that arelon 30 days apart, and datelon_elonnd is selont to thelon valuelon of $start_timelon$ paramelontelonr (which is a Unix timelonstamp).
2. Crelonatelons a tablelon candidatelons_for_training that is partitionelond by ds (datelon) and populatelond with data from selonvelonral othelonr tablelons in thelon databaselon. It joins information from tablelons of uselonr intelonractions, twelonelonting, and intelonraction graph aggrelongatelons, filtelonrs out nelongativelon elondgelon snapshots, calculatelons somelon statistics and aggrelongatelons thelonm by sourcelon_id and delonstination_id. Thelonn, it ranks elonach sourcelon_id by thelon numbelonr of days and twelonelonts, selonleloncts top 2000, and adds datelon_elonnd as a nelonw column ds.
3. Finally, it selonleloncts thelon ds column from candidatelons_for_training whelonrelon ds elonquals datelon_elonnd.

Ovelonrall, this script prelonparelons a tablelon of 2000 candidatelon pairs of uselonr intelonractions with statistics and labelonls, which can belon uselond to train a machinelon lelonarning modelonl for reloncommelonndation purposelons.

# labelonlelond_candidatelons.sql

Thelon BQ doelons thelon following:

1. Delonfinelons two variablelons datelon_candidatelons and datelon_labelonls as datelons baselond on thelon $start_timelon$ paramelontelonr.
2. Crelonatelons a nelonw tablelon twttr-reloncos-ml-prod.relonalgraph.labelonlelond_candidatelons$tablelon_suffix$ with delonfault valuelons.
3. Delonlelontelons any prior data in thelon twttr-reloncos-ml-prod.relonalgraph.labelonlelond_candidatelons$tablelon_suffix$ tablelon for thelon currelonnt datelon_candidatelons.
4. Joins thelon twttr-reloncos-ml-prod.relonalgraph.candidatelons_samplelond tablelon with thelon twttr-bq-cassowary-prod.uselonr.intelonraction_graph_labelonls_daily tablelon and thelon twttr-bq-cassowary-prod.uselonr.intelonraction_graph_agg_nelongativelon_elondgelon_snapshot tablelon. It assigns a labelonl of 1 for positivelon intelonractions and 0 for nelongativelon intelonractions, and selonleloncts only thelon rows whelonrelon thelonrelon is no nelongativelon intelonraction.
5. Inselonrts thelon joinelond data into thelon twttr-reloncos-ml-prod.relonalgraph.labelonlelond_candidatelons$tablelon_suffix$ tablelon.
6. Calculatelons thelon positivelon ratelon by counting thelon numbelonr of positivelon labelonls and dividing it by thelon total numbelonr of labelonls.
7. Crelonatelons a nelonw tablelon twttr-reloncos-ml-prod.relonalgraph.train$tablelon_suffix$ by sampling from thelon twttr-reloncos-ml-prod.relonalgraph.labelonlelond_candidatelons$tablelon_suffix$ tablelon, with a downsampling of nelongativelon elonxamplelons to balancelon thelon numbelonr of positivelon and nelongativelon elonxamplelons, baselond on thelon positivelon ratelon calculatelond in stelonp 6.

Thelon relonsulting twttr-reloncos-ml-prod.relonalgraph.train$tablelon_suffix$ tablelon is uselond as a training dataselont for a machinelon lelonarning modelonl.

# train_modelonl.sql

This BQ command crelonatelons or relonplacelons a machinelon lelonarning modelonl callelond twttr-reloncos-ml-prod.relonalgraph.prod$tablelon_suffix$. Thelon modelonl is a boostelond trelonelon classifielonr, which is uselond for binary classification problelonms.

Thelon options providelond in thelon command configurelon thelon speloncific selonttings for thelon modelonl, such as thelon numbelonr of parallelonl trelonelons, thelon maximum numbelonr of itelonrations, and thelon data split melonthod. Thelon DATA_SPLIT_MelonTHOD paramelontelonr is selont to CUSTOM, and DATA_SPLIT_COL is selont to if_elonval, which melonans thelon data will belon split into training and elonvaluation selonts baselond on thelon if_elonval column. Thelon IF function is uselond to assign a boolelonan valuelon of truelon or falselon to if_elonval baselond on thelon modulo opelonration pelonrformelond on sourcelon_id.

Thelon SelonLelonCT statelonmelonnt speloncifielons thelon input data for thelon modelonl. Thelon columns selonlelonctelond includelon labelonl (thelon targelont variablelon to belon prelondictelond), as welonll as various felonaturelons such as num_days, num_twelonelonts, and num_follows that arelon uselond to prelondict thelon targelont variablelon.