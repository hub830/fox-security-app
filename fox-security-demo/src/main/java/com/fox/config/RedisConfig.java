package com.fox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class RedisConfig {

/*  @SuppressWarnings("rawtypes")
  @Bean
  RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
      final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
      GenericJackson2JsonRedisSerializer json = new GenericJackson2JsonRedisSerializer();
      template.setConnectionFactory(redisConnectionFactory);
      template.setKeySerializer(new StringRedisSerializer());
      template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));
      template.setValueSerializer(json);
      return template;
  }
*/
  
  
  @Bean
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
  {
          Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
          ObjectMapper om = new ObjectMapper();
          om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
          om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
          jackson2JsonRedisSerializer.setObjectMapper(om);
          RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
          template.setConnectionFactory(redisConnectionFactory);
          template.setKeySerializer(new StringRedisSerializer());
          template.setValueSerializer(jackson2JsonRedisSerializer);
          template.setHashKeySerializer(jackson2JsonRedisSerializer);
          template.setHashValueSerializer(jackson2JsonRedisSerializer);
          template.afterPropertiesSet();
          return template;
  }
}