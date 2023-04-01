class WranglingGuideline < ApplicationRecord
  acts_as_list

  validates_presence_of :content, :title
  validates_length_of :content, maximum: ArchiveConfig.CONTENT_MAX,
                                too_long: ts('cannot be more than %{max} characters long.', max: ArchiveConfig.CONTENT_MAX)

  def self.reorder_list(positions)
    SortableList.new(self.all.order(position: :asc)).reorder_list(positions)
  end
end
