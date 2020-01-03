package left.majiang.communlty.communlty.provider;
import com.alibaba.fastjson.JSON;
import left.majiang.communlty.communlty.dto.AccessTokenDTO;
import left.majiang.communlty.communlty.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
@Component
public class GithubProvider {
    public  String getAccessToken(AccessTokenDTO accessTokenDTO){
         MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            String tokenstr=string.split("&")[0].split("=")[1];
//            System.out.print(tokenstr);
            return tokenstr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public GithubUser getUser(String access_token) {
        OkHttpClient client = new OkHttpClient();
        GithubUser githubUser=null;
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+access_token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
//            System.out.print("string"+string);
            githubUser=JSON.parseObject(string,GithubUser.class);
//            System.out.print("git"+githubUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return githubUser;
    }
}
