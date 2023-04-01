class RestoreDefaultEmptyStrings < ActiveRecord::Migration[5.1]
  def change
    change_column_default :archive_faqs, :slug, from: nil, to: ""
    change_column_default :collections, :icon_alt_text, from: nil, to: ""
    change_column_default :collections, :icon_comment_text, from: nil, to: ""
    change_column_default :invite_requests, :simplified_email, from: nil, to: ""
    change_column_default :languages, :sortable_name, from: nil, to: ""
    change_column_default :pseuds, :icon_alt_text, from: nil, to: ""
    change_column_default :pseuds, :icon_comment_text, from: nil, to: ""
    change_column_default :skins, :icon_alt_text, from: nil, to: ""
    change_column_default :taggings, :taggable_type, from: nil, to: ""
    change_column_default :taggings, :tagger_type, from: nil, to: ""
    change_column_default :tags, :name, from: nil, to: ""
    change_column_default :tags, :sortable_name, from: nil, to: ""
  end
end
