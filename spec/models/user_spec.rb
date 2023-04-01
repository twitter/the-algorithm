require "spec_helper"

describe User do
  describe "#destroy" do
    context "on a user with kudos" do
      let(:user) { create(:user) }
      let!(:kudo_bundle) { create_list(:kudo, 2, user: user) }

      it "removes user from kudos" do
        user.destroy!
        kudo_bundle.each do |kudo|
          kudo.reload
          expect(kudo.user).to be_nil
          expect(kudo.user_id).to be_nil
        end
      end
    end
  end

  describe "#save" do
    context "on a valid user" do
      let(:user) { build(:user) }

      it "saves without errors" do
        expect(user.save).to be_truthy
      end

      it "encrypts password" do
        user.save
        expect(user.encrypted_password).not_to be_empty
        expect(user.encrypted_password).not_to eq(user.password)
      end

      it "creates default associations" do
        user.save
        expect(user.profile).not_to be_nil
        expect(user.preference).not_to be_nil
        expect(user.pseuds.size).to eq(1)
        expect(user.pseuds.first.name).to eq(user.login)
        expect(user.pseuds.first.is_default).to be_truthy
      end
    end

    describe "on an invalid user" do
      context "missing the age_over_13 flag" do
        let(:no_age_over_13) { build(:user, age_over_13: "0") }

        it "does not save" do
          expect(no_age_over_13.save).to be_falsey
          expect(no_age_over_13.errors[:age_over_13].first).to include("you have to be over 13!")
        end
      end

      context "missing the terms_of_service flag" do
        let(:no_tos) { build(:user, terms_of_service: "0") }

        it "does not save" do
          expect(no_tos.save).to be_falsey
          expect(no_tos.errors[:terms_of_service].first).to include("you need to accept the Terms")
        end
      end

      context "with login too short" do
        let(:login_short) { build(:user, login: Faker::Lorem.characters(number: ArchiveConfig.LOGIN_LENGTH_MIN - 1)) }

        it "does not save" do
          expect(login_short.save).to be_falsey
          expect(login_short.errors[:login].first).to include("is too short")
        end
      end

      context "with login too long" do
        let(:login_long) { build(:user, login: Faker::Lorem.characters(number: ArchiveConfig.LOGIN_LENGTH_MAX + 1)) }

        it "does not save" do
          expect(login_long.save).to be_falsey
          expect(login_long.errors[:login].first).to include("is too long")
        end
      end

      BAD_EMAILS.each do |email|
        context "with email #{email}" do
          let(:bad_email) { build(:user, email: email) }

          it "does not save" do
            expect(bad_email.save).to be_falsey
            expect(bad_email.errors[:email]).to include("should look like an email address.")
            expect(bad_email.errors[:email]).to include("does not seem to be a valid address.")
          end
        end
      end

      context "with password too short" do
        let(:password_short) { build(:user, password: Faker::Lorem.characters(number: ArchiveConfig.PASSWORD_LENGTH_MIN - 1)) }

        it "does not save" do
          expect(password_short.save).to be_falsey
          expect(password_short.errors[:password].first).to include("is too short")
        end
      end

      context "with password too long" do
        let(:password_long) { build(:user, password: Faker::Lorem.characters(number: ArchiveConfig.PASSWORD_LENGTH_MAX + 1)) }

        it "does not save" do
          expect(password_long.save).to be_falsey
          expect(password_long.errors[:password].first).to include("is too long")
        end
      end

      context "with existing users" do
        let(:existing_user) { create(:user) }
        let(:new_user) { build(:user) }

        it "does not save a duplicate login" do
          new_user.login = existing_user.login
          expect(new_user.save).to be_falsey
          expect(new_user.errors[:login].first).to include("has already been taken")
        end

        it "does not save a duplicate email" do
          new_user.email = existing_user.email
          expect(new_user.save).to be_falsey
          expect(new_user.errors[:email].first).to include("has already been taken")
        end

        it "does not save a duplicate email with different capitalization" do
          new_user.email = existing_user.email.capitalize
          expect(new_user.save).to be_falsey
          expect(new_user.errors[:email].first).to include("has already been taken")
        end
      end
    end
  end

  describe "#update" do
    let!(:existing_user) { create(:user) }

    it "sets renamed_at if username is changed" do
      freeze_time
      existing_user.update(login: "new_username")
      expect(existing_user.renamed_at).to eq(Time.current)
    end

    context "username was recently changed" do
      before do
        freeze_time
        existing_user.update(login: "new_login")
      end

      it "does not allow another rename" do
        expect(existing_user.update(login: "new")).to be_falsey
        localized_renamed_at = I18n.l(existing_user.renamed_at, format: :long)
        expect(existing_user.errors[:login].first).to eq(
          "can only be changed once every 7 days. You last changed your user name on #{localized_renamed_at}."
        )
      end

      it "allows changing email" do
        existing_user.update(email: "new_email")
        expect(existing_user.email).to eq("new_email")
      end
    end

    context "username was changed outside window" do
      before do
        travel_to ArchiveConfig.USER_RENAME_LIMIT_DAYS.days.ago do
          existing_user.update(login: "new_username")
        end
      end

      it "allows another rename" do
        expect(existing_user.update(login: "new")).to be_truthy
        expect(existing_user.login).to eq("new")
      end
    end

    context "when email is changed" do
      before do
        existing_user.update!(email: "newemail@example.com")
        existing_user.reload
      end

      it "does not set renamed_at" do
        expect(existing_user.renamed_at).to be_nil
      end

      it "creates a log item" do
        log_item = existing_user.log_items.last
        expect(log_item.action).to eq(ArchiveConfig.ACTION_NEW_EMAIL)
        expect(log_item.admin_id).to be_nil
        expect(log_item.note).to eq("System Generated")
      end
    end

    context "as an admin" do
      let(:admin) { create(:admin) }

      before do
        User.current_user = admin
        existing_user.update!(email: "new_email@example.com")
        existing_user.reload
      end

      it "saves an admin log item" do
        log_item = existing_user.log_items.last
        expect(log_item.action).to eq(ArchiveConfig.ACTION_NEW_EMAIL)
        expect(log_item.admin_id).to eq(admin.id)
        expect(log_item.note).to eq("Change made by #{admin.login}")
      end
    end
  end

  describe ".search_multiple_by_email" do
    let(:user_bundle) { create_list(:user, 5) }

    it "removes exact duplicates from the list" do
      emails = user_bundle.map(&:email) << user_bundle.first.email
      expect(emails.size).to be > user_bundle.size
      expect(User.search_multiple_by_email(emails).first.size).to eq(emails.size - 1)
    end

    it "ignores case differences" do
      emails = user_bundle.map(&:email) << user_bundle.first.email.upcase
      expect(emails.size).to be > user_bundle.size
      expect(User.search_multiple_by_email(emails).first.size).to eq(emails.size - 1)
    end

    it "returns found users, not found emails and the number of duplicates" do
      more_emails = [user_bundle.second.email, user_bundle.first.email.upcase, "unknown@ao3.org", "UnKnown@AO3.org", "nobody@example.com"]
      emails = user_bundle.map(&:email) + more_emails

      found, not_found, duplicates = User.search_multiple_by_email(emails)

      expect(not_found).to eq(["unknown@ao3.org", "nobody@example.com"])
      expect(found.size).to eq(emails.map(&:downcase).uniq.size - not_found.size)
      expect(duplicates).to eq(3)
    end
  end
end
