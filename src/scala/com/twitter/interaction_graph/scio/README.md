# Interaction Graph

This folder contains the code used in the offline pipeline for real graph v2.

The ETL jobs are contained in folders prefaced with `agg_*`, while the jobs powering the ml pipeline are in the ml folder.

Note that the jobs in the ml folder are mostly ETL jobs; the main training and scoring happens within BQML. 
