 mport kerastuner as kt
 mport math
 mport numpy as np
 mport pandas as pd
 mport random
 mport sklearn. tr cs
 mport tensorflow as tf
 mport os
 mport glob

from tqdm  mport tqdm
from matplotl b  mport pyplot as plt
from tensorflow.keras.models  mport Sequent al
from tensorflow.keras.layers  mport Dense
from google.cloud  mport storage

phys cal_dev ces = tf.conf g.l st_phys cal_dev ces('GPU')
phys cal_dev ces

tf.conf g.set_v s ble_dev ces([tf.conf g.Phys calDev ce(na ='/phys cal_dev ce:GPU:1', dev ce_type='GPU')], 'GPU')
tf.conf g.get_v s ble_dev ces('GPU')

def decode_fn_embedd ng(example_proto):
  
  feature_descr pt on = {
    "embedd ng": tf. o.F xedLenFeature([256], dtype=tf.float32),
    "labels": tf. o.F xedLenFeature([], dtype=tf. nt64),
  }
  
  example = tf. o.parse_s ngle_example(
      example_proto,
      feature_descr pt on
  )

  return example

def preprocess_embedd ng_example(example_d ct, pos  ve_label=1, features_as_d ct=False):
  labels = example_d ct["labels"]
  label = tf.math.reduce_any(labels == pos  ve_label)
  label = tf.cast(label, tf. nt32)
  embedd ng = example_d ct["embedd ng"]
  
   f features_as_d ct:
    features = {"embedd ng": embedd ng}
  else:
    features = embedd ng
    
  return features, label
 nput_root = ...
sens_prev_ nput_root = ...

use_sens_prev_data = True
has_val dat on_data = True
pos  ve_label = 1

tra n_batch_s ze = 256
test_batch_s ze = 256
val dat on_batch_s ze = 256

do_resample = False
def class_func(features, label):
  return label

resample_fn = tf.data.exper  ntal.reject on_resample(
    class_func, target_d st = [0.5, 0.5], seed=0
)
tra n_glob = f"{ nput_root}/tra n/tfrecord/*.tfrecord"
tra n_f les = tf. o.gf le.glob(tra n_glob)

 f use_sens_prev_data:
  tra n_sens_prev_glob = f"{sens_prev_ nput_root}/tra n/tfrecord/*.tfrecord"
  tra n_sens_prev_f les = tf. o.gf le.glob(tra n_sens_prev_glob)
  tra n_f les = tra n_f les + tra n_sens_prev_f les
  
random.shuffle(tra n_f les)

 f not len(tra n_f les):
  ra se ValueError(f"D d not f nd any tra n f les match ng {tra n_glob}")


test_glob = f"{ nput_root}/test/tfrecord/*.tfrecord"
test_f les =  tf. o.gf le.glob(test_glob)

 f not len(test_f les):
  ra se ValueError(f"D d not f nd any eval f les match ng {test_glob}")
  
test_ds = tf.data.TFRecordDataset(test_f les).map(decode_fn_embedd ng)
test_ds = test_ds.map(lambda x: preprocess_embedd ng_example(x, pos  ve_label=pos  ve_label)).batch(batch_s ze=test_batch_s ze)
  
 f use_sens_prev_data:
  test_sens_prev_glob = f"{sens_prev_ nput_root}/test/tfrecord/*.tfrecord"
  test_sens_prev_f les =  tf. o.gf le.glob(test_sens_prev_glob)
  
   f not len(test_sens_prev_f les):
    ra se ValueError(f"D d not f nd any eval f les match ng {test_sens_prev_glob}")
  
  test_sens_prev_ds = tf.data.TFRecordDataset(test_sens_prev_f les).map(decode_fn_embedd ng)
  test_sens_prev_ds = test_sens_prev_ds.map(lambda x: preprocess_embedd ng_example(x, pos  ve_label=pos  ve_label)).batch(batch_s ze=test_batch_s ze)

tra n_ds = tf.data.TFRecordDataset(tra n_f les).map(decode_fn_embedd ng)
tra n_ds = tra n_ds.map(lambda x: preprocess_embedd ng_example(x, pos  ve_label=pos  ve_label))

 f do_resample:
  tra n_ds = tra n_ds.apply(resample_fn).map(lambda _,b:(b))

