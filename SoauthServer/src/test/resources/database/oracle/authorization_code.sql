--------------------------------------------------------
--  文件已创建 - 星期三-一月-10-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table AUTHORIZATION_CODE
--------------------------------------------------------

  CREATE TABLE "AUTHORIZATION_CODE" 
   (	"CODE" VARCHAR2(200 BYTE), 
	"AUTH_HOLDER" VARCHAR2(150 BYTE), 
	"EXPIRATION" DATE, 
	"CLIENT_ID" VARCHAR2(200 BYTE), 
	"CREATE_TIME" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into AUTHORIZATION_CODE
SET DEFINE OFF;
--------------------------------------------------------
--  Constraints for Table AUTHORIZATION_CODE
--------------------------------------------------------

  ALTER TABLE "AUTHORIZATION_CODE" MODIFY ("CODE" NOT NULL ENABLE);
 
  ALTER TABLE "AUTHORIZATION_CODE" MODIFY ("CLIENT_ID" NOT NULL ENABLE);
