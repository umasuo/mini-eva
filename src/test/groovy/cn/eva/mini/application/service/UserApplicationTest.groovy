package cn.eva.mini.application.service

import org.springframework.data.redis.core.BoundHashOperations
import org.springframework.data.redis.core.RedisTemplate
import spock.lang.Specification

/**
 * Test cases for user application.
 *  For test application, we mock db,cache and other third part class.
 */
class UserApplicationTest extends Specification {

    RedisTemplate redisTemplate = Mock(RedisTemplate)

    def setup() {

    }

    def "Test 1.1: test for redis"() {
        BoundHashOperations boundHashOperations = Mock(BoundHashOperations)
        when:
        redisTemplate.boundHashOps(_) >> boundHashOperations
        boundHashOperations.get(_) >> "test"
        String ss = redisTemplate.boundHashOps("key").get("leu")
        then:
        ss == "test"
    }
}
