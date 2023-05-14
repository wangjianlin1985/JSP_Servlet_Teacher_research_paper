/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50505
Source Host           : 127.0.0.1:3306
Source Database       : jsp_keyan

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2020-01-08 23:03:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `account` int(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1001', '管理员', 'admin');

-- ----------------------------
-- Table structure for `audit`
-- ----------------------------
DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `paperid` int(20) NOT NULL,
  `auditorid` int(20) NOT NULL,
  `status` varchar(30) NOT NULL,
  `time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE current_timestamp(),
  `views` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_admin` (`auditorid`),
  KEY `fk_paper` (`paperid`),
  CONSTRAINT `fk_admin` FOREIGN KEY (`auditorid`) REFERENCES `admin` (`account`) ON UPDATE CASCADE,
  CONSTRAINT `fk_paper` FOREIGN KEY (`paperid`) REFERENCES `paper` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of audit
-- ----------------------------
INSERT INTO `audit` VALUES ('117', '1000059', '1001', '审核通过', '2019-08-25 00:00:00', '不错');
INSERT INTO `audit` VALUES ('118', '1000060', '1001', '未通过', '2019-08-25 00:00:00', '');
INSERT INTO `audit` VALUES ('119', '1000064', '1001', '未审核', '2020-01-01 00:00:00', '');

-- ----------------------------
-- Table structure for `firstsub`
-- ----------------------------
DROP TABLE IF EXISTS `firstsub`;
CREATE TABLE `firstsub` (
  `id` int(20) NOT NULL,
  `firstsubname` varchar(20) NOT NULL,
  `subpartid` int(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fs` (`subpartid`),
  CONSTRAINT `fk_fs` FOREIGN KEY (`subpartid`) REFERENCES `subpart` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of firstsub
-- ----------------------------
INSERT INTO `firstsub` VALUES ('2001', '计算机科学与技术', '1001');
INSERT INTO `firstsub` VALUES ('2002', '物理学', '1002');

-- ----------------------------
-- Table structure for `grade`
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `teacherid` int(20) NOT NULL,
  `pubtime` int(20) NOT NULL,
  `partid1` int(20) NOT NULL DEFAULT 3001,
  `num1` int(20) NOT NULL DEFAULT 0,
  `partid2` int(20) NOT NULL DEFAULT 3002,
  `num2` int(20) NOT NULL DEFAULT 0,
  `partid3` int(20) NOT NULL DEFAULT 3003,
  `num3` int(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`teacherid`,`pubtime`),
  CONSTRAINT `fk_gt` FOREIGN KEY (`teacherid`) REFERENCES `teacher` (`empnum`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('800111', '2019', '3001', '0', '3002', '0', '3003', '0');

-- ----------------------------
-- Table structure for `journal`
-- ----------------------------
DROP TABLE IF EXISTS `journal`;
CREATE TABLE `journal` (
  `id` int(20) NOT NULL,
  `journalname` varchar(50) NOT NULL,
  `pubpartid` int(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_jpub` (`pubpartid`),
  CONSTRAINT `fk_jpub` FOREIGN KEY (`pubpartid`) REFERENCES `pubpart` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of journal
-- ----------------------------
INSERT INTO `journal` VALUES ('4001', '自然杂志', '5001');
INSERT INTO `journal` VALUES ('4002', '农艺博览', '5002');
INSERT INTO `journal` VALUES ('4003', '软件技术周刊', '5003');

-- ----------------------------
-- Table structure for `major`
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major` (
  `id` int(20) unsigned NOT NULL,
  `majorName` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES ('280611', '计算机科学与技术');
INSERT INTO `major` VALUES ('280612', '软件工程');

-- ----------------------------
-- Table structure for `paper`
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) NOT NULL,
  `firstauthor` varchar(20) NOT NULL,
  `pubtime` date NOT NULL,
  `pubtypeid` int(20) NOT NULL,
  `journalid` int(20) NOT NULL,
  `subtypeid` int(20) NOT NULL,
  `firstsubid` int(20) NOT NULL,
  `prosourceid` int(20) NOT NULL,
  `teacherid` int(20) NOT NULL,
  `pubarea` varchar(20) DEFAULT NULL,
  `istrans` varchar(20) DEFAULT NULL,
  `layout` varchar(20) DEFAULT NULL COMMENT '版面',
  `fileurl` varchar(50) DEFAULT NULL,
  `mentorflag` varchar(20) NOT NULL,
  `auditflag` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pubtype` (`pubtypeid`),
  KEY `fk_journal` (`journalid`),
  KEY `fk_subtype` (`subtypeid`),
  KEY `fk_firstsub` (`firstsubid`),
  KEY `fk_prosource` (`prosourceid`),
  KEY `fk_teacher` (`teacherid`),
  CONSTRAINT `fk_firstsub` FOREIGN KEY (`firstsubid`) REFERENCES `firstsub` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_journal` FOREIGN KEY (`journalid`) REFERENCES `journal` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_prosource` FOREIGN KEY (`prosourceid`) REFERENCES `projectsource` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_pubtype` FOREIGN KEY (`pubtypeid`) REFERENCES `pubpart` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_subtype` FOREIGN KEY (`subtypeid`) REFERENCES `subpart` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_teacher` FOREIGN KEY (`teacherid`) REFERENCES `teacher` (`empnum`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1000066 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paper
-- ----------------------------
INSERT INTO `paper` VALUES ('1000059', '小区物业管理系统', '李晓彤', '2019-08-25', '5001', '4001', '1001', '2001', '3001', '800111', '国外学术期刊', '否', '正刊', '酒店预订管理系统论文34575.docx', '否', '审核通过');
INSERT INTO `paper` VALUES ('1000064', '酒店论文', '李晓彤', '2019-08-25', '5001', '4001', '1001', '2001', '3001', '800111', '国外学术期刊', '是', '正刊', '酒店预订管理系统论文34575.docx', '否', '未审核');
INSERT INTO `paper` VALUES ('1000065', '酒店管理论文', '李晓彤', '2019-08-25', '5001', '4001', '1001', '2001', '3001', '800111', '国外学术期刊', '是', '正刊', '酒店预订管理系统论文47602.docx', '否', '未审核');

-- ----------------------------
-- Table structure for `projectsource`
-- ----------------------------
DROP TABLE IF EXISTS `projectsource`;
CREATE TABLE `projectsource` (
  `id` int(20) NOT NULL,
  `sourcename` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of projectsource
-- ----------------------------
INSERT INTO `projectsource` VALUES ('3001', '学校推荐项目');
INSERT INTO `projectsource` VALUES ('3002', '省级项目');

-- ----------------------------
-- Table structure for `pubpart`
-- ----------------------------
DROP TABLE IF EXISTS `pubpart`;
CREATE TABLE `pubpart` (
  `id` int(20) NOT NULL,
  `pubpartname` varchar(30) NOT NULL,
  `grade` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pubpart
-- ----------------------------
INSERT INTO `pubpart` VALUES ('5001', '自然科学', '20');
INSERT INTO `pubpart` VALUES ('5002', '农学', '23');
INSERT INTO `pubpart` VALUES ('5003', '工学', '34');

-- ----------------------------
-- Table structure for `subpart`
-- ----------------------------
DROP TABLE IF EXISTS `subpart`;
CREATE TABLE `subpart` (
  `id` int(20) NOT NULL,
  `subpartname` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subpart
-- ----------------------------
INSERT INTO `subpart` VALUES ('1001', '工科');
INSERT INTO `subpart` VALUES ('1002', '理科');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `empnum` int(20) NOT NULL,
  `name` varchar(25) NOT NULL,
  `password` varchar(50) NOT NULL,
  `sex` varchar(10) NOT NULL,
  `majorId` int(20) unsigned NOT NULL,
  `titleid` int(20) NOT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`empnum`),
  KEY `fk_tt` (`titleid`) USING BTREE,
  KEY `fk_tm` (`majorId`),
  CONSTRAINT `fk_tm` FOREIGN KEY (`majorId`) REFERENCES `major` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_tt` FOREIGN KEY (`titleid`) REFERENCES `title` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('800111', '李晓彤', '123456', '男', '280611', '7003', '13022502404', '1983-06-23');

-- ----------------------------
-- Table structure for `title`
-- ----------------------------
DROP TABLE IF EXISTS `title`;
CREATE TABLE `title` (
  `id` int(20) NOT NULL,
  `titleName` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of title
-- ----------------------------
INSERT INTO `title` VALUES ('7003', '讲师');
INSERT INTO `title` VALUES ('7004', '副教授');
INSERT INTO `title` VALUES ('7005', '院长');

-- ----------------------------
-- View structure for `adminshow`
-- ----------------------------
DROP VIEW IF EXISTS `adminshow`;
CREATE   VIEW `adminshow` AS select `paper`.`id` AS `id`,`paper`.`teacherid` AS `teacherid`,`teacher`.`name` AS `name`,`teacher`.`majorId` AS `majorId`,`major`.`majorName` AS `majorName`,`paper`.`auditflag` AS `auditflag` from ((`paper` join `teacher` on(`paper`.`teacherid` = `teacher`.`empnum`)) join `major` on(`teacher`.`majorId` = `major`.`id`)) ;

-- ----------------------------
-- View structure for `auditquery`
-- ----------------------------
DROP VIEW IF EXISTS `auditquery`;
CREATE  VIEW `auditquery` AS select `audit`.`auditorid` AS `auditorid`,`audit`.`status` AS `status`,`audit`.`time` AS `time`,`audit`.`views` AS `views`,`admin`.`name` AS `name`,`audit`.`paperid` AS `paperid` from (`audit` join `admin` on(`audit`.`auditorid` = `admin`.`account`)) ;

-- ----------------------------
-- View structure for `countgrade`
-- ----------------------------
DROP VIEW IF EXISTS `countgrade`;
CREATE VIEW `countgrade` AS select `grade`.`teacherid` AS `teacherid`,`teacher`.`name` AS `name`,`major`.`majorName` AS `majorName`,`grade`.`pubtime` AS `pubtime`,`grade`.`partid1` AS `partid1`,`grade`.`num1` AS `num1`,`grade`.`partid2` AS `partid2`,`grade`.`num2` AS `num2`,`grade`.`partid3` AS `partid3`,`grade`.`num3` AS `num3`,`major`.`id` AS `id` from ((`grade` join `teacher` on(`grade`.`teacherid` = `teacher`.`empnum`)) join `major` on(`teacher`.`majorId` = `major`.`id`)) ;

-- ----------------------------
-- View structure for `majorechart`
-- ----------------------------
DROP VIEW IF EXISTS `majorechart`;
CREATE  VIEW `majorechart` AS select `paper`.`id` AS `id`,`paper`.`teacherid` AS `teacherid`,`teacher`.`name` AS `name`,`teacher`.`majorId` AS `majorId`,`major`.`majorName` AS `majorName`,`paper`.`auditflag` AS `auditflag`,substr(`paper`.`pubtime`,1,4) AS `time` from ((`paper` join `teacher` on(`paper`.`teacherid` = `teacher`.`empnum`)) join `major` on(`teacher`.`majorId` = `major`.`id`)) ;

-- ----------------------------
-- View structure for `pubechart`
-- ----------------------------
DROP VIEW IF EXISTS `pubechart`;
CREATE  VIEW `pubechart` AS select `paper`.`id` AS `id`,`paper`.`pubtypeid` AS `pubtypeid`,`pubpart`.`pubpartname` AS `pubpartname`,`paper`.`teacherid` AS `teacherid`,`teacher`.`name` AS `name`,`teacher`.`majorId` AS `majorId`,`major`.`majorName` AS `majorName`,substr(`paper`.`pubtime`,1,4) AS `time`,`paper`.`auditflag` AS `auditflag` from (((`paper` join `pubpart` on(`paper`.`pubtypeid` = `pubpart`.`id`)) join `teacher` on(`paper`.`teacherid` = `teacher`.`empnum`)) join `major` on(`teacher`.`majorId` = `major`.`id`)) ;

-- ----------------------------
-- View structure for `teachergrade`
-- ----------------------------
DROP VIEW IF EXISTS `teachergrade`;
CREATE VIEW `teachergrade` AS select `paper`.`teacherid` AS `teacherid`,`teacher`.`name` AS `name`,`major`.`majorName` AS `majorname`,`pubpart`.`pubpartname` AS `pubpartname`,substr(`paper`.`pubtime`,1,4) AS `time`,`paper`.`auditflag` AS `auditflag`,count(0) AS `num`,sum(`pubpart`.`grade`) AS `sumgrade` from (((`paper` join `pubpart`) join `major`) join `teacher`) where `paper`.`pubtypeid` = `pubpart`.`id` and `teacher`.`empnum` = `paper`.`teacherid` and `teacher`.`majorId` = `major`.`id` and `paper`.`auditflag` = '审核通过' group by `paper`.`teacherid`,`teacher`.`name`,`major`.`majorName`,`pubpart`.`pubpartname`,substr(`paper`.`pubtime`,1,4),`paper`.`auditflag` ;

-- ----------------------------
-- View structure for `teacherquerypaper`
-- ----------------------------
DROP VIEW IF EXISTS `teacherquerypaper`;
CREATE  VIEW `teacherquerypaper` AS select `paper`.`id` AS `id`,`paper`.`title` AS `title`,`paper`.`firstauthor` AS `firstauthor`,`paper`.`pubtime` AS `pubtime`,`paper`.`pubarea` AS `pubarea`,`paper`.`istrans` AS `istrans`,`paper`.`auditflag` AS `auditflag`,`projectsource`.`sourcename` AS `sourcename`,`pubpart`.`pubpartname` AS `pubpartname`,`subpart`.`subpartname` AS `subpartname`,`journal`.`journalname` AS `journalname`,`firstsub`.`firstsubname` AS `firstsubname`,`paper`.`teacherid` AS `teacherid`,`paper`.`prosourceid` AS `prosourceid`,`paper`.`firstsubid` AS `firstsubid`,`paper`.`subtypeid` AS `subtypeid`,`paper`.`journalid` AS `journalid`,`paper`.`pubtypeid` AS `pubtypeid`,`major`.`majorName` AS `majorName`,`paper`.`mentorflag` AS `mentorflag`,`paper`.`layout` AS `layout`,`paper`.`fileurl` AS `fileurl` from (((((((`paper` join `projectsource` on(`paper`.`prosourceid` = `projectsource`.`id`)) join `pubpart` on(`paper`.`pubtypeid` = `pubpart`.`id`)) join `subpart` on(`paper`.`subtypeid` = `subpart`.`id`)) join `journal` on(`journal`.`pubpartid` = `pubpart`.`id` and `paper`.`journalid` = `journal`.`id`)) join `firstsub` on(`firstsub`.`subpartid` = `subpart`.`id` and `paper`.`firstsubid` = `firstsub`.`id`)) join `teacher` on(`paper`.`teacherid` = `teacher`.`empnum`)) join `major` on(`teacher`.`majorId` = `major`.`id`)) ;
DROP TRIGGER IF EXISTS `Gradde_1`;
DELIMITER ;;
CREATE TRIGGER `Gradde_1` AFTER INSERT ON `audit` FOR EACH ROW BEGIN

declare teacherid1 int;
declare pubtime1 varchar(20);

declare partid1 int;
set teacherid1 =(select teacherid from paper where id = new.paperid);
set partid1 = (select pubtypeid from paper where id = new.paperid);
set pubtime1 =  substr((select pubtime from paper where id = new.paperid),1,4);

if  (new.status)='审核通过' THEN
if not exists(select * from grade where teacherid = teacherid1 and pubtime = pubtime1) THEN
insert into grade(teacherid,pubtime) values (teacherid1,pubtime1);
if (partid1)=3001 then 
update grade set num1 = 1 where teacherid = teacherid1 and pubtime = pubtime1;
end if;
 if (partid1)=3002 then 
update grade set num2 = 1 where teacherid = teacherid1 and pubtime = pubtime1;
end if;
 if (partid1)=3003 then 
update grade set num3 = 1 where teacherid = teacherid1 and pubtime = pubtime1;
end if;

else 
if (partid1)=3003 then 
update grade set num3 = 1+num3 where teacherid = teacherid1 and pubtime = pubtime1;
end if;
if (partid1)=3002 then 
update grade set num2 = 1+num2 where teacherid = teacherid1 and pubtime = pubtime1;
end if;
if (partid1)=3001 then 
update grade set num1 = 1+num1 where teacherid = teacherid1 and pubtime = pubtime1;
end if;
end if;




end if;

end
;;
DELIMITER ;
