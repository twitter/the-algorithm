_M='mixed_precision'
_L='model_type'
_K='cls_dropout_rate'
_J='warmup_steps'
_I='optimizer_type'
_H='batch_size'
_G='max_seq_lengths'
_F='steps_per_epoch'
_E='epochs'
_D='...'
_C='precision_nsfw'
_B='has_media'
_A=True
import tensorflow as tf
physical_devices=tf.config.list_physical_devices('GPU')
for device in physical_devices:tf.config.experimental.set_memory_growth(device,_A)
from twitter.hmli.nimbus.modeling.model_config import FeatureType,EncodingType,Feature,Model,LogType
from twitter.hmli.nimbus.modeling.feature_loader import BigQueryFeatureLoader
from twitter.cuad.representation.models.text_encoder import TextEncoder
from twitter.cuad.representation.models.optimization import create_optimizer
from twitter.hmli.nimbus.modeling.feature_encoder import FeatureEncoder
import numpy as np,pandas as pd,utils
cat_names=[...]
category_features=[Feature(name=A,ftype=FeatureType.CONTINUOUS)for A in cat_names]
features=[Feature(name='tweet_text_with_media_annotations',ftype=FeatureType.STRING,encoding=EncodingType.BERT),Feature(name=_C,ftype=FeatureType.CONTINUOUS),Feature(name=_B,ftype=FeatureType.BINARY),Feature(name='num_media',ftype=FeatureType.DISCRETE)]+category_features
ptos_prototype=Model(name='ptos_prototype',export_path=_D,features=features)
print(ptos_prototype)
cq_loader=BigQueryFeatureLoader(gcp_project=COMPUTE_PROJECT)
labels=['has_non_punitive_action','has_punitive_action','has_punitive_action_contains_self_harm','has_punitive_action_encourage_self_harm','has_punitive_action_episodic','has_punitive_action_episodic_hateful_conduct','has_punitive_action_other_abuse_policy','has_punitive_action_without_self_harm']
train_query=f"""
SELECT 
  {{feature_names}},
  {",".join(labels)},
...
"""
val_query=f"""
SELECT 
  {{feature_names}},
  {",".join(labels)},
...
"""
print(train_query)
train=cq_loader.load_features(ptos_prototype,'','',custom_query=train_query)
val=cq_loader.load_features(ptos_prototype,'','',custom_query=val_query)
print(train.describe(model=ptos_prototype))
params={_G:128,_H:196,'lr':1e-05,_I:'adamw',_J:0,_K:0.1,_E:30,_F:5000,_L:'twitter_multilingual_bert_base_cased_mlm',_M:_A}
params
def parse_labeled_data(row_dict):A=row_dict;B=[A.pop(B)for B in labels];return A,B
mirrored_strategy=tf.distribute.MirroredStrategy()
BATCH_SIZE=params[_H]*mirrored_strategy.num_replicas_in_sync
train_ds=train.to_tf_dataset().map(parse_labeled_data).shuffle(BATCH_SIZE*100).batch(BATCH_SIZE).repeat()
val_ds=val.to_tf_dataset().map(parse_labeled_data).batch(BATCH_SIZE)
for record in train_ds:tf.print(record);break
def get_positive_weights():'Computes positive weights used for class imbalance from training data.';A=utils.get_label_weights('tos-data-media-full',project_id='twttr-abusive-interact-prod',dataset_id='tos_policy');B=tf.cast(A.sort_values(by='label').positive_class_weight,dtype=tf.float32);return B
pos_weight_tensor=get_positive_weights()
print(pos_weight_tensor)
class TextEncoderPooledOutput(TextEncoder):
	def call(A,x):return super().call([x])['pooled_output']
	def get_config(A):return super().get_config()
with mirrored_strategy.scope():
	text_encoder_pooled_output=TextEncoderPooledOutput(params[_G],model_type=params[_L],trainable=_A);fe=FeatureEncoder(train);inputs,preprocessing_head=fe.build_model_head(model=ptos_prototype,text_encoder=text_encoder_pooled_output);cls_dropout=tf.keras.layers.Dropout(params[_K],name='cls_dropout');outputs=cls_dropout(preprocessing_head);outputs=tf.keras.layers.Dense(8,name='output',dtype='float32')(outputs);model=tf.keras.Model(inputs=inputs,outputs=outputs);pr_auc=tf.keras.metrics.AUC(curve='PR',num_thresholds=1000,multi_label=_A,from_logits=_A);custom_loss=lambda y_true,y_pred:utils.multilabel_weighted_loss(y_true,y_pred,weights=pos_weight_tensor);optimizer=create_optimizer(init_lr=params['lr'],num_train_steps=params[_E]*params[_F],num_warmup_steps=params[_J],optimizer_type=params[_I])
	if params.get(_M):optimizer=tf.train.experimental.enable_mixed_precision_graph_rewrite(optimizer)
	model.compile(optimizer=optimizer,loss=custom_loss,metrics=[pr_auc])