tra n_ds = tra n_ds.batch(batch_s ze=256).shuffle(buffer_s ze=10)
tra n_ds = tra n_ds.repeat()
  

 f has_val dat on_data: 
  eval_glob = f"{ nput_root}/val dat on/tfrecord/*.tfrecord"
  eval_f les =  tf. o.gf le.glob(eval_glob)
    
   f use_sens_prev_data:
    eval_sens_prev_glob = f"{sens_prev_ nput_root}/val dat on/tfrecord/*.tfrecord"
    eval_sens_prev_f les = tf. o.gf le.glob(eval_sens_prev_glob)
    eval_f les =  eval_f les + eval_sens_prev_f les
    
    
   f not len(eval_f les):
    ra se ValueError(f"D d not f nd any eval f les match ng {eval_glob}")
  
  eval_ds = tf.data.TFRecordDataset(eval_f les).map(decode_fn_embedd ng)
  eval_ds = eval_ds.map(lambda x: preprocess_embedd ng_example(x, pos  ve_label=pos  ve_label)).batch(batch_s ze=val dat on_batch_s ze)

else:
  
  eval_ds = tf.data.TFRecordDataset(test_f les).map(decode_fn_embedd ng)
  eval_ds = eval_ds.map(lambda x: preprocess_embedd ng_example(x, pos  ve_label=pos  ve_label)).batch(batch_s ze=val dat on_batch_s ze)
c ck_ds = tf.data.TFRecordDataset(tra n_f les).map(decode_fn_embedd ng)
cnt = 0
pos_cnt = 0
for example  n tqdm(c ck_ds):
  label = example['labels']
   f label == 1:
    pos_cnt += 1
  cnt += 1
pr nt(f'{cnt} tra n entr es w h {pos_cnt} pos  ve')

 tr cs = []

 tr cs.append(
  tf.keras. tr cs.Prec s onAtRecall(
    recall=0.9, num_thresholds=200, class_ d=None, na =None, dtype=None
  )
)

 tr cs.append(
  tf.keras. tr cs.AUC(
    num_thresholds=200,
    curve="PR",
  )
)
def bu ld_model(hp):
  model = Sequent al()

  opt m zer = tf.keras.opt m zers.Adam(
    learn ng_rate=0.001,
    beta_1=0.9,
    beta_2=0.999,
    eps lon=1e-08,
    amsgrad=False,
    na ="Adam",
  )
  
  act vat on=hp.Cho ce("act vat on", ["tanh", "gelu"])
  kernel_ n  al zer=hp.Cho ce("kernel_ n  al zer", [" _un form", "glorot_un form"])
  for    n range(hp. nt("num_layers", 1, 2)):
    model.add(tf.keras.layers.BatchNormal zat on())

    un s=hp. nt("un s", m n_value=128, max_value=256, step=128)
    
     f   == 0:
      model.add(
        Dense(
          un s=un s,
          act vat on=act vat on,
          kernel_ n  al zer=kernel_ n  al zer,
           nput_shape=(None, 256)
        )
      )
    else:
      model.add(
        Dense(
          un s=un s,
          act vat on=act vat on,
          kernel_ n  al zer=kernel_ n  al zer,
        )
      )
    
  model.add(Dense(1, act vat on='s gmo d', kernel_ n  al zer=kernel_ n  al zer))
  model.comp le(opt m zer=opt m zer, loss='b nary_crossentropy',  tr cs= tr cs)

  return model

tuner = kt.tuners.Bayes anOpt m zat on(
  bu ld_model,
  object ve=kt.Object ve('val_loss', d rect on="m n"),
  max_tr als=30,
  d rectory='tuner_d r',
  project_na ='w h_tw ter_cl p')

callbacks = [tf.keras.callbacks.EarlyStopp ng(
    mon or='val_loss', m n_delta=0, pat ence=5, verbose=0,
    mode='auto', basel ne=None, restore_best_  ghts=True
)]

steps_per_epoch = 400
tuner.search(tra n_ds,
             epochs=100,
             batch_s ze=256,
             steps_per_epoch=steps_per_epoch,
             verbose=2,
             val dat on_data=eval_ds,
             callbacks=callbacks)

tuner.results_summary()
models = tuner.get_best_models(num_models=2)
best_model = models[0]

best_model.bu ld( nput_shape=(None, 256))
best_model.summary()

tuner.get_best_hyperpara ters()[0].values

opt m zer = tf.keras.opt m zers.Adam(
    learn ng_rate=0.001,
    beta_1=0.9,
    beta_2=0.999,
    eps lon=1e-08,
    amsgrad=False,
    na ="Adam",
  )
best_model.comp le(opt m zer=opt m zer, loss='b nary_crossentropy',  tr cs= tr cs)
best_model.summary()

callbacks = [tf.keras.callbacks.EarlyStopp ng(
    mon or='val_loss', m n_delta=0, pat ence=10, verbose=0,
    mode='auto', basel ne=None, restore_best_  ghts=True
)]
 tory = best_model.f (tra n_ds, epochs=100, val dat on_data=eval_ds, steps_per_epoch=steps_per_epoch, callbacks=callbacks)

