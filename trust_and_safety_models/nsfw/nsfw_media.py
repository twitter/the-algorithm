_S='threshold'
_R='preds_keras'
_Q='precision_at_recall'
_P='binary_crossentropy'
_O='upper left'
_N='test'
_M='train'
_L='epoch'
_K='tuner_dir'
_J='labels'
_I='embedding'
_H='recall'
_G='precision'
_F='val_loss'
_E=True
_D=False
_C='GPU'
_B='%.4f'
_A=None
import kerastuner as kt,math,numpy as np,pandas as pd,random,sklearn.metrics,tensorflow as tf,os,glob
from tqdm import tqdm
from matplotlib import pyplot as plt
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from google.cloud import storage
physical_devices=tf.config.list_physical_devices(_C)
physical_devices
tf.config.set_visible_devices([tf.config.PhysicalDevice(name='/physical_device:GPU:1',device_type=_C)],_C)
tf.config.get_visible_devices(_C)
def decode_fn_embedding(example_proto):A={_I:tf.io.FixedLenFeature([256],dtype=tf.float32),_J:tf.io.FixedLenFeature([],dtype=tf.int64)};B=tf.io.parse_single_example(example_proto,A);return B
def preprocess_embedding_example(example_dict,positive_label=1,features_as_dict=_D):
	B=example_dict;E=B[_J];A=tf.math.reduce_any(E==positive_label);A=tf.cast(A,tf.int32);C=B[_I]
	if features_as_dict:D={_I:C}
	else:D=C
	return D,A
input_root=...
sens_prev_input_root=...
use_sens_prev_data=_E
has_validation_data=_E
positive_label=1
train_batch_size=256
test_batch_size=256
validation_batch_size=256
do_resample=_D
def class_func(features,label):return label
resample_fn=tf.data.experimental.rejection_resample(class_func,target_dist=[0.5,0.5],seed=0)
train_glob=f"{input_root}/train/tfrecord/*.tfrecord"
train_files=tf.io.gfile.glob(train_glob)
if use_sens_prev_data:train_sens_prev_glob=f"{sens_prev_input_root}/train/tfrecord/*.tfrecord";train_sens_prev_files=tf.io.gfile.glob(train_sens_prev_glob);train_files=train_files+train_sens_prev_files
random.shuffle(train_files)
if not len(train_files):raise ValueError(f"Did not find any train files matching {train_glob}")
test_glob=f"{input_root}/test/tfrecord/*.tfrecord"
test_files=tf.io.gfile.glob(test_glob)
if not len(test_files):raise ValueError(f"Did not find any eval files matching {test_glob}")
test_ds=tf.data.TFRecordDataset(test_files).map(decode_fn_embedding)
test_ds=test_ds.map(lambda x:preprocess_embedding_example(x,positive_label=positive_label)).batch(batch_size=test_batch_size)
if use_sens_prev_data:
	test_sens_prev_glob=f"{sens_prev_input_root}/test/tfrecord/*.tfrecord";test_sens_prev_files=tf.io.gfile.glob(test_sens_prev_glob)
	if not len(test_sens_prev_files):raise ValueError(f"Did not find any eval files matching {test_sens_prev_glob}")
	test_sens_prev_ds=tf.data.TFRecordDataset(test_sens_prev_files).map(decode_fn_embedding);test_sens_prev_ds=test_sens_prev_ds.map(lambda x:preprocess_embedding_example(x,positive_label=positive_label)).batch(batch_size=test_batch_size)
