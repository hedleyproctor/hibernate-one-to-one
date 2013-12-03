Hibernate one-to-one example
============================

This is an example of the three different ways of creating a one-to-one association between two entities. It contains a Customer entity, which has one-to-one associations to a UserProfile, MarketingPreferences and a Wistlist.

1. Foreign key relationship. Customer has a foreign key to UserProfile.
2. Shared primary key. The MarketingPreferences entity is set to use the same primary key as the customer, so its primary key is also a foreign key to the customer.
3. Join table. Customer and wishlist are linked by a join table, appropriate if the association is optional.