model_na  = 'tw ter_hypertuned'
model_path = f'models/nsfw_Keras_w h_CL P_{model_na }'
tf.keras.models.save_model(best_model, model_path)

def copy_local_d rectory_to_gcs(local_path, bucket, gcs_path):
    """Recurs vely copy a d rectory of f les to GCS.

    local_path should be a d rectory and not have a tra l ng slash.
    """
    assert os.path. sd r(local_path)
    for local_f le  n glob.glob(local_path + '/**'):
         f not os.path. sf le(local_f le):
            d r_na  = os.path.basena (os.path.normpath(local_f le))
            copy_local_d rectory_to_gcs(local_f le, bucket, f"{gcs_path}/{d r_na }")
        else:
          remote_path = os.path.jo n(gcs_path, local_f le[1 + len(local_path) :])
          blob = bucket.blob(remote_path)
          blob.upload_from_f lena (local_f le)

cl ent = storage.Cl ent(project=...)
bucket = cl ent.get_bucket(...)
copy_local_d rectory_to_gcs(model_path, bucket, model_path)
copy_local_d rectory_to_gcs('tuner_d r', bucket, 'tuner_d r')
loaded_model = tf.keras.models.load_model(model_path)
pr nt( tory. tory.keys())

plt.f gure(f gs ze = (20, 5))

plt.subplot(1, 3, 1)
plt.plot( tory. tory['auc'])
plt.plot( tory. tory['val_auc'])
plt.t le('model auc')
plt.ylabel('auc')
plt.xlabel('epoch')
plt.legend(['tra n', 'test'], loc='upper left')

plt.subplot(1, 3, 2)
plt.plot( tory. tory['loss'])
plt.plot( tory. tory['val_loss'])
plt.t le('model loss')
plt.ylabel('loss')
plt.xlabel('epoch')
plt.legend(['tra n', 'test'], loc='upper left')

plt.subplot(1, 3, 3)
plt.plot( tory. tory['prec s on_at_recall'])
plt.plot( tory. tory['val_prec s on_at_recall'])
plt.t le('model prec s on at 0.9 recall')
plt.ylabel('prec s on_at_recall')
plt.xlabel('epoch')
plt.legend(['tra n', 'test'], loc='upper left')

plt.savef g(' tory_w h_tw ter_cl p.pdf')

test_labels = []
test_preds = []

for batch_features, batch_labels  n tqdm(test_ds):
  test_preds.extend(loaded_model.pred ct_proba(batch_features))
  test_labels.extend(batch_labels.numpy())
  
test_sens_prev_labels = []
test_sens_prev_preds = []

for batch_features, batch_labels  n tqdm(test_sens_prev_ds):
  test_sens_prev_preds.extend(loaded_model.pred ct_proba(batch_features))
  test_sens_prev_labels.extend(batch_labels.numpy())
  
n_test_pos = 0
n_test_neg = 0
n_test = 0

for label  n test_labels:
  n_test +=1
   f label == 1:
    n_test_pos +=1
  else:
    n_test_neg +=1

pr nt(f'n_test = {n_test}, n_pos = {n_test_pos}, n_neg = {n_test_neg}')

n_test_sens_prev_pos = 0
n_test_sens_prev_neg = 0
n_test_sens_prev = 0

for label  n test_sens_prev_labels:
  n_test_sens_prev +=1
   f label == 1:
    n_test_sens_prev_pos +=1
  else:
    n_test_sens_prev_neg +=1

pr nt(f'n_test_sens_prev = {n_test_sens_prev}, n_pos_sens_prev = {n_test_sens_prev_pos}, n_neg = {n_test_sens_prev_neg}')

test_  ghts = np.ones(np.asarray(test_preds).shape)

test_labels = np.asarray(test_labels)
test_preds = np.asarray(test_preds)
test_  ghts = np.asarray(test_  ghts)

pr = sklearn. tr cs.prec s on_recall_curve(
  test_labels, 
  test_preds)

auc = sklearn. tr cs.auc(pr[1], pr[0])
plt.plot(pr[1], pr[0])
plt.t le("nsfw (MU test set)")

test_sens_prev_  ghts = np.ones(np.asarray(test_sens_prev_preds).shape)

test_sens_prev_labels = np.asarray(test_sens_prev_labels)
test_sens_prev_preds = np.asarray(test_sens_prev_preds)
test_sens_prev_  ghts = np.asarray(test_sens_prev_  ghts)

pr_sens_prev = sklearn. tr cs.prec s on_recall_curve(
  test_sens_prev_labels, 
  test_sens_prev_preds)

auc_sens_prev = sklearn. tr cs.auc(pr_sens_prev[1], pr_sens_prev[0])
plt.plot(pr_sens_prev[1], pr_sens_prev[0])
plt.t le("nsfw (sens prev test set)")

