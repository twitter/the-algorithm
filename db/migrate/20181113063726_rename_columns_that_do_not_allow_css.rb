class RenameColumnsThatDoNotAllowCss < ActiveRecord::Migration[5.1]
  def up
    rename_column :bookmarks, :notes, :bookmarker_notes
    rename_column :bookmarks, :notes_sanitizer_version, :bookmarker_notes_sanitizer_version
    rename_column :comments, :content, :comment_content
    rename_column :comments, :content_sanitizer_version, :comment_content_sanitizer_version
    rename_column :series, :notes, :series_notes
    rename_column :series, :notes_sanitizer_version, :series_notes_sanitizer_version
  end

  def down
    rename_column :series, :series_notes_sanitizer_version, :notes_sanitizer_version
    rename_column :series, :series_notes, :notes
    rename_column :comments, :comment_content_sanitizer_version, :content_sanitizer_version
    rename_column :comments, :comment_content, :content
    rename_column :bookmarks, :bookmarker_notes_sanitizer_version, :notes_sanitizer_version
    rename_column :bookmarks, :bookmarker_notes, :notes
  end
end
