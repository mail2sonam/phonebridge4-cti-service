package com.eupraxia.telephony.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;

public class YamlUtil {

 private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
 
 public static void printYAML(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
  mapper.writeValue(System.out, obj);
 }

 public static <T> T getObject(String yaml, Class<T> clazz)
   throws JsonParseException, JsonMappingException, IOException {	 
  return mapper.readValue(yaml, clazz);

 }

 public static <T> List<T> getListOfObjects(String yaml, Class<T> clazz)
   throws JsonParseException, JsonMappingException, IOException {
  CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
  List<T> ts = mapper.readValue(yaml, listType);
  return ts;

 }
}
