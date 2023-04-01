class AddPrimaryKeyToSchemaMigrations < ActiveRecord::Migration[6.0]
  # By default Rails 6 creates the schema_migrations table with its column
  # "version" as the primary key, but we're missing that in staging and
  # production.
  #
  # Recently created development or test databases (e.g. via Docker) are
  # not affected.
  def up
    return unless Rails.env.staging? || Rails.env.production?

    execute "ALTER TABLE schema_migrations ADD PRIMARY KEY (version);"
  end

  def down
    return unless Rails.env.staging? || Rails.env.production?

    execute "ALTER TABLE schema_migrations DROP PRIMARY KEY;"
  end
end
