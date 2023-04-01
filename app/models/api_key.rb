class ApiKey < ApplicationRecord
  validates :name, presence: true, uniqueness: true
  validates :access_token, presence: true, uniqueness: true

  before_validation(on: :create) do
    self.access_token = SecureRandom.hex
  end
end
