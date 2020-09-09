import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TreeModelTest {
        //JsonNodeFactory 实例，可全局共享  
        private JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        //JsonFactory 实例，线程安全  
        private JsonFactory jsonFactory = new JsonFactory();
/*
        {
                "name": "aaa",
                "email": "aaa@aa.com",
                "age": 24,
                "pets": {
                "petName": "kitty",
                        "petAge": 3
        },
                "skills": [ "java","linux","mysql"]
        }*/
@Test
        public void testTreeModelGenerate() throws IOException {

                //根节点
                ObjectNode rootNode = jsonNodeFactory.objectNode();
                //往根节点中添加普通键值对
                rootNode.put("name","aaa");
                rootNode.put("email","aaa@aa.com");
                rootNode.put("age",24);
                //往根节点中添加一个子对象
                ObjectNode petsNode = jsonNodeFactory.objectNode();
                petsNode.put("petName","kitty")
                .put("petAge",3);
                rootNode.set("pets", petsNode);
                //往根节点中添加一个数组
                ArrayNode arrayNode = jsonNodeFactory.arrayNode();
                arrayNode.add("java")
                .add("linux")
                .add("mysql");
                rootNode.set("skills", arrayNode);
                //调用ObjectMapper的writeTree方法根据节点生成最终json字符串
                JsonGenerator generator = jsonFactory.createGenerator(System.out);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeTree(generator, rootNode);
                }

                /*
                *
         {
        "name": "aaa",
        "email": "aaa@aa.com",
        "age": 24,
        "pets": {
            "petName": "kitty",
            "petAge": 3
        },
        "skills": [ "java","linux","mysql"]
    }
                * */
        private String jsonString =
                "{\"name\":\"aaa\",\"email\":\"aaa@aa.com\",\"age\":24,\"pets\":{\"petName\":\"kitty\",\"petAge\":3},\"skills\":[\"java\",\"linux\",\"mysql\"]}";

        @Test
        public void testTreeModelParse() throws JsonParseException, JsonMappingException, IOException {
                ObjectMapper objectMapper = new ObjectMapper();
                //使用ObjectMapper的readValue方法将json字符串解析到JsonNode实例中
                JsonNode rootNode = objectMapper.readTree(jsonString);
                //直接从rootNode中获取某个键的值，
                //这种方式在如果我们只需要一个长json串中某个字段值时非常方便
                JsonNode nameNode = rootNode.get("name");
                String name = nameNode.asText();
                System.out.println(name);
                //从 rootNode 中获取数组节点
                JsonNode skillsNode = rootNode.get("skills");
                for(int i = 0;i < skillsNode.size();i++) {
                        System.out.println(skillsNode.get(i).asText());
                }
                //从 rootNode 中获取子对象节点
                JsonNode petsNode = rootNode.get("pets");
                String petsName = petsNode.get("petName").asText();
                System.out.println(petsName);
        }
}