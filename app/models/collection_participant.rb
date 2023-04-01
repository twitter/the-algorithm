class CollectionParticipant < ApplicationRecord
  belongs_to :pseud
  has_one :user, through: :pseud
  belongs_to :collection

  PARTICIPANT_ROLES = ["None", "Owner", "Moderator", "Member", "Invited"]
  NONE = PARTICIPANT_ROLES[0]
  OWNER = PARTICIPANT_ROLES[1]
  MODERATOR = PARTICIPANT_ROLES[2]
  MEMBER = PARTICIPANT_ROLES[3]
  INVITED = PARTICIPANT_ROLES[4]
  MAINTAINER_ROLES = [PARTICIPANT_ROLES[1], PARTICIPANT_ROLES[2]]
  PARTICIPANT_ROLE_OPTIONS = [ [ts("None"), NONE],
                         [ts("Invited"), INVITED],
                         [ts("Member"), MEMBER],
                         [ts("Moderator"), MODERATOR],
                         [ts("Owner"), OWNER] ]

  validates_uniqueness_of :pseud_id, scope: [:collection_id],
    message: ts("That person appears to already be a participant in that collection.")

  validates_presence_of :participant_role
  validates_inclusion_of :participant_role, in: PARTICIPANT_ROLES,
    message: ts("That is not a valid participant role.")

  scope :for_user, lambda {|user|
    select("DISTINCT collection_participants.*").
    joins(pseud: :user).
    where('users.id = ?', user.id)
  }

  scope :in_collection, lambda {|collection|
    select("DISTINCT collection_participants.*").
    joins(:collection).
    where('collections.id = ?', collection.id)
  }

  def is_owner? ; self.participant_role == OWNER ; end
  def is_moderator? ; self.participant_role == MODERATOR ; end
  def is_maintainer? ; is_owner? || is_moderator? ; end
  def is_member? ; self.participant_role == MEMBER ; end
  def is_invited? ; self.participant_role == INVITED ; end
  def is_none? ; self.participant_role == NONE ; end

  def approve_membership!
    self.participant_role = MEMBER
    save
  end

  def user_allowed_to_destroy?(user)
    self.collection.user_is_maintainer?(user) || self.pseud.user == user
  end

  def user_allowed_to_promote?(user, role)
    (role == MEMBER || role == NONE) ? self.collection.user_is_maintainer?(user) : self.collection.user_is_owner?(user)
  end
end
