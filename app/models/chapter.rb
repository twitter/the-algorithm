# encoding=utf-8

class Chapter < ApplicationRecord
  include HtmlCleaner
  include WorkChapterCountCaching
  include CreationNotifier
  include Creatable

  belongs_to :work, inverse_of: :chapters
  # acts_as_list scope: 'work_id = #{work_id}'

  acts_as_commentable

  validates_length_of :title, allow_blank: true, maximum: ArchiveConfig.TITLE_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.TITLE_MAX)

  validates_length_of :summary, allow_blank: true, maximum: ArchiveConfig.SUMMARY_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.SUMMARY_MAX)
  validates_length_of :notes, allow_blank: true, maximum: ArchiveConfig.NOTES_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.NOTES_MAX)
  validates_length_of :endnotes, allow_blank: true, maximum: ArchiveConfig.NOTES_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.NOTES_MAX)


  validates_presence_of :content
  validates_length_of :content, minimum: ArchiveConfig.CONTENT_MIN,
    too_short: ts("must be at least %{min} characters long.", min: ArchiveConfig.CONTENT_MIN)

  validates_length_of :content, maximum: ArchiveConfig.CONTENT_MAX,
    too_long: ts("cannot be more than %{max} characters long.", max: ArchiveConfig.CONTENT_MAX)

  attr_accessor :wip_length_placeholder

  before_validation :inherit_creatorships
  def inherit_creatorships
    if work && creatorships.empty? && current_user_pseuds.blank?
      work.pseuds_after_saving.each do |pseud|
        creatorships.build(pseud: pseud)
      end
    end
  end

  before_save :strip_title
  before_save :set_word_count
  before_save :validate_published_at

  after_create :notify_after_creation
  after_update :notify_after_update

  scope :in_order, -> { order(:position) }
  scope :posted, -> { where(posted: true) }

  after_save :fix_positions
  def fix_positions
    if work&.persisted?
      positions_changed = false
      self.position ||= 1
      chapters = work.chapters.order(:position)
      if chapters && chapters.length > 1
        chapters = chapters - [self]
        chapters.insert(self.position-1, self)
        chapters.compact.each_with_index do |chapter, i|
          if chapter.position != i+1
            Chapter.where(["id = ?", chapter.id]).update_all(["position = ?", i + 1])
            positions_changed = true
          end
        end
      end
      # We're caching the chapter positions in the comment blurbs and the last
      # chapter link in the work blurbs so we need to expire the blurbs and the
      # work indexes.
      if positions_changed
        work.comments.each{ |c| c.touch }
        work.expire_caches
      end
    end
  end

  after_save :invalidate_chapter_count,
    if: Proc.new { |chapter| chapter.saved_change_to_posted? }

  before_destroy :fix_positions_before_destroy, :invalidate_chapter_count
  def fix_positions_before_destroy
    if work&.persisted? && position
      chapters = work.chapters.where(["position > ?", position])
      chapters.each { |c| c.update_attribute(:position, c.position - 1) }
    end
  end

  after_commit :update_series_index
  def update_series_index
    return unless work&.series.present? && should_reindex_series?
    work.serial_works.each(&:update_series_index)
  end

  def should_reindex_series?
    pertinent_attributes = %w[id posted]
    destroyed? || (saved_changes.keys & pertinent_attributes).present?
  end

  def invalidate_chapter_count
    if work
      invalidate_work_chapter_count(work)
    end
  end

  def moderated_commenting_enabled?
    work && work.moderated_commenting_enabled?
  end

  # strip leading spaces from title
  def strip_title
    unless self.title.blank?
      self.title = self.title.gsub(/^\s*/, '')
    end
  end

  def chapter_header
    "#{ts("Chapter")} #{position}"
  end

  def chapter_title
    self.title.blank? ? self.chapter_header : self.title
  end

  # Header plus title, used in subscriptions
  def full_chapter_title
    str = chapter_header
    if title.present?
      str += ": #{title}"
    end
    str
  end

  def display_title
    self.position.to_s + '. ' + self.chapter_title
  end

  def abbreviated_display_title
    self.display_title.length > 50 ? (self.display_title[0..50] + "...") : self.display_title
  end

  # make em-dashes into html code
#  def clean_emdashes
#    self.content.gsub!(/\xE2\x80\"/, '&#8212;')
#  end
  # check if this chapter is the only chapter of its work
  def is_only_chapter?
    self.work.chapters.count == 1
  end

  # Virtual attribute for work wip_length
  # Chapter needed its own version for sense-checking purposes
  def wip_length
    if self.new_record? && self.work.expected_number_of_chapters == self.work.number_of_chapters
      self.work.expected_number_of_chapters += 1
    elsif self.work.expected_number_of_chapters && self.work.expected_number_of_chapters < self.work.number_of_chapters
      "?"
    else
      self.work.wip_length
    end
  end

  # Can't directly access work from a chapter virtual attribute
  # Using a placeholder variable for edits, where the value isn't saved immediately
  def wip_length=(number)
    self.wip_length_placeholder = number
  end

  # Checks the chapter published_at date isn't in the future
  def validate_published_at
    if !self.published_at
      self.published_at = Date.current
    elsif self.published_at > Date.current
      errors.add(:base, ts("Publication date can't be in the future."))
      throw :abort
    end
  end

  # Set the value of word_count to reflect the length of the text in the chapter content
  def set_word_count
    if self.new_record? || self.content_changed? || self.word_count.nil?
      counter = WordCounter.new(self.content)
      self.word_count = counter.count
    else
      self.word_count
    end
  end

  # Return the name to link comments to for this object
  def commentable_name
    self.work.title
  end

  def expire_comments_count
    super
    work&.expire_comments_count
  end
end
