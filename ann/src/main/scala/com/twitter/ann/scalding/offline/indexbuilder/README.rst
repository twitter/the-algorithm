********
Overv ew
********
T  job reads embedd ng data from HDFS  n t  embedd ng formats supported by t  cortex MLX team.   converts that data  nto t  ANN format and adds   to an ANN  ndex. T  ANN  ndex  s ser al zed and save to d sk.

*****************
Runn ng  n Aurora
*****************

Set up example
==============
T  job bu lds an ANN  ndex based on hnsw algor hm us ng user embedd ngs ava lable  n hdfs.

.. code-block:: bash

  $ export JOB_NAME=ann_ ndex_bu lder
  $ export OUTPUT_PATH=hdfs:///user/$USER/${JOB_NAME}_test

  $ CPU=32 RAM_GB=150 D SK_GB=60 aurora job create smf1/$USER/devel/$JOB_NAME ann/src/ma n/aurora/ ndex_bu lder/aurora_bu lder.aurora \
    --b nd=prof le.na =$JOB_NAME \
    --b nd=prof le.role=$USER \
    --b nd=prof le.output_d r=$OUTPUT_PATH \
    --b nd=prof le.ent y_k nd=user \
    --b nd=prof le.embedd ng_args='-- nput.embedd ng_format tab -- nput.embedd ng_path /user/cortex-mlx/off c al_examples/ann/non_p  _random_user_embedd ngs_tab_format' \
    --b nd=prof le.num_d  ns ons=300 \
    --b nd=prof le.algo=hnsw \
    --b nd=prof le.ef_construct on=200 \
    --b nd=prof le.max_m=16 \
    --b nd=prof le.expected_ele nts=10000000 \
    --b nd=prof le. tr c= nnerProduct \
    --b nd=prof le.concurrency_level=32 \
    --b nd=prof le.hadoop_cluster=dw2-smf1

T  job bu lds an ANN  ndex based on hnsw algor hm us ng producer embedd ngs (Major vers on 1546473691) ava lable  n feature store.

.. code-block:: bash

  $ export JOB_NAME=ann_ ndex_bu lder
  $ export OUTPUT_PATH=hdfs:///user/$USER/${JOB_NAME}_test

  $ CPU=32 RAM_GB=150 D SK_GB=60 aurora job create smf1/$USER/devel/$JOB_NAME ann/src/ma n/aurora/ ndex_bu lder/aurora_bu lder.aurora \
    --b nd=prof le.na =$JOB_NAME \
    --b nd=prof le.role=$USER \
    --b nd=prof le.output_d r=$OUTPUT_PATH \
    --b nd=prof le.ent y_k nd=user \
    --b nd=prof le.embedd ng_args='-- nput.feature_store_embedd ng ProducerFollowEmbedd ng300Dataset -- nput.feature_store_major_vers on 1546473691 -- nput.date_range 2019-01-02' \
    --b nd=prof le.num_d  ns ons=300 \
    --b nd=prof le.algo=hnsw \
    --b nd=prof le.ef_construct on=200 \
    --b nd=prof le.max_m=16 \
    --b nd=prof le.expected_ele nts=10000000 \
    --b nd=prof le. tr c= nnerProduct \
    --b nd=prof le.concurrency_level=32 \
    --b nd=prof le.hadoop_cluster=dw2-smf1


*************
Job argu nts
*************

Env ro nt var ables (res ces):
==============
- **CPU** Number of cpu cores (default: 32)
- **RAM_GB** RAM  n g gabytes (default: 150)
- **D SK_GB** D sk  n g gabytes (default: 60)

General argu nts (spec f ed as **--prof le.{opt ons}**):
==============
- **na ** Aurora job na 
- **role** Aurora role
- **hadoop_cluster** Hadoop cluster for data. dw2-smf1/proc-atla.
- ** nput_d r** Path of saved embedd ngs  n hdfs w hout pref x ng `hdfs://`
- **ent y_k nd** T  type of ent y  d that  s use w h t  embedd ngs. Poss ble opt ons:

  - word
  - url
  - user
  - t et
  - tfw d

- **embedd ng_args** Embedd ng format args. See t  docu ntat on  n `com.tw ter.cortex.ml.embedd ngs.common.Embedd ngFormatArgsParser` for a full explanat on of t   nput opt ons. Poss ble opt ons:

  1. ** nput.embedd ng_format** Format of t  ser al zed embedd ng.

     - usertensor
     - usercont nuous
     - comma
     - tab

  2. ** nput.embedd ng_path** Path of saved embedd ngs  n hdfs w hout pref x ng `hdfs://`

  3. ** nput.{feature_store_args}** For feature store related args l ke `feature_store_embedd ng`, `feature_store_major_vers on`, `date_range`:

- **output_d r** W re to save t  produced ser al zed ann  ndex. Save to HDFS by spec fy ng t  full UR . e.g `hdfs://hadoop-dw2-nn.smf1.tw ter.com/user/<user>/ ndex_f le` or us ng t  default cluster `hdfs:///user/<user>/ ndex_f le`.
- **num_d  ns ons** D  ns on of embedd ng  n t   nput data. An except on w ll be thrown  f any entry does not have a number of d  ns ons equal to t  number.
- ** tr c** D stance  tr c ( nnerProduct/Cos ne/L2)
- **concurrency_level** Spec f es how many parallel  nserts happen to t   ndex. T  should probably be set to t  number of cores on t  mach ne.
- **algo** T  k nd of  ndex   want to ouput. T  supported opt ons r ght now are:

  1. **hnsw** ( tr c supported: Cos ne, L2,  nnerProduct)

     .. _hnsw: https://arx v.org/abs/1603.09320

     - **ef\_construct on** : Larger value  ncreases bu ld t   but w ll g ve better recall. Good start value : 200
     - **max\_m** : Larger value  ncreases w ll  ncrease t   ndex s ze but w ll g ve better recall. Opt mal Range : 6-48. Good start ng value 16.
     - **expected\_ele nts** : Approx mate number of ele nts that w ll be  ndexed.

  2. **annoy** ( tr c supported: Cos ne, L2)

     .. _annoy: https://g hub.com/spot fy/annoy

     - **annoy\_num\_trees** T  para ter  s requ red for annoy. From t  annoy docu ntat on: num_trees  s prov ded dur ng bu ld t   and affects t  bu ld t   and t   ndex s ze. A larger value w ll g ve more accurate results, but larger  ndexes.

  3. **brute_force** ( tr c supported: Cos ne, L2,  nnerProduct)


Develop ng locally
===================

For bu ld ng and test ng custom ann  ndex bu lder job,
  can create job bundle locally, upload to packer and t n   can be used w h t  job us ng `prof le.packer_package` for na ,  `prof le.packer_role` for role and `prof le.packer_vers on` for bundle vers on.

.. code-block:: bash

  ./bazel bundle ann/src/ma n/scala/com/tw ter/ann/scald ng/offl ne/ ndexbu lder: ndexbu lder-deploy \
  --bundle-jvm-arch ve=z p

.. code-block:: bash

  packer add_vers on --cluster=atla <role> <package_na > d st/ ndexbu lder-deploy.z p


