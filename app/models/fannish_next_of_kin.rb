class FannishNextOfKin < ApplicationRecord
  belongs_to :user
  belongs_to :kin, class_name: "User"

  validates :user, :kin, :kin_email, presence: true

  def kin_name
    kin.try(:login)
  end
end
