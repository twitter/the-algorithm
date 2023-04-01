class LogItem < ApplicationRecord
  # Ignore the note_sanitizer_version field until it can be deleted.
  self.ignored_columns = [:note_sanitizer_version]

  belongs_to :user
  belongs_to :admin

  belongs_to :role

  validates_presence_of :note
  validates_presence_of :action

  validates_length_of :note, maximum: ArchiveConfig.LOGNOTE_MAX

end
