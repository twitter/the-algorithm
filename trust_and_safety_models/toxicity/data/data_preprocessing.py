import re
from abc import ABC

import numpy as np
import pandas as pd
from toxicity_ml_pipeline.settings.hcomp_settings import TOXIC_35

TOXIC_35_set = set(TOXIC_35)

URL_GROUP = r"(\bhttps?:\/\/\S+)"
MENTION_GROUP = r"(\B@\S+)"
URLS_MENTIONS_RE = re.compile(URL_GROUP + r"|" + MENTION_GROUP, re.IGNORECASE)
URL_RE = re.compile(URL_GROUP, re.IGNORECASE)
MENTION_RE = re.compile(MENTION_GROUP, re.IGNORECASE)
NEWLINE_RE = re.compile(r"\n+", re.IGNORECASE)
AND_RE = re.compile(r"&\s?amp\s?;", re.IGNORECASE)


class DataframeCleaner(ABC):
    def __init__(self):
        pass

    def _clean(self, df: pd.DataFrame) -> pd.DataFrame:
        return df

    def _systematic_preprocessing(self, df: pd.DataFrame) -> pd.DataFrame:
        df.reset_index(inplace=True, drop=True)
        if "media_url" in df.columns:
            print(".... removing tweets with media")
            df.drop(df[~df.media_url.isna()].index, inplace=True, axis=0)
        else:
            print(
                "WARNING you are not removing tweets with media to train a BERT model."
            )

        print(".... deleting duplicates")
        df.drop_duplicates("text", inplace=True, keep="last")
        print(f"Got {df.shape[0]} after cleaning")

        return df.reset_index(inplace=False, drop=True)

    def _postprocess(self, df: pd.DataFrame, *args, **kwargs) -> pd.DataFrame:
        return df

    def __call__(self, df: pd.DataFrame, *args, **kwargs) -> pd.DataFrame:
        print(f"Got {df.shape[0]} before cleaning")
        df["raw_text"] = df.text
        df = self._clean(df)
        df = self._systematic_preprocessing(df)
        return self._postprocess(df, *args, **kwargs)


def mapping_func(el: pd.Series) -> int:
    if el.aggregated_content in TOXIC_35_set:
        return 2
    if el.label == 1:
        return 1
    return 0


class DefaultENNoPreprocessor(DataframeCleaner):
    def _postprocess(self, df: pd.DataFrame, *args, **kwargs) -> pd.DataFrame:
        if "toxic_count" in df.columns and "non_toxic_count" in df.columns:
            df["vote"] = df.toxic_count / (df.toxic_count + df.non_toxic_count)
            df["agreement_rate"] = np.max((df.vote, 1 - df.vote), axis=0)

        if "label_column" in kwargs and kwargs["label_column"] != "label":
            if kwargs["label_column"] == "aggregated_content":
                print("Replacing v3 label by v3.5 label.")
                if "num_classes" in kwargs and kwargs["num_classes"] < 3:
                    df["label"] = np.where(
                        df.aggregated_content.isin(TOXIC_35_set), 1, 0
                    )
                elif "num_classes" in kwargs and kwargs["num_classes"] == 3:
                    print("Making it a 3-class pb")
                    df["label"] = df.apply(mapping_func, axis=1)
                else:
                    raise NotImplementedError
            elif kwargs["label_column"] in df.columns:
                df["label"] = df[kwargs["label_column"]]
                if kwargs["class_weight"] is not None:
                    df["class_weight"] = np.where(
                        df["label"] == 1,
                        1 - kwargs["class_weight"],
                        kwargs["class_weight"],
                    )
            else:
                raise NotImplementedError

        if (
            "filter_low_agreements" in kwargs
            and kwargs["filter_low_agreements"] == True
        ):
            df.drop(df[(df.agreement_rate <= 0.6)].index, axis=0, inplace=True)
            raise NotImplementedError

        return df


class DefaultENPreprocessor(DefaultENNoPreprocessor):
    def _clean(self, adhoc_df: pd.DataFrame) -> pd.DataFrame:
        print(
            "... removing \\n and replacing @mentions and URLs by placeholders. "
            "Emoji filtering is not done."
        )
        adhoc_df["text"] = [
            URL_RE.sub("URL", tweet) for tweet in adhoc_df.raw_text.values
        ]
        adhoc_df["text"] = [
            MENTION_RE.sub("MENTION", tweet) for tweet in adhoc_df.text.values
        ]
        adhoc_df["text"] = [
            NEWLINE_RE.sub(" ", tweet).lstrip(" ").rstrip(" ")
            for tweet in adhoc_df.text.values
        ]
        adhoc_df["text"] = [AND_RE.sub("&", tweet) for tweet in adhoc_df.text.values]
        return adhoc_df


class Defaulti18nPreprocessor(DataframeCleaner):
    def _clean(self, adhoc_df):
        print("... removing @mentions, \\n and URLs. Emoji filtering is not done.")
        adhoc_df["text"] = [
            URLS_MENTIONS_RE.sub("", tweet) for tweet in adhoc_df.raw_text.values
        ]
        adhoc_df["text"] = [
            NEWLINE_RE.sub(" ", tweet).lstrip(" ").rstrip(" ")
            for tweet in adhoc_df.text.values
        ]
        return adhoc_df
