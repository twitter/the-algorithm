## Real Graph (bqe)

T  project bu lds a mach ne learn ng model us ng a grad ent boost ng tree class f er to pred ct t  l kel hood of a Tw ter user  nteract ng w h anot r user.

T  algor hm works by f rst creat ng a labeled dataset of user  nteract ons from a graph of Tw ter users. T  graph  s represented  n a B gQuery table w re each row represents a d rected edge bet en two users, along w h var ous features such as t  number of t ets, follows, favor es, and ot r  tr cs related to user behav or.

To create t  labeled dataset, t  algor hm f rst selects a set of cand date  nteract ons by  dent fy ng all edges that  re act ve dur ng a certa n t   per od.   t n jo ns t  cand date set w h a set of labeled  nteract ons that occurred one day after t  cand date per od. Pos  ve  nteract ons are labeled as "1" and negat ve  nteract ons are labeled as "0". T  result ng labeled dataset  s t n used to tra n a boosted tree class f er model.

T  model  s tra ned us ng t  labeled dataset and var ous hyperpara ters,  nclud ng t  max mum number of  erat ons and t  subsample rate. T  algor hm spl s t  labeled dataset  nto tra n ng and test ng sets based on t  s ce user's  D, us ng a custom data spl   thod.

Once t  model  s tra ned,   can be used to generate a score est mat ng t  probab l y of a user  nteract ng w h anot r user.

## Real Graph (sc o)

T  project aggregates t  number of  nteract ons bet en pa rs of users on Tw ter. On a da ly bas s, t re are mult ple dataflow jobs that perform t  aggregat on, wh ch  ncludes publ c engage nts l ke favor es, ret ets, follows, etc. as  ll as pr vate engage nts l ke prof le v ews, t et cl cks, and w t r or not a user has anot r user  n t  r address book (g ven a user opt- n to share address book).

After t  da ly aggregat on of  nteract ons, t re  s a rollup job that aggregates yesterday's aggregat on w h today's  nteract ons. T  rollup job outputs several results,  nclud ng t  da ly count of  nteract ons per  nteract on types bet en a pa r of users, t  da ly  ncom ng  nteract ons made on a user per  nteract on type, t  rollup aggregat on of  nteract ons as a decayed sum bet en a pa r of users, and t  rollup aggregat on of  ncom ng  nteract ons made on a user.

F nally, t  rollup job outputs t  ML pred cted  nteract on score bet en t  pa r of users alongs de t  rollup aggregat on of  nteract ons as a decayed sum bet en t m.
