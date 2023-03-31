# Training

This folder contains the sql files that we'll use for training the prod real graph models:
- prod (predicts any interactions the next day)
- prod_explicit (predicts any explicit interactions the next day)

We have 3 steps that take place:
- candidate generation + feature hydration. this query samples 1% of edges from the `twttr-recos-ml-prod.realgraph.candidates` table which is already produced daily and saves it to `twttr-recos-ml-prod.realgraph.candidates_sampled`. we save each day's data according to the statebird batch run date and hence require checks to make sure that the data exists to begin with.
- label candidates. we join day T's candidates with day T+1's labels while filtering out any negative interactions to get our labeled dataset. we append an additional day's worth of segments for each day. we finally generate the training dataset which uses all day's labeled data for training, performing negative downsampling to get a roughly 50-50 split of positive to negative labels.
- training. we use bqml for training our xgboost models.

## Instructions

For deploying the job, you would need to create a zip file, upload to packer, and then schedule it with aurora.

```
zip -jr real_graph_training src/scala/com/twitter/interaction_graph/bqe/training && \
packer add_version --cluster=atla cassowary real_graph_training real_graph_training.zip
aurora cron schedule atla/cassowary/prod/real_graph_training src/scala/com/twitter/interaction_graph/bqe/training/training.aurora && \
aurora cron start atla/cassowary/prod/real_graph_training
```

# candidates.sql

1. Sets the value of the variable date_candidates to the date of the latest partition of the candidates_for_training table.
2. Creates a new table candidates_sampled if it does not exist already, which will contain a sample of 100 rows from the candidates_for_training table.
3. Deletes any existing rows from the candidates_sampled table where the ds column matches the date_candidates value, to avoid double-writing.
4. Inserts a sample of rows into the candidates_sampled table from the candidates_for_training table, where the modulo of the absolute value of the FARM_FINGERPRINT of the concatenation of source_id and destination_id is equal to the value of the $mod_remainder$ variable, and where the ds column matches the date_candidates value.

# check_candidates_exist.sql

This BigQuery prepares a table of candidates for training a machine learning model. It does the following:

1. Declares two variables date_start and date_end that are 30 days apart, and date_end is set to the value of $start_time$ parameter (which is a Unix timestamp).
2. Creates a table candidates_for_training that is partitioned by ds (date) and populated with data from several other tables in the database. It joins information from tables of user interactions, tweeting, and interaction graph aggregates, filters out negative edge snapshots, calculates some statistics and aggregates them by source_id and destination_id. Then, it ranks each source_id by the number of days and tweets, selects top 2000, and adds date_end as a new column ds.
3. Finally, it selects the ds column from candidates_for_training where ds equals date_end.

Overall, this script prepares a table of 2000 candidate pairs of user interactions with statistics and labels, which can be used to train a machine learning model for recommendation purposes.

# labeled_candidates.sql

The BQ does the following:

1. Defines two variables date_candidates and date_labels as dates based on the $start_time$ parameter.
2. Creates a new table twttr-recos-ml-prod.realgraph.labeled_candidates$table_suffix$ with default values.
3. Deletes any prior data in the twttr-recos-ml-prod.realgraph.labeled_candidates$table_suffix$ table for the current date_candidates.
4. Joins the twttr-recos-ml-prod.realgraph.candidates_sampled table with the twttr-bq-cassowary-prod.user.interaction_graph_labels_daily table and the twttr-bq-cassowary-prod.user.interaction_graph_agg_negative_edge_snapshot table. It assigns a label of 1 for positive interactions and 0 for negative interactions, and selects only the rows where there is no negative interaction.
5. Inserts the joined data into the twttr-recos-ml-prod.realgraph.labeled_candidates$table_suffix$ table.
6. Calculates the positive rate by counting the number of positive labels and dividing it by the total number of labels.
7. Creates a new table twttr-recos-ml-prod.realgraph.train$table_suffix$ by sampling from the twttr-recos-ml-prod.realgraph.labeled_candidates$table_suffix$ table, with a downsampling of negative examples to balance the number of positive and negative examples, based on the positive rate calculated in step 6.

The resulting twttr-recos-ml-prod.realgraph.train$table_suffix$ table is used as a training dataset for a machine learning model.

# train_model.sql

This BQ command creates or replaces a machine learning model called twttr-recos-ml-prod.realgraph.prod$table_suffix$. The model is a boosted tree classifier, which is used for binary classification problems.

The options provided in the command configure the specific settings for the model, such as the number of parallel trees, the maximum number of iterations, and the data split method. The DATA_SPLIT_METHOD parameter is set to CUSTOM, and DATA_SPLIT_COL is set to if_eval, which means the data will be split into training and evaluation sets based on the if_eval column. The IF function is used to assign a boolean value of true or false to if_eval based on the modulo operation performed on source_id.

The SELECT statement specifies the input data for the model. The columns selected include label (the target variable to be predicted), as well as various features such as num_days, num_tweets, and num_follows that are used to predict the target variable.