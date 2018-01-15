# Soauth
Soauth是一款基于OIDC的认证授权框架，整合jose4j, Apache Shiro, Apache oltu实现OIDC.  Soauth遵循模块化设计理念。 项目分为三个模块.

*core 模块 : 项目核心模块，SoauthServer. Client模块中的共用实体对象， RP, OP 加密,解密对象。 项目共用utils.*

*SoauthServer 模块:  op端，也是框架的认证授权服务端和资源服务端。 使用grant_type获取签名后的access_token, id_token.*

*client模块: rp端, 框架实现的客户端，负责向SoauthServer模块请求Token, 验证逻辑. 以及加密token等操作.*



