package com.image.app.imageapp.util;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public final class CommonUtil {

  // get a file from the resources folder
  public static InputStream getFileFromResourceAsStream(String fileName) {

    // The class loader that loaded the class
    ClassLoader classLoader = CommonUtil.class.getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);

    // the stream holding the file content
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    }
    return inputStream;
  }

  /*
     The resource URL is not working in the JAR
     If we try to access a file that is inside a JAR,
     It throws NoSuchFileException (linux), InvalidPathException (Windows)
  */
  public static File getFileFromResource(String fileName) throws URISyntaxException {

    ClassLoader classLoader = CommonUtil.class.getClassLoader();
    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    }
    // failed if files have whitespaces or special characters
    // return new File(resource.getFile());

    return new File(resource.toURI());
  }
}
