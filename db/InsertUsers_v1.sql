BEGIN TRANSACTION;
insert into User ( userName, userEmail, userRole, userToken, userStatus, userLastUpdate ) 
values  ('Pat', 'patUser@gmail.com', 'admin', 'user', '1', datetime('now'));
COMMIT;
