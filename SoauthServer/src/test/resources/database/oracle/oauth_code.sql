--------------------------------------------------------
--  文件已创建 - 星期三-一月-10-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table OAUTH_CODE
--------------------------------------------------------

  CREATE TABLE "OAUTH_CODE" 
   (	"CREATE_TIME" DATE, 
	"CODE" VARCHAR2(200 BYTE), 
	"USERNAME" VARCHAR2(200 BYTE), 
	"CLIENT_ID" VARCHAR2(200 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into OAUTH_CODE
SET DEFINE OFF;
Insert into OAUTH_CODE (CREATE_TIME,CODE,USERNAME,CLIENT_ID) values (to_date('03-1月 -18','DD-MON-RR'),'b52789914ed53716b149b9cecb765f4d','test','test');
--------------------------------------------------------
--  Constraints for Table OAUTH_CODE
--------------------------------------------------------

  ALTER TABLE "OAUTH_CODE" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
 
  ALTER TABLE "OAUTH_CODE" MODIFY ("CODE" NOT NULL ENABLE);
 
  ALTER TABLE "OAUTH_CODE" MODIFY ("USERNAME" NOT NULL ENABLE);
 
  ALTER TABLE "OAUTH_CODE" MODIFY ("CLIENT_ID" NOT NULL ENABLE);
