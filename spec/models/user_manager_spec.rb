require "spec_helper"

describe UserManager do
  describe "#save" do
    let(:admin) { create(:admin) }
    let(:user) { create(:user) }
    let(:orphan) { create(:user, login: "orphan_account") }

    it "returns error without user" do
      manager = UserManager.new(admin, nil, {})
      expect(manager.save).to be_falsey
      expect(manager.errors).to eq ["Must have a valid user and admin account to proceed."]
    end

    it "returns error if user is orphan_account" do
      manager = UserManager.new(admin, orphan, admin_action: "suspend", suspend_days: "7")
      expect(manager.save).to be_falsey
      expect(manager.errors).to eq ["orphan_account cannot be warned, suspended, or banned."]
    end
    
    it "does nothing without an admin action" do
      manager = UserManager.new(admin, user, {})
      expect do
        manager.save
      end.to avoid_changing { user.reload.updated_at }
        .and avoid_changing { user.reload.log_items.count }
      expect(manager.save).to be_truthy
      expect(manager.successes).to be_empty
    end

    it "returns error if notes are missing when suspending" do
      manager = UserManager.new(admin, user, admin_action: "suspend", suspend_days: "7")
      expect(manager.save).to be_falsey
      expect(manager.errors).to eq ["You must include notes in order to perform this action."]
    end

    it "returns error for suspension without time span" do
      manager = UserManager.new(admin, user, admin_action: "suspend", admin_note: "User violated community guidelines")
      expect(manager.save).to be_falsey
      expect(manager.errors).to eq ["Please enter the number of days for which the user should be suspended."]
    end

    it "returns error for invalid admin actions" do
      manager = UserManager.new(admin, user, admin_action: "something_wicked")
      expect(manager.save).to be_falsey
    end

    it "succeeds in suspending user" do
      manager = UserManager.new(admin, user, admin_action: "suspend", suspend_days: "5", admin_note: "User violated community guidelines")
      expect(manager.save).to be_truthy
      expect(manager.successes).to eq ["User has been temporarily suspended."]
    end

    it "succeeds in banning user" do
      manager = UserManager.new(admin, user, admin_action: "ban", admin_note: "User violated community guidelines")
      expect(manager.save).to be_truthy
      expect(manager.successes).to eq ["User has been permanently suspended."]
    end

    it "succeeds in unsuspending user" do
      user.update(suspended: true, suspended_until: 4.days.from_now)
      manager = UserManager.new(admin, user, admin_action: "unsuspend", admin_note: "There was a mistake in the review process")
      expect(manager.save).to be_truthy
      expect(manager.successes).to eq ["Suspension has been lifted."]
    end

    it "succeeds in unbanning user" do
      user.update(banned: true)
      manager = UserManager.new(admin, user, admin_action: "unban", admin_note: "There was a mistake in the review process")
      expect(manager.save).to be_truthy
      expect(manager.successes).to eq ["Suspension has been lifted."]
    end
  end
end
