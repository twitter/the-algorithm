from toxicity_ml_pipeline.load_model import reload_model_weights
from toxicity_ml_pipeline.utils.helpers import load_inference_func, upload_model

import numpy as np
import tensorflow as tf


def score(language, df, gcs_model_path, batch_size=64, text_col="text", kw="", **kwargs):
  if language != "en":
    raise NotImplementedError(
      "Data preprocessing not implemented here, needs to be added for i18n models"
    )
  model_folder = upload_model(full_gcs_model_path=gcs_model_path)
  try:
    inference_func = load_inference_func(model_folder)
  except OSError:
    model = reload_model_weights(model_folder, language, **kwargs)
    preds = model.predict(x=df[text_col], batch_size=batch_size)
    if type(preds) != list:
      if len(preds.shape)> 1 and preds.shape[1] > 1:
        if 'num_classes' in kwargs and kwargs['num_classes'] > 1:
          raise NotImplementedError
        preds = np.mean(preds, 1)

      df[f"prediction_{kw}"] = preds
    else:
      if len(preds) > 2:
        raise NotImplementedError
      for preds_arr in preds:
        if preds_arr.shape[1] == 1:
          df[f"prediction_{kw}_target"] = preds_arr
        else:
          for ind in range(preds_arr.shape[1]):
            df[f"prediction_{kw}_content_{ind}"] = preds_arr[:, ind]

    return df
  else:
    return _get_score(inference_func, df, kw=kw, batch_size=batch_size, text_col=text_col)


def _get_score(inference_func, df, text_col="text", kw="", batch_size=64):
  score_col = f"prediction_{kw}"
  beginning = 0
  end = df.shape[0]
  predictions = np.zeros(shape=end, dtype=float)

  while beginning < end:
    mb = df[text_col].values[beginning : beginning + batch_size]
    res = inference_func(input_1=tf.constant(mb))
    predictions[beginning : beginning + batch_size] = list(res.values())[0].numpy()[:, 0]
    beginning += batch_size

  df[score_col] = predictions
  return df
