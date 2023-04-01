class AdminPostTag < ApplicationRecord
  belongs_to :language
  has_many :admin_post_taggings
  has_many :admin_posts, through: :admin_post_taggings

  validates_presence_of :name
  validates :name, uniqueness: true
  validates_format_of :name, with: /[a-zA-Z0-9-]+$/, multiline: true

  # Find or create by name, and set the language if it's a new record
  def self.fetch(options)
    unless options[:name].blank?
      tag = self.find_by_name(options[:name])
      return tag unless tag.nil?
      tag = AdminPostTag.new(name: options[:name],
                             language_id: options[:language])
      tag.save ? tag : nil
    end
  end

end
