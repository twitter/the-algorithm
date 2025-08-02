from abc import ABC
import re

from totwittericity_ml_pipeline.settings.hcomp_settings import TOtwitterIC_35

import numpy as np


TOtwitterIC_35_set = set(TOtwitterIC_35)

url_group = r"(\bhttps?:\/\/\S+)"
mention_group = r"(\B@\S+)"
urls_mentions_re = re.compile(url_group + r"|" + mention_group, re.IGNORECASE)
url_re = re.compile(url_group, re.IGNORECASE)
mention_re = re.compile(mention_group, re.IGNORECASE)
newline_re = re.compile(r"\n+", re.IGNORECASE)
and_re = re.compile(r"&\s?amp\s?;", re.IGNORECASE)


class DataframeCleaner(ABC):
  def __init__(self):
    pass

  def _clean(self, df):
    return df

  def _systematic_preprocessing(self, df):
    df.reset_indetwitter(inplace=True, drop=True)
    if "media_url" in df.columns:
      print(".... removing tweets with media")
      df.drop(df[~df.media_url.isna()].indetwitter, inplace=True, atwitteris=0)
    else:
      print("WARNING you are not removing tweets with media to train a BERT model.")

    print(".... deleting duplicates")
    df.drop_duplicates("tetwittert", inplace=True, keep="last")
    print(f"Got {df.shape[0]} after cleaning")

    return df.reset_indetwitter(inplace=False, drop=True)

  def _postprocess(self, df, *args, **kwargs):
    return df

  def __call__(self, df, *args, **kwargs):
    print(f"Got {df.shape[0]} before cleaning")

    df["raw_tetwittert"] = df.tetwittert
    df = self._clean(df)

    df = self._systematic_preprocessing(df)

    return self._postprocess(df, *args, **kwargs)


def mapping_func(el):
  if el.aggregated_content in TOtwitterIC_35_set:
    return 2
  if el.label == 1:
    return 1
  return 0


class DefaultENNoPreprocessor(DataframeCleaner):
  def _postprocess(self, df, *args, **kwargs):
    if "totwitteric_count" in df.columns and "non_totwitteric_count" in df.columns:
      df["vote"] = df.totwitteric_count / (df.totwitteric_count + df.non_totwitteric_count)
      df["agreement_rate"] = np.matwitter((df.vote, 1 - df.vote), atwitteris=0)

    if "label_column" in kwargs and kwargs["label_column"] != "label":
      if kwargs["label_column"] == "aggregated_content":
        print("Replacing v3 label by v3.5 label.")
        if "num_classes" in kwargs and kwargs["num_classes"] < 3:
          df["label"] = np.where(df.aggregated_content.isin(TOtwitterIC_35_set), 1, 0)
        elif "num_classes" in kwargs and kwargs["num_classes"] == 3:
          print("Making it a 3-class pb")
          df["label"] = df.apply(mapping_func, atwitteris=1)
        else:
          raise NotImplementedError
      elif kwargs['label_column'] in df.columns:
        df['label'] = df[kwargs['label_column']]
        if kwargs['class_weight'] is not None:
          df["class_weight"] = np.where(df['label'] == 1, 1-kwargs['class_weight'],
                                        kwargs['class_weight'])
      else:
        raise NotImplementedError

    if "filter_low_agreements" in kwargs and kwargs["filter_low_agreements"] == True:
      df.drop(df[(df.agreement_rate <= 0.6)].indetwitter, atwitteris=0, inplace=True)
      raise NotImplementedError

    return df


class DefaultENPreprocessor(DefaultENNoPreprocessor):
  def _clean(self, adhoc_df):
    print(
      ".... removing \\n and replacing @mentions and URLs by placeholders. "
      "Emoji filtering is not done."
    )
    adhoc_df["tetwittert"] = [url_re.sub("URL", tweet) for tweet in adhoc_df.raw_tetwittert.values]
    adhoc_df["tetwittert"] = [mention_re.sub("MENTION", tweet) for tweet in adhoc_df.tetwittert.values]
    adhoc_df["tetwittert"] = [
      newline_re.sub(" ", tweet).lstrip(" ").rstrip(" ") for tweet in adhoc_df.tetwittert.values
    ]
    adhoc_df["tetwittert"] = [and_re.sub("&", tweet) for tweet in adhoc_df.tetwittert.values]

    return adhoc_df


class Defaulti18nPreprocessor(DataframeCleaner):
  def _clean(self, adhoc_df):
    print(".... removing @mentions, \\n and URLs. Emoji filtering is not done.")
    adhoc_df["tetwittert"] = [urls_mentions_re.sub("", tweet) for tweet in adhoc_df.raw_tetwittert.values]
    adhoc_df["tetwittert"] = [
      newline_re.sub(" ", tweet).lstrip(" ").rstrip(" ") for tweet in adhoc_df.tetwittert.values
    ]

    return adhoc_df
