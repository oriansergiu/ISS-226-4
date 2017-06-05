INSERT INTO `conference`(`id`,`name`) VALUES (1, 'DEVTALKS CLUJ');
INSERT INTO `conferencesession`(`id`, `abstractDeadline`, `endDate`, `feeValue`, `proposalDeadline`, `startDate`) VALUES (1, '2017-10-12', '2017-12-12', 220.00000, '2017-10-22', '2017-08-15');
INSERT INTO `paper` (`id`, `attachment`, `coAuthors`, `keywords`, `title`, `topic`, `author_id`, `paperAbstract_id`) VALUES ('1', NULL, 'Raul Paros', 'science;database;nosql', 'NOSQL Transactions', 'Databases', '1', '1');
INSERT INTO `abstract` (`id`, `text`, `paper_id`) VALUES ('1', 'Test abstract text', '1');