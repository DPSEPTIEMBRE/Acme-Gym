start transaction

use `Acme-Gym`;

revoke all privileges on Acme-Gym`.* from 'acme-user'@'%';
revoke all privileges on Acme-Gym`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-Gym`;

commit;