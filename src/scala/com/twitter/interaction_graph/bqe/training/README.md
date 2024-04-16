# Tra n ng

T  folder conta ns t  sql f les that  'll use for tra n ng t  prod real graph models:
- prod (pred cts any  nteract ons t  next day)
- prod_expl c  (pred cts any expl c   nteract ons t  next day)

  have 3 steps that take place:
- cand date generat on + feature hydrat on. t  query samples 1% of edges from t  `twttr-recos-ml-prod.realgraph.cand dates` table wh ch  s already produced da ly and saves   to `twttr-recos-ml-prod.realgraph.cand dates_sampled`.   save each day's data accord ng to t  stateb rd batch run date and  nce requ re c cks to make sure that t  data ex sts to beg n w h.
- label cand dates.   jo n day T's cand dates w h day T+1's labels wh le f lter ng out any negat ve  nteract ons to get   labeled dataset.   append an add  onal day's worth of seg nts for each day.   f nally generate t  tra n ng dataset wh ch uses all day's labeled data for tra n ng, perform ng negat ve downsampl ng to get a roughly 50-50 spl  of pos  ve to negat ve labels.
- tra n ng.   use bqml for tra n ng   xgboost models.

##  nstruct ons

For deploy ng t  job,   would need to create a z p f le, upload to packer, and t n sc dule   w h aurora.

```
z p -jr real_graph_tra n ng src/scala/com/tw ter/ nteract on_graph/bqe/tra n ng && \
packer add_vers on --cluster=atla cassowary real_graph_tra n ng real_graph_tra n ng.z p
aurora cron sc dule atla/cassowary/prod/real_graph_tra n ng src/scala/com/tw ter/ nteract on_graph/bqe/tra n ng/tra n ng.aurora && \
aurora cron start atla/cassowary/prod/real_graph_tra n ng
```

# cand dates.sql

1. Sets t  value of t  var able date_cand dates to t  date of t  latest part  on of t  cand dates_for_tra n ng table.
2. Creates a new table cand dates_sampled  f   does not ex st already, wh ch w ll conta n a sample of 100 rows from t  cand dates_for_tra n ng table.
3. Deletes any ex st ng rows from t  cand dates_sampled table w re t  ds column matc s t  date_cand dates value, to avo d double-wr  ng.
4.  nserts a sample of rows  nto t  cand dates_sampled table from t  cand dates_for_tra n ng table, w re t  modulo of t  absolute value of t  FARM_F NGERPR NT of t  concatenat on of s ce_ d and dest nat on_ d  s equal to t  value of t  $mod_rema nder$ var able, and w re t  ds column matc s t  date_cand dates value.

# c ck_cand dates_ex st.sql

T  B gQuery prepares a table of cand dates for tra n ng a mach ne learn ng model.   does t  follow ng:

1. Declares two var ables date_start and date_end that are 30 days apart, and date_end  s set to t  value of $start_t  $ para ter (wh ch  s a Un x t  stamp).
2. Creates a table cand dates_for_tra n ng that  s part  oned by ds (date) and populated w h data from several ot r tables  n t  database.   jo ns  nformat on from tables of user  nteract ons, t et ng, and  nteract on graph aggregates, f lters out negat ve edge snapshots, calculates so  stat st cs and aggregates t m by s ce_ d and dest nat on_ d. T n,   ranks each s ce_ d by t  number of days and t ets, selects top 2000, and adds date_end as a new column ds.
3. F nally,   selects t  ds column from cand dates_for_tra n ng w re ds equals date_end.

Overall, t  scr pt prepares a table of 2000 cand date pa rs of user  nteract ons w h stat st cs and labels, wh ch can be used to tra n a mach ne learn ng model for recom ndat on purposes.

# labeled_cand dates.sql

T  BQ does t  follow ng:

1. Def nes two var ables date_cand dates and date_labels as dates based on t  $start_t  $ para ter.
2. Creates a new table twttr-recos-ml-prod.realgraph.labeled_cand dates$table_suff x$ w h default values.
3. Deletes any pr or data  n t  twttr-recos-ml-prod.realgraph.labeled_cand dates$table_suff x$ table for t  current date_cand dates.
4. Jo ns t  twttr-recos-ml-prod.realgraph.cand dates_sampled table w h t  twttr-bq-cassowary-prod.user. nteract on_graph_labels_da ly table and t  twttr-bq-cassowary-prod.user. nteract on_graph_agg_negat ve_edge_snapshot table.   ass gns a label of 1 for pos  ve  nteract ons and 0 for negat ve  nteract ons, and selects only t  rows w re t re  s no negat ve  nteract on.
5.  nserts t  jo ned data  nto t  twttr-recos-ml-prod.realgraph.labeled_cand dates$table_suff x$ table.
6. Calculates t  pos  ve rate by count ng t  number of pos  ve labels and d v d ng   by t  total number of labels.
7. Creates a new table twttr-recos-ml-prod.realgraph.tra n$table_suff x$ by sampl ng from t  twttr-recos-ml-prod.realgraph.labeled_cand dates$table_suff x$ table, w h a downsampl ng of negat ve examples to balance t  number of pos  ve and negat ve examples, based on t  pos  ve rate calculated  n step 6.

T  result ng twttr-recos-ml-prod.realgraph.tra n$table_suff x$ table  s used as a tra n ng dataset for a mach ne learn ng model.

# tra n_model.sql

T  BQ command creates or replaces a mach ne learn ng model called twttr-recos-ml-prod.realgraph.prod$table_suff x$. T  model  s a boosted tree class f er, wh ch  s used for b nary class f cat on problems.

T  opt ons prov ded  n t  command conf gure t  spec f c sett ngs for t  model, such as t  number of parallel trees, t  max mum number of  erat ons, and t  data spl   thod. T  DATA_SPL T_METHOD para ter  s set to CUSTOM, and DATA_SPL T_COL  s set to  f_eval, wh ch  ans t  data w ll be spl   nto tra n ng and evaluat on sets based on t   f_eval column. T   F funct on  s used to ass gn a boolean value of true or false to  f_eval based on t  modulo operat on perfor d on s ce_ d.

T  SELECT state nt spec f es t   nput data for t  model. T  columns selected  nclude label (t  target var able to be pred cted), as  ll as var ous features such as num_days, num_t ets, and num_follows that are used to pred ct t  target var able.