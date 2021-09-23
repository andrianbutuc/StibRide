BEGIN TRANSACTION;
DROP TABLE IF EXISTS `FAVORITE`;
CREATE TABLE IF NOT EXISTS `FAVORITE` (
	`id` INTEGER PRIMARY KEY AUTOINCREMENT,
	`name` TEXT NOT NULL,
	`id_origin`	INTEGER NOT NULL,
	`id_destination` INTEGER NOT NULL,
	CONSTRAINT fk_origin FOREIGN KEY(`id_origin`) REFERENCES `STATIONS`(`id`),
	CONSTRAINT fk_destination FOREIGN KEY(`id_destination`) REFERENCES `STATIONS`(`id`)
);

INSERT INTO `FAVORITE` (`name`,`id_origin`,`id_destination`)values("test1" ,8322,8432 ) ;
INSERT INTO `FAVORITE` (`name`,`id_origin`,`id_destination`)values("test2" ,8022,8042 ) ;
INSERT INTO `FAVORITE` (`name`,`id_origin`,`id_destination`)values("test3" ,8042,8072 ) ;

COMMIT;