df = pd.DataFra (
  {
    "label": test_labels.squeeze(), 
    "preds_keras": np.asarray(test_preds).flatten(),
  })
plt.f gure(f gs ze=(15, 10))
df["preds_keras"]. t()
plt.t le("Keras pred ct ons", s ze=20)
plt.xlabel('score')
plt.ylabel("freq")

plt.f gure(f gs ze = (20, 5))
plt.subplot(1, 3, 1)

plt.plot(pr[2], pr[0][0:-1])
plt.xlabel("threshold")
plt.ylabel("prec s on")

plt.subplot(1, 3, 2)

plt.plot(pr[2], pr[1][0:-1])
plt.xlabel("threshold")
plt.ylabel("recall")
plt.t le("Keras", s ze=20)

plt.subplot(1, 3, 3)

plt.plot(pr[1], pr[0])
plt.xlabel("recall")
plt.ylabel("prec s on")

plt.savef g('w h_tw ter_cl p.pdf')

def get_po nt_for_recall(recall_value, recall, prec s on):
   dx = np.argm n(np.abs(recall - recall_value))
  return (recall[ dx], prec s on[ dx])

def get_po nt_for_prec s on(prec s on_value, recall, prec s on):
   dx = np.argm n(np.abs(prec s on - prec s on_value))
  return (recall[ dx], prec s on[ dx])
prec s on, recall, thresholds = pr

auc_prec s on_recall = sklearn. tr cs.auc(recall, prec s on)

pr nt(auc_prec s on_recall)

plt.f gure(f gs ze=(15, 10))
plt.plot(recall, prec s on)

plt.xlabel("recall")
plt.ylabel("prec s on")

ptAt50 = get_po nt_for_recall(0.5, recall, prec s on)
pr nt(ptAt50)
plt.plot( [ptAt50[0],ptAt50[0]], [0,ptAt50[1]], 'r')
plt.plot([0, ptAt50[0]], [ptAt50[1], ptAt50[1]], 'r')

ptAt90 = get_po nt_for_recall(0.9, recall, prec s on)
pr nt(ptAt90)
plt.plot( [ptAt90[0],ptAt90[0]], [0,ptAt90[1]], 'b')
plt.plot([0, ptAt90[0]], [ptAt90[1], ptAt90[1]], 'b')

ptAt50fmt = "%.4f" % ptAt50[1]
ptAt90fmt = "%.4f" % ptAt90[1]
aucFmt = "%.4f" % auc_prec s on_recall
plt.t le(
  f"Keras (nsfw MU test)\nAUC={aucFmt}\np={ptAt50fmt} @ r=0.5\np={ptAt90fmt} @ r=0.9\nN_tra n={...}} ({...} pos), N_test={n_test} ({n_test_pos} pos)",
  s ze=20
)
plt.subplots_adjust(top=0.72)
plt.savef g('recall_prec s on_nsfw_Keras_w h_tw ter_CL P_MU_test.pdf')

prec s on, recall, thresholds = pr_sens_prev

auc_prec s on_recall = sklearn. tr cs.auc(recall, prec s on)
pr nt(auc_prec s on_recall)
plt.f gure(f gs ze=(15, 10))

plt.plot(recall, prec s on)

plt.xlabel("recall")
plt.ylabel("prec s on")

ptAt50 = get_po nt_for_recall(0.5, recall, prec s on)
pr nt(ptAt50)
plt.plot( [ptAt50[0],ptAt50[0]], [0,ptAt50[1]], 'r')
plt.plot([0, ptAt50[0]], [ptAt50[1], ptAt50[1]], 'r')

ptAt90 = get_po nt_for_recall(0.9, recall, prec s on)
pr nt(ptAt90)
plt.plot( [ptAt90[0],ptAt90[0]], [0,ptAt90[1]], 'b')
plt.plot([0, ptAt90[0]], [ptAt90[1], ptAt90[1]], 'b')

ptAt50fmt = "%.4f" % ptAt50[1]
ptAt90fmt = "%.4f" % ptAt90[1]
aucFmt = "%.4f" % auc_prec s on_recall
plt.t le(
  f"Keras (nsfw sens prev test)\nAUC={aucFmt}\np={ptAt50fmt} @ r=0.5\np={ptAt90fmt} @ r=0.9\nN_tra n={...} ({...} pos), N_test={n_test_sens_prev} ({n_test_sens_prev_pos} pos)",
  s ze=20
)
plt.subplots_adjust(top=0.72)
plt.savef g('recall_prec s on_nsfw_Keras_w h_tw ter_CL P_sens_prev_test.pdf')