-- Ensure boolean soft-delete flag can be added safely on an existing database.
-- Hibernate's automatic ALTER TABLE can fail for a new NOT NULL boolean column
-- when portal_users already contains rows.
ALTER TABLE IF EXISTS portal_users
  ADD COLUMN IF NOT EXISTS archived BOOLEAN NOT NULL DEFAULT FALSE;
