package com.image.app.imageapp.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageFilter extends RequestParams {

  String searchTerm;
  String name;
}
