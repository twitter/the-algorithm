namespace :defaults do
  desc "Create default roles by name"
  task(create_roles: :environment) do
    %w[archivist no_resets opendoors protected_user tag_wrangler translator official].each do |role|
      Role.find_or_create_by(name: role)
    end
  end
end
