# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20201214013251) do

  create_table "abuse_reports", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "email"
    t.string "url", null: false
    t.text "comment", null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "ip_address"
    t.integer "comment_sanitizer_version", limit: 2, default: 0, null: false
    t.string "summary"
    t.string "summary_sanitizer_version"
    t.string "language"
    t.string "username"
  end

  create_table "admin_activities", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "admin_id"
    t.integer "target_id"
    t.string "target_type"
    t.string "action"
    t.text "summary"
    t.integer "summary_sanitizer_version", limit: 2, default: 0, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "admin_banners", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.text "content"
    t.integer "content_sanitizer_version", limit: 2, default: 0, null: false
    t.string "banner_type"
    t.boolean "active", default: false, null: false
  end

  create_table "admin_blacklisted_emails", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "email"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["email"], name: "index_admin_blacklisted_emails_on_email", unique: true
  end

  create_table "admin_post_taggings", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "admin_post_tag_id"
    t.integer "admin_post_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["admin_post_id"], name: "index_admin_post_taggings_on_admin_post_id"
  end

  create_table "admin_post_tags", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "name"
    t.integer "language_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "admin_posts", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "admin_id"
    t.string "title"
    t.text "content"
    t.datetime "updated_at"
    t.datetime "created_at"
    t.integer "content_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "translated_post_id"
    t.integer "language_id"
    t.integer "comment_permissions", limit: 1, default: 0, null: false
    t.index ["created_at"], name: "index_admin_posts_on_created_at"
    t.index ["translated_post_id"], name: "index_admin_posts_on_post_id"
  end

  create_table "admin_settings", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.boolean "account_creation_enabled", default: true, null: false
    t.boolean "invite_from_queue_enabled", default: true, null: false
    t.bigint "invite_from_queue_number"
    t.integer "invite_from_queue_frequency", limit: 3
    t.integer "days_to_purge_unactivated", limit: 3
    t.bigint "last_updated_by"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.datetime "invite_from_queue_at", default: "2009-11-07 21:27:21"
    t.boolean "suspend_filter_counts", default: false
    t.datetime "suspend_filter_counts_at"
    t.boolean "enable_test_caching", default: false
    t.bigint "cache_expiration", default: 10
    t.boolean "tag_wrangling_off", default: false, null: false
    t.integer "default_skin_id"
    t.boolean "request_invite_enabled", default: false, null: false
    t.boolean "creation_requires_invite", default: false, null: false
    t.boolean "downloads_enabled", default: true
    t.boolean "hide_spam", default: false, null: false
    t.boolean "disable_support_form", default: false, null: false
    t.text "disabled_support_form_text"
    t.integer "disabled_support_form_text_sanitizer_version", limit: 2, default: 0, null: false
    t.index ["last_updated_by"], name: "index_admin_settings_on_last_updated_by"
  end

  create_table "admins", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "email"
    t.string "login"
    t.string "encrypted_password"
    t.string "password_salt"
    t.text "roles"
    t.index ["email"], name: "index_admins_on_email", unique: true
    t.index ["login"], name: "index_admins_on_login", unique: true
  end

  create_table "api_keys", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "name", null: false
    t.string "access_token", null: false
    t.boolean "banned", default: false, null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["access_token"], name: "index_api_keys_on_access_token", unique: true
    t.index ["name"], name: "index_api_keys_on_name", unique: true
  end

  create_table "archive_faq_translations", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "archive_faq_id"
    t.string "locale", null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.string "title"
    t.index ["archive_faq_id"], name: "index_archive_faq_translations_on_archive_faq_id"
    t.index ["locale"], name: "index_archive_faq_translations_on_locale"
  end

  create_table "archive_faqs", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "admin_id"
    t.string "title"
    t.datetime "updated_at"
    t.datetime "created_at"
    t.integer "position", default: 1
    t.string "slug", default: "", null: false
    t.index ["position"], name: "index_archive_faqs_on_position"
    t.index ["slug"], name: "index_archive_faqs_on_slug", unique: true
  end

  create_table "audits", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "auditable_id"
    t.string "auditable_type"
    t.integer "associated_id"
    t.string "associated_type"
    t.integer "user_id"
    t.string "user_type"
    t.string "username"
    t.string "action"
    t.text "audited_changes"
    t.integer "version", default: 0
    t.string "comment"
    t.string "remote_address"
    t.datetime "created_at"
    t.string "request_uuid"
    t.index ["associated_id", "associated_type"], name: "associated_index"
    t.index ["auditable_id", "auditable_type"], name: "auditable_index"
    t.index ["created_at"], name: "index_audits_on_created_at"
    t.index ["request_uuid"], name: "index_audits_on_request_uuid"
    t.index ["user_id", "user_type"], name: "user_index"
  end

  create_table "bookmarks", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.datetime "created_at", null: false
    t.string "bookmarkable_type", limit: 15, null: false
    t.integer "bookmarkable_id", null: false
    t.integer "user_id"
    t.text "bookmarker_notes"
    t.boolean "private", default: false
    t.datetime "updated_at"
    t.boolean "hidden_by_admin", default: false, null: false
    t.integer "pseud_id", null: false
    t.boolean "rec", default: false, null: false
    t.boolean "delta", default: true
    t.integer "bookmarker_notes_sanitizer_version", limit: 2, default: 0, null: false
    t.index ["bookmarkable_id", "bookmarkable_type", "pseud_id"], name: "index_bookmarkable_pseud"
    t.index ["private", "hidden_by_admin", "created_at"], name: "index_bookmarks_on_private_and_hidden_by_admin_and_created_at"
    t.index ["pseud_id"], name: "index_bookmarks_on_pseud_id"
    t.index ["user_id"], name: "fk_bookmarks_user"
  end

  create_table "challenge_assignments", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "collection_id"
    t.integer "creation_id"
    t.string "creation_type"
    t.integer "offer_signup_id"
    t.integer "request_signup_id"
    t.integer "pinch_hitter_id"
    t.datetime "sent_at"
    t.datetime "fulfilled_at"
    t.datetime "defaulted_at"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer "pinch_request_signup_id"
    t.datetime "covered_at"
    t.index ["collection_id"], name: "index_challenge_assignments_on_collection_id"
    t.index ["creation_id"], name: "assignments_on_creation_id"
    t.index ["creation_type"], name: "assignments_on_creation_type"
    t.index ["defaulted_at"], name: "assignments_on_defaulted_at"
    t.index ["offer_signup_id"], name: "assignments_on_offer_signup_id"
    t.index ["pinch_hitter_id"], name: "assignments_on_pinch_hitter_id"
    t.index ["sent_at"], name: "assignments_on_offer_sent_at"
  end

  create_table "challenge_claims", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "collection_id"
    t.integer "creation_id"
    t.string "creation_type"
    t.integer "request_signup_id"
    t.integer "request_prompt_id"
    t.integer "claiming_user_id"
    t.datetime "sent_at"
    t.datetime "fulfilled_at"
    t.datetime "defaulted_at"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["claiming_user_id"], name: "index_challenge_claims_on_claiming_user_id"
    t.index ["collection_id"], name: "index_challenge_claims_on_collection_id"
    t.index ["creation_id", "creation_type"], name: "creations"
    t.index ["request_signup_id"], name: "index_challenge_claims_on_request_signup_id"
  end

  create_table "challenge_signups", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "collection_id"
    t.integer "pseud_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "assigned_as_request", default: false
    t.boolean "assigned_as_offer", default: false
    t.index ["collection_id"], name: "index_challenge_signups_on_collection_id"
    t.index ["pseud_id"], name: "signups_on_pseud_id"
  end

  create_table "chapters", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8", force: :cascade do |t|
    t.text "content", limit: 4294967295, null: false, collation: "utf8mb4_unicode_ci"
    t.integer "position", default: 1
    t.integer "work_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "posted", default: false, null: false
    t.string "title", collation: "utf8mb4_unicode_ci"
    t.text "notes", collation: "utf8mb4_unicode_ci"
    t.text "summary", collation: "utf8mb4_unicode_ci"
    t.integer "word_count"
    t.boolean "hidden_by_admin", default: false, null: false
    t.date "published_at"
    t.text "endnotes", collation: "utf8mb4_unicode_ci"
    t.integer "content_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "notes_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "summary_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "endnotes_sanitizer_version", limit: 2, default: 0, null: false
    t.index ["work_id"], name: "index_chapters_on_work_id"
    t.index ["work_id"], name: "works_chapter_index"
  end

  create_table "collection_items", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "collection_id"
    t.integer "item_id"
    t.string "item_type", default: "Work"
    t.integer "user_approval_status", limit: 1, default: 0, null: false
    t.integer "collection_approval_status", limit: 1, default: 0, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "anonymous", default: false, null: false
    t.boolean "unrevealed", default: false, null: false
    t.index ["anonymous"], name: "collection_items_anonymous"
    t.index ["collection_id", "item_id", "item_type"], name: "by collection and item", unique: true
    t.index ["collection_id", "user_approval_status", "collection_approval_status"], name: "index_collection_items_approval_status"
    t.index ["item_id"], name: "collection_items_item_id"
    t.index ["unrevealed"], name: "collection_items_unrevealed"
  end

  create_table "collection_participants", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "collection_id"
    t.integer "pseud_id"
    t.string "participant_role", default: "None", null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["collection_id", "participant_role"], name: "participants_by_collection_and_role"
    t.index ["collection_id", "pseud_id"], name: "by collection and pseud", unique: true
    t.index ["pseud_id"], name: "participants_pseud_id"
  end

  create_table "collection_preferences", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "collection_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "moderated", default: false, null: false
    t.boolean "closed", default: false, null: false
    t.boolean "unrevealed", default: false, null: false
    t.boolean "anonymous", default: false, null: false
    t.boolean "gift_exchange", default: false, null: false
    t.boolean "show_random", default: false, null: false
    t.boolean "prompt_meme", default: false, null: false
    t.boolean "email_notify", default: false, null: false
    t.index ["collection_id"], name: "index_collection_preferences_on_collection_id"
  end

  create_table "collection_profiles", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "collection_id"
    t.text "intro", limit: 16777215
    t.text "faq", limit: 16777215
    t.text "rules", limit: 16777215
    t.datetime "created_at"
    t.datetime "updated_at"
    t.text "gift_notification"
    t.integer "intro_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "faq_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "rules_sanitizer_version", limit: 2, default: 0, null: false
    t.text "assignment_notification"
    t.index ["collection_id"], name: "index_collection_profiles_on_collection_id"
  end

  create_table "collections", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "name"
    t.string "title"
    t.string "email"
    t.string "header_image_url"
    t.text "description"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer "parent_id"
    t.integer "challenge_id"
    t.string "challenge_type"
    t.string "icon_file_name"
    t.string "icon_content_type"
    t.integer "icon_file_size"
    t.datetime "icon_updated_at"
    t.integer "description_sanitizer_version", limit: 2, default: 0, null: false
    t.string "icon_alt_text", default: ""
    t.string "icon_comment_text", default: ""
    t.boolean "multifandom"
    t.boolean "open_doors"
    t.index ["name"], name: "index_collections_on_name"
    t.index ["parent_id"], name: "index_collections_on_parent_id"
  end

  create_table "comments", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "pseud_id"
    t.text "comment_content", null: false
    t.integer "depth"
    t.integer "threaded_left"
    t.integer "threaded_right"
    t.boolean "is_deleted", default: false, null: false
    t.string "name"
    t.string "email"
    t.string "ip_address"
    t.integer "commentable_id"
    t.string "commentable_type"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer "thread"
    t.string "user_agent"
    t.boolean "approved", default: false, null: false
    t.boolean "hidden_by_admin", default: false, null: false
    t.datetime "edited_at"
    t.integer "parent_id"
    t.string "parent_type"
    t.integer "comment_content_sanitizer_version", limit: 2, default: 0, null: false
    t.boolean "unreviewed", default: false, null: false
    t.boolean "iced", default: false, null: false
    t.index ["commentable_id", "commentable_type"], name: "index_comments_commentable"
    t.index ["ip_address"], name: "index_comments_on_ip_address"
    t.index ["parent_id", "parent_type"], name: "index_comments_parent"
    t.index ["pseud_id"], name: "index_comments_on_pseud_id"
    t.index ["thread"], name: "comments_by_thread"
  end

  create_table "common_taggings", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "common_tag_id", null: false
    t.integer "filterable_id", null: false
    t.string "filterable_type", limit: 100
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["common_tag_id", "filterable_type", "filterable_id"], name: "index_common_tags", unique: true
    t.index ["filterable_id"], name: "index_common_taggings_on_filterable_id"
  end

  create_table "creatorships", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "creation_id"
    t.string "creation_type", limit: 100
    t.integer "pseud_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "approved", default: false, null: false
    t.index ["creation_id", "creation_type", "pseud_id"], name: "creation_id_creation_type_pseud_id", unique: true
    t.index ["pseud_id"], name: "index_creatorships_pseud"
  end

  create_table "delayed_jobs", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "priority", default: 0
    t.integer "attempts", default: 0
    t.text "handler"
    t.text "last_error"
    t.datetime "run_at"
    t.datetime "locked_at"
    t.datetime "failed_at"
    t.string "locked_by"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["failed_at"], name: "delayed_jobs_failed_at"
    t.index ["locked_at"], name: "delayed_jobs_locked_at"
    t.index ["locked_by"], name: "delayed_jobs_locked_by"
    t.index ["run_at"], name: "delayed_jobs_run_at"
  end

  create_table "external_author_names", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "external_author_id", null: false
    t.string "name"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["external_author_id"], name: "index_external_author_names_on_external_author_id"
  end

  create_table "external_authors", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "email"
    t.boolean "is_claimed", default: false, null: false
    t.integer "user_id"
    t.boolean "do_not_email", default: false, null: false
    t.boolean "do_not_import", default: false, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["email"], name: "index_external_authors_on_email"
    t.index ["user_id"], name: "index_external_authors_on_user_id"
  end

  create_table "external_creatorships", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "creation_id"
    t.string "creation_type"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer "archivist_id"
    t.integer "external_author_name_id"
    t.index ["archivist_id"], name: "index_external_creatorships_on_archivist_id"
    t.index ["creation_id", "creation_type"], name: "index_external_creatorships_on_creation_id_and_creation_type"
    t.index ["external_author_name_id"], name: "index_external_creatorships_on_external_author_name_id"
  end

  create_table "external_works", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "url", null: false
    t.string "author", null: false
    t.boolean "dead", default: false, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "title", null: false
    t.text "summary"
    t.boolean "hidden_by_admin", default: false, null: false
    t.integer "summary_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "language_id"
  end

  create_table "fannish_next_of_kins", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id"
    t.integer "kin_id"
    t.string "kin_email"
    t.index ["user_id"], name: "index_fannish_next_of_kins_on_user_id"
  end

  create_table "favorite_tags", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id"
    t.integer "tag_id"
    t.index ["user_id", "tag_id"], name: "index_favorite_tags_on_user_id_and_tag_id", unique: true
  end

  create_table "feedbacks", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.text "comment", null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "email"
    t.string "summary"
    t.string "user_agent"
    t.string "category"
    t.integer "comment_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "summary_sanitizer_version", limit: 2, default: 0, null: false
    t.string "ip_address"
    t.string "username"
    t.string "language"
    t.string "rollout"
  end

  create_table "filter_counts", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.bigint "filter_id", null: false
    t.bigint "public_works_count", default: 0
    t.bigint "unhidden_works_count", default: 0
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["filter_id"], name: "index_filter_counts_on_filter_id", unique: true
    t.index ["public_works_count"], name: "index_public_works_count"
    t.index ["unhidden_works_count"], name: "index_unhidden_works_count"
  end

  create_table "filter_taggings", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.bigint "filter_id", null: false
    t.bigint "filterable_id", null: false
    t.string "filterable_type", limit: 100
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "inherited", default: false, null: false
    t.index ["filter_id", "filterable_type", "filterable_id"], name: "index_filter_taggings_on_filter_and_filterable", unique: true
    t.index ["filterable_id", "filterable_type"], name: "index_filter_taggings_filterable"
  end

  create_table "gift_exchanges", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "request_restriction_id"
    t.integer "offer_restriction_id"
    t.integer "requests_num_required", default: 1, null: false
    t.integer "offers_num_required", default: 1, null: false
    t.integer "requests_num_allowed", default: 1, null: false
    t.integer "offers_num_allowed", default: 1, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.text "signup_instructions_general"
    t.text "signup_instructions_requests"
    t.text "signup_instructions_offers"
    t.boolean "signup_open", default: false, null: false
    t.datetime "signups_open_at"
    t.datetime "signups_close_at"
    t.datetime "assignments_due_at"
    t.datetime "works_reveal_at"
    t.datetime "authors_reveal_at"
    t.integer "prompt_restriction_id"
    t.string "request_url_label"
    t.string "request_description_label"
    t.string "offer_url_label"
    t.string "offer_description_label"
    t.string "time_zone"
    t.integer "potential_match_settings_id"
    t.datetime "assignments_sent_at"
    t.integer "signup_instructions_general_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "signup_instructions_requests_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "signup_instructions_offers_sanitizer_version", limit: 2, default: 0, null: false
    t.boolean "requests_summary_visible", default: false, null: false
  end

  create_table "gifts", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "work_id"
    t.string "recipient_name"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer "pseud_id"
    t.boolean "rejected", default: false, null: false
    t.index ["pseud_id"], name: "index_gifts_on_pseud_id"
    t.index ["recipient_name"], name: "index_gifts_on_recipient_name"
    t.index ["work_id"], name: "index_gifts_on_work_id"
  end

  create_table "inbox_comments", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id"
    t.integer "feedback_comment_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "read", default: false, null: false
    t.boolean "replied_to", default: false, null: false
    t.index ["feedback_comment_id"], name: "index_inbox_comments_on_feedback_comment_id"
    t.index ["read", "user_id"], name: "index_inbox_comments_on_read_and_user_id"
    t.index ["user_id"], name: "index_inbox_comments_on_user_id"
  end

  create_table "innodb_monitor", id: false, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8", force: :cascade do |t|
    t.integer "a"
  end

  create_table "invitations", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "creator_id"
    t.string "invitee_email"
    t.string "token"
    t.datetime "sent_at"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "used", default: false, null: false
    t.integer "invitee_id"
    t.string "invitee_type"
    t.string "creator_type"
    t.datetime "redeemed_at"
    t.boolean "from_queue", default: false, null: false
    t.integer "external_author_id"
    t.index ["creator_id", "creator_type"], name: "index_invitations_on_creator_id_and_creator_type"
    t.index ["external_author_id"], name: "index_invitations_on_external_author_id"
    t.index ["invitee_id", "invitee_type"], name: "index_invitations_on_invitee_id_and_invitee_type"
    t.index ["token"], name: "index_invitations_on_token"
  end

  create_table "invite_requests", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "email"
    t.integer "position"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "simplified_email", default: "", null: false
    t.string "ip_address"
    t.index ["email"], name: "index_invite_requests_on_email"
    t.index ["simplified_email"], name: "index_invite_requests_on_simplified_email", unique: true
  end

  create_table "known_issues", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "admin_id"
    t.string "title"
    t.text "content"
    t.datetime "updated_at"
    t.datetime "created_at"
    t.integer "content_sanitizer_version", limit: 2, default: 0, null: false
  end

  create_table "kudos", options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "commentable_id"
    t.string "commentable_type", collation: "utf8_general_ci"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "ip_address", collation: "utf8_general_ci"
    t.integer "user_id"
    t.index ["commentable_id", "commentable_type", "ip_address"], name: "index_kudos_on_commentable_and_ip_address", unique: true
    t.index ["commentable_id", "commentable_type", "user_id"], name: "index_kudos_on_commentable_and_user", unique: true
    t.index ["ip_address"], name: "index_kudos_on_ip_address"
    t.index ["user_id"], name: "index_kudos_on_user_id"
  end

  create_table "languages", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "short", limit: 4
    t.string "name"
    t.boolean "support_available", default: false, null: false
    t.boolean "abuse_support_available", default: false, null: false
    t.string "sortable_name", default: "", null: false
    t.index ["short"], name: "index_languages_on_short"
    t.index ["sortable_name"], name: "index_languages_on_sortable_name"
  end

  create_table "locales", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "iso"
    t.string "short"
    t.string "name"
    t.boolean "main"
    t.datetime "updated_at"
    t.integer "language_id", null: false
    t.boolean "interface_enabled", default: false, null: false
    t.boolean "email_enabled", default: false, null: false
    t.index ["iso"], name: "index_locales_on_iso"
    t.index ["language_id"], name: "index_locales_on_language_id"
    t.index ["short"], name: "index_locales_on_short"
  end

  create_table "log_items", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id", null: false
    t.integer "admin_id"
    t.integer "role_id"
    t.integer "action", limit: 1
    t.text "note", null: false
    t.datetime "enddate"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer "note_sanitizer_version", limit: 2, default: 0, null: false
    t.index ["admin_id"], name: "index_log_items_on_admin_id"
    t.index ["role_id"], name: "index_log_items_on_role_id"
    t.index ["user_id"], name: "index_log_items_on_user_id"
  end

  create_table "meta_taggings", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.bigint "meta_tag_id", null: false
    t.bigint "sub_tag_id", null: false
    t.boolean "direct", default: true
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["meta_tag_id", "sub_tag_id"], name: "index_meta_taggings_on_meta_tag_id_and_sub_tag_id", unique: true
    t.index ["sub_tag_id"], name: "index_meta_taggings_on_sub_tag_id"
  end

  create_table "moderated_works", options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.bigint "work_id", null: false
    t.boolean "approved", default: false, null: false
    t.boolean "reviewed", default: false, null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["work_id"], name: "index_moderated_works_on_work_id"
  end

  create_table "open_id_authentication_associations", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "issued"
    t.integer "lifetime"
    t.string "handle"
    t.string "assoc_type"
    t.binary "server_url"
    t.binary "secret"
  end

  create_table "open_id_authentication_nonces", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "timestamp", null: false
    t.string "server_url"
    t.string "salt", null: false
  end

  create_table "owned_set_taggings", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "owned_tag_set_id"
    t.integer "set_taggable_id"
    t.string "set_taggable_type", limit: 100
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "owned_tag_sets", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "tag_set_id"
    t.boolean "visible", default: false, null: false
    t.boolean "nominated", default: false, null: false
    t.string "title"
    t.string "description"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "featured", default: false, null: false
    t.integer "description_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "fandom_nomination_limit", default: 0, null: false
    t.integer "character_nomination_limit", default: 0, null: false
    t.integer "relationship_nomination_limit", default: 0, null: false
    t.integer "freeform_nomination_limit", default: 0, null: false
    t.boolean "usable", default: false, null: false
  end

  create_table "potential_match_settings", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "num_required_prompts", default: 1, null: false
    t.integer "num_required_fandoms", default: 0, null: false
    t.integer "num_required_characters", default: 0, null: false
    t.integer "num_required_relationships", default: 0, null: false
    t.integer "num_required_freeforms", default: 0, null: false
    t.integer "num_required_categories", default: 0, null: false
    t.integer "num_required_ratings", default: 0, null: false
    t.integer "num_required_archive_warnings", default: 0, null: false
    t.boolean "include_optional_fandoms", default: false, null: false
    t.boolean "include_optional_characters", default: false, null: false
    t.boolean "include_optional_relationships", default: false, null: false
    t.boolean "include_optional_freeforms", default: false, null: false
    t.boolean "include_optional_categories", default: false, null: false
    t.boolean "include_optional_ratings", default: false, null: false
    t.boolean "include_optional_archive_warnings", default: false, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "potential_matches", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "collection_id"
    t.integer "offer_signup_id"
    t.integer "request_signup_id"
    t.integer "num_prompts_matched"
    t.boolean "assigned", default: false, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer "max_tags_matched"
    t.index ["collection_id"], name: "index_potential_matches_on_collection_id"
    t.index ["offer_signup_id"], name: "index_potential_matches_on_offer_signup_id"
    t.index ["request_signup_id"], name: "index_potential_matches_on_request_signup_id"
  end

  create_table "preferences", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id"
    t.boolean "history_enabled", default: true
    t.boolean "email_visible", default: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "date_of_birth_visible", default: false
    t.boolean "edit_emails_off", default: false, null: false
    t.boolean "comment_emails_off", default: false, null: false
    t.boolean "adult", default: false
    t.boolean "hide_warnings", default: false, null: false
    t.boolean "comment_inbox_off", default: false
    t.boolean "comment_copy_to_self_off", default: true, null: false
    t.string "work_title_format", default: "TITLE - AUTHOR - FANDOM"
    t.boolean "hide_freeform", default: false, null: false
    t.boolean "first_login", default: true
    t.boolean "automatically_approve_collections", default: false, null: false
    t.boolean "collection_emails_off", default: false, null: false
    t.boolean "collection_inbox_off", default: false, null: false
    t.boolean "recipient_emails_off", default: false, null: false
    t.boolean "view_full_works", default: false, null: false
    t.string "time_zone"
    t.boolean "plain_text_skin", default: false, null: false
    t.boolean "disable_work_skins", default: false, null: false
    t.integer "skin_id"
    t.boolean "minimize_search_engines", default: false, null: false
    t.boolean "kudos_emails_off", default: false, null: false
    t.boolean "disable_share_links", default: false, null: false
    t.boolean "banner_seen", default: false, null: false
    t.integer "preferred_locale", default: 1, null: false
    t.boolean "allow_cocreator", default: false
    t.index ["skin_id"], name: "index_preferences_on_skin_id"
    t.index ["user_id"], name: "index_preferences_on_user_id"
  end

  create_table "profiles", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id"
    t.string "location"
    t.text "about_me"
    t.date "date_of_birth"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "title"
    t.integer "about_me_sanitizer_version", limit: 2, default: 0, null: false
    t.index ["user_id"], name: "index_profiles_on_user_id"
  end

  create_table "prompt_memes", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "prompt_restriction_id"
    t.integer "request_restriction_id"
    t.integer "requests_num_required", default: 1, null: false
    t.integer "requests_num_allowed", default: 5, null: false
    t.boolean "signup_open", default: true, null: false
    t.datetime "signups_open_at"
    t.datetime "signups_close_at"
    t.datetime "assignments_due_at"
    t.datetime "works_reveal_at"
    t.datetime "authors_reveal_at"
    t.text "signup_instructions_general"
    t.text "signup_instructions_requests"
    t.string "request_url_label"
    t.string "request_description_label"
    t.string "time_zone"
    t.integer "signup_instructions_general_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "signup_instructions_requests_sanitizer_version", limit: 2, default: 0, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "anonymous", default: false, null: false
  end

  create_table "prompt_restrictions", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "tag_set_id"
    t.boolean "optional_tags_allowed", default: false, null: false
    t.boolean "description_allowed", default: true, null: false
    t.boolean "url_required", default: false, null: false
    t.integer "fandom_num_required", default: 0, null: false
    t.integer "category_num_required", default: 0, null: false
    t.integer "rating_num_required", default: 0, null: false
    t.integer "character_num_required", default: 0, null: false
    t.integer "relationship_num_required", default: 0, null: false
    t.integer "freeform_num_required", default: 0, null: false
    t.integer "archive_warning_num_required", default: 0, null: false
    t.integer "fandom_num_allowed", default: 1, null: false
    t.integer "category_num_allowed", default: 0, null: false
    t.integer "rating_num_allowed", default: 0, null: false
    t.integer "character_num_allowed", default: 1, null: false
    t.integer "relationship_num_allowed", default: 1, null: false
    t.integer "freeform_num_allowed", default: 0, null: false
    t.integer "archive_warning_num_allowed", default: 0, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "description_required", default: false, null: false
    t.boolean "url_allowed", default: false, null: false
    t.boolean "allow_any_fandom", default: false, null: false
    t.boolean "allow_any_character", default: false, null: false
    t.boolean "allow_any_rating", default: false, null: false
    t.boolean "allow_any_relationship", default: false, null: false
    t.boolean "allow_any_category", default: false, null: false
    t.boolean "allow_any_archive_warning", default: false, null: false
    t.boolean "allow_any_freeform", default: false, null: false
    t.boolean "require_unique_fandom", default: false, null: false
    t.boolean "require_unique_character", default: false, null: false
    t.boolean "require_unique_rating", default: false, null: false
    t.boolean "require_unique_relationship", default: false, null: false
    t.boolean "require_unique_category", default: false, null: false
    t.boolean "require_unique_archive_warning", default: false, null: false
    t.boolean "require_unique_freeform", default: false, null: false
    t.boolean "character_restrict_to_fandom", default: false, null: false
    t.boolean "relationship_restrict_to_fandom", default: false, null: false
    t.boolean "character_restrict_to_tag_set", default: false, null: false
    t.boolean "relationship_restrict_to_tag_set", default: false, null: false
    t.boolean "title_required", default: false, null: false
    t.boolean "title_allowed", default: false, null: false
  end

  create_table "prompts", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "collection_id"
    t.integer "challenge_signup_id"
    t.integer "pseud_id"
    t.integer "tag_set_id"
    t.integer "optional_tag_set_id"
    t.string "title"
    t.string "url"
    t.text "description"
    t.integer "position"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "type"
    t.integer "description_sanitizer_version", limit: 2, default: 0, null: false
    t.boolean "any_fandom", default: false, null: false
    t.boolean "any_character", default: false, null: false
    t.boolean "any_rating", default: false, null: false
    t.boolean "any_relationship", default: false, null: false
    t.boolean "any_category", default: false, null: false
    t.boolean "any_archive_warning", default: false, null: false
    t.boolean "any_freeform", default: false, null: false
    t.boolean "anonymous", default: false, null: false
    t.index ["challenge_signup_id"], name: "index_prompts_on_challenge_signup_id"
    t.index ["collection_id"], name: "index_prompts_on_collection_id"
    t.index ["optional_tag_set_id"], name: "index_prompts_on_optional_tag_set_id"
    t.index ["tag_set_id"], name: "index_prompts_on_tag_set_id"
    t.index ["type"], name: "index_prompts_on_type"
  end

  create_table "pseuds", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id"
    t.string "name", null: false
    t.text "description"
    t.boolean "is_default", default: false, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "icon_file_name"
    t.string "icon_content_type"
    t.integer "icon_file_size"
    t.datetime "icon_updated_at"
    t.string "icon_alt_text", default: ""
    t.boolean "delta", default: true
    t.integer "description_sanitizer_version", limit: 2, default: 0, null: false
    t.string "icon_comment_text", default: ""
    t.index ["name"], name: "index_psueds_on_name"
    t.index ["user_id", "name"], name: "index_pseuds_on_user_id_and_name"
  end

  create_table "question_translations", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "question_id"
    t.string "locale", null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.string "question"
    t.text "content"
    t.integer "content_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "screencast_sanitizer_version", limit: 2, default: 0, null: false
    t.string "is_translated"
    t.index ["locale"], name: "index_question_translations_on_locale"
    t.index ["question_id"], name: "index_question_translations_on_question_id"
  end

  create_table "questions", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "archive_faq_id"
    t.string "question"
    t.text "content"
    t.string "anchor"
    t.text "screencast"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.integer "position", default: 1
    t.index ["archive_faq_id", "position"], name: "index_questions_on_archive_faq_id_and_position"
  end

  create_table "readings", options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "major_version_read"
    t.integer "minor_version_read"
    t.integer "user_id"
    t.integer "work_id"
    t.datetime "created_at"
    t.datetime "last_viewed"
    t.integer "view_count", default: 0
    t.boolean "toread", default: false, null: false
    t.boolean "toskip", default: false, null: false
    t.index ["user_id"], name: "index_readings_on_user_id"
    t.index ["work_id"], name: "index_readings_on_work_id"
  end

  create_table "related_works", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "parent_id"
    t.string "parent_type"
    t.integer "work_id"
    t.boolean "reciprocal", default: false, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "translation", default: false, null: false
    t.index ["parent_id", "parent_type"], name: "index_related_works_on_parent_id_and_parent_type"
    t.index ["work_id"], name: "index_related_works_on_work_id"
  end

  create_table "roles", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "name", limit: 40
    t.string "authorizable_type", limit: 40
    t.integer "authorizable_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["authorizable_id", "authorizable_type"], name: "index_roles_on_authorizable_id_and_authorizable_type"
    t.index ["authorizable_type"], name: "index_roles_on_authorizable_type"
    t.index ["name"], name: "index_roles_on_name"
  end

  create_table "roles_users", id: false, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8", force: :cascade do |t|
    t.integer "user_id"
    t.integer "role_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["role_id", "user_id"], name: "index_roles_users_on_role_id_and_user_id"
    t.index ["user_id", "role_id"], name: "index_roles_users_on_user_id_and_role_id"
  end

  create_table "searches", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id"
    t.string "name"
    t.text "options"
    t.string "type"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "serial_works", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "series_id"
    t.integer "work_id"
    t.integer "position", default: 1
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["series_id"], name: "index_serial_works_on_series_id"
    t.index ["work_id"], name: "index_serial_works_on_work_id"
  end

  create_table "series", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "title", null: false
    t.text "summary"
    t.text "series_notes"
    t.boolean "hidden_by_admin", default: false, null: false
    t.boolean "restricted", default: true, null: false
    t.boolean "complete", default: false, null: false
    t.integer "summary_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "series_notes_sanitizer_version", limit: 2, default: 0, null: false
  end

  create_table "set_taggings", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "tag_id"
    t.integer "tag_set_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["tag_id"], name: "index_set_taggings_on_tag_id"
    t.index ["tag_set_id"], name: "index_set_taggings_on_tag_set_id"
  end

  create_table "skin_parents", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "child_skin_id"
    t.integer "parent_skin_id"
    t.integer "position"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "skins", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "title"
    t.integer "author_id"
    t.text "css"
    t.boolean "public", default: false
    t.boolean "official", default: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "icon_file_name"
    t.string "icon_content_type"
    t.integer "icon_file_size"
    t.datetime "icon_updated_at"
    t.string "icon_alt_text", default: ""
    t.integer "margin"
    t.integer "paragraph_gap"
    t.string "font"
    t.integer "base_em"
    t.string "background_color"
    t.string "foreground_color"
    t.text "description"
    t.boolean "rejected", default: false, null: false
    t.string "admin_note"
    t.integer "description_sanitizer_version", limit: 2, default: 0, null: false
    t.string "type"
    t.float "paragraph_margin"
    t.string "headercolor"
    t.string "accent_color"
    t.string "role"
    t.string "media"
    t.string "ie_condition"
    t.string "filename"
    t.boolean "do_not_upgrade", default: false, null: false
    t.boolean "cached", default: false, null: false
    t.boolean "unusable", default: false, null: false
    t.boolean "featured", default: false, null: false
    t.boolean "in_chooser", default: false, null: false
    t.index ["author_id"], name: "index_skins_on_author_id"
    t.index ["in_chooser"], name: "index_skins_on_in_chooser"
    t.index ["public", "official"], name: "index_skins_on_public_and_official"
    t.index ["title"], name: "index_skins_on_title"
    t.index ["type"], name: "index_skins_on_type"
  end

  create_table "stat_counters", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "work_id"
    t.integer "hit_count", default: 0, null: false
    t.integer "download_count", default: 0, null: false
    t.integer "comments_count", default: 0, null: false
    t.integer "kudos_count", default: 0, null: false
    t.integer "bookmarks_count", default: 0, null: false
    t.index ["hit_count"], name: "index_hit_counters_on_hit_count"
    t.index ["work_id"], name: "index_hit_counters_on_work_id", unique: true
  end

  create_table "subscriptions", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id"
    t.integer "subscribable_id"
    t.string "subscribable_type"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["subscribable_id", "subscribable_type"], name: "subscribable"
    t.index ["user_id"], name: "user_id"
  end

  create_table "tag_nominations", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "type"
    t.integer "tag_set_nomination_id"
    t.integer "fandom_nomination_id"
    t.string "tagname"
    t.string "parent_tagname"
    t.boolean "approved", default: false, null: false
    t.boolean "rejected", default: false, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean "canonical", default: false, null: false
    t.boolean "exists", default: false, null: false
    t.boolean "parented", default: false, null: false
    t.string "synonym"
    t.index ["tagname"], name: "index_tag_nominations_on_tagname"
    t.index ["type", "fandom_nomination_id"], name: "index_tag_nominations_on_type_and_fandom_nomination_id"
    t.index ["type", "synonym"], name: "index_tag_nominations_on_type_and_synonym"
    t.index ["type", "tag_set_nomination_id"], name: "index_tag_nominations_on_type_and_tag_set_nomination_id"
    t.index ["type", "tagname"], name: "index_tag_nominations_on_type_and_tagname"
  end

  create_table "tag_set_associations", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "owned_tag_set_id"
    t.integer "tag_id"
    t.integer "parent_tag_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "tag_set_nominations", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "pseud_id"
    t.integer "owned_tag_set_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "tag_set_ownerships", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "pseud_id"
    t.integer "owned_tag_set_id"
    t.boolean "owner", default: false, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "tag_sets", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "taggings", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "tagger_id"
    t.integer "taggable_id", null: false
    t.string "taggable_type", limit: 100, default: ""
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "tagger_type", limit: 100, default: ""
    t.index ["taggable_id", "taggable_type"], name: "index_taggings_taggable"
    t.index ["tagger_id", "tagger_type", "taggable_id", "taggable_type"], name: "index_taggings_polymorphic", unique: true
  end

  create_table "tags", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.string "name", limit: 100, default: ""
    t.boolean "canonical", default: false, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer "taggings_count_cache", default: 0
    t.boolean "adult", default: false
    t.string "type"
    t.integer "merger_id"
    t.boolean "delta", default: false
    t.integer "last_wrangler_id"
    t.string "last_wrangler_type"
    t.boolean "unwrangleable", default: false, null: false
    t.string "sortable_name", default: "", null: false
    t.index ["canonical"], name: "index_tags_on_canonical"
    t.index ["created_at"], name: "tag_created_at_index"
    t.index ["id", "type"], name: "index_tags_on_id_and_type"
    t.index ["merger_id"], name: "index_tags_on_merger_id"
    t.index ["name"], name: "index_tags_on_name", unique: true
    t.index ["sortable_name"], name: "index_tags_on_sortable_name"
    t.index ["taggings_count_cache"], name: "index_tags_on_taggings_count_cache"
    t.index ["type"], name: "index_tags_on_type"
  end

  create_table "user_invite_requests", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id"
    t.integer "quantity"
    t.text "reason"
    t.boolean "granted", default: false, null: false
    t.boolean "handled", default: false, null: false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["user_id"], name: "index_user_invite_requests_on_user_id"
  end

  create_table "users", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "email"
    t.string "confirmation_token"
    t.string "login"
    t.datetime "confirmed_at"
    t.string "encrypted_password"
    t.string "password_salt"
    t.boolean "suspended", default: false, null: false
    t.boolean "banned", default: false, null: false
    t.integer "invitation_id"
    t.datetime "suspended_until"
    t.boolean "out_of_invites", default: true, null: false
    t.integer "failed_attempts", default: 0
    t.integer "accepted_tos_version"
    t.datetime "confirmation_sent_at"
    t.string "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer "sign_in_count", default: 0
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string "current_sign_in_ip"
    t.string "last_sign_in_ip"
    t.string "unlock_token"
    t.datetime "locked_at"
    t.boolean "recently_reset"
    t.index ["confirmation_token"], name: "index_users_on_confirmation_token", unique: true
    t.index ["email"], name: "index_users_on_email", unique: true
    t.index ["login"], name: "index_users_on_login", unique: true
    t.index ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true
    t.index ["unlock_token"], name: "index_users_on_unlock_token", unique: true
  end

  create_table "work_links", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "work_id"
    t.string "url"
    t.integer "count"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.index ["work_id", "url"], name: "work_links_work_id_url", unique: true
  end

  create_table "works", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "expected_number_of_chapters", default: 1
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer "major_version", default: 1
    t.integer "minor_version", default: 0
    t.boolean "posted", default: false, null: false
    t.integer "language_id"
    t.boolean "restricted", default: false, null: false
    t.string "title", null: false
    t.text "summary"
    t.text "notes"
    t.integer "word_count"
    t.boolean "hidden_by_admin", default: false, null: false
    t.boolean "delta", default: false
    t.datetime "revised_at"
    t.string "title_to_sort_on"
    t.boolean "backdate", default: false, null: false
    t.text "endnotes"
    t.string "imported_from_url"
    t.boolean "complete", default: false, null: false
    t.integer "summary_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "notes_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "endnotes_sanitizer_version", limit: 2, default: 0, null: false
    t.integer "work_skin_id"
    t.boolean "in_anon_collection", default: false, null: false
    t.boolean "in_unrevealed_collection", default: false, null: false
    t.string "ip_address"
    t.boolean "spam", default: false, null: false
    t.datetime "spam_checked_at"
    t.boolean "moderated_commenting_enabled", default: false, null: false
    t.integer "comment_permissions", limit: 1, default: 0, null: false
    t.index ["complete", "posted", "hidden_by_admin"], name: "complete_works"
    t.index ["delta"], name: "index_works_on_delta"
    t.index ["imported_from_url"], name: "index_works_on_imported_from_url"
    t.index ["ip_address"], name: "index_works_on_ip_address"
    t.index ["language_id"], name: "index_works_on_language_id"
    t.index ["restricted", "posted", "hidden_by_admin"], name: "visible_works"
    t.index ["revised_at"], name: "index_works_on_revised_at"
    t.index ["spam"], name: "index_works_on_spam"
  end

  create_table "wrangling_assignments", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "user_id"
    t.integer "fandom_id"
    t.index ["fandom_id"], name: "wrangling_assignments_by_fandom_id"
    t.index ["user_id"], name: "wrangling_assignments_by_user_id"
  end

  create_table "wrangling_guidelines", id: :integer, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC", force: :cascade do |t|
    t.integer "admin_id"
    t.string "title"
    t.text "content"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer "position"
    t.integer "content_sanitizer_version", limit: 2, default: 0, null: false
  end

end
