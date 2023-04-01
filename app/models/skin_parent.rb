class SkinParent < ApplicationRecord
  belongs_to :child_skin, class_name: "Skin", inverse_of: :skin_parents, touch: true
  belongs_to :parent_skin, class_name: "Skin", inverse_of: :skin_children

  validates :position,
    uniqueness: {scope: [:child_skin_id, :parent_skin_id], message: ts("^Position has to be unique for each parent.")},
    numericality: {only_integer: true, greater_than: 0}

  validates_presence_of :child_skin, :parent_skin

  validate :no_site_parent
  def no_site_parent
    if parent_skin.get_role == "site" && !%w(override site).include?(child_skin.get_role)
      errors.add(:base, ts("^You can't use %{title} as a parent unless replacing the default archive skin.", title: parent_skin.title))
    end
  end

  validate :no_circular_skin
  def no_circular_skin
    if parent_skin == child_skin
      errors.add(:base, ts("^You can't make a skin its own parent"))
    end
    parent_ids = SkinParent.get_all_parent_ids(self.child_skin_id)
    if parent_ids.include?(self.parent_skin_id)
      errors.add(:base, ts("^%{parent_title} is already a parent of %{child_title}", child_title: child_skin.title, parent_title: parent_skin.title))
    end

    child_ids = SkinParent.get_all_child_ids(self.child_skin_id)
    if child_ids.include?(self.parent_skin_id)
      errors.add(:base, ts("^%{parent_title} is a child of %{child_title}", child_title: child_skin.title, parent_title: parent_skin.title))
    end

    # also don't allow duplication

  end

  # Takes as argument the ID of the skin to start at, and a block that should
  # take a list of skin IDs and produce a list of "adjacent" skin IDs (i.e.
  # parents or children).
  def self.search_skin_ids(root_id)
    found = Set.new([root_id])
    boundary = [root_id]

    until boundary.empty?
      new_boundary = yield boundary

      boundary = []

      new_boundary.each do |skin_id|
        boundary << skin_id if found.add?(skin_id)
      end
    end

    found.delete(root_id)
    found.to_a
  end

  def self.get_all_parent_ids(skin_id)
    search_skin_ids(skin_id) do |skin_ids|
      SkinParent.where(child_skin_id: skin_ids).pluck(:parent_skin_id)
    end
  end

  def self.get_all_child_ids(skin_id)
    search_skin_ids(skin_id) do |skin_ids|
      SkinParent.where(parent_skin_id: skin_ids).pluck(:child_skin_id)
    end
  end

   def parent_skin_title
     self.parent_skin.try(:title) || ""
   end

   def parent_skin_title=(title)
     self.parent_skin = Skin.find_by(title: title)
   end
end
