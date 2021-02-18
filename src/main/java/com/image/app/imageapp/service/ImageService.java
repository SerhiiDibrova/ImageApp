package com.image.app.imageapp.service;

import com.image.app.imageapp.domain.ImageFilter;
import com.image.app.imageapp.domain.ImageModel;
import com.image.app.imageapp.util.CommonUtil;
import com.image.app.imageapp.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"images"})
public class ImageService {

  private List<ImageModel> images = new ArrayList<>();

  @PostConstruct
  private void fillUsers() throws URISyntaxException, IOException {
    images.add(new ImageModel(CommonUtil.getFileFromResource("images/smile1.jpg")));
    images.add(new ImageModel(CommonUtil.getFileFromResource("images/smile2.jpg")));
    images.add(new ImageModel(CommonUtil.getFileFromResource("images/smile3.jpg")));
  }

  @Cacheable
  public Flux<ImageModel> findAll() {
    return Flux.fromIterable(this.images);
  }

  @Cacheable
  public Flux<ImageModel> findAllByFilter(ImageFilter filter) {
    Function<ImageModel, Boolean> name =
        img -> StringUtils.isEmpty(filter.getName()) || img.getName().equals(filter.getName());
    Function<ImageModel, Boolean> search =
        img ->
            StringUtils.isEmpty(filter.getSearchTerm())
                || (img.getName().equals(filter.getSearchTerm())
                    || img.getPath().equals(filter.getSearchTerm()));
    Predicate<ImageModel> predicate = img -> name.apply(img) && search.apply(img);

    List<ImageModel> filterModels =
        this.images.stream().filter(predicate).collect(Collectors.toList());
    List<ImageModel> imageModelList =
        ListUtils.getPage(filterModels, filter.getPage(), this.images.size());
    return Flux.fromIterable(imageModelList);
  }

  // update by name
  @CachePut
  public Mono<ImageModel> update(ImageModel imageModel) {
    for (ImageModel model : this.images) {
      if (model.getName().equals(imageModel.getName())) {
        model.setContent(imageModel.getContent());
        model.setSize(imageModel.getSize());
        model.setPath(imageModel.getPath());
        return Mono.just(model);
      }
    }
    throw new RuntimeException("Cannot update image by name");
  }

  // create
  @CachePut
  public void create(ImageModel imageModel) {
    if (this.images.contains(imageModel)) {
      throw new RuntimeException("Cannot insert image, image name is exist");
    }
    this.images.add(imageModel);
  }

  public Mono<ImageModel> findByName(String name) {
    for (ImageModel model : this.images) {
      if (model.getName().equals(name)) {
        return Mono.just(model);
      }
    }
    return Mono.empty();
  }
}
