--------------------------------------------------------
--  文件已创建 - 星期三-一月-10-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ROLES_PERMISSIONS
--------------------------------------------------------

  CREATE TABLE "ROLES_PERMISSIONS" 
   (	"RID" NUMBER(20,0), 
	"PID" NUMBER(20,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into ROLES_PERMISSIONS
SET DEFINE OFF;
Insert into ROLES_PERMISSIONS (RID,PID) values (2,2);
Insert into ROLES_PERMISSIONS (RID,PID) values (22,0);
Insert into ROLES_PERMISSIONS (RID,PID) values (21,0);
