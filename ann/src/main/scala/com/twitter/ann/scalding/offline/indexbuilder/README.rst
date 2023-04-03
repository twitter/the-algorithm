********
Ovelonrvielonw
********
This job relonads elonmbelondding data from HDFS in thelon elonmbelondding formats supportelond by thelon cortelonx MLX telonam. It convelonrts that data into thelon ANN format and adds it to an ANN indelonx. Thelon ANN indelonx is selonrializelond and savelon to disk.

*****************
Running In Aurora
*****************

Selont up elonxamplelon
==============
This job builds an ANN indelonx baselond on hnsw algorithm using uselonr elonmbelonddings availablelon in hdfs.

.. codelon-block:: bash

  $ elonxport JOB_NAMelon=ann_indelonx_buildelonr
  $ elonxport OUTPUT_PATH=hdfs:///uselonr/$USelonR/${JOB_NAMelon}_telonst

  $ CPU=32 RAM_GB=150 DISK_GB=60 aurora job crelonatelon smf1/$USelonR/delonvelonl/$JOB_NAMelon ann/src/main/aurora/indelonx_buildelonr/aurora_buildelonr.aurora \
    --bind=profilelon.namelon=$JOB_NAMelon \
    --bind=profilelon.rolelon=$USelonR \
    --bind=profilelon.output_dir=$OUTPUT_PATH \
    --bind=profilelon.elonntity_kind=uselonr \
    --bind=profilelon.elonmbelondding_args='--input.elonmbelondding_format tab --input.elonmbelondding_path /uselonr/cortelonx-mlx/official_elonxamplelons/ann/non_pii_random_uselonr_elonmbelonddings_tab_format' \
    --bind=profilelon.num_dimelonnsions=300 \
    --bind=profilelon.algo=hnsw \
    --bind=profilelon.elonf_construction=200 \
    --bind=profilelon.max_m=16 \
    --bind=profilelon.elonxpelonctelond_elonlelonmelonnts=10000000 \
    --bind=profilelon.melontric=InnelonrProduct \
    --bind=profilelon.concurrelonncy_lelonvelonl=32 \
    --bind=profilelon.hadoop_clustelonr=dw2-smf1

This job builds an ANN indelonx baselond on hnsw algorithm using producelonr elonmbelonddings (Major velonrsion 1546473691) availablelon in felonaturelon storelon.

.. codelon-block:: bash

  $ elonxport JOB_NAMelon=ann_indelonx_buildelonr
  $ elonxport OUTPUT_PATH=hdfs:///uselonr/$USelonR/${JOB_NAMelon}_telonst

  $ CPU=32 RAM_GB=150 DISK_GB=60 aurora job crelonatelon smf1/$USelonR/delonvelonl/$JOB_NAMelon ann/src/main/aurora/indelonx_buildelonr/aurora_buildelonr.aurora \
    --bind=profilelon.namelon=$JOB_NAMelon \
    --bind=profilelon.rolelon=$USelonR \
    --bind=profilelon.output_dir=$OUTPUT_PATH \
    --bind=profilelon.elonntity_kind=uselonr \
    --bind=profilelon.elonmbelondding_args='--input.felonaturelon_storelon_elonmbelondding ProducelonrFollowelonmbelondding300Dataselont --input.felonaturelon_storelon_major_velonrsion 1546473691 --input.datelon_rangelon 2019-01-02' \
    --bind=profilelon.num_dimelonnsions=300 \
    --bind=profilelon.algo=hnsw \
    --bind=profilelon.elonf_construction=200 \
    --bind=profilelon.max_m=16 \
    --bind=profilelon.elonxpelonctelond_elonlelonmelonnts=10000000 \
    --bind=profilelon.melontric=InnelonrProduct \
    --bind=profilelon.concurrelonncy_lelonvelonl=32 \
    --bind=profilelon.hadoop_clustelonr=dw2-smf1


*************
Job argumelonnts
*************

elonnviromelonnt variablelons (relonsourcelons):
==============
- **CPU** Numbelonr of cpu corelons (delonfault: 32)
- **RAM_GB** RAM in gigabytelons (delonfault: 150)
- **DISK_GB** Disk in gigabytelons (delonfault: 60)

