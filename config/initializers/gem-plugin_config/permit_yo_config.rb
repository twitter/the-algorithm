# Authorization plugin configuration goes here
#
# See http://github.com/ianterrell/permityo/tree/ Settings section for details on what can be configured,
# although most defaults are sensible.

module Otwarchive
  class Application < Rails::Application

    # Which flash key we stick error messages into
    config.permit_yo.require_user_flash = :error
    config.permit_yo.permission_denied_flash = :error

    # Where users get redirected if they are not currently logged in
    config.permit_yo.require_user_redirection = {controller: :user_sessions, action: :new}
  end
end

module PermitYo
  module Default
    module UserExtensions
      module InstanceMethods

        # Determine if the current model has a particular role
        # depends on the model having a relationship with roles! (eg, has_and_belongs_to_many :roles)
        def has_role?(role_name)
          return self.roles.any? { |role| role.name == role_name.to_s } if self.roles.loaded?

          role = Role.find_by(name: role_name)
          self.roles.include?(role)
        end
      end
    end
  end
end
