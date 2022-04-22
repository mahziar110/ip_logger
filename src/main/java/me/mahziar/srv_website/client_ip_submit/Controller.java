package me.mahziar.srv_website.client_ip_submit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mahziar.srv_website.Configs;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;


@RestController
public class Controller {
    @GetMapping("/SubmitIp")
    public Response SubmitIp() throws IOException, ClassNotFoundException {
        Response response = new Response();

        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());

        Boolean result = Pattern.matches(
                "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$",
                ip
        );
        if (!result) {
            response.result = false;
            return response;
        }

        String urlString = "https://ipinfo.io/" + ip + "?token=" + Configs.ipinfoToken;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(is);

        String country = json.get("country").toString().replaceAll("\"", "");
        String city = json.get("city").toString().replaceAll("\"", "");
        String location = json.get("loc").toString().replaceAll("\"", "");
        String org = json.get("org").toString().replaceAll("\"", "");

        Map<String, String> ipInfo = new HashMap<String, String>();
        ipInfo.put("ip", ip);
        ipInfo.put("country", country);
        ipInfo.put("city", city);
        ipInfo.put("location", location);
        ipInfo.put("org", org);

        Model.SubmitIpInDB(ipInfo);

        response.result = true;
        return response;
    }
}

class Response {
    public Boolean result;
}