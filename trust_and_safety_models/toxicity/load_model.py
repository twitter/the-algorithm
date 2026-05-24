import os

from toxicity_ml_pipeline.settings.default_settings_tox import LOCAL_DIR, MAX_SEQ_LENGTH
try:
  from toxicity_ml_pipeline.optim.losses import MaskedBCE
except ImportError:
  print('No MaskedBCE loss')
from toxicity_ml_pipeline.utils.helpers import execute_command

import tensorflow as tf


try:
  from twitter.cuad.representation.models.text_encoder import TextEncoder
except ModuleNotFoundError:
  print("No TextEncoder package")

try:
  from transformers import TFAutoModelForSequenceClassification
except ModuleNotFoundError:
  print("No HuggingFace package")

LOCAL_MODEL_DIR = os.path.join(LOCAL_DIR, "models")


def reload_model_weights(weights_dir, language, **kwargs):
  optimizer = tf.keras.optimizers.Adam(0.01)
  model_type = (
    "twitter_bert_base_en_uncased_mlm"
    if language == "en"
    else "twitter_multilingual_bert_base_cased_mlm"
  )
  model = load(optimizer=optimizer, seed=42, model_type=model_type, **kwargs)
  model.load_weights(weights_dir)

  return model


def _locally_copy_models(model_type):
  if model_type == "twitter_multilingual_bert_base_cased_mlm":
    preprocessor = "bert_multi_cased_preprocess_3"
  elif model_type == "twitter_bert_base_en_uncased_mlm":
    preprocessor = "bert_en_uncased_preprocess_3"
  else:
    raise NotImplementedError

  copy_cmd = """mkdir {local_dir}
gsutil cp -r ...
gsutil cp -r ..."""
  execute_command(
    copy_cmd.format(model_type=model_type, preprocessor=preprocessor, local_dir=LOCAL_MODEL_DIR)
  )

  return preprocessor


def load_encoder(model_type, trainable):
  try:
    model = TextEncoder(
      max_seq_lengths=MAX_SEQ_LENGTH,
      model_type=model_type,
      cluster="gcp",
      trainable=trainable,
      enable_dynamic_shapes=True,
    )
  except (OSError, tf.errors.AbortedError) as e:
    print(e)
    preprocessor = _locally_copy_models(model_type)

    model = TextEncoder(
      max_seq_lengths=MAX_SEQ_LENGTH,
      local_model_path=f"models/{model_type}",
      local_preprocessor_path=f"models/{preprocessor}",
      cluster="gcp",
      trainable=trainable,
      enable_dynamic_shapes=True,
    )

  return model


def get_loss(loss_name, from_logits, **kwargs):
  loss_name = loss_name.lower()
  if loss_name == "bce":
    print("Binary CE loss")
    return tf.keras.losses.BinaryCrossentropy(from_logits=from_logits)

  if loss_name == "cce":
    print("Categorical cross-entropy loss")
    return tf.keras.losses.CategoricalCrossentropy(from_logits=from_logits)

  if loss_name == "scce":
    print("Sparse categorical cross-entropy loss")
    return tf.keras.losses.SparseCategoricalCrossentropy(from_logits=from_logits)

  if loss_name == "focal_bce":
    gamma = kwargs.get("gamma", 2)
    print("Focal binary CE loss", gamma)
    return tf.keras.losses.BinaryFocalCrossentropy(gamma=gamma, from_logits=from_logits)

  if loss_name == 'masked_bce':
    multitask = kwargs.get("multitask", False)
    if from_logits or multitask:
      raise NotImplementedError
    print(f'Masked Binary Cross Entropy')
    return MaskedBCE()

  if loss_name == "inv_kl_loss":
    raise NotImplementedError

  raise ValueError(
    f"This loss name is not valid: {loss_name}. Accepted loss names: BCE, masked BCE, CCE, sCCE, "
    f"Focal_BCE, inv_KL_loss"
  )

def _add_additional_embedding_layer(doc_embedding, glorot, seed):
  doc_embedding = tf.keras.layers.Dense(768, activation="tanh", kernel_initializer=glorot)(doc_embedding)
  doc_embedding = tf.keras.layers.Dropout(rate=0.1, seed=seed)(doc_embedding)
  return doc_embedding