train_ds=tf.data.TFRecordDataset(train_files).map(decode_fn_embedding)
train_ds=train_ds.map(lambda x:preprocess_embedding_example(x,positive_label=positive_label))
if do_resample:train_ds=train_ds.apply(resample_fn).map(lambda _,b:b)
train_ds=train_ds.batch(batch_size=256).shuffle(buffer_size=10)
train_ds=train_ds.repeat()
if has_validation_data:
	eval_glob=f"{input_root}/validation/tfrecord/*.tfrecord";eval_files=tf.io.gfile.glob(eval_glob)
	if use_sens_prev_data:eval_sens_prev_glob=f"{sens_prev_input_root}/validation/tfrecord/*.tfrecord";eval_sens_prev_files=tf.io.gfile.glob(eval_sens_prev_glob);eval_files=eval_files+eval_sens_prev_files
	if not len(eval_files):raise ValueError(f"Did not find any eval files matching {eval_glob}")
	eval_ds=tf.data.TFRecordDataset(eval_files).map(decode_fn_embedding);eval_ds=eval_ds.map(lambda x:preprocess_embedding_example(x,positive_label=positive_label)).batch(batch_size=validation_batch_size)
else:eval_ds=tf.data.TFRecordDataset(test_files).map(decode_fn_embedding);eval_ds=eval_ds.map(lambda x:preprocess_embedding_example(x,positive_label=positive_label)).batch(batch_size=validation_batch_size)
check_ds=tf.data.TFRecordDataset(train_files).map(decode_fn_embedding)
cnt=0
pos_cnt=0
for example in tqdm(check_ds):
	label=example[_J]
	if label==1:pos_cnt+=1
	cnt+=1
print(f"{cnt} train entries with {pos_cnt} positive")
metrics=[]
metrics.append(tf.keras.metrics.PrecisionAtRecall(recall=0.9,num_thresholds=200,class_id=_A,name=_A,dtype=_A))
metrics.append(tf.keras.metrics.AUC(num_thresholds=200,curve='PR'))
def build_model(hp):
	A=Sequential();E=tf.keras.optimizers.Adam(learning_rate=0.001,beta_1=0.9,beta_2=0.999,epsilon=1e-08,amsgrad=_D,name='Adam');C=hp.Choice('activation',['tanh','gelu']);B=hp.Choice('kernel_initializer',['he_uniform','glorot_uniform'])
	for F in range(hp.Int('num_layers',1,2)):
		A.add(tf.keras.layers.BatchNormalization());D=hp.Int('units',min_value=128,max_value=256,step=128)
		if F==0:A.add(Dense(units=D,activation=C,kernel_initializer=B,input_shape=(_A,256)))
		else:A.add(Dense(units=D,activation=C,kernel_initializer=B))
	A.add(Dense(1,activation='sigmoid',kernel_initializer=B));A.compile(optimizer=E,loss=_P,metrics=metrics);return A
tuner=kt.tuners.BayesianOptimization(build_model,objective=kt.Objective(_F,direction='min'),max_trials=30,directory=_K,project_name='with_twitter_clip')
callbacks=[tf.keras.callbacks.EarlyStopping(monitor=_F,min_delta=0,patience=5,verbose=0,mode='auto',baseline=_A,restore_best_weights=_E)]
steps_per_epoch=400
tuner.search(train_ds,epochs=100,batch_size=256,steps_per_epoch=steps_per_epoch,verbose=2,validation_data=eval_ds,callbacks=callbacks)
tuner.results_summary()
models=tuner.get_best_models(num_models=2)
best_model=models[0]
best_model.build(input_shape=(_A,256))
best_model.summary()
tuner.get_best_hyperparameters()[0].values
optimizer=tf.keras.optimizers.Adam(learning_rate=0.001,beta_1=0.9,beta_2=0.999,epsilon=1e-08,amsgrad=_D,name='Adam')
best_model.compile(optimizer=optimizer,loss=_P,metrics=metrics)
best_model.summary()
callbacks=[tf.keras.callbacks.EarlyStopping(monitor=_F,min_delta=0,patience=10,verbose=0,mode='auto',baseline=_A,restore_best_weights=_E)]
history=best_model.fit(train_ds,epochs=100,validation_data=eval_ds,steps_per_epoch=steps_per_epoch,callbacks=callbacks)
model_name='twitter_hypertuned'
model_path=f"models/nsfw_Keras_with_CLIP_{model_name}"
tf.keras.models.save_model(best_model,model_path)
def copy_local_directory_to_gcs(local_path,bucket,gcs_path):
	'Recursively copy a directory of files to GCS.\n\n    local_path should be a directory and not have a trailing slash.\n    ';C=gcs_path;D=bucket;B=local_path;assert os.path.isdir(B)
	for A in glob.glob(B+'/**'):
		if not os.path.isfile(A):E=os.path.basename(os.path.normpath(A));copy_local_directory_to_gcs(A,D,f"{C}/{E}")
		else:F=os.path.join(C,A[1+len(B):]);G=D.blob(F);G.upload_from_filename(A)
