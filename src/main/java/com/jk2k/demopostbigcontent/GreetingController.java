package com.jk2k.demopostbigcontent;

import com.google.gson.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/greeting/big-content-convert")
    public Greeting greetingBigContentConvert(@RequestBody String payload) throws Exception {
        writeFile("target/payload.json", payload);

        Gson gson = new Gson();
        BigData bigData = gson.fromJson(payload, BigData.class);
        writeFile("target/payload-form-urlencode.txt", objectToUrlEncodedString(bigData, gson));

        return new Greeting(counter.incrementAndGet(),
                payload);
    }

    private void writeFile(String filename, String content) throws Exception {
        String rawPath = new ClassPathResource(filename).getPath();
        Path path = Paths.get(rawPath);
        File temp;
        if (!Files.exists(path)) {
            // 不存在
            Files.createFile(path);
            temp = new File(rawPath);
        } else {
            temp = new File(rawPath);
        }

        FileWriter fw = new FileWriter(temp.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    @RequestMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}, value="/greeting/big-content")
    public Greeting greetingBigContent(@ModelAttribute BigData data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return new Greeting(counter.incrementAndGet(),
                json);
    }

    public static String objectToUrlEncodedString(Object object, Gson gson) {
        return jsonToUrlEncodedString((JsonObject) new JsonParser().parse(gson.toJson(object)));
    }

    private static String jsonToUrlEncodedString(JsonObject jsonObject) {
        return jsonToUrlEncodedString(jsonObject, "");
    }

    private static String jsonToUrlEncodedString(JsonObject jsonObject, String prefix) {
        String urlString = "";
        for (Map.Entry<String, JsonElement> item : jsonObject.entrySet())
            if (item.getValue() != null && item.getValue().isJsonObject()) {
                urlString += jsonToUrlEncodedString(
                        item.getValue().getAsJsonObject(),
                        prefix.isEmpty() ? item.getKey() : prefix + "[" + item.getKey() + "]"
                );
            } else {
                urlString += prefix.isEmpty() ?
                        item.getKey() + ":" + item.getValue().getAsString() + "\n" :
                        prefix + "[" + item.getKey() + "]=" + item.getValue().getAsString();
            }
        return urlString;
    }
}
