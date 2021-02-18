package com.image.app.imageapp.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"path", "content", "size"})
@Getter
@Setter
@ToString
public class ImageModel {

  private String name;
  private String path;
  private byte[] content;
  private long size;

  public ImageModel(File file) throws IOException {
    this.name = file.getName();
    this.path = file.getAbsolutePath();
    this.size = file.length();
    this.content = FileCopyUtils.copyToByteArray(file);
  }
}