def _get_bias(**kwargs):
  smart_bias_value = kwargs.get('smart_bias_value', 0)
  print('Smart bias init to ', smart_bias_value)
  output_bias = tf.keras.initializers.Constant(smart_bias_value)
  return output_bias


def load_inhouse_bert(model_type, trainable, seed, **kwargs):
  inputs = tf.keras.layers.Input(shape=(), dtype=tf.string)
  encoder = load_encoder(model_type=model_type, trainable=trainable)
  doc_embedding = encoder([inputs])["pooled_output"]
  doc_embedding = tf.keras.layers.Dropout(rate=0.1, seed=seed)(doc_embedding)

  glorot = tf.keras.initializers.glorot_uniform(seed=seed)
  if kwargs.get("additional_layer", False):
    doc_embedding = _add_additional_embedding_layer(doc_embedding, glorot, seed)

  if kwargs.get('content_num_classes', None):
    probs = get_last_layer(glorot=glorot, last_layer_name='target_output', **kwargs)(doc_embedding)
    second_probs = get_last_layer(num_classes=kwargs['content_num_classes'],
                                  last_layer_name='content_output',
                                  glorot=glorot)(doc_embedding)
    probs = [probs, second_probs]
  else:
    probs = get_last_layer(glorot=glorot, **kwargs)(doc_embedding)
  model = tf.keras.models.Model(inputs=inputs, outputs=probs)

  return model, False

def get_last_layer(**kwargs):
  output_bias = _get_bias(**kwargs)
  if 'glorot' in kwargs:
    glorot = kwargs['glorot']
  else:
    glorot = tf.keras.initializers.glorot_uniform(seed=kwargs['seed'])
  layer_name = kwargs.get('last_layer_name', 'dense_1')

  if kwargs.get('num_classes', 1) > 1:
    last_layer = tf.keras.layers.Dense(
      kwargs["num_classes"], activation="softmax", kernel_initializer=glorot,
      bias_initializer=output_bias, name=layer_name
    )

  elif kwargs.get('num_raters', 1) > 1:
    if kwargs.get('multitask', False):
      raise NotImplementedError
    last_layer = tf.keras.layers.Dense(
      kwargs['num_raters'], activation="sigmoid", kernel_initializer=glorot,
      bias_initializer=output_bias, name='probs')

  else:
    last_layer = tf.keras.layers.Dense(
      1, activation="sigmoid", kernel_initializer=glorot,
      bias_initializer=output_bias, name=layer_name
    )

  return last_layer

def load_bertweet(**kwargs):
  bert = TFAutoModelForSequenceClassification.from_pretrained(
    os.path.join(LOCAL_MODEL_DIR, "bertweet-base"),
    num_labels=1,
    classifier_dropout=0.1,
    hidden_size=768,
  )
  if "num_classes" in kwargs and kwargs["num_classes"] > 2:
    raise NotImplementedError

  return bert, True


def load(
  optimizer,
  seed,
  model_type="twitter_multilingual_bert_base_cased_mlm",
  loss_name="BCE",
  trainable=True,
  **kwargs,
):
  if model_type == "bertweet-base":
    model, from_logits = load_bertweet()
  else:
    model, from_logits = load_inhouse_bert(model_type, trainable, seed, **kwargs)

  pr_auc = tf.keras.metrics.AUC(curve="PR", name="pr_auc", from_logits=from_logits)
  roc_auc = tf.keras.metrics.AUC(curve="ROC", name="roc_auc", from_logits=from_logits)

  loss = get_loss(loss_name, from_logits, **kwargs)
  if kwargs.get('content_num_classes', None):
    second_loss = get_loss(loss_name=kwargs['content_loss_name'], from_logits=from_logits)
    loss_weights = {'content_output': kwargs['content_loss_weight'], 'target_output': 1}
    model.compile(
      optimizer=optimizer,
      loss={'content_output': second_loss, 'target_output': loss},
      loss_weights=loss_weights,
      metrics=[pr_auc, roc_auc],
    )

  else:
    model.compile(
      optimizer=optimizer,
      loss=loss,
      metrics=[pr_auc, roc_auc],
    )
  print(model.summary(), "logits: ", from_logits)

  return model