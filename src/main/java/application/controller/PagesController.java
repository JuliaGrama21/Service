package application.controller;

import application.controller.exception.PageNotFoundException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class PagesController {

    private Map<String, String> pages = new HashMap<>();

    @PostConstruct
    private void init() throws IOException {
        final String resourceFolder = "/pages/";
        ClassPathResource classPathResource = new ClassPathResource(resourceFolder);

        try (Stream<Path> walk = Files.walk(classPathResource.getFile().toPath())) {
            walk
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".html"))
                    .forEach(path -> {
                        try {
                            String content = new String(Files.readAllBytes(path));

                            String fileName = path.getFileName().toString();
                            String fileNameWithoutExtension = fileName.split("\\.")[0];

                            pages.put(fileNameWithoutExtension, content);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/{page:^[^.]*$}")
    public String page(@PathVariable String page) {
        String content = pages.get(page);

        if (content == null) {
            throw new PageNotFoundException();
        }

        return content;
    }

}