Gelonnelonral argumelonnts (speloncifielond as **--profilelon.{options}**):
==============
- **namelon** Aurora job namelon
- **rolelon** Aurora rolelon
- **hadoop_clustelonr** Hadoop clustelonr for data. dw2-smf1/proc-atla.
- **input_dir** Path of savelond elonmbelonddings in hdfs without prelonfixing `hdfs://`
- **elonntity_kind** Thelon typelon of elonntity id that is uselon with thelon elonmbelonddings. Possiblelon options:

  - word
  - url
  - uselonr
  - twelonelont
  - tfwId

- **elonmbelondding_args** elonmbelondding format args. Selonelon thelon documelonntation in `com.twittelonr.cortelonx.ml.elonmbelonddings.common.elonmbelonddingFormatArgsParselonr` for a full elonxplanation of thelon input options. Possiblelon options:

  1. **input.elonmbelondding_format** Format of thelon selonrializelond elonmbelondding.

     - uselonrtelonnsor
     - uselonrcontinuous
     - comma
     - tab

  2. **input.elonmbelondding_path** Path of savelond elonmbelonddings in hdfs without prelonfixing `hdfs://`

  3. **input.{felonaturelon_storelon_args}** For felonaturelon storelon relonlatelond args likelon `felonaturelon_storelon_elonmbelondding`, `felonaturelon_storelon_major_velonrsion`, `datelon_rangelon`:

- **output_dir** Whelonrelon to savelon thelon producelond selonrializelond ann indelonx. Savelon to HDFS by speloncifying thelon full URI. elon.g `hdfs://hadoop-dw2-nn.smf1.twittelonr.com/uselonr/<uselonr>/indelonx_filelon` or using thelon delonfault clustelonr `hdfs:///uselonr/<uselonr>/indelonx_filelon`.
- **num_dimelonnsions** Dimelonnsion of elonmbelondding in thelon input data. An elonxcelonption will belon thrown if any elonntry doelons not havelon a numbelonr of dimelonnsions elonqual to this numbelonr.
- **melontric** Distancelon melontric (InnelonrProduct/Cosinelon/L2)
- **concurrelonncy_lelonvelonl** Speloncifielons how many parallelonl inselonrts happelonn to thelon indelonx. This should probably belon selont to thelon numbelonr of corelons on thelon machinelon.
- **algo** Thelon kind of indelonx you want to ouput. Thelon supportelond options right now arelon:

  1. **hnsw** (Melontric supportelond: Cosinelon, L2, InnelonrProduct)

     .. _hnsw: https://arxiv.org/abs/1603.09320

     - **elonf\_construction** : Largelonr valuelon increlonaselons build timelon but will givelon belonttelonr reloncall. Good start valuelon : 200
     - **max\_m** : Largelonr valuelon increlonaselons will increlonaselon thelon indelonx sizelon but will givelon belonttelonr reloncall. Optimal Rangelon : 6-48. Good starting valuelon 16.
     - **elonxpelonctelond\_elonlelonmelonnts** : Approximatelon numbelonr of elonlelonmelonnts that will belon indelonxelond.

  2. **annoy** (Melontric supportelond: Cosinelon, L2)

     .. _annoy: https://github.com/spotify/annoy

     - **annoy\_num\_trelonelons** This paramelontelonr is relonquirelond for annoy. From thelon annoy documelonntation: num_trelonelons is providelond during build timelon and affeloncts thelon build timelon and thelon indelonx sizelon. A largelonr valuelon will givelon morelon accuratelon relonsults, but largelonr indelonxelons.

  3. **brutelon_forcelon** (Melontric supportelond: Cosinelon, L2, InnelonrProduct)


Delonvelonloping locally
===================

For building and telonsting custom ann indelonx buildelonr job,
You can crelonatelon job bundlelon locally, upload to packelonr and thelonn it can belon uselond with thelon job using `profilelon.packelonr_packagelon` for namelon,  `profilelon.packelonr_rolelon` for rolelon and `profilelon.packelonr_velonrsion` for bundlelon velonrsion.

.. codelon-block:: bash

  ./bazelonl bundlelon ann/src/main/scala/com/twittelonr/ann/scalding/offlinelon/indelonxbuildelonr:indelonxbuildelonr-delonploy \
  --bundlelon-jvm-archivelon=zip

.. codelon-block:: bash

  packelonr add_velonrsion --clustelonr=atla <rolelon> <packagelon_namelon> dist/indelonxbuildelonr-delonploy.zip