client=storage.Client(project=...)
bucket=client.get_bucket(...)
copy_local_directory_to_gcs(model_path,bucket,model_path)
copy_local_directory_to_gcs(_K,bucket,_K)
loaded_model=tf.keras.models.load_model(model_path)
print(history.history.keys())
plt.figure(figsize=(20,5))
plt.subplot(1,3,1)
plt.plot(history.history['auc'])
plt.plot(history.history['val_auc'])
plt.title('model auc')
plt.ylabel('auc')
plt.xlabel(_L)
plt.legend([_M,_N],loc=_O)
plt.subplot(1,3,2)
plt.plot(history.history['loss'])
plt.plot(history.history[_F])
plt.title('model loss')
plt.ylabel('loss')
plt.xlabel(_L)
plt.legend([_M,_N],loc=_O)
plt.subplot(1,3,3)
plt.plot(history.history[_Q])
plt.plot(history.history['val_precision_at_recall'])
plt.title('model precision at 0.9 recall')
plt.ylabel(_Q)
plt.xlabel(_L)
plt.legend([_M,_N],loc=_O)
plt.savefig('history_with_twitter_clip.pdf')
test_labels=[]
test_preds=[]
for (batch_features,batch_labels) in tqdm(test_ds):test_preds.extend(loaded_model.predict_proba(batch_features));test_labels.extend(batch_labels.numpy())
test_sens_prev_labels=[]
test_sens_prev_preds=[]
for (batch_features,batch_labels) in tqdm(test_sens_prev_ds):test_sens_prev_preds.extend(loaded_model.predict_proba(batch_features));test_sens_prev_labels.extend(batch_labels.numpy())
n_test_pos=0
n_test_neg=0
n_test=0
for label in test_labels:
	n_test+=1
	if label==1:n_test_pos+=1
	else:n_test_neg+=1
print(f"n_test = {n_test}, n_pos = {n_test_pos}, n_neg = {n_test_neg}")
n_test_sens_prev_pos=0
n_test_sens_prev_neg=0
n_test_sens_prev=0
for label in test_sens_prev_labels:
	n_test_sens_prev+=1
	if label==1:n_test_sens_prev_pos+=1
	else:n_test_sens_prev_neg+=1
