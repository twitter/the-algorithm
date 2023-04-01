class ChallengeClaim < ApplicationRecord
  belongs_to :claiming_user, class_name: "User", inverse_of: :request_claims
  belongs_to :collection
  belongs_to :request_signup, class_name: "ChallengeSignup"
  belongs_to :request_prompt, class_name: "Prompt"
  belongs_to :creation, polymorphic: true

  before_create :inherit_fields_from_request_prompt
  def inherit_fields_from_request_prompt
    return unless request_prompt

    self.collection = request_prompt.collection
    self.request_signup = request_prompt.challenge_signup
  end

  scope :for_request_signup, lambda {|signup|
    where('request_signup_id = ?', signup.id)
  }

  scope :by_claiming_user, lambda {|user|
    select('DISTINCT challenge_claims.*')
    .joins("INNER JOIN users ON challenge_claims.claiming_user_id = users.id")
    .where('users.id = ?', user.id)
  }

  scope :in_collection, lambda {|collection|
    where('challenge_claims.collection_id = ?', collection.id)
  }

  scope :with_request, -> { where('request_signup IS NOT NULL') }
  scope :with_no_request, -> { where('request_signup_id IS NULL') }

  REQUESTING_PSEUD_JOIN = "INNER JOIN challenge_signups ON (challenge_claims.request_signup_id = challenge_signups.id)
                           INNER JOIN pseuds ON challenge_signups.pseud_id = pseuds.id"

  CLAIMING_PSEUD_JOIN = "INNER JOIN users ON challenge_claims.claiming_user_id = users.id"

  COLLECTION_ITEMS_JOIN = "INNER JOIN collection_items ON (collection_items.collection_id = challenge_claims.collection_id AND
                                                           collection_items.item_id = challenge_claims.creation_id AND
                                                           collection_items.item_type = challenge_claims.creation_type)"

  COLLECTION_ITEMS_LEFT_JOIN =  "LEFT JOIN collection_items ON (collection_items.collection_id = challenge_claims.collection_id AND
                                                                collection_items.item_id = challenge_claims.creation_id AND
                                                                collection_items.item_type = challenge_claims.creation_type)"


  scope :order_by_date, -> { order("created_at ASC") }

  def self.order_by_requesting_pseud(dir="ASC")
    if dir.casecmp("ASC").zero?
      joins(REQUESTING_PSEUD_JOIN).order("pseuds.name ASC")
    else
      joins(REQUESTING_PSEUD_JOIN).order("pseuds.name DESC")
    end
  end

  def self.order_by_offering_pseud(dir="ASC")
    if dir.casecmp("ASC").zero?
      joins(CLAIMING_PSEUD_JOIN).order("pseuds.name ASC")
    else
      joins(CLAIMING_PSEUD_JOIN).order("pseuds.name DESC")
    end
  end

  WORKS_JOIN = "INNER JOIN works ON works.id = challenge_claims.creation_id AND challenge_claims.creation_type = 'Work'"
  WORKS_LEFT_JOIN = "LEFT JOIN works ON works.id = challenge_claims.creation_id AND challenge_claims.creation_type = 'Work'"

  scope :fulfilled, -> {
    joins(COLLECTION_ITEMS_JOIN).joins(WORKS_JOIN)
      .where("challenge_claims.creation_id IS NOT NULL AND collection_items.user_approval_status = ? AND collection_items.collection_approval_status = ? AND works.posted = 1",
             CollectionItem.user_approval_statuses[:approved], CollectionItem.collection_approval_statuses[:approved])
  }


  scope :posted, -> { joins(WORKS_JOIN).where("challenge_claims.creation_id IS NOT NULL AND works.posted = 1") }

  # should be faster than unfulfilled scope because no giant left joins
  def self.unfulfilled_in_collection(collection)
    fulfilled_ids = ChallengeClaim.in_collection(collection).fulfilled.pluck(:id)
    fulfilled_ids.empty? ? in_collection(collection) : in_collection(collection).where("challenge_claims.id NOT IN (?)", fulfilled_ids)
  end

  # faster than unposted scope because no left join!
  def self.unposted_in_collection(collection)
    posted_ids = ChallengeClaim.in_collection(collection).posted.pluck(:id)
    posted_ids.empty? ? in_collection(collection) : in_collection(collection).where("challenge_claims.creation_id IS NULL OR challenge_claims.id NOT IN (?)", posted_ids)
  end

  # has to be a left join to get works that don't have a collection item
  scope :unfulfilled, -> {
    joins(COLLECTION_ITEMS_LEFT_JOIN).joins(WORKS_LEFT_JOIN)
      .where("challenge_claims.creation_id IS NULL OR collection_items.user_approval_status != ? OR collection_items.collection_approval_status != ? OR works.posted = 0",
             CollectionItem.user_approval_statuses[:approved], CollectionItem.collection_approval_statuses[:approved])
  }

  # ditto
  scope :unposted, -> { joins(WORKS_LEFT_JOIN).where("challenge_claims.creation_id IS NULL OR works.posted = 0") }

  scope :unstarted, -> { where("challenge_claims.creation_id IS NULL") }

  def self.unposted_for_user(user)
    all_claims = ChallengeClaim.by_claiming_user(user)
    posted_ids = all_claims.posted.pluck(:id)
    all_claims.where("challenge_claims.id NOT IN (?)", posted_ids)
  end


  def get_collection_item
    return nil unless self.creation
    CollectionItem.where('collection_id = ? AND item_id = ? AND item_type = ?', self.collection_id, self.creation_id, self.creation_type).first
  end

  def fulfilled?
    self.creation && (item = get_collection_item) && item.approved?
  end

  def title
    if !self.request_prompt.title.blank?
      title = request_prompt.title
    else
      title = ts("Untitled Prompt")
    end
    title += " " + ts("in") + " #{self.collection.title}"
    if self.request_prompt.anonymous?
      title += " " + ts("(Anonymous)")
    else
      title += " (#{self.request_byline})"
    end
    return title
  end

  def claiming_pseud
    claiming_user.try(:default_pseud)
  end

  def requesting_pseud
    request_signup ? request_signup.pseud : nil
  end

  def claim_byline
    claiming_pseud.try(:byline) || "deleted user"
  end

  def request_byline
    request_signup ? request_signup.pseud.byline : "- None -"
  end

  def user_allowed_to_destroy?(current_user)
    (self.claiming_user == current_user) || self.collection.user_is_maintainer?(current_user)
  end

  def prompt_description
    request_prompt&.description || ""
  end
end
