# Scor ng

T  folder conta ns t  sql f les that  'll use for scor ng t  real graph edges  n BQ.   have 4 steps that take place:
- c ck to make sure that   models are  n place. t  feature  mportance query should return 20 rows  n total: 10 rows per model, 1 for each feature.
- follow graph feature generat on. t   s to ensure that   have features for all users regardless  f t y have had any recent act v y.
- cand date generat on. t  query comb nes t  cand dates from t  follow graph and t  act v y graph, and t  features from both.
- scor ng. t  query scores w h 2 of   prod models and saves t  scores to a table, w h an add  onal f eld that d st ngu s s  f an edge  n  n/out of network.

##  nstruct ons

For deploy ng t  job,   would need to create a z p f le, upload to packer, and t n sc dule   w h aurora.

```
z p -jr real_graph_scor ng src/scala/com/tw ter/ nteract on_graph/bqe/scor ng && \
packer add_vers on --cluster=atla cassowary real_graph_scor ng real_graph_scor ng.z p
aurora cron sc dule atla/cassowary/prod/real_graph_scor ng src/scala/com/tw ter/ nteract on_graph/bqe/scor ng/scor ng.aurora && \
aurora cron start atla/cassowary/prod/real_graph_scor ng
```

# cand dates.sql

T  B gQuery (BQ) query does t  follow ng:

1. Declares two var ables, date_start and date_end, wh ch are both of type DATE.
2. Sets t  date_end var able to t  max mum part  on  D of t   nteract on_graph_labels_da ly table, us ng t  PARSE_DATE() funct on to convert t  part  on  D to a date format.
3. Sets t  date_start var able to 30 days pr or to t  date_end var able, us ng t  DATE_SUB() funct on.
4. Creates a new table called cand dates  n t  realgraph dataset, part  oned by ds.
5. T  query uses three common table express ons (T1, T2, and T3) to jo n data from two tables ( nteract on_graph_labels_da ly and t et ng_follows) to generate a table conta n ng cand date  nformat on and features.
6. T  table T3  s t  result of a full outer jo n bet en T1 and T2, group ng by s ce_ d and dest nat on_ d, and aggregat ng values such as num_t ets, label_types, and t  counts of d fferent types of labels (e.g. num_follows, num_favor es, etc.).
7. T  T4 table ranks each s ce_ d by t  number of num_days and num_t ets, and selects t  top 2000 rows for each s ce_ d.
8. F nally, t  query selects all columns from t  T4 table and appends t  date_end var able as a new column na d ds.

Overall, t  query generates a table of cand dates and t  r assoc ated features for a part cular date range, us ng data from two tables  n t  twttr-bq-cassowary-prod and twttr-recos-ml-prod datasets.

# follow_graph_features.sql

T  B gQuery scr pt creates a table twttr-recos-ml-prod.realgraph.t et ng_follows that  ncludes features for Tw ter user  nteract ons, spec f cally t et counts and follows.

F rst,   sets two var ables date_latest_t et and date_latest_follows to t  most recent dates ava lable  n two separate tables: twttr-bq-t ets ce-pub-prod.user.publ c_t ets and twttr-recos-ml-prod.user_events.val d_user_follows, respect vely.

T n,   creates t  t et_count and all_follows CTEs.

T  t et_count CTE counts t  number of t ets made by each user w h n t  last 3 days pr or to date_latest_t et.

T  all_follows CTE retr eves all t  follows from t  val d_user_follows table that happened on date_latest_follows and left jo ns   w h t  t et_count CTE.   also adds a row number that part  ons by t  s ce user  D and orders by t  number of t ets  n descend ng order. T  f nal output  s f ltered to keep only t  top 2000 follows per user based on t  row number.

T  f nal SELECT state nt comb nes t  all_follows CTE w h t  date_latest_t et var able and  nserts t  results  nto t  twttr-recos-ml-prod.realgraph.t et ng_follows table part  oned by date.

# scor ng.sql

T  BQ code performs operat ons on a B gQuery table called twttr-recos-ml-prod.realgraph.scores.  re  s a step-by-step breakdown of what t  code does:

Declare two var ables, date_end and date_latest_follows, and set t  r values based on t  latest part  ons  n t  twttr-bq-cassowary-prod.user. NFORMAT ON_SCHEMA.PART T ONS and twttr-recos-ml-prod.user_events. NFORMAT ON_SCHEMA.PART T ONS tables that correspond to spec f c tables, respect vely. T  PARSE_DATE() funct on  s used to convert t  part  on  Ds to date format.

Delete rows from t  twttr-recos-ml-prod.realgraph.scores table w re t  value of t  ds column  s equal to date_end.

 nsert rows  nto t  twttr-recos-ml-prod.realgraph.scores table based on a query that generates pred cted scores for pa rs of user  Ds us ng two mach ne learn ng models. Spec f cally, t  query uses t  ML.PRED CT() funct on to apply two mach ne learn ng models (twttr-recos-ml-prod.realgraph.prod and twttr-recos-ml-prod.realgraph.prod_expl c ) to t  twttr-recos-ml-prod.realgraph.cand dates table. T  result ng pred cted scores are jo ned w h t  twttr-recos-ml-prod.realgraph.t et ng_follows table, wh ch conta ns  nformat on about t  number of t ets made by users and t  r follow relat onsh ps, us ng a full outer jo n. T  f nal result  ncludes columns for t  s ce  D, dest nat on  D, pred cted score (prob), expl c  pred cted score (prob_expl c ), a b nary var able  nd cat ng w t r t  dest nat on  D  s follo d by t  s ce  D (follo d), and t  value of date_end for t  ds column.  f t re  s no match  n t  pred cted_scores table for a g ven pa r of user  Ds, t  COALESCE() funct on  s used to return t  correspond ng values from t  t et ng_follows table, w h default values of 0.0 for t  pred cted scores.