print(f"n_test_sens_prev = {n_test_sens_prev}, n_pos_sens_prev = {n_test_sens_prev_pos}, n_neg = {n_test_sens_prev_neg}")
test_weights=np.ones(np.asarray(test_preds).shape)
test_labels=np.asarray(test_labels)
test_preds=np.asarray(test_preds)
test_weights=np.asarray(test_weights)
pr=sklearn.metrics.precision_recall_curve(test_labels,test_preds)
auc=sklearn.metrics.auc(pr[1],pr[0])
plt.plot(pr[1],pr[0])
plt.title('nsfw (MU test set)')
test_sens_prev_weights=np.ones(np.asarray(test_sens_prev_preds).shape)
test_sens_prev_labels=np.asarray(test_sens_prev_labels)
test_sens_prev_preds=np.asarray(test_sens_prev_preds)
test_sens_prev_weights=np.asarray(test_sens_prev_weights)
pr_sens_prev=sklearn.metrics.precision_recall_curve(test_sens_prev_labels,test_sens_prev_preds)
auc_sens_prev=sklearn.metrics.auc(pr_sens_prev[1],pr_sens_prev[0])
plt.plot(pr_sens_prev[1],pr_sens_prev[0])
plt.title('nsfw (sens prev test set)')
df=pd.DataFrame({'label':test_labels.squeeze(),_R:np.asarray(test_preds).flatten()})
plt.figure(figsize=(15,10))
df[_R].hist()
plt.title('Keras predictions',size=20)
plt.xlabel('score')
plt.ylabel('freq')
plt.figure(figsize=(20,5))
plt.subplot(1,3,1)
plt.plot(pr[2],pr[0][0:-1])
plt.xlabel(_S)
plt.ylabel(_G)
plt.subplot(1,3,2)
plt.plot(pr[2],pr[1][0:-1])
plt.xlabel(_S)
plt.ylabel(_H)
plt.title('Keras',size=20)
plt.subplot(1,3,3)
plt.plot(pr[1],pr[0])
plt.xlabel(_H)
plt.ylabel(_G)
plt.savefig('with_twitter_clip.pdf')
def get_point_for_recall(recall_value,recall,precision):A=recall;B=np.argmin(np.abs(A-recall_value));return A[B],precision[B]
def get_point_for_precision(precision_value,recall,precision):A=precision;B=np.argmin(np.abs(A-precision_value));return recall[B],A[B]
precision,recall,thresholds=pr
auc_precision_recall=sklearn.metrics.auc(recall,precision)
print(auc_precision_recall)
plt.figure(figsize=(15,10))
plt.plot(recall,precision)
plt.xlabel(_H)
plt.ylabel(_G)
ptAt50=get_point_for_recall(0.5,recall,precision)
print(ptAt50)
plt.plot([ptAt50[0],ptAt50[0]],[0,ptAt50[1]],'r')
plt.plot([0,ptAt50[0]],[ptAt50[1],ptAt50[1]],'r')
ptAt90=get_point_for_recall(0.9,recall,precision)
print(ptAt90)
plt.plot([ptAt90[0],ptAt90[0]],[0,ptAt90[1]],'b')
plt.plot([0,ptAt90[0]],[ptAt90[1],ptAt90[1]],'b')
ptAt50fmt=_B%ptAt50[1]
ptAt90fmt=_B%ptAt90[1]
aucFmt=_B%auc_precision_recall
plt.title(f"Keras (nsfw MU test)\nAUC={aucFmt}\np={ptAt50fmt} @ r=0.5\np={ptAt90fmt} @ r=0.9\nN_train={...} ({...} pos), N_test={n_test} ({n_test_pos} pos)",size=20)
plt.subplots_adjust(top=0.72)
plt.savefig('recall_precision_nsfw_Keras_with_twitter_CLIP_MU_test.pdf')
precision,recall,thresholds=pr_sens_prev
auc_precision_recall=sklearn.metrics.auc(recall,precision)
print(auc_precision_recall)
plt.figure(figsize=(15,10))
plt.plot(recall,precision)
plt.xlabel(_H)
plt.ylabel(_G)
ptAt50=get_point_for_recall(0.5,recall,precision)
print(ptAt50)
plt.plot([ptAt50[0],ptAt50[0]],[0,ptAt50[1]],'r')
plt.plot([0,ptAt50[0]],[ptAt50[1],ptAt50[1]],'r')
ptAt90=get_point_for_recall(0.9,recall,precision)
print(ptAt90)
plt.plot([ptAt90[0],ptAt90[0]],[0,ptAt90[1]],'b')
plt.plot([0,ptAt90[0]],[ptAt90[1],ptAt90[1]],'b')
ptAt50fmt=_B%ptAt50[1]
ptAt90fmt=_B%ptAt90[1]
aucFmt=_B%auc_precision_recall
plt.title(f"Keras (nsfw sens prev test)\nAUC={aucFmt}\np={ptAt50fmt} @ r=0.5\np={ptAt90fmt} @ r=0.9\nN_train={...} ({...} pos), N_test={n_test_sens_prev} ({n_test_sens_prev_pos} pos)",size=20)
plt.subplots_adjust(top=0.72)
plt.savefig('recall_precision_nsfw_Keras_with_twitter_CLIP_sens_prev_test.pdf')