model.weights
model.summary()
pr_auc.name
import getpass,wandb
from wandb.keras import WandbCallback
try:wandb_key=...;wandb.login(...);run=wandb.init(project='ptos_with_media',group='new-split-trains',notes='tweet text with only (num_media, precision_nsfw). on full train set, new split.',entity='absv',config=params,name='tweet-text-w-nsfw-1.1',sync_tensorboard=_A)
except FileNotFoundError:print('Wandb key not found');run=wandb.init(mode='disabled')
import datetime,os
start_train_time=datetime.datetime.now()
print(start_train_time.strftime('%m-%d-%Y (%H:%M:%S)'))
checkpoint_path=os.path.join(_D)
print('Saving model checkpoints here: ',checkpoint_path)
cp_callback=tf.keras.callbacks.ModelCheckpoint(filepath=os.path.join(checkpoint_path,'model.{epoch:04d}.tf'),verbose=1,monitor=f"val_{pr_auc.name}",mode='max',save_freq='epoch',save_best_only=_A)
early_stopping_callback=tf.keras.callbacks.EarlyStopping(patience=7,monitor=f"val_{pr_auc.name}",mode='max')
model.fit(train_ds,epochs=params[_E],validation_data=val_ds,callbacks=[cp_callback,early_stopping_callback],steps_per_epoch=params[_F],verbose=2)
import tensorflow_hub as hub
gs_model_path=...
reloaded_keras_layer=hub.KerasLayer(gs_model_path)
inputs=tf.keras.layers.Input(name='tweet__core__tweet__text',shape=(1,),dtype=tf.string)
output=reloaded_keras_layer(inputs)
v7_model=tf.keras.models.Model(inputs=inputs,outputs=output)
pr_auc=tf.keras.metrics.AUC(curve='PR',name='pr_auc')
roc_auc=tf.keras.metrics.AUC(curve='ROC',name='roc_auc')
v7_model.compile(metrics=[pr_auc,roc_auc])
model.load_weights(_D)
candidate_model=model
with mirrored_strategy.scope():candidate_eval=candidate_model.evaluate(val_ds)
test_query=f"""
SELECT 
  {",".join(ptos_prototype.feature_names())},
  has_media,
  precision_nsfw,
  {",".join(labels)},
...
"""
test=cq_loader.load_features(ptos_prototype,'','',custom_query=test_query)
test=test.to_tf_dataset().map(parse_labeled_data)
print(test)
test_only_media=test.filter(lambda x,y:tf.equal(x[_B],_A))
test_only_nsfw=test.filter(lambda x,y:tf.greater_equal(x[_C],0.95))
test_no_media=test.filter(lambda x,y:tf.equal(x[_B],False))
test_media_not_nsfw=test.filter(lambda x,y:tf.logical_and(tf.equal(x[_B],_A),tf.less(x[_C],0.95)))
for d in [test,test_only_media,test_only_nsfw,test_no_media,test_media_not_nsfw]:print(d.reduce(0,lambda x,_:x+1).numpy())
from notebook_eval_utils import SparseMultilabelEvaluator,EvalConfig
from dataclasses import asdict
def display_metrics(probs,targets,labels=labels):
	B=targets;C=EvalConfig(prediction_threshold=0.5,precision_k=0.9)
	for (D,E) in [('implicit',np.ones(B.shape))]:print('Evaluation mode',D);F=SparseMultilabelEvaluator.evaluate(B,np.array(probs),E,classes=labels,eval_config=C);A=pd.DataFrame.from_dict(asdict(F)['per_topic_metrics']).transpose();A['pos_to_neg']=A['num_pos_samples']/(A['num_neg_samples']+1);display(A.median());display(A);return A
def eval_model(model,df):
	with mirrored_strategy.scope():A=np.stack(list(df.map(lambda x,y:y).as_numpy_iterator()),axis=0);df=df.padded_batch(BATCH_SIZE);B=model.predict(df);return display_metrics(B,A)
subsets={'test':test,'test_only_media':test_only_media,'test_only_nsfw':test_only_nsfw,'test_no_media':test_no_media,'test_media_not_nsfw':test_media_not_nsfw}
metrics={}
for (name,df) in subsets.items():metrics[name]=eval_model(candidate_model,df)
[(A,B.pr_auc)for(A,B)in metrics.items()]
for (name,x) in [(A,B.pr_auc.to_string(index=False).strip().split('\n'))for(A,B)in metrics.items()]:
	print(name)
	for y in x:print(y.strip(),end='\t')
	print('.')
for d in [test,test_only_media,test_only_nsfw,test_no_media,test_media_not_nsfw]:print(d.reduce(0,lambda x,_:x+1).numpy())