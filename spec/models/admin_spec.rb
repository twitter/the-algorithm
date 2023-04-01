require 'spec_helper'

describe Admin, :ready do
  it "can be created" do
    expect(create(:admin)).to be_valid
  end

  it "with a strange email address" do
    expect(create(:admin, email: "!def!xyz%abc@example.com")).to be_valid
  end

  it "sets reset_password_token" do
    admin = create(:admin)
    expect(admin.reset_password_token).not_to be_nil
  end

  it "sets reset_password_sent_at" do
    admin = create(:admin)
    expect(admin.reset_password_sent_at).not_to be_nil
  end

  it "sends set_password_notification to admin email" do
    expect { create(:admin, email: "test@example.com") }.to \
      change { ActionMailer::Base.deliveries.count }.by(1)
    expect(ActionMailer::Base.deliveries.last.recipients).to \
      eq(["test@example.com"])
    expect(ActionMailer::Base.deliveries.last.subject).to \
      eq("[AO3] Your AO3 admin account")
  end

  context "invalid" do
    it "without a user name" do
      expect { create(:admin, login: nil) }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Login can't be blank, Login is too short (minimum is #{ArchiveConfig.LOGIN_LENGTH_MIN} characters)")
    end

    it "without an invalid email address" do
      expect { create(:admin, email: "james_example.org") }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Email is invalid")
    end

    it "without an email address" do
      expect { create(:admin, email: nil) }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Email can't be blank")
    end

    it "without a password" do
      expect { create(:admin, password: nil) }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Password can't be blank, Password confirmation can't be blank")
    end

    it "without a password confirmation" do
      expect { create(:admin, password_confirmation: nil) }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Password confirmation can't be blank")
    end
  end

  context "length of login" do
    it "if under #{ArchiveConfig.LOGIN_LENGTH_MIN} long characters" do
      expect { create(:admin, login: Faker::Lorem.characters(number: ArchiveConfig.LOGIN_LENGTH_MIN - 1)) }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Login is too short (minimum is #{ArchiveConfig.LOGIN_LENGTH_MIN} characters)")
    end

    it "is invalid if over #{ArchiveConfig.LOGIN_LENGTH_MAX + 1} characters" do
      expect { create(:admin, login: Faker::Lorem.characters(number: ArchiveConfig.LOGIN_LENGTH_MAX + 1)) }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Login is too long (maximum is #{ArchiveConfig.LOGIN_LENGTH_MAX} characters)")
    end
  end

  context "length of password" do
    it "is invalid if under #{ArchiveConfig.ADMIN_PASSWORD_LENGTH_MIN - 1} characters" do
      expect { create(:admin, password: Faker::Lorem.characters(number: ArchiveConfig.ADMIN_PASSWORD_LENGTH_MIN - 1)) }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Password is too short (minimum is #{ArchiveConfig.ADMIN_PASSWORD_LENGTH_MIN} characters)")
    end

    it "is invalid if over #{ArchiveConfig.ADMIN_PASSWORD_LENGTH_MAX + 1} characters" do
      expect { create(:admin, password: Faker::Lorem.characters(number: ArchiveConfig.ADMIN_PASSWORD_LENGTH_MAX + 1)) }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Password is too long (maximum is #{ArchiveConfig.ADMIN_PASSWORD_LENGTH_MAX} characters)")
    end
  end

  context "uniqueness" do
    let(:existing_user) { create(:admin) }

    it "is invalid if login is not unique" do
      expect { create(:admin, login: existing_user.login) }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Login has already been taken")
    end

    it "is invalid if email already exists" do
      expect { create(:admin, email: existing_user.email) }.to \
        raise_error(ActiveRecord::RecordInvalid, \
                    "Validation failed: Email has already been taken")
    end
  end

  describe "admin roles" do
    context "has no roles by default" do
      it "has no roles when initially created" do
        admin = create(:admin)
        expect(admin.roles).to eq([])
      end
    end

    context "valid roles" do
      it "can be assigned a valid role" do
        admin = create(:admin)
        expect(admin.update(roles: ["superadmin"])).to be_truthy
      end
    end

    context "invalid roles" do
      it "cannot be assigned invalid role" do
        admin = create(:admin)

        expect { admin.update!(roles: ["fake_role"]) }.to \
          raise_error(ActiveRecord::RecordInvalid, \
                      "Validation failed: Roles is invalid")
      end
    end
  end
end
