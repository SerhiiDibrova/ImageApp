package com.image.app.imageapp.controllers;

import com.image.app.imageapp.domain.ImageFilter;
import com.image.app.imageapp.domain.ImageModel;
import com.image.app.imageapp.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@RestController
public class ImageResource {

  @Autowired private ImageService imageService;

  @GetMapping("/api/v1/images/{name}")
  private Mono<ImageModel> getByName(@PathVariable String name) {

    log.info("get image by name {}", name);

    return imageService.findByName(name);
  }

  // @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @GetMapping("/api/v1/images")
  private Flux<ImageModel> getAll() {

    log.info("get all images");

    return imageService.findAll();
  }

  @GetMapping("/api/v2/images")
  private Flux<ImageModel> getAllByFilter(ImageFilter filter) {
    log.info("get all images by filter {}", filter);
    return imageService.findAllByFilter(filter);
  }

  @PutMapping(path = "/api/v1/images/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
  private Mono<ImageModel> update(@PathVariable String name, @RequestBody final ImageModel model) {
    model.setName(name);
    log.info("update image {}", model);

    return imageService.update(model);
  }

  @PostMapping(path = "/api/v1/images", consumes = MediaType.APPLICATION_JSON_VALUE)
  private void create(@RequestBody final ImageModel model) {
    log.info("create image {}", model);
    imageService.create(model);
  }
}
