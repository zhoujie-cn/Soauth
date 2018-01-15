package openid.connect;

import com.soauth.core.openid.connect.JwksCacheService;
import com.soauth.core.vo.oauth2.AbstractOIDC;
import org.testng.annotations.Test;

/**
 * Created by zhoujie  on 2017/11/22.
 */
public class TestJwksCacheService {

    @Test
    public void testJwksCacheService(){
        JwksCacheService jwks= new JwksCacheService();

        String test= (String) jwks.getPublicKey("http://localhost:8000/SoauthServer/jwks");

        System.out.print(test);
    }

    /**
     * 测试一下enum
     */
    @Test
    public void TestEnum(){
        final String oidcPage = AbstractOIDC.Display.PAGE.getDisplay();
         System.out.println(oidcPage);
    }
}
