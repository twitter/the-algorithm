class Question < ApplicationRecord
  acts_as_list

  # The attributes that should be delegated to the translated class:
  translates :question, :content, :is_translated, :content_sanitizer_version
  translation_class.include(Globalized)

  # Ignore the screencast_sanitizer_version field until it can be deleted:
  translation_class.ignored_columns = [:screencast_sanitizer_version]

  belongs_to :archive_faq

  validates_presence_of :question, before: :create
  validates_presence_of :anchor, before: :create
  validates_presence_of :content, before: :create

  validates_length_of :content, minimum: ArchiveConfig.CONTENT_MIN,
                      too_short: ts('must be at least %{min} letters long.',
                      min: ArchiveConfig.CONTENT_MIN)

  validates_length_of :content, maximum: ArchiveConfig.CONTENT_MAX,
                      too_long: ts('cannot be more than %{max} characters
                      long.',
                      max: ArchiveConfig.CONTENT_MAX)

  scope :in_order, -> { order(:position) }

  # Change the positions of the questions in the
  def self.reorder(positions)
    SortableList.new(self.find(:all, order: 'position ASC')).
      reorder_list(positions)
  end
end
