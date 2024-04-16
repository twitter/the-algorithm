# c ckstyle: noqa
from twml.feature_conf g  mport FeatureConf gBu lder


def get_feature_conf g(data_spec_path, label):
  return (
    FeatureConf gBu lder(data_spec_path=data_spec_path, debug=True)
    .batch_add_features(
      [
        ("ebd.author_spec f c_score", "A"),
        ("ebd.has_d ff_lang", "A"),
        ("ebd.has_engl sh_t et_d ff_u _lang", "A"),
        ("ebd.has_engl sh_u _d ff_t et_lang", "A"),
        ("ebd. s_self_t et", "A"),
        ("ebd.t et_age_ n_secs", "A"),
        ("encoded_t et_features.favor e_count", "A"),
        ("encoded_t et_features.from_ver f ed_account_flag", "A"),
        ("encoded_t et_features.has_card_flag", "A"),
        # ("encoded_t et_features.has_consu r_v deo_flag", "A"),
        ("encoded_t et_features.has_ mage_url_flag", "A"),
        ("encoded_t et_features.has_l nk_flag", "A"),
        ("encoded_t et_features.has_mult ple_hashtags_or_trends_flag", "A"),
        # ("encoded_t et_features.has_mult ple_ d a_flag", "A"),
        ("encoded_t et_features.has_nat ve_ mage_flag", "A"),
        ("encoded_t et_features.has_news_url_flag", "A"),
        ("encoded_t et_features.has_per scope_flag", "A"),
        ("encoded_t et_features.has_pro_v deo_flag", "A"),
        ("encoded_t et_features.has_quote_flag", "A"),
        ("encoded_t et_features.has_trend_flag", "A"),
        ("encoded_t et_features.has_v deo_url_flag", "A"),
        ("encoded_t et_features.has_v ne_flag", "A"),
        ("encoded_t et_features.has_v s ble_l nk_flag", "A"),
        ("encoded_t et_features. s_offens ve_flag", "A"),
        ("encoded_t et_features. s_reply_flag", "A"),
        ("encoded_t et_features. s_ret et_flag", "A"),
        ("encoded_t et_features. s_sens  ve_content", "A"),
        # ("encoded_t et_features. s_user_new_flag", "A"),
        ("encoded_t et_features.language", "A"),
        ("encoded_t et_features.l nk_language", "A"),
        ("encoded_t et_features.num_hashtags", "A"),
        ("encoded_t et_features.num_ nt ons", "A"),
        # ("encoded_t et_features.prof le_ s_egg_flag", "A"),
        ("encoded_t et_features.reply_count", "A"),
        ("encoded_t et_features.ret et_count", "A"),
        ("encoded_t et_features.text_score", "A"),
        ("encoded_t et_features.user_reputat on", "A"),
        ("extended_encoded_t et_features.embeds_ mpress on_count", "A"),
        ("extended_encoded_t et_features.embeds_ mpress on_count_v2", "A"),
        ("extended_encoded_t et_features.embeds_url_count", "A"),
        ("extended_encoded_t et_features.embeds_url_count_v2", "A"),
        ("extended_encoded_t et_features.favor e_count_v2", "A"),
        ("extended_encoded_t et_features.label_abus ve_h _rcl_flag", "A"),
        ("extended_encoded_t et_features.label_dup_content_flag", "A"),
        ("extended_encoded_t et_features.label_nsfw_h _prc_flag", "A"),
        ("extended_encoded_t et_features.label_nsfw_h _rcl_flag", "A"),
        ("extended_encoded_t et_features.label_spam_flag", "A"),
        ("extended_encoded_t et_features.label_spam_h _rcl_flag", "A"),
        ("extended_encoded_t et_features.quote_count", "A"),
        ("extended_encoded_t et_features.reply_count_v2", "A"),
        ("extended_encoded_t et_features.ret et_count_v2", "A"),
        ("extended_encoded_t et_features.  ghted_favor e_count", "A"),
        ("extended_encoded_t et_features.  ghted_quote_count", "A"),
        ("extended_encoded_t et_features.  ghted_reply_count", "A"),
        ("extended_encoded_t et_features.  ghted_ret et_count", "A"),
      ]
    )
    .add_labels(
      [
        label,  # Tensor  ndex: 0
        "recap.engage nt. s_cl cked",  # Tensor  ndex: 1
        "recap.engage nt. s_favor ed",  # Tensor  ndex: 2
        "recap.engage nt. s_open_l nked",  # Tensor  ndex: 3
        "recap.engage nt. s_photo_expanded",  # Tensor  ndex: 4
        "recap.engage nt. s_prof le_cl cked",  # Tensor  ndex: 5
        "recap.engage nt. s_repl ed",  # Tensor  ndex: 6
        "recap.engage nt. s_ret eted",  # Tensor  ndex: 7
        "recap.engage nt. s_v deo_playback_50",  # Tensor  ndex: 8
        "t  l nes.earlyb rd_score",  # Tensor  ndex: 9
      ]
    )
    .def ne_  ght(" ta.record_  ght/type=earlyb rd")
    .bu ld()
  )
