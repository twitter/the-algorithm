class ExternalAuthorName < ApplicationRecord
  NAME_LENGTH_MIN = 1
  NAME_LENGTH_MAX = 100

  belongs_to :external_author, inverse_of: :external_author_names
  has_many :external_creatorships, inverse_of: :external_author_name
  has_many :works, -> { uniq }, through: :external_creatorships, source: :creation, source_type: 'Work'

  validates_presence_of :name

  validates_length_of :name,
    within: NAME_LENGTH_MIN..NAME_LENGTH_MAX,
    too_short: ts("is too short (minimum is %{min} characters)", min: NAME_LENGTH_MIN),
    too_long: ts("is too long (maximum is %{max} characters)", max: NAME_LENGTH_MAX)

  validates :name, uniqueness: { scope: :external_author_id }

  validates_format_of :name,
    message: ts('can contain letters, numbers, spaces, underscores, @-signs, dots, and dashes.'),
    with: /\A\w[ \w\-\@\.]*\Z/

  validates_format_of :name,
    message: ts('must contain at least one letter or number.'),
    with: /[a-zA-Z0-9]/

  def to_s
    self.name + ' <' + self.external_author.email + '>'
  end

  def external_work_creatorships
    external_creatorships.where("external_creatorships.creation_type = 'Work'")
  end

end
