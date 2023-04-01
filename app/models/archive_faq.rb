class ArchiveFaq < ApplicationRecord
  acts_as_list
  translates :title
  translation_class.include(Globalized)

  has_many :questions, -> { order(:position) }, dependent: :destroy
  accepts_nested_attributes_for :questions, allow_destroy: true

  validates :slug, presence: true, uniqueness: true

  belongs_to :language

  before_validation :set_slug
  def set_slug
    if I18n.locale == :en
      self.slug = self.title.parameterize
    end
  end

  # Change the positions of the questions in the archive_faq
  def reorder_list(positions)
    SortableList.new(self.questions.in_order).reorder_list(positions)
  end

  def to_param
    slug_was
  end

  def self.reorder_list(positions)
    SortableList.new(self.order('position ASC')).reorder_list(positions)
  end
end
